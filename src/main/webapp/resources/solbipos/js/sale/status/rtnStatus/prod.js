/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('rtnStatusProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnStatusProdCtrl', $scope, $http, $timeout, true));

//조회조건 콤보박스 데이터 Set
  $scope._setComboData("rtnStatusProdListScaleBox", gvListScaleBoxData);
  $scope.isSearch = false;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	$scope.ChkProdClassDisplay = false;

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("rtnStatusProdCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.lv1Nm    		= messages["rtnStatus.prodClassNm"];
    dataItem.lv2Nm  		= messages["rtnStatus.prodClassNm1"];
    dataItem.lv3Nm   		= messages["rtnStatus.prodClassNm2"];
    dataItem.prodCd 	    = messages["rtnStatus.prodCd"];
    dataItem.prodNm 	    = messages["rtnStatus.prodNm"];
    dataItem.barcdCd 	    = messages["rtnStatus.barcdCd"];
    dataItem.cnt 	        = messages["rtnStatus.rtnAmt"];
    dataItem.realSaleAmt 	= messages["rtnStatus.rtnAmt"];

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

    	/* 머지된 헤더 셀 클릭시 정렬 비활성화
    	 * 헤더 cellType: 2 && 머지된 row 인덱스: 0, 1 && 동적 생성된 column 인덱스 4 초과
    	 * 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
    	 */
    	if(ht.cellType == 2 && ht.row < 1 && ht.col > 5) {
    		s.allowSorting = false;
		} else {
			s.allowSorting = true;
		}

	});
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnStatusProdCtrl", function (event, data) {
    $scope.searchRtnStatusProdList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

//다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnStatusProdCtrlSrch", function (event, data) {
    $scope.searchRtnStatusProdList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 코너별매출일자별 리스트 조회
  $scope.searchRtnStatusProdList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.storeCd   = $("#rtnStatusProdSelectStoreCd").val();
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk; //-페이징 초기화
    
    $scope.excelStoreCd		= params.storeCd;
    $scope.excelListScale	= params.listScale;
    $scope.excelStartDate	= "";
    $scope.excelEndDate		= "";
    $scope.isSearch			= true;
    
    //등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
		params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
	//	    params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
	    params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyy-MM-dd');
	    params.endDate   = (params.endDate).split("-");
	    var endDay 		 = ( new Date(params.endDate[0],params.endDate[1], 0) ).getDate();
	    params.endDate 	 = params.endDate[0] + params.endDate[1] + endDay;
	    
	    $scope.excelStartDate	= params.startDate;
	    $scope.excelEndDate		= params.endDate;
	}
	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}
	
	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/rtnStatus/prod/list.sb", params);
  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.rtnStatusProdStartDateCombo.isReadOnly = $scope.isChecked;
    $scope.rtnStatusProdEndDateCombo.isReadOnly = $scope.isChecked;
  };

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.rtnStatusProdSelectStoreShow = function () {
    $scope._broadcast('rtnStatusProdSelectStoreCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownloadDay = function () {
	  var params     = {};
	  $scope._broadcast('rtnStatusProdExcelCtrl',params);
  };

  //상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
	  var columns = $scope.flex.columns;

	  for(var i=0; i<columns.length; i++){
		  if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
			  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
		  }
	  }
  };

}]);


app.controller('rtnStatusProdExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('rtnStatusProdExcelCtrl', $scope, $http, $timeout, true));

	var checkInt = true;

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	    // <-- 그리드 헤더2줄 -->
	    // 헤더머지
	    s.allowMerging = 2;
	    s.columnHeaders.rows.push(new wijmo.grid.Row());

	    // 첫째줄 헤더 생성
	    var dataItem         = {};
	    dataItem.lv1Nm    		= messages["rtnStatus.prodClassNm"];
	    dataItem.lv2Nm  		= messages["rtnStatus.prodClassNm1"];
	    dataItem.lv3Nm   		= messages["rtnStatus.prodClassNm2"];
	    dataItem.prodCd 	    = messages["rtnStatus.prodCd"];
	    dataItem.prodNm 	    = messages["rtnStatus.prodNm"];
	    dataItem.barcdCd 	    = messages["rtnStatus.barcdCd"];
	    dataItem.cnt 	        = messages["rtnStatus.rtnAmt"];
	    dataItem.realSaleAmt 	= messages["rtnStatus.rtnAmt"];

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
	$scope.$on("rtnStatusProdExcelCtrl", function (event, data) {
		if(data != undefined && $scope.isSearch) {
			$scope.searchRtnStatusProdExcelList(true);
			// 기능수행 종료 : 반드시 추가
			event.preventDefault();
		} else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

	});

	//상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
	$scope.isChkProdClassDisplay = function(){
		var columns = $scope.excelFlex.columns;

		for(var i=0; i<columns.length; i++){
			if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
				$scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
			}
		}
	};
	  
	// 전체 엑셀 리스트 조회
	$scope.searchRtnStatusProdExcelList = function (isPageChk) {// 파라미터
		
		// 파라미터
		var params     = {};
		params.storeCd		= $scope.excelStoreCd;
	    params.listScale	= $scope.excelListScale;
	    params.startDate	= $scope.excelStartDate;
	    params.endDate	= $scope.excelEndDate;

		$scope.isChkProdClassDisplay();

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/rtnStatus/prod/excelList.sb", params, function(){			    
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
				}, $(menuNm).selector + '_'+messages["rtnStatus.rtnStatus"]+'_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);
		});
	};

}]);