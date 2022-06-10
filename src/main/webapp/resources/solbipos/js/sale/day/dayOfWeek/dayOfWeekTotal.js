/****************************************************************
 *
 * 파일명 : dayOfWeekTotal.js
 * 설  명 : 기간별매출 > 요일별탭 > 주간종합탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.11.29     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  주간종합 조회 그리드 생성
 */
app.controller('dayOfWeekTotalCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayOfWeekTotalCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayOfWeekTotal", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayOfWeekTotal", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayOfWeekTotalCtrl");

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
        var dataItem         = {};
        dataItem.yoil    = messages["dayofweek.yoil"];
        dataItem.storeCnt    = messages["dayofweek.storeCnt"];
        dataItem.totSaleAmt    = messages["dayofweek.sale"];
        dataItem.totDcAmt    = messages["dayofweek.sale"];
        dataItem.realSaleAmt    = messages["dayofweek.sale"];
        dataItem.billCnt   = messages["dayofweek.sale"];
        dataItem.billUprc   = messages["dayofweek.sale"];
        dataItem.gaAmt   = messages["dayofweek.sale"];
        dataItem.vatAmt   = messages["dayofweek.sale"];
        dataItem.totTipAmt   = messages["dayofweek.totTipAmt"];
        dataItem.totEtcAmt   = messages["dayofweek.totEtcAmt"];
        dataItem.cupAmt   = messages["dayofweek.cupAmt"];
        dataItem.totPayAmt   = messages["dayofweek.payMethod"];
        dataItem.genRealSaleAmt   = messages["dayofweek.dlvrPack"];
        dataItem.dlvrRealSaleAmt   = messages["dayofweek.dlvrPack"];
        dataItem.packRealSaleAmt   = messages["dayofweek.dlvrPack"];
        dataItem.genRealSaleRate   = messages["dayofweek.dlvrPack"];
        dataItem.dlvrRealSaleRate   = messages["dayofweek.dlvrPack"];
        dataItem.packRealSaleRate   = messages["dayofweek.dlvrPack"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["dayofweek.payMethod"];
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

    // <-- 검색 호출 -->
    $scope.$on("dayOfWeekTotalCtrl", function(event, data) {
        $scope.searchDayOfWeekTotal();
        event.preventDefault();
    });

    $scope.searchDayOfWeekTotal = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.storeCds = $("#dayofweekTotalStoreCd").val();
        params.payCol    = payCol;

        $scope._inquiryMain("/sale/day/dayOfWeek/dayOfWeek/getDayOfWeekTotalList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayofweekTotalStoreShow = function () {
        $scope._broadcast('dayofweekTotalStoreCtrl');
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
                messages["dayofweek.dayofweek"] + '(' + messages["dayofweek.total"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);