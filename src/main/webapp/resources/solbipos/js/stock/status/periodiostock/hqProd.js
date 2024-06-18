/**
 * get application
 */

var app = agrid.getApp();

app.controller('periodiostockCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('periodiostockCtrl', $scope, $http, true));

	// 조회일자 세팅
	$scope.srchStartDate = wcombo.genDateVal("#srchClassStartDate", getToday());
	$scope.srchEndDate   = wcombo.genDateVal("#srchClassEndDate", getToday());
	$scope.orgnFg = gvOrgnFg; // ?
	$scope.isSearch = false;
	// 조회조건 콤보박스 listScale 세팅
	$scope._setComboData("periodiostockListScaleBox", gvListScaleBoxData);

	$scope._setComboData("srchUnitFg", [
	    {"name": messages["periodIostock.unitStockFg"], "value": "0"},
	    {"name": messages["periodIostock.unitOrderFg"], "value": "1"}
	]);

	// 조회옵션 세팅
	$scope._setComboData("srchSrchOption", [
		{"name": messages["periodIostock.QtyTot"], "value": "QtyTot"},
		{"name": messages["periodIostock.Qty"], "value": "Qty"},
	    {"name": messages["periodIostock.Tot"], "value": "Tot"}
	]);

	// 조회조건 마감재고표시 데이터 Set
	$scope._setComboData("srchStockOptionCombo", [
		{"name": "미표시", "value": "N"},
		{"name": "표시", "value": "Y"}
	]);

	$scope.periodIostockSelectVendrShow = function () {
		$scope._broadcast('periodIostockSelectVendrCtrl');
	};

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("periodiostockCtrl");

		// 그리드 링크 효과
	    s.formatItem.addHandler(function (s, e) {
	      if (e.panel === s.cells) {
	        var col = s.columns[e.col];
	        var colCode = col.binding.slice(-3);

	        if (col.binding === "prodCd" || (colCode === "Qty" && s.cells.getCellData(e.row,e.col,false) != null && col.binding !== "poUnitQty" && col.binding !== "setInQty" && col.binding !== "baseQty" && col.binding !== "closeQty")) { // 상품코드 & 수량
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

		// <-- 그리드 헤더2줄 -->
	    // 헤더머지
	    s.allowMerging = 2;
	    s.columnHeaders.rows.push(new wijmo.grid.Row());

	    // 첫째줄 헤더 생성
	    var dataItem				= {};
	    dataItem.prodClassNm		= messages["periodIostock.prodClassNm"];
	    dataItem.prodCd				= messages["periodIostock.prodCd"];
	    dataItem.prodNm				= messages["periodIostock.prodNm"];
	    dataItem.poUnitQty			= messages["periodIostock.poUnitQty"];
	    dataItem.poUnitFgNm			= messages["periodIostock.poUnitFg"];
	    dataItem.barcdCd			= messages["periodIostock.barcdCd"];
		dataItem.baseQty			= messages["periodIostock.basicStock"];
		dataItem.baseTot			= messages["periodIostock.basicStock"];

		// 본사
		dataItem.vendrInQty			= messages["periodIostock.ioOccr01"]; // 본사입고
		dataItem.vendrInTot			= messages["periodIostock.ioOccr01"];
		dataItem.vendrOutQty		= messages["periodIostock.ioOccr16"]; // 업체반출
		dataItem.vendrOutTot		= messages["periodIostock.ioOccr16"];
		dataItem.hqOutQty			= messages["periodIostock.ioOccr13"]; // 본사출고
		dataItem.hqOutTot			= messages["periodIostock.ioOccr13"];
		dataItem.hqInQty			= messages["periodIostock.ioOccr02"]; // 본사반입
		dataItem.hqInTot			= messages["periodIostock.ioOccr02"];

		// 공통
		dataItem.storeMoveInQty		= messages["periodIostock.ioOccr04"]; // 매장이입
		dataItem.storeMoveInTot		= messages["periodIostock.ioOccr04"];
		dataItem.storeMoveOutQty	= messages["periodIostock.ioOccr14"]; // 매장이출
		dataItem.storeMoveOutTot	= messages["periodIostock.ioOccr14"];
		dataItem.disuseQty			= messages["periodIostock.disuse"]; // 재고폐기
		dataItem.adjQty				= messages["periodIostock.adj"]; // 재고조정
		dataItem.setInQty			= messages["periodIostock.setIn"]; // 세트생성
		dataItem.closeQty			= messages["periodIostock.endingStock"];
		dataItem.closeTot			= messages["periodIostock.endingStock"];

		// 본사
		dataItem.saleVendrOrderQty	= messages["periodIostock.ioOccr19"]; // 거래처출고
		dataItem.saleVendrOrderTot	= messages["periodIostock.ioOccr19"];
		dataItem.saleVendrRtnQty	= messages["periodIostock.ioOccr33"]; // 거래처반품
		dataItem.saleVendrRtnTot	= messages["periodIostock.ioOccr33"];

	    s.columnHeaders.rows[0].dataItem = dataItem;

	    s.itemFormatter = function (panel, r, c, cell) {
	        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
	            //align in center horizontally and vertically
	            panel.rows[r].allowMerging    = true;
	            panel.columns[c].allowMerging = true;
	            wijmo.setCss(cell, {
	                display    : 'table',
	                tableLayout: 'fixed'
	            });
	            cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
	            wijmo.setCss(cell.children[0], {
	                display      : 'table-cell',
	                verticalAlign: 'middle',
	                textAlign    : 'center'
	            });
	        }
	        // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
	        else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
	            // GroupRow 인 경우에는 표시하지 않는다.
	            if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
	                cell.textContent = '';
	            } else {
	                if (!isEmpty(panel._rows[r]._data.rnum)) {
	                    cell.textContent = (panel._rows[r]._data.rnum).toString();
	                } else {
	                    cell.textContent = (r + 1).toString();
	                }
	            }
	        }
	        // readOnly 배경색 표시
	        else if (panel.cellType === wijmo.grid.CellType.Cell) {
	            var col = panel.columns[c];
	            if (col.isReadOnly) {
	                wijmo.addClass(cell, 'wj-custom-readonly');
	            }
	        }
	    }
	    // <-- //그리드 헤더2줄 -->

	    // 그리드 클릭 이벤트
	    s.addEventListener(s.hostElement, 'mousedown', function (e) {
	    	var ht = s.hitTest(e);

	    	// 병합된 그리드 헤더 정렬기능 제거, 버블링옵션추가(true) 필요
	        if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	        	  var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	        	  if (rng && rng.columnSpan > 1) {
	        	  	e.preventDefault();
	        	  }
	  	    }

	    	if (ht.cellType === wijmo.grid.CellType.Cell) {
	    		var col         = ht.panel.columns[ht.col];
	    		var selectedRow = s.rows[ht.row].dataItem;

	    		var params       = {};
	    		params.orgnFg = $scope.orgnFg;
	    		params.prodCd = selectedRow.prodCd; // 상품코드
    			params.prodNm = selectedRow.prodNm; // 상품명
    	        params.storeCd		= $("#storeCd").val();
    	        params.storeNm = $("#storeNm").val();
				params.startDate = selectedRow.startDate; // 시작날짜
				params.endDate 	 = selectedRow.endDate; // 종료날짜

	    		if (col.binding === "prodCd") { // 상품코드
	    			$scope._broadcast('prodCodeDtlCtrl', params);
	    		}
	    		if (col.binding.slice(-3) === "Qty" && selectedRow[col.binding] != null && col.binding !== "poUnitQty" && col.binding !== "setInQty" && col.binding !== "baseQty" && col.binding !== "closeQty"){
	    	        var colCode = col.binding;
	    	        params.poUnitQty = selectedRow.poUnitQty; // 입수
	    	        params.colCode = colCode; // 수량(컬럼 뒤에 붙는 숫자, 어떤 수량인지 구분)
	    	        params.ioOccrFg = s.columnHeaders.getCellData(0,ht.col,false);

	    			$scope._broadcast('prodQtyDtlCtrl', params);
	    		}
	    	}
	    }, true);
	};

	// 상품분류정보 팝업
	$scope.popUpProdClass = function() {
		var popUp = $scope.prodClassPopUpLayer;
		popUp.show(true, function (s) {
			// 선택 버튼 눌렀을때만
			if (s.dialogResult === "wj-hide-apply") {
				var scope = agrid.getScope('prodClassPopUpCtrl');
				var prodClassCd = scope.getSelectedClass();
				var params = {};
				params.prodClassCd = prodClassCd;
				// 조회 수행 : 조회URL, 파라미터, 콜백함수
				$scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
						function(response){
					$scope.prodClassCd = prodClassCd;
					$scope.prodClassCdNm = response.data.data;
					}
				);
			}
		});
	};

	// 상품분류정보 선택취소
	$scope.delProdClass = function(){
		$scope.prodClassCd = "";
		$scope.prodClassCdNm = "";
	}

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("periodiostockCtrl", function (event, data) {

		$scope.searchPeriodIostockList(true);
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});

	//다른 컨트롤러의 broadcast 받기(페이징 초기화)
	$scope.$on("periodiostockCtrlSrch", function (event, data) {
		$scope.comboArray =  $scope.comboArrayForSrc; // ?????????????
		$scope.searchPeriodIostockList(false);

		// 기능수행 종료 : 반드시 추가
    	event.preventDefault();
    });

	// 매장기간수불 리스트 조회
	$scope.searchPeriodIostockList = function (isPageChk) {
		// 파라미터
		var params     = {};
		params.startDate 	= wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
	    params.endDate 		= wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
		params.prodCd 		= $("#srchProdCd").val(); // 상품코드
		params.prodNm 		= $("#srchProdNm").val() ; // 상품명
		params.barcdCd 		= $("#srchBarcdCd").val(); // 바코드
		params.vendrCd		= $("#periodIostockSelectVendrCd").val(); // 거래처
		params.prodClassCd	= $scope.prodClassCd; // 분류
		params.unitFg		= $scope.unitFgModel; // 단위구분
		params.srchOption	= $("#srchSrchOption").val(); // 조회옵션
		params.isPageChk	= isPageChk;
		params.listScale = $scope.listScaleCombo.text;
		params.orgnFg		= $scope.orgnFg;

		$scope.excelStartDate	= params.startDate;
		$scope.excelEndDate 	= params.endDate;
		$scope.excelProdCd 		= params.prodCd; // 상품코드
		$scope.excelProdNm 		= params.prodNm; // 상품명
		$scope.excelBarcdCd 	= params.barcdCd; // 바코드
		$scope.excelVendrCd		= params.vendrCd; // 거래처
		$scope.excelProdClassCd	= params.prodClassCd; // 분류
		$scope.excelUnitFg		= $scope.unitFgModel; // 단위구분
		$scope.excelSrchOption	= params.srchOption; // 조회옵션
		$scope.excelListScale 	= params.listScale;
		$scope.excelSrchOption	= $scope.srchOption;
		$scope.isSearch			= true;
		$scope.excelSrchStockOption	= $scope.srchStockOption;

		if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
		}

		$scope.srchOptionView();

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/stock/status/periodIoStock/prod/periodiostockList.sb", params);
	};

	// 조회옵션에 따른 visible 처리 (박정은, 20.03.17)
	$scope.srchOptionView = function(){
		var check = $scope.srchOption;
		var check2 = $scope.srchStockOption;
		var columns = $scope.flex.columns;
		var includeWord;
		for(var i=6; i<columns.length-2; i++){
			includeWord = /Qty|Tot/.exec(columns[i].binding) ? /Qty|Tot/.exec(columns[i].binding)[0] : ""; // 컬럼명에 Qty나 Tot 포함시 해당 문자열을 읽어오고, 포함하지 않을 경우 [0]에 null 값이 들어가므로 "" 로 변경해준다.
			if(includeWord !== "" && includeWord !== "poUnitQty" && columns[i].binding != 'setInQty' && columns[i].binding != 'saleVendrOrderQty' && columns[i].binding != 'saleVendrOrderTot' && columns[i].binding != 'saleVendrRtnQty' && columns[i].binding != 'saleVendrRtnTot'){ // poUnitQty(입수)는 조회옵션에 따라 visible처리를 해야하는 컬럼이 아니라 무조건 표시해야하는 컬럼
				// 기초/마감재고
				if(columns[i].binding == 'baseQty' || columns[i].binding == 'baseTot' || columns[i].binding == 'closeQty' || columns[i].binding == 'closeTot'){
					if(check2 == "N") { // 미표시
						columns[i].visible = false;
					} else if(check2 == "Y") { // 표시
						if(check.indexOf(includeWord) >= 0){
							columns[i].visible = true; // 선택한 옵션값에 포함되는 컬럼을 true로 변경
						}else{
							columns[i].visible = false;
						}
					}
				} else {
					//check.includes(includeWord) ? columns[i].visible = true : columns[i].visible = false; // 선택한 옵션값에 포함되는 컬럼을 true로 변경
					if(check.indexOf(includeWord) >= 0){
						columns[i].visible = true; // 선택한 옵션값에 포함되는 컬럼을 true로 변경
					}else{
						columns[i].visible = false;
					}
				}
			}
		}
	};

	//상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
	$scope.isChkProdClassDisplay = function(){
		var columns = $scope.flex.columns;

		for(var i=0; i<columns.length; i++){
			if(columns[i].binding === 'prodClassNm'){
				$scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
			}
		}
	};

	//엑셀 다운로드
	$scope.excelDownload = function () {
		// 파라미터
		var params     = {};

		$scope._broadcast('periodiostockExcelCtrl',params);
	};
}]);

