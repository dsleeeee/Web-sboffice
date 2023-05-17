/**
 * get application
 */
var app = agrid.getApp();

app.controller('orderStatusTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#orderStatusView").show();
        $("#orderCancelView").hide();
    };

    // 주문현황 탭 보이기
    $scope.orderStatusTabShow = function () {
        $("#orderStatusTab").addClass("on");
        $("#orderCancelTab").removeClass("on");

        $("#orderStatusView").show();
        $("#orderCancelView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orderStatusCtrl");
        console.log(scope);
        scope.flex.refresh();
    };

    // 주문취소 탭 보이기
    $scope.orderCancelTabShow = function () {
        $("#orderStatusTab").removeClass("on");
        $("#orderCancelTab").addClass("on");

        $("#orderStatusView").hide();
        $("#orderCancelView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orderCancelCtrl");
        console.log(scope);
        scope.flex.refresh();

        var scope1 = agrid.getScope("orderCancelSrch1Ctrl");
        console.log(scope1);
        scope1.flex.refresh();

        var scope2 = agrid.getScope("orderCancelSrch2Ctrl");
        console.log(scope2);
        scope2.flex.refresh();

        var scope3 = agrid.getScope("orderCancelSrch3Ctrl");
        console.log(scope3);
        scope3.flex.refresh();
    };

}]);