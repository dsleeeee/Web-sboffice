<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<input type="hidden" id="HqOfficeCd" name="HqOfficeCd" ng-model="HqOfficeCd" value="${gvHqOfficeCd}"/>
<div class="con">
    <div class="tabType1" ng-controller="cornerSaleCtrl" ng-init="init()">
        <ul>
            <%-- 일자별 --%>
            <li>
                <a id="cornerDayTab" href="#" class="on" ng-click="cornerDayTabShow()"><s:message code="corner.day"/></a>
            </li>
            <%-- 요일별 --%>
            <li>
                <a id="cornerDayOfWeekTab" href="#" ng-click="cornerDayOfWeekTabShow()"><s:message code="corner.dayOfWeek"/></a>
            </li>
            <%-- 월별 --%>
            <li>
                <a id="cornerMonthTab" href="#" ng-click="cornerMonthTabShow()"><s:message code="corner.month"/></a>
            </li>
<%--             설정기간별 --%>
            <li>
                <a id="cornerDayPeriodTab" href="#" ng-click="cornerDayPeriodTabShow()"><s:message code="corner.dayPeriod"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/corner/cornerSale.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일자별 --%>
<c:import url="/WEB-INF/view/sale/status/corner/day/day.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 요일별 --%>
<c:import url="/WEB-INF/view/sale/status/corner/dayOfWeek/dayOfWeek.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 월별 --%>
<c:import url="/WEB-INF/view/sale/status/corner/month/month.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 설정기간별 --%>
<c:import url="/WEB-INF/view/sale/status/corner/dayPeriod/dayPeriod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
