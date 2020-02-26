/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('cornerDayPeriodCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('cornerDayPeriodCtrl', $scope, $http, $timeout, true));

    $scope.srchCornerDayPeriodStartDate = wcombo.genDateVal("#srchCornerDayPeriodStartDate", getToday());
	$scope.srchCornerDayPeriodEndDate   = wcombo.genDateVal("#srchCornerDayPeriodEndDate", getToday());

	//조회조건 콤보박스 데이터 Set
	$scope._setComboData("cornerDayPeriodListScaleBox", gvListScaleBoxData);
	$scope._setComboData("cornerDayPeriodDtlListScaleBox", gvListScaleBoxData);
	$scope.orgnFg = gvOrgnFg;
	
	//매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.cornerDayPeriodSelectStoreShow = function () {
		$scope._broadcast('cornerDayPeriodSelectStoreCtrl');
	};

	//코너선택 모듈 팝업 사용시 정의
	//함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	//_broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.cornerDayPeriodSelectCornerShow = function () {
		$scope._broadcast('cornerDayPeriodSelectCornerCtrl');
	};

	//전체기간 체크박스 클릭이벤트
	$scope.isChkDt = function() {
		$scope.srchCornerDayPeriodStartDate.isReadOnly = $scope.isChecked;
		$scope.srchCornerDayPeriodEndDate.isReadOnly = $scope.isChecked;
	};

	//매장의 코너(corner) 리스트 조회
	$scope.getCornerNmList = function () {
		var storeCd = $("#cornerDayPeriodSelectStoreCd").val();
		var cornrCd = $("#cornerDayPeriodSelectCornerCd").val();
		$scope.getReCornerNmList(storeCd, cornrCd);
	};

	//매장의 코너 리스트 재생성
	$scope.getReCornerNmList = function (storeCd, cornrCd) {
		var url = "/sale/status/corner/corner/cornerNmList.sb";
	    var params = {};
	    params.storeCd = storeCd;
	    params.cornrCd = cornrCd;
	    params.hqOfficeCd = $("#HqOfficeCd").val();

	    // ajax 통신 설정
	    $http({
	    	method : 'POST', //방식
	    	url    : url, /* 통신할 URL */
	    	params : params, /* 파라메터로 보낼 데이터 */
	    	headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
	    }).then(function successCallback(response) {
	    	if ($scope._httpStatusCheck(response, true)) {
	    		if (!$.isEmptyObject(response.data.data.list)) {
	    			var list       = response.data.data.list;
	    			var arrStoreCornr = [];
	    			var arrStoreCornrNm = [];

	    			for (var i = 0; i < list.length; i++) {
	    				arrStoreCornr.push(list[i].cornrCd);
	    				arrStoreCornrNm.push(list[i].storeNm + "||" + list[i].cornrNm);
	    			}

	    			$("#cornerDayPeriodSelectCornerCd").val(arrStoreCornr.join());
	    			$("#cornerDayPeriodSelectCornerName").val(arrStoreCornrNm.join());

	    			storeCornrCd = $("#cornerDayPeriodSelectCornerCd").val();
	    			storeCornrNm = $("#cornerDayPeriodSelectCornerName").val();

	    		}
	    	}
	    }, function errorCallback(response) {
	      $scope._popMsg(messages["cmm.error"]);
	      return false;
	    }).then(function () {

	    });
	  };
}]);


