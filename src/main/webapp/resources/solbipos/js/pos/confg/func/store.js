/****************************************************************
 *
 * 파일명 : store.js
 * 설  명 : 포스 기능정의 - 기능별 매장등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     김지은      1.0
 * 2019.01.16     김지은      2.0            angular 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 조회
function search(){
  var scope = agrid.getScope("regStoreCtrl");
  scope._pageView('regStoreCtrl', 1);
}

/**********************************************************************
 *  적용매장 그리드
 **********************************************************************/
app.controller('regStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('regStoreCtrl', $scope, $http, true));

  // 조회 버튼 클릭
  $scope.$on("regStoreCtrl", function(event, data) {
    $scope.addStoreSearch();

    event.preventDefault();
  });

  // 적용매장 목록 조회
  $scope.addStoreSearch = function(){

    var params = {};
    var scope  = agrid.getScope('funcCtrl');

    params.fnkeyFg = scope.getSelectedFunc().fnkeyFg;
    params.fnkeyNo = scope.getSelectedFunc().fnkeyNo;
    params.hqOfficeCd = '';
    params.hqOfficeNm = '';
    params.storeCd = '';
    params.storeNm = '';
    params.regYn = 'Y';

    $scope._inquiryMain("/pos/confg/func/func/getFuncStoreList.sb", params, function() {
      // 적용매장 조회 후, 미적용 매장 조회
      var allStoreScope = agrid.getScope("noRegStoreCtrl");
      allStoreScope._pageView('noRegStoreCtrl', 1);

    }, false);
  };

  // 삭제
  $scope.delete = function(){

    var params = new Array();
    var scope  = agrid.getScope('funcCtrl');

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].status = "D";
        $scope.flex.collectionView.items[i].fnkeyFg = scope.getSelectedFunc().fnkeyFg;
        $scope.flex.collectionView.items[i].fnkeyNo = scope.getSelectedFunc().fnkeyNo;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/pos/confg/func/func/saveFuncStore.sb", params, function(){
      $scope.addStoreSearch();
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("noRegStoreCtrl");
      addStoreScope._broadcast('noRegStoreCtrl');
    });
  };

}]);


/**********************************************************************
 *  미적용매장 그리드
 **********************************************************************/
app.controller('noRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('noRegStoreCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 조회 버튼 클릭
  $scope.$on("noRegStoreCtrl", function(event, data) {
    $scope.allStoreSearch();
    event.preventDefault();
  });

  // 적용매장 목록 조회
  $scope.allStoreSearch = function(){

    var params = {};
    var scope  = agrid.getScope('funcCtrl');

    params.fnkeyFg = scope.getSelectedFunc().fnkeyFg;
    params.fnkeyNo = scope.getSelectedFunc().fnkeyNo;
    params.hqOfficeCd = $("#srchHqOfficeCd").val();
    params.hqOfficeNm = $("#srchHqOfficeNm").val();
    params.storeCd = $("#srchStoreCd").val();
    params.storeNm = $("#srchStoreNm").val();
    params.regYn = 'N';

    $scope._inquiryMain("/pos/confg/func/func/getFuncStoreList.sb", params, function() {
    }, false);
  };


  // 저장
  $scope.regist = function(){

    var params = new Array();
    var scope  = agrid.getScope('funcCtrl');


    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].status = "I";
        $scope.flex.collectionView.items[i].fnkeyFg = scope.getSelectedFunc().fnkeyFg;
        $scope.flex.collectionView.items[i].fnkeyNo = scope.getSelectedFunc().fnkeyNo;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    console.log('save params',params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/pos/confg/func/func/saveFuncStore.sb", params, function(){
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("regStoreCtrl");
      addStoreScope._broadcast('regStoreCtrl');
    });
  };
}]);

