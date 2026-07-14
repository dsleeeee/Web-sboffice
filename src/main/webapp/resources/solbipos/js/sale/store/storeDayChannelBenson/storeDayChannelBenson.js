/****************************************************************
 *
 * 파일명 : storeDayChannelBenson.js
 * 설  명 : (벤슨) 매장-일별매출현황(채널별) JavaScript
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

var optionData = [
    {"name":"그룹별","value":"branch"},
    {"name":"매장별","value":"store"}
];

app.controller('storeDayChannelBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeDayChannelBensonCtrl', $scope, $http, true));

    var startDate = wcombo.genDateVal("#srchDayStartDate", gvStartDate);
    var endDate   = wcombo.genDateVal("#srchDayEndDate", gvEndDate);
    $scope._setComboData("option", optionData);
    $scope.orgnFg        = gvOrgnFg;

    // 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("storeDayChannelBensonCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.saleDate    = messages["day.dayTotal.saleDate"];
        dataItem.yoil        = messages["day.dayTotal.yoil"];
        // dataItem.branchCd    = messages["storeDayChannel.branchCd"];
        // dataItem.branchNm    = messages["storeDayChannel.branchNm"];
        dataItem.storeCnt    = messages["day.dayTotal.storeCnt"];
        dataItem.storeCd     = messages["day.dayTotal.storeCd"];
        dataItem.storeNm     = messages["day.dayTotal.storeNm"];
        dataItem.brand       = messages["dayProd.brand"];
        // dataItem.momsTeam    = messages["cmm.moms.momsTeam"];
        // dataItem.momsAcShop  = messages["cmm.moms.momsAcShop"];
        dataItem.totSaleAmt  = messages["day.dayTotal.saleInfo"];
        dataItem.totDcAmt    = messages["day.dayTotal.saleInfo"];
        dataItem.realSaleAmt = messages["day.dayTotal.saleInfo"];
        dataItem.billCnt     = messages["day.dayTotal.saleInfo"];
        dataItem.billUprc    = messages["day.dayTotal.saleInfo"];
        dataItem.gaAmt       = messages["day.dayTotal.saleInfo"];
        dataItem.vatAmt      = messages["day.dayTotal.saleInfo"];
        dataItem.totTipAmt   = messages["day.dayTotal.totTipAmt"];
        dataItem.totEtcAmt   = messages["day.dayTotal.totEtcAmt"];
        dataItem.cupAmt   = messages["day.dayTotal.cupAmt"];
        dataItem.totPayAmt   = messages["day.dayTotal.payMethod"];

        dataItem.stinBillCnt      = messages["storeDayChannel.stin"];
        dataItem.stinBillUprc     = messages["storeDayChannel.stin"];
        dataItem.stinRealSaleAmt  = messages["storeDayChannel.stin"];
        dataItem.stinRate         = messages["storeDayChannel.stin"];
        dataItem.dlvrBillCnt      = messages["storeDayChannel.dlvr"];
        dataItem.dlvrBillUprc     = messages["storeDayChannel.dlvr"];
        dataItem.dlvrRealSaleAmt  = messages["storeDayChannel.dlvr"];
        dataItem.dlvrRate         = messages["storeDayChannel.dlvr"];
        dataItem.packBillCnt      = messages["storeDayChannel.pack"];
        dataItem.packBillUprc     = messages["storeDayChannel.pack"];
        dataItem.packRealSaleAmt  = messages["storeDayChannel.pack"];
        dataItem.packRate         = messages["storeDayChannel.pack"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["day.dayTotal.payMethod"];
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
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeDayChannelBensonCtrl", function (event, data) {

        // 매장-일별매출현황(채널별) 리스트 조회
        $scope.searchStoreDayChannelBensonList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 매장-일별매출현황(채널별) 리스트 조회
    $scope.searchStoreDayChannelBensonList = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        console.log(diffDay);
        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#storeDayChannelBensonStoreCd").val();
        params.payCol = payCol;
        params.mpayCol = mpayCol;
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

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/store/storeDayChannelBenson/storeDayChannelBenson/getStoreDayChannelBensonList.sb", params, function () {
        });
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        console.log(diffDay);
        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#storeDayChannelBensonStoreCd").val();
        params.payCol = payCol;
        params.mpayCol = mpayCol;
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

        $scope._broadcast('storeDayChannelBensonExcelCtrl', params);
    };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('storeDayChannelBensonExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeDayChannelBensonExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.saleDate    = messages["day.dayTotal.saleDate"];
        dataItem.yoil        = messages["day.dayTotal.yoil"];
        // dataItem.branchCd    = messages["storeDayChannel.branchCd"];
        // dataItem.branchNm    = messages["storeDayChannel.branchNm"];
        dataItem.storeCnt    = messages["day.dayTotal.storeCnt"];
        dataItem.storeCd     = messages["day.dayTotal.storeCd"];
        dataItem.storeNm     = messages["day.dayTotal.storeNm"];
        dataItem.brand       = messages["dayProd.brand"];
        // dataItem.momsTeam    = messages["cmm.moms.momsTeam"];
        // dataItem.momsAcShop  = messages["cmm.moms.momsAcShop"];
        dataItem.totSaleAmt  = messages["day.dayTotal.saleInfo"];
        dataItem.totDcAmt    = messages["day.dayTotal.saleInfo"];
        dataItem.realSaleAmt = messages["day.dayTotal.saleInfo"];
        dataItem.billCnt     = messages["day.dayTotal.saleInfo"];
        dataItem.billUprc    = messages["day.dayTotal.saleInfo"];
        dataItem.gaAmt       = messages["day.dayTotal.saleInfo"];
        dataItem.vatAmt      = messages["day.dayTotal.saleInfo"];
        dataItem.totTipAmt   = messages["day.dayTotal.totTipAmt"];
        dataItem.totEtcAmt   = messages["day.dayTotal.totEtcAmt"];
        dataItem.cupAmt   = messages["day.dayTotal.cupAmt"];
        dataItem.totPayAmt   = messages["day.dayTotal.payMethod"];

        dataItem.stinBillCnt      = messages["storeDayChannel.stin"];
        dataItem.stinBillUprc     = messages["storeDayChannel.stin"];
        dataItem.stinRealSaleAmt  = messages["storeDayChannel.stin"];
        dataItem.stinRate         = messages["storeDayChannel.stin"];
        dataItem.dlvrBillCnt      = messages["storeDayChannel.dlvr"];
        dataItem.dlvrBillUprc     = messages["storeDayChannel.dlvr"];
        dataItem.dlvrRealSaleAmt  = messages["storeDayChannel.dlvr"];
        dataItem.dlvrRate         = messages["storeDayChannel.dlvr"];
        dataItem.packBillCnt      = messages["storeDayChannel.pack"];
        dataItem.packBillUprc     = messages["storeDayChannel.pack"];
        dataItem.packRealSaleAmt  = messages["storeDayChannel.pack"];
        dataItem.packRate         = messages["storeDayChannel.pack"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["day.dayTotal.payMethod"];
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
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeDayChannelBensonExcelCtrl", function (event, data) {

        // 엑셀 리스트 조회
        $scope.searchExcelList(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/store/storeDayChannelBenson/storeDayChannelBenson/getStoreDayChannelBensonExcelList.sb", params, function () {

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
                    messages["storeDayChannelBenson.storeDayChannelBenson"] + '_' + params.startDate + '_' + params.endDate + '_' + getCurDateTime() + '.xlsx', function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    });
            }, 10);
        });
    };
}]);
