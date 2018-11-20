/****************************************************************
 *
 * 파일명 : dtl.js
 * 설  명 : 매장정보상세조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.20     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  매장 상세조화
 **********************************************************************/
app.controller('storeInfoViewCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeInfoViewCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);

  // 선택 매장
  $scope.selectedStore;
  $scope.setSelectedStore = function(store) {
    $scope.selectedStore = store;
  };
  $scope.getSelectedStore = function(){
    return $scope.selectedStore;
  };

  // 팝업 호출시 상세정보 조회
  $scope.$on("storeInfoViewCtrl", function(event, data) {
    $scope.setSelectedStore(data);
    $scope.getStoreDetail();
    event.preventDefault();
  });

  // 매장상세정보 조회
  $scope.getStoreDetail = function(){
    var params        = {};
    params.hqOfficeCd = $scope.selectedStore.hqOfficeCd;
    params.storeCd    = $scope.selectedStore.storeCd;

    $scope._postJSONQuery.withOutPopUp( "/base/store/view/dtl/list.sb", params, function(response){
      // console.log(response);
      var storeInfoDtl = response.data.data.storeInfo;
      $scope.storeInfo = storeInfoDtl;
    });
  };

  // 팝업 닫기
  $scope.close = function(){
    $scope.storeInfoViewLayer.hide();
  };
}]);
