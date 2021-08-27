/**
 * get application
 */
var app = agrid.getApp();

app.controller('apprSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#payMethodView").show();
        $("#apprAcquireView").hide();
    };

    // 승인형황 탭 보이기
    $scope.payMethodTabShow = function () {
        $("#payMethodTab").addClass("on");
        	$scope.payMethodTab();
        $("#apprAcquireTab").removeClass("on");

        $("#payMethodView").show();
        $("#apprAcquireView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        $scope.payMethodTabRefresh();
    };

    //매입현황 탭 보이기
    $scope.apprAcquireTabShow = function () {
    	$("#payMethodTab").removeClass("on");
	    $("#apprAcquireTab").addClass("on");
			$scope.apprAcquireTab();
		
	    $("#payMethodView").hide();
	    $("#apprAcquireView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
	    $scope.apprAcquireTabRefresh();
    };


    //승인현황 하위 탭 
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

        $("#apprAcquireCardView").hide();
        $("#apprAcquireMpayView").hide();
        $("#apprAcquireMcouponView").hide();
        $("#apprAcquireeNcardView").hide();
    };
    
    //매입현황 하위 탭
    $scope.apprAcquireTab = function() {
		$("#apprAcquireCardTab").addClass("on");
        $("#apprAcquireMpayTab").removeClass("on");
        $("#apprAcquireMcouponTab").removeClass("on");
        $("#apprAcquireNcardTab").removeClass("on");

        $("#apprCardView").hide();
        $("#apprCashView").hide();
        $("#apprPaycoView").hide();
        $("#apprMpayView").hide();
        $("#apprMcouponView").hide();
        $("#apprPartnerView").hide();
        $("#apprNcardView").hide();
        $("#apprNcashView").hide();

        $("#apprAcquireCardView").show();
        $("#apprAcquireMpayView").hide();
        $("#apprAcquireMcouponView").hide();
        $("#apprAcquireeNcardView").hide();

    };
    
    //승인현황 하위 탭 초기화
    $scope.payMethodTabRefresh = function(){
    	var scope = agrid.getScope("apprCardCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprCashCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprPaycoCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprMpayCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprMcouponCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprPartnerCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprNcardCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprNcashCtrl");
        scope.flex.refresh();
    }
    
    //매입현황 하위 탭 초기화
    $scope.apprAcquireTabRefresh = function(){
    	var scope = agrid.getScope("apprAcquireCardCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprMpayCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprAcquireMcouponCtrl");
        scope.flex.refresh();
        scope = agrid.getScope("apprNcardCtrl");
        scope.flex.refresh();
    }
    
    
}]);