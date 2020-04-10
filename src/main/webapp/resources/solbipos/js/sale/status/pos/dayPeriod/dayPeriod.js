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
    params.listScale = $scope.posDayPeriodListScale; //-페이지 스케일 갯수
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

	    // 설정기간별(포스별 매출) 바 차트
	    $scope._broadcast("posDayPeriodBarChartCtrl", params);

	    // 설정기간별(포스별 매출) 파이 차트
	    $scope._broadcast("posDayPeriodPieChartCtrl", params);
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
	    params.listScale    = $scope.posDayPeriodDtlListScale; //-페이지 스케일 갯수
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


/** 설정기간별 차트 (포스별 바) controller */
app.controller('posDayPeriodBarChartCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	angular.extend(this, new RootController('posDayPeriodBarChartCtrl', $scope, $http, $timeout, true));

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

	}

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("posDayPeriodBarChartCtrl", function (event, data) {

		if (data != undefined) {
			$scope.data = data.data.items;
		}

		event.preventDefault();
	});

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

                console.log(boxWidth);

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
			var nameArr = ht._xfmt.split(" - ");
			var value = ht.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			return "<b>" + title + "</b><br><br>" + nameArr[0] + "<br>" + nameArr[1] + "<br><br>" + value;
		}
    }

}]);

/** 설정기간별 차트 (포스별 파이) controller */
app.controller('posDayPeriodPieChartCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	angular.extend(this, new RootController('posDayPeriodPieChartCtrl', $scope, $http, $timeout, true));

	//메인그리드 조회후 상세그리드 조회.
	$scope.initChart = function(s, args){
		s.plotMargin = 'auto auto auto auto';
		var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
	        animationMode: wijmo.chart.animation.AnimationMode.Point,
	        easing: wijmo.chart.animation.Easing.Linear,
	        duration: 400
	    });
	}

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("posDayPeriodPieChartCtrl", function (event, data) {

		if (data != undefined) {
			$scope.data = data.data.items;
			$scope.sum = $scope.data.map(c => c.realSaleAmt).reduce((sum, cur) => sum + cur);

			$scope.posDayPeriodPieChart.dataLabel.content = function (ht) {
				var dataLabel = "";

				if (ht.value > 0) {
					dataLabel = ht.name;
				}

				return dataLabel;
			}

			$scope.posDayPeriodPieChart.tooltip.content = function (ht) {
				var nameArr = ht.name.split(" - ");
				return nameArr[0] + "<br>" + nameArr[1] + "<br><br>" + (ht.value / $scope.sum * 100).toFixed(2) + "%";
			}
		}

		event.preventDefault();
	});

}]);