/****************************************************************
 *
 * 파일명 : agencyDtl.js
 * 설  명 : 업체현황 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.15     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 설치구분 DropBoxDataMap
var instFgData = [
    {"name":"","value":""},
    {"name":"설치의뢰","value":"0"},
    {"name":"신규설치","value":"1"},
    {"name":"재설치","value":"2"}
];

/**
 *  팝업 그리드 생성
 */
app.controller('agencyDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('agencyDtlCtrl', $scope, $http, true));

    // 검색조건에 조회일자
    var startDate = wcombo.genDateVal("#startDateDtl", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDtl", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.instFgDataMap = new wijmo.grid.DataMap(instFgData, 'value', 'name'); //설치구분

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
        dataItem.dt    = messages["instl.dt"];
        dataItem.buyCnt    = messages["instl.buyInfo"];
        dataItem.buyAmt    = messages["instl.buyInfo"];
        dataItem.buyStore    = messages["instl.buyInfo"];
        dataItem.instCnt    = messages["instl.instlInfo"];
        dataItem.storeCd    = messages["instl.instlInfo"];
        dataItem.storeNm    = messages["instl.instlInfo"];
        dataItem.posNo    = messages["instl.instlInfo"];
        dataItem.restCnt   = messages["instl.restCnt"];
        dataItem.memo   = messages["instl.memo"];

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
    $scope.$on("agencyDtlCtrl", function(event, data) {
        $scope.setSelectedStore(data);
        $("#lblAgencyDtl1").text("[");
        $("#lblAgencyDtl2").text("]");
        $("#lblAgencyCdAgencyDtl").text($scope.selectedStore.agencyCd);
        $("#lblAgencyNmAgencyDtl").text($scope.selectedStore.agencyNm);
        $scope.searchAgencyDtlList();
        event.preventDefault();
    });

    // 업체현황 상세조회
    $scope.searchAgencyDtlList = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.agencyCd = $("#lblAgencyCdAgencyDtl").text();

        $scope._inquiryMain("/pos/license/instlManage/instlManage/getAgencyDtlList.sb", params, function() {}, false);
    };

}]);