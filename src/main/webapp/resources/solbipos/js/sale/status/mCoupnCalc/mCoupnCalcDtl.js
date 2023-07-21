/****************************************************************
 *
 * 파일명 : mCoupnCalc.js
 * 설  명 : 맘스터치 > 매출분석2 > 모바일쿠폰 정산 상세화면 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.19     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 모바일쿠폰 정산 상세화면 controller */
app.controller('mCoupnCalcDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mCoupnCalcDtlCtrl', $scope, $http, $timeout, true));

    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.saleDate = messages["mCoupnCalc.saleDate"];
        dataItem.posNo = messages["mCoupnCalc.posNo"];
        dataItem.billNo = messages["mCoupnCalc.billNo"];
        dataItem.saleFg = messages["mCoupnCalc.appr"];
        dataItem.saleAmt = messages["mCoupnCalc.appr"];
        dataItem.tipAmt = messages["mCoupnCalc.appr"];
        dataItem.vatAmt = messages["mCoupnCalc.appr"];
        dataItem.mcoupnTypeFg = messages["mCoupnCalc.mCoupn"];
        dataItem.mcoupnUprc = messages["mCoupnCalc.mCoupn"];
        dataItem.mcoupnRemainAmt = messages["mCoupnCalc.mCoupn"];
        dataItem.cashBillApprProcFg = messages["mCoupnCalc.cashBill"];
        dataItem.cashBillCardNo = messages["mCoupnCalc.cashBill"];
        dataItem.cashBillApprDt = messages["mCoupnCalc.cashBill"];
        dataItem.apprDt = messages["mCoupnCalc.apprDt"];
        dataItem.apprNo = messages["mCoupnCalc.apprNo"];
        dataItem.apprAmt = messages["mCoupnCalc.apprAmt"];

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

    $scope.$on("mCoupnCalcDtlCtrl", function(event, data) {

        // 모바일쿠폰 정산 상세 리스트 조회
        $scope.searchMCoupnCalcDtl(data);
        event.preventDefault();
    });

    // 모바일쿠폰 정산 상세 리스트 조회
    $scope.searchMCoupnCalcDtl = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/mCoupnCalc/mCoupnCalc/getMCoupnCalcDtlList.sb", params);
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
          }, messages["mCoupnCalc.mCoupnCalc"] + '_' + messages["mCoupnCalc.mCoupnCalcDtl"] + '_' + getToday() + '.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
        }, 10);

    };

}]);