/**
 * get application
 */
var app = agrid.getApp();

/** 승인 controller */
app.controller('storeMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeMomsCtrl', $scope, $http, true));

  $scope.srchStartDate  = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate    = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "yoil") {
          if(item.yoil === "토") {
            wijmo.addClass(e.cell, 'blue');
          } else if(item.yoil === "일") {
            wijmo.addClass(e.cell, 'red');
          }
        }
      }
    });

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storeMomsCtrl");

    //Grid Header 2줄 - START	----------------------------------------------------------------
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    //첫째줄 Header 생성
    var dataItem = {};
    dataItem.branchNm         = messages["storeMoms.branchNm"];
    dataItem.storeCd          = messages["storeMoms.storeCd"];
    dataItem.storeNm          = messages["storeMoms.storeNm"];
    dataItem.billCntIn        = messages["storeMoms.billCnt"];
    dataItem.billCntPack      = messages["storeMoms.billCnt"];
    dataItem.billCntDlvr      = messages["storeMoms.billCnt"];
    dataItem.billCntTot       = messages["storeMoms.billCnt"];
    dataItem.billUprcIn       = messages["storeMoms.billUprc"];
    dataItem.billUprcPack     = messages["storeMoms.billUprc"];
    dataItem.billUprcDlvr     = messages["storeMoms.billUprc"];
    dataItem.billUprcTot      = messages["storeMoms.billUprc"];
    dataItem.totGuestCnt      = messages["storeMoms.totGuestCnt"];
    dataItem.guestUprc        = messages["storeMoms.guestUprc"];
    dataItem.saleCnt          = messages["storeMoms.saleCnt"];
    dataItem.totSaleAmt       = messages["storeMoms.totSaleAmt"];
    dataItem.realSaleAmtIn    = messages["storeMoms.realSaleAmt"];
    dataItem.realSaleAmtPack  = messages["storeMoms.realSaleAmt"];
    dataItem.realSaleAmtDlvr  = messages["storeMoms.realSaleAmt"];
    dataItem.realSaleAmtTot   = messages["storeMoms.realSaleAmt"];
    dataItem.totDcAmt         = messages["storeMoms.totDcAmt"];
    s.columnHeaders.rows[0].dataItem = dataItem;
    //Grid Header 2줄 - END		----------------------------------------------------------------

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
        panel.rows   [r].allowMerging	= true;
        panel.columns[c].allowMerging	= true;

        wijmo.setCss(cell, {
              display    : 'table',
              tableLayout: 'fixed'
            }
        );

        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

        wijmo.setCss(cell.children[0], {
              display      : 'table-cell',
              verticalAlign: 'middle',
              textAlign    : 'center'
            }
        );
      }
      else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
        if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
          cell.textContent = '';
        } else {
          if (!isEmpty(panel._rows[r]._data.rnum)) {
            cell.textContent = (panel._rows[r]._data.rnum).toString();
          } else {
            cell.textContent = (r + 1).toString();
          }
        }
      }
      else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
        var col = panel.columns[c];
        if (col.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }	//s.itemFormatter = function (panel, r, c, cell) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeMomsCtrl", function (event, data) {
    $scope.searchStoreMomsList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchStoreMomsList = function () {

    // 파라미터
    var params       = {};
    params.storeCds   = $("#storeMomsStoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.listScale = 500;
    
    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/store/storeMoms/storeMoms/getStoreMomsList.sb", params);
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeMomsStoreShow = function () {
    $scope._broadcast('storeMomsStoreCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    var params = {};
    params.storeCds   = $("#storeMomsStoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

    $scope._broadcast('storeMomsExcelCtrl',params);
  }
}]);


app.controller('storeMomsExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeMomsExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    //Grid Header 2줄 - START	----------------------------------------------------------------
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    //첫째줄 Header 생성
    var dataItem = {};
    dataItem.branchNm         = messages["storeMoms.branchNm"];
    dataItem.storeCd          = messages["storeMoms.storeCd"];
    dataItem.storeNm          = messages["storeMoms.storeNm"];
    dataItem.billCntIn        = messages["storeMoms.billCnt"];
    dataItem.billCntPack      = messages["storeMoms.billCnt"];
    dataItem.billCntDlvr      = messages["storeMoms.billCnt"];
    dataItem.billCntTot       = messages["storeMoms.billCnt"];
    dataItem.billUprcIn       = messages["storeMoms.billUprc"];
    dataItem.billUprcPack     = messages["storeMoms.billUprc"];
    dataItem.billUprcDlvr     = messages["storeMoms.billUprc"];
    dataItem.billUprcTot      = messages["storeMoms.billUprc"];
    dataItem.totGuestCnt      = messages["storeMoms.totGuestCnt"];
    dataItem.guestUprc        = messages["storeMoms.guestUprc"];
    dataItem.saleCnt          = messages["storeMoms.saleCnt"];
    dataItem.totSaleAmt       = messages["storeMoms.totSaleAmt"];
    dataItem.realSaleAmtIn    = messages["storeMoms.realSaleAmt"];
    dataItem.realSaleAmtPack  = messages["storeMoms.realSaleAmt"];
    dataItem.realSaleAmtDlvr  = messages["storeMoms.realSaleAmt"];
    dataItem.realSaleAmtTot   = messages["storeMoms.realSaleAmt"];
    dataItem.totDcAmt         = messages["storeMoms.totDcAmt"];
    s.columnHeaders.rows[0].dataItem = dataItem;
    //Grid Header 2줄 - END		----------------------------------------------------------------

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
        panel.rows   [r].allowMerging	= true;
        panel.columns[c].allowMerging	= true;

        wijmo.setCss(cell, {
              display    : 'table',
              tableLayout: 'fixed'
            }
        );

        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

        wijmo.setCss(cell.children[0], {
              display      : 'table-cell',
              verticalAlign: 'middle',
              textAlign    : 'center'
            }
        );
      }
      else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
        if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
          cell.textContent = '';
        } else {
          if (!isEmpty(panel._rows[r]._data.rnum)) {
            cell.textContent = (panel._rows[r]._data.rnum).toString();
          } else {
            cell.textContent = (r + 1).toString();
          }
        }
      }
      else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
        var col = panel.columns[c];
        if (col.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }	//s.itemFormatter = function (panel, r, c, cell) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeMomsExcelCtrl", function (event, data) {
    $scope.searchExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (data) {
    // 파라미터
    var params       = {};
    params.storeCds = data.storeCds;
    params.startDate = data.startDate;
    params.endDate = data.endDate;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/store/storeMoms/storeMoms/getStoreMomsExcelList.sb", params, function() {
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
        }, messages["storeMomsMoms.storeMomsMoms"]+getToday()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);