/****************************************************************
 *
 * 파일명 : systemEmpDetail.js
 * 설  명 : 사원정보 상세조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.26     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 사원 상세조회
 */
app.controller('systemEmpDetailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('systemEmpDetailCtrl', $scope, $http, false));

  // 선택된 사원
  $scope.selectedSystemEmp;

  // _broadcast
  $scope.$on("systemEmpDetailCtrl", function(event, data) {
    $scope.selectedSystemEmp = data;
    $scope.getSystemEmpList(data);
    event.preventDefault();
  });

  // 사원정보관리 그리드 조회
  $scope.getSystemEmpList = function(data){

    var params = data;
    $scope._postJSONQuery.withOutPopUp( "/base/store/emp/system/detail.sb", params, function(response){
      // console.log('response', response);

      var data                 = response.data.data;
      $scope.systemEmp         = data;
      $scope.systemEmp.empInfo = ' [' + data.empNo + ']' + data.empNm;
    });
  };

  // 수정버튼 클릭
  $scope.modify = function(){
      //$scope.systemEmpRegistLayer.show(true);
      $scope.systemEmpRegistLayer.show(true, function(){
          var scope = agrid.getScope('systemEmpRegistCtrl');
          $scope.getSystemEmpList($scope.selectedSystemEmp);
      });
  };

  // 닫기버튼 클릭
  $scope.close = function(){
    $scope.systemEmpDetailLayer.hide();
  };

}]);
