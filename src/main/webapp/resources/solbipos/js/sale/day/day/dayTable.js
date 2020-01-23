/****************************************************************
 *
 * 파일명 : dayTable.js
 * 설  명 : 기간별매출 > 일자별탭 > 외식테이블별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.09     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  외식테이블별 매출 조회 그리드 생성
 */
app.controller('dayTableCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayTableCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayTable", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayTable", gvEndDate);

    // <-- 콤보박스 데이터 Set -->
    // 조회조건 콤보박스 데이터 Set
    var comboArray = [];
    comboData1       = {};
    comboData1.name  = "전체";
    comboData1.value = null;
    comboArray.push(comboData1);

    for (var i = 0; i < tableColList.length; i++) {
        comboData       = {};
        comboData.name  = tableColList[i].tblNm;
        comboData.value = tableColList[i].tblCd;
        comboArray.push(comboData);
    }
    $scope._setComboData("tableCdCombo", comboArray); //테이블표시
    // <-- //콤보박스 데이터 Set -->

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
        var dataItem         = {};
        dataItem.saleDate    = messages["day.dayTable.saleDate"];
        dataItem.totRealSaleAmt    = messages["day.dayTable.totRealSaleAmt"];
        dataItem.totTblCnt    = messages["day.dayTable.totTblCnt"];
        dataItem.totGuestCnt    = messages["day.dayTable.totGuestCnt"];

        // 외식테이블 헤더머지 컬럼 생성
        for (var i = 0; i < arrTableCol.length; i++) {
            dataItem['tbl' + arrTableCol[i] + 'RealSaleAmt'] = tableColList[i].tblNm;
            dataItem['tbl' + arrTableCol[i] + 'TblCnt'] = tableColList[i].tblNm;
            dataItem['tbl' + arrTableCol[i] + 'GuestCnt'] = tableColList[i].tblNm;
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

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 실매출
                for (var i = 0; i < tableColList.length; i++) {
                    if (col.binding === ("tbl" + tableColList[i].tblCd + "RealSaleAmt")) {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (nvl(item[("tbl" + tableColList[i].tblCd + "RealSaleAmt")], '') !== '') {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                        }
                    }
                }

                // 날짜 형식
                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                var selectedRow = s.rows[ht.row].dataItem;
                var params      = {};
                params.saleDate = selectedRow.saleDate;
                params.gubun = "day";

                // 실매출
                for (var i = 0; i < tableColList.length; i++) {
                    if (col.binding === ("tbl" + tableColList[i].tblCd + "RealSaleAmt")) {

                        params.tableCd = tableColList[i].tblCd;

                        // 값이 있으면 링크
                        if (nvl(selectedRow[("tbl" + tableColList[i].tblCd + "RealSaleAmt")], '') !== '') {
                            $scope._broadcast('dayProdSaleDtlCtrl', params);
                        }
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("dayTableCtrl", function(event, data) {
        $scope.searchDayTable();
        event.preventDefault();
    });

    $scope.searchDayTable = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.tableCol = tableCol;
        params.tableCd = $scope.dayTableCd; // 외식테이블 코드

        $scope._inquiryMain("/sale/day/day/day/getDayTableList.sb", params, function() {}, false);

        // <-- 그리드 visible -->
        // 선택한 테이블에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridDayTableList");
        var columns = grid.columns;
        var start = 0;
        var end = 0;

        // tableCol 배열로 담기
        var table = tableCol.split(',');
        var tableColArray = [];
        for (var i = 0; i < table.length; i++) {
            comboData = {};
            comboData.value = table[i];
            tableColArray.push(comboData);
        }

        // 컬럼 총갯수
        var columnsCnt = 4 + (tableColArray.length * 3);

        // 전체선택시 전부 visible
        if($scope.dayTableCd === null)
        {
            for (var i = 4; i <= columnsCnt; i++) {
                columns[i].visible = true;
            }
        }
        // 선택한 테이블만 visible
        else
        {
            for (var i = 0; i < tableColArray.length; i++) {
                if (tableColArray[i].value === $scope.dayTableCd) {
                    start = (i * 3) + 4;
                    end = (i * 3) + 6;
                }
            }

            for (var i = 4; i < columnsCnt; i++) {
                if (i >= start && i <= end) {
                    columns[i].visible = true;
                } else {
                    columns[i].visible = false;
                }
            }
        }
        // <-- //그리드 visible -->
    };
    // <-- //검색 호출 -->

}]);