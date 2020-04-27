/**
 * get application
 */
var app = agrid.getApp();

/** 반품현황(조회조건) controller */
app.controller('rtnStatusDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnStatusDayCtrl', $scope, $http, $timeout, true));

  $scope.srchRtnStatusDayStartDate = wcombo.genDateVal("#srchRtnStatusDayStartDate", getToday());
  $scope.srchRtnStatusDayEndDate   = wcombo.genDateVal("#srchRtnStatusDayEndDate", getToday());
  
  $scope.isDaySearch = false;
  $scope.isDayDtlSearch = false;
  $scope.isPosDtlSearch = false;

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("rtnStatusDayListScaleBox", gvListScaleBoxData);
  $scope._setComboData("rtnStatusDayDtlListScaleBox", gvListScaleBoxData);
  $scope._setComboData("rtnStatusPosDtlListScaleBox", gvListScaleBoxData);

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.rtnStatusDaySelectStoreShow = function () {
    $scope._broadcast('rtnStatusDaySelectStoreCtrl');
  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchRtnStatusDayStartDate.isReadOnly = $scope.isChecked;
    $scope.srchRtnStatusDayEndDate.isReadOnly = $scope.isChecked;
  };

}]);

/** 반품현황(메인리스트) controller */
app.controller('rtnStatusDayMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnStatusDayMainCtrl', $scope, $http, $timeout, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// picker 사용시 호출 : 미사용시 호출안함
	s.refresh();

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("rtnStatusDayMainCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.binding === "storeNm") { // 매장명
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
      if(ht.cellType == 2 && ht.row < 1 && ht.col > 0) {
    	  s.allowSorting = false;
      } else {
    	  s.allowSorting = true;
	  }

      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
        	params.storeCd   = selectedRow.storeCd;
        	params.storeNm   = selectedRow.storeNm;
        	params.startDate = $scope.startDateForDt;
        	params.endDate   = $scope.endDateForDt;
        if (col.binding === "storeNm") { // 매장명
            $scope._broadcast('rtnStatusDayDtlCtrlSrch', params);
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
    dataItem.storeNm		= messages["rtnStatus.storeNm"];
    dataItem.cntY      		= messages["rtnStatus.saleAmt"];
    dataItem.realSaleAmtY  	= messages["rtnStatus.saleAmt"];
    dataItem.cntN      		= messages["rtnStatus.rtnAmt"];
    dataItem.realSaleAmtN  	= messages["rtnStatus.rtnAmt"];
    dataItem.cnt      		= messages["rtnStatus.realSaleAmt"];
    dataItem.realSaleAmt  	= messages["rtnStatus.realSaleAmt"];

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
  $scope.$on("rtnStatusDayMainCtrl", function (event, data) {
    $scope.searchRtnStatusDayList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기(페이징 초기화)
  $scope.$on("rtnStatusDayMainCtrlSrch", function (event, data) {
    $scope.searchRtnStatusDayList(false);
    // 기능수행 종료 : 반드시 추가
	event.preventDefault();
  });

  // 코너별매출일자별 리스트 조회
  $scope.searchRtnStatusDayList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.storeCd   = $("#rtnStatusDaySelectStoreCd").val();
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    
    $scope.excelDayStoreCd		= params.storeCd;
    $scope.excelDayListScale	= params.listScale;
    $scope.isDaySearch			= true;

	//등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
	  $scope.startDateForDt = wijmo.Globalize.format($scope.srchRtnStatusDayStartDate.value, 'yyyyMMdd');
      $scope.endDateForDt = wijmo.Globalize.format($scope.srchRtnStatusDayEndDate.value, 'yyyyMMdd');

	  params.startDate = wijmo.Globalize.format($scope.srchRtnStatusDayStartDate.value, 'yyyyMMdd');
	  params.endDate = wijmo.Globalize.format($scope.srchRtnStatusDayEndDate.value, 'yyyyMMdd');
	  
	  $scope.excelDayStartDate	= params.startDate;
	  $scope.excelDayEndDate	= params.endDate;
	  
	}else{
    	$scope.startDateForDt = "";
    	$scope.endDateForDt = "";
  	  
  	  	$scope.excelDayStartDate	= "";
  	  	$scope.excelDayEndDate		= "";
    }
	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}
	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/rtnStatus/day/list.sb", params);

	//메인그리드 조회후 상세그리드 조회.
	$scope.loadedRows = function(sender, args){
		var rows = sender.rows;

		var params		 = {};
		if(rows.length > 0){
		    params.storeCd   = rows[0].dataItem.storeCd;
		    params.storeNm   = rows[0].dataItem.storeNm;

		    // 등록일자 '전체기간' 선택에 따른 params
		    if(!$scope.isChecked){
		      params.startDate = wijmo.Globalize.format($scope.srchRtnStatusDayStartDate.value, 'yyyyMMdd');
		      params.endDate = wijmo.Globalize.format($scope.srchRtnStatusDayEndDate.value, 'yyyyMMdd');
		    }
		    
		    params.isDayDtlSearch	= true;
		    
		    $scope._broadcast("rtnStatusDayDtlCtrlSrch", params);
		}else{
			$scope.isDayDtlSearch	= false;
			//$scope._broadcast("rtnStatusDayDtlCtrl", params);
			// 바코드별 매출 그리드 조회 후 상세내역 그리드 초기화
			var orderStockInfoDtlScope = agrid.getScope('rtnStatusDayDtlCtrl');
		    orderStockInfoDtlScope.dtlGridDefault();
			
		}
		// 코너별 매출현황 상세조회.
	    
	}
  };

  //엑셀 다운로드1
  $scope.excelDownloadDay = function () {
	  // 파라미터
	  var params     = {};
	  $scope._broadcast('rtnStatusDayExcelCtrl',params);
  };
}]);

