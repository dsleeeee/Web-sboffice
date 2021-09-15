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
    };

    // SMS충전/KCP PG 탭 보이기
    $scope.smsChargeShow = function () {
        $("#smsChargeTab").addClass("on");
        $("#smsChargeHistTab").removeClass("on");

        $("#smsChargeView").show();
        $("#smsChargeHistView").hide();
    };

    // SMS충전내역 탭 보이기
    $scope.smsChargeHistShow = function () {
        $("#smsChargeTab").removeClass("on");
        $("#smsChargeHistTab").addClass("on");

        $("#smsChargeView").hide();
        $("#smsChargeHistView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("smsChargeHistCtrl");
        scope.flex.refresh();
    };

}]);