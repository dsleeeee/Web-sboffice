/****************************************************************
 *
 * 파일명 : orderEmpTab.js
 * 설  명 : 주문자현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.10     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('orderEmpTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#orderEmpPeriodView").show();
        $("#orderEmpDayView").hide();
    };

    // 기간별 탭 보이기
    $scope.orderEmpPeriodShow = function () {
        $("#orderEmpPeriodTab").addClass("on");
        $("#orderEmpDayTab").removeClass("on");

        $("#orderEmpPeriodView").show();
        $("#orderEmpDayView").hide();
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orderEmpPeriodCtrl");
        scope.flex.refresh();
        var scope1 = agrid.getScope("orderEmpPeriodMainCtrl");
        scope1.flex.refresh();
        var scope2 = agrid.getScope("orderEmpPeriodDtlCtrl");
        scope2.flex.refresh();
    };

    // 일자별 탭 보이기
    $scope.orderEmpDayShow = function () {
        $("#orderEmpPeriodTab").removeClass("on");
        $("#orderEmpDayTab").addClass("on");

        $("#orderEmpPeriodView").hide();
        $("#orderEmpDayView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orderEmpDayCtrl");
        scope.flex.refresh();
    };

}]);