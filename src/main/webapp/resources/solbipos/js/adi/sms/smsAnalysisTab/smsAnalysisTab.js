/****************************************************************
 *
 * 파일명 : smsAnalysisTab.js
 * 설  명 : SMS전송분석(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('smsAnalysisTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#smsSendHistView").show();
        $("#daySendStatusView").hide();
    };

    // SMS전송이력 탭 보이기
    $scope.smsSendHistShow = function () {
        $("#smsSendHistTab").addClass("on");
        $("#daySendStatusTab").removeClass("on");

        $("#smsSendHistView").show();
        $("#daySendStatusView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("smsSendHistCtrl");
        scope.flex.refresh();
    };

    // 일자별 전송현황 탭 보이기
    $scope.daySendStatusShow = function () {
        $("#smsSendHistTab").removeClass("on");
        $("#daySendStatusTab").addClass("on");

        $("#smsSendHistView").hide();
        $("#daySendStatusView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("daySendStatusCtrl");
        scope.flex.refresh();
    };
}]);