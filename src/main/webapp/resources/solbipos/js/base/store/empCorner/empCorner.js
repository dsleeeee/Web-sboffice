var app = agrid.getApp();

app.controller('empCornerCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#empCornerEmpView").show();
        $("#empCornerCornerView").hide();
    };

    // 사원별 탭 보이기
    $scope.empCornerEmpShow = function () {
        $("#empCornerEmpTab").addClass("on");
        $("#empCornerCornerTab").removeClass("on");

        $("#empCornerEmpView").show();
        $("#empCornerCornerView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empCornerEmpCtrl");
        scope.flex.refresh();
        var scope2 = agrid.getScope("empManageCornerCtrl");
        scope2.flex.refresh();
        var scope3 = agrid.getScope("empNoManageCornerCtrl");
        scope3.flex.refresh();
    };

    // 코너별 탭 보이기
    $scope.empCornerCornerShow = function () {
        $("#empCornerEmpTab").removeClass("on");
        $("#empCornerCornerTab").addClass("on");

        $("#empCornerEmpView").hide();
        $("#empCornerCornerView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("empCornerCornerCtrl");
        scope.flex.refresh();
        var scope2 = agrid.getScope("cornerManageEmpCtrl");
        scope2.flex.refresh();
        var scope3 = agrid.getScope("cornerNoManageEmpCtrl");
        scope3.flex.refresh();
    };

}]);