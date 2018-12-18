/****************************************************************
 *
 * 파일명 : searchTaxBill.js
 * 설  명 : 세금계산서 요청목록 조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.14     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('searchTaxBillCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('searchTaxBillCtrl', $scope, $http, true));

  $scope.taxBill;
  $scope.setTaxBill = function(obj){
    $scope.taxBill = obj;
  };
  $scope.getTaxBill = function(){
    return $scope.taxBill;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "billNo") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "billNo" ) {
          $scope.setTaxBill(selectedRow);
          $scope.searchTaxBillLayer.hide();
        }
      }
    });
  };

  $scope.$on("searchTaxBillCtrl", function(event, data) {
    $scope.searchTaxbill();
    event.preventDefault();
  });

  // 세금계산서 요청목록 조회
  $scope.searchTaxbill = function(){
    var params = {};

    console.log('searchTaxbill');
    $scope._inquiryMain("/membr/anals/postpaid/deposit/getTaxBillList.sb", params, function() {}, false);
  };
}]);


