/****************************************************************
 *
 * 파일명 : todayBenson.js
 * 설  명 : (벤슨) 당일매출현황(영수증) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.16     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/**
 *  당일매출현황(영수증) 그리드 생성
 */
app.controller('todayBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('todayBensonCtrl', $scope, $http, false));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드

    // 그리드 매출구분
    $scope.saleYnMap = new wijmo.grid.DataMap([
        {id: "Y", name: messages["todayDtl.saleY"]},
        {id: "N", name: messages["todayDtl.saleN"]}
    ], 'id', 'name');


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("todayBensonCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "billNo") { // 영수증번호
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "billNo") { // 전표번호 클릭
                    var params    = {};
                    params.storeCd  = selectedRow.storeCd;
                    params.saleDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
                    params.posNo    = selectedRow.posNo;
                    params.billNo   = selectedRow.billNo;
                    
                    // 영수증 조회 팝업
                    $scope._broadcast('billInfoBensonCtrl', params);
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.storeCd = messages["todayBenson.storeCd"];
        dataItem.storeNm = messages["todayBenson.storeNm"];
        dataItem.posNo = messages["todayDtl.dtl.posNo"];
        dataItem.billNo = messages["todayDtl.dtl.billNo"];
        dataItem.billDt = messages["todayDtl.dtl.billDt"];
        dataItem.saleYn = messages["todayDtl.dtl.saleYn"];
        dataItem.totSaleAmt = messages["todayDtl.dtl.totSaleAmt"];
        dataItem.totDcAmt = messages["todayDtl.dtl.totDcAmt"];
        dataItem.realSaleAmt = messages["todayDtl.dtl.realSaleAmt"];
        dataItem.gaAmt = messages["todayDtl.dtl.gaAmt"];
        dataItem.vatAmt = messages["todayDtl.dtl.vatAmt"];
        dataItem.totTipAmt = messages["todayDtl.dtl.totTipAmt"];
        dataItem.totEtcAmt = messages["todayDtl.dtl.totEtcAmt"];
        dataItem.cupAmt = messages["todayDtl.dtl.cupAmt"];
        dataItem.totPayAmt = messages["todayDtl.dtl.payMethod"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["todayDtl.dtl.payMethod"];
        }

        // 모바일페이상세 헤더머지 컬럼 생성
        for (var i = 0; i < arrMpayCol.length; i++) {
            dataItem['mpay' + arrMpayCol[i]] = messages["month.mpayMethod"];
        }

        // 할인구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrDcCol.length; i++) {
            dataItem['dc' + arrDcCol[i]] = messages["todayDtl.dcInfo"];
        }

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
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("todayBensonCtrl", function (event, data) {

        // 당일매출현황(영수증) 조회
        $scope.searchTodayBensonList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 당일매출현황(영수증) 조회
    $scope.searchTodayBensonList = function () {

        // 파라미터
        var params = {};
        params.storeCds = $("#todayBensonStoreCd").val();
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.payCol = payCol;
        params.mpayCol = mpayCol;
        params.dcCol = dcCol;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
                if (momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.listScale = 500;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/today/todayBenson/todayBenson/getTodayBensonList.sb", params, function () {

        });
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {

        var params = {};
        params.storeCds = $("#todayBensonStoreCd").val();
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.payCol = payCol;
        params.mpayCol = mpayCol;
        params.dcCol = dcCol;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
                if (momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        $scope._broadcast('todayBensonExcelCtrl', params);
    };
}]);

/**
 *  당일매출현황(영수증) 엑셀 다운로드
 */
app.controller('todayBensonExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('todayBensonExcelCtrl', $scope, $http, false));

    // 그리드 매출구분
    $scope.saleYnMap = new wijmo.grid.DataMap([
        {id: "Y", name: messages["todayDtl.saleY"]},
        {id: "N", name: messages["todayDtl.saleN"]}
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.storeCd = messages["todayBenson.storeCd"];
        dataItem.storeNm = messages["todayBenson.storeNm"];
        dataItem.posNo = messages["todayDtl.dtl.posNo"];
        dataItem.billNo = messages["todayDtl.dtl.billNo"];
        dataItem.billDt = messages["todayDtl.dtl.billDt"];
        dataItem.saleYn = messages["todayDtl.dtl.saleYn"];
        dataItem.totSaleAmt = messages["todayDtl.dtl.totSaleAmt"];
        dataItem.totDcAmt = messages["todayDtl.dtl.totDcAmt"];
        dataItem.realSaleAmt = messages["todayDtl.dtl.realSaleAmt"];
        dataItem.gaAmt = messages["todayDtl.dtl.gaAmt"];
        dataItem.vatAmt = messages["todayDtl.dtl.vatAmt"];
        dataItem.totTipAmt = messages["todayDtl.dtl.totTipAmt"];
        dataItem.totEtcAmt = messages["todayDtl.dtl.totEtcAmt"];
        dataItem.cupAmt = messages["todayDtl.dtl.cupAmt"];
        dataItem.totPayAmt = messages["todayDtl.dtl.payMethod"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["todayDtl.dtl.payMethod"];
        }

        // 모바일페이상세 헤더머지 컬럼 생성
        for (var i = 0; i < arrMpayCol.length; i++) {
            dataItem['mpay' + arrMpayCol[i]] = messages["month.mpayMethod"];
        }

        // 할인구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrDcCol.length; i++) {
            dataItem['dc' + arrDcCol[i]] = messages["todayDtl.dcInfo"];
        }

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
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("todayBensonExcelCtrl", function (event, data) {

        // 엑셀 리스트 조회
        $scope.searchExcelList(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/today/todayBenson/todayBenson/getTodayBensonExcelList.sb", params, function () {

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
                }, messages["todayBenson.todayBenson"] + '_' + params.startDate + '_' + getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);
