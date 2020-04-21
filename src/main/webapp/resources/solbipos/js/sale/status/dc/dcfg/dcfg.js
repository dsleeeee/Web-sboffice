/**
 * get application
 */
var app = agrid.getApp();

/** 할인구분별(매출리스트) controller */
app.controller('dcDcfgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dcDcfgCtrl', $scope, $http, $timeout, true));

  //groupRow 접고 펼치기 flag 변수
  $scope.setCollapsed = false;

  $scope.srchDcDcfgStartDate = wcombo.genDateVal("#srchDcDcfgStartDate", getToday());
  $scope.srchDcDcfgEndDate   = wcombo.genDateVal("#srchDcDcfgEndDate", getToday());
  $scope.orgnFg = gvOrgnFg;

  // 리스트 콤보박스 데이터 Set
  $scope._setComboData("dcDcfgListScaleBox", gvListScaleBoxData);
  $scope._setComboData("dcDcfgDtlListScaleBox", gvListScaleBoxData);

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dcDcfgSelectStoreShow = function () {
    $scope._broadcast('dcDcfgSelectStoreCtrl');
  };

  //할인유형선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dcDcfgSelectDcfgShow = function () {
    $scope._broadcast('dcDcfgSelectDcfgCtrl');
  };

  $scope.getDcNmList = function() {
	  $scope._broadcast('dcDcfgSelectedTableCtrl');
	  $("#dcDcfgSelectDcfgNm").val(messages["cmm.all"]);
	  $("#dcDcfgSelectDcfgCd").val("");
  }

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchDcDcfgStartDate.isReadOnly = $scope.isChecked;
    $scope.srchDcDcfgEndDate.isReadOnly = $scope.isChecked;
  };

  // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
	  $scope._broadcast("chkProdClassDisplay");
  }

}]);

