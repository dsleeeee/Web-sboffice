/**
 * get application
 */
var app = agrid.getApp();

// 시간대 DropBoxDataMap
var vSaleTime = [
    {"name":"전체","value":""},
    {"name":"심야","value":"0"},
    {"name":"아침","value":"1"},
    {"name":"점심","value":"2"},
    {"name":"저녁","value":"3"}
];

/** 과세면별 controller */
app.controller('dayTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayTimeCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchSaleTimeCombo", vSaleTime);

    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);
    $scope.orgnFg        = gvOrgnFg;

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
            if(i<10){
                dataItem['realSaleAmt0' + i] = i + "시 ~ " + j + "시";
                dataItem['saleCnt0' + i] = i + "시 ~ " + j + "시";
                dataItem['totGuestCnt0' + i] = i + "시 ~ " + j + "시";

            }else{
                dataItem['realSaleAmt' + i] = i + "시 ~ " + j + "시";
                dataItem['saleCnt' + i] = i + "시 ~ " + j + "시";
                dataItem['totGuestCnt' + i] = i + "시 ~ " + j + "시";
            }
            j=0;
        }

        dataItem.realSaleAmtT0  = messages["day.time.T0"];
        dataItem.saleCntT0  = messages["day.time.T0"];
        dataItem.totGuestCntT0  = messages["day.time.T0"];

        dataItem.realSaleAmtT1  = messages["day.time.T1"];
        dataItem.saleCntT1  = messages["day.time.T1"];
        dataItem.totGuestCntT1  = messages["day.time.T1"];

        dataItem.realSaleAmtT2  = messages["day.time.T2"];
        dataItem.saleCntT2  = messages["day.time.T2"];
        dataItem.totGuestCntT2  = messages["day.time.T2"];

        dataItem.realSaleAmtT3  = messages["day.time.T3"];
        dataItem.saleCntT3  = messages["day.time.T3"];
        dataItem.totGuestCntT3  = messages["day.time.T3"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {z
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
        params.saleTime = $scope.saleTime;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/sale/day/day/dayTime/list.sb", params);

        // 선택한 시간대에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridList");
        var columns = grid.columns;
        var start = 0;
        var end = 0;

        if($scope.saleTime === "0"){ //심야
            start = 5;
            end = 25;
        }else if($scope.saleTime  === "1"){ //아침
            start = 26;
            end = 37;
        }else if($scope.saleTime  === "2"){ //점심
            start = 38;
            end = 52;
        }else if($scope.saleTime  === "3"){ //저녁
            start = 53;
            end = 76;
        }else if($scope.saleTime === ""){ //전체
            start = 77;
            end = 88;
        }

        // 본사권한인 경우, 보여야 하는 컬럼 항목이 늘어나야 함(StoreCnt)
        if($scope.orgnFg === 'H') {
            start++;
            end++;
        }

        for(var i = 5; i <= 89; i++){
            if(i >= start && i <= end){
                columns[i].visible = true;
            }else{
                columns[i].visible = false;
            }
        }
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayTimeSelectStoreShow = function () {
        $scope._broadcast('dayTimeSelectStoreCtrl');
    };

}]);