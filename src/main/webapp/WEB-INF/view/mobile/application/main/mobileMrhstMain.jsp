<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="sessionId" value="${param.sid}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />

<div ng-controller="mobileMrhstMainCtrl">

    <!--메인컨텐츠-->
    <div class="mainCon">
        <!--오늘의 매출건수-->
        <div class="w100 fl bb stat_sky2_2" style="height: 40px;">
            <h2>오늘의 매출건수<span id="totalSaleCnt"></span></h2>
        </div>
        <!--//오늘의 매출건수-->

        <!--오늘의 매출금액-->
        <div class="w100 fl bb stat_sky2_2" style="height: 150px;">
            <h2>오늘의 매출금액<span id="totalSaleAmt"></span></h2>
            <div>
                <p><span>신용카드</span><span id="cardSaleAmt"></span></p>
                <p><span>현금</span><span id="cashSaleAmt"></span></p>
                <p><span>기타</span><span id="etcSaleAmt"></span></p>
            </div>
        </div>
        <!--//오늘의 매출금액-->

        <!--공지사항-->
        <div class="w100 fl bb notice1" style="overflow-y: auto;">
            <h2>공지사항</h2>
            <ul>
                <c:forEach var="item" items="${noticeList}">
                    <li><a href="#" onclick="noticeDetail('${item.boardSeqNo}')">${item.title}</a><span>${item.noticeDate}</span></li>
                </c:forEach>
            </ul>
        </div>
        <!--//공지사항-->

        <!--매출현황-->
        <div class="w100 fl bb graph1">
            <h2>
                매출현황
                <%--기간--%>
                <div class="sb-select dkbr fr mr10">
                    <p class="tl s13 mt5 lh15 blue"><span id="dateText1"></span></p>
                </div>
            </h2>
            <div class="wizWrap" id="chart1" style="width:100%; height:230px; font-size:10px;"></div>
        </div>
    </div>
    <!--//매출현황-->

    <!--매출 상위 상품-->
    <div class="w100 fl br bb graph1">
        <h2>
            매출 상위 상품
            <%--기간--%>
            <div class="sb-select dkbr fr mr10">
                <p class="tl s13 mt5 lh15 blue"><span id="dateText2"></span></p>
            </div>
        </h2>
        <div class="wizWrap" id="chart2" style="width:100%; height:230px; font-size:10px;"></div>
    </div>
    <!--//매출 상위 상품-->

    <!--팝업-->
    <div>
        <%-- 6개월이상 비밀번호 미수정시 팝업 알림 --%>
        <div id="divDimmedLastPwd" class="fullDimmed" style="display: none;"></div>
        <div id="divPopupLastPwd" class="layer" style="display: none;">
            <div class="layer_inner" style="position:absolute; left:50%; top:50%;  transform: translate(-50%, -50%); text-align: center;">
                <!--layerContent-->
                <div class="title" style="width:430px;">
                    <div class="con">
                        <s:message code="login.pw.chg.lastPwd"/>
                    </div>
                    <div class="btnSet">
                        <span><a href="#" class="btn_blue" id="btnPwdChg"><s:message code="login.pw.chg"/></a></span>
                        <span><a href="#" class="btn_blue" id="btnPwdClose"><s:message code="login.pw.chg.next"/></a></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--팝업-->

</div>
<!--//메인컨텐츠-->

</div>

<script type="text/javascript">
    // 6개월이상 비밀번호 미수정시 팝업 알림
    var lastPwdChgDtChk = "${lastPwdChgDtChk}";

    // 오늘의 매출건수
    var daySaleCntList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="daySaleCnt" items="${daySaleCntList}">
        var daySaleCntParam = {};
        daySaleCntParam.realSaleCntTotal = "${daySaleCnt.realSaleCntTotal}";
        daySaleCntParam.cardCnt = "${daySaleCnt.cardCnt}";
        daySaleCntParam.cashCnt = "${daySaleCnt.cashCnt}";
        daySaleCntParam.etcCnt = "${daySaleCnt.etcCnt}";
        daySaleCntList.push(daySaleCntParam);
    </c:forEach>

    // 오늘의 매출금액
    var daySaleAmtList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="daySaleAmt" items="${daySaleAmtList}">
        var daySaleAmtParam = {};
        daySaleAmtParam.realSaleAmtTotal = "${daySaleAmt.realSaleAmtTotal}";
        daySaleAmtParam.cardAmt = "${daySaleAmt.cardAmt}";
        daySaleAmtParam.cashAmt = "${daySaleAmt.cashAmt}";
        daySaleAmtParam.etcAmt = "${daySaleAmt.etcAmt}";
        daySaleAmtList.push(daySaleAmtParam);
    </c:forEach>

    // 공지사항
    var noticeList = '${noticeList}';

    // 매출현황
    var saleWeekList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="saleWeek" items="${saleWeekList}">
        var saleWeekParam = {};
        saleWeekParam.saleDate = "${saleWeek.saleDate}";
        saleWeekParam.realSaleAmt = "${saleWeek.realSaleAmt}";
        saleWeekList.push(saleWeekParam);
    </c:forEach>

    // 매출 상위 상품
    var saleProdWeekList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="saleProdWeek" items="${saleProdWeekList}">
        var saleProdWeekParam = {};
        saleProdWeekParam.prodNm = "${saleProdWeek.prodNm}";
        saleProdWeekParam.realSaleAmt = "${saleProdWeek.realSaleAmt}";
        saleProdWeekList.push(saleProdWeekParam);
    </c:forEach>
