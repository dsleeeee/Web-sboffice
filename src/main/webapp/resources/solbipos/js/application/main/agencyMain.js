/****************************************************************
 *
 * 파일명 : agencyMain.js
 * 설  명 : 어플리케이션 > 메인 > 내용(총판/대리점) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.01.25     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 공지사항 상세
function noticeDetail(boardSeqNo){
    var scope = agrid.getScope('agencyMainCtrl');
    scope.noticeDetailPopup(boardSeqNo);
}

// 공지사항 more
function boardMove(){
    if(board_auth == "Y") {
        // 가상로그인
        if(document.getElementsByName('sessionId')[0]) {
            var vSessionId = document.getElementsByName('sessionId')[0].value;
            location.href = '/adi/board/board/01/list.sb?sid='+ vSessionId;
            // 로그인
        } else {
            location.href = '/adi/board/board/01/list.sb';
        }
    } else {
        var msg = "메뉴 권한이 없습니다.";
        s_alert.pop(msg);
        return;
    }
}

/**
 *  메인(총판/대리점) 그리드 생성
 */
app.controller('agencyMainCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('agencyMainCtrl', $scope, $http, true));

    // 총 매장수
    $("#totalStoreCnt").text(storeCntList[0].storeCntTotal);
    $("#openStoreCnt").text(storeCntList[0].storeCnt1);
    $("#closeStoreCnt").text(storeCntList[0].storeCnt2);
    $("#stopStoreCnt").text(storeCntList[0].storeCnt3);
    $("#demoStoreCnt").text(storeCntList[0].storeCnt9);

    // 총 포스수
    $("#totalPosCnt").text(posCntList[0].posCntTotal);
    $("#openPosCnt").text(posCntList[0].posCnt1);
    $("#closePosCnt").text(posCntList[0].posCnt2);
    $("#stopPosCnt").text(posCntList[0].posCnt3);
    $("#demoPosCnt").text(posCntList[0].posCnt9);

    // 주간 매출 상위 가맹점
    $("#lblClsFg1").text("-");
    $("#lblStoreNm1").text("-");
    $("#lblSaleCnt1").text("-");
    $("#lblClsFg2").text("-");
    $("#lblStoreNm2").text("-");
    $("#lblSaleCnt2").text("-");
    $("#lblClsFg3").text("-");
    $("#lblStoreNm3").text("-");
    $("#lblSaleCnt3").text("-");
    $("#lblClsFg4").text("-");
    $("#lblStoreNm4").text("-");
    $("#lblSaleCnt4").text("-");
    $("#lblClsFg5").text("-");
    $("#lblStoreNm5").text("-");
    $("#lblSaleCnt5").text("-");
    for(var i=0; i<weekSaleAgencyTopList.length; i++) {
        if(i === 0) {
            $("#lblClsFg1").text(weekSaleAgencyTopList[i].clsFg);
            $("#lblStoreNm1").text(weekSaleAgencyTopList[i].storeNm);
            $("#lblSaleCnt1").text(weekSaleAgencyTopList[i].saleCnt);
        } else if(i == 1) {
            $("#lblClsFg2").text(weekSaleAgencyTopList[i].clsFg);
            $("#lblStoreNm2").text(weekSaleAgencyTopList[i].storeNm);
            $("#lblSaleCnt2").text(weekSaleAgencyTopList[i].saleCnt);
        } else if(i == 2) {
            $("#lblClsFg3").text(weekSaleAgencyTopList[i].clsFg);
            $("#lblStoreNm3").text(weekSaleAgencyTopList[i].storeNm);
            $("#lblSaleCnt3").text(weekSaleAgencyTopList[i].saleCnt);
        } else if(i == 3) {
            $("#lblClsFg4").text(weekSaleAgencyTopList[i].clsFg);
            $("#lblStoreNm4").text(weekSaleAgencyTopList[i].storeNm);
            $("#lblSaleCnt4").text(weekSaleAgencyTopList[i].saleCnt);
        } else if(i == 4) {
            $("#lblClsFg5").text(weekSaleAgencyTopList[i].clsFg);
            $("#lblStoreNm5").text(weekSaleAgencyTopList[i].storeNm);
            $("#lblSaleCnt5").text(weekSaleAgencyTopList[i].saleCnt);
        }
    }

    // 선택 공지사항
    $scope.selectedNotice;
    $scope.setSelectedNotice = function(store) {
        $scope.selectedNotice = store;
    };
    $scope.getSelectedNotice = function(){
        return $scope.selectedNotice;
    };

    // 공지사항 상세 팝업
    $scope.noticeDetailPopup = function(boardSeqNo){
        var param = {};
        param.boardCd = "01";
        param.boardSeqNo = boardSeqNo;
        param.userId = "PASS"; // 읽기만가능(PASS 명칭 의미없음)
        $scope.setSelectedNotice(param);
        $scope.wjBoardDetailLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 게시판 상세 팝업 핸들러 추가
        $scope.wjBoardDetailLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('boardDetailCtrl', $scope.getSelectedNotice());
            }, 50)
        });
    });

}]);