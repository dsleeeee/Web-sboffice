/****************************************************************
 *
 * 파일명 : brandTerminal.js
 * 설  명 : 브랜드별 매장터미널관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.06     권지현      1.0
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
var paperVoucherList  = new Array();
var taxRefundList  = new Array();

for(var i in vandorList) {
  if(vandorList[i].vanFg === '01'){
    vanList.push(vandorList[i].name);
  } else if(vandorList[i].vanFg === '02'){
    paycoList.push(vandorList[i].name);
  } else if(vandorList[i].vanFg === '03') {
    mpayList.push(vandorList[i].name);
  } else if(vandorList[i].vanFg === '04'){
    mcoupnList.push(vandorList[i].name);
  } else if(vandorList[i].vanFg === '06'){
    paperVoucherList.push(vandorList[i].name);
  } else if(vandorList[i].vanFg === '07'){
    taxRefundList.push(vandorList[i].name);
  }
}
/**********************************************************************
 *  터미널 Ctrl
 **********************************************************************/
app.controller('brandTerminalCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('brandTerminalCtrl', $scope, $http, false));

  // 콤보박스 데이터 Set
  $scope._setComboData("useYnAllComboData", useYn);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name'); // 사용여부

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "hqBrandCd") {
          // var item = s.rows[e.row].dataItem;
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];

        // 브랜드코드 클릭시 상세정보 조회
        if ( col.binding === "hqBrandCd") {
          var selectedRow = s.rows[ht.row].dataItem;
          var params      = {};

          $("#lblBrandInfo").text("[" + selectedRow.hqBrandCd + "] " + selectedRow.hqBrandNm);
          $("#lblHqBrandCd").text(selectedRow.hqBrandCd);
          $("#lblHqBrandNm").text(selectedRow.hqBrandNm);

          var posScope = agrid.getScope('terminalCtrl');
          posScope.search();
        }
      }
    });
  };

  // <-- 검색 호출 -->
  $scope.searchBrandTerminal = function(){
    var params = {};

    console.log(params);

    $scope._inquiryMain("/store/hq/brandTerminal/brandTerminal/getBrandList.sb", params, function() {
      $("#lblBrandInfo").text("");
      $("#lblHqBrandCd").text("");
      $("#lblHqBrandNm").text("");
      $("#btnArea").hide();

      $scope.$apply(function() {
        // 그리드 초기화
        var posScope = agrid.getScope('terminalCtrl');
        posScope._gridDataInit();
      });
    }, false);
  };
  // <-- //검색 호출 -->

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

    var posScope = agrid.getScope('terminalCtrl');
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

  $scope._setComboData("terminalFg", terminalFg);

  // 매장 선택 후, 조회 (환경변수, 포스목록, 코너목록)
  $scope.$on("brandTerminalCtrl", function(event, data) {
    $scope.searchBrandTerminal();
    event.preventDefault();
  });

  // 그리드 행 추가
  $scope.posAddRow = function(){
    var posScope = agrid.getScope('terminalCtrl');
    posScope.addRow();
  };

  // 포스 저장
  $scope.posSave = function(){
    var posScope = agrid.getScope('terminalCtrl');
    posScope.save();
  };

  // 포스 삭제
  $scope.posDel = function(){
    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 포스 저장
      var posScope = agrid.getScope('terminalCtrl');
      posScope.savePosDel();
    });
  };

}]);

/**********************************************************************
 *  터미널 설정 그리드
 **********************************************************************/
