/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('cornerMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cornerMonthCtrl', $scope, $http, $timeout, true));

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("cornerMonthListScaleBox", gvListScaleBoxData);

  $scope.excelFg = false;
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	  var storeCd = $("#cornerMonthSelectStoreCd").val();
	  $scope.getReCornerNmList(storeCd, "", false);


    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("cornerMonthCtrl");

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
        var col          = ht.panel.columns[ht.col];
        var selectedRow  = s.rows[ht.row].dataItem;
        var params       = {};
		params.chkPop    = "cornerMonthProdPop"; // 매출관리>매출현황>코너별>월별탭
		params.startDate = selectedRow.saleYm.replace("-", "");
		params.endDate   = selectedRow.saleYm.replace("-", "");
		var storeCornr   = $scope.cornrCdForExcel.split(",");
		var arrStoreCornr     = [];
		for(var i=0; i < storeCornr.length; i++) {
			var temp = storeCornr[i];
			arrStoreCornr.push(temp);
		}

        if (col.binding.substring(0, 10) === "totSaleQty") { // 수량
			params.arrStoreCornr	 = arrStoreCornr;
			params.storeCd = $scope.storeCdForExcel;
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
    	s.columnHeaders.setCellData(i, "saleYm", messages["corner.saleYm"]);
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
  $scope.$on("cornerMonthCtrl", function (event, data) {
    $scope.searchCornerMonthList(true);

    var storeCd = $("#cornerMonthSelectStoreCd").val();
	var cornrCd = $("#cornerMonthSelectCornerCd").val();

	$scope.getReCornerNmList(storeCd, cornrCd, true);
  });

//다른 컨트롤러의 broadcast 받기
  $scope.$on("cornerMonthCtrlSrch", function (event, data) {
	  
	if( $("#cornerMonthSelectStoreCd").val() === ''){
    	 $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
    	 return false;
    }
	  if($("#cornerMonthSelectStoreCd").val().split(",").length > 10) {
		  $scope._popMsg(messages["day.corner.storeCntAlert"]); // 매장은 최대 10개 선택 가능합니다.
		  return false;
	  }

	  var storeCd = $("#cornerMonthSelectStoreCd").val();
	  var cornrCd = $("#cornerMonthSelectCornerCd").val();

	  if($("#cornerMonthSelectCornerCd").val() === ''){
		  $scope.getReCornerNmList(storeCd, "" , true, true);
	  }else {
		  $scope.searchCornerMonthList(false);
		  $scope.getReCornerNmList(storeCd, cornrCd, true);
	  }
  });

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
	$scope.srchCornerMonthStartDateCombo.isReadOnly = $scope.isChecked;
	$scope.srchCornerMonthEndDateCombo.isReadOnly = $scope.isChecked;
  };

  // 코너별매출일자별 리스트 조회
  $scope.searchCornerMonthList = function (isPageChk) {

	  var startDt = new Date(wijmo.Globalize.format($scope.startDate, 'yyyy-MM'));
	  var endDt = new Date(wijmo.Globalize.format($scope.endDate, 'yyyy-MM'));
	  var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

	  // 시작일자가 종료일자보다 빠른지 확인
	  if(startDt.getTime() > endDt.getTime()){
		  $scope._popMsg(messages['cmm.dateChk.error']);
		  return false;
	  }
	  // 조회일자 최대 3년(36개월) 제한
	  if (diffMonth > 36) {
		  $scope._popMsg(messages['cmm.dateOver.3year.error']);
		  return false;
	  }

    // 파라미터
    var params       = {};
    params.storeCd   = $("#cornerMonthSelectStoreCd").val();
    params.cornrCd   = $("#cornerMonthSelectCornerCd").val();
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    
    $scope.storeCdForExcel   = params.storeCd;
    $scope.cornrCdForExcel   = params.cornrCd;
    $scope.searchChecked = $scope.isChecked;

	//등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
	  params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
	  params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyy-MM-dd');
	  params.endDate   = (params.endDate).split("-");
  	  var endDay 		 = ( new Date(params.endDate[0],params.endDate[1], 0) ).getDate();
  	  params.endDate 	 = params.endDate[0] + params.endDate[1] + endDay;
  	  
  	  $scope.startDateForExcel   = params.startDate;
      $scope.endDateForExcel     = params.endDate;
	}
	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}
	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/corner/month/list.sb", params, function(){
		var flex = $scope.flex;
		//row수가 0이면
		if(flex.rows.length === 0){

			var grid = wijmo.Control.getControl("#cornrMonthGrid");
			//컬럼 삭제
			while(grid.columns.length > 3){
		          grid.columns.removeAt(grid.columns.length-1);
		    }
		}
	});

	$scope.excelFg = true;
  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.cornerMonthStartDateCombo.isReadOnly = $scope.isChecked;
    $scope.cornerMonthEndDateCombo.isReadOnly = $scope.isChecked;
  };


  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.cornerMonthSelectStoreShow = function () {
    $scope._broadcast('cornerMonthSelectStoreCtrl');
  };

  //코너선택 모듈 팝업 사용시 정의
  //함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  //_broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.cornerMonthSelectCornerShow = function () {
	  $scope._broadcast('cornerMonthSelectCornerCtrl');
  };

