/****************************************************************
 *
 * 파일명 : mobileSystemMain.js
 * 설  명 : (모바일) 어플리케이션 > 메인 > 내용(시스템) JavaScript
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
    var scope = agrid.getScope('mobileSystemMainCtrl');
    scope.noticeDetailPopup(boardSeqNo);
}

/**
 *  메인(시스템) 그리드 생성
 */
app.controller('mobileSystemMainCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileSystemMainCtrl', $scope, $http, false));

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
    $("#lblAgencyNm1").text("-");
    $("#lblInstStoreCntNew1").text("-");
    $("#lblStoreCntClose1").text("-");
    $("#lblAgencyNm2").text("-");
    $("#lblInstStoreCntNew2").text("-");
    $("#lblStoreCntClose2").text("-");
    $("#lblAgencyNm3").text("-");
    $("#lblInstStoreCntNew3").text("-");
    $("#lblStoreCntClose3").text("-");
    $("#lblAgencyNm4").text("-");
    $("#lblInstStoreCntNew4").text("-");
    $("#lblStoreCntClose4").text("-");
    $("#lblAgencyNm5").text("-");
    $("#lblInstStoreCntNew5").text("-");
    $("#lblStoreCntClose5").text("-");
    for(var i=0; i<weekPosInstTopList.length; i++) {
        if(i === 0) {
            $("#lblAgencyNm1").text(weekPosInstTopList[i].agencyNm);
            $("#lblInstStoreCntNew1").text(weekPosInstTopList[i].instStoreCntNew);
            $("#lblStoreCntClose1").text(weekPosInstTopList[i].storeCntClose);
        } else if(i == 1) {
            $("#lblAgencyNm2").text(weekPosInstTopList[i].agencyNm);
            $("#lblInstStoreCntNew2").text(weekPosInstTopList[i].instStoreCntNew);
            $("#lblStoreCntClose2").text(weekPosInstTopList[i].storeCntClose);
        } else if(i == 2) {
            $("#lblAgencyNm3").text(weekPosInstTopList[i].agencyNm);
            $("#lblInstStoreCntNew3").text(weekPosInstTopList[i].instStoreCntNew);
            $("#lblStoreCntClose3").text(weekPosInstTopList[i].storeCntClose);
        } else if(i == 3) {
            $("#lblAgencyNm4").text(weekPosInstTopList[i].agencyNm);
            $("#lblInstStoreCntNew4").text(weekPosInstTopList[i].instStoreCntNew);
            $("#lblStoreCntClose4").text(weekPosInstTopList[i].storeCntClose);
        } else if(i == 4) {
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