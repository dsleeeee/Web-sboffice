/**
 * get application
 */
var app = agrid.getApp();

/** 테이블별 매출현황 controller */
app.controller('saleAnalsMonthlyMomsStoreDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleAnalsMonthlyMomsStoreDtlCtrl', $scope, $http, true));
  $scope.orgnFg = gvOrgnFg;
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
	  
	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("saleAnalsMonthlyMomsStoreDtlCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleAnalsMonthlyMomsStoreDtlCtrl", function (event, data) {

    $("#spanDtlTitle").text("[" + data.storeCd + "]" + data.storeNm + " " + data.saleDate + " 매출현황");
	$scope.storeCd  	= data.storeCd;
	$scope.saleDate  	= data.saleDate.replaceAll('-', '');
	$scope.hqOfficeCd  	= data.hqOfficeCd;
	
    $scope.monthlyMomsStoreDtlLayer.show(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
    
    $scope.saleAnalsMonthlyMomsStoreDtlList();
    
  });

  // 조회
  $scope.saleAnalsMonthlyMomsStoreDtlList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $scope.storeCd;
    params.saleDate = $scope.saleDate;
    params.hqOfficeCd = $scope.hqOfficeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/anals/monthlyMoms/monthlyMoms/getSaleAnalsMonthlyMomsStoreDtlList.sb", params);
  };
  
}]);
