/****************************************************************
 *
 * 파일명 : storeEmp.js
 * 설  명 : 매장사원정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.29     이한빈      1.0
 * 2018.11.23     김지은      2.0            angular로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var mainSaleFg = [
  {"name": "전체", "value": ""},
  {"name": "사용", "value": "0"},
  {"name": "미사용", "value": "1"}
];

var useYn = [
  {"name":"전체","value":""},
  {"name":"사용","value":"Y"},
  {"name":"미사용","value":"N"}
];
/**
 * 매장 사원 정보관리
 */
app.controller('storeEmpCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeEmpCtrl', $scope, $http, false));

  // 전체기간 체크박스
  $scope.isChecked = true;
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  // 재직여부
  $scope._getComboDataQuery('007', 'serviceFgComboData', 'A');
  // 웹사용여부
  $scope._getComboDataQuery('067', 'webUseYnComboData', 'A');
  // SMS 수신여부
  $scope._getComboDataQuery('072', 'smsRecvYnComboData', 'A');
  // 사용여부
  $scope._getComboDataQuery('067', 'useYnComboData', 'A');
  // 메인화면매출표시
  $scope._setComboData("srchMainSaleFg", mainSaleFg);

  $scope.selectedStoreEmp;

  // 등록일자 셋팅
  $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드에서 사용하는 dataMap 초기화
    $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFg, 'value', 'name');
    $scope.webUseYnDataMap  = new wijmo.grid.DataMap(webUseYn, 'value', 'name');
    // $scope.smsRecvYnDataMap = new wijmo.grid.DataMap(smsRecvYn, 'value', 'name');
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.mainSaleFgDataMap = new wijmo.grid.DataMap(mainSaleFg, 'value', 'name');

    // console.log('smsRecvYn', smsRecvYn);
    // console.log('$scope.smsRecvYnDataMap', $scope.smsRecvYnDataMap);


    // 그리드 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if( col.binding === "empNo" || col.binding === "empNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if( col.binding === "empNo" || col.binding === "empNm") {
          // 상세정보 팝업
          $scope.storeEmpDetailLayer.show(true);
        }
      }
    });
    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

  // _broadcast
  $scope.$on("storeEmpCtrl", function(event, data) {

    $scope.getStoreEmpList();
    event.preventDefault();
  });

  // 본사사원정보관리 그리드 조회
  $scope.getStoreEmpList = function(){
    var params = {};

    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }

    $scope._inquiryMain("/base/store/emp/store/list.sb", params, function() {});
  };

  // 신규등록 버튼 클릭
  $scope.registStoreEmp = function(){
    $scope.selectedStoreEmp = {};
    $scope.storeEmpRegistLayer.show(true, function(s){
      $scope.getStoreEmpList();
    });
  };

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 사원상세정보 팝업 핸들러 추가
    $scope.storeEmpDetailLayer.shown.addHandler(function (s) {
      $scope.selectedStoreEmp = $scope.flex.selectedRows[0]._data;
      setTimeout(function() {
        var params = $scope.selectedStoreEmp;
        $scope._broadcast('storeEmpDetailCtrl', params);
      }, 50);
    });
    // 사원 등록 팝업 핸들러 추가
    $scope.storeEmpRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        var params = $scope.selectedStoreEmp;
        $scope._broadcast('storeEmpRegistCtrl', params);
      }, 50);
    });
  });

}]);

