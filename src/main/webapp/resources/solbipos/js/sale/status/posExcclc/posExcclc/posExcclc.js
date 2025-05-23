/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('posExcclcCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('posExcclcCtrl', $scope, $http, $timeout, true));

  $scope.srchPosExcclcStartDate = wcombo.genDateVal("#srchPosExcclcStartDate", getToday());
  $scope.srchPosExcclcEndDate   = wcombo.genDateVal("#srchPosExcclcEndDate", getToday());
  
  $scope.isSearch = false;

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("posExcclcListScaleBox", gvListScaleBoxData);
  $scope._setComboData("posExcclcListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	$scope.getCloseFgNmList();

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("posExcclcCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;

        if (col.binding === "closeFgNm") { // 수량합계
          	wijmo.addClass(e.cell, 'wijLink');
          	wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
        if(item.closeFg !== "2" && item.regSeq === "00") {
            wijmo.addClass(e.cell, 'red');
        }
      }
    });

    // 선택 매장
    $scope.selectedHq;
    $scope.setSelectedHq = function(hq) {
      $scope.selectedHq = hq;
    };
    $scope.getSelectedHq  = function(){
      return $scope.selectedHq;
    };

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
        var col = ht.panel.columns[ht.col];
        if (col.binding === "closeFgNm") {//마감구분
        	var selectedRow = s.rows[ht.row].dataItem;
            $scope.openDtlLayer(selectedRow);
            console.log(selectedRow);
        }
      }
    }, true);

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem			= {};
    dataItem.storeCd		= messages["posExcclc.storeCd"];
    dataItem.storeNm		= messages["posExcclc.storeNm"];
    dataItem.saleDate		= messages["posExcclc.saleDate"];
    dataItem.posNo			= messages["posExcclc.posNo"];
    dataItem.closeFgNm		= messages["posExcclc.closeFg"];
    dataItem.openDate	    = messages["posExcclc.openDate"];
    dataItem.closeDate	    = messages["posExcclc.closeDate"];
    dataItem.totSaleAmt		= messages["posExcclc.sale"];
    dataItem.totDcAmt		= messages["posExcclc.sale"];
    dataItem.realSaleAmt	= messages["posExcclc.sale"];
    dataItem.cashExactAmt	= messages["posExcclc.sale"];
    dataItem.cashBillSaleAmt    = messages["posExcclc.sale"];
    dataItem.fundAmt		= messages["posExcclc.posFundAmt"];
    dataItem.accntInAmt		= messages["posExcclc.inOut"];
    dataItem.accntOutAmt	= messages["posExcclc.inOut"];
    dataItem.cashTicketAmt	= messages["posExcclc.cashTicketAmt"];
    dataItem.lostAmt		= messages["posExcclc.cashLostAmt"];

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
    // <-- //그리드 헤더2줄 -->
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("posExcclcCtrl", function (event, data) {
	$scope.comboArray =  $scope.comboArrayForSrc;
    $scope.searchPosExcclcList(true);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기(페이징 초기화)
  $scope.$on("posExcclcCtrlSrch", function (event, data) {
	$scope.comboArray =  $scope.comboArrayForSrc;
    $scope.searchPosExcclcList(false);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //조회조건 마감구분 리스트 조회
  $scope.getCloseFgNmList = function () {
    var url             = ''
    var comboParams     = {};
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchCloseFgDisplay", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
  };

  //상세 화면 열기
  $scope.openDtlLayer = function(selectedRow){
	$scope.selectedSample = {};
	openDtlLayer(selectedRow);
    $scope.posExcclcDetailLayer.show(true, function(selectedRow){
    	//openDtlLayer(selectedRow);
    });
  };

  // POS 정산내역 리스트 조회
  $scope.searchPosExcclcList = function (isPageChk) {

//	if ($("#posExcclcSelectStoreCd").val() === '') {
//		$scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
//		return false;
//	}

      var startDt = new Date(wijmo.Globalize.format($scope.srchPosExcclcStartDate.value, 'yyyy-MM-dd'));
      var endDt = new Date(wijmo.Globalize.format($scope.srchPosExcclcEndDate.value, 'yyyy-MM-dd'));
      var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

      // 시작일자가 종료일자보다 빠른지 확인
      if(startDt.getTime() > endDt.getTime()){
          $scope._popMsg(messages['cmm.dateChk.error']);
          $("div.prodRankLayer").hide();
          return false;
      }

      // 조회일자 최대 1년(365일) 제한
      if (diffDay > 365) {
          $scope._popMsg(messages['cmm.dateOver.1year.error']);
          $("div.prodRankLayer").hide();
          return false;
      }

    // 파라미터
    var params       = {};
    params.storeCd   = $("#posExcclcSelectStoreCd").val();
    params.closeFg   = $scope.closeFgModel;
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    params.isAll 	 = $scope.isAll;
    
    $scope.excelStoreCd   = params.storeCd;
    $scope.execelCloseFg  = params.closeFg;
    $scope.excelListScale = params.listScale; //-페이지 스케일 갯수
    $scope.excelIsAll 	  = params.isAll;
    
    $scope.isSearch = true;
    
    $scope.excelStartDate = '';
    $scope.excelEndDate = '';

	//등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
	  params.startDate = wijmo.Globalize.format($scope.srchPosExcclcStartDate.value, 'yyyyMMdd');
	  params.endDate = wijmo.Globalize.format($scope.srchPosExcclcEndDate.value, 'yyyyMMdd');
	  
	  $scope.excelStartDate = params.startDate;
	  $scope.excelEndDate	= params.endDate;
	}

	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}

	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/posExcclc/posExcclc/list.sb", params);


  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchPosExcclcStartDate.isReadOnly = $scope.isChecked;
    $scope.srchPosExcclcEndDate.isReadOnly = $scope.isChecked;
  };
  // 전체 할인유형 체크박스 클릭이벤트
  $scope.totalCloseFg = function() {
	  var grid = wijmo.Control.getControl("#srchCloseFgDisplay");
	  grid.isReadOnly = $scope.isAll;;
  };

  //엑셀 다운로드
  $scope.excelDownloadDay = function () {

      var startDt = new Date(wijmo.Globalize.format($scope.srchPosExcclcStartDate.value, 'yyyy-MM-dd'));
      var endDt = new Date(wijmo.Globalize.format($scope.srchPosExcclcEndDate.value, 'yyyy-MM-dd'));
      var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

      // 시작일자가 종료일자보다 빠른지 확인
      if(startDt.getTime() > endDt.getTime()){
          $scope._popMsg(messages['cmm.dateChk.error']);
          $("div.prodRankLayer").hide();
          return false;
      }

      // 조회일자 최대 1년(365일) 제한
      if (diffDay > 365) {
          $scope._popMsg(messages['cmm.dateOver.1year.error']);
          $("div.prodRankLayer").hide();
          return false;
      }

	  // 파라미터
	  var params     = {};
	  $scope._broadcast('posExcclcExcelCtrl',params);
  };

  //조회조건 마감조건 리스트 조회
  $scope.getDcNmList = function () {
    var comboParams     = {};
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchDcDcfgDisplay", null, '', comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
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

	  var listCd       = ["3","1","2"];
	  var listNm       = ["일마감", "개점", "중간마감"];
      var comboArray = [];
      var comboArrayForSrc = [];
      var comboData  = {};

      if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
        comboArray = [];
        /*if (option === "A") {
          comboData.name  = messages["cmm.all"];
          comboData.value = "";
          comboArray.push(comboData);
        } else if (option === "S") {
          comboData.name  = messages["cmm.select"];
          comboData.value = "";
          comboArray.push(comboData);
        }*/
        for (var i = 0; i < listCd.length; i++) {
          comboData       = {};
          comboData.name  = listNm[i]
          comboData.value = listCd[i]
          comboArray.push(comboData);
          comboArrayForSrc.push(comboData);
        }
        $scope.comboArray = comboArrayForSrc; // 조회시 동적 컬럼생성을 위한 코너정보 저장('전체' 제외).
        $scope.comboArrayForSrc =  comboArrayForSrc;
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

  };

}]);

