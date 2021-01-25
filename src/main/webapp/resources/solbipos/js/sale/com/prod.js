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
	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("saleComProdCtrl");  

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleComProdCtrl", function (event, data) {
    $scope.prodLayer.show(true);
    $scope.searchSaleComProdList(data);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 테이블별 리스트 조회
  $scope.searchSaleComProdList = function (data) {
    // 파라미터
    var params = {};

    // 매출관리>매출현황>코너별>일자별탭, 요일별탭, 월별탭
    if (data.chkPop == "cornerDayProdPop" || data.chkPop == "cornerDayOfWeekProdPop") {
      params.chkPop = "cornerDayProdPop";
      params.startDate = data.startDate;
      params.endDate = data.endDate;
      params.arrStoreCornr = data.arrStoreCornr;
      params.storeCd = data.storeCd;
      // 요일별탭
      if(data.chkPop == "cornerDayOfWeekProdPop") {
        params.yoil = data.yoil;
      }
    } else {
      params.chkPop = data.chkPop;
      params.storeCd  	= data.storeCd;
      params.prodCd  		= data.prodCd;
      params.cornrCd  	= data.cornrCd;
      params.arrStoreCornr = data.arrStoreCornr;
      params.saleDate 	= data.saleDate;
      params.startDate 	= data.startDate;
      params.endDate		= data.endDate;
      params.saleMonth 	= data.saleMonth;
      params.empNo 		= data.empNo;
      params.yoil 		= data.yoil;
      params.posNo 		= data.posNo;
      params.billNo 		= data.billNo;
      params.saleHour 	= data.saleHour;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/com/popup/prod/view.sb", params);
  };

}]);