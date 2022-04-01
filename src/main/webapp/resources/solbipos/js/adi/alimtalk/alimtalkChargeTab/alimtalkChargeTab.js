/****************************************************************
 *
 * 파일명 : alimtalkChargeTab.js
 * 설  명 : 알림톡충전(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.21     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('alimtalkChargeTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#smsChargeView").show();
        $("#smsChargeHistView").hide();

        // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
        if(tabVal == "5") {
            $("#smsChargeTab").addClass("on");
            $("#smsChargeHistTab").removeClass("on");

            $("#smsChargeView").show();
            $("#smsChargeHistView").hide();
        }
    };

    // 알림톡충전/KCP PG 탭 보이기
    $scope.alimtalkChargeShow = function () {
        $("#smsChargeTab").addClass("on");
        $("#smsChargeHistTab").removeClass("on");

        $("#smsChargeView").show();
        $("#smsChargeHistView").hide();

        // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
        tabVal = "";
    };

    // 알림톡충전내역 탭 보이기
    $scope.alimtalkChargeHistShow = function () {
        $("#smsChargeTab").removeClass("on");
        $("#smsChargeHistTab").addClass("on");

        $("#smsChargeView").hide();
        $("#smsChargeHistView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("smsChargeHistCtrl");
        scope.flex.refresh();

        // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
        tabVal = "";
    };

}]);