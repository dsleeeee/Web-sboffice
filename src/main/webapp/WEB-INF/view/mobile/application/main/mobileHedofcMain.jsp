<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="sessionId" value="${param.sid}" />

<div ng-controller="mobileHedofcMainCtrl">

    <!--메인컨텐츠-->
    <div class="mainCon">
        <!--총 매장수-->
        <div class="w50 fl br bb stat_purple3" style="height: 90px;">
            <div>
                <h2>총 매장수 (오픈)<span id="totalStoreCnt"></span></h2>
            </div>
        </div>
        <!--//총 매장수-->

        <!--총 포스수-->
        <div class="w50 fl bb stat_purple3" style="height: 90px;">
            <div>
                <h2>총 포스수 (오픈)<span id="totalPosCnt"></span></h2>
            </div>
        </div>
        <!--//총 포스수-->

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
        <!--//매출현황-->

        <!--매출 상위 상품-->
        <div class="w100 fl bb graph1">
            <h2>
                매출 상위 상품
                <%--기간--%>
                <div class="sb-select dkbr fr mr10">
                    <p class="tl s13 mt5 lh15 blue"><span id="dateText2"></span></p>
                </div>
            </h2>
            <div class="wizWrap" id="chart2" style="width:100%; height:230px; font-size:10px;"></div>
        </div>
        <%--<!--//매출 상위 상품-->--%>

        <!--순위테이블-->
        <div class="w100 fl bb mainTbl1">
            <h2>
                매출 상위 가맹점
                <%--기간--%>
                <div class="sb-select dkbr fr mr10">
                    <p class="tl s13 mt5 lh15 blue"><span id="dateText3"></span></p>
                </div>
            </h2>
            <table class="mt10">
                <colgroup>
                    <col class="w10" />
                    <col class="w50" />
                    <col class="w40" />
                </colgroup>
                <thead>
                <tr>
                    <th class="tc">순위</th>
                    <th class="tc">매장명</th>
                    <th class="tc">매출액</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="tc">1</th>
                    <td><label id="lblStoreNm1"></label></td>
                    <td><label id="lblRealSaleAmt1"></label></td>
                </tr>
                <tr>
                    <th class="tc">2</th>
                    <td><label id="lblStoreNm2"></label></td>
                    <td><label id="lblRealSaleAmt2"></label></td>
                </tr>
                <tr>
                    <th class="tc">3</th>
                    <td><label id="lblStoreNm3"></label></td>
                    <td><label id="lblRealSaleAmt3"></label></td>
                </tr>
                <tr>
                    <th class="tc">4</th>
                    <td><label id="lblStoreNm4"></label></td>
                    <td><label id="lblRealSaleAmt4"></label></td>
                </tr>
                <tr>
                    <th class="tc">5</th>
                    <td><label id="lblStoreNm5"></label></td>
                    <td><label id="lblRealSaleAmt5"></label></td>
                </tr>
                <tr>
                    <th class="tc">6</th>
                    <td><label id="lblStoreNm6"></label></td>
                    <td><label id="lblRealSaleAmt6"></label></td>
                </tr>
                <tr>
                    <th class="tc">7</th>
                    <td><label id="lblStoreNm7"></label></td>
                    <td><label id="lblRealSaleAmt7"></label></td>
                </tr>
                <tr>
                    <th class="tc">8</th>
                    <td><label id="lblStoreNm8"></label></td>
                    <td><label id="lblRealSaleAmt8"></label></td>
                </tr>
                <tr>
                    <th class="tc">9</th>
                    <td><label id="lblStoreNm9"></label></td>
                    <td><label id="lblRealSaleAmt9"></label></td>
                </tr>
                <tr>
                    <th class="tc">10</th>
                    <td><label id="lblStoreNm10"></label></td>
                    <td><label id="lblRealSaleAmt10"></label></td>
                </tr>
                </tbody>
            </table>
        </div>
        <!--//순위테이블-->
    </div>
    <!--//메인컨텐츠-->

</div>

<script type="text/javascript">
    // 총 매장수
    var storeCntList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="storeCnt" items="${storeCntList}">
        var storeCntParam = {};
        storeCntParam.storeCntTotal = "${storeCnt.storeCntTotal}";
        storeCntList.push(storeCntParam);
    </c:forEach>

    // 총 포스수
    var posCntList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="posCnt" items="${posCntList}">
        var posCntParam = {};
        posCntParam.posCntTotal = "${posCnt.posCntTotal}";
        posCntList.push(posCntParam);
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
        saleProdWeekParam.saleDate = "${saleProdWeek.saleDate}";
        saleProdWeekParam.realSaleAmt = "${saleProdWeek.realSaleAmt}";
        saleProdWeekList.push(saleProdWeekParam);
    </c:forEach>

    // 매출 상위 가맹점
    var saleStoreWeekList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="saleStoreWeek" items="${saleStoreWeekList}">
        var saleStoreWeekParam = {};
        saleStoreWeekParam.storeNm = "${saleStoreWeek.storeNm}";
        saleStoreWeekParam.realSaleAmt = "${saleStoreWeek.realSaleAmt}";
        saleStoreWeekList.push(saleStoreWeekParam);
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
    function getData(numCount) {
        var data = new wijmo.collections.ObservableArray();
        for (var i = 0; i < numCount.length; i++) {
            data.push(getRandomData(numCount[i].saleDate, numCount[i].realSaleAmt));
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
        chart1.itemsSource = getData(saleWeekList); // 여기에 받아온 데이터 넣기
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
        chart2.itemsSource = getData(saleProdWeekList); // 여기에 받아온 데이터 넣기
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
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/application/main/mobileHedofcMain.js?ver=20210531.01" charset="utf-8"></script>

<%-- 게시판 상세 팝업 --%>
<c:import url="/WEB-INF/view/mobile/adi/board/board/mobileBoardDetail.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>