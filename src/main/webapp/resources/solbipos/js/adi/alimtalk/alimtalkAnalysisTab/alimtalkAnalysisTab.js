/****************************************************************
 *
 * 파일명 : alimtalkAnalysisTab.js
 * 설  명 : 알림톡전송분석(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.30     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('alimtalkAnalysisTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#alimtalkSendHistView").show();
        $("#alimtalkDaySendStatusView").hide();
        $("#alimtalkPeriodSendStatusView").hide();
    };

    // 알림톡 전송이력 탭 보이기
    $scope.alimtalkSendHistShow = function () {
        $("#alimtalkSendHistTab").addClass("on");
        $("#alimtalkDaySendStatusTab").removeClass("on");
        $("#alimtalkPeriodSendStatusTab").removeClass("on");

        $("#alimtalkSendHistView").show();
        $("#alimtalkDaySendStatusView").hide();
        $("#alimtalkPeriodSendStatusView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("alimtalkSendHistCtrl");
        scope.flex.refresh();
    };

    // 알림톡 일자별 전송현황 탭 보이기
    $scope.alimtalkDaySendStatusShow = function () {
        $("#alimtalkSendHistTab").removeClass("on");
        $("#alimtalkDaySendStatusTab").addClass("on");
        $("#alimtalkPeriodSendStatusTab").removeClass("on");

        $("#alimtalkSendHistView").hide();
        $("#alimtalkDaySendStatusView").show();
        $("#alimtalkPeriodSendStatusView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("alimtalkDaySendStatusCtrl");
        scope.flex.refresh();
    };

    // 알림톡 기간별 전송현황 탭 보이기
    $scope.alimtalkPeriodSendStatusShow = function () {
        $("#alimtalkSendHistTab").removeClass("on");
        $("#alimtalkDaySendStatusTab").removeClass("on");
        $("#alimtalkPeriodSendStatusTab").addClass("on");

        $("#alimtalkSendHistView").hide();
        $("#alimtalkDaySendStatusView").hide();
        $("#alimtalkPeriodSendStatusView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("alimtalkPeriodSendStatusCtrl");
        scope.flex.refresh();
    };
}]);