app.controller('terminalCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('terminalCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.vendorFgDataMap = new wijmo.grid.DataMap(vendorFg, 'value', 'name');
    // $scope.vanCdDataMap = new wijmo.grid.DataMap(vandorList, 'value', 'name');
    $scope.vanCdDataMap = allVanList;
    $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "vendorFg" || col.binding === "vendorCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용.
            e.cell.outerHTML = e.cell.outerHTML;
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

  // 매장별 터미널 조회시, 먼저 환경변수 조회 수행
  // 해당 매장의 코너목록과 포스목록도 함께 조회
  $scope.search = function (){

    var params = {};
    params.hqBrandCd = $("#lblHqBrandCd").text();

    $scope._inquiryMain("/store/hq/brandTerminal/brandTerminal/getTerminalList.sb", params, function (){
      $("#btnArea").show();
    });
  };

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
          case '06':
            col.dataMap = paperVoucherList;
            break;
          case '07':
            col.dataMap = taxRefundList;
            break;
        }
      }
    }
  };

  // 포스설정 터미널 데이터 조회
  $scope.$on("terminalCtrl", function(event, data) {
    $scope.getPosSetting();
    event.preventDefault();
  });

  // 포스설정 터미널 데이터 조회
  $scope.getPosSetting = function(){

    if(isNull($("#lblHqBrandCd").text())) {
      $scope._popMsg(messages["brandTerminal.request.select.store"]);
      return false;
    }

    var terminalScope = agrid.getScope('brandTerminalCtrl');

    var params = {};
    params.storeCd = $("#lblHqBrandCd").text();
    params.posNo = terminalScope.getPosFgVal();

    $scope._inquirySub("/store/hq/brandTerminal/brandTerminal/getTerminalList.sb", params, function() {
      $scope.flex.collectionView.commitEdit();
    }, false);
  };

  // 행 추가
  $scope.addRow = function(){

    var params = {};
    params.status = "I";
    params.hqBrandCd = $("#lblHqBrandCd").text();
    params.vendorFg = "04";
    params.gChk = true;

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 정보 저장
  $scope.save = function(){
    var terminalScope = agrid.getScope('brandTerminalCtrl');

    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 필수값 체크
    for(var i=0; i<params.length; i++) {

      if(params[i].vendorFg == "") {
        $scope._popMsg(messages["brandTerminal.vendorFg"] + messages["cmm.require.select"]);
        return false;
      }

      if(params[i].vendorCd == "") {
        $scope._popMsg(messages["brandTerminal.vendorCd"] + messages["cmm.require.select"]);
        return false;
      }

      if(params[i].vendorNm == "" || params[i].vendorNm == undefined){
        $scope._popMsg(messages["brandTerminal.vendorCd"] + messages["cmm.require.select"]);
        return false;
      }

      if(params[i].vendorTermnlNo == "" || params[i].vendorTermnlNo == undefined) {
        $scope._popMsg(messages["brandTerminal.vendorTermnlNo"] + messages["terminalManage.require.input"]);
        return false;
      } else {
        if(params[i].vendorFg === "01" && params[i].vendorNm === "KCP"){
          if(params[i].vendorTermnlNo.length != 10) {
            $scope._popMsg(messages["brandTerminal.vendorTermnlNo"] +"는 10자리로 입력하세요.");
            return false;
          }
        }else {
          if (params[i].vendorTermnlNo.length > 50) {
            $scope._popMsg(messages["brandTerminal.vendorTermnlNo"] + messages["terminalManage.require.exact.data"]);
            return false;
          }
        }
      }

      if(params[i].vendorSerNo == "" || params[i].vendorSerNo == undefined) {
        $scope._popMsg(messages["brandTerminal.vendorSerNo"] + messages["terminalManage.require.input"]);
        return false;
      } else {
        if(params[i].vendorSerNo.length > 20) {
          $scope._popMsg(messages["brandTerminal.vendorSerNo"] + messages["terminalManage.require.exact.data"]);
          return false;
        }
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
    $scope._save("/store/hq/brandTerminal/brandTerminal/saveTerminalInfo.sb", params, function(){
      $scope.search();
    });

  };

  // 포스 정보 삭제
  $scope.savePosDel = function(){
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];

      if(item.gChk) {
        $scope.flex.collectionView.removeAt(i);
      }
    }

    // 포스 정보 저장
    $scope.save();
  };

}]);
