/****************************************************************
 *
 * 파일명 : timeSaleBenson.js
 * 설  명 : 벤슨 > 매출분석 > 시간대매출 JavaScript
 *
 *    수정일자      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.20    김유승        1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('timeSaleBensonCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#timeSaleBensonDayView").show();
        $("#timeSaleBensonMonthView").hide();

    };

    // 일별 탭
    $scope.timeSaleBensonDayShow = function () {
        $("#timeSaleBensonDayTab").addClass("on");
        $("#timeSaleBensonMonthTab").removeClass("on");

        $("#timeSaleBensonDayView").show();
        $("#timeSaleBensonMonthView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        agrid.getScope("timeSaleBensonDayCtrl").flex.refresh();
    };

    // 월별 탭
    $scope.timeSaleBensonMonthShow = function () {
        $("#timeSaleBensonDayTab").removeClass("on");
        $("#timeSaleBensonMonthTab").addClass("on");

        $("#timeSaleBensonDayView").hide();
        $("#timeSaleBensonMonthView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        agrid.getScope("timeSaleBensonMonthCtrl").flex.refresh();
    };
}]);
