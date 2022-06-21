/**
 * get application
 */
var app = agrid.getApp();

/** 일별종합 controller */
app.controller('dayTotalCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayTotalCtrl', $scope, $http, true));

  $scope.srchStartDate = wcombo.genDateVal("#srchDayTotalStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchDayTotalEndDate", gvEndDate);
  $scope.orgnFg        = gvOrgnFg;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dayTotalCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.saleDate    = messages["day.dayTotal.saleDate"];
    dataItem.yoil        = messages["day.dayTotal.yoil"];
    dataItem.storeCnt    = messages["day.dayTotal.storeCnt"];
    dataItem.storeCd     = messages["day.dayTotal.storeCd"];
    dataItem.totSaleAmt  = messages["day.dayTotal.saleInfo"];
    dataItem.totDcAmt    = messages["day.dayTotal.saleInfo"];
    dataItem.realSaleAmt = messages["day.dayTotal.saleInfo"];
    dataItem.billCnt     = messages["day.dayTotal.saleInfo"];
    dataItem.billUprc    = messages["day.dayTotal.saleInfo"];
    dataItem.gaAmt       = messages["day.dayTotal.saleInfo"];
    dataItem.vatAmt      = messages["day.dayTotal.saleInfo"];
    dataItem.totTipAmt   = messages["day.dayTotal.totTipAmt"];
    dataItem.totEtcAmt   = messages["day.dayTotal.totEtcAmt"];
    dataItem.totPayAmt   = messages["day.dayTotal.payMethod"];
    dataItem.genRealSaleAmt   = messages["day.dayTotal.dlvrPack"];
    dataItem.genRealSaleRate   = messages["day.dayTotal.dlvrPack"];
    dataItem.dlvrRealSaleAmt   = messages["day.dayTotal.dlvrPack"];
    dataItem.dlvrRealSaleRate   = messages["day.dayTotal.dlvrPack"];
    dataItem.packRealSaleAmt   = messages["day.dayTotal.dlvrPack"];
    dataItem.packRealSaleRate   = messages["day.dayTotal.dlvrPack"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["day.dayTotal.payMethod"];
    }
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


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dayTotalCtrl", function (event, data) {
    $scope.searchDayTotalList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 일별종합 리스트 조회
  $scope.searchDayTotalList = function () {
    $scope.searchedStoreCd = $("#dayTotalSelectStoreCd").val();
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $scope.searchedStoreCd;
    params.payCol    = payCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/day/day/dayCashTotal/list.sb", params);
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dayTotalSelectStoreShow = function () {
    $scope._broadcast('dayTotalSelectStoreCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownloadInfo = function () {

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        includeColumns: function (column) {
          return column.visible;
        }
      },
          messages["day.dayCash"] + '(' + messages["day.dayTotal"] + ')_' + getCurDateTime() +'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
