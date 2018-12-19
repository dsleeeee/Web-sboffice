/****************************************************************
 *
 * 파일명 : excpForward.js
 * 설  명 : 예외출고 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.14     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  예외출고 상품목록 그리드 생성
 */
app.controller('productCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('productCtrl', $scope, $http, true));

  // 선택 상품
  $scope.prod;
  $scope.setProd = function(item){
    $scope.prod = item;
  };
  $scope.getProd = function(){
    return $scope.prod;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "regist") {
          if((item.inQty - item.totSaleQty) > 0){
            wijmo.addClass(e.cell, 'wijLink');
          }
        }
      }
    });

    // 입고 상품목록 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var item = s.rows[ht.row].dataItem;
        $scope.setProd(s.rows[ht.row].dataItem);
        if ( col.binding === "regist" && item.stockQty > 0) {

          var popup = $scope.excpForwardRegistLayer;
          popup.show(true, function (s) {
            $scope.searchProduct();
          });
        }
      }
    });
  };

  $scope.$on("productCtrl", function(event, data) {
    $scope.searchProduct();
    event.preventDefault();
  });

  // 예외출고 대상상품 그리드 조회
  $scope.searchProduct = function(){
    var params = {};
    params.listScale = 30;
    $scope._inquiryMain("/application/pos/excpForward/excpForward/getExcpForwardProduct.sb", params, function() {}, false);
  };


  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 세금계산서 목록 팝업 핸들러 추가
    $scope.excpForwardRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('registCtrl', $scope.getProd());
      }, 50)
    });
  });
}]);


