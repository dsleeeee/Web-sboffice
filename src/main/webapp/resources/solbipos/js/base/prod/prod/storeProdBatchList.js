/****************************************************************
 *
 * 파일명 : storeProdBatchList.js
 * 설  명 : 매장 리스트 팝업(매장 상품 일괄적용을 위한) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeProdBatchListCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdBatchListCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 포맷
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if( col.binding === "storeCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if( col.binding === "storeCd") {
                    
                    // 매장 상품 일괄등록 화면 호출
                    $scope.storeProdBatchRegistLayer.show(true);

                    var params     = {};
                    params.hqOfficeCd = selectedRow.hqOfficeCd;
                    params.storeCd = selectedRow.storeCd;
                    params.storeNm = selectedRow.storeNm;
                    $scope._broadcast('regProdCtrl', params);
                }
            }
        });
    };

    // 조회 버튼 클릭 (_broadcast)
    $scope.$on("storeProdBatchListCtrl", function(event, data) {
        $scope.getStoreList();
        event.preventDefault();
    });

    // 매장목록 조회
    $scope.getStoreList = function(){
        // 파라미터
        var params = {};
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;
        $scope._inquirySub("/iostock/cmm/iostockCmm/selectStoreList.sb", params, function () {
        });
    };

}]);