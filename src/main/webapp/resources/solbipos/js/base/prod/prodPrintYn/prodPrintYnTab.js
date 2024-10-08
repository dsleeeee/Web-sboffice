/****************************************************************
 *
 * 파일명 : prodPrintYnTab.js
 * 설  명 : 출력여부관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.06.28     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('prodPrintYnTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#prodOptionPrintYnView").show();
        $("#sideMenuProdPrintYnView").hide();
    };

    // 옵션관리 탭 보이기
    $scope.prodOptionPrintYnShow = function () {
        $("#prodOptionPrintYnTab").addClass("on");
        $("#sideMenuProdPrintYnTab").removeClass("on");

        $("#prodOptionPrintYnView").show();
        $("#sideMenuProdPrintYnView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodOptionPrintYnCtrl");
        scope.flex.refresh();
    };

    // 사이드메뉴관리 탭 보이기
    $scope.sideMenuProdPrintYnShow = function () {
        $("#prodOptionPrintYnTab").removeClass("on");
        $("#sideMenuProdPrintYnTab").addClass("on");

        $("#prodOptionPrintYnView").hide();
        $("#sideMenuProdPrintYnView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideMenuProdPrintYnCtrl");
        scope.flex.refresh();
    };

}]);