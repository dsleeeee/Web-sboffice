/****************************************************************
 *
 * 파일명 : dayCashSaleBenson.js
 * 설  명 : 벤슨 > 매출현황2 > 일별(현금)현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.10     김유승      1.0            최초작성 (매장코드/매장명 컬럼 추가, 매장별 행 분리)
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 일별(현금)현황 탭 controller */
app.controller('dayCashSaleBensonCtrl', ['$scope', function ($scope) {
    $scope.init = function () {
        $("#dayTotalViewBenson").show();
    };

    // 일별종합 탭 보이기
    $scope.dayTotalShow = function () {
        $("#dayTotalTabBenson").addClass("on");

        $("#dayTotalViewBenson").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dayCashTotalBensonCtrl");
        scope.flex.refresh();
    };
}]);

/** 일별종합(현금) controller */
app.controller('dayCashTotalBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayCashTotalBensonCtrl', $scope, $http, true));

  $scope.srchStartDate = wcombo.genDateVal("#srchDayTotalStartDateBenson", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchDayTotalEndDateBenson", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dayCashTotalBensonCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.storeCd     = messages["cmm.storeCd"];
    dataItem.storeNm     = messages["cmm.storeNm"];
    dataItem.saleDate    = messages["dayCashSaleBenson.dayTotal.saleDate"];
    dataItem.yoil        = messages["dayCashSaleBenson.dayTotal.yoil"];
    dataItem.totSaleAmt  = messages["dayCashSaleBenson.dayTotal.saleInfo"];
    dataItem.totDcAmt    = messages["dayCashSaleBenson.dayTotal.saleInfo"];
    dataItem.realSaleAmt = messages["dayCashSaleBenson.dayTotal.saleInfo"];
    dataItem.billCnt     = messages["dayCashSaleBenson.dayTotal.saleInfo"];
    dataItem.billUprc    = messages["dayCashSaleBenson.dayTotal.saleInfo"];
    dataItem.gaAmt       = messages["dayCashSaleBenson.dayTotal.saleInfo"];
    dataItem.vatAmt      = messages["dayCashSaleBenson.dayTotal.saleInfo"];
    dataItem.totTipAmt   = messages["dayCashSaleBenson.dayTotal.totTipAmt"];
    dataItem.totEtcAmt   = messages["dayCashSaleBenson.dayTotal.totEtcAmt"];
    dataItem.totPayAmt   = messages["dayCashSaleBenson.dayTotal.payMethod"];
    dataItem.genRealSaleAmt   = messages["dayCashSaleBenson.dayTotal.dlvrPack"];
    dataItem.genRealSaleRate   = messages["dayCashSaleBenson.dayTotal.dlvrPack"];
    dataItem.dlvrRealSaleAmt   = messages["dayCashSaleBenson.dayTotal.dlvrPack"];
    dataItem.dlvrRealSaleRate   = messages["dayCashSaleBenson.dayTotal.dlvrPack"];
    dataItem.packRealSaleAmt   = messages["dayCashSaleBenson.dayTotal.dlvrPack"];
    dataItem.packRealSaleRate   = messages["dayCashSaleBenson.dayTotal.dlvrPack"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["dayCashSaleBenson.dayTotal.payMethod"];
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
  $scope.$on("dayCashTotalBensonCtrl", function (event, data) {
    $scope.searchDayTotalList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 일별종합(현금) 리스트 조회
  $scope.searchDayTotalList = function () {

    var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      s_alert.pop(messages['cmm.dateChk.error']);
      return false;
    }

    // 조회일자 최대 1년(365일) 제한
    if (diffDay > 365) {
      s_alert.pop(messages['cmm.dateOver.1year.error']);
      return false;
    }

    $scope.searchedStoreCd = $("#dayTotalSelectStoreBensonCd").val();
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $scope.searchedStoreCd;
    params.payCol    = payCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/benson/dayCashSaleBenson/dayCashSaleBenson/getDayCashTotalBensonList.sb", params);
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
          messages["dayCashSaleBenson.dayCash"] + '(' + messages["dayCashSaleBenson.dayTotal"] + ')_' + getCurDateTime() +'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
