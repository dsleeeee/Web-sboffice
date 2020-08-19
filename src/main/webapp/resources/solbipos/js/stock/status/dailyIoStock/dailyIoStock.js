/**
 * get application
 */
var app = agrid.getApp();

/** 일자별수불현황 그리드 controller */
app.controller('dailyIoStockCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dailyIoStockCtrl', $scope, $http, true));

  $scope.excelFg = false;
  
  $scope.orgnFg = gvOrgnFg;
  $scope.isChecked = true;
	//조회옵션 value 값 확인 필요
	$scope._setComboData("srchSrchOption", [
		{"name": messages["dailyIostock.qtyAmt"], "value": "2"},
		{"name": messages["dailyIostock.qty"], "value": "1"},
		{"name": messages["dailyIostock.amt"], "value": "0"}
	]);

  // 조회조건 조회일자 세팅
  $scope.startDate = wcombo.genDateVal("#srchStartDate", getToday());
  $scope.endDate = wcombo.genDateVal("#srchEndDate", getToday());
  // 조회조건 콤보박스 listScale 세팅
  $scope._setComboData("dailyIoStockListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dailyIoStockCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding.substr(6, 3) === "Qty" && s.cells.getCellData(e.row,e.col,false) != null) { // 수량 링크
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);

      if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
  		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
  		if (rng && rng.columnSpan > 1) {
  			e.preventDefault();
  		}
  	  }

      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var colLength 	= col.binding.length;
        var selectedRow = s.rows[ht.row].dataItem;
		if (col.binding.substr(6, 3) === "Qty" && selectedRow[col.binding] != null) { // 수량 클릭
			var params    = {};
			params.orgnFg = $scope.orgnFg;
			//params.slipFg = selectedRow.slipNo; // 전표구분
			params.ioOccrDt = selectedRow.ioOccrDt; // 일자
			params.ioOccrFg = col.binding.substring(colLength, colLength-2);
			params.ioOccrNm = s.columnHeaders.getCellData(0,ht.col,false);
			$scope._broadcast('dailyIoStockInfoCtrl', params);
		}
      }
    }, true);

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    s.allowMerging = 'AllHeaders';
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    if($scope.orgnFg == "H") {
	    var dataItem         = {};
	    dataItem.ioOccrDt  = messages["dailyIostock.ioOccrDt"];
	    dataItem.ioOccrQty01  = messages["dailyIostock.col2"];
	    dataItem.ioOccrTot01  = messages["dailyIostock.col2"];
	    dataItem.ioOccrQty16  = messages["dailyIostock.col3"];
	    dataItem.ioOccrTot16       = messages["dailyIostock.col3"];
	    dataItem.ioOccrQty13      = messages["dailyIostock.col4"];
	    dataItem.ioOccrTot13   = messages["dailyIostock.col4"];
	    dataItem.ioOccrQty02   = messages["dailyIostock.col5"];
	    dataItem.ioOccrTot02   = messages["dailyIostock.col5"];
	    dataItem.ioOccrQty04  = messages["dailyIostock.col6"];
	    dataItem.ioOccrTot04  = messages["dailyIostock.col6"];
	    dataItem.ioOccrQty14  = messages["dailyIostock.col7"];
	    dataItem.ioOccrTot14       = messages["dailyIostock.col7"];
	    dataItem.ioOccrQty17      = messages["dailyIostock.col8"];
	    dataItem.ioOccrTot17   = messages["dailyIostock.col8"];
	    dataItem.ioOccrQty21   = messages["dailyIostock.col9"];
	    dataItem.ioOccrTot21   = messages["dailyIostock.col9"];
	    dataItem.ioOccrQty22       = messages["dailyIostock.col10"];
	    dataItem.ioOccrTot22      = messages["dailyIostock.col10"];
	    dataItem.ioOccrQty19   = messages["dailyIostock.col11"];
	    dataItem.ioOccrTot19   = messages["dailyIostock.col11"];
	    dataItem.ioOccrQty33   = messages["dailyIostock.col12"];
	    dataItem.ioOccrTot33   = messages["dailyIostock.col12"];
    } else if($scope.orgnFg == "S") {
    	var dataItem         = {};
	    dataItem.ioOccrDt  = messages["dailyIostock.ioOccrDt"];
	    dataItem.ioOccrQty03  = messages["dailyIostock.col13"];
	    dataItem.ioOccrTot03  = messages["dailyIostock.col13"];
	    dataItem.ioOccrQty12  = messages["dailyIostock.col14"];
	    dataItem.ioOccrTot12       = messages["dailyIostock.col14"];
	    dataItem.ioOccrQty06      = messages["dailyIostock.col15"];
	    dataItem.ioOccrTot06   = messages["dailyIostock.col15"];
	    dataItem.ioOccrQty18   = messages["dailyIostock.col16"];
	    dataItem.ioOccrTot18   = messages["dailyIostock.col16"];
	    dataItem.ioOccrQty11  = messages["dailyIostock.col17"];
	    dataItem.ioOccrTot11  = messages["dailyIostock.col17"];
	    dataItem.ioOccrQty04  = messages["dailyIostock.col18"];
	    dataItem.ioOccrTot04       = messages["dailyIostock.col18"];
	    dataItem.ioOccrQty14      = messages["dailyIostock.col19"];
	    dataItem.ioOccrTot14   = messages["dailyIostock.col19"];
	    dataItem.ioOccrQty01   = messages["dailyIostock.col20"];
	    dataItem.ioOccrTot01   = messages["dailyIostock.col20"];
	    dataItem.ioOccrQty02       = messages["dailyIostock.col21"];
	    dataItem.ioOccrTot02      = messages["dailyIostock.col21"];
	    dataItem.ioOccrQty05   = messages["dailyIostock.col22"];
	    dataItem.ioOccrTot05   = messages["dailyIostock.col22"];
    }
    s.columnHeaders.rows[0].dataItem = dataItem;
	// <-- //그리드 헤더2줄 -->

    s.itemFormatter = function (panel, r, c, cell) {
        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
          //align in center horizontally and vertically
      	panel.allowMerging = 'All';
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

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dailyIoStockCtrl", function (event, data) {

    $scope.searchDailyIoStockList();

    $scope.visibleQtyAmt();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 일자별수불현황 리스트 조회
  $scope.searchDailyIoStockList = function () {
    // 파라미터
    var params     = {};
    params.startDate = $scope.startDate.text.replace(/-/g, '');
    params.endDate = $scope.endDate.text.replace(/-/g, '');
    params.orgnFg = $scope.orgnFg;
    
    $scope.excelStartDate   = params.startDate;
    $scope.excelEndDate     = params.endDate;
    $scope.excelOrgnFg	    = params.orgnFg;
    $scope.excelSrchOption	= $scope.srchOption;
    
    if(params.startDate > params.endDate){
		$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		return false;
	}

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/status/dailyIoStock/dailyIoStock/getDailyIoStockList.sb", params);
  
    $scope.excelFg = true;
  };

  //수량, 금액 컬럼 보이기 선택
  $scope.visibleQtyAmt = function () {

	var flag = $scope.srchOption;

	switch(flag) {
	// 금액 보이기
	case "0": $scope.showOrHide(false, true); break;
	// 수량 보이기
	case "1": $scope.showOrHide(true, false); break;
	// 수량 + 금액 보이기
	case "2": $scope.showOrHide(true, true); break;
	}

  };

  // 파라미터 false:숨김, true:보이기 / amtFg: 금액, qryFg: 수량
  $scope.showOrHide = function (amtFg, qtyFg){

	var columns = $scope.flex.columns;

	for(var c = 0; c < columns.length; c++) {
		var substr = columns[c].binding.substr(6, 3);
		if (substr === "Qty") { // 수량
			$scope.flex.columns[c].visible = amtFg;
		} else if (substr === "Tot") { // 금액
			$scope.flex.columns[c].visible = qtyFg;
		}
	}
  };

  //엑셀 다운로드
  $scope.excelDownload = function () {
	  
	    // 파라미터
	    var params     = {};
		$scope._broadcast('dailyIoStockExcelCtrl',params);
  };

}]);



