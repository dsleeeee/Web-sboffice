/****************************************************************
 *
 * 파일명 : hqSalePriceTab.js
 * 설  명 : 본사판매가관리(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.02.21     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('hqSalePriceTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#hqSalePriceArea").show();
        $("#hqSalePriceExcelUploadView").hide();
    };

    // 본사판매가관리 조회 탭 보이기
    $scope.hqSalePriceShow = function () {
        $("#hqSalePriceTab").addClass("on");
        $("#hqSalePriceExcelUploadTab").removeClass("on");

        $("#hqSalePriceArea").show();
        $("#hqSalePriceExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("hqSalePriceCtrl");
        scope.flex.refresh();
    };

    // 본사판매가관리 엑셀업로드 탭 보이기
    $scope.hqSalePriceExcelUploadShow = function () {
        $("#hqSalePriceTab").removeClass("on");
        $("#hqSalePriceExcelUploadTab").addClass("on");

        $("#hqSalePriceArea").hide();
        $("#hqSalePriceExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("hqSalePriceExcelUploadCtrl");
        scope.flex.refresh();
    };

}]);