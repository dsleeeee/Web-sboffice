/****************************************************************
 *
 * 파일명 : kioskTab.js
 * 설  명 : 다국어관리(키오스크(카테고리)/사이드/옵션) 탭 JavaScript
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

app.controller('kioskTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#kioskCategoryView").show();
        //$("#sideView").hide();
        //$("#optionView").hide();
    };

    // 키오스크(카테고리) 탭 보이기
    $scope.kioskCategoryShow = function () {
        $("#kioskCategoryTab").addClass("on");
        //$("#sideTab").removeClass("on");
        //$("#optionTab").removeClass("on");

        $("#kioskCategoryView").show();
        //$("#sideView").hide();
        //$("#optionView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("kioskCategoryCtrl");
        scope.flex.refresh();
    };

    // 사이드 탭 보이기
    /*$scope.sideShow = function () {
        $("#kioskCategoryTab").removeClass("on");
        $("#sideTab").addClass("on");
        $("#optionTab").removeClass("on");

        $("#kioskCategoryView").hide();
        $("#sideView").show();
        $("#optionView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideCtrl");
        scope.flex.refresh();
    };*/

    // 옵션 탭 보이기
    /*$scope.optionShow = function () {
        $("#kioskCategoryTab").removeClass("on");
        $("#sideTab").removeClass("on");
        $("#optionTab").addClass("on");

        $("#kioskCategoryView").hide();
        $("#sideView").hide();
        $("#optionView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("optionCtrl");
        scope.flex.refresh();
    };*/

}]);