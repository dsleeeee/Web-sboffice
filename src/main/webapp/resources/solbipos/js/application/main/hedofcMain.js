/****************************************************************
 *
 * 파일명 : hedofcMain.js
 * 설  명 : 어플리케이션 > 메인 > 내용(본사) JavaScript
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
function noticeDetail(boardSeqNo) {
    var scope = agrid.getScope('hedofcMainCtrl');
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
 *  메인(본사) 그리드 생성
 */
app.controller('hedofcMainCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hedofcMainCtrl', $scope, $http, true));

    // 총 매장수
    $("#totalStoreCnt").text(storeCntList[0].storeCnt1 + ' /' + storeCntList[0].storeCntTotal);

    // 총 포스수
    $("#totalPosCnt").text(posCntList[0].posCntTotal);

    // 날짜
    var comboArray = [];
    var comboData  = {};
    for (var i = 0; i < dateSelList1.length; i++) {
        comboData = {};
        comboData.name  = dateSelList1[i].name;
        comboData.value = dateSelList1[i].dateStr;
        comboArray.push(comboData);
    }
    // 매출현황
    $scope._setComboData("gubunCombo1", comboArray);

    // 날짜
    var comboArray2 = [];
    var comboData2  = {};
    for (var i = 0; i < dateSelList2.length; i++) {
        comboData2 = {};
        comboData2.name  = dateSelList2[i].name;
        comboData2.value = dateSelList2[i].dateStr;
        comboArray2.push(comboData2);
    }
    // 매출 상위 상품
    $scope._setComboData("gubunCombo2", comboArray2);
    // 매출 상위 가맹점
    $scope._setComboData("gubunCombo3", comboArray2);

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
        $scope._broadcast("saleHqCtrl", params);
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
        $scope._broadcast("saleProdHqCtrl", params);
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

    // 매출 상위 가맹점
    $scope.setDateSelect3 = function(s){
        $("#dateText3").text("기간 : " + s.selectedValue);

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

        var saleStoreList;
        if(s.selectedIndex == 0) {
            saleStoreList = saleStoreDayList;
        } else if(s.selectedIndex == 1) {
            saleStoreList = saleStoreWeekList;
        } else if(s.selectedIndex == 2) {
            saleStoreList = saleStoreMonthList;
        }

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
    };

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
app.controller('saleHqCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('saleHqCtrl', $scope, $http, $timeout, true));

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

    $scope.$on("saleHqCtrl", function (event, data) {
        $scope.searchSaleHq(data);
    });

    // 매출현황 리스트 조회
    $scope.searchSaleHq = function (data) {
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
app.controller('saleProdHqCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('saleProdHqCtrl', $scope, $http, $timeout, true));

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

    $scope.$on("saleProdHqCtrl", function (event, data) {
        $scope.searchSaleProdHq(data);
    });

    // 매출 상위 상품 리스트 조회
    $scope.searchSaleProdHq = function (data) {
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