app.controller('periodiostockExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('periodiostockExcelCtrl', $scope, $http, $timeout, true));

	var checkInt = true;

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
	    // 총매출열에 CSS 추가
		wijmo.addClass(s.columns[2], 'wijLink');
		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');

		// <-- 그리드 헤더2줄 -->
	    // 헤더머지
	    s.allowMerging = 2;
	    s.columnHeaders.rows.push(new wijmo.grid.Row());

	    // 첫째줄 헤더 생성
	    var dataItem				= {};
	    dataItem.prodClassNm		= messages["periodIostock.prodClassNm"];
	    dataItem.prodCd				= messages["periodIostock.prodCd"];
	    dataItem.prodNm				= messages["periodIostock.prodNm"];
	    dataItem.poUnitQty			= messages["periodIostock.poUnitQty"];
		dataItem.poUnitFgNm			= messages["periodIostock.poUnitFg"];
	    dataItem.barcdCd			= messages["periodIostock.barcdCd"];
		dataItem.baseQty			= messages["periodIostock.basicStock"];
		dataItem.baseTot			= messages["periodIostock.basicStock"];

	    // 본사
	    dataItem.vendrInQty			= messages["periodIostock.ioOccr01"]; // 본사입고
	    dataItem.vendrInTot			= messages["periodIostock.ioOccr01"];
	    dataItem.vendrOutQty		= messages["periodIostock.ioOccr16"]; // 업체반출
	    dataItem.vendrOutTot		= messages["periodIostock.ioOccr16"];
	    dataItem.hqOutQty			= messages["periodIostock.ioOccr13"]; // 본사출고
	    dataItem.hqOutTot			= messages["periodIostock.ioOccr13"];
	    dataItem.hqInQty			= messages["periodIostock.ioOccr02"]; // 본사반입
	    dataItem.hqInTot			= messages["periodIostock.ioOccr02"];

	    // 공통
	    dataItem.storeMoveInQty		= messages["periodIostock.ioOccr04"]; // 매장이입
	    dataItem.storeMoveInTot		= messages["periodIostock.ioOccr04"];
	    dataItem.storeMoveOutQty	= messages["periodIostock.ioOccr14"]; // 매장이출
	    dataItem.storeMoveOutTot	= messages["periodIostock.ioOccr14"];
	    dataItem.disuseQty			= messages["periodIostock.disuse"]; // 재고폐기
	    dataItem.adjQty				= messages["periodIostock.adj"]; // 재고조정
	    dataItem.setInQty			= messages["periodIostock.setIn"]; // 세트생성
		dataItem.closeQty			= messages["periodIostock.endingStock"];
		dataItem.closeTot			= messages["periodIostock.endingStock"];

	    // 본사
	    dataItem.saleVendrOrderQty	= messages["periodIostock.ioOccr19"]; // 거래처출고
	    dataItem.saleVendrOrderTot	= messages["periodIostock.ioOccr19"];
	    dataItem.saleVendrRtnQty	= messages["periodIostock.ioOccr33"]; // 거래처반품
	    dataItem.saleVendrRtnTot	= messages["periodIostock.ioOccr33"];

	    s.columnHeaders.rows[0].dataItem = dataItem;

	    s.itemFormatter = function (panel, r, c, cell) {
	        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
	            //align in center horizontally and vertically
	            panel.rows[r].allowMerging    = true;
	            panel.columns[c].allowMerging = true;
	            wijmo.setCss(cell, {
	                display    : 'table',
	                tableLayout: 'fixed'
	            });
	            cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
	            wijmo.setCss(cell.children[0], {
	                display      : 'table-cell',
	                verticalAlign: 'middle',
	                textAlign    : 'center'
	            });
	        }
	        // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
	        else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
	            // GroupRow 인 경우에는 표시하지 않는다.
	            if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
	                cell.textContent = '';
	            } else {
	                if (!isEmpty(panel._rows[r]._data.rnum)) {
	                    cell.textContent = (panel._rows[r]._data.rnum).toString();
	                } else {
	                    cell.textContent = (r + 1).toString();
	                }
	            }
	        }
	        // readOnly 배경색 표시
	        else if (panel.cellType === wijmo.grid.CellType.Cell) {
	            var col = panel.columns[c];
	            if (col.isReadOnly) {
	                wijmo.addClass(cell, 'wj-custom-readonly');
	            }
	        }
	    }
	    // <-- //그리드 헤더2줄 -->
	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("periodiostockExcelCtrl", function (event, data) {
		if(data != undefined && $scope.isSearch) {
			$scope.searchPeriodIostockExcelList(true);
			// 기능수행 종료 : 반드시 추가
			event.preventDefault();
		} else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

	});

	// 조회옵션에 따른 visible 처리 (박정은, 20.03.17)
	$scope.srchOptionView = function(){
		var check = $scope.excelSrchOption;
		var check2 = $scope.excelSrchStockOption;
		var columns = $scope.excelFlex.columns;
		var includeWord;
		for(var i=0; i<columns.length; i++){
			includeWord = /Qty|Tot/.exec(columns[i].binding) ? /Qty|Tot/.exec(columns[i].binding)[0] : ""; // 컬럼명에 Qty나 Tot 포함시 해당 문자열을 읽어오고, 포함하지 않을 경우 [0]에 null 값이 들어가므로 "" 로 변경해준다.
			if(includeWord !== "" && includeWord !== "poUnitQty" && columns[i].binding != 'setInQty' && columns[i].binding != 'saleVendrOrderQty' && columns[i].binding != 'saleVendrOrderTot' && columns[i].binding != 'saleVendrRtnQty' && columns[i].binding != 'saleVendrRtnTot'){ // poUnitQty(입수)는 조회옵션에 따라 visible처리를 해야하는 컬럼이 아니라 무조건 표시해야하는 컬럼
				// 기초/마감재고
				if(columns[i].binding == 'baseQty' || columns[i].binding == 'baseTot' || columns[i].binding == 'closeQty' || columns[i].binding == 'closeTot'){
					if(check2 == "N") { // 미표시
						columns[i].visible = false;
					} else if(check2 == "Y") { // 표시
						if(check.indexOf(includeWord) >= 0){
							columns[i].visible = true; // 선택한 옵션값에 포함되는 컬럼을 true로 변경
						}else{
							columns[i].visible = false;
						}
					}
				} else {
					//check.includes(includeWord) ? columns[i].visible = true : columns[i].visible = false; // 선택한 옵션값에 포함되는 컬럼을 true로 변경
					if(check.indexOf(includeWord) >= 0){
						columns[i].visible = true; // 선택한 옵션값에 포함되는 컬럼을 true로 변경
					}else{
						columns[i].visible = false;
					}
				}
			}
		}
	};

	//상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
	$scope.isChkProdClassDisplay = function(){
		var columns = $scope.excelFlex.columns;

		for(var i=0; i<columns.length; i++){
			if(columns[i].binding === 'prodClassNm'){
				$scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
			}
		}
	};

	// 전체 엑셀 리스트 조회
	$scope.searchPeriodIostockExcelList = function (isPageChk) {// 파라미터

		// 파라미터
		var params     = {};
		params.startDate 	= $scope.excelStartDate;
	    params.endDate 		= $scope.excelEndDate;
		params.prodCd 		= $scope.excelProdCd; // 상품코드
		params.prodNm 		= $scope.excelProdNm; // 상품명
		params.barcdCd 		= $scope.excelBarcdCd ; // 바코드
		params.vendrCd		= $scope.excelVendrCd; // 거래처
		params.prodClassCd	= $scope.excelProdClassCd; // 분류
		params.unitFg		= $scope.excelUnitFg; // 단위구분
		params.srchOption	= $scope.excelSrchOption; // 조회옵션
		params.listScale 	= $scope.excelListScale;
		params.srchStockOption	= $scope.excelSrchStockOption;

		if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
		}

		$scope.srchOptionView();
		$scope.isChkProdClassDisplay();

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/stock/status/periodIoStock/prod/periodiostockExcelList.sb", params, function(){
			if ($scope.excelFlex.rows.length <= 0) {
				$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
				return false;
			}

			$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
			$timeout(function () {
				wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
					includeColumnHeaders: true,
					includeCellStyles   : true,
					includeColumns      : function (column) {
						return column.visible;
					}
				}, $(menuNm).selector + '_'+messages["storePeriod.storePeriod"]+'_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);
		});
	};

}]);