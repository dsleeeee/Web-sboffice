/**
 * get application
 */
var app = agrid.getApp();

app.controller('accountManageTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#longTermUnusedView").show();
    };

    // 장기미사용 탭 보이기
    $scope.longTermUnusedShow = function () {
        $("#longTermUnusedTab").addClass("on");

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("longTermUnusedCtrl");
        scope.flex.refresh();
    };

}]);