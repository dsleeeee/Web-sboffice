/****************************************************************
 *
 * 파일명 : storeTotal.js
 * 설  명 : 입금/공제관리 - 매장별집계 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.20     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매장별집계
app.controller('storeTotalCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeTotalCtrl', $scope, $http, true));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "storeCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.hostElement.addEventListener('mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if (col.binding === "storeCd") { // 매장코드 클릭
                    var params = {};
                    params.storeCd = selectedRow.storeCd;
                    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
                    $scope._broadcast('storeTotalDtlCtrl', params);
                }
            }
        });

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.$on("storeTotalCtrl", function(event, data) {

        // 매장별집계 리스트 조회
        $scope.searchStoreTotal();
        event.preventDefault();

    });

    // 매장별집계 리스트 조회
    $scope.searchStoreTotal = function(){

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#selectStoreCd").val();

        $scope._inquiryMain('/excclc/excclc/depositDdc/getStoreTotalList.sb', params);

        // 상세내역 grid 초기화
        var scope = agrid.getScope('storeTotalDtlCtrl');
        scope._gridDataInit();
        scope._broadcast('storeTotalDtlCtrl', null);
    };

    // 입금/기타공제 등록 팝업
    $scope.depositDdcReg = function(){

        $scope.depositDdcRegLayer.show(true);

        var params      = {};
        $scope._broadcast('depositDdcRegCtrl', params);
    };

    // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.selectStoreShow = function () {
        $scope._broadcast('selectStoreCtrl');
    };
    
    // 매장별집계 엑셀다운로드
    $scope.excelDownload = function () {

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
            }, '입금_공제관리_매장별집계_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }

}]);

// 상세내역
app.controller('storeTotalDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeTotalDtlCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "moneyAmt") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.hostElement.addEventListener('mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if (col.binding === "moneyAmt") { // 금액 클릭

                    $scope.depositDdcRegLayer.show(true);

                    var params = {};
                    params.moneyDt = selectedRow.moneyDt;
                    params.moneyDate = selectedRow.moneyDate;
                    params.storeCd = selectedRow.storeCd;
                    params.seqNo = selectedRow.seqNo;
                    $scope._broadcast('depositDdcRegCtrl', params);
                }
            }
        });

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };


    $scope.$on("storeTotalDtlCtrl", function(event, data) {
        
        // 상세내역 리스트 조회
        $scope.searchStoreTotalDtl(data);
        event.preventDefault();

    });
    
    // 상세내역 리스트 조회
    $scope.searchStoreTotalDtl = function (data) {

        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.storeCd = data.storeCd;

        $scope._inquiryMain('/excclc/excclc/depositDdc/getStoreTotalDtlList.sb', params);

    };

    // 상세내역 엑셀다운로드
    $scope.excelDownload2 = function () {

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
            }, '입금_공제관리_매장별집계_상세내역_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }

}]);