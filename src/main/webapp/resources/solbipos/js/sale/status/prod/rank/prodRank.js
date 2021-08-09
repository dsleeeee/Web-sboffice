/****************************************************************
 *
 * 파일명 : prodRank.js
 * 설  명 : 상품별 >상품매출순위 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.06     김진        1.0
 * 2021.01.04     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var orderTypeFg = [
	{"name": "수량", "value": "1"},
	{"name": "실매출액	", "value": "2"}
];

/** 상품매출순위 상세현황 controller */
app.controller('prodRankCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('prodRankCtrl', $scope, $http, true));

	// 조회일자 세팅
	var startDate = wcombo.genDateVal("#startDateProdRank", gvStartDate);
	var endDate = wcombo.genDateVal("#endDateProdRank", gvEndDate);

	// 콤보박스 데이터 Set
	$scope._setComboData('prodRanklistScaleBox', gvListScaleBoxData);
	$scope._setComboData("orderType", orderTypeFg);

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("prodRankCtrl");

		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');
	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("prodRankCtrl", function (event, data) {
		$scope.searchProdRankList();
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});

	// 상품매출순위 리스트 조회
	$scope.searchProdRankList = function () {
		// 파라미터
		var params       = {};
		params.storeCd   = $("#prodRankSelectStoreCd").val();
		params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
		params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
		params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수

		if(params.startDate > params.endDate){
			$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
			return false;
		}

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/prod/rank/getProdRankList.sb", params, function() {});

		// 상품매출순위별 바 차트
		$scope._broadcast("prodRankChartCtrl", params);
	};
  

	// 매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.prodRankSelectStoreShow = function () {
		$scope._broadcast('prodRankSelectStoreCtrl');
	};

	// 엑셀 다운로드
	$scope.excelDownloadRank = function () {
		var params       = {};
		params.storeCd   = $("#prodRankSelectStoreCd").val();
		params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
		params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
		params.orderType = $scope.orderTypeCombo.selectedValue; // 정렬기준

		$scope._broadcast('prodRankExcelCtrl', params);
	};

	//상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
	$scope.isChkProdClassDisplay = function(){
		var columns = $scope.flex.columns;

		for(var i=0; i<columns.length; i++){
			if(columns[i].binding === 'pathNm'){
				$scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
			}
		}
	};

}]);


//** 상품매출순위 차트 (상품별 바) controller *//
app.controller('prodRankChartCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

	angular.extend(this, new RootController('prodRankChartCtrl', $scope, $http, $timeout, true));

	//메인그리드 조회후 상세그리드 조회.
	$scope.initChart = function(s, args){
		s.plotMargin = 'auto auto 50 auto';
		s.axisX.labelAngle = 0;
	    //s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;

	    var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
	        animationMode: wijmo.chart.animation.AnimationMode.All,
	        easing: wijmo.chart.animation.Easing.Linear,
	        duration: 400
	    });
	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("prodRankChartCtrl", function (event, data) {
		if(data != undefined) {
            $scope.storeCd = data.storeCd;
			$scope.startDate = data.startDate;
			$scope.endDate = data.endDate;
			//$scope.orderType = data.orderType;
		}
	    $scope.prodRankBarChartList(data);
	    // 기능수행 종료 : 반드시 추가
	    //event.preventDefault();
	});

	// 코너별매출일자별 리스트 조회
	$scope.prodRankBarChartList = function (data) {
		// 파라미터
		var params          = {};
        params.storeCd = data.storeCd;
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        //params.orderType = data.orderType;

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/prod/rank/getProdRankChartList.sb", params);
	};

	$scope.rendered = function(s, e) {

		var pArea =  s.hostElement.querySelector('.wj-plot-area > rect');
		var pAreaWidth = pArea.width.baseVal.value;
		var groupWidth = pAreaWidth / (s.collectionView.items.length || 1);

		var labels = document.querySelectorAll('.wj-axis-x .wj-label');
		var widthMax = new Array();

	    labels.forEach(function(value, key, parent) {

	    	var x = +value.getAttribute('x');
	        var y = +value.getAttribute('y');
	        var text = value.innerHTML.split(' - ');
	        value.innerHTML = '';

	        widthMax[key] = new Array();

	        text.forEach(function(item, index) {

	            var e = document.createElementNS('http://www.w3.org/2000/svg', 'tspan');
	            //e.setAttribute("x", (x + 0).toString());
	            e.setAttribute('y', (y + (15 * index)).toString());
	            e.innerHTML = item;
	            value.appendChild(e);

	            var bbox = e.getBoundingClientRect();
	            var extent = e.getExtentOfChar(0);
	            var boxWidth = e.getComputedTextLength();
	            var gap = 0;

	            gap = (groupWidth - boxWidth) / 2;
	            widthMax[key][index] = gap;

	            e.setAttribute('x', (x + gap).toString());
	        });
	    });

	    labels.forEach(function(value, key, parent) {

	    	var children = value.childNodes;

	    	for (var i = 0; i < children.length; i++) {
	    		var e = value.childNodes[i];
	    		var extent = e.getExtentOfChar(0);

	    		e.setAttribute('x', extent.x - widthMax[key][0] + 30);
	    	}
	    });

	    s.tooltip.content = function (ht) {
	    	var title = ht.name;
			var nameArr = ht._xfmt.split(" - ");
			var value = ht.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			return "<b>" + title + "</b><br><br>" + nameArr[0]  + "<br><br>" + value;
		}
	}
}]);


//** 상품매출순위 엑셀 다운로드 controller *//
app.controller('prodRankExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('prodRankExcelCtrl', $scope, $http, true));

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');
	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("prodRankExcelCtrl", function (event, data) {
		$scope.searchProdRankExcelList(data);
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});

	// 상품매출순위 리스트 조회
	$scope.searchProdRankExcelList = function (data) {
		// 파라미터
		var params       = {};
		params.storeCd = data.storeCd;
		params.startDate = data.startDate;
		params.endDate = data.endDate;
		params.orderType = data.orderType;

		$scope.isChkProdClassDisplay();

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/prod/rank/getProdRankExcelList.sb", params, function() {
			if ($scope.excelFlexSec.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
			}

			$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
			$timeout(function () {
				wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlexSec, {
					includeColumnHeaders: true,
					includeCellStyles   : true,
					includeColumns      : function (column) {
						return column.visible;
					}
				}, '매출현황_상품별_상품매출순위_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);
		});
	};

	//상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
	$scope.isChkProdClassDisplay = function(){
		var columns = $scope.excelFlexSec.columns;

		for(var i=0; i<columns.length; i++){
			if(columns[i].binding === 'pathNm'){
				$scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
			}
		}
	};

}]);