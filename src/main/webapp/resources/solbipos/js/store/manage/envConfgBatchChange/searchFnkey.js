/****************************************************************
 *
 * 파일명 : searchFnkey.js
 * 설  명 : 환경설정 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.14     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  환경설정 조회 그리드 생성
 */
app.controller('searchFnkeyCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchFnkeyCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 설정코드
                if (col.binding === "fnkeyNo") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 설정코드 클릭시 상세정보 조회
                if ( col.binding === "fnkeyNo") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedFnkey(selectedRow);
                    $scope.wjSearchFnkeyLayer.hide();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("searchFnkeyCtrl", function(event, data) {
        $scope.searchFnkey();
        event.preventDefault();
    });

    $scope.searchFnkey = function(){
        var params = {}
        $scope._inquiryMain("/store/manage/envConfgBatchChange/searchFnkey/getSearchFnkeyList.sb", params);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedFnkey;
    $scope.setSelectedFnkey = function(store) {
        $scope.selectedFnkey = store;
    };
    $scope.getSelectedFnkey = function(){
        return $scope.selectedFnkey;
    };

    // 팝업 닫기
    $scope.close = function() {
        $scope.setSelectedFnkey(null);
        $scope.wjSearchFnkeyLayer.hide();
        event.preventDefault();
    };

}]);