/****************************************************************
 *
 * 파일명 : salePerfCompareBenson.js
 * 설  명 : 벤슨 > 매출분석 > 대비기간 매출실적 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.09     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('salePerfCompareBensonCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#salePerfCompareBensonAllView").show();
        $("#salePerfCompareBensonStoreView").hide();
    };

    // 영수증별 반품현황 탭 보이기
    $scope.salePerfCompareBensonAllTabShow = function () {
        $("#salePerfCompareBensonAll").addClass("on");
        $("#salePerfCompareBensonStore").removeClass("on");

        $("#salePerfCompareBensonAllView").show();
        $("#salePerfCompareBensonStoreView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("salePerfCompareBensonAllCtrl");
        scope.flex.refresh();
        // angular 그리드 hide 시 깨지므로 refresh()
        scope = agrid.getScope("salePerfCompareBensonAllDtlCtrl");
        scope.flex.refresh();
    };

    // 반품현황 탭 보이기
    $scope.salePerfCompareBensonStoreTabShow = function () {
        $("#salePerfCompareBensonAll").removeClass("on");
        $("#salePerfCompareBensonStore").addClass("on");

        $("#salePerfCompareBensonAllView").hide();
        $("#salePerfCompareBensonStoreView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("salePerfCompareBensonStoreCtrl");
        scope.flex.refresh();
    };

}]);