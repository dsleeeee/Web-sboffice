/****************************************************************
 *
 * 파일명 : dayProdSaleDtl.js
 * 설  명 : 상품매출 상세내역 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.08     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('dayProdSaleDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayProdSaleDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.saleFgDataMap = new wijmo.grid.DataMap(saleFgData, 'value', 'name'); //상태구분

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
        dataItem.saleDate    = messages["day.dayProdSaleDtl.saleDate"];
        dataItem.posNo    = messages["day.dayProdSaleDtl.posNo"];
        dataItem.billNo    = messages["day.dayProdSaleDtl.billNo"];
        dataItem.regDt    = messages["day.dayProdSaleDtl.regDt"];
        dataItem.saleFg    = messages["day.dayProdSaleDtl.saleFg"];
        dataItem.totSaleAmt    = messages["day.dayProdSaleDtl.totSaleAmt"];
        dataItem.realSaleAmt    = messages["day.dayProdSaleDtl.realSaleAmt"];
        dataItem.gaAmt    = messages["day.dayProdSaleDtl.gaAmt"];
        dataItem.vatAmt    = messages["day.dayProdSaleDtl.vatAmt"];
        dataItem.totPayAmt    = messages["day.dayProdSaleDtl.totPayAmt"];
        dataItem.orderDt    = messages["day.dayProdSaleDtl.orderDt"];
        dataItem.totGuestCnt    = messages["day.dayProdSaleDtl.totGuestCnt"];
        dataItem.guestUprc    = messages["day.dayProdSaleDtl.guestUprc"];

        // 결제수단 헤더머지 컬럼 생성
        for (var i = 0; i < arrPayCol.length; i++) {
            dataItem['pay' + arrPayCol[i]] = messages["day.dayProdSaleDtl.payMethod"];
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
                if (col.binding === "billNo") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 영수증번호 클릭시 상세정보 조회
                if ( col.binding === "billNo") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.hqOfficeCd = selectedRow.hqOfficeCd;
                    params.hqBrandCd = selectedRow.hqBrandCd;
                    params.storeCd = selectedRow.storeCd;
                    params.saleDate  = selectedRow.saleDate.replace("-", "").replace("-", "");
                    params.posNo = selectedRow.posNo;
                    params.billNo = selectedRow.billNo;

                    $scope._broadcast('saleDtlCtrl', params);
                }
            }
        });
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("dayProdSaleDtlCtrl", function(event, data) {
        $scope.wjDayProdSaleDtlLayer.show(true);
        $scope.searchDayProdSaleDtlList(data);
        event.preventDefault();
    });

    $scope.searchDayProdSaleDtlList = function(data){
        var params = {};
        // 기간별매출 > 일자별 탭 > 일별종합 탭
        if(data.gubun == "day") {
            params.saleDate = data.saleDate;
        }
        // 기간별매출 > 월별 탭 > 월별종합 탭
        if(data.gubun == "month") {
            params.yearMonth = data.yearMonth;
        }
        params.tableCd  = data.tableCd;
        params.gubun  = data.gubun;
        params.payCol = payCol;

        $scope._inquiryMain("/sale/day/day/dayProdSaleDtl/getDayProdSaleDtlList.sb", params, function() {}, false);
    };

}]);