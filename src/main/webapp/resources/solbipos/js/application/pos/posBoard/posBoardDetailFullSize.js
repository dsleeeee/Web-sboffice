/****************************************************************
 *
 * 파일명 : posBoardDetailFullSize.js
 * 설  명 : POS 화면에서 게시판 상세 FullSize 팝업(포스용) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.10.05     이다솜      1.0
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
    var scope = agrid.getScope('posBoardDetailFullSizeCtrl');
    var params = {};

    params.idx = idx;
    scope.delAnswerFs(params);
}

// 댓글 수정
function viewEditAnswerFs(idx){
    var scope = agrid.getScope('posBoardDetailFullSizeCtrl');
    var params = {};

    params.idx = idx;
    params.contentFs = $("#txtAnswerFs_" + idx).val();
    scope.editAnswerFs(params);
}

/**
 *  팝업 그리드 생성
 */
app.controller('posBoardDetailFullSizeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posBoardDetailFullSizeCtrl', $scope, $http, false));

    // <-- 검색 호출 -->
    $scope.$on("posBoardDetailFullSizeCtrl", function(event, data) {
        // 파일과 댓글 DIV 값 초기화
        $("#fileContentFs").html("");
        $("#divCommentFs").html("");

        $scope.setSelectedPosBoardDetailFs(data);
        $scope.searchPosBoardDetailFs();
        $scope.searchPosBoardDetailAnswerFs();
        $scope.searchPosBoardDetailAtchFs();
        event.preventDefault();
    });

    $scope.searchPosBoardDetailFs = function() {
        var params = {};
        params.boardCd = $scope.selectedPosBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetailFs.boardSeqNo;
        params.userId = userId;

        $scope._postJSONQuery.withOutPopUp( "/adi/board/board/board/getBoardDetailList.sb", params, function(response){
            var posBoardDetailFs = response.data.data.result;
            $scope.posBoardDetailFs = posBoardDetailFs;

            if($scope.posBoardDetailFs.fullSizeYn === "Y") {
                $scope.posBoardDetailFs.fullSizeYn = true;
            } else if ($scope.posBoardDetailFs.fullSizeYn === "N") {
                $scope.posBoardDetailFs.fullSizeYn = false;
            }
            if($scope.posBoardDetailFs.targetFg === "1") {
                $scope.posBoardDetailFs.targetFg = "전체";
            } else if($scope.posBoardDetailFs.targetFg === "2") {
                $scope.posBoardDetailFs.targetFg = $scope.posBoardDetailFs.partOrgnNm;
            }
            if($scope.posBoardDetailFs.noticeYn === "Y") {
                $scope.posBoardDetailFs.noticeYn = true;
            } else if ($scope.posBoardDetailFs.noticeYn === "N") {
                $scope.posBoardDetailFs.noticeYn = false;
            }
            if($scope.posBoardDetailFs.emergencyYn === "Y") {
                $scope.posBoardDetailFs.emergencyYn = true;
            } else if ($scope.posBoardDetailFs.emergencyYn === "N") {
                $scope.posBoardDetailFs.emergencyYn = false;
            }
            // if($scope.posBoardDetailFs.smsYn === "Y") {
            //     $scope.posBoardDetailFs.smsYn = true;
            // } else if ($scope.posBoardDetailFs.smsYn === "N") {
            //     $scope.posBoardDetailFs.smsYn = false;
            // }
            var startDate = $scope.posBoardDetailFs.startDate.substr(0, 4) + "-" + $scope.posBoardDetailFs.startDate.substr(4, 2) + "-" + $scope.posBoardDetailFs.startDate.substr(6, 2);
            var endDate = $scope.posBoardDetailFs.endDate.substr(0, 4) + "-" + $scope.posBoardDetailFs.endDate.substr(4, 2) + "-" + $scope.posBoardDetailFs.endDate.substr(6, 2);
            $scope.posBoardDetailFs.startDate = startDate;
            $scope.posBoardDetailFs.endDate = endDate;

            // 게시글 내용 div 에 넣기
            $("#summernoteDetailFs").html($scope.posBoardDetailFs.content);

            if($scope.posBoardDetailFs.answerFg === "Y") {
                $("#divAnswerFs").show();
            } else if($scope.posBoardDetailFs.answerFg === "N") {
                $("#divAnswerFs").hide();
            }
        });
    };

    // 댓글 조회
    $scope.searchPosBoardDetailAnswerFs = function() {
        var params = {};
        params.boardCd = $scope.selectedPosBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetailFs.boardSeqNo;
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
    $scope.searchPosBoardDetailAtchFs = function() {
        var params = {};
        params.boardCd = $scope.selectedPosBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetailFs.boardSeqNo;

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

                $("#fileContentFs").html(innerHtml);
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedPosBoardDetailFs;
    $scope.setSelectedPosBoardDetailFs = function(store) {
        $scope.selectedPosBoardDetailFs = store;
    };
    $scope.getSelectedPosBoardDetailFs = function(){
        return $scope.selectedPosBoardDetailFs;
    };

    // 댓글 등록
    $scope.saveAnswerFs = function(){
        if($scope.contentFs === "") {
            $scope._popMsg(messages["posBoardDetail.contentBlank"]); // 댓글을 입력해주세요
            return false;
        }

        var params = {};
        params.boardCd = $scope.selectedPosBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetailFs.boardSeqNo;
        params.content = $scope.contentFs;
        params.status = "I";
        params.userId = userId;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/adi/board/board/board/getBoardDetailAnswerSave.sb", params, function () { $scope.allSearchFs(); });
    };

    // 댓글 삭제
    $scope.delAnswerFs = function(data){
        // 해당 댓글을 삭제하시겠습니까?
        $scope._popConfirm(messages["posBoardDetail.delConfirmAnswer"], function() {

            var params = {};
            params.boardCd = $scope.selectedPosBoardDetailFs.boardCd;
            params.boardSeqNo = $scope.selectedPosBoardDetailFs.boardSeqNo;
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
        params.boardCd = $scope.selectedPosBoardDetailFs.boardCd;
        params.boardSeqNo = $scope.selectedPosBoardDetailFs.boardSeqNo;
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
        $scope.searchPosBoardDetailAnswerFs();
        $scope.contentFs = "";

        // 게시판 목록 재조회(댓글 변동에 따른 리스트 제목 옆 댓글 갯수 알림을 위해)
        $scope._broadcast('posBoardListCtrl');
    };

    // 팝업 닫기
    $scope.closeFs = function(){
        $scope.setSelectedPosBoardDetailFs(null);
        $scope.contentFs = "";

        $scope.wjPosBoardDetailFullSizeLayer.hide();

        // 재조회
        $scope._broadcast('posBoardListCtrl');
    };

}]);