/** 반품현황 상세(일자별 상세) controller */
app.controller('rtnStatusDayDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	 // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('rtnStatusDayDtlCtrl', $scope, $http, $timeout, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("rtnStatusDayDtlCtrl");

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	    // 그리드 링크 효과
	    s.formatItem.addHandler(function (s, e) {
	      if (e.panel === s.cells) {
	        var col = s.columns[e.col];
	        if(col.binding === "saleDate" || col.binding === "cntY" || col.binding === "cntN") { // 일자, 수량
	        	var item = s.rows[e.row].dataItem;
	        	wijmo.addClass(e.cell, 'wijLink');
	        	wijmo.addClass(e.cell, 'wj-custom-readonly');
	        }
	      }
	    });

	    // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
	    s.addEventListener(s.hostElement, 'mousedown', function (e) {
	      var ht = s.hitTest(e);

	      if(ht.cellType == 2 && ht.row < 1 && ht.col > 0) {
	    	  s.allowSorting = false;
	      } else {
	    	  s.allowSorting = true;
		  }

	      if (ht.cellType === wijmo.grid.CellType.Cell) {
	        var col         = ht.panel.columns[ht.col];
	        var selectedRow = s.rows[ht.row].dataItem;
	        var params       = {};
	        	params.storeCd   = $scope.storeCd;
	        	params.saleDate  = selectedRow.saleDate;
	        	params.isPageChk = false;

	        if (col.binding === "saleDate") { // 일자
	            $scope._broadcast('rtnStatusPosDtlCtrlSrch', params);
	        }else if(col.binding === "cntY"){
	        	params.saleYn = "Y";
	        	$scope._broadcast('rtnStatusPosDtlCtrlSrch', params);
	        }else if(col.binding === "cntN"){
	        	params.saleYn = "N";
	        	$scope._broadcast('rtnStatusPosDtlCtrlSrch', params);
	        }
	      }
	    });



	    // <-- 그리드 헤더2줄 -->
	    // 헤더머지
	    s.allowMerging = 2;
	    s.columnHeaders.rows.push(new wijmo.grid.Row());

	    // 첫째줄 헤더 생성
	    var dataItem         = {};
	    dataItem.saleDate		= messages["rtnStatus.saleDate"];
	    dataItem.cntY      		= messages["rtnStatus.saleAmt"];
	    dataItem.realSaleAmtY  	= messages["rtnStatus.saleAmt"];
	    dataItem.cntN      		= messages["rtnStatus.rtnAmt"];
	    dataItem.realSaleAmtN  	= messages["rtnStatus.rtnAmt"];
	    dataItem.cnt      		= messages["rtnStatus.realSaleAmt"];
	    dataItem.realSaleAmt  	= messages["rtnStatus.realSaleAmt"];

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

	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("rtnStatusDayDtlCtrl", function (event, data) {
		  if(data != undefined){
			$scope.startDate = data.startDate;
			$scope.endDate   = data.endDate;
			$scope.storeCd   = data.storeCd;
			$scope.storeNm   = data.storeNm;
		  }
	    $scope.searchRtnStatusDayDtlList(true);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });

	// 다른 컨트롤러의 broadcast 받기
	  $scope.$on("rtnStatusDayDtlCtrlSrch", function (event, data) {
		  if(data != undefined){
			$scope.startDate = data.startDate;
			$scope.endDate   = data.endDate;
			$scope.storeCd   = data.storeCd;
			$scope.storeNm   = data.storeNm;
		  }
	    $scope.searchRtnStatusDayDtlList(false);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


	  // 코너별매출일자별 리스트 조회
	  $scope.searchRtnStatusDayDtlList = function (isPageChk) {
	    // 파라미터
	    var params       = {};
//	    params.listScale = $scope.cornerDayListScale; //-페이지 스케일 갯수
	    params.startDate = $scope.startDate;
	    params.endDate   = $scope.endDate;
	    params.storeCd   = $scope.storeCd;
	    params.storeNm   = $scope.storeNm;
	    params.isPageChk	= isPageChk;
	    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
	    $("#strNm").text($scope.storeNm);
	    
	    $scope.excelDayDtlStartDate	= params.startDate;
	    $scope.excelDayDtlEndDate	= params.endDate;
	    $scope.excelDayDtlStoreCd	= params.storeCd;
	    $scope.excelDayDtlStoreNm	= params.storeNm;
	    $scope.excelDayDtlListScale	= params.listScale; //-페이지 스케일 갯수
	    $scope.isDayDtlSearch		= true;

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/status/rtnStatus/dayDtl/list.sb", params);
	    $scope.flex.refresh();

	    //메인그리드 조회후 상세그리드 조회.
		$scope.loadedRows2 = function(sender, args){
			var rows = sender.rows;

			var params		 = {};
			if(rows.length > 0){
			    params.storeCd   = $scope.storeCd;
			    params.saleDate  = rows[0].dataItem.saleDate;
			    $scope._broadcast("rtnStatusPosDtlCtrlSrch", params);
			}else{
				//$scope._broadcast("rtnStatusDayDtlCtrl", params);
				var orderStockInfoDtlScope = agrid.getScope('rtnStatusPosDtlCtrl');
			    orderStockInfoDtlScope.dtlGridDefault();
				
			}
			// 반품현황 포스별 상세조회.
		    if(params.saleDate != "" || params.saleDate != null){
		    	
		    }
		}
	  };
	  
	  //엑셀 다운로드2
	  $scope.excelDownloadDayDtlCtrl = function () {
		  // 파라미터
		  var params     = {};
		  params.isDayDtlSearch	= $scope.isDayDtlSearch;
		  $scope._broadcast('rtnStatusDayDtlExcelCtrl',params);
	  };
	  
	// 상세 그리드 초기화
	  $scope.dtlGridDefault = function () {
	    $timeout(function () {
	      var cv          = new wijmo.collections.CollectionView([]);
	      cv.trackChanges = true;
	      $scope.data     = cv;
	      $scope.flex.refresh();
	      $scope.isDayDtlSearch	= false;
	    }, 10);
	  };
}]);


