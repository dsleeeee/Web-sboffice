/****************************************************************
 *
 * 파일명 : alimtalkSendTab.js
 * 설  명 : 알림톡전송(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('alimtalkSendTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#alimtalkSendTypeTab").addClass("on");
        $("#alimtalkSsendStatusTab").removeClass("on");

        $("#alimtalkSendTypeView").show();
        $("#alimtalkSendStatusView").hide();
    };

    // 알림톡 전송유형 탭 보이기
    $scope.alimtalkSendTypeShow = function () {
        $("#alimtalkSendTypeTab").addClass("on");
        $("#alimtalkSendStatusTab").removeClass("on");

        $("#alimtalkSendTypeView").show();
        $("#alimtalkSendStatusView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("alimtalkSendTypeCtrl");
        scope.flex.refresh();
    };

    // 알림톡 전송결과 탭 보이기
    $scope.alimtalkSendStatusShow = function () {
        $("#alimtalkSendTypeTab").removeClass("on");
        $("#alimtalkSendStatusTab").addClass("on");

        $("#alimtalkSendTypeView").hide();
        $("#alimtalkSendStatusView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("alimtalkSendStatusCtrl");
        scope.flex.refresh();
    };
}]);