/****************************************************************
 *
 * 파일명 : batchStore.js
 * 설  명 : 일괄기능적용 매장 리스트 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.07.08     이다솜      1.0
 *
 * **************************************************************/

app.controller('batchStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('batchStoreCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("bsHqOfficeCd", hqList);

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

                    // 일괄기능적용 화면 호출
                    $scope.batchFuncLayer.show(true);

                    var params     = {};
                    params.hqOfficeCd = selectedRow.hqOfficeCd;
                    params.storeCd = selectedRow.storeCd;
                    params.storeNm = selectedRow.storeNm;
                    $scope._broadcast('regFnkeyCtrl', params);
                }
            }
        });
    };

    // 팝업 오픈시 테이블 그룹정보 조회
    $scope.$on("batchStoreCtrl", function(event, data) {
        $scope.selectStoreList();
        event.preventDefault();
    });

    // 매장코드 조회
    $scope.selectStoreList = function () {

        // 파라미터
        var params = {};
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;
        params.orgnFg = orgnFg;
        params.agencyCd = orgnCd;

        $scope._inquirySub("/pos/confg/func/func/selectStoreList.sb", params, function () {

        });
    };

    // 닫기버튼 클릭
    $scope.close = function(){

        // 입력 값 초기화
        $scope.hqOfficeCd = "";
        $scope.storeCd = "";
        $scope.storeNm = "";

        $scope.defaultStoreLayer.hide();
    }



}]);