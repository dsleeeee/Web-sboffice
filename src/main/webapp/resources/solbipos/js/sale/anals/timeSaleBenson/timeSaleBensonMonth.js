/****************************************************************
 *
 * 파일명 : timeSaleBensonMonth.js
 * 설  명 : 벤슨 > 매출분석 > 시간대매출 > 월별 탭 JavaScript
 *
 *    수정일자      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.20    김유승        1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// 일자옵션 (월별/기간합)
var monthOptionComboData = [
    {"name": "월별", "value": "date"},
    {"name": "기간합", "value": "period"}
];

// 매장옵션 (전체/매장별)
var monthStoreOptionComboData = [
    {"name": "전체", "value": "all"},
    {"name": "매장별", "value": "store"}
];

app.controller('timeSaleBensonMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('timeSaleBensonMonthCtrl', $scope, $http, true));

    var startMonth = new wijmo.input.InputDate('#srchTimeSaleBensonMonthStartDate', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#srchTimeSaleBensonMonthEndDate', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // gvStartDate/gvEndDate 는 "yyyyMMdd" 형태의 문자열이라 Date로 변환 후 세팅
    if (gvStartDate && gvStartDate.length === 8) {
        startMonth.value = new Date(gvStartDate.substr(0, 4) + "-" + gvStartDate.substr(4, 2) + "-" + gvStartDate.substr(6, 2));
    }
    if (gvEndDate && gvEndDate.length === 8) {
        endMonth.value = new Date(gvEndDate.substr(0, 4) + "-" + gvEndDate.substr(4, 2) + "-" + gvEndDate.substr(6, 2));
    }

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("monthOptionCombo", monthOptionComboData);
    $scope._setComboData("monthStoreOptionCombo", monthStoreOptionComboData);

    // 기본값
    $scope.dayOption = "date";
    $scope.storeOption = "all";

    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                wijmo.addClass(e.cell, 'wj-custom-readonly');
            }
        });
    };

    // 매장옵션 변경 : 매장선택(멀티) 노출여부만 갱신 (그리드는 조회 시점에 반영)
    $scope.onStoreOptionChanged = function (s) {
        $scope.storeOption = s.selectedValue;

        if ($scope.storeOption === "store") {
            $("#timeSaleBensonMonthStoreSelectTh, #timeSaleBensonMonthStoreSelectTd").show();
        } else {
            $("#timeSaleBensonMonthStoreSelectTh, #timeSaleBensonMonthStoreSelectTd").hide();
            delStore("timeSaleBensonMonthStore", "M");
        }
    };

    // 조회조건(일자옵션/매장옵션)에 맞춰 그리드 컬럼 표시/숨김 : 조회 완료 시점에만 반영
    // 매번 전체 컬럼을 visible=true로 리셋한 뒤 조건에 맞는 컬럼만 숨김 (dayProd.js 방식)
    function applyColumnVisible(flex, dayOption, storeOption) {
        var columns = flex.columns;
        var i;

        for (i = 0; i < columns.length; i++) {
            columns[i].visible = true;
        }

        for (i = 0; i < columns.length; i++) {
            if (dayOption === "date") {
                if (columns[i].binding === "dayFrom" || columns[i].binding === "dayTo") {
                    columns[i].visible = false;
                }
            } else if (dayOption === "period") {
                if (columns[i].binding === "saleYm") {
                    columns[i].visible = false;
                }
            }

            if (storeOption === "all") {
                if (columns[i].binding === "storeCd" || columns[i].binding === "storeNm") {
                    columns[i].visible = false;
                }
            }
        }
    }

    // <-- 검색 호출 -->
    $scope.$on("timeSaleBensonMonthCtrl", function (event, data) {
        $scope.searchTimeSaleBensonMonthList();
        event.preventDefault();
    });

    // 시간대매출 월별 리스트 조회
    $scope.searchTimeSaleBensonMonthList = function () {

        var startYm = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        var endYm = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        var startYear = parseInt(startYm.substring(0, 4), 10);
        var startMon = parseInt(startYm.substring(4, 6), 10);
        var endYear = parseInt(endYm.substring(0, 4), 10);
        var endMon = parseInt(endYm.substring(4, 6), 10);
        var diffMonth = ((endYear - startYear) * 12) + (endMon - startMon);

        if (startYm > endYm) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if (diffMonth > 11) {
            $scope._popMsg(messages['timeSaleBenson.dateOver.12month']);
            return false;
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.dayOption = $scope.dayOption;
        params.storeOption = $scope.storeOption;
        params.storeCds = $("#timeSaleBensonMonthStoreCd").val();
        params.listScale = 500;

        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }

        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/sale/anals/timeSaleBenson/timeSaleBensonMonth/getTimeSaleBensonMonthList.sb", params, function (response) {
            var grid = $scope.flex;
            var list = response.data.list;

            // 조회에 사용된 조건 기준으로 컬럼 표시/숨김 반영 (콤보 변경 즉시가 아니라 조회 완료 시점)
            applyColumnVisible(grid, params.dayOption, params.storeOption);

            grid.itemsSource = list;
            grid.itemsSource.trackChanges = true;

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

    // 현재화면 엑셀다운로드
    $scope.excelDownloadCurrent = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, "시간대매출_월별_" + wijmo.Globalize.format(startMonth.value, 'yyyyMM') + "_" + wijmo.Globalize.format(endMonth.value, 'yyyyMM') + "_" + getCurDateTime() + ".xlsx", function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownloadSearch = function () {
        var startYm = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        var endYm = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        var startYear = parseInt(startYm.substring(0, 4), 10);
        var startMon = parseInt(startYm.substring(4, 6), 10);
        var endYear = parseInt(endYm.substring(0, 4), 10);
        var endMon = parseInt(endYm.substring(4, 6), 10);
        var diffMonth = ((endYear - startYear) * 12) + (endMon - startMon);

        if (startYm > endYm) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if (diffMonth > 11) {
            $scope._popMsg(messages['timeSaleBenson.dateOver.12month']);
            return false;
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.dayOption = $scope.dayOption;
        params.storeOption = $scope.storeOption;
        params.storeCds = $("#timeSaleBensonMonthStoreCd").val();

        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function () {
            $scope._broadcast('timeSaleBensonMonthExcelCtrl', params);
        });
    };
}]);

app.controller('timeSaleBensonMonthExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('timeSaleBensonMonthExcelCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.$on("timeSaleBensonMonthExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    // 조회조건 기준 전체 엑셀다운로드
    $scope.searchExcelList = function (params) {
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/sale/anals/timeSaleBenson/timeSaleBensonMonth/getTimeSaleBensonMonthExcelList.sb", params, function (response) {
            var list = response.data.list;

            if (list.length === undefined || list.length === 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]);
                return false;
            }

            $scope.excelFlex.itemsSource = list;
            $scope.excelFlex.itemsSource.trackChanges = true;

            // 조회에 사용된 조건 기준으로 컬럼 표시/숨김 반영 (전체 리셋 후 조건에 맞는 컬럼만 숨김)
            var columns = $scope.excelFlex.columns;
            var i;
            for (i = 0; i < columns.length; i++) {
                columns[i].visible = true;
            }
            for (i = 0; i < columns.length; i++) {
                if (params.dayOption === "date") {
                    if (columns[i].binding === "dayFrom" || columns[i].binding === "dayTo") {
                        columns[i].visible = false;
                    }
                } else if (params.dayOption === "period") {
                    if (columns[i].binding === "saleYm") {
                        columns[i].visible = false;
                    }
                }
                if (params.storeOption === "all") {
                    if (columns[i].binding === "storeCd" || columns[i].binding === "storeNm") {
                        columns[i].visible = false;
                    }
                }
            }

            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    includeColumns: function (column) {
                        return column.visible;
                    }
                }, "시간대매출_월별_" + params.startMonth + "_" + params.endMonth + "_" + getCurDateTime() + ".xlsx");
            }, 10);
        });
    };
}]);
