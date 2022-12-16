/****************************************************************
 *
 * 파일명 : storeOpenCloseDay.js
 * 설  명 : 맘스터치 > 점포매출 > 매장 오픈/마감 현황 - 일별 JavaScript
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

/** 매장 오픈/마감 현황 - 일별 controller */
app.controller('storeOpenCloseDayTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOpenCloseDayTimeCtrl', $scope, $http, $timeout, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#dayStartDate", gvStartDate);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 지사

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.storeOpenCloseDayStoreShow = function () {
        $scope._broadcast('storeOpenCloseDayStoreCtrl');
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeOpenCloseDayTimeCtrl", function (event, data) {

        // 파라미터
        var params = {};
        params.saleDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.storeCds = $("#storeOpenCloseDayStoreCd").val();
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
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

        var scope = agrid.getScope("storeOpenCloseDayCtrl");
        // 일별 조회
        scope.searchStoreOpenCloseDay(params);
        var scope2 = agrid.getScope('storeOpenCloseDayDtlCtrl');
        scope2._gridDataInit();   // 그리드 초기화
        event.preventDefault();
    });

    }]);

app.controller('storeOpenCloseDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOpenCloseDayCtrl', $scope, $http, $timeout, true));

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
                    var scope = agrid.getScope("storeOpenCloseDayTimeCtrl");
                    var params = {};
                    params.saleDate = selectedRow.saleDate;
                    params.min = selectedRow.min;
                    params.max = selectedRow.max;
                    params.gubun = col.binding;
                    params.storeCds = $("#storeOpenCloseDayStoreCd").val();
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

                    $scope._broadcast('storeOpenCloseDayDtlCtrl', params);
                }
            }
        });


        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem	    = {};
        dataItem.time		= messages["storeOpenClose.time"];
        dataItem.openCnt    = messages["storeOpenClose.openCnt"];
        dataItem.closeCnt   = messages["storeOpenClose.closeCnt"];
        dataItem.open		= messages["storeOpenClose.dtlStoreList"];
        dataItem.close		= messages["storeOpenClose.dtlStoreList"];
        dataItem.none	    = messages["storeOpenClose.dtlStoreList"];

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
    $scope.$on("storeOpenCloseDayCtrl", function (event, data) {

        event.preventDefault();
    });

    // 일별 조회
    $scope.searchStoreOpenCloseDay = function(data){
        // 파라미터
        var params = {};
        params.saleDate = data.saleDate; // 조회기간
        params.optionFg = data.optionFg;
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
        $scope._inquiryMain("/sale/store/storeOpenClose/storeOpenClose/getStoreOpenCloseDayList.sb", params, function () {
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
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, messages["storeOpenCloseMoms.storeOpenCloseMoms"] + messages["storeOpenClose.day"] + '_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);


/** 주문채널별현황 - 일별 controller */
app.controller('storeOpenCloseDayDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeOpenCloseDayDtlCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

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
        dataItem.storeCd		= messages["storeOpenClose.storeCd"];
        dataItem.storeNm		= messages["storeOpenClose.storeNm"];
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
    $scope.$on("storeOpenCloseDayDtlCtrl", function (event, data) {

        // 일별 조회
        $scope.searchStoreOpenCloseDayDtl(data);
        event.preventDefault();
    });

    // 일별 조회 상세
    $scope.searchStoreOpenCloseDayDtl = function(data){

        // 파라미터
        var params = {};
        params.saleDate = data.saleDate;
        params.min = data.min;
        params.max = data.max;
        params.gubun = data.gubun;
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
        $scope._inquiryMain("/sale/store/storeOpenClose/storeOpenClose/getStoreOpenCloseDayDtlList.sb", params, function () {

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
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, messages["storeOpenCloseMoms.storeOpenCloseMoms"] + messages["storeOpenClose.day"] + '_(상세)' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);