/****************************************************************
 *
 * 파일명 : memberInfoPoint.js
 * 설  명 : 회원 포인트변경내역, 구매내역 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.11.27     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원 포인트변경내역 조회 그리드 생성
 */
app.controller('memberInfoPointCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberInfoPointCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.pointChgFgDataMap = new wijmo.grid.DataMap(pointChgFgData, 'value', 'name'); // 구분

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 재료코드
                if (col.binding === "pointChgFg") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (nvl(item[("pointChgFg")], '') == '3') {
                        if(!(orgnFg == "HQ" && membrClassManageFg == "0")){
                            wijmo.addClass(e.cell, 'wijLink');
                        }
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 구분 클릭시 상세정보 조회
                if ( col.binding === "pointChgFg") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    // 값이 있으면 링크
                    if (nvl(selectedRow[("pointChgFg")], '') == '3') {
                        if(!(orgnFg == "HQ" && membrClassManageFg == "0")) {
                            var params = $scope.getSelectedMemberInfoPoint();
                            params.gubun = "memberInfoPointDtl";
                            params.chgDate = selectedRow.chgDate;
                            params.chgSeq = selectedRow.chgSeq;

                            $scope.setSelectedMemberInfoPoint(params);
                            $scope.wjMemberPointAdjustDtlLayer.show(true);
                            event.preventDefault();
                        }
                    }
                }
            }
        });
    };

    // 선택 회원
    $scope.selectedMemberInfoPoint;
    $scope.setSelectedMemberInfoPoint = function (data) {
        $scope.selectedMemberInfoPoint = data;
    };
    $scope.getSelectedMemberInfoPoint = function () {
        return $scope.selectedMemberInfoPoint;
    };

    // <-- 검색 호출 -->
    $scope.$on("memberInfoPointCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedMemberInfoPoint(data);
            $scope.searchMemberInfoPoint();
        }
        event.preventDefault();
    });

    $scope.searchMemberInfoPoint = function(){
        var params = $scope.getSelectedMemberInfoPoint();
        $("#lblMemberInfoPointMembrNo").text("[" + params.membrNo + "] ");
        $("#lblMemberInfoPointMembrNm").text(params.membrNm);

        $scope._inquirySub("/membr/info/view/base/getMemberInfoPointList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('memberInfoBuyCtrl');
                storeScope._broadcast('memberInfoBuyCtrl', params);
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 회원 포인트 조정
    $scope.memberInfoPointAdjust = function () {
        var params = $scope.getSelectedMemberInfoPoint();
        params.gubun = "memberInfoPoint";
        params.chgDate = "";
        params.chgSeq = "";

        $scope.setSelectedMemberInfoPoint(params);
        $scope.wjMemberPointAdjustDtlLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 회원 포인트 조정 팝업 핸들러 추가
        $scope.wjMemberPointAdjustDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('memberPointAdjustDtlCtrl', $scope.getSelectedMemberInfoPoint());
            }, 50)
        });
    });

}]);

/**
 *  회원 구매내역 조회 그리드 생성
 */
app.controller('memberInfoBuyCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberInfoBuyCtrl', $scope, $http, true));

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

        // 첫째줄 헤더 생성d
        var dataItem         = {};
        dataItem.saleDate    = messages["regist.memberInfoPoint.saleDate"];
        dataItem.storeNm    = messages["regist.memberInfoPoint.storeNm"];
        dataItem.prodNm    = messages["regist.memberInfoPoint.prodNm"];
        dataItem.totSaleQty    = messages["regist.memberInfoPoint.totSaleQty"];
        dataItem.totSaleAmt    = messages["regist.memberInfoPoint.totSaleAmt"];
        dataItem.membrSavePoint    = messages["regist.memberInfoPoint.point"];
        dataItem.membrUsePoint    = messages["regist.memberInfoPoint.point"];

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

    // 선택 회원
    $scope.selectedMemberInfoPointBuy;
    $scope.setSelectedMemberInfoPointBuy = function (data) {
        $scope.selectedMemberInfoPointBuy = data;
    };
    $scope.getSelectedMemberInfoPointBuy = function () {
        return $scope.selectedMemberInfoPointBuy;
    };

    // <-- 검색 호출 -->
    $scope.$on("memberInfoBuyCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedMemberInfoPointBuy(data);
            $scope.searchMemberInfoBuy();
        }
        event.preventDefault();
    });

    $scope.searchMemberInfoBuy = function(){
        var params = $scope.getSelectedMemberInfoPointBuy();

        $scope._inquirySub("/membr/info/view/base/getMemberInfoBuyList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);