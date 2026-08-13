/****************************************************************
 *
 * 파일명 : smsUserStatusTab.js
 * 설  명 : SMS사용자현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.07     이다솜      1.0
 *
 * **************************************************************/
var app = agrid.getApp();

app.controller('smsUserStatusTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#smsUserView").show();
        $("#sendTelNoView").hide();
        $("#smsChargeStatusView").hide();
        $("#smsSendHistView").hide();
    };

    // SMS 사용자 탭
    $scope.smsUserShow = function () {
        $("#smsUserTab").addClass("on");
        $("#sendTelNoTab").removeClass("on");
        $("#smsChargeStatusTab").removeClass("on");
        $("#smsSendHistTab").removeClass("on");

        $("#smsUserView").show();
        $("#sendTelNoView").hide();
        $("#smsChargeStatusView").hide();
        $("#smsSendHistView").hide();

        var scope = agrid.getScope("smsUserCtrl");
        scope.flex.refresh();
    };

    // 발신번호 탭
    $scope.sendTelNoShow = function () {
        $("#smsUserTab").removeClass("on");
        $("#sendTelNoTab").addClass("on");
        $("#smsChargeStatusTab").removeClass("on");
        $("#smsSendHistTab").removeClass("on");

        $("#smsUserView").hide();
        $("#sendTelNoView").show();
        $("#smsChargeStatusView").hide();
        $("#smsSendHistView").hide();

        var scope = agrid.getScope("sendTelNoCtrl");
        scope.flex.refresh();
    };

    // 충전내역 탭
    $scope.smsChargeStatusShow = function () {
        $("#smsUserTab").removeClass("on");
        $("#sendTelNoTab").removeClass("on");
        $("#smsChargeStatusTab").addClass("on");
        $("#smsSendHistTab").removeClass("on");

        $("#smsUserView").hide();
        $("#sendTelNoView").hide();
        $("#smsChargeStatusView").show();
        $("#smsSendHistView").hide();

        var scope = agrid.getScope("smsChargeStatusCtrl");
        scope.flex.refresh();
    };

    // 전송이력 탭
    $scope.smsSendHistShow = function () {
        $("#smsUserTab").removeClass("on");
        $("#sendTelNoTab").removeClass("on");
        $("#smsChargeStatusTab").removeClass("on");
        $("#smsSendHistTab").addClass("on");

        $("#smsUserView").hide();
        $("#sendTelNoView").hide();
        $("#smsChargeStatusView").hide();
        $("#smsSendHistView").show();

        var scope = agrid.getScope("smsSendHistCtrl");
        scope.flex.refresh();
    };

}]);
