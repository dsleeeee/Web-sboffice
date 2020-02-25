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

    // <-- 검색 호출 -->
    $scope.$on("boardInfoCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedBoardInfo(data);
            $scope.searchBoardInfo(); //수정
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
            // if($scope.boardInfo.smsYn = "Y") {
            //     $scope.smsYn = true;
            // } else if ($scope.boardInfo.smsYn = "N") {
            //     $scope.smsYn = false;
            // }
            var startDate = $scope.boardInfo.startDate.substr(0, 4) + "/" + $scope.boardInfo.startDate.substr(4, 2) + "/" + $scope.boardInfo.startDate.substr(6, 2);
            var endDate = $scope.boardInfo.endDate.substr(0, 4) + "/" + $scope.boardInfo.endDate.substr(4, 2) + "/" + $scope.boardInfo.endDate.substr(6, 2);
            $scope.startDate = startDate;
            $scope.endDate = endDate;
        });
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
        // if($scope.smsYn = true) {
        //     params.smsYn = "Y";
        // } else if($scope.smsYn = false) {
        //     params.smsYn = "N";
        // }
        params.startDate = dateToDaystring($scope.startDate).replaceAll("-","");
        params.endDate = dateToDaystring($scope.endDate).replaceAll("-","");

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
    });

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
        $scope.wjBoardInfoLayer.hide();
    };

    // 파일찾기
    $scope.findFile = function(){
       //  alert('test1111111');

        // File path = new File("D:/workspace_example/Data/");
        // File[] fileList = path.listFiles();
        //
        // if(fileList.length > 0){
        //     for(int i=0; i < fileList.length; i++){
        //         System.out.println(fileList[i]) ;
        //     }
        // }
    };

}]);