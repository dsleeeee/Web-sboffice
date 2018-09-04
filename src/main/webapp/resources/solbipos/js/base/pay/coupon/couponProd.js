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
  $scope.initGrid = function (s, e) {
    // 조회 이벤트 발생시킴.
    setTimeout(function() {
      $scope._broadcast('regProdCtrl', true);
    }, 100)
  };

  // 쿠폰분류 그리드 조회
  $scope.$on("regProdCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.coupnEnvstVal = coupnEnvstVal;
    params.prodRegFg = "Y";

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain(baseUrl + "prod/getProdList.sb", params, function() {
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 등록 상품 삭제
  $scope.delete = function(){
    var gridRepresent = agrid.getScope("couponCtrl");
    var selectedRow = gridRepresent.flex.selectedRows[0]._data;
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].coupnCd = selectedRow.coupnCd;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    console.log(params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "prod/deleteCouponProd.sb", params, function(){
      console.log("callback--------------------");
    });
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
    // 조회 이벤트 발생시킴.
    setTimeout(function() {
      $scope._broadcast('noRegProdCtrl', true);
    }, 100)
  };

  // 쿠폰분류 그리드 조회
  $scope.$on("noRegProdCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.coupnEnvstVal = coupnEnvstVal;
    params.prodRegFg = "N";

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain(baseUrl + "prod/getProdList.sb", params, function() {
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품 등록
  $scope.regist = function() {
    var gridRepresent = agrid.getScope("couponCtrl");
    var selectedRow = gridRepresent.flex.selectedRows[0]._data;
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].coupnCd = selectedRow.coupnCd;
        $scope.flex.collectionView.items[i].coupnEnvstVal = coupnEnvstVal;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    console.log(params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "prod/registCouponProd.sb", params, function(){
      console.log("callback--------------------");
    });
  };

}]);

