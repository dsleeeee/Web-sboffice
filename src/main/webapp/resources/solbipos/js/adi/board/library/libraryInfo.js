/****************************************************************
 *
 * 파일명 : libraryInfo.js
 * 설  명 : 자료실 신규등록,수정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2010.03.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('libraryInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('libraryInfoCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // var item = s.rows[e.row].dataItem;

                // 삭제
                if (col.binding === "del") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
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
                    var params = {};
                    params.idx = selectedRow.idx;

                    $scope.delAtch(params);
                }
            }
        });
    };


    // <-- 검색 호출 -->
    $scope.$on("libraryInfoCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedLibraryInfo(data);
            $scope.searchLibraryInfo(); //수정
        } else {
            $scope.newForm();  //신규
        }
        event.preventDefault();
    });

    // 수정
    $scope.searchLibraryInfo = function() {
        $("#libraryInfoTitle").text("자료수정");
        $("#lblStatus").text("U");
        $scope.title = $scope.selectedLibraryInfo.title;

        var params = {};
        params.boardCd = $scope.selectedLibraryInfo.boardCd;
        params.boardSeqNo = $scope.selectedLibraryInfo.boardSeqNo;

        $scope._inquiryMain("/adi/board/library/library/getLibraryInfoList.sb", params, function() {}, false);
    };

    // 신규
    $scope.newForm = function() {
        $("#libraryInfoTitle").text("자료등록");
        $("#lblStatus").text("I");

        $scope.title = "";

        $scope.setSelectedLibraryInfo(null);

        var storeScope = agrid.getScope('libraryInfoCtrl');
        storeScope._gridDataInit();   // 그리드 초기화

        // 첨부파일 리셋
        var agent = navigator.userAgent.toLowerCase();
        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
            // ie 일때
            $("#file").replaceWith( $("#file").clone(true) );
        } else {
            // other browser 일때
            $("#file").val("");
        }

        $("#libraryForm")[0].reset();
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedLibraryInfo;
    $scope.setSelectedLibraryInfo = function(store) {
        $scope.selectedLibraryInfo = store;
    };
    $scope.getSelectedLibraryInfo = function(){
        return $scope.selectedLibraryInfo;
    };

    // 저장
    $("#funcSave").click(function(e){
        if($scope.title === "") {
            $scope._popMsg(messages["libraryInfo.titleBlank"]); // 자료명을 입력해주세요
            return false;
        }

        // 이미지명 형식 체크
        if($("#file").val() !== null && $("#file").val() !== undefined && $("#file").val() !== "") {
            var imgFullNm = $("#file").val().substring($("#file").val().lastIndexOf('\\') + 1);
            if (1 > imgFullNm.lastIndexOf('.')) {
                $scope._popMsg(messages["libraryInfo.fileNmChk.msg"]);
                return;
            }
        }

        var params = {};
        // 신규, 수정
        params.status = $("#lblStatus").text();
        params.title = $scope.title;
        // 신규
        if(params.status === "I") {
            params.status = "INSERT";
            params.boardCd = boardCd;
        // 수정
        } else if (params.status === "U") {
            params.status = "UPDATE";
            params.boardCd = $scope.selectedLibraryInfo.boardCd;
            params.boardSeqNo = $scope.selectedLibraryInfo.boardSeqNo;
        }

        var formData = new FormData($("#libraryForm")[0]);
        formData.append("boardCd", params.boardCd);
        formData.append("boardSeqNo", params.boardSeqNo);
        formData.append("title", params.title);
        formData.append("status", params.status);

        var url = '/adi/board/library/library/getLibraryInfoAtchSave.sb';

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
                    $scope._popMsg("저장되었습니다.");
                    $scope.$broadcast('loadingPopupInactive');

                    $scope.close();

                    // 수정
                    if (params.status === "U") {
                        // 저장기능 수행후 재조회
                        $scope._broadcast('libraryDetailCtrl', params);
                    }
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
    });

    // 첨부파일 삭제
    $scope.delAtch = function(data){
        // 해당 자료를 삭제하시겠습니까?
        $scope._popConfirm(messages["libraryInfo.delConfirmAtch"], function() {

            var params = {};
            params.boardCd = $scope.selectedLibraryInfo.boardCd;
            params.boardSeqNo = $scope.selectedLibraryInfo.boardSeqNo;
            params.idx = data.idx;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/adi/board/library/library/getLibraryInfoAtchDel.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchLibraryInfo();
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.newForm();  //신규
        $scope.wjLibraryInfoLayer.hide();

        // 저장기능 수행후 재조회
        $scope._broadcast('libraryListCtrl');
    };

}]);