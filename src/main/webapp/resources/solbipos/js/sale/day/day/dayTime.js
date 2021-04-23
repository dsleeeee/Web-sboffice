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

/** 과세면별 controller */
app.controller('dayTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayTimeCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);
    $scope.orgnFg        = gvOrgnFg;
    $scope.SaleTimeReadOnly = true;

    $scope._setComboData("startTimeCombo", Hh);
    $scope._setComboData("endTimeCombo", Hh);
    $scope.startTime     = "0";
    $scope.endTime       = "23";

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

       // picker 사용시 호출 : 미사용시 호출안함
       $scope._makePickColumns("dayTimeCtrl");

       // add the new GroupRow to the grid's 'columnFooters' panel
       s.columnFooters.rows.push(new wijmo.grid.GroupRow());
       // add a sigma to the header to show that this is a summary row
       s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.saleDate    = messages["day.time.saleDate"];
        dataItem.yoil         = messages["day.time.yoil"];
        dataItem.storeCnt    = messages["day.time.storeCnt"];
        dataItem.realSaleAmt  = messages["day.time.Tot"];
        dataItem.saleCnt    = messages["day.time.Tot"];
        dataItem.totGuestCnt = messages["day.time.Tot"];

        // 시간대별 컬럼 생성
        var j=0;
        for (var i = 0; i < 24; i++) {
            j=i + 1;
            dataItem['realSaleAmtT' + i] = i + "시 ~ " + j + "시";
            dataItem['saleCntT' + i] = i + "시 ~ " + j + "시";
            dataItem['totGuestCntT' + i] = i + "시 ~ " + j + "시";
            j=0;
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

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayTimeCtrl", function (event, data) {
        if($scope.startTime*1 > $scope.endTime*1){ // *1하는이유 : Time들이 String이라 int로 바꿀라고
            $scope._popMsg(messages["day.time.startEnd"]); // 검색 시작 시간대가 검색 종료 시간대보다 큽니다.
            return false;
        }

        $scope.searchDayTimeList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 과면세별 리스트 조회
    $scope.searchDayTimeList = function () {
        $scope.searchedStoreCd = $("#dayTimeSelectStoreCd").val();
        // 파라미터
        var params= {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $scope.searchedStoreCd;
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/sale/day/day/dayTime/list.sb", params);

        // 선택한 시간대에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridList");
        var columns = grid.columns;
        var start = $scope.startTime*1;
        var end = $scope.endTime*1;
        var defaultCol = 5;

        if($scope.orgnFg === 'H') {
            defaultCol++;
        }

        for(var i = defaultCol; i <= columns.length; i++){ //72번 돈다
            columns[i].visible = false;
            for(var j = start; j <= end; j++) {
                if (columns[i].binding == 'realSaleAmtT'+j || columns[i].binding == 'saleCntT'+j || columns[i].binding == 'totGuestCntT'+j) {
                    columns[i].visible = true;
                }
            }
        }
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayTimeSelectStoreShow = function () {
        $scope._broadcast('dayTimeSelectStoreCtrl');
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

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
                messages["day.day"] + '(' + messages["day.time"] + ')_'  + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);