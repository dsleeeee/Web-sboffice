/****************************************************************
 *
 * 파일명 : orderChannel.js
 * 설  명 : 매출관리 > 배달현황 > 주문채널별현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.01     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 탭 영역
 */
app.controller('orderChannelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#orderChannelPeriodView").show();
        $("#orderChannelDayView").hide();
        $("#orderChannelMonthView").hide();
    };

    // 기간별 탭 보이기
    $scope.orderChannelPeriodShow = function () {
        $("#orderChannelPeriodTab").addClass("on");
        $("#orderChannelDayTab").removeClass("on");
        $("#orderChannelMonthTab").removeClass("on");

        $("#orderChannelPeriodView").show();
        $("#orderChannelDayView").hide();
        $("#orderChannelMonthView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orderChannelPeriodCtrl");
        scope.flex.refresh();
    };

    // 일별 탭 보이기
    $scope.orderChannelDayShow = function () {
        $("#orderChannelPeriodTab").removeClass("on");
        $("#orderChannelDayTab").addClass("on");
        $("#orderChannelMonthTab").removeClass("on");

        $("#orderChannelPeriodView").hide();
        $("#orderChannelDayView").show();
        $("#orderChannelMonthView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orderChannelDayCtrl");
        scope.flex.refresh();
    };

    // 월별 탭 보이기
    $scope.orderChannelMonthShow = function () {
        $("#orderChannelPeriodTab").removeClass("on");
        $("#orderChannelDayTab").removeClass("on");
        $("#orderChannelMonthTab").addClass("on");

        $("#orderChannelPeriodView").hide();
        $("#orderChannelDayView").hide();
        $("#orderChannelMonthView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orderChannelMonthCtrl");
        scope.flex.refresh();
    };

}]);