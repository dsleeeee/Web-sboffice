/****************************************************************
 *
 * 파일명 : splyPriceTab.js
 * 설  명 : 기초관리 > 가격관리 > 공급가관리 JavaScript
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

app.controller('splyPriceTabCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#splyPriceView").show();
        $("#splyPriceExcelUploadView").hide();
    };
    
    // 공급가관리 탭 보이기
    $scope.splyPriceShow = function () {
        $("#splyPriceTab").addClass("on");
        $("#splyPriceExcelUploadTab").removeClass("on");

        $("#splyPriceView").show();
        $("#splyPriceExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("splyPriceCtrl");
        scope.flex.refresh();
    };
    
    // 공급가관리 엑셀업로드 탭 보이기
    $scope.splyPriceExcelUploadShow = function () {
        $("#splyPriceTab").removeClass("on");
        $("#splyPriceExcelUploadTab").addClass("on");

        $("#splyPriceView").hide();
        $("#splyPriceExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("splyPriceExcelUploadCtrl");
        scope.flex.refresh();
    };
    
}]);