/****************************************************************
 *
 * 파일명 : bizHour.js
 * 설  명 : 매장영업시간관리 탭 JavaScript
 *
 *    수정일      수정자      Version
 * ------------  ---------   -------------
 * 2026.10.01     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('bizHourTabCtrl', ['$scope', function ($scope) {
  $scope.init = function () {
    $("#daysView").show();
  };

  // 요일별 탭 보이기
  $scope.daysShow = function () {
    $("#daysTab").addClass("on");
    $("#specificTab").removeClass("on");

    $("#daysView").show();
    $("#specificView").hide();

    // angular 그리드 hide 시 깨지므로 refresh() (탭 전환 시 재조회 안 함)
    var scope = agrid.getScope("daysCtrl");
    scope.flex.refresh();
  };

  // 특정일 탭 보이기
  $scope.specificShow = function () {
    $("#daysTab").removeClass("on");
    $("#specificTab").addClass("on");

    $("#daysView").hide();
    $("#specificView").show();

    // angular 그리드 hide 시 깨지므로 refresh() (탭 전환 시 재조회 안 함)
    var scope = agrid.getScope("specificCtrl");
    scope.flex.refresh();
  };

}]);
