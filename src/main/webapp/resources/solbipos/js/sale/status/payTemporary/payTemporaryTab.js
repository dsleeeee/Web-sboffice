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
    };

    // 가승인-상품권결제차액 조회 탭 보이기
    $scope.payTemporaryShow = function () {
        $("#payTemporaryTab").addClass("on");

        $("#payTemporaryView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("payTemporaryCtrl");
        scope.flex.refresh();
    };

}]);