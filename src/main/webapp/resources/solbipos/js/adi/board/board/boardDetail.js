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

// 태그를 그려서 div에 바인딩 시, ng-click이 먹히지 않아 onclick을 사용하고, function 함수를 통해 $scope.함수에 접근
// 댓글 수정 클릭 시, 수정창 보이기 or 숨기기
function viewAnswerArea(idx, mod){
    if(mod === "open"){
        $("#hdAnswer_" + idx).css("display", "");
    }else{
        $("#hdAnswer_" + idx).css("display", "none");
    }
}

// 댓글 삭제
function viewDelAnswer(idx){
    var scope = agrid.getScope('boardDetailCtrl');
    var params = {};

    params.idx = idx;
    scope.delAnswer(params);
}

// 댓글 수정
function viewEditAnswer(idx){
    var scope = agrid.getScope('boardDetailCtrl');
    var params = {};

    params.idx = idx;
    params.content = $("#txtAnswer_" + idx).val();
    scope.editAnswer(params);
}

/**
 *  팝업 그리드 생성
 */
app.controller('boardDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardDetailCtrl', $scope, $http, true));

    // <-- 검색 호출 -->
    $scope.$on("boardDetailCtrl", function(event, data) {
        // 파일과 댓글 DIV 값 초기화
        $("#fileContent").html("");
        $("#divComment").html("");

        // 열람자조회/삭제/수정 버튼 기본 hidden
        $("#readingHistButton").hide();
        $("#delButton").hide();
        $("#modifyButton").hide();

        // 1. [182] 게시판수정사용자 인 경우 열람자조회/삭제/수정 가능
        if (orgnFg === "MASTER") {
            if (data.targetFg === "123" || data.targetFg === "23" || data.targetFg === "12345" || data.targetFg === "45" || data.targetFg === "6") {
                for (var i = 0; i < boardModifyUserId.length; i++) {
                    if (userId === boardModifyUserId[i].name) {
                        $("#readingHistButton").show();
                        $("#delButton").show();
                        $("#modifyButton").show();
                    }
                }
            }
        }

        if (orgnFg === "HQ") {
            if (data.targetFg === "45" || data.targetFg === "5" || data.targetFg === "6" || data.targetFg === "4") {
                for (var i = 0; i < HqBoardModifyUserId.length; i++) {
                    if (userId === HqBoardModifyUserId[i].name) {
                        $("#readingHistButton").show();
                        $("#delButton").show();
                        $("#modifyButton").show();
                    }
                }
            }
        }

        // 2. 작성자 본인 열람자조회/삭제/수정 가능
        if (userId === data.userId) {
            $("#readingHistButton").show();
            $("#delButton").show();
            $("#modifyButton").show();
        }

        /*if(userId !== data.userId) {
            $("#readingHistButton").hide();
            $("#delButton").hide();
            $("#modifyButton").hide();
        } else if (userId === data.userId) {
            $("#readingHistButton").show();
            $("#delButton").show();
            $("#modifyButton").show();
        }*/
        $scope.setSelectedBoardDetail(data);
        $scope.searchBoardDetail();
        $scope.searchBoardDetailAnswer();
        $scope.searchBoardDetailAtch();
        event.preventDefault();
    });

    $scope.searchBoardDetail = function() {
        var params = {};
        params.boardCd = $scope.selectedBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetail.boardSeqNo;
        params.userId = userId;

        $scope._postJSONQuery.withOutPopUp( "/adi/board/board/board/getBoardDetailList.sb", params, function(response){
            var boardDetail = response.data.data.result;
            $scope.boardDetail = boardDetail;

            $scope.boardDetail.title = $scope.boardDetail.title;
            $scope.boardDetail.userNm = $scope.boardDetail.userNm;
            $scope.boardDetail.regDt = $scope.boardDetail.regDt;
            if($scope.boardDetail.fullSizeYn === "Y") {
                $scope.boardDetail.fullSizeYn = true;
            } else if ($scope.boardDetail.fullSizeYn === "N") {
                $scope.boardDetail.fullSizeYn = false;
            }
            if($scope.boardDetail.popupYn === "Y") {
                $scope.boardDetail.popupYn = true;
            } else if ($scope.boardDetail.popupYn === "N") {
                $scope.boardDetail.popupYn = false;
            }
            if($scope.boardDetail.targetFg === "123") {
                $scope.boardDetail.targetFg = "관리자/총판/대리점";
            } else if($scope.boardDetail.targetFg === "23") {
                $scope.boardDetail.targetFg = "총판/대리점";
            } else if($scope.boardDetail.targetFg === "45") {
                $scope.boardDetail.targetFg = "본사/매장";
            } else if($scope.boardDetail.targetFg === "3") {
                $scope.boardDetail.targetFg = "대리점";
            } else if($scope.boardDetail.targetFg === "4") {
                $scope.boardDetail.targetFg = "본사";
            } else if($scope.boardDetail.targetFg === "5") {
                $scope.boardDetail.targetFg = "매장";
            } else if($scope.boardDetail.targetFg === "6") {
                $scope.boardDetail.targetFg = "특정대상";
            }else {
                $scope.boardDetail.targetFg = "전체";
            }
            if($scope.boardDetail.noticeYn === "Y") {
                $scope.boardDetail.noticeYn = true;
            } else if ($scope.boardDetail.noticeYn === "N") {
                $scope.boardDetail.noticeYn = false;
            }
            if($scope.boardDetail.emergencyYn === "Y") {
                $scope.boardDetail.emergencyYn = true;
            } else if($scope.boardDetail.emergencyYn === "N")  {
                $scope.boardDetail.emergencyYn = false;
            }
            // if($scope.boardDetail.smsYn === "Y") {
            //     $scope.boardDetail.smsYn = true;
            // } else if ($scope.boardDetail.smsYn === "N") {
            //     $scope.boardDetail.smsYn = false;
            // }
            var startDate = $scope.boardDetail.startDate.substr(0, 4) + "-" + $scope.boardDetail.startDate.substr(4, 2) + "-" + $scope.boardDetail.startDate.substr(6, 2);
            var endDate = $scope.boardDetail.endDate.substr(0, 4) + "-" + $scope.boardDetail.endDate.substr(4, 2) + "-" + $scope.boardDetail.endDate.substr(6, 2);
            $scope.boardDetail.startDate = startDate;
            $scope.boardDetail.endDate = endDate;
            $scope.boardDetail.remark = $scope.boardDetail.remark;

            // 게시글 내용 div 에 넣기
            $("#summernoteDetail").html($scope.boardDetail.content);

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
        params.userId = userId;

        $scope._postJSONQuery.withOutPopUp("/adi/board/board/board/getBoardDetailAnswerList.sb", params, function(response) {
            var list = response.data.data.list;
            var innerHtml = "";

            if(list.length > 0){

                innerHtml += "<table class=\"tblType01 mt10\">";
                innerHtml += "<colgroup><col width=\"85%\" /><col width=\"15%\" /></colgroup>";
                innerHtml += "<tbody>";

                for(var i=0; i< list.length; i++) {

                    innerHtml += "<tr style=\"height: 10px;\">";
                    innerHtml += "<th>" + list[i].userNm +" | " + list[i].modDt + " </th>";
                    // 본인이 쓴 글만 수정/삭제 가능
                    if(userId == list[i].regId) {
                        innerHtml += "<th><a href=\"#\" style=\"font-size: 12px; color: black;\" onclick=\"viewAnswerArea(\'"+ list[i].idx + "\', \'open\');\">수정</a> | ";
                        innerHtml += "<a href=\"#\" style=\"font-size: 12px; color: black;\" onclick=\"viewDelAnswer(\'" + list[i].idx + "\')\">삭제</a></th>";
                    } else {
                        innerHtml += "<th></th>";
                    }
                    innerHtml += "</tr>";

                    innerHtml += "<tr>";
                    innerHtml += "<td align=\"left\" style=\"padding:5px;\" colspan=\"2\">" + list[i].content +"</td>";
                    innerHtml += "</tr>";

                    innerHtml += "<tr id=\"hdAnswer_" + list[i].idx + "\" style=\"display: none;\">";
                    innerHtml += "<td align=\"center\"><input style=\"border: 1px solid #d0d0d0;\" type=\"text\" id=\"txtAnswer_" + list[i].idx +"\" value=\"" + list[i].content + "\"></td>";
                    innerHtml += "<td align=\"center\"><a href=\"#\" style=\"font-size: 12px; color: black;\" onclick=\"viewEditAnswer(\'" + list[i].idx + "\')\">저장 </a> | ";
                    innerHtml += "<a href=\"#\" style=\"font-size: 12px; color: black;\" onclick=\"viewAnswerArea(\'"+ list[i].idx + "\', \'close\');\"> 취소</a></td>";
                    innerHtml += "</tr>";

                }

                innerHtml += "</tbody>";
                innerHtml += "</table>";

                $("#divComment").html(innerHtml);
            }
        });
    };

    // 첨부파일 다운로드 조회
    $scope.searchBoardDetailAtch = function() {
        var params = {};
        params.boardCd = $scope.selectedBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedBoardDetail.boardSeqNo;

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

                $("#fileContent").html(innerHtml);
            }
        });
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
        params.userId = userId;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function () { $scope.allSearch(); });
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
            params.userId = userId;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function () { $scope.allSearch(); });
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
        params.userId = userId;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function () { $scope.allSearch(); });
    };

    // 재조회
    $scope.allSearch = function () {
        // 댓글 DIV 값 초기화 해준 뒤 재조회
        $("#divComment").html("");
        $scope.searchBoardDetailAnswer();
        $scope.content = "";
        
        // 게시판 목록 재조회(댓글 변동에 따른 리스트 제목 옆 댓글 갯수 알림을 위해)
        $scope._broadcast('boardListCtrl');
    };

    // 게시판 삭제
    $scope.del = function(){
        // 해당 게시물을 삭제하시겠습니까?
        $scope._popConfirm(messages["boardDetail.delConfirm"], function() {

            var params = {};
            params.boardCd = $scope.selectedBoardDetail.boardCd;
            params.boardSeqNo = $scope.selectedBoardDetail.boardSeqNo;
            params.status = "D";
            params.userId = userId;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            //$scope._save("/adi/board/board/board/getBoardInfoSave.sb", params, function(){ });
            $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardInfoSave.sb", params, function () { $scope.close(); });
        });
    };

    // 수정
    $scope.modify = function(){
        $scope.wjBoardInfoLayer.show(true);
        $scope._broadcast('boardInfoCtrl', $scope.getSelectedBoardDetail());
        event.preventDefault();
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.setSelectedBoardDetail(null);
        $scope.content = "";

        $scope.wjBoardDetailLayer.hide();

        // 재조회
        $scope._broadcast('boardListCtrl');
    };

    // 열람자 목록
    $scope.readingHist = function(){
        $scope.wjBoardReadingHistLayer.show(true);
        $scope._broadcast('boardReadingHistCtrl', $scope.getSelectedBoardDetail());
        event.preventDefault();
    };

}]);