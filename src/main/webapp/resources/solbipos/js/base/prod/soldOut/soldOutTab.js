/****************************************************************
 *
 * 파일명 : soldOutTab.js
 * 설  명 : 품절관리 Tab JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.02.25     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('soldOutTabCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#prodSoldOut").addClass("on");
        $("#sideSoldOut").removeClass("on");
        $("#sideProdSoldOut").removeClass("on");
    };

    $scope.prodSoldOutShow = function () {
        $("#prodSoldOut").addClass("on");
        $("#sideSoldOut").removeClass("on");
        $("#sideProdSoldOut").removeClass("on");

        $("#prodSoldOutView").show();
        $("#sideMenuSoldOutView").hide();
        $("#sideMenuProdSoldOutView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodSoldOutCtrl");
        scope.flex.refresh();
    };

    $scope.sideSoldOutShow = function () {
        $("#prodSoldOut").removeClass("on");
        $("#sideSoldOut").addClass("on");
        $("#sideProdSoldOut").removeClass("on");

        $("#prodSoldOutView").hide();
        $("#sideMenuSoldOutView").show();
        $("#sideMenuProdSoldOutView").hide();

        if(orgnFg != 'HQ'){
            // 선택그룹 조회
            $scope._broadcast("sideMenuSoldOutCtrl");

        }

        var grpGrid = agrid.getScope('sideMenuSoldOutCtrl');
        grpGrid.flex.refresh();
        var classGrid = agrid.getScope('sideMenuSelectClassCtrl');
        classGrid.flex.refresh();
        var prodGrid = agrid.getScope('sideMenuSelectProdCtrl');
        prodGrid.flex.refresh();

    };

    $scope.sideProdSoldOutShow = function () {
        $("#prodSoldOut").removeClass("on");
        $("#sideSoldOut").removeClass("on");
        $("#sideProdSoldOut").addClass("on");

        $("#prodSoldOutView").hide();
        $("#sideMenuSoldOutView").hide();
        $("#sideMenuProdSoldOutView").show();

        var scope = agrid.getScope('sideMenuProdSoldOutCtrl');
        scope.flex.refresh();

    };

}]);