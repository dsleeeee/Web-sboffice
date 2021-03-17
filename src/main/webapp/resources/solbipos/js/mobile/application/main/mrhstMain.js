/****************************************************************
 *
 * 파일명 : mrhstMain.js
 * 설  명 : 어플리케이션 > 메인 > 내용(매장) JavaScript
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
    var scope = agrid.getScope('mrhstMainCtrl');
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
 *  메인(매장) 그리드 생성
 */
app.controller('mrhstMainCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mrhstMainCtrl', $scope, $http, true));

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

    // 매출현황 날짜
    var comboArray = [];
    var comboData  = {};
    for (var i = 0; i < dateSelList1.length; i++) {
        comboData = {};
        comboData.name  = dateSelList1[i].name;
        comboData.value = dateSelList1[i].dateStr;
        comboArray.push(comboData);
    }
    $scope._setComboData("gubunCombo1", comboArray);

    // 매출 상위 상품 날짜
    var comboArray2 = [];
    var comboData2  = {};
    for (var i = 0; i < dateSelList2.length; i++) {
        comboData2 = {};
        comboData2.name  = dateSelList2[i].name;
        comboData2.value = dateSelList2[i].dateStr;
        comboArray2.push(comboData2);
    }
    $scope._setComboData("gubunCombo2", comboArray2);

    // var loadCnt1 = 0;
    // 매출현황
    $scope.setDateSelect1 = function(s) {
        $("#dateText1").text("기간 : " + s.selectedValue);

        // 페이지로드시 display:none 하면 그래프 그려지지 않음 -> 보이게하고 jsp에서 숨김
        // if(loadCnt1 == 0) {
        //     $("#chart1").css("display", "");
        //     $("#chart2").css("display", "");
        //     $("#chart3").css("display", "");
        // } else if(s.selectedIndex == 0) {
        //     $("#chart1").css("display", "");
        //     $("#chart2").css("display", "none");
        //     $("#chart3").css("display", "none");
        // } else if(s.selectedIndex == 1) {
        //     $("#chart1").css("display", "none");
        //     $("#chart2").css("display", "");
        //     $("#chart3").css("display", "none");
        // } else if(s.selectedIndex == 2) {
        //     $("#chart1").css("display", "none");
        //     $("#chart2").css("display", "none");
        //     $("#chart3").css("display", "");
        // }
        // loadCnt1++;

        var params  = {};
        params.gubun = s.selectedIndex;

        // 매출현황 차트
        $scope._broadcast("saleStoreCtrl", params);
    };

    // var loadCnt2 = 0;
    // 매출 상위 상품
    $scope.setDateSelect2 = function(s) {
        $("#dateText2").text("기간 : " + s.selectedValue);

        // 페이지로드시 display:none 하면 그래프 그려지지 않음 -> 보이게하고 jsp에서 숨김
        // if(loadCnt2 == 0) {
        //     $("#chart4").css("display", "");
        //     $("#chart5").css("display", "");
        //     $("#chart6").css("display", "");
        // } else if(s.selectedIndex == 0) {
        //     $("#chart4").css("display", "");
        //     $("#chart5").css("display", "none");
        //     $("#chart6").css("display", "none");
        // } else if(s.selectedIndex == 1) {
        //     $("#chart4").css("display", "none");
        //     $("#chart5").css("display", "");
        //     $("#chart6").css("display", "none");
        // } else if(s.selectedIndex == 2) {
        //     $("#chart4").css("display", "none");
        //     $("#chart5").css("display", "none");
        //     $("#chart6").css("display", "");
        // }
        // loadCnt2++;

        var params  = {};
        params.gubun = s.selectedIndex;

        // 매출 상위 상품 차트
        $scope._broadcast("saleProdStoreCtrl", params);
    };

    // 가상로그인시 창 크기변경하면 그래프 그려지지 않음
    // $(window).on('resize', function(){
    //     $("#chart1").css("display", "");
    //     $("#chart2").css("display", "");
    //     $("#chart3").css("display", "");
    //     $("#chart4").css("display", "");
    //     $("#chart5").css("display", "");
    //     $("#chart6").css("display", "");
    //
    //     setTimeout(function() {
    //         if($scope._gubunCombo1.selectedIndex == 0) {
    //             $("#chart1").css("display", "");
    //             $("#chart2").css("display", "none");
    //             $("#chart3").css("display", "none");
    //         } else if($scope._gubunCombo1.selectedIndex == 1) {
    //             $("#chart1").css("display", "none");
    //             $("#chart2").css("display", "");
    //             $("#chart3").css("display", "none");
    //         } else if($scope._gubunCombo1.selectedIndex == 2) {
    //             $("#chart1").css("display", "none");
    //             $("#chart2").css("display", "none");
    //             $("#chart3").css("display", "");
    //         }
    //
    //         if($scope._gubunCombo2.selectedIndex == 0) {
    //             $("#chart4").css("display", "");
    //             $("#chart5").css("display", "none");
    //             $("#chart6").css("display", "none");
    //         } else if($scope._gubunCombo2.selectedIndex == 1) {
    //             $("#chart4").css("display", "none");
    //             $("#chart5").css("display", "");
    //             $("#chart6").css("display", "none");
    //         } else if($scope._gubunCombo2.selectedIndex == 2) {
    //             $("#chart4").css("display", "none");
    //             $("#chart5").css("display", "none");
    //             $("#chart6").css("display", "");
    //         }
    //     }, 30)
    // });

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


