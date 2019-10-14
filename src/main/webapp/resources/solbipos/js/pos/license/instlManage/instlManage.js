var app = agrid.getApp();

app.controller('instlManageCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#agencyListView").show();
        $("#instlListView").hide();
    };

    // 업체현황 탭 보이기
    $scope.agencyListShow = function () {
        $("#agencyListTab").addClass("on");
        $("#instlListTab").removeClass("on");

        $("#agencyListView").show();
        $("#instlListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("agencyListCtrl");
        scope.flex.refresh();
    };

    // 설치현황 탭 보이기
    $scope.instlListShow = function () {
        $("#agencyListTab").removeClass("on");
        $("#instlListTab").addClass("on");

        $("#agencyListView").hide();
        $("#instlListView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("instlListCtrl");
        scope.flex.refresh();
    };

}]);