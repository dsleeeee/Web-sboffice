/**
 * get application
 */
var app = agrid.getApp();

/** 주문대비 입고현황 그리드 controller */
app.controller('orderCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('orderCtrl', $scope, $http, true));

	$scope.srchStartDate = wcombo.genDateVal("#srchOrderStartDate", getToday());
	$scope.srchEndDate   = wcombo.genDateVal("#srchOrderEndDate", getToday());

	  //페이지스케일 콤보박스 데이터 Set
	  $scope._setComboData("orderMainListScaleBox", gvListScaleBoxData);
	  $scope._setComboData("orderDtlListScaleBox", gvListScaleBoxData);

	  //조회조건 출고일자 데이터 Set
	  $scope._setComboData("srchOrderOutDateFgDisplay", [
	    {"name": messages["orderStockInfo.outstockResveDate"], "value": "1"}, // 출고예약일자
	    {"name": messages["orderStockInfo.outDate"], "value": "-1"} // 출고일자
	  ]);

	  //조회조건 구분 데이터 Set
	  $scope._setComboData("srchOrderSlipFgDisplay", [
	    {"name": messages["cmm.all"], "value": ""}, // 전체
	    {"name": messages["orderStockInfo.out"], "value": "1"}, // 출고
	    {"name": messages["orderStockInfo.rtn"], "value": "-1"} // 반품
	  ]);

	  //매장선택 모듈 팝업 사용시 정의
	  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	  $scope.orderSelectStoreShow = function () {
		  $scope._broadcast('orderSelectStoreCtrl');
	  };

	// 그리드 전표구분
	  $scope.slipFgMap = new wijmo.grid.DataMap([
	    {id: "1", name: messages["vendrInstock.slipFgIn"]},
	    {id: "-1", name: messages["vendrInstock.slipFgRtn"]}
	  ], 'id', 'name');


	  //거래처선택 모듈 팝업 사용시 정의
	  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	  $scope.orderSelectVendrShow = function () {
	    $scope._broadcast('orderSelectVendrCtrl');
	  };


	  // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
	  // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
	  // comboId : combo 생성할 ID
	  // gridMapId : grid 에서 사용할 Map ID
	  // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
	  // params : 데이터 조회할 url에 보낼 파라미터
	  // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
	  // callback : queryCombo 후 callback 할 함수
	  $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
	    var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
	    if (url) {
	      comboUrl = url;
	    }

	    // ajax 통신 설정
	    $http({
	      method : 'POST', //방식
	      url    : comboUrl, /* 통신할 URL */
	      params : params, /* 파라메터로 보낼 데이터 */
	      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
	    }).then(function successCallback(response) {
	      if ($scope._httpStatusCheck(response, true)) {
	        if (!$.isEmptyObject(response.data.data.list)) {
	          var list       = response.data.data.list;
	          var comboArray = [];
	          var comboData  = {};

	          if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
	            comboArray = [];
	            if (option === "A") {
	              comboData.name  = messages["cmm.all"];
	              comboData.value = "";
	              comboArray.push(comboData);
	            } else if (option === "S") {
	              comboData.name  = messages["cmm.select"];
	              comboData.value = "";
	              comboArray.push(comboData);
	            }

	            for (var i = 0; i < list.length; i++) {
	              comboData       = {};
	              comboData.name  = list[i].nmcodeNm;
	              comboData.value = list[i].nmcodeCd;
	              comboArray.push(comboData);
	            }
	            $scope._setComboData(comboId, comboArray);
	          }

	          if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
	            comboArray = [];
	            for (var i = 0; i < list.length; i++) {
	              comboData      = {};
	              comboData.id   = list[i].nmcodeCd;
	              comboData.name = list[i].nmcodeNm;
	              comboArray.push(comboData);
	            }
	            $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
	          }
	        }
	      }
	    }, function errorCallback(response) {
	      $scope._popMsg(messages["cmm.error"]);
	      return false;
	    }).then(function () {
	      if (typeof callback === 'function') {
	        $timeout(function () {
	          callback();
	        }, 10);
	      }
	    });
	  };

	  //조회조건 진행상태
	  $scope._queryCombo("combo", "srchOrderProcFgDisplay", null, "/iostock/frnchs/order/srchOrderProcFg/list.sb", null, "A", null);
}]);


