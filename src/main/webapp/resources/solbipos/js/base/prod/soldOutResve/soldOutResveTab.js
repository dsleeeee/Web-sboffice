/****************************************************************
 *
 * 파일명 : soldOutResveTab.js
 * 설  명 : 품절관리(예약) Tab JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.30     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('soldOutResveTabCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#soldOutResve").addClass("on");
        $("#sdselSoldOutResve").removeClass("on");
    };

    // 상품탭
    $scope.soldOutResveShow = function () {
        $("#soldOutResve").addClass("on");
        $("#sdselSoldOutResve").removeClass("on");

        $("#soldOutResveView").show();
        $("#sdselSoldOutResveView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("soldOutResveCtrl");
        scope.flex.refresh();
    };

    // 사이드상품탭
    $scope.sdselSoldOutResveShow = function () {
        $("#soldOutResve").removeClass("on");
        $("#sdselSoldOutResve").addClass("on");

        $("#soldOutResveView").hide();
        $("#sdselSoldOutResveView").show();

        var scope = agrid.getScope("sdselSoldOutResveCtrl");
        scope.flex.refresh();
    };

}]);