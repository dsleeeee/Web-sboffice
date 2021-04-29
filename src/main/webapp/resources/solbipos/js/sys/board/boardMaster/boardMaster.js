/****************************************************************
 *
 * 파일명 : boardMaster.js
 * 설  명 : 게시판관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 답변허용여부
var answerFgData = [
    {"name":"허용","value":"Y"},
    {"name":"불가","value":"N"}
];
// 자동승인여부
var autoApprFgData = [
    {"name":"자동","value":"Y"},
    {"name":"승인","value":"N"}
];

/**
 *  게시판관리 그리드 생성
 */
app.controller('boardMasterCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardMasterCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.boardFgDataMap = new wijmo.grid.DataMap(boardFgData, 'value', 'name'); //구분
        $scope.answerFgDataMap = new wijmo.grid.DataMap(answerFgData, 'value', 'name'); //답변허용여부
        $scope.autoApprFgDataMap = new wijmo.grid.DataMap(autoApprFgData, 'value', 'name'); //자동승인여부
    };

    // <-- 검색 호출 -->
    $scope.$on("boardMasterCtrl", function(event, data) {
        $scope.searchBoardMaster();
        event.preventDefault();
    });

    $scope.searchBoardMaster = function(){
        var params = {};

        $scope._inquiryMain("/sys/board/boardMaster/boardMaster/getBoardMasterList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 그리드 행 추가 -->
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.boardCd="자동채번";
        params.boardNm = "";
        params.boardFg = "1";
        params.answerFg = "Y";
        params.autoApprFg = "Y";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //그리드 행 추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];

            if(item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }
    };
    // <-- //그리드 행 삭제 -->

    // <-- 그리드 저장 -->
    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/board/boardMaster/boardMaster/getBoardMasterSave.sb", params, function(){ $scope.allSearch() });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchBoardMaster();
    };
    // <-- //그리드 저장 -->
}]);