/****************************************************************
 *
 * 파일명 : storeSalePriceResve.js
 * 설  명 : 기초관리 > 가격관리 > 가격예약(매장판매가) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.12     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 가격예약(매장판매가) 그리드 생성
 */
app.controller('storeSalePriceResveCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSalePriceResveCtrl', $scope, $http, false));
    // 상품 탭
    $scope.isProdTab = false;
    // 매장 탭
    $scope.isStoreTab = true;
    // 탭변경
    $scope.changeTab = function(type){

        if ( type === 'P' ) {  // 상품별 판매가관리 탭

            $("#prodSalePriceResve").addClass("on");
            $("#storeSalePriceResve").removeClass("on");
            $("#prodSalePriceResveArea").show();
            $("#storeSalePriceResveArea").hide();
            $scope.isProdTab = false;
            $scope.isStoreTab = true;

        } else if ( type === 'S' ) {  // 매장별 판매가관리 탭

            $("#prodSalePriceResve").removeClass("on");
            $("#storeSalePriceResve").addClass("on");
            $("#prodSalePriceResveArea").hide();
            $("#storeSalePriceResveArea").show();
            $scope.isProdTab = true;
            $scope.isStoreTab = false;
        }
    };

}]);
