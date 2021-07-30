/****************************************************************
 *
 * 파일명 : dlvrFgSaleDtlPop.js
 * 설  명 : 내점/배달/포장현황 - 매출상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.28     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매출상세 팝업 그리드 생성
 */

app.controller('dlvrFgSaleDtlPopCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrFgSaleDtlPopCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.packFgDataMap = new wijmo.grid.DataMap(packFg, 'value', 'name');

        if(hqOfficeCd === 'A0001'){ //배달경로(상품별-배달포장구분 hq/ms 113)
            $scope.dlvrInFgDataMap = new wijmo.grid.DataMap(comboFgData, 'value', 'name');
        } else {
            $scope.dlvrInFgDataMap = new wijmo.grid.DataMap(dlvrInFg, 'value', 'name');  // 배달경로(상품별-배달포장구분 CM 112)
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
        var dataItem          = {};
        dataItem.storeCd = messages["dlvrFg.storeCd"];
        dataItem.storeNm = messages["dlvrFg.storeNm"];
        dataItem.prodCd = messages["dlvrFg.prodCd"];
        dataItem.prodNm = messages["dlvrFg.prodNm"];
        dataItem.barCd = messages["dlvrFg.barCd"];
        dataItem.saleDate = messages["dlvrFg.saleDate"];
        dataItem.posNo = messages["dlvrFg.posNo"];
        dataItem.billNo = messages["dlvrFg.billNo"];
        dataItem.dlvrPackFg = messages["dlvrFg.dlvrFg"];
        dataItem.dlvrInFg = messages["dlvrFg.dlvrInFg"];
        dataItem.cookMemo = messages["dlvrFg.cookMemo"];
        dataItem.saleQty = messages["dlvrFg.saleQty"];
        dataItem.saleAmt = messages["dlvrFg.sale"];
        dataItem.dcAmt = messages["dlvrFg.sale"];
        dataItem.realSaleAmt = messages["dlvrFg.sale"];
        dataItem.gaAmt = messages["dlvrFg.sale"];
        dataItem.vatAmt = messages["dlvrFg.sale"];
        dataItem.tipAmt = messages["dlvrFg.totTipAmt"];
        dataItem.etcAmt = messages["dlvrFg.totEtcAmt"];
        dataItem.totPayAmt = messages["dlvrFg.payMethod"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["dlvrFg.payMethod"];
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
        // <-- //그리드 헤더2줄 -->

    };

    $scope.$on("dlvrFgSaleDtlPopCtrl", function (event, data) {

        // 팝업
        $scope.dlvrFgSaleDtlPopLayer.show(true);

        // 상품정보 보여주기
        $("#lblProdInfo").text( data.prodNm + " [" + data.prodCd + "]");

        // 리스트 조회
        $scope.searchSaleDtlList(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 리스트 조회
    $scope.searchSaleDtlList = function (data) {

        // 파라미터
        var params = data;
        params.payCol = payCol;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/dlvr/dlvrFg/dlvrFgProd/getSaleDtl.sb", params);

    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
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
          }, '내점배달포장현황_상품영수별매출상세_' + getToday() + '.xlsx', function () {
              $timeout(function () {
                  $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
              }, 10);
          });
      }, 10);
    };

}]);