/****************************************************************
 *
 * 파일명 : prepaidCardStatusTab.js
 * 설  명 : 맘스터치 > 매출분석2 > 선불카드현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.04.02     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('prepaidCardStatusTabCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    $scope.init = function () {
        $("#prepaidCardChargeStatusView").show();
        $("#prepaidCardUseStatusView").hide();
    };

    // 선불카드 충전 현황 탭 보이기
    $scope.prepaidCardChargeStatusShow = function () {
        $("#prepaidCardChargeStatusTab").addClass("on");
        $("#prepaidCardUseStatusTab").removeClass("on");

        $("#prepaidCardChargeStatusView").show();
        $("#prepaidCardUseStatusView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prepaidCardChargeStatusCtrl");
        scope.flex.refresh();
    };

    // 선불카드 사용 현황 탭 보이기
    $scope.prepaidCardUseStatusShow = function () {
        $("#prepaidCardChargeStatusTab").removeClass("on");
        $("#prepaidCardUseStatusTab").addClass("on");

        $("#prepaidCardChargeStatusView").hide();
        $("#prepaidCardUseStatusView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prepaidCardUseStatusCtrl");
        scope.flex.refresh();
    };

}]);