/**
 * get application
 */
var app = agrid.getApp();

/** 판매자별(월별) 상세현황 controller */
app.controller('empMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('empMonthCtrl', $scope, $http, $timeout, true));
         
	// 콤보박스 데이터 Set
	$scope._setComboData('empMonthlistScaleBox', gvListScaleBoxData);
  
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
					
		//  picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("empMonthCtrl");
	
	    // 그리드 링크 효과
	    s.formatItem.addHandler(function (s, e) {
	      if (e.panel === s.cells) {
	        var col = s.columns[e.col];
	
	        if (col.binding === "totBillCnt") { // 수량
	        	var item = s.rows[e.row].dataItem;
	          	wijmo.addClass(e.cell, 'wijLink');
	          	wijmo.addClass(e.cell, 'wj-custom-readonly');
	        }
	      }
	    });
	    
	    // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
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
	        	params.chkPop	= "empPop";
	        	params.storeCd   = $("#empMonthSelectStoreCd").val();
	        	params.saleMonth   = selectedRow.saleDate;
	        if (col.binding === "totBillCnt") { // 수량
	          $scope._broadcast('saleComProdCtrl', params);
	        }
	      }
	    });
	    
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
	    
	    // <-- 그리드 헤더3줄 -->
	    // 헤더머지
	    s.allowMerging = 'ColumnHeaders';
	    s.columnHeaders.rows.push(new wijmo.grid.Row());
		s.columnHeaders.rows.push(new wijmo.grid.Row());

	    // 첫째줄 헤더 생성
		for(var i = 0; i < s.columnHeaders.rows.length; i++) {
			s.columnHeaders.setCellData(i, "saleDate", messages["empday.saleDate"]);
			s.columnHeaders.setCellData(i, "storeCnt", messages["empday.storeCnt"]);
			s.columnHeaders.setCellData(i, "realSaleAmtTot", messages["empday.realSaleAmtTot"]);
			s.columnHeaders.setCellData(i, "totBillCnt", messages["empday.totBillCnt"]);
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
  $scope.$on("empMonthCtrl", function (event, data) {
    if( $("#empMonthSelectStoreCd").val() === ''){
   	 	$scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
   	 	return false;
    }   
    if(wijmo.Globalize.format($scope.startDate, 'yyyyMM') > wijmo.Globalize.format($scope.endDate, 'yyyyMM')){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    } 	  
	$scope.getEmpNmList(true);    
	$scope.searchEmpMonthList(true);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("empMonthCtrlSrch", function (event, data) {
    if( $("#empMonthSelectStoreCd").val() === ''){
   	 	$scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
   	 	return false;
    }   
    if(wijmo.Globalize.format($scope.startDate, 'yyyyMM') > wijmo.Globalize.format($scope.endDate, 'yyyyMM')){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }
	$scope.getEmpNmList(false);    
	$scope.searchEmpMonthList(false);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
  // 판매자월별 리스트 조회
  $scope.searchEmpMonthList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.storeCd   = $("#empMonthSelectStoreCd").val();
    if(!$scope.isChecked){
    	params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMM');
    	params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMM');
    }
    
    if($scope.isCheckedEmpAll){
    	params.empChk = "Y";
    }else{
    	params.empChk = "N";
    }
	params.isPageChk = isPageChk;
	params.listScale = $scope.empMonthlistScale; //-페이지 스케일 갯수
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/emp/month/list.sb", params, function() {
		var flex = $scope.flex;
		//row수가 0이면
		if(flex.rows.length === 0){
			
			var grid = wijmo.Control.getControl("#empMonthGrid");
			//컬럼 삭제
			while(grid.columns.length > 4){
		          grid.columns.removeAt(grid.columns.length-1);
		    }
		}   
    });

  }; 
  
  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.empMonthSelectStoreShow = function () {
    $scope._broadcast('empMonthSelectStoreCtrl');
  };
  
  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
	$scope.empMonthStartDateCombo.isReadOnly = $scope.isChecked;
	$scope.empMonthEndDateCombo.isReadOnly = $scope.isChecked;
  };
  
  //판매자 조회
  $scope.getEmpNmList = function () {
	  	$scope.flex.refresh();
	  	// 파라미터
	  	var params       = {};	  	  	
	  	params.storeCd   = $("#empMonthSelectStoreCd").val();
	  	
	  	if(!$scope.isChecked){
		  	params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMM');
		  	params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMM');
	  	}
	  
	    if($scope.isCheckedEmpAll){
	    	params.empChk = "Y";
	    }else{
	    	params.empChk = "N";
	    }
	  
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._postJSONQuery.withOutPopUp("/sale/status/emp/month/empList.sb", params, function(response) {
	    	
		  var length = response.data.data.list.length;
		  var grid = wijmo.Control.getControl("#empMonthGrid");
		  
		  if(length != "" || length != null){
	   	 		
				while(grid.columns.length > 4){
					grid.columns.removeAt(grid.columns.length-1);
				}
	   	 	
			    //첫째줄 헤더 생성
			   	for(var i=0; i<length; i++){
			   		grid.columns.push(new wijmo.grid.Column({header: messages["empday.realSaleAmt"], binding: 'realSaleAmt'+i, align: "right" , isReadOnly: "true", aggregate: "Sum"}));
			   		grid.columns.push(new wijmo.grid.Column({header: messages["empday.billCnt"], binding: 'billCnt'+i, align: "center" , isReadOnly: "true", aggregate: "Sum"}));
			   		grid.columnHeaders.setCellData(0, 4+(i*2), response.data.data.list[i].storeNm);
			   		grid.columnHeaders.setCellData(0, 5+(i*2), response.data.data.list[i].storeNm);
			   		grid.columnHeaders.setCellData(1, 4+(i*2), response.data.data.list[i].nmcodeNm);
			   		grid.columnHeaders.setCellData(1, 5+(i*2), response.data.data.list[i].nmcodeNm);		
		    	    
					grid.columnHeaders.rows[0].allowSorting = false;
					grid.columnHeaders.rows[1].allowSorting = false;
			   	}
		   	
	   	 	}
	   	 	
			// 그리드 링크 효과
			grid.formatItem.addHandler(function (s, e) {
				if (e.panel === s.cells) {
					var col = s.columns[e.col];
				
					if (col.binding.substring(0, 7) === "billCnt") { // 영수건수
						wijmo.addClass(e.cell, 'wijLink');
					}
				}
			});
			
			// 그리드 클릭 이벤트
			grid.addEventListener(grid.hostElement, 'mousedown', function (e) {
			   	var ht = grid.hitTest(e);
			   	if (ht.cellType === wijmo.grid.CellType.Cell) {
			   		var col         = ht.panel.columns[ht.col];
			   		var selectedRow = grid.rows[ht.row].dataItem;
			   		var params      = {};
			   		var storeNm		= grid.columnHeaders.getCellData(0,ht.col,true);			   		
			   		var storeCd 	= storeNm.match( /[^()]+(?=\))/g); 
			   		var empNo		= grid.columnHeaders.getCellData(1,ht.col,true);			   		
			   		var empNoCd 	= empNo.match( /[^()]+(?=\))/g); 
			   		
			   		params.chkPop	= "empPop";
			   		params.empNo    = empNoCd;
			   		params.storeCd   = storeCd;
			   		params.saleMonth   = selectedRow.saleDate;

			   		if (col.binding.substring(0, 7) === "billCnt") { //영수건수 클릭
			   			$scope._broadcast('saleComProdCtrl', params);
			   		}
			   	}
			});	
			
		    $scope.flex.refresh();
	    });
	  
  };
  
  // 엑셀 다운로드
  $scope.excelDownloadEmpMonth = function () {
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
      }, '매출현황_판매자별_월별_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
  
}]);
