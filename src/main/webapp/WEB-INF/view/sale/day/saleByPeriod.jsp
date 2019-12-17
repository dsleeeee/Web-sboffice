<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="saleByPeriodCtrl" ng-init="init()">
        <ul>
            <%-- 일자별 탭 --%>
            <li>
                <a id="dayTab" href="#" class="on" ng-click="dayShow()"><s:message code="day.day"/></a>
            </li>
            <%-- 요일별 탭 --%>
            <li>
                <a id="dayOfWeekTab" href="#" ng-click="dayOfWeekShow()"><s:message code="day.dayOfWeek"/></a>
            </li>
            <%-- 월별 탭 --%>
            <li>
                <a id="monthTab" href="#" ng-click="monthShow()"><s:message code="day.month"/></a>
            </li>
            <%-- 설정기간별 탭 --%>
            <li>
                <a id="dayPeriodTab" href="#" ng-click="dayPeriodShow()"><s:message code="day.dayPeriod"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 결제수단
    var payColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
        var payParam       = {};
        payParam.payCd     = "${payCol.payCd}";
        payParam.payMethod = "${payCol.payMethod}";
        payColList.push(payParam);
    </c:forEach>

    var payCol    = '${payCol}';
    var arrPayCol = payCol.split(',');


    // 할인
    var dcColList = [];
    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="dcCol" items="${dcColList}">
        var dcParam      = {};
        dcParam.dcCd     = "${dcCol.dcCd}";
        dcParam.dcMethod = "${dcCol.dcMethod}";
        dcColList.push(dcParam);
    </c:forEach>

    var dcCol    = '${dcCol}';
    var arrDcCol = dcCol.split(',');


    // 포스
    var posColList = [];
    <%--javascript에서 사용할 할인 json 데이터 생성--%>
    <c:forEach var="posCol" items="${posColList}">
        var posParam      = {};
        posParam.posNo     = "${posCol.posNo}";
        posColList.push(posParam);
    </c:forEach>

    var posCol    = '${posCol}';
    var arrPosCol = posCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/saleByPeriod.js?ver=20191209" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일자별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/day/day.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 요일별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeek.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 월별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/month/month.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 설정기간별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayPeriod/dayPeriod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>