/**
 * get application
 */
var app = agrid.getApp();

/** 주문대비 입고현황 그리드 controller */
app.controller('monthIostockCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('monthIostockCtrl', $scope, $http, true));

	$scope.excelFg = false;
	$scope.isChecked = false;

	  // 접속 사용자의 권한
	  $scope.orgnFg = gvOrgnFg;

	  //조회조건 단위구분 데이터 Set
	  $scope._setComboData("srchUnitFgDisplay", [
	    {"name": messages["monthIostock.unitStockFg"], "value": "0"}, // 재고단위
	    {"name": messages["monthIostock.unitOrderFg"], "value": "1"} // 주문단위
	  ]);

	  //조회조건 조회옵션 데이터 Set
	  $scope._setComboData("srchOptionDisplay", [
		{"name": messages["monthIostock.Qty"] + "+" + messages["monthIostock.Amt"], "value": "3"}, // 수량+금액
	    {"name": messages["monthIostock.Qty"], "value": "1"}, // 수량
	    {"name": messages["monthIostock.Amt"], "value": "2"} // 금액
	  ]);

	  //거래처선택 모듈 팝업 사용시 정의
	  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	  $scope.monthIostockSelectVendrShow = function () {
	    $scope._broadcast('monthIostockSelectVendrCtrl');
	  };

	  // 상품분류정보 팝업
	  $scope.popUpProdClass = function() {
	    var popUp = $scope.prodClassPopUpLayer;
	    popUp.show(true, function (s) {
	      // 선택 버튼 눌렀을때만
	      if (s.dialogResult === "wj-hide-apply") {
	        var scope = agrid.getScope('prodClassPopUpCtrl');
	        var prodClassCd = scope.getSelectedClass();
	        var params = {};
	        params.prodClassCd = prodClassCd;
	        // 조회 수행 : 조회URL, 파라미터, 콜백함수
	        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
	          function(response){
	            $scope.prodClassCd = prodClassCd;
	            $scope.prodClassCdNm = response.data.data;
	          }
	        );
	      }
	    });
	  };

	  // 상품분류정보 선택취소
	  $scope.delProdClass = function(){
	    $scope.prodClassCd = "";
	    $scope.prodClassCdNm = "";
	  }


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

	//상품분류 항목표시 함수
	  $scope.isChkDt = function() {
		  var grid = wijmo.Control.getControl("#monthIostockMainGrid");
	      var columns = grid.columns;
	      var length  = grid.columns.length-1;
	      var isChecked = $scope.isChecked;
		  if(isChecked){
			  for(var i=0; i<length; i++){
				  if(columns[i].binding == 'prodClassNm'){
	    			  columns[i].visible = true;
	    		  }
	          }
		  }else{
			  for(var i=0; i<length; i++){
				  if(columns[i].binding == 'prodClassNm'){
	    			  columns[i].visible = false;
	    		  }
	          }
		  }
	  };
}]);


