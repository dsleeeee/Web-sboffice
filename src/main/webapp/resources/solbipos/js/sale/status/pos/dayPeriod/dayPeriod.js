/**
 * get application
 */
var app = agrid.getApp();

app.controller('posDayPeriodCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('posDayPeriodCtrl', $scope, $http, $timeout, true));

	  $scope.srchPosDayPeriodStartDate = wcombo.genDateVal("#srchPosDayPeriodStartDate", getToday());
	  $scope.srchPosDayPeriodEndDate   = wcombo.genDateVal("#srchPosDayPeriodEndDate", getToday());

	  //조회조건 콤보박스 데이터 Set
	  $scope._setComboData("posDayPeriodListScaleBox", gvListScaleBoxData);
	  $scope._setComboData("posDayPeriodDtlListScaleBox", gvListScaleBoxData);
	  $scope.orgnFg = gvOrgnFg;

	  //전체기간 체크박스 클릭이벤트
	  $scope.isChkDt = function() {
	    $scope.srchPosDayPeriodStartDate.isReadOnly = $scope.isChecked;
	    $scope.srchPosDayPeriodEndDate.isReadOnly = $scope.isChecked;
	  };

	  // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
	  $scope.isChkProdClassDisplay = function(){
		  $scope._broadcast("chkProdClassDisplay");
	  }

	  //매장선택 모듈 팝업 사용시 정의
	  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	  $scope.posDayPeriodSelectStoreShow = function () {
	    $scope._broadcast('posDayPeriodSelectStoreCtrl');
	  };

}]);

/** 일자별(코너별 매출) controller */
app.controller('posDayPeriodMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('posDayPeriodMainCtrl', $scope, $http, $timeout, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("posDayPeriodMainCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.binding === "realSaleAmt") { // 실매출
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
//        	var arrPosNo     = (selectedRow.posNo).split("POS ");
//        	params.posNo     = arrPosNo[1];
        	params.posNo     = selectedRow.posNo;
        	params.startDate = $scope.startDateForDt;
        	params.endDate   = $scope.endDateForDt;
        	params.isPageChk = false;
        if (col.binding === "realSaleAmt") { // 실매출
            $scope._broadcast('posDayPeriodDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("posDayPeriodMainCtrl", function (event, data) {
    $scope.searchPosDayPeriodList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("posDayPeriodMainCtrlSrch", function (event, data) {
    $scope.searchPosDayPeriodList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 코너별매출일자별 리스트 조회
  $scope.searchPosDayPeriodList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
    params.storeCd   = $("#posDayPeriodSelectStoreCd").val();
    params.isPageChk = isPageChk;

	//등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
	  $scope.startDateForDt = wijmo.Globalize.format($scope.srchPosDayPeriodStartDate.value, 'yyyyMMdd');
      $scope.endDateForDt = wijmo.Globalize.format($scope.srchPosDayPeriodEndDate.value, 'yyyyMMdd');

	  params.startDate = wijmo.Globalize.format($scope.srchPosDayPeriodStartDate.value, 'yyyyMMdd');
	  params.endDate = wijmo.Globalize.format($scope.srchPosDayPeriodEndDate.value, 'yyyyMMdd');

	  //params.startDate = '20191201';
	  //params.endDate = '20191231';

	}else{
    	$scope.startDateForDt = "";
    	$scope.endDateForDt = "";
    }
	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}
	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/pos/pos/list.sb", params);

	//메인그리드 조회후 상세그리드 조회.
	$scope.loadedRows = function(sender, args){
		var rows = sender.rows;

		var params		 = {};
//		var arrPosNo     = (rows[0].dataItem.posNo).split("POS ");
//    	params.posNo     = arrPosNo[1];
    	params.storeCd   = rows[0].dataItem.storeCd;
		params.posNo     = rows[0].dataItem.posNo;
		params.data      = $scope.flex.collectionView; //새로 추가
		params.isPageChk = false;

	    // 코너별 매출현황 상세조회.
	    $scope._broadcast("posDayPeriodDtlCtrl", params);
	}
  };

//엑셀 다운로드
  $scope.excelDownloadDayPeriod = function () {
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
      }, messages["month.sale"]+'_'+messages["empsale.pos"]+'_'+messages["pos.dayPeriod"]+'_MAIN_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
}]);

/** 반품현황 상세(포스별 상세) controller */
app.controller('posDayPeriodDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	 // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('posDayPeriodDtlCtrl', $scope, $http, $timeout, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("posDayPeriodDtlCtrl");

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	  }

	  $scope.$on("chkProdClassDisplay", function (event) {
		  var columns = $scope.flex.columns;

		  for(var i=0; i<columns.length; i++){
			  if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
				  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
			  }
		  }
	  });

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("posDayPeriodDtlCtrl", function (event, data) {

		  var isPageChk = true;

		  if(data != undefined){
			$scope.startDate = data.startDate;
			$scope.endDate = data.endDate;
			$scope.posNo = data.posNo;
			$scope.storeCd = data.storeCd;
			isPageChk = data.isPageChk;
		  }

	    $scope.searchPosDayPeriodDtlList(isPageChk);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


	  // 코너별매출일자별 리스트 조회
	  $scope.searchPosDayPeriodDtlList = function (isPageChk) {
	    // 파라미터
	    var params          = {};
	    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
	    params.posNo        = $scope.posNo;
	    params.storeCd      = $scope.storeCd;
	    params.startDate    = $scope.startDateForDt;
	    params.endDate      = $scope.endDateForDt;
	    params.orgnFg    	= $scope.orgnFg;
	    if (isPageChk != null && isPageChk != undefined) {
	    	params.isPageChk    = isPageChk;
	    } else {
	    	params.isPageChk    = true;
	    }

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquiryMain("/sale/status/pos/pos/dtl.sb", params);
	  };

	//엑셀 다운로드
	  $scope.excelDownloadDayPeriodDtl = function () {
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
	      }, messages["month.sale"]+'_'+messages["empsale.pos"]+'_'+messages["pos.dayPeriod"]+'_DETAIL_'+getToday()+'.xlsx', function () {
	        $timeout(function () {
	          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
	        }, 10);
	      });
	    }, 10);
	  };
}]);