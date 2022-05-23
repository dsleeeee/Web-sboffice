/****************************************************************
 *
 * 파일명 : smsChargeTab.js
 * 설  명 : SMS충전(탭) JavaScript
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

app.controller('smsChargeTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#smsChargeView").show();
        $("#smsChargeHistView").hide();
        $("#msgOneAmtSettingView").hide();

        // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
        if(tabVal == "5") {
            $("#smsChargeTab").addClass("on");
            $("#smsChargeHistTab").removeClass("on");
            $("#msgOneAmtSettingTab").removeClass("on");

            $("#smsChargeView").show();
            $("#smsChargeHistView").hide();
            $("#msgOneAmtSettingView").hide();
        }
    };

    // SMS충전/KCP PG 탭 보이기
    $scope.smsChargeShow = function () {
        $("#smsChargeTab").addClass("on");
        $("#smsChargeHistTab").removeClass("on");
        $("#msgOneAmtSettingTab").removeClass("on");

        $("#smsChargeView").show();
        $("#smsChargeHistView").hide();
        $("#msgOneAmtSettingView").hide();

        // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
        tabVal = "";
    };

    // SMS충전내역 탭 보이기
    $scope.smsChargeHistShow = function () {
        $("#smsChargeTab").removeClass("on");
        $("#smsChargeHistTab").addClass("on");
        $("#msgOneAmtSettingTab").removeClass("on");

        $("#smsChargeView").hide();
        $("#smsChargeHistView").show();
        $("#msgOneAmtSettingView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("smsChargeHistCtrl");
        scope.flex.refresh();

        // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
        tabVal = "";
    };

    // 전송요금설정 탭 보이기
    $scope.msgOneAmtSettingShow = function () {
        $("#smsChargeTab").removeClass("on");
        $("#smsChargeHistTab").removeClass("on");
        $("#msgOneAmtSettingTab").addClass("on");

        $("#smsChargeView").hide();
        $("#smsChargeHistView").hide();
        $("#msgOneAmtSettingView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("msgOneAmtSettingCtrl");
        scope.flex.refresh();
        var scope2 = agrid.getScope("msgOneAmtSettingDetailCtrl");
        scope2.flex.refresh();

        // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
        tabVal = "";
    };

}]);