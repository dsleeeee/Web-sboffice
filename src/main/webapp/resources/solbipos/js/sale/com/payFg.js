/**
 * get application
 */
var app = agrid.getApp();

/** 테이블별 매출현황 controller */
app.controller('saleComPayFgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleComPayFgCtrl', $scope, $http, true));

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleComPayFgCtrl", function (event, data) {

	console.log(data);

    $scope.payFgLayer.show(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

}]);