/** 주문대비 입고현황 그리드 controller */
app.controller('orderMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('orderMainCtrl', $scope, $http, true));

  $scope.excelFg = false;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("orderMainCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
        	var item = s.rows[e.row].dataItem;
        	if (item.slipNo !== "미생성") {
        		wijmo.addClass(e.cell, 'wijLink');
                wijmo.addClass(e.cell, 'wj-custom-readonly');
              }
        }
        // 구분이 반출이면 글씨색을 red 로 변경한다.
        if (col.binding === "slipFg") {
          var item = s.rows[e.row].dataItem;
          if (item.slipFg === -1) {
            wijmo.addClass(e.cell, 'red');
          }
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);

  	  if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
	  	var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
	  	if (rng && rng.columnSpan > 1) {
	  		e.preventDefault();
	  	}
	  }

      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "slipNo") { // 전표번호
          var params       = {};
          params.slipNo    = selectedRow.slipNo;
          params.excelFg = true;
          var item = s.rows[ht.row].dataItem;
      		if (item.slipNo !== "미생성") {
      			$scope._broadcast('orderDtlCtrlSrch', params);
      			$('#dtlSlipNo').text('상품상세 (전표번호 : '+selectedRow.slipNo+')');
            }
        }
      }
    }, true);

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      reqDateFm    	: messages["orderStockInfo.outstockResveDate"],
      storeNm 		: messages["orderStockInfo.storeNm"],
      slipFgNm   	: messages["orderStockInfo.slipFg"],
      procFgNm    	: messages["orderStockInfo.procFg"],
      slipNo    	: messages["orderStockInfo.slipNo"],
      outDtFm     	: messages["orderStockInfo.outDate"],
      inDtFm    	: messages["orderStockInfo.inDate"],
      dtlCnt		: messages["orderStockInfo.prodCnt"],
      orderTotQty  	: messages["orderStockInfo.order2"],
      orderTot  	: messages["orderStockInfo.order2"],
      outTotQty  	: messages["orderStockInfo.out"],
      outTot  		: messages["orderStockInfo.out"],
      inTotQty     	: messages["orderStockInfo.in"],
      inTot  		: messages["orderStockInfo.in"],
      penaltyAmt  	: messages["orderStockInfo.penaltyAmt"],
    };

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
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("orderMainCtrl", function (event, data) {
    $scope.searchOrderList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("orderMainCtrlSrch", function (event, data) {
    $scope.searchOrderList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 주문대비 입고현황 리스트 조회
  $scope.searchOrderList = function (isPageChk) {
    $scope.searchedStartDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    $scope.searchedEndDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

    // 파라미터
    var params       = {};
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    params.startDate = $scope.searchedStartDate;
    params.endDate   = $scope.searchedEndDate;
    params.storeCd	 = $("#orderSelectStoreCd").val();
    params.outDateFg = $scope.outDateFgModel;
    params.slipFg 	 = $scope.slipFgModel;
    params.procFg 	 = $scope.procFgModel;
    paramsisPageChk  = isPageChk;

    $scope.searchedStoreCd    = params.storeCd;
    $scope.searchedOutDateFg  = params.outDateFg;
    $scope.searchedslipFg     = params.slipFg;
    $scope.searchedProcFg     = params.procFg;


    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/frnchs/order/ioStock/list.sb", params, function () {});

	//메인그리드 조회후 상세그리드 조회.
    $scope.loadedRows = function(sender, args){
        var rows = sender.rows;
        var params       = {};
        if(rows.length > 0){
        	params.slipNo   = rows[0].dataItem.slipNo;
        	params.excelFg = true;
        	$('#dtlSlipNo').text('상품상세 (전표번호 : '+rows[0].dataItem.slipNo+')');
        }else{
        	$('#dtlSlipNo').text('');
        	params.slipNo = -1;
        	params.excelFg = false;
        }

        $scope._broadcast("orderDtlCtrlSrch", params);

        /*if(rows.length > 0){
	        if(rows[0].dataItem.slipNo != "미생성"){
	        	$scope._broadcast("orderDtlCtrlSrch", params);
	        }
        }*/
    }

    // 주문대비 입출고현황 그리드 조회 후 상세내역 그리드 초기화
    var orderDtlScope = agrid.getScope('orderDtlCtrl');
    orderDtlScope.dtlGridDefault();

    $scope.excelFg = true;
  };

  //엑셀 다운로드
  $scope.excelDownloadOrder = function () {
	// 파라미터
	var params = {};

    params.startDate   = $scope.searchedStartDate;
    params.endDate     = $scope.searchedEndDate;
    params.storeCd	   = $scope.searchedStoreCd;
    params.outDateFg   = $scope.searchedOutDateFg;
    params.slipFg 	   = $scope.searchedslipFg;
    params.procFg 	   = $scope.searchedProcFg;
    params.excelFg	   = $scope.excelFg;

    $scope._broadcast('orderExcelCtrl',params);
  };
}]);


/** 주문대비 입고현황 상세 그리드 controller */
app.controller('orderDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('orderDtlCtrl', $scope, $http, true));

  $scope.excelFg = false;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("orderDtlCtrl");

    // 그리드 링크 효과
//    s.formatItem.addHandler(function (s, e) {
//      if (e.panel === s.cells) {
//        var col = s.columns[e.col];
//
//        if (col.format === "date") {
//          e.cell.innerHTML = getFormatDate(e.cell.innerText);
//        } else if (col.format === "dateTime") {
//          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
//        }
//      }
//    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);

  	  if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
  	  	var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
  	  	if (rng && rng.columnSpan > 1) {
  	  		e.preventDefault();
  	  	}
  	  }
    }, true);

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      reqDateFm  : messages["orderStockInfo.outstockResveDate"],
      prodCd     : messages["orderStockInfo.dtl.prodCd"],
      prodNm     : messages["orderStockInfo.dtl.prodNm"],
      poUnitFgNm : messages["orderStockInfo.dtl.poUnitFg"],
      poUnitQty  : messages["orderStockInfo.dtl.poUnitQty"],
      splyUprc   : messages["orderStockInfo.dtl.outSplyUprc"],
      slipFgNm	 : messages["orderStockInfo.dtl.fg"],

      orderTotQty: messages["orderStockInfo.dtl.order2"],
      orderAmt   : messages["orderStockInfo.dtl.order2"],
      orderVat   : messages["orderStockInfo.dtl.order2"],
      orderTot   : messages["orderStockInfo.dtl.order2"],

      mdTotQty   : messages["orderStockInfo.dtl.dstb"],
      mdAmt      : messages["orderStockInfo.dtl.dstb"],
      mdVat      : messages["orderStockInfo.dtl.dstb"],
      mdTot  	 : messages["orderStockInfo.dtl.dstb"],

      outTotQty  : messages["orderStockInfo.dtl.out"],
      outAmt     : messages["orderStockInfo.dtl.out"],
      outVat     : messages["orderStockInfo.dtl.out"],
      outTot  	 : messages["orderStockInfo.dtl.out"],

      inTotQty   : messages["orderStockInfo.dtl.in"],
      inAmt      : messages["orderStockInfo.dtl.in"],
      inVat      : messages["orderStockInfo.dtl.in"],
      inTot  	 : messages["orderStockInfo.dtl.in"],

      penaltyAmt : messages["orderStockInfo.dtl.penaltyAmt"],
      remark	 : messages["orderStockInfo.dtl.remark"],
    };

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

  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("orderDtlCtrl", function (event, data) {
    $scope.searchOrderDtlList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("orderDtlCtrlSrch", function (event, data) {
    $scope.slipNo    = data.slipNo;
    $scope.inSlipNo  = data.inSlipNo;
    $scope.startDate = data.startDate;
    $scope.endDate   = data.endDate;
    $scope.excelFg = data.excelFg;

    $scope.searchOrderDtlList(false);


    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.displayChg = function () {
	  var check = $('input[name=displayFg]:checked').val();
	  var grid = wijmo.Control.getControl("#orderDtlGrid");
      var columns = grid.columns;
      var length  = grid.columns.length;

      if(check == 'all'){
    	  for(var i=0; i<length; i++){
    		  if(columns[i].binding != 'poUnitFg' && columns[i].binding != 'reqDate'){
    			  columns[i].visible = true;
    		  }
          }
      }else if(check == 'cntSum'){
    	  for(var i=0; i<length; i++){
    		  var colLength = columns[i].binding.length;
    		  if(columns[i].binding != 'penaltyAmt'){
    			  if(columns[i].binding.substring(colLength,colLength-3) == 'Amt'||columns[i].binding.substring(colLength,colLength-3) == 'Vat'){
        			  columns[i].visible = false;
        		  }
    		  }
          }
      }
  }


  // 주문대비 입고현황 상세 리스트 조회
  $scope.searchOrderDtlList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    params.slipNo    = $scope.slipNo;
    params.inSlipNo  = $scope.inSlipNo;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.isPageChk = isPageChk;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/frnchs/order/ioStockDtl/list.sb", params);

    //$scope.excelFg = true;
  };


  // 상세 그리드 초기화
  $scope.dtlGridDefault = function () {
    $timeout(function () {
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;
      $scope.flex.refresh();
    }, 10);
  };

  //엑셀 다운로드
  $scope.excelDownloadOrderDtl = function () {
	// 파라미터
	var params = {};
    params.startDate   = $scope.startDate;
    params.endDate     = $scope.endDate;
    params.slipNo      = $scope.slipNo;
    params.inSlipNo    = $scope.inSlipNo;
    params.checked	   = $('input[name=displayFg]:checked').val();
    params.excelFg	   = $scope.excelFg;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
//    $scope._inquirySub("/iostock/frnchs/order/ioStockDtl/list.sb", params);

//    $scope.excelFg = true;

    $scope._broadcast('orderDtlExcelCtrl',params);
  };
}]);



