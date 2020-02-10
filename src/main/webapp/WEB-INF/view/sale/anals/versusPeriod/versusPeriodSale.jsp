<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="versusPeriodSaleCtrl" ng-init="init()">
        <ul>
            <%-- 분류별대비 탭 --%>
            <li>
                <a id="versusPeriodClassTab" href="#" ng-click="versusPeriodClassShow()"><s:message code="versusPeriod.cls"/></a>
            </li>
            <%-- 시간대비 탭 --%>
            <li>
                <a id="versusPeriodHourTab" href="#" ng-click="versusPeriodHourShow()"><s:message code="versusPeriod.hour"/></a>
            </li>
            <%-- 주간대비 탭 --%>
            <li>
                <a id="versusPeriodWeekTab" href="#" ng-click="versusPeriodWeekShow()"><s:message code="versusPeriod.week"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/versusPeriod/versusPeriodSale.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 분류별 대비 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/versusPeriod/cls/versusPeriodClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import> 

<%-- 시간대비 레이어  --%>
<c:import url="/WEB-INF/view/sale/anals/versusPeriod/hour/versusPeriodHour.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 주간대비 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/versusPeriod/week/versusPeriodWeek.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>