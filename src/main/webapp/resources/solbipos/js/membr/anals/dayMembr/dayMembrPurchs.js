/****************************************************************
 *
 * 파일명 : dayMembrPurchs.js
 * 설  명 : 매출정보 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.08.29     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('dayMembrPurchsCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayMembrPurchsCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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
        dataItem.saleDate    = messages["dayMembrPurchs.saleDate"];
        dataItem.storeNm    = messages["dayMembrPurchs.storeNm"];
        dataItem.prodNm    = messages["dayMembrPurchs.prodNm"];
        dataItem.saleQty    = messages["dayMembrPurchs.saleQty"];
        dataItem.realSaleAmt    = messages["dayMembrPurchs.realSaleAmt"];
        dataItem.membrSavePoint   = messages["dayMembrPurchs.point"];
        dataItem.membrUsePoint   = messages["dayMembrPurchs.point"];

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

    // 선택 매장
    $scope.selectedStore;
    $scope.setSelectedStore = function(store) {
        $scope.selectedStore = store;
    };
    $scope.getSelectedStore = function(){
        return $scope.selectedStore;
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("dayMembrPurchsCtrl", function(event, data) {
        $scope.setSelectedStore(data);
        $scope.searchDayMembrPurchsList();
        event.preventDefault();
    });

    // 매출정보 상세조회
    $scope.searchDayMembrPurchsList = function(){

        $("#dayMembrInfoTitle").text("/" + $scope.selectedStore.membrNm);

        var params = {};
        params.saleDate = $scope.selectedStore.saleDate;
        params.membrNo = $scope.selectedStore.membrNo;
        params.saleFg = $scope.selectedStore.saleFg;
        params.membrCardNo = $scope.selectedStore.membrCardNo;
        params.gubun = $scope.selectedStore.gubun;

        $scope._inquirySub("/membr/anals/dayMembr/dayMembr/getDayMembrPurchsList.sb", params, function() {}, false);
    };

}]);