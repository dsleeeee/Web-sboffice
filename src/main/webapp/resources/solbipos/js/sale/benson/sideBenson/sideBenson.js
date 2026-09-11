/****************************************************************
 *
 * 파일명 : sideBenson.js
 * 설  명 : 벤슨 > 매출현황2 > 상품별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.10     김유승      1.0            링크 개발실 개발1팀
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('sideBensonCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#sideBensonProdClassView").show();
    };

    // 상품분류별 탭 보이기
    $scope.sideBensonProdClassShow = function () {
        $("#sideBensonProdClassTab").addClass("on");

        $("#sideBensonProdClassView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideBensonProdClassCtrl");
        scope.flex.refresh();
    };

}]);
