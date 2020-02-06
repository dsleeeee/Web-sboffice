/**
 * get application
 */
var app = agrid.getApp();

/** 테이블별 매출현황 controller */
app.controller('saleComProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleComProdCtrl', $scope, $http, true));


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleComProdCtrl", function (event, data) {

    $scope.storeCd  	= data.storeCd;
    $scope.prodCd  		= data.prodCd;
    $scope.cornrCd  	= data.cornrCd;
    $scope.saleDate 	= data.saleDate;
    $scope.startDate 	= data.startDate;
    $scope.endDate		= data.endDate;
    $scope.saleMonth 	= data.saleMonth;
    $scope.chkPop 		= data.chkPop;
    $scope.empNo 		= data.empNo;
    $scope.yoil 		= data.yoil;
    $scope.posNo 		= data.posNo;

    $scope.prodLayer.show(true);

    $scope.searchSaleComProdList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 테이블별 리스트 조회
  $scope.searchSaleComProdList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $scope.storeCd;
    params.prodCd  	 = $scope.prodCd;
    params.cornrCd   = $scope.cornrCd;
    params.saleDate  = $scope.saleDate;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.saleMonth = $scope.saleMonth;
    params.chkPop    = $scope.chkPop;
    params.empNo 	 = $scope.empNo;
    params.yoil 	 = $scope.yoil;
    params.posNo 	 = $scope.posNo;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/com/popup/prod/view.sb", params);
  };

}]);
