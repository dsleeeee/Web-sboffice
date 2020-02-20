/**
 * get application
 */
var app = agrid.getApp();

/** 일자별(코너별 매출) controller */
app.controller('rtnStatusProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnStatusProdCtrl', $scope, $http, $timeout, true));

//조회조건 콤보박스 데이터 Set
  $scope._setComboData("rtnStatusProdListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("rtnStatusProdCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.lv1Nm    		= messages["rtnStatus.prodClassNm"];
    dataItem.lv2Nm  		= messages["rtnStatus.prodClassNm1"];
    dataItem.lv3Nm   		= messages["rtnStatus.prodClassNm2"];
    dataItem.prodCd 	    = messages["rtnStatus.prodCd"];
    dataItem.prodNm 	    = messages["rtnStatus.prodNm"];
    dataItem.barcdCd 	    = messages["rtnStatus.barcdCd"];
    dataItem.cnt 	        = messages["rtnStatus.rtnAmt"];
    dataItem.realSaleAmt 	= messages["rtnStatus.rtnAmt"];

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
  $scope.$on("rtnStatusProdCtrl", function (event, data) {
    $scope.searchRtnStatusProdList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
//다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnStatusProdCtrlSrch", function (event, data) {
    $scope.searchRtnStatusProdList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 코너별매출일자별 리스트 조회
  $scope.searchRtnStatusProdList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.storeCd   = $("#rtnStatusProdSelectStoreCd").val();
    params.listScale = $scope.cornerDayListScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk; //-페이징 초기화
    //등록일자 '전체기간' 선택에 따른 params
	if(!$scope.isChecked){
		params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
	//	    params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
	    params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyy-MM-dd');
	    params.endDate   = (params.endDate).split("-");
	    var endDay 		 = ( new Date(params.endDate[0],params.endDate[1], 0) ).getDate();
	    params.endDate 	 = params.endDate[0] + params.endDate[1] + endDay;
	}
	if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
	}
	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/rtnStatus/prod/list.sb", params);
  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.rtnStatusProdStartDateCombo.isReadOnly = $scope.isChecked;
    $scope.rtnStatusProdEndDateCombo.isReadOnly = $scope.isChecked;
  };

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.rtnStatusProdSelectStoreShow = function () {
    $scope._broadcast('rtnStatusProdSelectStoreCtrl');
  };

//엑셀 다운로드
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
      }, '매출현황_반품현황_상품별 반품현황_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);