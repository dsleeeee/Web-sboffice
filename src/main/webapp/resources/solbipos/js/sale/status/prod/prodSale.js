/****************************************************************
 *
 * 파일명 : prodSale.js
 * 설  명 : 상품별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.06     김진        1.0
 * 2021.01.04     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('prodSaleCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#prodClassView").show();
        $("#prodRankView").hide();
        $("#prodPayFgView").hide();
        $("#prodHourView").hide();
        $("#prodDayView").hide();
        $("#prodPosView").hide();

    };

    // 분류별상품 탭 보이기
    $scope.prodClassShow = function () {
        $("#prodClassTab").addClass("on");
        $("#prodRankTab").removeClass("on");
        $("#prodPayFgTab").removeClass("on");
        $("#prodHourTab").removeClass("on");
        $("#prodDayTab").removeClass("on");
        $("#prodPosTab").removeClass("on");
               
        $("#prodClassView").show();
        $("#prodRankView").hide();
        $("#prodPayFgView").hide();
        $("#prodHourView").hide();
        $("#prodDayView").hide();
        $("#prodPosView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodClassCtrl");
        scope.flex.refresh();
    };

    // 상품매출순위 탭 보이기
    $scope.prodRankShow = function () {
        $("#prodClassTab").removeClass("on");
        $("#prodRankTab").addClass("on");
        $("#prodPayFgTab").removeClass("on");
        $("#prodHourTab").removeClass("on");
        $("#prodDayTab").removeClass("on");
        $("#prodPosTab").removeClass("on");
        
        $("#prodClassView").hide();
        $("#prodRankView").show();
        $("#prodPayFgView").hide();
        $("#prodHourView").hide();
        $("#prodDayView").hide();
        $("#prodPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodRankCtrl");
        scope.flex.refresh();       
    };

    // 결제수단별 탭 보이기
    $scope.prodPayFgShow = function () {
        $("#prodClassTab").removeClass("on");
        $("#prodRankTab").removeClass("on");
        $("#prodPayFgTab").addClass("on");
        $("#prodHourTab").removeClass("on");
        $("#prodDayTab").removeClass("on");
        $("#prodPosTab").removeClass("on");
        
        $("#prodClassView").hide();
        $("#prodRankView").hide();
        $("#prodPayFgView").show();
        $("#prodHourView").hide();
        $("#prodDayView").hide();
        $("#prodPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodPayFgCtrl");
        scope.flex.refresh();
    };
    
    // 시간대별 탭 보이기
    $scope.prodHourShow = function () {
        $("#prodClassTab").removeClass("on");
        $("#prodRankTab").removeClass("on");
        $("#prodPayFgTab").removeClass("on");
        $("#prodHourTab").addClass("on");
        $("#prodDayTab").removeClass("on");
        $("#prodPosTab").removeClass("on");
        
        
        $("#prodClassView").hide();
        $("#prodRankView").hide();
        $("#prodPayFgView").hide();
        $("#prodHourView").show();
        $("#prodDayView").hide();
        $("#prodPosView").hide();
        
        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodHourCtrl");
        scope.flex.refresh();
    };

    // 일자별 탭 보이기
    $scope.prodDayShow = function () {
        $("#prodClassTab").removeClass("on");
        $("#prodRankTab").removeClass("on");
        $("#prodPayFgTab").removeClass("on");
        $("#prodHourTab").removeClass("on");
        $("#prodDayTab").addClass("on");
        $("#prodPosTab").removeClass("on");
        
        $("#prodClassView").hide();
        $("#prodRankView").hide();
        $("#prodPayFgView").hide();
        $("#prodHourView").hide();
        $("#prodDayView").show();
        $("#prodPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodDayCtrl");
        scope.flex.refresh();
    };
    
    // 포스별 탭 보이기
    $scope.prodPosShow = function () {
        $("#prodClassTab").removeClass("on");
        $("#prodRankTab").removeClass("on");
        $("#prodPayFgTab").removeClass("on");
        $("#prodHourTab").removeClass("on");
        $("#prodDayTab").removeClass("on");
        $("#prodPosTab").addClass("on");
        
        $("#prodClassView").hide();
        $("#prodRankView").hide();
        $("#prodPayFgView").hide();
        $("#prodHourView").hide();
        $("#prodDayView").hide();
        $("#prodPosView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("prodPosCtrl");
        scope.flex.refresh();
    };
}]);