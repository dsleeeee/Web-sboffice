/****************************************************************
 *
 * 파일명 : dayTimeMrpizza.js
 * 설  명 : (미스터피자) 일별시간대 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.06.10     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

var optionData = [
    {"name":"일자별","value":"day"},
    {"name":"기간별","value":"period"}
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

/** 일별시간대 controller */
app.controller('dayTimeMrpizzaCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayTimeMrpizzaCtrl', $scope, $http, true));

    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
    $scope._setComboData("option", optionData);
    $scope.orgnFg        = gvOrgnFg;
    $scope.SaleTimeReadOnly = true;

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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

       // picker 사용시 호출 : 미사용시 호출안함
       $scope._makePickColumns("dayTimeMrpizzaCtrl");

       // add the new GroupRow to the grid's 'columnFooters' panel
       s.columnFooters.rows.push(new wijmo.grid.GroupRow());
       // add a sigma to the header to show that this is a summary row
       s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem            = {};
        dataItem.saleDate       = messages["dayTimeMrpizza.saleDate"];
        dataItem.yoil           = messages["dayTimeMrpizza.yoil"];
        dataItem.storeCnt       = messages["dayTimeMrpizza.storeCnt"];

        dataItem.saleQty        = messages["dayTimeMrpizza.sale"];
        dataItem.totSaleAmt     = messages["dayTimeMrpizza.sale"];
        dataItem.totDcAmt       = messages["dayTimeMrpizza.sale"];
        dataItem.realSaleAmt    = messages["dayTimeMrpizza.sale"];
        dataItem.saleCnt        = messages["dayTimeMrpizza.sale"];
        dataItem.rtnSaleCnt     = messages["dayTimeMrpizza.sale"];
        dataItem.realSaleCnt    = messages["dayTimeMrpizza.sale"];
        dataItem.billUprc       = messages["dayTimeMrpizza.sale"];

        // 시간대별 컬럼 생성
        var j=0;
        for (var i = 0; i < 24; i++) {
            j=i + 1;
            dataItem['realSaleCnt' + i] = i + "시 ~ " + j + "시";
            dataItem['saleQty' + i] = i + "시 ~ " + j + "시";
            dataItem['realSaleAmt' + i] = i + "시 ~ " + j + "시";
            dataItem['rate' + i] = i + "시 ~ " + j + "시";
            j=0;
        }

        // 시간대분류 컬럼 생성
        for (var i = 0; i < timeSlotColList.length; i++) {
            dataItem['realSaleCnt' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['saleQty' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['realSaleAmt' + timeSlotColList[i].value.replaceAll("~","")]     = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
            dataItem['rate' + timeSlotColList[i].value.replaceAll("~","")] = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
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

        // 비율 값 입력
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                if (col.binding.substring(0, 4) === "rate") {
                    for (var i = 0; i < 24; i++) {
                        if (item[("realSaleAmt" + i)] > 0) { // 실매출이 0보다 크면 비율 계산
                            item[("rate" + i)] = Math.round((item[("realSaleAmt" + i)] / item[("realSaleAmt")]) * 100) / 100;
                        }
                    }
                    for (var j = 0; j < timeSlotColList.length; j++) {
                        if (item[("realSaleAmt" + timeSlotColList[j].value.replaceAll("~", ""))] > 0) { // 실매출이 0보다 크면 비율 계산
                            item[("rate" + timeSlotColList[j].value.replaceAll("~", ""))] = Math.round((item[("realSaleAmt" + timeSlotColList[j].value.replaceAll("~", ""))] / item[("realSaleAmt")]) * 100) / 100;
                        }
                    }
                }
            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayTimeMrpizzaCtrl", function (event, data) {

        if($("input[name=optionFg]:checked").val() == "time") {
            if ($scope.startTime * 1 > $scope.endTime * 1) { // *1하는이유 : Time들이 String이라 int로 바꿀라고
                $scope._popMsg(messages["dayTimeMrpizza.startEnd"]); // 검색 시작 시간대가 검색 종료 시간대보다 큽니다.
                return false;
            }
        }

        // 일별시간대 리스트 조회
        $scope.searchDayTimeMrpizzaList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 과면세별 리스트 조회
    $scope.searchDayTimeMrpizzaList = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params       = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#dayTimeMrpizzaStoreCd").val();
        params.option = $scope.option;
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;
        params.timeSlot = $scope.timeSlot;
        params.timeCol= timeSlotCol;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/day/dayTimeMrpizza/dayTimeMrpizza/getDayTimeMrpizzaList.sb", params, function (){

            // 선택한 시간대에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;
            var start = $scope.startTime*1;
            var end = $scope.endTime*1;
            var columnsCnt = 3;

            // 옵션(기간별/일자별)에 따라 날짜, 요일 visible
            for (var j = 0; j < columnsCnt; j++) {
                if($scope.option === "period"){
                    if(columns[j].binding == "saleDate" || columns[j].binding == "yoil") {
                        columns[j].visible = false;
                    } else {
                        columns[j].visible = true;
                    }
                } else if($scope.option === "day"){
                    columns[j].visible = true;
                }
            }

            var defaultCol = 11;

            // 선택한 시간대에 따른 리스트 항목 visible
            for (var i = defaultCol; i < columns.length; i++) {
                columns[i].visible = false;

                if($("input[name=optionFg]:checked").val() == "time"){ // 시간대
                    for(var j = start; j <= end; j++) {
                        if (columns[i].binding == 'realSaleCnt'+j || columns[i].binding == 'saleQty'+j || columns[i].binding == 'realSaleAmt'+j || columns[i].binding == 'rate'+j) {
                            columns[i].visible = true;
                        }
                    }
                } else if($("input[name=optionFg]:checked").val() == "timeSlot") {   // 시간대분류
                    for (var j = 0; j < timeSlotColList.length; j++) {
                        if ($scope.timeSlot == timeSlotColList[j].value || $scope.timeSlot === "") {
                            if (columns[i].binding == 'realSaleCnt'+ timeSlotColList[j].value.replaceAll("~", "") || columns[i].binding == 'saleQty' + timeSlotColList[j].value.replaceAll("~", "") || columns[i].binding == 'realSaleAmt' + timeSlotColList[j].value.replaceAll("~", "") || columns[i].binding == 'rate' + timeSlotColList[j].value.replaceAll("~", "")) {
                                columns[i].visible = true;
                            }
                        }
                    }
                }
            }

        });
    };

    // 라디오버튼 클릭시 이벤트 발생
    $("input:radio[name=optionFg]").click(function () {
        if ($("input[name=optionFg]:checked").val() == "time") { // 시간대
            $("#timeOption").show();
            $("#timeSlotOption").hide();
        } else {       // 시간대분류
            $("#timeOption").hide();
            $("#timeSlotOption").show();
        }
    });

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

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
                messages["dayTimeMrpizza.dayTimeMrpizza"] + '_' + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + '_' + getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);