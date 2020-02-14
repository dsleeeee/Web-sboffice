/**
 * get application
 */
var app = agrid.getApp();

app.controller('empSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
    	$("#empDayView").show();
        $("#empDayOfWeekView").hide();
        $("#empMonthView").hide();
        $("#empDayPeriodView").hide();
        $("#empPosView").hide();
    };

    // 일자별 탭 보이기
    $scope.empDayShow = function () {
        $("#empDayTab").addClass("on");
        $("#empDayOfWeekTab").removeClass("on");
        $("#empMonthTab").removeClass("on");
        $("#empDayPeriodTab").removeClass("on");
        $("#empPosTab").removeClass("on");

        $("#empDayView").show();
        $("#empDayOfWeekView").hide();
        $("#empMonthView").hide();
        $("#empDayPeriodView").hide();
        $("#empPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empDayCtrl");
        scope.flex.refresh();
    };


    // 요일별 탭 보이기
    $scope.empDayOfWeekShow = function () {
        $("#empDayTab").removeClass("on");
        $("#empDayOfWeekTab").addClass("on");
        $("#empMonthTab").removeClass("on");
        $("#empDayPeriodTab").removeClass("on");
        $("#empPosTab").removeClass("on");

        $("#empDayView").hide();
        $("#empDayOfWeekView").show();
        $("#empMonthView").hide();
        $("#empDayPeriodView").hide();
        $("#empPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empDayOfWeekCtrl");
        scope.flex.refresh();
    };


    // 월별 탭 보이기
    $scope.empMonthShow = function () {
        $("#empDayTab").removeClass("on");
        $("#empDayOfWeekTab").removeClass("on");
        $("#empMonthTab").addClass("on");
        $("#empDayPeriodTab").removeClass("on");
        $("#empPosTab").removeClass("on");

        $("#empDayView").hide();
        $("#empDayOfWeekView").hide();
        $("#empMonthView").show();
        $("#empDayPeriodView").hide();
        $("#empPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empMonthCtrl");
        scope.flex.refresh();
    };

    // 설정기간별 탭 보이기
    $scope.empDayPeriodShow = function () {
        $("#empDayTab").removeClass("on");
        $("#empDayOfWeekTab").removeClass("on");
        $("#empMonthTab").removeClass("on");
        $("#empDayPeriodTab").addClass("on");
        $("#empPosTab").removeClass("on");

        $("#empDayView").hide();
        $("#empDayOfWeekView").hide();
        $("#empMonthView").hide();
        $("#empDayPeriodView").show();
        $("#empPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scopeMain = agrid.getScope("empDayPeriodMainCtrl");
        scopeMain.flex.refresh();

        var scopeDtl = agrid.getScope("empDayPeriodDtlCtrl");
        scopeDtl.dtlFlex.refresh();
    };

    // 포스별 탭 보이기
    $scope.empPosShow = function () {
        $("#empDayTab").removeClass("on");
        $("#empDayOfWeekTab").removeClass("on");
        $("#empMonthTab").removeClass("on");
        $("#empDayPeriodTab").removeClass("on");
        $("#empPosTab").addClass("on");

        $("#empDayView").hide();
        $("#empDayOfWeekView").hide();
        $("#empMonthView").hide();
        $("#empDayPeriodView").hide();
        $("#empPosView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empPosCtrl");
        scope.flex.refresh();
    };

}]);