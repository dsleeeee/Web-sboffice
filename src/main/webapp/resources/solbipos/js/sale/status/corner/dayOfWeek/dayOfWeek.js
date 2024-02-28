/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('cornerDayOfWeekCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('cornerDayOfWeekCtrl', $scope, $http, $timeout, true));

	$scope.srchCornerDayOfWeekStartDate = wcombo.genDateVal("#srchCornerDayOfWeekStartDate", getToday());
	$scope.srchCornerDayOfWeekEndDate   = wcombo.genDateVal("#srchCornerDayOfWeekEndDate", getToday());

	//조회조건 콤보박스 데이터 Set
	//  $scope._setComboData("cornerDayOfWeekListScaleBox", gvListScaleBoxData);

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {

		var storeCd = $("#cornerDayOfWeekSelectStoreCd").val();
		$scope.getReCornerNmList(storeCd, "", false);

		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("cornerDayOfWeekCtrl");

		// 그리드 링크 효과
		s.formatItem.addHandler(function (s, e) {
			if (e.panel === s.cells) {
				var col = s.columns[e.col];

				if (col.binding.substring(0, 10) === "totSaleQty" || col.binding.substring(0, 7) === "saleQty") { // 수량합계
					var item = s.rows[e.row].dataItem;
					wijmo.addClass(e.cell, 'wijLink');
					wijmo.addClass(e.cell, 'wj-custom-readonly');
				}
			}
		});

		// 그리드 클릭 이벤트
		s.addEventListener(s.hostElement, 'mousedown', function (e) {
			var ht = s.hitTest(e);

			/* 머지된 헤더 셀 클릭시 정렬 비활성화
			* 헤더 cellType: 2 && 머지된 row 인덱스: 0, 1 && 동적 생성된 column 인덱스 4 초과
			* 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
			*/
			if(ht.cellType == 2 && ht.row < 2 && ht.col > 2) {
				s.allowSorting = false;
			} else {
				s.allowSorting = true;
			}

			if (ht.cellType === wijmo.grid.CellType.Cell) {
				var col         = ht.panel.columns[ht.col];
				var selectedRow = s.rows[ht.row].dataItem;
				var params      = {};
				params.chkPop   = "cornerDayOfWeekProdPop"; // 매출관리>매출현황>코너별>요일별탭
				params.yoil	 	= selectedRow.yoil;
				if(!$scope.isChecked){
					params.startDate = wijmo.Globalize.format($scope.srchCornerDayOfWeekStartDate.value, 'yyyyMMdd');
					params.endDate = wijmo.Globalize.format($scope.srchCornerDayOfWeekEndDate.value, 'yyyyMMdd');
				}
				var storeCornr   =  $scope.searchCornrCd.split(",");
				var arrStoreCornr     = [];
				for(var i=0; i < storeCornr.length; i++) {
					var temp = storeCornr[i];
					arrStoreCornr.push(temp);
				}

				if (col.binding.substring(0, 10) === "totSaleQty") { // 수량합계
					params.arrStoreCornr	 = arrStoreCornr;
					params.storeCd = $scope.searchStoreCd;
					$scope._broadcast('saleComProdCtrl', params);
				}else if(col.binding.substring(0, 7) === "saleQty") {
					params.arrStoreCornr   = arrStoreCornr[Math.floor(ht.col/2) - 2];
					var len = params.arrStoreCornr.indexOf("||");
					params.storeCd 	 = arrStoreCornr[Math.floor(ht.col/2)-2].substring(0,len);
					$scope._broadcast('saleComProdCtrl', params);
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
			s.columnHeaders.rows.push(new wijmo.grid.Row());
			s.columnHeaders.rows.push(new wijmo.grid.Row());

			for(var i = 0; i < s.columnHeaders.rows.length; i++) {
				s.columnHeaders.setCellData(i, "yoil", messages["corner.yoil"]);
				s.columnHeaders.setCellData(i, "totRealSaleAmt", messages["corner.totRealSaleAmt"]);
				s.columnHeaders.setCellData(i, "totSaleQty", messages["corner.totSaleQty"]);
			}

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
	$scope.$on("cornerDayOfWeekCtrl", function (event, data) {
		$scope.searchCornerDayOfWeekList(true);

		var storeCd = $("#cornerDayOfWeekSelectStoreCd").val();
		var cornrCd = $("#cornerDayOfWeekSelectCornerCd").val();

		$scope.getReCornerNmList(storeCd, cornrCd, true);
	});

	//다른 컨트롤러의 broadcast 받기
	$scope.$on("cornerDayOfWeekCtrlSrch", function (event, data) {

		if( $("#cornerDayOfWeekSelectStoreCd").val() === ''){
			$scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
			return false;
		}

		if($("#cornerDayOfWeekSelectStoreCd").val().split(",").length > 10) {
			$scope._popMsg(messages["day.corner.storeCntAlert"]); // 매장은 최대 10개 선택 가능합니다.
			return false;
		}

		var storeCd = $("#cornerDayOfWeekSelectStoreCd").val();
		var cornrCd = $("#cornerDayOfWeekSelectCornerCd").val();

		if($("#cornerDayOfWeekSelectCornerCd").val() === ''){
			$scope.getReCornerNmList(storeCd, "" , true, true);
		}else {
			$scope.searchCornerDayOfWeekList(false);
			$scope.getReCornerNmList(storeCd, cornrCd, true);
		}
	});

	// 코너별매출 요일별 리스트 조회
	$scope.searchCornerDayOfWeekList = function (isPageChk) {
		// 파라미터
		var params       = {};
		params.storeCd   = $("#cornerDayOfWeekSelectStoreCd").val();
		params.cornrCd   = $("#cornerDayOfWeekSelectCornerCd").val();
		//    params.listScale = $scope.cornerDayOfWeekListScale; //-페이지 스케일 갯수
		params.isPageChk = isPageChk;

		$scope.searchCornrCd = params.cornrCd;
		$scope.searchStoreCd = params.storeCd;

		//등록일자 '전체기간' 선택에 따른 params
		if(!$scope.isChecked){
			$scope.startDateForDt = wijmo.Globalize.format($scope.srchCornerDayOfWeekStartDate.value, 'yyyyMMdd');
			$scope.endDateForDt = wijmo.Globalize.format($scope.srchCornerDayOfWeekEndDate.value, 'yyyyMMdd');

			params.startDate = wijmo.Globalize.format($scope.srchCornerDayOfWeekStartDate.value, 'yyyyMMdd');
			params.endDate = wijmo.Globalize.format($scope.srchCornerDayOfWeekEndDate.value, 'yyyyMMdd');
		}
		if(params.startDate > params.endDate){
			$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
			return false;
		}
		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/corner/dayOfWeek/list.sb", params, function(){
			var flex = $scope.flex;
			//row수가 0이면
			if(flex.rows.length === 0){

				var grid = wijmo.Control.getControl("#cornrDayOfWeekGrid");
				//컬럼 삭제
				while(grid.columns.length > 3){
					grid.columns.removeAt(grid.columns.length-1);
				}
			}
		});

	};

	//전체기간 체크박스 클릭이벤트
	$scope.isChkDt = function() {
		$scope.srchCornerDayOfWeekStartDate.isReadOnly = $scope.isChecked;
		$scope.srchCornerDayOfWeekEndDate.isReadOnly = $scope.isChecked;
	};


	//매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.cornerDayOfWeekSelectStoreShow = function () {
		$scope._broadcast('cornerDayOfWeekSelectStoreCtrl');
	};

	//코너선택 모듈 팝업 사용시 정의
	//함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	//_broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.cornerDayOfWeekSelectCornerShow = function () {
		$scope._broadcast('cornerDayOfWeekSelectCornerCtrl');
	};

	//엑셀 다운로드
	$scope.excelDownloadDayOfWeek = function () {
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
			}, '매출현황_코너별_요일별_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	};


	//매장의 코너(corner) 리스트 조회
	//	$scope.getCornerNmList = function () {
	//		var storeCd = $("#cornerDayOfWeekSelectStoreCd").val();
	//		var cornrCd = $("#cornerDayOfWeekSelectCornerCd").val();
	//		$scope.getReCornerNmList(storeCd, cornrCd, false);
	//	};

	// 조회조건 매장 선택 팝업 닫힐 때 메서드
	$scope.closeSelectStore = function () {
		var storeCd = $("#cornerDayOfWeekSelectStoreCd").val();
		$scope.getReCornerNmList(storeCd, "",  false);
	};

	// 조회조건 코너 선택 팝업 닫힐 때 메서드
	$scope.closeSelectCorner = function () {
		var storeCd = $("#cornerDayOfWeekSelectStoreCd").val();
		var tableCd = $("#cornerDayOfWeekSelectCornerCd").val();
		$scope.getReCornerNmList(storeCd, tableCd,  false);
	};

	//매장의 코너 리스트 재생성
	$scope.getReCornerNmList = function (storeCd, cornrCd, gridSet, cornrSet) {
		var url = "/sale/status/corner/corner/cornerNmList.sb";
		var params = {};
		params.storeCd = storeCd;
		params.cornrCd = cornrCd;
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
					var arrStoreCornr = [];
					var arrStoreCornrNm = [];

					for (var i = 0; i < list.length; i++) {
						arrStoreCornr.push(list[i].cornrCd);
						arrStoreCornrNm.push(list[i].storeNm + "||" + list[i].cornrNm);
					}

					$("#cornerDayOfWeekSelectCornerCd").val(arrStoreCornr.join());
					$("#cornerDayOfWeekSelectCornerName").val(arrStoreCornrNm.join());

					storeCornrCd = $("#cornerDayOfWeekSelectCornerCd").val();
					storeCornrNm = $("#cornerDayOfWeekSelectCornerName").val();

					if(gridSet){
						$scope.makeDataGrid();
					}
					if(cornrSet) {
						var vScope = agrid.getScope('cornerDayOfWeekCtrl');
						vScope.searchCornerDayOfWeekList(false);
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

		var grid = wijmo.Control.getControl("#cornrDayOfWeekGrid");

		var colLength = grid.columns.length;

		while(grid.columns.length > 3){
			grid.columns.removeAt(grid.columns.length-1);
		}

		var arrCornrCd = storeCornrCd.split(',');
		var arrCornrNm = storeCornrNm.split(',');

		if (arrCornrCd != "") {
			for(var i = 1; i < arrCornrCd.length + 1; i++) {

				var colValue = arrCornrCd[i-1];
				var colName = arrCornrNm[i-1];
				var colSplit = colName.split('||');
				var colValSplit = colValue.split('||');
				//				  if(colSplit[0] == null || colSplit[0] == "" || colSplit[0] == "null"){
				//					  colSplit[0] = "테스트 매장"+i;
				//				  }

				grid.columns.push(new wijmo.grid.Column({header: messages["corner.realSaleAmt"], binding: 'realSaleAmt'+(i-1), width: 100, align: 'right', isReadOnly: 'true', aggregate: 'Sum'}));
				grid.columns.push(new wijmo.grid.Column({header: messages["corner.saleQty"], binding: 'saleQty'+(i-1), width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));

				grid.columnHeaders.setCellData(0, 3+((i-1)*2), "[" + colValSplit[0] + "]" + colSplit[0]);
				grid.columnHeaders.setCellData(0, 4+((i-1)*2), "[" + colValSplit[0] + "]" + colSplit[0]);

				grid.columnHeaders.setCellData(1, 3+((i-1)*2), "[" + colValSplit[1] + "]"  + colSplit[1]);
				grid.columnHeaders.setCellData(1, 4+((i-1)*2), "[" + colValSplit[1] + "]"  + colSplit[1]);
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