app.controller('posExcclcExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('posExcclcExcelCtrl', $scope, $http, $timeout, true));

	var checkInt = true;

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {

		$scope.getCloseFgNmList();

	    // 선택 매장
	    $scope.selectedHq;
	    $scope.setSelectedHq = function(hq) {
	      $scope.selectedHq = hq;
	    };
	    $scope.getSelectedHq  = function(){
	      return $scope.selectedHq;
	    };

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	    // <-- 그리드 헤더2줄 -->
	    // 헤더머지
	    s.allowMerging = 2;
	    s.columnHeaders.rows.push(new wijmo.grid.Row());

	    // 첫째줄 헤더 생성
	    var dataItem			= {};
	    dataItem.storeCd		= messages["posExcclc.storeCd"];
	    dataItem.storeNm		= messages["posExcclc.storeNm"];
	    dataItem.saleDate		= messages["posExcclc.saleDate"];
	    dataItem.posNo			= messages["posExcclc.posNo"];
	    dataItem.closeFgNm		= messages["posExcclc.closeFg"];
        dataItem.openDate	    = messages["posExcclc.openDate"];
        dataItem.closeDate	    = messages["posExcclc.closeDate"];
	    dataItem.totSaleAmt		= messages["posExcclc.sale"];
	    dataItem.totDcAmt		= messages["posExcclc.sale"];
	    dataItem.realSaleAmt	= messages["posExcclc.sale"];
        dataItem.cashExactAmt	= messages["posExcclc.sale"];
        dataItem.cashBillSaleAmt	= messages["posExcclc.sale"];
	    dataItem.fundAmt		= messages["posExcclc.posFundAmt"];
	    dataItem.accntInAmt		= messages["posExcclc.inOut"];
	    dataItem.accntOutAmt	= messages["posExcclc.inOut"];
	    dataItem.cashTicketAmt	= messages["posExcclc.cashTicketAmt"];
	    dataItem.lostAmt		= messages["posExcclc.cashLostAmt"];

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
	    // <-- //그리드 헤더2줄 -->
	};
	
	//조회조건 마감구분 리스트 조회
	$scope.getCloseFgNmList = function () {
	  var url             = ''
	  var comboParams     = {};
	  // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
	  $scope._queryCombo("combo", "srchCloseFgDisplay", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("posExcclcExcelCtrl", function (event, data) {
		if(data != undefined && $scope.isSearch) {
			$scope.searchPosExcclcExcelList(true);
			// 기능수행 종료 : 반드시 추가
			event.preventDefault();
		} else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

	});
	
	$scope.searchPosExcclcList = function (isPageChk) {
	    // 파라미터
	    var params       = {};
	    params.storeCd   = $("#posExcclcSelectStoreCd").val();
	    params.closeFg   = $scope.closeFgModel;
	    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수
	    params.isPageChk = isPageChk;
	    params.isAll 	 = $scope.isAll;
	    
	    $scope.excelStoreCd   = params.storeCd;
	    $scope.execelCloseFg  = params.closeFg;
	    $scope.excelListScale = params.listScale; //-페이지 스케일 갯수
	    $scope.excelIsAll 	  = params.isAll;
	    
	    $scope.isSearch = true;

		//등록일자 '전체기간' 선택에 따른 params
		if(!$scope.isChecked){
		  params.startDate = wijmo.Globalize.format($scope.srchPosExcclcStartDate.value, 'yyyyMMdd');
		  params.endDate = wijmo.Globalize.format($scope.srchPosExcclcEndDate.value, 'yyyyMMdd');
		  
		  
		}

		if(params.startDate > params.endDate){
			 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
			 	return false;
		}

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/posExcclc/posExcclc/list.sb", params);


	  };

	// 전체 엑셀 리스트 조회
	$scope.searchPosExcclcExcelList = function (isPageChk) {// 파라미터
		// 파라미터
		var params     = {};
		params.storeCd   = $scope.excelStoreCd;
	    params.closeFg   = $scope.execelCloseFg;
	    params.listScale = $scope.excelListScale; //-페이지 스케일 갯수
	    params.isAll 	 = $scope.excelIsAll;
	    
	    params.startDate	= $scope.excelStartDate;
	    params.endDate		= $scope.excelEndDate;

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/sale/status/posExcclc/posExcclc/excelList.sb", params, function(){
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
			      }, '매출현황_POS정산내역_'+getToday()+'.xlsx', function () {
			        $timeout(function () {
			          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
			        }, 10);
			      });
			  }, 10);
		});
	};

}]);