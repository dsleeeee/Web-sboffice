/****************************************************************
 *
 * 파일명 : hqSalePriceTab.js
 * 설  명 : 가격예약(본사판매가)(탭) JavaScript
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

app.controller('hqSalePriceResveTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#hqSalePriceResve").show();
        $("#hqSalePriceResveExcelUploadView").hide();
    };

    // 가격예약(본사판매가) 조회 탭 보이기
    $scope.hqSalePriceResveShow = function () {
        $("#hqSalePriceResveTab").addClass("on");
        $("#hqSalePriceResveExcelUploadTab").removeClass("on");

        $("#hqSalePriceResve").show();
        $("#hqSalePriceResveExcelUploadView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("hqSalePriceResveCtrl");
        scope.flex.refresh();
    };

    // 가격예약(본사판매가) 엑셀업로드 조회 탭 보이기
    $scope.hqSalePriceResveExcelUploadShow = function () {
        $("#hqSalePriceResveTab").removeClass("on");
        $("#hqSalePriceResveExcelUploadTab").addClass("on");

        $("#hqSalePriceResve").hide();
        $("#hqSalePriceResveExcelUploadView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("hqSalePriceResveExcelUploadCtrl");
        scope.flex.refresh();
    };

}]);