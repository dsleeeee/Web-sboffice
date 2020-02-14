/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(테이블별 매출) controller */
app.controller('tableDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('tableDayCtrl', $scope, $http, $timeout, true));

	$scope.srchTableDayStartDate = wcombo.genDateVal("#srchTableDayStartDate", gvStartDate);
	$scope.srchTableDayEndDate   = wcombo.genDateVal("#srchTableDayEndDate", gvEndDate);

	//조회조건 콤보박스 데이터 Set
	$scope._setComboData("tableDayListScaleBox", gvListScaleBoxData);
	var checkInt = true;
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {

		var storeCd = "";
		$scope.getReTableNmList(storeCd, "", false);

		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("tableDayCtrl");

		// 그리드 링크 효과
		s.formatItem.addHandler(function (s, e) {
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				if (col.binding.substring(0, 11) === "realSaleAmt") { // 실매출
		          	wijmo.addClass(e.cell, 'wijLink');
		        }
			}
		});

		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');

		// <-- 그리드 헤더2줄 -->
		// 헤더머지
		s.allowMerging = 'ColumnHeaders';

		//헤더 생성
		s.columnHeaders.rows.push(new wijmo.grid.Row());
		s.columnHeaders.rows.push(new wijmo.grid.Row());

		for(var i = 0; i < s.columnHeaders.rows.length; i++) {
			s.columnHeaders.setCellData(i, "saleDate", messages["tableDay.saleDate"]);
			s.columnHeaders.setCellData(i, "saleDay", messages["tableDay.saleDay"]);
			s.columnHeaders.setCellData(i, "totRealSaleAmt", messages["tableDay.totRealSaleAmt"]);
			s.columnHeaders.setCellData(i, "totRealSaleCnt", messages["tableDay.totRealSaleCnt"]);
			s.columnHeaders.setCellData(i, "totGuestCnt", messages["tableDay.totGuestCnt"]);
		}

		//그리드 아이템포멧 생성
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
			} else if (panel.cellType === wijmo.grid.CellType.RowHeader) { // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
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
			} else if (panel.cellType === wijmo.grid.CellType.Cell) { // readOnly 배경색 표시
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
	    	if (ht.cellType === wijmo.grid.CellType.Cell) {
	    		var col         = ht.panel.columns[ht.col];
	    		var selectedRow = s.rows[ht.row].dataItem;
	    		var params       = {};
	    		params.saleDate = selectedRow.saleDate;
	    		//params.storeCd = $scope.arrTableCd[Math.floor(ht.col/3) - 1];
	    		var storeTable   = $("#tableDaySelectTableCd").val().split(",");
	    		var arrStore= [];
	    		var arrTbl= [];
	    		for(var i=0; i < storeTable.length; i++) {
	    			var temp = storeTable[i].split("||");
	    			arrStore.push(temp[0]);
	    			arrTbl.push(temp[1]);
	    		}
	    		params.storeCd = arrStore[Math.floor(ht.col/3) - 1];
	    		params.tblCd   = arrTbl[Math.floor(ht.col/3) - 1];

	    		if (col.binding.substring(0, 11) === "realSaleAmt") { //실매출 클릭
	    			$scope._broadcast('saleComTableCtrl', params);
	    		}
	    	}
	    });
	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("tableDayCtrl", function (event, data) {

		if ($("#tableDaySelectStoreCd").val() === '') {
			$scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
			return false;
		}

		$scope.searchTableDayList(true);

		var storeCd = $("#tableDaySelectStoreCd").val();
		var tableCd = $("#tableDaySelectTableCd").val();

		$scope.getReTableNmList(storeCd, tableCd, true);
	});

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("tableDayCtrlSrch", function (event, data) {

		if ($("#tableDaySelectStoreCd").val() === '') {
			$scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
			return false;
		}

		$scope.searchTableDayList(false);

		var storeCd = $("#tableDaySelectStoreCd").val();
		var tableCd = $("#tableDaySelectTableCd").val();

		$scope.getReTableNmList(storeCd, tableCd, true);
	});

	// 테이블별매출일자별 리스트 조회
	$scope.searchTableDayList = function (isPageChk) {

		// 파라미터
		var params = {};
		params.storeCd = $("#tableDaySelectStoreCd").val();
		params.tableCd = $("#tableDaySelectTableCd").val();
		params.hqOfficeCd = $("#hqOfficeCd").val();
		params.listScale = $scope.tableDayListScale; //-페이지 스케일 갯수
		params.isPageChk = isPageChk;

		//등록일자 '전체기간' 선택에 따른 params
		if(!$scope.isChecked){
			params.startDate = wijmo.Globalize.format($scope.srchTableDayStartDate.value, 'yyyyMMdd');
			params.endDate = wijmo.Globalize.format($scope.srchTableDayEndDate.value, 'yyyyMMdd');
		}

		if(params.startDate > params.endDate){
			$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
			return false;
		}

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquirySub("/sale/status/table/day/list.sb", params);
	};

	//전체기간 체크박스 클릭이벤트
	$scope.isChkDt = function() {
		$scope.srchTableDayStartDate.isReadOnly = $scope.isChecked;
		$scope.srchTableDayEndDate.isReadOnly = $scope.isChecked;
	};

	//매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.tableDaySelectStoreShow = function () {
		$scope._broadcast('tableDaySelectStoreCtrl');
	};

	//테이블선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.tableDaySelectTableShow = function () {
		$scope._broadcast('tableDaySelectTableCtrl');
	};

	//엑셀 다운로드
	$scope.excelDownloadDay = function () {

		if ($scope.flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
		$timeout(function () {
			wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
				includeColumnHeaders: true,
				includeCellStyles   : false,
				includeColumns      : function (column) {
					return column.visible;
				}
			},'매출현황_테이블별_일자별_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	};

	//매장의 테이블 리스트 조회
	$scope.getTableNmList = function () {
		var storeCd = $("#tableDaySelectStoreCd").val();
		var tableCd = $("#tableDaySelectTableCd").val();
		$scope.getReTableNmList(storeCd, tableCd,  false);
	};

	//매장의 테이블 리스트 재생성
	$scope.getReTableNmList = function (storeCd, tableCd, grindSet) {
		var url = "/sale/status/table/day/tableNmList.sb";
	    var params = {};
	    params.storeCd = storeCd;
	    params.tableCd = tableCd;
	    params.hqOfficeCd = $("#HqOfficeCd").val();

	    // ajax 통신 설정
	    $http({
	    	method : 'POST', //방식
	    	url    : url, /* 통신할 URL */
	    	params : params, /* 파라메터로 보낼 데이터 */
	    	headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
	    }).then(function successCallback(response) {
	    	if ($scope._httpStatusCheck(response, true)) {
	    		if (!$.isEmptyObject(response.data.data.list)) {
	    			var list       = response.data.data.list;
	    			var arrStroreTable = [];
	    			var arrStoreTableNm = [];

	    			for (var i = 0; i < list.length; i++) {
	    				arrStroreTable.push(list[i].tableCd);
	    				arrStoreTableNm.push(list[i].storeNm + "||" + list[i].tableNm);
	    			}

	    			$("#tableDaySelectTableCd").val(arrStroreTable.join());
	    			$("#tableDaySelectTableName").val(arrStoreTableNm.join());

	    			storeTableCd = $("#tableDaySelectTableCd").val();
	    			storeTableNm = $("#tableDaySelectTableName").val();

	    			if (grindSet) {
	                    $scope.makeDataGrid();
	                 }
	    		}
	    	}
	    }, function errorCallback(response) {
	      $scope._popMsg(messages["cmm.error"]);
	      return false;
	    }).then(function () {

	    });
	  };

	  $scope.makeDataGrid = function () {

		  var grid = wijmo.Control.getControl("#tableDayGrid");

		  var colLength = grid.columns.length;

		  while(grid.columns.length > 5){
	            grid.columns.removeAt(grid.columns.length-1);
	        }

		  var arrTableCd = storeTableCd.split(',');
		  var arrTableNm = storeTableNm.split(',');

		  if (arrTableCd != null) {
			  for(var i = 1; i < arrTableCd.length + 1; i++) {

				  var colValue = arrTableCd[i-1];
				  var colName = arrTableNm[i-1];
				  var colSplit = colName.split('||');

				  grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.realSaleAmt"], binding: 'realSaleAmtT'+(i-1), width: 80, align: 'right', isReadOnly: 'true', aggregate: 'Sum'}));
				  grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.realSaleCnt"], binding: 'realSaleCntT'+(i-1), width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));
				  grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.guestCnt"], binding: 'guestCnt1T'+(i-1), width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));

				  grid.columnHeaders.setCellData(0, 'realSaleAmtT'+(i-1), colSplit[0]);
				  grid.columnHeaders.setCellData(0, 'realSaleCntT'+(i-1), colSplit[0]);
				  grid.columnHeaders.setCellData(0, 'guestCnt1T'+(i-1), colSplit[0]);

				  grid.columnHeaders.setCellData(1, 'realSaleAmtT'+(i-1), colSplit[1]);
				  grid.columnHeaders.setCellData(1, 'realSaleCntT'+(i-1), colSplit[1]);
				  grid.columnHeaders.setCellData(1, 'guestCnt1T'+(i-1), colSplit[1]);

//				  grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'realSaleAmtT"+i, width: 100, align: "right", isReadOnly: "true", aggregate: "Sum"}));
//				  grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'realSaleCntT"+i, width: 100, align: "right", isReadOnly: "true", aggregate: "Sum"}));
//				  grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'guestCnt1T"+i, width: 100, align: "right", isReadOnly: "true", aggregate: "Sum"}));
//
//				  grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'SaleAmt", colSplit[0]);
//				  grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'DcAmt", colSplit[0]);
//				  grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'RealSaleAmt", colSplit[0]);
//
//				  grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'SaleAmt", colSplit[1]);
//				  grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'DcAmt", colSplit[1]);
//				  grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'RealSaleAmt", colSplit[1]);
//
//				  grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'SaleAmt", messages["pos.SaleAmt"]);
//				  grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'DcAmt", messages["pos.DcAmt"]);
//				  grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'RealSaleAmt", messages["pos.realSaleAmt"]);

			  }
		  }

		  grid.itemFormatter = function (panel, r, c, cell) {

			  if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
				  //align in center horizontally and vertically
				  panel.rows[r].allowMerging    = true;
				  panel.columns[c].allowMerging = true;

				  wijmo.setCss(cell, {
					  display : 'table',
					  tableLayout : 'fixed'
				  });

				  cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

				  wijmo.setCss(cell.children[0], {
					  display : 'table-cell',
					  verticalAlign : 'middle',
					  textAlign : 'center'
				  });
			  } else if (panel.cellType === wijmo.grid.CellType.RowHeader) { // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
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
			  } else if (panel.cellType === wijmo.grid.CellType.Cell) { // readOnly 배경색 표시
				  var col = panel.columns[c];
				  if (col.isReadOnly) {
					  wijmo.addClass(cell, 'wj-custom-readonly');
				  }
			  }

		  }

		  $scope.flex.refresh();

		  // 기능수행 종료 : 반드시 추가
		  event.preventDefault();
	  }
}]);
