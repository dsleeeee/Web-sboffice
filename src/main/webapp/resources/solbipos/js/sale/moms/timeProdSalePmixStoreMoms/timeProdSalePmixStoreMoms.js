/****************************************************************
 *
 * 파일명 : timeProdSalePmixStoreMoms.js
 * 설  명 : 맘스터치 > 간소화화면 > 시간대별 상품매출(P.MIX 매장) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.05.27     김설아      1.0
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
 *  시간대별 상품매출(P.MIX 매장) 그리드 생성
 */
app.controller('timeProdSalePmixStoreMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('timeProdSalePmixStoreMomsCtrl', $scope, $http, false));

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
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList, 'value', 'name'); // 지역구분
    };

    // <-- 검색 호출 -->
    $scope.$on("timeProdSalePmixStoreMomsCtrl", function (event, data) {
        $scope.searchTimeProdSalePmixStoreMomsList();
        event.preventDefault();
    });

    $scope.searchTimeProdSalePmixStoreMomsList = function () {
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

            // 조회일자 최대 1일 제한
            if (diffDay > 0) {
                $scope._popMsg(messages['cmm.dateOver.1day.error']);
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

            // 조회일자 최대 1개월 제한
            if (diffDay > 31) {
                s_alert.pop(messages['cmm.dateOver.1month.error']);
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
        params.storeCds = $("#timeProdSalePmixStoreMomsStoreCd").val();
        params.prodCds = $("#timeProdSalePmixStoreMomsSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) { // 확인완료 1992
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

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/moms/timeProdSalePmixStoreMoms/timeProdSalePmixStoreMoms/getTimeProdSalePmixStoreMomsList.sb", params, function (response) {
            // <-- 그리드 생성 -->
            var list = response.data.list;
            var length = response.data.list.length;
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
                    while (grid.columns.length > 5) {
                        grid.columns.removeAt(grid.columns.length - 1);
                    }
                }

                // 값 초기화
                params.storeCds = '';
                params.prodCds = '';

                // 기간선택 두 날짜 사이 모든날짜 구하기
                // ajax 통신 설정
                $http({
                    method : 'POST', //방식
                    url    : "/sale/moms/timeProdSalePmixStoreMoms/timeProdSalePmixStoreMoms/getDateDiff.sb", /* 통신할 URL */
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
                                binding: "saleQty1" +  dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["timeProdSalePmixStoreMoms.total"],
                            binding: "totSaleQty1",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // 실매출액
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "realSaleAmt1" + dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["timeProdSalePmixStoreMoms.total"],
                            binding: "totRealSaleAmt1",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // P.MIX
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "pMixSale1" + dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["timeProdSalePmixStoreMoms.total"],
                            binding: "totPMixSale1",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // 시간대
                        var timeCol = "";
                        for(var i = 0; i <= 23; i++) {
                            timeCol += (i < 10 ? "0" + i : i);
                            if(i != 23){
                                timeCol += ",";
                            }
                        }
                        var arrTimeCol = timeCol.split(",");

                        for (var i = 0; i < arrTimeCol.length; i++) {
                            // 판매수량
                            grid.columns.push(new wijmo.grid.Column({
                                header: "판매수량",
                                binding: "timeSaleQty1" + arrTimeCol[i],
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                            // 실매출액
                            grid.columns.push(new wijmo.grid.Column({
                                header: "실매출액",
                                binding: "timeRealSaleAmt1" + arrTimeCol[i],
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                            // P.MIX
                            grid.columns.push(new wijmo.grid.Column({
                                header: "P.MIX",
                                binding: "timePMixSale1" + arrTimeCol[i],
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }

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
                        dataItem.storeCd = messages["timeProdSalePmixStoreMoms.storeCd"];
                        dataItem.storeNm = messages["timeProdSalePmixStoreMoms.storeNm"];
                        dataItem.momsAreaFg = messages["cmm.moms.momsAreaFg"];
                        dataItem.prodClassNm = messages["timeProdSalePmixStoreMoms.prodClassNm"];
                        dataItem.prodNm = messages["timeProdSalePmixStoreMoms.prodNm"];

                        for (var i = 0; i < dateArr.length; i++) {
                            dataItem['saleQty1' + dateArr[i].sOrgDate] = "판매수량";
                        }
                        dataItem.totSaleQty1 = "판매수량";

                        for (var i = 0; i < dateArr.length; i++) {
                            dataItem['realSaleAmt1' + dateArr[i].sOrgDate] = "실매출액";
                        }
                        dataItem.totRealSaleAmt1 = "실매출액";

                        for (var i = 0; i < dateArr.length; i++) {
                            dataItem['pMixSale1' + dateArr[i].sOrgDate] = "P.MIX";
                        }
                        dataItem.totPMixSale1 = "P.MIX";

                        for (var i = 0; i < arrTimeCol.length; i++) {
                            if(i != 23){
                                dataItem['timeSaleQty1' + arrTimeCol[i]] = arrTimeCol[i] + '시~' + arrTimeCol[i+1] + '시';
                                dataItem['timeRealSaleAmt1' + arrTimeCol[i]] = arrTimeCol[i] + '시~' + arrTimeCol[i+1] + '시';
                                dataItem['timePMixSale1' + arrTimeCol[i]] = arrTimeCol[i] + '시~' + arrTimeCol[i+1] + '시';
                            } else {
                                dataItem['timeSaleQty1' + arrTimeCol[i]] = arrTimeCol[i] + '시~24시';
                                dataItem['timeRealSaleAmt1' + arrTimeCol[i]] = arrTimeCol[i] + '시~24시';
                                dataItem['timePMixSale1' + arrTimeCol[i]] = arrTimeCol[i] + '시~24시';
                            }
                        }

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
            if (response.data.page && response.data.page.curr) {
                var pagingInfo = response.data.page;
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

    // 상품선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.timeProdSalePmixStoreMomsSelectShow = function () {
        $scope._broadcast('timeProdSalePmixStoreMomsSelectCtrl');
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

            // 조회일자 최대 1일 제한
            if (diffDay > 0) {
                $scope._popMsg(messages['cmm.dateOver.1day.error']);
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

            // 조회일자 최대 1개월 제한
            if (diffDay > 31) {
                s_alert.pop(messages['cmm.dateOver.1month.error']);
                return false;
            }
        }

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
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
        params.storeCds = $("#timeProdSalePmixStoreMomsStoreCd").val();
        params.prodCds = $("#timeProdSalePmixStoreMomsSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) { // 확인완료 1992
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
                $scope._broadcast('timeProdSalePmixStoreMomsExcelCtrl', params);
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
                        $scope._broadcast('timeProdSalePmixStoreMomsExcelCtrl', params);
                    });
                }
            });
        }
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

            // 조회일자 최대 1일 제한
            if (diffDay > 0) {
                $scope._popMsg(messages['cmm.dateOver.1day.error']);
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

            // 조회일자 최대 1개월 제한
            if (diffDay > 31) {
                s_alert.pop(messages['cmm.dateOver.1month.error']);
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
                "시간대별 상품매출(P.MIX 매장)_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
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
app.controller('timeProdSalePmixStoreMomsExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('timeProdSalePmixStoreMomsExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList, 'value', 'name'); // 지역구분
    };

    // <-- 검색 호출 -->
    $scope.$on("timeProdSalePmixStoreMomsExcelCtrl", function (event, data) {
        if(data.excelType === '1') {
            $scope.searchExcelList(data);
        } else {
            // 엑셀다운로드 진행 사용자 현재 인원수 체크
            data.downloadFg = "0"; // 다운로드 구분 (0:간소화화면, 1:상품매출분석)
            data.resrceCd = menuCd;
            data.resrceNm = menuNm;
            data.downloadUseFg = "2"; // 다운로드 사용기능 (0:전체다운로드, 1:조회조건다운로드, 2:분할다운로드)
            if (data.dayGubun === "day") {
                data.downloadNo = "14-1"; // 다운로드 화면구분번호
            } else if (data.dayGubun === "month") {
                data.downloadNo = "14-2"; // 다운로드 화면구분번호
            }

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
        $scope._postJSONQuery.withPopUp("/sale/moms/timeProdSalePmixStoreMoms/timeProdSalePmixStoreMoms/getTimeProdSalePmixStoreMomsExcelList.sb", params, function (response) {
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
                    while (grid.columns.length > 5) {
                        grid.columns.removeAt(grid.columns.length - 1);
                    }
                }

                // 기간선택 두 날짜 사이 모든날짜 구하기
                // ajax 통신 설정
                $http({
                    method : 'POST', //방식
                    url    : "/sale/moms/timeProdSalePmixStoreMoms/timeProdSalePmixStoreMoms/getDateDiff.sb", /* 통신할 URL */
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
                                binding: "saleQty1" +  dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["timeProdSalePmixStoreMoms.total"],
                            binding: "totSaleQty1",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // 실매출액
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "realSaleAmt1" + dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["timeProdSalePmixStoreMoms.total"],
                            binding: "totRealSaleAmt1",
                            width: 90,
                            align: "right",
                            isReadOnly: "true"
                        }));

                        // P.MIX
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "pMixSale1" + dateArr[i].sOrgDate,
                                width: 90,
                                align: "right",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["timeProdSalePmixStoreMoms.total"],
                            binding: "totPMixSale1",
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
                        dataItem.storeCd = messages["timeProdSalePmixStoreMoms.storeCd"];
                        dataItem.storeNm = messages["timeProdSalePmixStoreMoms.storeNm"];
                        dataItem.momsAreaFg = messages["cmm.moms.momsAreaFg"];
                        dataItem.prodClassNm = messages["timeProdSalePmixStoreMoms.prodClassNm"];
                        dataItem.prodNm = messages["timeProdSalePmixStoreMoms.prodNm"];

                        for (var i = 0; i < dateArr.length; i++) {
                            dataItem['saleQty1' + dateArr[i].sOrgDate] = "판매수량";
                        }
                        dataItem.totSaleQty1 = "판매수량";

                        for (var i = 0; i < dateArr.length; i++) {
                            dataItem['realSaleAmt1' + dateArr[i].sOrgDate] = "실매출액";
                        }
                        dataItem.totRealSaleAmt1 = "실매출액";

                        for (var i = 0; i < dateArr.length; i++) {
                            dataItem['pMixSale1' + dateArr[i].sOrgDate] = "P.MIX";
                        }
                        dataItem.totPMixSale1 = "P.MIX";

                        for (var i = 0; i < arrTimeCol.length; i++) {
                            if(i != 23){
                                dataItem['timeSaleQty1' + arrTimeCol[i]] = arrTimeCol[i] + '시~' + arrTimeCol[i+1] + '시';
                                dataItem['timeRealSaleAmt1' + arrTimeCol[i]] = arrTimeCol[i] + '시~' + arrTimeCol[i+1] + '시';
                                dataItem['timePMixSale1' + arrTimeCol[i]] = arrTimeCol[i] + '시~' + arrTimeCol[i+1] + '시';
                            } else {
                                dataItem['timeSaleQty1' + arrTimeCol[i]] = arrTimeCol[i] + '시~24시';
                                dataItem['timeRealSaleAmt1' + arrTimeCol[i]] = arrTimeCol[i] + '시~24시';
                                dataItem['timePMixSale1' + arrTimeCol[i]] = arrTimeCol[i] + '시~24시';
                            }
                        }

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
                            }, "시간대별 상품매출(P.MIX 매장)_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime()+'.xlsx', function () {
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

        $.postJSON("/sale/moms/timeProdSalePmixStoreMoms/timeProdSalePmixStoreMoms/getTimeProdSalePmixStoreMomsList.sb", params, function(response){

            listSize = response.data.list[0].totCnt;
            totFileCnt = Math.ceil(listSize/5000); // 하나의 엑셀파일에 5000개씩 다운로드

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

                        // 페이징 5000개씩 지정해 분할 다운로드 진행
                        params.limit = 5000 * (x + 1);
                        params.offset = (5000 * (x + 1)) - 4999;

                        // 가상로그인 대응한 session id 설정
                        if (document.getElementsByName('sessionId')[0]) {
                            params['sid'] = document.getElementsByName('sessionId')[0].value;
                        }

                        // 엑셀다운로드 진행 사용자 저장 update
                        params.seq = seq;
                        $.postJSON("/sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveUpdate.sb", params, function(response){

                            $.postJSON('/sale/moms/timeProdSalePmixStoreMoms/timeProdSalePmixStoreMoms/getTimeProdSalePmixStoreMomsExcelDivisionList.sb', params, function(response){
                                var list = response.data.list;
                                if (list.length === undefined || list.length === 0) {
                                    $scope.data = new wijmo.collections.CollectionView([]);
                                    $scope.excelUploadingPopup(false);
                                    return false;
                                }

                                // <-- 그리드 생성 -->
                                var list = response.data.list;
                                var length = response.data.list.length;
                                var grid = wijmo.Control.getControl("#wjGridExcelList");

                                // rows, footer 초기화
                                grid.rows.clear();
                                grid.columnFooters.rows.clear();

                                if(length > 0){

                                    // 동적 컬럼 초기화
                                    if (length != "" || length != null) {
                                        while (grid.columns.length > 5) {
                                            grid.columns.removeAt(grid.columns.length - 1);
                                        }
                                    }

                                    // 기간선택 두 날짜 사이 모든날짜 구하기
                                    $.postJSON("/sale/moms/timeProdSalePmixStoreMoms/timeProdSalePmixStoreMoms/getDateDiff.sb", params, function(response) {
                                            var dateArr = response.data.list;

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
                                                        binding: "saleQty1" +  dateArr[i].sOrgDate,
                                                        width: 90,
                                                        align: "right",
                                                        isReadOnly: "true"
                                                    }));
                                                }
                                                grid.columns.push(new wijmo.grid.Column({
                                                    header: messages["timeProdSalePmixStoreMoms.total"],
                                                    binding: "totSaleQty1",
                                                    width: 90,
                                                    align: "right",
                                                    isReadOnly: "true"
                                                }));

                                                // 실매출액
                                                for (var i = 0; i < dateArr.length; i++) {
                                                    grid.columns.push(new wijmo.grid.Column({
                                                        header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                                        binding: "realSaleAmt1" + dateArr[i].sOrgDate,
                                                        width: 90,
                                                        align: "right",
                                                        isReadOnly: "true"
                                                    }));
                                                }
                                                grid.columns.push(new wijmo.grid.Column({
                                                    header: messages["timeProdSalePmixStoreMoms.total"],
                                                    binding: "totRealSaleAmt1",
                                                    width: 90,
                                                    align: "right",
                                                    isReadOnly: "true"
                                                }));

                                                // P.MIX
                                                for (var i = 0; i < dateArr.length; i++) {
                                                    grid.columns.push(new wijmo.grid.Column({
                                                        header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                                        binding: "pMixSale1" + dateArr[i].sOrgDate,
                                                        width: 90,
                                                        align: "right",
                                                        isReadOnly: "true"
                                                    }));
                                                }
                                                grid.columns.push(new wijmo.grid.Column({
                                                    header: messages["timeProdSalePmixStoreMoms.total"],
                                                    binding: "totPMixSale1",
                                                    width: 90,
                                                    align: "right",
                                                    isReadOnly: "true"
                                                }));

                                                // 시간대
                                                var timeCol = "";
                                                for(var i = 0; i <= 23; i++) {
                                                    timeCol += (i < 10 ? "0" + i : i);
                                                    if(i != 23){
                                                        timeCol += ",";
                                                    }
                                                }
                                                var arrTimeCol = timeCol.split(",");

                                                for (var i = 0; i < arrTimeCol.length; i++) {
                                                    // 판매수량
                                                    grid.columns.push(new wijmo.grid.Column({
                                                        header: "판매수량",
                                                        binding: "timeSaleQty1" + arrTimeCol[i],
                                                        width: 90,
                                                        align: "right",
                                                        isReadOnly: "true"
                                                    }));
                                                    // 실매출액
                                                    grid.columns.push(new wijmo.grid.Column({
                                                        header: "실매출액",
                                                        binding: "timeRealSaleAmt1" + arrTimeCol[i],
                                                        width: 90,
                                                        align: "right",
                                                        isReadOnly: "true"
                                                    }));
                                                    // P.MIX
                                                    grid.columns.push(new wijmo.grid.Column({
                                                        header: "P.MIX",
                                                        binding: "timePMixSale1" + arrTimeCol[i],
                                                        width: 90,
                                                        align: "right",
                                                        isReadOnly: "true"
                                                    }));
                                                }

                                                // 데이터 뿌리기
                                                var data = new wijmo.collections.CollectionView(list);
                                                data.trackChanges = true;
                                                $scope.data = data;
                                                grid.itemsSource = data;
                                                $scope.excelFlex.itemsSource = data;

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
                                                dataItem.storeCd = messages["timeProdSalePmixStoreMoms.storeCd"];
                                                dataItem.storeNm = messages["timeProdSalePmixStoreMoms.storeNm"];
                                                dataItem.momsAreaFg = messages["cmm.moms.momsAreaFg"];
                                                dataItem.prodClassNm = messages["timeProdSalePmixStoreMoms.prodClassNm"];
                                                dataItem.prodNm = messages["timeProdSalePmixStoreMoms.prodNm"];

                                                for (var i = 0; i < dateArr.length; i++) {
                                                    dataItem['saleQty1' + dateArr[i].sOrgDate] = "판매수량";
                                                }
                                                dataItem.totSaleQty1 = "판매수량";

                                                for (var i = 0; i < dateArr.length; i++) {
                                                    dataItem['realSaleAmt1' + dateArr[i].sOrgDate] = "실매출액";
                                                }
                                                dataItem.totRealSaleAmt1 = "실매출액";

                                                for (var i = 0; i < dateArr.length; i++) {
                                                    dataItem['pMixSale1' + dateArr[i].sOrgDate] = "P.MIX";
                                                }
                                                dataItem.totPMixSale1 = "P.MIX";

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

                                                // 엑셀 다운로드
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
                                                    }, "시간대별 상품매출(P.MIX 매장)_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime() + '_' + (x + 1) + '.xlsx', function () {
                                                        $timeout(function () {
                                                            console.log("Export complete start. _" + (x + 1));
                                                            getExcelFile(x + 1);
                                                        }, 500);
                                                    }, function (reason) {
                                                        console.log('The reason of save failure is ' + reason + "_" + (x + 1));
                                                        $scope.excelUploadingPopup(false);
                                                    });
                                                }, 1000);
                                        });
                                    }
                                    //<-- //그리드 생성 -->
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