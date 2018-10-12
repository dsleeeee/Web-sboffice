/****************************************************************
 *
 * 파일명 : terminal.js
 * 설  명 : 터미널 설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.06     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**********************************************************************
 *  터미널 Ctrl
 **********************************************************************/
app.controller('terminalCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('terminalCtrl', $scope, $http, true));

  // 터미널 환경변수 [2028]
  $scope.terminalEnvVal;
  $scope.setTerminalEnvVal = function(s){
    $scope.terminalEnvVal = s;
  };
  $scope.getTerminalEnvVal = function(){
    return $scope.terminalEnvVal;
  };

  // 포스 선택값
  $scope.posFgVal = "01";
  $scope.setPosFgVal = function(s,e){
    $scope.posFgVal = s.selectedValue;
  };
  $scope.getPosFgVal = function(){
    return $scope.posFgVal;
  };

  // 코너 선택값
  $scope.cornerFgVal = "01";
  $scope.setCornerFgVal = function(s,e){
    $scope.cornerFgVal = s.selectedValue;
  };
  $scope.getCornerFgVal = function(){
    return $scope.cornerFgVal;
  };

  // 콤보박스 생성
  $scope._setComboData("terminalFg", terminalFg);
  // $scope._setComboData("posFg", []);
  // $scope._setComboData("cornerFg", []);

  // 매장찾기 팝업 오픈
  $scope.searchStore = function(){
    var popup = $scope.storeLayer;
    popup.show(true, function (s) {
      var storeData = agrid.getScope('storeCtrl');
      storeData._gridDataInit(); //TODO 초기화가 잘 안됨
      var data = storeData.getSelectedStore();

      $("#storeInfo").val("["+data.storeCd+"] "+data.storeNm);
      $("#storeCd").val(data.storeCd);
    });
  };

  // 조회
  $scope.$on("terminalCtrl", function(event, data) {
    $scope.getEnvInfo();
    event.preventDefault();
  });

  // 매장별 터미널 조회시, 먼저 환경변수 조회 수행
  // 해당 매장의 코너목록과 포스목록도 함께 조회
  $scope.getEnvInfo = function (){

    if($("#storeCd").val() === null || $("#storeCd").val() === "") {
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var params = {};
    params.storeCd = $("#storeCd").val();

    $.ajax({
      type: "POST",
      cache: false,
      async:true,
      dataType: "json",
      url: baseUrl + "terminalManage/getTerminalEnv.sb",
      data: params,
      success: function(result) {

        var terminalEnvVal = result.data.envstVal;
        var posList = result.data.posList;
        var cornerList = result.data.cornerList;

        console.log(posList);
        console.log(cornerList);

        $("#terminalFgVal").val(terminalEnvVal);
        // terminalFg.selectedValue = terminalEnvVal;
        $scope.setTerminalEnvVal(terminalEnvVal);

        // 콤보박스 왜 안생겨 TODO
        $scope._setComboData("posFg", posList);
        $scope._setComboData("cornerFg", cornerList);

        $("#posFgVal").val($scope.getPosFgVal());
        $("#cornerFgVal").val($scope.getCornerFgVal());

        // 코너별 승인
        if($scope.getTerminalEnvVal() === "2") {
          var cornerScope = agrid.getScope('cornerCtrl');
          cornerScope.getCornerSetting();
        }
        // 포스별 승인
        else if($scope.getTerminalEnvVal() === "0" || $scope.getTerminalEnvVal() === "3"){
          var posScope = agrid.getScope('posCtrl');
          posScope.getPosSetting();
        }
      }
    });
  };

  // 콤보박스 값 변경 이벤트
  $scope.changeTerminalFg = function(s,e) {

    if($scope.getTerminalEnvVal() ===  undefined){
      return false;
    }

    // 선택한 터미널 콤보값
    $scope.setTerminalEnvVal(s.selectedValue);

    var terminalFgVal = $scope.getTerminalEnvVal();

    // 포스 목록 조회
    if(terminalFgVal == "0" || terminalFgVal == "3" ) {
      var posScope = agrid.getScope('posCtrl');
      posScope.getPosSetting();
    }
    // 코너 목록 조회
    else if(terminalFgVal === "2") {
      var cornerScope = agrid.getScope('cornerCtrl');
      cornerScope.getCornerSetting();
    }
  };

  // 포스 터미널 그리드 행 추가
  $scope.posAddRow = function(){
    var posScope = agrid.getScope('posCtrl');
    posScope.addRow();
  };

  // 포스 터미널 그리드 행 추가
  $scope.posSave = function(){
    var posScope = agrid.getScope('posCtrl');
    posScope.save();
  };

  // 코너 터미널 그리드 행 추가
  $scope.cornerAddRow = function(){
    var cornerScope = agrid.getScope('cornerCtrl');
    cornerScope.addRow();
  };

  // 코너 터미널 그리드 행 추가
  $scope.cornerSave = function(){
    var cornerScope = agrid.getScope('cornerCtrl');
    cornerScope.save();
  };

}]);

