/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('storageHqCurrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storageHqCurrCtrl', $scope, $http, $timeout, true));

  $scope._setComboData("srchUnitFg", [
    {"name": messages["storageHqCurr.unitStockFg"], "value": "0"},
    {"name": messages["storageHqCurr.unitOrderFg"], "value": "1"}
  ]);

  $scope._setComboData("srchSafeStockFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["storageHqCurr.safeStockFg0"], "value": "0"}
  ]);

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("storageHqCurrListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	$scope.isDisplay = false;
	// 접속 사용자의 권한
	$scope.orgnFg = gvOrgnFg;

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storageHqCurrCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "currQty" && s.cells.getCellData(e.row, e.col,false) != null) { // 전체현재고
          	wijmo.addClass(e.cell, 'wijLink');
          	wijmo.addClass(e.cell, 'wj-custom-readonly');
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
        var params       = {};
        	if (col.binding === "currQty") { // 현재고
    			params.prodCd	 = selectedRow.prodCd;
    			params.prodNm	 = selectedRow.prodNm;
    			params.hqOfficeCd	= $("#hqOfficeCd").val();
    		    params.storeCd		= $("#storeCd").val();
    			$scope._broadcast('hqCurrDtlCtrl', params);
            }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storageHqCurrCtrl", function (event, data) {
	$scope.getReStorageNmList(true, true); //(동적 그리드 그리기, 페이징)

  });

  //다른 컨트롤러의 broadcast 받기(페이징 초기화)
  $scope.$on("storageHqCurrCtrlSrch", function (event, data) {
	$scope.getReStorageNmList(true, false); //(동적 그리드 그리기, 페이징)

  });

  // 창고별현재고현황 리스트 조회
  $scope.searchstorageHqCurrList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.storeCd   = $("#storageHqCurrSelectStoreCd").val();
    params.storageCd = $("#storageHqCurrSelectStorageCd").val();
    params.vendrCd   = $("#storageHqCurrSelectVendrCd").val();
    params.safeStockFg = $scope.safeStockFg;
    params.prodCd	 = $scope.prodCd;
    params.prodNm	 = $scope.prodNm;
    params.barcdCd	 = $scope.barcdCd;
    params.prodClassCd	 = $scope.prodClassCd;
    params.unitFg 	 = $scope.unitFg;
    params.isPageChk = isPageChk;
    params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수

	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/stock/curr/storageHqCurr/storageHqCurr/list.sb", params, function() {});
  };

  // 상품분류정보 팝업
  $scope.popUpProdClass = function () {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope          = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd    = scope.getSelectedClass();
        var params         = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
          function (response) {
            $scope.prodClassCd   = prodClassCd;
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
  };

  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storageHqCurrSelectVendrShow = function () {
    $scope._broadcast('storageHqCurrSelectVendrCtrl');
  };

  //상품분류 항목표시 함수
  $scope.fnDisplay = function() {
	  var grid = wijmo.Control.getControl("#storageHqCurrGrid");
      var columns = grid.columns;
      var length  = grid.columns.length;
      var isDisplay  = $scope.isDisplay;
	  if(isDisplay){
		  for(var i=0; i<length; i++){
			  if(columns[i].binding.substring(0,2) == 'lv'){
    			  columns[i].visible = true;
    		  }
          }
	  }else{
		  for(var i=0; i<length; i++){
			  if(columns[i].binding.substring(0,2) == 'lv'){
    			  columns[i].visible = false;
    		  }
          }
	  }
  };

  //엑셀 다운로드
  $scope.excelDownloadStorageHqCurr = function () {
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
      },  '재고현황_창고별현재고현황_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };


	//창고 리스트 조회
	$scope.getReStorageNmList = function (gridSet, isPageChk) {
		var url = "/stock/curr/storageHqCurr/storageHqCurr/storageList.sb";
	    var params = {};
	    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
          params['sid'] = document.getElementsByName('sessionId')[0].value;
       }

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
	    			var arrStorageCd = [];
	    			var arrStorageNm = [];

	    			for (var i = 0; i < list.length; i++) {
	    				arrStorageCd.push(list[i].storageCd);
	    				arrStorageNm.push(list[i].storageNm);
	    			}

	    			$("#storageHqCurrSelectStorageCd").val(arrStorageCd.join());
	    			$("#storageHqCurrSelectStorageName").val(arrStorageNm.join());

	    			storageCd = $("#storageHqCurrSelectStorageCd").val();
	    			storageNm = $("#storageHqCurrSelectStorageName").val();
	    			if(gridSet){
	    				$scope.makeDataGrid();
	    			}
	    			// 그리드 리스트 조회
	    			$scope.searchstorageHqCurrList(isPageChk);
	    		}
	    	}
	    }, function errorCallback(response) {
	      $scope._popMsg(messages["cmm.error"]);
	      return false;
	    }).then(function () {

	    });
	  };

	  $scope.makeDataGrid = function () {
		  var grid = wijmo.Control.getControl("#storageHqCurrGrid");

		  var arrStorageCd = storageCd.split(',');
		  var arrStorageNm = storageNm.split(',');
		  var colLength = grid.columns.length;
		  var addLength = arrStorageCd.length;
		  if($scope.orgnFg == "H"){
			  if(colLength-6 > 11){
				  for(var i=12; i<colLength-6; i++){
			          grid.columns.removeAt(12);
				  }
			  }
		  }else{
			  if(colLength-4 > 12){
				  for(var i=13; i<colLength-4; i++){
			          grid.columns.removeAt(13);
				  }
			  }
		  }
		  if (arrStorageCd != "") {
			  for(var i = 1; i < arrStorageCd.length + 1; i++) {
				  var colValue = arrStorageCd[i-1];
				  var colName = arrStorageNm[i-1];
				  if($scope.orgnFg == "H"){
					  grid.columns.insert(12+(i-1), new wijmo.grid.Column({header: colName+messages["storageHqCurr.currQty"], binding: 'currQty'+colValue, width: 100, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));
				  }else{
					  grid.columns.insert(13+(i-1), new wijmo.grid.Column({header: colName+messages["storageHqCurr.currQty"], binding: 'currQty'+colValue, width: 100, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));
				  }
			  }
		  }

		  grid.itemFormatter = function (panel, r, c, cell) {

			  if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
				  //align in center horizontally and vertically
				  panel.rows[r].allowMerging    = true;
				  panel.columns[c].allowMerging = true;

				  wijmo.setCss(cell, {
					  display : 'table',
					  tableLayout : 'fixed'
				  });

				  cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

				  wijmo.setCss(cell.children[0], {
					  display : 'table-cell',
					  verticalAlign : 'middle',
					  textAlign : 'center'
				  });
			  } else if (panel.cellType === wijmo.grid.CellType.RowHeader) { // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
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
			  } else if (panel.cellType === wijmo.grid.CellType.Cell) { // readOnly 배경색 표시
				  var col = panel.columns[c];
				  if (col.isReadOnly) {
					  wijmo.addClass(cell, 'wj-custom-readonly');
				  }
			  }

		  }

		  $scope.flex.refresh();

		  // 기능수행 종료 : 반드시 추가
		  event.preventDefault();
	  }
}]);