/**
 *  매출현황 차트 생성
 */
app.controller('saleStoreCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('saleStoreCtrl', $scope, $http, $timeout, true));

    // 메인그리드 조회후 상세그리드 조회.
    $scope.initChart = function(s, args){
        s.plotMargin = 'auto auto 50 auto';
        s.axisX.labelAngle = 0;
        //s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;
        s.legend.position = wijmo.chart.Position.None; // 그래프 좌측 항목나열한거 제거
        s.plotMargin = 'NaN 40 NaN 100'; // top, right, bottom, left

        var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
            animationMode: wijmo.chart.animation.AnimationMode.All,
            easing: wijmo.chart.animation.Easing.Linear,
            duration: 400
        });
    };

    $scope.$on("saleStoreCtrl", function (event, data) {
        $scope.searchSaleStore(data);
    });

    // 매출현황 리스트 조회
    $scope.searchSaleStore = function (data) {
        // 파라미터
        var params = {};

        // 일별(1주)
        if(data.gubun == "0") {
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/sale/main/mainContext/mainContext/getSaleWeekList.sb", params);

            // 요일별(1개월
        } else if(data.gubun == "1") {
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/sale/main/mainContext/mainContext/getSaleMonthList.sb", params);

            // 월별(1년)
        } else if(data.gubun == "2") {
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/sale/main/mainContext/mainContext/getSaleYearList.sb", params);
        }
    };

}]);


/**
 *  매출 상위 상품 차트 생성
 */
app.controller('saleProdStoreCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('saleProdStoreCtrl', $scope, $http, $timeout, true));

    // 메인그리드 조회후 상세그리드 조회.
    $scope.initChart = function(s, args){
        s.plotMargin = 'auto auto 50 auto';
        s.axisX.labelAngle = 0;
        //s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;
        s.legend.position = wijmo.chart.Position.None; // 그래프 좌측 항목나열한거 제거
        s.plotMargin = 'NaN 40 NaN 100'; // top, right, bottom, left

        var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
            animationMode: wijmo.chart.animation.AnimationMode.All,
            easing: wijmo.chart.animation.Easing.Linear,
            duration: 400
        });
    };

    $scope.$on("saleProdStoreCtrl", function (event, data) {
        $scope.searchSaleProdStore(data);
    });

    // 매출 상위 상품 리스트 조회
    $scope.searchSaleProdStore = function (data) {
        // 파라미터
        var params = {};

        // 오늘
        if(data.gubun == "0") {
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/sale/main/mainContext/mainContext/getSaleProdDayList.sb", params);

            // 1주일
        } else if(data.gubun == "1") {
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/sale/main/mainContext/mainContext/getSaleProdWeekList.sb", params);

            // 1개월
        } else if(data.gubun == "2") {
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/sale/main/mainContext/mainContext/getSaleProdMonthList.sb", params);
        }
    };

}]);