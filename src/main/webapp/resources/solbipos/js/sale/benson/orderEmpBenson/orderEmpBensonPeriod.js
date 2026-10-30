/****************************************************************
 *
 * 파일명 : orderEmpBensonPeriod.js
 * 설  명 : 벤슨 > 매출현황2 > 주문자현황(기간별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.10     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 주문자별 - 기간별 controller */
app.controller('orderEmpBensonPeriodCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('orderEmpBensonPeriodCtrl', $scope, $http, $timeout, true));

  $scope.excelMainFg = false;
  $scope.srchOrderEmpBensonPeriodStartDate = wcombo.genDateVal("#srchOrderEmpBensonPeriodStartDate", getToday());
  $scope.srchOrderEmpBensonPeriodEndDate   = wcombo.genDateVal("#srchOrderEmpBensonPeriodEndDate", getToday());

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchOrderEmpBensonPeriodStartDate.isReadOnly = $scope.isChecked;
    $scope.srchOrderEmpBensonPeriodEndDate.isReadOnly = $scope.isChecked;
  };
}]);

app.controller('orderEmpBensonPeriodMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('orderEmpBensonPeriodMainCtrl', $scope, $http, $timeout, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("orderEmpBensonPeriodMainCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "realSaleAmt") { // 실매출
          	wijmo.addClass(e.cell, 'wijLink');
          	wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });

    // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
        	params.storeCd   = selectedRow.storeCd;
        	params.startDate = $scope.startDateForDt;
        	params.endDate   = $scope.endDateForDt;
        	params.empNo     = selectedRow.orderEmpNo;
        if (col.binding === "realSaleAmt") { // 영수증번호
            $scope._broadcast('orderEmpBensonPeriodDtlCtrlSrch', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  }

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("orderEmpBensonPeriodMainCtrl", function (event, data) {

	$scope.searchOrderEmpPeriodList(true);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기(페이징 초기화)
  $scope.$on("orderEmpBensonPeriodMainCtrlSrch", function (event, data) {

	$scope.searchOrderEmpPeriodList(false);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 판매자별 설정기간 리스트 조회
  $scope.searchOrderEmpPeriodList = function (isPageChk) {

	  var startDt = new Date(wijmo.Globalize.format($scope.srchOrderEmpBensonPeriodStartDate.value, 'yyyy-MM-dd'));
	  var endDt = new Date(wijmo.Globalize.format($scope.srchOrderEmpBensonPeriodEndDate.value, 'yyyy-MM-dd'));
	  var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

	  // 시작일자가 종료일자보다 빠른지 확인
	  if(startDt.getTime() > endDt.getTime()){
		  $scope._popMsg(messages['cmm.dateChk.error']);
		  return false;
	  }

	  // 조회일자 최대 1년(365일) 제한
	  if (diffDay > 365) {
		  $scope._popMsg(messages['cmm.dateOver.1year.error']);
		  return false;
	  }

    // 파라미터
    var params       = {};
    params.storeCd   = $("#orderEmpBensonPeriodSelectStoreCd").val();

    $scope.excelMainStoreCd   = params.storeCd;
    $scope.excelMainIsChecked = $scope.isChecked;

    params.isPageChk = isPageChk;
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      $scope.startDateForDt = wijmo.Globalize.format($scope.srchOrderEmpBensonPeriodStartDate.value, 'yyyyMMdd');
	  $scope.endDateForDt = wijmo.Globalize.format($scope.srchOrderEmpBensonPeriodEndDate.value, 'yyyyMMdd');

      params.startDate = wijmo.Globalize.format($scope.srchOrderEmpBensonPeriodStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchOrderEmpBensonPeriodEndDate.value, 'yyyyMMdd');

      $scope.excelMainStartDate = params.startDate;
      $scope.excelMainEndDate   = params.endDate;
    }else{
    	$scope.startDateForDt = "";
    	$scope.endDateForDt = "";

        $scope.excelMainStartDate = "";
        $scope.excelMainEndDate   = "";
    }
    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }
    if($scope.isCheckedEmpAll){
    	params.empChk = "Y";

    	$scope.excelMainEmpChk = params.empChk;
    }else{
    	params.empChk = "N";

    	$scope.excelMainEmpChk = params.empChk;
    }
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/benson/orderEmpBenson/orderEmpBenson/getOrderEmpPeriodList.sb", params);

    $scope.excelMainFg = true;

    //메인그리드 조회후 상세그리드 조회.
	$scope.loadedRows = function(sender, args){
		var rows = sender.rows;

		var params		 = {};

		if(rows.length != 0) {

			params.startDate = $scope.startDateForDt;
        	params.endDate   = $scope.endDateForDt;
			params.storeCd   = rows[0].dataItem.storeCd;
		    params.empNo     = rows[0].dataItem.orderEmpNo;

		    $scope._broadcast("orderEmpBensonPeriodDtlCtrlSrch", params);
	    }
		else {
		    params.storeCd   = $("#orderEmpBensonPeriodSelectStoreCd").val();

            var orderEmpBensonPeriodDtlScope = agrid.getScope('orderEmpBensonPeriodDtlCtrl');
			orderEmpBensonPeriodDtlScope.dtlGridDefault();
	    }
	}
  };

  //엑셀 다운로드
  $scope.excelDownloadDayPeriod = function () {

	  var startDt = new Date(wijmo.Globalize.format($scope.srchOrderEmpBensonPeriodStartDate.value, 'yyyy-MM-dd'));
	  var endDt = new Date(wijmo.Globalize.format($scope.srchOrderEmpBensonPeriodEndDate.value, 'yyyy-MM-dd'));
	  var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

	  // 시작일자가 종료일자보다 빠른지 확인
	  if(startDt.getTime() > endDt.getTime()){
		  $scope._popMsg(messages['cmm.dateChk.error']);
		  return false;
	  }

	  // 조회일자 최대 1년(365일) 제한
	  if (diffDay > 365) {
		  $scope._popMsg(messages['cmm.dateOver.1year.error']);
		  return false;
	  }
		// 파라미터
		var params     = {};
		$scope._broadcast('orderEmpBensonPeriodExcelCtrl',params);
  };

}]);