//엑셀 다운로드
  $scope.excelDownloadMonth = function () {

	  var startDt = new Date(wijmo.Globalize.format($scope.startDate, 'yyyy-MM'));
	  var endDt = new Date(wijmo.Globalize.format($scope.endDate, 'yyyy-MM'));
	  var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

	  // 시작일자가 종료일자보다 빠른지 확인
	  if(startDt.getTime() > endDt.getTime()){
		  $scope._popMsg(messages['cmm.dateChk.error']);
		  return false;
	  }
	  // 조회일자 최대 3년(36개월) 제한
	  if (diffMonth > 36) {
		  $scope._popMsg(messages['cmm.dateOver.3year.error']);
		  return false;
	  }

		// 파라미터
		var params = {};
		params.storeCd   = $scope.storeCdForExcel;
		params.cornrCd   = $scope.cornrCdForExcel;
		params.excelFg   = $scope.excelFg;

		if(!$scope.searchChecked){
			params.startDate = $scope.startDateForExcel;
			params.endDate 	 = $scope.endDateForExcel;
		}
		params.isPageChk = true;

		$scope._broadcast('cornerMonthExcelCtrl',params);
  };


  //매장의 코너(corner) 리스트 조회
//	$scope.getCornerNmList = function () {
//		var storeCd = $("#cornerMonthSelectStoreCd").val();
//		var cornrCd = $("#cornerMonthSelectCornerCd").val();
//		$scope.getReCornerNmList(storeCd, cornrCd, false);
//	};

	// 조회조건 매장 선택 팝업 닫힐 때 메서드
	$scope.closeSelectStore = function () {
		var storeCd = $("#cornerMonthSelectStoreCd").val();
		$scope.getReCornerNmList(storeCd, "",  false);
	};

	// 조회조건 코너 선택 팝업 닫힐 때 메서드
	$scope.closeSelectCorner = function () {
		var storeCd = $("#cornerMonthSelectStoreCd").val();
		var tableCd = $("#cornerMonthSelectCornerCd").val();
		$scope.getReCornerNmList(storeCd, tableCd,  false);
	};
	
	//매장의 코너 리스트 재생성
	$scope.getReCornerNmList = function (storeCd, cornrCd, gridSet, cornrSet) {
		var url = "/sale/status/corner/corner/cornerNmList.sb";
	    var params = {};
	    params.storeCd = storeCd;
	    params.cornrCd = cornrCd;
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
	    			var arrStoreCornr = [];
	    			var arrStoreCornrNm = [];

	    			for (var i = 0; i < list.length; i++) {
	    				arrStoreCornr.push(list[i].cornrCd);
	    				arrStoreCornrNm.push(list[i].storeNm + "||" + list[i].cornrNm);
	    			}

	    			$("#cornerMonthSelectCornerCd").val(arrStoreCornr.join());
	    			$("#cornerMonthSelectCornerName").val(arrStoreCornrNm.join());

	    			storeCornrCd = $("#cornerMonthSelectCornerCd").val();
	    			storeCornrNm = $("#cornerMonthSelectCornerName").val();

	    			if(gridSet){
	    				$scope.makeDataGrid();
	    			}
					if(cornrSet){
						var vScope = agrid.getScope('cornerMonthCtrl');
						vScope.searchCornerMonthList(false);
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

		  var grid = wijmo.Control.getControl("#cornrMonthGrid");

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

/** 월별(코너별 매출 엑셀 리스트) controller */
app.controller('cornerMonthExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cornerMonthExcelCtrl', $scope, $http, $timeout, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

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
    	s.columnHeaders.setCellData(i, "saleYm", messages["corner.saleYm"]);
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
  $scope.$on("cornerMonthExcelCtrl", function (event, data) {
	if(data != undefined && data.excelFg) {

		if(data.startDate > data.endDate){
		 	return false;
		}
		
		$scope.storeCd = data.storeCd;
		$scope.cornrCd = data.cornrCd;
		$scope.startDate = data.startDate;
		$scope.endDate = data.endDate;
		
		$scope.getReCornerNmList($scope.storeCd, $scope.cornrCd, true);
	}else{
		$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
	}
  });


  // 코너별매출일자별 리스트 조회
  $scope.searchCornerMonthList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.storeCd   = $scope.storeCd;
    params.cornrCd   = $scope.cornrCd;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.isPageChk = isPageChk;

	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquirySub("/sale/status/corner/month/excelList.sb", params, function(){
		var flex = $scope.excelFlex;
		
		//row수가 0이면
		if (flex.rows.length <= 0) {
			var grid = wijmo.Control.getControl("#cornrMonthExcelGrid");
			//컬럼 삭제
			while(grid.columns.length > 3){
		          grid.columns.removeAt(grid.columns.length-1);
		    }
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
			}, '매출현황_코너별_월별_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	});
  };


	//매장의 코너 리스트 재생성
	$scope.getReCornerNmList = function (storeCd, cornrCd, gridSet) {
		var url = "/sale/status/corner/corner/cornerNmList.sb";
	    var params = {};
	    params.storeCd = storeCd;
	    params.cornrCd = cornrCd;
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
	    			var arrStoreCornr = [];
	    			var arrStoreCornrNm = [];

	    			for (var i = 0; i < list.length; i++) {
	    				arrStoreCornr.push(list[i].cornrCd);
	    				arrStoreCornrNm.push(list[i].storeNm + "||" + list[i].cornrNm);
	    			}

	    			$("#cornerMonthSelectExcelCornerCd").val(arrStoreCornr.join());
	    			$("#cornerMonthSelectExcelCornerName").val(arrStoreCornrNm.join());

	    			storeCornrCd = $("#cornerMonthSelectExcelCornerCd").val();
	    			storeCornrNm = $("#cornerMonthSelectExcelCornerName").val();

	    			if(gridSet){
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

		  var grid = wijmo.Control.getControl("#cornrMonthExcelGrid");

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
		  
		  $scope.searchCornerMonthList(false);
	  }
}]);