/****************************************************************
 *
 * 파일명 : giftCalc.js
 * 설  명 : 지류상품권 정산 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.08.30     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 승인구분
var saleFgComboData = [
    {"name":"전체","value":""},
    {"name":"승인","value":"1"},
    {"name":"취소","value":"-1"}
];

/**
 *  지류상품권 현황 그리드 생성
 */
app.controller('giftCalcCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('giftCalcCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 콤보박스 셋팅
    $scope._setComboData("saleFgCombo", saleFgComboData); // 승인구분

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.brandCdDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "storeNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "storeNm") {
                    var params = {};
                    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
                    params.storeCd = selectedRow.storeCd;
                    params.posNo = $("#giftCalcSelectPosCd").val();
                    params.saleFg = $scope.saleFg;
                    params.momsTeam = selectedRow.momsTeam;
                    params.momsAcShop = selectedRow.momsAcShop;
                    params.momsAreaFg = $scope.momsAreaFg;
                    params.momsCommercial = $scope.momsCommercial;
                    params.momsShopType = $scope.momsShopType;
                    params.momsStoreManageType = $scope.momsStoreManageType;
                    params.branchCd = selectedRow.branchCd;
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
                    params.momsStoreFg01 = $scope.momsStoreFg01;
                    params.momsStoreFg02 = $scope.momsStoreFg02;
                    params.momsStoreFg03 = $scope.momsStoreFg03;
                    params.momsStoreFg04 = $scope.momsStoreFg04;
                    params.momsStoreFg05 = $scope.momsStoreFg05;

                    $scope._broadcast('giftCalcDtlCtrl', params);
                    $scope.wjGiftCalcDtlLayer.show(true);
                }
            }
        });

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.branchCd = messages["giftCalc.branchCd"];
        dataItem.branchNm = messages["giftCalc.branchNm"];
        dataItem.storeCd = messages["giftCalc.storeCd"];
        dataItem.storeNm = messages["giftCalc.storeNm"];
        dataItem.brandCd = messages["giftCalc.brandCd"];
        dataItem.momsTeam = messages["cmm.moms.momsTeam"];
        dataItem.momsAcShop = messages["cmm.moms.momsAcShop"];
        dataItem.giftCd = messages["giftCalc.giftCd"];
        dataItem.giftNm = messages["giftCalc.giftNm"];
        dataItem.saleCnt = messages["cmm.all"];
        dataItem.saleAmt = messages["cmm.all"];
        dataItem.apprCnt = messages["giftCalc.appr"];
        dataItem.apprAmt = messages["giftCalc.appr"];
        dataItem.cancelCnt = messages["giftCalc.cancel"];
        dataItem.cancelAmt = messages["giftCalc.cancel"];

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
    $scope.$on("giftCalcCtrl", function(event, data) {
        $scope.searchGiftCalc();
        event.preventDefault();
    });

    $scope.searchGiftCalc = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#giftCalcStoreCd").val();
        params.posNo = $("#giftCalcSelectPosCd").val();
        params.saleFg = $scope.saleFg;
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
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
        params.listScale = 500;

        $scope._inquiryMain("/sale/status/giftCalc/giftCalc/getGiftCalcList.sb", params, function() {
            // <-- 그리드 visible -->
            var grid = wijmo.Control.getControl("#wjGridGiftCalc");
            var columns = grid.columns;

            // 조회조건 '승인구분'에 따른 컴럼 show/hidden 처리
            if(orgnFg === "HQ") {
                if($scope.saleFg === '1') {
                    columns[11].visible = true;
                    columns[12].visible = true;
                    columns[13].visible = false;
                    columns[14].visible = false;
                } else if($scope.saleFg === '-1') {
                    columns[11].visible = false;
                    columns[12].visible = false;
                    columns[13].visible = true;
                    columns[14].visible = true;
                } else {
                    columns[11].visible = true;
                    columns[12].visible = true;
                    columns[13].visible = true;
                    columns[14].visible = true;
                }
            } else {
                if($scope.saleFg === '1') {
                    columns[6].visible = true;
                    columns[7].visible = true;
                    columns[8].visible = false;
                    columns[9].visible = false;
                } else if($scope.saleFg === '-1') {
                    columns[6].visible = false;
                    columns[7].visible = false;
                    columns[8].visible = true;
                    columns[9].visible = true;
                } else {
                    columns[6].visible = true;
                    columns[7].visible = true;
                    columns[8].visible = true;
                    columns[9].visible = true;
                }
            }
            // <-- //그리드 visible -->
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.giftCalcStoreShow = function () {
        $scope._broadcast('giftCalcStoreCtrl');
    };

    //포스선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.giftCalcSelectPosShow = function () {
        $scope._broadcast('giftCalcSelectPosCtrl');
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#giftCalcStoreCd").val();
        params.posNo = $("#giftCalcSelectPosCd").val();
        params.saleFg = $scope.saleFg;
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
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;

        $scope._broadcast('giftCalcExcelCtrl', params);
    };

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('giftCalcExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('giftCalcExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.brandCdDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.branchCd = messages["giftCalc.branchCd"];
        dataItem.branchNm = messages["giftCalc.branchNm"];
        dataItem.storeCd = messages["giftCalc.storeCd"];
        dataItem.storeNm = messages["giftCalc.storeNm"];
        dataItem.brandCd = messages["giftCalc.brandCd"];
        dataItem.momsTeam = messages["cmm.moms.momsTeam"];
        dataItem.momsAcShop = messages["cmm.moms.momsAcShop"];
        dataItem.giftCd = messages["giftCalc.giftCd"];
        dataItem.giftNm = messages["giftCalc.giftNm"];
        dataItem.saleCnt = messages["cmm.all"];
        dataItem.saleAmt = messages["cmm.all"];
        dataItem.apprCnt = messages["giftCalc.appr"];
        dataItem.apprAmt = messages["giftCalc.appr"];
        dataItem.cancelCnt = messages["giftCalc.cancel"];
        dataItem.cancelAmt = messages["giftCalc.cancel"];

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
    $scope.$on("giftCalcExcelCtrl", function(event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/giftCalc/giftCalc/getGiftCalcExcelList.sb", params, function (){

            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // <-- 그리드 visible -->
            var grid = wijmo.Control.getControl("#wjGridGiftCalcExcel");
            var columns = grid.columns;

            // 조회조건 '승인구분'에 따른 컴럼 show/hidden 처리
            if(orgnFg === "HQ") {
                if(params.saleFg === '1') {
                    columns[11].visible = true;
                    columns[12].visible = true;
                    columns[13].visible = false;
                    columns[14].visible = false;
                } else if(params.saleFg === '-1') {
                    columns[11].visible = false;
                    columns[12].visible = false;
                    columns[13].visible = true;
                    columns[14].visible = true;
                } else {
                    columns[11].visible = true;
                    columns[12].visible = true;
                    columns[13].visible = true;
                    columns[14].visible = true;
                }
            } else {
                if(params.saleFg === '1') {
                    columns[6].visible = true;
                    columns[7].visible = true;
                    columns[8].visible = false;
                    columns[9].visible = false;
                } else if(params.saleFg === '-1') {
                    columns[6].visible = false;
                    columns[7].visible = false;
                    columns[8].visible = true;
                    columns[9].visible = true;
                } else {
                    columns[6].visible = true;
                    columns[7].visible = true;
                    columns[8].visible = true;
                    columns[9].visible = true;
                }
            }
            // <-- //그리드 visible -->

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    includeColumns: function (column) {
                        return column.visible;
                    }
                },  '지류상품권 정산' + '_' + params.startDate + '_' + params.endDate + '_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
    // <-- //검색 호출 -->

}]);