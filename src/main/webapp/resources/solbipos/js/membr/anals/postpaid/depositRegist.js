/****************************************************************
 *
 * 파일명 : depositRegist.js
 * 설  명 : 외상액 입금 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.14     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('depositRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('depositRegistCtrl', $scope, $http, true));

  // 입금 회원
  $scope.depositMember;

  $scope.$on("depositRegistCtrl", function(event, data) {
    console.log(data);

    $scope.depositMember = data;
    event.preventDefault();
  });

  // 외상입금 처리
  $scope.deposit = function(){

    if( $("#postpaidAmt").val() === null || $("#postpaidAmt").val() === "" || $("#postpaidAmt").val() === "0") {
      $scope._popMsg(messages["postpaid.request.postpaidAmt"]);
      return false;
    }

    var params = {};

    params.storeCd = $scope.depositMember.storeCd;
    params.membrNo = $scope.depositMember.membrNo;
    params.postpaidAmt = $scope.postpaidAmt;

    $scope._postJSONSave.withPopUp( "/membr/anals/postpaid/deposit/saveDeposit.sb", params, function(response){
    });

  };
}]);


