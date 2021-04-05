/****************************************************************
 *
 * 파일명 : posBoard.js
 * 설  명 : POS 화면에서 게시판(포스용) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.03.30     김설아      1.0
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
 *  게시판관리 그리드 생성
 */
app.controller('posBoardCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posBoardCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("gubunCombo", gubunComboData); // 조회구분
    $scope._setComboData("gubunReadCombo", gubunReadComboData); // 열람구분

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
                    $scope.wjPosBoardDetailLayer.show(true);
                    event.preventDefault();
                }
            }
        });

        // 검색
        $scope.searchPosBoard();
    };

    // <-- 검색 호출 -->
    $scope.$on("posBoardCtrl", function(event, data) {
        $scope.searchPosBoard();
        event.preventDefault();
    });

    $scope.searchPosBoard = function(){
        var params = {};
        params.listScale = 10;
        params.boardCd = "01";

        $scope._inquirySub("/application/pos/posBoard/posBoard/getPosBoardList.sb", params, function() {
            // 공지팝업 여부(미열람 공지사항 띄움)
            if(noticePopupYn == "Y") {
                $scope.$apply(function() {
                    var scope = agrid.getScope('posBoardPopupCtrl');
                    scope._gridDataInit();
                    scope._broadcast('posBoardPopupCtrl', params);
                });
            }
        }, false);
    };

    $scope.$on("posBoardListCtrl", function(event, data) {
        $scope.searchPosBoardList();
        event.preventDefault();
    });

    $scope.searchPosBoardList = function(){
        var params = {};
        params.listScale = 10;
        params.boardCd = "01";

        $scope._inquiryMain("/application/pos/posBoard/posBoard/getPosBoardList.sb", params, function() {}, false);
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

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 게시판 상세 팝업 핸들러 추가
        $scope.wjPosBoardDetailLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('posBoardDetailCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });

    // 공지팝업 여부(미열람 공지사항 띄움)
    $scope.posBoardPopupOpen = function(params){
        $scope.setSelectedStore(params);
        $scope.wjPosBoardDetailLayer.show(true);
        event.preventDefault();
    };

}]);


/**
 *  게시판관리 그리드 생성 - 공지팝업 여부(미열람 공지사항 띄움)
 */
app.controller('posBoardPopupCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posBoardPopupCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.targetFgDataMap = new wijmo.grid.DataMap(targetFgData, 'value', 'name'); //공개대상
        $scope.apprFgDataMap = new wijmo.grid.DataMap(apprFgData, 'value', 'name'); //승인여부
    };

    // <-- 검색 호출 -->
    $scope.$on("posBoardPopupCtrl", function(event, data) {
        $scope.searchPosBoardPopup(data);
        event.preventDefault();
    });

    $scope.searchPosBoardPopup = function(data){
        var params = {};
        params.listScale = data.listScale;
        params.boardCd = data.boardCd;
        params.gubunReadCombo = "N";

        $scope._inquirySub("/application/pos/posBoard/posBoard/getPosBoardList.sb", params, function() {
            if($scope.flex.rows.length > 0) {
                var selectedRow = $scope.flex.selectedRows[0]._data;

                var params = {};
                params.boardCd = selectedRow.boardCd;
                params.boardSeqNo = selectedRow.boardSeqNo;

                // 공지팝업 여부(미열람 공지사항 띄움)
                var scope = agrid.getScope('posBoardCtrl');
                scope.posBoardPopupOpen(params);
            }
        }, false);
    };
    // <-- //검색 호출 -->

}]);