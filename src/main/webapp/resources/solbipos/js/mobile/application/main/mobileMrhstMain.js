/****************************************************************
 *
 * 파일명 : mobileMrhstMain.js
 * 설  명 : (모바일) 어플리케이션 > 메인 > 내용(매장) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.27     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 공지사항 상세
function noticeDetail(boardSeqNo){
    var scope = agrid.getScope('mobileMrhstMainCtrl');
    scope.noticeDetailPopup(boardSeqNo);
}

/**
 *  메인(매장) 그리드 생성
 */
app.controller('mobileMrhstMainCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMrhstMainCtrl', $scope, $http, false));

    // 오늘의 매출건수
    $("#totalSaleCnt").text(daySaleCntList[0].realSaleCntTotal);
    $("#cardSaleCnt").text(daySaleCntList[0].cardCnt);
    $("#cashSaleCnt").text(daySaleCntList[0].cashCnt);
    $("#etcSaleCnt").text(daySaleCntList[0].etcCnt);

    // 오늘의 매출금액
    $("#totalSaleAmt").text(daySaleAmtList[0].realSaleAmtTotal);
    $("#cardSaleAmt").text(daySaleAmtList[0].cardAmt);
    $("#cashSaleAmt").text(daySaleAmtList[0].cashAmt);
    $("#etcSaleAmt").text(daySaleAmtList[0].etcAmt);

    // 7일 전 날짜 구하기
    var startDt = new Date();
    var startDiffDay = startDt.getTime() - (7 * 24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨
    var startNewDate = new Date();
    startNewDate.setTime(startDiffDay);
    // 7일 전 날짜 만들기
    var beforeDt = wijmo.Globalize.format(startNewDate, 'yyyyMMdd');
    var beforeYear = beforeDt.substring(0,4);
    var beforeMonth = beforeDt.substring(4,6);
    var beforeDay = beforeDt.substring(6,8);
    beforeDt = beforeYear + "-" + beforeMonth + "-" + beforeDay; // 시작날짜

    // 현재날짜
    var date = wijmo.Globalize.format(getToday(), 'yyyyMMdd');
    var afterYear = date.substring(0,4);
    var afterMonth = date.substring(4,6);
    var afterDay = date.substring(6,8);
    date = afterYear + "-" + afterMonth + "-" + afterDay; // 종료날짜

    // 매출현황
    $("#dateText1").text("기간 : " + beforeDt + "~" + date);
    // 매출 상위 상품
    $("#dateText2").text("기간 : " + beforeDt + "~" + date);

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
        $scope.wjMobileBoardDetailLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 게시판 상세 팝업 핸들러 추가
        $scope.wjMobileBoardDetailLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('mobileBoardDetailCtrl', $scope.getSelectedNotice());
            }, 50)
        });
    });

}]);