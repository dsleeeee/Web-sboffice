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

app.controller('taxBillDepositCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('taxBillDepositCtrl', $scope, $http, true));

  // 선택한 세금계산서
  $scope.selectedTaxBill;

  $scope.$on("taxBillDepositCtrl", function(event, data) {
    event.preventDefault();
  });

  $scope.searchTaxBill = function(){
    $scope.searchTaxBillLayer.show(true, function(){
      var taxBillScope = agrid.getScope('searchTaxBillCtrl');
      $scope.$apply(function() {
        $scope.selectedTaxBill = taxBillScope.getTaxBill();
      });
      // console.log('$scope.selectedTaxBill', $scope.selectedTaxBill);
    });
  };

  // 세금계산서 발행 완료, 외상 입금 동시에 처리
  $scope.completeTaxBill = function(){
    var params = {};
    params = $scope.selectedTaxBill;
    console.log('params', params);
    $scope._postJSONSave.withPopUp( "/membr/anals/postpaid/deposit/saveTaxBillComplete.sb", params, function(response){
      // console.log('taxbill - response', response);
      $scope.taxBillDepositLayer.hide();
    });
  };


  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 세금계산서 목록 팝업 핸들러 추가
    $scope.searchTaxBillLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('searchTaxBillCtrl');
      }, 50)
    });
  });
}]);


