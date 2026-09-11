/****************************************************************
 *
 * 파일명 : orderEmpBensonTab.js
 * 설  명 : 벤슨 > 매출현황2 > 주문자현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.10     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('orderEmpBensonTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#orderEmpBensonPeriodView").show();
        $("#orderEmpBensonDayView").hide();
    };

    // 기간별 탭 보이기
    $scope.orderEmpBensonPeriodShow = function () {
        $("#orderEmpBensonPeriodTab").addClass("on");
        $("#orderEmpBensonDayTab").removeClass("on");

        $("#orderEmpBensonPeriodView").show();
        $("#orderEmpBensonDayView").hide();
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orderEmpBensonPeriodCtrl");
        scope.flex.refresh();
        var scope1 = agrid.getScope("orderEmpBensonPeriodMainCtrl");
        scope1.flex.refresh();
        var scope2 = agrid.getScope("orderEmpBensonPeriodDtlCtrl");
        scope2.flex.refresh();
    };

    // 일자별 탭 보이기
    $scope.orderEmpBensonDayShow = function () {
        $("#orderEmpBensonPeriodTab").removeClass("on");
        $("#orderEmpBensonDayTab").addClass("on");

        $("#orderEmpBensonPeriodView").hide();
        $("#orderEmpBensonDayView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("orderEmpBensonDayCtrl");
        scope.flex.refresh();
    };

}]);
