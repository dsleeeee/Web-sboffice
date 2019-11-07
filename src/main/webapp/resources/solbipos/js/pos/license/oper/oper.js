var app = agrid.getApp();

app.controller('operCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#saleStoreListView").show();
        $("#agencyAuthListView").hide();
    };

    // 업체현황 탭 보이기
    $scope.saleStoreListShow = function () {
        $("#saleStoreListTab").addClass("on");
        $("#agencyAuthListTab").removeClass("on");

        $("#saleStoreListView").show();
        $("#agencyAuthListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("saleStoreListCtrl");
        scope.flex.refresh();
    };

    // 설치현황 탭 보이기
    $scope.agencyAuthListShow = function () {
        $("#saleStoreListTab").removeClass("on");
        $("#agencyAuthListTab").addClass("on");

        $("#saleStoreListView").hide();
        $("#agencyAuthListView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("agencyAuthListCtrl");
        scope.flex.refresh();
    };

}]);