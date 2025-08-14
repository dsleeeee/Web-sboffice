/****************************************************************
 *
 * 파일명 : daySaleMrpizza.js
 * 설  명 : 미스터피자 > 마케팅조회 > 일자별매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.25     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 배달주문구분
var dlvrOrderFgData = [
    {"name": "일반", "value": "1"},
    {"name": "배달", "value": "2"},
    {"name": "포장", "value": "3"},
    {"name": "포장", "value": "4"}
];

/**
 *  일자별매출 그리드 생성
 */
app.controller('daySaleMrpizzaCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('daySaleMrpizzaCtrl', $scope, $http, false));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "yoil") {
                    if (item.yoil === "토") {
                        wijmo.addClass(e.cell, 'blue');
                    } else if (item.yoil === "일") {
                        wijmo.addClass(e.cell, 'red');
                    }
                }

                if (col.binding === 'saleDate') {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== 'I') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "saleDate") { //  영업일자 클릭
                    var params = {};
                    params.saleDate = selectedRow.saleDate;
                    params.storeCd = selectedRow.storeCd;
                    $scope._broadcast('daySaleMrpizzaDtlCtrl', params);
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("daySaleMrpizzaCtrl", function (event, data) {

        // 일자별매출 리스트 조회
        $scope.searchDaySaleList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 일자별매출 리스트 조회
    $scope.searchDaySaleList = function () {

        // 그리드 초기화
        var posScope = agrid.getScope('daySaleMrpizzaDtlCtrl');
        posScope._gridDataInit();

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if ($("#daySaleMrpizzaStoreCd").val().length > 0 && $("#daySaleMrpizzaStoreCd").val().split(",").length - 1 === 0) {
            // 조회일자 최대 6달(186일) 제한
            if (diffDay > 186) {
                $scope._popMsg(messages['cmm.dateOver.6month.error']);
                return false;
            }
        } else {
            // 조회일자 최대 31일 제한
            if (diffDay >= 31) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCds = $("#daySaleMrpizzaStoreCd").val();
        params.payCol = payCol;
        params.dcCol = dcCol;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/mrpizza/daySaleMrpizza/getDaySaleMrpizzaList.sb", params, function (){

            // 조회날짜 기준 엑셀 다운로드 기간
            $scope.excelStartDate = params.startDate;
            $scope.excelEndDate   = params.endDate;
        });
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 엑셀다운로드 기간
        var startDt = $scope.excelStartDate;
        var endDt = $scope.excelEndDate;

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

        // 일자별매출 그리드
        var workBook1 = wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
          includeColumnHeaders: true,
          includeCellStyles: false,
            function() {
                // 원래의 합계 행 데이터로 복원
                groupRow.dataItem = originalDataItem;
            }
        });

        // 일자별매출 상세 그리드
        var vScope = agrid.getScope("daySaleMrpizzaDtlCtrl");

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
        workBook1.saveAsync("일자별매출" + '_' + startDt + '_' + endDt + '_' + getCurDateTime() + '.xlsx');
    }
}]);

/**
 *  일자별매출 상세 그리드 생성
 */
app.controller('daySaleMrpizzaDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('daySaleMrpizzaDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.dlvrOrderFgDataMap = new wijmo.grid.DataMap(dlvrOrderFgData, 'value', 'name'); // 배달주문구분
        // 그리드 매출구분
        $scope.saleFgMap = new wijmo.grid.DataMap([
            {id: "1", name: messages["todayBillSaleDtl.saleY"]},
            {id: "-1", name: messages["todayBillSaleDtl.saleN"]}
        ], 'id', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "yoil") {
                    if (item.yoil === "토") {
                        wijmo.addClass(e.cell, 'blue');
                    } else if (item.yoil === "일") {
                        wijmo.addClass(e.cell, 'red');
                    }
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("daySaleMrpizzaDtlCtrl", function (event, data) {

        // 일자별매출 상세 리스트 조회
        $scope.searchDaySaleDtlList(data);
    });

    // 일자별매출 상세 리스트 조회
    $scope.searchDaySaleDtlList = function (data) {

        // 파라미터
        var params = {};
        params.saleDate = data.saleDate.replaceAll("-", "");
        params.storeCd = data.storeCd;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/mrpizza/daySaleMrpizza/getDaySaleMrpizzaDtlList.sb", params, function (){

        });
    };

    // 조회조건 엑셀다운로드
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

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "일자별상세매출" + '_' + getCurDateTime() + '.xlsx', function () {
                    // 원래의 합계 행 데이터로 복원
                    groupRow.dataItem = originalDataItem;
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);