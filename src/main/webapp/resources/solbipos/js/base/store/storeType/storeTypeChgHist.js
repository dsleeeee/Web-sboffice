/****************************************************************
 *
 * 파일명 : storeTypeChgHist.js
 * 설  명 : 매장타입관리 - 매장타입변경이력 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.11.18     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */

var procFgData = [
    {"name": "전체", "value": ""},
    {"name": "입력", "value": "I"},
    {"name": "수정", "value": "U"},
    {"name": "삭제", "value": "D"}
];

var app = agrid.getApp();

// 매장타입관리-매장타입변경이력
app.controller('storeTypeChgHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeTypeChgHistCtrl', $scope, $http, true));

    //페이지 스케일 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("stchProcFg", procFgData);

    // 변경일자 셋팅
    var startDate = wcombo.genDateVal("#stchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#stchEndDate", gvEndDate);

    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); // 사용여부
        $scope.procFgDataMap = new wijmo.grid.DataMap(procFgData, 'value', 'name'); // 입력구분

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.storeTypeCd = messages["storeType.code"];
        dataItem.storeTypeNm  = messages["storeType.storeTypeCur"];
        dataItem.useYn = messages["storeType.storeTypeCur"];
        dataItem.remark = messages["storeType.storeTypeCur"];
        dataItem.bStoreTypeNm  = messages["storeType.storeTypeB"];
        dataItem.bUseYn = messages["storeType.storeTypeB"];
        dataItem.bRemark = messages["storeType.storeTypeB"];
        dataItem.procFg = messages["storeType.procFg"];
        dataItem.procDt = messages["storeType.procDt"];
        dataItem.modId = messages["storeType.modId"];

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

    $scope.$on("storeTypeChgHistCtrl", function(event, data) {
        // 매장타입변경이력 조회
        $scope.srchStoreTypeChgHist();
        event.preventDefault();

    });

    // 매장타입변경이력 조회
    $scope.srchStoreTypeChgHist =  function () {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.storeTypeCd = $("#stchStoreTypeCd").val();
        params.storeTypeNm = $("#stchStoreTypeNm").val();
        params.procFg = $scope.stchProcFgCombo.selectedValue;
        //params.listScale = $scope.listScale;

        $scope._inquiryMain('/base/store/storeType/storeType/getStoreTypeChgHist.sb', params);
    }

}]);