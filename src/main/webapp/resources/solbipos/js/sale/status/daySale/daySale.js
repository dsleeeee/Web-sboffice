/****************************************************************
 *
 * 파일명 : daySale.js
 * 설  명 : 매출관리 > 매출현황 > 일자별 JavaScript
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

app.controller('daySaleCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#dayTotalView").show();
        $("#dayDcView").hide();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").hide();
        $("#dayTimeView").hide();
        $("#dayProdClassView").hide();
        $("#dayCornerView").hide();
        $("#dayTableView").hide();
        $("#dayPosView").hide();
        $("#dayEmpCardView").hide();
    };


    // 일별종합별 탭 보이기
    $scope.dayTotalShow = function () {
        $("#dayTotalTab").addClass("on");
        $("#dayDcTab").removeClass("on");
        $("#dayDcBbqTab").removeClass("on");
        $("#dayTaxTab").removeClass("on");
        $("#dayTimeTab").removeClass("on");
        $("#dayProdClassTab").removeClass("on");
        $("#dayCornerTab").removeClass("on");
        $("#dayTableTab").removeClass("on");
        $("#dayPosTab").removeClass("on");
        $("#dayEmpCardTab").removeClass("on");

        $("#dayTotalView").show();
        $("#dayDcView").hide();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").hide();
        $("#dayTimeView").hide();
        $("#dayProdClassView").hide();
        $("#dayCornerView").hide();
        $("#dayTableView").hide();
        $("#dayPosView").hide();
        $("#dayEmpCardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayTotalCtrl");
        scope.flex.refresh();
    };


    // 할인구분별 탭 보이기
    $scope.dcShow = function () {
        $("#dayTotalTab").removeClass("on");
        $("#dayDcTab").addClass("on");
        $("#dayDcBbqTab").removeClass("on");
        $("#dayTaxTab").removeClass("on");
        $("#dayTimeTab").removeClass("on");
        $("#dayProdClassTab").removeClass("on");
        $("#dayCornerTab").removeClass("on");
        $("#dayTableTab").removeClass("on");
        $("#dayPosTab").removeClass("on");
        $("#dayEmpCardTab").removeClass("on");

        $("#dayTotalView").hide();
        $("#dayDcView").show();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").hide();
        $("#dayTimeView").hide();
        $("#dayProdClassView").hide();
        $("#dayCornerView").hide();
        $("#dayTableView").hide();
        $("#dayPosView").hide();
        $("#dayEmpCardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayDcCtrl");
        scope.flex.refresh();
    };


    // 할인구분별(BBQ) 탭 보이기
    $scope.dcBbqShow = function () {
        $("#dayTotalTab").removeClass("on");
        $("#dayDcTab").removeClass("on");
        $("#dayDcBbqTab").addClass("on");
        $("#dayTaxTab").removeClass("on");
        $("#dayTimeTab").removeClass("on");
        $("#dayProdClassTab").removeClass("on");
        $("#dayCornerTab").removeClass("on");
        $("#dayTableTab").removeClass("on");
        $("#dayPosTab").removeClass("on");
        $("#dayEmpCardTab").removeClass("on");

        $("#dayTotalView").hide();
        $("#dayDcView").hide();
        $("#dayDcBbqView").show();
        $("#dayTaxView").hide();
        $("#dayTimeView").hide();
        $("#dayProdClassView").hide();
        $("#dayCornerView").hide();
        $("#dayTableView").hide();
        $("#dayPosView").hide();
        $("#dayEmpCardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayDcBbqCtrl");
        scope.flex.refresh();
    };


    // 과면세별 탭 보이기
    $scope.taxShow = function () {
        $("#dayTotalTab").removeClass("on");
        $("#dayDcTab").removeClass("on");
        $("#dayDcBbqTab").removeClass("on");
        $("#dayTaxTab").addClass("on");
        $("#dayTimeTab").removeClass("on");
        $("#dayProdClassTab").removeClass("on");
        $("#dayCornerTab").removeClass("on");
        $("#dayTableTab").removeClass("on");
        $("#dayPosTab").removeClass("on");
        $("#dayEmpCardTab").removeClass("on");

        $("#dayTotalView").hide();
        $("#dayDcView").hide();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").show();
        $("#dayTimeView").hide();
        $("#dayProdClassView").hide();
        $("#dayCornerView").hide();
        $("#dayTableView").hide();
        $("#dayPosView").hide();
        $("#dayEmpCardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayTaxCtrl");
        scope.flex.refresh();
    };


    // 시간대별 탭 보이기
    $scope.timeShow = function () {
        $("#dayTotalTab").removeClass("on");
        $("#dayDcTab").removeClass("on");
        $("#dayDcBbqTab").removeClass("on");
        $("#dayTaxTab").removeClass("on");
        $("#dayTimeTab").addClass("on");
        $("#dayProdClassTab").removeClass("on");
        $("#dayCornerTab").removeClass("on");
        $("#dayTableTab").removeClass("on");
        $("#dayPosTab").removeClass("on");
        $("#dayEmpCardTab").removeClass("on");

        $("#dayTotalView").hide();
        $("#dayDcView").hide();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").hide();
        $("#dayTimeView").show();
        $("#dayProdClassView").hide();
        $("#dayCornerView").hide();
        $("#dayTableView").hide();
        $("#dayPosView").hide();
        $("#dayEmpCardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayTimeCtrl");
        scope.flex.refresh();
    };


    // 상품분류별 탭 보이기
    $scope.prodClassShow = function () {
        $("#dayTotalTab").removeClass("on");
        $("#dayDcTab").removeClass("on");
        $("#dayDcBbqTab").removeClass("on");
        $("#dayTaxTab").removeClass("on");
        $("#dayTimeTab").removeClass("on");
        $("#dayProdClassTab").addClass("on");
        $("#dayCornerTab").removeClass("on");
        $("#dayTableTab").removeClass("on");
        $("#dayPosTab").removeClass("on");
        $("#dayEmpCardTab").removeClass("on");

        $("#dayTotalView").hide();
        $("#dayDcView").hide();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").hide();
        $("#dayTimeView").hide();
        $("#dayProdClassView").show();
        $("#dayCornerView").hide();
        $("#dayTableView").hide();
        $("#dayPosView").hide();
        $("#dayEmpCardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayProdClassCtrl");
        scope.flex.refresh();
    };


    // 코너별 탭 보이기
    $scope.cornerShow = function () {
        $("#dayTotalTab").removeClass("on");
        $("#dayDcTab").removeClass("on");
        $("#dayDcBbqTab").removeClass("on");
        $("#dayTaxTab").removeClass("on");
        $("#dayTimeTab").removeClass("on");
        $("#dayProdClassTab").removeClass("on");
        $("#dayCornerTab").addClass("on");
        $("#dayTableTab").removeClass("on");
        $("#dayPosTab").removeClass("on");
        $("#dayEmpCardTab").removeClass("on");

        $("#dayTotalView").hide();
        $("#dayDcView").hide();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").hide();
        $("#dayTimeView").hide();
        $("#dayProdClassView").hide();
        $("#dayCornerView").show();
        $("#dayTableView").hide();
        $("#dayPosView").hide();
        $("#dayEmpCardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayCornerCtrl");
        scope.flex.refresh();
    };

    // 외식테이블 탭 보이기
    $scope.tableShow = function () {
        $("#dayTotalTab").removeClass("on");
        $("#dayDcTab").removeClass("on");
        $("#dayDcBbqTab").removeClass("on");
        $("#dayTaxTab").removeClass("on");
        $("#dayTimeTab").removeClass("on");
        $("#dayProdClassTab").removeClass("on");
        $("#dayCornerTab").removeClass("on");
        $("#dayTableTab").addClass("on");
        $("#dayPosTab").removeClass("on");
        $("#dayEmpCardTab").removeClass("on");

        $("#dayTotalView").hide();
        $("#dayDcView").hide();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").hide();
        $("#dayTimeView").hide();
        $("#dayProdClassView").hide();
        $("#dayCornerView").hide();
        $("#dayTableView").show();
        $("#dayPosView").hide();
        $("#dayEmpCardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayTableCtrl");
        scope.flex.refresh();
    };

    // 포스별 탭 보이기
    $scope.posShow = function () {
        $("#dayTotalTab").removeClass("on");
        $("#dayDcTab").removeClass("on");
        $("#dayDcBbqTab").removeClass("on");
        $("#dayTaxTab").removeClass("on");
        $("#dayTimeTab").removeClass("on");
        $("#dayProdClassTab").removeClass("on");
        $("#dayCornerTab").removeClass("on");
        $("#dayTableTab").removeClass("on");
        $("#dayPosTab").addClass("on");
        $("#dayEmpCardTab").removeClass("on");

        $("#dayTotalView").hide();
        $("#dayDcView").hide();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").hide();
        $("#dayTimeView").hide();
        $("#dayProdClassView").hide();
        $("#dayCornerView").hide();
        $("#dayTableView").hide();
        $("#dayPosView").show();
        $("#dayEmpCardView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayPosCtrl");
        scope.flex.refresh();
    };

    // 사원카드별 탭 보이기
    $scope.empCardShow = function () {
        $("#dayTotalTab").removeClass("on");
        $("#dayDcTab").removeClass("on");
        $("#dayDcBbqTab").removeClass("on");
        $("#dayTaxTab").removeClass("on");
        $("#dayTimeTab").removeClass("on");
        $("#dayProdClassTab").removeClass("on");
        $("#dayCornerTab").removeClass("on");
        $("#dayTableTab").removeClass("on");
        $("#dayPosTab").removeClass("on");
        $("#dayEmpCardTab").addClass("on");

        $("#dayTotalView").hide();
        $("#dayDcView").hide();
        $("#dayDcBbqView").hide();
        $("#dayTaxView").hide();
        $("#dayTimeView").hide();
        $("#dayProdClassView").hide();
        $("#dayCornerView").hide();
        $("#dayTableView").hide();
        $("#dayPosView").hide();
        $("#dayEmpCardView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayEmpCardCtrl");
        scope.flex.refresh();
    };

}]);