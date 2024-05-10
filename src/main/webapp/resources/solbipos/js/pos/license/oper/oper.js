var app = agrid.getApp();

app.controller('operCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#runSaleStoreListView").show();
        $("#saleStoreListView").hide();
        $("#agencyAuthListView").hide();
        $("#operStoreListView").hide();
    };

    // 러닝매장현황 탭 보이기
    $scope.runSaleStoreListShow = function () {
        $("#runSaleStoreListTab").addClass("on");
        $("#saleStoreListTab").removeClass("on");
        $("#agencyAuthListTab").removeClass("on");
        $("#operStoreListTab").removeClass("on");

        $("#runSaleStoreListView").show();
        $("#saleStoreListView").hide();
        $("#agencyAuthListView").hide();
        $("#operStoreListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("runSaleStoreListCtrl");
        scope.flex.refresh();
    };

    // 업체현황 탭 보이기
    $scope.saleStoreListShow = function () {
        $("#runSaleStoreListTab").removeClass("on");
        $("#saleStoreListTab").addClass("on");
        $("#agencyAuthListTab").removeClass("on");
        $("#operStoreListTab").removeClass("on");

        $("#runSaleStoreListView").hide();
        $("#saleStoreListView").show();
        $("#agencyAuthListView").hide();
        $("#operStoreListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("saleStoreListCtrl");
        scope.flex.refresh();
    };

    // 설치현황 탭 보이기
    $scope.agencyAuthListShow = function () {
        $("#runSaleStoreListTab").removeClass("on");
        $("#saleStoreListTab").removeClass("on");
        $("#agencyAuthListTab").addClass("on");
        $("#operStoreListTab").removeClass("on");

        $("#runSaleStoreListView").hide();
        $("#saleStoreListView").hide();
        $("#agencyAuthListView").show();
        $("#operStoreListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("agencyAuthListCtrl");
        scope.flex.refresh();
    };

    // 운영현황 탭 보이기
    $scope.operStoreListShow = function () {
        $("#runSaleStoreListTab").removeClass("on");
        $("#saleStoreListTab").removeClass("on");
        $("#agencyAuthListTab").removeClass("on");
        $("#operStoreListTab").addClass("on");

        $("#runSaleStoreListView").hide();
        $("#saleStoreListView").hide();
        $("#agencyAuthListView").hide();
        $("#operStoreListView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("operStoreListCtrl");
        scope.flex.refresh();
    };

}]);