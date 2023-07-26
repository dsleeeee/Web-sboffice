/****************************************************************
 *
 * 파일명 : storeProdPrintYnTab.js
 * 설  명 : 매장별출력여부관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.24     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeProdPrintYnTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#storeProdOptionPrintYnView").show();
        $("#storeSideMenuProdPrintYnView").hide();
    };

    // 옵션관리 탭 보이기
    $scope.storeProdOptionPrintYnShow = function () {
        $("#storeProdOptionPrintYnTab").addClass("on");
        $("#storeSideMenuProdPrintYnTab").removeClass("on");

        $("#storeProdOptionPrintYnView").show();
        $("#storeSideMenuProdPrintYnView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeProdOptionPrintYnCtrl");
        scope.flex.refresh();
    };

    // 사이드메뉴관리 탭 보이기
    $scope.storeSideMenuProdPrintYnShow = function () {
        $("#storeProdOptionPrintYnTab").removeClass("on");
        $("#storeSideMenuProdPrintYnTab").addClass("on");

        $("#storeProdOptionPrintYnView").hide();
        $("#storeSideMenuProdPrintYnView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeSideMenuProdPrintYnCtrl");
        scope.flex.refresh();
    };

}]);