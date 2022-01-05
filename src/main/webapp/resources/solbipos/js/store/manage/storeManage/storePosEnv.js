/****************************************************************
 *
 * 파일명 : storePosEnv.js
 * 설  명 : 매장정보관리 > 매장환경설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

// DB구성방법
var vEnv1221 = "";

// 메인포스 번호
var mainPosList;
var mainPosNo = "";

app.controller('posEnvCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('posEnvCtrl', $scope, $http, false));

  // 선택된 포스
  $scope.selectedPosNo;
  $scope.setSelectedPosNo = function(posNo) {
    $scope.selectedPosNo = posNo;
  };
  $scope.getSelectedPosNo = function(){
    return $scope.selectedPosNo;
  };

  // 조회한 포스 목록
  $scope.posList;
  $scope.setPosList = function(list) {
    $scope.posList = list;
  };
  $scope.getPosList = function(){
    return $scope.posList;
  };

  // 팝업 오픈시 매장정보 조회
  $scope.$on("posEnvCtrl", function(event, data) {
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
      if($(this).attr("envstFg") == envGroupCd) {
        $(this).attr("class", "on");
      } else {
        $(this).removeAttr("class");
      }
    });

    if(envGroupCd === '00' || envGroupCd === '01' || envGroupCd === '02') { // 매장환경, 외식환경 유통환경 환경변수

      var cmmEnvScope = agrid.getScope('cmmEnvCtrl');
      cmmEnvScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === '03') { // 포스환경

      $("#cmmEnvArea").hide();
      $("#posEnvArea").show();
      $("#kitchenPrintArea").hide();
      $("#kitchenPrintProductArea").hide();

      $scope.getStorePosList();

    } else if(envGroupCd === "10") { // 포스기능키

      var posFuncKeyScope = agrid.getScope('funcKeyCtrl');
      posFuncKeyScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "98") { // 주방프린터

      var kitchenPrintScope = agrid.getScope('kitchenPrintCtrl');
      kitchenPrintScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "99") { // 주방프린터 상품연결

      var kitchenPrintProdScope = agrid.getScope('kitchenPrintProductCtrl');
      kitchenPrintProdScope.changeEnvGroup(envGroupCd);
    }
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

    $scope._postJSONQuery.withPopUp( '/store/manage/storeManage/storeManage/getPosList.sb', params, function(response){
      if (!$.isEmptyObject(response.data)) {

        var posList = response.data.data.list;

        $scope._setComboData("posNo", posList);
        $scope.setPosList(response.data.data.list);

        $scope.$broadcast('loadingPopupInactive');
        $scope.searchPosEnv();
      }
    });
  };

  /*********************************************************
   * 포스 선택값 변경
   * *******************************************************/
  $scope.changePosNo = function(s){
    var selected = s.selectedItem.posNo;
    if($scope.getSelectedPosNo() !== selected) {
      $scope.setSelectedPosNo(selected);
      $scope.searchPosEnv();
    }

    // DB구성요소[1221] 값 조회
    $scope.getEnv1221();

    // 메인포스 조회
    $scope.getMainPosList();
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
    params.envst = $("#srchConfig").val();
    $scope.$broadcast('loadingPopupActive');
    $scope._postJSONQuery.withPopUp( '/store/manage/storeManage/storeManage/getPosConfigList.sb', params, function(response){
      if (!$.isEmptyObject(response.data)) {
        var list = response.data.data.list;

        $scope.$broadcast('loadingPopupInactive');

        var envScope = agrid.getScope('storeEnvCtrl');
        envScope.setEnvContents("P", list);
      }
    });
  };


  /*********************************************************
   * 환경변수 저장
   * *******************************************************/
  $scope.save = function(){

    var storeScope  = agrid.getScope('storeManageCtrl');
    var objhqOfficeCd  = storeScope.getSelectedStore().hqOfficeCd;

    var objStatus       = document.getElementsByName("pos_status");
    var objEnvstCd      = document.getElementsByName("pos_envstCd");
    var objEnvstNm      = document.getElementsByName("pos_envstNm");
    var objEnvstGrpCd   = document.getElementsByName("pos_envstGrpCd");
    var objDefault      = document.getElementsByName("pos_defltYn");
    var objEnvstValCd   = document.getElementsByName("pos_envstValCd");
    var objDirctInYn    = document.getElementsByName("pos_dirctInYn");
    var objOldEnvstVal  = document.getElementsByName("pos_oldEnvstVal");
    var objHqEnvstValCd = document.getElementsByName("pos_hqEnvstValCd");
    var objHqEnvstValNm = document.getElementsByName("pos_hqEnvstValNm");
    var objTargtFg      = document.getElementsByName("pos_targtFg");

    // 포스형태[204]가 외식업-후불제인 경우에만 후불제를 선택할 수 있습니다.
    var env1015 = $("#env1015").val();
    var env4020 = $("#env4020").val();
    if(env1015 == "03") {
      if(env4020 != "0") {
        $scope._popMsg(messages["storeManage.only.select.postPay"]);
        return false;
      }
    }

    var env4021 = $("#env4021").val(); // 포스-메인여부

    // DB구성방법 [1221:통합DB]환경 사용시 메인포스가 반드시 존재해야 합니다.
    if(vEnv1221 === "0") {
      if(env4021 === "2") {
        if(mainPosList.length === 0){ // 메인포스가 없을 때
          $scope._popMsg(messages["storeManage.require.mainPos.msg"]);
          return false;
        }else{
          if(mainPosList.length === 1){ // 메인포스가 1개 일때
            if(mainPosNo === $scope.getSelectedPosNo()) {
              $scope._popMsg(messages["storeManage.require.mainPos.msg"]);
              return false;
            }
          }
        }
      }
    }

    // DB구성방법 [1221:개별DB]환경은 서브포스를 사용할 수 없습니다.
    if(vEnv1221 === "1") {
      if(env4021 === "2") {
        $scope._popMsg(messages["storeManage.notUse.subPos.msg"]);
        return false;
      }
    }

    var chngCnt  = 0; // 변경된 건수
    var arrChg = []; //  변경된 환경변수 배열 Key 값
    var params = new Array();

    for(var i=0; i<objEnvstCd.length; i++){

      if(objDirctInYn[i].value == "Y" && objEnvstValCd[i].value == ""){
        var msgStr = messages["hqManage.envSetting"] + " [" + objEnvstCd[i].value + "] "+ objEnvstNm[i].value
            + messages["storeManage.require.regist.inputEnv"]
        ;

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

      if(objOldEnvstVal[i].value != $("#env"+objEnvstCd[i].value).val()) {
        arrChg.push(i);
        chngCnt ++;
      }
    }

    if(chngCnt == 0 ){
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
        obj.posNo       = $scope.getSelectedPosNo();
        obj.useYn       = "Y";

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
        obj.posNo       = $scope.getSelectedPosNo();
        obj.useYn       = "Y";

        params.push(obj);
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);

      $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/savePosConfig.sb", params, function () {

        // 나머지는 모두 서브포스로 강제 업데이트
        if(vEnv1221 === "0") {
          if (env4021 === "1") {
            $scope.updateToSubPos();
          }
        }

        $scope.$broadcast('loadingPopupInactive');
        $scope._popMsg(messages["cmm.saveSucc"]);
        // 재조회 - 포스명칭 selectBox까지 초기화되어, 그부분 없이 바로 포스 환경설정 값 조회 (2020.04.03_이다솜)
        //var envScope = agrid.getScope('storeEnvCtrl');
        //$scope.changeEnvGroup(envScope.getEnvGroupCd());
        $scope.searchPosEnv();
      });
    });
    event.preventDefault();
  };

  // 서브포스로 변경
  $scope.updateToSubPos = function(){

    var storeScope    = agrid.getScope('storeManageCtrl');
    var params = {};

    params.storeCd = storeScope.getSelectedStore().storeCd;
    params.envstCd = "4021";
    params.posNo = $scope.getSelectedPosNo();

    $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/updateToSubPos.sb", params, function () {});
  };

  // DB구성요소[1221] 값 조회
  $scope.getEnv1221 = function(){

    // 초기화
    vEnv1221 = "";

    var storeScope    = agrid.getScope('storeManageCtrl');
    var params = {};
    params.storeCd = storeScope.getSelectedStore().storeCd;
    params.envstCd = "1221";

    $scope._postJSONQuery.withOutPopUp( "/store/manage/storeManage/storeManage/getStoreEnvVal.sb", params, function(result) {
      if (result.data.status === "OK") {
        vEnv1221 = result.data.data;
      }
      console.log("vEnv1221 : " + vEnv1221);
    });

  };

  // 사용중인 메인포스 번호 가져오기
  $scope.getMainPosList = function(){

    // 초기화
    mainPosList = null;
    mainPosNo =  "";

    var storeScope    = agrid.getScope('storeManageCtrl');
    var params = {};
    params.storeCd = storeScope.getSelectedStore().storeCd;
    params.envstCd = "4021";
    params.envstVal = "1";

    $scope._postJSONQuery.withOutPopUp("/store/manage/storeManage/storeManage/getEnvPosList.sb", params, function(result){

      // 메인포스로 사용하는 포스번호 가져오기
      mainPosList = result.data.data.mainPosList;

      if(mainPosList !== null && mainPosList !== "" && mainPosList.length > 0){
        for(var i=0; i<mainPosList.length; i++){
          mainPosNo += "," +  mainPosList[i].posNo;
        }
        mainPosNo = mainPosNo.substring(1, mainPosNo.length);
      }

      console.log("mainPosNo : " + mainPosNo);
    });
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
    var popup = $scope.posNmLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    popup.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('posNmCtrl');
      }, 50)
    });

    popup.show(true, function (s) {
    });
  };


  /*********************************************************
   * 포스 설정복사
   * *******************************************************/
  $scope.copyPosSetting = function(){
    var popup = $scope.copyPosEnvLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    popup.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('copyPosEnvCtrl');
      }, 50)
    });

    popup.show(true, function (s) {
    });
  };


  /*********************************************************
   * 포스 삭제
   * *******************************************************/
  $scope.deletePos = function(){

    // 01번 포스는 삭제 할 수 없습니다.
    if($scope.getSelectedPosNo()== "01") {
      $scope._popMsg(messages["storeManage.unable.delete.defaultPos"]);
      return false;
    }

    var storeScope    = agrid.getScope('storeManageCtrl');
    var param         = {};
    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;
    params.posNo      = $scope.getSelectedPosNo();

    $scope.$broadcast('loadingPopupActive');
    $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/deletePos.sb", params, function () {
      $scope.$broadcast('loadingPopupInactive');
      $scope._popMsg(messages["cmm.delSucc"]);

      // 재조회
      var envScope = agrid.getScope('storeEnvCtrl');
      $scope.searchCmmEnv(envScope.getEnvGroupCd());
    });

  };

}]);