/** 일자별(코너별 매출) controller */
app.controller('cornerDayPeriodMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cornerDayPeriodMainCtrl', $scope, $http, $timeout, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("cornerDayPeriodMainCtrl");

    var storeCd = $("#cornerDayPeriodSelectStoreCd").val();
	$scope.getReCornerNmList(storeCd);

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
          	  params.chkPop   = "tablePop";
              params.cornrCd   = selectedRow.cornrCd;
              params.storeCd   = selectedRow.storeCd;
//              params.startDate = $scope.startDateForDt;
//              params.endDate   = $scope.endDateForDt;
          if (col.binding === "realSaleAmt") { // 실매출
            $scope._broadcast('cornerDayPeriodDtlCtrlSrch', params);
          }
        }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("cornerDayPeriodMainCtrl", function (event, data) {
    $scope.searchCornerDayPeriodList(true);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 다른 컨트롤러의 broadcast 받기(페이징 초기화)
  $scope.$on("cornerDayPeriodMainCtrlSrch", function (event, data) {
    $scope.searchCornerDayPeriodList(false);
    console.log("cornerDayPeriodSrch!!");

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 코너별매출 설정기간별 리스트 조회
  $scope.searchCornerDayPeriodList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.storeCd   = $("#cornerDayPeriodSelectStoreCd").val();
    params.cornrCd   = $("#cornerDayPeriodSelectCornerCd").val();
    if(params.cornrCd == ""){ //-해당 매장의 코너가 없을시 조회x
    	params.cornrCd = -1;
    }
    params.listScale = $scope.cornerDayPeriodListScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk; //-페이지 초기화 여부

	//등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
	  params.startDate = wijmo.Globalize.format($scope.srchCornerDayPeriodStartDate.value, 'yyyyMMdd');
	  params.endDate = wijmo.Globalize.format($scope.srchCornerDayPeriodEndDate.value, 'yyyyMMdd');
	}
	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}
	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/corner/corner/list.sb", params);

	//메인그리드 조회후 상세그리드 조회.
    $scope.loadedRows = function(sender, args){
        var rows = sender.rows;

        var params       = {};
        if(rows.length > 0){
        	params.storeCd   = rows[0].dataItem.storeCd;
        	params.cornrCd   = rows[0].dataItem.cornrCd;

	        // 등록일자 '전체기간' 선택에 따른 params
	        if(!$scope.isChecked){
	          $scope.startDateForDt = wijmo.Globalize.format($scope.srchCornerDayPeriodStartDate.value, 'yyyyMMdd');
	          $scope.endDateForDt = wijmo.Globalize.format($scope.srchCornerDayPeriodEndDate.value, 'yyyyMMdd');

	          params.startDate = wijmo.Globalize.format($scope.srchCornerDayPeriodStartDate.value, 'yyyyMMdd');
	          params.endDate = wijmo.Globalize.format($scope.srchCornerDayPeriodEndDate.value, 'yyyyMMdd');
	        }else{
	            $scope.startDateForDt = "";
	            $scope.endDateForDt = "";
	        }
        }else{
        	params.cornrCd = -1;
        }
        // 코너별 매출현황 상세조회.
        $scope._broadcast("cornerDayPeriodDtlCtrlSrch", params);
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
      }, '매출현황_코너별_설정기간별_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
}]);




/** 설정기간별매출(매출상세) controller */
app.controller('cornerDayPeriodDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	 //Main은 어디로??
	 // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('cornerDayPeriodDtlCtrl', $scope, $http, $timeout, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("cornerDayPeriodDtlCtrl");

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("cornerDayPeriodDtlCtrl", function (event, data) {

	    $scope.searchCornerDayPeriodDtlList(true);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });

	  // 다른 컨트롤러의 broadcast 받기(페이징 초기화)
	  $scope.$on("cornerDayPeriodDtlCtrlSrch", function (event, data) {
		$scope.storeCd   = data.storeCd;
		$scope.cornrCd   = data.cornrCd;

	    $scope.searchCornerDayPeriodDtlList(false);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });

	  // 코너별매출일자별 리스트 조회
	  $scope.searchCornerDayPeriodDtlList = function (isPageChk) {
	    // 파라미터
	    var params       = {};
	    params.listScale = $scope.cornerDayPeriodDtlListScale; //-페이지 스케일 갯수
	    params.startDate = $scope.startDateForDt;
	    params.endDate   = $scope.endDateForDt;
	    params.storeCd   = $scope.storeCd;
	    params.cornrCd   = $scope.cornrCd;
	    params.isPageChk = isPageChk;
	    params.orgnFg    = $scope.orgnFg;

	    $scope._inquirySub("/sale/status/corner/corner/dtl.sb", params);
//		$scope.flex.refresh();
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
	      }, '매출현황_코너별_설정기간별(상세)_'+getToday()+'.xlsx', function () {
	        $timeout(function () {
	          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
	        }, 10);
	      });
	    }, 10);
	  };
}]);
