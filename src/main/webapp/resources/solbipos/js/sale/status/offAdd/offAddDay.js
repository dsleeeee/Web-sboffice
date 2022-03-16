/****************************************************************
 *
 * 파일명 : offAddDay.js
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
 *  일별 매출 조회 그리드 생성
 */
app.controller('offAddDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('offAddDayCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    $scope.startDateDay = wcombo.genDateVal("#startDateOffAddDay", gvStartDate);
    $scope.endDateDay = wcombo.genDateVal("#endDateOffAddDay", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("offAddDayCtrl");

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
                    params.storeCd = $("#offAddDayStoreCd").val();
                    params.startDate = wijmo.Globalize.format($scope.startDateDay.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format($scope.endDateDay.value, 'yyyyMMdd');

                    var storeScope = agrid.getScope('offAddDayDetailCtrl');
                    storeScope._broadcast('offAddDayDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("offAddDayCtrl", function(event, data) {
        $scope.searchOffAddDay();
        event.preventDefault();
    });

    $scope.searchOffAddDay = function() {

        var startDt = new Date(wijmo.Globalize.format($scope.startDateDay.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.endDateDay.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            s_alert.pop(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 세달(93 일) 제한
        if (diffDay > 93) {
            s_alert.pop(messages['cmm.dateOver.3month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.startDateDay.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format($scope.endDateDay.value, 'yyyyMMdd'); //조회기간
        params.storeCd = $("#offAddDayStoreCd").val();

        $scope._inquiryMain("/sale/status/offAdd/offAdd/getOffAddDayList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('offAddDayDetailCtrl');
                storeScope._gridDataInit();   // 그리드 초기화
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.offAddDayStoreShow = function () {
        $scope._broadcast('offAddDayStoreCtrl');
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
                messages["offAdd.offAdd"] + '_' + messages["offAdd.day"] + '_' + getCurDateTime() +'.xlsx', function () {
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
app.controller('offAddDayDetailCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('offAddDayDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.orderAddFgDataMap = new wijmo.grid.DataMap(orderAddFgData, 'value', 'name'); //승인여부

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("offAddDayDetailCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("offAddDayDetailCtrl", function(event, data) {
        $scope.searchOffAddDayDetail(data);
        event.preventDefault();
    });

    $scope.searchOffAddDayDetail = function(data) {
        var params = {};
        params.saleDate = data.saleDate;
        params.storeCd = data.storeCd;
        params.startDate = data.startDate;
        params.endDate = data.endDate;

        $scope._inquiryMain("/sale/status/offAdd/offAdd/getOffAddDayDetailList.sb", params, function() {}, false);
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
                messages["offAdd.offAdd"] + '_' + messages["offAdd.day"] + '(' + messages["offAdd.dtl"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);