/** 주문대비 입고현황 엑셀리스트 controller */
app.controller('orderExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('orderExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
        	var item = s.rows[e.row].dataItem;
        	if (item.slipNo !== "미생성") {
        		wijmo.addClass(e.cell, 'wijLink');
                wijmo.addClass(e.cell, 'wj-custom-readonly');
              }
        }
        // 구분이 반출이면 글씨색을 red 로 변경한다.
        if (col.binding === "slipFg") {
          var item = s.rows[e.row].dataItem;
          if (item.slipFg === -1) {
            wijmo.addClass(e.cell, 'red');
          }
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      reqDateFm    	: messages["orderStockInfo.outstockResveDate"],
      storeNm 		: messages["orderStockInfo.storeNm"],
      slipFgNm   	: messages["orderStockInfo.slipFg"],
      procFgNm    	: messages["orderStockInfo.procFg"],
      slipNo    	: messages["orderStockInfo.slipNo"],
      outDtFm     	: messages["orderStockInfo.outDate"],
      inDtFm    	: messages["orderStockInfo.inDate"],
      dtlCnt		: messages["orderStockInfo.prodCnt"],
      orderTotQty  	: messages["orderStockInfo.order2"],
      orderTot  	: messages["orderStockInfo.order2"],
      outTotQty  	: messages["orderStockInfo.out"],
      outTot  		: messages["orderStockInfo.out"],
      inTotQty     	: messages["orderStockInfo.in"],
      inTot  		: messages["orderStockInfo.in"],
      penaltyAmt  	: messages["orderStockInfo.penaltyAmt"],
    };

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
  };



  //다른 컨트롤러의 broadcast 받기
  $scope.$on("orderExcelCtrl", function (event, data) {
	  if(data != undefined && data.excelFg) {
		  $scope.startDate   = data.startDate;
		  $scope.endDate     = data.endDate;
		  $scope.storeCd	 = data.storeCd;
		  $scope.outDateFg   = data.outDateFg;
		  $scope.slipFg 	 = data.slipFg;
		  $scope.procFg 	 = data.procFg;
		  $scope.excelFg     = data.excelFg;

		  $scope.searchOrderExcelList(false);
	  }else{
		  $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
		  return false;
	  }
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 주문대비 입고현황 리스트 조회
  $scope.searchOrderExcelList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.storeCd	 = $scope.storeCd;
    params.outDateFg = $scope.outDateFg;
    params.slipFg 	 = $scope.slipFg;
    params.procFg 	 = $scope.procFg;
    paramsisPageChk  = isPageChk;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/frnchs/order/ioStock/excelList.sb", params, function () {
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
			}, '본사-매장간 입출고현황_주문대비 입출고현황_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
    });
  };
}]);


