/****************************************************************
 *
 * 파일명 : prodSalePmixMoms.js
 * 설  명 : 맘스터치 > 간소화화면 > 상품매출(P.MIX) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.12.19     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 일/월 구분
var dayGubunComboData = [
    {"name": "일", "value": "day"},
    {"name": "월", "value": "month"}
];

/**
 *  상품매출(P.MIX) 그리드 생성
 */
app.controller('prodSalePmixMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSalePmixMomsCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 조회일자(월)
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 콤보박스 셋팅
    $scope._setComboData("srchDayGubun", dayGubunComboData);  // 일/월 구분
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("prodSalePmixMomsCtrl", function (event, data) {
        $scope.searchProdSalePmixMomsList();
        event.preventDefault();
    });

    $scope.searchProdSalePmixMomsList = function () {
        // 기간제한 체크
        if($scope.srchDayGubunCombo.selectedValue === "day") {

            var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 한달(31일) 제한
            if (diffDay > 31) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }

        } else if($scope.srchDayGubunCombo.selectedValue === "month") {

            var startDt = new Date(wijmo.Globalize.format(startMonth.value + "-01", 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format(endMonth.value + "-01", 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 12개월 제한
            if (diffDay > 365) {
                s_alert.pop(messages['cmm.dateOver.1year.error']);
                return false;
            }
        }

        // 파라미터
        var params = {};
        params.dayGubun = $scope.srchDayGubunCombo.selectedValue;
        if (params.dayGubun === "day") {
            params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        } else if (params.dayGubun === "month") {
            params.startDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
            params.endDate = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        }
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#prodSalePmixMomsStoreCd").val();
        params.prodCds = $("#prodSalePmixMomsSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.listScale = 500;

        // 페이징 처리
        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/sale/moms/prodSalePmixMoms/prodSalePmixMoms/getProdSalePmixMomsList.sb", params, function (response) {
            // <-- 그리드 생성 -->
            var list = response.data.data.list;
            var length = response.data.data.list.length;
            var grid = wijmo.Control.getControl("#wjGridList");

            // 페이징 처리
            if (list.length === undefined || list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
                if (response.data.message) {
                    $scope._setPagingInfo('ctrlName', $scope.name);
                    $scope._setPagingInfo('pageScale', 10);
                    $scope._setPagingInfo('curr', 1);
                    $scope._setPagingInfo('totCnt', 1);
                    $scope._setPagingInfo('totalPage', 1);

                    $scope._broadcast('drawPager');
                    $scope._popMsg(response.data.message);
                }
                return false;
            }

            // rows, footer 초기화
            grid.rows.clear();
            grid.columnFooters.rows.clear();

            if(length > 0){

                // 동적 컬럼 초기화
                if (length != "" || length != null) {
                    while (grid.columns.length > 2) {
                        grid.columns.removeAt(grid.columns.length - 1);
                    }
                }

                // 기간선택 두 날짜 사이 모든날짜 구하기
                // ajax 통신 설정
                $http({
                    method : 'POST', //방식
                    url    : "/sale/moms/prodSalePmixMoms/prodSalePmixMoms/getDateDiff.sb", /* 통신할 URL */
                    params : params, /* 파라메터로 보낼 데이터 */
                    headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
                }).then(function successCallback(response) {
                    if ($scope._httpStatusCheck(response, true)) {

                        var dateArr = response.data.data.list;

                        // 날짜 형태 정규식
                        var regex = {};
                        if(params.dayGubun === "day"){
                            regex[0] = /(\d{4})(\d{2})(\d{2})/g;
                            regex[1] = '$1-$2-$3';
                        }else if(params.dayGubun === "month"){
                            regex[0] = /(\d{4})(\d{2})/g;
                            regex[1] = '$1-$2';
                        }

                        // 판매수량
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "saleQty" +  dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["prodSalePmixMoms.total"],
                            binding: "totSaleQty",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // 실매출액
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "realSaleAmt" + dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["prodSalePmixMoms.total"],
                            binding: "totRealSaleAmt",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // P.MIX
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "pMix" + dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["prodSalePmixMoms.total"],
                            binding: "totPMix",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // 데이터 뿌리기
                        var data = new wijmo.collections.CollectionView(list);
                        data.trackChanges = true;
                        $scope.data = data;

                        // grid merge 가능 영역
                        // All:= 7 (Merge all areas), AllHeaders:= 6(Merge column and row headers), Cells:= 1(Merge scrollable cells),
                        // ColumnHeaders:= 2(Merge column headers), None:= 0(No merging), RowHeaders:= 4(Merge row headers)
                        grid.allowMerging = 'ColumnHeaders';
                        // grid header 라인 기본 2줄 유지
                        if(2 > grid.columnHeaders.rows.length){
                            grid.columnHeaders.rows.push(new wijmo.grid.Row());
                        }

                        // 첫째줄 헤더 생성
                        var dataItem = {};
                        dataItem.prodClassNm = messages["prodSalePmixMoms.prodClassNm"];
                        dataItem.prodNm = messages["prodSalePmixMoms.prodNm"];

                        for (var i = 0; i < dateArr.length; i++) {
                            eval('dataItem.saleQty' + dateArr[i].sOrgDate + '= "판매수량"');
                        }
                        dataItem.totSaleQty = "판매수량";

                        for (var i = 0; i < dateArr.length; i++) {
                            eval('dataItem.realSaleAmt' + dateArr[i].sOrgDate + '= "실매출액"');
                        }
                        dataItem.totRealSaleAmt = "실매출액";

                        for (var i = 0; i < dateArr.length; i++) {
                            eval('dataItem.pMix' + dateArr[i].sOrgDate + '= "P.MIX"');
                        }
                        dataItem.totPMix = "P.MIX";

                        grid.columnHeaders.rows[0].dataItem = dataItem;

                        grid.itemFormatter = function (panel, r, c, cell) {

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
                                var rows = panel.rows[r];

                                if (col.isReadOnly) {
                                    wijmo.addClass(cell, 'wj-custom-readonly');
                                }
                            }
                        };
                    }
                }, function errorCallback(response) {
                    $scope._popMsg(messages["cmm.error"]);
                    return false;
                }).then(function () {
                    if (typeof callback === 'function') {
                        $timeout(function () {
                            callback();
                        }, 10);
                    }
                });
            }

            // 페이징 처리
            if (response.data.data.page && response.data.data.page.curr) {
                var pagingInfo = response.data.data.page;
                $scope._setPagingInfo('ctrlName', $scope.name);
                $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
                $scope._setPagingInfo('curr', pagingInfo.curr);
                $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
                $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
                $scope._broadcast('drawPager');
            }
            // <-- //그리드 생성 -->
        });
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.prodSalePmixMomsStoreShow = function () {
        $scope._broadcast('prodSalePmixMomsStoreCtrl');
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.prodSalePmixMomsSelectShow = function () {
        $scope._broadcast('prodSalePmixMomsSelectCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    // 일/월 구분 콤보박스 선택 이벤트
    $scope.setDayGubunCombo = function (s) {
        if (s.selectedValue === "day") {
            $("#spanDay").css("display", "");
            $("#spanMonth").css("display", "none");
        } else if (s.selectedValue === "month") {
            $("#spanDay").css("display", "none");
            $("#spanMonth").css("display", "");
        }
    };

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
        // 기간제한 체크
        if($scope.srchDayGubunCombo.selectedValue === "day") {

            var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 한달(31일) 제한
            if (diffDay > 31) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }

        } else if($scope.srchDayGubunCombo.selectedValue === "month") {

            var startDt = new Date(wijmo.Globalize.format(startMonth.value + "-01", 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format(endMonth.value + "-01", 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 12개월 제한
            if (diffDay > 365) {
                s_alert.pop(messages['cmm.dateOver.1year.error']);
                return false;
            }
        }

        var params = {};
        params.dayGubun = $scope.srchDayGubunCombo.selectedValue;
        if (params.dayGubun === "day") {
            params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        } else if (params.dayGubun === "month") {
            params.startDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
            params.endDate = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        }
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#prodSalePmixMomsStoreCd").val();
        params.prodCds = $("#prodSalePmixMomsSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.excelType = excelType;

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('prodSalePmixMomsExcelCtrl', params);
        });
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload2 = function () {
        // 기간제한 체크
        if($scope.srchDayGubunCombo.selectedValue === "day") {

            var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 한달(31일) 제한
            if (diffDay > 31) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }

        } else if($scope.srchDayGubunCombo.selectedValue === "month") {

            var startDt = new Date(wijmo.Globalize.format(startMonth.value + "-01", 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format(endMonth.value + "-01", 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 12개월 제한
            if (diffDay > 365) {
                s_alert.pop(messages['cmm.dateOver.1year.error']);
                return false;
            }
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
                "상품매출(P.MIX)_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
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
app.controller('prodSalePmixMomsExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSalePmixMomsExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("prodSalePmixMomsExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/sale/moms/prodSalePmixMoms/prodSalePmixMoms/getProdSalePmixMomsExcelList.sb", params, function (response) {
            if (response.data.data.list.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // <-- 그리드 생성 -->
            var list = response.data.data.list;
            var length = response.data.data.list.length;
            var grid = wijmo.Control.getControl("#wjGridExcelList");

            // rows, footer 초기화
            grid.rows.clear();
            grid.columnFooters.rows.clear();

            if(length > 0){

                // 동적 컬럼 초기화
                if (length != "" || length != null) {
                    while (grid.columns.length > 2) {
                        grid.columns.removeAt(grid.columns.length - 1);
                    }
                }

                // 기간선택 두 날짜 사이 모든날짜 구하기
                // ajax 통신 설정
                $http({
                    method : 'POST', //방식
                    url    : "/sale/moms/prodSalePmixMoms/prodSalePmixMoms/getDateDiff.sb", /* 통신할 URL */
                    params : params, /* 파라메터로 보낼 데이터 */
                    headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
                }).then(function successCallback(response) {
                    if ($scope._httpStatusCheck(response, true)) {

                        var dateArr = response.data.data.list;

                        // 날짜 형태 정규식
                        var regex = {};
                        if(params.dayGubun === "day"){
                            regex[0] = /(\d{4})(\d{2})(\d{2})/g;
                            regex[1] = '$1-$2-$3';
                        }else if(params.dayGubun === "month"){
                            regex[0] = /(\d{4})(\d{2})/g;
                            regex[1] = '$1-$2';
                        }

                        // 판매수량
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "saleQty" +  dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["prodSalePmixMoms.total"],
                            binding: "totSaleQty",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // 실매출액
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "realSaleAmt" + dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["prodSalePmixMoms.total"],
                            binding: "totRealSaleAmt",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // P.MIX
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "pMix" + dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["prodSalePmixMoms.total"],
                            binding: "totPMix",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // 데이터 뿌리기
                        var data = new wijmo.collections.CollectionView(list);
                        data.trackChanges = true;
                        $scope.data = data;

                        // grid merge 가능 영역
                        // All:= 7 (Merge all areas), AllHeaders:= 6(Merge column and row headers), Cells:= 1(Merge scrollable cells),
                        // ColumnHeaders:= 2(Merge column headers), None:= 0(No merging), RowHeaders:= 4(Merge row headers)
                        grid.allowMerging = 'ColumnHeaders';
                        // grid header 라인 기본 2줄 유지
                        if(2 > grid.columnHeaders.rows.length){
                            grid.columnHeaders.rows.push(new wijmo.grid.Row());
                        }

                        // 첫째줄 헤더 생성
                        var dataItem = {};
                        dataItem.prodClassNm = messages["prodSalePmixMoms.prodClassNm"];
                        dataItem.prodNm = messages["prodSalePmixMoms.prodNm"];

                        for (var i = 0; i < dateArr.length; i++) {
                            eval('dataItem.saleQty' + dateArr[i].sOrgDate + '= "판매수량"');
                        }
                        dataItem.totSaleQty = "판매수량";

                        for (var i = 0; i < dateArr.length; i++) {
                            eval('dataItem.realSaleAmt' + dateArr[i].sOrgDate + '= "실매출액"');
                        }
                        dataItem.totRealSaleAmt = "실매출액";

                        for (var i = 0; i < dateArr.length; i++) {
                            eval('dataItem.pMix' + dateArr[i].sOrgDate + '= "P.MIX"');
                        }
                        dataItem.totPMix = "P.MIX";

                        grid.columnHeaders.rows[0].dataItem = dataItem;

                        grid.itemFormatter = function (panel, r, c, cell) {

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
                                var rows = panel.rows[r];

                                if (col.isReadOnly) {
                                    wijmo.addClass(cell, 'wj-custom-readonly');
                                }
                            }
                        };

                        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
                        $timeout(function () {
                            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                                includeColumnHeaders: true,
                                includeCellStyles   : false,
                                includeColumns      : function (column) {
                                    return column.visible;
                                }
                            }, "상품매출(P.MIX)_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime()+'.xlsx', function () {
                                $timeout(function () {
                                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                                }, 10);
                            });
                        }, 10);

                    }
                }, function errorCallback(response) {
                    $scope._popMsg(messages["cmm.error"]);
                    return false;
                }).then(function () {
                    if (typeof callback === 'function') {
                        $timeout(function () {
                            callback();
                        }, 10);
                    }
                });
            }
            // <-- //그리드 생성 -->
        });
    };
    // <-- //검색 호출 -->

}]);