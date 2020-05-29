/****************************************************************
 *
 * 파일명 : hqEmpDetail.js
 * 설  명 : 본사사원정보 상세조회 JavaScript
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
 * 본사사원 상세조회
 */
app.controller('hqEmpDetailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqEmpDetailCtrl', $scope, $http, false));

  // 선택된 사원
  $scope.selectedHqEmp;

  // _broadcast
  $scope.$on("hqEmpDetailCtrl", function(event, data) {
    $scope.selectedHqEmp = data;
    $scope.getHqEmpList(data);
    event.preventDefault();
  });

  // 본사사원정보관리 그리드 조회
  $scope.getHqEmpList = function(data){

    var params = data;
    $scope._postJSONQuery.withOutPopUp( "/base/store/emp/hq/detail.sb", params, function(response){
      var data             = response.data.data;
      $scope.hqEmp         = data;
      $scope.hqEmp.empInfo = ' [' + data.empNo + ']' + data.empNm;
    });
  };

  // 수정버튼 클릭
  $scope.modify = function(){

    $scope.hqEmpRegistLayer.show(true, function(){
      var scope = agrid.getScope('hqEmpRegistCtrl');
      $scope.getHqEmpList($scope.selectedHqEmp);
    });

  };

  // 탭변경
  $scope.changeTab = function() {

        $scope.hqEmpDetailLayer.hide();
        $scope.hqEmpAuthLayer.show(true);

        $scope._broadcast('hqEmpAuthCtrl', $scope.selectedHqEmp);
        event.preventDefault();
  };

  // 닫기버튼 클릭
  $scope.close = function(){
    $scope.hqEmpDetailLayer.hide();
  };

}]);