/** 일자별수불현황 그리드 controller */
app.controller('dailyIoStockExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dailyIoStockExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    s.allowMerging = 'AllHeaders';
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    if($scope.orgnFg == "H") {
	    var dataItem         = {};
	    dataItem.ioOccrDt  = messages["dailyIostock.ioOccrDt"];
	    dataItem.ioOccrQty01  = messages["dailyIostock.col2"];
	    dataItem.ioOccrTot01  = messages["dailyIostock.col2"];
	    dataItem.ioOccrQty16  = messages["dailyIostock.col3"];
	    dataItem.ioOccrTot16       = messages["dailyIostock.col3"];
	    dataItem.ioOccrQty13      = messages["dailyIostock.col4"];
	    dataItem.ioOccrTot13   = messages["dailyIostock.col4"];
	    dataItem.ioOccrQty02   = messages["dailyIostock.col5"];
	    dataItem.ioOccrTot02   = messages["dailyIostock.col5"];
	    dataItem.ioOccrQty04  = messages["dailyIostock.col6"];
	    dataItem.ioOccrTot04  = messages["dailyIostock.col6"];
	    dataItem.ioOccrQty14  = messages["dailyIostock.col7"];
	    dataItem.ioOccrTot14       = messages["dailyIostock.col7"];
	    dataItem.ioOccrQty17      = messages["dailyIostock.col8"];
	    dataItem.ioOccrTot17   = messages["dailyIostock.col8"];
	    dataItem.ioOccrQty21   = messages["dailyIostock.col9"];
	    dataItem.ioOccrTot21   = messages["dailyIostock.col9"];
	    dataItem.ioOccrQty22       = messages["dailyIostock.col10"];
	    dataItem.ioOccrTot22      = messages["dailyIostock.col10"];
	    dataItem.ioOccrQty19   = messages["dailyIostock.col11"];
	    dataItem.ioOccrTot19   = messages["dailyIostock.col11"];
	    dataItem.ioOccrQty33   = messages["dailyIostock.col12"];
	    dataItem.ioOccrTot33   = messages["dailyIostock.col12"];
    } else if($scope.orgnFg == "S") {
    	var dataItem         = {};
	    dataItem.ioOccrDt  = messages["dailyIostock.ioOccrDt"];
	    dataItem.ioOccrQty03  = messages["dailyIostock.col13"];
	    dataItem.ioOccrTot03  = messages["dailyIostock.col13"];
	    dataItem.ioOccrQty12  = messages["dailyIostock.col14"];
	    dataItem.ioOccrTot12       = messages["dailyIostock.col14"];
	    dataItem.ioOccrQty06      = messages["dailyIostock.col15"];
	    dataItem.ioOccrTot06   = messages["dailyIostock.col15"];
	    dataItem.ioOccrQty18   = messages["dailyIostock.col16"];
	    dataItem.ioOccrTot18   = messages["dailyIostock.col16"];
	    dataItem.ioOccrQty11  = messages["dailyIostock.col17"];
	    dataItem.ioOccrTot11  = messages["dailyIostock.col17"];
	    dataItem.ioOccrQty04  = messages["dailyIostock.col18"];
	    dataItem.ioOccrTot04       = messages["dailyIostock.col18"];
	    dataItem.ioOccrQty14      = messages["dailyIostock.col19"];
	    dataItem.ioOccrTot14   = messages["dailyIostock.col19"];
	    dataItem.ioOccrQty01   = messages["dailyIostock.col20"];
	    dataItem.ioOccrTot01   = messages["dailyIostock.col20"];
	    dataItem.ioOccrQty02       = messages["dailyIostock.col21"];
	    dataItem.ioOccrTot02      = messages["dailyIostock.col21"];
	    dataItem.ioOccrQty05   = messages["dailyIostock.col22"];
	    dataItem.ioOccrTot05   = messages["dailyIostock.col22"];
    }
    s.columnHeaders.rows[0].dataItem = dataItem;
	// <-- //그리드 헤더2줄 -->

    s.itemFormatter = function (panel, r, c, cell) {
        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
          //align in center horizontally and vertically
      	panel.allowMerging = 'All';
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

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dailyIoStockExcelCtrl", function (event, data) {
	  
	if(data != undefined && $scope.excelFg) {
		if($scope.excelStartDate > $scope.excelEndDate){
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.searchDailyIoStockExcelList();    
    
	}else{
		$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
		return false;
	}
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
 

  // 일자별수불현황 리스트 조회
  $scope.searchDailyIoStockExcelList = function () {
    // 파라미터
    var params     = {};
    params.startDate   = $scope.excelStartDate;
    params.endDate     = $scope.excelEndDate;
    params.orgnFg	   = $scope.excelOrgnFg;

    if(params.startDate > params.endDate){
		$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		return false;
	}
    
    $scope.visibleQtyAmt();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/status/dailyIoStock/dailyIoStock/getDailyIoStockExcelList.sb", params, function(){

		var flex = $scope.excelFlex;
		//row수가 0이면
		if(flex.rows.length === 0){

			 var grid = wijmo.Control.getControl("#dailyIoStockExcelGrid")
			//컬럼 삭제
			while(grid.columns.length > 7){
		          grid.columns.removeAt(grid.columns.length-1);
		    }
		}

		if (flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
		$timeout(function () {
			wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(flex, {
				includeColumnHeaders: true,
				includeCellStyles   : true,
				includeColumns      : function (column) {
					return column.visible;
				}
			}, messages["cmmStockStatus.stockStatus"]+'_'+messages["dailyIostock.dailyIoStockList"]+'_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
    	
    });
  };

//수량, 금액 컬럼 보이기 선택
  $scope.visibleQtyAmt = function () {

	var flag = $scope.excelSrchOption;

	switch(flag) {
	// 금액 보이기
	case "0": $scope.showOrHide(false, true); break;
	// 수량 보이기
	case "1": $scope.showOrHide(true, false); break;
	// 수량 + 금액 보이기
	case "2": $scope.showOrHide(true, true); break;
	}

  };

  // 파라미터 false:숨김, true:보이기 / amtFg: 금액, qryFg: 수량
  $scope.showOrHide = function (amtFg, qtyFg){

	var columns = $scope.excelFlex.columns;

	for(var c = 0; c < columns.length; c++) {
		var substr = columns[c].binding.substr(6, 3);
		if (substr === "Qty") { // 수량
			$scope.excelFlex.columns[c].visible = amtFg;
		} else if (substr === "Tot") { // 금액
			$scope.excelFlex.columns[c].visible = qtyFg;
		}
	}
  };


}]);

