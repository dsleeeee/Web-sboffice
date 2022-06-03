/****************************************************************
 *
 * 파일명 : daySaleReportList.js
 * 설  명 : 일별매출내역 조회(제너시스올떡 분식대장) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.25     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일별매출내역 조회 그리드 생성
 */
app.controller('daySaleReportListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('daySaleReportListCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#daySaleReportListStartMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // -
                if (col.binding === "prodNm") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (item[("prodCd")] === '99999999999993') {
                        wijmo.addClass(e.cell, 'wij_gridBackground-gary-bold');
                    }
                    if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '3') {
                        wijmo.addClass(e.cell, 'wij_gridBackground-blue-bold');
                    }
                    if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '4') {
                        wijmo.addClass(e.cell, 'wij_gridBackground-orange-bold');
                    }
                    if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '5') {
                        wijmo.addClass(e.cell, 'wij_gridBackground-pink-bold');
                    }
                    if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '999') {
                        wijmo.addClass(e.cell, 'wij_gridBackground-yellow-bold');
                    }
                    if (item[("prodCd")] === '9999999999999') {
                        wijmo.addClass(e.cell, 'wij_gridText-black-bold');
                    }
                }

                // 날짜 컬럼
                for (var i = 1; i <= 31; i++) {
                    if (i < 10) {
                        // 날짜
                        if (col.binding === ("a0" + i + "TotSaleQty") || col.binding === ("a0" + i + "TotSaleAmt") || col.binding === ("a0" + i + "TotDcAmt") || col.binding === ("a0" + i + "TotRealSaleAmt")) {
                            var item = s.rows[e.row].dataItem;
                            // 값이 있으면 링크 효과
                            if (item[("prodCd")] === '99999999999993') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-gary-bold');
                            }
                            if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '3') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-blue-bold');
                            }
                            if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '4') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-orange-bold');
                            }
                            if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '5') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-pink-bold');
                            }
                            if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '999') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-yellow-bold');
                            }
                        }
                    } else {
                        // 날짜
                        if (col.binding === ("a" + i + "TotSaleQty") || col.binding === ("a" + i + "TotSaleAmt") || col.binding === ("a" + i + "TotDcAmt") || col.binding === ("a" + i + "TotRealSaleAmt")) {
                            var item = s.rows[e.row].dataItem;
                            // 값이 있으면 링크 효과
                            if (item[("prodCd")] === '99999999999993') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-gary-bold');
                            }
                            if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '3') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-blue-bold');
                            }
                            if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '4') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-orange-bold');
                            }
                            if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '5') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-pink-bold');
                            }
                            if (item[("prodCd")] === '99999999999991' && item[("dlvrInFg")] === '999') {
                                wijmo.addClass(e.cell, 'wij_gridBackground-yellow-bold');
                            }
                        }
                    }
                }
            }
        });

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.prodNm = messages["daySaleReportList.prodNm"];

        // 날짜 헤더머지 컬럼 생성
        for (var i = 1; i <= 31; i++) {
            if (i < 10) {
                dataItem['a0' + i + 'TotSaleQty'] = i+'일';
                dataItem['a0' + i + 'TotSaleAmt'] = i+'일';
                dataItem['a0' + i + 'TotDcAmt'] = i+'일';
                dataItem['a0' + i + 'TotRealSaleAmt'] = i+'일';
            } else {
                dataItem['a' + i + 'TotSaleQty'] = i+'일';
                dataItem['a' + i + 'TotSaleAmt'] = i+'일';
                dataItem['a' + i + 'TotDcAmt'] = i+'일';
                dataItem['a' + i + 'TotRealSaleAmt'] = i+'일';
            }
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
    $scope.$on("daySaleReportListCtrl", function(event, data) {
        $scope.searchDaySaleReportList();
        event.preventDefault();
    });

    $scope.searchDaySaleReportList = function(){
        if ($("#daySaleReportListStoreCd").val() === '') {
            $scope._popMsg(messages["daySaleReportList.storeCdBlank"]); // 매장을 선택해주세요.
            return false;
        }

        // 해당 달에 마지막날짜 구하기
        var createMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        var createMonthLastDate = new Date(createMonth.substring(0, 4), createMonth.substring(4, 6), 0).getDate();

        // 그리드
        var grid = wijmo.Control.getControl("#wjGridDaySaleReportListList");
        var columns = grid.columns;

        // 기존에 조회된 컬럼 제거
        if(columns.length > 1) {
            var removeItem = [];
            for (var j = 1; j < columns.length; j++) {
                removeItem[j-1] = columns[j].binding;
            }
            for (var q = 0; q < removeItem.length; q++) {
                columns.remove(removeItem[q]);
            }
        }

        // 해당 달만큼 날짜 늘리기
        for (var i = 1; i <= createMonthLastDate; i++) {
            if (i < 10) {
                columns.push(new wijmo.grid.Column({ header: messages["daySaleReportList.totSaleQty"], binding : 'a0' + i + 'TotSaleQty', align: "center", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["daySaleReportList.totSaleAmt"], binding : 'a0' + i + 'TotSaleAmt', align: "center", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["daySaleReportList.totDcAmt"], binding : 'a0' + i + 'TotDcAmt', align: "center", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["daySaleReportList.totRealSaleAmt"], binding : 'a0' + i + 'TotRealSaleAmt', align: "center", isReadOnly: "true", aggregate: "Sum"}));
            } else {
                columns.push(new wijmo.grid.Column({ header: messages["daySaleReportList.totSaleQty"], binding : 'a' + i + 'TotSaleQty', align: "center", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["daySaleReportList.totSaleAmt"], binding : 'a' + i + 'TotSaleAmt', align: "center", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["daySaleReportList.totDcAmt"], binding : 'a' + i + 'TotDcAmt', align: "center", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["daySaleReportList.totRealSaleAmt"], binding : 'a' + i + 'TotRealSaleAmt', align: "center", isReadOnly: "true", aggregate: "Sum"}));
            }
        }

        var params = {};
        params.dataCreateMonth = createMonth;
        params.dataCreateMonthLastDate = createMonthLastDate;
        params.storeCd = $("#daySaleReportListStoreCd").val();

        $scope._inquiryMain("/sale/status/daySaleReport/daySaleReportList/getDaySaleReportListList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.daySaleReportListStoreShow = function () {
        $scope._broadcast('daySaleReportListStoreCtrl');
    };

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        var month = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        month = month.substring(4);

        var storeNm = $("#daySaleReportListStoreNm").val();
        storeNm = storeNm.substring(storeNm.indexOf("]")+2); // 띄어쓰기 제외하고

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	true, // 그리드 스타일적용
                    includeColumns      :	function (column) {
                        return column.visible;
                    }
                },
                storeNm+' '+month+'월 매출 정리_'+getCurDate()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //엑셀다운로드 -->
}]);