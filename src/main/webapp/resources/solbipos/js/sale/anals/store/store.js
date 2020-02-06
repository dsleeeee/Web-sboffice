/**
 * get application
 */
var app = agrid.getApp();

app.controller('empSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
    	$("#storeRankView").show();
        $("#storeProdView").hide();
        $("#storeMonthView").hide();
        $("#storeFgView").hide();
        $("#storeBrandView").hide();
    };

   // 매장순위 탭 보이기
    $scope.storeRankShow = function () {
        $("#storeRankTab").addClass("on");
        $("#storeProdTab").removeClass("on");
        $("#storeMonthTab").removeClass("on");
        $("#storeFgTab").removeClass("on");
        $("#storeBrandTab").removeClass("on");
               
        $("#storeRankView").show();
        $("#storeProdView").hide();
        $("#storeMonthView").hide();
        $("#storeFgView").hide();
        $("#storeBrandView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeRankCtrl");
        scope.flex.refresh();
    };


    // 매장상품순위 탭 보이기
    $scope.storeProdShow = function () {
        $("#storeRankTab").removeClass("on");
        $("#storeProdTab").addClass("on");
        $("#storeMonthTab").removeClass("on");
        $("#storeFgTab").removeClass("on");
        $("#storeBrandTab").removeClass("on");

        $("#storeRankView").hide();
        $("#storeProdView").show();
        $("#storeMonthView").hide();
        $("#storeFgView").hide();
        $("#storeBrandView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeProdCtrl");
        scope.flex.refresh();       
    };

	// 매장월별순위 탭 보이기
    $scope.storeMonthShow = function () {
        $("#storeRankTab").removeClass("on");
        $("#storeProdTab").removeClass("on");
        $("#storeMonthTab").addClass("on");
		$("#storeFgTab").removeClass("on");
        $("#storeBrandTab").removeClass("on");
        
        $("#storeRankView").hide();
        $("#storeProdView").hide();
        $("#storeMonthView").show();
		$("#storeFgView").hide();
        $("#storeBrandView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeMonthCtrl");
        scope.flex.refresh();
    };

    // 매장형태별 탭 보이기
    $scope.storeFgShow = function () {
        $("#storeRankTab").removeClass("on");
        $("#storeProdTab").removeClass("on");
        $("#storeMonthTab").removeClass("on");
        $("#storeFgTab").addClass("on");
        $("#storeBrandTab").removeClass("on");
        
        $("#storeRankView").hide();
        $("#storeProdView").hide();
        $("#storeMonthView").hide();
        $("#storeFgView").show();
        $("#storeBrandView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeFgCtrl");
        scope.flex.refresh();
    };
    
    // 브랜드별 탭 보이기
    $scope.storeBrandShow = function () {
        $("#storeRankTab").removeClass("on");
        $("#storeProdTab").removeClass("on");
        $("#storeMonthTab").removeClass("on");
        $("#storeFgTab").removeClass("on");
        $("#storeBrandTab").addClass("on");
        
        $("#storeRankView").hide();
        $("#storeProdView").hide();
        $("#storeMonthView").hide();
        $("#storeFgView").hide();
        $("#storeBrandView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("storeBrandCtrl");
        scope.flex.refresh();
    };

}]);