/**
 * get application
 */

var app = agrid.getApp();

app.controller('stockManageViewCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('stockManageViewCtrl', $scope, $http, true));
	
	// 조회일자 세팅
	$scope.srchStartDate = wcombo.genDateVal("#srchClassStartDate", getToday());
	$scope.srchEndDate   = wcombo.genDateVal("#srchClassEndDate", getToday());
	$scope.orgnFg = gvOrgnFg; // ?
	// 조회조건 콤보박스 listScale 세팅
	$scope._setComboData("stockManageViewListScaleBox", gvListScaleBoxData);
	
	// 상태 세팅
	$scope._setComboData("srchStatus", [
	    {"name": messages["cmm.all"], "value": ""},
	    {"name": messages["stockManageView.acins"], "value": "1"}, // 실사
	    {"name": messages["stockManageView.adj"], "value": "2"}, // 조정
	    {"name": messages["stockManageView.disuse"], "value": "3"} // 폐기
	]);
	
	// 진행 세팅
	$scope._setComboData("srchProcFg", [
	    {"name": messages["cmm.all"], "value": ""},
	    {"name": messages["stockManageView.procFg0"], "value": "0"}, // 등록
	    {"name": messages["stockManageView.procFg1"], "value": "1"} // 확정
	]);
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("stockManageViewCtrl");
		
		// 그리드 링크 효과
	    s.formatItem.addHandler(function (s, e) {
	      if (e.panel === s.cells) {
	        var col = s.columns[e.col];

	        if (col.binding === "totDate") { // 일자
	        	var item = s.rows[e.row].dataItem;
	          	wijmo.addClass(e.cell, 'wijLink');
	          	wijmo.addClass(e.cell, 'wj-custom-readonly');
	        }
	      }
	    });
	    
	    // 총매출열에 CSS 추가
		wijmo.addClass(s.columns[2], 'wijLink');
		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');
		
		// 그리드 클릭 이벤트
	    s.addEventListener(s.hostElement, 'mousedown', function (e) {
	    	var ht = s.hitTest(e);
	    	
	    	if (ht.cellType === wijmo.grid.CellType.Cell) {
	    		var col         = ht.panel.columns[ht.col];
	    		var selectedRow = s.rows[ht.row].dataItem;
	    		var params       = {};
    			
	    		if (col.binding === "totDate") { // 일자
	    			params.orgnFg = $scope.orgnFg;
	    			params.totDate		= selectedRow.totDate; // 날짜
	    			params.seqNo		= selectedRow.seqNo; // 차수
	    			params.hqGbn		= selectedRow.hqGbn; // 상태
	    		    params.title		= selectedRow.title; // 제목
	    		    params.hqOfficeCd	= $("#hqOfficeCd").val();
	    		    params.storeCd		= $("#storeCd").val();
	    		    
	    			$scope._broadcast('viewDtlCtrl', params);
	    		}
	    	}
	    });
	};
	
	function formatDate(date) {
		var yyyy = date.substr(0,4);
	    var mm = date.substr(4,2);
	    var dd = date.substr(6,2);
		return [yyyy, mm, dd].join('-');
	}
	
	$scope.$on("stockManageViewCtrl", function (event, data){
		$scope.searchStockManageViewList(true);
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});
	
	//다른 컨트롤러의 broadcast 받기(페이징 초기화)
	$scope.$on("stockManageViewCtrlSrch", function (event, data) {
		$scope.comboArray =  $scope.comboArrayForSrc; // ????????????
		$scope.searchStockManageViewList(false);
   
		// 기능수행 종료 : 반드시 추가
    	event.preventDefault();
    });
	
	// 실사/조정/폐기 리스트 조회
	$scope.searchStockManageViewList = function (isPageChk) {
		// 파라미터
		var params     = {};
		params.isPageChk = isPageChk;
		params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
	    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
	    params.hqGbn = $("#srchStatus").val(); // 상태
	    params.procFg = $("#srchProcFg").val(); // 진행
	    
		if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
		}
				
		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/stock/manage/view/view/stockManageViewList.sb", params);
	};

	//엑셀 다운로드
	$scope.excelDownload = function () {
		if ($scope.flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
		$timeout(function () {
			wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
				includeColumnHeaders: true,
				includeCellStyles   : true,
				includeColumns      : function (column) {
					return column.visible;
				}
			}, '실사/조정/폐기_실사/조정/폐기 조회_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	};
}]);