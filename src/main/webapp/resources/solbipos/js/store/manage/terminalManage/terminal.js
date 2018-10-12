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
 *  POS 설정 그리드
 **********************************************************************/
app.controller('posCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('posCtrl', $scope, $http, true));

  // 원본 [2028] ENVST_VAL
  $scope.terminalPosEnvVal;
  $scope.setTerminalPosEnvVal = function(s){
    $scope.terminalPosEnvVal = s;
  };
  $scope.getTerminalPosEnvVal = function(){
    return $scope.terminalPosEnvVal;
  };

  // 콤보박스 생성
  $scope._setComboData("terminalPosFg", terminalFg);

  // 콤보박스 값 변경 이벤트
  $scope.changeTerminalPosFg = function(s,e) {

    console.log("originalVal : " + $scope.terminalPosEnvVal);
    console.log("selectedVal : "+ s.selectedValue);

    // 원래 포스별 승인인데 코너별 승인으로 변경하는 경우
    if(s.selectedValue === "2" && ($scope.terminalPosEnvVal === "0" ||  $scope.terminalPosEnvVal === "3")) {

      var alertMsg = messages["terminalManage.confirm.change.terminal"];
          alertMsg += "<br>";
          alertMsg += messages["terminalManage.confirm.delete.posSetting"];

      $scope._popConfirm(alertMsg,
        function() {
          $scope.changeToCornerList();
        }
      );
    }
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.vanCdDataMap = new wijmo.grid.DataMap(vanCdFg, 'value', 'name');
    $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');
  };

  // 매장찾기 팝업 오픈
  $scope.searchStore = function(){
    var popup = $scope.storeLayer;
    popup.show(true, function (s) {
      var storeData = agrid.getScope('storeCtrl');
      storeData._gridDataInit();
      var data = storeData.getSelectedStore();

      $("#storeInfo").val("["+data.storeCd+"] "+data.storeNm);
      $("#storeCd").val(data.storeCd);
    });
  };

  // 조회
  $scope.$on("posCtrl", function(event, data) {
    $scope.getPosSetting();
    event.preventDefault();
  });

  // 포스설정 데이터 조회
  $scope.getPosSetting = function(){
    if($("#storeCd").val() == null || $("#storeCd").val() == "") {
      // 매장 필수 선택
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var params = {};
    params.storeCd = $("#storeCd").val();

    // 환경변수 조회
    $.ajax({
      type: "POST",
      cache: false,
      async:true,
      dataType: "json",
      url: baseUrl + "terminalManage/getTerminalEnv.sb",
      data: params,
      success: function(result) {

        console.log("result >>>> ")
        console.log(result);

        // 포스별 설정 //TODO
        $("#terminalPosFgVal").val(result.data);
        // terminalPosFg.selectedValue = result.data;//TODO 변경후 재조회시에 보이는 값이 안변하네
        terminalFg.selectedValue = result.data;
        $scope.setTerminalPosEnvVal(result.data); // 기존에 저장된 환경변수값

        console.log(terminalFg);
        console.log(terminalFg.selectedValue);

        // 코너별 설정
        var cornerGrid = agrid.getScope('cornerCtrl');
        cornerGrid.setTerminalCornerEnvVal(result.data);
        $("#terminalCornerFgVal").val(result.data);

        // 코너, 포스별 목록 조회
        if(terminalFg.selectedValue === "2"){  // 코너별 승인

          $scope.changeToCornerList();

        } else if(terminalFg.selectedValue === "0" || terminalFg.selectedValue === "3"){ // 포스별 승인

          $scope._inquirySub(baseUrl + "pos/getPosList.sb", params, function() {

            $("#cornerArea").hide();
            $("#cornerBtnArea").hide();

            $("#posArea").show();
            $("#posBtnArea").show();

          }, false);
        }

      }
    });

  };

  // 코너 목록으로 전환, 조회
  $scope.changeToCornerList = function(){
    var cornerGrid = agrid.getScope('cornerCtrl');
    cornerGrid.getCornerSetting();
  };

  // 행 추가
  $scope.addRow = function(){

    var params = {};
    params.status = "I";
    params.posNo = "자동채번";
    params.vanCd = "001";
    params.vanCertYn = "N";
    params.useYn = "Y";
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
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      $scope.flex.collectionView.itemsAdded[i].storeCd = $("#storeCd").val();
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }

    //TODO 필수값 체크
    console.log(params);
    console.log(" $scope.getTerminalPosEnvVal() : "+  $scope.getTerminalPosEnvVal());

    var chkChanged = false;
    if($("#terminalPosFgVal").val() !== $scope.getTerminalPosEnvVal()) {
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
    sParam['terminalFgVal'] = $("#terminalPosFgVal").val();

    // ajax 통신 설정
    $http({
      method: 'POST', //방식
      url: baseUrl + "pos/savePosInfo.sb", /* 통신할 URL */
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

// 조회 버튼 클릭
$("#storeInfo").click(function(){
  var posGrid = agrid.getScope('posCtrl');
  posGrid.searchStore();
});


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
  $scope.changeToPosList = function(){
    var posGrid = agrid.getScope('posCtrl');
    posGrid.getPosSetting();
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
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

    terminalFg.selectedValue = "2";

    console.log(terminalFg.selectedValue);

    if($("#storeCd").val() == null || $("#storeCd").val() == "") {
      // 매장 필수 선택
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var params = {};
    params.storeCd = $("#storeCd").val();

    $scope._inquirySub(baseUrl + "corner/getCornerList.sb", params, function() {

      $("#posArea").hide();
      $("#posBtnArea").hide();

      $("#cornerArea").show();
      $("#cornerBtnArea").show();

    }, false);
  };

  // 행 추가
  $scope.addRow = function(){

    var params = {};
    params.status = "I";
    params.cornrCd = "자동채번";
    params.vanCd = "001";
    params.useYn = "Y";
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
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      $scope.flex.collectionView.itemsAdded[i].storeCd = $("#storeCd").val();
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }

    //TODO 필수값 체크
    console.log(params);
    console.log(" $scope.getTerminalCornerEnvVal() : "+  $scope.getTerminalCornerEnvVal());

    var chkChanged = false;
    if($("#terminalCornerFgVal").val() !== $scope.getTerminalCornerEnvVal()) {
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
    sParam['terminalFgVal'] = $("#terminalCornerFgVal").val();

    console.log('sParam');
    console.log(sParam);

    // ajax 통신 설정
    $http({
      method: 'POST', //방식
      url: baseUrl + "corner/saveCornerInfo.sb", /* 통신할 URL */
      data: params, /* 파라메터로 보낼 데이터 */
      // params: sParam,
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
