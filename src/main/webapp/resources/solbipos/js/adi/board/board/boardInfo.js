/****************************************************************
 *
 * 파일명 : boardInfo.js
 * 설  명 : 일반게시판 신규등록,수정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2010.02.12     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('boardInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardInfoCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("apprFg", apprFgData); //승인구분
    $scope._setComboData("targetFg", targetFgData); //공개대상

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
                        var params = {};
                        params.idx = selectedRow.idx;

                        $scope.delAtch(params);
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("boardInfoCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedBoardInfo(data);
            $scope.searchBoardInfo(); //수정
            $scope.searchBoardDetailAtch(); // 첨부파일 조회
        } else {
            $scope.newForm();  //신규
        }
        event.preventDefault();
    });

    // 수정
    $scope.searchBoardInfo = function() {
        $("#boardInfoTitle").text("수정");
        $("#lblStatus").text("U");

        var params = {};
        params.boardCd = $scope.selectedBoardInfo.boardCd;
        params.boardSeqNo = $scope.selectedBoardInfo.boardSeqNo;

        $scope._postJSONQuery.withOutPopUp( "/adi/board/board/board/getBoardInfoList.sb", params, function(response){
            var boardInfo = response.data.data.result;
            $scope.boardInfo = boardInfo;

            $scope.title = $scope.boardInfo.title;
            $scope.userNm = $scope.boardInfo.userNm;
            $scope.apprFg = $scope.boardInfo.apprFg;
            $scope.targetFg = $scope.boardInfo.targetFg;
            if($scope.boardInfo.noticeYn === "Y") {
                $scope.noticeYn = true;
            } else if ($scope.boardInfo.noticeYn === "N") {
                $scope.noticeYn = false;
            }
            // if($scope.boardInfo.smsYn === "Y") {
            //     $scope.smsYn = true;
            // } else if ($scope.boardInfo.smsYn === "N") {
            //     $scope.smsYn = false;
            // }
            var startDate = $scope.boardInfo.startDate.substr(0, 4) + "/" + $scope.boardInfo.startDate.substr(4, 2) + "/" + $scope.boardInfo.startDate.substr(6, 2);
            var endDate = $scope.boardInfo.endDate.substr(0, 4) + "/" + $scope.boardInfo.endDate.substr(4, 2) + "/" + $scope.boardInfo.endDate.substr(6, 2);
            $scope.startDate = startDate;
            $scope.endDate = endDate;
            // 서머노트에 text 쓰기
            $('#summernote').summernote('code', $scope.boardInfo.content);
            // $('#summernote').summernote('insertText', $scope.boardInfo.content);
        });
    };

    // 첨부파일 조회
    $scope.searchBoardDetailAtch = function() {
        var params = {};
        params.boardCd = $scope.selectedBoardInfo.boardCd;
        params.boardSeqNo = $scope.selectedBoardInfo.boardSeqNo;

        $scope._inquirySub("/adi/board/board/board/getBoardInfoAtchList.sb", params, function() {}, false);
    };

    // 신규
    $scope.newForm = function() {
        $("#boardInfoTitle").text("신규등록");
        $("#lblStatus").text("I");

        $scope.title = "";
        $scope.userNm = "";
        $scope.apprFg.selectedIndex = 1;
        $scope.targetFg.selectedIndex  = 1;
        $scope.noticeYn = false;
        $scope.smsYn = false;
        $scope.startDate = new Date();
        $scope.endDate = new Date();

        $scope.setSelectedBoardInfo(null);
    };
    // <-- //검색 호출 -->

    // 저장
    $("#funcSave").click(function(e){
        if($scope.title === "") {
            $scope._popMsg(messages["boardInfo.titleBlank"]); // 제목을 입력해주세요
            return false;
        }
        if($scope.userNm === "") {
            $scope._popMsg(messages["boardInfo.userNmBlank"]); // 작성자를 입력해주세요
            return false;
        }

        var params = {};
        // 신규, 수정
        params.status = $("#lblStatus").text();
        params.title = $scope.title;
        params.userNm = $scope.userNm;
        params.apprFg = $scope.apprFg;
        params.targetFg = $scope.targetFg;
        if($scope.noticeYn === true) {
            params.noticeYn = "Y";
        } else if ($scope.noticeYn === false) {
            params.noticeYn = "N";
        }
        // if($scope.smsYn === true) {
        //     params.smsYn = "Y";
        // } else if($scope.smsYn === false) {
        //     params.smsYn = "N";
        // }
        params.startDate = dateToDaystring($scope.startDate).replaceAll("-","");
        params.endDate = dateToDaystring($scope.endDate).replaceAll("-","");
        var html = $('#summernote').summernote('code');
        params.content = html;

        // 신규
        if(params.status === "I") {
            params.boardCd = boardCd;
        // 수정
        } else if (params.status === "U") {
            params.boardCd = $scope.selectedBoardInfo.boardCd;
            params.boardSeqNo = $scope.selectedBoardInfo.boardSeqNo;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/adi/board/board/board/getBoardInfoSave.sb", params, function(){ });

        //첨부파일 저장
        $scope.atchSave(params);
    });

    // 첨부파일 저장
    $scope.atchSave = function(data){
        var params = {};
        params.boardCd = data.boardCd;
        params.boardSeqNo = data.boardSeqNo;
        params.title = data.title;
        params.userNm = data.userNm;
        // 신규
        if(data.status === "I") {
            params.status = "INSERT";
        // 수정
        } else if (params.status === "U") {
            params.status = "UPDATE";
        }

        var formData = new FormData($("#boradForm")[0]);
        formData.append("boardCd", params.boardCd);
        formData.append("boardSeqNo", params.boardSeqNo);
        formData.append("status", params.status);
        formData.append("title", params.title);
        formData.append("userNm", params.userNm);

        var url = '/adi/board/board/board/getBoardInfoAtchSave.sb';

        $.ajax({
            url: url,
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            // async:false,
            success: function(result) {
                // console.log('save result', result);
                if (result.status === "OK") {
                    if( isEmptyObject($scope.getSelectVersion()) ) {
                        $scope._popMsg("등록되었습니다.");
                    } else {
                        $scope._popMsg("저장되었습니다.");
                    }

                    $scope.$broadcast('loadingPopupInactive');
                    $scope.close();
                }
                else if (result.status === "FAIL") {
                    $scope._popMsg('Ajax Fail By HTTP Request');
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "SERVER_ERROR") {
                    $scope._popMsg(result.message);
                    $scope.$broadcast('loadingPopupInactive');
                }
                else {
                    var msg = result.status + " : " + result.message;
                    $scope._popMsg(msg);
                    $scope.$broadcast('loadingPopupInactive');
                }
            },
            error : function(result){
                $scope._popMsg("error");
                $scope.$broadcast('loadingPopupInactive');
            }
        },function() {
            $scope._popMsg("Ajax Fail By HTTP Request");
            $scope.$broadcast('loadingPopupInactive');
        });
    };

    // 첨부파일 삭제
    $scope.delAtch = function(data){
        // 해당 파일을 삭제하시겠습니까?
        $scope._popConfirm(messages["boardInfo.delConfirmAtch"], function() {

            var params = {};
            params.boardCd = $scope.selectedBoardInfo.boardCd;
            params.boardSeqNo = $scope.selectedBoardInfo.boardSeqNo;
            params.idx = data.idx;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/adi/board/board/board/getBoardInfoAtchDel.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchBoardDetailAtch();
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.boardInfoStoreShow = function () {
        $scope._broadcast('boardInfoStoreCtrl');
    };

    // 선택 매장
    $scope.selectedBoardInfo;
    $scope.setSelectedBoardInfo = function(store) {
        $scope.selectedBoardInfo = store;
    };
    $scope.getSelectedBoardInfo = function(){
        return $scope.selectedBoardInfo;
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.newForm();  //신규

        // 서머노트 리셋
        $('#summernote').summernote('reset');

        // 첨부파일 리셋
        // ie 일때
        $("#file").replaceWith( $("#file").clone(true) );
        // other browser
        $("#file").val("");

        $scope.wjBoardInfoLayer.hide();
    };

    // 글쓰기 에디터
    $(document).ready(function() {
        $('#summernote').summernote( { height: 150 });
    });

}]);