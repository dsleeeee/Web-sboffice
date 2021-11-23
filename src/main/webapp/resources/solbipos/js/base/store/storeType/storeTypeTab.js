/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeTypeTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#storeTypeView").show();
        $("#menuGroupView").hide();
        $("#storeTypeChgHistView").hide();
        $("#menuGroupChgHistView").hide();
    };

    // 매장타입 탭 보이기
    $scope.storeTypeShow = function () {
        $("#storeTypeTab").addClass("on");
        $("#menuGroupTab").removeClass("on");
        $("#storeTypeChgHistTab").removeClass("on");
        $("#menuGroupChgHistTab").removeClass("on");

        $("#storeTypeView").show();
        $("#menuGroupView").hide();
        $("#storeTypeChgHistView").hide();
        $("#menuGroupChgHistView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeTypeCtrl");
        scope.flex.refresh();
    };
    
    // 메뉴그룹 탭 보이기
    $scope.menuGroupShow = function () {
        $("#storeTypeTab").removeClass("on");
        $("#menuGroupTab").addClass("on");
        $("#storeTypeChgHistTab").removeClass("on");
        $("#menuGroupChgHistTab").removeClass("on");

        $("#storeTypeView").hide();
        $("#menuGroupView").show();
        $("#storeTypeChgHistView").hide();
        $("#menuGroupChgHistView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("menuGroupCtrl");
        scope.flex.refresh();
    };

    // 매장타입변경이력 탭 보이기
    $scope.storeTypeChgHistShow = function () {
        $("#storeTypeTab").removeClass("on");
        $("#menuGroupTab").removeClass("on");
        $("#storeTypeChgHistTab").addClass("on");
        $("#menuGroupChgHistTab").removeClass("on");

        $("#storeTypeView").hide();
        $("#menuGroupView").hide();
        $("#storeTypeChgHistView").show();
        $("#menuGroupChgHistView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeTypeChgHistCtrl");
        scope.flex.refresh();
    };

    // 메뉴그룹변경이력 탭 보이기
    $scope.menuGroupChgHistShow = function () {
        $("#storeTypeTab").removeClass("on");
        $("#menuGroupTab").removeClass("on");
        $("#storeTypeChgHistTab").removeClass("on");
        $("#menuGroupChgHistTab").addClass("on");

        $("#storeTypeView").hide();
        $("#menuGroupView").hide();
        $("#storeTypeChgHistView").hide();
        $("#menuGroupChgHistView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("menuGroupChgHistCtrl");
        scope.flex.refresh();
    };

}]);