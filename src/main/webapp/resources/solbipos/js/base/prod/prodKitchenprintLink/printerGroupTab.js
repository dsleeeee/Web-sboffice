/****************************************************************
 *
 * 파일명 : side.js
 * 설  명 : 기초관리 > 상품관리 > 상품그룹-매장프린터연결 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.09.21     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('printerGroupTabCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#printerGroupView").show();
    };

    // 상품-매장주방프린터 연결 탭 보이기
    $scope.printerGroupShow = function () {
        $("#printerGroupTab").addClass("on");

        $("#printerGroupView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("printerGroupCtrl");
        scope.flex.refresh();
    };

}]);