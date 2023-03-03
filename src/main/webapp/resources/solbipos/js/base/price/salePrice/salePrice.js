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
  // 본사 탭
  $scope.isHqTab = true;
  // 복사 탭
  $scope.isCopyTab = true;
  // 탭변경
  $scope.changeTab = function(type){

    if ( type === 'P' ) {  // 상품별 판매가관리 탭
      $("#prodSalePrice").addClass("on");
      $("#storeSalePrice").removeClass("on");
      $("#hqSalePrice").removeClass("on");
      $("#storeSaleCopy").removeClass("on");
      $("#storeSalePriceExcelUpload").removeClass("on");
      $("#prodSalePriceArea").show();
      $("#storeSalePriceArea").hide();
      $("#hqSalePriceArea").hide();
      $("#storeSaleCopyArea").hide();
      $("#storeSalePriceExcelUploadView").hide();
      $scope.isProdTab = false;
      $scope.isStoreTab = true;
      $scope.isHqTab = true;
      $scope.isCopyTab = true;
      // 속성 조회
      // $scope._broadcast("prodSalePriceCtrl");

    } else if ( type === 'S' ) {  // 매장별 판매가관리 탭

      // $scope._popMsg("서비스 준비중입니다.");
      // return false;

      $("#prodSalePrice").removeClass("on");
      $("#storeSalePrice").addClass("on");
      $("#hqSalePrice").removeClass("on");
      $("#storeSaleCopy").removeClass("on");
      $("#storeSalePriceExcelUpload").removeClass("on");
      $("#prodSalePriceArea").hide();
      $("#storeSalePriceArea").show();
      $("#hqSalePriceArea").hide();
      $("#storeSaleCopyArea").hide();
      $("#storeSalePriceExcelUploadView").hide();
      $scope.isProdTab = true;
      $scope.isStoreTab = false;
      $scope.isHqTab = true;
      $scope.isCopyTab = true;
      // 선택그룹 조회
      // $scope._broadcast("storeSalePriceCtrl");
      // // 그리드 refresh
      // setTimeout(function () {
      //   $scope._broadcast("selectMenuRefresh");
      // }, 10);
    }  else if ( type === 'C' ) {  // 매장판매가복사 탭

      // $scope._popMsg("서비스 준비중입니다.");
      // return false;

      $("#prodSalePrice").removeClass("on");
      $("#storeSalePrice").removeClass("on");
      $("#hqSalePrice").removeClass("on");
      $("#storeSaleCopy").addClass("on");
      $("#storeSalePriceExcelUpload").removeClass("on");
      $("#prodSalePriceArea").hide();
      $("#storeSalePriceArea").hide();
      $("#hqSalePriceArea").hide();
      $("#storeSaleCopyArea").show();
      $("#storeSalePriceExcelUploadView").hide();
      $scope.isProdTab = true;
      $scope.isStoreTab = true;
      $scope.isHqTab = true;
      $scope.isCopyTab = false;
      // 선택그룹 조회
      // $scope._broadcast("storeSalePriceCtrl");
      // // 그리드 refresh
      // setTimeout(function () {
      //   $scope._broadcast("selectMenuRefresh");
      // }, 10);
    }  else if ( type === 'U' ) {  // 매장판매가관리 엑셀업로드 탭

      // $scope._popMsg("서비스 준비중입니다.");
      // return false;

      $("#prodSalePrice").removeClass("on");
      $("#storeSalePrice").removeClass("on");
      $("#hqSalePrice").removeClass("on");
      $("#storeSaleCopy").removeClass("on");
      $("#storeSalePriceExcelUpload").addClass("on");
      $("#prodSalePriceArea").hide();
      $("#storeSalePriceArea").hide();
      $("#hqSalePriceArea").hide();
      $("#storeSaleCopyArea").hide();
      $("#storeSalePriceExcelUploadView").show();
      $scope.isProdTab = true;
      $scope.isStoreTab = true;
      $scope.isHqTab = true;
      $scope.isCopyTab = false;
      // 선택그룹 조회
      // $scope._broadcast("storeSalePriceCtrl");
      // // 그리드 refresh
      // setTimeout(function () {
      //   $scope._broadcast("selectMenuRefresh");
      // }, 10);
    }
  };

  // // 탭 조회
  // $scope.queryTab = function() {
  //   if ( $scope.isProdTab ) {
  //     // 상품 조회
  //     $scope._broadcast("prodSalePriceCtrl");
  //   } else {
  //     // 매장 조회
  //     $scope._broadcast("storeSalePriceCtrl");
  //   }
  // };

}]);