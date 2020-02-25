/****************************************************************
 *
 * 파일명 : boardReadingHist.js
 * 설  명 : 열람자목록 상세팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2010.02.19     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('boardReadingHistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('boardReadingHistCtrl', $scope, $http, true));

    // 열람자 권한
    var userAuth = "";
    $scope.totYn = true;
    if(orgnFg == "HQ"){
        userAuth = "allHQ"; // 본사로 로그인시 전체 체크박스
    } else if(orgnFg == "STORE") {
        userAuth = "STORE"; // 매장으로 로그인시 매장만 체크
    }else {
        userAuth = "all"; // 시스템,대리점 로그인시 전체 체크박스
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.authGrpNmDataMap = new wijmo.grid.DataMap(authGrpNmData, 'value', 'name'); //업체구분
        
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("boardReadingHistCtrl", function(event, data) {
        $("#lblBoardCd").text(data.boardCd);
        $("#lblBoardSeqNo").text(data.boardSeqNo);
        $scope.searchBoardReadingHist();
        event.preventDefault();
    });

    $scope.searchBoardReadingHist = function() {
        var params = {};
        params.boardCd = $("#lblBoardCd").text();
        params.boardSeqNo = $("#lblBoardSeqNo").text();
        params.userNm = $scope.userNm;
        params.authGrpNm = userAuth;

        $scope._inquirySub("/adi/board/board/board/getBoardReadingHistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $scope.wjBoardReadingHistLayer.hide();
    };

    // <-- 체크박스 이벤트 -->
    // 전체
    $scope.totYnChk = function(){
        $scope.systemYn = false;
        $scope.agencyYn = false;
        $scope.hqOfficeYn = false;
        $scope.storeYn = false;
        if(orgnFg == "HQ"){
            userAuth = "allHQ"; // 본사로 로그인시 전체 체크박스
        } else if(orgnFg == "STORE") {
            userAuth = "STORE"; // 매장으로 로그인시 매장만 체크
        }else {
            userAuth = "all"; // 시스템,대리점 로그인시 전체 체크박스
        }
    };
    // 시스템
    $scope.systemYnChk = function(){
        $scope.totYn = false;
        $scope.agencyYn = false;
        $scope.hqOfficeYn = false;
        $scope.storeYn = false;
        userAuth = "SYSTEM"; // M
    };
    // 대리점
    $scope.agencyYnChk = function(){
        $scope.totYn = false;
        $scope.systemYn = false;
        $scope.hqOfficeYn = false;
        $scope.storeYn = false;
        userAuth = "AGENCY"; // A
    };
    // 본사
    $scope.hqOfficeYnChk = function(){
        $scope.totYn = false;
        $scope.systemYn = false;
        $scope.agencyYn = false;
        $scope.storeYn = false;
        userAuth = "HQ"; // H
    };
    // 매장
    $scope.storeYnChk = function(){
        $scope.totYn = false;
        $scope.systemYn = false;
        $scope.agencyYn = false;
        $scope.hqOfficeYn = false;
        userAuth = "STORE"; // S
    };
    // <-- 체크박스 이벤트 -->

}]);