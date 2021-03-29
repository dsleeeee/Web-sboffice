<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="sessionId" value="${param.sid}" />

<div ng-controller="mrhstMainCtrl">

    <!--메인컨텐츠-->
    <div class="mainCon">
        <!--오늘의 매출건수-->
        <%--<div class="w35 fl br bb stat_purple2_1" style="height: 230px;">--%>
            <%--<h2>오늘의 매출건수<span id="totalSaleCnt"></span></h2>--%>
            <%--<div>--%>
                <%--<p><span>신용카드</span><span id="cardSaleCnt"></span></p>--%>
                <%--<p><span>현금</span><span id="cashSaleCnt"></span></p>--%>
                <%--<p><span>기타</span><span id="etcSaleCnt"></span></p>--%>
            <%--</div>--%>
        <%--</div>--%>
        <!--//오늘의 매출건수-->

        <!--오늘의 매출금액-->
        <%--<div class="w35 fl br bb stat_sky2_1" style="height: 230px;">--%>
            <%--<h2>오늘의 매출금액<span id="totalSaleAmt"></span></h2>--%>
            <%--<div>--%>
                <%--<p><span>신용카드</span><span id="cardSaleAmt"></span></p>--%>
                <%--<p><span>현금</span><span id="cashSaleAmt"></span></p>--%>
                <%--<p><span>기타</span><span id="etcSaleAmt"></span></p>--%>
            <%--</div>--%>
        <%--</div>--%>
        <!--//오늘의 매출금액-->

        <div class="w35 fl br bb stat_sky2_2" style="height: 230px;">
            <h2 class="bb">오늘의 매출건수<span id="totalSaleCnt"></span></h2>
            <h2 class="pdt10">오늘의 매출금액<span id="totalSaleAmt"></span></h2>
            <div>
                <p><span>신용카드</span><span id="cardSaleAmt"></span></p>
                <p><span>현금</span><span id="cashSaleAmt"></span></p>
                <p><span>기타</span><span id="etcSaleAmt"></span></p>
            </div>
        </div>

        <!--공지사항-->
        <div class="w65 fl bb notice2" style="overflow-y: auto;">
            <h2>공지사항
                <div class="circle">
                    <%--가상로그인 후 로고 클릭시 세션없어짐 2021.02.16 김설아--%>
                    <%--<span><a href="/adi/board/board/01/list.sb?sid=${sessionId}">more</a></span>--%>
                    <%--페이지 이동시 권한체크 2021.02.18 김설아--%>
                    <span><a href="#" onclick="boardMove()">more</a></span>
                </div>
            </h2>
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
                <div class="sb-select fr w15">
                    <wj-combo-box
                        id="srchGubunCombo1"
                        control="_gubunCombo1"
                        ng-model="gubunCombo1"
                        items-source="_getComboData('gubunCombo1')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        selected-index-changed="setDateSelect1(s)">
                    </wj-combo-box>
                </div>
                <%--기간--%>
                <div class="sb-select dkbr fr mr10">
                    <p class="tl s16 mt5 lh15"><span id="dateText1"></span></p>
                </div>
                <%--<div class="searchBox">--%>
                    <%--<span id="dateText1"></span>--%>
                    <%--<span>--%>
                        <%--<select id="dateSelect1">--%>
                          <%--<c:forEach var="item" items="${dateSelList1}">--%>
                            <%--<option value="${item.dateStr}">${item.name}</option>--%>
                          <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</span>--%>
                <%--</div>--%>
            </h2>
            <%--매출현황 일별(1주)--%>
            <%--<div class="wizWrap" id="chart1" style="width:100%; height:215px;"></div>--%>
            <%--매출현황 일별(1주)--%>
            <%--<div class="wizWrap" id="chart2" style="width:100%; height:215px;"></div>--%>
            <%--매출현황 월별(1년)--%>
            <%--<div class="wizWrap" id="chart3" style="width:100%; height:215px;"></div>--%>
            <div class="w100" id="wjWrapType1" ng-controller="saleStoreCtrl">
                <div class="wj-gridWrap" style="display:none;" >
                    <wj-flex-grid
                            id="saleStoreChartGrid"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="날짜" binding="saleDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="실매출액" binding="realSaleAmt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <!-- 막대 차트 샘플 -->
                <div>
                    <wj-flex-chart
                            id="saleStoreChart"
                            name="barChart1"
                            style="width:100%; height:230px;"
                            initialized="initChart(s,e)"
                            items-source="data"
                            binding-x="saleDate">

                        <wj-flex-chart-series name="실매출액" binding="realSaleAmt" ></wj-flex-chart-series>
                    </wj-flex-chart>
                </div>
            </div>
        </div>
        <!--//매출현황-->

        <!--매출 상위 상품-->
        <div class="w100 fl br bb graph1">
            <h2>
                매출 상위 상품
                <div class="sb-select fr w15">
                    <wj-combo-box
                        id="srchGubunCombo2"
                        control="_gubunCombo2"
                        ng-model="gubunCombo2"
                        items-source="_getComboData('gubunCombo2')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        selected-index-changed="setDateSelect2(s)">
                    </wj-combo-box>
                </div>
                <%--기간--%>
                <div class="sb-select dkbr fr mr10">
                    <p class="tl s16 mt5 lh15"><span id="dateText2"></span></p>
                </div>
                <%--<div class="searchBox">--%>
                    <%--<span id="dateText2"></span>--%>
                    <%--<span>--%>
                        <%--<select id="dateSelect2">--%>
                          <%--<c:forEach var="item" items="${dateSelList2}">--%>
                              <%--<option value="${item.dateStr}">${item.name}</option>--%>
                          <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</span>--%>
                <%--</div>--%>
            </h2>
            <%--매출 상위 상품 오늘--%>
            <%--<div class="wizWrap" id="chart4" style="width:100%; height:215px;"></div>--%>
            <%--매출 상위 상품 1주일--%>
            <%--<div class="wizWrap" id="chart5" style="width:100%; height:215px;"></div>--%>
            <%--매출 상위 상품 1개월--%>
            <%--<div class="wizWrap" id="chart6" style="width:100%; height:215px;"></div>--%>
            <div class="w100" id="wjWrapType1" ng-controller="saleProdStoreCtrl">
                <div class="wj-gridWrap" style="display:none;" >
                    <wj-flex-grid
                            id="saleProdStoreChartGrid"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="상품명" binding="prodNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="실매출액" binding="realSaleAmt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <!-- 막대 차트 샘플 -->
                <div>
                    <wj-flex-chart
                            id="saleProdStoreChart"
                            name="barChart2"
                            style="width:100%; height:230px;"
                            initialized="initChart(s,e)"
                            items-source="data"
                            binding-x="prodNm">

                        <wj-flex-chart-series name="실매출액" binding="realSaleAmt" ></wj-flex-chart-series>
                    </wj-flex-chart>
                </div>
            </div>
        </div>
        <!--//매출 상위 상품-->

        <!--날씨-->
        <%--<div class="w30 fl bb weather">--%>
           <%--<div class="today"> --%>
               <%--<h2 class="hidden">날씨</h2>      --%>
               <%--<p class="date">--%>
                   <%--<span>서울시</span>--%>
                   <%--<span><em>11월 26일</em> <em>일요일</em></span>--%>
               <%--</p>--%>
               <%--<a href="#">날씨더보기</a>--%>
               <%--<div class="temperatures">--%>
                   <%--<p>--%>
                       <%--<!--파란색 날씨아이콘 : weIc01~14까지-->--%>
                       <%--<em class="weIc02"></em>--%>
                       <%--<span>--%>
                           <%--최고 <em>13°C</em><br />--%>
                           <%--최저 <em>5°C</em>--%>
                       <%--</span>--%>
                   <%--</p> --%>
               <%--</div>--%>
           <%--</div>--%>
           <%--<ul>--%>
               <%--<li>--%>
                   <%--<!--검은색 날씨아이콘 : weIc01~14까지-->--%>
                   <%--<span class="weIc11"></span>--%>
                   <%--<span class="day">월</span>--%>
                   <%--<span><em>5°C</em> / <em>13°C</em></span>--%>
               <%--</li>--%>
               <%--<li>--%>
                   <%--<!--검은색 날씨아이콘 : weIc01~14까지-->--%>
                   <%--<span class="weIc08"></span>--%>
                   <%--<span class="day">화</span>--%>
                   <%--<span><em>-3°C</em> / <em>10°C</em></span>--%>
               <%--</li>--%>
           <%--</ul> --%>
        <%--</div>--%>
        <!--//날씨-->
    </div>
    <!--//메인컨텐츠-->

