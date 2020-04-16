/**
 * get application
 */
var app = agrid.getApp();

/** 일자별상품 상세현황 controller */
app.controller('versusPeriodWeekCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('versusPeriodWeekCtrl', $scope, $http, true));

  var srchStartDateDash;
  var srchEndDateDash;
  var compStartDateDash;
  var compEndDateDash;

  // 대비일자 세팅
//  $scope.srchCompStartDate = wcombo.genDateVal("#compWeekStartDate", getToday());
//  $scope.srchCompEndDate   = wcombo.genDateVal("#compWeekEndDate", getToday());
//
//  $scope.changeDate = function() {
//
//	var srchStartDate = new Date($scope.srchStartDate);
//    var srchEndDate = new Date($scope.srchEndDate);
//
//	srchStartDate.setFullYear(srchStartDate.getFullYear() - 1);
//	srchEndDate.setFullYear(srchEndDate.getFullYear() - 1);
//
//	var startResult = $scope.getFormatDate(srchStartDate);
//	$scope.srchCompStartDate.text = $scope.getFormatDate(srchStartDate);
//
//	var endResult = $scope.getFormatDate(srchEndDate);
//	$scope.srchCompEndDate.text = endResult;
//
//	srchStartDateDash = wijmo.Globalize.format($scope.srchWeekStartDate, 'yyyy-MM-dd');
//	srchEndDateDash = wijmo.Globalize.format($scope.srchWeekEndDate, 'yyyy-MM-dd');
//
//	compStartDateDash = $scope.srchCompStartDate.text;
//    compEndDateDash = $scope.srchCompEndDate.text;
//	//compEndDateDash = result;
//
//  };


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    var srchStartDate = new Date($scope.srchStartDate);
    srchStartDate.setDate(1);
    $scope.startDateCombo.text = $scope.getFormatDate(srchStartDate);

	srchStartDateDash = wijmo.Globalize.format($scope.srchStartDate, 'yyyy-MM-dd');
	srchEndDateDash = wijmo.Globalize.format($scope.srchEndDate, 'yyyy-MM-dd');
	compStartDateDash = $scope.compStartDateCombo.text;
	compEndDateDash = $scope.compEndDateCombo.text;

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("versusPeriodWeekCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.lvNm      = messages["versusPeriod.day"];
    dataItem.saleDateCntA      = messages["versusPeriod.period"];
    dataItem.realSaleAmtA  = messages["versusPeriod.period"];
    dataItem.saleCntA    = messages["versusPeriod.period"];
    dataItem.ratA = messages["versusPeriod.period"];
    dataItem.saleDateCntB       = messages["versusPeriod.comp"];
    dataItem.realSaleAmtB      = messages["versusPeriod.comp"];
    dataItem.saleCntB   = messages["versusPeriod.comp"];
    dataItem.ratB   = messages["versusPeriod.comp"];
    dataItem.sinAmt     = messages["versusPeriod.sin"];
    dataItem.sinCnt    = messages["versusPeriod.sin"];

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

    // 그리드 클릭 이벤트
	s.addEventListener(s.hostElement, 'mousedown', function (e) {
    	var ht = s.hitTest(e);

    	/* 머지된 헤더 셀 클릭시 정렬 비활성화
    	 * 헤더 cellType: 2 && 머지된 row 인덱스: 0 && 동적 생성된 column 인덱스 0 초과
    	 * 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
    	 */
    	if(ht.cellType == 2 && ht.row < 1 && ht.col > 0) {
    		s.allowSorting = false;
		} else {
			s.allowSorting = true;
		}
	});

  };

  $scope.getFormatDate = function getFormatDate(date) {
	var year = date.getFullYear();              //yyyy
	var month = (1 + date.getMonth());          //M
	month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
	var day = date.getDate();                   //d
	day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
	return  year + '-' + month + '-' + day;
  }

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("versusPeriodWeekCtrl", function (event, data) {
    $scope.searchVersusPeriodWeekList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("versusPeriodWeekCtrlSrch", function (event, data) {
    $scope.searchVersusPeriodWeekList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 주간대비 리스트 조회
  $scope.searchVersusPeriodWeekList = function (isPageChk) {

//    if ($("#versusPeriodWeekSelectStoreCd").val() === '') {
//      $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
//      return false;
//    }

	srchStartDateDash = wijmo.Globalize.format($scope.srchStartDate, 'yyyy-MM-dd');
	srchEndDateDash = wijmo.Globalize.format($scope.srchEndDate, 'yyyy-MM-dd');
	compStartDateDash = $scope.compStartDateCombo.text;
	compEndDateDash = $scope.compEndDateCombo.text;

    // 파라미터
    var params       = {};
    params.startDate = srchStartDateDash;
    params.endDate = srchEndDateDash;
    params.compStartDate = compStartDateDash;
    params.compEndDate = compEndDateDash;
    params.storeCd   = $("#versusPeriodWeekSelectStoreCd").val();
    params.isPageChk = isPageChk;

    /*// 조회일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }

    // 대비일자 '전체기간' 선택에 따른 params
    if(!$scope.isCheckedComp){
      params.compStartDate = wijmo.Globalize.format($scope.compStartDate.value, 'yyyyMMdd');
      params.compEndDate = wijmo.Globalize.format($scope.compEndDate.value, 'yyyyMMdd');
    }*/

    if(srchStartDateDash.split(/-/gi, '') > srchEndDateDash.split(/-/gi, '') || compStartDateDash.split(/-/gi, '') > compEndDateDash.split(/-/gi, '')){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }

    // 조회일자와 대비일자 설정기간이 동일한지 유효성 체크
//    if($scope.dateDiff(srchStartDateDash, srchEndDateDash) !== $scope.dateDiff(compStartDateDash, compEndDateDash)){
//   	 	$scope._popMsg(messages["versusPeriod.dateDiff"]);
//   	 	return false;
//    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/anals/versusPeriod/week/list.sb", params, function() {});

    var days = "(" + $scope.dateDiff(srchStartDateDash, srchEndDateDash) + "일)\n";
    var compDays = "(" + $scope.dateDiff(compStartDateDash, compEndDateDash) + "일)\n";
    var srchStartToEnd = "(" + srchStartDateDash + " ~ " + srchEndDateDash + ")";
    var compStartToEnd = "(" + compStartDateDash + " ~ " + compEndDateDash + ")";

    var grid = wijmo.Control.getControl("#versusPeriodWeekGrid").columnHeaders.rows[0].dataItem;
    grid.saleDateCntA      = messages["versusPeriod.period"]+ (days + srchStartToEnd);
    grid.realSaleAmtA  = messages["versusPeriod.period"]+ (days + srchStartToEnd);
    grid.saleCntA    = messages["versusPeriod.period"]+ (days + srchStartToEnd);
    grid.ratA = messages["versusPeriod.period"]+ (days + srchStartToEnd);
    grid.saleDateCntB       = messages["versusPeriod.comp"] + (compDays + compStartToEnd);
    grid.realSaleAmtB      = messages["versusPeriod.comp"] + (compDays + compStartToEnd);
    grid.saleCntB   = messages["versusPeriod.comp"] + (compDays + compStartToEnd);
    grid.ratB   = messages["versusPeriod.comp"] + (compDays + compStartToEnd);

	// 대비기간매출분석(주간대비) 바 차트
    $scope._broadcast("versusPeriodWeekChartCtrl", params);
    
  };

  //조회일자 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly = $scope.isChecked;
  };

  
  // 대비기간 전체기간 체크박스 클릭이벤트
  $scope.isChkDtComp = function() {
	$scope.srchCompStartDate.isReadOnly = $scope.isCheckedComp;
	$scope.srchCompEndDate.isReadOnly = $scope.isCheckedComp;
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.versusPeriodWeekSelectStoreShow = function () {
    $scope._broadcast('versusPeriodWeekSelectStoreCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownloadDay = function () {
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
      }, '매출분석_대비기간매출분석_주간대비_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

  //두개의 날짜를 비교하여 차이를 알려준다.
  $scope.dateDiff = function(date1, date2) {

	  var diffDate_1 = date1 instanceof Date ? date1 : new Date(date1);
	  var diffDate_2 = date2 instanceof Date ? date2 : new Date(date2);

	  var diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
	  diff = Math.ceil(diff / (1000 * 3600 * 24));

	  return diff + 1;
  }

}]);



/** 대비기간매출분석 주간대비 차트 (요일 바) controller */
app.controller('versusPeriodWeekChartCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	angular.extend(this, new RootController('versusPeriodWeekChartCtrl', $scope, $http, $timeout, true));

	//메인그리드 조회후 상세그리드 조회.
	$scope.initChart = function(s, args){
		s.plotMargin = 'auto auto 50 auto';
		s.axisX.labelAngle = 0;
	    //s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;

	    var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
	        animationMode: wijmo.chart.animation.AnimationMode.All,
	        easing: wijmo.chart.animation.Easing.Linear,
	        duration: 400
	    });

	    /*var axisY2 = new wijmo.chart.Axis();
	    axisY2.position = 'Right';
	    axisY2.title = '판매수량';
	    axisY2.format = 'n0';
	    axisY2.min = 0;
	    axisY2.axisLine = true;

	    getSeries("saleCntMon").axisY = axisY2;
	    getSeries("saleCntTue").axisY = axisY2;
	    getSeries("saleCntWed").axisY = axisY2;
	    getSeries("saleCntThu").axisY = axisY2;
	    getSeries("saleCntFri").axisY = axisY2;
	    getSeries("saleCntSat").axisY = axisY2;
	    getSeries("saleCntSun").axisY = axisY2;

	    function getSeries(binding) {
	        var seriesTemp = s.series;
	        //
	        for (var i = 0; i < seriesTemp.length; i++) {
	            if (seriesTemp[i].binding == binding) {
	                return seriesTemp[i];
	            }
	        }
	        //
	        return null;
	    }*/

	}

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("versusPeriodWeekChartCtrl", function (event, data) {

		var isPageChk = true;

		if(data != undefined) {

			$scope.startDate     = data.startDate;
			$scope.endDate       = data.endDate;
			$scope.compStartDate = data.compStartDate;
			$scope.compEndDate   = data.compEndDate;
			$scope.storeCd       = data.storeCd;
			$scope.isPageChk     = data.isPageChk;

		}

	    $scope.versusPeriodWeekChartList(isPageChk);
	    // 기능수행 종료 : 반드시 추가
	    //event.preventDefault();
	  });


	  // 대비기간매출분석 주간대비 리스트 조회
	  $scope.versusPeriodWeekChartList = function (isPageChk) {

		  // 파라미터
		  var params            = {};
		  params.listScale      = 10;
		  params.startDate      = $scope.startDate;
		  params.endDate        = $scope.endDate;
		  params.compStartDate  = $scope.compStartDate;
		  params.compEndDate    = $scope.compEndDate;
		  params.storeCd        = $scope.storeCd;

		  if (isPageChk != null && isPageChk != undefined) {
			  params.isPageChk    = isPageChk;
		  } else {
			  params.isPageChk    = true;
		  }

		  // 조회 수행 : 조회URL, 파라미터, 콜백함수
		  $scope._inquiryMain("/sale/anals/versusPeriod/week/chartList.sb", params);
	  };

	$scope.rendered = function(s, e) {
		
		var pArea =  s.hostElement.querySelector('.wj-plot-area > rect');
		var pAreaWidth = pArea.width.baseVal.value;
		var groupWidth = pAreaWidth / (s.collectionView.items.length || 1);

		var labels = document.querySelectorAll('.wj-axis-x .wj-label');
		var widthMax = new Array();

        labels.forEach((value, key, parent) => {

        	var x = +value.getAttribute('x');
            var y = +value.getAttribute('y');
            var text = value.innerHTML.split(' - ');
            value.innerHTML = '';

            widthMax[key] = new Array();

            text.forEach((item, index) => {

                var e = document.createElementNS('http://www.w3.org/2000/svg', 'tspan');
                //e.setAttribute("x", (x + 0).toString());
                e.setAttribute('y', (y + (15 * index)).toString());
                e.innerHTML = item;
                value.appendChild(e);

                var bbox = e.getBoundingClientRect();
                var extent = e.getExtentOfChar(0);
                var boxWidth = e.getComputedTextLength();
                var gap = 0;

                gap = (groupWidth - boxWidth) / 2;
                widthMax[key][index] = gap;

                e.setAttribute('x', (x + gap).toString());
            });
        });

        labels.forEach((value, key, parent) => {

        	var children = value.childNodes;

        	for (var i = 0; i < children.length; i++) {
        		var e = value.childNodes[i];
        		var extent = e.getExtentOfChar(0);

        		e.setAttribute('x', extent.x - widthMax[key][0] + 30);
        	}
        });

        s.tooltip.content = function (ht) {
        	var title = ht.name;
			var nameArr = ht._xfmt;
			var value = ht.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			return "<b>" + title + "</b><br><br>" + nameArr + "<br><br>" + value;
		}
    }

}]);


