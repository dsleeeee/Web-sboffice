/****************************************************************
 *
 * 파일명 : taxBillRequest.js
 * 설  명 : 세금계산서발행요청 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.03     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**
 *  세금계산서발행요청 등록
 */
app.controller('requestTaxBillCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('requestTaxBillCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.save();
  };

  $scope.requestMember;

  // 화면 오픈
  $scope.$on("requestTaxBillCtrl", function(event, data) {

    $scope.$apply(function() {
      $scope.requestMember     = data;
      $scope.memberInfo = '[' + $scope.requestMember.membrNo + '] '+$scope.requestMember.membrNm;
      $scope.balance    = addComma($scope.requestMember.postpaidBalAmt) + ' 원';
      $scope.requestAmt = "";

    });

    event.preventDefault();
  });

  // 세금계산서 발행 요청
  $scope.request = function(){

    if(isEmptyObject($scope.requestAmt) ) {
      $scope._popMsg(messages["postpaid.require.input.taxBillAmt"]);
      return false;
    }

    if( $scope.requestAmt >  $scope.requestMember.postpaidBalAmt) {
      $scope._popMsg(messages["postpaid.taxBillAmt.not.overTot"]);
      return false;
    }

    var params = $scope.requestMember;
    params.requestAmt = $scope.requestAmt;
    params.remark = $scope.remark;
    // console.log('remark : '+ $scope.remark)

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._postJSONSave.withPopUp( "/application/pos/posPostpaid/posPostpaid/saveTaxBillRequet.sb", params, function(response){
      $scope._popMsg(messages["cmm.saveSucc"]);
      $scope.requestMember = null;
      $scope.remark = null;
      $scope.close();
    });
  };

  // 팝업 닫기
  $scope.close = function(){
    $scope.requestTaxBillLayer.hide();
  };
}]);
