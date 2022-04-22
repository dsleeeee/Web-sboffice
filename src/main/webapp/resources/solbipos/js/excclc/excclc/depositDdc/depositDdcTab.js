/**
 * get application
 */
var app = agrid.getApp();

app.controller('depositDdcTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#storeTotalView").show();
    };

    // 키오스크키맵등록 탭 보이기
    $scope.storeTotalTabShow = function () {
        $("#storeTotalTab").addClass("on");

        $("#storeTotalView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeTotalCtrl");
        scope.flex.refresh();
    };

}]);