/** 기간별(매출리스트) 엑셀 controller */
app.controller('orderEmpBensonPeriodExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	  // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('orderEmpBensonPeriodExcelCtrl', $scope, $http, $timeout, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("orderEmpBensonPeriodExcelCtrl", function (event, data) {
		  if(data != undefined && $scope.excelMainFg) {
				if($scope.excelMainStartDate > $scope.excelMainEndDate){
					$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
					return false;
				}
				 $scope.searchOrderEmpPeriodExcelList();

			}else{
				$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
				return false;
			}

	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


	  // 판매자별 설정기간 리스트 조회
	  $scope.searchOrderEmpPeriodExcelList = function (isPageChk) {

	    // 파라미터
	    var params       = {};
	    params.storeCd   = $scope.excelMainStoreCd;

	    // 등록일자 '전체기간' 선택에 따른 params
	    if(!$scope.excelMainIsChecked){
	      $scope.startDateForDt = $scope.excelMainStartDate;
		  $scope.endDateForDt = $scope.excelMainEndDate;

	      params.startDate = $scope.excelMainStartDate;
	      params.endDate = $scope.excelMainEndDate;
	    }else{
	    	$scope.startDateForDt = "";
	    	$scope.endDateForDt = "";
	    }
	    if(params.startDate > params.endDate){
	   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
	   	 	return false;
	    }
	    params.empChk = $scope.excelMainEmpChk;

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquiryMain("/sale/benson/orderEmpBenson/orderEmpBenson/getOrderEmpPeriodExcelList.sb", params, function(){
			var flex = $scope.excelFlex;
			//row수가 0이면
			if (flex.rows.length <= 0) {
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
				}, messages["orderEmpBenson.orderEmp"]+'_'+messages["orderEmpBenson.orderEmpPeriod"]+'_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);
	    });

	  };

}]);


