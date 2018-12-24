/****************************************************************
 *
 * 파일명 : salePrice.js
 * 설  명 : 판매가관리 >매장판매가관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.20     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 판매가관리 그리드 생성
 */
app.controller('sideMenuCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuCtrl', $scope, $http, false));
  // 상품 탭
  $scope.isProdTab = false;
  // 매장 탭
  $scope.isStoreTab = true;
  // 탭변경
  $scope.changeTab = function(type){

    if ( type === 'P' ) {  // 상품별 판매가관리 탭
      $("#prodSalePrice").addClass("on");
      $("#storeSalePrice").removeClass("on");
      $scope.isProdTab = false;
      $scope.isStoreTab = true;
      // 속성 조회
      $scope._broadcast("prodSalePriceCtrl");

    } else if ( type === 'S' ) {  // 매장별 판매가관리 탭
      $("#prodSalePrice").removeClass("on");
      $("#storeSalePrice").addClass("on");
      $scope.isProdTab = true;
      $scope.isStoreTab = false;
      // 선택그룹 조회
      $scope._broadcast("storeSalePriceCtrl");
      // // 그리드 refresh
      // setTimeout(function () {
      //   $scope._broadcast("selectMenuRefresh");
      // }, 10);
    }
  };
  // 탭 조회
  $scope.queryTab = function() {
    if ( $scope.isProdTab ) {
      // 상품 조회
      $scope._broadcast("prodSalePriceCtrl");
    } else {
      // 매장 조회
      $scope._broadcast("storeSalePriceCtrl");
    }
  };
}]);

