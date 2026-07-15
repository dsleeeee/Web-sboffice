/****************************************************************
 *
 * 파일명 : accountingMain.js
 * 설  명 : 벤슨 > 회계관리 > 회계관리 메인(탭) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.13     김유승       1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('accountingMainCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $scope.acDayTransferShow();
    };

    // 일별전송 탭 보이기
    $scope.acDayTransferShow = function () {
        $("#acDayTransferTab").addClass("on");
        $("#acMonthTransferTab").removeClass("on");
        $("#acStoreOptionTab").removeClass("on");
        $("#acComCodeTab").removeClass("on");

        $("#acDayTransferView").show();
        $("#acMonthTransferView").hide();
        $("#acStoreOptionView").hide();
        $("#acComCodeView").hide();
    };

    // 월별전송 탭 보이기
    $scope.acMonthTransferShow = function () {
        $("#acDayTransferTab").removeClass("on");
        $("#acMonthTransferTab").addClass("on");
        $("#acStoreOptionTab").removeClass("on");
        $("#acComCodeTab").removeClass("on");

        $("#acDayTransferView").hide();
        $("#acMonthTransferView").show();
        $("#acStoreOptionView").hide();
        $("#acComCodeView").hide();
    };

    // 매장별항목관리 탭 보이기
    $scope.acStoreOptionShow = function () {
        $("#acDayTransferTab").removeClass("on");
        $("#acMonthTransferTab").removeClass("on");
        $("#acStoreOptionTab").addClass("on");
        $("#acComCodeTab").removeClass("on");

        $("#acDayTransferView").hide();
        $("#acMonthTransferView").hide();
        $("#acStoreOptionView").show();
        $("#acComCodeView").hide();
    };

    // 공통코드관리 탭 보이기
    $scope.acComCodeShow = function () {
        $("#acDayTransferTab").removeClass("on");
        $("#acMonthTransferTab").removeClass("on");
        $("#acStoreOptionTab").removeClass("on");
        $("#acComCodeTab").addClass("on");

        $("#acDayTransferView").hide();
        $("#acMonthTransferView").hide();
        $("#acStoreOptionView").hide();
        $("#acComCodeView").show();
    };
}]);
