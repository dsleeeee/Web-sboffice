/**
 * get application
 */
var app = agrid.getApp();

app.controller('apprSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#payMethodView").show();
        $("#apprAcquireView").hide();
        $("#apprCoprtnView").hide();
        $("#apprCashBillView").hide();
    };

    // 신용카드별 탭 보이기
    $scope.payMethodTabShow = function () {
        $("#payMethodTab").addClass("on");
        	$scope.payMethodTab();
        $("#apprAcquireTab").removeClass("on");
        $("#apprCoprtnTab").removeClass("on");
        $("#apprCashBillTab").removeClass("on");

        $("#payMethodView").show();
        $("#apprAcquireView").hide();
        $("#apprCoprtnView").hide();
        $("#apprCashBillView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("payMethodCtrl");
        scope.flex.refresh();
    };

    // 카드매입사별 탭 보이기
    $scope.apprAcquireTabShow = function () {
    	$("#apprAcquireTab").addClass("on");
    	$("#payMethodTab").removeClass("on");
    	$("#apprAcquireView").show();
        $("#payMethodView").hide();
        $("#apprCoprtnView").hide();
        $("#apprCashBillView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprAcquireCtrl");
        scope.flex.refresh();
    };

/*
    // 제휴카드별 탭 보이기
    $scope.apprCoprtnTabShow = function () {
    	$("#apprCoprtnTab").addClass("on");
    	$("#payMethodTab").removeClass("on");
    	$("#apprAcquireTab").removeClass("on");
        $("#apprCashBillTab").removeClass("on");

    	$("#apprCoprtnView").show();
        $("#payMethodView").hide();
        $("#apprAcquireView").hide();
        $("#apprCashBillView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprCoprtnCtrl");
        scope.flex.refresh();
    };
    
    
 // 현금영수증별 탭 보이기
    $scope.apprCashBillTabShow = function () {
    	$("#apprCashBillTab").addClass("on");
    	$("#payMethodTab").removeClass("on");
    	$("#apprAcquireTab").removeClass("on");
        $("#apprCoprtnTab").removeClass("on");

    	$("#apprCashBillView").show();
        $("#payMethodView").hide();
        $("#apprAcquireView").hide();
        $("#apprCoprtnView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprCashBillCtrl");
        scope.flex.refresh();
    };
*/
    
    $scope.payMethodTab = function() {
		$("#apprCardTab").addClass("on");
        $("#apprCashTab").removeClass("on");
        $("#apprPaycoTab").removeClass("on");
        $("#apprMpayTab").removeClass("on");
        $("#apprMcouponTab").removeClass("on");
        $("#apprPartnerTab").removeClass("on");
        $("#apprNcardTab").removeClass("on");
        $("#apprNcashTab").removeClass("on");

        $("#apprCardView").show();
        $("#apprCashView").hide();
        $("#apprPaycoView").hide();
        $("#apprMpayView").hide();
        $("#apprMcouponView").hide();
        $("#apprPartnerView").hide();
        $("#apprNcardView").hide();
        $("#apprNcashView").hide();
    };
}]);