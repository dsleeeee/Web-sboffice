/****************************************************************
 *
 * 파일명 : storeInfo.js
 * 설  명 : 매장정보관리 > 매장환경설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

app.controller('storeEnvCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeEnvCtrl', $scope, $http, false));

  // 조회시 선택된 환경변수 그룹 (재조회를 위해 변수로 관리)
  $scope.envGroupCd;
  $scope.setEnvGroupCd = function(envGroupCd){
    $scope.envGroupCd = envGroupCd;
  };
  $scope.getEnvGroupCd = function(){
    return $scope.envGroupCd;
  };

  $scope.selectedPosNo = "01";
  $scope.setSelectedPosNo = function(posNo){
    $scope.selectedPosNo = posNo;
  };
  $scope.getSelectedPosNo = function(){
    return $scope.selectedPosNo;
  };

  // 조회된 POS LIST
  $scope.posList;
  $scope.setPosList = function(list){
    $scope.posList = list;
  }
  $scope.getPosList = function(){
    return $scope.posList;
  }


  // 팝업 오픈시 매장정보 조회
  $scope.$on("storeEnvCtrl", function(event, data) {
    event.preventDefault();
  });

  /*********************************************************
   * 매장정보 탭 클릭
   * *******************************************************/
  $scope.changeTab = function(){
    $scope.storeEnvLayer.hide();

    var infoPopup = $scope.storeInfoLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    infoPopup.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('storeInfoCtrl');
      }, 50)
    });

    // 팝업 닫을때
    infoPopup.show(true, function (s) {
    });

  };

  /*********************************************************
   * 매장환경 분류 탭 변경
   * *******************************************************/
  $scope.changeEnvGroup = function(envGroupCd){

    var storeScope    = agrid.getScope('storeManageCtrl');
    var storeCd       = storeScope.getSelectedStore().storeCd;
    var storeNm       = storeScope.getSelectedStore().storeNm;

    $("#storeEnvTitle").text("[" + storeCd + "] " + storeNm);


    $scope.setEnvGroupCd(envGroupCd);

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

      $scope.getStorePosList();
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
        $scope.setEnvContents(list);
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
    });
  };


  /*********************************************************
   * 포스 선택값 변경
   * *******************************************************/
  $scope.getStorePosList = function(){

    var params        = {};
    var storeScope    = agrid.getScope('storeManageCtrl');

    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;

    $scope.$broadcast('loadingPopupActive');

    $http({
      method : 'POST',
      url    : '/store/manage/storeManage/storeManage/getPosList.sb',
      params : params,
      headers: {'Content-Type': 'application/json; charset=utf-8'}
    }).then(function successCallback(response) {
      if (!$.isEmptyObject(response.data)) {

        var posList = response.data.data.list;

        $scope._setComboData("posNo", posList);
        $scope.setPosList(response.data.data.list);
        $scope.$broadcast('loadingPopupInactive');
        $scope.searchPosEnv();
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  /*********************************************************
   * 포스 선택값 변경
   * *******************************************************/
  $scope.changePosNo = function(s){
    if($scope.getSelectedPosNo() !== s.selectedValue) {
      $scope.setSelectedPosNo(s.selectedValue);
      $scope.searchPosEnv();
    }
  };

  /*********************************************************
   * 포스환경
   * *******************************************************/
  $scope.searchPosEnv = function(){

    var params        = {};
    var storeScope    = agrid.getScope('storeManageCtrl');

    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;
    params.posNo      = $scope.getSelectedPosNo();
    params.envstFg    = "03"; // 포스환경

    $scope.$broadcast('loadingPopupActive');

    $http({
      method : 'POST',
      url    : '/store/manage/storeManage/storeManage/getPosConfigList.sb',
      params : params,
      headers: {'Content-Type': 'application/json; charset=utf-8'}
    }).then(function successCallback(response) {
      if (!$.isEmptyObject(response.data)) {

        var list    = response.data.data.list;

        $scope.$broadcast('loadingPopupInactive');
        $scope.setEnvContents(list);

      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  /*********************************************************
   * 조회한 환경변수 그리기
   * *******************************************************/
  $scope.setEnvContents = function(list){

    // 환경변수 타입 (P:포스환경변수, S:그 외)
    var envType = ($scope.getEnvGroupCd() == "03" ? "P" : "S" );

    var innerHtml = "";
    var envCnt    = 0;
    var allCnt    = 0; // 전체 환경값 갯수
    var existCnt  = 0; // 현재 등록된 환경값 갯수

    // 환경변수 테이블 그리기
    for(var i=0; i<envstGrp.length; i++) {

      var storeEnvCnt    = 0;
      var storeEnvHtml   = "";
      var storeEnvstGrp  = envstGrp[i];

      storeEnvHtml += "<h3 class='h3_tbl2 lh30'>"+storeEnvstGrp.name+" <button class='open'></button>";

      if(i == 0){ // 기본값으로 설정 버튼
        storeEnvHtml += "<span class='fr'><a href='javascript:;' class='btn_grayS' id='btnDefault'>";
        storeEnvHtml += messages["storeManage.setting.default.env"];
        storeEnvHtml += "</a></span>";
      }

      storeEnvHtml += "</h3>";
      storeEnvHtml += "<table class='searchTbl'>";
      storeEnvHtml += "  <colgroup>";
      storeEnvHtml += "    <col class='w5' />";
      storeEnvHtml += "    <col class='w25' />";
      storeEnvHtml += "    <col class='w20' />";
      storeEnvHtml += "    <col class='w5' />";
      storeEnvHtml += "    <col class='w25' />";
      storeEnvHtml += "    <col class='w20' />";
      storeEnvHtml += "  </colgroup>";
      storeEnvHtml += "<tbody>";

      var b_env = ""; // 변경전의 환경변수

      for(var j=0; j<list.length; j++) {

        if(storeEnvstGrp.value == list[j].envstGrpCd){
          if(b_env == "" || b_env != list[j].envstCd ){
            if(storeEnvCnt == 0 || storeEnvCnt % 2 == 0) storeEnvHtml += "<tr>";

            storeEnvHtml += "  <th class='tc'>" + list[j].envstCd + "</th>";
            storeEnvHtml += "  <td>" + list[j].envstNm + "</td>";
            storeEnvHtml += "  <td>";

            if(list[j].dirctInYn == "Y"){ // 직접입력
              storeEnvHtml += "    <input type='text' name='envstValCd' id='env" + list[j].envstCd + "' class='sb-input w100'>";
            } else {  // 값 선택
              storeEnvHtml += "    <select name='envstValCd' id='env" + list[j].envstCd + "' class='sb-select w100' />";
            }

            storeEnvHtml += "    <input type='hidden' name='status'    value='"+ (list[j].existFg =="N" ? "I":"U") +"'>";
            storeEnvHtml += "    <input type='hidden' name='envstCd'   value='"+ list[j].envstCd +"'>";
            storeEnvHtml += "    <input type='hidden' name='envstNm'   value='"+ list[j].envstNm +"'>";
            storeEnvHtml += "    <input type='hidden' name='envstGrpCd'value='"+ list[j].envstGrpCd +"'>";
            storeEnvHtml += "    <input type='hidden' name='defltYn'   value='"+ list[j].defltYn +"'>";
            storeEnvHtml += "    <input type='hidden' name='dirctInYn' value='"+ list[j].dirctInYn +"'>";
            storeEnvHtml += "    <input type='hidden' name='targtFg'   value='"+ list[j].targtFg +"'>";
            storeEnvHtml += "    <input type='hidden' name='oldEnvstVal'   value='"+ list[j].selEnvstVal +"'>";
            storeEnvHtml += "  </td>";

            b_env = list[j].envstCd;
            storeEnvCnt ++;
            allCnt ++;

            if(list[j].existFg == "Y") existCnt++;
            if(list[j].envstCdCnt == storeEnvCnt && (storeEnvCnt % 2 == 1)) {
              storeEnvHtml += "  <td class='tc'></td>";
              storeEnvHtml += "  <td></td>";
              storeEnvHtml += "  <td></td>";
              storeEnvHtml += "</tr>";
            } else if(storeEnvCnt % 2 == 0) {
              storeEnvHtml += "</tr>";
            }
          }
        }
      }

      storeEnvHtml += "  </tbody>";
      storeEnvHtml += "</table>";
      storeEnvHtml += "</div>";
      storeEnvHtml += "<br>";

      if(storeEnvCnt > 0) innerHtml += storeEnvHtml;
    }

    if(envType === "S") {
      $("#storeConfigContent").html(innerHtml);
    } else if(envType === "P") {
      $("#posConfigContent").html(innerHtml);
    }

    // 환경변수 select option 추가
    var envstCd = "";
    var sOption = false;

    for(var i=0; i<list.length; i++){
      if(list[i].dirctInYn == "N") {
        $("#env"+list[i].envstCd).append("<option value='"+ list[i].envstValCd +"' >" + list[i].envstValNm +  "</option>");
        if(i == 0 || envstCd != list[i].envstCd ) {
          envstCd = list[i].envstCd;
          if(list[i].selEnvstVal == list[i].envstValCd){
            sOption = true;
            $("#env"+list[i].envstCd).val(list[i].envstValCd).prop("selected", true);
          }
          else{
            sOption = false;
            $("#env"+list[i].envstCd).val(list[i].envstValCd).prop("selected", true);
          }
        }else if(list[i].selEnvstVal == list[i].envstValCd){
          sOption = true;
          $("#env"+list[i].envstCd).val(list[i].envstValCd).prop("selected", true);
        }else if(sOption == false && list[i].defltYn == "Y") {
          $("#env"+list[i].envstCd).val(list[i].envstValCd).prop("selected", true);
        }

        if(list[i].defltYn == "Y") {
          $("#env"+list[i].envstCd).attr("defaultVal", list[i].envstValCd);
        }

      } else {
        if(list[i].selEnvstVal === "" || list[i].selEnvstVal == null ) {
          $("#env"+list[i].envstCd).val("*");
        } else{
          $("#env"+list[i].envstCd).val(list[i].selEnvstVal);
        }
      }
    }

    // 등록되지 않은 환경값이 있을 경우
    if(allCnt > existCnt) {

      var msg = messages["storeManage.no.regist.env"]
              + messages["storeManage.require.regist.env"] +"\n"
              + allCnt + messages["storeManage.total.env.count"]
              + (allCnt - existCnt)+ messages["storeManage.no.regist.env.count"]
      ;

      $scope._popMsg(msg);
    }
  };

  /*********************************************************
   * 환경변수 저장 (포스환경 제외)
   * *******************************************************/
  $scope.save = function(){

    // 환경변수 타입 (P:포스환경변수, S:그 외)
    var envType = ($scope.getEnvGroupCd() == "03" ? "P" : "S" );

    var objStatus       = document.getElementsByName("status");
    var objEnvstCd      = document.getElementsByName("envstCd");
    var objEnvstNm      = document.getElementsByName("envstNm");
    var objEnvstGrpCd   = document.getElementsByName("envstGrpCd");
    var objDefault      = document.getElementsByName("defltYn");
    var objEnvstValCd   = document.getElementsByName("envstValCd");
    var objDirctInYn    = document.getElementsByName("dirctInYn");
    var objOldEnvstVal  = document.getElementsByName("oldEnvstVal");


    if(envType === "S") {

      // 봉사료 사용구분이 'Y'일 경우, 봉사료율이 설정되어야 함.
      var env2001 = $("#env2001").val();
      var env2002 = $("#env2002").val();

      if(env2001 == '1' && (env2002 == null || env2002 == '')) {
        $scope._popMsg(messages["storeManage.require.serviceRate"]);
        return false;
      }
    }

    if(envType === "P") {

      // 포스형태[204]가 외식업-후불제인 경우에만 후불제를 선택할 수 있습니다.
      var env1015 = $("#env1015").val();
      var env4020 = $("#env4020").val();
      if(env1015 == "03") {
        if(env4020 != "0") {
          $scope._popMsg(messages["storeManage.only.select.postPay"]);
          return false;
        }
      }
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

        if(envType === "P") {
          obj.posNo   = $scope.getSelectedPosNo();
          obj.useYn   = "Y";
        }
        params.push(obj);
      }

      var saveUrl = "";
      if(envType === "S") {
        saveUrl = "/store/manage/storeManage/storeManage/saveStoreConfig.sb";
      }else {
        saveUrl = "/store/manage/storeManage/storeManage/savePosConfig.sb";
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);
      $scope._save(saveUrl, params, function () {
        $scope.$broadcast('loadingPopupInactive');
        $scope._popMsg(messages["cmm.saveSucc"]);
      });
      // 재조회
      $scope.changeEnvGroup($scope.getEnvGroupCd());
    });
    event.preventDefault();
  };

  /*********************************************************
   * 테이블 그룹설정
   * *******************************************************/
  $scope.settingTableGroup = function(){

    var popup = $scope.tableGroupLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    popup.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('tableGroupCtrl');
      }, 50)
    });

    popup.show(true, function (s) {
    });
  };


  /*********************************************************
   * 포스 명칭 설정
   * *******************************************************/
  $scope.settingPosNm = function(){

  };

  /*********************************************************
   * 포스 설정복사
   * *******************************************************/
  $scope.copyPosSetting = function(){

  };

  /*********************************************************
   * 포스 삭제
   * *******************************************************/
  $scope.deletePos = function(){

  };

}]);

