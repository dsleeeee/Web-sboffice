/****************************************************************
 *
 * 파일명 : daySaleStoreMoms.js
 * 설  명 : 맘스터치 > 간소화화면 > 일별매출(매장) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.12.29     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일별매출(매장) 그리드 생성
 */
app.controller('daySaleStoreMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('daySaleStoreMomsCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 콤보박스 셋팅
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
        dataItem.saleDate = messages["daySaleStoreMoms.saleDate"];
        dataItem.storeCd = messages["daySaleStoreMoms.storeCd"];
        dataItem.storeNm = messages["daySaleStoreMoms.storeNm"];
        dataItem.totSaleAmt = messages["daySaleStoreMoms.tot"];
        dataItem.totRealSaleAmt = messages["daySaleStoreMoms.tot"];
        dataItem.totBillCnt = messages["daySaleStoreMoms.tot"];
        dataItem.stinTotSaleAmt = messages["daySaleStoreMoms.stin"];
        dataItem.stinRealSaleAmt = messages["daySaleStoreMoms.stin"];
        dataItem.stinBillCnt = messages["daySaleStoreMoms.stin"];
        dataItem.dlvrTotSaleAmt = messages["daySaleStoreMoms.dlvr"];
        dataItem.dlvrRealSaleAmt = messages["daySaleStoreMoms.dlvr"];
        dataItem.dlvrBillCnt = messages["daySaleStoreMoms.dlvr"];
        dataItem.packTotSaleAmt = messages["daySaleStoreMoms.pack"];
        dataItem.packRealSaleAmt = messages["daySaleStoreMoms.pack"];
        dataItem.packBillCnt = messages["daySaleStoreMoms.pack"];
        dataItem.baeminTotSaleAmt = messages["daySaleStoreMoms.baemin"];
        dataItem.baeminRealSaleAmt = messages["daySaleStoreMoms.baemin"];
        dataItem.baeminBillCnt = messages["daySaleStoreMoms.baemin"];
        dataItem.yogiyoTotSaleAmt = messages["daySaleStoreMoms.yogiyo"];
        dataItem.yogiyoRealSaleAmt = messages["daySaleStoreMoms.yogiyo"];
        dataItem.yogiyoBillCnt = messages["daySaleStoreMoms.yogiyo"];
        dataItem.coupangeatsTotSaleAmt = messages["daySaleStoreMoms.coupangeats"];
        dataItem.coupangeatsRealSaleAmt = messages["daySaleStoreMoms.coupangeats"];
        dataItem.coupangeatsBillCnt = messages["daySaleStoreMoms.coupangeats"];
        dataItem.etcTotSaleAmt = messages["daySaleStoreMoms.etc"];
        dataItem.etcRealSaleAmt = messages["daySaleStoreMoms.etc"];
        dataItem.etcBillCnt = messages["daySaleStoreMoms.etc"];

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
    $scope.$on("daySaleStoreMomsCtrl", function (event, data) {
        $scope.searchDaySaleStoreMomsList();
        event.preventDefault();
    });

    $scope.searchDaySaleStoreMomsList = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#daySaleStoreMomsStoreCd").val();
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
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
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
        params.listScale = 500;

        // 페이징 처리
        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }
        // 가상로그인 대응한 session id 설정
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/moms/daySaleStoreMoms/daySaleStoreMoms/getDaySaleStoreMomsList.sb", params, function (response){
            var grid = $scope.flex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            var list = response.data.list;
            if (list.length === undefined || list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
                if (true && response.message) {
                    $scope._setPagingInfo('ctrlName', $scope.name);
                    $scope._setPagingInfo('pageScale', 10);
                    $scope._setPagingInfo('curr', 1);
                    $scope._setPagingInfo('totCnt', 1);
                    $scope._setPagingInfo('totalPage', 1);
                    $scope._broadcast('drawPager');
                    $scope._popMsg(response.message);
                }
                return false;
            }
            var data = new wijmo.collections.CollectionView(list);
            data.trackChanges = true;
            $scope.data = data;

            if (response.data.page && response.data.page.curr) {
                var pagingInfo = response.data.page;
                $scope._setPagingInfo('ctrlName', $scope.name);
                $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
                $scope._setPagingInfo('curr', pagingInfo.curr);
                $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
                $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
                $scope._broadcast('drawPager');
            }
        });
    };
    // <-- //검색 호출 -->

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 조회조건/분할 엑셀다운로드
    $scope.excelDownload = function (excelType) {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#daySaleStoreMomsStoreCd").val();
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
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
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
        params.excelType = excelType;

        if(params.excelType === '1') {
            // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
            $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
                $scope._broadcast('daySaleStoreMomsExcelCtrl', params);
            });
        }else{
            // 분할 엑셀다운로드 사용자 제한 체크
            $.postJSON('/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadUserIdChk.sb', params, function (response) {
                if (response.data.list === 0) {
                    $scope._popMsg(messages["prodSaleDayStoreMoms.userIdChkAlert"]); // 사용권한이 없습니다.
                    return;
                } else {
                    // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
                    $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
                        $scope._broadcast('daySaleStoreMomsExcelCtrl', params);
                    });
                }
            });
        }
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload2 = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

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
                "일별매출(매장)_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('daySaleStoreMomsExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('daySaleStoreMomsExcelCtrl', $scope, $http, false));

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
        var dataItem = {};
        dataItem.saleDate = messages["daySaleStoreMoms.saleDate"];
        dataItem.storeCd = messages["daySaleStoreMoms.storeCd"];
        dataItem.storeNm = messages["daySaleStoreMoms.storeNm"];
        dataItem.totSaleAmt = messages["daySaleStoreMoms.tot"];
        dataItem.totRealSaleAmt = messages["daySaleStoreMoms.tot"];
        dataItem.totBillCnt = messages["daySaleStoreMoms.tot"];
        dataItem.stinTotSaleAmt = messages["daySaleStoreMoms.stin"];
        dataItem.stinRealSaleAmt = messages["daySaleStoreMoms.stin"];
        dataItem.stinBillCnt = messages["daySaleStoreMoms.stin"];
        dataItem.dlvrTotSaleAmt = messages["daySaleStoreMoms.dlvr"];
        dataItem.dlvrRealSaleAmt = messages["daySaleStoreMoms.dlvr"];
        dataItem.dlvrBillCnt = messages["daySaleStoreMoms.dlvr"];
        dataItem.packTotSaleAmt = messages["daySaleStoreMoms.pack"];
        dataItem.packRealSaleAmt = messages["daySaleStoreMoms.pack"];
        dataItem.packBillCnt = messages["daySaleStoreMoms.pack"];
        dataItem.baeminTotSaleAmt = messages["daySaleStoreMoms.baemin"];
        dataItem.baeminRealSaleAmt = messages["daySaleStoreMoms.baemin"];
        dataItem.baeminBillCnt = messages["daySaleStoreMoms.baemin"];
        dataItem.yogiyoTotSaleAmt = messages["daySaleStoreMoms.yogiyo"];
        dataItem.yogiyoRealSaleAmt = messages["daySaleStoreMoms.yogiyo"];
        dataItem.yogiyoBillCnt = messages["daySaleStoreMoms.yogiyo"];
        dataItem.coupangeatsTotSaleAmt = messages["daySaleStoreMoms.coupangeats"];
        dataItem.coupangeatsRealSaleAmt = messages["daySaleStoreMoms.coupangeats"];
        dataItem.coupangeatsBillCnt = messages["daySaleStoreMoms.coupangeats"];
        dataItem.etcTotSaleAmt = messages["daySaleStoreMoms.etc"];
        dataItem.etcRealSaleAmt = messages["daySaleStoreMoms.etc"];
        dataItem.etcBillCnt = messages["daySaleStoreMoms.etc"];

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
    $scope.$on("daySaleStoreMomsExcelCtrl", function (event, data) {
        if(data.excelType === '1') {
            $scope.searchExcelList(data);
        } else {
            // 엑셀다운로드 진행 사용자 현재 인원수 체크
            data.downloadFg = "0"; // 다운로드 구분 (0:간소화화면, 1:상품매출분석)
            data.resrceCd = menuCd;
            data.resrceNm = menuNm;
            data.downloadUseFg = "2"; // 다운로드 사용기능 (0:전체다운로드, 1:조회조건다운로드, 2:분할다운로드)
            data.downloadNo = "7"; // 다운로드 화면구분번호

            $.postJSON('/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadCntChk.sb', data, function (response) {
                if (response.data.list === 0) {
                } else {
                    var msgCntChk = response.data.list; // 00:0명의 사용자 다운로드 중
                    if(msgCntChk.substr(0, 2) === "00") {
                        $scope.searchExcelDivisionList(data);
                    } else {
                        // 엑셀다운로드 진행 사용자 저장 insert
                        var params2 = data;
                        params2.resrceNm = "실패:" + menuNm;
                        params2.downloadFileCount = 0; // 다운로드 파일수
                        $.postJSON("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveInsert.sb", params2, function(response){});

                        $scope._popMsg(msgCntChk); // 다운로드 사용량이 초과되어 대기중입니다. 잠시 후 다시 진행하여 주십시오.
                        return;
                    }
                }
            });
        }
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/moms/daySaleStoreMoms/daySaleStoreMoms/getDaySaleStoreMomsExcelList.sb", params, function(response) {
            var grid = $scope.excelFlex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "일별매출(매장)_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
    // <-- //검색 호출 -->

    // 분할 엑셀 리스트 조회
    $scope.searchExcelDivisionList = function (params) {
        // 다운로드 시작이면 작업내역 로딩 팝업 오픈
        $scope.excelUploadingPopup(true);
        $("#totalRows").html(0);

        // 전체 데이터 수
        var listSize = 0;
        // 다운로드 되는 총 엑셀파일 수
        var totFileCnt = 0;

        // 전체 데이터 수 조회
        params.limit = 1;
        params.offset = 1;

        $.postJSON("/sale/moms/daySaleStoreMoms/daySaleStoreMoms/getDaySaleStoreMomsList.sb", params, function(response){

            listSize = response.data.list[0].totCnt;
            totFileCnt = Math.ceil(listSize/15000); // 하나의 엑셀파일에 15000개씩 다운로드

            if(listSize === 0 || totFileCnt === 0){
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                $scope.excelUploadingPopup(false);
                return false;
            };

            // 다운로드 될 전체 파일 갯수 셋팅
            $("#totalRows").html(totFileCnt);

            // 엑셀다운로드 진행 사용자 저장 insert
            params.downloadFileCount = totFileCnt; // 다운로드 파일수
            $.postJSON("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveInsert.sb", params, function(response){
                var seq = response.data.list; // 순번

                // 엑셀 분할 다운로드
                function delay(x){
                    return new Promise(function(resolve, reject){
                        console.log("setTimeout  > i=" + x + " x=" + x);

                        // 다운로드 진행중인 파일 숫자 변경
                        $("#progressCnt").html(x + 1);

                        // 페이징 15000개씩 지정해 분할 다운로드 진행
                        params.limit = 15000 * (x + 1);
                        params.offset = (15000 * (x + 1)) - 14999;

                        // 가상로그인 대응한 session id 설정
                        if (document.getElementsByName('sessionId')[0]) {
                            params['sid'] = document.getElementsByName('sessionId')[0].value;
                        }

                        // 엑셀다운로드 진행 사용자 저장 update
                        params.seq = seq;
                        $.postJSON("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveUpdate.sb", params, function(response){

                            // ajax 통신 설정
                            $.postJSON('/sale/moms/daySaleStoreMoms/daySaleStoreMoms/getDaySaleStoreMomsExcelDivisionList.sb', params, function(response) {

                                if(response.status === "FAIL") {
                                    s_alert.pop(response.message);
                                    $scope.excelFlex.itemsSource = new wijmo.collections.CollectionView([]);
                                    $scope.excelUploadingPopup(false);
                                    return;
                                }

                                var list = response.data.list;
                                if (list.length === undefined || list.length === 0) {
                                    $scope.data = new wijmo.collections.CollectionView([]);
                                    $scope.excelUploadingPopup(false);
                                    return false;
                                }

                                $scope.excelFlex.itemsSource = list;
                                $scope.excelFlex.itemsSource.trackChanges = true;

                                var data = new wijmo.collections.CollectionView(list);
                                data.trackChanges = true;
                                $scope.data = data;

                                setTimeout(function() {
                                    if ($scope.excelFlex.rows.length <= 0) {
                                        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                                        $scope.excelUploadingPopup(false);
                                        return false;
                                    }

                                    wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                                        includeColumnHeaders: true,
                                        includeCellStyles: false,
                                        includeColumns: function (column) {
                                            return column.visible;
                                        }
                                    }, "일별매출(매장)_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime() + '_' + (x + 1) + '.xlsx', function () {
                                        $timeout(function () {
                                            console.log("Export complete start. _" + (x + 1));
                                            getExcelFile(x + 1);
                                        }, 500);
                                    }, function (reason) { // onError
                                        // User can catch the failure reason in this callback.
                                        console.log('The reason of save failure is ' + reason + "_" + (x + 1));
                                        $scope.excelUploadingPopup(false);
                                    });

                                }, 1000);
                            });
                            resolve(x);
                            
                        });
                    });
                };

                async function getExcelFile(x) {
                    if(totFileCnt > x){
                        await delay(x);
                    }else{
                        $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                    }
                };

                // 엑셀 분할 다운로드 시작
                getExcelFile(0);

            });
        });
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.progress'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 다운로드 진행 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };

}]);