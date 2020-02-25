/****************************************************************
 *
 * 파일명 : boardDetail.js
 * 설  명 : 일반게시판 상세팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2010.02.14     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('boardDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardDetailCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("apprFgBoardDetail", apprFgData); //승인구분
    $scope._setComboData("targetFgBoardDetail", targetFgData); //공개대상

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 삭제
                if (col.binding === "del") {
                    // 값이 있으면 링크 효과
                    if (item["del"] === '삭제') {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }

                // 수정
                if (col.binding === "edit") {
                    // 값이 있으면 링크 효과
                    if (item["edit"] === '수정') {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }

                // 저장
                if (col.binding === "save") {
                    // 값이 있으면 링크 효과
                    if (item["save"] === '저장') {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;

                // 삭제 클릭시 상세정보 조회
                if ( col.binding === "del") {
                    // 값이 있으면 링크
                    if (selectedRow["del"] === '삭제') {
                        var params      = {};
                        params.idx = selectedRow.idx;

                        $scope.delAnswer(params);
                    }
                }

                // 수정 클릭시 상세정보 조회
                if ( col.binding === "edit") {
                    // 값이 있으면 링크
                    if (selectedRow["edit"] === '수정') {

                        // 선택한 시간대에 따른 리스트 항목 visible
                        var grid = wijmo.Control.getControl("#wjGridBoardDetailAnswerList");
                        var columns = grid.columns;
                        columns[1].isReadOnly = false; // 내용
                        columns[3].visible = false; // 수정
                        columns[4].visible = true; // 저장
                    }
                }

                // 저장 클릭시 상세정보 조회
                if ( col.binding === "save") {
                    // 값이 있으면 링크
                    if (selectedRow["save"] === '저장') {
                        var params      = {};
                        params.idx = selectedRow.idx;
                        params.content = selectedRow.content;

                        $scope.editAnswer(params);
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("boardDetailCtrl", function(event, data) {
        if(userId !== data.userId) {
            $("#delButton").hide();
            $("#modifyButton").hide();
        } else if (userId === data.userId) {
            $("#delButton").show();
            $("#modifyButton").show();
        }
        $scope.setSelectedBoardDetail(data);
        $scope.searchBoardDetail();
        $scope.searchBoardDetailAnswer();
        event.preventDefault();
    });

    $scope.searchBoardDetail = function() {
        var params = {};
        params.boardCd = $scope.selectedBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetail.boardSeqNo;

        $scope._postJSONQuery.withOutPopUp( "/adi/board/board/board/getBoardDetailList.sb", params, function(response){
            var boardDetail = response.data.data.result;
            $scope.boardDetail = boardDetail;

            $scope.boardDetail.title = $scope.boardDetail.title;
            $scope.boardDetail.userNm = $scope.boardDetail.userNm;
            $scope.boardDetail.regDt = $scope.boardDetail.regDt;
            $scope.boardDetail.target = $scope.boardDetail.target;
            if($scope.boardDetail.noticeYn === "Y") {
                $scope.boardDetail.noticeYn = true;
            } else if ($scope.boardDetail.noticeYn === "N") {
                $scope.boardDetail.noticeYn = false;
            }
            // if($scope.boardDetail.smsYn = "Y") {
            //     $scope.boardDetail.smsYn = true;
            // } else if ($scope.boardDetail.smsYn = "N") {
            //     $scope.boardDetail.smsYn = false;
            // }
            var startDate = $scope.boardDetail.startDate.substr(0, 4) + "-" + $scope.boardDetail.startDate.substr(4, 2) + "-" + $scope.boardDetail.startDate.substr(6, 2);
            var endDate = $scope.boardDetail.endDate.substr(0, 4) + "-" + $scope.boardDetail.endDate.substr(4, 2) + "-" + $scope.boardDetail.endDate.substr(6, 2);
            $scope.boardDetail.startDate = startDate;
            $scope.boardDetail.endDate = endDate;

            if($scope.boardDetail.answerFg === "Y") {
                $("#divAnswer").show();
            } else if($scope.boardDetail.answerFg === "N") {
                $("#divAnswer").hide();
            }
        });
    };

    // 댓글 조회
    $scope.searchBoardDetailAnswer = function() {
        var params = {};
        params.boardCd = $scope.selectedBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetail.boardSeqNo;

        $scope._inquirySub("/adi/board/board/board/getBoardDetailAnswerList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedBoardDetail;
    $scope.setSelectedBoardDetail = function(store) {
        $scope.selectedBoardDetail = store;
    };
    $scope.getSelectedBoardDetail = function(){
        return $scope.selectedBoardDetail;
    };

    // 댓글 등록
    $scope.saveAnswer = function(){
        if($scope.content === "") {
            $scope._popMsg(messages["boardDetail.contentBlank"]); // 댓글을 입력해주세요
            return false;
        }

        var params = {};
        params.boardCd = $scope.selectedBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetail.boardSeqNo;
        params.content = $scope.content;
        params.status = "I";

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function(){ $scope.allSearch() });
    };

    // 댓글 삭제
    $scope.delAnswer = function(data){
        // 해당 댓글을 삭제하시겠습니까?
        $scope._popConfirm(messages["boardDetail.delConfirmAnswer"], function() {

            var params = {};
            params.boardCd = $scope.selectedBoardDetail.boardCd;
            params.boardSeqNo = $scope.selectedBoardDetail.boardSeqNo;
            params.idx = data.idx;
            params.status = "D";

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 댓글 수정저장
    $scope.editAnswer = function(data){
        var params = {};
        params.boardCd = $scope.selectedBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetail.boardSeqNo;
        params.idx = data.idx;
        params.content = data.content;
        params.status = "U";

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function(){ $scope.allSearch() });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchBoardDetailAnswer();
        $scope.content = "";

        // 선택한 시간대에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridBoardDetailAnswerList");
        var columns = grid.columns;
        columns[1].isReadOnly = true; // 내용
        columns[3].visible = true; // 수정
        columns[4].visible = false; // 저장
    };

    // 게시판 삭제
    $scope.del = function(data){
        // 해당 게시물을 삭제하시겠습니까?
        $scope._popConfirm(messages["boardDetail.delConfirm"], function() {

            var params = {};
            params.boardCd = $scope.selectedBoardDetail.boardCd;
            params.boardSeqNo = $scope.selectedBoardDetail.boardSeqNo;
            params.status = "D";

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/adi/board/board/board/getBoardInfoSave.sb", params, function(){ });
        });
    };

    // 수정
    $scope.modify = function(){
        $scope.wjBoardInfoLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 게시판 신규,수정 팝업 핸들러 추가
        $scope.wjBoardInfoLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('boardInfoCtrl', $scope.getSelectedBoardDetail());
            }, 50)
        });

        // 열림자목록 팝업 핸들러 추가
        $scope.wjBoardReadingHistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('boardReadingHistCtrl', $scope.getSelectedBoardDetail());
            }, 50)
        });
    });

    // 팝업 닫기
    $scope.close = function(){
        $scope.setSelectedBoardDetail(null);
        $scope.content = "";

        // 선택한 시간대에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridBoardDetailAnswerList");
        var columns = grid.columns;
        columns[1].isReadOnly = true; // 내용
        columns[3].visible = true; // 수정
        columns[4].visible = false; // 저장

        $scope.wjBoardDetailLayer.hide();
    };

    // 열람자 목록
    $scope.readingHist = function(){
        $scope.wjBoardReadingHistLayer.show(true);
        event.preventDefault();
    };

}]);