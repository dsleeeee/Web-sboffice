/****************************************************************
 *
 * 파일명 : monthSale.js
 * 설  명 : 매출관리 > 매출현황 > 월별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.09.25     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('monthSaleCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#monthTotalView").show();
        $("#monthDcView").hide();
        $("#monthTaxView").hide();
        $("#monthTimeView").hide();
        $("#monthProdClassView").hide();
        $("#monthCornerView").hide();
        $("#monthTableView").hide();
        $("#monthPosView").hide();
    };

    // 월별종합 탭 보이기
    $scope.monthTotalShow = function () {
        $("#monthTotalTab").addClass("on");
        $("#monthDcTab").removeClass("on");
        $("#monthTaxTab").removeClass("on");
        $("#monthTimeTab").removeClass("on");
        $("#monthProdClassTab").removeClass("on");
        $("#monthCornerTab").removeClass("on");
        $("#monthTableTab").removeClass("on");
        $("#monthPosTab").removeClass("on");

        $("#monthTotalView").show();
        $("#monthDcView").hide();
        $("#monthTaxView").hide();
        $("#monthTimeView").hide();
        $("#monthProdClassView").hide();
        $("#monthCornerView").hide();
        $("#monthTableView").hide();
        $("#monthPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("monthTotalCtrl");
        scope.flex.refresh();
    };

    // 할인구분별 탭 보이기
    $scope.monthDcShow = function () {
        $("#monthTotalTab").removeClass("on");
        $("#monthDcTab").addClass("on");
        $("#monthTaxTab").removeClass("on");
        $("#monthTimeTab").removeClass("on");
        $("#monthProdClassTab").removeClass("on");
        $("#monthCornerTab").removeClass("on");
        $("#monthTableTab").removeClass("on");
        $("#monthPosTab").removeClass("on");

        $("#monthTotalView").hide();
        $("#monthDcView").show();
        $("#monthTaxView").hide();
        $("#monthTimeView").hide();
        $("#monthProdClassView").hide();
        $("#monthCornerView").hide();
        $("#monthTableView").hide();
        $("#monthPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("monthDcCtrl");
        scope.flex.refresh();
    };

    // 과면세별 탭 보이기
    $scope.monthTaxShow = function () {
        $("#monthTotalTab").removeClass("on");
        $("#monthDcTab").removeClass("on");
        $("#monthTaxTab").addClass("on");
        $("#monthTimeTab").removeClass("on");
        $("#monthProdClassTab").removeClass("on");
        $("#monthCornerTab").removeClass("on");
        $("#monthTableTab").removeClass("on");
        $("#monthPosTab").removeClass("on");

        $("#monthTotalView").hide();
        $("#monthDcView").hide();
        $("#monthTaxView").show();
        $("#monthTimeView").hide();
        $("#monthProdClassView").hide();
        $("#monthCornerView").hide();
        $("#monthTableView").hide();
        $("#monthPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("monthTaxCtrl");
        scope.flex.refresh();
    };

    // 시간대별 탭 보이기
    $scope.monthTimeShow = function () {
        $("#monthTotalTab").removeClass("on");
        $("#monthDcTab").removeClass("on");
        $("#monthTaxTab").removeClass("on");
        $("#monthTimeTab").addClass("on");
        $("#monthProdClassTab").removeClass("on");
        $("#monthCornerTab").removeClass("on");
        $("#monthTableTab").removeClass("on");
        $("#monthPosTab").removeClass("on");

        $("#monthTotalView").hide();
        $("#monthDcView").hide();
        $("#monthTaxView").hide();
        $("#monthTimeView").show();
        $("#monthProdClassView").hide();
        $("#monthCornerView").hide();
        $("#monthTableView").hide();
        $("#monthPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("monthTimeCtrl");
        scope.flex.refresh();
    };

    // 상품분류별 탭 보이기
    $scope.monthProdClassShow = function () {
        $("#monthTotalTab").removeClass("on");
        $("#monthDcTab").removeClass("on");
        $("#monthTaxTab").removeClass("on");
        $("#monthTimeTab").removeClass("on");
        $("#monthProdClassTab").addClass("on");
        $("#monthCornerTab").removeClass("on");
        $("#monthTableTab").removeClass("on");
        $("#monthPosTab").removeClass("on");

        $("#monthTotalView").hide();
        $("#monthDcView").hide();
        $("#monthTaxView").hide();
        $("#monthTimeView").hide();
        $("#monthProdClassView").show();
        $("#monthCornerView").hide();
        $("#monthTableView").hide();
        $("#monthPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("monthProdClassCtrl");
        scope.flex.refresh();
    };

    // 코너별 탭 보이기
    $scope.monthCornerShow = function () {
        $("#monthTotalTab").removeClass("on");
        $("#monthDcTab").removeClass("on");
        $("#monthTaxTab").removeClass("on");
        $("#monthTimeTab").removeClass("on");
        $("#monthProdClassTab").removeClass("on");
        $("#monthCornerTab").addClass("on");
        $("#monthTableTab").removeClass("on");
        $("#monthPosTab").removeClass("on");

        $("#monthTotalView").hide();
        $("#monthDcView").hide();
        $("#monthTaxView").hide();
        $("#monthTimeView").hide();
        $("#monthProdClassView").hide();
        $("#monthCornerView").show();
        $("#monthTableView").hide();
        $("#monthPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("monthCornerCtrl");
        scope.flex.refresh();
    };

    // 외식테이블 탭 보이기
    $scope.monthTableShow = function () {
        $("#monthTotalTab").removeClass("on");
        $("#monthDcTab").removeClass("on");
        $("#monthTaxTab").removeClass("on");
        $("#monthTimeTab").removeClass("on");
        $("#monthProdClassTab").removeClass("on");
        $("#monthCornerTab").removeClass("on");
        $("#monthTableTab").addClass("on");
        $("#monthPosTab").removeClass("on");

        $("#monthTotalView").hide();
        $("#monthDcView").hide();
        $("#monthTaxView").hide();
        $("#monthTimeView").hide();
        $("#monthProdClassView").hide();
        $("#monthCornerView").hide();
        $("#monthTableView").show();
        $("#monthPosView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("monthTableCtrl");
        scope.flex.refresh();
    };

    // 포스별 탭 보이기
    $scope.monthPosShow = function () {
        $("#monthTotalTab").removeClass("on");
        $("#monthDcTab").removeClass("on");
        $("#monthTaxTab").removeClass("on");
        $("#monthTimeTab").removeClass("on");
        $("#monthProdClassTab").removeClass("on");
        $("#monthCornerTab").removeClass("on");
        $("#monthTableTab").removeClass("on");
        $("#monthPosTab").addClass("on");

        $("#monthTotalView").hide();
        $("#monthDcView").hide();
        $("#monthTaxView").hide();
        $("#monthTimeView").hide();
        $("#monthProdClassView").hide();
        $("#monthCornerView").hide();
        $("#monthTableView").hide();
        $("#monthPosView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("monthPosCtrl");
        scope.flex.refresh();
    };

}]);