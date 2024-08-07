<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="sessionId" value="${param.sid}" />

<div ng-controller="systemMainCtrl">

    <!--메인컨텐츠-->
    <div class="mainCon">
        <!--총 매장수-->
        <div class="w50 fl br bb stat_purple1_1">
            <h2>총 매장수<span id="totalStoreCnt"></span></h2>
            <div>
                <p class="w25 fl tc"><span>오픈</span><span id="openStoreCnt"></span></p>
                <p class="w25 fl tc"><span>폐점</span><span id="closeStoreCnt"></span></p>
                <p class="w25 fl tc"><span>중지</span><span id="stopStoreCnt"></span></p>
                <p class="w25 fl tc"><span>데모</span><span id="demoStoreCnt"></span></p>
            </div>
        </div>
        <!--//총 매장수-->

        <!--총 포스수-->
        <div class="w50 fl bb stat_sky1_1">
            <h2>총 포스수<span id="totalPosCnt"></span></h2>
            <div>
                <p class="w25 fl tc"><span>오픈</span><span id="openPosCnt"></span></p>
                <p class="w25 fl tc"><span>폐점</span><span id="closePosCnt"></span></p>
                <p class="w25 fl tc"><span>중지</span><span id="stopPosCnt"></span></p>
                <p class="w25 fl tc"><span>데모</span><span id="demoPosCnt"></span></p>
            </div>
        </div>
        <!--//총 포스수-->

        <!--주간매출-->
        <div class="w70 fl br bb graph1">
            <h2>주간매출 (매장수/포스수)<div class="circle"><span class="blue">매장수</span><span class="sky">포스수</span></div></h2>
            <div class="wizWrap" id="chart1" style="width:100%; height:250px;"></div>
        </div>
        <!--//주간매출-->

        <!--공지사항-->
        <div class="w30 fl bb notice1" style="overflow-y: auto;">
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

        <!--주간 POS 설치현황-->
        <div class="w70 fl br bb graph1">
            <h2>주간 POS 설치현황<div class="square"><span class="blue">신규</span><span class="sky">재설치</span></div></h2>
            <div class="wizWrap" id="chart2" style="width:100%; height:250px;"></div>
        </div>
        <!--//주간 POS 설치현황-->

        <!--날씨-->
        <div class="w30 fl bb weather">
        </div>
        <%--<div class="w30 fl bb weather">--%>
            <%--<div class="today">--%>
                <%--<h2 class="hidden">날씨</h2>--%>
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
                    <%--</p>--%>
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
            <%--</ul>--%>
        <%--</div>--%>
        <!--//날씨-->

        <!--순위테이블-->
        <div class="w100 fl mainTbl1">
            <h2>주간 POS 설치 상위 대리점</h2>
            <table>
                <colgroup>
                    <col class="w10" />
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w20" />
                </colgroup>
                <thead>
                    <tr>
                        <th class="tc">순위</th>
                        <th class="tc">구분</th>
                        <th class="tc">총판 / 대리점명</th>
                        <th class="tc">신규설치 매장수</th>
                        <th class="tc">폐점수</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th class="tc">1</th>
                        <td><label id="lblOrgnFg1"></label></td>
                        <td><label id="lblAgencyNm1"></label></td>
                        <td><label id="lblInstStoreCntNew1"></label></td>
                        <td><label id="lblStoreCntClose1"></label></td>
                    </tr>
                    <tr>
                        <th class="tc">2</th>
                        <td><label id="lblOrgnFg2"></label></td>
                        <td><label id="lblAgencyNm2"></label></td>
                        <td><label id="lblInstStoreCntNew2"></label></td>
                        <td><label id="lblStoreCntClose2"></label></td>
                    </tr>
                    <tr>
                        <th class="tc">3</th>
                        <td><label id="lblOrgnFg3"></label></td>
                        <td><label id="lblAgencyNm3"></label></td>
                        <td><label id="lblInstStoreCntNew3"></label></td>
                        <td><label id="lblStoreCntClose3"></label></td>
                    </tr>
                    <tr>
                        <th class="tc">4</th>
                        <td><label id="lblOrgnFg4"></label></td>
                        <td><label id="lblAgencyNm4"></label></td>
                        <td><label id="lblInstStoreCntNew4"></label></td>
                        <td><label id="lblStoreCntClose4"></label></td>
                    </tr>
                    <tr>
                        <th class="tc">5</th>
                        <td><label id="lblOrgnFg5"></label></td>
                        <td><label id="lblAgencyNm5"></label></td>
                        <td><label id="lblInstStoreCntNew5"></label></td>
                        <td><label id="lblStoreCntClose5"></label></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <%--<!--//순위테이블-->--%>
    </div>
    <!--//메인컨텐츠-->

    <!--팝업-->
    <div>
        <c:forEach var="board" items="${boardList}" varStatus="status">
            <div id="divDimmed${status.index}" class="fullDimmed" style="display: none;"></div>
            <div id="divPopup${status.index}" class="layer" style="display: none;">
                <div class="layer_inner" style="position:absolute; left:50%; top:50%;  transform: translate(-50%, -50%); text-align: center;">
                    <!--layerContent-->
                    <div class="title" style="max-width:1000px;max-width:700px;max-height:700px;">
                        <p class="tit" id="popTitle${status.index}">
                                ${board.title}</p>
                        <div class="con" style="overflow: auto;max-width:560px;max-height:550px;">
                            <div id="boardContent${status.index}"></div>
                                ${board.content}
                        </div>
                        <div class="btnSet">
                            <span><a href="#" class="btn_blue" id="btnCloseToday${status.index}">오늘하루 열지않기</a></span>
                            <span><a href="#" class="btn_blue" id="btnClose${status.index}" ><s:message code="cmm.close" /></a></span>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <!--팝업-->

