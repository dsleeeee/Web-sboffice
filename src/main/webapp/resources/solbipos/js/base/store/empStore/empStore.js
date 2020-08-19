var app = agrid.getApp();

app.controller('empStoreCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#empStoreEmpView").show();
        $("#empStoreStoreView").hide();
    };

    // 사원별 탭 보이기
    $scope.empStoreEmpShow = function () {
        $("#empStoreEmpTab").addClass("on");
        $("#empStoreStoreTab").removeClass("on");

        $("#empStoreEmpView").show();
        $("#empStoreStoreView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empStoreEmpCtrl");
        scope.flex.refresh();
        var scope2 = agrid.getScope("empManageStoreCtrl");
        scope2.flex.refresh();
        var scope3 = agrid.getScope("empNoManageStoreCtrl");
        scope3.flex.refresh();
    };

    // 매장별 탭 보이기
    $scope.empStoreStoreShow = function () {
        $("#empStoreEmpTab").removeClass("on");
        $("#empStoreStoreTab").addClass("on");

        $("#empStoreEmpView").hide();
        $("#empStoreStoreView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empStoreStoreCtrl");
        scope.flex.refresh();
        var scope2 = agrid.getScope("storeManageEmpCtrl");
        scope2.flex.refresh();
        var scope3 = agrid.getScope("storeNoManageEmpCtrl");
        scope3.flex.refresh();
    };

}]);