/** 할인구분별(매출리스트) controller */
app.controller('dcDcfgMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dcDcfgMainCtrl', $scope, $http, $timeout, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
	s.refresh();

	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("dcDcfgMainCtrl");

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
  $scope.$on("dcDcfgMainCtrl", function (event, data) {
    $scope.searchDcDcfgList(true);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

	//다른 컨트롤러의 broadcast 받기
	$scope.$on("dcDcfgMainCtrlSrch", function (event, data) {
		$scope.searchDcDcfgList(false);

	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	});


  // 할인구분별 리스트 조회
  $scope.searchDcDcfgList = function (isPageChk) {

    // 파라미터
    var params       = {};
    if(!$scope.isChecked){
    	params.startDate = wijmo.Globalize.format($scope.srchDcDcfgStartDate.value, 'yyyyMMdd');
    	params.endDate = wijmo.Globalize.format($scope.srchDcDcfgEndDate.value, 'yyyyMMdd');
    }
    params.storeCd   = $("#dcDcfgSelectStoreCd").val();
    params.dcCd = $("#dcDcfgSelectDcfgCd").val();
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/dc/dcfg/list.sb", params);

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

    	// 할인구분별 매출현황 상세조회.
    	$scope._broadcast("dcDcfgDtlCtrlSrch", params);

	}
  };

  //엑셀 다운로드
  $scope.excelDownload = function () {

	// 파라미터
	var params = {};
	params.storeCd = $("#dcDcfgSelectStoreCd").val();
	params.dcCd = $("#dcDcfgSelectDcfgCd").val();

	if(!$scope.isChecked){
		params.startDate = wijmo.Globalize.format($scope.srchDcDcfgStartDate.value, 'yyyyMMdd');
    	params.endDate = wijmo.Globalize.format($scope.srchDcDcfgEndDate.value, 'yyyyMMdd');
	}

	params.isPageChk = true;

	$scope._broadcast('dcDcfgExcelCtrl',params);

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

	  $scope.$on("chkProdClassDisplay", function (event) {
		  var columns = $scope.flex.columns;

		  for(var i=0; i<columns.length; i++){
			  if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
				  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
			  }
		  }
	  });
	  // 할인구분별매출일자별 리스트 조회
	  $scope.searchDcfgDtlList = function (isPageChk) {

		$scope.setCollapsed = false;
	    // 파라미터
	    var params       = {};
	    params.startDate = $scope.startDate;
	    params.endDate   = $scope.endDate;
	    params.storeCd   = $scope.storeCd;
	    params.dcCd   = $scope.dcCd;
	    params.listScale = $scope.dcDcfgDtlListScale;
	    params.isPageChk = isPageChk;
	    params.orgnFg    = $scope.orgnFg;

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/status/dc/dcfg/dtl.sb", params);

	    //create a group to show the grand totals
	    var grpLv1 = new wijmo.collections.PropertyGroupDescription('전체');
	    var grpLv2 = new wijmo.collections.PropertyGroupDescription('dcdtlDcNm');

	    var theGrid = new wijmo.Control.getControl('#dcfgDtlGrid');
	    theGrid.itemsSource = new wijmo.collections.CollectionView();

	    // custom cell calculation
	    theGrid.formatItem.addHandler(function(s, e) {

	    	var lengthTemp = s.collectionView.groupDescriptions.length;

	    	if (lengthTemp < 2) {
	    		s.collectionView.groupDescriptions.push(grpLv1);
	        	s.collectionView.groupDescriptions.push(grpLv2);
	    	}

	    	s.rows.forEach(function(row) {
	    		if(row instanceof wijmo.grid.GroupRow){
	    			var groupProp=row.dataItem.groupDescription.propertyName;
	    			var className=null;
	    			switch(groupProp){
	    				case "전체":className="grp-lv-1";break;
	    				case "dcdtlDcNm":className="grp-lv-2";break;
	    			}

	    			if(className){
	    				row.cssClass=className;
	    			}

	    			if(row.level == 1) {
						if(!$scope.setCollapsed){
							row.isCollapsed = true;
						}
					}
	    		}
	    	});

	    });

		// 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
	    theGrid.addEventListener(theGrid.hostElement, 'mousedown', function (e) {
	      var ht = theGrid.hitTest(e);
	      if (ht.cellType === wijmo.grid.CellType.Cell) {
	        if (theGrid.rows[ht.row].level == 1) { // 2단계 분류
	        	$scope.setCollapsed = true;
	        	var isCollapsed = theGrid.rows[ht.row].isCollapsed;
	        	theGrid.rows[ht.row].isCollapsed ? false : true;
	        }
	      }
	    });

	    // start collapsed
	    theGrid.collapseGroupsToLevel(1);
	    theGrid.collectionView.refresh();
//	    $scope.flex.refresh();
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
	        includeCellStyles   : true,
	        includeColumns      : function (column) {
	          return column.visible;
	        }
	      }, '할인구분별-상품상세-'+getToday()+'.xlsx', function () {
	        $timeout(function () {
	          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
	        }, 10);
	      });
	    }, 10);
	  };

}]);

app.controller('dcDcfgExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('dcDcfgExcelCtrl', $scope, $http, $timeout, true));

	var checkInt = true;

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {

		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');

	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("dcDcfgExcelCtrl", function (event, data) {

		var storeCd = $("#dcDcfgSelectStoreCd").val();
		var dcCd = $("#dcDcfgSelectDcCd").val();

		if(data != undefined) {

			if(data.startDate > data.endDate){
				$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
				return false;
			}

			$scope.storeCd = data.storeCd;
			$scope.dcCd = data.dcCd;
			$scope.startDate = data.startDate;
			$scope.endDate = data.endDate;

			$scope.searchDcDcfgExcelList(true);
		}

	});

	// 포스별매출일자별 리스트 조회
	$scope.searchDcDcfgExcelList = function (isPageChk) {

		// 파라미터
		var params = {};
		params.storeCd = $scope.storeCd;
		params.dcCd = $scope.dcCd;
		params.listScale = 0 //-페이지 스케일 갯수
		params.isPageChk = isPageChk;
	    params.startDate = $scope.startDate;
		params.endDate = $scope.endDate;

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/dc/dcfg/excelList.sb", params, function() {

			var flex = $scope.excelFlex;
			//row수가 0이면
			if(flex.rows.length === 0){

				 var grid = wijmo.Control.getControl("#dcDcfgExcelGrid")
				//컬럼 삭제
				while(grid.columns.length > 7){
			          grid.columns.removeAt(grid.columns.length-1);
			    }
			}

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
				}, messages["dcDcfg.dcfg"]+'_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);

		});
	};

}]);