// 매장 클릭 (매장찾기)
$("#storeInfo").click(function(){
  var terminalScope = agrid.getScope('terminalCtrl');
  terminalScope.searchStore();
});


/**********************************************************************
 *  POS 설정 그리드
 **********************************************************************/
app.controller('posCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('posCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.vendorFgDataMap = new wijmo.grid.DataMap(
        [{id: "01", name: "VAN"}, {id: "02",name: "PAYCO"}, {id: "03",name: "MPAY"},{id: "04",name: "MCOUPN"}]
        , 'id', 'name');
    $scope.vanCdDataMap = new wijmo.grid.DataMap(vanCdFg, 'value', 'name');
    $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');
  };

  // 조회
  $scope.$on("posCtrl", function(event, data) {
    $scope.getPosSetting();
    event.preventDefault();
  });

  // 포스설정 터미널 데이터 조회
  $scope.getPosSetting = function(){

    if($("#storeCd").val() == null || $("#storeCd").val() == "") {
      // 매장 필수 선택
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var params = {};
    params.storeCd = $("#storeCd").val();
    params.posNo = $("#posFgVal").val();

    $scope._inquirySub(baseUrl + "pos/getPosTerminalList.sb", params, function() {

      $("#cornerListArea").hide();
      $("#cornerArea").hide();
      $("#cornerBtnArea").hide();

      $("#posListArea").show();
      $("#posArea").show();
      $("#posBtnArea").show();

    }, false);
  };

  // 행 추가
  $scope.addRow = function(){

    var params = {};
    params.status = "I";
    params.vendorFg = "01";
    params.vendorCd = "001";
    params.gChk = true;

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 포스 정보 저장
  $scope.save = function(){

    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      $scope.flex.collectionView.itemsEdited[i].storeCd = $("#storeCd").val();
      $scope.flex.collectionView.itemsEdited[i].posNo = $("#posFgVal").val();
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      $scope.flex.collectionView.itemsAdded[i].storeCd = $("#storeCd").val();
      $scope.flex.collectionView.itemsAdded[i].posNo = $("#posFgVal").val();
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }

    var terminalScope = agrid.getScope('terminalCtrl');

    //TODO 필수값 체크

    var chkChanged = false;
    if($("#terminalFgVal").val() !== terminalScope.getTerminalEnvVal()) {
      chkChanged = true;
    }

    if (params.length <= 0  && !chkChanged) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    } else {
      params = JSON.stringify(params);
    }

    var sParam = {};
    sParam['storeCd'] = $("#storeCd").val();
    sParam['terminalFgVal'] = terminalScope.getTerminalEnvVal();

    // ajax 통신 설정
    $http({
      method: 'POST', //방식
      url: baseUrl + "pos/savePosTerminalInfo.sb", /* 통신할 URL */
      data: params, /* 파라메터로 보낼 데이터 */
      params: sParam,
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      $scope._popMsg(messages["cmm.saveSucc"]);
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };
}]);

/***************************************************************************
 * 코너 설정 그리드
 ***************************************************************************/