/** 설정기간별매출(매출상세) controller */
app.controller('orderEmpBensonPeriodDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('orderEmpBensonPeriodDtlCtrl', $scope, $http, $timeout, true));

	  $scope.excelFg = false;

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("orderEmpBensonPeriodDtlCtrl");

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	    // 그리드 링크 효과
	    s.formatItem.addHandler(function (s, e) {
	      if (e.panel === s.cells) {
	        var col = s.columns[e.col];
	        if(col.binding === "billNo") { // 영수증번호
	        	wijmo.addClass(e.cell, 'wijLink');
	        	wijmo.addClass(e.cell, 'wj-custom-readonly');
	        }
	      }
	    });

	    // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
	    s.addEventListener(s.hostElement, 'mousedown', function (e) {
	      var ht = s.hitTest(e);
	      if (ht.cellType === wijmo.grid.CellType.Cell) {
	        var col         = ht.panel.columns[ht.col];
	        var selectedRow = s.rows[ht.row].dataItem;
	        var params       = {};
	        	params.chkPop   = "empPop";
	        	params.posNo	 = selectedRow.posNo;
	        	params.billNo    = selectedRow.billNo;
	        	params.storeCd   = selectedRow.storeCd;
	        	params.startDate = selectedRow.saleDate;
	        	params.endDate   = selectedRow.saleDate;
	        if (col.binding === "billNo") { // 영수증번호
	            $scope._broadcast('saleComProdCtrl', params);
	        }
	      }
	    });

	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("orderEmpBensonPeriodDtlCtrl", function (event, data) {

		  if(data != undefined){
			$scope.startDate = data.startDate;
			$scope.endDate   = data.endDate;
			$scope.storeCd   = data.storeCd;
			$scope.empNo     = data.empNo;
		  }

	    $scope.searchOrderEmpPeriodDtlList(true);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });

	  // 다른 컨트롤러의 broadcast 받기(페이징 초기화)
	  $scope.$on("orderEmpBensonPeriodDtlCtrlSrch", function (event, data) {
		  if(data != undefined){
			$scope.startDate = data.startDate;
			$scope.endDate   = data.endDate;
			$scope.storeCd   = data.storeCd;
			$scope.empNo     = data.empNo;
		  }

	    $scope.searchOrderEmpPeriodDtlList(false);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


	  // 코너별매출일자별 리스트 조회
	  $scope.searchOrderEmpPeriodDtlList = function (isPageChk) {

	    // 파라미터
	    var params       = {};
	    params.startDate = $scope.startDate;
	    params.endDate   = $scope.endDate;
	    params.storeCd   = $scope.storeCd;
	    params.empNo     = $scope.empNo;
	    params.isPageChk = isPageChk;

	    $scope.excelStartDate = params.startDate;
	    $scope.excelEndDate   = params.endDate;
	    $scope.excelStoreCd   = params.storeCd;
	    $scope.excelEmpNo     = params.empNo;

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/benson/orderEmpBenson/orderEmpBenson/getOrderEmpPeriodDtlList.sb", params);

	    $scope.excelFg = true;
	  };

	  //엑셀 다운로드
	  $scope.excelDownloadDayPeriodDtl = function () {
		// 파라미터
		var params     = {};
		$scope._broadcast('orderEmpBensonPeriodDtlExcelCtrl',params);
	  };

	  // 상세 그리드 초기화
	  $scope.dtlGridDefault = function () {
	    $timeout(function () {
	      var cv          = new wijmo.collections.CollectionView([]);
	      cv.trackChanges = true;
	      $scope.data     = cv;
	      $scope.flex.refresh();
	      $scope.excelFg = false;
	    }, 10);
	  };

}]);


/** 설정기간별매출(매출상세) 엑셀 controller */
app.controller('orderEmpBensonPeriodDtlExcelCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	 // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('orderEmpBensonPeriodDtlExcelCtrl', $scope, $http, $timeout, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("orderEmpBensonPeriodDtlExcelCtrl", function (event, data) {

		  if(data != undefined && $scope.excelFg) {
				if($scope.excelStartDate > $scope.excelEndDate){
					$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
					return false;
				}
				 $scope.searchOrderEmpPeriodDtlExcelList();

			}else{
				$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
				return false;
			}

	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });

	  // 코너별매출일자별 리스트 조회
	  $scope.searchOrderEmpPeriodDtlExcelList = function () {

	    // 파라미터
	    var params       = {};
	    params.startDate = $scope.excelStartDate;
	    params.endDate   = $scope.excelEndDate;
	    params.storeCd   = $scope.excelStoreCd;
	    params.empNo     = $scope.excelEmpNo;


	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/benson/orderEmpBenson/orderEmpBenson/getOrderEmpPeriodDtlExcelList.sb", params, function(){
			var flex = $scope.excelFlex;
			//row수가 0이면
			if (flex.rows.length <= 0) {
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
				}, messages["orderEmpBenson.orderEmp"]+'_'+messages["orderEmpBenson.orderEmpPeriod"]+'('+messages["orderEmpBenson.saleDtl"]+')'+'_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);
	    });
	  };

}]);
