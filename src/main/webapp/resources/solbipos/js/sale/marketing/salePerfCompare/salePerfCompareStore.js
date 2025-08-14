/****************************************************************
 *
 * 파일명 : salePerfCompareStore.js
 * 설  명 : 미스터피자 > 마케팅조회 > 취소현황 > 선택점포 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.05     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 취소현황 선택점포 탭 controller */
app.controller('salePerfCompareStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('salePerfCompareStoreCtrl', $scope, $http, $timeout, true));

    $scope.srchStartDateStore = wcombo.genDateVal("#srchStartDateStore", getToday());
    $scope.srchEndDateStore   = wcombo.genDateVal("#srchEndDateStore", getToday());

    $scope.compStartDateStore = wcombo.genDateVal("#compStartDateStore", getToday());
    $scope.compEndDateStore   = wcombo.genDateVal("#compEndDateStore", getToday());

    // 콤보박스 셋팅
    $scope._setComboData("srchDateFgStoreCombo", dateFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // picker 사용시 호출 : 미사용시 호출안함
        s.refresh();
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("salePerfCompareStoreCtrl");

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.storeCd            = messages["salePerfCompare.storeCd"];
        dataItem.storeNm            = messages["salePerfCompare.storeNm"];
        dataItem.compSaleCntComm    = messages["salePerfCompare.compDate"];
        dataItem.compSaleAmtComm    = messages["salePerfCompare.compDate"];
        dataItem.compSaleCntDlvr    = messages["salePerfCompare.compDate"];
        dataItem.compSaleAmtDlvr    = messages["salePerfCompare.compDate"];
        dataItem.compSaleCntPack    = messages["salePerfCompare.compDate"];
        dataItem.compSaleAmtPack    = messages["salePerfCompare.compDate"];
        dataItem.srchSaleCntComm    = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleAmtComm    = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleCntDlvr    = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleAmtDlvr    = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleCntPack    = messages["salePerfCompare.srchDate"];
        dataItem.srchSaleAmtPack    = messages["salePerfCompare.srchDate"];
        dataItem.grSaleCntComm      = messages["salePerfCompare.growthRate"];
        dataItem.grSaleAmtComm      = messages["salePerfCompare.growthRate"];
        dataItem.grSaleCntDlvr      = messages["salePerfCompare.growthRate"];
        dataItem.grSaleAmtDlvr      = messages["salePerfCompare.growthRate"];
        dataItem.grSaleCntPack      = messages["salePerfCompare.growthRate"];
        dataItem.grSaleAmtPack      = messages["salePerfCompare.growthRate"];

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
    $scope.$on("salePerfCompareStoreCtrl", function (event, data) {
        $scope.searchSalePerfCompareStoreList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 매출실적비교 리스트 조회
    $scope.searchSalePerfCompareStoreList = function () {


        if(isEmptyObject($("#salePerfCompareStoreSelctStoreCd").val())){
            $scope._popMsg(messages["cmm.require.selectStore"]); // 매장을 선택해주세요
            return false;
        }
        if($("#salePerfCompareStoreSelctStoreCd").val().split(",").length > 10) {
            $scope._popMsg(messages["salePerfCompare.storeCntAlert"]); // 매장은 최대 10개 선택 가능합니다.
            return false;
        }


        var dateFg  = $scope.srchDateFgStore.selectedValue;
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

        // 조회구분별 날짜 제한
        if(dateFg === 'day' || dateFg === 'dayPeriod'){

            if(dateFg === 'dayPeriod') {

                // 조회일자
                startDt = new Date(wijmo.Globalize.format($scope.srchStartDateStore.value, 'yyyy-MM-dd'));
                endDt   = new Date(wijmo.Globalize.format($scope.srchEndDateStore.value, 'yyyy-MM-dd'));
                diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000);

                // 시작일자가 종료일자보다 빠른지 확인
                if (startDt.getTime() > endDt.getTime()) {
                    $scope._popMsg(messages['cmm.dateChk.error']);
                    return false;
                }

                // 조회일자 최대 한달(31일) 제한
                if (diffDay > 31) {
                    $scope._popMsg(messages['cmm.dateOver.1month.error']);
                    return false;
                }
                // 대비일자
                compStartDt = new Date(wijmo.Globalize.format($scope.compStartDateStore.value, 'yyyy-MM-dd'));
                compEndDt = new Date(wijmo.Globalize.format($scope.compEndDateStore.value, 'yyyy-MM-dd'));
                compDiffDay = (compEndDt.getTime() - compStartDt.getTime()) / (24 * 60 * 60 * 1000);

                // 시작일자가 종료일자보다 빠른지 확인
                if (compStartDt.getTime() > compEndDt.getTime()) {
                    $scope._popMsg(messages['cmm.dateChk.error']);
                    return false;
                }

                // 조회일자 최대 한달(31일) 제한
                if (compDiffDay > 31) {
                    $scope._popMsg(messages['cmm.dateOver.1month.error']);
                    return false;
                }
            }
            startDate       = wijmo.Globalize.format($scope.srchStartDateStore.value, 'yyyyMMdd')
            endDate         = wijmo.Globalize.format($scope.srchEndDateStore.value, 'yyyyMMdd')
            compStartDate   = wijmo.Globalize.format($scope.compStartDateStore.value, 'yyyyMMdd')
            compEndDate     = wijmo.Globalize.format($scope.compEndDateStore.value, 'yyyyMMdd')

        }else if(dateFg === 'month' || dateFg === 'monthPeriod'){

            if(dateFg === 'monthPeriod') {
                // 조회일자
                startDt = new Date(wijmo.Globalize.format($scope.srchStartDateStore.value, 'yyyy-MM'));
                endDt = new Date(wijmo.Globalize.format($scope.srchEndDateStore.value, 'yyyy-MM'));
                diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30);

                // 시작일자가 종료일자보다 빠른지 확인
                if (startDt.getTime() > endDt.getTime()) {
                    $scope._popMsg(messages['cmm.dateChk.error']);
                    return false;
                }

                // 조회일자 최대 1년 제한
                if (diffDay > 12) {
                    $scope._popMsg(messages['cmm.dateOver.1year.error']);
                    return false;
                }

                // 대비일자
                compStartDt = new Date(wijmo.Globalize.format($scope.compStartDateStore.value, 'yyyy-MM'));
                compEndDt = new Date(wijmo.Globalize.format($scope.compEndDateStore.value, 'yyyy-MM'));
                compDiffDay = (compEndDt.getTime() - compStartDt.getTime()) / (24 * 60 * 60 * 1000 *30);

                // 시작일자가 종료일자보다 빠른지 확인
                if (compStartDt.getTime() > compEndDt.getTime()) {
                    $scope._popMsg(messages['cmm.dateChk.error']);
                    return false;
                }

                // 조회일자 최대 1년 제한
                if (compDiffDay > 12) {
                    $scope._popMsg(messages['cmm.dateOver.1year.error']);
                    return false;
                }
            }
            startDate       = wijmo.Globalize.format($scope.srchStartDateStore.value, 'yyyyMM')
            endDate         = wijmo.Globalize.format($scope.srchEndDateStore.value, 'yyyyMM')
            compStartDate   = wijmo.Globalize.format($scope.compStartDateStore.value, 'yyyyMM')
            compEndDate     = wijmo.Globalize.format($scope.compEndDateStore.value, 'yyyyMM')

        }else if(dateFg === 'year' || dateFg === 'yearPeriod'){

            if(dateFg === 'yearPeriod') {

                $scope._popMsg(messages['salePerfCompare.msg.yearPeriod']);
                return false;

                // startDt = parseInt($scope.srchStartDateStore.selectedValue);
                // endDt = parseInt($scope.srchEndDateStore.selectedValue);
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

                // compStartDt = parseInt($scope.compStartDateStore.selectedValue);
                // compEndDt = parseInt($scope.compEndDateStore.selectedValue);
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
            startDate       = $scope.srchStartDateStore.selectedValue;
            endDate         = $scope.srchEndDateStore.selectedValue;
            compStartDate   = $scope.compStartDateStore.selectedValue;
            compEndDate     = $scope.compEndDateStore.selectedValue;

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
        params.storeCd          = $("#salePerfCompareStoreSelctStoreCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/marketing/salePerfCompare/salePerfCompare/getSalePerfCompareStoreList.sb", params, function (){

            // 그리드 상단에 조회일자 대비일자 표시
            var grid = wijmo.Control.getControl("#salePerfCompareStoreMainGrid");
            var dataItem         = {};
            var srchDate = '';
            var compDate = '';
            if(dateFg === 'day' || dateFg  === 'month' || dateFg === 'year'){
                srchDate = '(' + params.startDate + ')';
                compDate = '(' + params.compStartDate + ')';
            }else{
                srchDate = '(' + params.startDate + '~' + params.endDate + ')';
                compDate = '(' + params.compStartDate + '~' + params.compEndDate + ')';
            }
            dataItem.storeCd            = messages["salePerfCompare.storeCd"];
            dataItem.storeNm            = messages["salePerfCompare.storeNm"];
            dataItem.compSaleCntComm    = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compSaleAmtComm    = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compSaleCntDlvr    = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compSaleAmtDlvr    = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compSaleCntPack    = messages["salePerfCompare.compDate"] + compDate;
            dataItem.compSaleAmtPack    = messages["salePerfCompare.compDate"] + compDate;
            dataItem.srchSaleCntComm    = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchSaleAmtComm    = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchSaleCntDlvr    = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchSaleAmtDlvr    = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchSaleCntPack    = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.srchSaleAmtPack    = messages["salePerfCompare.srchDate"] + srchDate;
            dataItem.grSaleCntComm      = messages["salePerfCompare.growthRate"];
            dataItem.grSaleAmtComm      = messages["salePerfCompare.growthRate"];
            dataItem.grSaleCntDlvr      = messages["salePerfCompare.growthRate"];
            dataItem.grSaleAmtDlvr      = messages["salePerfCompare.growthRate"];
            dataItem.grSaleCntPack      = messages["salePerfCompare.growthRate"];
            dataItem.grSaleAmtPack      = messages["salePerfCompare.growthRate"];
            grid.columnHeaders.rows[0].dataItem = dataItem;

        });
    };

    // 구분 값 변경 시
    $scope.selectedIndexChanged = function (s) {

        // 기존 control 제거
        $scope.srchStartDateStore.dispose();
        $scope.srchEndDateStore.dispose();
        $scope.compStartDateStore.dispose();
        $scope.compEndDateStore.dispose();


        if(s.selectedValue === 'day' || s.selectedValue === 'month' || s.selectedValue === 'year'){
            // 일/월/연
            $("#srchRgStore").css('display', 'none');
            $("#srchEndStore").css('display', 'none');
            $("#compRgStore").css('display', 'none');
            $("#compEndStore").css('display', 'none');
        }else{
            // 일기간,월기간,연기간
            $("#srchRgStore").css('display', '');
            $("#srchEndStore").css('display', '');
            $("#compRgStore").css('display', '');
            $("#compEndStore").css('display', '');
        }


        if(s.selectedValue === 'day' || s.selectedValue === 'dayPeriod'){ // 일/일기간
            // 조회일자 설정
            $scope.srchStartDateStore = wcombo.genDateVal("#srchStartDateStore", getToday());
            $scope.srchEndDateStore   = wcombo.genDateVal("#srchEndDateStore", getToday());

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

                $scope.compStartDateStore = new wijmo.input.InputDate('#compStartDateStore', {
                    value: prevMonthStart
                });
                $scope.compEndDateStore = new wijmo.input.InputDate('#compEndDateStore', {
                    value: prevMonthEnd
                });
            } else {
                // 전월에 같은 일자가 없음 → 대비일 설정 안 함(오늘)
                $scope.compStartDateStore = new wijmo.input.InputDate('#compStartDateStore', {
                    value: getToday()
                });
                $scope.compEndDateStore = new wijmo.input.InputDate('#compEndDateStore', {
                    value: getToday()
                });
            }

            // 조회일자 변경 시 대비일자 변경 설정
            $scope.srchStartDateStore.valueChanged.addHandler(function(sender) {
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
                        $scope.compStartDateStore.value = prevMonthDate;
                    }
                }
            });

            $scope.srchEndDateStore.valueChanged.addHandler(function(sender) {
                var selectedDate = sender.value;
                if (selectedDate) {
                    // 전월 같은 일자
                    var year = selectedDate.getFullYear();
                    var month = selectedDate.getMonth();
                    var date = selectedDate.getDate();

                    var lastDayOfPrevMonth = new Date(year, month, 0).getDate();

                    if (date <= lastDayOfPrevMonth) {
                        var prevMonthDate = new Date(year, month - 1, date);
                        $scope.compEndDateStore.value = prevMonthDate;
                    }
                }
            });

        }else if(s.selectedValue === 'month' || s.selectedValue === 'monthPeriod'){
            var today = new Date();
            var lastYearSameMonth = new Date(today.getFullYear() - 1, today.getMonth(), 1);

            // 조회일자 설정
            $scope.srchStartDateStore = new wijmo.input.InputDate('#srchStartDateStore', {
                format       : "yyyy-MM",
                selectionMode: "2", // 달력 선택 모드(1:day 2:month)
                valueChanged: function(sender) {  // 조회일자 변경 시 대비일자 변경 설정
                    var selectedDate = sender.value;
                    if (selectedDate) {
                        var lastYearSameMonth = new Date(selectedDate.getFullYear() - 1, selectedDate.getMonth(), 1);
                        $scope.compStartDateStore.value = lastYearSameMonth;
                    }
                }
            });
            $scope.srchEndDateStore   = new wijmo.input.InputDate('#srchEndDateStore', {
                format       : "yyyy-MM",
                selectionMode: "2", // 달력 선택 모드(1:day 2:month)
                valueChanged: function(sender) { // 조회일자 변경 시 대비일자 변경 설정
                    var selectedDate = sender.value;
                    if (selectedDate) {
                        var lastYearSameMonth = new Date(selectedDate.getFullYear() - 1, selectedDate.getMonth(), 1);
                        $scope.compEndDateStore.value = lastYearSameMonth;
                    }
                }
            });
            // 대비일자 설정(조회일자 기준 전년도 같은 월)
            $scope.compStartDateStore = new wijmo.input.InputDate('#compStartDateStore', {
                format       : "yyyy-MM",
                selectionMode: "2", // 달력 선택 모드(1:day 2:month)
                value: lastYearSameMonth
            });
            $scope.compEndDateStore   = new wijmo.input.InputDate('#compEndDateStore', {
                format       : "yyyy-MM",
                selectionMode: "2", // 달력 선택 모드(1:day 2:month)
                value: lastYearSameMonth
            });

        }else if(s.selectedValue === 'year' || s.selectedValue === 'yearPeriod'){

            // 조회일자 설정
            $scope.srchStartDateStore = new wijmo.input.ComboBox('#srchStartDateStore', {
                itemsSource: yearsData,
                selectedValue: new Date().getFullYear().toString(), // 기본 선택값: 올해
                isEditable: false, // 사용자가 직접 입력하지 못하게 설정 (선택 전용)
                selectedIndexChanged: function(sender) { // 조회일자 변경 시 대비일자 변경 설정
                    var selectedYear = parseInt(sender.selectedValue);
                    if (!isNaN(selectedYear)) {
                        $scope.compStartDateStore.selectedValue = (selectedYear - 1).toString();
                    }
                }
            });
            $scope.srchEndDateStore = new wijmo.input.ComboBox('#srchEndDateStore', {
                itemsSource: yearsData,
                selectedValue: new Date().getFullYear().toString(), // 기본 선택값: 올해
                isEditable: false, // 사용자가 직접 입력하지 못하게 설정 (선택 전용)
                selectedIndexChanged: function(sender) { // 조회일자 변경 시 대비일자 변경 설정
                    var selectedYear = parseInt(sender.selectedValue);
                    if (!isNaN(selectedYear)) {
                        $scope.compEndDateStore.selectedValue = (selectedYear - 1).toString();
                    }
                }
            });
            // 대비일자 설정 (조회일자 기준 전년도)
            $scope.compStartDateStore = new wijmo.input.ComboBox('#compStartDateStore', {
                itemsSource: yearsData,
                selectedValue: (new Date().getFullYear()-1).toString(), // 기본 선택값: 올해
            });
            $scope.compEndDateStore = new wijmo.input.ComboBox('#compEndDateStore', {
                itemsSource: yearsData,
                selectedValue: (new Date().getFullYear()-1).toString(), // 기본 선택값: 올해
            });
        }
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload = function () {

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
                "매출실적비교(선택점포)_" + wijmo.Globalize.format($scope.srchStartDateStore.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format($scope.srchEndDateStore.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);