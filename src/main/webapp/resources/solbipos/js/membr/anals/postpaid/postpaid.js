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

    if($scope.orgnFg === 'H' && params.storeCds  === '') {
      $scope._popMsg(messages["postpaid.require.selectStore"]);
      return false;
    }

    $scope._inquiryMain(baseUrl + "postpaid/getPostpaidMemberList.sb", params, function() {}, false);
  };

  // 외상입금 팝업
  $scope.deposit = function(){
    var popup = $scope.depositLayer;
    popup.show(true, function (s) {
    });
  };

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeShow = function () {
    $scope._broadcast('storeCtrl');
  };
}]);
