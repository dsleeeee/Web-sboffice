/****************************************************************
 *
 * 파일명 : dailyTableReportKwu.js
 * 설  명 : 일일일계표2 인쇄 (광운대 아이스링크) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.10.05     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일일일계표 인쇄 팝업 조회 그리드 생성
 */
app.controller('dailyTableReportKwuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dailyTableReportKwuCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("dailyTableReportKwuCtrl", function(event, data) {
        $scope.printNum = data.printNum;
        $scope.startDate = data.startDate;
        $scope.storeCd = data.storeCd;

        if($scope.printNum === "1") {
            // dailyTableKwuReport html 내용 초기화
            $("#dailyTableKwuReport").html('');

            $("#divPrintNum1").css("display", "");
            $("#divPrintNum2").css("display", "none");

            $scope.searchDailyTableReportKwu();

        } else if($scope.printNum === "2") {
            // dailyTableKwuReport html 내용 초기화
            $("#dailyTableKwuReport2").html('');

            $("#divPrintNum1").css("display", "none");
            $("#divPrintNum2").css("display", "");

            $scope.searchDailyTableReportKwu2();
        }

        event.preventDefault();
    });
    // <-- //검색 호출 -->


    // <-- 첫째 장 인쇄 -->
    $scope.searchDailyTableReportKwu = function(){
        var params = {};
        params.startDate = $scope.startDate;
        params.storeCd = $scope.storeCd;

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//'데이터 처리 중입니다.' 시작	//cmm.progress=데이터 처리 중입니다.

        $scope._postJSONQuery.withOutPopUp ( "/excclc/excclc/dailyTableKwu/dailyTableKwu/getDailyTableKwuList1.sb",	//영업일보 구성 조회
            params,
            function(response)	{
                //데이터 setting > 영업일보
                $scope.dailyTableKwuCtrl_courseStatus = response.data.data.courseStatus; // 수강현황
                $scope.dailyTableKwuCtrl_courseType = response.data.data.courseType; // 수강유형
                $scope.dailyTableKwuCtrl_tuition1 = response.data.data.tuition1; // 수강료현황
                $scope.dailyTableKwuCtrl_tuition2 = response.data.data.tuition2; // 수강료현황
                $scope.dailyTableKwuCtrl_groupCourse = response.data.data.groupCourse; // 단체수강내역

                $scope.$broadcast('loadingPopupInactive');	//'데이터 처리 중입니다.' 중지

                // report html 생성
                $scope.reportRender();
            }, false);
    };

    // report html 생성
    $scope.reportRender = function () {
        var dailyTableKwuHtml = '';
        var nextPageHtml  = '<p class="nextPage mt5"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html


        // 최상단 html
        var titleHtml = '<table class="w100 mt40">'
            + '<colgroup>'
            + '<col style="width: 30%">'
            + '<col style="width: 40%">'
            + '<col style="width: 30%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td class="tc br0"></td>'
            + '<td class="tc" style="background-color: lightgoldenrodyellow;"><p class="s25 bk">일일일계표</p></td>'
            + '<td class="tc br0"></td>'
            + '</tr>'
            + '</table>';


        // 최상단 html
        var infoHtml = '<table class="w100 mt10">'
            + '<colgroup>'
            + '<col style="width: 25%">'
            + '<col style="width: 45%">'
            + '<col style="width: 10%">'
            + '<col style="width: 10%">'
            + '<col style="width: 10%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td rowspan="3" class="tl br0"><p class="s18 bk">광운대학교<br><br>일 자 : ' + $scope.startDate.substr(0, 4) + '-' + $scope.startDate.substr(4, 2) + '-' + $scope.startDate.substr(6, 2) + '</p></td>'
            + '<td rowspan="3" class="tc br0"></td>'
            + '<td class="tc">담당</td>'
            + '<td class="tc">팀장</td>'
            + '<td class="tc">관장</td>'
            + '</tr>'
            + '<tr class="h25">'
            + '<td rowspan="2" class="tc"></td>'
            + '<td rowspan="2" class="tc"></td>'
            + '<td rowspan="2" class="tc"></td>'
            + '</tr>'
            + '<tr class="h25">'
            + '</tr>'
            + '</table>';


        // 수강현황 리스트 HTML 생성
        var courseStatusListHtml = '';
        var courseStatusListHeaderHtml = '';
        courseStatusListHeaderHtml = '<table class="w100 mt5">' // 수강현황 header html
            + '<colgroup>'
            + '<col style="width: 15%">'
            + '<col style="width: 70%">'
            + '<col style="width: 15%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td class="tl br0 s15">1. 수강현황</td>'
            + '<td class="tc br0"></td>'
            + '<td class="tr br0 s15">[ VAT 포함 ]</td>'
            + '</tr>'
            + '</table>';

        courseStatusListHeaderHtml += '<table class="w100">' // 수강현황 header html
            + '<colgroup>'
            + '<col style="width:15%;">'
            + '<col style="width:17%;">'
            + '<col style="width:17%;">'
            + '<col style="width:17%;">'
            + '<col style="width:17%;">'
            + '<col style="width:17%;">'
            + '</colgroup>'
            + '<tr class="h30">'
            + '<th class="tc" rowspan="2" style="background-color: lightgrey;">구분</th>'
            + '<th class="tc" colspan="5" style="background-color: lightgrey;">수강현황</th>'
            + '</tr>'
            + '<tr class="h30">'
            + '<th class="tc" style="background-color: lightgrey;">총수강료</th>'
            + '<th class="tc" style="background-color: lightgrey;">수강생수</th>'
            + '<th class="tc" style="background-color: lightgrey;">할인수강료</th>'
            + '<th class="tc" style="background-color: lightgrey;">순수강료</th>'
            + '<th class="tc" style="background-color: lightgrey;">정규수강료</th>'
            + '</tr>';

        for (var i = 0; i < $scope.dailyTableKwuCtrl_courseStatus.length; i++) {
            // 금액표시(,)
            var item = $scope.dailyTableKwuCtrl_courseStatus[i];
            var totTuition = (item.totTuition === undefined || item.totTuition == null || item.totTuition.length <= 0) ? nvl(item.totTuition,0) : addComma(item.totTuition);
            var studentCnt = (item.studentCnt === undefined || item.studentCnt == null || item.studentCnt.length <= 0) ? nvl(item.studentCnt,0) : addComma(item.studentCnt);
            var dcTuition = (item.dcTuition === undefined || item.dcTuition == null || item.dcTuition.length <= 0) ? nvl(item.dcTuition,0) : addComma(item.dcTuition);
            var netTuition = (item.netTuition === undefined || item.netTuition == null || item.netTuition.length <= 0) ? nvl(item.netTuition,0) : addComma(item.netTuition);
            var tuition = (item.tuition === undefined || item.tuition == null || item.tuition.length <= 0) ? nvl(item.tuition,0) : addComma(item.tuition);
            courseStatusListHtml += '<tr class="h25">'
                + '<td class="tc" style="background-color: lightgrey;">' + item.fg + '</td>'
                + '<td class="tr">' + totTuition + '</td>'
                + '<td class="tr">' + studentCnt + '</td>'
                + '<td class="tr">' + dcTuition + '</td>'
                + '<td class="tr">' + netTuition + '</td>'
                + '<td class="tr">' + tuition + '</td>'
                + '</tr>';
        }

        courseStatusListHtml += '</table>';


        // 수강유형 리스트 HTML 생성
        var courseTypeListHtml = '';
        var courseTypeListHeaderHtml = '';
        courseTypeListHeaderHtml = '<table class="w100 mt5">' // 수강유형 header html
            + '<colgroup>'
            + '<col style="width: 15%">'
            + '<col style="width: 85%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td class="tl br0 s15">2. 수강유형</td>'
            + '<td class="tc br0"></td>'
            + '</tr>'
            + '</table>';

        courseTypeListHeaderHtml += '<table class="w100">' // 수강유형 header html
            + '<colgroup>'
            + '<col style="width:15%;">'
            + '<col style="width:10%;">'
            + '<col style="width:10%;">'
            + '<col style="width:10%;">'
            + '<col style="width:11%;">'
            + '<col style="width:11%;">'
            + '<col style="width:11%;">'
            + '<col style="width:11%;">'
            + '<col style="width:11%;">'
            + '</colgroup>'
            + '<tr class="h30">'
            + '<th class="tc" style="background-color: lightgrey;">구분</th>'
            + '<th class="tc" style="background-color: lightgrey;">현금</th>'
            + '<th class="tc" style="background-color: lightgrey;">카드</th>'
            + '<th class="tc" style="background-color: lightgrey;">일수량</th>'
            + '<th class="tc" style="background-color: lightgrey;">일계</th>'
            + '<th class="tc" style="background-color: lightgrey">월수량</th>'
            + '<th class="tc" style="background-color: lightgrey;">금월계</th>'
            + '<th class="tc" style="background-color: lightgrey;">전월누계</th>'
            + '<th class="tc" style="background-color: lightgrey;">총계</th>'
            + '</tr>';

        for (var i = 0; i < $scope.dailyTableKwuCtrl_courseType.length; i++) {
            // 금액표시(,)
            var item = $scope.dailyTableKwuCtrl_courseType[i];
            var cashAmt = (item.cashAmt === undefined || item.cashAmt == null || item.cashAmt.length <= 0) ? nvl(item.cashAmt,0) : addComma(item.cashAmt);
            var cardAmt = (item.cardAmt === undefined || item.cardAmt == null || item.cardAmt.length <= 0) ? nvl(item.cardAmt,0) : addComma(item.cardAmt);
            var daySaleQty = (item.daySaleQty === undefined || item.daySaleQty == null || item.daySaleQty.length <= 0) ? nvl(item.daySaleQty,0) : addComma(item.daySaleQty);
            var daily = (item.daily === undefined || item.daily == null || item.daily.length <= 0) ? nvl(item.daily,0) : addComma(item.daily);
            var monthSaleQty = (item.monthSaleQty === undefined || item.monthSaleQty == null || item.monthSaleQty.length <= 0) ? nvl(item.monthSaleQty,0) : addComma(item.monthSaleQty);
            var monthly = (item.monthly === undefined || item.monthly == null || item.monthly.length <= 0) ? nvl(item.monthly,0) : addComma(item.monthly);
            var bMonthly = (item.bMonthly === undefined || item.bMonthly == null || item.bMonthly.length <= 0) ? nvl(item.bMonthly,0) : addComma(item.bMonthly);
            var totSum = (item.totSum === undefined || item.totSum == null || item.totSum.length <= 0) ? nvl(item.totSum,0) : addComma(item.totSum);
            courseTypeListHtml += '<tr class="h25">'
                + '<td class="tc" style="background-color: lightgrey;">' + item.fg + '</td>'
                + '<td class="tr">' + cashAmt + '</td>'
                + '<td class="tr">' + cardAmt + '</td>'
                + '<td class="tr">' + daySaleQty + '</td>'
                + '<td class="tr">' + daily + '</td>'
                + '<td class="tr">' + monthSaleQty + '</td>'
                + '<td class="tr">' + monthly + '</td>'
                + '<td class="tr">' + bMonthly + '</td>'
                + '<td class="tr">' + totSum + '</td>'
                + '</tr>';
        }

        courseTypeListHtml += '</table>';


        // 수강료현황1 리스트 HTML 생성
        var tuition1ListHtml = '';
        var tuition1ListHeaderHtml = '';
        tuition1ListHeaderHtml = '<table class="w100 mt5">' // 수강료현황1 header html
            + '<colgroup>'
            + '<col style="width: 15%">'
            + '<col style="width: 85%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td class="tl br0 s15">3. 수강료현황</td>'
            + '<td class="tc br0"></td>'
            + '</tr>'
            + '</table>';

        tuition1ListHeaderHtml += '<table class="w100">' // 수강료현황1 header html
            + '<colgroup>'
            + '<col style="width:15%;">'
            + '<col style="width:14%;">'
            + '<col style="width:14%;">'
            + '<col style="width:15%;">'
            + '<col style="width:14%;">'
            + '<col style="width:14%;">'
            + '<col style="width:14%;">'
            + '</colgroup>'
            + '<tr class="h30">'
            + '<th class="tc" style="background-color: lightgrey;">구분</th>'
            + '<th class="tc" style="background-color: lightgrey;">현금</th>'
            + '<th class="tc" style="background-color: lightgrey;">신용카드(+)</th>'
            + '<th class="tc" style="background-color: lightgrey;">신용단말기(+)</th>'
            + '<th class="tc" style="background-color: lightgrey;">총입금계</th>'
            + '<th class="tc" colspan="2" style="background-color: lightgrey">수강취소 현황</th>'
            + '</tr>';

        for (var i = 0; i < $scope.dailyTableKwuCtrl_tuition1.length; i++) {
            // 금액표시(,)
            var item = $scope.dailyTableKwuCtrl_tuition1[i];
            var cashAmt = (item.cashAmt === undefined || item.cashAmt == null || item.cashAmt.length <= 0) ? nvl(item.cashAmt,0) : addComma(item.cashAmt);
            var cardAmt = (item.cardAmt === undefined || item.cardAmt == null || item.cardAmt.length <= 0) ? nvl(item.cardAmt,0) : addComma(item.cardAmt);
            var catAmt = (item.catAmt === undefined || item.catAmt == null || item.catAmt.length <= 0) ? nvl(item.catAmt,0) : addComma(item.catAmt);
            var totInAmt = (item.totInAmt === undefined || item.totInAmt == null || item.totInAmt.length <= 0) ? nvl(item.totInAmt,0) : addComma(item.totInAmt);
            var cancelCnt = (item.cancelCnt === undefined || item.cancelCnt == null || item.cancelCnt.length <= 0) ? nvl(item.cancelCnt,0) : addComma(item.cancelCnt);
            var cancelAmt = (item.cancelAmt === undefined || item.cancelAmt == null || item.cancelAmt.length <= 0) ? nvl(item.cancelAmt,0) : addComma(item.cancelAmt);
            tuition1ListHtml += '<tr class="h25">'
                + '<td class="tc" style="background-color: lightgrey;">' + item.fg + '</td>'
                + '<td class="tr">' + cashAmt + '</td>'
                + '<td class="tr">' + cardAmt + '</td>'
                + '<td class="tr">' + catAmt + '</td>'
                + '<td class="tr">' + totInAmt + '</td>'
                + '<td class="tr">' + cancelCnt + '</td>'
                + '<td class="tr">' + cancelAmt + '</td>'
                + '</tr>';
        }

        tuition1ListHtml += '</table>';


        // 수강료현황2 리스트 HTML 생성
        var tuition2ListHtml = '';
        var tuition2ListHeaderHtml = '';
        var tuition2ListFooterHtml = '';
        tuition2ListHeaderHtml += '<table class="w100 mt5">' // 수강료현황2 header html
            + '<colgroup>'
            + '<col style="width:15%;">'
            + '<col style="width:17%;">'
            + '<col style="width:17%;">'
            + '<col style="width:17%;">'
            + '<col style="width:17%;">'
            + '<col style="width:17%;">'
            + '</colgroup>'
            + '<tr class="h30">'
            + '<th class="tc" style="background-color: lightgrey;">구분</th>'
            + '<th class="tc" style="background-color: lightgrey;">전월미수(+)</th>'
            + '<th class="tc" style="background-color: lightgrey;">당월미수(-)</th>'
            + '<th class="tc" style="background-color: lightgrey;">수수료(-)</th>'
            + '<th class="tc" style="background-color: lightgrey;">결산이자(+)</th>'
            + '<th class="tc" style="background-color: lightgrey">카드입금계</th>'
            + '</tr>';

        for (var i = 0; i < $scope.dailyTableKwuCtrl_tuition2.length; i++) {
            // 금액표시(,)
            var item = $scope.dailyTableKwuCtrl_tuition2[i];
            var bMonthUnpaidAmt = (item.bMonthUnpaidAmt === undefined || item.bMonthUnpaidAmt == null || item.bMonthUnpaidAmt.length <= 0) ? nvl(item.bMonthUnpaidAmt,0) : addComma(item.bMonthUnpaidAmt);
            var monthUnpaidAmt = (item.monthUnpaidAmt === undefined || item.monthUnpaidAmt == null || item.monthUnpaidAmt.length <= 0) ? nvl(item.monthUnpaidAmt,0) : addComma(item.monthUnpaidAmt);
            var commissionAmt = (item.commissionAmt === undefined || item.commissionAmt == null || item.commissionAmt.length <= 0) ? nvl(item.commissionAmt,0) : addComma(item.commissionAmt);
            var interestAmt = (item.interestAmt === undefined || item.interestAmt == null || item.interestAmt.length <= 0) ? nvl(item.interestAmt,0) : addComma(item.interestAmt);
            var cardInAmt = (item.cardInAmt === undefined || item.cardInAmt == null || item.cardInAmt.length <= 0) ? nvl(item.cardInAmt,0) : addComma(item.cardInAmt);
            tuition2ListHtml += '<tr class="h25">'
                + '<td class="tc" style="background-color: lightgrey;">' + item.fg + '</td>'
                + '<td class="tr">' + bMonthUnpaidAmt + '</td>'
                + '<td class="tr">' + monthUnpaidAmt + '</td>'
                + '<td class="tr">' + commissionAmt + '</td>'
                + '<td class="tr">' + interestAmt + '</td>'
                + '<td class="tr">' + cardInAmt + '</td>'
                + '</tr>';
        }

        tuition2ListHtml += '</table>';

        tuition2ListFooterHtml = '<table class="w100 mt5">' // 수강료현황2 footer html
            + '<colgroup>'
            + '<col style="width: 100%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td class="tl br0 s15" style="color: red">카드입금계 : (신용카드+신용단말기+전월미수+결산이자) - (당월미수+수수료)</td>'
            + '</tr>'
            + '</table>';


        // 단체수강내역 리스트 HTML 생성
        var groupCourseListHtml = '';
        var groupCourseListHeaderHtml = '';
        groupCourseListHeaderHtml = '<table class="w100 mt5">' // 단체수강내역 header html
            + '<colgroup>'
            + '<col style="width: 15%">'
            + '<col style="width: 85%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td class="tl br0 s15">4. 단체수강내역</td>'
            + '<td class="tc br0"></td>'
            + '</tr>'
            + '</table>';

        groupCourseListHeaderHtml += '<table class="w100">' // 단체수강내역 header html
            + '<colgroup>'
            + '<col style="width:15%;">'
            + '<col style="width:21%;">'
            + '<col style="width:21%;">'
            + '<col style="width:22%;">'
            + '<col style="width:21%;">'
            + '</colgroup>'
            + '<tr class="h30">'
            + '<th class="tc" style="background-color: lightgrey;">구분</th>'
            + '<th class="tc" style="background-color: lightgrey;">단체명</th>'
            + '<th class="tc" style="background-color: lightgrey;">판매일자</th>'
            + '<th class="tc" style="background-color: lightgrey;">적요</th>'
            + '<th class="tc" style="background-color: lightgrey;">금액</th>'
            + '</tr>';

        for (var i = 0; i < $scope.dailyTableKwuCtrl_groupCourse.length; i++) {
            // 금액표시(,)
            var item = $scope.dailyTableKwuCtrl_groupCourse[i];
            var amt = (item.amt === undefined || item.amt == null || item.amt.length <= 0) ? nvl(item.amt,0) : addComma(item.amt);
            groupCourseListHtml += '<tr class="h25">'
                + '<td class="tc">' + item.fg + '</td>'
                + '<td class="tl">' + item.groupNm + '</td>'
                + '<td class="tl">' + item.saleDate + '</td>'
                + '<td class="tl">' + item.summary + '</td>'
                + '<td class="tr">' + amt + '</td>'
                + '</tr>';
        }

        groupCourseListHtml += '</table>';


        // 전체 HTML 생성
        dailyTableKwuHtml += titleHtml + infoHtml
            + courseStatusListHeaderHtml + courseStatusListHtml
            + courseTypeListHeaderHtml + courseTypeListHtml
            + tuition1ListHeaderHtml + tuition1ListHtml
            + tuition2ListHeaderHtml + tuition2ListHtml + tuition2ListFooterHtml
            + groupCourseListHeaderHtml + groupCourseListHtml
        ;
        $('#dailyTableKwuReport').append(dailyTableKwuHtml);
    };

    // 인쇄
    $scope.print = function () {
        // create document
        var doc = new wijmo.PrintDocument({
            title: ''
        });

        // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
        var browser = navigator.userAgent.toLowerCase();
        if (-1 != browser.indexOf('chrome')) {
            // doc.append('<div style="height: 9mm;"></div>');
        }

        // add content to it
        var view = document.querySelector('#dailyTableKwuReport');
        doc.append(view);

        // and print it
        doc.print();
    };
    // <-- //첫째 장 인쇄 -->


    // <-- 둘째 장 인쇄 -->
    $scope.searchDailyTableReportKwu2 = function(){
        var params = {};
        params.startDate = $scope.startDate;
        params.storeCd = $scope.storeCd;

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//'데이터 처리 중입니다.' 시작	//cmm.progress=데이터 처리 중입니다.

        $scope._postJSONQuery.withOutPopUp ( "/excclc/excclc/dailyTableKwu/dailyTableKwu/getDailyTableKwuList2.sb",	//영업일보 구성 조회
            params,
            function(response)	{
                //데이터 setting > 영업일보
                $scope.dailyTableKwuCtrl_paymentStatus1 = response.data.data.paymentStatus1; // 출납현황
                $scope.dailyTableKwuCtrl_paymentStatus2 = response.data.data.paymentStatus2; // 출납현황
                $scope.dailyTableKwuCtrl_paymentStatus3 = response.data.data.paymentStatus3; // 출납현황

                $scope.$broadcast('loadingPopupInactive');	//'데이터 처리 중입니다.' 중지

                // report html 생성
                $scope.reportRender2();
            }, false);
    };

    // report html 생성
    $scope.reportRender2 = function () {
        var dailyTableKwuHtml = '';
        var nextPageHtml  = '<p class="nextPage mt5"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html


        // 최상단 html
        var titleHtml = '<table class="w100 mt40">'
            + '<colgroup>'
            + '<col style="width: 30%">'
            + '<col style="width: 40%">'
            + '<col style="width: 30%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td class="tc br0"></td>'
            + '<td class="tc" style="background-color: lightgoldenrodyellow;"><p class="s25 bk">일일일계표</p></td>'
            + '<td class="tc br0"></td>'
            + '</tr>'
            + '</table>';


        // 최상단 html
        var infoHtml = '<table class="w100 mt10">'
            + '<colgroup>'
            + '<col style="width: 25%">'
            + '<col style="width: 45%">'
            + '<col style="width: 10%">'
            + '<col style="width: 10%">'
            + '<col style="width: 10%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td rowspan="3" class="tl br0"><p class="s18 bk">광운대학교<br><br>일 자 : ' + $scope.startDate.substr(0, 4) + '-' + $scope.startDate.substr(4, 2) + '-' + $scope.startDate.substr(6, 2) + '</p></td>'
            + '<td rowspan="3" class="tc br0"></td>'
            + '<td class="tc">담당</td>'
            + '<td class="tc">팀장</td>'
            + '<td class="tc">관장</td>'
            + '</tr>'
            + '<tr class="h25">'
            + '<td rowspan="2" class="tc"></td>'
            + '<td rowspan="2" class="tc"></td>'
            + '<td rowspan="2" class="tc"></td>'
            + '</tr>'
            + '<tr class="h25">'
            + '</tr>'
            + '</table>';


        // 출납현황1 리스트 HTML 생성
        var paymentStatus1ListHtml = '';
        var paymentStatus1ListHeaderHtml = '';
        paymentStatus1ListHeaderHtml = '<table class="w100 mt5">' // 출납현황1 header html
            + '<colgroup>'
            + '<col style="width: 15%">'
            + '<col style="width: 85%">'
            + '</colgroup>'
            + '<tr class="h25">'
            + '<td class="tl br0 s15">5. 출납현황</td>'
            + '<td class="tc br0"></td>'
            + '</tr>'
            + '</table>';

        paymentStatus1ListHeaderHtml += '<table class="w100">' // 출납현황1 header html
            + '<colgroup>'
            + '<col style="width:25%;">'
            + '<col style="width:25%;">'
            + '<col style="width:25%;">'
            + '<col style="width:25%;">'
            + '</colgroup>'
            + '<tr class="h30">'
            + '<th class="tc" rowspan="2" style="background-color: lightgrey;">월입금 누계</th>'
            + '<th class="tc" style="background-color: lightgrey;">입금액</th>'
            + '<th class="tc" style="background-color: lightgrey;">출금액</th>'
            + '<th class="tc" style="background-color: lightgrey;">잔액</th>'
            + '</tr>';

        for (var i = 0; i < $scope.dailyTableKwuCtrl_paymentStatus1.length; i++) {
            // 금액표시(,)
            var item = $scope.dailyTableKwuCtrl_paymentStatus1[i];
            var inAmt = (item.inAmt === undefined || item.inAmt == null || item.inAmt.length <= 0) ? nvl(item.inAmt,0) : addComma(item.inAmt);
            var outAmt = (item.outAmt === undefined || item.outAmt == null || item.outAmt.length <= 0) ? nvl(item.outAmt,0) : addComma(item.outAmt);
            var remainAmt = (item.remainAmt === undefined || item.remainAmt == null || item.remainAmt.length <= 0) ? nvl(item.remainAmt,0) : addComma(item.remainAmt);
            paymentStatus1ListHtml += '<tr class="h25">'
                + '<td class="tr">' + inAmt + '</td>'
                + '<td class="tr">' + outAmt + '</td>'
                + '<td class="tr">' + remainAmt + '</td>'
                + '</tr>';
        }

        paymentStatus1ListHtml += '</table>';


        // 출납현황2 리스트 HTML 생성
        var paymentStatus2ListHtml = '';
        var paymentStatus2ListHeaderHtml = '';
        paymentStatus2ListHeaderHtml += '<table class="w100 mt5">' // 출납현황2 header html
            + '<colgroup>'
            + '<col style="width:20%;">'
            + '<col style="width:20%;">'
            + '<col style="width:20%;">'
            + '<col style="width:20%;">'
            + '<col style="width:20%;">'
            + '</colgroup>'
            + '<tr class="h30">'
            + '<th class="tc" colspan="2" style="background-color: lightgrey;">입금</th>'
            + '<th class="tc" colspan="2" style="background-color: lightgrey;">출금</th>'
            + '<th class="tc" rowspan="2" style="background-color: lightgrey;">잔액</th>'
            + '</tr>'
            + '<tr class="h30">'
            + '<th class="tc" style="background-color: lightgrey;">내역</th>'
            + '<th class="tc" style="background-color: lightgrey;">금액</th>'
            + '<th class="tc" style="background-color: lightgrey;">내역</th>'
            + '<th class="tc" style="background-color: lightgrey;">금액</th>'
            + '</tr>';

        for (var i = 0; i < $scope.dailyTableKwuCtrl_paymentStatus2.length; i++) {
            // 금액표시(,)
            var item = $scope.dailyTableKwuCtrl_paymentStatus2[i];
            var inAmt = (item.inAmt === undefined || item.inAmt == null || item.inAmt.length <= 0) ? nvl(item.inAmt,0) : addComma(item.inAmt);
            var outAmt = (item.outAmt === undefined || item.outAmt == null || item.outAmt.length <= 0) ? nvl(item.outAmt,0) : addComma(item.outAmt);
            var remainAmt = (item.remainAmt === undefined || item.remainAmt == null || item.remainAmt.length <= 0) ? nvl(item.remainAmt,0) : addComma(item.remainAmt);
            if(i === 0) {
                paymentStatus2ListHtml += '<tr class="h25">'
                    + '<td class="tc" style="background-color: lightgrey;">' + item.inInfo + '</td>'
                    + '<td class="tr">' + inAmt + '</td>'
                    + '<td class="tc" rowspan="4" style="background-color: lightgrey;"></td>'
                    + '<td class="tr" rowspan="4"></td>'
                    + '<td class="tr" rowspan="4"></td>'
                    + '</tr>';
            } else if(i === 1 || i === 2 || i === 3) {
                paymentStatus2ListHtml += '<tr class="h25">'
                    + '<td class="tc" style="background-color: lightgrey;">' + item.inInfo + '</td>'
                    + '<td class="tr">' + inAmt + '</td>'
                    + '</tr>';
            } else {
                paymentStatus2ListHtml += '<tr class="h25">'
                    + '<td class="tc" style="background-color: lightgrey;">' + item.inInfo + '</td>'
                    + '<td class="tr">' + inAmt + '</td>'
                    + '<td class="tc" style="background-color: lightgrey;">' + item.outInfo + '</td>'
                    + '<td class="tr">' + outAmt + '</td>'
                    + '<td class="tr">' + remainAmt + '</td>'
                    + '</tr>';
            }
        }

        for (var i = 0; i < $scope.dailyTableKwuCtrl_paymentStatus3.length; i++) {
            // null표시
            var item = $scope.dailyTableKwuCtrl_paymentStatus3[i];
            var content = nvl(item.content,'');
            if(i === 0) {
                paymentStatus2ListHtml += '<tr class="h25">'
                    + '<td class="tc" rowspan="6" style="background-color: lightgrey;">비고</td>'
                    + '<td class="tl" colspan="4">' + content + '</td>'
                    + '</tr>';
            } else {
                paymentStatus2ListHtml += '<tr class="h25">'
                    + '<td class="tl" colspan="4">' + content + '</td>'
                    + '</tr>';
            }
        }

        paymentStatus2ListHtml += '</table>';


        // 전체 HTML 생성
        dailyTableKwuHtml += titleHtml + infoHtml
            + paymentStatus1ListHeaderHtml + paymentStatus1ListHtml
            + paymentStatus2ListHeaderHtml + paymentStatus2ListHtml
        ;
        $('#dailyTableKwuReport2').append(dailyTableKwuHtml);
    };

    // 인쇄
    $scope.print2 = function () {
        // create document
        var doc = new wijmo.PrintDocument({
            title: ''
        });

        // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
        var browser = navigator.userAgent.toLowerCase();
        if (-1 != browser.indexOf('chrome')) {
            // doc.append('<div style="height: 9mm;"></div>');
        }

        // add content to it
        var view = document.querySelector('#dailyTableKwuReport2');
        doc.append(view);

        // and print it
        doc.print();
    };
    // <-- //둘째 장 인쇄 -->


    // 팝업 닫기
    $scope.close = function(){
        $scope.wjDailyTableReportKwuLayer.hide();
    };
}]);