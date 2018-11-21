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

// vandorList 에서 name 만을 dataMap으로 사용. (name과 value 동시 사용시 오류) // todo 추후 수정 필요
var allVanList  = new Array();
var vanList     = new Array();
var paycoList   = new Array();
var mpayList    = new Array();
var mcoupnList  = new Array();

for(var i in vandorList) {
  if(vandorList[i].vanFg === '01'){
    vanList.push(vandorList[i].name);
  } else if(vandorList[i].vanFg === '02'){
    paycoList.push(vandorList[i].name);
  } else if(vandorList[i].vanFg === '03') {
    mpayList.push(vandorList[i].name);
  } else if(vandorList[i].vanFg === '04'){
    mcoupnList.push(vandorList[i].name);
  }
}

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
    if(isNull(s.selectedValue)) {
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

    if(isNull(s.selectedValue)){
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

  $scope.posFgArr = [];
  $scope.cornerFgArr = [];

  $scope.resetCombobox = function(){

    $scope.posFgArr = [];
    $scope.cornerFgArr = [];

    $scope.posFgArr.push({value:"", name:"POS 선택"});
    $scope.cornerFgArr.push({value:"", name:"코너 선택"})
  };

  $scope._setComboData("terminalFg", terminalFg);

  // 매장찾기 팝업 오픈
  $scope.searchStore = function(){
    var popup = $scope.storeLayer;
    popup.show(true, function (s) {
      var storeData = agrid.getScope('storeCtrl');
      storeData.$apply(function(){
        storeData._gridDataInit();
      });
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

    if(isNull($("#storeCd").val())) {
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var params = {};
    params.storeCd = $("#storeCd").val();

    $scope._postJSONQuery.withOutPopUp( baseUrl + "terminalManage/getTerminalEnv.sb", params, function(result){

      var terminalEnvVal = result.data.data.envstVal;
      var posList = result.data.data.posList;
      var cornerList = result.data.data.cornerList;

      $scope.setTerminalEnvVal(terminalEnvVal);
      $scope.terminalFg = terminalEnvVal;

      // 다시 초기화해주고
      $scope.resetCombobox();

      // 조회한 데이터 붙이기
      for(var i=0; i<=posList.length; i++){
        $scope.posFgArr.push(posList[i]);
      }
      $scope.comboDt.posCombo.itemsSource = new wijmo.collections.CollectionView( $scope.posFgArr);

      for(var j=0; j<=cornerList.length; j++){
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

      $scope._popMsg(messages["terminalManage.require.select.pos"]);

      // 그리드 초기화
      var posScope = agrid.getScope('posCtrl');
      posScope._gridDataInit();

      var cornerScope = agrid.getScope('cornerCtrl');
      cornerScope._gridDataInit();

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

    // 리스트 초기화
    var posScope = agrid.getScope('posCtrl');
    posScope._gridDataInit();

    var cornerScope = agrid.getScope('cornerCtrl');
    cornerScope._gridDataInit();

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

    if($scope.comboDt.posCombo.selectedValue === ''){
      $scope._popMsg("POS를 선택해주세요.");
      return false;
    }

    var posScope = agrid.getScope('posCtrl');
    posScope.addRow();
  };

  // 포스 저장
  $scope.posSave = function(){
    var posScope = agrid.getScope('posCtrl');
    posScope.save();
  };

  // 코너 터미널 그리드 행 추가
  $scope.cornerAddRow = function(){

    if($scope.comboDt.cornerCombo.selectedValue === ''){
      $scope._popMsg("코너를 선택해주세요.");
      return false;
    }

    var cornerScope = agrid.getScope('cornerCtrl');
    cornerScope.addRow();
  };

  // 코너 저장
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
    $scope.vendorFgDataMap = new wijmo.grid.DataMap(vendorFg, 'value', 'name');
    // $scope.vanCdDataMap = new wijmo.grid.DataMap(vandorList, 'value', 'name');
    $scope.vanCdDataMap = allVanList;
    $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "vendorFg" || col.binding === "vendorCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });

    // 벤더구분, 벤더코드 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "vendorFg" || col.binding === "vendorCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          // elements.cancel = true;
        }
      }
    });

  };

  // // 벤더구분 변경시 벤더 dataMap 변경
  // $scope.changeVendorFg = function(s, e){
  //   if (e.panel === s.cells) {
  //     var col = s.columns[e.col];
  //     if (col.binding === "vendorFg") {
  //       var changeVendorFg = s.rows[e.row].dataItem.vendorFg;
  //       $scope.vanCdDataMap.collectionView.filter = function(item) {
  //         return item.vanFg == changeVendorFg;
  //       };
  //       s.rows[e.row].dataItem.vendorCd = ""; // 벤더구분 변경시에는 값 벤더코드 변경하도록 ""으로!
  //     }
  //   }
  // };

  // 벤더구분 변경시 벤더 dataMap 변경
  $scope.changeVendorFg = function(s, e){
    if (e.panel === s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "vendorNm") {
        var changeVendorFg = s.rows[e.row].dataItem.vendorFg;
        switch (changeVendorFg) {
          case '01':
            col.dataMap = vanList;
            break;
          case '02':
            col.dataMap = paycoList;
            break;
          case '03':
            col.dataMap = mpayList;
            break;
          case '04':
            col.dataMap = mcoupnList;
            break;
        }
      }
    }
  };

  // 포스설정 터미널 데이터 조회
  $scope.$on("posCtrl", function(event, data) {
    $scope.getPosSetting();
    event.preventDefault();
  });

  // 포스설정 터미널 데이터 조회
  $scope.getPosSetting = function(){

    if(isNull($("#storeCd").val())) {
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var terminalScope = agrid.getScope('terminalCtrl');

    var params = {};
    params.storeCd = $("#storeCd").val();
    params.posNo = terminalScope.getPosFgVal();

    $scope._inquirySub(baseUrl + "pos/getPosTerminalList.sb", params, function() {
      // var rows = $scope.flex.rows;
      // if(rows.length > 0) {
      //   for (var i = 0; i < rows.length; i++) {
      //     // $scope.flex.setCellData(i,3, rows[i]._data.vendorFg);
      //     // $scope.flex.setCellData(i,3, "2");
      //     // console.log($scope.flex.getCellData(i,3));
      //     // console.log($scope.flex.getCellData(i,4));
      //
      //     // var changeVendorFg = rows[i]._data.vendorFg;
      //     // $scope.vanCdDataMap.collectionView.filter = function(item) {
      //     //   return item.vanFg == changeVendorFg;
      //     // };
      //   }
      // }
      $scope.flex.collectionView.commitEdit();
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

    // 필수값 체크
    for(var i=0; i<params.length; i++) {

      if(params[i].vendorFg == "") {
        $scope._popMsg(messages["terminalManage.vendorFg"] + messages["terminalManage.require.select"]);
        return false;
      }

      if(params[i].vendorCd == "") {
        $scope._popMsg(messages["terminalManage.vendorCd"] + messages["terminalManage.require.select"]);
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

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "pos/savePosTerminalInfo.sb", params, function(){
      // $scope.allSearch();
    });

    // // ajax 통신 설정
    // $http({
    //   method: 'POST', //방식
    //   url: baseUrl + "pos/savePosTerminalInfo.sb", /* 통신할 URL */
    //   data: params, /* 파라메터로 보낼 데이터 */
    //   headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    // }).then(function successCallback(response) {
    //   $scope._popMsg(messages["cmm.saveSucc"]);
    // }, function errorCallback(response) {
    //   // called asynchronously if an error occurs
    //   // or server returns response with an error status.
    //   $scope._popMsg(messages["cmm.saveFail"]);
    //   return false;
    // }).then(function () {
    //   // "complete" code here
    // });
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
    $scope.vendorFgDataMap = new wijmo.grid.DataMap(vendorFg, 'value', 'name');
    // $scope.vanCdDataMap = new wijmo.grid.DataMap(vandorList, 'value', 'name');
    $scope.vanCdDataMap = allVanList;
    $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');


    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "vendorFg" || col.binding === "vendorCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });

    // 벤더구분, 벤더코드 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "vendorFg" || col.binding === "vendorCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
    });
  };

  // // 벤더구분 변경시 벤더 dataMap 변경
  // $scope.changeVendorFg = function(s, e){
  //   if (e.panel === s.cells) {
  //     var col = s.columns[e.col];
  //     if (col.binding === "vendorFg") {
  //       var changeVendorFg = s.rows[e.row].dataItem.vendorFg;
  //       $scope.vanCdDataMap.collectionView.filter = function(item) {
  //         return item.vanFg == changeVendorFg;
  //       };
  //       s.rows[e.row].dataItem.vendorCd = "";
  //     }
  //   }
  // };

  // 벤더구분 변경시 벤더 dataMap 변경
  $scope.changeVendorFg = function(s, e){
    if (e.panel === s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "vendorNm") {
        var changeVendorFg = s.rows[e.row].dataItem.vendorFg;
        // console.log("changeVendorFg : "+ changeVendorFg)
        switch (changeVendorFg) {
          case '01':
            col.dataMap = vanList;
            break;
          case '02':
            col.dataMap = paycoList;
            break;
          case '03':
            col.dataMap = mpayList;
            break;
          case '04':
            col.dataMap = mcoupnList;
            break;
        }
      }
    }
  };

  // 조회
  $scope.$on("cornerCtrl", function(event, data) {
    $scope.getCornerSetting();
    event.preventDefault();
  });

  // 코너설정 데이터 조회
  $scope.getCornerSetting = function(){

    if(isNull($("#storeCd").val())){
      $scope._popMsg(messages["terminalManage.request.select.store"]);
      return false;
    }

    var terminalScope = agrid.getScope('terminalCtrl');

    var params = {};
    params.storeCd = $("#storeCd").val();
    params.cornrCd = terminalScope.getCornerFgVal();

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
    for(var i=0; i<params.length; i++) {

      if(params[i].vendorFg == "") {
        $scope._popMsg(messages["terminalManage.vendorFg"] + messages["terminalManage.require.select"]);
        return false;
      }

      if(params[i].vendorCd == "") {
        $scope._popMsg(messages["terminalManage.vendorCd"] + messages["terminalManage.require.select"]);
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


    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "corner/saveCornerTerminalInfo.sb", params, function(){
      // $scope.allSearch();
    });

    // // ajax 통신 설정
    // $http({
    //   method: 'POST', //방식
    //   url: baseUrl + "corner/saveCornerTerminalInfo.sb", /* 통신할 URL */
    //   data: params, /* 파라메터로 보낼 데이터 */
    //   headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    // }).then(function successCallback(response) {
    //   $scope._popMsg(messages["cmm.saveSucc"]);
    // }, function errorCallback(response) {
    //   // called asynchronously if an error occurs
    //   // or server returns response with an error status.
    //   $scope._popMsg(messages["cmm.saveFail"]);
    //   return false;
    // }).then(function () {
    //   // "complete" code here
    // });
  };
}]);
