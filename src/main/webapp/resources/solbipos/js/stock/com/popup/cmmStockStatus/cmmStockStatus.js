/**
 * get application
 */
var app = agrid.getApp();

/** 재고현황 팝업 controller */
app.controller('cmmStockStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cmmStockStatusCtrl', $scope, $http, true));

	// 단위구분 세팅
	$scope._setComboData("srchUnitFg", [
		{"name": messages["cmmStockStatus.stockUnit"], "value": "0"},
		{"name": messages["cmmStockStatus.orderUnit"], "value": "1"}
	]);

	// 조회기간 세팅
	$scope.startDate = wcombo.genDateVal("#srchStartDate", getToday());
	$scope.endDate = wcombo.genDateVal("#srchEndDate", getToday());

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("cmmStockStatusCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("cmmStockStatusCtrl", function (event, data) {
	
	$scope.prodCd		= data.prodCd;
	$scope.prodNm 		= data.prodNm;
	
	// $scope에 변경된 값 뷰에 반영
	$scope.$apply();

    $scope.cmmStockStatusLayer.show(true);
    // 초기에 데이터 조회
    $scope.searchCmmStockStatusList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("cmmStockStatusSrchCtrl", function (event, data) {

    $scope.searchCmmStockStatusList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
//다른 컨트롤러의 broadcast 받기
  $scope.$on("cmmStoreStockStatusCtrl", function (event, data) {

	$scope.prodCd		= data.prodCd;
	$scope.prodNm 		= data.prodNm;
	$scope.storeCd		= data.storeCd;
	
	// $scope에 변경된 값 뷰에 반영
	$scope.$apply();

    $scope.cmmStockStatusLayer.show(true);
    // 초기에 데이터 조회
    $scope.searchCmmStoreStockStatusList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 재고현황- 재고현황 리스트 조회
  $scope.searchCmmStockStatusList = function () {
    // 파라미터
    var params      	= {};
    	params.vendrCd = $("#hqCurrSelectVendrCd").val();
        params.startDate 	= wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
        params.endDate 		= wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');
        params.unitFg		= $scope.unitFgPop; // 단위구분
        params.prodCd		= $scope.prodCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/stock/com/popup/cmmStockStatus/getCmmStockStatusList.sb", params);
  };
  
  //재고현황- 매장 재고현황 리스트 조회
  $scope.searchCmmStoreStockStatusList = function () {
    // 파라미터
    var params      	= {};
    	params.vendrCd = $("#hqCurrSelectVendrCd").val();
        params.startDate 	= wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
        params.endDate 		= wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');
        params.unitFg		= $scope.unitFgPop; // 단위구분
        params.storeCd      = $scope.storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/stock/com/popup/cmmStockStatus/getCmmStoreStockStatusList.sb", params);
  };
}]);

