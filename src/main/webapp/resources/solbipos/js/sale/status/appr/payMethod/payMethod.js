/**
 * get application
 */
var app = agrid.getApp();

app.controller('payMethodCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#apprCardView").show();
        $("#apprCashView").hide();
        $("#apprPaycoView").hide();
        $("#apprMpayView").hide();
        $("#apprMcouponView").hide();
        $("#apprPartnerView").hide();
        $("#apprNcardView").hide();
        $("#apprNcashView").hide();
    };

    // 신용카드별 탭 보이기
    $scope.apprCardTabShow = function () {
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
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprCardCtrl");
        scope.flex.refresh();
    };


    // 현금별 탭 보이기
    $scope.apprCashTabShow = function () {
    	$("#apprCashTab").addClass("on");
    	$("#apprCardTab").removeClass("on");
    	$("#apprPaycoTab").removeClass("on");
        $("#apprMpayTab").removeClass("on");
        $("#apprMcouponTab").removeClass("on");
        $("#apprPartnerTab").removeClass("on");
        $("#apprNcardTab").removeClass("on");
        $("#apprNcashTab").removeClass("on");
        
    	$("#apprCashView").show();
        $("#apprCardView").hide();
        $("#apprPaycoView").hide();
        $("#apprMpayView").hide();
        $("#apprMcouponView").hide();
        $("#apprPartnerView").hide();
        $("#apprNcardView").hide();
        $("#apprNcashView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprCashCtrl");
        scope.flex.refresh();
    };


    // Payco별 탭 보이기
    $scope.apprPaycoTabShow = function () {
    	$("#apprPaycoTab").addClass("on");
    	$("#apprCardTab").removeClass("on");
    	$("#apprCashTab").removeClass("on");
        $("#apprMpayTab").removeClass("on");
        $("#apprMcouponTab").removeClass("on");
        $("#apprPartnerTab").removeClass("on");
        $("#apprNcardTab").removeClass("on");
        $("#apprNcashTab").removeClass("on");

    	$("#apprPaycoView").show();
        $("#apprCardView").hide();
        $("#apprCashView").hide();
        $("#apprMpayView").hide();
        $("#apprMcouponView").hide();
        $("#apprPartnerView").hide();
        $("#apprNcardView").hide();
        $("#apprNcashView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprPaycoCtrl");
        scope.flex.refresh();
    };
    
    
 // Mpay별 탭 보이기
    $scope.apprMpayTabShow = function () {
    	$("#apprMpayTab").addClass("on");
    	$("#apprCardTab").removeClass("on");
    	$("#apprCashTab").removeClass("on");
        $("#apprPaycoTab").removeClass("on");
        $("#apprMcouponTab").removeClass("on");
        $("#apprPartnerTab").removeClass("on");
        $("#apprNcardTab").removeClass("on");
        $("#apprNcashTab").removeClass("on");

    	$("#apprMpayView").show();
        $("#apprCardView").hide();
        $("#apprCashView").hide();
        $("#apprPaycoView").hide();
        $("#apprMcouponView").hide();
        $("#apprPartnerView").hide();
        $("#apprNcardView").hide();
        $("#apprNcashView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprMpayCtrl");
        scope.flex.refresh();
    };
    
    
 // Mcoupon별 탭 보이기
    $scope.apprMcouponTabShow = function () {
    	$("#apprMcouponTab").addClass("on");
    	$("#apprMpayTab").removeClass("on");
    	$("#apprCardTab").removeClass("on");
    	$("#apprCashTab").removeClass("on");
        $("#apprPaycoTab").removeClass("on");
        $("#apprPartnerTab").removeClass("on");
        $("#apprNcardTab").removeClass("on");
        $("#apprNcashTab").removeClass("on");

        $("#apprMcouponView").show();
    	$("#apprMpayView").hide();
        $("#apprCardView").hide();
        $("#apprCashView").hide();
        $("#apprPaycoView").hide();
        $("#apprPartnerView").hide();
        $("#apprNcardView").hide();
        $("#apprNcashView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprMcouponCtrl");
        scope.flex.refresh();
    };
    
    
 // Partner별 탭 보이기
    $scope.apprPartnerTabShow = function () {
    	$("#apprPartnerTab").addClass("on");
    	$("#apprMpayTab").removeClass("on");
    	$("#apprCardTab").removeClass("on");
    	$("#apprCashTab").removeClass("on");
        $("#apprPaycoTab").removeClass("on");
        $("#apprMcouponTab").removeClass("on");
        $("#apprNcardTab").removeClass("on");
        $("#apprNcashTab").removeClass("on");

        $("#apprPartnerView").show();
    	$("#apprMpayView").hide();
        $("#apprCardView").hide();
        $("#apprCashView").hide();
        $("#apprPaycoView").hide();
        $("#apprMcouponView").hide();
        $("#apprNcardView").hide();
        $("#apprNcashView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprPartnerCtrl");
        scope.flex.refresh();
    };
    
    
 // Ncard별 탭 보이기
    $scope.apprNcardTabShow = function () {
    	$("#apprNcardTab").addClass("on");
    	$("#apprMpayTab").removeClass("on");
    	$("#apprCardTab").removeClass("on");
    	$("#apprCashTab").removeClass("on");
        $("#apprPaycoTab").removeClass("on");
        $("#apprMcouponTab").removeClass("on");
        $("#apprPartnerTab").removeClass("on");
        $("#apprNcashTab").removeClass("on");

        $("#apprNcardView").show();
    	$("#apprMpayView").hide();
        $("#apprCardView").hide();
        $("#apprCashView").hide();
        $("#apprPaycoView").hide();
        $("#apprMcouponView").hide();
        $("#apprPartnerView").hide();
        $("#apprNcashView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprNcardCtrl");
        scope.flex.refresh();
    };
    
    
 // Ncash별 탭 보이기
    $scope.apprNcashTabShow = function () {
    	$("#apprNcashTab").addClass("on");
    	$("#apprMpayTab").removeClass("on");
    	$("#apprCardTab").removeClass("on");
    	$("#apprCashTab").removeClass("on");
        $("#apprPaycoTab").removeClass("on");
        $("#apprMcouponTab").removeClass("on");
        $("#apprPartnerTab").removeClass("on");
        $("#apprNcardTab").removeClass("on");

        $("#apprNcashView").show();
    	$("#apprMpayView").hide();
        $("#apprCardView").hide();
        $("#apprCashView").hide();
        $("#apprPaycoView").hide();
        $("#apprMcouponView").hide();
        $("#apprPartnerView").hide();
        $("#apprNcardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprNcashCtrl");
        scope.flex.refresh();
    };
}]);