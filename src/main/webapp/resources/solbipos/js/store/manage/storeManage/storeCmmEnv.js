/****************************************************************
 *
 * 파일명 : storeCmmEnv.js
 * 설  명 : 매장정보관리 > 매장환경설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

app.controller('cmmEnvCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cmmEnvCtrl', $scope, $http, false));

  // 팝업 오픈시 매장정보 조회
  $scope.$on("cmmEnvCtrl", function(event, data) {
    event.preventDefault();
  });

  /*********************************************************
   * 매장환경 분류 탭 변경
   * *******************************************************/
  $scope.changeEnvGroup = function(envGroupCd){

    var storeScope    = agrid.getScope('storeManageCtrl');
    var storeCd       = storeScope.getSelectedStore().storeCd;
    var storeNm       = storeScope.getSelectedStore().storeNm;

    $("#storeEnvTitle").text("[" + storeCd + "] " + storeNm);

    // 환경변수 셋팅
    var envScope = agrid.getScope('storeEnvCtrl');
    envScope.setEnvGroupCd(envGroupCd);

    $("#envGroupTab li a").each(function(index, item){

      if($(this).attr("envstFg") == envGroupCd) {
        $(this).attr("class", "on");
      } else {
        $(this).removeAttr("class");
      }
    });

    if(envGroupCd === "00" || envGroupCd === "01" || envGroupCd === "02") { // 매장환경, 외식환경 유통환경 환경변수

      $("#cmmEnvArea").show();
      $("#posEnvArea").hide();

      $scope.searchCmmEnv(envGroupCd);

    } else if(envGroupCd === "03") { // 포스환경

      $("#posEnvArea").show();
      $("#cmmEnvArea").hide();

      var posEnvScope = agrid.getScope('posEnvCtrl');
      posEnvScope.changeEnvGroup(envGroupCd);
    }
  };

  /*********************************************************
   * 매장환경 / 외식환경 / 유통환경
   * *******************************************************/
  $scope.searchCmmEnv = function(envGroupCd){

    var params        = {};
    var storeScope    = agrid.getScope('storeManageCtrl');

    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;
    params.envstFg    = envGroupCd;

    $scope.$broadcast('loadingPopupActive');
    $http({
      method : 'POST',
      url    : '/store/manage/storeManage/storeManage/getStoreConfigList.sb',
      params : params,
      headers: {'Content-Type': 'application/json; charset=utf-8'}
    }).then(function successCallback(response) {
      if (!$.isEmptyObject(response.data)) {
        var list = response.data.data.list;

        $scope.$broadcast('loadingPopupInactive');
        // setEnvContents("S", list);

        var envScope = agrid.getScope('storeEnvCtrl');
        envScope.setEnvContents("S", list);

      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
    });
  };

  /*********************************************************
   * 환경변수 저장
   * *******************************************************/
  $scope.save = function(){

    var objStatus       = document.getElementsByName("status");
    var objEnvstCd      = document.getElementsByName("envstCd");
    var objEnvstNm      = document.getElementsByName("envstNm");
    var objEnvstGrpCd   = document.getElementsByName("envstGrpCd");
    var objDefault      = document.getElementsByName("defltYn");
    var objEnvstValCd   = document.getElementsByName("envstValCd");
    var objDirctInYn    = document.getElementsByName("dirctInYn");
    var objOldEnvstVal  = document.getElementsByName("oldEnvstVal");

    // 봉사료 사용구분이 'Y'일 경우, 봉사료율이 설정되어야 함.
    var env2001 = $("#env2001").val();
    var env2002 = $("#env2002").val();

    if(env2001 == '1' && (env2002 == null || env2002 == '')) {
      $scope._popMsg(messages["storeManage.require.serviceRate"]);
      return false;
    }

    var chngCnt  = 0; // 변경된 건수
    var params = new Array();

    for(var i=0; i<objEnvstCd.length; i++){

      if(objDirctInYn[i].value == "Y" && objEnvstValCd[i].value == ""){
        var msgStr = messages["hqManage.envSetting"] + " [" + objEnvstCd[i].value + "] "+ objEnvstNm[i].value
            + messages["storeManage.require.regist.inputEnv"]
        ;

        $scope._popMsg(msgStr);
        return false;
      }

      if(objOldEnvstVal[i].value != $("#env"+objEnvstCd[i].value).val()) {
        chngCnt ++;
      }
    }

    if(chngCnt == 0 ){
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }

    $scope._popConfirm( messages["cmm.choo.save"], function(){

      var storeScope = agrid.getScope('storeManageCtrl');

      for(var i=0; i<objEnvstCd.length; i++){

        var obj = {};
        obj.hqOfficeCd  = storeScope.getSelectedStore().hqOfficeCd;
        obj.storeCd     = storeScope.getSelectedStore().storeCd;
        obj.status      = objStatus[i].value;
        obj.envstCd     = objEnvstCd[i].value;
        obj.envstNm     = objEnvstNm[i].value;
        obj.envstGrpCd  = objEnvstGrpCd[i].value;
        obj.envstVal    = objEnvstValCd[i].value;
        obj.dirctInYn   = objDirctInYn[i].value;

        params.push(obj);
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);

      $scope._save( "/store/manage/storeManage/storeManage/saveStoreConfig.sb", params, function () {
        $scope.$broadcast('loadingPopupInactive');
        $scope._popMsg(messages["cmm.saveSucc"]);
      });
      // 재조회
      // $scope.changeEnvGroup($scope.getEnvGroupCd());
    });
    event.preventDefault();
  };


}]);

