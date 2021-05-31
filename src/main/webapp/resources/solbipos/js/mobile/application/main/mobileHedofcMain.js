/****************************************************************
 *
 * 파일명 : mobileHedofcMain.js
 * 설  명 : (모바일) 어플리케이션 > 메인 > 내용(본사) JavaScript
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
    var scope = agrid.getScope('mobileHedofcMainCtrl');
    scope.noticeDetailPopup(boardSeqNo);
}

/**
 *  메인(본사) 그리드 생성
 */
app.controller('mobileHedofcMainCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileHedofcMainCtrl', $scope, $http, false));

    // 총 매장수
    $("#totalStoreCnt").text(storeCntList[0].storeCntTotal);

    // 총 포스수
    $("#totalPosCnt").text(posCntList[0].posCntTotal);

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
    // 매출 상위 가맹점
    $("#dateText3").text("기간 : " + beforeDt + "~" + date);

    // 매출 상위 가맹점
    $("#lblStoreNm1").text("-");
    $("#lblRealSaleAmt1").text("-");
    $("#lblStoreNm2").text("-");
    $("#lblRealSaleAmt2").text("-");
    $("#lblStoreNm3").text("-");
    $("#lblRealSaleAmt3").text("-");
    $("#lblStoreNm4").text("-");
    $("#lblRealSaleAmt4").text("-");
    $("#lblStoreNm5").text("-");
    $("#lblRealSaleAmt5").text("-");
    $("#lblStoreNm6").text("-");
    $("#lblRealSaleAmt6").text("-");
    $("#lblStoreNm7").text("-");
    $("#lblRealSaleAmt7").text("-");
    $("#lblStoreNm8").text("-");
    $("#lblRealSaleAmt8").text("-");
    $("#lblStoreNm9").text("-");
    $("#lblRealSaleAmt9").text("-");
    $("#lblStoreNm10").text("-");
    $("#lblRealSaleAmt10").text("-");

    var saleStoreList = saleStoreWeekList;
    for(var i=0; i<saleStoreList.length; i++) {
        if(i === 0) {
            $("#lblStoreNm1").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt1").text(saleStoreList[i].realSaleAmt);
        } else if(i == 1) {
            $("#lblStoreNm2").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt2").text(saleStoreList[i].realSaleAmt);
        } else if(i == 2) {
            $("#lblStoreNm3").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt3").text(saleStoreList[i].realSaleAmt);
        } else if(i == 3) {
            $("#lblStoreNm4").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt4").text(saleStoreList[i].realSaleAmt);
        } else if(i == 4) {
            $("#lblStoreNm5").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt5").text(saleStoreList[i].realSaleAmt);
        } else if(i == 5) {
            $("#lblStoreNm6").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt6").text(saleStoreList[i].realSaleAmt);
        } else if(i == 6) {
            $("#lblStoreNm7").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt7").text(saleStoreList[i].realSaleAmt);
        } else if(i == 7) {
            $("#lblStoreNm8").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt8").text(saleStoreList[i].realSaleAmt);
        } else if(i == 8) {
            $("#lblStoreNm9").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt9").text(saleStoreList[i].realSaleAmt);
        } else if(i == 9) {
            $("#lblStoreNm10").text(saleStoreList[i].storeNm);
            $("#lblRealSaleAmt10").text(saleStoreList[i].realSaleAmt);
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
