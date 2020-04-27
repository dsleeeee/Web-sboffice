/**
 * get application
 */
var app = agrid.getApp();

/** 바코드별(매출리스트) controller */
app.controller('barcdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('barcdCtrl', $scope, $http, $timeout, true));

  $scope.srchBarcdStartDate = wcombo.genDateVal("#srchBarcdStartDate", getToday());
  $scope.srchBarcdEndDate   = wcombo.genDateVal("#srchBarcdEndDate", getToday());
  
  $scope.isSearch		= false;
  $scope.isDtlSearch	= false;

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("barcdListScaleBox", gvListScaleBoxData);
  $scope._setComboData("barcdDtlListScaleBox", gvListScaleBoxData);
  $scope.orgnFg = gvOrgnFg;

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchBarcdStartDate.isReadOnly = $scope.isChecked;
    $scope.srchBarcdEndDate.isReadOnly = $scope.isChecked;
  };

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.barcdSelectStoreShow = function () {
    $scope._broadcast('barcdSelectStoreCtrl');
  };

  // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
	  $scope._broadcast("chkProdClassDisplay");
  }

  // 조회조건 바코드코드 엔터 이벤트
  $scope.searchBarCdKeyEvt = function (event) {
	  if (event.keyCode === 13) { // 이벤트가 enter 이면
		  $scope._broadcast('barcdMainCtrlSrch');
	  }
  }

}]);


/** 일자별(코너별 매출) controller */
app.controller('barcdMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('barcdMainCtrl', $scope, $http, $timeout, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("barcdMainCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.binding === "barcdCd") { // 매장명
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
        	params.prodCd	 = selectedRow.prodCd;
        	params.storeCd	 = $("#barcdSelectStoreCd").val();
        if (col.binding === "barcdCd") { // 바코드
            $scope._broadcast('barcdDtlCtrlSrch', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  $scope.$on("chkProdClassDisplay", function (event) {
	  var columns = $scope.flex.columns;

	  for(var i=0; i<columns.length; i++){
		  if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
			  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
		  }
	  }
  });

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("barcdMainCtrl", function (event, data) {
    $scope.searchBarcdList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("barcdMainCtrlSrch", function (event, data) {
    $scope.searchBarcdList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 바코드별매출 리스트 조회
  $scope.searchBarcdList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    params.storeCd   = $("#barcdSelectStoreCd").val();
    $scope.storeCd   = $("#barcdSelectStoreCd").val();
    params.barcdCd	 = $scope.searchBarCd;
    params.prodNm    = $scope.searchProdNm;
    params.orgnFg	 = $scope.orgnFg;
    
    $scope.excelListScale	= params.listScale;
    $scope.excelStoreCd		= params.storeCd;
    $scope.excelBarcdCd		= params.barcdCd;
    $scope.excelProdNm		= params.prodNm;
    $scope.excelOrgnFg		= params.orgnFg;
    $scope.excelStartDate	= "";
    $scope.excelEndDate		= "";
    $scope.isSearch	= true;

	//등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
	  $scope.startDateForDt = wijmo.Globalize.format($scope.srchBarcdStartDate.value, 'yyyyMMdd');
      $scope.endDateForDt = wijmo.Globalize.format($scope.srchBarcdEndDate.value, 'yyyyMMdd');

	  params.startDate = wijmo.Globalize.format($scope.srchBarcdStartDate.value, 'yyyyMMdd');
	  params.endDate = wijmo.Globalize.format($scope.srchBarcdEndDate.value, 'yyyyMMdd');
	  $scope.excelStartDate	= params.startDate;
	  $scope.excelEndDate	= params.endDate;
	}else{
    	$scope.startDateForDt = "";
    	$scope.endDateForDt = "";
    }
	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}
	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/barcd/barcd/list.sb", params);

	//메인그리드 조회후 상세그리드 조회.
	$scope.loadedRows = function(sender, args){
		var rows = sender.rows;
        var params       = {};
        if(rows.length > 0){
        	params.prodCd    = rows[0].dataItem.prodCd;
    	    params.storeCd	 = $scope.storeCd;

    	    // 코너별 매출현황 상세조회.
    	    $scope._broadcast("barcdDtlCtrlSrch", params);
        }else{
        	// 엑셀 상세 그리드 초기화
//        	$scope.isDtlSearch = false;
//        	$scope._broadcast("barcdDtlCtrl", params);
        	
        	var orderStockInfoDtlScope = agrid.getScope('barcdDtlCtrl');
		    orderStockInfoDtlScope.dtlGridDefault();
        	
        	// 바코드별 매출 그리드 조회 후 상세내역 그리드 초기화
//        	var cv          = new wijmo.collections.CollectionView([]);
//  	      	cv.trackChanges = true;
//  	      	
//  	      	params.cv = cv;
//  	      	$scope._broadcast("barcdDtlCtrl", params);
        }
	}
  };


  //엑셀 다운로드
  $scope.excelDownloadBarcd = function () {
	  // 파라미터
	  var params     = {};
	  $scope._broadcast('barcdExcelCtrl',params);
  };
}]);

