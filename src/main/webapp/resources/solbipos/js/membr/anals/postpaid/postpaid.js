/****************************************************************
 *
 * 파일명 : postpaid.js
 * 설  명 : 외상입금/후불 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.20     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  후불회원 그리드 생성
 */
app.controller('postpaidCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('postpaidCtrl', $scope, $http, true));

  $scope.orgnFg = gvOrgnFg;

  if($scope.orgnFg === 'S') {
    $scope.storeCds = gvStoreCd;
  }

 // todo 검색조건에 입금구분넣고 회원번호, 회원명 like검색

  // comboBox 초기화
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.postpaidFgDataMap = new wijmo.grid.DataMap(postpaidFgData, 'value', 'name');
    $scope.postpaidPayFgDataMap = new wijmo.grid.DataMap(postpaidPayFgData, 'value', 'name');
  };

  $scope.$on("postpaidCtrl", function(event, data) {
    $scope.searchPostpaid();
    event.preventDefault();
  });

  // 후불회원 그리드 조회
  $scope.searchPostpaid = function(){
    var params      = {};
    params.storeCds = $("#storeCd").val();
    $scope._inquiryMain("/membr/anals/postpaid/postpaid/getPostpaidMemberList.sb", params, function() {}, false);
  };

  // 외상입금 팝업
  $scope.deposit = function(){
    $scope.depositLayer.show(true, function(){
      $scope._broadcast("postpaidCtrl");
    });
  };

  // 세금계산서 발행완료처리 팝업
  $scope.taxBillDeposit = function(){
    $scope.taxBillDepositLayer.show(true, function(){
      $scope._broadcast("postpaidCtrl");
    });
  };

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeShow = function () {
    $scope._broadcast('storeCtrl');
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 외상입금 팝업 핸들러 추가
    $scope.depositLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('depositCtrl');
      }, 50)
    });
    // 세금계산서 발행완료처리 팝업 핸들러 추가
    $scope.depositLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('taxBillDepositCtrl');
      }, 50)
    });
  });

}]);
