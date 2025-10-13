/****************************************************************
 *
 * 파일명 : storeKioskSideOptionTab.js
 * 설  명 : 다국어관리(키오스크/사이드/옵션)(매장) 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.19     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeKioskSideOptionTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#storeKioskCategoryView").show();
        $("#storeKioskMClsView").hide();
        $("#storeSideSdselGrpView").hide();
        $("#storeSideSdselClassView").hide();
        $("#storeOptionGrpView").hide();
        $("#storeOptionValView").hide();
    };

    // 키오스크(카테고리) 탭 보이기
    $scope.storeKioskCategoryShow = function () {
        $("#storeKioskCategoryTab").addClass("on");
        $("#storeKioskMClsTab").removeClass("on");
        $("#storeSideSdselGrpTab").removeClass("on");
        $("#storeSideSdselClassTab").removeClass("on");
        $("#storeOptionGrpTab").removeClass("on");
        $("#storeOptionValTab").removeClass("on");

        $("#storeKioskCategoryView").show();
        $("#storeKioskMClsView").hide();
        $("#storeSideSdselGrpView").hide();
        $("#storeSideSdselClassView").hide();
        $("#storeOptionGrpView").hide();
        $("#storeOptionValView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeKioskCategoryCtrl");
        scope.flex.refresh();
    };

    // 키오스크중분류(카테고리명) 탭 보이기
    $scope.storeKioskMClsShow = function () {
        $("#storeKioskCategoryTab").removeClass("on");
        $("#storeKioskMClsTab").addClass("on");
        $("#storeSideSdselGrpTab").removeClass("on");
        $("#storeSideSdselClassTab").removeClass("on");
        $("#storeOptionGrpTab").removeClass("on");
        $("#storeOptionValTab").removeClass("on");

        $("#storeKioskCategoryView").hide();
        $("#storeKioskMClsView").show();
        $("#storeSideSdselGrpView").hide();
        $("#storeSideSdselClassView").hide();
        $("#storeOptionGrpView").hide();
        $("#storeOptionValView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeKioskMClsCtrl");
        scope.flex.refresh();
    };

    // 사이드(선택그룹명) 탭 보이기
    $scope.storeSideSdselGrpShow = function () {
        $("#storeKioskCategoryTab").removeClass("on");
        $("#storeKioskMClsTab").removeClass("on");
        $("#storeSideSdselGrpTab").addClass("on");
        $("#storeSideSdselClassTab").removeClass("on");
        $("#storeOptionGrpTab").removeClass("on");
        $("#storeOptionValTab").removeClass("on");

        $("#storeKioskCategoryView").hide();
        $("#storeKioskMClsView").hide();
        $("#storeSideSdselGrpView").show();
        $("#storeSideSdselClassView").hide();
        $("#storeOptionGrpView").hide();
        $("#storeOptionValView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeSideSdselGrpCtrl");
        scope.flex.refresh();
    };

    // 사이드(선택분류) 탭 보이기
    $scope.storeSideSdselClassShow = function () {
        $("#storeKioskCategoryTab").removeClass("on");
        $("#storeKioskMClsTab").removeClass("on");
        $("#storeSideSdselGrpTab").removeClass("on");
        $("#storeSideSdselClassTab").addClass("on");
        $("#storeOptionGrpTab").removeClass("on");
        $("#storeOptionValTab").removeClass("on");

        $("#storeKioskCategoryView").hide();
        $("#storeKioskMClsView").hide();
        $("#storeSideSdselGrpView").hide();
        $("#storeSideSdselClassView").show();
        $("#storeOptionGrpView").hide();
        $("#storeOptionValView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeSideSdselClassCtrl");
        scope.flex.refresh();
    };

    // 옵션(그룹명) 탭 보이기
    $scope.storeOptionGrpShow = function () {
        $("#storeKioskCategoryTab").removeClass("on");
        $("#storeKioskMClsTab").removeClass("on");
        $("#storeSideSdselGrpTab").removeClass("on");
        $("#storeSideSdselClassTab").removeClass("on");
        $("#storeOptionGrpTab").addClass("on");
        $("#storeOptionValTab").removeClass("on");

        $("#storeKioskCategoryView").hide();
        $("#storeKioskMClsView").hide();
        $("#storeSideSdselGrpView").hide();
        $("#storeSideSdselClassView").hide();
        $("#storeOptionGrpView").show();
        $("#storeOptionValView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeOptionGrpCtrl");
        scope.flex.refresh();
    };

    // 옵션(옵션명) 탭 보이기
    $scope.storeOptionValShow = function () {
        $("#storeKioskCategoryTab").removeClass("on");
        $("#storeKioskMClsTab").removeClass("on");
        $("#storeSideSdselGrpTab").removeClass("on");
        $("#storeSideSdselClassTab").removeClass("on");
        $("#storeOptionGrpTab").removeClass("on");
        $("#storeOptionValTab").addClass("on");

        $("#storeKioskCategoryView").hide();
        $("#storeKioskMClsView").hide();
        $("#storeSideSdselGrpView").hide();
        $("#storeSideSdselClassView").hide();
        $("#storeOptionGrpView").hide();
        $("#storeOptionValView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeOptionValCtrl");
        scope.flex.refresh();
    };
}]);