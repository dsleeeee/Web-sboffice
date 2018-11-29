/****************************************************************
 *
 * 파일명 : systemEmp.js
 * 설  명 : 사원정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.26     김지은      1.0            최초작성
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 사원 정보관리
 */
app.controller('systemEmpCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('systemEmpCtrl', $scope, $http, false));

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
  // 관리자여부
  $scope._getComboDataQuery('097', 'adminFgComboData', 'A');

  $scope.selectedSystemEmp;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드에서 사용하는 dataMap 초기화
    $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFg, 'value', 'name');
    $scope.webUseYnDataMap  = new wijmo.grid.DataMap(webUseYn, 'value', 'name');
    $scope.smsRecvYnDataMap = new wijmo.grid.DataMap(smsRecvYn, 'value', 'name');
    $scope.adminFgDataMap = new wijmo.grid.DataMap(adminFgData, 'value', 'name');

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
          $scope.systemEmpDetailLayer.show(true);
        }
      }
    });
    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly   = $scope.isChecked;
  };

  // _broadcast
  $scope.$on("systemEmpCtrl", function(event, data) {
    $scope.getSystemEmpList();
    event.preventDefault();
  });

  // 본사사원정보관리 그리드 조회
  $scope.getSystemEmpList = function(){
    var params = {};
    $scope._inquiryMain("/base/store/emp/system/list.sb", params, function() {});
  };

  // 신규등록 버튼 클릭
  $scope.registSystemEmp = function(){
    $scope.selectedSystemEmp = {};
    $scope.systemEmpRegistLayer.show(true, function(s){
      $scope.getSystemEmpList();
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
    $scope.systemEmpDetailLayer.shown.addHandler(function (s) {
      $scope.selectedSystemEmp = $scope.flex.selectedRows[0]._data;
      setTimeout(function() {
        var params = $scope.selectedSystemEmp;
        $scope._broadcast('systemEmpDetailCtrl', params);
      }, 50);
    });
    // 사원 등록 팝업 핸들러 추가
    $scope.systemEmpRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        var params = $scope.selectedSystemEmp;
        $scope._broadcast('systemEmpRegistCtrl', params);
      }, 50);
    });
  });

}]);

