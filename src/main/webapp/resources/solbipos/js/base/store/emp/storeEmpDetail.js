/****************************************************************
 *
 * 파일명 : storeEmpDetail.js
 * 설  명 : 매장사원정보 상세조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.21     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 매장사원 상세조회
 */
app.controller('storeEmpDetailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeEmpDetailCtrl', $scope, $http, false));

  // 선택된 사원
  $scope.selectedStoreEmp;

  // _broadcast
  $scope.$on("storeEmpDetailCtrl", function(event, data) {
    $scope.selectedStoreEmp = data;
    $scope.getStoreEmpList(data);
    event.preventDefault();
  });

  // 매장사원정보관리 그리드 조회
  $scope.getStoreEmpList = function(data){

    var params = data;
    $scope._postJSONQuery.withOutPopUp( "/base/store/emp/store/detail.sb", params, function(response){
      console.log('response', response);

      var data                = response.data.data;
      $scope.storeEmp         = data;
      $scope.storeEmp.empInfo = ' [' + data.empNo + ']' + data.empNm;

      if($scope.storeEmp.useYn === "Y") {
        $scope.storeEmp.useYn = "사용";
      } else {
        $scope.storeEmp.useYn = "미사용";
      }
      if($scope.storeEmp.mainSaleFg === "0") {
        $scope.storeEmp.mainSaleFg = "사용";
      } else {
        $scope.storeEmp.mainSaleFg = "미사용";
      }
    });
  };

  // 수정버튼 클릭
  $scope.modify = function(){
    $scope.storeEmpRegistLayer.show(true, function(){
      $scope.getStoreEmpList($scope.selectedStoreEmp);
    });
  };

  // 탭변경
  $scope.changeTab = function() {

    $scope.storeEmpDetailLayer.hide();
    $scope.storeEmpAuthLayer.show(true);

    $scope._broadcast('storeEmpAuthCtrl', $scope.selectedStoreEmp);
    event.preventDefault();
  };

  // 닫기버튼 클릭
  $scope.close = function(){
    $scope.storeEmpDetailLayer.hide();

    var scope = agrid.getScope('storeEmpCtrl');
    scope.getStoreEmpList();
  };

}]);
