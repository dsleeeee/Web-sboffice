/**
 * get application
 */
var app = agrid.getApp();

/** 매입현황 controller */
app.controller('apprAcquireCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#apprAcquireCardView").hide();
        $("#apprAcquireMpayView").hide();
        $("#apprAcquireMcouponView").hide();
        $("#apprAcquireNcardView").hide();
    };

    // 신용카드별 탭 보이기
    $scope.apprAcquireCardTabShow = function () {
		$("#apprAcquireCardTab").addClass("on");
        $("#apprAcquireMpayTab").removeClass("on");
        $("#apprAcquireMcouponTab").removeClass("on");
        $("#apprAcquireNcardTab").removeClass("on");
		
        $("#apprAcquireCardView").show();
        $("#apprAcquireMpayView").hide();
        $("#apprAcquireMcouponView").hide();
        $("#apprAcquireNcardView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprAcquireCardCtrl");
        scope.flex.refresh();
    };   
    
    // Mpay별 탭 보이기
    $scope.apprAcquireMpayTabShow = function () {
		$("#apprAcquireCardTab").removeClass("on");
        $("#apprAcquireMpayTab").addClass("on");
        $("#apprAcquireMcouponTab").removeClass("on");
        $("#apprAcquireNcardTab").removeClass("on");

        $("#apprAcquireCardView").hide();
        $("#apprAcquireMpayView").show();
        $("#apprAcquireMcouponView").hide();
        $("#apprAcquireNcardView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprAcquireMpayCtrl");
        scope.flex.refresh();
    };
        
    // Mcoupon별 탭 보이기
    $scope.apprAcquireMcouponTabShow = function () {
		$("#apprAcquireCardTab").removeClass("on");
        $("#apprAcquireMpayTab").removeClass("on");
        $("#apprAcquireMcouponTab").addClass("on");
        $("#apprAcquireNcardTab").removeClass("on");

        $("#apprAcquireCardView").hide();
        $("#apprAcquireMpayView").hide();
        $("#apprAcquireMcouponView").show();
        $("#apprAcquireNcardView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprAcquireMcouponCtrl");
        scope.flex.refresh();
    };
        
 // Ncard별 탭 보이기
    $scope.apprAcquireNcardTabShow = function () {
		$("#apprAcquireCardTab").removeClass("on");
        $("#apprAcquireMpayTab").removeClass("on");
        $("#apprAcquireMcouponTab").removeClass("on");
        $("#apprAcquireNcardTab").addClass("on");

        $("#apprAcquireCardView").hide();
        $("#apprAcquireMpayView").hide();
        $("#apprAcquireMcouponView").hide();
        $("#apprAcquireNcardView").show();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("apprAcquireNcardCtrl");
        scope.flex.refresh();
    };

}]);