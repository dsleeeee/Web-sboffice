/****************************************************************
 *
 * 파일명 : salePriceHistory.js
 * 설  명 : 가격관리 >판매가변경이력 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.28     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 판매가관리 그리드 생성
 */
app.controller('salePriceHistoryCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('salePriceHistoryCtrl', $scope, $http, false));
  $scope.init = function () {
    $("#storeSalePriceHistoryView").show();
    $("#hqSalePriceHistoryView").hide();
  };

  // 매장판매가변경이력
  $scope.storeSalePriceHistoryShow = function(){
      $("#storeSalePriceHistoryTab").addClass("on");
      $("#hqSalePriceHistoryTab").removeClass("on");
      $("#storeSalePriceHistoryView").show();
      $("#hqSalePriceHistoryView").hide();

      // angular 그리드 hide 시 깨지므로 refresh()
      var scope = agrid.getScope("storeSalePriceHistoryCtrl");
      scope.flex.refresh();
  };

  // 본사판매가변경이력
  $scope.hqSalePriceHistoryShow = function(){
    $("#storeSalePriceHistoryTab").removeClass("on");
    $("#hqSalePriceHistoryTab").addClass("on");
    $("#storeSalePriceHistoryView").hide();
    $("#hqSalePriceHistoryView").show();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("hqSalePriceHistoryCtrl");
    scope.flex.refresh();
  };
}]);

