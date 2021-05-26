/**
 * get application
 */
var app = agrid.getApp();

app.controller('dlvrFgCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#dlvrFgProdShow").show();
    };

    // 상품별 탭 보이기
    $scope.dlvrFgProdShow = function () {
        $("#dlvrFgProdTab").addClass("on");
        $("#dlvrFgProdView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dlvrFgProdCtrl");
        scope.flex.refresh();
    };
}]);