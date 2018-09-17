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
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 쿠폰 등록 매장 그리드 조회
  $scope.$on("regStoreCtrl", function(event, data) {
    $scope.searchRegStore();
    // 등록상품 조회 후, 미등록상품 조회
    var noRegCouponGrid = agrid.getScope("noRegStoreCtrl");
    noRegCouponGrid.searchNoRegStore();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 등록된 매장 조회
  $scope.searchRegStore = function(){
    var couponClassGrid = agrid.getScope("couponClassCtrl");
    var couponGrid = agrid.getScope("couponCtrl");
    if(couponGrid.flex.selectedItems.length > 0 ){
      var params = {};
      params.payClassCd = couponClassGrid.flex.selectedItems[0].payClassCd;
      params.coupnCd = couponGrid.flex.selectedItems[0].coupnCd;
      params.coupnEnvstVal = coupnEnvstVal;
      params.storeRegFg = "Y";
      // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
      $scope._inquirySub(baseUrl + "store/getStoreList.sb", params, function() {}, false);
    }
  };

  // 등록 매장 삭제
  $scope.delete = function(){
    var couponClassGrid = agrid.getScope("couponClassCtrl");
    var couponGrid = agrid.getScope("couponCtrl");
    var selectedClassRow = couponClassGrid.flex.selectedRows[0]._data;
    var selectedCouponRow = couponGrid.flex.selectedRows[0]._data;
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].payClassCd = selectedClassRow.payClassCd;
        $scope.flex.collectionView.items[i].coupnCd = selectedCouponRow.coupnCd;
        params.push($scope.flex.collectionView.items[i]);


        console.log("del:::: ");
        console.log(params);
      }
    }
    $http({
      method: 'POST', //방식
      url: baseUrl + "store/deleteCouponStore.sb",
      data: JSON.stringify(params), /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      $scope.allSearch()
    }, function errorCallback(response) {
      s_alert.pop(response.data.message);
      return;
    });
  };

  // 매장 삭제 완료 후처리
  $scope.allSearch = function () {
    $scope.searchRegStore();
    var noRegCouponGrid = agrid.getScope("noRegStoreCtrl");
    noRegCouponGrid.searchNoRegStore();
    // 쿠폰그리드 조회
    var couponGrid = agrid.getScope("couponCtrl");
    couponGrid.searchCoupon(selectedCouponClass);
  };

}]);

/**
 *  쿠폰 미등록 매장 그리드 생성
 */
app.controller('noRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('noRegStoreCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 쿠폰 미등록 매장 그리드 조회
  $scope.$on("noRegStoreCtrl", function(event, data) {
    $scope.searchNoRegStore();
    event.preventDefault();
  });

  // 미등록 매장 조회
  $scope.searchNoRegStore = function(){
    var couponClassGrid = agrid.getScope("couponClassCtrl");
    var couponGrid = agrid.getScope("couponCtrl");
    if(couponGrid.flex.selectedItems.length > 0) {
      var params = {};
      params.payClassCd = couponClassGrid.flex.selectedItems[0].payClassCd;
      params.coupnCd = couponGrid.flex.selectedItems[0].coupnCd;
      params.storeRegFg = "N";
      // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
      $scope._inquirySub(baseUrl + "store/getStoreList.sb", params, function() {}, false);
    }
  };

  // 매장 등록
  $scope.regist = function() {
    var couponClassGrid = agrid.getScope("couponClassCtrl");
    var couponGrid = agrid.getScope("couponCtrl");
    var selectedClassRow = couponClassGrid.flex.selectedRows[0]._data;
    var selectedCouponRow = couponGrid.flex.selectedRows[0]._data;
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].payClassCd = selectedClassRow.payClassCd;
        $scope.flex.collectionView.items[i].coupnCd = selectedCouponRow.coupnCd;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    console.log(":: reg");
    console.log(params);
    $http({
      method: 'POST', //방식
      url: baseUrl + "store/registCouponStore.sb",
      data: JSON.stringify(params), /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      $scope.allSearch()
    }, function errorCallback(response) {
      s_alert.pop(response.data.message);
      return;
    });
  };

  // 매장 등록 완료 후처리
  $scope.allSearch = function () {
    $scope.searchNoRegStore();
    var regCouponGrid = agrid.getScope("regStoreCtrl");
    regCouponGrid.searchRegStore();
    // 쿠폰그리드 조회(적용매장 수량 변화)
    var couponGrid = agrid.getScope("couponCtrl");
    couponGrid.searchCoupon(selectedCouponClass);
  };
}]);

