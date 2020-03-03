/**
 * get application
 */
var app = agrid.getApp();

app.controller('goalCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#goalDayView").show();
        $("#goalMonthView").hide();
    };

    // 일자별목표대배매출 탭 보이기
    $scope.goalDayShow = function () {
        $("#goalDayTab").addClass("on");
        $("#goalMonthTab").removeClass("on");
               
        $("#goalDayView").show();
        $("#goalMonthView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("goalDayCtrl");
        scope.flex.refresh();
        
    };


    // 월별목표대배매출 탭 보이기
    $scope.goalMonthShow = function () {
        $("#goalDayTab").removeClass("on");
        $("#goalMonthTab").addClass("on");
        
        $("#goalDayView").hide();
        $("#goalMonthView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("goalMonthCtrl");
        scope.flex.refresh();       

    };

}]);