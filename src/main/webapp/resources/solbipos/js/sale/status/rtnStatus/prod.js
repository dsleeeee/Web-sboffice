/****************************************************************
 *
 * 파일명 : prod.js
 * 설  명 : 상품별 > 결제수단별 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.06     김진        1.0
 * 2021.01.04     김설아      1.0           주석
 *
 * **************************************************************/
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

	// 검색조건에 조회기간
	var startMonth = new wijmo.input.InputDate('#startMonth', {
		format       : "yyyy-MM",
		selectionMode: "2" // 달력 선택 모드(1:day 2:month)
	});
	var endMonth = new wijmo.input.InputDate('#endMonth', {
		format       : "yyyy-MM",
		selectionMode: "2" // 달력 선택 모드(1:day 2:month)
	});

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  	// 상품분류 항목표시
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
    dataItem.pathNm    		= messages["rtnStatus.prodClassNm"];
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
    };
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
    $scope.searchRtnStatusProdList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 코너별매출일자별 리스트 조회
  $scope.searchRtnStatusProdList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $("#rtnStatusProdSelectStoreCd").val();
    params.listScale = $scope.listScaleCombo.text;
	params.startDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM') + '01';
	params.endDate = wijmo.Globalize.format(endMonth.value, 'yyyyMM') + '31';

	if(params.startDate > params.endDate){
		$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		return false;
	}

	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/rtnStatus/prod/getRtnStatusProdList.sb", params);
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
	  params.storeCd   = $("#rtnStatusProdSelectStoreCd").val();
	  params.startDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM') + '01';
	  params.endDate = wijmo.Globalize.format(endMonth.value, 'yyyyMM') + '31';

	  $scope._broadcast('rtnStatusProdExcelCtrl', params);
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


app.controller('rtnStatusProdExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('rtnStatusProdExcelCtrl', $scope, $http, $timeout, true));

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
		dataItem.pathNm    		= messages["rtnStatus.prodClassNm"];
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
		$scope.searchRtnStatusProdExcelList(data);
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});

	// 전체 엑셀 리스트 조회
	$scope.searchRtnStatusProdExcelList = function (data) {
		// 파라미터
		var params     = {};
		params.storeCd = data.storeCd;
		params.startDate = data.startDate;
		params.endDate = data.endDate;

		$scope.isChkProdClassDisplay();

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/rtnStatus/prod/getRtnStatusProdExcelList.sb", params, function(){
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
				}, $(menuNm).selector + '_상품별 반품현황_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);
		});
	};

	//상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
	$scope.isChkProdClassDisplay = function(){
		var columns = $scope.excelFlex.columns;

		for(var i=0; i<columns.length; i++){
			if(columns[i].binding === 'pathNm'){
				$scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
			}
		}
	};

}]);