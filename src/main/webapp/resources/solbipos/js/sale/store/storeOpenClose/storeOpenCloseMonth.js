/****************************************************************
 *
 * 파일명 : storeOpenCloseMonth.js
 * 설  명 : 맘스터치 > 점포매출 > 매장 오픈/마감 현황 - 월별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.11     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 매장 오픈/마감 현황 - 월별 controller */
app.controller('storeOpenCloseMonthTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOpenCloseMonthTimeCtrl', $scope, $http, $timeout, true));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#monthStartDate', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#monthEndDate', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo2", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo2", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo2", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo2", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo2", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo2", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo2", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo2", branchCdComboList); // 지사

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeOpenCloseMonthTimeCtrl", function (event, data) {

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM') + '01'; // 조회기간
        params.endDate = wijmo.Globalize.format(endMonth.value, 'yyyyMM') + '31'; // 조회기간
        params.storeCds = $("#storeOpenCloseMonthStoreCd").val();
        params.momsTeam = $scope.momsTeam2;
        params.momsAcShop = $scope.momsAcShop2;
        params.momsAreaFg = $scope.momsAreaFg2;
        params.momsCommercial = $scope.momsCommercial2;
        params.momsShopType = $scope.momsShopType2;
        params.momsStoreManageType = $scope.momsStoreManageType2;
        params.branchCd = $scope.branchCd2;
        if($scope.storeHqBrandCd2 === undefined) {
            params.storeHqBrandCd = null;
        } else {
            params.storeHqBrandCd = $scope.storeHqBrandCd2;
        }
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

        var scope = agrid.getScope("storeOpenCloseMonthCtrl");
        // 월별 조회
        scope.searchStoreOpenCloseMonth(params);
        var scope2 = agrid.getScope('storeOpenCloseMonthDtlCtrl');
        scope2._gridDataInit();   // 그리드 초기화
        event.preventDefault();
    });

    // 확장조회 숨김/보임
    $scope.searchAddShowChange2 = function(){
        if( $("#tblSearchAddShow2").css("display") === 'none') {
            $("#tblSearchAddShow2").show();
        } else {
            $("#tblSearchAddShow2").hide();
        }
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.storeOpenCloseMonthStoreShow = function () {
        $scope._broadcast('storeOpenCloseMonthStoreCtrl');
    };
}]);

/** 매장 오픈/마감 현황 - 월별 controller */
app.controller('storeOpenCloseMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOpenCloseMonthCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "yyyymmdd") {
                    var item = s.rows[e.row].dataItem;
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
                if (col.binding === "yyyymmdd") {
                    var scope = agrid.getScope("storeOpenCloseMonthTimeCtrl");
                    var params = {};
                    params.saleDate = selectedRow.yyyymmdd;
                    params.storeCds = $("#storeOpenCloseMonthStoreCd").val();
                    params.momsTeam = scope.momsTeam;
                    params.momsAcShop = scope.momsAcShop;
                    params.momsAreaFg = scope.momsAreaFg;
                    params.momsCommercial = scope.momsCommercial;
                    params.momsShopType = scope.momsShopType;
                    params.momsStoreManageType = scope.momsStoreManageType;
                    params.branchCd = scope.branchCd;
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

                    $scope._broadcast('storeOpenCloseMonthDtlCtrl', params);
                }
            }
        });

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeOpenCloseMonthCtrl", function (event, data) {

        event.preventDefault();
    });

    // 월별 조회
    $scope.searchStoreOpenCloseMonth = function(data){

        // 파라미터
        var params = {};
        params.startDate = data.startDate; // 조회기간
        params.endDate = data.endDate; // 조회기간
        params.storeCds = data.storeCds;
        params.momsTeam = data.momsTeam;
        params.momsAcShop = data.momsAcShop;
        params.momsAreaFg = data.momsAreaFg;
        params.momsCommercial = data.momsCommercial;
        params.momsShopType = data.momsShopType;
        params.momsStoreManageType = data.momsStoreManageType;
        params.branchCd = data.branchCd;
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
        $scope._inquiryMain("/sale/store/storeOpenClose/storeOpenClose/getStoreOpenCloseMonthList.sb", params, function () {
        });
    };
    
    // 엑셀 다운로드
    $scope.excelDownloadMonth = function () {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, messages["storeOpenCloseMoms.storeOpenCloseMoms"] + messages["storeOpenClose.month"] + '_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);

/** 주문채널별현황 - 월별 상세 controller */
app.controller('storeOpenCloseMonthDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOpenCloseMonthDtlCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

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
                        selectedRow.hqOfficeCd = hqOfficeCd;
                        selectedRow.regSeq = '00';
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
        dataItem.saleDate		= messages["storeOpenClose.date"];
        dataItem.storeCd		= messages["storeOpenClose.storeCd"];
        dataItem.storeNm		= messages["storeOpenClose.storeNm"];
        dataItem.brand          = messages["dayProd.brand"];
        dataItem.momsTeam       = messages["dayProd.momsTeam"];
        dataItem.momsAcShop     = messages["dayProd.momsAcShop"];
        dataItem.openTime		= messages["storeOpenClose.openTime"];
        dataItem.closeTime		= messages["storeOpenClose.closeTime"];
        dataItem.runTime		= messages["storeOpenClose.runTime"];
        dataItem.posNo			= messages["storeOpenClose.posNo"];
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
    $scope.$on("storeOpenCloseMonthDtlCtrl", function (event, data) {

        // 월별 조회
        $scope.searchStoreOpenCloseMonthDtl(data);
        event.preventDefault();
    });

    // 월별 조회
    $scope.searchStoreOpenCloseMonthDtl = function(data){

        // 파라미터
        var params = {};
        params.saleDate = data.saleDate.replaceAll("-", "");
        params.storeCds = data.storeCds;
        params.momsTeam = data.momsTeam;
        params.momsAcShop = data.momsAcShop;
        params.momsAreaFg = data.momsAreaFg;
        params.momsCommercial = data.momsCommercial;
        params.momsShopType = data.momsShopType;
        params.momsStoreManageType = data.momsStoreManageType;
        params.branchCd = data.branchCd;
        if(data.storeHqBrandCd2 === undefined) {
            params.storeHqBrandCd = null;
        } else {
            params.storeHqBrandCd = data.storeHqBrandCd2;
        }
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
        $scope._inquiryMain("/sale/store/storeOpenClose/storeOpenClose/getStoreOpenCloseMonthDtlList.sb", params, function () {

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
    $scope.excelDownloadMonth = function () {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, messages["storeOpenCloseMoms.storeOpenCloseMoms"] + messages["storeOpenClose.month"] + '_(상세)' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);