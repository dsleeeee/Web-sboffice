/**
 * get application
 */
var app = agrid.getApp();

var vRowNum = [
    {"name":"30위","value":"30"},
    {"name":"50위","value":"50"},
    {"name":"100위","value":"100"}
];

/** 판매자별(월별) 상세현황 controller */
app.controller('storeRankCtrl', ['$scope', '$http', function ($scope, $http) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('storeRankCtrl', $scope, $http, true));
    
	// 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchRowNumCombo", vRowNum);
    
    // 조회조건 정렬순서 Set
    $scope.isCheckedSort = "1";
    
	// 조회일자 세팅
	$scope.srchStartDate = wcombo.genDateVal("#srchRankStartDate", getToday());
	$scope.srchEndDate   = wcombo.genDateVal("#srchRankEndDate", getToday());
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
					
		//  picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("storeRankCtrl");
		    	    
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
	    dataItem.storeNm  		= messages["store.storeNm"];
	    dataItem.totSaleAmt 	= messages["store.totSaleAmt"];
	    dataItem.totDcAmt 		= messages["store.totDcAmt"];
	    dataItem.realSaleAmt 	= messages["store.realSaleAmt"];
	    dataItem.saleDateCnt  	= messages["store.openDay"];
	    dataItem.realSaleAmtAvg = messages["store.openDayAmt"];
	    dataItem.billCnt 		= messages["store.billCnt"];
	    dataItem.totBillAmt 	= messages["store.totBillAmt"];
	    dataItem.totGuestCnt 	= messages["store.totGuestCnt"];
	    dataItem.storeRat 		= messages["store.storeRat"];

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
  $scope.$on("storeRankCtrl", function (event, data) {

	 $scope.getPayList();    
	 $scope.searchStoreRankList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };
  
  // 매장순위 리스트 조회
  $scope.searchStoreRankList = function () {

    // 파라미터
    var params       = {};  
    	params.storeCd   = $("#storeRankSelectStoreCd").val();
    if(!$scope.isChecked){
    	params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }
	if($scope.isCheckedPayAll){
		params.chkPay = "Y";
	}else{
		params.chkPay = "N";
	}
	params.chkSort = $scope.isCheckedSort;
	params.rowNum = $scope.rowNum;
	params.hqOfficeCd = $("#hqOfficeCd").val();
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/anals/store/rank/list.sb", params, function() {});

  }; 
   
  //결제수단 조회
  $scope.getPayList = function () {
	  $scope.flex.refresh();
	  // 파라미터
	  var params       = {};
	  
	  if($scope.isCheckedPayAll){
		  // 조회 수행 : 조회URL, 파라미터, 콜백함수
		  $scope._postJSONQuery.withOutPopUp("/sale/anals/store/payFg/list.sb", params, function(response) {
		    	
			  var length = response.data.data.list.length;
			  var grid = wijmo.Control.getControl("#storeRankGrid");
	
			  if(length != "" || length != null){
				  //생성된 그리드컬럼 삭제
					while(grid.columns.length > 10){
						grid.columns.removeAt(grid.columns.length-1);
					}
		   	 	
				    //첫째줄 헤더 생성
				   	for(var i=0; i<length; i++){
				   		grid.columns.push(new wijmo.grid.Column({header: messages["store.pay"+i], binding: 'pay'+i, align: "right" , isReadOnly: "true", aggregate: "Sum"}));
				   		grid.columnHeaders.setCellData(0, 10+i, messages["store.pay"]);
				   		grid.columnHeaders.setCellData(1, 10+i, response.data.data.list[i].payNm);
			    	    
				   	}
			   	
		   	 	}
			   $scope.flex.refresh();
		    });
	  }else{		  
		  	//첫째줄 헤더 생성
		  	var grid = wijmo.Control.getControl("#storeRankGrid");
		  	
		  	//생성된 그리드컬럼 삭제
			while(grid.columns.length > 10){
				grid.columns.removeAt(grid.columns.length-1);
			}
		  	
		   	for(var i=0; i<4; i++){
		   		grid.columns.push(new wijmo.grid.Column({header: messages["store.pay"+i], binding: 'pay'+i, align: "right" , isReadOnly: "true", aggregate: "Sum"}));
		   		grid.columnHeaders.setCellData(0, 10+i, messages["store.pay"]);
	    	    
		   	}
	  }
	  
  };
  
  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeRankSelectStoreShow = function () {
    $scope._broadcast('storeRankSelectStoreCtrl');
  };
  
  // 엑셀 다운로드
  $scope.excelDownloadStoreRank = function () {
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
      }, '매출분석_매장별매출분석_매장순위_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
  
}]);