/** 반품현황 상세(포스별 상세) controller */
app.controller('barcdDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	 // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('barcdDtlCtrl', $scope, $http, $timeout, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("barcdDtlCtrl");

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("barcdDtlCtrl", function (event, data) {
		  if(data != undefined){
			$scope.saleDate   = data.saleDate;
			$scope.barcdCd	  = data.barcdCd;
			$scope.storeCd	  = data.storeCd;
		  }

	    $scope.searchBarcdDtlList(true);
	    
	    if(data.cv != null && data.cv != ''){
	    	$scope.data     = data.cv;
		    $scope.flex.refresh();
	    }
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("barcdDtlCtrlSrch", function (event, data) {
		  if(data != undefined){
			$scope.saleDate   = data.saleDate;
			$scope.prodCd	  = data.prodCd;
			$scope.storeCd	  = data.storeCd;
		  }

	    $scope.searchBarcdDtlList(false);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


	  // 코너별매출일자별 리스트 조회
	  $scope.searchBarcdDtlList = function (isPageChk) {
	    // 파라미터
	    var params          = {};
	    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
	    params.isPageChk	= isPageChk;

	    // 등록일자 '전체기간' 선택에 따른 params
	    if(!$scope.isChecked){
	      params.startDate = wijmo.Globalize.format($scope.srchBarcdStartDate.value, 'yyyyMMdd');
	      params.endDate = wijmo.Globalize.format($scope.srchBarcdEndDate.value, 'yyyyMMdd');
	    }
	    params.prodCd		= $scope.prodCd;
	    params.storeCd		= $scope.storeCd;
	    $scope.excelDtlProdCd	= params.prodCd;
	    $scope.excelDtlStoreCd	= params.storeCd;
	    $scope.excelDtlStartDate	= params.startDate;
	    $scope.excelDtlEndDate		= params.endDate;
	    $scope.isDtlSearch		= true;
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/status/barcd/barcdDtl/list.sb", params);
	  };

	//엑셀 다운로드2
	  $scope.excelDownloadBarcdDtl = function () {
		  // 파라미터
		  var params     = {};
		  $scope._broadcast('barcdDtlExcelCtrl',params);
	  };

	  // 상세 그리드 초기화
	  $scope.dtlGridDefault = function () {
	    $timeout(function () {
	      var cv          = new wijmo.collections.CollectionView([]);
	      cv.trackChanges = true;
	      $scope.data     = cv;
	      $scope.flex.refresh();
	      $scope.isDtlSearch = false;
	    }, 10);
	  };
}]);

app.controller('barcdExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('barcdExcelCtrl', $scope, $http, $timeout, true));

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("barcdExcelCtrl", function (event, data) {
		if(data != undefined && $scope.isSearch) {
			$scope.searchBarcdExcelList(true);
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
	$scope.searchBarcdExcelList = function (isPageChk) {
		// 파라미터
		var params     = {};
		params.listScale	= $scope.excelListScale;
	    params.storeCd		= $scope.excelStoreCd;
	    params.barcdCd		= $scope.excelBarcdCd;
	    params.prodNm		= $scope.excelProdNm;
	    params.orgnFg		= $scope.excelOrgnFg;
	    params.startDate	= $scope.excelStartDate;
	    params.endDate		= $scope.excelEndDate;

		$scope.isChkProdClassDisplay();

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/barcd/barcd/excelList.sb", params, function(){			    
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
				}, $(menuNm).selector + '_'+messages["barcd.barcd"]+'_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);
		});
	};
}]);

// 엑셀 컨트롤러2
app.controller('barcdDtlExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('barcdDtlExcelCtrl', $scope, $http, $timeout, true));
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("barcdDtlExcelCtrl", function (event, data) {
		if(data != undefined && $scope.isDtlSearch) {
			$scope.searchBarcdDtlExcelList(true);
			// 기능수행 종료 : 반드시 추가
			event.preventDefault();
		} else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

	});

	// 전체 엑셀 리스트 조회
	$scope.searchBarcdDtlExcelList = function (isPageChk) {// 파라미터
		
		// 파라미터
		var params     = {};
		params.startDate	= $scope.excelDtlStartDate;
		params.endDate		= $scope.excelDtlEndDate;
	    params.prodCd		= $scope.excelDtlProdCd;
	    params.storeCd		= $scope.excelDtlStoreCd;

		if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
		}

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/barcd/barcdDtl/excelList.sb", params, function(){
			if ($scope.excelDtlFlex.rows.length <= 0) {
				$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
				return false;
			}

			$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
			$timeout(function () {
				wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelDtlFlex, {
					includeColumnHeaders: true,
					includeCellStyles   : true,
					includeColumns      : function (column) {
						return column.visible;
					}
				}, '매출현황_바코드별(상세)_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);
		});
	};

}]);