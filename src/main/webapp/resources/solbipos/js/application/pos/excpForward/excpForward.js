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
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    // $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    // $scope.coupnDcFgDataMap = new wijmo.grid.DataMap(coupnDcFg, 'value', 'name');
    // $scope.coupnApplyFgDataMap = new wijmo.grid.DataMap(coupnApplyFg, 'value', 'name');

  };

  $scope.$on("productCtrl", function(event, data) {
    $scope.searchProduct();
    event.preventDefault();
  });

  // 상품목록 그리드 조회
  $scope.searchProduct = function(){
    // 파라미터
    var params = {};

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "excpForward/getExcpForwardProduct.sb", params, function() {}, false);
  };


}]);


