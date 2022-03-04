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
    };

    // 판매상품여부, 포인트적립여부, 매핑상품코드, 가격관리구분 변경 탭 보이기
    $scope.prodSoldOutShow = function () {
        $("#prodSoldOut").addClass("on");
        $("#sideSoldOut").removeClass("on");

        $("#prodSoldOutView").show();
        $("#sideMenuSoldOutView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodSoldOutCtrl");
        scope.flex.refresh();
    };

    // 브랜드, 상품분류 변경 레이어 탭 보이기
    $scope.sideSoldOutShow = function () {
        $("#prodSoldOut").removeClass("on");
        $("#sideSoldOut").addClass("on");

        $("#prodSoldOutView").hide();
        $("#sideMenuSoldOutView").show();

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

}]);