</div>

<script type="text/javascript">
    // 공지사항 more 페이지 이동시 권한체크
    var board_auth = "${board_auth}";

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

    // 날짜
    var dateSelList1 = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="dateSel1" items="${dateSelList1}">
        var dateSel1Param = {};
        dateSel1Param.name = "${dateSel1.name}";
        dateSel1Param.dateStr = "${dateSel1.dateStr}";
        dateSelList1.push(dateSel1Param);
    </c:forEach>
    var dateSelList2 = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="dateSel2" items="${dateSelList2}">
        var dateSel2Param = {};
        dateSel2Param.name = "${dateSel2.name}";
        dateSel2Param.dateStr = "${dateSel2.dateStr}";
        dateSelList2.push(dateSel2Param);
    </c:forEach>

    // 매출현황 일별(1주)
    <%--var saleWeekList = [];--%>
    <%--&lt;%&ndash;javascript에서 사용할 결제수단 json 데이터 생성&ndash;%&gt;--%>
    <%--<c:forEach var="saleWeek" items="${saleWeekList}">--%>
        <%--var saleWeekParam = {};--%>
        <%--saleWeekParam.saleDate = "${saleWeek.saleDate}";--%>
        <%--saleWeekParam.realSaleAmt = "${saleWeek.realSaleAmt}";--%>
        <%--saleWeekList.push(saleWeekParam);--%>
    <%--</c:forEach>--%>

    // 매출현황 요일별(1개월)
    <%--var saleMonthList = [];--%>
    <%--&lt;%&ndash;javascript에서 사용할 결제수단 json 데이터 생성&ndash;%&gt;--%>
    <%--<c:forEach var="saleMonth" items="${saleMonthList}">--%>
        <%--var saleMonthParam = {};--%>
        <%--saleMonthParam.yoil = "${saleMonth.yoil}";--%>
        <%--saleMonthParam.realSaleAmt = "${saleMonth.realSaleAmt}";--%>
        <%--saleMonthList.push(saleMonthParam);--%>
    <%--</c:forEach>--%>

    // 매출현황 월별(1년)
    <%--var saleYearList = [];--%>
    <%--&lt;%&ndash;javascript에서 사용할 결제수단 json 데이터 생성&ndash;%&gt;--%>
    <%--<c:forEach var="saleYear" items="${saleYearList}">--%>
        <%--var saleYearParam = {};--%>
        <%--saleYearParam.saleYm = "${saleYear.saleYm}";--%>
        <%--saleYearParam.realSaleAmt = "${saleYear.realSaleAmt}";--%>
        <%--saleYearList.push(saleYearParam);--%>
    <%--</c:forEach>--%>

    // 매출 상위 상품 오늘
    <%--var saleProdDayList = [];--%>
    <%--&lt;%&ndash;javascript에서 사용할 결제수단 json 데이터 생성&ndash;%&gt;--%>
    <%--<c:forEach var="saleProdDay" items="${saleProdDayList}">--%>
        <%--var saleProdDayParam = {};--%>
        <%--saleProdDayParam.prodNm = "${saleProdDay.prodNm}";--%>
        <%--saleProdDayParam.realSaleAmt = "${saleProdDay.realSaleAmt}";--%>
        <%--saleProdDayList.push(saleProdDayParam);--%>
    <%--</c:forEach>--%>

    // 매출 상위 상품 1주일
    <%--var saleProdWeekList = [];--%>
    <%--&lt;%&ndash;javascript에서 사용할 결제수단 json 데이터 생성&ndash;%&gt;--%>
    <%--<c:forEach var="saleProdWeek" items="${saleProdWeekList}">--%>
        <%--var saleProdWeekParam = {};--%>
        <%--saleProdWeekParam.prodNm = "${saleProdWeek.prodNm}";--%>
        <%--saleProdWeekParam.realSaleAmt = "${saleProdWeek.realSaleAmt}";--%>
        <%--saleProdWeekList.push(saleProdWeekParam);--%>
    <%--</c:forEach>--%>

    // 매출 상위 상품 1개월
    <%--var saleProdMonthList = [];--%>
    <%--&lt;%&ndash;javascript에서 사용할 결제수단 json 데이터 생성&ndash;%&gt;--%>
    <%--<c:forEach var="saleProdMonth" items="${saleProdMonthList}">--%>
        <%--var saleProdMonthParam = {};--%>
        <%--saleProdMonthParam.prodNm = "${saleProdMonth.prodNm}";--%>
        <%--saleProdMonthParam.realSaleAmt = "${saleProdMonth.realSaleAmt}";--%>
        <%--saleProdMonthList.push(saleProdMonthParam);--%>
    <%--</c:forEach>--%>
