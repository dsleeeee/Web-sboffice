/**
 * get application
 */
var app = agrid.getApp();

app.controller('dayOfWeekCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#dayOfWeekTotalView").hide();
        $("#dayOfWeekDcView").hide();
        $("#dayOfWeekTaxView").hide();
        $("#dayOfWeekTimeView").hide();
        $("#dayOfWeekProdClassView").hide();
        $("#dayOfWeekCornerView").hide();
        $("#dayOfWeekTableView").hide();
        $("#dayOfWeekPosView").hide();
    };

    // 주간종합 탭 보이기
    $scope.dayOfWeekTotalShow = function () {
        $("#dayOfWeekTotalTab").addClass("on");
        $("#dayOfWeekDcTab").removeClass("on");
        $("#dayOfWeekTaxTab").removeClass("on");
        $("#dayOfWeekTimeTab").removeClass("on");
        $("#dayOfWeekProdClassTab").removeClass("on");
        $("#dayOfWeekCornerTab").removeClass("on");
        $("#dayOfWeekTableTab").removeClass("on");
        $("#dayOfWeekPosTab").removeClass("on");

        $("#dayOfWeekTotalView").show();
        $("#dayOfWeekDcView").hide();
        $("#dayOfWeekTaxView").hide();
        $("#dayOfWeekTimeView").hide();
        $("#dayOfWeekProdClassView").hide();
        $("#dayOfWeekCornerView").hide();
        $("#dayOfWeekTableView").hide();
        $("#dayOfWeekPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayOfWeekTotalCtrl");
        scope.flex.refresh();
    };

    // 할인구분별 탭 보이기
    $scope.dayOfWeekDcShow = function () {
        $("#dayOfWeekTotalTab").removeClass("on");
        $("#dayOfWeekDcTab").addClass("on");
        $("#dayOfWeekTaxTab").removeClass("on");
        $("#dayOfWeekTimeTab").removeClass("on");
        $("#dayOfWeekProdClassTab").removeClass("on");
        $("#dayOfWeekCornerTab").removeClass("on");
        $("#dayOfWeekTableTab").removeClass("on");
        $("#dayOfWeekPosTab").removeClass("on");

        $("#dayOfWeekTotalView").hide();
        $("#dayOfWeekDcView").show();
        $("#dayOfWeekTaxView").hide();
        $("#dayOfWeekTimeView").hide();
        $("#dayOfWeekProdClassView").hide();
        $("#dayOfWeekCornerView").hide();
        $("#dayOfWeekTableView").hide();
        $("#dayOfWeekPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayOfWeekDcCtrl");
        scope.flex.refresh();
    };

    // 과면세별 탭 보이기
    $scope.dayOfWeekTaxShow = function () {
        $("#dayOfWeekTotalTab").removeClass("on");
        $("#dayOfWeekDcTab").removeClass("on");
        $("#dayOfWeekTaxTab").addClass("on");
        $("#dayOfWeekTimeTab").removeClass("on");
        $("#dayOfWeekProdClassTab").removeClass("on");
        $("#dayOfWeekCornerTab").removeClass("on");
        $("#dayOfWeekTableTab").removeClass("on");
        $("#dayOfWeekPosTab").removeClass("on");

        $("#dayOfWeekTotalView").hide();
        $("#dayOfWeekDcView").hide();
        $("#dayOfWeekTaxView").show();
        $("#dayOfWeekTimeView").hide();
        $("#dayOfWeekProdClassView").hide();
        $("#dayOfWeekCornerView").hide();
        $("#dayOfWeekTableView").hide();
        $("#dayOfWeekPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayOfWeekTaxCtrl");
        scope.flex.refresh();
    };

    // 시간대별 탭 보이기
    $scope.dayOfWeekTimeShow = function () {
        $("#dayOfWeekTotalTab").removeClass("on");
        $("#dayOfWeekDcTab").removeClass("on");
        $("#dayOfWeekTaxTab").removeClass("on");
        $("#dayOfWeekTimeTab").addClass("on");
        $("#dayOfWeekProdClassTab").removeClass("on");
        $("#dayOfWeekCornerTab").removeClass("on");
        $("#dayOfWeekTableTab").removeClass("on");
        $("#dayOfWeekPosTab").removeClass("on");

        $("#dayOfWeekTotalView").hide();
        $("#dayOfWeekDcView").hide();
        $("#dayOfWeekTaxView").hide();
        $("#dayOfWeekTimeView").show();
        $("#dayOfWeekProdClassView").hide();
        $("#dayOfWeekCornerView").hide();
        $("#dayOfWeekTableView").hide();
        $("#dayOfWeekPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayOfWeekTimeCtrl");
        scope.flex.refresh();
    };

    // 상품분류별 탭 보이기
    $scope.dayOfWeekProdClassShow = function () {
        $("#dayOfWeekTotalTab").removeClass("on");
        $("#dayOfWeekDcTab").removeClass("on");
        $("#dayOfWeekTaxTab").removeClass("on");
        $("#dayOfWeekTimeTab").removeClass("on");
        $("#dayOfWeekProdClassTab").addClass("on");
        $("#dayOfWeekCornerTab").removeClass("on");
        $("#dayOfWeekTableTab").removeClass("on");
        $("#dayOfWeekPosTab").removeClass("on");

        $("#dayOfWeekTotalView").hide();
        $("#dayOfWeekDcView").hide();
        $("#dayOfWeekTaxView").hide();
        $("#dayOfWeekTimeView").hide();
        $("#dayOfWeekProdClassView").show();
        $("#dayOfWeekCornerView").hide();
        $("#dayOfWeekTableView").hide();
        $("#dayOfWeekPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayOfWeekProdClassCtrl");
        scope.flex.refresh();
    };

    // 코너별 탭 보이기
    $scope.dayOfWeekCornerShow = function () {
        $("#dayOfWeekTotalTab").removeClass("on");
        $("#dayOfWeekDcTab").removeClass("on");
        $("#dayOfWeekTaxTab").removeClass("on");
        $("#dayOfWeekTimeTab").removeClass("on");
        $("#dayOfWeekProdClassTab").removeClass("on");
        $("#dayOfWeekCornerTab").addClass("on");
        $("#dayOfWeekTableTab").removeClass("on");
        $("#dayOfWeekPosTab").removeClass("on");

        $("#dayOfWeekTotalView").hide();
        $("#dayOfWeekDcView").hide();
        $("#dayOfWeekTaxView").hide();
        $("#dayOfWeekTimeView").hide();
        $("#dayOfWeekProdClassView").hide();
        $("#dayOfWeekCornerView").show();
        $("#dayOfWeekTableView").hide();
        $("#dayOfWeekPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayOfWeekCornerCtrl");
        scope.flex.refresh();
    };

    // 외식테이블 탭 보이기
    $scope.dayOfWeekTableShow = function () {
        $("#dayOfWeekTotalTab").removeClass("on");
        $("#dayOfWeekDcTab").removeClass("on");
        $("#dayOfWeekTaxTab").removeClass("on");
        $("#dayOfWeekTimeTab").removeClass("on");
        $("#dayOfWeekProdClassTab").removeClass("on");
        $("#dayOfWeekCornerTab").removeClass("on");
        $("#dayOfWeekTableTab").addClass("on");
        $("#dayOfWeekPosTab").removeClass("on");

        $("#dayOfWeekTotalView").hide();
        $("#dayOfWeekDcView").hide();
        $("#dayOfWeekTaxView").hide();
        $("#dayOfWeekTimeView").hide();
        $("#dayOfWeekProdClassView").hide();
        $("#dayOfWeekCornerView").hide();
        $("#dayOfWeekTableView").show();
        $("#dayOfWeekPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayOfWeekTableCtrl");
        scope.flex.refresh();
    };

    // 포스별 탭 보이기
    $scope.dayOfWeekPosShow = function () {
        $("#dayOfWeekTotalTab").removeClass("on");
        $("#dayOfWeekDcTab").removeClass("on");
        $("#dayOfWeekTaxTab").removeClass("on");
        $("#dayOfWeekTimeTab").removeClass("on");
        $("#dayOfWeekProdClassTab").removeClass("on");
        $("#dayOfWeekCornerTab").removeClass("on");
        $("#dayOfWeekTableTab").removeClass("on");
        $("#dayOfWeekPosTab").addClass("on");

        $("#dayOfWeekTotalView").hide();
        $("#dayOfWeekDcView").hide();
        $("#dayOfWeekTaxView").hide();
        $("#dayOfWeekTimeView").hide();
        $("#dayOfWeekProdClassView").hide();
        $("#dayOfWeekCornerView").hide();
        $("#dayOfWeekTableView").hide();
        $("#dayOfWeekPosView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayOfWeekPosCtrl");
        scope.flex.refresh();
    };
}]);