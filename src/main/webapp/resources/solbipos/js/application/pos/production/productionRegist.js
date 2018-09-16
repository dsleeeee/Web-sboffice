/****************************************************************
 *
 * 파일명 : productionRegist.js
 * 설  명 : 생산량 등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.14     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  생산량 등록
 */
app.controller('registCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('registCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    console.log('init')
    $scope.save();
  };

  $scope.$on("registCtrl", function(event, data) {
    event.preventDefault();
  });

  // 생산량 등록
  $scope.save = function(){
    var params = {};
    params.prodClassCd = prod.prodClassCd;
    params.prodCd = prod.prodCd;
    params.productionCnt = $("#productionCnt").val();

    console.log(params);    //TODO 저장 로직 추가 필요
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    // $scope._save("/sys/cd/envConfg/envConfg/envstDtl/save.sb", params);
  };
}]);


