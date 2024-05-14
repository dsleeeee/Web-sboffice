/****************************************************************
 *
 * 파일명 : chgCostPriceTab.js
 * 설  명 : 기초관리 > 가격관리 > 원가임의변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.29     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('chgCostPriceTabCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#chgCostPriceView").show();
        $("#chgCostPriceExcelUploadView").hide();
    };

    // 원가임의변경 탭 보이기
    $scope.chgCostPriceShow = function () {
        $("#chgCostPriceTab").addClass("on");
        $("#chgCostPriceExcelUploadTab").removeClass("on");

        $("#chgCostPriceView").show();
        $("#chgCostPriceExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("chgCostPriceCtrl");
        scope.flex.refresh();
    };

    // 원가임의변경 엑셀업로드 탭 보이기
    $scope.chgCostPriceExcelUploadShow = function () {
        $("#chgCostPriceTab").removeClass("on");
        $("#chgCostPriceExcelUploadTab").addClass("on");

        $("#chgCostPriceView").hide();
        $("#chgCostPriceExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("chgCostPriceExcelUploadCtrl");
        scope.flex.refresh();
    };

}]);