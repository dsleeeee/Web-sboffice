/****************************************************************
 *
 * 파일명 : boardDetailFullSize.js
 * 설  명 : 일반게시판 상세 FullSize 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.30     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 태그를 그려서 div에 바인딩 시, ng-click이 먹히지 않아 onclick을 사용하고, function 함수를 통해 $scope.함수에 접근
// 댓글 수정 클릭 시, 수정창 보이기 or 숨기기
function viewAnswerAreaFs(idx, mod){
    if(mod === "open"){
        $("#hdAnswerFs_" + idx).css("display", "");
    }else{
        $("#hdAnswerFs_" + idx).css("display", "none");
    }
}

// 댓글 삭제
function viewDelAnswerFs(idx){
    var scope = agrid.getScope('boardDetailCtrl');
    var params = {};

    params.idx = idx;
    scope.delAnswerFs(params);
}

// 댓글 수정
function viewEditAnswerFs(idx){
    var scope = agrid.getScope('boardDetailCtrl');
    var params = {};

    params.idx = idx;
    params.content = $("#txtAnswerFs_" + idx).val();
    scope.editAnswerFs(params);
}

/**
 *  팝업 그리드 생성
 */
app.controller('boardDetailFullSizeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardDetailFullSizeCtrl', $scope, $http, true));

    // <-- 검색 호출 -->
    $scope.$on("boardDetailFullSizeCtrl", function (event, data) {
        // 파일과 댓글 DIV 값 초기화
        $("#fileContentFs").html("");
        $("#divCommentFs").html("");

        if(userId !== data.userId) {
            $("#delButtonFs").hide();
            $("#modifyButtonFs").hide();
        } else if (userId === data.userId) {
            $("#delButtonFs").show();
            $("#modifyButtonFs").show();
        }

        $scope.setSelectedBoardDetailFs(data);
        $scope.searchBoardDetailFs();
        $scope.searchBoardDetailAnswerFs();
        $scope.searchBoardDetailAtchFs();
        event.preventDefault();
    });

    // 게시글 정보
    $scope.searchBoardDetailFs = function() {
        var params = {};
        params.boardCd = $scope.selectedBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetailFs.boardSeqNo;
        params.userId = userId;

        $scope._postJSONQuery.withOutPopUp( "/adi/board/board/board/getBoardDetailList.sb", params, function(response){
            var boardDetailFs = response.data.data.result;
            $scope.boardDetailFs = boardDetailFs;

            if($scope.boardDetailFs.fullSizeYn === "Y") {
                $scope.boardDetailFs.fullSizeYn = true;
            } else if ($scope.boardDetailFs.fullSizeYn === "N") {
                $scope.boardDetailFs.fullSizeYn = false;
            }
            if($scope.boardDetailFs.targetFg === "1") {
                $scope.boardDetailFs.targetFg = "전체";
            } else if($scope.boardDetailFs.targetFg === "2") {
                $scope.boardDetailFs.targetFg = $scope.boardDetailFs.partOrgnNm;
            }
            if($scope.boardDetailFs.noticeYn === "Y") {
                $scope.boardDetailFs.noticeYn = true;
            } else if ($scope.boardDetailFs.noticeYn === "N") {
                $scope.boardDetailFs.noticeYn = false;
            }
            if($scope.boardDetailFs.emergencyYn === "Y") {
                $scope.boardDetailFs.emergencyYn = true;
            } else if($scope.boardDetailFs.emergencyYn === "N")  {
                $scope.boardDetailFs.emergencyYn = false;
            }
            // if($scope.boardDetailFs.smsYn === "Y") {
            //     $scope.boardDetailFs.smsYn = true;
            // } else if ($scope.boardDetailFs.smsYn === "N") {
            //     $scope.boardDetailFs.smsYn = false;
            // }
            var startDate = $scope.boardDetailFs.startDate.substr(0, 4) + "-" + $scope.boardDetailFs.startDate.substr(4, 2) + "-" + $scope.boardDetailFs.startDate.substr(6, 2);
            var endDate = $scope.boardDetailFs.endDate.substr(0, 4) + "-" + $scope.boardDetailFs.endDate.substr(4, 2) + "-" + $scope.boardDetailFs.endDate.substr(6, 2);
            $scope.boardDetailFs.startDate = startDate;
            $scope.boardDetailFs.endDate = endDate;
            $scope.boardDetailFs.remark = $scope.boardDetailFs.remark;

            // 게시글 내용 div 에 넣기
            $("#summernoteDetailFs").html($scope.boardDetailFs.content);

            if($scope.boardDetailFs.answerFg === "Y") {
                $("#divAnswerFs").show();
            } else if($scope.boardDetailFs.answerFg === "N") {
                $("#divAnswerFs").hide();
            }
        });
    };

    // 댓글 조회
    $scope.searchBoardDetailAnswerFs = function() {
        var params = {};
        params.boardCd = $scope.selectedBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetailFs.boardSeqNo;
        params.userId = userId;

        $scope._postJSONQuery.withOutPopUp("/adi/board/board/board/getBoardDetailAnswerList.sb", params, function(response) {
            var list = response.data.data.list;
            var innerHtml = "";

            if(list.length > 0){

                innerHtml += "<table class=\"tblType01 mt10\">";
                innerHtml += "<colgroup><col width=\"90%\" /><col width=\"10%\" /></colgroup>";
                innerHtml += "<tbody>";

                for(var i=0; i< list.length; i++) {

                    innerHtml += "<tr style=\"height: 10px;\">";
                    innerHtml += "<th>" + list[i].userNm +" | " + list[i].modDt + " </th>";
                    // 본인이 쓴 글만 수정/삭제 가능
                    if(userId == list[i].regId) {
                        innerHtml += "<th><a href=\"#\" style=\"font-size: 12px; color: black;\" onclick=\"viewAnswerAreaFs(\'"+ list[i].idx + "\', \'open\');\">수정</a> | ";
                        innerHtml += "<a href=\"#\" style=\"font-size: 12px; color: black;\" onclick=\"viewDelAnswerFs(\'" + list[i].idx + "\')\">삭제</a></th>";
                    } else {
                        innerHtml += "<th></th>";
                    }
                    innerHtml += "</tr>";

                    innerHtml += "<tr>";
                    innerHtml += "<td align=\"left\" style=\"padding:5px;\" colspan=\"2\">" + list[i].content +"</td>";
                    innerHtml += "</tr>";

                    innerHtml += "<tr id=\"hdAnswerFs_" + list[i].idx + "\" style=\"display: none;\">";
                    innerHtml += "<td align=\"center\"><input style=\"border: 1px solid #d0d0d0;\" type=\"text\" id=\"txtAnswerFs_" + list[i].idx +"\" value=\"" + list[i].content + "\"></td>";
                    innerHtml += "<td align=\"center\"><a href=\"#\" style=\"font-size: 12px; color: black;\" onclick=\"viewEditAnswerFs(\'" + list[i].idx + "\')\">저장 </a> | ";
                    innerHtml += "<a href=\"#\" style=\"font-size: 12px; color: black;\" onclick=\"viewAnswerAreaFs(\'"+ list[i].idx + "\', \'close\');\"> 취소</a></td>";
                    innerHtml += "</tr>";

                }

                innerHtml += "</tbody>";
                innerHtml += "</table>";

                $("#divCommentFs").html(innerHtml);
            }
        });
    };

    // 첨부파일 다운로드 조회
    $scope.searchBoardDetailAtchFs = function() {
        var params = {};
        params.boardCd = $scope.selectedBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetailFs.boardSeqNo;

        $scope._postJSONQuery.withOutPopUp( "/adi/board/board/board/getBoardDetailAtchList.sb", params, function(response){
            if($.isEmptyObject(response.data) ) {
                $scope._popMsg(messages["cmm.empty.data"]);
                return false;
            }

            var list = response.data.data;
            var innerHtml = "";

            if(list.length > 0) {

                innerHtml += "<table class=\"tblType01 mt10\">";
                innerHtml += "<colgroup><col width=\"15%\" /><col width=\"85%\" /></colgroup>";
                innerHtml += "<tbody>";

                for (var i = 0; i < list.length; i++) {

                    innerHtml += "<tr>";
                    if (i === 0) {
                        innerHtml += "<th rowspan='" + list.length + "'>" + messages["boardDetail.file"] + "</th>";
                    }
                    // innerHtml += "<a href=\"" + list[i].filePath + "\">" + list[i].orginlFileNm + "</a>";
                    // innerHtml += "<td>"+"<a href=\"" + list[i].filePath + "\" download >" + list[i].orginlFileNm + "</a>"+"</td>";
                    // innerHtml += "<td><a href=\"http://www.naver.com\" >" + list[i].orginlFileNm + "</a></td>";
                    // innerHtml += "<td><a href=\"/adi/board/board/board/getBoardDetailAtchDownload.sb\" >" + list[i].orginlFileNm + "</a></td>";
                    innerHtml += "<td><a href=\"/adi/board/board/board/getBoardDetailAtchDownload.sb?fileNm=" + list[i].fileNm + "&orginlFileNm=" + list[i].orginlFileNm + "&fileExt=" + list[i].fileExt + "\" style=\"font-size: 12px; color: black;\">" + list[i].orginlFileNm + "." + list[i].fileExt + "</a></td>";
                    innerHtml += "</tr>";
                }

                innerHtml += "</tbody>";
                innerHtml += "</table>";

                $("#fileContentFs").html(innerHtml);
            }
        });
    };

    // 선택 매장
    $scope.selectedBoardDetailFs;
    $scope.setSelectedBoardDetailFs = function(store) {
        $scope.selectedBoardDetailFs = store;
    };
    $scope.getSelectedBoardDetailFs = function(){
        return $scope.selectedBoardDetailFs;
    };

    // 댓글 등록
    $scope.saveAnswerFs = function(){
        if($scope.contentFs === "") {
            $scope._popMsg(messages["boardDetail.contentBlank"]); // 댓글을 입력해주세요
            return false;
        }

        var params = {};
        params.boardCd = $scope.selectedBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetailFs.boardSeqNo;
        params.content = $scope.contentFs;
        params.status = "I";
        params.userId = userId;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function () { $scope.allSearchFs(); });
    };

    // 댓글 삭제
    $scope.delAnswerFs = function(data){
        // 해당 댓글을 삭제하시겠습니까?
        $scope._popConfirm(messages["boardDetail.delConfirmAnswer"], function() {

            var params = {};
            params.boardCd = $scope.selectedBoardDetailFs.boardCd;
            params.boardSeqNo = $scope.selectedBoardDetailFs.boardSeqNo;
            params.idx = data.idx;
            params.status = "D";
            params.userId = userId;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function () { $scope.allSearchFs(); });
        });
    };

    // 댓글 수정저장
    $scope.editAnswerFs = function(data){
        var params = {};
        params.boardCd = $scope.selectedBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetailFs.boardSeqNo;
        params.idx = data.idx;
        params.content = data.contentFs;
        params.status = "U";
        params.userId = userId;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function () { $scope.allSearchFs(); });
    };

    // 재조회
    $scope.allSearchFs = function () {
        // 댓글 DIV 값 초기화 해준 뒤 재조회
        $("#divCommentFs").html("");
        $scope.searchBoardDetailAnswerFs();
        $scope.contentFs = "";

        // 게시판 목록 재조회(댓글 변동에 따른 리스트 제목 옆 댓글 갯수 알림을 위해)
        $scope._broadcast('boardListCtrl');
    };

    // 게시글 삭제
    $scope.delFs = function(){
        // 해당 게시물을 삭제하시겠습니까?
        $scope._popConfirm(messages["boardDetail.delConfirm"], function() {

            var params = {};
            params.boardCd = $scope.selectedBoardDetailFs.boardCd;
            params.boardSeqNo = $scope.selectedBoardDetailFs.boardSeqNo;
            params.status = "D";
            params.userId = userId;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            //$scope._save("/adi/board/board/board/getBoardInfoSave.sb", params, function(){ });
            $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardInfoSave.sb", params, function () { $scope.closeFs(); });
        });
    };

    // 게시글 수정
    $scope.modifyFs = function(){
        $scope.wjBoardInfoLayer.show(true);
        $scope._broadcast('boardInfoCtrl', $scope.getSelectedBoardDetailFs());
        event.preventDefault();
    };

    // 팝업 닫기
    $scope.closeFs = function(){
        $scope.setSelectedBoardDetailFs(null);
        $scope.contentFs = "";

        $scope.wjBoardDetailFullSizeLayer.hide();

        // 재조회
        $scope._broadcast('boardListCtrl');
    };

    // 열람자 목록
    $scope.readingHistFs = function(){
        $scope.wjBoardReadingHistLayer.show(true);
        $scope._broadcast('boardReadingHistCtrl', $scope.getSelectedBoardDetailFs());
        event.preventDefault();
    };


}]);