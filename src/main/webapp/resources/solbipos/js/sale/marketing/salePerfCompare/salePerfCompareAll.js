/****************************************************************
 *
 * 파일명 : salePerfCompareAll.js
 * 설  명 : 미스터피자 > 마케팅조회 > 취소현황 > 전체점포 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.31     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var dateFgData = [
    {"name":"일","value":"day"},
    {"name":"일기간","value":"dayPeriod"},
    {"name":"월","value":"month"},
    {"name":"월기간","value":"monthPeriod"},
    {"name":"년","value":"year"}
    /*{"name":"년기간","value":"yearPeriod"}*/
];

var currentYear = new Date().getFullYear();
var yearsData = [];
for (let y = 2000; y <= currentYear; y++) {
    yearsData.push(y.toString());
}

/** 취소현황(메인리스트) controller */
app.controller('salePerfCompareAllCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('salePerfCompareAllCtrl', $scope, $http, $timeout, true));

    $scope.srchStartDateAll = wcombo.genDateVal("#srchStartDateAll", getToday());
    $scope.srchEndDateAll   = wcombo.genDateVal("#srchEndDateAll", getToday());

    $scope.compStartDateAll = wcombo.genDateVal("#compStartDateAll", getToday());
    $scope.compEndDateAll   = wcombo.genDateVal("#compEndDateAll", getToday());

    // 콤보박스 셋팅
    $scope._setComboData("srchDateFgCombo", dateFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // picker 사용시 호출 : 미사용시 호출안함
        s.refresh();
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("salePerfCompareAllCtrl");

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.dlvrOrderFg        = messages["salePerfCompare.dlvrOrderFg"];
        dataItem.compSaleCntFran    = messages["salePerfCompare.compDate"];
        dataItem.compSaleAmtFran    = messages["salePerfCompare.compDate"];
        dataItem.compSaleCntDm      = messages["salePerfCompare.compDate"];
        dataItem.compSaleAmtDm      = messages["salePerfCompare.compDate"];
        dataItem.compTotSaleCnt     = messages["salePerfCompare.compDate"];
        dataItem.compTotSaleAmt     = messages["salePerfCompare.compDate"];
        dataItem.srchSaleCntFran    = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleAmtFran    = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleCntDm      = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleAmtDm      = messages["salePerfCompare.srchDate"];
        dataItem.srchTotSaleCnt     = messages["salePerfCompare.srchDate"];
        dataItem.srchTotSaleAmt     = messages["salePerfCompare.srchDate"];
        dataItem.grSaleCntFran      = messages["salePerfCompare.growthRate"];
        dataItem.grSaleAmtFran      = messages["salePerfCompare.growthRate"];
        dataItem.grSaleCntDm        = messages["salePerfCompare.growthRate"];
        dataItem.grSaleAmtDm        = messages["salePerfCompare.growthRate"];
        dataItem.grTotSaleCnt       = messages["salePerfCompare.growthRate"];
        dataItem.grTotSaleAmt       = messages["salePerfCompare.growthRate"];

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
    $scope.$on("salePerfCompareAllCtrl", function (event, data) {
        $scope.searchSalePerfCompareList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 코너별매출일자별 리스트 조회
    $scope.searchSalePerfCompareList = function () {

        var dateFg  = $scope.srchDateFg.selectedValue;
        var startDt = '';
        var endDt   = '';
        var diffDay = '';
        var compStartDt = '';
        var compEndDt   = '';
        var compDiffDay = '';
        var startDate   = '';
        var endDate     = '';
        var compStartDate   = '';
        var compEndDate     = '';

        if(dateFg === 'day' || dateFg === 'dayPeriod'){

            if(dateFg === 'dayPeriod') {
                startDt = new Date(wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyy-MM-dd'));
                endDt = new Date(wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyy-MM-dd'));
                diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000);

                if (startDt.getTime() > endDt.getTime()) {
                    $scope._popMsg(messages['cmm.dateChk.error']);
                    return false;
                }

                if (diffDay > 186) {
                    $scope._popMsg(messages['cmm.dateOver.1month.error']);
                    return false;
                }
                compStartDt = new Date(wijmo.Globalize.format($scope.compStartDateAll.value, 'yyyy-MM-dd'));
                compEndDt = new Date(wijmo.Globalize.format($scope.compEndDateAll.value, 'yyyy-MM-dd'));
                compDiffDay = (compEndDt.getTime() - compStartDt.getTime()) / (24 * 60 * 60 * 1000);

                if (compStartDt.getTime() > compEndDt.getTime()) {
                    $scope._popMsg(messages['cmm.dateChk.error']);
                    return false;
                }

                if (compDiffDay > 186) {
                    $scope._popMsg(messages['cmm.dateOver.1month.error']);
                    return false;
                }
            }
            startDate       = wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyyMMdd')
            endDate         = wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyyMMdd')
            compStartDate   = wijmo.Globalize.format($scope.compStartDateAll.value, 'yyyyMMdd')
            compEndDate     = wijmo.Globalize.format($scope.compEndDateAll.value, 'yyyyMMdd')

        }else if(dateFg === 'month' || dateFg === 'monthPeriod'){

            if(dateFg === 'monthPeriod') {
                startDt = new Date(wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyy-MM'));
                endDt = new Date(wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyy-MM'));
                diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30);

                if (startDt.getTime() > endDt.getTime()) {
                    $scope._popMsg(messages['cmm.dateChk.error']);
                    return false;
                }

                if (diffDay > 12) {
                    $scope._popMsg(messages['cmm.dateOver.1year.error']);
                    return false;
                }

                compStartDt = new Date(wijmo.Globalize.format($scope.compStartDateAll.value, 'yyyy-MM'));
                compEndDt = new Date(wijmo.Globalize.format($scope.compEndDateAll.value, 'yyyy-MM'));
                compDiffDay = (compEndDt.getTime() - compStartDt.getTime()) / (24 * 60 * 60 * 1000 *30);

                if (compStartDt.getTime() > compEndDt.getTime()) {
                    $scope._popMsg(messages['cmm.dateChk.error']);
                    return false;
                }

                if (compDiffDay > 12) {
                    $scope._popMsg(messages['cmm.dateOver.1year.error']);
                    return false;
                }
            }
            startDate       = wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyyMM')
            endDate         = wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyyMM')
            compStartDate   = wijmo.Globalize.format($scope.compStartDateAll.value, 'yyyyMM')
            compEndDate     = wijmo.Globalize.format($scope.compEndDateAll.value, 'yyyyMM')

        }else if(dateFg === 'year' || dateFg === 'yearPeriod'){

            if(dateFg === 'yearPeriod') {

                $scope._popMsg(messages['salePerfCompare.msg.yearPeriod']);
                return false;

                // startDt = parseInt($scope.srchStartDateAll.selectedValue);
                // endDt = parseInt($scope.srchEndDateAll.selectedValue);
                // diffDay = endDt - startDt;
                //
                // if (startDt > endDt) {
                //     $scope._popMsg(messages['cmm.dateChk.error']);
                //     return false;
                // }
                // if (diffDay > 3) {
                //     $scope._popMsg(messages['cmm.dateOver.3year.error']);
                //     return false;
                // }

                // compStartDt = parseInt($scope.compStartDateAll.selectedValue);
                // compEndDt = parseInt($scope.compEndDateAll.selectedValue);
                // compDiffDay = endDt - startDt;
                //
                // if (compStartDt > compEndDt) {
                //     $scope._popMsg(messages['cmm.dateChk.error']);
                //     return false;
                // }
                // if (compDiffDay > 3) {
                //     $scope._popMsg(messages['cmm.dateOver.3year.error']);
                //     return false;
                // }
            }
            startDate       = $scope.srchStartDateAll.selectedValue;
            endDate         = $scope.srchEndDateAll.selectedValue;
            compStartDate   = $scope.compStartDateAll.selectedValue;
            compEndDate     = $scope.compEndDateAll.selectedValue;

        }

        if(dateFg === 'day' || dateFg  === 'month' || dateFg === 'year'){
            endDate     = '';
            compEndDate = '';
        }

        // 파라미터
        var params              = {};
        params.dateFg           = dateFg;
        params.startDate        = startDate;
        params.endDate          = endDate;
        params.compStartDate    = compStartDate;
        params.compEndDate      = compEndDate;
        $scope._broadcast('salePerfCompareAllDtlCtrl',params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        // $scope._inquiryMain("/sale/marketing/salePerfCompare/salePerfCompare/getSalePerfCompareList.sb", params, function (){
        //     var grid = wijmo.Control.getControl("#salePerfCompareAllMainGrid");
        //     var dataItem         = {};
        //     var srchDate = '';
        //     var compDate = '';
        //     if(dateFg === 'day' || dateFg  === 'month' || dateFg === 'year'){
        //         srchDate = '(' + params.startDate + ')';
        //         compDate = '(' + params.compStartDate + ')';
        //     }else{
        //         srchDate = '(' + params.startDate + '~' + params.endDate + ')';
        //         compDate = '(' + params.compStartDate + '~' + params.compEndDate + ')';
        //     }
        //     dataItem.dlvrOrderFg        = messages["salePerfCompare.dlvrOrderFg"];
        //     dataItem.compSaleCntFran    = messages["salePerfCompare.compDate"] + compDate;
        //     dataItem.compSaleAmtFran    = messages["salePerfCompare.compDate"] + compDate;
        //     dataItem.compSaleCntDm      = messages["salePerfCompare.compDate"] + compDate;
        //     dataItem.compSaleAmtDm      = messages["salePerfCompare.compDate"] + compDate;
        //     dataItem.compTotSaleCnt     = messages["salePerfCompare.compDate"] + compDate;
        //     dataItem.compTotSaleAmt     = messages["salePerfCompare.compDate"] + compDate;
        //     dataItem.srchSaleCntFran    = messages["salePerfCompare.srchDate"] + srchDate;
        //     dataItem.srchSaleAmtFran    = messages["salePerfCompare.srchDate"] + srchDate;
        //     dataItem.srchSaleCntDm      = messages["salePerfCompare.srchDate"] + srchDate;
        //     dataItem.srchSaleAmtDm      = messages["salePerfCompare.srchDate"] + srchDate;
        //     dataItem.srchTotSaleCnt     = messages["salePerfCompare.srchDate"] + srchDate;
        //     dataItem.srchTotSaleAmt     = messages["salePerfCompare.srchDate"] + srchDate;
        //     dataItem.grSaleCntFran      = messages["salePerfCompare.growthRate"];
        //     dataItem.grSaleAmtFran      = messages["salePerfCompare.growthRate"];
        //     dataItem.grSaleCntDm        = messages["salePerfCompare.growthRate"];
        //     dataItem.grSaleAmtDm        = messages["salePerfCompare.growthRate"];
        //     dataItem.grTotSaleCnt       = messages["salePerfCompare.growthRate"];
        //     dataItem.grTotSaleAmt       = messages["salePerfCompare.growthRate"];
        //     grid.columnHeaders.rows[0].dataItem = dataItem;
        //
        //     $scope._broadcast('salePerfCompareAllDtlCtrl',params);
        //
        // });
    };

    // 구분 값 변경 시
    $scope.selectedIndexChanged = function (s) {
        // 기존 control 제거
        $scope.srchStartDateAll.dispose();
        $scope.srchEndDateAll.dispose();
        $scope.compStartDateAll.dispose();
        $scope.compEndDateAll.dispose();


        if(s.selectedValue === 'day' || s.selectedValue === 'month' || s.selectedValue === 'year'){
            // 일/월/연
            $("#srchRg").css('display', 'none');
            $("#srchEnd").css('display', 'none');
            $("#compRg").css('display', 'none');
            $("#compEnd").css('display', 'none');
        }else{
            // 일기간,월기간,연기간
            $("#srchRg").css('display', '');
            $("#srchEnd").css('display', '');
            $("#compRg").css('display', '');
            $("#compEnd").css('display', '');
        }


        if(s.selectedValue === 'day' || s.selectedValue === 'dayPeriod'){ // 일/일기간
            // 조회일자 설정
            $scope.srchStartDateAll = wcombo.genDateVal("#srchStartDateAll", getToday());
            $scope.srchEndDateAll   = wcombo.genDateVal("#srchEndDateAll", getToday());

            // 대비일자 설정
            // 대비일은 오늘 기준 전월 날짜
            var today = new Date();
            var year = today.getFullYear();
            var month = today.getMonth();
            var date = today.getDate();

            // 전월의 마지막 날짜
            var lastDayOfPrevMonth = new Date(year, month, 0).getDate();

            // 전월에 같은 일이 존재할 때만 대비일 설정
            if (date <= lastDayOfPrevMonth) {
                var prevMonthStart = new Date(year, month - 1, date);
                var prevMonthEnd   = new Date(year, month - 1, date);

                $scope.compStartDateAll = new wijmo.input.InputDate('#compStartDateAll', {
                    value: prevMonthStart
                });
                $scope.compEndDateAll = new wijmo.input.InputDate('#compEndDateAll', {
                    value: prevMonthEnd
                });
            } else {
                // 전월에 같은 일자가 없음 → 대비일 설정 안 함(오늘)
                $scope.compStartDateAll = new wijmo.input.InputDate('#compStartDateAll', {
                    value: getToday()
                });
                $scope.compEndDateAll = new wijmo.input.InputDate('#compEndDateAll', {
                    value: getToday()
                });
            }

            // 조회일자 변경 시 대비일자 변경 설정
            $scope.srchStartDateAll.valueChanged.addHandler(function(sender) {
                var selectedDate = sender.value;
                if (selectedDate) {
                    // 전월 같은 일자
                    var year = selectedDate.getFullYear();
                    var month = selectedDate.getMonth();
                    var date = selectedDate.getDate();

                    // 전월의 마지막 날 계산
                    var lastDayOfPrevMonth = new Date(year, month, 0).getDate();

                    // 전월에 해당 일이 존재하는 경우에만 설정
                    if (date <= lastDayOfPrevMonth) {
                        var prevMonthDate = new Date(year, month - 1, date);
                        $scope.compStartDateAll.value = prevMonthDate;
                    }
                }
            });

            $scope.srchEndDateAll.valueChanged.addHandler(function(sender) {
                var selectedDate = sender.value;
                if (selectedDate) {
                    // 전월 같은 일자
                    var year = selectedDate.getFullYear();
                    var month = selectedDate.getMonth();
                    var date = selectedDate.getDate();

                    var lastDayOfPrevMonth = new Date(year, month, 0).getDate();

                    if (date <= lastDayOfPrevMonth) {
                        var prevMonthDate = new Date(year, month - 1, date);
                        $scope.compEndDateAll.value = prevMonthDate;
                    }
                }
            });

        }else if(s.selectedValue === 'month' || s.selectedValue === 'monthPeriod'){
            var today = new Date();
            var lastYearSameMonth = new Date(today.getFullYear() - 1, today.getMonth(), 1);

            // 조회일자 설정
            $scope.srchStartDateAll = new wijmo.input.InputDate('#srchStartDateAll', {
                format       : "yyyy-MM",
                selectionMode: "2", // 달력 선택 모드(1:day 2:month)
                valueChanged: function(sender) {  // 조회일자 변경 시 대비일자 변경 설정
                    var selectedDate = sender.value;
                    if (selectedDate) {
                        var lastYearSameMonth = new Date(selectedDate.getFullYear() - 1, selectedDate.getMonth(), 1);
                        $scope.compStartDateAll.value = lastYearSameMonth;
                    }
                }
            });
            $scope.srchEndDateAll   = new wijmo.input.InputDate('#srchEndDateAll', {
                format       : "yyyy-MM",
                selectionMode: "2", // 달력 선택 모드(1:day 2:month)
                valueChanged: function(sender) { // 조회일자 변경 시 대비일자 변경 설정
                    var selectedDate = sender.value;
                    if (selectedDate) {
                        var lastYearSameMonth = new Date(selectedDate.getFullYear() - 1, selectedDate.getMonth(), 1);
                        $scope.compEndDateAll.value = lastYearSameMonth;
                    }
                }
            });
            // 대비일자 설정(조회일자 기준 전년도 같은 월)
            $scope.compStartDateAll = new wijmo.input.InputDate('#compStartDateAll', {
                format       : "yyyy-MM",
                selectionMode: "2", // 달력 선택 모드(1:day 2:month)
                value: lastYearSameMonth
            });
            $scope.compEndDateAll   = new wijmo.input.InputDate('#compEndDateAll', {
                format       : "yyyy-MM",
                selectionMode: "2", // 달력 선택 모드(1:day 2:month)
                value: lastYearSameMonth
            });

        }else if(s.selectedValue === 'year' || s.selectedValue === 'yearPeriod'){

            // 조회일자 설정
            $scope.srchStartDateAll = new wijmo.input.ComboBox('#srchStartDateAll', {
                itemsSource: yearsData,
                selectedValue: new Date().getFullYear().toString(), // 기본 선택값: 올해
                isEditable: false, // 사용자가 직접 입력하지 못하게 설정 (선택 전용)
                selectedIndexChanged: function(sender) { // 조회일자 변경 시 대비일자 변경 설정
                    var selectedYear = parseInt(sender.selectedValue);
                    if (!isNaN(selectedYear)) {
                        $scope.compStartDateAll.selectedValue = (selectedYear - 1).toString();
                    }
                }
            });
            $scope.srchEndDateAll = new wijmo.input.ComboBox('#srchEndDateAll', {
                itemsSource: yearsData,
                selectedValue: new Date().getFullYear().toString(), // 기본 선택값: 올해
                isEditable: false, // 사용자가 직접 입력하지 못하게 설정 (선택 전용)
                selectedIndexChanged: function(sender) { // 조회일자 변경 시 대비일자 변경 설정
                    var selectedYear = parseInt(sender.selectedValue);
                    if (!isNaN(selectedYear)) {
                        $scope.compEndDateAll.selectedValue = (selectedYear - 1).toString();
                    }
                }
            });
            // 대비일자 설정 (조회일자 기준 전년도)
            $scope.compStartDateAll = new wijmo.input.ComboBox('#compStartDateAll', {
                itemsSource: yearsData,
                selectedValue: (new Date().getFullYear()-1).toString(), // 기본 선택값: 올해
            });
            $scope.compEndDateAll = new wijmo.input.ComboBox('#compEndDateAll', {
                itemsSource: yearsData,
                selectedValue: (new Date().getFullYear()-1).toString(), // 기본 선택값: 올해
            });
        }
    };

    // 엑셀다운로드 (상단,하단 그리드 같이)
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 합계 행(GroupRow) 가져오기
        var groupRow = $scope.flex.columnFooters.rows[0];

        // 기존의 합계 행 데이터를 임시 저장
        var originalDataItem = groupRow.dataItem;

        // 첫 번째 데이터 열의 바인딩명 가져오기
        var firstColumnBinding = $scope.flex.columns[0].binding;

        // 첫번째 열에 '합계' 텍스트 임의 설정
        var newDataItem = {};
        newDataItem[firstColumnBinding] = '합계';
        groupRow.dataItem = newDataItem;

        // 취소현황 그리드
        var workBook1 = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
            includeColumnHeaders: true,
            includeCellStyles: false,
            function() {
                // 원래의 합계 행 데이터로 복원
                groupRow.dataItem = originalDataItem;
            }
        });

        var vScope = agrid.getScope("salePerfCompareAllDtlCtrl");

        // 합계 행(GroupRow) 가져오기
        var groupRowDtl = vScope.flex.columnFooters.rows[0];

        // 기존의 합계 행 데이터를 임시 저장
        var originalDataItemDtl = groupRowDtl.dataItem;

        // 첫 번째 데이터 열의 바인딩명 가져오기
        var firstColumnBindingDtl = vScope.flex.columns[0].binding;

        // 첫번째 열에 '합계' 텍스트 임의 설정
        var newDataItemDtl = {};
        newDataItemDtl[firstColumnBindingDtl] = '합계';
        groupRowDtl.dataItem = newDataItemDtl;

        // 취소현황 상세 그리드
        var workBook2 = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(vScope.flex, {
            includeColumnHeaders: true,
            includeCellStyles: false,
            function() {
                // 원래의 합계 행 데이터로 복원
                groupRowDtl.dataItem = originalDataItemDtl;
            }
        });

        // 시트 정보 push
        workBook1.sheets.push(workBook2.sheets[0]);
        workBook1.saveAsync("매출실적비교" + '_' + wijmo.Globalize.format($scope.srchStartDateAll.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format($scope.srchEndDateAll.value, 'yyyyMMdd') + '_' + getCurDateTime() + '.xlsx');
    }

}]);

/** 취소현황 상세(일자별 상세) controller */
app.controller('salePerfCompareAllDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('salePerfCompareAllDtlCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("salePerfCompareAllDtlCtrl");

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.dlvrInFg           = messages["salePerfCompare.dlvrInFg"];
        dataItem.compSaleCntFran    = messages["salePerfCompare.compDate"];
        dataItem.compSaleAmtFran    = messages["salePerfCompare.compDate"];
        dataItem.compSaleCntDm      = messages["salePerfCompare.compDate"];
        dataItem.compSaleAmtDm      = messages["salePerfCompare.compDate"];
        dataItem.compTotSaleCnt     = messages["salePerfCompare.compDate"];
        dataItem.compTotSaleAmt     = messages["salePerfCompare.compDate"];
        dataItem.srchSaleCntFran    = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleAmtFran    = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleCntDm      = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleAmtDm      = messages["salePerfCompare.srchDate"];
        dataItem.srchTotSaleCnt     = messages["salePerfCompare.srchDate"];
        dataItem.srchTotSaleAmt     = messages["salePerfCompare.srchDate"];
        dataItem.grSaleCntFran      = messages["salePerfCompare.growthRate"];
        dataItem.grSaleAmtFran      = messages["salePerfCompare.growthRate"];
        dataItem.grSaleCntDm        = messages["salePerfCompare.growthRate"];
        dataItem.grSaleAmtDm        = messages["salePerfCompare.growthRate"];
        dataItem.grTotSaleCnt       = messages["salePerfCompare.growthRate"];
        dataItem.grTotSaleAmt       = messages["salePerfCompare.growthRate"];

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
    }

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("salePerfCompareAllDtlCtrl", function (event, data) {
        if(data != undefined){
            $scope.dateFg           = data.dateFg;
            $scope.startDate        = data.startDate;
            $scope.endDate          = data.endDate;
            $scope.compStartDate    = data.compStartDate;
            $scope.compEndDate      = data.compEndDate;
        }
        $scope.searchSalePerfCompareDtlList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 코너별매출일자별 리스트 조회
    $scope.searchSalePerfCompareDtlList = function () {
        // 파라미터
        var params       = {};

        params.dateFg           = $scope.dateFg;
        params.startDate        = $scope.startDate;
        params.endDate          = $scope.endDate;
        params.compStartDate    = $scope.compStartDate;
        params.compEndDate      = $scope.compEndDate;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/marketing/salePerfCompare/salePerfCompare/getSalePerfCompareDtlList.sb", params,function (){
            var grid = wijmo.Control.getControl("#salePerfCompareAllDtlGrid");
            var dataItem         = {};
            var srchDate = '';
            var compDate = '';
            if(params.dateFg === 'day' || params.dateFg  === 'month' || params.dateFg === 'year'){
                srchDate = '(' + params.startDate + ')';
                compDate = '(' + params.compStartDate + ')';
            }else{
                srchDate = '(' + params.startDate + '~' + params.endDate + ')';
                compDate = '(' + params.compStartDate + '~' + params.compEndDate + ')';
            }
            dataItem.dlvrOrderFg        = messages["salePerfCompare.dlvrOrderFg"];
            dataItem.compSaleCntFran    = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compSaleAmtFran    = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compSaleCntDm      = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compSaleAmtDm      = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compTotSaleCnt     = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compTotSaleAmt     = messages["salePerfCompare.compDate"] + compDate;
            dataItem.srchSaleCntFran    = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchSaleAmtFran    = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchSaleCntDm      = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchSaleAmtDm      = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchTotSaleCnt     = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchTotSaleAmt     = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.grSaleCntFran      = messages["salePerfCompare.growthRate"];
            dataItem.grSaleAmtFran      = messages["salePerfCompare.growthRate"];
            dataItem.grSaleCntDm        = messages["salePerfCompare.growthRate"];
            dataItem.grSaleAmtDm        = messages["salePerfCompare.growthRate"];
            dataItem.grTotSaleCnt       = messages["salePerfCompare.growthRate"];
            dataItem.grTotSaleAmt       = messages["salePerfCompare.growthRate"];
            grid.columnHeaders.rows[0].dataItem = dataItem;
        });
        $scope.flex.refresh();

    };

    // 상세 그리드 초기화
    $scope.dtlGridDefault = function () {
        $timeout(function () {
            var cv          = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data     = cv;
            $scope.flex.refresh();
        }, 10);
    };

    // 엑셀다운로드
    $scope.excelDownloadDtl = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 합계 행(GroupRow) 가져오기
        var groupRow = $scope.flex.columnFooters.rows[0];

        // 기존의 합계 행 데이터를 임시 저장
        var originalDataItem = groupRow.dataItem;

        // 첫 번째 데이터 열의 바인딩명 가져오기
        var firstColumnBinding = $scope.flex.columns[0].binding;

        // 첫번째 열에 '합계' 텍스트 임의 설정
        var newDataItem = {};
        newDataItem[firstColumnBinding] = '합계';
        groupRow.dataItem = newDataItem;

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "매출실적비교(채널)" + '_' + $scope.startDate + '_' + $scope.endDate + '-' +getCurDateTime() + '.xlsx', function () {
                    // 원래의 합계 행 데이터로 복원
                    groupRow.dataItem = originalDataItem;

                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);