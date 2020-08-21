/****************************************************************
 *
 * 파일명 : postpaidStoreRegist.js
 * 설  명 : 회원정보관리 > 회원정보등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.09     김지은      1.0
 *
 * **************************************************************/

/**
 *  후불 대상으로 등록된 매장 그리드 생성
 */
app.controller('postpaidStoreRegistCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('postpaidStoreRegistCtrl', $scope, $http, false));

  // 조회조건 콤보박스 데이터 Set
  // $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택 회원
  $scope.selectedMembr;
  $scope.setSelectedMembr = function(membr) {
    $scope.selectedMembr = membr;
  };
  $scope.getSelectedMembr = function(){
    return $scope.selectedMembr;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 후불 대상으로 등록된 매장 목록 조회
  $scope.$on("postpaidStoreRegistCtrl", function(event, data) {

    // 등록매장 조회 후, 미등록매장 조회
    $scope.setSelectedMembr(data);
    $scope.searchRegStore();

    var noRegStoreGrid = agrid.getScope("postpaidStoreNoRegistCtrl");
    noRegStoreGrid.setSelectedMembr(data);
    noRegStoreGrid.searchNoRegStore();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 등록된 매장 조회
  $scope.searchRegStore = function(){

    var params        = {};
    params.regYn      = 'Y';
    params.storeCd    = $scope.selectedMembr.membrOrgnCd;
    params.membrNo   = $scope.selectedMembr.membrNo;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/membr/info/view/postpaid/getPostpaidStoreList.sb", params, function() {}, false);
  };

  // 등록 매장 삭제
  $scope.delete = function(){
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].membeNo = $scope.selectedMembr.membrNo;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length <= 0) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/membr/info/view/postpaid/deletePostpaidStore.sb", params, function(){
      $scope.allSearch()
    });
  };

  // 매장 삭제 완료 후처리
  $scope.allSearch = function () {
    $scope.searchRegStore();
    var noRegCouponGrid = agrid.getScope("postpaidStoreNoRegistCtrl");
    noRegCouponGrid.searchNoRegStore();
  };

}]);



/**
 *  후불 대상으로 등록된 매장 그리드 생성
 */
app.controller('postpaidStoreNoRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('postpaidStoreNoRegistCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  // $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택 회원
  $scope.selectedMembr;
  $scope.setSelectedMembr = function(membr) {
    $scope.selectedMembr = membr;
  };
  $scope.getSelectedMembr = function(){
    return $scope.selectedMembr;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 후불 대상으로 등록된 매장 그리드 조회
  $scope.$on("postpaidStoreNoRegistCtrl", function(event, data) {
    $scope.searchNoRegStore();
    event.preventDefault();
  });

  // 미등록 매장 조회
  $scope.searchNoRegStore = function(){

    var params        = {};
    params.regYn      = 'N';
    params.storeCd    = $scope.selectedMembr.membrOrgnCd;
    params.membrNo    = $scope.selectedMembr.membrNo;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/membr/info/view/postpaid/getPostpaidStoreList.sb", params, function() {}, false);
  };

  // 매장 등록
  $scope.regist = function() {
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].membrNo = $scope.selectedMembr.membrNo;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length <= 0) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }

    // console.log(params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/membr/info/view/postpaid/registPostpaidStore.sb", params, function(){
      $scope.allSearch()
    });
  };

  // 매장 등록 완료 후처리
  $scope.allSearch = function () {
    $scope.searchNoRegStore();
    var regCouponGrid = agrid.getScope("postpaidStoreRegistCtrl");
    regCouponGrid.searchRegStore();
  };

}]);

