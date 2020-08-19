/****************************************************************
 *
 * 파일명 : dayPos.js
 * 설  명 : 기간별매출 > 일자별탭 > 포스별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.12.18     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  포스별 매출 조회 그리드 생성
 */
app.controller('dayPosCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPosCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayPos", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayPos", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayPosCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.saleDate    = messages["day.dayPos.saleDate"];
        dataItem.yoil    = messages["day.dayPos.yoil"];
        dataItem.totSaleAmt    = messages["day.dayPos.totSaleAmt"];
        dataItem.totDcAmt    = messages["day.dayPos.totDcAmt"];
        dataItem.totRealSaleAmt    = messages["day.dayPos.totRealSaleAmt"];
        dataItem.totSaleQty    = messages["day.dayPos.totSaleQty"];

        // 포스구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrPosCol.length; i++) {
            dataItem['pos' + arrPosCol[i] + 'SaleAmt'] = posColList[i].posNm;
            dataItem['pos' + arrPosCol[i] + 'DcAmt'] = posColList[i].posNm;
            dataItem['pos' + arrPosCol[i] + 'RealSaleAmt'] = posColList[i].posNm;
            dataItem['pos' + arrPosCol[i] + 'SaleQty'] = posColList[i].posNm;
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

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 수량합계
                if (col.binding === "totSaleQty") {
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // 수량
                for (var i = 0; i < posColList.length; i++) {
                    if (col.binding === ("pos" + posColList[i].posNo + "SaleQty")) {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (nvl(item[("pos" + posColList[i].posNo + "SaleQty")], '') !== '') {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                        }
                    }
                }

                // 날짜 형식
                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                //  수량합계 클릭시 상세정보 조회
                if ( col.binding === "totSaleQty") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.saleDate = selectedRow.saleDate;
                    params.storeCd = $("#dayPosStoreCd").val();
                    params.gubun = "day";

                    $scope._broadcast('prodSaleDtlCtrl', params);
                }

                // 수량 클릭시 상세정보 조회
                for (var i = 0; i < posColList.length; i++) {
                    if (col.binding === ("pos" + posColList[i].posNo + "SaleQty")) {

                        var selectedRow = s.rows[ht.row].dataItem;
                        var params      = {};
                        params.saleDate = selectedRow.saleDate;
                        params.storeCd = $("#dayPosStoreCd").val();
                        params.posNo = posColList[i].posNo;
                        params.gubun = "dayPos";

                        // 값이 있으면 링크
                        if (nvl(selectedRow[("pos" + posColList[i].posNo + "SaleQty")], '') !== '') {
                            $scope._broadcast('prodSaleDtlCtrl', params);
                        }
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPosCtrl", function(event, data) {
        $scope.searchDayPos();
        event.preventDefault();
    });

    $scope.searchDayPos = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.storeCd = $("#dayPosStoreCd").val();
        params.posCol    = posCol;

        $scope._inquiryMain("/sale/day/day/day/getDayPosList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayPosStoreShow = function () {
        $scope._broadcast('dayPosStoreCtrl');
    };

}]);