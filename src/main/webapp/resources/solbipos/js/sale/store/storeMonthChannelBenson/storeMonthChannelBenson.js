/****************************************************************
 *
 * 파일명 : storeMonthChannelBenson.js
 * 설  명 : (벤슨) 매장-월별매출현황(채널별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.08     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeMonthChannelBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeMonthChannelBensonCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonthMonthTotal', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonthMonthTotal', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');

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
        // dataItem.branchCd    = messages["storeMonthChannel.branchCd"];
        // dataItem.branchNm    = messages["storeMonthChannel.branchNm"];
        dataItem.storeCnt     = messages["month.storeCnt"];
        dataItem.storeCd     = messages["month.storeCd"];
        dataItem.storeNm     = messages["month.storeNm"];
        dataItem.storeCnt    = messages["month.storeCnt"];
        dataItem.brand       = messages["dayProd.brand"];
        // dataItem.momsTeam    = messages["cmm.moms.momsTeam"];
        // dataItem.momsAcShop  = messages["cmm.moms.momsAcShop"];
        dataItem.saleCnt    = messages["month.saleCnt"];
        dataItem.totSaleAmt    = messages["month.sale"];
        dataItem.totDcAmt    = messages["month.sale"];
        dataItem.realSaleAmt    = messages["month.sale"];
        dataItem.billCnt   = messages["month.sale"];
        dataItem.billUprc   = messages["month.sale"];
        dataItem.gaAmt   = messages["month.sale"];
        dataItem.vatAmt   = messages["month.sale"];
        dataItem.totTipAmt   = messages["month.totTipAmt"];
        dataItem.totEtcAmt   = messages["month.totEtcAmt"];
        dataItem.cupAmt   = messages["month.cupAmt"];
        dataItem.totPayAmt   = messages["month.payMethod"];

        dataItem.stinBillCnt      = messages["storeMonthChannel.stin"];
        dataItem.stinBillUprc     = messages["storeMonthChannel.stin"];
        dataItem.stinRealSaleAmt  = messages["storeMonthChannel.stin"];
        dataItem.stinRate         = messages["storeMonthChannel.stin"];
        dataItem.dlvrBillCnt      = messages["storeMonthChannel.dlvr"];
        dataItem.dlvrBillUprc     = messages["storeMonthChannel.dlvr"];
        dataItem.dlvrRealSaleAmt  = messages["storeMonthChannel.dlvr"];
        dataItem.dlvrRate         = messages["storeMonthChannel.dlvr"];
        dataItem.packBillCnt      = messages["storeMonthChannel.pack"];
        dataItem.packBillUprc     = messages["storeMonthChannel.pack"];
        dataItem.packRealSaleAmt  = messages["storeMonthChannel.pack"];
        dataItem.packRate         = messages["storeMonthChannel.pack"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["month.payMethod"];
        }

        // 모바일페이상세 헤더머지 컬럼 생성
        for (var i = 0; i < arrMpayCol.length; i++) {
            dataItem['mpay' + arrMpayCol[i]] = messages["month.mpayMethod"];
        }

        // 주문채널별 header 셋팅
        for (var i = 0; i < arrDlvrInFgCol.length; i++) {
            dataItem['realSaleCnt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
            dataItem['saleQty' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
            dataItem['realSaleAmt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
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
    $scope.$on("storeMonthChannelBensonCtrl", function(event, data) {

        // 매장-월별매출현황(채널별) 리스트 조회
        $scope.searchMonthTotalBensonList();

        event.preventDefault();
    });

    // 매장-월별매출현황(채널별) 리스트 조회
    $scope.searchMonthTotalBensonList = function() {

        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['mobile.cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1년(12개월) 제한
        if (diffMonth > 12) {
            $scope._popMsg(messages['mobile.cmm.dateOver.1year.error']);
            return false;
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeCds = $("#storeMonthChannelBensonStoreCd").val();
        params.payCol    = payCol;
        params.mpayCol   = mpayCol;
        params.dlvrInFgCol = dlvrInFgCol;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.listScale = 500; //-페이지 스케일 갯수
        console.log(params);

        $scope._inquiryMain("/sale/store/storeMonthChannelBenson/storeMonthChannelBenson/getStoreMonthChannelBensonList.sb", params, function() {
        }, false);
    };
    // <-- //검색 호출 -->

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {
        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['mobile.cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1년(12개월) 제한
        if (diffMonth > 12) {
            $scope._popMsg(messages['mobile.cmm.dateOver.1year.error']);
            return false;
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeCds = $("#storeMonthChannelBensonStoreCd").val();
        params.payCol    = payCol;
        params.mpayCol   = mpayCol;
        params.dlvrInFgCol = dlvrInFgCol;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }

        $scope._broadcast('storeMonthChannelBensonExcelCtrl', params);
    };
}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('storeMonthChannelBensonExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeMonthChannelBensonExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');

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
        // dataItem.branchCd    = messages["storeMonthChannel.branchCd"];
        // dataItem.branchNm    = messages["storeMonthChannel.branchNm"];
        dataItem.storeCnt     = messages["month.storeCnt"];
        dataItem.storeCd     = messages["month.storeCd"];
        dataItem.storeNm     = messages["month.storeNm"];
        dataItem.storeCnt    = messages["month.storeCnt"];
        dataItem.brand       = messages["dayProd.brand"];
        // dataItem.momsTeam    = messages["cmm.moms.momsTeam"];
        // dataItem.momsAcShop  = messages["cmm.moms.momsAcShop"];
        dataItem.saleCnt    = messages["month.saleCnt"];
        dataItem.totSaleAmt    = messages["month.sale"];
        dataItem.totDcAmt    = messages["month.sale"];
        dataItem.realSaleAmt    = messages["month.sale"];
        dataItem.billCnt   = messages["month.sale"];
        dataItem.billUprc   = messages["month.sale"];
        dataItem.gaAmt   = messages["month.sale"];
        dataItem.vatAmt   = messages["month.sale"];
        dataItem.totTipAmt   = messages["month.totTipAmt"];
        dataItem.totEtcAmt   = messages["month.totEtcAmt"];
        dataItem.cupAmt   = messages["month.cupAmt"];
        dataItem.totPayAmt   = messages["month.payMethod"];

        dataItem.stinBillCnt      = messages["storeMonthChannel.stin"];
        dataItem.stinBillUprc     = messages["storeMonthChannel.stin"];
        dataItem.stinRealSaleAmt  = messages["storeMonthChannel.stin"];
        dataItem.stinRate         = messages["storeMonthChannel.stin"];
        dataItem.dlvrBillCnt      = messages["storeMonthChannel.dlvr"];
        dataItem.dlvrBillUprc     = messages["storeMonthChannel.dlvr"];
        dataItem.dlvrRealSaleAmt  = messages["storeMonthChannel.dlvr"];
        dataItem.dlvrRate         = messages["storeMonthChannel.dlvr"];
        dataItem.packBillCnt      = messages["storeMonthChannel.pack"];
        dataItem.packBillUprc     = messages["storeMonthChannel.pack"];
        dataItem.packRealSaleAmt  = messages["storeMonthChannel.pack"];
        dataItem.packRate         = messages["storeMonthChannel.pack"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["month.payMethod"];
        }

        // 모바일페이상세 헤더머지 컬럼 생성
        for (var i = 0; i < arrMpayCol.length; i++) {
            dataItem['mpay' + arrMpayCol[i]] = messages["month.mpayMethod"];
        }

        // 주문채널별 header 셋팅
        for (var i = 0; i < arrDlvrInFgCol.length; i++) {
            dataItem['realSaleCnt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
            dataItem['saleQty' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
            dataItem['realSaleAmt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
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

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeMonthChannelBensonExcelCtrl", function (event, data) {

        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/store/storeMonthChannelBenson/storeMonthChannelBenson/getStoreMonthChannelBensonExcelList.sb", params, function (){
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    includeColumns: function (column) {
                        return column.visible;
                    }
                },
                    messages["storeMonthChannelBenson.storeMonthChannelBenson"] + '_' + params.startMonth + '_' + params.endMonth + '_' + getCurDateTime() +'.xlsx', function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    });
            }, 10);
        });
    };

}]);
