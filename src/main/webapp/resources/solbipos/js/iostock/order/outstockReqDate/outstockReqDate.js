/**
 * get application
 */
var app = agrid.getApp();

app.controller('outstockReqDateTabCtrl', ['$scope', function ($scope) {
  $scope.init            = function () {
    $("#daysView").show();
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    var scope = agrid.getScope("daysCtrl");
    scope.searchDaysList();
  });

  // 요일별 탭 보이기
  $scope.daysShow        = function () {
    $("#daysTab").addClass("on");
    $("#specificTab").removeClass("on");
    $("#reqDateCopyTab").removeClass("on");

    $("#daysView").show();
    $("#specificView").hide();
    $("#reqDateCopyView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("daysCtrl");
    scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
    scope.searchDaysList();
    scope.flex.refresh();
  };
  // 특정일 탭 보이기
  $scope.specificShow    = function () {
    $("#daysTab").removeClass("on");
    $("#specificTab").addClass("on");
    $("#reqDateCopyTab").removeClass("on");

    $("#daysView").hide();
    $("#specificView").show();
    $("#reqDateCopyView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("specificCtrl");
    scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
    scope.searchspecificDateList();
    scope.flex.refresh();
  };
  // 요청일복사 탭 보이기
  $scope.reqDateCopyShow = function () {
    $("#daysTab").removeClass("on");
    $("#specificTab").removeClass("on");
    $("#reqDateCopyTab").addClass("on");

    $("#daysView").hide();
    $("#specificView").hide();
    $("#reqDateCopyView").show();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("reqDateCopyDaysCtrl");
    scope.flex.refresh();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("reqDateCopySpecificCtrl");
    scope.flex.refresh();
  };

}]);