</div>

<script type="text/javascript">
    // 공지사항 more 페이지 이동시 권한체크
    var board_auth = "${board_auth}";

    // 총 매장수
    var storeCntList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="storeCnt" items="${storeCntList}">
        var storeCntParam = {};
        storeCntParam.storeCntTotal = "${storeCnt.storeCntTotal}";
        storeCntParam.storeCnt1 = "${storeCnt.storeCnt1}";
        storeCntParam.storeCnt2 = "${storeCnt.storeCnt2}";
        storeCntParam.storeCnt3 = "${storeCnt.storeCnt3}";
        storeCntParam.storeCnt9 = "${storeCnt.storeCnt9}";
        storeCntList.push(storeCntParam);
    </c:forEach>

    // 총 포스수
    var posCntList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="posCnt" items="${posCntList}">
        var posCntParam = {};
        posCntParam.posCntTotal = "${posCnt.posCntTotal}";
        posCntParam.posCnt1 = "${posCnt.posCnt1}";
        posCntParam.posCnt2 = "${posCnt.posCnt2}";
        posCntParam.posCnt3 = "${posCnt.posCnt3}";
        posCntParam.posCnt9 = "${posCnt.posCnt9}";
        posCntList.push(posCntParam);
    </c:forEach>

    // 주간매출(매장수/포스수)
    var weekSaleList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="weekSale" items="${weekSaleList}">
        var weekSaleParam = {};
        weekSaleParam.saleDate = "${weekSale.saleDate}";
        weekSaleParam.storeCnt = "${weekSale.storeCnt}";
        weekSaleParam.posCnt = "${weekSale.posCnt}";
        weekSaleList.push(weekSaleParam);
    </c:forEach>

    // 공지사항
    var noticeList = '${noticeList}';

    // 주간 POS 설치현황(신규설치/재설치)
    var weekPosInstList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="weekPosInst" items="${weekPosInstList}">
        var weekPosInstParam = {};
        weekPosInstParam.instInsDt = "${weekPosInst.instInsDt}";
        weekPosInstParam.instStoreCntNew = "${weekPosInst.instStoreCntNew}";
        weekPosInstParam.instStoreCntRe = "${weekPosInst.instStoreCntRe}";
        weekPosInstList.push(weekPosInstParam);
    </c:forEach>

    // 주간 POS 설치 상위 대리점
    var weekPosInstTopList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="weekPosInstTop" items="${weekPosInstTopList}">
        var weekPosInstTopParam = {};
        weekPosInstTopParam.orgnFg = "${weekPosInstTop.orgnFg}";
        weekPosInstTopParam.agencyNm = "${weekPosInstTop.agencyNm}";
        weekPosInstTopParam.instStoreCntNew = "${weekPosInstTop.instStoreCntNew}";
        weekPosInstTopParam.storeCntClose = "${weekPosInstTop.storeCntClose}";
        weekPosInstTopList.push(weekPosInstTopParam);
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
                data.push(getRandomData(numCount[i].saleDate, numCount[i].storeCnt, numCount[i].posCnt));
            } else if(gubun == "chart2") {
                data.push(getRandomData(numCount[i].instInsDt, numCount[i].instStoreCntNew, numCount[i].instStoreCntRe));
            }
        }
        return data;
    }
    function getRandomData(idx ,y0, y1) {
        return {
            x: idx,
            y0: getRandomValue(y0),
            y1: getRandomValue(y1)
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

        <%-- 주간매출(매장수/포스수) --%>
        var chart1 = new wijmo.chart.FlexChart('#chart1');
        chart1.beginUpdate();
        chart1.chartType = wijmo.chart.ChartType.Line;
        chart1.itemsSource = getData(weekSaleList, 'chart1'); // 여기에 받아온 데이터 넣기
        chart1.bindingX = 'x';
        chart1.palette = ['#93cbfc', '#90f0fc'];

        for (var i = 0; i < 2; i++) {
            var series = new wijmo.chart.Series();
            series.binding = 'y' + i;
            chart1.series.push(series);
        }
        chart1.endUpdate();

        <%-- 주간 POS 설치현황(신규설치/재설치) --%>
        var chart2 = new wijmo.chart.FlexChart('#chart2');
        chart2.beginUpdate();
        chart2.chartType = wijmo.chart.ChartType.Column;
        chart2.itemsSource = getData(weekPosInstList, 'chart2'); // 여기에 받아온 데이터 넣기
        chart2.bindingX = 'x';
        chart2.chartType = parseInt(0);
        chart2.stacking = parseInt(1);
        chart2.rotated = false;
        chart2.palette = ['#93cbfc', '#90f0fc'];

        for (var i = 0; i < 2; i++) {
            var series = new wijmo.chart.Series();
            series.binding = 'y' + i;
            chart2.series.push(series);
        }
        chart2.endUpdate();
    });
    <%-- // wijmo flexChart --%>


    // 1. 쿠키 만들기
    function setCookie(name, value, expiredays) {
        var today = new Date();
        today.setDate(today.getDate() + expiredays);
        document.cookie = name + '=' + escape(value) + '; path=/; expires=' + today.toGMTString() + ';'
    }

    //2. 쿠키 가져오기
    function getCookie(name){
        var cName = name + "=";
        var x = 0;
        while ( x <= document.cookie.length )
        {
            var y = (x+cName.length);
            if ( document.cookie.substring( x, y ) == cName )
            {
                if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                    endOfCookie = document.cookie.length;
                return unescape( document.cookie.substring( y, endOfCookie ) );
            }
            x = document.cookie.indexOf( " ", x ) + 1;
            if ( x == 0 )
                break;
        }
        return "";
    }

    <c:forEach var="board" items="${boardList}" varStatus="status">
    // 쿠키체크 후 팝업 띄우기
    if(getCookie("notPopupSystem" + orgnCd + "${status.index}")!="Y") {
        $("#divDimmed${status.index}").css('display', 'block');
        $("#divPopup${status.index}").css('display', 'block');
    }

    $("#btnCloseToday${status.index}").click(function(){
        setCookie('notPopupSystem' + orgnCd + '${status.index}','Y', 1);
        $("#divDimmed${status.index}").css('display', 'none');
        $("#divPopup${status.index}").css('display', 'none');
    });

    $("#btnClose${status.index}").click(function(){
        $("#divDimmed${status.index}").css('display', 'none');
        $("#divPopup${status.index}").css('display', 'none');
    });
    $(".btn_close").click(function(){
        $("#divDimmed${status.index}").css('display', 'none');
        $("#divPopup${status.index}").css('display', 'none');
    });
    </c:forEach>
</script>

<script type="text/javascript" src="/resource/solbipos/js/application/main/systemMain.js?ver=20210218.06" charset="utf-8"></script>

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