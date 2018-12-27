/****************************************************************
 *
 * 파일명 : prodStoreRegist.js
 * 설  명 : 상품적용 매장등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.04     김지은      1.0
 *
 * **************************************************************/

/**
 *  상품적용매장 그리드 생성
 */
app.controller('regStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('regStoreCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 등록 매장 그리드 조회
  $scope.$on("regStoreCtrl", function(event, data) {
    $scope.searchRegStore();
    // 등록상품 조회 후, 미등록상품 조회
    var noRegStoreGrid = agrid.getScope("noRegStoreCtrl");
    noRegStoreGrid._pageView('noRegStoreCtrl', 1);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 등록된 매장 조회
  $scope.searchRegStore = function(){
    var prodScope     = agrid.getScope("prodCtrl");
    var params        = {};

    params.storeCd    = '';
    params.storeNm    = '';
    params.prodCd     = prodScope.getProdInfo().prodCd;
    params.storeRegFg = 'Y';

    $scope._inquirySub("/base/prod/prod/prod/getRegStoreList.sb", params, function() {}, false);
  };

  // 등록 매장 삭제
  $scope.delete = function(){
    var prodScope = agrid.getScope("prodCtrl");
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].prodCd = prodScope.getProdInfo().prodCd;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/base/prod/prod/prod/deleteProdStore.sb", params, function(){ $scope.allSearch() });
  };

  // 판매가 변경
  $scope.changeSaleUprc = function(){

    // 그리드 변경된 건 커밋
    $scope.flex.collectionView.commitEdit();

    var prodScope = agrid.getScope("prodCtrl");
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

      var saleUprcB = $scope.flex.collectionView.items[i].saleUprcB; // 기존 판매가
      var saleUprc = $scope.flex.collectionView.items[i].saleUprc; // 현재 판매가

      if(saleUprc !== saleUprcB){
        $scope.flex.collectionView.items[i].prodCd = prodScope.getProdInfo().prodCd;
        params.push($scope.flex.collectionView.items[i]);
      }
    }
    $scope._save("/base/prod/prod/prod/updateStoreSaleUprc.sb", params, function(){ $scope.allSearch() });
  };

  // 매장 삭제 완료 후처리
  $scope.allSearch = function () {
    $scope.searchRegStore();
    var noRegStoreGrid = agrid.getScope("noRegStoreCtrl");
    noRegStoreGrid.searchNoRegStore();
  };

}]);

/**
 *  상품 미적용 매장 그리드 생성
 */
app.controller('noRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('noRegStoreCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 미등록 매장 그리드 조회
  $scope.$on("noRegStoreCtrl", function(event, data) {
    $scope.searchNoRegStore();
    event.preventDefault();
  });

  // 미등록 매장 조회
  $scope.searchNoRegStore = function(){

    var prodScope     = agrid.getScope("prodCtrl");
    var params        = {};

    params.storeCd    = $("#srchStoreCd").val();
    params.storeNm    = $("#srchStoreNm").val();
    params.prodCd     = prodScope.getProdInfo().prodCd;
    params.storeRegFg = 'N';

    $scope._inquirySub("/base/prod/prod/prod/getRegStoreList.sb", params, function() {}, false);
  };

  // 매장 등록
  $scope.regist = function() {

    var prodScope = agrid.getScope("prodCtrl");
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].prodCd = prodScope.getProdInfo().prodCd;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/base/prod/prod/prod/insertProdStore.sb", params, function(){ $scope.allSearch() });
  };

  // 매장 등록 완료 후처리
  $scope.allSearch = function () {
    $scope.searchNoRegStore();
    var regStoreGrid = agrid.getScope("regStoreCtrl");
    regStoreGrid.searchRegStore();
  };
}]);

