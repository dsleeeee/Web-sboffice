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
        $("#nonSaleDayView").show();
    };

    // 일별 탭 보이기
    $scope.nonSaleDayShow = function () {
        $("#nonSaleDayTab").addClass("on");

        $("#nonSaleDayView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("nonSaleDayCtrl");
        scope.flex.refresh();
    };
}]);