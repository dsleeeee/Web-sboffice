/****************************************************************
 *
 * 파일명 : boardDisplayAdd.js
 * 설  명 : 상위노출게시물선택 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.06.04     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상위노출게시물선택 팝업 조회 그리드 생성
 */
app.controller('boardDisplayAddCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardDisplayAddCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.viewYnDataMap = new wijmo.grid.DataMap(gubunReadComboData, 'value', 'name'); // 열람구분
        $scope.targetFgDataMap = new wijmo.grid.DataMap(targetFgData, 'value', 'name'); //공개대상
        $scope.apprFgDataMap = new wijmo.grid.DataMap(apprFgData, 'value', 'name'); //승인여부
    };

    // <-- 검색 호출 -->
    $scope.$on("boardDisplayAddCtrl", function(event, data) {
        $scope.setSelectedBoardDisplayAdd(data);
        $scope.searchBoardDisplayAdd();
        event.preventDefault();
    });

    $scope.searchBoardDisplayAdd = function(){
        var params = {};
        params.boardCd = $scope.selectedBoardDisplayAdd.boardCd;
        params.gubunCombo = $scope.selectedBoardDisplayAdd.gubunCombo;
        params.gubunName = $scope.selectedBoardDisplayAdd.gubunName;
        params.gubunReadCombo = $scope.selectedBoardDisplayAdd.gubunReadCombo;

        $scope._inquiryMain("/adi/board/boardDisplay/boardDisplayAdd/getBoardDisplayAddList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedBoardDisplayAdd;
    $scope.setSelectedBoardDisplayAdd = function(store) {
        $scope.selectedBoardDisplayAdd = store;
    };
    $scope.getSelectedBoardDisplayAdd = function(){
        return $scope.selectedBoardDisplayAdd;
    };

    // <-- 저장 호출 -->
    // 등록
    $("#funcSaveBoardDisplayAdd").click(function(e){
        // 노출등록 하시겠습니까?
        $scope._popConfirm(messages["boardDisplayAdd.displayAddConfirm"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status = "I";
                    $scope.flex.collectionView.items[i].boardCd = $scope.flex.collectionView.items[i].boardCd;
                    $scope.flex.collectionView.items[i].boardSeqNo = $scope.flex.collectionView.items[i].boardSeqNo;
                    $scope.flex.collectionView.items[i].topYn = "Y";
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/adi/board/boardDisplay/boardDisplay/getBoardDisplaySave.sb", params, function(){
                // 팝업 닫기
                $scope.close();
            });
        });
    });
    // <-- //저장 호출 -->

    // 팝업 닫기
    $scope.close = function() {
        var scope = agrid.getScope("boardDisplayCtrl");
        scope.searchBoardDisplay();

        $scope.wjBoardDisplayAddLayer.hide();
        event.preventDefault();
    };
}]);