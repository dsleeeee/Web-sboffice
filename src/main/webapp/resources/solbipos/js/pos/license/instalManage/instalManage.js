/**
 * get application
 */
var app = agrid.getApp();

app.controller('instalManageCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#agencyListView").show();
        $("#instalListView").hide();
    };

    // 업체현황 탭 보이기
    $scope.agencyListShow = function () {
        $("#agencyListTab").addClass("on");
        $("#instalListTab").removeClass("on");

        $("#agencyListView").show();
        $("#instalListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("agencyListCtrl");
        scope.flex.refresh();
    };

    // 설치현황 탭 보이기
    $scope.instalListShow = function () {
        $("#agencyListTab").removeClass("on");
        $("#instalListTab").addClass("on");

        $("#agencyListView").hide();
        $("#instalListView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("instalListCtrl");
        scope.flex.refresh();
    };

}]);