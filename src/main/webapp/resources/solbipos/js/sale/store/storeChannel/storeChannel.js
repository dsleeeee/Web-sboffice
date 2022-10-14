/****************************************************************
 *
 * 파일명 : storeChannel.js
 * 설  명 : 맘스터치 > 점포매출 > 채널별 매출 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.10.14     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 탭 영역
 */
app.controller('storeChannelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#storeChannelPeriodView").show();
        $("#storeChannelDayView").hide();
        $("#storeChannelMonthView").hide();
    };

    // 기간별 탭 보이기
    $scope.storeChannelPeriodShow = function () {
        $("#storeChannelPeriodTab").addClass("on");
        $("#storeChannelDayTab").removeClass("on");
        $("#storeChannelMonthTab").removeClass("on");

        $("#storeChannelPeriodView").show();
        $("#storeChannelDayView").hide();
        $("#storeChannelMonthView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeChannelPeriodCtrl");
        scope.flex.refresh();
    };

    // 일별 탭 보이기
    $scope.storeChannelDayShow = function () {
        $("#storeChannelPeriodTab").removeClass("on");
        $("#storeChannelDayTab").addClass("on");
        $("#storeChannelMonthTab").removeClass("on");

        $("#storeChannelPeriodView").hide();
        $("#storeChannelDayView").show();
        $("#storeChannelMonthView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeChannelDayCtrl");
        scope.flex.refresh();
    };

    // 월별 탭 보이기
    $scope.storeChannelMonthShow = function () {
        $("#storeChannelPeriodTab").removeClass("on");
        $("#storeChannelDayTab").removeClass("on");
        $("#storeChannelMonthTab").addClass("on");

        $("#storeChannelPeriodView").hide();
        $("#storeChannelDayView").hide();
        $("#storeChannelMonthView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeChannelMonthCtrl");
        scope.flex.refresh();
    };

}]);