/****************************************************************
 *
 * 파일명 : storeOpenClose.js
 * 설  명 : 맘스터치 > 점포매출 > 매장 오픈/마감 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.11     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 탭 영역
 */
app.controller('storeOpenCloseCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#storeOpenCloseDayView").show();
        $("#storeOpenCloseMonthView").hide();
    };

    // 일별 탭 보이기
    $scope.storeOpenCloseDayShow = function () {
        $("#storeOpenCloseDayTab").addClass("on");
        $("#storeOpenCloseMonthTab").removeClass("on");

        $("#storeOpenCloseDayView").show();
        $("#storeOpenCloseMonthView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeOpenCloseDayTimeCtrl");
        scope.flex.refresh();
        var scope1 = agrid.getScope("storeOpenCloseDayCtrl");
        scope1.flex.refresh();
        var scope2 = agrid.getScope("storeOpenCloseDayDtlCtrl");
        scope2.flex.refresh();
    };

    // 월별 탭 보이기
    $scope.storeOpenCloseMonthShow = function () {
        $("#storeOpenCloseDayTab").removeClass("on");
        $("#storeOpenCloseMonthTab").addClass("on");

        $("#storeOpenCloseDayView").hide();
        $("#storeOpenCloseMonthView").show();

        var scope = agrid.getScope("storeOpenCloseMonthTimeCtrl");
        // scope.flex.refresh();
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope1 = agrid.getScope("storeOpenCloseMonthCtrl");
        scope1.flex.refresh();
        var scope2 = agrid.getScope("storeOpenCloseMonthDtlCtrl");
        scope2.flex.refresh();
    };

}]);