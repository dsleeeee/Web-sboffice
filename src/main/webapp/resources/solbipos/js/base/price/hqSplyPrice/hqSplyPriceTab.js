/****************************************************************
 *
 * 파일명 : hqSplyPriceTab.js
 * 설  명 : 기초관리 > 가격관리 > 본사공급가관리 JavaScript
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

app.controller('hqSplyPriceTabCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#hqSplyPriceView").show();
        $("#hqSplyPriceExcelUploadView").hide();
    };
    
    // 본사공급가관리 탭 보이기
    $scope.hqSplyPriceShow = function () {
        $("#hqSplyPriceTab").addClass("on");
        $("#hqSplyPriceExcelUploadTab").removeClass("on");

        $("#hqSplyPriceView").show();
        $("#hqSplyPriceExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("hqSplyPriceCtrl");
        scope.flex.refresh();
    };
    
    // 본사공급가관리 엑셀업로드 탭 보이기
    $scope.hqSplyPriceExcelUploadShow = function () {
        $("#hqSplyPriceTab").removeClass("on");
        $("#hqSplyPriceExcelUploadTab").addClass("on");

        $("#hqSplyPriceView").hide();
        $("#hqSplyPriceExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("hqSplyPriceExcelUploadCtrl");
        scope.flex.refresh();
    };
    
}]);