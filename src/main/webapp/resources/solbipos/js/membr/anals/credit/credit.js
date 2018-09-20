/****************************************************************
 *
 * 파일명 : credit.js
 * 설  명 : 모바일쿠폰 JavaScript
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
app.controller('creditCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('creditCtrl', $scope, $http, true));
  // comboBox 초기화
  $scope._setComboData("srchArrayCombo", sysStatFgComboData);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    // $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    // $scope.mobileCouponDcFgDataMap = new wijmo.grid.DataMap(mobileCouponDcFg, 'value', 'name');
    // $scope.mobileCouponApplyFgDataMap = new wijmo.grid.DataMap(mobileCouponApplyFg, 'value', 'name');

    $scope.searchCredit();
  };

  $scope.$on("creditCtrl", function(event, data) {
    $scope.searchCredit();
    event.preventDefault();
  });

  // 후불회원 그리드 조회
  $scope.searchCredit = function(){
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "credit/getCreditMemberList.sb", params, function() {}, false);
  };

}]);
