/**
 * get application
 */
var app = agrid.getApp();

app.controller('saleAnalsTabCtrl', ['$scope', function ($scope) {
  $scope.init = function () {
    $("#dayView").show();
    $("#monthView").hide();
  };

  // 일별 탭 보이기
  $scope.dayShow = function () {
    $("#dayTab").addClass("on");
    $("#monthTab").removeClass("on");

    $("#dayView").show();
    $("#monthView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("dayCtrl");
    scope.flex.refresh();
  };

  // 월별 탭 보이기
  $scope.monthShow = function () {
    $("#dayTab").removeClass("on");
    $("#monthTab").addClass("on");

    $("#dayView").hide();
    $("#monthView").show();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("monthCtrl");
    scope.flex.refresh();
  };
}]);