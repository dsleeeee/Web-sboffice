/**
 * get application
 */
var app = agrid.getApp();

/** 일자별상품 상세현황 controller */
app.controller('versusPeriodClassCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('versusPeriodClassCtrl', $scope, $http, true));

  // 조회일자 세팅
  $scope.srchStartDate = wcombo.genDateVal("#srchClassStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchClassEndDate", gvEndDate);

  // 1년 전 날짜 세팅
  var date = new Date();
  date.setFullYear(date.getFullYear()-1);
  var result = date.getFullYear() + '' + date.getMonth() + 1 + '' + date.getDate();

  // 대비일자 세팅
  $scope.srchCompStartDate = wcombo.genDateVal("#compClassStartDate", result);
  $scope.srchCompEndDate   = wcombo.genDateVal("#compClassEndDate", result);

  // 콤보박스 데이터 Set
  $scope._setComboData('versusPeriodClasslistScaleBox', gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	//조회조건 콤보박스 데이터 Set
	$scope.getBrandCdList();

	s.allowMerging = 'AllHeaders';
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    s.refresh();

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("versusPeriodClassCtrl");


    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.lv1Nm  = messages["versusPeriod.category"];
    dataItem.lv2Nm  = messages["versusPeriod.category"];
    dataItem.lv3Cd  = messages["versusPeriod.category"];
    dataItem.lv3Nm  = messages["versusPeriod.category"];
    dataItem.realSaleAmtA       = messages["versusPeriod.period"];
    dataItem.saleCntA      = messages["versusPeriod.period"];
    dataItem.realSaleAmtB   = messages["versusPeriod.comp"];
    dataItem.saleCntB   = messages["versusPeriod.comp"];
    dataItem.sinAmt   = messages["versusPeriod.sin"];
    dataItem.sinCnt   = messages["versusPeriod.sin"];

    s.columnHeaders.rows[0].dataItem = dataItem;

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //align in center horizontally and vertically
    	panel.allowMerging = 'All';
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

/*    s.columnHeaders.allowMerging = true;
    s.rowHeaders.allowMerging = true;

    s.columnHeaders.setCellData(0,0,"Country");
    s.columnHeaders.setCellData(0,1,"Country");
    s.columnHeaders.setCellData(0,2,"Country");

    s.columnHeaders.setCellData(1,0,"Country");
    s.columnHeaders.setCellData(1,1,"Country");
    s.columnHeaders.setCellData(1,2,"Country");*/

    s.refresh();

    // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = $scope.params;
        	params.prodClassCd   = selectedRow.lv3Cd;

        if (col.binding === "lv3Nm") { // 3단계 분류
          $scope._broadcast('versusPeriodClassDtlCtrlSrch', params);
        }
      }
    });
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("versusPeriodClassCtrl", function (event, data) {
    if ($("#versusPeriodClassSelectStoreCd").val() === '') {
         $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
         return false;
    }

    $scope.searchProdDayList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품매출순위 리스트 조회
  $scope.searchProdDayList = function () {

    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.compStartDate = wijmo.Globalize.format($scope.srchCompStartDate.value, 'yyyyMMdd');
    params.compEndDate = wijmo.Globalize.format($scope.srchCompEndDate.value, 'yyyyMMdd');
    params.storeCd   = $("#versusPeriodClassSelectStoreCd").val();
    params.brandCd = $scope.brandCd;

    $scope.params = params;

    if(params.startDate > params.endDate || params.compStartDate > params.compEndDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }

    // 조회일자와 대비일자 설정기간이 동일한지 유효성 체크
    if($scope.dateDiff(params.startDate, params.endDate) !== $scope.dateDiff(params.compStartDate, params.compEndDate)){
   	 	$scope._popMsg(messages["versusPeriod.dateDiff"]);
   	 	return false;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/anals/versusPeriod/class/versusPeriodClassList.sb", params);

    var days = "(" + $scope.dateDiff(params.startDate, params.endDate) + "일)\n";
    var srchStartToEnd = "(" + $scope.srchStartDate.text + " ~ " + $scope.srchEndDate.text + ")";
    var compStartToEnd = "(" + $scope.srchCompStartDate.text + " ~ " + $scope.srchCompEndDate.text + ")";

    var grid = wijmo.Control.getControl("#versusPeriodClassGrid").columnHeaders.rows[0].dataItem;
    grid.realSaleAmtA = messages["versusPeriod.period"] + (days + srchStartToEnd);
    grid.saleCntA = messages["versusPeriod.period"] + (days + srchStartToEnd);
    grid.realSaleAmtB = messages["versusPeriod.comp"] + (days + compStartToEnd);
    grid.saleCntB = messages["versusPeriod.comp"] + (days + compStartToEnd);

    //create a group to show the grand totals
    var grpLv1 = new wijmo.collections.PropertyGroupDescription('전체');
    var grpLv2 = new wijmo.collections.PropertyGroupDescription('lv1Nm');
    var grpLv3 = new wijmo.collections.PropertyGroupDescription('lv2Nm');

    var theGrid = new wijmo.Control.getControl('#versusPeriodClassGrid');

    theGrid.itemsSource = new wijmo.collections.CollectionView();
    // custom cell calculation
    theGrid.formatItem.addHandler(function(s, e) {

    	var lengthTemp = s.collectionView.groupDescriptions.length;

    	if (lengthTemp < 3) {
    		s.collectionView.groupDescriptions.push(grpLv1);
        	s.collectionView.groupDescriptions.push(grpLv2);
        	s.collectionView.groupDescriptions.push(grpLv3);
    	}

    	s.rows.forEach(function(row) {
    		if(row instanceof wijmo.grid.GroupRow){
    			var groupProp=row.dataItem.groupDescription.propertyName;
    			var className=null;
    			switch(groupProp){
    				case "전체":className="grp-lv-1";break;
    				case "lv1Nm":className="grp-lv-2";break;
    				case "lv2Nm":className="grp-lv-3";break;
    			}

    			if(className){
    				row.cssClass=className;
    			}
    		}
    	});

    	// 그리드 링크 효과, 정렬 효과
        s.formatItem.addHandler(function (s, e) {
          if (e.panel === s.cells) {
            var col = s.columns[e.col];
            if (col.binding === "lv3Nm") { // 3단계 분류
              	wijmo.addClass(e.cell, 'wijLink');
            }
            else if (col.binding === "realSaleAmtA") { // 조회기간 실매출
              	wijmo.addClass(e.cell, 'wj-align-right');
            }
            else if (col.binding === "saleCntA") { // 조회기간 판매수량
              	wijmo.addClass(e.cell, 'wj-align-center');
            }
            else if (col.binding === "realSaleAmtB") { // 대비기간 실매출
              	wijmo.addClass(e.cell, 'wj-align-right');
            }
            else if (col.binding === "saleCntB") { // 대비기간 판매수량
              	wijmo.addClass(e.cell, 'wj-align-center');
            }
            else if (col.binding === "sinAmt") { // 신장률 실매출
              	wijmo.addClass(e.cell, 'wj-align-center');
            }
            else if (col.binding === "sinCnt") { // 신장률 판매수량
              	wijmo.addClass(e.cell, 'wj-align-center');
            }

          }
        });


    });

    // start collapsed
    theGrid.collapseGroupsToLevel(1);
    theGrid.collectionView.refresh();

    //메인그리드 조회후 상세그리드 조회.
	$scope.loadedRows = function(sender, args){
    	var rows = sender.rows;
    	var params		 = {};

    	if(rows.length != 0) {
    		params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    	    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    	    params.compStartDate = wijmo.Globalize.format($scope.srchCompStartDate.value, 'yyyyMMdd');
    	    params.compEndDate = wijmo.Globalize.format($scope.srchCompEndDate.value, 'yyyyMMdd');
    	    params.storeCd = $("#versusPeriodClassSelectStoreCd").val();
    	    params.brandCd = rows[0].dataItem.lv3Cd;
	    }
		else {
			params.storeCd   = -1;
		}

	    // 코너별 매출현황 상세조회.
	    $scope._broadcast("versusPeriodClassDtlCtrlSrch", params);
	}

  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.versusPeriodClassSelectStoreShow = function () {
    $scope._broadcast('versusPeriodClassSelectStoreCtrl');
  };


  //매장의 브랜드 리스트 조회
  $scope.getBrandCdList = function () {
    var url             = '';
    var comboParams     = {};
    comboParams.brandCd = $("#brandCd").val();
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "brandCd", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
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
          //$scope.arrTblCol = response.data.data.page.arrTblCol;

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
            $scope.comboArray = comboArray;
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
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, 'versusPeriodClass.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

  //두개의 날짜를 비교하여 차이를 알려준다.
  $scope.dateDiff = function(_date1, _date2) {

	    var date1 = [_date1.slice(0, 4), "-", _date1.slice(4, 6), "-", _date1.slice(6, 8)].join('');
	    var date2 = [_date2.slice(0, 4), "-", _date2.slice(4, 6), "-", _date2.slice(6, 8)].join('');

	    var diffDate_1 = date1 instanceof Date ? date1 : new Date(date1);
	    var diffDate_2 = date2 instanceof Date ? date2 : new Date(date2);

	    diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
	    diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());

	    var diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
	    diff = Math.ceil(diff / (1000 * 3600 * 24));

	    return diff + 1;
	};
}]);

/** 일자별상품 상세현황 controller */
app.controller('versusPeriodClassDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('versusPeriodClassDtlCtrl', $scope, $http, true));

  // 콤보박스 데이터 Set
  $scope._setComboData('versusPeriodClassDtllistScaleBox', gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("versusPeriodClassDtlCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.prodNm      = messages["versusPeriod.prod"];
    dataItem.realSaleAmtA       = messages["versusPeriod.period"];
    dataItem.saleCntA      = messages["versusPeriod.period"];
    dataItem.realSaleAmtB   = messages["versusPeriod.comp"];
    dataItem.saleCntB   = messages["versusPeriod.comp"];
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

  };

  var params = '';
  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("versusPeriodClassDtlCtrl", function (event, data) {

	  $scope.searchVersusPeriodClassDtlList(true, data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.$on("versusPeriodClassDtlCtrlSrch", function (event, data) {

	  $scope.searchVersusPeriodClassDtlList(false, data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 주간대비 리스트 조회
  $scope.searchVersusPeriodClassDtlList = function (isPageChk, data) {

	  if(data != undefined && data.storeCd != -1){
		  $scope.startDate = data.startDate;
		  $scope.endDate   = data.endDate;
		  $scope.compStartDate = data.compStartDate;
		  $scope.compEndDate   = data.compEndDate;
		  $scope.storeCd   = data.storeCd;
		  $scope.brandCd   = data.brandCd;
		  $scope.prodClassCd   = data.prodClassCd;

		  $scope.srchStartDate = [data.startDate.slice(0, 4), "-", data.startDate.slice(4, 6), "-", data.startDate.slice(6, 8)].join('');
		  $scope.srchEndDate = [data.endDate.slice(0, 4), "-", data.endDate.slice(4, 6), "-", data.endDate.slice(6, 8)].join('');
		  $scope.srchCompStartDate = [data.compStartDate.slice(0, 4), "-", data.compStartDate.slice(4, 6), "-", data.compStartDate.slice(6, 8)].join('');
		  $scope.srchCompEndDate = [data.compEndDate.slice(0, 4), "-", data.compEndDate.slice(4, 6), "-", data.compEndDate.slice(6, 8)].join('');
	  }

	  var params       = {};
	  params.startDate = $scope.startDate;
	  params.endDate = $scope.endDate;
	  params.compStartDate = $scope.compStartDate;
	  params.compEndDate = $scope.compEndDate;
	  params.storeCd   = $scope.storeCd;
	  params.brandCd = $scope.brandCd;
	  params.prodClassCd = $scope.prodClassCd;
      params.isPageChk = isPageChk;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/anals/versusPeriod/class/versusPeriodClassDtlList.sb", params, function() {});
    $scope.flex.refresh();

    var days = "(" + $scope.dateDiff(params.startDate, params.endDate) + "일)\n";
    var srchStartToEnd = "(" + $scope.srchStartDate + " ~ " + $scope.srchEndDate + ")";
    var compStartToEnd = "(" + $scope.srchCompStartDate + " ~ " + $scope.srchCompEndDate + ")";

    var grid = wijmo.Control.getControl("#versusPeriodClassDtlGrid").columnHeaders.rows[0].dataItem;
    grid.realSaleAmtA    = messages["versusPeriod.period"]+ (days + srchStartToEnd);
    grid.saleCntA = messages["versusPeriod.period"]+ (days + srchStartToEnd);
    grid.realSaleAmtB       = messages["versusPeriod.comp"] + (days + compStartToEnd);
    grid.saleCntB      = messages["versusPeriod.comp"] + (days + compStartToEnd);
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
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, 'versusPeriodClassDtl.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

  //두개의 날짜를 비교하여 차이를 알려준다.
  $scope.dateDiff = function(_date1, _date2) {
	  var diff;

	  if(_date1 != undefined && _date2 != undefined ){
	    var date1 = [_date1.slice(0, 4), "-", _date1.slice(4, 6), "-", _date1.slice(6, 8)].join('');
	    var date2 = [_date2.slice(0, 4), "-", _date2.slice(4, 6), "-", _date2.slice(6, 8)].join('');

	    var diffDate_1 = date1 instanceof Date ? date1 : new Date(date1);
	    var diffDate_2 = date2 instanceof Date ? date2 : new Date(date2);

	    diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
	    diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());

	    diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
	    diff = Math.ceil(diff / (1000 * 3600 * 24));


	  }
	  return diff + 1;
	}

}]);