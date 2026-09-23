/****************************************************************
 *
 * 파일명 : posRcvSaleMoms.js
 * 설  명 : 맘스터치 > 정산 > POS내역수신(매출) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.18     이다솜      1.0            최초생성
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var dlvrOrderFgData = [
    {"name": "일반", "value": "1"},
    {"name": "배달", "value": "2"},
    {"name": "온라인포장", "value": "3"},
    {"name": "내점포장", "value": "4"}
];

/**
 *  POS내역수신(매출) 그리드 생성
 */
app.controller('posRcvSaleMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posRcvSaleMomsCtrl', $scope, $http, false));

    // 매출구분
    $scope.saleYnMap = new wijmo.grid.DataMap([
        {id: "Y", name: messages["posRcvSaleMoms.saleY"]},
        {id: "N", name: messages["posRcvSaleMoms.saleN"]}
    ], 'id', 'name');

    // 조회일자
    var srchDate = wcombo.genDateVal("#srchDate", gvStartDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.dlvrInFgDataMap = new wijmo.grid.DataMap(dlvrInFg, 'value', 'name'); // 채널타입
        $scope.dlvrOrderFgDataMap = new wijmo.grid.DataMap(dlvrOrderFgData, 'value', 'name'); // 배달구분

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

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.branchNm       = messages["prodRankMoms.branchNm"];
        dataItem.storeCd        = messages["todayBillSaleDtl.storeCd"];
        dataItem.storeNm        = messages["todayBillSaleDtl.storeNm"];
        dataItem.saleDate       = messages["todayBillSaleDtl.saleDate"];
        dataItem.posNo          = messages["todayBillSaleDtl.posNo"];
        dataItem.billNo         = messages["todayBillSaleDtl.billNo"];
        dataItem.saleYn         = messages["todayBillSaleDtl.saleYn"];
        dataItem.channelOrderNo     = messages["saleDtlChannel.channelOrderNo"];
        dataItem.channelOrderNo2    = messages["saleDtlChannel.channelOrderNo2"];
        dataItem.channelType        = messages["saleDtlChannel.channelType"];
        dataItem.dlvrOrderFg2       = messages["saleDtlChannel.serviceType"];
        dataItem.channelServiceType = messages["saleDtlChannel.channelServiceType"];
        dataItem.orgSaleDate = messages["saleDtlChannel.orgSaleDate"];
        dataItem.orgPosNo    = messages["saleDtlChannel.orgPosNo"];
        dataItem.orgBillNo   = messages["saleDtlChannel.orgBillNo"];
        dataItem.tblNm          = messages["todayBillSaleDtl.tblNm"];
        dataItem.billDt         = messages["todayBillSaleDtl.billDt"];
        dataItem.lClassCd       = messages["saleDtlChannel.lClassCd"];
        dataItem.lClassNm       = messages["saleDtlChannel.lClassNm"];
        dataItem.mClassCd       = messages["saleDtlChannel.mClassCd"];
        dataItem.mClassNm       = messages["saleDtlChannel.mClassNm"];
        dataItem.sClassCd       = messages["saleDtlChannel.sClassCd"];
        dataItem.sClassNm       = messages["saleDtlChannel.sClassNm"];
        dataItem.prodCd         = messages["todayBillSaleDtl.prodCd"];
        dataItem.sideProdCd     = messages["prodRankMoms.sideProdCd"];
        dataItem.selTypeFg      = messages["prodRankMoms.selTypeFg"];
        dataItem.prodNm         = messages["prodRankMoms.prodNm"];

        dataItem.saleQty1       = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty2       = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty3       = messages["prodRankMoms.totSaleQty"];
        dataItem.totSaleAmt1    = messages["prodRankMoms.totSaleAmt"];
        dataItem.totSaleAmt2    = messages["prodRankMoms.totSaleAmt"];
        dataItem.totSaleAmt3    = messages["prodRankMoms.totSaleAmt"];
        dataItem.dcAmt1         = messages["saleDtlChannel.dcAmt"];
        dataItem.dcAmt2         = messages["saleDtlChannel.dcAmt"];
        dataItem.dcAmt3         = messages["saleDtlChannel.dcAmt"];
        dataItem.realSaleAmt1   = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt2   = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt3   = messages["prodRankMoms.realSaleAmt"];
        dataItem.gaAmt1         = messages["saleDtlChannel.gaAmt"];
        dataItem.gaAmt2         = messages["saleDtlChannel.gaAmt"];
        dataItem.gaAmt3         = messages["saleDtlChannel.gaAmt"];
        dataItem.vatAmt1        = messages["saleDtlChannel.vatAmt"];
        dataItem.vatAmt2        = messages["saleDtlChannel.vatAmt"];
        dataItem.vatAmt3        = messages["saleDtlChannel.vatAmt"];

        // header 셋팅
        for (var i = 0; i < vDlvrOrderFg.length; i++) {
            dataItem[vDlvrOrderFg[i] + "SaleQty1"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
            dataItem[vDlvrOrderFg[i] + "SaleQty2"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
            dataItem[vDlvrOrderFg[i] + "SaleQty3"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
            dataItem[vDlvrOrderFg[i] + "RealSaleAmt1"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
            dataItem[vDlvrOrderFg[i] + "RealSaleAmt2"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
            dataItem[vDlvrOrderFg[i] + "RealSaleAmt3"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
        }

        // header 셋팅
        for (var j = 0; j < vDlvrOrderFg.length; j++) {
            for (var i = 0; i < arrDlvrInFgCol.length; i++) {
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty1"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty2"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty3"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt1"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt2"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt3"] =messages["prodRankMoms." + vDlvrOrderFg[j]];
            }
        }

        s.columnHeaders.rows[0].dataItem = dataItem;


        // 둘째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1 = {};
        dataItem1.branchNm       = messages["prodRankMoms.branchNm"];
        dataItem1.storeCd        = messages["todayBillSaleDtl.storeCd"];
        dataItem1.storeNm        = messages["todayBillSaleDtl.storeNm"];
        dataItem1.saleDate       = messages["todayBillSaleDtl.saleDate"];
        dataItem1.posNo          = messages["todayBillSaleDtl.posNo"];
        dataItem1.billNo         = messages["todayBillSaleDtl.billNo"];
        dataItem1.saleYn         = messages["todayBillSaleDtl.saleYn"];
        dataItem1.channelOrderNo     = messages["saleDtlChannel.channelOrderNo"];
        dataItem1.channelOrderNo2    = messages["saleDtlChannel.channelOrderNo2"];
        dataItem1.channelType        = messages["saleDtlChannel.channelType"];
        dataItem1.dlvrOrderFg2       = messages["saleDtlChannel.serviceType"];
        dataItem1.channelServiceType = messages["saleDtlChannel.channelServiceType"];
        dataItem1.orgSaleDate = messages["saleDtlChannel.orgSaleDate"];
        dataItem1.orgPosNo    = messages["saleDtlChannel.orgPosNo"];
        dataItem1.orgBillNo   = messages["saleDtlChannel.orgBillNo"];
        dataItem1.tblNm          = messages["todayBillSaleDtl.tblNm"];
        dataItem1.billDt         = messages["todayBillSaleDtl.billDt"];
        dataItem1.lClassCd       = messages["saleDtlChannel.lClassCd"];
        dataItem1.lClassNm       = messages["saleDtlChannel.lClassNm"];
        dataItem1.mClassCd       = messages["saleDtlChannel.mClassCd"];
        dataItem1.mClassNm       = messages["saleDtlChannel.mClassNm"];
        dataItem1.sClassCd       = messages["saleDtlChannel.sClassCd"];
        dataItem1.sClassNm       = messages["saleDtlChannel.sClassNm"];
        dataItem1.prodCd         = messages["todayBillSaleDtl.prodCd"];
        dataItem1.sideProdCd     = messages["prodRankMoms.sideProdCd"];
        dataItem1.selTypeFg      = messages["prodRankMoms.selTypeFg"];
        dataItem1.prodNm         = messages["prodRankMoms.prodNm"];

        dataItem1.saleQty1       = messages["prodRankMoms.saleQty1"];
        dataItem1.saleQty2       = messages["prodRankMoms.saleQty2"];
        dataItem1.saleQty3       = messages["prodRankMoms.saleQty3"];
        dataItem1.totSaleAmt1    = messages["saleDtlChannel.totSaleAmt1"];
        dataItem1.totSaleAmt2    = messages["saleDtlChannel.totSaleAmt2"];
        dataItem1.totSaleAmt3    = messages["saleDtlChannel.totSaleAmt3"];
        dataItem1.dcAmt1         = messages["saleDtlChannel.dcAmt1"];
        dataItem1.dcAmt2         = messages["saleDtlChannel.dcAmt2"];
        dataItem1.dcAmt3         = messages["saleDtlChannel.dcAmt3"];
        dataItem1.realSaleAmt1   = messages["prodRankMoms.realSaleAmt1"];
        dataItem1.realSaleAmt2   = messages["prodRankMoms.realSaleAmt2"];
        dataItem1.realSaleAmt3   = messages["prodRankMoms.realSaleAmt3"];
        dataItem1.gaAmt1         = messages["saleDtlChannel.gaAmt1"];
        dataItem1.gaAmt2         = messages["saleDtlChannel.gaAmt2"];
        dataItem1.gaAmt3         = messages["saleDtlChannel.gaAmt3"];
        dataItem1.vatAmt1        = messages["saleDtlChannel.vatAmt1"];
        dataItem1.vatAmt2        = messages["saleDtlChannel.vatAmt2"];
        dataItem1.vatAmt3        = messages["saleDtlChannel.vatAmt3"];

        // header 셋팅
        for (var j = 0; j < vDlvrOrderFg.length; j++) {
            dataItem1[vDlvrOrderFg[j] + "SaleQty1"] = messages["prodRankMoms.saleQty1"];
            dataItem1[vDlvrOrderFg[j] + "SaleQty2"] = messages["prodRankMoms.saleQty2"];
            dataItem1[vDlvrOrderFg[j] + "SaleQty3"] = messages["prodRankMoms.saleQty3"];
            dataItem1[vDlvrOrderFg[j] + "RealSaleAmt1"] = messages["prodRankMoms.realSaleAmt1"];
            dataItem1[vDlvrOrderFg[j] + "RealSaleAmt2"] = messages["prodRankMoms.realSaleAmt2"];
            dataItem1[vDlvrOrderFg[j] + "RealSaleAmt3"] = messages["prodRankMoms.realSaleAmt3"];
        }

        // header 셋팅
        for (var j = 0; j < vDlvrOrderFg.length; j++) {
            for (var i = 0; i < arrDlvrInFgCol.length; i++) {
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty1"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty2"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty3"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt1"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt2"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt3"] = arrDlvrInFgColNm[i];
            }
        }
        s.columnHeaders.rows[1].dataItem = dataItem1;

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

                if ((panel.grid.columnHeaders.rows.length - 1) === r) {
                    // 헤더의 전체선택 클릭 로직
                    var flex   = panel.grid;
                    var column = flex.columns[c];
                    // check that this is a boolean column
                    if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
                        // prevent sorting on click
                        column.allowSorting = false;
                        // count true values to initialize checkbox
                        var cnt             = 0;
                        for (var i = 0; i < flex.rows.length; i++) {
                            if (flex.getCellData(i, c) === true) {
                                cnt++;
                            }
                        }
                        // create and initialize checkbox
                        if (column.format === 'checkBoxText') {
                            cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
                                + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
                        } else {
                            cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
                        }
                        var cb           = cell.firstChild;
                        cb.checked       = cnt > 0;
                        cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
                        // apply checkbox value to cells
                        cb.addEventListener('click', function (e) {
                            flex.beginUpdate();
                            for (var i = 0; i < flex.rows.length; i++) {
                                if(!flex.rows[i].isReadOnly) {
                                    flex.setCellData(i, c, cb.checked);
                                }
                            }
                            flex.endUpdate();
                        });
                    }
                }
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
        // <-- //그리드 헤더3줄 -->

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("posRcvSaleMomsCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };
    
    $scope.$on("posRcvSaleMomsCtrl", function (event, data) {
        // 조회
        $scope.searchPosRcvSaleMomsList();
        event.preventDefault();
    });

    // 조회
    $scope.searchPosRcvSaleMomsList = function () {

        // 조회일자 필수 체크
        if (!srchDate.value) {
            $scope._popMsg(messages['cmm.require.select']);
            return false;
        }

        // 매장선택 필수 체크
        if (!$("#posRcvSaleMomsStoreCd").val()) {
            $scope._popMsg(messages['cmm.require.selectStore']);
            return false;
        }

        // 파라미터
        var params = {};
        params.saleDate = wijmo.Globalize.format(srchDate.value, 'yyyyMMdd');
        params.storeCd = $("#posRcvSaleMomsStoreCd").val();
        params.dlvrInFgCol = dlvrInFgCol;

        $scope._inquiryMain("/sale/moms/posRcvSaleMoms/posRcvSaleMoms/getPosRcvSaleMomsList.sb", params, function () {

            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = columns.length;

            //
            for (var i = 0; i < columnsCnt; i++) {
                columns[i].visible = true;
            }
        });
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "POS내역수신(매출)" + '_' + getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);
