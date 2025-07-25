/****************************************************************
 *
 * 파일명 : couponStore.js
 * 설  명 : 쿠폰 매장등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.05     김지은      1.0
 *
 * **************************************************************/


/**
 *  쿠폰 등록 매장 그리드 생성
 */
app.controller('regStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('regStoreCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("sysStatFg", sysStatFg); // 매장상태구분

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
      $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };
  // 쿠폰 등록 매장 그리드 조회
  $scope.$on("regStoreCtrl", function(event, data) {
    $scope.searchRegStore();
    // 등록상품 조회 후, 미등록상품 조회
    var noRegCouponGrid = agrid.getScope("noRegStoreCtrl");
    noRegCouponGrid._pageView('noRegStoreCtrl', 1);

    event.preventDefault();
  });

  // 등록된 매장 조회
  $scope.searchRegStore = function(){
    var params = {};
    params.srchStoreCd = '';
    params.srchStoreNm = '';

    params.payClassCd = selectedCouponClass.payClassCd;
    params.coupnCd = selectedCoupon.coupnCd;
    params.coupnEnvstVal = coupnEnvstVal;
    params.storeRegFg = "Y";

    $scope._inquirySub(baseUrl + "store/getStoreList.sb", params, function() {}, false);
  };

  // 등록 매장 삭제
  $scope.delete = function(){

    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].payClassCd = selectedCouponClass.payClassCd;
        $scope.flex.collectionView.items[i].coupnCd = selectedCoupon.coupnCd;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "store/deleteCouponStore.sb", params, function(){ $scope.allSearch() });
  };

  // 매장 삭제 완료 후처리
  $scope.allSearch = function () {
    $scope.searchRegStore();
    var noRegCouponGrid = agrid.getScope("noRegStoreCtrl");
    noRegCouponGrid._pageView('noRegStoreCtrl', 1);
  };

}]);

/**
 *  쿠폰 미등록 매장 그리드 생성
 */
app.controller('noRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('noRegStoreCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
      $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  // 쿠폰 미등록 매장 그리드 조회
  $scope.$on("noRegStoreCtrl", function(event, data) {
    $scope.searchNoRegStore();

    event.preventDefault();
  });

  // 미등록 매장 조회
  $scope.searchNoRegStore = function(){
    var regStoreScope  = agrid.getScope('regStoreCtrl');
    var params = {};
    params.srchStoreCd = $("#srchCoupnStoreCd").val();
    params.srchStoreNm = $("#srchCoupnStoreNm").val();
    params.sysStatFg = regStoreScope.sysStatFgCombo.selectedValue;
    params.payClassCd = selectedCouponClass.payClassCd;
    params.coupnCd = selectedCoupon.coupnCd;
    params.storeRegFg = "N";

    $scope._inquirySub(baseUrl + "store/getStoreList.sb", params, function() {}, false);
  };

  // 매장 등록
  $scope.regist = function() {
   var params = [];
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].payClassCd = selectedCouponClass.payClassCd;
        $scope.flex.collectionView.items[i].coupnCd = selectedCoupon.coupnCd;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "store/registCouponStore.sb", params, function(){ $scope.allSearch() });
  };

  // 매장 등록 완료 후처리
  $scope.allSearch = function () {
    $scope.searchNoRegStore();
    var regCouponGrid = agrid.getScope("regStoreCtrl");
    regCouponGrid._pageView('regStoreCtrl', 1);
  };
}]);

