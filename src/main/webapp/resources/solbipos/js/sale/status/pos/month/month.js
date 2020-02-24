/**
 * get application
 */
var app = agrid.getApp();

/** 월별(포스별 매출) controller */
app.controller('posMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('posMonthCtrl', $scope, $http, $timeout, true));

	//조회조건 콤보박스 데이터 Set
	$scope._setComboData("posMonthListScaleBox", gvListScaleBoxData);

	var checkInt = true;

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {

		var storeCd = $("#posMonthSelectStoreCd").val();

		$scope.getRePosNmList(storeCd);

		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("posMonthCtrl");

		// 그리드 링크 효과
	    s.formatItem.addHandler(function (s, e) {
	      if (e.panel === s.cells) {
	        var col = s.columns[e.col];

	        if (col.binding.substring(col.binding.length, col.binding.length-7) === "SaleCnt") { // 수량합계
	        	var item = s.rows[e.row].dataItem;
	          	wijmo.addClass(e.cell, 'wijLink');
	          	wijmo.addClass(e.cell, 'wj-custom-readonly');
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
			s.columnHeaders.setCellData(i, "yearMonth", messages["pos.yearMonth"]);
			s.columnHeaders.setCellData(i, "saleStoreCnt", messages["pos.saleStore"]);
			s.columnHeaders.setCellData(i, "totSaleAmt", messages["pos.totSaleAmt"]);
			s.columnHeaders.setCellData(i, "totDcAmt", messages["pos.totDcAmt"]);
			s.columnHeaders.setCellData(i, "totRealSaleAmt", messages["pos.totRealSaleAmt"]);
			s.columnHeaders.setCellData(i, "totSaleCnt", messages["pos.totSaleQty"]);
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
	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("posMonthCtrl", function (event, data) {

		$scope.searchPosMonthList(true);

		var storeCd = $("#posMonthSelectStoreCd").val();
		var posCd = $("#posMonthSelectPosCd").val();

		$scope.getRePosNmList(storeCd, posCd);
	});

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("posMonthCtrlSrch", function (event, data) {

		$scope.searchPosMonthList(false);

		var storeCd = $("#posMonthSelectStoreCd").val();
		var posCd = $("#posMonthSelectPosCd").val();

		$scope.getRePosNmList(storeCd, posCd);
	});

	// 포스별매출월별 리스트 조회
	$scope.searchPosMonthList = function (isPageChk) {

		// 파라미터
		var params = {};
		params.storeCd = $("#posMonthSelectStoreCd").val();
		params.posNo = $("#posMonthSelectPosCd").val();
		params.listScale = $scope.posMonthListScale; //-페이지 스케일 갯수
		params.arrPosCd = $scope.comboArray; //-포스정보
		params.isPageChk = isPageChk;

		//등록일자 '전체기간' 선택에 따른 params
		if(!$scope.isChecked){
			params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMM') + "01";
	    	params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMM') + "31";
		}

		if(params.startDate > params.endDate){
			$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
			return false;
		}

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/pos/month/list.sb", params);
	};

	//전체기간 체크박스 클릭이벤트
	$scope.isChkDt = function() {
		$scope.posMonthStartDateCombo.isReadOnly = $scope.isChecked;
		$scope.posMonthEndDateCombo.isReadOnly = $scope.isChecked;
	};

	//매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.posMonthSelectStoreShow = function () {
		$scope._broadcast('posMonthSelectStoreCtrl');
	};

	//포스선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.posMonthSelectPosShow = function () {
		$scope._broadcast('posMonthSelectPosCtrl');
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
			}, messages["month.sale"]+'_'+messages["empsale.pos"]+'_'+messages["pos.month"]+'_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	};

	//매장의 포스(pos) 리스트 조회
	$scope.getPosNmList = function () {
		var url             = '/sale/status/pos/pos/posNmList.sb';
		var comboParams     = {};

		comboParams.storeCd = $("#posMonthSelectStoreCd").val();
	};

	//매장의 포스 리스트 재생성
	$scope.getRePosNmList = function (storeCd, posCd) {
		var url = "/sale/status/pos/pos/posNmList.sb";
		var params = {};
	    params.storeCd = storeCd;
	    params.PosNo = posCd;
	    params.hqOfficeCd = $("#posMonthSelectHqOfficeCd").val();

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
	    			var arrStorePos = [];
	    			var arrStorePosNm = [];

	    			for (var i = 0; i < list.length; i++) {
	    				arrStorePos.push(list[i].posCd);
	    				arrStorePosNm.push(list[i].storeNm + "||" + list[i].posNm);
	    			}

	    			$("#posMonthSelectPosCd").val(arrStorePos.join());
	    			$("#posMonthSelectPosName").val(arrStorePosNm.join());

	    			storePosCd = $("#posMonthSelectPosCd").val();
	    			storePosNm = $("#posMonthSelectPosName").val();

	    			if (!checkInt) {
	    				$scope.makeDataGrid();
	    			} else {
	    				checkInt = false;
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

		  var grid = wijmo.Control.getControl("#posMonthGrid");

		  var colLength = grid.columns.length;

		  if (grid.columns.length > 6) {
			  for(var i = 6; i < colLength; i++) {
				  grid.columns.removeAt(grid.columns.length-1);
			  }
		  }

		  var arrPosCd = storePosCd.split(',');
		  var arrPosNm = storePosNm.split(',');

		  if (arrPosCd != null) {

			  for(var i = 1; i < arrPosCd.length + 1; i++) {

				  var colValue = arrPosCd[i-1];
				  var colName = arrPosNm[i-1];
				  var colSplit = colName.split('||');
				  var colSplit2 = colValue.split('||');

				  grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'SaleAmt", width: 100, align: "right", isReadOnly: "true", aggregate: "Sum"}));
				  grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'DcAmt", width: 100, align: "right", isReadOnly: "true", aggregate: "Sum"}));
				  grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'RealSaleAmt", width: 100, align: "right", isReadOnly: "true", aggregate: "Sum"}));
				  grid.columns.push(new wijmo.grid.Column({binding: "'"+colValue.toLowerCase()+"'SaleCnt", width: 100, align: "right", isReadOnly: "true", aggregate: "Sum"}));

				  grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'SaleAmt", colSplit[0]+"("+colSplit2[0]+")");
				  grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'DcAmt", colSplit[0]+"("+colSplit2[0]+")");
				  grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'RealSaleAmt", colSplit[0]+"("+colSplit2[0]+")");
				  grid.columnHeaders.setCellData(0, "'"+colValue.toLowerCase()+"'SaleCnt", colSplit[0]+"("+colSplit2[0]+")");

				  grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'SaleAmt", colSplit[1]);
				  grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'DcAmt", colSplit[1]);
				  grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'RealSaleAmt", colSplit[1]);
				  grid.columnHeaders.setCellData(1, "'"+colValue.toLowerCase()+"'SaleCnt", colSplit[1]);

				  grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'SaleAmt", messages["pos.SaleAmt"]);
				  grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'DcAmt", messages["pos.DcAmt"]);
				  grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'RealSaleAmt", messages["pos.realSaleAmt"]);
				  grid.columnHeaders.setCellData(2, "'"+colValue.toLowerCase()+"'SaleCnt", messages["pos.saleQty"]);

			  }
		  }
		  
		  // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
		  grid.addEventListener(grid.hostElement, 'mousedown', function (e) {
		    	var ht = grid.hitTest(e);
		    	if (ht.cellType === wijmo.grid.CellType.Cell) {
	
		    		var col         = ht.panel.columns[ht.col];
		    		var selectedRow = grid.rows[ht.row].dataItem;
			   		var storeNm		= grid.columnHeaders.getCellData(0,ht.col,true);			   		
			   		var storeCd 	= storeNm.match( /[^()]+(?=\))/g); 
			   		var posNo		= grid.columnHeaders.getCellData(1,ht.col,true);			   		
			   		
		    		var params       = {};
		    		params.chkPop	= "posPop";    		
		    		params.saleMonth   = selectedRow.yearMonth;
		    		
		    		if (col.binding.substring(col.binding.length, col.binding.length-8) === "'SaleCnt") { 
			    		params.storeCd   = storeCd;
			    		params.posNo	 = posNo;
		    			$scope._broadcast('saleComProdCtrl', params); // 수량
		    		}else if (col.binding === "totSaleCnt") { // 수량합계
		    			params.storeCd   = $("#posMonthSelectStoreCd").val();
		    			$scope._broadcast('saleComProdCtrl', params);
		    		}
		    	}
		  });
		  
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

	  $scope.loadedRows = function (s, e) {

		  var rowLength = s.rows.length;
		  var arrPosCd = storePosCd.split(',');
		  var arrPosNm = storePosNm.split(',');

		  if (arrPosCd != null) {

			  for(var i = 1; i < arrPosCd.length + 1; i++) {

				  var colValue = arrPosCd[i-1];
				  var colName = arrPosNm[i-1];
				  var colSplit = colName.split('||');

				  for(var j = 0; j < rowLength; j++) {

					  var saleAmt = s.getCellData(j, "'"+colValue.toLowerCase()+"'SaleAmt", false);
					  var dcAmt = s.getCellData(j, "'"+colValue.toLowerCase()+"'DcAmt", false);
					  var realSaleAmt = s.getCellData(j, "'"+colValue.toLowerCase()+"'RealSaleAmt", false);
					  var saleCnt = s.getCellData(j, "'"+colValue.toLowerCase()+"'SaleCnt", false);

					  if (saleAmt == null || saleAmt == "") {
						  s.setCellData(j, "'"+colValue.toLowerCase()+"'SaleAmt", "0");
					  }

					  if (dcAmt == null || dcAmt == "") {
						  s.setCellData(j, "'"+colValue.toLowerCase()+"'DcAmt", "0");
					  }

					  if (realSaleAmt == null || realSaleAmt == "") {
						  s.setCellData(j, "'"+colValue.toLowerCase()+"'RealSaleAmt", "0");
					  }

					  if (saleCnt == null || saleCnt == "") {
						  s.setCellData(j, "'"+colValue.toLowerCase()+"'SaleCnt", "0");
					  }
				  }
			  }
		  }
	  }
}]);
