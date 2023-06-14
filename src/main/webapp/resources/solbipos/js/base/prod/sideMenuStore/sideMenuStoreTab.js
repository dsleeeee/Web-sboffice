/****************************************************************
 *
 * 파일명 : sideMenuStoreTab.js
 * 설  명 : 원산지관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.06.07     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('sideMenuStoreTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#sideMenuClassStoreView").show();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuProdView").hide();
    };

    // 선택분류(매장별) 탭 보이기
    $scope.sideMenuClassStoreShow = function () {
        $("#sideMenuClassStoreTab").addClass("on");
        $("#sideMenuClassTab").removeClass("on");
        $("#sideMenuProdStoreTab").removeClass("on");
        $("#sideMenuProdTab").removeClass("on");

        $("#sideMenuClassStoreView").show();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuClassStoreCtrl");
        scope.flex.refresh();
    };

    // 선택분류(선택분류별) 탭 보이기
    $scope.sideMenuClassShow = function () {
        $("#sideMenuClassStoreTab").removeClass("on");
        $("#sideMenuClassTab").addClass("on");
        $("#sideMenuProdStoreTab").removeClass("on");
        $("#sideMenuProdTab").removeClass("on");

        $("#sideMenuClassStoreView").hide();
        $("#sideMenuClassView").show();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuClassCtrl");
        scope.flex.refresh();
    };

    // 선택상품(매장별) 탭 보이기
    $scope.sideMenuProdStoreShow = function () {
        $("#sideMenuClassStoreTab").removeClass("on");
        $("#sideMenuClassTab").removeClass("on");
        $("#sideMenuProdStoreTab").addClass("on");
        $("#sideMenuProdTab").removeClass("on");

        $("#sideMenuClassStoreView").hide();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdStoreView").show();
        $("#sideMenuProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuProdStoreCtrl");
        scope.flex.refresh();
    };

    // 선택상품(선택상품별) 탭 보이기
    $scope.sideMenuProdShow = function () {
        $("#sideMenuClassStoreTab").removeClass("on");
        $("#sideMenuClassTab").removeClass("on");
        $("#sideMenuProdStoreTab").removeClass("on");
        $("#sideMenuProdTab").addClass("on");

        $("#sideMenuClassStoreView").hide();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuProdView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuProdCtrl");
        scope.flex.refresh();
    };

}]);