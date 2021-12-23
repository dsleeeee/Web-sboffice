/****************************************************************
 *
 * 파일명 : saleAnalysisReport.js
 * 설  명 : 중분류(매출분석) 다운로드(정직유부) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.14     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  중분류(매출분석) 다운로드 그리드 생성
 */
app.controller('saleAnalysisReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleAnalysisReportCtrl', $scope, $http, $timeout, false));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
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
        var dataItem         = {};
        dataItem.prodCd = messages["saleAnalysisReport.prodCd"];
        dataItem.prodNm = messages["saleAnalysisReport.prodNm"];
        dataItem.totSaleQty = messages["saleAnalysisReport.totSaleQty"];
        dataItem.totServiceQty = messages["saleAnalysisReport.totServiceQty"];
        dataItem.totSaleAmt = messages["saleAnalysisReport.totSaleAmt"];
        dataItem.totDcAmt = messages["saleAnalysisReport.totDcAmt"];
        dataItem.totRealSaleAmt = messages["saleAnalysisReport.totRealSaleAmt"];
        dataItem.totQtyRate = messages["saleAnalysisReport.totQtyRate"];

        // 매장 헤더머지 컬럼 생성
        for (var i = 0; i < arrStoreCol.length; i++) {
            dataItem['store' + arrStoreCol[i] + 'SaleQty'] = storeColList[i].storeNm;
            dataItem['store' + arrStoreCol[i] + 'ServiceQty'] = storeColList[i].storeNm;
            dataItem['store' + arrStoreCol[i] + 'SaleAmt'] = storeColList[i].storeNm;
            dataItem['store' + arrStoreCol[i] + 'DcAmt'] = storeColList[i].storeNm;
            dataItem['store' + arrStoreCol[i] + 'RealSaleAmt'] = storeColList[i].storeNm;
            dataItem['store' + arrStoreCol[i] + 'QtyRate'] = storeColList[i].storeNm;
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
    $scope.$on("saleAnalysisReportCtrl", function(event, data) {
        $scope.searchSaleAnalysisReport();
        event.preventDefault();
    });

    $scope.searchSaleAnalysisReport = function(){
        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1개월 제한
        if (diffMonth > 1) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeCol = storeCol;

        $scope._postJSONQuery.withOutPopUp( "/sale/status/saleAnalysisReport/saleAnalysisReport/getSaleAnalysisReportStoreList.sb", params, function(response){
            var saleAnalysisReport = response.data.data.result;
            $scope.saleAnalysisReport = saleAnalysisReport;

            if(response.data.data.result != null) {
                var storeCds = $scope.saleAnalysisReport.storeCd;
                $scope.searchSaleAnalysisReportList(params, storeCds);
            } else {
                // 조회 데이터가 없습니다 출력하려고
                $scope.searchSaleAnalysisReportListNo(params);
            }
        });
    };

    // 조회버튼 누른 횟수
    var searchCnt = 0;

    $scope.searchSaleAnalysisReportList = function(params, selectStoreCol){
        $scope._inquiryMain("/sale/status/saleAnalysisReport/saleAnalysisReport/getSaleAnalysisReportList.sb", params, function() {
            searchCnt = searchCnt + 1;

            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridSaleAnalysisReportList");
            var columns = grid.columns;

            // storeColList 에 storeCd 배열로 담기
            var storeColArray = [];
            for (var i = 0; i < storeColList.length; i++) {
                comboData = {};
                comboData.value = storeColList[i].storeCd;
                storeColArray.push(comboData);
            }

            if(searchCnt > 1) {
                // 컬럼 총갯수
                var columnsCnt = 8 + (storeColArray.length * 6);
                // 전부 visible
                for (var i = 8; i < columnsCnt; i++) {
                    if(columns[i].visible === true) {
                        columns[i].visible = false;
                    }
                }
            }

            // 매출이 발생한 매장
            var arrSelectStoreCol = selectStoreCol.split(',');

            // 매출이 발생한 매장만 보여주기
            for (var i = 0; i < storeColArray.length; i++) {
                for (var j = 0; j < arrSelectStoreCol.length; j++) {
                    if(storeColArray[i].value === arrSelectStoreCol[j]) {
                        var A = 2 + ((i+1) * 6);
                        columns[A].visible = true;
                        columns[A+1].visible = true;
                        columns[A+2].visible = true;
                        columns[A+3].visible = true;
                        columns[A+4].visible = true;
                        columns[A+5].visible = true;
                    }
                }
            }
            // <-- //그리드 visible -->
        }, false);
    };

    // 조회 데이터가 없습니다 출력하려고
    $scope.searchSaleAnalysisReportListNo = function(params){
        $scope._inquiryMain("/sale/status/saleAnalysisReport/saleAnalysisReport/getSaleAnalysisReportList.sb", params, function() {
            searchCnt = searchCnt + 1;

            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridSaleAnalysisReportList");
            var columns = grid.columns;

            // storeColList 에 storeCd 배열로 담기
            var storeColArray = [];
            for (var i = 0; i < storeColList.length; i++) {
                comboData = {};
                comboData.value = storeColList[i].storeCd;
                storeColArray.push(comboData);
            }

            if(searchCnt > 1) {
                // 컬럼 총갯수
                var columnsCnt = 8 + (storeColArray.length * 6);
                // 전부 visible
                for (var i = 8; i < columnsCnt; i++) {
                    if(columns[i].visible === true) {
                        columns[i].visible = false;
                    }
                }
            }
            // <-- //그리드 visible -->
        }, false);
    };
    // <-- //검색 호출 -->

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        var selectedStartMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        var selectedEndMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	true,
                    includeColumns      :	function (column) {
                        return column.visible;
                    }
                },
                '중분류(매출분석) 다운로드_'+selectedStartMonth+'_'+selectedEndMonth+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //엑셀다운로드 -->
}]);