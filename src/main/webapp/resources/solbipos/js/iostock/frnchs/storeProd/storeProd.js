/**
 * get application
 */
var app = agrid.getApp();
var prodNoEnvFg = "";
/** 분류별상품 상세현황 controller */
app.controller('frnchsStoreProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('frnchsStoreProdCtrl', $scope, $http, true));

  // 조회일자 세팅
  $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", getToday());
  $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", getToday());
  $scope.orgnFg = gvOrgnFg;

  // 콤보박스 데이터 Set
  $scope._setComboData('frnchsStoreProdlistScaleBox', gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("frnchsStoreProdCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.storeCd  = messages["frnchsStore.storeCd"];
    dataItem.storeNm  = messages["frnchsStore.storeNm"];
    dataItem.lv1Nm  = messages["frnchsStoreProd.lv1Nm"];
    dataItem.lv2Nm  = messages["frnchsStoreProd.lv2Nm"];
    dataItem.lv3Nm  = messages["frnchsStoreProd.lv3Nm"];
    dataItem.prodCd  = messages["frnchsStoreProd.prodCd"];
    dataItem.prodNm  = messages["frnchsStoreProd.prodNm"];
    dataItem.poUnitFgNm  = messages["frnchsStoreProd.poUnitFg"];
    dataItem.poUnitQty       = messages["frnchsStoreProd.poUnitQty"];
    dataItem.outCnt  = messages["frnchsStoreProd.outCnt"];
    dataItem.outTotQty      = messages["frnchsStoreProd.in"];
    dataItem.outTot   = messages["frnchsStoreProd.in"];
    dataItem.inTotQty   = messages["frnchsStoreProd.out"];
    dataItem.inTot   = messages["frnchsStoreProd.out"];
    dataItem.penaltyAmt   = messages["frnchsStoreProd.penaltyAmt"];

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

    // 그리드 링크 효과
	s.formatItem.addHandler(function (s, e) {
		if (e.panel === s.cells) {
			var col = s.columns[e.col];
			if (col.binding === "prodCd"){ // 상품코드
				wijmo.addClass(e.cell, 'wijLink');
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
	            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
	            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
            	params.storeCd   = selectedRow.storeCd;
        	    params.prodCd    = selectedRow.prodCd;

            if (col.binding === "prodCd") { // 상품코드
              $scope._broadcast('frnchsStoreProdDtlCtrl', params);
            }

          }

	});

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("frnchsStoreProdCtrl", function (event, data) {
    $scope.searchFrnchsStoreProdList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("frnchsStoreProdCtrlSrch", function (event, data) {
    $scope.searchFrnchsStoreProdList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장-상품별입출고내역 리스트 조회
  $scope.searchFrnchsStoreProdList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $("#frnchsStoreProdSelectStoreCd").val();
    params.prodCd    = $("#srchProdCd").val();
    params.prodNm    = $("#srchProdNm").val();
    params.orgnFg    = $scope.orgnFg;
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/frnchs/storeprod/storeprod/frnchsStoreProdList.sb", params, function() {});

  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.frnchsStoreProdSelectStoreShow = function () {
    $scope._broadcast('frnchsStoreProdSelectStoreCtrl');
  };

  //상품분류정보 팝업
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
  $scope.delFrnchsStoreProd = function(){
    $scope.prodClassCd = "";
    $scope.prodClassCdNm = "";
  }

  // 엑셀 다운로드
  $scope.excelDownloadClass = function () {
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
      }, '본사매장간입출고내역_매장별입출고내역_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
}]);
