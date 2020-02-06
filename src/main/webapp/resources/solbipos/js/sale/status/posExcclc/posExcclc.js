/**
 * get application
 */
var app = agrid.getApp();

app.controller('posExcclcMainCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#posExcclcView").show();
    };

    // 일자별 탭 보이기
    $scope.posExcclcTabShow = function () {
        $("#posExcclcTab").addClass("on");

        $("#posExcclcView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("posExcclcCtrl");
        console.log(scope);
        scope.flex.refresh();
    };
}]);