</script>

<%--<script type="text/javascript">--%>
    <%--// 기간--%>
    <%--&lt;%&ndash;$(document).ready(function(){&ndash;%&gt;--%>
    <%--&lt;%&ndash;// 매출현황&ndash;%&gt;--%>
    <%--&lt;%&ndash;$("#dateText1").text("기간 : " + $("#dateSelect1").val());&ndash;%&gt;--%>
    <%--&lt;%&ndash;// 매출 상위 상품&ndash;%&gt;--%>
    <%--&lt;%&ndash;$("#dateText2").text("기간 : " + $("#dateSelect2").val());&ndash;%&gt;--%>
    <%--&lt;%&ndash;});&ndash;%&gt;--%>
    <%--&lt;%&ndash;&lt;%&ndash; 날짜 선택 변경 &ndash;%&gt;&ndash;%&gt;--%>
    <%--&lt;%&ndash;// 매출현황&ndash;%&gt;--%>
    <%--&lt;%&ndash;$("#dateSelect1").change(function(){&ndash;%&gt;--%>
    <%--&lt;%&ndash;$("#dateText1").text("기간 : " + $(this).val());&ndash;%&gt;--%>
    <%--&lt;%&ndash;});&ndash;%&gt;--%>
    <%--&lt;%&ndash;// 매출 상위 상품&ndash;%&gt;--%>
    <%--&lt;%&ndash;$("#dateSelect2").change(function(){&ndash;%&gt;--%>
    <%--&lt;%&ndash;$("#dateText2").text("기간 : " + $(this).val());&ndash;%&gt;--%>
    <%--&lt;%&ndash;});&ndash;%&gt;--%>

    <%--&lt;%&ndash; 랜덤 데이터 생성 (추후 데이터 받아오면서 변경)&ndash;%&gt;--%>
    <%--// function getData(numCount) {--%>
    <%--//     var data = new wijmo.collections.ObservableArray();--%>
    <%--//     // var data = [];--%>
    <%--//     for (var i = 0; i < numCount; i++) {--%>
    <%--//         data.push(getRandomData('11월 28일'));--%>
    <%--//     }--%>
    <%--//     return data;--%>
    <%--// }--%>
    <%--// function getRandomData(idx) {--%>
    <%--//     return {--%>
    <%--//         x: idx,--%>
    <%--//         y0: getRandomValue(100),--%>
    <%--//         y1: getRandomValue(400)--%>
    <%--//     };--%>
    <%--// }--%>
    <%--// function getRandomValue(max) {--%>
    <%--//     return Math.round(Math.random() * max);--%>
    <%--// }--%>
    <%--&lt;%&ndash; // 랜덤 데이터 생성 (추후 데이터 받아오면서 변경)&ndash;%&gt;--%>

    <%--&lt;%&ndash; 데이터 생성&ndash;%&gt;--%>
    <%--function getData(numCount, gubun) {--%>
        <%--var data = new wijmo.collections.ObservableArray();--%>
        <%--for (var i = 0; i < numCount.length; i++) {--%>
            <%--if(gubun == "chart1") {--%>
                <%--data.push(getRandomData(numCount[i].saleDate, numCount[i].realSaleAmt));--%>
            <%--} else if(gubun == "chart2") {--%>
                <%--data.push(getRandomData(numCount[i].yoil, numCount[i].realSaleAmt));--%>
            <%--} else if(gubun == "chart3") {--%>
                <%--data.push(getRandomData(numCount[i].saleYm, numCount[i].realSaleAmt));--%>
            <%--} else if(gubun == "chart4" || gubun == "chart5" || gubun == "chart6") {--%>
                <%--data.push(getRandomData(numCount[i].prodNm, numCount[i].realSaleAmt));--%>
            <%--}--%>
        <%--}--%>
        <%--return data;--%>
    <%--}--%>
    <%--function getRandomData(idx ,y0) {--%>
        <%--return {--%>
            <%--x: idx,--%>
            <%--y0: getRandomValue(y0)--%>
        <%--};--%>
    <%--}--%>
    <%--function getRandomValue(max) {--%>
        <%--return Math.round(max);--%>
        <%--// return Math.round(Math.random() * max);--%>
    <%--}--%>
    <%--&lt;%&ndash; // 데이터 생성&ndash;%&gt;--%>

    <%--&lt;%&ndash; wijmo flexChart &ndash;%&gt;--%>
    <%--$(document).ready(function(){--%>
        <%--// var flexChartPoints = 7;--%>

        <%--&lt;%&ndash; 매출현황 일별(1주) &ndash;%&gt;--%>
        <%--var chart1 = new wijmo.chart.FlexChart('#chart1');--%>
        <%--chart1.beginUpdate();--%>
        <%--chart1.chartType = wijmo.chart.ChartType.Column;--%>
        <%--chart1.itemsSource = getData(saleWeekList, 'chart1'); // 여기에 받아온 데이터 넣기--%>
        <%--chart1.bindingX = 'x';--%>
        <%--chart1.chartType = parseInt(0);--%>
        <%--chart1.stacking = parseInt(1);--%>
        <%--chart1.rotated = false;--%>
        <%--chart1.palette = ['#93cbfc', '#90f0fc'];--%>

        <%--for (var i = 0; i < 1; i++) {--%>
            <%--var series = new wijmo.chart.Series();--%>
            <%--series.binding = 'y' + i;--%>
            <%--chart1.series.push(series);--%>
        <%--}--%>
        <%--chart1.endUpdate();--%>

        <%--&lt;%&ndash; 매출현황 요일별(1개월)&ndash;%&gt;--%>
        <%--var chart2 = new wijmo.chart.FlexChart('#chart2');--%>
        <%--chart2.beginUpdate();--%>
        <%--chart2.chartType = wijmo.chart.ChartType.Column;--%>
        <%--chart2.itemsSource = getData(saleMonthList, 'chart2'); // 여기에 받아온 데이터 넣기--%>
        <%--chart2.bindingX = 'x';--%>
        <%--chart2.chartType = parseInt(0);--%>
        <%--chart2.stacking = parseInt(1);--%>
        <%--chart2.rotated = false;--%>
        <%--chart2.palette = ['#93cbfc', '#90f0fc'];--%>

        <%--for (var i = 0; i < 1; i++) {--%>
            <%--var series = new wijmo.chart.Series();--%>
            <%--series.binding = 'y' + i;--%>
            <%--chart2.series.push(series);--%>
        <%--}--%>
        <%--chart2.endUpdate();--%>

        <%--&lt;%&ndash; 매출현황 월별(1년)&ndash;%&gt;--%>
        <%--var chart3 = new wijmo.chart.FlexChart('#chart3');--%>
        <%--chart3.beginUpdate();--%>
        <%--chart3.chartType = wijmo.chart.ChartType.Column;--%>
        <%--chart3.itemsSource = getData(saleYearList, 'chart3'); // 여기에 받아온 데이터 넣기--%>
        <%--chart3.bindingX = 'x';--%>
        <%--chart3.chartType = parseInt(0);--%>
        <%--chart3.stacking = parseInt(1);--%>
        <%--chart3.rotated = false;--%>
        <%--chart3.palette = ['#93cbfc', '#90f0fc'];--%>

        <%--for (var i = 0; i < 1; i++) {--%>
            <%--var series = new wijmo.chart.Series();--%>
            <%--series.binding = 'y' + i;--%>
            <%--chart3.series.push(series);--%>
        <%--}--%>
        <%--chart3.endUpdate();--%>

        <%--&lt;%&ndash; 매출 상위 상품 오늘&ndash;%&gt;--%>
        <%--var chart4 = new wijmo.chart.FlexChart('#chart4');--%>
        <%--chart4.beginUpdate();--%>
        <%--chart4.chartType = wijmo.chart.ChartType.Column;--%>
        <%--chart4.itemsSource = getData(saleProdDayList, 'chart4'); // 여기에 받아온 데이터 넣기--%>
        <%--chart4.bindingX = 'x';--%>
        <%--chart4.chartType = parseInt(0);--%>
        <%--chart4.stacking = parseInt(1);--%>
        <%--chart4.rotated = false;--%>
        <%--chart4.palette = ['#93cbfc', '#90f0fc'];--%>

        <%--for (var i = 0; i < 1; i++) {--%>
            <%--var series = new wijmo.chart.Series();--%>
            <%--series.binding = 'y' + i;--%>
            <%--chart4.series.push(series);--%>
        <%--}--%>
        <%--chart4.endUpdate();--%>

        <%--&lt;%&ndash; 매출 상위 상품 1주일&ndash;%&gt;--%>
        <%--var chart5 = new wijmo.chart.FlexChart('#chart5');--%>
        <%--chart5.beginUpdate();--%>
        <%--chart5.chartType = wijmo.chart.ChartType.Column;--%>
        <%--chart5.itemsSource = getData(saleProdWeekList, 'chart5'); // 여기에 받아온 데이터 넣기--%>
        <%--chart5.bindingX = 'x';--%>
        <%--chart5.chartType = parseInt(0);--%>
        <%--chart5.stacking = parseInt(1);--%>
        <%--chart5.rotated = false;--%>
        <%--chart5.palette = ['#93cbfc', '#90f0fc'];--%>

        <%--for (var i = 0; i < 1; i++) {--%>
            <%--var series = new wijmo.chart.Series();--%>
            <%--series.binding = 'y' + i;--%>
            <%--chart5.series.push(series);--%>
        <%--}--%>
        <%--chart5.endUpdate();--%>

        <%--&lt;%&ndash; 매출 상위 상품 1주일&ndash;%&gt;--%>
        <%--var chart6 = new wijmo.chart.FlexChart('#chart6');--%>
        <%--chart6.beginUpdate();--%>
        <%--chart6.chartType = wijmo.chart.ChartType.Column;--%>
        <%--chart6.itemsSource = getData(saleProdMonthList, 'chart6'); // 여기에 받아온 데이터 넣기--%>
        <%--chart6.bindingX = 'x';--%>
        <%--chart6.chartType = parseInt(0);--%>
        <%--chart6.stacking = parseInt(1);--%>
        <%--chart6.rotated = false;--%>
        <%--chart6.palette = ['#93cbfc', '#90f0fc'];--%>

        <%--for (var i = 0; i < 1; i++) {--%>
            <%--var series = new wijmo.chart.Series();--%>
            <%--series.binding = 'y' + i;--%>
            <%--chart6.series.push(series);--%>
        <%--}--%>
        <%--chart6.endUpdate();--%>

        <%--// 페이지로드시 display:none 하면 그래프 그려지지 않음 -> 보이게하고 jsp에서 숨김--%>
        <%--setTimeout(function() {--%>
            <%--$("#chart1").css("display", "");--%>
            <%--$("#chart2").css("display", "none");--%>
            <%--$("#chart3").css("display", "none");--%>
            <%--$("#chart4").css("display", "");--%>
            <%--$("#chart5").css("display", "none");--%>
            <%--$("#chart6").css("display", "none");--%>
        <%--}, 100)--%>
    <%--});--%>
    <%--&lt;%&ndash; // wijmo flexChart &ndash;%&gt;--%>
<%--</script>--%>

<script type="text/javascript" src="/resource/solbipos/js/application/main/mrhstMain.js?ver=20210223.01" charset="utf-8"></script>

<%-- 게시판 상세 팝업 --%>
<c:import url="/WEB-INF/view/adi/board/board/boardDetail.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 게시판 신규등록,수정 팝업 --%>
<c:import url="/WEB-INF/view/adi/board/board/boardInfo.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>