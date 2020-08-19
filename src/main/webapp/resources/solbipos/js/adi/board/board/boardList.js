/****************************************************************
 *
 * 파일명 : boardList.js
 * 설  명 : 일반게시판 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2010.02.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 조회구분
var gubunComboData = [
    {"name":"제목","value":"1"},
    {"name":"내용","value":"2"},
    {"name":"제목 + 내용","value":"3"},
    {"name":"작성자","value":"4"}
];

/**
 *  일반게시판 그리드 생성
 */
app.controller('boardListCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardListCtrl', $scope, $http, true));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("gubunCombo", gubunComboData); // 조회구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.targetFgDataMap = new wijmo.grid.DataMap(targetFgData, 'value', 'name'); //공개대상
        $scope.apprFgDataMap = new wijmo.grid.DataMap(apprFgData, 'value', 'name'); //승인여부

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 제목
                if (col.binding === "title") {
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

                // 제목 클릭시 상세정보 조회
                if ( col.binding === "title") {
                    $scope.setSelectedStore(s.rows[ht.row].dataItem);
                    $scope.wjBoardDetailLayer.show(true);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("boardListCtrl", function(event, data) {
        $scope.searchBoardList();
        event.preventDefault();
    });

    $scope.searchBoardList = function(){
        var params = {};
        params.listScale = $scope.listScaleBoard;
        params.boardCd = boardCd;

        $scope._inquiryMain("/adi/board/board/board/getBoardList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedStore;
    $scope.setSelectedStore = function(store) {
        $scope.selectedStore = store;
    };
    $scope.getSelectedStore = function(){
        return $scope.selectedStore;
    };

    // 게시판 신규등록 팝업 오픈
    $scope.addInfo = function(){
        $scope.setSelectedStore(null);
        $scope.wjBoardInfoLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 게시판 상세 팝업 핸들러 추가
        $scope.wjBoardDetailLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('boardDetailCtrl', $scope.getSelectedStore());
            }, 50)
        });

        // 게시판 신규,수정 팝업 핸들러 추가
        $scope.wjBoardInfoLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('boardInfoCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });

}]);