/** 주문대비 입고현황 그리드 controller */
app.controller('monthIostockMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('monthIostockMainCtrl', $scope, $http, true));

  //조회조건 콤보박스 listScale 세팅
  $scope._setComboData("monthIostockMainlistScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("monthIostockMainCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var colLength = col.binding.length;
        if (col.binding === "prodCd" || (col.binding.slice(-3) == 'Qty' && s.cells.getCellData(e.row,e.col,false) != null && col.binding !== "poUnitQty" && col.binding !== "setInQty" && col.binding !== "closeQty")) { // 상품코드, 각 수량별
    		wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
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
        var colLength 	= col.binding.length;
        var selectedRow = s.rows[ht.row].dataItem;
        var params      = {};
	        params.prodCd    = selectedRow.prodCd;
	        params.prodNm    = selectedRow.prodNm;
	        params.poUnitQty = selectedRow.poUnitQty;
	        params.startDate = selectedRow.startDate + "01";
            params.endDate   = selectedRow.startDate + "31";
			params.storeCd   = $("#storeCd").val();
			params.storeNm   = $("#storeNm").val();
        if (col.binding === "prodCd") { // 상품코드

  		  	$scope._broadcast('prodCodeDtlCtrl', params);
		}else if(col.binding.slice(-3) == 'Qty' && selectedRow[col.binding] != null && col.binding !== "poUnitQty" && col.binding !== "setInQty" && col.binding !== "closeQty"){ //각 수량별
            var colCode = col.binding;
        	params.colCode = colCode; // 수량(컬럼 뒤에 붙는 숫자, 어떤 수량인지 구분)
        	params.ioOccrFg = s.columnHeaders.getCellData(0,ht.col,false);

  		  	$scope._broadcast('prodQtyDtlCtrl', params);
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
	  prodClassNm		: messages["monthIostock.prodClassNm"],

	  prodCd 		: messages["monthIostock.prodCd"],
	  prodNm 		: messages["monthIostock.prodNm"],
	  poUnitQty		: messages["monthIostock.poUnitQty"],
	  poUnitFgNm	: messages["monthIostock.poUnitFg"],
	  barcdCd		: messages["monthIostock.barcdCd"],

	  baseQty				:messages["monthIostock.basicStock"],
	  baseAmt				:messages["monthIostock.basicStock"],
	  storeInQty	:messages["monthIostock.accStoreIn"],
	  storeInTot	:messages["monthIostock.accStoreIn"],
	  storeOutQty	:messages["monthIostock.accStoreOut"],
	  storeOutTot	:messages["monthIostock.accStoreOut"],
	  purchsInQty	:messages["monthIostock.accPurchsIn"],
	  purchsInTot	:messages["monthIostock.accPurchsIn"],
	  purchsOutQty	:messages["monthIostock.accPurchsOut"],
	  purchsOutTot	:messages["monthIostock.accPurchsOut"],
	  storeSaleQty	:messages["monthIostock.accStoreSale"],
	  storeSaleTot	:messages["monthIostock.accStoreSale"],

	  moveInQty		:messages["monthIostock.accStoreMoveIn"],
	  moveInTot		:messages["monthIostock.accStoreMoveIn"],
      moveOutQty	:messages["monthIostock.accStoreMoveOut"],
      moveOutTot	:messages["monthIostock.accStoreMoveOut"],
      disuseQty		:messages["monthIostock.accDisuse"],
      adjQty 		:messages["monthIostock.accAdj"],
      setInQty		:messages["monthIostock.accSetIn"],

	  closeQty		:messages["monthIostock.endingStock"],
	  closeAmt		:messages["monthIostock.endingStock"],
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
  $scope.$on("monthIostockMainCtrl", function (event, data) {
    $scope.searchMonthIostockList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("monthIostockMainCtrlSrch", function (event, data) {
    $scope.searchMonthIostockList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 월수불현황 리스트 조회
  $scope.searchMonthIostockList = function (isPageChk) {
    $scope.searchedStartDate = wijmo.Globalize.format($scope.startDate, 'yyyyMM');

    // 파라미터
    var params         = {};
    params.startDate   = $scope.searchedStartDate;
    params.endDate     = $scope.searchedStartDate;
    params.prodCd	   = $scope.srchProdCd;
    params.prodNm	   = $scope.srchProdNm;
    params.barcdCd	   = $scope.srchBarcdCd;
    params.vendrCd 	   = $("#monthIostockSelectVendrCd").val();
    params.prodClassCd = $scope.prodClassCd;
    params.unitFg 	   = $scope.unitFg;
    params.isPageChk   = isPageChk;
    params.listScale = $scope.listScaleCombo.text;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/status/monthIoStock/prod/viewList.sb", params, function () {
    	$scope.displayChg($scope.srchOption);
    });
	  $scope.excelFg = true;
  };


  //조회옵션 함수
  $scope.displayChg = function (srchOption) {
	  var check = srchOption;
	  var grid = wijmo.Control.getControl("#monthIostockMainGrid");
      var columns = grid.columns;
      var length  = grid.columns.length-1;

      if(check == '1'){ // 수량
    	  for(var i=6; i<length; i++){
    		  if(columns[i].binding != 'poUnitQty'){
    			  if(columns[i].binding.slice(-3) == 'Tot' || columns[i].binding.slice(-3) == 'Amt'){
        			  columns[i].visible = false;
        		  }else if(columns[i].binding.slice(-3) == 'Qty'){
        			  columns[i].visible = true;
        		  }
    		  }
          }
      }else if(check == '2'){ // 금액
    	  for(var i=6; i<length; i++){
    		  if(columns[i].binding != 'poUnitQty'){
    			  if(columns[i].binding.slice(-3) == 'Qty'){
        			  columns[i].visible = false;
        		  }else if(columns[i].binding.slice(-3) == 'Tot' || columns[i].binding.slice(-3) == 'Amt'){
        			  columns[i].visible = true;
        		  }
    		  }
          }
      }else{ //수량 + 금액
    	  for(var i=0; i<length; i++){
			  if(columns[i].binding != 'prodClassNm' && columns[i].binding != 'poUnitQty'){
    			  columns[i].visible = true;
    		  }
          }
      }
  }

  //엑셀 다운로드
  $scope.excelDownloadMonthIostock = function () {
	  // 파라미터
	  var params     		= {};
	  params.startDate		= $scope.searchedStartDate;
	  params.endDate  		= $scope.searchedStartDate;
	  params.prodCd	   		= $scope.srchProdCd;
	  params.prodNm	   		= $scope.srchProdNm;
	  params.barcdCd	   	= $scope.srchBarcdCd;
	  params.vendrCd 	   	= $("#monthIostockSelectVendrCd").val();
	  params.prodClassCd 	= $scope.prodClassCd;
	  params.unitFg 	   	= $scope.unitFg;

	  $scope._broadcast('monthIostockExcelCtrl', params);
  };
}]);


/** 주문대비 입고현황 그리드 controller */
app.controller('monthIostockExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('monthIostockExcelCtrl', $scope, $http, true));

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {

		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');

		// 헤더머지
		s.allowMerging = 2;
		s.columnHeaders.rows.push(new wijmo.grid.Row());
		s.columnHeaders.rows[0].dataItem = {
			prodClassNm: messages["monthIostock.prodClassNm"],

			prodCd: messages["monthIostock.prodCd"],
			prodNm: messages["monthIostock.prodNm"],
			poUnitQty: messages["monthIostock.poUnitQty"],
			poUnitFgNm: messages["monthIostock.poUnitFg"],
			barcdCd: messages["monthIostock.barcdCd"],

			baseQty: messages["monthIostock.basicStock"],
			baseAmt: messages["monthIostock.basicStock"],
			vendrInQty: messages["monthIostock.accVendrIn"],
			vendrInTot: messages["monthIostock.accVendrIn"],
			vendrOutQty: messages["monthIostock.accVendrOut"],
			vendrOutTot: messages["monthIostock.accVendrOut"],
			hqOutQty: messages["monthIostock.hqOut"],
			hqOutTot: messages["monthIostock.hqOut"],
			hqInQty: messages["monthIostock.hqIn"],
			hqInTot: messages["monthIostock.hqIn"],

			storeMoveInQty: messages["monthIostock.accStoreMoveIn"],
			storeMoveInTot: messages["monthIostock.accStoreMoveIn"],
			storeMoveOutQty: messages["monthIostock.accStoreMoveOut"],
			storeMoveOutTot: messages["monthIostock.accStoreMoveOut"],
			disuseQty: messages["monthIostock.accDisuse"],
			adjQty: messages["monthIostock.accAdj"],
			setInQty: messages["monthIostock.accSetIn"],

			saleVendrOrderQty: messages["monthIostock.accSaleVendrOut"],
			saleVendrOrderTot: messages["monthIostock.accSaleVendrOut"],
			saleVendrRtnQty: messages["monthIostock.accSaleVendrIn"],
			saleVendrRtnTot: messages["monthIostock.accSaleVendrIn"],

			closeQty: messages["monthIostock.endingStock"],
			closeAmt: messages["monthIostock.endingStock"],
		}

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
	$scope.$on("monthIostockExcelCtrl", function (event, data) {

		if(data != undefined && $scope.excelFg) {
			if($scope.excelStartDate > $scope.excelEndDate){
				$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
				return false;
			}
			$scope.searchMonthIostockExcelList(data);
		}else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});

	// 주문대비 입고현황 리스트 조회
	$scope.searchMonthIostockExcelList = function (data) {

		// 파라미터
		var params         = {};
		params.startDate	= data.startDate
		params.endDate  	= data.endDate
		params.prodCd		= data.prodCd
		params.prodNm		= data.prodNm
		params.barcdCd	   	= data.barcdCd
		params.vendrCd 	   	= data.vendrCd
		params.prodClassCd 	= data.prodClassCd
		params.unitFg 	   	= data.unitFg

		$scope.displayChg();
		$scope.isChkDt();


		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/stock/status/monthIoStock/prod/viewExcelList.sb", params, function () {
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
				}, '재고관리_재고현황_월수불현황_' +'_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
			}, 10);
		});
	};

	$scope.isChkDt = function() {
		var grid = wijmo.Control.getControl("#monthIostockMainExcelGrid");
		var columns = grid.columns;
		var length  = grid.columns.length;
		var isChecked = $scope.isChecked;
		if(isChecked){
			for(var i=0; i<length; i++){
				if(columns[i].binding === 'prodClassNm'){
					columns[i].visible = true;
				}
			}
		}else{
			for(var i=0; i<length; i++){
				if(columns[i].binding === 'prodClassNm'){
					columns[i].visible = false;
				}
			}
		}
	};


	//조회옵션 함수
	$scope.displayChg = function () {
		var check = $scope.excelSrchOption;
		var grid = wijmo.Control.getControl("#monthIostockMainExcelGrid");
		var columns = grid.columns;
		var length  = grid.columns.length;

		if(check == '1'){ // 수량
			for(var i=6; i<length; i++){
				var colLength = columns[i].binding.length;
				if(columns[i].binding.slice(-3) == 'Tot'){
					columns[i].visible = false;
				}else if(columns[i].binding.slice(-3) == 'Qty'){
					columns[i].visible = true;
				}
			}
		}else if(check == '2'){ // 금액
			for(var i=6; i<length; i++){
				var colLength = columns[i].binding.length;
				if(columns[i].binding != 'poUnitQty'){
					if(columns[i].binding.slice(-3) == 'Qty'){
						columns[i].visible = false;
					}else if(columns[i].binding.slice(-3) == 'Tot'){
						columns[i].visible = true;
					}
				}
			}
		}else{ //수량 + 금액
			for(var i=0; i<length; i++){
				if(columns[i].binding != 'prodClassNm'){
					columns[i].visible = true;
				}
			}
		}
	}

}]);

