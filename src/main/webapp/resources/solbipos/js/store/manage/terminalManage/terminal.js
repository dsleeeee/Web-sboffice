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

  // 포스 콥보박스 선택값 (포스 콤보박스 선택시, 해당 포스의 터미널 정보 조회)
  $scope.posFgVal = "01";
  $scope.setPosFgVal = function(s,e){

    if(s.selectedValue === undefined || s.selectedValue === "") {
      return false;
    }

    $scope.posFgVal = s.selectedValue;

    var posScope = agrid.getScope('posCtrl');

    posScope.getPosSetting();
  };
  $scope.getPosFgVal = function(){
    return $scope.posFgVal;
  };

  // 코너 콤보박스 선택값 (코너 콤보박스 선택시, 해당 코너의 터미널 정보 조회)
  $scope.cornerFgVal = "01";
  $scope.setCornerFgVal = function(s,e){

    if(s.selectedValue === undefined || s.selectedValue === "") {
      return false;
    }

    $scope.cornerFgVal = s.selectedValue;

    var cornerScope = agrid.getScope('cornerCtrl');

    cornerScope.getCornerSetting();
  };
  $scope.getCornerFgVal = function(){
    return $scope.cornerFgVal;
  };

  // 콤보박스 생성 및 데이터 초기화
  $scope.comboDt = { posCombo:null, cornerCombo:null }

  $scope.posFgArr = [
    {value:"", name:"POS 선택"}
  ];

  $scope.cornerFgArr = [
    {value:"", name:"코너 선택"}
  ];

  $scope.addComboData = function(){
    $scope.comboDt.posCombo.itemsSource = new wijmo.collections.CollectionView( $scope.posFgArr);
    $scope.comboDt.cornerCombo.itemsSource = new wijmo.collections.CollectionView( $scope.cornerFgArr);
  };

  $scope._setComboData("terminalFg", terminalFg);

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

  // 매장 선택 후, 조회 (환경변수, 포스목록, 코너목록)
  $scope.$on("terminalCtrl", function(event, data) {
    $scope.search();
    event.preventDefault();
  });

  // 매장별 터미널 조회시, 먼저 환경변수 조회 수행
  // 해당 매장의 코너목록과 포스목록도 함께 조회
  $scope.search = function (){

    if($("#storeCd").val() === null || $("#storeCd").val() === "") {
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var params = {};
    params.storeCd = $("#storeCd").val();

    $.ajax({
      type: "POST",
      cache: false,
      async:false,
      dataType: "json",
      url: baseUrl + "terminalManage/getTerminalEnv.sb",
      data: params,
      success: function(result) {

        var terminalEnvVal = result.data.envstVal;
        var posList = result.data.posList;
        var cornerList = result.data.cornerList;

        // console.log(posList);
        // console.log(cornerList);

        $scope.setTerminalEnvVal(terminalEnvVal);
        $scope.terminalFg = terminalEnvVal;

        for(var i in posList){
          $scope.posFgArr.push(posList[i]);
        }
        $scope.comboDt.posCombo.itemsSource = new wijmo.collections.CollectionView( $scope.posFgArr);

        for(var j in cornerList){
          $scope.cornerFgArr.push(cornerList[j]);
        }
        $scope.comboDt.cornerCombo.itemsSource = new wijmo.collections.CollectionView( $scope.cornerFgArr);

        // 코너별 승인
        if($scope.terminalFg  === "2") {
         $scope.showCorner();
        }
        // 포스별 승인
        else if($scope.terminalFg  === "0" || $scope.terminalFg  === "3"){
          $scope.showPos();
        }
      }
    });
  };

  // 코너 보여주기
  $scope.showCorner = function(){
    $("#cornerListArea").show();
    $("#cornerArea").show();
    $("#cornerBtnArea").show();

    $("#posListArea").hide();
    $("#posArea").hide();
    $("#posBtnArea").hide();
  };

  // 포스 보여주기
  $scope.showPos = function(){

    $("#cornerListArea").hide();
    $("#cornerArea").hide();
    $("#cornerBtnArea").hide();

    $("#posListArea").show();
    $("#posArea").show();
    $("#posBtnArea").show();

  };

  // 콤보박스 값 변경 이벤트
  $scope.changeTerminalFg = function(s,e) {

    if($scope.getTerminalEnvVal() ===  undefined){
      return false;
    }

    $scope.setTerminalEnvVal(s.selectedValue);
    var selectedTerminalFgVal = $scope.getTerminalEnvVal();

    // 포스 목록 조회
    if(selectedTerminalFgVal == "0" || selectedTerminalFgVal == "3" ) {
      $scope.showPos();
    }
    // 코너 목록 조회
    else if(selectedTerminalFgVal === "2") {
      $scope.showCorner();
    }
  };

  // 포스 환경변수 복사 //TODO
  $scope.copyEnv = function(){
    $scope._popMsg(messages["terminalManage.no.open"]);
    return false;
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

  // 포스설정 터미널 데이터 조회
  $scope.$on("posCtrl", function(event, data) {
    $scope.getPosSetting();
    event.preventDefault();
  });

  // 포스설정 터미널 데이터 조회
  $scope.getPosSetting = function(){

    if($("#storeCd").val() == null || $("#storeCd").val() == "") {
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var terminalScope = agrid.getScope('terminalCtrl');

    var params = {};
    params.storeCd = $("#storeCd").val();
    params.posNo = terminalScope.getPosFgVal();

    $scope._inquirySub(baseUrl + "pos/getPosTerminalList.sb", params, function() {}, false);
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
    var terminalScope = agrid.getScope('terminalCtrl');

    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      $scope.flex.collectionView.itemsEdited[i].storeCd = $("#storeCd").val();
      $scope.flex.collectionView.itemsEdited[i].posNo = terminalScope.getPosFgVal();
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      $scope.flex.collectionView.itemsAdded[i].storeCd = $("#storeCd").val();
      $scope.flex.collectionView.itemsAdded[i].posNo = terminalScope.getPosFgVal();
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }

    var terminalScope = agrid.getScope('terminalCtrl');

    // 필수값 체크
    for(var i in params) {

      if(params[i].vendorFg == "") {
        $scope._popMsg(messages["terminalManage.vendorFg"] + messages["terminalManage.require.select"]);
        return false;
      }

      if(params[i].vendorCd == "") {
        $scope._popMsg(messages["terminalManage.vendorCd"] + messages["terminalManage.require.select"]);
        return false;
      }

      if(params[i].vendorNm == "") {
        $scope._popMsg(messages["terminalManage.vendorNm"] + messages["terminalManage.require.input"]);
        return false;
      }

      if(params[i].vendorNm.length > 6) {
        $scope._popMsg(messages["terminalManage.vendorNm"] + messages["terminalManage.require.exact.data"]);
        return false;
      }

      if(params[i].vendorTermnlNo == "") {
        $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.input"]);
        return false;
      }

      if(params[i].vendorTermnlNo.length > 20) {
        $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.exact.data"]);
        return false;
      }

      if(params[i].vendorSerNo == "") {
        $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.input"]);
        return false;
      }

      if(params[i].vendorSerNo.length > 20) {
        $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.exact.data"]);
        return false;
      }
    }

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
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var terminalScope = agrid.getScope('terminalCtrl');

    var params = {};
    params.storeCd = $("#storeCd").val();
    params.cornrCd = terminalScope.getCornerFgVal();

    // console.log(params);

    $scope._inquirySub(baseUrl + "corner/getCornerTerminalList.sb", params, function() {}, false);
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

    var terminalScope = agrid.getScope('terminalCtrl');


    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      $scope.flex.collectionView.itemsEdited[i].storeCd = $("#storeCd").val();
      $scope.flex.collectionView.itemsEdited[i].cornrCd = terminalScope.getCornerFgVal();
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      $scope.flex.collectionView.itemsAdded[i].storeCd = $("#storeCd").val();
      $scope.flex.collectionView.itemsAdded[i].cornrCd = terminalScope.getCornerFgVal();
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }

    //필수값 체크
    for(var i in params) {

      if(params[i].vendorFg == "") {
        $scope._popMsg(messages["terminalManage.vendorFg"] + messages["terminalManage.require.select"]);
        return false;
      }

      if(params[i].vendorCd == "") {
        $scope._popMsg(messages["terminalManage.vendorCd"] + messages["terminalManage.require.select"]);
        return false;
      }

      if(params[i].vendorNm == "") {
        $scope._popMsg(messages["terminalManage.vendorNm"] + messages["terminalManage.require.input"]);
        return false;
      }

      if(params[i].vendorNm.length > 6) {
        $scope._popMsg(messages["terminalManage.vendorNm"] + messages["terminalManage.require.exact.data"]);
        return false;
      }

      if(params[i].vendorTermnlNo == "") {
        $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.input"]);
        return false;
      }

      if(params[i].vendorTermnlNo.length > 20) {
        $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.exact.data"]);
        return false;
      }

      if(params[i].vendorSerNo == "") {
        $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.input"]);
        return false;
      }

      if(params[i].vendorSerNo.length > 20) {
        $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.exact.data"]);
        return false;
      }
    }

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
