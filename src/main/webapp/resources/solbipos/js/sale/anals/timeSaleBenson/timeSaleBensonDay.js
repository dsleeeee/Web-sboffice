/****************************************************************
 *
 * 파일명 : timeSaleBensonDay.js
 * 설  명 : 벤슨 > 매출분석 > 시간대매출 > 일별 탭 JavaScript
 *
 *    수정일자      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.20    김유승        1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// 일자옵션 (일자별/기간합)
var dayOptionComboData = [
    {"name": "일자별", "value": "date"},
    {"name": "기간합", "value": "period"}
];

// 매장옵션 (전체/매장별)
var dayStoreOptionComboData = [
    {"name": "전체", "value": "all"},
    {"name": "매장별", "value": "store"}
];

app.controller('timeSaleBensonDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('timeSaleBensonDayCtrl', $scope, $http, true));

    var startDate = wcombo.genDateVal("#srchTimeSaleBensonDayStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchTimeSaleBensonDayEndDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("dayOptionCombo", dayOptionComboData);
    $scope._setComboData("dayStoreOptionCombo", dayStoreOptionComboData);

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
            $("#timeSaleBensonDayStoreSelectTh, #timeSaleBensonDayStoreSelectTd").show();
        } else {
            $("#timeSaleBensonDayStoreSelectTh, #timeSaleBensonDayStoreSelectTd").hide();
            delStore("timeSaleBensonDayStore", "M");
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
                if (columns[i].binding === "saleDate") {
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
    $scope.$on("timeSaleBensonDayCtrl", function (event, data) {
        $scope.searchTimeSaleBensonDayList();
        event.preventDefault();
    });

    // 시간대매출 일별 리스트 조회
    $scope.searchTimeSaleBensonDayList = function () {

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
        params.dayOption = $scope.dayOption;
        params.storeOption = $scope.storeOption;
        params.storeCds = $("#timeSaleBensonDayStoreCd").val();
        params.listScale = 500;

        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }

        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/sale/anals/timeSaleBenson/timeSaleBensonDay/getTimeSaleBensonDayList.sb", params, function (response) {
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
            }, "시간대매출_일별_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + "_" + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + "_" + getCurDateTime() + ".xlsx", function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownload = function () {
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
        params.dayOption = $scope.dayOption;
        params.storeOption = $scope.storeOption;
        params.storeCds = $("#timeSaleBensonDayStoreCd").val();

        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function () {
            $scope._broadcast('timeSaleBensonDayExcelCtrl', params);
        });
    };
}]);

app.controller('timeSaleBensonDayExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('timeSaleBensonDayExcelCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.$on("timeSaleBensonDayExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    // 조회조건 기준 전체 엑셀다운로드
    $scope.searchExcelList = function (params) {
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/sale/anals/timeSaleBenson/timeSaleBensonDay/getTimeSaleBensonDayExcelList.sb", params, function (response) {
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
                    if (columns[i].binding === "saleDate") {
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
                }, "시간대매출_일별_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime() + ".xlsx");
            }, 10);
        });
    };
}]);
