/****************************************************************
 *
 * 파일명 : prepaid.js
 * 설  명 : 선불 입금/사용 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.01     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  선불회원 그리드 생성
 */
app.controller('prepaidCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prepaidCtrl', $scope, $http, true));

  $scope.orgnFg = gvOrgnFg;

  if($scope.orgnFg === 'S') {
    $scope.storeCds = gvStoreCd;
  }

  // comboBox 초기화
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("srchArrayCombo", arrayData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.prepaidInFgDataMap = new wijmo.grid.DataMap(prepaidInFgData, 'value', 'name');
    $scope.prepaidPayFgDataMap = new wijmo.grid.DataMap(prepaidPayFgData, 'value', 'name');
  };

  $scope.$on("prepaidCtrl", function(event, data) {
    $scope.searchPostpaid();
    event.preventDefault();
  });

  // 선불회원 그리드 조회
  $scope.searchPostpaid = function(){

    // 파라미터
    var params = {};
    params.storeCds = $("#storeCd").val();
    params.array = srchArrayCombo.selectedValue;

    if($scope.orgnFg === 'H' && params.storeCds  === '') {
      alert("매장을 선택해주세요.");
      $scope._popMsg(messages["prepaid.require.selectStore"]);
      return false;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain(baseUrl + "prepaid/getPrepaidMemberList.sb", params, function() {}, false);
  };

  // 선불금 충전 팝업
  $scope.charge = function(){
    var popup = $scope.chargeLayer;
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
