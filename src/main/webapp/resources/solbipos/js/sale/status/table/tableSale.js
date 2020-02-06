/**
 * get application
 */
var app = agrid.getApp();

app.controller('tableCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#tableDayView").show();
        $("#tableDayOfWeekView").hide();
        $("#tableMonthView").hide();
        $("#tableDayPeriodView").hide();
    };

    // 일자별 탭 보이기
    $scope.tableDayShow = function () {
        $("#tableDayTab").addClass("on");
        $("#tableDayOfWeekTab").removeClass("on");
        $("#tableMonthTab").removeClass("on");
        $("#tableDayPeriodTab").removeClass("on");

        $("#tableDayView").show();
        $("#tableDayOfWeekView").hide();
        $("#tableMonthView").hide();
        $("#tableDayPeriodView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("tableDayCtrl");
        scope.flex.refresh();

//        scope = agrid.getScope("todayGnrlzPayCtrl");
//        scope.flex.refresh();
//
//        scope = agrid.getScope("todayGnrlzMemberCtrl");
//        scope.flex.refresh();
//
//        scope = agrid.getScope("todayGnrlzProdCtrl");
//        scope.flex.refresh();
    };


    // 요일별 탭 보이기
    $scope.tableDayOfWeekShow = function () {
        $("#tableDayTab").removeClass("on");
        $("#tableDayOfWeekTab").addClass("on");
        $("#tableMonthTab").removeClass("on");
        $("#tableDayPeriodTab").removeClass("on");

        $("#tableDayView").hide();
        $("#tableDayOfWeekView").show();
        $("#tableMonthView").hide();
        $("#tableDayPeriodView").hide();


        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("tableDayOfWeekCtrl");
        scope.flex.refresh();

//        scope = agrid.getScope("todayDtlDetailCtrl");
//        scope.flex.refresh();
    };


    // 월별 탭 보이기
    $scope.tableMonthShow = function () {
        $("#tableDayTab").removeClass("on");
        $("#tableDayOfWeekTab").removeClass("on");
        $("#tableMonthTab").addClass("on");
        $("#tableDayPeriodTab").removeClass("on");

        $("#tableDayView").hide();
        $("#tableDayOfWeekView").hide();
        $("#tableMonthView").show();
        $("#tableDayPeriodView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("tableMonthCtrl");
        scope.flex.refresh();
    };


    // 설정기간별 탭 보이기
    $scope.tableDayPeriodShow = function () {
        $("#tableDayTab").removeClass("on");
        $("#tableDayOfWeekTab").removeClass("on");
        $("#tableMonthTab").removeClass("on");
        $("#tableDayPeriodTab").addClass("on");

        $("#tableDayView").hide();
        $("#tableDayOfWeekView").hide();
        $("#tableMonthView").hide();
        $("#tableDayPeriodView").show();


        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("tableDayPeriodCtrl");
        scope.flex.refresh();
    };

}]);