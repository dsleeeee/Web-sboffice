/****************************************************************
 *
 * 파일명 : kioskSideOptionTab.js
 * 설  명 : 다국어관리(키오스크/사이드/옵션) 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.11.20     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('kioskSideOptionTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#kioskCategoryView").show();
        $("#sideSdselGrpView").hide();
        $("#sideSdselClassView").hide();
        $("#optionGrpView").hide();
        $("#optionValView").hide();
    };

    // 키오스크(카테고리) 탭 보이기
    $scope.kioskCategoryShow = function () {
        $("#kioskCategoryTab").addClass("on");
        $("#sideSdselGrpTab").removeClass("on");
        $("#sideSdselClassTab").removeClass("on");
        $("#optionGrpTab").removeClass("on");
        $("#optionValTab").removeClass("on");

        $("#kioskCategoryView").show();
        $("#sideSdselGrpView").hide();
        $("#sideSdselClassView").hide();
        $("#optionGrpView").hide();
        $("#optionValView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("kioskCategoryCtrl");
        scope.flex.refresh();
    };

    // 사이드(선택그룹명) 탭 보이기
    $scope.sideSdselGrpShow = function () {
        $("#kioskCategoryTab").removeClass("on");
        $("#sideSdselGrpTab").addClass("on");
        $("#sideSdselClassTab").removeClass("on");
        $("#optionGrpTab").removeClass("on");
        $("#optionValTab").removeClass("on");

        $("#kioskCategoryView").hide();
        $("#sideSdselGrpView").show();
        $("#sideSdselClassView").hide();
        $("#optionGrpView").hide();
        $("#optionValView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideSdselGrpCtrl");
        scope.flex.refresh();
    };

    // 사이드(선택분류) 탭 보이기
    $scope.sideSdselClassShow = function () {
        $("#kioskCategoryTab").removeClass("on");
        $("#sideSdselGrpTab").removeClass("on");
        $("#sideSdselClassTab").addClass("on");
        $("#optionGrpTab").removeClass("on");
        $("#optionValTab").removeClass("on");

        $("#kioskCategoryView").hide();
        $("#sideSdselGrpView").hide();
        $("#sideSdselClassView").show();
        $("#optionGrpView").hide();
        $("#optionValView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideSdselClassCtrl");
        scope.flex.refresh();
    };

    // 옵션(그룹명) 탭 보이기
    $scope.optionGrpShow = function () {
        $("#kioskCategoryTab").removeClass("on");
        $("#sideSdselGrpTab").removeClass("on");
        $("#sideSdselClassTab").removeClass("on");
        $("#optionGrpTab").addClass("on");
        $("#optionValTab").removeClass("on");

        $("#kioskCategoryView").hide();
        $("#sideSdselGrpView").hide();
        $("#sideSdselClassView").hide();
        $("#optionGrpView").show();
        $("#optionValView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("optionGrpCtrl");
        scope.flex.refresh();
    };

    // 옵션(옵션명) 탭 보이기
    $scope.optionValShow = function () {
        $("#kioskCategoryTab").removeClass("on");
        $("#sideSdselGrpTab").removeClass("on");
        $("#sideSdselClassTab").removeClass("on");
        $("#optionGrpTab").removeClass("on");
        $("#optionValTab").addClass("on");

        $("#kioskCategoryView").hide();
        $("#sideSdselGrpView").hide();
        $("#sideSdselClassView").hide();
        $("#optionGrpView").hide();
        $("#optionValView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("optionValCtrl");
        scope.flex.refresh();
    };
}]);