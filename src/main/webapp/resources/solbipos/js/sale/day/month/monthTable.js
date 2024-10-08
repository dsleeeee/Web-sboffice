/****************************************************************
 *
 * 파일명 : monthTable.js
 * 설  명 : 기간별매출 > 월별탭 > 외식테이블 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.08     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  외식테이블별 매출 조회 그리드 생성
 */
app.controller('monthTableCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthTableCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonthMonthTable', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonthMonthTable', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

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

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("monthTableCtrl");

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
        dataItem.yearMonth    = messages["month.yearMonth"];
        dataItem.totRealSaleAmt    = messages["month.totRealSaleAmt"];
        dataItem.totTblCnt    = messages["month.totTblCnt"];
        dataItem.totGuestCnt    = messages["month.table.totGuestCnt"];

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
                        if (nvl(item[("tbl" + tableColList[i].tblCd + "RealSaleAmt")], '') !== '' && nvl(item[("tbl" + tableColList[i].tblCd + "RealSaleAmt")], '') != "0") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                        }
                    }
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
                params.yearMonth = selectedRow.yearMonth.replace("-", "");
                params.gubun = "month";

                // 실매출
                for (var i = 0; i < tableColList.length; i++) {
                    if (col.binding === ("tbl" + tableColList[i].tblCd + "RealSaleAmt")) {

                        params.tableCd = tableColList[i].tblCd;

                        // 값이 있으면 링크
                        if (nvl(selectedRow[("tbl" + tableColList[i].tblCd + "RealSaleAmt")], '') !== '' && nvl(selectedRow[("tbl" + tableColList[i].tblCd + "RealSaleAmt")], '') != "0") {
                            $scope._broadcast('dayProdSaleDtlCtrl', params);
                        }
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("monthTableCtrl", function(event, data) {
        $scope.searchMonthTable();
        event.preventDefault();
    });

    $scope.searchMonthTable = function() {

        var grid = wijmo.Control.getControl("#wjGridMonthTableList");
        var columns = grid.columns;
        var arr = tableColList;

        // 기존에 조회된 컬럼 제거
        if(columns.length > 4) {
            var removeItem = [];
            for (var j = 4; j < columns.length; j++) {
                removeItem[j-4] = columns[j].binding;
            }
            for (var q = 0; q < removeItem.length; q++) {
                columns.remove(removeItem[q]);
            }
        }

        // 외식테이블별 실매출, 회전수, 고객수 컬럼 생성
        if(arr.length > 0) {

            // 검색조건 외식테이블코드가 있는경우 해당 테이블만 표시
            if($scope.monthTableCd !== "" && $scope.monthTableCd !== null && $scope.monthTableCd !== undefined){
                columns.push(new wijmo.grid.Column({ header: messages["month.realSaleAmt"], binding : 'tbl' + $scope.monthTableCd + 'RealSaleAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["month.tblCnt"], binding : 'tbl' + $scope.monthTableCd + 'TblCnt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["month.totGuestCnt"], binding : 'tbl' + $scope.monthTableCd + 'GuestCnt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
            }else{ // 전체 테이블 표시
                for (var i = 0; i < arr.length; i++) {
                    columns.push(new wijmo.grid.Column({ header: messages["month.realSaleAmt"], binding : 'tbl' + arr[i].tblCd + 'RealSaleAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                    columns.push(new wijmo.grid.Column({ header: messages["month.tblCnt"], binding : 'tbl' + arr[i].tblCd + 'TblCnt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                    columns.push(new wijmo.grid.Column({ header: messages["month.totGuestCnt"], binding : 'tbl' + arr[i].tblCd + 'GuestCnt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                }
            }
        }

        // 외식테이블별 매출 조회
        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.tableCol = tableCol;
        params.tableCd = $scope.monthTableCd; // 외식테이블 코드

        $scope._inquiryMain("/sale/day/month/month/getMonthTableList.sb", params, function() {}, false);

    };
    // <-- //검색 호출 -->

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
                messages["day.month"] + '(' + messages["month.table"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);