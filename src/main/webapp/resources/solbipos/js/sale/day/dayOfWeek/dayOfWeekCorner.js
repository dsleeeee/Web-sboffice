/****************************************************************
 *
 * 파일명 : dayOfWeekCorner.js
 * 설  명 : 기간별매출 > 요일별탭 > 코너별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.12.20     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  코너별 조회 그리드 생성
 */
app.controller('dayOfWeekCornerCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayOfWeekCornerCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayOfWeekCorner", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayOfWeekCorner", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayOfWeekCornerCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.yoil    = messages["dayofweek.yoil"];
        dataItem.storeCnt    = messages["dayofweek.storeCnt"];
        dataItem.totRealSaleAmt    = messages["dayofweek.totRealSaleAmt"];
        dataItem.totSaleQty    = messages["dayofweek.totSaleQty"];

        // 코너구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrCornerCol.length; i++) {
            dataItem['cornr' + arrCornerCol[i] + 'RealSaleAmt'] = cornerColList[i].storeNm;
            dataItem['cornr' + arrCornerCol[i] + 'SaleQty'] = cornerColList[i].storeNm;
        }

        s.columnHeaders.rows[0].dataItem = dataItem;

        // 둘째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1         = {};
        dataItem1.yoil    = messages["dayofweek.yoil"];
        dataItem1.storeCnt    = messages["dayofweek.storeCnt"];
        dataItem1.totRealSaleAmt    = messages["dayofweek.totRealSaleAmt"];
        dataItem1.totSaleQty    = messages["dayofweek.totSaleQty"];

        // 코너구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrCornerCol.length; i++) {
            dataItem1['cornr' + arrCornerCol[i] + 'RealSaleAmt'] = cornerColList[i].cornrNm;
            dataItem1['cornr' + arrCornerCol[i] + 'SaleQty'] = cornerColList[i].cornrNm;
        }

        s.columnHeaders.rows[1].dataItem = dataItem1;

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
        // <-- //그리드 헤더3줄 -->
    };

    // <-- 검색 호출 -->
    $scope.$on("dayOfWeekCornerCtrl", function(event, data) {
        $scope.searchDayOfWeekCorner();
        event.preventDefault();
    });

    $scope.searchDayOfWeekCorner = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.cornerCol = cornerCol;
        // 전체매장
        if ($("#dayofweekCornerStoreCd").val() == "") {
            params.storeCd = null;
        //매장 선택시
        } else {
            params.storeCd = $("#dayofweekCornerStoreCd").val();

            // 선택시 매장의 storeCornrCd 가져오기
            var storeCornerCd = "";
            for (var i = 0; i < cornerColList.length; i++) {
                if (cornerColList[i].storeCd === $("#dayofweekCornerStoreCd").val()) {
                    storeCornerCd = arrCornerCol[i];
                }
            }
            params.storeCornerCd = storeCornerCd;
        }

        $scope._inquiryMain("/sale/day/dayOfWeek/dayOfWeek/getDayOfWeekCornerList.sb", params, function() {}, false);

        // <-- 그리드 visible -->
        // 선택한 테이블에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridDayofweekCornerList");
        var columns = grid.columns;
        var start = 0;
        var end = 3;

        // cornerColList 에 storeCd 배열로 담기
        var cornerColArray = [];
        for (var i = 0; i < cornerColList.length; i++) {
            comboData = {};
            comboData.value = cornerColList[i].storeCd;
            cornerColArray.push(comboData);
        }

        // 컬럼 총갯수
        var columnsCnt = 4 + (cornerColArray.length * 2);

        // 전체선택시 전부 visible
        if($("#dayofweekCornerStoreCd").val() === "")
        {
            for (var i = 4; i <= columnsCnt; i++) {
                columns[i].visible = true;
            }
        }
        // 선택한 테이블만 visible
        else
        {
            for (var i = 0; i < cornerColArray.length; i++) {
                if (cornerColArray[i].value === $("#dayofweekCornerStoreCd").val()) {
                    start = (i * 2) + 4;
                    end = (i * 2) + 5;
                }
            }

            for (var i = 4; i < columnsCnt; i++) {
                if (i >= start && i <= end) {
                    columns[i].visible = true;
                } else {
                    columns[i].visible = false;
                }
            }
        }
        // <-- //그리드 visible -->
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayofweekCornerStoreShow = function () {
        $scope._broadcast('dayofweekCornerStoreCtrl');
    };

}]);