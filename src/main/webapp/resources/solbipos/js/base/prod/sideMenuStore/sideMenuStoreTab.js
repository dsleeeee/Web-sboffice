/****************************************************************
 *
 * 파일명 : sideMenuStoreTab.js
 * 설  명 : 매장별사이드관리 JavaScript
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
        $("#sideMenuClassRegStoreView").show();
        $("#sideMenuProdRegStoreView").hide();
        $("#sideMenuClassStoreView").hide();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdView").hide();
    };

    // 선택분류(적용매장) 탭 보이기
    $scope.sideMenuClassRegStoreShow = function () {
        $("#sideMenuClassRegStoreTab").addClass("on");
        $("#sideMenuProdRegStoreTab").removeClass("on");
        $("#sideMenuClassStoreTab").removeClass("on");
        $("#sideMenuProdStoreTab").removeClass("on");
        $("#sideMenuClassTab").removeClass("on");
        $("#sideMenuProdTab").removeClass("on");

        $("#sideMenuClassRegStoreView").show();
        $("#sideMenuProdRegStoreView").hide();
        $("#sideMenuClassStoreView").hide();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuClassRegStoreCtrl");
        scope.flex.refresh();
    };

    // 선택상품(적용매장) 탭 보이기
    $scope.sideMenuProdRegStoreShow = function () {
        $("#sideMenuClassRegStoreTab").removeClass("on");
        $("#sideMenuProdRegStoreTab").addClass("on");
        $("#sideMenuClassStoreTab").removeClass("on");
        $("#sideMenuProdStoreTab").removeClass("on");
        $("#sideMenuClassTab").removeClass("on");
        $("#sideMenuProdTab").removeClass("on");

        $("#sideMenuClassRegStoreView").hide();
        $("#sideMenuProdRegStoreView").show();
        $("#sideMenuClassStoreView").hide();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuProdRegStoreCtrl");
        scope.flex.refresh();
    };

    // 선택분류(매장별) 탭 보이기
    $scope.sideMenuClassStoreShow = function () {
        $("#sideMenuClassRegStoreTab").removeClass("on");
        $("#sideMenuProdRegStoreTab").removeClass("on");
        $("#sideMenuClassStoreTab").addClass("on");
        $("#sideMenuProdStoreTab").removeClass("on");
        $("#sideMenuClassTab").removeClass("on");
        $("#sideMenuProdTab").removeClass("on");

        $("#sideMenuClassRegStoreView").hide();
        $("#sideMenuProdRegStoreView").hide();
        $("#sideMenuClassStoreView").show();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuClassStoreCtrl");
        scope.flex.refresh();
    };

    // 선택상품(매장별) 탭 보이기
    $scope.sideMenuProdStoreShow = function () {
        $("#sideMenuClassRegStoreTab").removeClass("on");
        $("#sideMenuProdRegStoreTab").removeClass("on");
        $("#sideMenuClassStoreTab").removeClass("on");
        $("#sideMenuProdStoreTab").addClass("on");
        $("#sideMenuClassTab").removeClass("on");
        $("#sideMenuProdTab").removeClass("on");

        $("#sideMenuClassRegStoreView").hide();
        $("#sideMenuProdRegStoreView").hide();
        $("#sideMenuClassStoreView").hide();
        $("#sideMenuProdStoreView").show();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuProdStoreCtrl");
        scope.flex.refresh();
    };

    // 선택분류(선택분류별) 탭 보이기
    $scope.sideMenuClassShow = function () {
        $("#sideMenuClassRegStoreTab").removeClass("on");
        $("#sideMenuProdRegStoreTab").removeClass("on");
        $("#sideMenuClassStoreTab").removeClass("on");
        $("#sideMenuProdStoreTab").removeClass("on");
        $("#sideMenuClassTab").addClass("on");
        $("#sideMenuProdTab").removeClass("on");

        $("#sideMenuClassRegStoreView").hide();
        $("#sideMenuProdRegStoreView").hide();
        $("#sideMenuClassStoreView").hide();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuClassView").show();
        $("#sideMenuProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuClassCtrl");
        scope.flex.refresh();
    };

    // 선택상품(선택상품별) 탭 보이기
    $scope.sideMenuProdShow = function () {
        $("#sideMenuClassRegStoreTab").removeClass("on");
        $("#sideMenuProdRegStoreTab").removeClass("on");
        $("#sideMenuClassStoreTab").removeClass("on");
        $("#sideMenuProdStoreTab").removeClass("on");
        $("#sideMenuClassTab").removeClass("on");
        $("#sideMenuProdTab").addClass("on");

        $("#sideMenuClassRegStoreView").hide();
        $("#sideMenuProdRegStoreView").hide();
        $("#sideMenuClassStoreView").hide();
        $("#sideMenuProdStoreView").hide();
        $("#sideMenuClassView").hide();
        $("#sideMenuProdView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuProdCtrl");
        scope.flex.refresh();
    };

}]);