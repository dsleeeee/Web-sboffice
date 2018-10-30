/****************************************************************
 *
 * 파일명 : kitchenPrint.js
 * 설  명 : 매장정보관리 > 매장환경설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

app.controller('kitchenPrintCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('kitchenPrintCtrl', $scope, $http, false));

  var posScope = agrid.getScope("posEnvCtrl");
  var posList = posScope.getPosList();

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("prterKindDataMap", prterKind);
  $scope._setComboData("prterPortDataMap", prterPort);
  $scope._setComboData("prterSpeedDataMap", prterSpeed);
  $scope._setComboData("useYnFgDataMap", useYn);
  $scope._setComboData("posDataMap", posList);  // posList datamap

  // 주방프린터 영역 보여줌
  $scope.$on("kitchenPrintCtrl", function(event, data) {

    $("#kitchenPrintArea").show();

    $scope.getKitchenPrintList();

    event.preventDefault();
  });

  /*********************************************************
   * 주방프린터 목록 조회
   * *******************************************************/
  $scope.getKitchenPrintList = function(){

    var param         = {};
    var storeScope    = agrid.getScope('storeManageCtrl');
    param.hqOfficeCd  = storeScope.getSelectedStore().hqOfficeCd;
    param.storeCd     = storeScope.getSelectedStore().storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/store/manage/storeManage/storeManage/getKitchenPrintInfo.sb", params, function() {}, false);
  };


  /*********************************************************
   * 주방프린터 추가
   * *******************************************************/
  $scope.addRow = function(){

    // 파라미터 설정
    var params        = {};
    params.status     = "I";
    params.gChk       = true;
    params.posNo      ="01";
    params.prterKind  ="0";
    params.prterPort  = "0";
    params.prterSpeed = "0";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };


  /*********************************************************
   * 주방프린터 삭제
   * *******************************************************/
  $scope.delete = function() {
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      if ($scope.flex.collectionView.items[i].gChk === true) {
        $scope.flex.itemsSource.removeAt(i);
      }
    }
  };

  /*********************************************************
   * 주방프린터 저장
   * *******************************************************/
  $scope.save = function(){

    var storeScope    = agrid.getScope('storeManageCtrl');

    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].storeCd = storeScope.selectedStore.storeCd;
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].storeCd = storeScope.selectedStore.storeCd;
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].storeCd = storeScope.selectedStore.storeCd;
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    // $scope._save("/store/manage/storeManage/storeManage/saveKitchenPrintInfo.sb", params,
    //     function(){
    //       s_alert.pop("<s:message code='cmm.saveSucc' />");
    //     }
    // );


  };



  /*********************************************************
   * 매장환경 분류 탭 변경
   * *******************************************************/
  // $scope.changeEnvGroup = function(envGroupCd){
  //
  //   var storeScope    = agrid.getScope('storeManageCtrl');
  //   var storeCd       = storeScope.getSelectedStore().storeCd;
  //   var storeNm       = storeScope.getSelectedStore().storeNm;
  //
  //   $("#storeEnvTitle").text("[" + storeCd + "] " + storeNm);
  //
  //   // 환경변수 셋팅
  //   var envScope = agrid.getScope('storeEnvCtrl');
  //   envScope.setEnvGroupCd(envGroupCd);
  //
  //   $("#envGroupTab li a").each(function(index, item){
  //
  //     if($(this).attr("envstFg") == envGroupCd) {
  //       $(this).attr("class", "on");
  //     } else {
  //       $(this).removeAttr("class");
  //     }
  //   });
  //
  //   if(envGroupCd === "00" || envGroupCd === "01" || envGroupCd === "02") { // 매장환경, 외식환경 유통환경 환경변수
  //
  //     $("#cmmEnvArea").show();
  //     $("#posEnvArea").hide();
  //
  //     $scope.searchCmmEnv(envGroupCd);
  //
  //   } else if(envGroupCd === "03") { // 포스환경
  //
  //     $("#posEnvArea").show();
  //     $("#cmmEnvArea").hide();
  //
  //     var posEnvScope = agrid.getScope('posEnvCtrl');
  //     posEnvScope.changeEnvGroup(envGroupCd);
  //   }
  // };

  /*********************************************************
   * 매장환경 / 외식환경 / 유통환경
   * *******************************************************/
  // $scope.searchCmmEnv = function(envGroupCd){
  //
  //   var params        = {};
  //   var storeScope    = agrid.getScope('storeManageCtrl');
  //
  //   params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
  //   params.storeCd    = storeScope.getSelectedStore().storeCd;
  //   params.envstFg    = envGroupCd;
  //
  //   $scope.$broadcast('loadingPopupActive');
  //   $http({
  //     method : 'POST',
  //     url    : '/store/manage/storeManage/storeManage/getStoreConfigList.sb',
  //     params : params,
  //     headers: {'Content-Type': 'application/json; charset=utf-8'}
  //   }).then(function successCallback(response) {
  //     if (!$.isEmptyObject(response.data)) {
  //       var list = response.data.data.list;
  //
  //       $scope.$broadcast('loadingPopupInactive');
  //       // setEnvContents("S", list);
  //
  //       var envScope = agrid.getScope('storeEnvCtrl');
  //       envScope.setEnvContents("S", list);
  //
  //     }
  //   }, function errorCallback(response) {
  //     $scope._popMsg(messages["cmm.saveFail"]);
  //     return false;
  //   }).then(function () {
  //   });
  // };

  /*********************************************************
   * 환경변수 저장
   * *******************************************************/
  // $scope.save = function(){
  //
  //   var objStatus       = document.getElementsByName("status");
  //   var objEnvstCd      = document.getElementsByName("envstCd");
  //   var objEnvstNm      = document.getElementsByName("envstNm");
  //   var objEnvstGrpCd   = document.getElementsByName("envstGrpCd");
  //   var objDefault      = document.getElementsByName("defltYn");
  //   var objEnvstValCd   = document.getElementsByName("envstValCd");
  //   var objDirctInYn    = document.getElementsByName("dirctInYn");
  //   var objOldEnvstVal  = document.getElementsByName("oldEnvstVal");
  //
  //   // 봉사료 사용구분이 'Y'일 경우, 봉사료율이 설정되어야 함.
  //   var env2001 = $("#env2001").val();
  //   var env2002 = $("#env2002").val();
  //
  //   if(env2001 == '1' && (env2002 == null || env2002 == '')) {
  //     $scope._popMsg(messages["storeManage.require.serviceRate"]);
  //     return false;
  //   }
  //
  //   var chngCnt  = 0; // 변경된 건수
  //   var params = new Array();
  //
  //   for(var i=0; i<objEnvstCd.length; i++){
  //
  //     if(objDirctInYn[i].value == "Y" && objEnvstValCd[i].value == ""){
  //       var msgStr = messages["hqManage.envSetting"] + " [" + objEnvstCd[i].value + "] "+ objEnvstNm[i].value
  //           + messages["storeManage.require.regist.inputEnv"]
  //       ;
  //
  //       $scope._popMsg(msgStr);
  //       return false;
  //     }
  //
  //     if(objOldEnvstVal[i].value != $("#env"+objEnvstCd[i].value).val()) {
  //       chngCnt ++;
  //     }
  //   }
  //
  //   if(chngCnt == 0 ){
  //     $scope._popMsg(messages["cmm.not.modify"]);
  //     return false;
  //   }
  //
  //   $scope._popConfirm( messages["cmm.choo.save"], function(){
  //
  //     var storeScope = agrid.getScope('storeManageCtrl');
  //
  //     for(var i=0; i<objEnvstCd.length; i++){
  //
  //       var obj = {};
  //       obj.hqOfficeCd  = storeScope.getSelectedStore().hqOfficeCd;
  //       obj.storeCd     = storeScope.getSelectedStore().storeCd;
  //       obj.status      = objStatus[i].value;
  //       obj.envstCd     = objEnvstCd[i].value;
  //       obj.envstNm     = objEnvstNm[i].value;
  //       obj.envstGrpCd  = objEnvstGrpCd[i].value;
  //       obj.envstVal    = objEnvstValCd[i].value;
  //       obj.dirctInYn   = objDirctInYn[i].value;
  //
  //       params.push(obj);
  //     }
  //
  //     $scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);
  //
  //     $scope._save( "/store/manage/storeManage/storeManage/saveStoreConfig.sb", params, function () {
  //       $scope.$broadcast('loadingPopupInactive');
  //       $scope._popMsg(messages["cmm.saveSucc"]);
  //     });
  //     // 재조회
  //     // $scope.changeEnvGroup($scope.getEnvGroupCd());
  //   });
  //   event.preventDefault();
  // };


}]);

