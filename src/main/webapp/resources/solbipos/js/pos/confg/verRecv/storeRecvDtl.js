/****************************************************************
 *
 * 파일명 : storeRecvDtl.js
 * 설  명 : 매장 포스 수신현황 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.01.08     김지은      1.0           Angular방식으로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  매장포스 수신현황 상세 그리드
 **********************************************************************/
app.controller('storeRecvDtlCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeRecvDtlCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택 매장
  $scope.selectStore;
  $scope.setSelectStore = function(store){
    $scope.selectStore = store;
  };
  $scope.getSelectStore = function(){
    return $scope.selectStore;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.verRecvFgDatMap = new wijmo.grid.DataMap(verRecvFg, 'value', 'name');
  };

  // 조회 버튼 클릭
  $scope.$on("storeRecvDtlCtrl", function(event, data) {

    console.log('data', data);

    $scope.setSelectStore(data);

    // 타이틀
    var storeTitle = "[" + $scope.getSelectStore().storeCd + "] " + $scope.getSelectStore().storeNm;
    $("#storeTitle").text(storeTitle);

    $scope.getStoreVersionList();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getStoreVersionList = function(){
    var params = {};
    params.hqOfficeCd = $scope.getSelectStore().hqOfficeCd;
    params.storeCd = $scope.getSelectStore().storeCd;

    $scope._inquiryMain("/pos/confg/verRecv/storeRecv/storeDtl.sb", params, function() {
    });
  };

  // 팝업 닫기
  $scope.close = function(){
    $scope.storeRecvDtlLayer.hide();
  };

}]);
