/****************************************************************
 *
 * 파일명 : posSmsSend.js
 * 설  명 : SMS전송(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.10.22     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('posSmsSendCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        if(orgnFg == "HQ" || orgnFg == "STORE") {
            $("#marketingSmsSendView").show();
            $("#sendStatusView").hide();
            $("#msgManageView").hide();
            $("#smsTelNoManageView").hide();
        } else {
            $("#marketingSmsSendTab").removeClass("on");
            $("#sendStatusTab").addClass("on");
            $("#msgManageTab").removeClass("on");
            $("#smsTelNoManageTab").removeClass("on");

            $("#marketingSmsSendView").hide();
            $("#sendStatusView").show();
            $("#msgManageView").hide();
            $("#smsTelNoManageView").hide();
        }
    };

    // 마케팅용 SMS전송 탭 보이기
    $scope.marketingSmsSendShow = function () {
        $("#marketingSmsSendTab").addClass("on");
        $("#sendStatusTab").removeClass("on");
        $("#msgManageTab").removeClass("on");
        $("#smsTelNoManageTab").removeClass("on");

        $("#marketingSmsSendView").show();
        $("#sendStatusView").hide();
        $("#msgManageView").hide();
        $("#smsTelNoManageView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("marketingSmsSendCtrl");
        scope.flexMarketingSmsSend.refresh();
    };

    // 문자전송현황 탭 보이기
    $scope.sendStatusShow = function () {
        $("#marketingSmsSendTab").removeClass("on");
        $("#sendStatusTab").addClass("on");
        $("#msgManageTab").removeClass("on");
        $("#smsTelNoManageTab").removeClass("on");

        $("#marketingSmsSendView").hide();
        $("#sendStatusView").show();
        $("#msgManageView").hide();
        $("#smsTelNoManageView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("sendStatusCtrl");
        scope.flex.refresh();
    };

    // 메세지관리 탭 보이기
    $scope.msgManageShow = function () {
        $("#marketingSmsSendTab").removeClass("on");
        $("#sendStatusTab").removeClass("on");
        $("#msgManageTab").addClass("on");
        $("#smsTelNoManageTab").removeClass("on");

        $("#marketingSmsSendView").hide();
        $("#sendStatusView").hide();
        $("#msgManageView").show();
        $("#smsTelNoManageView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("msgManageCtrl");
        scope.flex.refresh();
    };

    // 발신번호관리 탭 보이기
    $scope.smsTelNoManageShow = function () {
        $("#marketingSmsSendTab").removeClass("on");
        $("#sendStatusTab").removeClass("on");
        $("#msgManageTab").removeClass("on");
        $("#smsTelNoManageTab").addClass("on");

        $("#marketingSmsSendView").hide();
        $("#sendStatusView").hide();
        $("#msgManageView").hide();
        $("#smsTelNoManageView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("smsTelNoManageCtrl");
        scope.flex.refresh();
    };
}]);