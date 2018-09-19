/****************************************************************
 *
 * 파일명 : mobileCoupon.js
 * 설  명 : 모바일쿠폰 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.18     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품권분류등록 그리드 생성
 */
app.controller('mCouponClassCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('mCouponClassCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.mobileCouponDcFgDataMap = new wijmo.grid.DataMap(mobileCouponDcFg, 'value', 'name');
    $scope.mobileCouponApplyFgDataMap = new wijmo.grid.DataMap(mobileCouponApplyFg, 'value', 'name');

    $scope.searchMobileCouponClass();

  };

  $scope.$on("mCouponClassCtrl", function(event, data) {
    $scope.searchMobileCouponClass();
    event.preventDefault();
  });

  // 상품권 분류 그리드 조회
  $scope.searchMobileCouponClass = function(){
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "class/getMobileCouponClassList.sb", params, function() {}, false);
  };

  // 상품권 분류 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.payClassCd="자동채번"
    params.serNoYn = "N";
    params.useYn = "Y";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 상품권분류 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/saveMobileCouponClassList.sb", params, function(){ $scope.allSearch() });
  };

  // 상품권 삭제 완료 후처리 (상품권분류 재조회)
  $scope.allSearch = function () {
    $scope.searchMobileCouponClass();
  };
}]);
