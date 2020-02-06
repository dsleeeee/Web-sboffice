/**
 * get application
 */
var app = agrid.getApp();

app.controller('versusPeriodSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
    	$("#versusPeriodClassView").show();
        $("#versusPeriodHourView").hide();
        $("#versusPeriodWeekView").hide();
        
        $("#versusPeriodClassTab").addClass("on");
    };

    // 분류별대비 탭 보이기
    $scope.versusPeriodClassShow = function () {
        $("#versusPeriodClassTab").addClass("on");
        $("#versusPeriodHourTab").removeClass("on");
        $("#versusPeriodWeekTab").removeClass("on");
               
        $("#versusPeriodClassView").show();
        $("#versusPeriodHourView").hide();
        $("#versusPeriodWeekView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("versusPeriodClassCtrl");
        scope.flex.refresh();
        
/*        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodRankCtrl");
        scope.flex.refresh();   
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodPayFgCtrl");
        scope.flex.refresh();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodHourCtrl");
        scope.flex.refresh();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodDayCtrl");
        scope.flex.refresh();*/
    };


    // 시간대비 탭 보이기
    $scope.versusPeriodHourShow = function () {
        $("#versusPeriodClassTab").removeClass("on");
        $("#versusPeriodHourTab").addClass("on");
        $("#versusPeriodWeekTab").removeClass("on");
        
        $("#versusPeriodClassView").hide();
        $("#versusPeriodHourView").show();
        $("#versusPeriodWeekView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("versusPeriodHourCtrl");
        scope.flex.refresh();       
    };


    // 주간대비 탭 보이기
    $scope.versusPeriodWeekShow = function () {
        $("#versusPeriodClassTab").removeClass("on");
        $("#versusPeriodHourTab").removeClass("on");
        $("#versusPeriodWeekTab").addClass("on");
        
        $("#versusPeriodClassView").hide();
        $("#versusPeriodHourView").hide();
        $("#versusPeriodWeekView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("versusPeriodWeekCtrl");
        scope.flex.refresh();
    };

}]);