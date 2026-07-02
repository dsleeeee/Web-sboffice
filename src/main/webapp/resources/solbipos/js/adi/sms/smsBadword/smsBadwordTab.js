/****************************************************************
 *
 * 파일명 : smsBadwordTab.js
 * 설  명 : SMS금칙어(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.01     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('smsBadwordTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#badwordManageView").show();
        $("#msgBlockLogView").hide();
    };

    // 금칙어관리 탭 보이기
    $scope.badwordManageShow = function () {
        $("#badwordManageTab").addClass("on");
        $("#msgBlockLogTab").removeClass("on");

        $("#badwordManageView").show();
        $("#msgBlockLogView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("badwordManageCtrl");
        scope.flex.refresh();
    };

    // 탐지/차단 결과 로그 탭 보이기
    $scope.msgBlockLogShow = function () {
        $("#badwordManageTab").removeClass("on");
        $("#msgBlockLogTab").addClass("on");

        $("#badwordManageView").hide();
        $("#msgBlockLogView").show();

        // angular 그리드 hide 시 깨지므로 refresh() 후 조회
        var scope = agrid.getScope("msgBlockLogCtrl");
        scope.flex.refresh();
    };

}]);
