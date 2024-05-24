/****************************************************************
 *
 * 파일명 : storeChgCostPriceTab.js
 * 설  명 : 기초관리 > 가격관리 > 매장원가임의변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.05.17     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeChgCostPriceTabCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#byProdChgCostPriceView").show();
        $("#byStoreChgCostPriceView").hide();
        $("#storeChgCostPriceExcelUploadView").hide();
    };

    // 상품별 원가관리 탭 보이기
    $scope.byProdChgCostPriceShow = function () {
        $("#byProdChgCostPriceTab").addClass("on");
        $("#byStoreChgCostPriceTab").removeClass("on");
        $("#storeChgCostPriceExcelUploadTab").removeClass("on");

        $("#byProdChgCostPriceView").show();
        $("#byStoreChgCostPriceView").hide();
        $("#storeChgCostPriceExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("byProdChgCostPriceCtrl");
        scope.flex.refresh();
    };

    // 매장별 원가관리 탭 보이기
    $scope.byStoreChgCostPriceShow = function () {
        $("#byProdChgCostPriceTab").removeClass("on");
        $("#byStoreChgCostPriceTab").addClass("on");
        $("#storeChgCostPriceExcelUploadTab").removeClass("on");

        $("#byProdChgCostPriceView").hide();
        $("#byStoreChgCostPriceView").show();
        $("#storeChgCostPriceExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("byStoreChgCostPriceCtrl");
        scope.flex.refresh();
    };

    // 매장원가임의변경 엑셀업로드 탭 보이기
    $scope.storeChgCostPriceExcelUploadShow = function () {
        $("#byProdChgCostPriceTab").removeClass("on");
        $("#byStoreChgCostPriceTab").removeClass("on");
        $("#storeChgCostPriceExcelUploadTab").addClass("on");

        $("#byProdChgCostPriceView").hide();
        $("#byStoreChgCostPriceView").hide();
        $("#storeChgCostPriceExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeChgCostPriceExcelUploadCtrl");
        scope.flex.refresh();
    };

}]);