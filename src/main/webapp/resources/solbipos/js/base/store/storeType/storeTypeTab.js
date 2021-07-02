/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeTypeTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#storeTypeView").show();
        $("#menuGroupView").hide();
    };

    // 매장타입 탭 보이기
    $scope.storeTypeShow = function () {
        $("#storeTypeTab").addClass("on");
        $("#menuGroupTab").removeClass("on");

        $("#storeTypeView").show();
        $("#menuGroupView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeTypeCtrl");
        scope.flex.refresh();
    };
    
    // 메뉴그룹 탭 보이기
    $scope.menuGroupShow = function () {
        $("#storeTypeTab").removeClass("on");
        $("#menuGroupTab").addClass("on");

        $("#storeTypeView").hide();
        $("#menuGroupView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("menuGroupCtrl");
        scope.flex.refresh();
    };

}]);