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

/**********************************************************************
 *  매장추가 그리드
 **********************************************************************/
app.controller('storeAddCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeAddCtrl', $scope, $http, true));

  // 콤보박스 데이터
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("clsFgCombo", clsFg);
  $scope._setComboData("sysStatFgCombo", sysStatFg);

  // 조회조건
  // $scope.store;

  // 조회 버튼 클릭
  $scope.$on("storeAddCtrl", function(event, data) {
    $scope.storeSearch();
    event.preventDefault();
  });

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  // 매장 목록 조회
  $scope.storeSearch = function(){

    var params = {};
    var scope  = agrid.getScope('verManageCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    if( !isEmptyObject($scope.store)){
      params = $scope.store;
    }

    params.verSerNo    = ver;
    params.listScale   = 50;
    params.curr        = $scope._getPagingInfo('curr');

    $scope._inquiryMain("/pos/confg/verManage/applcStore/srchStoreList.sb", params, function() {
    });
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

    console.log('save params',params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/pos/confg/verManage/applcStore/regist.sb", params, function(){
      $scope.storeSearch();
    });

  };

  // 닫기
  $scope.close = function(){
    $scope.versionInfoDetailLayer.hide();
  };

  // 탭변경
  $scope.changeTab = function(){
    $scope.storeAddLayer.hide();
    $scope.versionInfoDetailLayer.show(true);
  };


  //todo 등록매장수를 상세화면이나 리스트에 추가
}]);