/** 주문대비 입고현황 상세 엑셀리스트 controller */
app.controller('orderDtlExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('orderDtlExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);

  	  if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
  	  	var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
  	  	if (rng && rng.columnSpan > 1) {
  	  		e.preventDefault();
  	  	}
  	  }
    }, true);

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      reqDateFm  : messages["orderStockInfo.outstockResveDate"],
      prodCd     : messages["orderStockInfo.dtl.prodCd"],
      prodNm     : messages["orderStockInfo.dtl.prodNm"],
      poUnitFgNm : messages["orderStockInfo.dtl.poUnitFg"],
      poUnitQty  : messages["orderStockInfo.dtl.poUnitQty"],
      splyUprc   : messages["orderStockInfo.dtl.outSplyUprc"],
      slipFgNm	 : messages["orderStockInfo.dtl.fg"],

      orderTotQty: messages["orderStockInfo.dtl.order2"],
      orderAmt   : messages["orderStockInfo.dtl.order2"],
      orderVat   : messages["orderStockInfo.dtl.order2"],
      orderTot   : messages["orderStockInfo.dtl.order2"],

      mdTotQty   : messages["orderStockInfo.dtl.dstb"],
      mdAmt      : messages["orderStockInfo.dtl.dstb"],
      mdVat      : messages["orderStockInfo.dtl.dstb"],
      mdTot  	 : messages["orderStockInfo.dtl.dstb"],

      outTotQty  : messages["orderStockInfo.dtl.out"],
      outAmt     : messages["orderStockInfo.dtl.out"],
      outVat     : messages["orderStockInfo.dtl.out"],
      outTot  	 : messages["orderStockInfo.dtl.out"],

      inTotQty   : messages["orderStockInfo.dtl.in"],
      inAmt      : messages["orderStockInfo.dtl.in"],
      inVat      : messages["orderStockInfo.dtl.in"],
      inTot  	 : messages["orderStockInfo.dtl.in"],

      penaltyAmt : messages["orderStockInfo.dtl.penaltyAmt"],
      remark	 : messages["orderStockInfo.dtl.remark"],
    };

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

  };

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("orderDtlExcelCtrl", function (event, data) {
	  if(data != undefined && data.excelFg) {
		  $scope.slipNo    = data.slipNo;
		  $scope.inSlipNo  = data.inSlipNo;
		  $scope.startDate = data.startDate;
		  $scope.endDate   = data.endDate;
		  $scope.checked   = data.checked
		  $scope.excelFg   = data.excelFg;

		  $scope.searchOrderDtlExcelList(false);

	  }else{
		  $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
		  return false;
	  }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.displayChg = function () {
	  var check = $scope.checked;
	  var grid = wijmo.Control.getControl("#orderDtlExcelGrid");
      var columns = grid.columns;
      var length  = grid.columns.length;

      if(check == 'all'){
    	  for(var i=0; i<length; i++){
    		  if(columns[i].binding != 'poUnitFg' && columns[i].binding != 'reqDate'){
    			  columns[i].visible = true;
    		  }
          }
      }else if(check == 'cntSum'){
    	  for(var i=0; i<length; i++){
    		  var colLength = columns[i].binding.length;
    		  if(columns[i].binding != 'penaltyAmt'){
    			  if(columns[i].binding.substring(colLength,colLength-3) == 'Amt'||columns[i].binding.substring(colLength,colLength-3) == 'Vat'){
        			  columns[i].visible = false;
        		  }
    		  }
          }
      }
  }


  // 주문대비 입고현황 상세 엑셀 리스트 조회
  $scope.searchOrderDtlExcelList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.slipNo    = $scope.slipNo;
    params.inSlipNo  = $scope.inSlipNo;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    //params.isPageChk = isPageChk;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/frnchs/order/ioStockDtl/excelList.sb", params, function () {
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
			}, '본사-매장간 입출고현황_주문대비 입출고현황 상세_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
    });
    $scope.displayChg();
  };
}]);

