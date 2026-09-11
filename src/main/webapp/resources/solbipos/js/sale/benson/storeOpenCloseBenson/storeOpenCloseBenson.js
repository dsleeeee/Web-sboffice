/****************************************************************
 *
 * 파일명 : storeOpenCloseBenson.js
 * 설  명 : (벤슨) 매장분석 > 매장 오픈/마감 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.09     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 탭 영역
 */
app.controller('storeOpenCloseBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#storeOpenCloseBensonDayView").show();
        $("#storeOpenCloseBensonMonthView").hide();
    };

    // 일별 탭 보이기
    $scope.storeOpenCloseBensonDayShow = function () {
        $("#storeOpenCloseBensonDayTab").addClass("on");
        $("#storeOpenCloseBensonMonthTab").removeClass("on");

        $("#storeOpenCloseBensonDayView").show();
        $("#storeOpenCloseBensonMonthView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeOpenCloseBensonDayTimeCtrl");
        scope.flex.refresh();
        var scope1 = agrid.getScope("storeOpenCloseBensonDayCtrl");
        scope1.flex.refresh();
        var scope2 = agrid.getScope("storeOpenCloseBensonDayDtlCtrl");
        scope2.flex.refresh();
    };

    // 월별 탭 보이기
    $scope.storeOpenCloseBensonMonthShow = function () {
        $("#storeOpenCloseBensonDayTab").removeClass("on");
        $("#storeOpenCloseBensonMonthTab").addClass("on");

        $("#storeOpenCloseBensonDayView").hide();
        $("#storeOpenCloseBensonMonthView").show();

        var scope = agrid.getScope("storeOpenCloseBensonMonthTimeCtrl");
        // scope.flex.refresh();
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope1 = agrid.getScope("storeOpenCloseBensonMonthCtrl");
        scope1.flex.refresh();
        var scope2 = agrid.getScope("storeOpenCloseBensonMonthDtlCtrl");
        scope2.flex.refresh();
    };

}]);