/** 반품현황 상세(포스별 상세) controller */
app.controller('rtnStatusPosDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	 // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('rtnStatusPosDtlCtrl', $scope, $http, $timeout, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("rtnStatusPosDtlCtrl");

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("rtnStatusPosDtlCtrl", function (event, data) {
		  if(data != undefined){
			$scope.saleDate   = data.saleDate;
			$scope.storeCd    = data.storeCd;
			$scope.saleYn	  = data.saleYn;
		  }

	    $scope.searchRtnStatusPosDtlList(true);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("rtnStatusPosDtlCtrlSrch", function (event, data) {
		  if(data != undefined){
			$scope.saleDate   = data.saleDate;
			$scope.storeCd    = data.storeCd;
			$scope.saleYn	  = data.saleYn;
		  }

	    $scope.searchRtnStatusPosDtlList(false);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


	  // 코너별매출일자별 리스트 조회
	  $scope.searchRtnStatusPosDtlList = function (isPageChk) {
	    // 파라미터
	    var params          = {};
//	    params.listScale = $scope.cornerDayListScale; //-페이지 스케일 갯수
	    params.saleDate     = $scope.saleDate;
	    params.storeCd      = $scope.storeCd;
	    params.saleYn		= $scope.saleYn;
	    params.isPageChk	= isPageChk;
	    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수

	    
	    
	    
	    $scope.excelDayDtlSaleDate	= params.saleDate;
	    $scope.excelDayDtlStoreCd	= params.storeCd;
	    $scope.excelDayDtlSaleYn	= params.saleYn;
	    $scope.excelDayDtlListScale	= params.listScale;
	    $scope.isPosDtlSearch 		= true;

	    if(params.saleDate != null){
	    	var saleDate	=$scope.saleDate;
//		    var saleDate	=(""+$scope.saleDate).substring(0,4);
//		    saleDate		= saleDate+"."+(""+$scope.saleDate).substring(4,6);
//		    saleDate		= saleDate+"."+(""+$scope.saleDate).substring(6,8);
		    $("#dateYMD").text(saleDate);
	    }

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/status/rtnStatus/posDtl/list.sb", params);
	    $scope.flex.refresh();
	  };

	//엑셀 다운로드3
	  $scope.excelDownloadPosDtlCtrl = function () {
		  var params     = {};
		  $scope._broadcast('rtnStatusPosDtlExcelCtrl',params);
	  };
	  
	// 상세 그리드 초기화
	  $scope.dtlGridDefault = function () {
	    $timeout(function () {
	      var cv          = new wijmo.collections.CollectionView([]);
	      cv.trackChanges = true;
	      $scope.data     = cv;
	      $scope.flex.refresh();
	      $scope.isPosDtlSearch	= false;
	    }, 10);
	  };
}]);

//엑셀 전체 다운 컨트롤러1
app.controller('rtnStatusDayExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('rtnStatusDayExcelCtrl', $scope, $http, $timeout, true));

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		// picker 사용시 호출 : 미사용시 호출안함
		s.refresh();

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
	    dataItem.storeNm		= messages["rtnStatus.storeNm"];
	    dataItem.cntY      		= messages["rtnStatus.saleAmt"];
	    dataItem.realSaleAmtY  	= messages["rtnStatus.saleAmt"];
	    dataItem.cntN      		= messages["rtnStatus.rtnAmt"];
	    dataItem.realSaleAmtN  	= messages["rtnStatus.rtnAmt"];
	    dataItem.cnt      		= messages["rtnStatus.realSaleAmt"];
	    dataItem.realSaleAmt  	= messages["rtnStatus.realSaleAmt"];

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
	$scope.$on("rtnStatusDayExcelCtrl", function (event, data) {
		if(data != undefined && $scope.isDaySearch) {			
			$scope.searchRtnStatusDayExcelList(true);
			// 기능수행 종료 : 반드시 추가
			event.preventDefault();
		} else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

	});
	
	// 코너별매출일자별 리스트 조회
	$scope.searchRtnStatusDayExcelList = function (isPageChk) {
		// 파라미터
	    var params       = {};
	    params.storeCd = $scope.excelDayStoreCd;
	    params.listScale = $scope.excelDayListScale;
	    params.startDate = $scope.excelDayStartDate;
		params.endDate = $scope.excelDayEndDate;

		// 전체 엑셀 조회
		$scope._inquirySub("/sale/status/rtnStatus/day/excelList.sb", params, function(){
			if ($scope.dayExcelflex.rows.length <= 0 || !$scope.isDaySearch) {
			      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			      return false;
			    }

			    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
			    $timeout(function () {
			      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.dayExcelflex, {
			        includeColumnHeaders: true,
			        includeCellStyles   : true,
			        includeColumns      : function (column) {
			          return column.visible;
			        }
			      }, '매출현황_반품현황_'+getToday()+'.xlsx', function () {
			        $timeout(function () {
			          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
			        }, 10);
			      });
			}, 10);
		});
	  };
}]);

