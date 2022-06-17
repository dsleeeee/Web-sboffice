/****************************************************************
 *
 * 파일명 : weightTab.js
 * 설  명 : 중량별탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.09     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('weightTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#weightView").show();
        $("#weightDayView").hide();
        $("#weightProdView").hide();
    };

    // 중량별 탭 보이기
    $scope.weightShow = function () {
        $("#weightTab").addClass("on");
        $("#weightDayTab").removeClass("on");
        $("#weightProdTab").removeClass("on");

        $("#weightView").show();
        $("#weightDayView").hide();
        $("#weightProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("weightCtrl");
        scope.flex.refresh();
    };

    // 일자별 탭 보이기
    $scope.weightDayShow = function () {
        $("#weightTab").removeClass("on");
        $("#weightDayTab").addClass("on");
        $("#weightProdTab").removeClass("on");

        $("#weightView").hide();
        $("#weightDayView").show();
        $("#weightProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("weightDayCtrl");
        scope.flex.refresh();
    };

    // 상품별 탭 보이기
    $scope.weightProdShow = function () {
        $("#weightTab").removeClass("on");
        $("#weightDayTab").removeClass("on");
        $("#weightProdTab").addClass("on");

        $("#weightView").hide();
        $("#weightDayView").hide();
        $("#weightProdView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("weightProdCtrl");
        scope.flex.refresh();
    };
}]);