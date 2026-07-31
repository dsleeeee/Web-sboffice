/****************************************************************
 *
 * 파일명 : smsXrayTab.js
 * 설  명 : 악성문자차단관리(X-Ray)(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.30     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('smsXrayTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#smsXrayManageView").show();
        $("#urlBlockLogView").hide();
    };

    // URL 관리 탭 보이기
    $scope.smsXrayManageShow = function () {
        $("#smsXrayManageTab").addClass("on");
        $("#urlBlockLogTab").removeClass("on");

        $("#smsXrayManageView").show();
        $("#urlBlockLogView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("smsXrayManageCtrl");
        scope.flex.refresh();
    };

    // 탐지/차단결과 로그 탭 보이기
    $scope.urlBlockLogShow = function () {
        $("#smsXrayManageTab").removeClass("on");
        $("#urlBlockLogTab").addClass("on");

        $("#smsXrayManageView").hide();
        $("#urlBlockLogView").show();

        // angular 그리드 hide 시 깨지므로 refresh() 후 조회
        var scope = agrid.getScope("urlBlockLogCtrl");
        scope.flex.refresh();
    };

}]);
