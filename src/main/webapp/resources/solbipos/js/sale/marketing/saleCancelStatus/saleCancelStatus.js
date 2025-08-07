/****************************************************************
 *
 * 파일명 : saleCancelStatus.js
 * 설  명 : 미스터피자 > 마케팅조회 > 취소현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.31     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('saleCancelStatusCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#saleCancelStatusAllView").show();
        $("#saleCancelStatusStoreView").hide();
    };

    // 영수증별 반품현황 탭 보이기
    $scope.saleCancelStatusAllTabShow = function () {
        $("#saleCancelStatusAll").addClass("on");
        $("#saleCancelStatusStore").removeClass("on");

        $("#saleCancelStatusAllView").show();
        $("#saleCancelStatusStoreView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("saleCancelStatusAllCtrl");
        scope.flex.refresh();
        // angular 그리드 hide 시 깨지므로 refresh()
        scope = agrid.getScope("saleCancelStatusAllDtlCtrl");
        scope.flex.refresh();
    };

    // 반품현황 탭 보이기
    $scope.saleCancelStatusStoreTabShow = function () {
        $("#saleCancelStatusAll").removeClass("on");
        $("#saleCancelStatusStore").addClass("on");

        $("#saleCancelStatusAllView").hide();
        $("#saleCancelStatusStoreView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("saleCancelStatusStoreCtrl");
        scope.flex.refresh();
    };

}]);