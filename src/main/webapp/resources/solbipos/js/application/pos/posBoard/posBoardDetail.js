/****************************************************************
 *
 * 파일명 : posBoardDetail.js
 * 설  명 : POS 화면에서 게시판 상세 팝업(포스용) JavaScript
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
    var scope = agrid.getScope('posBoardDetailCtrl');
    var params = {};

    params.idx = idx;
    scope.delAnswer(params);
}

// 댓글 수정
function viewEditAnswer(idx){
    var scope = agrid.getScope('posBoardDetailCtrl');
    var params = {};

    params.idx = idx;
    params.content = $("#txtAnswer_" + idx).val();
    scope.editAnswer(params);
}

/**
 *  팝업 그리드 생성
 */
app.controller('posBoardDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posBoardDetailCtrl', $scope, $http, false));

    // <-- 검색 호출 -->
    $scope.$on("posBoardDetailCtrl", function(event, data) {
        // 파일과 댓글 DIV 값 초기화
        $("#fileContent").html("");
        $("#divComment").html("");

        $scope.setSelectedPosBoardDetail(data);
        $scope.searchPosBoardDetail();
        $scope.searchPosBoardDetailAnswer();
        $scope.searchPosBoardDetailAtch();
        event.preventDefault();
    });

    $scope.searchPosBoardDetail = function() {
        var params = {};
        params.boardCd = $scope.selectedPosBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetail.boardSeqNo;
        params.userId = userId;

        $scope._postJSONQuery.withOutPopUp( "/adi/board/board/board/getBoardDetailList.sb", params, function(response){
            var posBoardDetail = response.data.data.result;
            $scope.posBoardDetail = posBoardDetail;

            $scope.posBoardDetail.title = $scope.posBoardDetail.title;
            $scope.posBoardDetail.userNm = $scope.posBoardDetail.userNm;
            $scope.posBoardDetail.regDt = $scope.posBoardDetail.regDt;
            if($scope.posBoardDetail.fullSizeYn === "Y") {
                $scope.posBoardDetail.fullSizeYn = true;
            } else if ($scope.posBoardDetail.fullSizeYn === "N") {
                $scope.posBoardDetail.fullSizeYn = false;
            }

            if($scope.posBoardDetail.targetFg === "123") {
                $scope.posBoardDetail.targetFg = "관리자/총판/대리점";
            } else if($scope.posBoardDetail.targetFg === "23") {
                $scope.posBoardDetail.targetFg = "총판/대리점";
            } else if($scope.posBoardDetail.targetFg === "45") {
                $scope.posBoardDetail.targetFg = "본사/매장";
            } else if($scope.posBoardDetail.targetFg === "3") {
                $scope.posBoardDetail.targetFg = "대리점";
            } else if($scope.posBoardDetail.targetFg === "4") {
                $scope.posBoardDetail.targetFg = "본사";
            } else if($scope.posBoardDetail.targetFg === "5") {
                $scope.posBoardDetail.targetFg = "매장";
            } else if($scope.posBoardDetail.targetFg === "6") {
                $scope.posBoardDetail.targetFg = "특정대상";
            }else {
                $scope.posBoardDetail.targetFg = "전체";
            }
            if($scope.posBoardDetail.noticeYn === "Y") {
                $scope.posBoardDetail.noticeYn = true;
            } else if ($scope.posBoardDetail.noticeYn === "N") {
                $scope.posBoardDetail.noticeYn = false;
            }
            if($scope.posBoardDetail.emergencyYn === "Y") {
                $scope.posBoardDetail.emergencyYn = true;
            } else if ($scope.posBoardDetail.emergencyYn === "N") {
                $scope.posBoardDetail.emergencyYn = false;
            }
            // if($scope.posBoardDetail.smsYn === "Y") {
            //     $scope.posBoardDetail.smsYn = true;
            // } else if ($scope.posBoardDetail.smsYn === "N") {
            //     $scope.posBoardDetail.smsYn = false;
            // }
            var startDate = $scope.posBoardDetail.startDate.substr(0, 4) + "-" + $scope.posBoardDetail.startDate.substr(4, 2) + "-" + $scope.posBoardDetail.startDate.substr(6, 2);
            var endDate = $scope.posBoardDetail.endDate.substr(0, 4) + "-" + $scope.posBoardDetail.endDate.substr(4, 2) + "-" + $scope.posBoardDetail.endDate.substr(6, 2);
            $scope.posBoardDetail.startDate = startDate;
            $scope.posBoardDetail.endDate = endDate;
            $scope.posBoardDetail.remark = $scope.posBoardDetail.remark;

            // 게시글 내용 div 에 넣기
            $("#summernoteDetail").html($scope.posBoardDetail.content);

            if($scope.posBoardDetail.answerFg === "Y") {
                $("#divAnswer").show();
            } else if($scope.posBoardDetail.answerFg === "N") {
                $("#divAnswer").hide();
            }
        });
    };

    // 댓글 조회
    $scope.searchPosBoardDetailAnswer = function() {
        var params = {};
        params.boardCd = $scope.selectedPosBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetail.boardSeqNo;
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
    $scope.searchPosBoardDetailAtch = function() {
        var params = {};
        params.boardCd = $scope.selectedPosBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetail.boardSeqNo;

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
                        innerHtml += "<th rowspan='" + list.length + "'>" + messages["posBoardDetail.file"] + "</th>";
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
    $scope.selectedPosBoardDetail;
    $scope.setSelectedPosBoardDetail = function(store) {
        $scope.selectedPosBoardDetail = store;
    };
    $scope.getSelectedPosBoardDetail = function(){
        return $scope.selectedPosBoardDetail;
    };

    // 댓글 등록
    $scope.saveAnswer = function(){
        if($scope.content === "") {
            $scope._popMsg(messages["posBoardDetail.contentBlank"]); // 댓글을 입력해주세요
            return false;
        }

        var params = {};
        params.boardCd = $scope.selectedPosBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetail.boardSeqNo;
        params.content = $scope.content;
        params.status = "I";
        params.userId = userId;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function () { $scope.allSearch(); });
    };

    // 댓글 삭제
    $scope.delAnswer = function(data){
        // 해당 댓글을 삭제하시겠습니까?
        $scope._popConfirm(messages["posBoardDetail.delConfirmAnswer"], function() {

            var params = {};
            params.boardCd = $scope.selectedPosBoardDetail.boardCd;
            params.boardSeqNo = $scope.selectedPosBoardDetail.boardSeqNo;
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
        params.boardCd = $scope.selectedPosBoardDetail.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetail.boardSeqNo;
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
        $scope.searchPosBoardDetailAnswer();
        $scope.content = "";

        // 게시판 목록 재조회(댓글 변동에 따른 리스트 제목 옆 댓글 갯수 알림을 위해)
        $scope._broadcast('posBoardListCtrl');
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.setSelectedPosBoardDetail(null);
        $scope.content = "";

        $scope.wjPosBoardDetailLayer.hide();

        // 재조회
        $scope._broadcast('posBoardListCtrl');
    };

}]);