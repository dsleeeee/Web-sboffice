/****************************************************************
 *
 * 파일명 : accountingDlvr.js
 * 설  명 : 벤슨 > 회계관리 > 배달비현황 JavaScript
 *
 *    수정일자      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.16    김유승        1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('accountingDlvrCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#accountingDlvrDayView").show();
        $("#accountingDlvrMonthView").hide();
    };

    // 일별 탭
    $scope.accountingDlvrDayShow = function () {
        $("#accountingDlvrDayTab").addClass("on");
        $("#accountingDlvrMonthTab").removeClass("on");

        $("#accountingDlvrDayView").show();
        $("#accountingDlvrMonthView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        agrid.getScope("accountingDlvrDayCtrl").flex.refresh();
    };

    // 월별 탭
    $scope.accountingDlvrMonthShow = function () {
        $("#accountingDlvrDayTab").removeClass("on");
        $("#accountingDlvrMonthTab").addClass("on");

        $("#accountingDlvrDayView").hide();
        $("#accountingDlvrMonthView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        agrid.getScope("accountingDlvrMonthCtrl").flex.refresh();
    };
}]);
