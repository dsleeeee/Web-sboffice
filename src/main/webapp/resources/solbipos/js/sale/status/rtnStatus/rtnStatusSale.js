/**
 * get application
 */
var app = agrid.getApp();

app.controller('rtnStatusSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#rtnStatusDayView").show();
        $("#rtnStatusProdView").hide();
    };

    // 반품현황 탭 보이기
    $scope.rtnStatusDayTabShow = function () {
        $("#rtnStatusDayTab").addClass("on");
        $("#rtnStatusProdTab").removeClass("on");

        $("#rtnStatusDayView").show();
        $("#rtnStatusProdView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("rtnStatusDayCtrl");
        scope.flex.refresh();
    };


    // 상품별 반품현황 탭 보이기
    $scope.rtnStatusProdTabShow = function () {
    	$("#rtnStatusProdTab").addClass("on");
    	$("#rtnStatusDayTab").removeClass("on");

    	$("#rtnStatusProdView").show();
        $("#rtnStatusDayView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("rtnStatusProdCtrl");
        scope.flex.refresh();
    };
}]);