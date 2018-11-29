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

  $scope.selectedStoreEmp;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드에서 사용하는 dataMap 초기화
    $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFg, 'value', 'name');
    $scope.webUseYnDataMap  = new wijmo.grid.DataMap(webUseYn, 'value', 'name');
    $scope.smsRecvYnDataMap = new wijmo.grid.DataMap(smsRecvYn, 'value', 'name');

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
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly   = $scope.isChecked;
  };

  // _broadcast
  $scope.$on("storeEmpCtrl", function(event, data) {

    $scope.getStoreEmpList();
    event.preventDefault();
  });

  // 본사사원정보관리 그리드 조회
  $scope.getStoreEmpList = function(){
    var params = {};
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
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly = $scope.isChecked;
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

