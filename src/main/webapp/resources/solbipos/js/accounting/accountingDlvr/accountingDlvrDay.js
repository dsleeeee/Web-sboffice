/****************************************************************
 *
 * 파일명 : accountingDlvrDay.js
 * 설  명 : 벤슨 > 회계관리 > 배달비현황 > 일별 탭JavaScript
 *
 *    수정일자      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.16    김유승        1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('accountingDlvrDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('accountingDlvrDayCtrl', $scope, $http, true));

    var startDate = wcombo.genDateVal("#srchAccountingDlvrDayStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchAccountingDlvrDayEndDate", gvEndDate);

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

    $scope.$on("accountingDlvrDayCtrl", function (event, data) {
        $scope.searchAccountingDlvrDayList();
        event.preventDefault();
    });

    // 배달비현황 일별 리스트 조회
    $scope.searchAccountingDlvrDayList = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000);

        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#accountingDlvrDayStoreCd").val();
        params.listScale = 500;

        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }

        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/accounting/accountingDlvr/accountingDlvrDay/getAccountingDlvrDayList.sb", params, function (response) {
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
            }, "배달비현황_일별_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + "_" + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + "_" + getCurDateTime() + ".xlsx", function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownloadSearch = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000);

        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#accountingDlvrDayStoreCd").val();

        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function () {
            $scope._broadcast('accountingDlvrDayExcelCtrl', params);
        });
    };
}]);

app.controller('accountingDlvrDayExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('accountingDlvrDayExcelCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.$on("accountingDlvrDayExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    // 조회조건 기준 전체 엑셀다운로드
    $scope.searchExcelList = function (params) {
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/accounting/accountingDlvr/accountingDlvrDay/getAccountingDlvrDayExcelList.sb", params, function (response) {
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
                }, "배달비현황_일별_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime() + ".xlsx");
            }, 10);
        });
    };
}]);
