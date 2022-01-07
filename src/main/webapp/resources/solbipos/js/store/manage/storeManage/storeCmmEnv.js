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

  $scope.changeTabSrch = function (){
    $("#srchConfig").val('');
  };

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

      if($(this).attr("envstFg") === envGroupCd) {
        $(this).attr("class", "on");
      } else {
        $(this).removeAttr("class");
      }
    });

    if(envGroupCd === "00" || envGroupCd === "01" || envGroupCd === "02") { // 매장환경, 외식환경 유통환경 환경변수

      $("#cmmEnvArea").show();
      $("#posEnvArea").hide();
      $("#posFuncKeyArea").hide();
      $("#kitchenPrintArea").hide();
      $("#kitchenPrintProductArea").hide();

      $scope.searchCmmEnv(envGroupCd);

    } else if(envGroupCd === "03") { // 포스환경

      var posEnvScope = agrid.getScope('posEnvCtrl');
      posEnvScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "10") { // 포스기능키

      var posFuncKeyScope = agrid.getScope('funcKeyCtrl');
      posFuncKeyScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "98"){ // 주방프린터

      var kitchenPrintScope = agrid.getScope('kitchenPrintCtrl');
      kitchenPrintScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "99") { // 주방프린터 상품연결

      var kitchenPrintProdScope = agrid.getScope('kitchenPrintProductCtrl');
      kitchenPrintProdScope.changeEnvGroup(envGroupCd);

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
    params.envst = $("#srchConfig").val();

    $scope.$broadcast('loadingPopupActive');

    $scope._postJSONQuery.withPopUp( '/store/manage/storeManage/storeManage/getStoreConfigList.sb', params, function(response){
      if (!$.isEmptyObject(response.data)) {
        var list = response.data.data.list;

        $scope.$broadcast('loadingPopupInactive');
        // setEnvContents("S", list);

        var envScope = agrid.getScope('storeEnvCtrl');
        envScope.setEnvContents("S", list);
      }
    });
  };

  /*********************************************************
   * 환경변수 저장
   * *******************************************************/
  $scope.save = function(){

    var storeScope  = agrid.getScope('storeManageCtrl');
    var objhqOfficeCd  = storeScope.getSelectedStore().hqOfficeCd;

    var objStatus       = document.getElementsByName("status");
    var objEnvstCd      = document.getElementsByName("envstCd");
    var objEnvstNm      = document.getElementsByName("envstNm");
    var objEnvstGrpCd   = document.getElementsByName("envstGrpCd");
    var objDefault      = document.getElementsByName("defltYn");
    var objEnvstValCd   = document.getElementsByName("envstValCd");
    var objDirctInYn    = document.getElementsByName("dirctInYn");
    var objOldEnvstVal  = document.getElementsByName("oldEnvstVal");
    var objHqEnvstValCd = document.getElementsByName("hqEnvstValCd");
    var objHqEnvstValNm = document.getElementsByName("hqEnvstValNm");
    var objTargtFg      = document.getElementsByName("targtFg");

    // 봉사료 사용구분이 'Y'일 경우, 봉사료율이 설정되어야 함.
    var env2001 = $("#env2001").val();
    var env2002 = $("#env2002").val();

    if(env2001 === '1' && (env2002 == null || env2002 === '')) {
      $scope._popMsg(messages["storeManage.require.serviceRate"]);
      return false;
    }

    var chngCnt  = 0; // 변경된 건수
    var arrChg = []; //  변경된 환경변수 배열 Key 값
    var params = [];

    for(var i=0; i<objEnvstCd.length; i++){

      if(objDirctInYn[i].value === "Y" && objEnvstValCd[i].value === ""){
        var msgStr = messages["hqManage.envSetting"] + " ["
            + objEnvstCd[i].value + "] "
            + objEnvstNm[i].value
            + messages["storeManage.require.regist.inputEnv"];

        $scope._popMsg(msgStr);
        return false;
      }

      if(objhqOfficeCd !== "00000" && objTargtFg[i].value === "X" && objHqEnvstValCd[i].value !== objEnvstValCd[i].value){
        var msgStr = "["
            + objEnvstCd[i].value + "] "
            + objEnvstNm[i].value
            + messages["storeManage.require.regist.hqEnvstVal"]
            + messages["storeManage.require.regist.hqEnvstVal2"]
            + "( '"+objHqEnvstValNm[i].value+"' )"
            + messages["storeManage.require.regist.hqEnvstVal3"];
          if(objHqEnvstValNm[i].value.toString() === 'null') {  // 본사 설정값이 없으면 '본사환경값을 먼저 세팅하여 주십시오' 출력
            msgStr = msgStr + messages["storeManage.require.regist.hqEnvstVal4"];
          }

        $scope._popMsg(msgStr);
        return false;
      }

      if(objOldEnvstVal[i].value !== $("#env"+objEnvstCd[i].value).val()) {
        arrChg.push(i);
        chngCnt ++;
      }
    }

    if(chngCnt === 0 ){
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }

    $scope._popConfirm( messages["cmm.choo.save"], function(){

      var storeScope = agrid.getScope('storeManageCtrl');

      /*for(var i=0; i<objEnvstCd.length; i++){

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
      }*/

      // 기존에 전체 환경변수값 저장에서 변경된 환경변수값만 저장되도록 수정
      for(var i=0; i<arrChg.length; i++){

        var obj = {};
        obj.hqOfficeCd  = storeScope.getSelectedStore().hqOfficeCd;
        obj.storeCd     = storeScope.getSelectedStore().storeCd;
        obj.status      = objStatus[arrChg[i]].value;
        obj.envstCd     = objEnvstCd[arrChg[i]].value;
        obj.envstNm     = objEnvstNm[arrChg[i]].value;
        obj.envstGrpCd  = objEnvstGrpCd[arrChg[i]].value;
        obj.envstVal    = objEnvstValCd[arrChg[i]].value;
        obj.dirctInYn   = objDirctInYn[arrChg[i]].value;

        params.push(obj);
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);

      $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/saveStoreConfig.sb", params, function () {

        // DB구성방법[1221]에 따라 매장포스들 포스-메인여부[4021] 환경설정값 수정
        $scope.updateToPos($("#env1221").val());

        $scope.$broadcast('loadingPopupInactive');
        $scope._popMsg(messages["cmm.saveSucc"]);

        // 재조회
        var envScope = agrid.getScope('storeEnvCtrl');
        $scope.searchCmmEnv(envScope.getEnvGroupCd());
      });
    });
    event.preventDefault();
  };

  // DB구성방법[1221]에 따라 매장포스들 포스-메인여부[4021] 환경설정값 수정
  $scope.updateToPos = function (env1221) {

    var storeScope = agrid.getScope('storeManageCtrl');
    var params = {};
    params.storeCd = storeScope.getSelectedStore().storeCd;
    params.envstCd = "4021";
    params.envstVal = "1";

    $scope._postJSONQuery.withOutPopUp("/store/manage/storeManage/storeManage/getEnvPosList.sb", params, function(result){

      // 메인포스로 사용하는 포스번호 가져오기
      var mainPosList = result.data.data.mainPosList;

      if(env1221 === "0"){ // DB구성방법[1221]이 통합DB 일 때

        if(mainPosList.length !== 1){ // 메인포스 1대가 아닌경우

          var mainUsePosNo = "";
          var params2 = {};
          params2.storeCd = storeScope.getSelectedStore().storeCd;

          // 메인으로 사용할 포스번호 조회
          $scope._postJSONQuery.withOutPopUp( "/store/manage/storeManage/storeManage/getUseMainPos.sb", params, function(result) {

              if (result.data.status === "OK") {
                mainUsePosNo = result.data.data;

                if(mainUsePosNo !== "" && mainUsePosNo !== null && mainUsePosNo !== undefined){
                   params2.storeCd = storeScope.getSelectedStore().storeCd;
                   params2.posNo = mainUsePosNo;
                   params2.envstCd = "4021";
                   params2.envstVal = "1";

                   // 메인포스 저장
                   $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/updatePosEnvVal.sb", params2, function () {

                   // 나머지 서브포스로 저장
                   $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/updateToSubPos.sb", params2, function () {});

                  });
                }
              }
            });
        }
      }

      if(env1221 === "1"){// DB구성방법[1221]이 개별DB 일 때
        var params3 = {};
        params3.storeCd = storeScope.getSelectedStore().storeCd;
        params3.envstCd = "4021";
        params3.envstVal = "1";

        // 모두 메인포스로 저장
        $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/updatePosEnvVal.sb", params3, function () {});
      }
    });
  }

}]);