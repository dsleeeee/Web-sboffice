/****************************************************************
 *
 * 파일명 : dayTimeSaleBenson.js
 * 설  명 : (벤슨) 매출분석 > 일별시간대별매출조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.10     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 시 VALUE
var Hh = [24];
for(i =0 ; i < 24; i++){
    var timeVal = i.toString();
    if(i>=0 && i<=9){
        timeVal = "0" + timeVal;
    }
    Hh[i] = {"name":timeVal,"value":timeVal}
}

/**
 *  일별시간대별매출조회 그리드 생성
 */
app.controller('dayTimeSaleBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayTimeSaleBensonCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 조회시간 콤보박스 셋팅
    $scope._setComboData("startTimeCombo", Hh);
    $scope._setComboData("endTimeCombo", Hh);
    $scope.startTime = "00";
    $scope.endTime   = "23";

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 고정 컬럼 (매출일자/요일/순매출액)
        s.frozenColumns = 3;

        // 요일 색상 표시 (일요일 : 빨간색, 토요일 : 파란색)
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "yoil") {
                    if(item && item.yoil === "일") {
                        wijmo.addClass(e.cell, 'red');
                    } else if(item && item.yoil === "토") {
                        wijmo.addClass(e.cell, 'blue');
                    }
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.saleDate = messages["dayTimeSaleBenson.saleDate"];
        dataItem.yoil = messages["dayTimeSaleBenson.yoil"];
        dataItem.realSaleAmtTot = messages["dayTimeSaleBenson.realSaleAmtTot"];
        // 시간대별 컬럼 생성
        for (var i = 0; i < 10; i++) {
            dataItem['realSaleAmt0' + i] = messages["dayTimeSaleBenson.timeSale"];
        }
        for (var i = 10; i < 24; i++) {
            dataItem['realSaleAmt' + i] = messages["dayTimeSaleBenson.timeSale"];
        }

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

    // <-- 검색 호출 -->
    $scope.$on("dayTimeSaleBensonCtrl", function (event, data) {
        $scope.searchDayTimeSaleBensonList();
        event.preventDefault();
    });

    // 조회조건 검증
    $scope.fnValidChk = function () {
        // 매장 선택여부 확인
        if ($("#dayTimeSaleBensonStoreCd").val() === '' || $("#dayTimeSaleBensonStoreCd").val() === undefined) {
            $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
            return false;
        }

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 검색 시작 시간대가 검색 종료 시간대보다 큰지 확인
        if($scope.startTime*1 > $scope.endTime*1){ // *1하는이유 : Time들이 String이라 int로 바꿀라고
            $scope._popMsg(messages["dayTimeSaleBenson.startEnd"]); // 검색 시작 시간대가 검색 종료 시간대보다 큽니다.
            return false;
        }

        return true;
    };

    // 일별시간대별매출 리스트 조회
    $scope.searchDayTimeSaleBensonList = function () {
        // 조회조건 검증
        if (!$scope.fnValidChk()) {
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;
        params.storeCd = $("#dayTimeSaleBensonStoreCd").val();

        // 가상로그인 대응한 session id 설정
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/benson/dayTimeSaleBenson/dayTimeSaleBenson/getDayTimeSaleBensonList.sb", params, function (response){
            var grid = $scope.flex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            // <-- 그리드 visible -->
            // 선택한 조회시간 범위에 따른 시간대 컬럼 visible
            var columns = grid.columns;
            for (var j = 0; j < columns.length; j++) {
                if (columns[j].binding !== null && columns[j].binding.indexOf("realSaleAmt") === 0 && columns[j].binding !== "realSaleAmtTot") {
                    var colTime = columns[j].binding.replace("realSaleAmt", "");
                    columns[j].visible = (colTime*1 >= params.startTime*1 && colTime*1 <= params.endTime*1);
                }
            }
            // <-- //그리드 visible -->

            var list = response.data.list;
            if (list.length === undefined || list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
                if (response.message) {
                    $scope._popMsg(response.message);
                }
                return false;
            }
            var data = new wijmo.collections.CollectionView(list);
            data.trackChanges = true;
            $scope.data = data;
        });
    };
    // <-- //검색 호출 -->

    // 현재화면 엑셀다운로드
    $scope.excelDownload2 = function () {
        // 조회조건 검증
        if (!$scope.fnValidChk()) {
            return false;
        }

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
                messages["dayTimeSaleBenson.dayTimeSaleBenson"] + "_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);
