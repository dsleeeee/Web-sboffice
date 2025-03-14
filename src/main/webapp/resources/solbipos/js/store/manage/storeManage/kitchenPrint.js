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

  // 그리드 DataMap 설정
  $scope.prterDataMap;
  $scope.prterKindDataMap;
  $scope.prterLabelKindDataMap;
  $scope.prterPortDataMap;
  $scope.prterSpeedDataMap;
  $scope.useYnFgDataMap;
  $scope.posDataMap;

  // 조회한 포스 목록
  $scope.posList;
  $scope.setPosList = function(list) {
    $scope.posList = list;
  };
  $scope.getPosList = function(){
    return $scope.posList;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 주방프린터 영역 보여줌
  $scope.$on("kitchenPrintCtrl", function(event, data) {
    event.preventDefault();
  });

  $scope.changeTabSrch = function (){
    $("#srchConfig").val('');

    // "" 는 탭을 클릭하여 환경설정값을 조회하였다는 의미
    $("#hdSrchYn").val("");
  };

  /*********************************************************
   * 환경변수 탭 변경
   * *******************************************************/
  $scope.changeEnvGroup = function(envGroupCd){

    // 탭 변경
    $("#envGroupTab li a").each(function(index, item){
      if($(this).attr("envstFg") === envGroupCd) {
        $(this).attr("class", "on");
      } else {
        $(this).removeAttr("class");
      }
    });

    if(envGroupCd === "00" || envGroupCd === "01" || envGroupCd === "02") { // 매장환경, 외식환경 유통환경 환경변수

      var cmmEnvScope = agrid.getScope('cmmEnvCtrl');
      cmmEnvScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "03") { // 포스환경

      var posEnvScope = agrid.getScope('posEnvCtrl');
      posEnvScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "10") { // 포스기능키

      var posFuncKeyScope = agrid.getScope('funcKeyCtrl');
      posFuncKeyScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "98") { // 주방프린터

      $("#cmmEnvArea").hide();
      $("#posEnvArea").hide();
      $("#posFuncKeyArea").hide();
      $("#kitchenPrintArea").show();
      $("#kitchenPrintProductArea").hide();

      $scope.setDataMap();

    } else if(envGroupCd === "99") { // 주방프린터 상품연결

      var kitchenPrintProdScope = agrid.getScope('kitchenPrintProductCtrl');
      kitchenPrintProdScope.changeEnvGroup(envGroupCd);

    }
  };

  /*********************************************************
   * Grid dataMap
   * *******************************************************/
  $scope.setDataMap = function(){

    // pos list 조회
    var params        = {};
    var storeScope    = agrid.getScope('storeManageCtrl');
    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;

    $scope.$broadcast('loadingPopupActive');

    $scope._postJSONQuery.withPopUp( '/store/manage/storeManage/storeManage/getPosList.sb', params, function(response){
      if (!$.isEmptyObject(response.data)) {

        $scope.setPosList(response.data.data.list);
        $scope.$broadcast('loadingPopupInactive');

        // 그리드 DataMap 설정
        $scope.prterDataMap = new wijmo.grid.DataMap(prter, 'value', 'name');
        $scope.prterKindDataMap = new wijmo.grid.DataMap(prterKind, 'value', 'name');
        $scope.prterLabelKindDataMap = new wijmo.grid.DataMap(prterLabelKind, 'value', 'name');
        $scope.prterPortDataMap = new wijmo.grid.DataMap(prterPort, 'value', 'name');
        $scope.prterSpeedDataMap = new wijmo.grid.DataMap(prterSpeed, 'value', 'name');
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
        $scope.posDataMap = new wijmo.grid.DataMap($scope.getPosList(), 'posNo', 'posCdNm');

        $scope.getKitchenPrintList();

      }
    });
  };

  /*********************************************************
   * 주방프린터 목록 조회
   * *******************************************************/
  $scope.getKitchenPrintList = function(){

    var params        = {};
    var storeScope    = agrid.getScope('storeManageCtrl');
    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/store/manage/storeManage/storeManage/getKitchenPrintInfo.sb", params, function() {}, false);
  };

  /*********************************************************
   * 주방프린터 추가
   * *******************************************************/
  $scope.addRow = function(){

    // 파라미터 설정
    var params        = {};
    params.gChk       = true;
    params.posNo      ="01";
    params.prterFg = "0";
    params.prterKindFg  ="00";
    params.prterLabelKindFg  ="00";
    params.prterPortFg  = "00";
    params.prterSpeedFg = "0";
    params.prterOutputQty = "1";
    params.prterCheckYn = "N";
    params.bellUseYn    = "N";
    params.useYn        = "Y";
    params.onlinePrintYn = "Y";
    params.dlvrPrintYn = "Y";

    //프린터 코드 값 셋팅을 위한
    var vPrterCd = '';
    if($scope.flex.collectionView.items.length == 0){
      vPrterCd = '01';
    }else{
      for(var i = 0; i < $scope.flex.collectionView.items.length; i++ ){
        var item = $scope.flex.collectionView.items[i];

        if(vPrterCd == ''){
          vPrterCd = item.prterNo;
        }else{
          if(parseInt(item.prterNo) > parseInt(vPrterCd)){
            vPrterCd = item.prterNo;
          }
        }
      }

      if(10 > (parseInt(vPrterCd) + 1)){
        vPrterCd = "0" + String((parseInt(vPrterCd) + 1));
      }else{
        vPrterCd =  String((parseInt(vPrterCd) + 1));
      }
    }

    params.prterNo = vPrterCd;

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

    var params      = [];
    var storeScope  = agrid.getScope('storeManageCtrl');

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      if($scope.flex.collectionView.itemsEdited[i].prterNm === "" || $scope.flex.collectionView.itemsEdited[i].prterNm === null || $scope.flex.collectionView.itemsEdited[i].prterNm === undefined) {
        $scope._popMsg(messages["storeManage.kitchenPrint.prterNmBlank"]); // 이름을 입력해주세요.
        return false;
      } else {
        if ($scope.flex.collectionView.itemsEdited[i].prterNm.length > 16) {
          $scope._popMsg(messages["storeManage.kitchenPrint.prterNmLengthChk"]); // 이름 길이가 너무 깁니다.
          return false;
        }
      }

      // TCP/포트 최대길이 체크
      if (nvl($scope.flex.collectionView.itemsEdited[i].prterNetIp, '') !== '' &&
          nvl($scope.flex.collectionView.itemsEdited[i].prterNetIp + '', '').getByteLengthForOracle() > 15) {
        var msg = messages["storeManage.prterNetIp"] + messages["cmm.overLength"] + " 15 " +
            ", 현재 : " + $scope.flex.collectionView.itemsEdited[i].prterNetIp.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]; //  데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100 현재 : (영문:1yte, 한글:3byte)
        $scope._popMsg(msg);
        return false;
      }

      if($scope.flex.collectionView.itemsEdited[i].prterNetPort !== "" && $scope.flex.collectionView.itemsEdited[i].prterNetPort !== null && $scope.flex.collectionView.itemsEdited[i].prterNetPort !== undefined){
        var numChkexp = /[^0-9]/g;
        if (numChkexp.test($scope.flex.collectionView.itemsEdited[i].prterNetPort)) {
          $scope._popMsg(messages["storeManage.prterNetPort"] + messages["cmm.require.number"]); // TCP/포트는 숫자만 입력할 수 있습니다.
          return false;
        }
      }
      // TCP/포트 최대길이 체크
      if (nvl($scope.flex.collectionView.itemsEdited[i].prterNetPort, '') !== '' &&
          nvl($scope.flex.collectionView.itemsEdited[i].prterNetPort + '', '').length > 5) {
        var msg = messages["storeManage.prterNetPort"] + messages["cmm.overLength"] + " 5 " +
            ", 현재 : " + $scope.flex.collectionView.itemsEdited[i].prterNetPort.length
        $scope._popMsg(msg);
        return false;
      }

      // 제우스매핑코드 최대길이 체크
      if (nvl($scope.flex.collectionView.itemsEdited[i].zeusPrterNo, '') !== '' &&
          nvl($scope.flex.collectionView.itemsEdited[i].zeusPrterNo + '', '').getByteLengthForOracle() > 10) {
        var msg = messages["storeManage.kitchenPrint.zeusPrterNo"] + messages["cmm.overLength"] + " 10 " +
            ", 현재 : " + $scope.flex.collectionView.itemsEdited[i].zeusPrterNo.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
        $scope._popMsg(msg);
        return false;
      }

      $scope.flex.collectionView.itemsEdited[i].storeCd = storeScope.selectedStore.storeCd;
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if($scope.flex.collectionView.itemsAdded[i].prterNm === "" || $scope.flex.collectionView.itemsAdded[i].prterNm === null || $scope.flex.collectionView.itemsAdded[i].prterNm === undefined) {
        $scope._popMsg(messages["storeManage.kitchenPrint.prterNmBlank"]); // 이름을 입력해주세요.
        return false;
      } else {
        if ($scope.flex.collectionView.itemsAdded[i].prterNm.length > 16) {
          $scope._popMsg(messages["storeManage.kitchenPrint.prterNmLengthChk"]); // 이름 길이가 너무 깁니다.
          return false;
        }
      }

      // TCP/IP 최대길이 체크
      if (nvl($scope.flex.collectionView.itemsAdded[i].prterNetIp, '') !== '' &&
          nvl($scope.flex.collectionView.itemsAdded[i].prterNetIp + '', '').getByteLengthForOracle() > 15) {
        var msg = messages["storeManage.prterNetIp"] + messages["cmm.overLength"] + " 15 " +
            ", 현재 : " + $scope.flex.collectionView.itemsAdded[i].prterNetIp.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]; //  데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100 현재 : (영문:1yte, 한글:3byte)
        $scope._popMsg(msg);
        return false;
      }

      if($scope.flex.collectionView.itemsAdded[i].prterNetPort !== "" && $scope.flex.collectionView.itemsAdded[i].prterNetPort !== null && $scope.flex.collectionView.itemsAdded[i].prterNetPort !== undefined){
        var numChkexp = /[^0-9]/g;
        if (numChkexp.test($scope.flex.collectionView.itemsAdded[i].prterNetPort)) {
          $scope._popMsg(messages["storeManage.prterNetPort"] + messages["cmm.require.number"]); // TCP/포트는 숫자만 입력할 수 있습니다.
          return false;
        }
      }

      // TCP/포트 최대길이 체크
      if (nvl($scope.flex.collectionView.itemsAdded[i].prterNetPort, '') !== '' &&
          nvl($scope.flex.collectionView.itemsAdded[i].prterNetPort + '', '').length > 5) {
        var msg = messages["storeManage.prterNetPort"] + messages["cmm.overLength"] + " 5 " +
            ", 현재 : " + $scope.flex.collectionView.itemsAdded[i].prterNetPort.length
        $scope._popMsg(msg);
        return false;
      }

      // 제우스매핑코드 최대길이 체크
      if (nvl($scope.flex.collectionView.itemsAdded[i].zeusPrterNo, '') !== '' &&
          nvl($scope.flex.collectionView.itemsAdded[i].zeusPrterNo + '', '').getByteLengthForOracle() > 10) {
        var msg = messages["storeManage.kitchenPrint.zeusPrterNo"] + messages["cmm.overLength"] + " 10 " +
            ", 현재 : " + $scope.flex.collectionView.itemsAdded[i].zeusPrterNo.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
        $scope._popMsg(msg);
        return false;
      }
      $scope.flex.collectionView.itemsAdded[i].storeCd = storeScope.selectedStore.storeCd;
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].storeCd = storeScope.selectedStore.storeCd;
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/store/manage/storeManage/storeManage/saveKitchenPrintInfo.sb", params,
      function(){
        $scope._popMsg(messages["cmm.saveSucc"]);
      }
    );

  };

}]);

