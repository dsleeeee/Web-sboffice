/**
 * get application
 */
var app = agrid.getApp();

app.controller('dayPeriodCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#dayPeriodTimeView").hide();
        $("#dayPeriodProdClassView").hide();
        $("#dayPeriodTableView").hide();
        $("#dayPeriodCornerView").hide();
        $("#dayPeriodGiftView").hide();
    };

    // 시간대별 탭 보이기
    $scope.dayPeriodTimeShow = function () {
        $("#dayPeriodTimeTab").addClass("on");
        $("#dayPeriodProdClassTab").removeClass("on");
        $("#dayPeriodTableTab").removeClass("on");
        $("#dayPeriodCornerTab").removeClass("on");
        $("#dayPeriodGiftTab").removeClass("on");

        $("#dayPeriodTimeView").show();
        $("#dayPeriodProdClassView").hide();
        $("#dayPeriodTableView").hide();
        $("#dayPeriodCornerView").hide();
        $("#dayPeriodGiftView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayPeriodTimeCtrl");
        scope.flex.refresh();
        var scope2 = agrid.getScope("dayPeriodTimeDetailCtrl");
        scope2.flex.refresh();
    };

    // 상품분류별 탭 보이기
    $scope.dayPeriodProdClassShow = function () {
        $("#dayPeriodTimeTab").removeClass("on");
        $("#dayPeriodProdClassTab").addClass("on");
        $("#dayPeriodTableTab").removeClass("on");
        $("#dayPeriodCornerTab").removeClass("on");
        $("#dayPeriodGiftTab").removeClass("on");

        $("#dayPeriodTimeView").hide();
        $("#dayPeriodProdClassView").show();
        $("#dayPeriodTableView").hide();
        $("#dayPeriodCornerView").hide();
        $("#dayPeriodGiftView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayPeriodProdClassCtrl");
        scope.flex.refresh();
        var scope2 = agrid.getScope("dayPeriodProdClassDetailCtrl");
        scope2.flex.refresh();
    };

    // 외식테이블별 탭 보이기
    $scope.dayPeriodTableShow = function () {
        $("#dayPeriodTimeTab").removeClass("on");
        $("#dayPeriodProdClassTab").removeClass("on");
        $("#dayPeriodTableTab").addClass("on");
        $("#dayPeriodCornerTab").removeClass("on");
        $("#dayPeriodGiftTab").removeClass("on");

        $("#dayPeriodTimeView").hide();
        $("#dayPeriodProdClassView").hide();
        $("#dayPeriodTableView").show();
        $("#dayPeriodCornerView").hide();
        $("#dayPeriodGiftView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayPeriodTableCtrl");
        scope.flex.refresh();
    };

    // 코너별 탭 보이기
    $scope.dayPeriodCornerShow = function () {
        $("#dayPeriodTimeTab").removeClass("on");
        $("#dayPeriodProdClassTab").removeClass("on");
        $("#dayPeriodTableTab").removeClass("on");
        $("#dayPeriodCornerTab").addClass("on");
        $("#dayPeriodGiftTab").removeClass("on");

        $("#dayPeriodTimeView").hide();
        $("#dayPeriodProdClassView").hide();
        $("#dayPeriodTableView").hide();
        $("#dayPeriodCornerView").show();
        $("#dayPeriodGiftView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayPeriodCornerCtrl");
        scope.flex.refresh();
        var scope2 = agrid.getScope("dayPeriodCornerDetailCtrl");
        scope2.flex.refresh();
    };

    // 상품권별 탭 보이기
    $scope.dayPeriodGiftShow = function () {
        $("#dayPeriodTimeTab").removeClass("on");
        $("#dayPeriodProdClassTab").removeClass("on");
        $("#dayPeriodTableTab").removeClass("on");
        $("#dayPeriodCornerTab").removeClass("on");
        $("#dayPeriodGiftTab").addClass("on");

        $("#dayPeriodTimeView").hide();
        $("#dayPeriodProdClassView").hide();
        $("#dayPeriodTableView").hide();
        $("#dayPeriodCornerView").hide();
        $("#dayPeriodGiftView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayPeriodGiftCtrl");
        scope.flex.refresh();
        var scope2 = agrid.getScope("dayPeriodGiftDetailCtrl");
        scope2.flex.refresh();
    };
}]);