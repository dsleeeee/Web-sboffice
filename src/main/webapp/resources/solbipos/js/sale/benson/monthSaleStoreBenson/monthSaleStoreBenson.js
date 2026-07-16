/****************************************************************
 *
 * 파일명 : monthSaleStoreBenson.js
 * 설  명 : (벤슨) 간소화화면 > 월별매출(매장) JavaScript
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

/**
 *  월별매출(매장) 그리드 생성
 */
app.controller('monthSaleStoreBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthSaleStoreBensonCtrl', $scope, $http, false));

    // 조회월
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드

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
        dataItem.saleYm = messages["monthSaleStoreBenson.saleYm"];
        dataItem.storeCd = messages["monthSaleStoreBenson.storeCd"];
        dataItem.storeNm = messages["monthSaleStoreBenson.storeNm"];
        dataItem.totSaleAmt = messages["monthSaleStoreBenson.tot"];
        dataItem.totRealSaleAmt = messages["monthSaleStoreBenson.tot"];
        dataItem.totBillCnt = messages["monthSaleStoreBenson.tot"];
        dataItem.stinTotSaleAmt = messages["monthSaleStoreBenson.stin"];
        dataItem.stinRealSaleAmt = messages["monthSaleStoreBenson.stin"];
        dataItem.stinBillCnt = messages["monthSaleStoreBenson.stin"];
        dataItem.dlvrTotSaleAmt = messages["monthSaleStoreBenson.dlvr"];
        dataItem.dlvrRealSaleAmt = messages["monthSaleStoreBenson.dlvr"];
        dataItem.dlvrBillCnt = messages["monthSaleStoreBenson.dlvr"];
        dataItem.packTotSaleAmt = messages["monthSaleStoreBenson.pack"];
        dataItem.packRealSaleAmt = messages["monthSaleStoreBenson.pack"];
        dataItem.packBillCnt = messages["monthSaleStoreBenson.pack"];
        dataItem.baeminTotSaleAmt = messages["monthSaleStoreBenson.baemin"];
        dataItem.baeminRealSaleAmt = messages["monthSaleStoreBenson.baemin"];
        dataItem.baeminBillCnt = messages["monthSaleStoreBenson.baemin"];
        dataItem.yogiyoTotSaleAmt = messages["monthSaleStoreBenson.yogiyo"];
        dataItem.yogiyoRealSaleAmt = messages["monthSaleStoreBenson.yogiyo"];
        dataItem.yogiyoBillCnt = messages["monthSaleStoreBenson.yogiyo"];
        dataItem.coupangeatsTotSaleAmt = messages["monthSaleStoreBenson.coupangeats"];
        dataItem.coupangeatsRealSaleAmt = messages["monthSaleStoreBenson.coupangeats"];
        dataItem.coupangeatsBillCnt = messages["monthSaleStoreBenson.coupangeats"];
        dataItem.etcTotSaleAmt = messages["monthSaleStoreBenson.etc"];
        dataItem.etcRealSaleAmt = messages["monthSaleStoreBenson.etc"];
        dataItem.etcBillCnt = messages["monthSaleStoreBenson.etc"];

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
    $scope.$on("monthSaleStoreBensonCtrl", function (event, data) {
        $scope.searchMonthSaleStoreBensonList();
        event.preventDefault();
    });

    $scope.searchMonthSaleStoreBensonList = function () {
        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 12달 제한
        if (diffMonth > 12) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#monthSaleStoreBensonStoreCd").val();
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
        params.listScale = 500;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/benson/monthSaleStoreBenson/monthSaleStoreBenson/getMonthSaleStoreBensonList.sb", params, function (){

        });
    };
    // <-- //검색 호출 -->

    // 조회조건/분할 엑셀다운로드
    $scope.excelDownload = function (excelType) {
        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 12달 제한
        if (diffMonth > 12) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 파라미터
        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#monthSaleStoreBensonStoreCd").val();
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
        params.excelType = excelType;

        if(params.excelType === '1') {
            // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
            $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
                $scope._broadcast('monthSaleStoreBensonExcelCtrl', params);
            });
        }else{
            // 분할 엑셀다운로드 사용자 제한 체크
            $.postJSON('/sale/benson/prodSaleMonthStoreBenson/prodSaleMonthStoreBenson/getDivisionExcelDownloadUserIdChk.sb', params, function (response) {
                if (response.data.list === 0) {
                    $scope._popMsg(messages["prodSaleDayStoreMoms.userIdChkAlert"]); // 사용권한이 없습니다.
                    return;
                } else {
                    // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
                    $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
                        $scope._broadcast('monthSaleStoreBensonExcelCtrl', params);
                    });
                }
            });
        }
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload2 = function () {
        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 12달 제한
        if (diffMonth > 12) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
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
                "월별매출(매장)_" + wijmo.Globalize.format(startMonth.value, 'yyyyMM') + '_' + wijmo.Globalize.format(endMonth.value, 'yyyyMM') + '_' + getCurDateTime()+'.xlsx', function () {
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
app.controller('monthSaleStoreBensonExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monthSaleStoreBensonExcelCtrl', $scope, $http, false));

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
        dataItem.saleYm = messages["monthSaleStoreBenson.saleYm"];
        dataItem.storeCd = messages["monthSaleStoreBenson.storeCd"];
        dataItem.storeNm = messages["monthSaleStoreBenson.storeNm"];
        dataItem.totSaleAmt = messages["monthSaleStoreBenson.tot"];
        dataItem.totRealSaleAmt = messages["monthSaleStoreBenson.tot"];
        dataItem.totBillCnt = messages["monthSaleStoreBenson.tot"];
        dataItem.stinTotSaleAmt = messages["monthSaleStoreBenson.stin"];
        dataItem.stinRealSaleAmt = messages["monthSaleStoreBenson.stin"];
        dataItem.stinBillCnt = messages["monthSaleStoreBenson.stin"];
        dataItem.dlvrTotSaleAmt = messages["monthSaleStoreBenson.dlvr"];
        dataItem.dlvrRealSaleAmt = messages["monthSaleStoreBenson.dlvr"];
        dataItem.dlvrBillCnt = messages["monthSaleStoreBenson.dlvr"];
        dataItem.packTotSaleAmt = messages["monthSaleStoreBenson.pack"];
        dataItem.packRealSaleAmt = messages["monthSaleStoreBenson.pack"];
        dataItem.packBillCnt = messages["monthSaleStoreBenson.pack"];
        dataItem.baeminTotSaleAmt = messages["monthSaleStoreBenson.baemin"];
        dataItem.baeminRealSaleAmt = messages["monthSaleStoreBenson.baemin"];
        dataItem.baeminBillCnt = messages["monthSaleStoreBenson.baemin"];
        dataItem.yogiyoTotSaleAmt = messages["monthSaleStoreBenson.yogiyo"];
        dataItem.yogiyoRealSaleAmt = messages["monthSaleStoreBenson.yogiyo"];
        dataItem.yogiyoBillCnt = messages["monthSaleStoreBenson.yogiyo"];
        dataItem.coupangeatsTotSaleAmt = messages["monthSaleStoreBenson.coupangeats"];
        dataItem.coupangeatsRealSaleAmt = messages["monthSaleStoreBenson.coupangeats"];
        dataItem.coupangeatsBillCnt = messages["monthSaleStoreBenson.coupangeats"];
        dataItem.etcTotSaleAmt = messages["monthSaleStoreBenson.etc"];
        dataItem.etcRealSaleAmt = messages["monthSaleStoreBenson.etc"];
        dataItem.etcBillCnt = messages["monthSaleStoreBenson.etc"];

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
    $scope.$on("monthSaleStoreBensonExcelCtrl", function (event, data) {
        if(data.excelType === '1') {
            $scope.searchExcelList(data);
        } else {
            // 엑셀다운로드 진행 사용자 현재 인원수 체크
            data.downloadFg = "0"; // 다운로드 구분 (0:간소화화면, 1:상품매출분석)
            data.resrceCd = menuCd;
            data.resrceNm = menuNm;
            data.downloadUseFg = "2"; // 다운로드 사용기능 (0:전체다운로드, 1:조회조건다운로드, 2:분할다운로드)
            data.downloadNo = "11"; // 다운로드 화면구분번호

            $.postJSON('/sale/benson/prodSaleMonthStoreBenson/prodSaleMonthStoreBenson/getDivisionExcelDownloadCntChk.sb', data, function (response) {
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
                        $.postJSON("/sale/benson/prodSaleMonthStoreBenson/prodSaleMonthStoreBenson/getDivisionExcelDownloadSaveInsert.sb", params2, function(response){});

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
        $.postJSON("/sale/benson/monthSaleStoreBenson/monthSaleStoreBenson/getMonthSaleStoreBensonExcelList.sb", params, function(response) {
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
                }, "월별매출(매장)_" + params.startMonth + "_" + params.endMonth + "_" + getCurDateTime()+'.xlsx', function () {
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

        $.postJSON("/sale/benson/monthSaleStoreBenson/monthSaleStoreBenson/getMonthSaleStoreBensonList.sb", params, function(response){

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
            $.postJSON("/sale/benson/prodSaleMonthStoreBenson/prodSaleMonthStoreBenson/getDivisionExcelDownloadSaveInsert.sb", params, function(response){
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
                        $.postJSON("/sale/benson/prodSaleMonthStoreBenson/prodSaleMonthStoreBenson/getDivisionExcelDownloadSaveUpdate.sb", params, function(response){

                            // ajax 통신 설정
                            $.postJSON('/sale/benson/monthSaleStoreBenson/monthSaleStoreBenson/getMonthSaleStoreBensonExcelDivisionList.sb', params, function(response) {

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
                                    }, "월별매출(매장)_" + params.startMonth + "_" + params.endMonth + "_" + getCurDateTime() + '_' + (x + 1) + '.xlsx', function () {
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
