/**
 * get application
 */
var app = agrid.getApp();

/** 할인구분별(매출리스트) controller */
app.controller('dcDcfgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dcDcfgCtrl', $scope, $http, $timeout, true));

  $scope.srchDcDcfgStartDate = wcombo.genDateVal("#srchDcDcfgStartDate", gvStartDate);
  $scope.srchDcDcfgEndDate   = wcombo.genDateVal("#srchDcDcfgEndDate", gvEndDate);

  // 리스트 콤보박스 데이터 Set
  $scope._setComboData("dcDcfgListScaleBox", gvListScaleBoxData);
  $scope._setComboData("dcDcfgDtlListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dcDcfgCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "realSaleAmt") { // 실매출
          	wijmo.addClass(e.cell, 'wijLink');
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
        	params.dcCd   = selectedRow.dcCd;
        	params.storeCd   = selectedRow.storeCd;
        	params.startDate   = selectedRow.saleDate;
    	    params.endDate   = selectedRow.saleDate;
        if (col.binding === "realSaleAmt") { // 실매출
          $scope._broadcast('dcDcfgDtlCtrlSrch', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  }

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dcDcfgCtrl", function (event, data) {
    $scope.searchDcDcfgList(true);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

	//다른 컨트롤러의 broadcast 받기
	$scope.$on("dcDcfgCtrlSrch", function (event, data) {
		$scope.searchDcDcfgList(false);

	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	});


  // 할인구분별 리스트 조회
  $scope.searchDcDcfgList = function (isPageChk) {
  if ($("#dcDcfgSelectStoreCd").val() === '') {
      $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
      return false;
    }

    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchDcDcfgStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchDcDcfgEndDate.value, 'yyyyMMdd');
    params.storeCd   = $("#dcDcfgSelectStoreCd").val();
//    var storeDcfg   = $("#dcDcfgSelectStoreCd").val().split(",");
//	var arrStore= [];
//	var arrDcfg= [];
//	for(var i=0; i < storeDcfg.length; i++) {
//		var temp = storeDcfg[i].split("||");
//		arrStore.push(temp[0]);
//		arrDcfg.push(temp[1]);
//	}
    params.dcCd = $("#dcDcfgSelectDcfgCd").val();
    params.listScale = $scope.dcDcfgListScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/status/dc/dcfg/list.sb", params);

    //메인그리드 조회후 상세그리드 조회.
	$scope.loadedRows = function(sender, args){

		var rows = sender.rows;
		var params		 = {};

		if(rows.length != 0) {
			 params.startDate   = rows[0].dataItem.saleDate;
		     params.endDate   = rows[0].dataItem.saleDate;
			 params.dcCd   = rows[0].dataItem.dcCd;
			 params.storeCd   = rows[0].dataItem.storeCd;
			 params.isPageChk = false;
	    }
		else {
			params.dcCd   = -1;
		}

    	// 코너별 매출현황 상세조회.
    	$scope._broadcast("dcDcfgDtlCtrlSrch", params);

	}
  };

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dcDcfgSelectStoreShow = function () {
    $scope._broadcast('dcDcfgSelectStoreCtrl');
  };

  $scope.dcDcfgSelectDcfgShow = function () {
    $scope._broadcast('dcDcfgSelectDcfgCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownloadDcDcfg = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, 'dcDcfg.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };


}]);


/** 할인구분별매출(매출상세) controller */
app.controller('dcDcfgDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	 // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('dcDcfgDtlCtrl', $scope, $http, $timeout, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("dcDcfgDtlCtrl");
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("dcDcfgDtlCtrl", function (event, data) {

		  if(data != undefined){
			  $scope.startDate   = data.startDate;
			  $scope.endDate   = data.endDate;
			  $scope.storeCd   = data.storeCd;
			  $scope.dcCd   = data.dcCd;
		  }

	    $scope.searchDcfgDtlList(true);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });

	  $scope.$on("dcDcfgDtlCtrlSrch", function (event, data) {

		  if(data != undefined){
			$scope.startDate = data.startDate;
			$scope.endDate   = data.endDate;
			$scope.storeCd   = data.storeCd;
			$scope.dcCd   = data.dcCd;
		  }

		    $scope.searchDcfgDtlList(false);
		    // 기능수행 종료 : 반드시 추가
		    event.preventDefault();
	  });

	  // 코너별매출일자별 리스트 조회
	  $scope.searchDcfgDtlList = function (isPageChk) {
	  if ($("#dcDcfgSelectStoreCd").val() === '') {
	      $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
	      return false;
	    }

	    // 파라미터
	    var params       = {};
	    params.startDate = $scope.startDate;
	    params.endDate   = $scope.endDate;
	    params.storeCd   = $scope.storeCd;
	    params.dcCd   = $scope.dcCd;
	    params.listScale = $scope.dcDcfgDtlListScale;
	    params.isPageChk = isPageChk;

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/status/dc/dcfg/dtl.sb", params);
	    $scope.flex.refresh();
	  };

	//엑셀 다운로드
	  $scope.excelDownloadDcDcfgDtl = function () {
	    if ($scope.flex.rows.length <= 0) {
	      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
	      return false;
	    }

	    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
	    $timeout(function () {
	      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
	        includeColumnHeaders: true,
	        includeCellStyles   : false,
	        includeColumns      : function (column) {
	          return column.visible;
	        }
	      }, 'dcDcfgDtl.xlsx', function () {
	        $timeout(function () {
	          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
	        }, 10);
	      });
	    }, 10);
	  };
}]);