//엑셀 전체 다운 컨트롤러2
app.controller('rtnStatusDayDtlExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('rtnStatusDayDtlExcelCtrl', $scope, $http, $timeout, true));

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
	    dataItem.saleDate		= messages["rtnStatus.saleDate"];
	    dataItem.cntY      		= messages["rtnStatus.saleAmt"];
	    dataItem.realSaleAmtY  	= messages["rtnStatus.saleAmt"];
	    dataItem.cntN      		= messages["rtnStatus.rtnAmt"];
	    dataItem.realSaleAmtN  	= messages["rtnStatus.rtnAmt"];
	    dataItem.cnt      		= messages["rtnStatus.realSaleAmt"];
	    dataItem.realSaleAmt  	= messages["rtnStatus.realSaleAmt"];

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
	$scope.$on("rtnStatusDayDtlExcelCtrl", function (event, data) {
		if(data != undefined && $scope.isDayDtlSearch) {			
			$scope.searchRtnStatusDayDtlExcelList(true);
			// 기능수행 종료 : 반드시 추가
			event.preventDefault();
		} else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}
	});
	
	// 코너별매출일자별 리스트 조회
	$scope.searchRtnStatusDayDtlExcelList = function (isPageChk) {
		// 파라미터
		var params       = {};
		
		params.startDate	= $scope.excelDayDtlStartDate;
	    params.endDate		= $scope.excelDayDtlEndDate;
	    params.storeCd		= $scope.excelDayDtlStoreCd;
	    params.storeNm		= $scope.excelDayDtlStoreNm;
	    params.listScale	= $scope.excelDayDtlListScale;

		// 전체 엑셀 조회
		$scope._inquiryMain("/sale/status/rtnStatus/dayDtl/excelList.sb", params, function(){
			if ($scope.dayDtlExcelflex.rows.length <= 0 || !$scope.isDayDtlSearch) {
			      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			      return false;
			    }

			    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
			    $timeout(function () {
			      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.dayDtlExcelflex, {
			        includeColumnHeaders: true,
			        includeCellStyles   : true,
			        includeColumns      : function (column) {
			          return column.visible;
			        }
			      }, '매출현황_반품현황_일자별상세_'+getToday()+'.xlsx', function () {
			        $timeout(function () {
			          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
			        }, 10);
			      });
			}, 10);
		});
	  };
}]);

