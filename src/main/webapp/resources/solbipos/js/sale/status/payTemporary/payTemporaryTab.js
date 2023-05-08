/****************************************************************
 *
 * 파일명 : payTemporaryTab.js
 * 설  명 : 가승인-상품권결제차액(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.03     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('payTemporaryTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#payTemporaryView").show();
        $("#payTemporaryGiftView").hide();
    };

    // 가승인-상품권결제차액 조회 탭 보이기
    $scope.payTemporaryShow = function () {
        $("#payTemporaryTab").addClass("on");
        $("#payTemporaryGiftTab").removeClass("on");

        $("#payTemporaryView").show();
        $("#payTemporaryGiftView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("payTemporaryCtrl");
        scope.flex.refresh();
    };

    // 가승인-상품권결제차액 상세내역 조회 탭 보이기
    $scope.payTemporaryGiftShow = function () {
        $("#payTemporaryTab").removeClass("on");
        $("#payTemporaryGiftTab").addClass("on");

        $("#payTemporaryView").hide();
        $("#payTemporaryGiftView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("payTemporaryGiftCtrl");
        scope.flex.refresh();
    };

}]);