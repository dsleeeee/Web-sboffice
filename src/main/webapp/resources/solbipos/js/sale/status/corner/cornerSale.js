/**
 * get application
 */
var app = agrid.getApp();

app.controller('cornerSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#cornerDayView").show();
        $("#cornerDayOfWeekView").hide();
        $("#cornerMonthView").hide();
        $("#cornerDayPeriodView").hide();
    };

    // 일자별 탭 보이기
    $scope.cornerDayTabShow = function () {
        $("#cornerDayTab").addClass("on");
        $("#cornerDayOfWeekTab").removeClass("on");
        $("#cornerMonthTab").removeClass("on");
        $("#cornerDayPeriodTab").removeClass("on");

        $("#cornerDayView").show();
        $("#cornerDayOfWeekView").hide();
        $("#cornerMonthView").hide();
        $("#cornerDayPeriodView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("cornerDayCtrl");
        scope.flex.refresh();
    };


    // 요일별 탭 보이기
    $scope.cornerDayOfWeekTabShow = function () {
    	$("#cornerDayOfWeekTab").addClass("on");
    	$("#cornerDayTab").removeClass("on");
    	$("#cornerMonthTab").removeClass("on");
        $("#cornerDayPeriodTab").removeClass("on");

    	$("#cornerDayOfWeekView").show();
        $("#cornerDayView").hide();
        $("#cornerMonthView").hide();
        $("#cornerDayPeriodView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("cornerDayOfWeekCtrl");
        scope.flex.refresh();
    };


    // 월별 탭 보이기
    $scope.cornerMonthTabShow = function () {
    	$("#cornerMonthTab").addClass("on");
    	$("#cornerDayTab").removeClass("on");
    	$("#cornerDayOfWeekTab").removeClass("on");
        $("#cornerDayPeriodTab").removeClass("on");

    	$("#cornerMonthView").show();
        $("#cornerDayView").hide();
        $("#cornerDayOfWeekView").hide();
        $("#cornerDayPeriodView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("cornerMonthCtrl");
        scope.flex.refresh();
    };


 // 설정기간별 탭 보이기
    $scope.cornerDayPeriodTabShow = function () {
    	$("#cornerDayPeriodTab").addClass("on");
    	$("#cornerDayTab").removeClass("on");
    	$("#cornerDayOfWeekTab").removeClass("on");
        $("#cornerMonthTab").removeClass("on");

    	$("#cornerDayPeriodView").show();
        $("#cornerDayView").hide();
        $("#cornerDayOfWeekView").hide();
        $("#cornerMonthView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scopeMain = agrid.getScope("cornerDayPeriodMainCtrl");
        scopeMain.flex.refresh();

        var scopeDtl = agrid.getScope("cornerDayPeriodDtlCtrl");
        scopeDtl.flex.refresh();
    };
}]);