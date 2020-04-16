/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('goalMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('goalMonthCtrl', $scope, $http, $timeout, true));

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("goalMonthListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("goalMonthCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
//        if (col.binding.substring(0, 10) === "totSaleQty" || col.binding.substring(0, 7) === "saleQty") { // 수량합계
//        	var item = s.rows[e.row].dataItem;
//          	wijmo.addClass(e.cell, 'wijLink');
//          	wijmo.addClass(e.cell, 'wj-custom-readonly');
//        }
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
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
        	params.chkPop   = "tablePop";
        	params.storeCd	 = $("#goalMonthSelectStoreCd").val();
        	params.startDate = selectedRow.saleDate;
        	params.endDate   = selectedRow.saleDate;
//        	if (col.binding.substring(0, 10) === "totSaleQty") { // 수량
//            	$scope._broadcast('saleComProdCtrl', params);
//            }
      }
    }, true);

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 'ColumnHeaders';
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    for(var i = 0; i < s.columnHeaders.rows.length-1; i++) {
    	s.columnHeaders.setCellData(i, "storeNm", messages["cmm.mrhst.nm"]);
    	s.columnHeaders.setCellData(i, "saleGoalYm", messages["goal.goalMonth.saleMonth"]);
    	s.columnHeaders.setCellData(i, "saleGoalDateCnt", messages["goal.goalMonth.monthDay"]);
    	s.columnHeaders.setCellData(i, "saleDateCnt", messages["goal.goalMonth.saleCnt"]);
    	s.columnHeaders.setCellData(i, "saleGoalMonthlyAmt", messages["goal.goalMonth.goalAmt"]);
    	s.columnHeaders.setCellData(i, "saleGoalAmt", messages["goal.goalMonth.goalAmt"]);
    	s.columnHeaders.setCellData(i, "totSaleAmt", messages["goal.goalMonth.realSaleAmt"]);
    	s.columnHeaders.setCellData(i, "goalAchiMonthly", messages["goal.goalMonth.achv"]);
    	s.columnHeaders.setCellData(i, "goalAchi", messages["goal.goalMonth.achv"]);
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
  $scope.$on("goalMonthCtrl", function (event, data) {
    $scope.searchGoalMonthList(true);
  });
  
  //다른 컨트롤러의 broadcast 받기(페이징 초기화)
  $scope.$on("goalMonthCtrlSrch", function (event, data) {
    $scope.searchGoalMonthList(false);
  });


  // 월별 목표대비 매출분석 리스트 조회
  $scope.searchGoalMonthList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.storeCd   = $("#goalMonthSelectStoreCd").val();
    params.isPageChk = isPageChk;
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수

	//등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
	  params.startDate = wijmo.Globalize.format($scope.goalMonthStartDateCombo.value, 'yyyyMM');
	  params.endDate = wijmo.Globalize.format($scope.goalMonthEndDateCombo.value, 'yyyyMM');
//	  params.endDate = (params.endDate).split("-");
//	  var endDay 		 = ( new Date(params.endDate[0],params.endDate[1], 0) ).getDate();
//  	  params.endDate 	 = params.endDate[0] + params.endDate[1] + endDay;
	}
	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}
	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/anals/goal/month/list.sb", params, function() {
	});
	
  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.goalMonthStartDateCombo.isReadOnly = $scope.isChecked;
    $scope.goalMonthEndDateCombo.isReadOnly = $scope.isChecked;
  };


  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.goalMonthSelectStoreShow = function () {
    $scope._broadcast('goalMonthSelectStoreCtrl');
  };
  
  //매출목표 등록 모듈 팝업 사용시 정의
  $scope.saleGoalReg = function () {
	  $scope._broadcast('saleGoalRegCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownloadGoalMonth = function () {
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
      },  '매출목표관리_월별 목표대비 매출_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);