app.controller('cornerCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cornerCtrl', $scope, $http, true));

  // 원본 [2028] ENVST_VAL
  $scope.terminalCornerEnvVal;
  $scope.setTerminalCornerEnvVal = function(s){
    $scope.terminalCornerEnvVal = s;
  };
  $scope.getTerminalCornerEnvVal = function(){
    return $scope.terminalCornerEnvVal;
  };

  // 콤보박스 생성
  $scope._setComboData("terminalCornerFg", terminalFg);

  // 콤보박스 값 변경 이벤트
  $scope.changeTerminalCornerFg = function(s,e) {

    // 원래 코너별 승인인데 포스별 승인으로 변경하는 경우
    if((s.selectedValue === "0" || s.selectedValue === "3") && $scope.terminalCornerEnvVal === "2") {

      var alertMsg = messages["terminalManage.confirm.change.terminal"];
      alertMsg += "<br>";
      alertMsg += messages["terminalManage.confirm.delete.cornerSetting"];

      $scope._popConfirm(alertMsg,
        function(){
          $scope.changeToPosList();
        }
      );
    }
  };

  // 포스리스트로 변경
  // $scope.changeToPosList = function(){
  //   var posGrid = agrid.getScope('posCtrl');
  //   posGrid.getPosSetting();
  // };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.vendorFgDataMap = new wijmo.grid.DataMap(
        [{id: "01", name: "VAN"}, {id: "02",name: "PAYCO"}, {id: "03",name: "MPAY"},{id: "04",name: "MCOUPN"}]
        , 'id', 'name');
    $scope.vanCdDataMap = new wijmo.grid.DataMap(vanCdFg, 'value', 'name');
    $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');
  };

  // 조회
  $scope.$on("cornerCtrl", function(event, data) {
    $scope.getCornerSetting();
    event.preventDefault();
  });

  // 코너설정 데이터 조회
  $scope.getCornerSetting = function(){

    if($("#storeCd").val() == null || $("#storeCd").val() == "") {
      // 매장 필수 선택
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var terminalScope = agrid.getScope('terminalCtrl');

    var params = {};
    params.storeCd = $("#storeCd").val();
    params.cornrCd = terminalScope.getCornerFgVal();

    console.log(params);

    $scope._inquirySub(baseUrl + "corner/getCornerTerminalList.sb", params, function() {

      $("#posListArea").hide();
      $("#posArea").hide();
      $("#posBtnArea").hide();

      $("#cornerListArea").show();
      $("#cornerArea").show();
      $("#cornerBtnArea").show();

    }, false);
  };

  // 행 추가
  $scope.addRow = function(){

    var params = {};
    params.status = "I";
    params.vendorFg = "01";
    params.vendorCd = "001";
    params.gChk = true;

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 코너 정보 저장
  $scope.save = function(){
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      $scope.flex.collectionView.itemsEdited[i].storeCd = $("#storeCd").val();
      $scope.flex.collectionView.itemsEdited[i].cornrCd = $("#cornerFgVal").val();
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      $scope.flex.collectionView.itemsAdded[i].storeCd = $("#storeCd").val();
      $scope.flex.collectionView.itemsAdded[i].cornrCd = $("#cornerFgVal").val();
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }

    //TODO 필수값 체크
    console.log(params);

    var terminalScope = agrid.getScope('terminalCtrl');

    var chkChanged = false;
    if($("#terminalFgVal").val() !== terminalScope.getTerminalEnvVal()) {
      chkChanged = true;
    }

    if (params.length <= 0  && !chkChanged) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    } else {
      params = JSON.stringify(params);
    }

    // 코너사용여부 환경변수
    var sParam = {};
    sParam['storeCd'] = $("#storeCd").val();
    sParam['terminalFgVal'] = terminalScope.getTerminalEnvVal();

    console.log('sParam');
    console.log(sParam);

    // ajax 통신 설정
    $http({
      method: 'POST', //방식
      url: baseUrl + "corner/saveCornerTerminalInfo.sb", /* 통신할 URL */
      data: params, /* 파라메터로 보낼 데이터 */
      params: sParam,
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      $scope._popMsg(messages["cmm.saveSucc"]);
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };
}]);
