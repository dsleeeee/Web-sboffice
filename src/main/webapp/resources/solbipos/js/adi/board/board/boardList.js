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

// 열람구분
var gubunReadComboData = [
    {"name":"전체","value":""},
    {"name":"미열람","value":"N"},
    {"name":"열람","value":"Y"}
];

/**
 *  일반게시판 그리드 생성
 */
app.controller('boardListCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardListCtrl', $scope, $http, false));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("gubunCombo", gubunComboData); // 조회구분
    $scope._setComboData("gubunReadCombo", gubunReadComboData); // 열람구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.viewYnDataMap = new wijmo.grid.DataMap(gubunReadComboData, 'value', 'name'); // 열람구분
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
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedStore(s.rows[ht.row].dataItem);

                    // FULL SIZE 표시 여부에 따라 상세화면 사이즈가 달라짐.
                    if(selectedRow.fullSizeYn === "Y"){
                        $scope.wjBoardDetailFullSizeLayer.show(true);
                        event.preventDefault();
                    }else{
                        $scope.wjBoardDetailLayer.show(true);
                        event.preventDefault();
                    }

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

        $scope._inquiryMain("/adi/board/board/board/getBoardList.sb", params, function() {

            // 게시물 '[NXPOS2] 포스 및 키오스크 프로그램 최신 설치파일 입니다.' 제목 강조 처리(20240412)
            var grid = wijmo.Control.getControl("#wjGrid");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];

                if(item.boardSeqNo == "326"){
                    rows[i].cssClass = 'wij_gridBackground-yellow-bold';
                }
            }

        }, false);
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
        $scope._broadcast('boardInfoCtrl', $scope.getSelectedStore());
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

        // 게시판 상세 FULL SIZE 팝업 핸들러 추가
        $scope.wjBoardDetailFullSizeLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('boardDetailFullSizeCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });

}]);