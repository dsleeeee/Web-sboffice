/**
 * get application
 */
var app = agrid.getApp();

app.controller('posSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#posDayView").show();
        $("#posDayOfWeekView").hide();
        $("#posMonthView").hide();
        $("#posProdView").hide();
        $("#posDayPeriodView").hide();
        $("#posHourView").hide();
    };

    // 일자별 탭 보이기
    $scope.posDayTabShow = function () {
        $("#posDayTab").addClass("on");
        $("#posDayOfWeekTab").removeClass("on");
        $("#posMonthTab").removeClass("on");
        $("#posProdTab").removeClass("on");
        $("#posDayPeriodTab").removeClass("on");
        $("#posHourTab").removeClass("on");

        $("#posDayView").show();
        $("#posDayOfWeekView").hide();
        $("#posMonthView").hide();
        $("#posProdView").hide();
        $("#posDayPeriodView").hide();
        $("#posHourView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("posDayCtrl");
        console.log(scope);
        scope.flex.refresh();
    };

    // 요일별 탭 보이기
    $scope.posDayOfWeekTabShow = function () {
    	$("#posDayOfWeekTab").addClass("on");
    	$("#posDayTab").removeClass("on");
    	$("#posMonthTab").removeClass("on");
    	$("#posProdTab").removeClass("on");
        $("#posDayPeriodTab").removeClass("on");
        $("#posHourTab").removeClass("on");

    	$("#posDayOfWeekView").show();
        $("#posDayView").hide();
        $("#posMonthView").hide();
        $("#posProdView").hide();
        $("#posDayPeriodView").hide();
        $("#posHourView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("posDayOfWeekCtrl");
        console.log(scope);
        scope.flex.refresh();
    };

    // 월별 탭 보이기
    $scope.posMonthTabShow = function () {
    	$("#posMonthTab").addClass("on");
    	$("#posDayTab").removeClass("on");
    	$("#posDayOfWeekTab").removeClass("on");
    	$("#posProdTab").removeClass("on");
        $("#posDayPeriodTab").removeClass("on");
        $("#posHourTab").removeClass("on");
        
    	$("#posMonthView").show();
        $("#posDayView").hide();
        $("#posDayOfWeekView").hide();
        $("#posProdView").hide();
        $("#posDayPeriodView").hide();
        $("#posHourView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("posMonthCtrl");
        scope.flex.refresh();
    };

    // 상품별 탭 보이기
    $scope.posProdTabShow = function () {
    	$("#posProdTab").addClass("on");
    	$("#posDayTab").removeClass("on");
    	$("#posDayOfWeekTab").removeClass("on");
    	$("#posMonthTab").removeClass("on");
        $("#posDayPeriodTab").removeClass("on");
        $("#posHourTab").removeClass("on");

        $("#posProdView").show();
        $("#posDayView").hide();
        $("#posDayOfWeekView").hide();
        $("#posMonthView").hide();
        $("#posDayPeriodView").hide();
        $("#posHourView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("posProdCtrl");
        scope.flex.refresh();
    };

    // 설정기간별 탭 보이기
    $scope.posDayPeriodTabShow = function () {
    	$("#posDayPeriodTab").addClass("on");
    	$("#posDayTab").removeClass("on");
    	$("#posDayOfWeekTab").removeClass("on");
    	$("#posProdTab").removeClass("on");
        $("#posMonthTab").removeClass("on");
        $("#posHourTab").removeClass("on");
        
    	$("#posDayPeriodView").show();
        $("#posDayView").hide();
        $("#posDayOfWeekView").hide();
        $("#posProdView").hide();
        $("#posMonthView").hide();
        $("#posHourView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("posDayPeriodDtlCtrl");
        scope.flex.refresh();

        scope = agrid.getScope("posDayPeriodCtrl");
        scope.flex.refresh();
    };
    
    // 시간대별 탭 보이기
    $scope.posHourTabShow = function () {
    	$("#posDayPeriodTab").removeClass("on");
    	$("#posDayTab").removeClass("on");
    	$("#posDayOfWeekTab").removeClass("on");
    	$("#posProdTab").removeClass("on");
        $("#posMonthTab").removeClass("on");
        $("#posHourTab").addClass("on");
        
    	$("#posDayPeriodView").hide();
        $("#posDayView").hide();
        $("#posDayOfWeekView").hide();
        $("#posProdView").hide();
        $("#posMonthView").hide();
        $("#posHourView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("posHourCtrl");
        scope.flex.refresh();

    };
}]);