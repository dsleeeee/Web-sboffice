/****************************************************************
 *
 * 파일명 : daySumAvg.js
 * 설  명 : 매출현황 상세 팝업(매장:월별 > 영수건수 클릭 시 일자별로 매출을 조회) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.30     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('daySumAvgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('daySumAvgCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 합계
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
        dataItem.saleDate       = messages["day.daySumAvg.saleDate"];
        dataItem.totSaleAmt     = messages["day.daySumAvg.sum"];
        dataItem.totDcAmt       = messages["day.daySumAvg.sum"];
        dataItem.realSaleAmt    = messages["day.daySumAvg.sum"];
        dataItem.avgSaleAmt     = messages["day.daySumAvg.avg"];
        dataItem.avgDcAmt       = messages["day.daySumAvg.avg"];
        dataItem.avgRealSaleAmt = messages["day.daySumAvg.avg"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display: 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display: 'table-cell',
                    verticalAlign: 'middle',
                    textAlign: 'center'
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

    // 팝업 호출시 상세정보 조회
    $scope.$on("daySumAvgCtrl", function(event, data) {
        $scope.wjDaySumAvgLayer.show(true);
        $scope.searchDaySumAvgList(data);
        event.preventDefault();
    });

    $scope.searchDaySumAvgList = function(data){
        var params = {};
        // 기간별매출 > 일자별 탭 > 일별종합 탭
        if(data.gubun == "day") {
          params.saleDate = data.saleDate;
        }
        // 기간별매출 > 월별 탭 > 월별종합 탭
        if(data.gubun == "month") {
          params.yearMonth = data.yearMonth;
        }
        params.storeCd  = data.storeCd;
        params.gubun  = data.gubun;

        $scope._inquiryMain("/sale/day/day/daySumAvg/list.sb", params, function() {}, false);
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
            }, '일자별매출현황_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);