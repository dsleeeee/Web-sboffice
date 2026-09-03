/****************************************************************
 *
 * 파일명 : naverMenuLink.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버 메뉴 연동 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.19     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// 네이버 메뉴 연동 여부 구분
var naverMenuLinkYnComboData = [
    {"name":"전체","value":""},
    {"name":"연동","value":"Y"},
    {"name":"미연동","value":"N"}
];

app.controller('naverMenuLinkCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverMenuLinkCtrl', $scope, $http, $timeout, true));

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("naverMenuLinkYn", naverMenuLinkYnComboData); // 네이버 메뉴 연동 여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "optionIdClick") {
                    var item = s.rows[e.row].dataItem;
                    if (item.agencyKey === null) {
                        wijmo.addClass(e.cell, 'red');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        item.optionIdClick = "미연동";
                    } else {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        item.optionIdClick = item.optionId;
                    }
                }
            }
        });

        // 매장 선택
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if ( col.binding === "optionIdClick") {
                    var params      = {};
                    params.posShopId  = selectedRow.posShopId;
                    params.optionId  = selectedRow.optionId;
                    params.name  = selectedRow.name;
                    params.agencyKey  = selectedRow.agencyKey;
                    params.prodCd = selectedRow.prodCd;
                    params.prodNm = selectedRow.prodNm;
                    $scope._broadcast('naverMenuSetting1Ctrl', params);
                }
            }
        });

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem            = {};
        dataItem.optionIdClick  = messages["naverMenuLink.naverMenu"];
        dataItem.name           = messages["naverMenuLink.naverMenu"];
        dataItem.prodCd         = messages["naverMenuLink.lynkPosMneu"];
        dataItem.prodNm         = messages["naverMenuLink.lynkPosMneu"];
        dataItem.sideYn         = messages["naverMenuLink.lynkPosMneu"];

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

    $scope.$on("naverMenuLinkCtrl", function (event, data) {

        // 네이버 메뉴관리 리스트 조회
        $scope.getMenuOptionList();
    });

    // 네이버 메뉴관리 리스트 조회
    $scope.getMenuOptionList = function () {

        var params = {};
        params.listScale = $scope.listScale;
        params.naverMenuLinkYn = $scope.naverMenuLinkYn;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquirySub("/naverPlace/naverPlace/naverMenuLink/getMenuOptionList.sb", params, function() {
        }, false);
    }
}]);
