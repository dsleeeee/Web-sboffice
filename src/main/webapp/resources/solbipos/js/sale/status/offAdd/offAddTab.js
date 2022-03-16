/****************************************************************
 *
 * 파일명 : offAddTab.js
 * 설  명 : SMS전송분석(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.11     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('offAddCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#offAddDayView").show();
        $("#offAddMonthView").hide();
        $("#offAddProdView").hide();
    };

    // 일별 탭 보이기
    $scope.offAddDayShow = function () {
        $("#offAddDayTab").addClass("on");
        $("#offAddMonthTab").removeClass("on");
        $("#offAddProdTab").removeClass("on");

        $("#offAddDayView").show();
        $("#offAddMonthView").hide();
        $("#offAddProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("offAddDayCtrl");
        scope.flex.refresh();
    };

    // 월별 탭 보이기
    $scope.offAddMonthShow = function () {
        $("#offAddDayTab").removeClass("on");
        $("#offAddMonthTab").addClass("on");
        $("#offAddProdTab").removeClass("on");

        $("#offAddDayView").hide();
        $("#offAddMonthView").show();
        $("#offAddProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("offAddMonthCtrl");
        scope.flex.refresh();
        var scopeDtl = agrid.getScope("offAddMonthDetailCtrl");
        scopeDtl.flex.refresh();

    };

    // 상품별 탭 보이기
    $scope.offAddProdShow = function () {
        $("#offAddDayTab").removeClass("on");
        $("#offAddMonthTab").removeClass("on");
        $("#offAddProdTab").addClass("on");

        $("#offAddDayView").hide();
        $("#offAddMonthView").hide();
        $("#offAddProdView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("offAddProdCtrl");
        scope.flex.refresh();
    };
}]);