</script>

<script type="text/javascript">
    <%-- 랜덤 데이터 생성 (추후 데이터 받아오면서 변경)--%>
    // function getData(numCount) {
    //     var data = new wijmo.collections.ObservableArray();
    //     // var data = [];
    //     for (var i = 0; i < numCount; i++) {
    //         data.push(getRandomData('11월 28일'));
    //     }
    //     return data;
    // }
    // function getRandomData(idx) {
    //     return {
    //         x: idx,
    //         y0: getRandomValue(100),
    //         y1: getRandomValue(400)
    //     };
    // }
    // function getRandomValue(max) {
    //     return Math.round(Math.random() * max);
    // }
    <%-- // 랜덤 데이터 생성 (추후 데이터 받아오면서 변경)--%>

    <%-- 데이터 생성--%>
    function getData(numCount, gubun) {
        var data = new wijmo.collections.ObservableArray();
        for (var i = 0; i < numCount.length; i++) {
            if(gubun == "chart1") {
                data.push(getRandomData(numCount[i].saleDate, numCount[i].realSaleAmt));
            } else if(gubun == "chart2") {
                data.push(getRandomData(numCount[i].prodNm, numCount[i].realSaleAmt));
            }
        }
        return data;
    }
    function getRandomData(idx ,y0) {
        return {
            x: idx,
            y0: getRandomValue(y0)
        };
    }
    function getRandomValue(max) {
        return Math.round(max);
        // return Math.round(Math.random() * max);
    }
    <%-- // 데이터 생성--%>

    <%-- wijmo flexChart --%>
    $(document).ready(function(){
        // var flexChartPoints = 7;

        <%-- 매출현황 --%>
        var chart1 = new wijmo.chart.FlexChart('#chart1');
        chart1.beginUpdate();
        chart1.chartType = wijmo.chart.ChartType.Column;
        chart1.itemsSource = getData(saleWeekList, 'chart1'); // 여기에 받아온 데이터 넣기
        chart1.bindingX = 'x';
        chart1.chartType = parseInt(1); // 세로차트형(Bar)
        // chart1.stacking = parseInt(1); // 겹쳐보이게
        chart1.rotated = false;
        chart1.palette = ['#93cbfc', '#90f0fc'];

        for (var i = 0; i < 1; i++) {
            var series = new wijmo.chart.Series();
            series.binding = 'y' + i;
            chart1.series.push(series);
        }
        chart1.endUpdate();

        <%-- 매출 상위 상품--%>
        var chart2 = new wijmo.chart.FlexChart('#chart2');
        chart2.beginUpdate();
        chart2.chartType = wijmo.chart.ChartType.Column;
        chart2.itemsSource = getData(saleProdWeekList, 'chart2'); // 여기에 받아온 데이터 넣기
        chart2.bindingX = 'x';
        chart2.chartType = parseInt(1); // 세로차트형(Bar)
        // chart2.stacking = parseInt(1); // 겹쳐보이게
        chart2.rotated = false;
        chart2.palette = ['#93cbfc', '#90f0fc'];

        for (var i = 0; i < 1; i++) {
            var series = new wijmo.chart.Series();
            series.binding = 'y' + i;
            chart2.series.push(series);
        }
        chart2.endUpdate();
    });
    <%-- // wijmo flexChart --%>

    // 6개월이상 비밀번호 미수정시 팝업 알림
    if(lastPwdChgDtChk === "Y") {
        $("#divDimmedLastPwd").css('display', 'block');
        $("#divPopupLastPwd").css('display', 'block');
    } else {
        $("#divDimmedLastPwd").css('display', 'none');
        $("#divPopupLastPwd").css('display', 'none');
    }
    $("#btnPwdChg").click(function(){
        // 비밀번호 변경 레이어 팝업 가져오기 (pwChgPop.jsp)
        var id = "${userId}";
        $("#labelUserId").text(id);
        $("#pwdUserId").val(id);
        $("#fullDimmedPw").show();
        $("#layerpw").show();

        $("#divDimmedLastPwd").css('display', 'none');
        $("#divPopupLastPwd").css('display', 'none');
    });
    $("#btnPwdClose").click(function(){
        $("#divDimmedLastPwd").css('display', 'none');
        $("#divPopupLastPwd").css('display', 'none');
    });
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/application/main/mobileMrhstMain.js?ver=20210531.01" charset="utf-8"></script>

<%-- 게시판 상세 팝업 --%>
<c:import url="/WEB-INF/view/mobile/adi/board/board/mobileBoardDetail.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>