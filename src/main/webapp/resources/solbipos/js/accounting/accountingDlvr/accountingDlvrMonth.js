/****************************************************************
 *
 * 파일명 : accountingDlvrMonth.js
 * 설  명 : 벤슨 > 회계관리 > 배달비현황 > 월별 탭 JavaScript
 *
 *    수정일자      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.16    김유승        1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('accountingDlvrMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('accountingDlvrMonthCtrl', $scope, $http, true));

    var startMonth = new wijmo.input.InputDate('#srchAccountingDlvrMonthStartDate', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#srchAccountingDlvrMonthEndDate', {
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

    $scope.$on("accountingDlvrMonthCtrl", function (event, data) {
        $scope.searchAccountingDlvrMonthList();
        event.preventDefault();
    });

    // 배달비현황 월별 리스트 조회
    $scope.searchAccountingDlvrMonthList = function () {

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
            $scope._popMsg(messages['accountingDlvr.dateOver.12month']);
            return false;
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeCds = $("#accountingDlvrMonthStoreCd").val();
        params.listScale = 500;

        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }

        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/accounting/accountingDlvr/accountingDlvrMonth/getAccountingDlvrMonthList.sb", params, function (response) {
            var grid = $scope.flex;
            var list = response.data.list;

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
            }, "배달비현황_월별_" + wijmo.Globalize.format(startMonth.value, 'yyyyMM') + "_" + wijmo.Globalize.format(endMonth.value, 'yyyyMM') + "_" + getCurDateTime() + ".xlsx", function () {
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
            $scope._popMsg(messages['accountingDlvr.dateOver.12month']);
            return false;
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.storeCds = $("#accountingDlvrMonthStoreCd").val();

        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function () {
            $scope._broadcast('accountingDlvrMonthExcelCtrl', params);
        });
    };
}]);

app.controller('accountingDlvrMonthExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('accountingDlvrMonthExcelCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.$on("accountingDlvrMonthExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    // 조회조건 기준 전체 엑셀다운로드
    $scope.searchExcelList = function (params) {
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/accounting/accountingDlvr/accountingDlvrMonth/getAccountingDlvrMonthExcelList.sb", params, function (response) {
            var list = response.data.list;

            if (list.length === undefined || list.length === 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]);
                return false;
            }

            $scope.excelFlex.itemsSource = list;
            $scope.excelFlex.itemsSource.trackChanges = true;

            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    includeColumns: function (column) {
                        return column.visible;
                    }
                }, "배달비현황_월별_" + params.startMonth + "_" + params.endMonth + "_" + getCurDateTime() + ".xlsx");
            }, 10);
        });
    };
}]);
