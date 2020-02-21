/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('apprMpayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('apprMpayCtrl', $scope, $http, $timeout, true));

  $scope.srchApprMpayStartDate = wcombo.genDateVal("#srchApprMpayStartDate", getToday());
  $scope.srchApprMpayEndDate   = wcombo.genDateVal("#srchApprMpayEndDate", getToday());

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("apprMpayListScaleBox", gvListScaleBoxData);
  
  //조회조건 승인구분 데이터 Set
  $scope._setComboData("srchMpaySaleFgDisplay", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["appr.approve"], "value": "1"},
    {"name": messages["cmm.cancel"], "value": "-1"}
  ]);
  
  //조회조건 승인처리 데이터 Set
  $scope._setComboData("srchMpayApprProcFgDisplay", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["card.apprProcFg1"], "value": "1"},
    {"name": messages["card.apprProcFg2"], "value": "2"},
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
	$scope.getCornerNmList();

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("apprMpayCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.binding === "storeNm") { // 수량합계
        	var item = s.rows[e.row].dataItem;
          	wijmo.addClass(e.cell, 'wijLink');
          	wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });
    
    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
	    	params.storeCd   = selectedRow.storeCd;
	    	if(!$scope.isChecked){
	    		  params.startDate = wijmo.Globalize.format($scope.srchApprMpayStartDate.value, 'yyyyMMdd');
	    		  params.endDate = wijmo.Globalize.format($scope.srchApprMpayEndDate.value, 'yyyyMMdd');
	    	}
	    	params.chkPop    = "mpayApprPop";
	    if (col.binding === "storeNm") { // 매장명
	        $scope._broadcast('saleApprMpayCtrl', params);
	    }
      }
    });

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
    dataItem.storeCd			   	= messages["rtnStatus.storeCd"];
    dataItem.storeNm			   	= messages["rtnStatus.storeNm"];
    
    dataItem.cnt        			= messages["cmm.all"];
    dataItem.apprAmt         		= messages["cmm.all"];
    
    dataItem.cntA       			= messages["appr.approve"];
    dataItem.apprAmtA        		= messages["appr.approve"];

    dataItem.cntB        			= messages["cmm.cancel"];
    dataItem.apprAmtB         		= messages["cmm.cancel"];


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
  $scope.$on("apprMpayCtrl", function (event, data) {
    $scope.searchApprMpayList(true);
    
    
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
  //다른 컨트롤러의 broadcast 받기
  $scope.$on("apprMpayCtrlSrch", function (event, data) {
    $scope.searchApprMpayList(false);
    
    
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 신용카드 승인현황 리스트 조회
  $scope.searchApprMpayList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.storeCd   = $("#apprMpaySelectStoreCd").val();
    params.posNo  	 = $("#apprMpaySelectPosCd").val();
    params.cornrCd   = $("#apprMpaySelectCornerCd").val();
    params.saleFg	 = $scope.saleFg;
    params.apprProcFg = $scope.apprProcFg;
    params.listScale = $scope.apprMpayListScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    params.arrCornrCol  = [];

	//등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
	  params.startDate = wijmo.Globalize.format($scope.srchApprMpayStartDate.value, 'yyyyMMdd');
	  params.endDate = wijmo.Globalize.format($scope.srchApprMpayEndDate.value, 'yyyyMMdd');
	}
	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}
		
	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/appr/mpay/list.sb", params);
	
	$scope.editDataGrid();
  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchApprMpayStartDate.isReadOnly = $scope.isChecked;
    $scope.srchApprMpayEndDate.isReadOnly = $scope.isChecked;
  };

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.apprMpaySelectStoreShow = function () {
    $scope._broadcast('apprMpaySelectStoreCtrl');
  };

  	//포스선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.apprMpaySelectPosShow = function () {
		$scope._broadcast('apprMpaySelectPosCtrl');
	};

	//포스선택 모듈 팝업 사용시 정의
	//함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	//_broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.apprMpaySelectCornerShow = function () {
		$scope._broadcast('apprMpaySelectCornerCtrl');
	};
	
//엑셀 다운로드
  $scope.excelDownloadMpay = function () {
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
      }, '승인현황_승인현황_Mpay_'+getToday()+'.xlsx', function () {
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

		comboParams.storeCd = $("#posMpaySelectStoreCd").val();
	};


 //매장의 코너(corner) 리스트 조회
	$scope.getCornerNmList = function () {
		var url             = '/sale/status/corner/corner/cornerNmList.sb';
		var comboParams     = {};
		comboParams.storeCd = $("#apprMpaySelectStoreCd").val();
	};


	// 선택한 승인구분에 따른 리스트 항목 visible
	$scope.editDataGrid = function () {
        var grid = wijmo.Control.getControl("#apprMpayGrid");
        var columns = grid.columns;
        if($scope.saleFg == '1'){
        	columns[4].visible = true;
        	columns[5].visible = true;
        	columns[6].visible = false;
        	columns[7].visible = false;
        }else if($scope.saleFg == '-1'){
        	columns[4].visible = false;
        	columns[5].visible = false;
        	columns[6].visible = true;
        	columns[7].visible = true;
        }else{
        	columns[4].visible = true;
        	columns[5].visible = true;
        	columns[6].visible = true;
        	columns[7].visible = true;
        }
	}
	
}]);