/****************************************************************
 *
 * 파일명 : couponProd.js
 * 설  명 : 쿠폰 상품등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.17     김지은      1.0
 *
 * **************************************************************/


/**
 *  쿠폰 등록 상품 그리드 생성
 */
app.controller('regProdCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('regProdCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 쿠폰등록 상품 그리드 조회
  $scope.$on("regProdCtrl", function(event, data) {
    $scope.searchRegProd();
    // 등록상품 조회 후, 미등록상품 조회
    var noRegCouponGrid = agrid.getScope("noRegProdCtrl");
    noRegCouponGrid.searchNoRegProd();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 등록된 상품 조회
  $scope.searchRegProd = function(){
    var couponGrid = agrid.getScope("couponCtrl");
    if(couponGrid.flex.selectedItems.length > 0 ){
      var params = {};
      params.coupnCd = couponGrid.flex.selectedItems[0].coupnCd;
      params.coupnEnvstVal = coupnEnvstVal;
      params.prodRegFg = "Y";
      // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
      $scope._inquirySub(baseUrl + "prod/getProdList.sb", params, function() {}, false);
    }
  };

  // 등록 상품 삭제
  $scope.delete = function(){
    var couponGrid = agrid.getScope("couponCtrl");
    var selectedRow = couponGrid.flex.selectedRows[0]._data;
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].coupnCd = selectedRow.coupnCd;
        $scope.flex.collectionView.items[i].coupnEnvstVal = coupnEnvstVal;
        params.push($scope.flex.collectionView.items[i]);
      }
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    // $scope._save(baseUrl + "prod/deleteCouponProd.sb", params, function(){ $scope.allSearch() });
    $http({
      method: 'POST', //방식
      url: baseUrl + "prod/deleteCouponProd.sb",
      data: JSON.stringify(params), /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      $scope.allSearch()
    }, function errorCallback(response) {
      s_alert.pop(response.data.message);
      return false;
    });
  };

  // 상품 삭제 완료 후처리
  $scope.allSearch = function () {
    $scope.searchRegProd();
    var noRegProdGrid = agrid.getScope("noRegProdCtrl");
    noRegProdGrid.searchNoRegProd();
    // 쿠폰그리드 조회
    var couponGrid = agrid.getScope("couponCtrl");
    couponGrid.searchCoupon(selectedCouponClass);
  };
}]);

/**
 *  쿠폰 미등록 상품 그리드 생성
 */
app.controller('noRegProdCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('noRegProdCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 미등록 상품 그리드 조회
  $scope.$on("noRegProdCtrl", function(event, data) {
    $scope.searchNoRegProd();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 미등록 상품
  $scope.searchNoRegProd = function(){
    var couponGrid = agrid.getScope("couponCtrl");
    if(couponGrid.flex.selectedItems.length > 0) {
      var selectedCoupnCd = couponGrid.flex.selectedItems[0].coupnCd;
      // 파라미터
      var params = {};
      params.coupnCd = selectedCoupnCd;
      params.coupnEnvstVal = coupnEnvstVal;
      params.prodRegFg = "N";
      // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
      $scope._inquirySub(baseUrl + "prod/getProdList.sb", params, function() {}, false);
    }
  };

  // 상품 등록
  $scope.regist = function() {
    var couponGrid = agrid.getScope("couponCtrl");
    var selectedRow = couponGrid.flex.selectedRows[0]._data;
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].coupnCd = selectedRow.coupnCd;
        $scope.flex.collectionView.items[i].coupnEnvstVal = coupnEnvstVal;
        params.push($scope.flex.collectionView.items[i]);
      }
    }
    // 저장
    $http({
      method: 'POST', //방식
      url: baseUrl + "prod/registCouponProd.sb",
      data: JSON.stringify(params), /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      $scope.allSearch()
    }, function errorCallback(response) {
      s_alert.pop(response.data.message);
      return false;
    });

  };

  // 상품 등록 완료 후처리 (상품수량 변화)
  $scope.allSearch = function () {
    $scope.searchNoRegProd();
    var regProdGrid = agrid.getScope("regProdCtrl");
    regProdGrid.searchRegProd();
    // 쿠폰그리드 조회
    var couponGrid = agrid.getScope("couponCtrl");
    couponGrid.searchCoupon(selectedCouponClass);
  };
}]);

