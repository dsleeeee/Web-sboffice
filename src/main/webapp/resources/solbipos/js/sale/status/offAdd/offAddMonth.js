/****************************************************************
 *
 * 파일명 : offAddMonth.js
 * 설  명 : 기간별매출 > 설정기간별탭 > 오프라인추가매출현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.11     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var orderAddFgData = [
    {"name":"오프라인","value":"1"},
    {"name":"온라인","value":"2"}
]
/**
 *  월별 매출 조회 그리드 생성
 */
app.controller('offAddMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('offAddMonthCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startDateOffAddMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endDateOffAddMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("offAddMonthCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "saleDate") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 오프라인추가금액 클릭시 상세정보 조회
                if ( col.binding === "saleDate") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.saleDate  = selectedRow.saleDate.replaceAll("-","");
                    params.storeCd = $("#offAddMonthStoreCd").val();
                    params.startDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
                    params.endDate = wijmo.Globalize.format(endMonth.value, 'yyyyMM');

                    var storeScope = agrid.getScope('offAddMonthDetailCtrl');
                    storeScope._broadcast('offAddMonthDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("offAddMonthCtrl", function(event, data) {
        $scope.searchOffAddMonth();
        event.preventDefault();
    });

    $scope.searchOffAddMonth = function() {

        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            s_alert.pop(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 세달(93 일) 제한
        if (diffDay > 2) {
            s_alert.pop(messages['cmm.dateOver.3month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM'); //조회기간
        params.endDate = wijmo.Globalize.format(endMonth.value, 'yyyyMM'); //조회기간
        params.storeCd = $("#offAddMonthStoreCd").val();

        $scope._inquiryMain("/sale/status/offAdd/offAdd/getOffAddMonthList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('offAddMonthDetailCtrl');
                storeScope._gridDataInit();   // 그리드 초기화
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.offAddMonthStoreShow = function () {
        $scope._broadcast('offAddMonthStoreCtrl');
    };

    // 시간대별 엑셀 다운로드
    $scope.excelDownloadPeriodSaleTime = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                messages["offAdd.offAdd"] + '_' + messages["offAdd.month"] + '_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);

/**
 *  시간대별 매출 상세조회 그리드 생성
 */
app.controller('offAddMonthDetailCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('offAddMonthDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.orderAddFgDataMap = new wijmo.grid.DataMap(orderAddFgData, 'value', 'name'); //승인여부

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("offAddMonthDetailCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("offAddMonthDetailCtrl", function(event, data) {
        $scope.searchOffAddMonthDetail(data);
        event.preventDefault();
    });

    $scope.searchOffAddMonthDetail = function(data) {
        var params = {};
        params.saleDate = data.saleDate;
        params.storeCd = data.storeCd;
        params.startDate = data.startDate;
        params.endDate = data.endDate;

        $scope._inquiryMain("/sale/status/offAdd/offAdd/getOffAddMonthDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매출상세 엑셀 다운로드
    $scope.excelDownloadPeriodSaleDtl = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                messages["offAdd.offAdd"] + '_' + messages["offAdd.month"] + '(' + messages["offAdd.dtl"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);