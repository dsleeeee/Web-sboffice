/****************************************************************
 *
 * 파일명 : erpStoreSet.js
 * 설  명 : ERP 연동 매장 셋팅 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.10.13     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// 등록여부(전체)
var regYnAllFgData = [
    {"name":"전체","value":""},
    {"name":"등록","value":"Y"},
    {"name":"미등록","value":"N"}
];

app.controller('erpStoreSetCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('erpStoreSetCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleErpBox", gvListScaleBoxData);
    $scope._setComboData("erpRegYn", regYnAllFgData); // 등록여부

    // 선택된 매장
    $scope.erpStore;
    $scope.setErpStore = function(obj) {
        $scope.erpStore = obj;
    };
    $scope.getErpStore = function(){
        return $scope.erpStore;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.regYnDataMap = new wijmo.grid.DataMap(regYnAllFgData, 'value', 'name'); // 등록여부

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "bbqStoreCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.regYn === 'N') {
                        wijmo.addClass(e.cell, 'wijLink');
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
                if (col.binding === "bbqStoreCd") {
                    if (selectedRow.regYn === 'N') {
                        
                        // 선택값 셋팅
                        $scope.setErpStore(selectedRow);

                        // 팝업 닫기 전 초기화
                        $("#erpStoreCd").val("");
                        $("#erpStoreNm").val("");
                        $("#erpBizNo").val("");
                        $scope.erpRegYnCombo.selectedIndex = 2;

                        $scope.erpStoreSetLayer.hide();
                    }
                }
            }
        });
    };

    // 팝업 오픈 시, 매장 조회
    $scope.$on("erpStoreSetCtrl", function(event, data) {

        // ERP 매장 조회
        $scope.searchErpStore();
        event.preventDefault();
    });

    // ERP 매장 조회
    $scope.searchErpStore = function () {

        var params = {};
        params.srchStoreCd = $("#erpStoreCd").val();
        params.srchStoreNm = $("#erpStoreNm").val();
        params.bizNo = $("#erpBizNo").val();
        params.regYn = $scope.erpRegYnCombo.selectedValue;
        params.listScale = $scope.listScaleErp;

        $scope._inquirySub("/store/manage/storeManage/storeManage/getErpStore.sb", params, function () {});
    };

    // 팝업 닫기 전 초기화
    $scope.close = function () {
        
        // 선택값 셋팅
        $scope.setErpStore(null);

        $("#erpStoreCd").val("");
        $("#erpStoreNm").val("");
        $("#erpBizNo").val("");
        $scope.erpRegYnCombo.selectedIndex = 2;
    };

}]);