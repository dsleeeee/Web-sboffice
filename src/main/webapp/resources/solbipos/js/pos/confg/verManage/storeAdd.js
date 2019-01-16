/****************************************************************
 *
 * 파일명 : storeAdd.js
 * 설  명 : 포스버전관리 > 매장추가 상세보기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.01.05    김지은      1.0           Angular방식으로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 탭 변경
function changeTab(){
  $scope.storeAddLayer.hide();
  $scope.versionInfoDetailLayer.show();
}

// 조회
function search(){
  var scope = agrid.getScope("addStoreCtrl");
  scope._pageView('addStoreCtrl', 1);
}

/**********************************************************************
 *  적용매장 그리드
 **********************************************************************/
app.controller('addStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('addStoreCtrl', $scope, $http, true));

  // 조회조건
  // $scope.store;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  // 조회 버튼 클릭
  $scope.$on("addStoreCtrl", function(event, data) {
    $scope.addStoreSearch();

    event.preventDefault();
  });

  // 적용매장 목록 조회
  $scope.addStoreSearch = function(){

    var params = {};
    var scope  = agrid.getScope('verManageCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    if( !isEmptyObject($scope.store)){
      params = $scope.store;
    }

    if( isEmptyObject($('#srchHqOfficeCd').val()) &&  isEmptyObject($('#srchHqOfficeNm').val()) ){
      $scope._popMsg("본사코드나 본사명을 입력해주세요.");
      return false;
    }

    params.verSerNo    = ver;
    params.searchSatus = 'Y';
    params.hqOfficeCd = $("#srchHqOfficeCd").val();
    params.hqOfficeNm = $("#srchHqOfficeNm").val();
    params.storeCd = $("#srchStoreCd").val();
    params.storeNm = $("#srchStoreNm").val();

    $scope._inquiryMain("/pos/confg/verManage/applcStore/srchStoreList.sb", params, function() {
      // 적용매장 조회 후, 미적용 매장 조회
      var allStoreScope = agrid.getScope("allStoreCtrl");
      allStoreScope._pageView('allStoreCtrl', 1);

    }, false);
  };

  // 삭제
  $scope.delete = function(){

    var params = new Array();
    var scope  = agrid.getScope('verManageCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].verSerNo = ver;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/pos/confg/verManage/applcStore/removeStore.sb", params, function(){
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("addStoreCtrl");
      addStoreScope._broadcast('addStoreCtrl');
    });
  };

}]);


/**********************************************************************
 *  미적용매장 그리드
 **********************************************************************/
app.controller('allStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('allStoreCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  // 조회 버튼 클릭
  $scope.$on("allStoreCtrl", function(event, data) {
    $scope.allStoreSearch();
    event.preventDefault();
  });

  // 적용매장 목록 조회
  $scope.allStoreSearch = function(){

    var params = {};
    var scope  = agrid.getScope('verManageCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    params.verSerNo    = ver;
    params.searchSatus = 'N';
    params.hqOfficeCd = $("#srchHqOfficeCd").val();
    params.hqOfficeNm = $("#srchHqOfficeNm").val();
    params.storeCd = $("#srchStoreCd").val();
    params.storeNm = $("#srchStoreNm").val();

    $scope._inquiryMain("/pos/confg/verManage/applcStore/srchStoreList.sb", params, function() {
    }, false);
  };


  // 저장
  $scope.save = function(){

    var params = new Array();
    var scope  = agrid.getScope('verManageCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].verSerNo = ver;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    // console.log('save params',params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/pos/confg/verManage/applcStore/regist.sb", params, function(){
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("addStoreCtrl");
      addStoreScope._broadcast('addStoreCtrl');
    });
  };
}]);
