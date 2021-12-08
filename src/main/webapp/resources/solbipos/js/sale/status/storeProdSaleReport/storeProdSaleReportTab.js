/****************************************************************
 *
 * 파일명 : storeProdSaleReportTab.js
 * 설  명 : 기간별 매장-상품 매출 다운로드(고봉민) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.02     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeProdSaleReportTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#storeProdSaleReportView").show();
        $("#branchAreaView").hide();
        $("#areaStoreMappingView").hide();
    };

    // 기간별 매장-상품 매출 다운로드 탭 보이기
    $scope.storeProdSaleReportShow = function () {
        $("#storeProdSaleReportTab").addClass("on");
        $("#branchAreaTab").removeClass("on");
        $("#areaStoreMappingTab").removeClass("on");

        $("#storeProdSaleReportView").show();
        $("#branchAreaView").hide();
        $("#areaStoreMappingView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeProdSaleReportCtrl");
        scope.flex.refresh();
    };

    // 지사-지역관리 탭 보이기
    $scope.branchAreaShow = function () {
        $("#storeProdSaleReportTab").removeClass("on");
        $("#branchAreaTab").addClass("on");
        $("#areaStoreMappingTab").removeClass("on");

        $("#storeProdSaleReportView").hide();
        $("#branchAreaView").show();
        $("#areaStoreMappingView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("branchAreaCtrl");
        scope.flex.refresh();
        var scope1 = agrid.getScope("branchAreaDetailCtrl");
        scope1.flex.refresh();
    };

    // 지역-매장관리 탭 보이기
    $scope.areaStoreMappingShow = function () {
        $("#storeProdSaleReportTab").removeClass("on");
        $("#branchAreaTab").removeClass("on");
        $("#areaStoreMappingTab").addClass("on");

        $("#storeProdSaleReportView").hide();
        $("#branchAreaView").hide();
        $("#areaStoreMappingView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("areaStoreMappingCtrl");
        scope.flex.refresh();
        var scope1 = agrid.getScope("areaStoreMappingDetailCtrl");
        scope1.flex.refresh();
        var scope2 = agrid.getScope("areaStoreMappingStoreCtrl");
        scope2.flex.refresh();
    };
}]);