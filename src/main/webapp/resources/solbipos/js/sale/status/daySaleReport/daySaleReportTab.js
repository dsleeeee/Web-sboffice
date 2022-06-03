/****************************************************************
 *
 * 파일명 : daySaleReportTab.js
 * 설  명 : 일별매출내역 다운로드(탭)(제너시스올떡 분식대장) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.25     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('daySaleReportTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#daySaleReportListView").show();
        $("#daySaleReportView").hide();
    };

    // 일별매출내역 조회 탭 보이기
    $scope.daySaleReportListShow = function () {
        $("#daySaleReportListTab").addClass("on");
        $("#daySaleReportTab").removeClass("on");

        $("#daySaleReportListView").show();
        $("#daySaleReportView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("daySaleReportListCtrl");
        scope.flex.refresh();
    };

    // 일별매출내역 다운로드 탭 보이기
    $scope.daySaleReportShow = function () {
        $("#daySaleReportListTab").removeClass("on");
        $("#daySaleReportTab").addClass("on");

        $("#daySaleReportListView").hide();
        $("#daySaleReportView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("daySaleReportCtrl");
        scope.flex.refresh();
    };
}]);