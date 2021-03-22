/****************************************************************
 *
 * 파일명 : systemMain.js
 * 설  명 : 어플리케이션 > 메인 > 내용(시스템) JavaScript
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
    var scope = agrid.getScope('systemMainCtrl');
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
 *  메인(시스템) 그리드 생성
 */
app.controller('systemMainCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('systemMainCtrl', $scope, $http, true));

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

    // 주간 POS 설치 상위 대리점
    $("#lblOrgnFg1").text("-");
    $("#lblAgencyNm1").text("-");
    $("#lblInstStoreCntNew1").text("-");
    $("#lblStoreCntClose1").text("-");
    $("#lblOrgnFg2").text("-");
    $("#lblAgencyNm2").text("-");
    $("#lblInstStoreCntNew2").text("-");
    $("#lblStoreCntClose2").text("-");
    $("#lblOrgnFg3").text("-");
    $("#lblAgencyNm3").text("-");
    $("#lblInstStoreCntNew3").text("-");
    $("#lblStoreCntClose3").text("-");
    $("#lblOrgnFg4").text("-");
    $("#lblAgencyNm4").text("-");
    $("#lblInstStoreCntNew4").text("-");
    $("#lblStoreCntClose4").text("-");
    $("#lblOrgnFg5").text("-");
    $("#lblAgencyNm5").text("-");
    $("#lblInstStoreCntNew5").text("-");
    $("#lblStoreCntClose5").text("-");
    for(var i=0; i<weekPosInstTopList.length; i++) {
        if(i === 0) {
            $("#lblOrgnFg1").text(weekPosInstTopList[i].orgnFg);
            $("#lblAgencyNm1").text(weekPosInstTopList[i].agencyNm);
            $("#lblInstStoreCntNew1").text(weekPosInstTopList[i].instStoreCntNew);
            $("#lblStoreCntClose1").text(weekPosInstTopList[i].storeCntClose);
        } else if(i == 1) {
            $("#lblOrgnFg2").text(weekPosInstTopList[i].orgnFg);
            $("#lblAgencyNm2").text(weekPosInstTopList[i].agencyNm);
            $("#lblInstStoreCntNew2").text(weekPosInstTopList[i].instStoreCntNew);
            $("#lblStoreCntClose2").text(weekPosInstTopList[i].storeCntClose);
        } else if(i == 2) {
            $("#lblOrgnFg3").text(weekPosInstTopList[i].orgnFg);
            $("#lblAgencyNm3").text(weekPosInstTopList[i].agencyNm);
            $("#lblInstStoreCntNew3").text(weekPosInstTopList[i].instStoreCntNew);
            $("#lblStoreCntClose3").text(weekPosInstTopList[i].storeCntClose);
        } else if(i == 3) {
            $("#lblOrgnFg4").text(weekPosInstTopList[i].orgnFg);
            $("#lblAgencyNm4").text(weekPosInstTopList[i].agencyNm);
            $("#lblInstStoreCntNew4").text(weekPosInstTopList[i].instStoreCntNew);
            $("#lblStoreCntClose4").text(weekPosInstTopList[i].storeCntClose);
        } else if(i == 4) {
            $("#lblOrgnFg5").text(weekPosInstTopList[i].orgnFg);
            $("#lblAgencyNm5").text(weekPosInstTopList[i].agencyNm);
            $("#lblInstStoreCntNew5").text(weekPosInstTopList[i].instStoreCntNew);
            $("#lblStoreCntClose5").text(weekPosInstTopList[i].storeCntClose);
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