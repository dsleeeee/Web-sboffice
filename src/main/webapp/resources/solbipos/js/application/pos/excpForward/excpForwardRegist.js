/****************************************************************
 *
 * 파일명 : excpForward.js
 * 설  명 : 예외출고 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.14     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  예외출고 등록
 */
app.controller('registCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('registCtrl', $scope, $http, true));

  $scope.prod;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.save();
  };

  $scope.$on("registCtrl", function(event, data) {
    $scope.$apply(function() {
      console.log('data', data);
      $scope.prod = data;
      $scope.prod.qtIo = (data.inQty - data.totSaleQty ); // 입고량 - 판매수량
      $scope.prod.prevQtIo = data.qtIo; // 이전 예외출고 수량
    });
    event.preventDefault();
  });

  // 예외출고 수량 저장
  $scope.save = function(){

    console.log('PARAM', $scope.prod);

    if($scope.prod.prevQtIo > 0) {
      $scope._popConfirm("이미 등록된 예외출고가 있습니다.<br>지금 등록하시면 먼저 등록한 예외출고가 삭제됩니다. <br>진행하시겠습니까? ", function(){
        $scope.excpForward();
      });
    } else {
      $scope.excpForward();
    }
  };

  // 예외출고 처리
  $scope.excpForward = function(){
    var params = $scope.prod;
    $scope._postJSONSave.withPopUp( "/application/pos/excpForward/excpForward/saveExcpForwardProduct.sb", params, function(response){
      // console.log('response', response);
      $scope.excpForwardRegistLayer.hide();
    } );
  };

}]);


