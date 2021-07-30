/**
 * get application
 */
var app = agrid.getApp();

app.controller('rtnStatusSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#rtnStatusBillView").show();
        $("#rtnStatusDayView").hide();
        $("#rtnStatusProdView").hide();
    };


    // 영수증별 반품현황 탭 보이기
    $scope.rtnStatusBillTabShow = function () {
        $("#rtnStatusBillTab").addClass("on");
        $("#rtnStatusDayTab").removeClass("on");
        $("#rtnStatusProdTab").removeClass("on");

        $("#rtnStatusBillView").show();
        $("#rtnStatusDayView").hide();
        $("#rtnStatusProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("rtnStatusBillCtrl");
        scope.flex.refresh();
    };

    // 반품현황 탭 보이기
    $scope.rtnStatusDayTabShow = function () {
        $("#rtnStatusBillTab").removeClass("on");
        $("#rtnStatusDayTab").addClass("on");
        $("#rtnStatusProdTab").removeClass("on");

        $("#rtnStatusBillView").hide();
        $("#rtnStatusDayView").show();
        $("#rtnStatusProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("rtnStatusDayMainCtrl");
        scope.flex.refresh();

        scope = agrid.getScope("rtnStatusDayDtlCtrl");
        scope.flex.refresh();

        scope = agrid.getScope("rtnStatusPosDtlCtrl");
        scope.flex.refresh();
    };


    // 상품별 반품현황 탭 보이기
    $scope.rtnStatusProdTabShow = function () {
        $("#rtnStatusBillTab").removeClass("on");
    	$("#rtnStatusProdTab").addClass("on");
    	$("#rtnStatusDayTab").removeClass("on");

        $("#rtnStatusBillView").hide();
    	$("#rtnStatusProdView").show();
        $("#rtnStatusDayView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("rtnStatusProdCtrl");
        scope.flex.refresh();
    };
}]);