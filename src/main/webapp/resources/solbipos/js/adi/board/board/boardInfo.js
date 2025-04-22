/****************************************************************
 *
 * 파일명 : boardInfo.js
 * 설  명 : 일반게시판 신규등록,수정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2010.02.12     김설아      1.1
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 공개대상
// var targetFgData1 = [
//     {"name":"전체","value":"1"}
// ];

/**
 *  팝업 그리드 생성
 */
app.controller('boardInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardInfoCtrl', $scope, $http, $timeout, true));

    // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S: 매장)
    $scope.orgnFg = gvOrgnFg;

    var partOrgnCd;
    var partOrgnNm;

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("apprFg", apprFgData); //승인구분
    // 본사권한으로 로그인 한 경우, 매장선택 가능.
    // if($scope.orgnFg === 'H') {
    if($scope.orgnFg === 'M') {
        // 관리자
        var targetFgData = [
            {"name": "관리자/총판/대리점", "value": "123"},
            {"name": "총판/대리점", "value": "23"},
            {"name": "전체", "value": "12345"},
            {"name": "본사/매장", "value": "45"},
            {"name": "특정대상(본사/매장)", "value": "6"}
        ]
    } else if($scope.orgnFg === 'A') {
        if(pAgencyCd === "00000") {
            // 총판
            var targetFgData = [
                {"name": "총판/대리점", "value": "23"},
                {"name": "전체", "value": "2345"},
                {"name": "본사/매장", "value": "45"},
                {"name": "특정대상(본사/매장)", "value": "6"}
            ]
        } else {
            // 대리점
            var targetFgData = [
                {"name": "대리점", "value": "3"},
                {"name": "전체", "value": "345"},
                {"name": "본사/매장", "value": "45"},
                {"name": "특정대상(본사/매장)", "value": "6"}
            ]
        }
    } else if($scope.orgnFg === 'H') {
        // 본사
        var targetFgData = [
            {"name": "전체", "value": "45"},
            {"name": "매장", "value": "5"},
            {"name": "특정대상", "value": "6"}
        ]
    } else if($scope.orgnFg === 'S') {
        // 매장
        var targetFgData = [
            {"name": "매장", "value": "5"}
        ]
    }
        $scope._setComboData("targetFg", targetFgData); // 공개대상
    // } else {
    //     $scope._setComboData("targetFg", targetFgData1); // 공개대상
    // }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 본문적용
                if (col.binding === "imgApply") {
                    // 값이 있으면 링크 효과
                    if(item["fileExt"] === "png" || item["fileExt"] === "PNG" ||
                        item["fileExt"] === "jpg" || item["fileExt"] === "JPG" ||
                        item["fileExt"] === "jpeg" || item["fileExt"] === "JPEG" ||
                        item["fileExt"] === "gif" || item["fileExt"] === "GIF"){
                        if (item["imgApply"] === '본문적용') {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                        }
                    }else{
                        e.cell.innerHTML = "";
                    }
                }

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

                // '본문추가' 클릭 시 이미지 본문에 적용
                if(col.binding === "imgApply"){
                    if(selectedRow["fileExt"] === "png" || selectedRow["fileExt"] === "PNG" ||
                        selectedRow["fileExt"] === "jpg" || selectedRow["fileExt"] === "JPG" ||
                        selectedRow["fileExt"] === "gif" || selectedRow["fileExt"] === "GIF") {
                        if (selectedRow["imgApply"] === '본문적용') {
                            var params = {};
                            params.tempPath = selectedRow.tempPath;
                            params.idx = selectedRow.idx;

                            $scope.addImgContents(params);
                        }
                    }
                }

                // 삭제 클릭 시 파일 제거
                if ( col.binding === "del") {
                    if (selectedRow["del"] === '삭제') {
                        // 서버에 있는 파일 삭제 인 경우
                        if(selectedRow["idx"] !== "" && selectedRow["idx"] !== null) {

                            // 게시글에서 파일관련 내용 제거
                            var html = $('#summernote').summernote('code');
                            $('#summernote').summernote('code', html.replaceAll("<p><img src=\"" + rootUrl + "/Board/" + selectedRow["tempPath"] + "\"></p>", ""));

                            // 첨부파일도 삭제하시겠습니까?
                            $scope._popConfirm(messages["boardInfo.tempFileDel.msg"], function() {

                                var params = {};
                                params.idx = selectedRow.idx;

                                $scope.delAtch(params);
                            });

                        }else{ // 로컬 파일 삭제인 경우

                            // 게시글에서 파일관련 내용 제거
                            var html = $('#summernote').summernote('code');
                            $('#summernote').summernote('code', html.replaceAll("<p><img src=\"" + selectedRow["tempPath"] + "\"></p>", ""));

                            // 첨부파일도 삭제하시겠습니까?
                            $scope._popConfirm(messages["boardInfo.tempFileDel.msg"], function() {

                                // input file 에서 삭제하려는 파일 제거
                                var files = document.getElementById("file").files;
                                var dt = new DataTransfer();

                                for (var i = 0; i < files.length; i++) {
                                    if (files[i].name !== selectedRow["orginlFileNm"]) {
                                        dt.items.add(files.item(i));
                                    }
                                }

                                document.getElementById("file").files = dt.files;

                                // 파일 리스트에서 제거
                                for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                                    var item = $scope.flex.collectionView.items[i];
                                    if (item.orginlFileNm === selectedRow["orginlFileNm"]) {
                                        $scope.flex.collectionView.removeAt(i);
                                    }
                                }

                                // 객체 URL 제거
                                URL.revokeObjectURL(selectedRow["tempPath"]);
                            });
                        }
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
            setTimeout(function() {
                $scope.setTargetFg();
            }, 500);
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
            if($scope.boardInfo.fullSizeYn === "Y") {
                $scope.fullSizeYn = true;
            } else if ($scope.boardInfo.fullSizeYn === "N") {
                $scope.fullSizeYn = false;
            }
            if($scope.boardInfo.popupYn === "Y") {
                $scope.popupYn = true;
            } else if ($scope.boardInfo.popupYn === "N") {
                $scope.popupYn = false;
            }
            $scope.targetFg = $scope.boardInfo.targetFg;
            if($scope.targetFg === "6"){
                partOrgnCd = $scope.boardInfo.partOrgnCd;
                partOrgnNm = $scope.boardInfo.partOrgnNm;
            }
            if($scope.boardInfo.noticeYn === "Y") {
                $scope.noticeYn = true;
            } else if ($scope.boardInfo.noticeYn === "N") {
                $scope.noticeYn = false;
            }
            $scope.noticeYnChk();
            if($scope.boardInfo.emergencyYn === "Y") {
                $scope.emergencyYn = true;
            } else {
                $scope.emergencyYn = false;
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
            $scope.remark = $scope.boardInfo.remark;
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
        $scope.userNm = userNm;
        $scope.apprFg = "1";
        $scope.fullSizeYn = false;
        $scope.popupYn = false;
        if($scope.orgnFg != 'S'){
            $scope.targetFgCombo.selectedIndex = 0;
            partOrgnCd = "";
            partOrgnNm = "선택";
        }
        $scope.noticeYn = false;
        $scope.emergencyYn = false;
        $scope.smsYn = false;
        $scope.startDate = new Date();
        $scope.endDate = new Date();
        $scope.remark = "";

        $scope.noticeYnChk();
        $scope.setSelectedBoardInfo(null);

        // 첨부파일 그리드 초기화
        $scope.gridDefault();

        // 서머노트 리셋
        $('#summernote').summernote('reset');

        // 첨부파일 리셋
        var agent = navigator.userAgent.toLowerCase();
        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
            // ie 일때
            $("#file").replaceWith( $("#file").clone(true) );
        } else {
            // other browser 일때
            $("#file").val("");
        }

        $("#boradForm")[0].reset();
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedBoardInfo;
    $scope.setSelectedBoardInfo = function(store) {
        $scope.selectedBoardInfo = store;
    };
    $scope.getSelectedBoardInfo = function(){
        return $scope.selectedBoardInfo;
    };

    // 저장
    $("#funcSave").click(function(e){
        if($scope.title === "") {
            $scope._popMsg(messages["boardInfo.titleBlank"]); // 제목을 입력해주세요
            return false;
        }

        // 이미지명 형식 체크(multiple check)
        if($("#file").val() !== null && $("#file").val() !== undefined && $("#file").val() !== "") {
            var files = $("#file")[0].files;
            for(var i = 0; i < files.length; i++){
                var imgFullNm = files[i].name.substring(files[i].name.lastIndexOf('\\') + 1);
                if (1 > imgFullNm.lastIndexOf('.')) {
                    $scope._popMsg(messages["boardInfo.fileNmChk.msg"]  + "<br/>[파일명 : " + imgFullNm + "]");
                    return false;
                }
            }
        }

        // 공개대상이 특정대상인데 대상을 선택하지 않은 경우
        if($scope.targetFg === "6" && $scope.orgnFg != "S"){
            if($("#boardInfoStoreCd").val() === undefined || $("#boardInfoStoreCd").val() === "" || $("#boardInfoStoreCd").val() === null){
                $scope._popMsg(messages["boardInfo.targetFg.msg"]);
                return false;
            }
        } else if($scope.orgnFg === "S"){
            $scope.targetFg = "5";
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기

        var params = {};
        // 신규, 수정
        params.status = $("#lblStatus").text();
        params.title = $scope.title;
        params.userId = userId;
        params.userNm = $scope.userNm;
        params.apprFg = $scope.apprFg;
        if($scope.fullSizeYn === true) {
            params.fullSizeYn = "Y";
        } else if ($scope.fullSizeYn === false) {
            params.fullSizeYn = "N";
        }
        if($scope.popupYn === true) {
            params.popupYn = "Y";
        } else if ($scope.popupYn === false) {
            params.popupYn = "N";
        }
        params.targetFg = $scope.targetFg;
        if($scope.noticeYn === true) {
            params.noticeYn = "Y";
        } else if ($scope.noticeYn === false) {
            params.noticeYn = "N";
        }
        if($scope.emergencyYn === true) {
            params.emergencyYn = "Y";
        } else {
            params.emergencyYn = "N";
        }
        // if($scope.smsYn === true) {
        //     params.smsYn = "Y";
        // } else if($scope.smsYn === false) {
        //     params.smsYn = "N";
        // }
        params.startDate = dateToDaystring($scope.startDate).replaceAll("-","");
        params.endDate = dateToDaystring($scope.endDate).replaceAll("-","");
        params.remark = $scope.remark;
        params.storeCds = $("#boardInfoStoreCd").val();
        // 신규
        if(params.status === "I") {
            params.boardCd = boardCd;
        // 수정
        } else if (params.status === "U") {
            params.boardCd = $scope.selectedBoardInfo.boardCd;
            params.boardSeqNo = $scope.selectedBoardInfo.boardSeqNo;
        }

        var html = $('#summernote').summernote('code');
        params.content = html;


        // 특정 구문 찾기 <v:shape ~ </v:shape>
        var input_str = "";
        // 특정 구문 몇번나오는지 체크
        var str_chk = params.content.match(/(\<v:shape)(\s+)/gi); // <v:shape 전체공백 (대소문자전체)
        if(str_chk != null) {
            var startWord = "<v:shape ";
            var endWord = "</v:shape>";
            var startCnt = 0;
            var endCnt = 0;
            for (var i = 0; i < str_chk.length; i++) {
                startCnt = params.content.indexOf(startWord, endCnt+(endWord.length));
                endCnt = params.content.indexOf(endWord, endCnt+(endWord.length));

                input_str = params.content.substring(startCnt, endCnt+(endWord.length));

                // 특정 구문에서 width: ~ pt,px 체크하기
                var chkTot = "";
                // 소수점있는숫자만 pt
                var chk1 = input_str.match(/(\width:)(\d+)(\.\d+)(\pt)/gi); // width: 소수점있는숫자만 pt (대소문자전체)
                var chk2 = input_str.match(/(\width:)(\s+)(\d+)(\.\d+)(\pt)/gi); // width: 전체공백 소수점있는숫자만 pt (대소문자전체)
                var chk3 = input_str.match(/(\width:)(\d+)(\.\d+)(\s+)(\pt)/gi); // width: 소수점있는숫자만 전체공백 pt (대소문자전체)
                var chk4 = input_str.match(/(\width:)(\s+)(\d+)(\.\d+)(\s+)(\pt)/gi); // width: 전체공백 소수점있는숫자만 전체공백 pt (대소문자전체)
                if(chk1 !== null) { chkTot = chkTot + chk1 + "," }
                if(chk2 !== null) { chkTot = chkTot + chk2 + "," }
                if(chk3 !== null) { chkTot = chkTot + chk3 + "," }
                if(chk4 !== null) { chkTot = chkTot + chk4 + "," }
                // 소수점있는숫자만 px
                var chk5 = input_str.match(/(\width:)(\d+)(\.\d+)(\px)/gi); // width: 소수점있는숫자만 px (대소문자전체)
                var chk6 = input_str.match(/(\width:)(\s+)(\d+)(\.\d+)(\px)/gi); // width: 전체공백 소수점있는숫자만 px (대소문자전체)
                var chk7 = input_str.match(/(\width:)(\d+)(\.\d+)(\s+)(\px)/gi); // width: 소수점있는숫자만 전체공백 px (대소문자전체)
                var chk8 = input_str.match(/(\width:)(\s+)(\d+)(\.\d+)(\s+)(\px)/gi); // width: 전체공백 소수점있는숫자만 전체공백 px (대소문자전체)
                if(chk5 !== null) { chkTot = chkTot + chk5 + "," }
                if(chk6 !== null) { chkTot = chkTot + chk6 + "," }
                if(chk7 !== null) { chkTot = chkTot + chk7 + "," }
                if(chk8 !== null) { chkTot = chkTot + chk8 + "," }
                // 숫자만 pt
                var chk9 = input_str.match(/(\width:)(\d+)(\pt)/gi); // width: 숫자만 pt (대소문자전체)
                var chk10 = input_str.match(/(\width:)(\s+)(\d+)(\pt)/gi); // width: 전체공백 숫자만 pt (대소문자전체)
                var chk11 = input_str.match(/(\width:)(\d+)(\s+)(\pt)/gi); // width: 숫자만 전체공백 pt (대소문자전체)
                var chk12 = input_str.match(/(\width:)(\s+)(\d+)(\s+)(\pt)/gi); // width: 전체공백 숫자만 전체공백 pt (대소문자전체)
                if(chk9 !== null) { chkTot = chkTot + chk9 + "," }
                if(chk10 !== null) { chkTot = chkTot + chk10 + "," }
                if(chk11 !== null) { chkTot = chkTot + chk11 + "," }
                if(chk12 !== null) { chkTot = chkTot + chk12 + "," }
                // 숫자만 px
                var chk13 = input_str.match(/(\width:)(\d+)(\px)/gi); // width: 숫자만 px (대소문자전체)
                var chk14 = input_str.match(/(\width:)(\s+)(\d+)(\px)/gi); // width: 전체공백 숫자만 px (대소문자전체)
                var chk15 = input_str.match(/(\width:)(\d+)(\s+)(\px)/gi); // width: 숫자만 전체공백 px (대소문자전체)
                var chk16 = input_str.match(/(\width:)(\s+)(\d+)(\s+)(\px)/gi); // width: 전체공백 숫자만 전체공백 px (대소문자전체)
                if(chk13 !== null) { chkTot = chkTot + chk13 + "," }
                if(chk14 !== null) { chkTot = chkTot + chk14 + "," }
                if(chk15 !== null) { chkTot = chkTot + chk15 + "," }
                if(chk16 !== null) { chkTot = chkTot + chk16 + "," }
                chkTot = chkTot.substring(0, chkTot.length-1);

                var input_str2 = "";
                if(chkTot != null) {
                    var arr = chkTot.split(",");
                    if (arr.length > 0) {
                        for (var i = 0; i < arr.length; i++) {
                            var data = {};
                            data.chkTot1 = arr[i]; // width: ~ pt,px
                            data.chkTot2 = arr[i].match(/(\d+)(\.\d+)/g); // 소수점있는숫자만
                            if (data.chkTot2 === null) {
                                data.chkTot2 = arr[i].match(/\d+/g); // 숫자만
                            }
                            data.chkTot3 = arr[i].match(/(\pt)/gi); // pt
                            if (data.chkTot3 === null) {
                                data.chkTot3 = arr[i].match(/(\px)/gi); // px
                            }
                            // arr.push(data);

                            // 소수점제거 (round 반올림, ceil 올림, floor내림)
                            if (Math.ceil(data.chkTot2) > 100) {
                                // 100pt, 100px 이상 줄이기
                                input_str2 = input_str.replace(data.chkTot1, "width: 100" + data.chkTot3);

                                params.content = params.content.replace(input_str, input_str2);
                            }
                        }
                    }
                }
            }
        }


        //보안때문에 게시판에서 스크립트 및 액션이벤트 사용하지 못하도록 막음.
        //내용에 script 또는 on이벤트가 들어있는 경우 저장하지 못함.
        if(params.content.toLowerCase().indexOf('script') >= 0
        || params.content.toLowerCase().indexOf('onabort') >= 0
        || params.content.toLowerCase().indexOf('onactivate') >= 0
        || params.content.toLowerCase().indexOf('onafterprint') >= 0
        || params.content.toLowerCase().indexOf('onafterupdate') >= 0
        || params.content.toLowerCase().indexOf('onbeforeactivate') >= 0
        || params.content.toLowerCase().indexOf('onbeforecopy') >= 0
        || params.content.toLowerCase().indexOf('onbeforecut') >= 0
        || params.content.toLowerCase().indexOf('onbeforedeactivate') >= 0
        || params.content.toLowerCase().indexOf('onbeforeeditfocus') >= 0
        || params.content.toLowerCase().indexOf('onbeforepaste') >= 0
        || params.content.toLowerCase().indexOf('onbeforeprint') >= 0
        || params.content.toLowerCase().indexOf('onbeforeunload') >= 0
        || params.content.toLowerCase().indexOf('onbeforeupdate') >= 0
        || params.content.toLowerCase().indexOf('onblur') >= 0
        || params.content.toLowerCase().indexOf('onbounce') >= 0
        || params.content.toLowerCase().indexOf('oncellchange') >= 0
        || params.content.toLowerCase().indexOf('onchange') >= 0
        || params.content.toLowerCase().indexOf('onclick') >= 0
        || params.content.toLowerCase().indexOf('oncontextmenu') >= 0
        || params.content.toLowerCase().indexOf('oncontrolselect') >= 0
        || params.content.toLowerCase().indexOf('oncopy') >= 0
        || params.content.toLowerCase().indexOf('oncut') >= 0
        || params.content.toLowerCase().indexOf('ondataavailable') >= 0
        || params.content.toLowerCase().indexOf('ondatasetchanged') >= 0
        || params.content.toLowerCase().indexOf('ondatasetcomplete') >= 0
        || params.content.toLowerCase().indexOf('ondblclick') >= 0
        || params.content.toLowerCase().indexOf('ondeactivate') >= 0
        || params.content.toLowerCase().indexOf('ondrag') >= 0
        || params.content.toLowerCase().indexOf('ondragend') >= 0
        || params.content.toLowerCase().indexOf('ondragenter') >= 0
        || params.content.toLowerCase().indexOf('ondragleave') >= 0
        || params.content.toLowerCase().indexOf('ondragover') >= 0
        || params.content.toLowerCase().indexOf('ondragstart') >= 0
        || params.content.toLowerCase().indexOf('ondrop') >= 0
        || params.content.toLowerCase().indexOf('onerror') >= 0
        || params.content.toLowerCase().indexOf('onerrorupdate') >= 0
        || params.content.toLowerCase().indexOf('onfilterchange') >= 0
        || params.content.toLowerCase().indexOf('onfinish') >= 0
        || params.content.toLowerCase().indexOf('onfocus') >= 0
        || params.content.toLowerCase().indexOf('onfocusin') >= 0
        || params.content.toLowerCase().indexOf('onfocusout') >= 0
        || params.content.toLowerCase().indexOf('onhelp') >= 0
        || params.content.toLowerCase().indexOf('onkeydown') >= 0
        || params.content.toLowerCase().indexOf('onkeypress') >= 0
        || params.content.toLowerCase().indexOf('onkeyup') >= 0
        || params.content.toLowerCase().indexOf('onlayoutcomplete') >= 0
        || params.content.toLowerCase().indexOf('onload') >= 0
        || params.content.toLowerCase().indexOf('onlosecapture') >= 0
        || params.content.toLowerCase().indexOf('onmousedown') >= 0
        || params.content.toLowerCase().indexOf('onmouseenter') >= 0
        || params.content.toLowerCase().indexOf('onmouseleave') >= 0
        || params.content.toLowerCase().indexOf('onmousemove') >= 0
        || params.content.toLowerCase().indexOf('onmouseout') >= 0
        || params.content.toLowerCase().indexOf('onmouseover') >= 0
        || params.content.toLowerCase().indexOf('onmouseup') >= 0
        || params.content.toLowerCase().indexOf('onmousewheel') >= 0
        || params.content.toLowerCase().indexOf('onmove') >= 0
        || params.content.toLowerCase().indexOf('onmoveend') >= 0
        || params.content.toLowerCase().indexOf('onmovestart') >= 0
        || params.content.toLowerCase().indexOf('onpaste') >= 0
        || params.content.toLowerCase().indexOf('onpropertychange') >= 0
        || params.content.toLowerCase().indexOf('onreadystatechange') >= 0
        || params.content.toLowerCase().indexOf('onreset') >= 0
        || params.content.toLowerCase().indexOf('onresize') >= 0
        || params.content.toLowerCase().indexOf('onresizeend') >= 0
        || params.content.toLowerCase().indexOf('onresizestart') >= 0
        || params.content.toLowerCase().indexOf('onrowenter') >= 0
        || params.content.toLowerCase().indexOf('onrowexit') >= 0
        || params.content.toLowerCase().indexOf('onrowsdelete') >= 0
        || params.content.toLowerCase().indexOf('onrowsinserted') >= 0
        || params.content.toLowerCase().indexOf('onscroll') >= 0
        || params.content.toLowerCase().indexOf('onselect') >= 0
        || params.content.toLowerCase().indexOf('onselectionchange') >= 0
        || params.content.toLowerCase().indexOf('onselectstart') >= 0
        || params.content.toLowerCase().indexOf('onstart') >= 0
        || params.content.toLowerCase().indexOf('onstop') >= 0
        || params.content.toLowerCase().indexOf('onsubmit') >= 0
        || params.content.toLowerCase().indexOf('onunload') >= 0
        )
        {
            $scope._popMsg(messages["boardInfo.scriptChk.msg"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        // $scope._save("/adi/board/board/board/getBoardInfoSave.sb", params, function(){ });
        $.ajax({
            type: "POST",
            url: "/adi/board/board/board/getBoardInfoSave.sb",
            data:  JSON.stringify(params),
            success: function(result){
                // alert(result.status);
                // alert(result.data);
                if (result.status === "OK") {
                    // 신규
                    if(params.status === "I") {
                        params.boardSeqNo = result.data;
                    }
                    //첨부파일 저장
                    $scope.atchSave(params);

                    // 첨부파일 임시경로 UPDATE 후 게시글 이미지 서버경로로 치환
                    var params2 = new Array();

                    $scope.flex.collectionView.commitEdit();
                    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                        var item = $scope.flex.collectionView.itemsAdded[i];

                        if(item.idx === "" && item.orginlFileNm !== "" && item.tempPath !== ""){

                            if(item.fileExt === "png" || item.fileExt === "PNG" ||
                                item.fileExt === "jpg" || item.fileExt === "JPG" ||
                                item.fileExt === "jpeg" || item.fileExt === "JPEG" ||
                                item.fileExt === "gif" || item.fileExt === "GIF") {

                                item.boardCd = params.boardCd;
                                item.boardSeqNo = params.boardSeqNo;
                                item.rootUrl = rootUrl;

                                params2.push(item);
                            }
                        }

                        // 객체 URL 제거
                        URL.revokeObjectURL(item.tempPath);
                    }

                    if(params2.length > 0) {
                        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                        setTimeout(function() {
                            $scope._postJSONSave.withOutPopUp("/adi/board/board/board/setServerPathFile.sb", params2, function () {});
                        },1500)
                    }

                    // 수정
                    if(params.status === "U") {
                        params.userId = $scope.selectedBoardInfo.userId;

                        // 저장기능 수행후 재조회
                        if($scope.fullSizeYn === true) {
                            $scope.wjBoardDetailLayer.hide();
                            $scope.wjBoardDetailFullSizeLayer.show(true);
                        }else{
                            $scope.wjBoardDetailFullSizeLayer.hide();
                            $scope.wjBoardDetailLayer.show(true);
                        }
                    }

                    $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    $scope._popMsg("저장되었습니다.");
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
                /*else if(result.status === undefined) {
                    location.href = "/";
                }*/
                else {
                    var msg = result.status + " : " + result.message;
                    $scope._popMsg(msg);
                    $scope.$broadcast('loadingPopupInactive');
                }
            },
            cache: false,
            dataType: "json",
            contentType : 'application/json'
        });
    });

    // 첨부파일 저장
    $scope.atchSave = function(data){

        var formData = new FormData($("#boradForm")[0]);
        formData.append("boardCd", data.boardCd);
        formData.append("boardSeqNo", data.boardSeqNo);
        formData.append("userId", data.userId);

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
                    //$scope._popMsg("저장되었습니다.");
                    //$scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "FAIL") {
                    $scope._popMsg('Ajax Fail By HTTP Request');
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "SERVER_ERROR") {
                    $scope._popMsg(result.message);
                    $scope.$broadcast('loadingPopupInactive');
                }
                /*else if(result.status === undefined) {
                    location.href = "/";
                }*/
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
        //$scope._popConfirm(messages["boardInfo.delConfirmAtch"], function() {

            var params = {};
            params.boardCd = $scope.selectedBoardInfo.boardCd;
            params.boardSeqNo = $scope.selectedBoardInfo.boardSeqNo;
            params.idx = data.idx;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/adi/board/board/board/getBoardInfoAtchDel.sb", params, function(){ $scope.allSearch() });
        //});
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

    // 팝업 닫기
    $scope.close = function(){

        if($scope.selectedBoardInfo !== null && $scope.selectedBoardInfo !== "" && $scope.selectedBoardInfo !== undefined) {
            var params = {};
            params.boardCd = $scope.selectedBoardInfo.boardCd;
            params.boardSeqNo = $scope.selectedBoardInfo.boardSeqNo;
            params.userId = $scope.selectedBoardInfo.userId;

            if($scope.fullSizeYn){
                $scope._broadcast('boardDetailFullSizeCtrl', params);
            } else {
                $scope._broadcast('boardDetailCtrl', params);
            }
        }

        $scope.newForm();  //신규
        $scope.wjBoardInfoLayer.hide();

        // 저장기능 수행후 재조회
        $scope._broadcast('boardListCtrl');
    };

    // 공지사항등록 체크
    $scope.noticeYnChk = function (){
        if($scope.noticeYn == false){
            $("#emergency").css("display", "none");
            $scope.emergencyYn = false;
        } else {
            $("#emergency").css("display", "");
            $scope.emergency = true;
        }
    };

    $scope.setTargetFg = function (){
        if($scope.targetFg !== undefined) {
            console.log(partOrgnCd + " " + partOrgnNm);
            if($scope.targetFg === "6"){
                $("#boardInfoStoreCd").val(partOrgnCd);
                $("#boardInfoStoreNm").val(partOrgnNm);
            }
        }
    };

    // 글쓰기 에디터
    $(document).ready(function() {
        $('#summernote').summernote( {
            height: 230,
            width:630,
            toolbar: [
                /*['fontname', ['fontname']],*/
                ['fontsize', ['fontsize']],
                ['color', ['color']],
                ['para', ['paragraph']],
                ['height', ['height']],
                ['style', ['style']],
                ['style', ['bold', 'italic', 'underline', 'clear']],
                ['font', ['strikethrough', 'superscript', 'subscript']],
                ['insert', ['link','image', 'doc', 'video', 'table']],
                ['view', ['fullscreen', 'codeview', 'help']],
            ],
            fontSizes: [ '8', '10', '12', '14','16', '18', '24', '36'],
        });
    });

    // 첨부파일 리스트에 바인딩
    $scope.previewFile = function () {

        var fileInput = document.getElementById("file");
        var files = fileInput.files;
        var file;
        var temp;

        // 첨부파일 바인딩 전 그리드 초기화
        if($scope.selectedBoardInfo === null || $scope.selectedBoardInfo === undefined || $scope.selectedBoardInfo === ""){

            if($scope.flex.collectionView !== undefined){
                for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                    var item = $scope.flex.collectionView.items[i];

                    // 게시글에서 파일관련 내용 제거
                    var html = $('#summernote').summernote('code');
                    $('#summernote').summernote('code', html.replaceAll("<p><img src=\"" + item.tempPath + "\"></p>", ""));

                    // 객체 URL 제거
                    URL.revokeObjectURL(item.tempPath);
                }
            }

            // 신규 게시글은 리스트 전체 삭제
            $scope.gridDefault();

        }else{
            // 수정 게시글은 서버에 저장된 파일은 삭제 안되게 처리
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.idx === "") {
                    $scope.flex.collectionView.removeAt(i);

                    // 게시글에서 파일관련 내용 제거
                    var html = $('#summernote').summernote('code');
                    $('#summernote').summernote('code', html.replaceAll("<p><img src=\"" + item.tempPath + "\"></p>", ""));

                    // 객체 URL 제거
                    URL.revokeObjectURL(item.tempPath);
                }
            }
        }

        // 첨부파일 리스트에 Add
        setTimeout(function() {
            for (var i = 0; i < files.length; i++) {
                file = files[i];
                temp = URL.createObjectURL(file); // 객체 URL 생성

                // 파라미터 설정
                var params = {};
                params.orginlFileNm = file.name;
                params.del = "삭제";
                params.fileExt = file.name.split(".")[1];
                params.tempPath = temp;
                params.idx = "";

                if(params.fileExt === "png" || params.fileExt === "PNG" ||
                    params.fileExt === "jpg" || params.fileExt === "JPG" ||
                    params.fileExt === "jpeg" || params.fileExt === "JPEG" ||
                    params.fileExt === "gif" || params.fileExt === "GIF"){
                    params.imgApply = "본문적용";
                }else{
                    params.imgApply = "";
                }

                // 추가기능 수행 : 파라미터
                $scope._addRow(params);
            }
        }, 700)
    };
    
    // 본문에 이미지 적용
    $scope.addImgContents = function (data){

        var html = $('#summernote').summernote('code');

        if(data.idx === ""){ // 신규 첨부파일인 경우
            $('#summernote').summernote('code', html + "<p><img src=\"" + data.tempPath + "\"></p>");
        }else{
            $('#summernote').summernote('code', html + "<p><img src=\"" + rootUrl + "/Board/" + data.tempPath + "\"></p>");
        }

    };

    // 그리드 초기화
    $scope.gridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data     = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

// 첨부파일 리스트에 바인딩
function previewFile() {
    var scope = agrid.getScope('boardInfoCtrl');
    scope.previewFile();
}