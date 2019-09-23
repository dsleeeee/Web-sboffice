/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeStatusCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#statusStoreView").show();
        $("#statusAgencyView").hide();
        $("#statusVanView").hide();
        $("#statusPosInstallView").hide();
        $("#statusApprListView").hide();
    };


    // 일별종합별 탭 보이기
    $scope.statusStoreShow = function () {
        $("#statusStoreTab").addClass("on");
        $("#statusAgencyTab").removeClass("on");
        $("#statusVanTab").removeClass("on");
        $("#statusPosInstallTab").removeClass("on");
        $("#statusApprListTab").removeClass("on");

        $("#statusStoreView").show();
        $("#statusAgencyView").hide();
        $("#statusVanView").hide();
        $("#statusPosInstallView").hide();
        $("#statusApprListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("statusStoreCtrl");
        scope.flex.refresh();
    };


    // 할인구분별 탭 보이기
    $scope.statusAgencyShow = function () {
        $("#statusStoreTab").removeClass("on");
        $("#statusAgencyTab").addClass("on");
        $("#statusVanTab").removeClass("on");
        $("#statusPosInstallTab").removeClass("on");
        $("#statusApprListTab").removeClass("on");

        $("#statusStoreView").hide();
        $("#statusAgencyView").show();
        $("#statusVanView").hide();
        $("#statusPosInstallView").hide();
        $("#statusApprListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("statusAgencyCtrl");
        scope.flex.refresh();
    };


    // 과면세별 탭 보이기
    $scope.statusVanShow = function () {
        $("#statusStoreTab").removeClass("on");
        $("#statusAgencyTab").removeClass("on");
        $("#statusVanTab").addClass("on");
        $("#statusPosInstallTab").removeClass("on");
        $("#statusApprListTab").removeClass("on");

        $("#statusStoreView").hide();
        $("#statusAgencyView").hide();
        $("#statusVanView").show();
        $("#statusPosInstallView").hide();
        $("#statusApprListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("statusVanCtrl");
        scope.flex.refresh();
    };


    // 시간대별 탭 보이기
    $scope.statusPosInstallShow = function () {
        $("#statusStoreTab").removeClass("on");
        $("#statusAgencyTab").removeClass("on");
        $("#statusVanTab").removeClass("on");
        $("#statusPosInstallTab").addClass("on");
        $("#statusApprListTab").removeClass("on");

        $("#statusStoreView").hide();
        $("#statusAgencyView").hide();
        $("#statusVanView").hide();
        $("#statusPosInstallView").show();
        $("#statusApprListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("statusPosInstallCtrl");
        scope.flex.refresh();
    };


    // 상품분류별 탭 보이기
    $scope.statusApprListShow = function () {
        $("#statusStoreTab").removeClass("on");
        $("#statusAgencyTab").removeClass("on");
        $("#statusVanTab").removeClass("on");
        $("#statusPosInstallTab").removeClass("on");
        $("#statusApprListTab").addClass("on");

        $("#statusStoreView").hide();
        $("#statusAgencyView").hide();
        $("#statusVanView").hide();
        $("#statusPosInstallView").hide();
        $("#statusApprListView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("statusApprListCtrl");
        scope.flex.refresh();
    };

}]);