//엑셀 전체 다운 컨트롤러3
app.controller('rtnStatusPosDtlExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('rtnStatusPosDtlExcelCtrl', $scope, $http, $timeout, true));

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("rtnStatusPosDtlExcelCtrl", function (event, data) {
		if(data != undefined && $scope.isPosDtlSearch) {			
			$scope.searchRtnStatusPosDtlExcelList(true);
			// 기능수행 종료 : 반드시 추가
			event.preventDefault();
		} else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}
	});
	
	// 코너별매출일자별 리스트 조회
	$scope.searchRtnStatusPosDtlExcelList = function (isPageChk) {
		// 파라미터
		var params       = {};
	    params.saleDate		= $scope.excelDayDtlSaleDate;
	    params.storeCd		= $scope.excelDayDtlStoreCd;
	    params.saleYn		= $scope.excelDayDtlSaleYn;
	    params.listScale	= $scope.excelDayDtlListScale;

		// 전체 엑셀 조회
		$scope._inquirySub("/sale/status/rtnStatus/posDtl/excelList.sb", params, function(){
			if ($scope.posDtlExcelflex.rows.length <= 0 || !$scope.isPosDtlSearch) {
			      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			      return false;
			    }

			$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
		    $timeout(function () {
		      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.posDtlExcelflex, {
		        includeColumnHeaders: true,
		        includeCellStyles   : true,
		        includeColumns      : function (column) {
		          return column.visible;
		        }
		      }, '매출현황_반품현황_포스별상세_'+getToday()+'.xlsx', function () {
		        $timeout(function () {
		          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
		        }, 10);
		      });
		    }, 10);
		});
	  };
}]);