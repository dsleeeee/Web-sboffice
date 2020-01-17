/**
 * get application
 */
var app = agrid.getApp();

app.controller('dayCtrl', ['$scope', function ($scope) {
  $scope.init = function () {
    $("#dayTotalView").show();
    $("#dayDcView").hide();
    $("#dayTaxView").hide();
    $("#dayTimeView").hide();
    $("#dayProdClassView").hide();
    $("#dayCornerView").hide();
    $("#dayTableView").hide();
    $("#dayPosView").hide();
  };


  // 일별종합별 탭 보이기
  $scope.dayTotalShow = function () {
    $("#dayTotalTab").addClass("on");
    $("#dayDcTab").removeClass("on");
    $("#dayTaxTab").removeClass("on");
    $("#dayTimeTab").removeClass("on");
    $("#dayProdClassTab").removeClass("on");
    $("#dayCornerTab").removeClass("on");
    $("#dayTableTab").removeClass("on");
    $("#dayPosTab").removeClass("on");

    $("#dayTotalView").show();
    $("#dayDcView").hide();
    $("#dayTaxView").hide();
    $("#dayTimeView").hide();
    $("#dayProdClassView").hide();
    $("#dayCornerView").hide();
    $("#dayTableView").hide();
    $("#dayPosView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("dayTotalCtrl");
    scope.flex.refresh();
  };


  // 할인구분별 탭 보이기
  $scope.dcShow = function () {
    $("#dayTotalTab").removeClass("on");
    $("#dayDcTab").addClass("on");
    $("#dayTaxTab").removeClass("on");
    $("#dayTimeTab").removeClass("on");
    $("#dayProdClassTab").removeClass("on");
    $("#dayCornerTab").removeClass("on");
    $("#dayTableTab").removeClass("on");
    $("#dayPosTab").removeClass("on");

    $("#dayTotalView").hide();
    $("#dayDcView").show();
    $("#dayTaxView").hide();
    $("#dayTimeView").hide();
    $("#dayProdClassView").hide();
    $("#dayCornerView").hide();
    $("#dayTableView").hide();
    $("#dayPosView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("dayDcCtrl");
    scope.flex.refresh();
  };


  // 과면세별 탭 보이기
  $scope.taxShow = function () {
    $("#dayTotalTab").removeClass("on");
    $("#dayDcTab").removeClass("on");
    $("#dayTaxTab").addClass("on");
    $("#dayTimeTab").removeClass("on");
    $("#dayProdClassTab").removeClass("on");
    $("#dayCornerTab").removeClass("on");
    $("#dayTableTab").removeClass("on");
    $("#dayPosTab").removeClass("on");

    $("#dayTotalView").hide();
    $("#dayDcView").hide();
    $("#dayTaxView").show();
    $("#dayTimeView").hide();
    $("#dayProdClassView").hide();
    $("#dayCornerView").hide();
    $("#dayTableView").hide();
    $("#dayPosView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("dayTaxCtrl");
    scope.flex.refresh();
  };


  // 시간대별 탭 보이기
  $scope.timeShow = function () {
    $("#dayTotalTab").removeClass("on");
    $("#dayDcTab").removeClass("on");
    $("#dayTaxTab").removeClass("on");
    $("#dayTimeTab").addClass("on");
    $("#dayProdClassTab").removeClass("on");
    $("#dayCornerTab").removeClass("on");
    $("#dayTableTab").removeClass("on");
    $("#dayPosTab").removeClass("on");

    $("#dayTotalView").hide();
    $("#dayDcView").hide();
    $("#dayTaxView").hide();
    $("#dayTimeView").show();
    $("#dayProdClassView").hide();
    $("#dayCornerView").hide();
    $("#dayTableView").hide();
    $("#dayPosView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("dayTimeCtrl");
    scope.flex.refresh();
  };


  // 상품분류별 탭 보이기
  $scope.prodClassShow = function () {
    $("#dayTotalTab").removeClass("on");
    $("#dayDcTab").removeClass("on");
    $("#dayTaxTab").removeClass("on");
    $("#dayTimeTab").removeClass("on");
    $("#dayProdClassTab").addClass("on");
    $("#dayCornerTab").removeClass("on");
    $("#dayTableTab").removeClass("on");
    $("#dayPosTab").removeClass("on");

    $("#dayTotalView").hide();
    $("#dayDcView").hide();
    $("#dayTaxView").hide();
    $("#dayTimeView").hide();
    $("#dayProdClassView").show();
    $("#dayCornerView").hide();
    $("#dayTableView").hide();
    $("#dayPosView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("dayProdClassCtrl");
    scope.flex.refresh();
  };


  // 코너별 탭 보이기
  $scope.cornerShow = function () {
    $("#dayTotalTab").removeClass("on");
    $("#dayDcTab").removeClass("on");
    $("#dayTaxTab").removeClass("on");
    $("#dayTimeTab").removeClass("on");
    $("#dayProdClassTab").removeClass("on");
    $("#dayCornerTab").addClass("on");
    $("#dayTableTab").removeClass("on");
    $("#dayPosTab").removeClass("on");

    $("#dayTotalView").hide();
    $("#dayDcView").hide();
    $("#dayTaxView").hide();
    $("#dayTimeView").hide();
    $("#dayProdClassView").hide();
    $("#dayCornerView").show();
    $("#dayTableView").hide();
    $("#dayPosView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("cornerCtrl");
    scope.flex.refresh();
  };

  // 외식테이블 탭 보이기
  $scope.tableShow = function () {
     $("#dayTotalTab").removeClass("on");
     $("#dayDcTab").removeClass("on");
     $("#dayTaxTab").removeClass("on");
     $("#dayTimeTab").removeClass("on");
     $("#dayProdClassTab").removeClass("on");
     $("#dayCornerTab").removeClass("on");
     $("#dayTableTab").addClass("on");
     $("#dayPosTab").removeClass("on");

     $("#dayTotalView").hide();
     $("#dayDcView").hide();
     $("#dayTaxView").hide();
     $("#dayTimeView").hide();
     $("#dayProdClassView").hide();
     $("#dayCornerView").hide();
     $("#dayTableView").show();
     $("#dayPosView").hide();

     // angular 그리드 hide 시 깨지므로 refresh()
     var scope = agrid.getScope("dayTableCtrl");
     scope.flex.refresh();
  };

  // 포스별 탭 보이기
  $scope.posShow = function () {
    $("#dayTotalTab").removeClass("on");
    $("#dayDcTab").removeClass("on");
    $("#dayTaxTab").removeClass("on");
    $("#dayTimeTab").removeClass("on");
    $("#dayProdClassTab").removeClass("on");
    $("#dayCornerTab").removeClass("on");
    $("#dayTableTab").removeClass("on");
    $("#dayPosTab").addClass("on");

    $("#dayTotalView").hide();
    $("#dayDcView").hide();
    $("#dayTaxView").hide();
    $("#dayTimeView").hide();
    $("#dayProdClassView").hide();
    $("#dayCornerView").hide();
    $("#dayTableView").hide();
    $("#dayPosView").show();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("dayPosCtrl");
    scope.flex.refresh();
  };

}]);