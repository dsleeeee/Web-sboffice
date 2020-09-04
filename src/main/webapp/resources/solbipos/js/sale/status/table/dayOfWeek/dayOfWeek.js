/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(테이블별 매출) controller */
app.controller('tableDayOfWeekCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('tableDayOfWeekCtrl', $scope, $http, $timeout, true));

	$scope.srchTableDayOfWeekStartDate = wcombo.genDateVal("#srchTableDayOfWeekStartDate", getToday());
	$scope.srchTableDayOfWeekEndDate   = wcombo.genDateVal("#srchTableDayOfWeekEndDate", getToday());

	//조회조건 콤보박스 데이터 Set
	$scope._setComboData("tableDayOfWeekListScaleBox", gvListScaleBoxData);

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {

		var storeCd = $("#tableDayOfWeekSelectStoreCd").val();
		$scope.getReTableNmList(storeCd, "", false);

		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("tableDayOfWeekCtrl");

		// 그리드 링크 효과
		s.formatItem.addHandler(function (s, e) {
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				if (col.binding.substring(0, 11) === "realSaleAmt") { // 실매출
		          	wijmo.addClass(e.cell, 'wijLink');
		        } else if (col.binding === "totRealSaleAmt"){
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
			s.columnHeaders.setCellData(i, "saleDay", messages["tableDay.saleDay"]);
			s.columnHeaders.setCellData(i, "totRealSaleAmt", messages["tableDay.totRealSaleAmt"]);
			s.columnHeaders.setCellData(i, "totSaleCnt", messages["tableDay.totSaleCnt"]);
			s.columnHeaders.setCellData(i, "totGuestCnt", messages["tableDay.totGuestCnt"]);
		}
		// <-- //그리드 헤더2줄 -->

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

		// 그리드 클릭 이벤트
    	s.addEventListener(s.hostElement, 'mousedown', function (e) {
	    	var ht = s.hitTest(e);

	    	/* 머지된 헤더 셀 클릭시 정렬 비활성화
	    	 * 헤더 cellType: 2 && 머지된 row 인덱스: 0, 1 && 동적 생성된 column 인덱스 4 초과
	    	 * 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
	    	 */
	    	if(ht.cellType == 2 && ht.row < 2 && ht.col > 3) {
	    		s.allowSorting = false;
    		} else {
    			s.allowSorting = true;
    		}

	    	if (ht.cellType === wijmo.grid.CellType.Cell) {
	    		var col         = ht.panel.columns[ht.col];
	    		var selectedRow = s.rows[ht.row].dataItem;
	    		var params       = {};
				if(!$scope.isChecked){
					params.startDate = wijmo.Globalize.format($scope.srchTableDayOfWeekStartDate.value, 'yyyyMMdd');
					params.endDate = wijmo.Globalize.format($scope.srchTableDayOfWeekEndDate.value, 'yyyyMMdd');
				}
	    		params.saleDay    = selectedRow.saleDay;
	    		params.chkPop   = "tablePop";

	    		var storeTable   = $("#tableDayOfWeekSelectTableCd").val().split(",");
	    		var storeTableOrg   = 	$("#tableDayOfWeekSelectTableCdOrg").val().split(",");
	    		var storeCd			=	selectedRow.storeCd; //$("#tableDayOfWeekSelectStoreCd").val();

	    		if (col.binding.substring(0, 11) === "realSaleAmt") { //실매출 클릭
	    			var arrStore= [];
		    		var arrTbl= [];
		    		for(var i=0; i < storeTable.length; i++) {
		    			var temp = storeTable[i].split("||");
		    			arrStore.push(temp[0]);
		    			arrTbl.push(temp[1]);
		    		}

		    		//params.storeCd = arrStore[Math.floor(ht.col/3) - 1];
//		    		params.tblCd   = arrStore[Math.floor(ht.col/3) - 1] + '||' + arrTbl[Math.floor(ht.col/3) - 1];
		    		params.tblCd   = arrTbl[Math.floor(ht.col/3) - 1];
		    		params.storeCd	=	storeCd;
		    		
	    			$scope._broadcast('saleComTableCtrl', params);
	    		} else if (col.binding === "totRealSaleAmt") { // 총실매출 클릭
//        			params.tblCd	 = storeTable.join(",");
        			params.tblCd	= storeTableOrg.join(",");
        			params.storeCd	=	storeCd;
	    			$scope._broadcast('saleComTableCtrl', params);
	    		}
	    	}
	    });
	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("tableDayOfWeekCtrl", function (event, data) {

		$scope.searchTableDayOfWeekList(true);

		var storeCd = $("#tableDayOfWeekSelectStoreCd").val();
		var tableCd = $("#tableDayOfWeekSelectTableCd").val();

		$scope.getReTableNmList(storeCd, tableCd, true);
	});

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("tableDayOfWeekCtrlSrch", function (event, data) {

		if ($("#tableDayOfWeekSelectStoreCd").val() === "") {
			$scope._popMsg(messages["todayDtl.require.selectStore"]); // 매장을 선택해주세요.
			return false;
		}

		$scope.searchTableDayOfWeekList(false);

		var storeCd = $("#tableDayOfWeekSelectStoreCd").val();
		var tableCd = $("#tableDayOfWeekSelectTableCd").val();

		$scope.getReTableNmList(storeCd, tableCd, true);
	});

	// 테이블별매출일자별 리스트 조회
	$scope.searchTableDayOfWeekList = function (isPageChk) {

		// 파라미터
		var params = {};
		params.storeCd = $("#tableDayOfWeekSelectStoreCd").val();
		params.tableCd = $("#tableDayOfWeekSelectTableCd").val();
		params.hqOfficeCd = $("#hqOfficeCd").val();
		params.listScale = $scope.tableDayOfWeekListScale; //-페이지 스케일 갯수
		params.isPageChk = isPageChk;

		//등록일자 '전체기간' 선택에 따른 params
		if(!$scope.isChecked){
			params.startDate = wijmo.Globalize.format($scope.srchTableDayOfWeekStartDate.value, 'yyyyMMdd');
			params.endDate = wijmo.Globalize.format($scope.srchTableDayOfWeekEndDate.value, 'yyyyMMdd');
		}

		if(params.startDate > params.endDate){
			$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
			return false;
		}

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/table/dayofweek/list.sb", params, function() {

			var flex = $scope.flex;
			//row수가 0이면
			if(flex.rows.length === 0){

				var grid = wijmo.Control.getControl("#tableDayOfWeekGrid");
				//컬럼 삭제
				while(grid.columns.length > 4){
			          grid.columns.removeAt(grid.columns.length-1);
			    }
			}

		});
	};

	//전체기간 체크박스 클릭이벤트
	$scope.isChkDt = function() {
		$scope.srchTableDayOfWeekStartDate.isReadOnly = $scope.isChecked;
		$scope.srchTableDayOfWeekEndDate.isReadOnly = $scope.isChecked;
	};

	//매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.tableDayOfWeekSelectStoreShow = function () {
		$scope._broadcast('tableDayOfWeekSelectStoreCtrl');
	};

	//테이블선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.tableDayOfWeekSelectTableShow = function () {
		$scope._broadcast('tableDayOfWeekSelectTableCtrl');
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
				includeCellStyles   : true,
				includeColumns      : function (column) {
					return column.visible;
				}
			}, '매출현황_테이블별_요일별_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	};

	// 조회조건 매장 선택 팝업 닫힐 때 메서드
	$scope.closeSelectStore = function () {
		var storeCd = $("#tableDayOfWeekSelectStoreCd").val();
		$scope.getReTableNmList(storeCd, "",  false);
	};

	// 조회조건 테이블 선택 팝업 닫힐 때 메서드
	$scope.closeSelectTable = function () {
		var storeCd = $("#tableDayOfWeekSelectStoreCd").val();
		var tableCd = $("#tableDayOfWeekSelectTableCd").val();
		$scope.getReTableNmList(storeCd, tableCd,  false);
	};

	//매장의 테이블 리스트 재생성
	$scope.getReTableNmList = function (storeCd, tableCd, grindSet) {
		var url = "/sale/status/table/day/tableNmList.sb";
	    var params = {};
	    params.storeCd = storeCd;
	    params.tableCd = tableCd;
	    params.hqOfficeCd = $("#HqOfficeCd").val();
	    
	    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
	    	params['sid'] = document.getElementsByName('sessionId')[0].value;
	    }
	    
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
	    			var arrTblGrpCd = [];

	    			for (var i = 0; i < list.length; i++) {
	    				arrStroreTable.push(list[i].tableCd);
	    				arrStoreTableNm.push(list[i].storeNm + "||" + list[i].tableNm);
	    				arrTblGrpCd.push(list[i].tblGrpCd);
	    			}

	    			$("#tableDayOfWeekSelectTableCd").val(arrStroreTable.join());
	    			$("#tableDayOfWeekSelectTableName").val(arrStoreTableNm.join());

	    			storeTableCd = $("#tableDayOfWeekSelectTableCd").val();
	    			storeTableNm = $("#tableDayOfWeekSelectTableName").val();
	    			tblGrpCd = arrTblGrpCd.join();

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

		  var grid = wijmo.Control.getControl("#tableDayOfWeekGrid");

		  var colLength = grid.columns.length;

		  while(grid.columns.length > 4){
	            grid.columns.removeAt(grid.columns.length-1);
	        }

		  var arrTableCd = storeTableCd.split(',');
		  var arrTableNm = storeTableNm.split(',');
		  var arrTblGrpCd = tblGrpCd.split(',');

		  if (arrTableCd != null) {
			  for(var i = 0; i < arrTableCd.length; i++) {

				  var colValue = arrTableCd[i];
				  var colName = arrTableNm[i];
				  var colSplit = colName.split('||');

				  grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.realSaleAmt"], binding: 'realSaleAmtT'+(i), width: 80, align: 'right', isReadOnly: 'true', aggregate: 'Sum'}));
				  grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.saleCnt"], binding: 'saleCntT'+(i), width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));
				  grid.columns.push(new wijmo.grid.Column({header: messages["tableDay.guestCnt"], binding: 'guestCnt1T'+(i), width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));

				  grid.columnHeaders.setCellData(0, 'realSaleAmtT'+(i), colSplit[0]);
				  grid.columnHeaders.setCellData(0, 'saleCntT'+(i), colSplit[0]);
				  grid.columnHeaders.setCellData(0, 'guestCnt1T'+(i), colSplit[0]);

				  grid.columnHeaders.setCellData(1, 'realSaleAmtT'+(i), arrTblGrpCd[i] + "(" + colSplit[1] + ")");
				  grid.columnHeaders.setCellData(1, 'saleCntT'+(i), arrTblGrpCd[i] + "(" + colSplit[1] + ")");
				  grid.columnHeaders.setCellData(1, 'guestCnt1T'+(i), arrTblGrpCd[i] + "(" + colSplit[1] + ")");

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
