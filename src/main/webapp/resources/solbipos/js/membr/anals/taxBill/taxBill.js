/****************************************************************
 *
 * 파일명 : taxBill.js
 * 설  명 : 세금계산서 요청 목록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.13     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  세금계산서 요청목록 그리드 생성
 */
app.controller('taxBillCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('taxBillCtrl', $scope, $http, true));

  // comboBox 초기화
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("srchStatusFgCombo", statusFg);

  // 전체기간 체크박스
  $scope.isChecked = true;

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly = $scope.isChecked;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.statusFgDataMap = new wijmo.grid.DataMap(statusFg, 'value', 'name');
  };

  $scope.$on("taxBillCtrl", function(event, data) {
    $scope.searchTaxBillList();
    event.preventDefault();
  });

  // 후불회원 그리드 조회
  $scope.searchTaxBillList = function(){
    var params      = {};
    $scope._inquiryMain("/membr/anals/taxBill/taxBill/getTaxBillRequestList.sb", params, function() {}, false);
  };
}]);
