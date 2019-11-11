var app = agrid.getApp();

app.controller('instlManageCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#instlRequestListView").show();
        $("#agencyListView").hide();
        $("#instlListView").hide();
    };

    // 설치요청 탭 보이기
    $scope.instlRequestListShow = function () {
        $("#instlRequestListTab").addClass("on");
        $("#agencyListTab").removeClass("on");
        $("#instlListTab").removeClass("on");

        $("#instlRequestListView").show();
        $("#agencyListView").hide();
        $("#instlListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("instlRequestListCtrl");
        scope.flex.refresh();
    };

    // 업체현황 탭 보이기
    $scope.agencyListShow = function () {
        $("#instlRequestListTab").removeClass("on");
        $("#agencyListTab").addClass("on");
        $("#instlListTab").removeClass("on");

        $("#instlRequestListView").hide();
        $("#agencyListView").show();
        $("#instlListView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("agencyListCtrl");
        scope.flex.refresh();
    };

    // 설치현황 탭 보이기
    $scope.instlListShow = function () {
        $("#instlRequestListTab").removeClass("on");
        $("#agencyListTab").removeClass("on");
        $("#instlListTab").addClass("on");

        $("#instlRequestListView").hide();
        $("#agencyListView").hide();
        $("#instlListView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("instlListCtrl");
        scope.flex.refresh();
    };

}]);