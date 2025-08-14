/****************************************************************
 *
 * 파일명 : salePerfCompare.js
 * 설  명 : 미스터피자 > 마케팅조회 > 매출실적비교 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.07     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('salePerfCompareCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#salePerfCompareAllView").show();
        $("#salePerfCompareStoreView").hide();
    };

    // 영수증별 반품현황 탭 보이기
    $scope.salePerfCompareAllTabShow = function () {
        $("#salePerfCompareAll").addClass("on");
        $("#salePerfCompareStore").removeClass("on");

        $("#salePerfCompareAllView").show();
        $("#salePerfCompareStoreView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("salePerfCompareAllCtrl");
        scope.flex.refresh();
        // angular 그리드 hide 시 깨지므로 refresh()
        scope = agrid.getScope("salePerfCompareAllDtlCtrl");
        scope.flex.refresh();
    };

    // 반품현황 탭 보이기
    $scope.salePerfCompareStoreTabShow = function () {
        $("#salePerfCompareAll").removeClass("on");
        $("#salePerfCompareStore").addClass("on");

        $("#salePerfCompareAllView").hide();
        $("#salePerfCompareStoreView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("salePerfCompareStoreCtrl");
        scope.flex.refresh();
    };

}]);