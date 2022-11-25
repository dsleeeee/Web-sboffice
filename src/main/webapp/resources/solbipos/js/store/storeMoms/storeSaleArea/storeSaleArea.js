/****************************************************************
 *
 * 파일명 : storeSaleArea.js
 * 설  명 : 점포 영업 지역 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.21     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeSaleAreaCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSaleAreaCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("areaCd", areaCd);
    $scope._setComboData("sysStatFg", sysStatFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'storeCd') {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "storeCd") {

                    // 지사가 있는 매장인지 확인
                    if(selectedRow.branchCd === null || selectedRow.branchCd === undefined || selectedRow.branchCd === "") {
                        $scope._popMsg(messages["storeSaleArea.require.branch"]); // 지사가 없는 매장입니다. 지사를 먼저 등록하세요.
                        return;
                    }

                    var params = {};
                    params.branchCd = selectedRow.branchCd;
                    params.storeCd = selectedRow.storeCd;
                    $scope.wjStoreSaleAreaDtlLayer.show(true);
                    $scope._broadcast('storeSaleAreaDtlCtrl', params);
                }
            }
        });
    };

    $scope.$on("storeSaleAreaCtrl", function(event, data) {
        // 매장목록 조회
        $scope.searchStoreList();
        event.preventDefault();
    });
    
    // 매장목록 조회
    $scope.searchStoreList = function(){
        var params = {};

        params.branchCd = $("#srchBranchCd").val();
        params.branchNm = $("#srchBranchNm").val();
        params.storeCd = $("#srchStoreCd").val();
        params.storeNm = $("#srchStoreNm").val();
        params.areaCd = $scope.srchAreaCdCombo.selectedValue;
        params.sysStatFg = $scope.srchSysStatFgCombo.selectedValue;

        $scope._inquiryMain("/store/storeMoms/storeSaleArea/getStoreList.sb", params, function(result){});
    };
    
    // 신규 등록
    $scope.addStoreSaleArea = function () {

        var params = null;
        $scope.wjStoreSaleAreaDtlLayer.show(true);
        $scope._broadcast('storeSaleAreaDtlCtrl', params);
    }

}]);