/****************************************************************
 *
 * 파일명 : storeOpenCloseBensonDay.js
 * 설  명 : (벤슨) 매장분석 > 매장 오픈/마감 현황 - 일별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.09     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 매장 오픈/마감 현황 - 일별 controller */
app.controller('storeOpenCloseBensonDayTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOpenCloseBensonDayTimeCtrl', $scope, $http, $timeout, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#dayStartDate", gvStartDate);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeOpenCloseBensonDayTimeCtrl", function (event, data) {

        // 파라미터
        var params = {};
        params.saleDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.storeCds = $("#storeOpenCloseBensonDayStoreCd").val();
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }

        var scope = agrid.getScope("storeOpenCloseBensonDayCtrl");
        // 일별 조회
        scope.searchStoreOpenCloseBensonDay(params);
        var scope2 = agrid.getScope('storeOpenCloseBensonDayDtlCtrl');
        scope2._gridDataInit();   // 그리드 초기화
        event.preventDefault();
    });

    }]);

app.controller('storeOpenCloseBensonDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOpenCloseBensonDayCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 포맷
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                if(col.binding === "open"){
                    $(e.cell).html("<button id='green'>개점</button>");
                } else if(col.binding === "close"){
                    $(e.cell).html("<button id='yellow'>마감</button>");
                } else if(col.binding === "none"){
                    $(e.cell).html("<button id='red'>미마감</button>");
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {

            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if(col.binding === "open" || col.binding === "close" || col.binding === "none"){
                    var scope = agrid.getScope("storeOpenCloseBensonDayTimeCtrl");
                    var params = {};
                    params.saleDate = selectedRow.saleDate;
                    params.min = selectedRow.min;
                    params.max = selectedRow.max;
                    params.gubun = col.binding;
                    params.storeCds = $("#storeOpenCloseBensonDayStoreCd").val();
                    params.storeHqBrandCd = scope.storeHqBrandCd;
                    // '전체' 일때
                    if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                        var momsHqBrandCd = "";
                        for(var i=0; i < momsHqBrandCdComboList.length; i++){
                            if(momsHqBrandCdComboList[i].value !== null) {
                                momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                            }
                        }
                        params.userBrands = momsHqBrandCd;
                    }

                    $scope._broadcast('storeOpenCloseBensonDayDtlCtrl', params);
                }
            }
        });


        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem	    = {};
        dataItem.time		= messages["storeOpenCloseBenson.time"];
        dataItem.openCnt    = messages["storeOpenCloseBenson.openCnt"];
        dataItem.closeCnt   = messages["storeOpenCloseBenson.closeCnt"];
        dataItem.open		= messages["storeOpenCloseBenson.dtlStoreList"];
        dataItem.close		= messages["storeOpenCloseBenson.dtlStoreList"];
        dataItem.none	    = messages["storeOpenCloseBenson.dtlStoreList"];

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
    $scope.$on("storeOpenCloseBensonDayCtrl", function (event, data) {

        event.preventDefault();
    });

    // 일별 조회
    $scope.searchStoreOpenCloseBensonDay = function(data){
        // 파라미터
        var params = {};
        params.saleDate = data.saleDate; // 조회기간
        params.optionFg = data.optionFg;
        params.storeCds = data.storeCds;
        params.storeHqBrandCd = data.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/benson/storeOpenCloseBenson/storeOpenCloseBenson/getStoreOpenCloseBensonDayList.sb", params, function(response) {
            var grid = $scope.flex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;
        }, function(response) {
            s_alert.pop(response.message);
            var grid = $scope.flex;
            grid.itemsSource = new wijmo.collections.CollectionView([]);
        });
    };

    // 엑셀 다운로드
    $scope.excelDownloadDay = function () {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : false,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, messages["storeOpenCloseBenson.storeOpenCloseBenson"] + messages["storeOpenCloseBenson.day"] + '_' + $scope.flex.rows[0].dataItem.saleDate + '_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);


/** 매장 오픈/마감 현황 - 일별 상세 controller */
app.controller('storeOpenCloseBensonDayDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOpenCloseBensonDayDtlCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "storeCd") {
                    var item = s.rows[e.row].dataItem;
                    if(nvl(item.posNo, '') !== ''){
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);

            if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
                var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
                if (rng && rng.columnSpan > 1) {
                    e.preventDefault();
                }
            }

            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                if (col.binding === "storeCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    if(nvl(selectedRow.posNo, '') !== ""){
                        $scope.openDtlLayer(selectedRow);
                    }
                }
            }
        }, true);

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem			= {};
        dataItem.storeCd		= messages["storeOpenCloseBenson.storeCd"];
        dataItem.storeNm		= messages["storeOpenCloseBenson.storeNm"];
        dataItem.brand          = messages["dayProd.brand"];
        dataItem.openTime		= messages["storeOpenCloseBenson.openTime"];
        dataItem.closeTime		= messages["storeOpenCloseBenson.closeTime"];
        dataItem.runTime		= messages["storeOpenCloseBenson.runTime"];
        dataItem.posNo			= messages["storeOpenCloseBenson.posNo"];
        dataItem.closeFgNm		= messages["posExcclc.closeFg"];
        dataItem.regDt			= messages["posExcclc.regDate"];
        dataItem.totSaleAmt		= messages["posExcclc.sale"];
        dataItem.totDcAmt		= messages["posExcclc.sale"];
        dataItem.realSaleAmt	= messages["posExcclc.sale"];
        dataItem.cashExactAmt	= messages["posExcclc.sale"];
        dataItem.cashBillSaleAmt = messages["posExcclc.sale"];
        dataItem.fundAmt		= messages["posExcclc.posFundAmt"];
        dataItem.accntInAmt		= messages["posExcclc.inOut"];
        dataItem.accntOutAmt	= messages["posExcclc.inOut"];
        dataItem.cashTicketAmt	= messages["posExcclc.cashTicketAmt"];
        dataItem.lostAmt		= messages["posExcclc.cashLostAmt"];

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
    $scope.$on("storeOpenCloseBensonDayDtlCtrl", function (event, data) {

        // 일별 조회
        $scope.searchStoreOpenCloseBensonDayDtl(data);
        event.preventDefault();
    });

    // 일별 조회 상세
    $scope.searchStoreOpenCloseBensonDayDtl = function(data){

        // 파라미터
        var params = {};
        params.saleDate = data.saleDate;
        params.min = data.min;
        params.max = data.max;
        params.gubun = data.gubun;
        params.storeCds = data.storeCds;
        params.storeHqBrandCd = data.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/benson/storeOpenCloseBenson/storeOpenCloseBenson/getStoreOpenCloseBensonDayDtlList.sb", params, function(response) {
            var grid = $scope.flex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;
        }, function(response) {
            s_alert.pop(response.message);
            var grid = $scope.flex;
            grid.itemsSource = new wijmo.collections.CollectionView([]);
        });
    };

    //상세 화면 열기
    $scope.openDtlLayer = function(selectedRow){
        $scope.selectedSample = {};
        openDtlLayer(selectedRow);
        $scope.posExcclcDetailLayer.show(true, function(selectedRow){
        });
    };

    // 엑셀 다운로드
    $scope.excelDownloadDay = function () {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : false,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, messages["storeOpenCloseBenson.storeOpenCloseBenson"] + messages["storeOpenCloseBenson.day"] + '_' + $scope.flex.rows[0].dataItem.saleDate + '_(상세)' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);
