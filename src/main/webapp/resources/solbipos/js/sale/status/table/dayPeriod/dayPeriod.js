/**
 * get application
 */
var app = agrid.getApp();


/** 테이블별 설정기간 리스트 그리드 controller */
app.controller('tableDayPeriodCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('tableDayPeriodCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
	// comboBox 초기화
	$scope._setComboData("listScaleBox", gvListScaleBoxData);
	// picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("tableDayPeriodCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "realSaleAmt") { //실매출
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
        var params      = {};
        params.storeCd  = $scope.storeCd;
        params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
        params.tblCd   = selectedRow.tblCd;

        if (col.binding === "realSaleAmt") { //실매출 클릭
          $scope._broadcast('saleComTableCtrl', params);
        }
      }
    });

    // 헤더 컬럼 2개 머지
    s.allowMerging = 'ColumnHeaders';
    s.columnHeaders.rows[0].allowMerging = true;
    s.columnHeaders.setCellData(0,1,"테이블");
    s.columnHeaders.setCellData(0,2,"테이블");
  }

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("tableDayPeriodCtrl", function (event, data) {

	$scope.searchTableDayList();
    event.preventDefault();

  });

  // 테이블별 설정기간 리스트 리스트 조회
  $scope.searchTableDayList = function (s, e) {
	if ($("#tableDayPeriodSelectStoreCd").val() === '') {
	    $scope._popMsg(messages["todayDtl.require.selectStore"]); // 매장을 선택해주세요.
	    return false;
	}
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
    params.storeCd   = $("#tableDayPeriodSelectStoreCd").val();
    params.listScale = $scope.listScale;

    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/table/dayperiod/list.sb", params);

  };

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.tableDayPeriodSelectStoreShow = function () {
    $scope._broadcast('tableDayPeriodSelectStoreCtrl');
  };

//	엑셀 다운로드
  $scope.excelDownloadClass = function () {
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
      }, 'tableDayPeriod.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
