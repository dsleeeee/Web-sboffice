/**
 * get application
 */
var app = agrid.getApp();

app.controller('kioskKeyMapManageCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#kioskKeyMapRegistView").show();
        $("#kioskKeyMapCopyView").hide();
    };

    // 키오스크키맵등록 탭 보이기
    $scope.kioskKeyMapRegistShow = function () {
        $("#kioskKeyMapRegistTab").addClass("on");
        $("#kioskKeyMapCopyTab").removeClass("on");

        $("#kioskKeyMapRegistView").show();
        $("#kioskKeyMapCopyView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("kioskKeyMapRegistCtrl");
        scope.flex.refresh();
    };

    // 키오스크키맵복사 탭 보이기
    $scope.kioskKeyMapCopyShow = function () {
        $("#kioskKeyMapRegistTab").removeClass("on");
        $("#kioskKeyMapCopyTab").addClass("on");

        $("#kioskKeyMapRegistView").hide();
        $("#kioskKeyMapCopyView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("kiosKeyMapCopyCtrl");
        scope.flex.refresh();
    };
}]);