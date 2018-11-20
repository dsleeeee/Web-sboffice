/****************************************************************
 *
 * 파일명 : vanConfig.js
 * 설  명 : 매장정보 벤 정보 JavaScript
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
 *  포스 터미널 그리드
 **********************************************************************/
app.controller('posTerminalCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('posTerminalCtrl', $scope, $http, true));

  // 콤보박스 셋팅
  $scope._setComboData("cornerUseYnFg", cornerUseFg);

  // 선택 매장
  $scope.selectedStore;
  $scope.setSelectedStore = function(store) {
    $scope.selectedStore = store;
  };
  $scope.getSelectedStore = function(){
    return $scope.selectedStore;
  };

  // 코너 사용여부
  $scope.cornerUseYnVal;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 조회 버튼 클릭
  $scope.$on("posTerminalCtrl", function(event, data) {
    $scope.setSelectedStore(data);
    $scope.getVanConfigInfo();
    event.preventDefault();
  });

  // 벤 정보 조회
  $scope.getVanConfigInfo = function(){

    var params      = {};
    params.storeCd  = $scope.selectedStore.storeCd;

    $scope._postJSONQuery.withOutPopUp( "/base/store/view/vanConfg/vanConfigInfo.sb", params, function(response){
      // console.log("response", response);

      var cornerUseYnVal  = response.data.data.cornerUseYnVal;
      var posVanInfo      = response.data.data.posTerminalList;
      var cornerVanInfo   = response.data.data.cornrTerminalList;

      $scope.cornerUseYnVal                 = cornerUseYnVal;
      $scope.cornerUseYnCombo.selectedValue = cornerUseYnVal;

      // 그리드 데이터 셋팅
      if(cornerUseYnVal === '0' || cornerUseYnVal === '3' ){ // 포스별

        $scope.flex.itemsSource = posVanInfo;

      } else if(cornerUseYnVal === '2') { // 코너별

        var cornerGrid = agrid.getScope("cornerTerminalCtrl");
        cornerGrid.setCornerList(cornerVanInfo);
        cornerGrid.setCornerUseYnVal(cornerVanInfo);

      }
    });
  };
}]);


/**********************************************************************
 *  코너 터미널 그리드
 **********************************************************************/
app.controller('cornerTerminalCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cornerTerminalCtrl', $scope, $http, true));

  // 코너 사용여부
  $scope.cornerUseYnVal;
  $scope.setCornerUseYnVal = function(val) {
    $scope.cornerUseYnVal = val;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 조회 버튼 클릭
  $scope.$on("cornerTerminalCtrl", function(event, data) {
    event.preventDefault();
  });

  // 코너 그리드 셋팅
  $scope.setCornerList = function(list){
    $scope.flex.itemsSource = list;
  };

}]);
