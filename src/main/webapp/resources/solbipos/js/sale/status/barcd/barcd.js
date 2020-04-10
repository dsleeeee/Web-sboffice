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
    params.listScale = $scope.barcdListScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    params.storeCd   = $("#barcdSelectStoreCd").val();
    $scope.storeCd   = $("#barcdSelectStoreCd").val();
    params.barcdCd	 = $scope.searchBarCd;
    params.prodNm    = $scope.searchProdNm;
    params.orgnFg	 = $scope.orgnFg;
    
	//등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
	  $scope.startDateForDt = wijmo.Globalize.format($scope.srchBarcdStartDate.value, 'yyyyMMdd');
      $scope.endDateForDt = wijmo.Globalize.format($scope.srchBarcdEndDate.value, 'yyyyMMdd');
      
	  params.startDate = wijmo.Globalize.format($scope.srchBarcdStartDate.value, 'yyyyMMdd');
	  params.endDate = wijmo.Globalize.format($scope.srchBarcdEndDate.value, 'yyyyMMdd');
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
		
		var params		 = {};
	    params.prodCd    = rows[0].dataItem.prodCd;
	    params.storeCd	 = $("#barcdSelectStoreCd").val();
	   
	    // 코너별 매출현황 상세조회.
	    $scope._broadcast("barcdDtlCtrlSrch", params);
	}
  };


//엑셀 다운로드
  $scope.excelDownloadBarcd = function () {
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
      }, '매출현황_바코드별_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
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
	    params.listScale    = $scope.barcdDtlListScale; //-페이지 스케일 갯수
	    params.isPageChk	= isPageChk;
	    // 등록일자 '전체기간' 선택에 따른 params
	    if(!$scope.isChecked){
	      params.startDate = wijmo.Globalize.format($scope.srchBarcdStartDate.value, 'yyyyMMdd');
	      params.endDate = wijmo.Globalize.format($scope.srchBarcdEndDate.value, 'yyyyMMdd');
	    }
	    params.prodCd		= $scope.prodCd;
	    params.storeCd		= $scope.storeCd;   
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/status/barcd/barcdDtl/list.sb", params);
	  };
	  
	//엑셀 다운로드
	  $scope.excelDownloadBarcdDtl = function () {
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
	      }, '매출현황_바코드별(상세)_'+getToday()+'.xlsx', function () {
	        $timeout(function () {
	          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
	        }, 10);
	      });
	    }, 10);
	  };
}]);