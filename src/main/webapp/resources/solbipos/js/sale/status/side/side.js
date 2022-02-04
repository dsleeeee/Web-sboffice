/****************************************************************
 *
 * 파일명 : side.js
 * 설  명 : 매출관리 > 매출현황2 > 상품별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.21     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('sideCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#sideProdClassView").show();
    };

    // 상품분류별 탭 보이기
    $scope.sideProdClassShow = function () {
        $("#sideProdClassTab").addClass("on");

        $("#sideProdClassView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sideProdClassCtrl");
        scope.flex.refresh();
    };

}]);