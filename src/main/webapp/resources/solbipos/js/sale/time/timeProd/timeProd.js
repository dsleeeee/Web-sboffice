/****************************************************************
 *
 * 파일명 : timeProd.js
 * 설  명 : 맘스터치 > 상품매출분석 > 상품별 시간대 매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.10.12     권지현      1.0
 * 2022.12.15     김설아      1.0            전체수정
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 상품표시옵션
var prodOptionComboData = [
    {"name":"단품/세트","value":"1"},
    {"name":"단품/구성","value":"2"},
    {"name":"단품/세트/구성","value":"3"},
    {"name":"모두표시","value":"4"}
];
// 일자표시옵션
var dayOptionComboData = [
    {"name":"일자별","value":"1"},
    {"name":"기간합","value":"2"}
];
//  일/월 구분
var dayGubunComboData = [
    {"name":"일","value":"day"},
    {"name":"월","value":"month"}
];

// 시 VALUE
var Hh = [24];
for(i =0 ; i < 24; i++){
    var timeVal = i.toString();
    if(i>=0 && i<=9){
        timeVal = "0" + timeVal;
    }
    Hh[i] = {"name":timeVal,"value":timeVal}
}

/**
 *  상품별 시간대 매출 그리드 생성
 */
app.controller('timeProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('timeProdCtrl', $scope, $http, false));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
    // 조회일자
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    
    // 시간대
    $scope.timeSlotData = [];
    var comboArray  = [{name:"전체", value:""}];
    for(var i = 0; i < timeSlotColList.length; i++){
        var comboData   = {};
        comboData.name = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
        comboData.value = timeSlotColList[i].value;
        comboArray.push(comboData);
    }

    timeSlotData = comboArray;
    $scope._setComboData("timeSlotCombo", timeSlotData);
    $scope._setComboData("startTimeCombo", Hh);
    $scope._setComboData("endTimeCombo", Hh);
    $scope.startTime     = "0";
    $scope.endTime       = "23";
    
    // 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 지사
    $scope._setComboData("prodOptionCombo", prodOptionComboData); // 상품표시옵션
    $scope._setComboData("dayOptionCombo", dayOptionComboData); // 일자표시옵션
    $scope._setComboData("dayGubunCombo", dayGubunComboData); // 일/월 구분

    // 팀별
    if(momsTeamComboList.length <= 1) {
        $("#srchMomsTeamCombo").css('background-color', '#F0F0F0');
        $("#srchMomsTeamCombo").attr("disabled", true);
    } else {
        $("#srchMomsTeamCombo").css('background-color', '#FFFFFF');
        $("#srchMomsTeamCombo").attr("disabled", false);
    }
    // AC점포별
    if(momsAcShopComboList.length <= 1) {
        $("#srchMomsAcShopCombo").css('background-color', '#F0F0F0');
        $("#srchMomsAcShopCombo").attr("disabled", true);
    } else {
        $("#srchMomsAcShopCombo").css('background-color', '#FFFFFF');
        $("#srchMomsAcShopCombo").attr("disabled", false);
    }
    // 지역구분
    if(momsAreaFgComboList.length <= 1) {
        $("#srchMomsAreaFgCombo").css('background-color', '#F0F0F0');
        $("#srchMomsAreaFgCombo").attr("disabled", true);
    } else {
        $("#srchMomsAreaFgCombo").css('background-color', '#FFFFFF');
        $("#srchMomsAreaFgCombo").attr("disabled", false);
    }
    // 상권
    if(momsCommercialComboList.length <= 1) {
        $("#srchMomsCommercialCombo").css('background-color', '#F0F0F0');
        $("#srchMomsCommercialCombo").attr("disabled", true);
    } else {
        $("#srchMomsCommercialCombo").css('background-color', '#FFFFFF');
        $("#srchMomsCommercialCombo").attr("disabled", false);
    }
    // 점포유형
    if(momsShopTypeComboList.length <= 1) {
        $("#srchMomsShopTypeCombo").css('background-color', '#F0F0F0');
        $("#srchMomsShopTypeCombo").attr("disabled", true);
    } else {
        $("#srchMomsShopTypeCombo").css('background-color', '#FFFFFF');
        $("#srchMomsShopTypeCombo").attr("disabled", false);
    }
    // 매장관리타입
    if(momsStoreManageTypeComboList.length <= 1) {
        $("#srchMomsStoreManageTypeCombo").css('background-color', '#F0F0F0');
        $("#srchMomsStoreManageTypeCombo").attr("disabled", true);
    } else {
        $("#srchMomsStoreManageTypeCombo").css('background-color', '#FFFFFF');
        $("#srchMomsStoreManageTypeCombo").attr("disabled", false);
    }
    // 지사
    if(branchCdComboList.length <= 1) {
        $("#srchBranchCdComboo").css('background-color', '#F0F0F0');
        $("#srchBranchCdComboo").attr("disabled", true);
    } else {
        $("#srchBranchCdComboo").css('background-color', '#FFFFFF');
        $("#srchBranchCdComboo").attr("disabled", false);
    }

    // 그리드 매출구분
    $scope.saleFgMap = new wijmo.grid.DataMap([
        {id: "1", name: messages["todayBillSaleDtl.saleY"]},
        {id: "-1", name: messages["todayBillSaleDtl.saleN"]}
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "yoil") {
                    if(item.yoil === "토") {
                        wijmo.addClass(e.cell, 'blue');
                    } else if(item.yoil === "일") {
                        wijmo.addClass(e.cell, 'red');
                    }
                }
            }
        });

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
        dataItem.saleDate = messages["timeProd.saleDate"];
        dataItem.yoil = messages["timeProd.yoil"];
        dataItem.saleYm = messages["timeProd.saleYm"];
        dataItem.dayFrom = messages["timeProd.dayFrom"];
        dataItem.dayTo = messages["timeProd.dayTo"];
        dataItem.branchNm = messages["timeProd.branchNm"];
        dataItem.storeCd = messages["timeProd.storeCd"];
        dataItem.storeNm = messages["timeProd.storeNm"];
        dataItem.lClassCd = messages["timeProd.lClassCd"];
        dataItem.lClassNm = messages["timeProd.lClassNm"];
        dataItem.mClassCd = messages["timeProd.mClassCd"];
        dataItem.mClassNm = messages["timeProd.mClassNm"];
        dataItem.sClassCd = messages["timeProd.sClassCd"];
        dataItem.sClassNm = messages["timeProd.sClassNm"];
        dataItem.prodCd = messages["timeProd.prodCd"];
        dataItem.sideProdCd = messages["timeProd.sideProdCd"];
        dataItem.selTypeFg = messages["timeProd.selTypeFg"];
        dataItem.sideProdNm = messages["timeProd.sideProdNm"];
        dataItem.totSaleQty = messages["timeProd.tot"];
        dataItem.totRealSaleAmt = messages["timeProd.tot"];

        // 시간대별 컬럼 생성
        for (var i = 0; i < 24; i++) {
            dataItem['saleQty1T' + i] = i + "시";
            dataItem['saleQty2T' + i] = i + "시";
            dataItem['saleQty3T' + i] = i + "시";
            dataItem['realSaleAmt1T' + i] = i + "시";
            dataItem['realSaleAmt2T' + i] = i + "시";
            dataItem['realSaleAmt3T' + i] = i + "시";
            dataItem['rate1T' + i] = i + "시";
            dataItem['rate2T' + i] = i + "시";
            dataItem['rate3T' + i] = i + "시";
        }

        // 시간대분류 컬럼 생성
        for (var i = 0; i < timeSlotColList.length; i++) {
            dataItem['saleQty1T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['saleQty2T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['saleQty3T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['realSaleAmt1T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['realSaleAmt2T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['realSaleAmt3T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['rate1T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['rate2T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['rate3T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
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
        // <-- //그리드 헤더2줄 -->
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("timeProdCtrl", function (event, data) {
        $scope.searchTimeProdList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 리스트 조회
    $scope.searchTimeProdList = function () {

        if($scope.dayGubun === "day") {
            var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }
            // 조회일자 최대 7일 제한
            if (diffDay > 7) {
                $scope._popMsg(messages['cmm.dateOver.7day.error']);
                return false;
            }

        } else if($scope.dayGubun === "month") {
            var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
            var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
            var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }
            // 조회일자 최대 7달 제한
            if (diffMonth > 7) {
                $scope._popMsg(messages['cmm.dateOver.7month.error']);
                return false;
            }
        }

        if($scope.startTime*1 > $scope.endTime*1){ // *1하는이유 : Time들이 String이라 int로 바꿀라고
            $scope._popMsg(messages["timeProd.startEnd"]); // 검색 시작 시간대가 검색 종료 시간대보다 큽니다.
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.prodClassCd = $scope.prodClassCd;
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;
        params.timeSlot = $scope.timeSlot;
        params.timeCol= timeSlotCol;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#timeProdStoreCd").val();
        params.prodCds = $("#timeProdSelectCd").val();
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
        params.prodOption = $scope.prodOption;
        params.dayOption = $scope.dayOption;
        params.dayGubun = $scope.dayGubun;
        params.listScale = 500;

        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/time/timeProd/timeProd/getTimeProdList.sb", params, function (){
            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;
            
            // 시간대
            var start = params.startTime*1;
            var end = params.endTime*1;

            // 컬럼 총갯수
            var columnsCnt = columns.length;

            for (var i = 0; i < columnsCnt; i++) {
                columns[i].visible = true;
            }

            // 조회일자 일/월 & 일자표시옵션 구분에 따른 날짜 컬럼 제어
            if(params.dayGubun === "day") {                     // 일
                if(params.dayOption === "1"){                   // 일자별
                    columns[2].visible = false;                 // saleYm
                    columns[3].visible = false;                 // dayFrom
                    columns[4].visible = false;                 // dayTo
                } else if(params.dayOption === "2"){            // 기간합
                    columns[0].visible = false;                 // saleDate
                    columns[1].visible = false;                 // yoil
                    columns[2].visible = false;                 // saleYm
                }
            } else if(params.dayGubun === "month") {            // 월
                if(params.dayOption === "1"){                   // 일자별
                    columns[0].visible = false;                 // saleDate
                    columns[1].visible = false;                 // yoil
                    columns[3].visible = false;                 // dayFrom
                    columns[4].visible = false;                 // dayTo
                } else if(params.dayOption === "2"){            // 기간합
                    columns[0].visible = false;                 // saleDate
                    columns[1].visible = false;                 // yoil
                    columns[2].visible = false;                 // saleYm
                }
            }

            // 시간대옵션에 따른 시간 컬럼제어
            if($("input[name=optionFg]:checked").val() == "time") { // 시간대

                // 시간대 컬럼 show
                for (var i = 20; i < 236; i++) {

                    // 시간대에 해당되는 컬럼 파악
                    var startCol = (start * 9) + 20;
                    var endCol = (end * 9) + 28;

                    if(i >= startCol && endCol >= i){
                        if(params.prodOption === "1") {  // 단품+세트
                            if(i%3 === 2){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "2") {  // 단품+구성
                            if(i%3 === 0){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "3") {  // 단품+세트+구성
                            if(i%3 === 1){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "4") {  // 모두표시
                            columns[i].visible = true;
                        }
                    }else{
                        columns[i].visible = false;
                    }
                }

                // 시간대분류 컬럼 hidden
                for (var i = 236; i < 272; i++) {
                    columns[i].visible = false;
                }

            } else if($("input[name=optionFg]:checked").val() == "timeSlot") {   // 시간대분류

                // 시간대 컬럼 hidden
                for (var i = 20; i < 236; i++) {
                    columns[i].visible = false;
                }

                // 시간대분류 컬럼 show
                for (var i = 236; i < 272; i++) {

                    if(params.timeSlot === ""){ // 전체

                        if(params.prodOption === "1") {  // 단품+세트
                            if(i%3 === 2){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "2") {  // 단품+구성
                            if(i%3 === 0){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "3") {  // 단품+세트+구성
                            if(i%3 === 1){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "4") {  // 모두표시
                            columns[i].visible = true;
                        }

                    }else{
                        // 시간대에 해당되는 컬럼 파악
                        var startCol = ((23 + $scope.timeSlotCombo.selectedIndex) * 9) + 20;
                        var endCol = ((23 + $scope.timeSlotCombo.selectedIndex) * 9) + 28;

                        if(i >= startCol && endCol >= i){
                            if(params.prodOption === "1") {  // 단품+세트
                                if(i%3 === 2){
                                    columns[i].visible = true;
                                }else{
                                    columns[i].visible = false;
                                }
                            } else if(params.prodOption === "2") {  // 단품+구성
                                if(i%3 === 0){
                                    columns[i].visible = true;
                                }else{
                                    columns[i].visible = false;
                                }
                            } else if(params.prodOption === "3") {  // 단품+세트+구성
                                if(i%3 === 1){
                                    columns[i].visible = true;
                                }else{
                                    columns[i].visible = false;
                                }
                            } else if(params.prodOption === "4") {  // 모두표시
                                columns[i].visible = true;
                            }
                        }else{
                            columns[i].visible = false;
                        }
                    }
                }
            }
            // <-- //그리드 visible -->
        });
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.timeProdStoreShow = function () {
        $scope._broadcast('timeProdStoreCtrl');
    };

    // 상품선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.timeProdSelectShow = function () {
        $scope._broadcast('timeProdSelectCtrl');
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

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if($scope.dayGubun === "day") {
            var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }
            // 조회일자 최대 7일 제한
            if (diffDay > 7) {
                $scope._popMsg(messages['cmm.dateOver.7day.error']);
                return false;
            }

        } else if($scope.dayGubun === "month") {
            var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
            var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
            var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }
            // 조회일자 최대 7달 제한
            if (diffMonth > 7) {
                $scope._popMsg(messages['cmm.dateOver.7month.error']);
                return false;
            }
        }

        if($scope.startTime*1 > $scope.endTime*1){ // *1하는이유 : Time들이 String이라 int로 바꿀라고
            $scope._popMsg(messages["timeProd.startEnd"]); // 검색 시작 시간대가 검색 종료 시간대보다 큽니다.
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.prodClassCd = $scope.prodClassCd;
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;
        params.timeSlot = $scope.timeSlot;
        params.timeSlotIdx = $scope.timeSlotCombo.selectedIndex; // 엑셀다운로드시, hidden 컬럼 계산을 위해
        params.timeCol= timeSlotCol;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#timeProdStoreCd").val();
        params.prodCds = $("#timeProdSelectCd").val();
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
        params.prodOption = $scope.prodOption;
        params.dayOption = $scope.dayOption;
        params.dayGubun = $scope.dayGubun;

        $scope._broadcast('timeProdExcelCtrl', params);
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 일/월 구분 콤보박스 선택 이벤트
    $scope.setDayGubunCombo = function (s){
        if(s.selectedValue === "day") {
            $("#spanDay").css("display" , "");
            $("#spanMonth").css("display" , "none");
        } else if(s.selectedValue === "month") {
            $("#spanDay").css("display" , "none");
            $("#spanMonth").css("display" , "");
        }
    };

    // 라디오버튼 클릭시 이벤트 발생
    $("input:radio[name=optionFg]").click(function(){
        if($("input[name=optionFg]:checked").val() == "time"){              // 시간대
            $("#timeOption").show();
            $("#timeSlotOption").hide();
        }else {       // 시간대분류
            $("#timeOption").hide();
            $("#timeSlotOption").show();
        }
    });

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('timeProdExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('timeProdExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "yoil") {
                    if(item.yoil === "토") {
                        wijmo.addClass(e.cell, 'blue');
                    } else if(item.yoil === "일") {
                        wijmo.addClass(e.cell, 'red');
                    }
                }
            }
        });

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
        dataItem.saleDate = messages["timeProd.saleDate"];
        dataItem.yoil = messages["timeProd.yoil"];
        dataItem.saleYm = messages["timeProd.saleYm"];
        dataItem.dayFrom = messages["timeProd.dayFrom"];
        dataItem.dayTo = messages["timeProd.dayTo"];
        dataItem.branchNm = messages["timeProd.branchNm"];
        dataItem.storeCd = messages["timeProd.storeCd"];
        dataItem.storeNm = messages["timeProd.storeNm"];
        dataItem.lClassCd = messages["timeProd.lClassCd"];
        dataItem.lClassNm = messages["timeProd.lClassNm"];
        dataItem.mClassCd = messages["timeProd.mClassCd"];
        dataItem.mClassNm = messages["timeProd.mClassNm"];
        dataItem.sClassCd = messages["timeProd.sClassCd"];
        dataItem.sClassNm = messages["timeProd.sClassNm"];
        dataItem.prodCd = messages["timeProd.prodCd"];
        dataItem.sideProdCd = messages["timeProd.sideProdCd"];
        dataItem.selTypeFg = messages["timeProd.selTypeFg"];
        dataItem.sideProdNm = messages["timeProd.sideProdNm"];
        dataItem.totSaleQty = messages["timeProd.tot"];
        dataItem.totRealSaleAmt = messages["timeProd.tot"];

        // 시간대별 컬럼 생성
        for (var i = 0; i < 24; i++) {
            dataItem['saleQty1T' + i] = i + "시";
            dataItem['saleQty2T' + i] = i + "시";
            dataItem['saleQty3T' + i] = i + "시";
            dataItem['realSaleAmt1T' + i] = i + "시";
            dataItem['realSaleAmt2T' + i] = i + "시";
            dataItem['realSaleAmt3T' + i] = i + "시";
            dataItem['rate1T' + i] = i + "시";
            dataItem['rate2T' + i] = i + "시";
            dataItem['rate3T' + i] = i + "시";
        }

        // 시간대분류 컬럼 생성
        for (var i = 0; i < timeSlotColList.length; i++) {
            dataItem['saleQty1T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['saleQty2T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['saleQty3T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['realSaleAmt1T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['realSaleAmt2T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['realSaleAmt3T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['rate1T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['rate2T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['rate3T' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
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
        // <-- //그리드 헤더2줄 -->
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("timeProdExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/time/timeProd/timeProd/getTimeProdExcelList.sb", params, function() {
            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridExcelList");
            var columns = grid.columns;

            // 시간대
            var start = params.startTime*1;
            var end = params.endTime*1;

            // 컬럼 총갯수
            var columnsCnt = columns.length;

            for (var i = 0; i < columnsCnt; i++) {
                columns[i].visible = true;
            }

            // 조회일자 일/월 & 일자표시옵션 구분에 따른 날짜 컬럼 제어
            if(params.dayGubun === "day") {                     // 일
                if(params.dayOption === "1"){                   // 일자별
                    columns[2].visible = false;                 // saleYm
                    columns[3].visible = false;                 // dayFrom
                    columns[4].visible = false;                 // dayTo
                } else if(params.dayOption === "2"){            // 기간합
                    columns[0].visible = false;                 // saleDate
                    columns[1].visible = false;                 // yoil
                    columns[2].visible = false;                 // saleYm
                }
            } else if(params.dayGubun === "month") {            // 월
                if(params.dayOption === "1"){                   // 일자별
                    columns[0].visible = false;                 // saleDate
                    columns[1].visible = false;                 // yoil
                    columns[3].visible = false;                 // dayFrom
                    columns[4].visible = false;                 // dayTo
                } else if(params.dayOption === "2"){            // 기간합
                    columns[0].visible = false;                 // saleDate
                    columns[1].visible = false;                 // yoil
                    columns[2].visible = false;                 // saleYm
                }
            }

            // 시간대옵션에 따른 시간 컬럼제어
            if($("input[name=optionFg]:checked").val() == "time") { // 시간대

                // 시간대 컬럼 show
                for (var i = 20; i < 236; i++) {

                    // 시간대에 해당되는 컬럼 파악
                    var startCol = (start * 9) + 20;
                    var endCol = (end * 9) + 28;

                    if(i >= startCol && endCol >= i){
                        if(params.prodOption === "1") {  // 단품+세트
                            if(i%3 === 2){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "2") {  // 단품+구성
                            if(i%3 === 0){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "3") {  // 단품+세트+구성
                            if(i%3 === 1){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "4") {  // 모두표시
                            columns[i].visible = true;
                        }
                    }else{
                        columns[i].visible = false;
                    }
                }

                // 시간대분류 컬럼 hidden
                for (var i = 236; i < 272; i++) {
                    columns[i].visible = false;
                }

            } else if($("input[name=optionFg]:checked").val() == "timeSlot") {   // 시간대분류

                // 시간대 컬럼 hidden
                for (var i = 20; i < 236; i++) {
                    columns[i].visible = false;
                }

                // 시간대분류 컬럼 show
                for (var i = 236; i < 272; i++) {

                    if(params.timeSlot === ""){ // 전체

                        if(params.prodOption === "1") {  // 단품+세트
                            if(i%3 === 2){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "2") {  // 단품+구성
                            if(i%3 === 0){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "3") {  // 단품+세트+구성
                            if(i%3 === 1){
                                columns[i].visible = true;
                            }else{
                                columns[i].visible = false;
                            }
                        } else if(params.prodOption === "4") {  // 모두표시
                            columns[i].visible = true;
                        }

                    }else{
                        // 시간대에 해당되는 컬럼 파악
                        var startCol = ((23 + params.timeSlotIdx) * 9) + 20;
                        var endCol = ((23 + params.timeSlotIdx) * 9) + 28;

                        if(i >= startCol && endCol >= i){
                            if(params.prodOption === "1") {  // 단품+세트
                                if(i%3 === 2){
                                    columns[i].visible = true;
                                }else{
                                    columns[i].visible = false;
                                }
                            } else if(params.prodOption === "2") {  // 단품+구성
                                if(i%3 === 0){
                                    columns[i].visible = true;
                                }else{
                                    columns[i].visible = false;
                                }
                            } else if(params.prodOption === "3") {  // 단품+세트+구성
                                if(i%3 === 1){
                                    columns[i].visible = true;
                                }else{
                                    columns[i].visible = false;
                                }
                            } else if(params.prodOption === "4") {  // 모두표시
                                columns[i].visible = true;
                            }
                        }else{
                            columns[i].visible = false;
                        }
                    }
                }
            }
            // <-- //그리드 visible -->

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "상품별시간대매출_"+getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);