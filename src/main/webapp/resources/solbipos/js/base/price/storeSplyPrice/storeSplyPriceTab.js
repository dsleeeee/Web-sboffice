/****************************************************************
 *
 * 파일명 : storeSplyPriceTab.js
 * 설  명 : 기초관리 > 가격관리 > 매장공급가관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.04     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeSplyPriceTabCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#byProdSplyPriceView").show();
        $("#byStoreSplyPriceView").hide();
        $("#storeSplyPriceCopyView").hide();
        $("#storeSplyPriceExcelUploadView").hide();
    };
    
    // 상품별 공급가관리 탭 보이기
    $scope.byProdSplyPriceShow = function () {
        $("#byProdSplyPriceTab").addClass("on");
        $("#byStoreSplyPriceTab").removeClass("on");
        $("#storeSplyPriceCopyTab").removeClass("on");
        $("#storeSplyPriceExcelUploadTab").removeClass("on");

        $("#byProdSplyPriceView").show();
        $("#byStoreSplyPriceView").hide();
        $("#storeSplyPriceCopyView").hide();
        $("#storeSplyPriceExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("byProdSplyPriceCtrl");
        scope.flex.refresh();
    };
    
    // 매장별 공급가관리 탭 보이기
    $scope.byStoreSplyPriceShow = function () {
        $("#byProdSplyPriceTab").removeClass("on");
        $("#byStoreSplyPriceTab").addClass("on");
        $("#storeSplyPriceCopyTab").removeClass("on");
        $("#storeSplyPriceExcelUploadTab").removeClass("on");

        $("#byProdSplyPriceView").hide();
        $("#byStoreSplyPriceView").show();
        $("#storeSplyPriceCopyView").hide();
        $("#storeSplyPriceExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("byStoreSplyPriceCtrl");
        scope.flex.refresh();
    };
    
    // 매장공급가복사 탭 보이기
    $scope.storeSplyPriceCopyShow = function () {
        $("#byProdSplyPriceTab").removeClass("on");
        $("#byStoreSplyPriceTab").removeClass("on");
        $("#storeSplyPriceCopyTab").addClass("on");
        $("#storeSplyPriceExcelUploadTab").removeClass("on");

        $("#byProdSplyPriceView").hide();
        $("#byStoreSplyPriceView").hide();
        $("#storeSplyPriceCopyView").show();
        $("#storeSplyPriceExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeSplyPriceCopyCtrl");
        scope.flex.refresh();
    };
    
    // 매장공급가관리 엑셀업로드 탭 보이기
    $scope.storeSplyPriceExcelUploadShow = function () {
        $("#byProdSplyPriceTab").removeClass("on");
        $("#byStoreSplyPriceTab").removeClass("on");
        $("#storeSplyPriceCopyTab").removeClass("on");
        $("#storeSplyPriceExcelUploadTab").addClass("on");

        $("#byProdSplyPriceView").hide();
        $("#byStoreSplyPriceView").hide();
        $("#storeSplyPriceCopyView").hide();
        $("#storeSplyPriceExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeSplyPriceExcelUploadCtrl");
        scope.flex.refresh();
    };

}]);