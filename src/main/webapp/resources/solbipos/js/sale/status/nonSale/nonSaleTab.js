/****************************************************************
 *
 * 파일명 : nonSaleTab.js
 * 설  명 : 보증금현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.16     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('nonSaleTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#cupRefundView").show();
        $("#nonSaleDayView").hide();
    };

    // 반환내역 탭 보이기
    $scope.cupRefundShow = function () {
        $("#cupRefundTab").addClass("on");
        $("#nonSaleDayTab").removeClass("on");

        $("#cupRefundView").show();
        $("#nonSaleDayView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("cupRefundCtrl");
        scope.flex.refresh();
    };

    // 일별 탭 보이기
    $scope.nonSaleDayShow = function () {
        $("#cupRefundTab").removeClass("on");
        $("#nonSaleDayTab").addClass("on");

        $("#cupRefundView").hide();
        $("#nonSaleDayView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("nonSaleDayCtrl");
        scope.flex.refresh();
    };
}]);