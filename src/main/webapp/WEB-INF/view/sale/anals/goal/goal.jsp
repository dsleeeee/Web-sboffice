<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<input type="hidden" id="HqOfficeCd" name="HqOfficeCd" ng-model="HqOfficeCd" value="${gvHqOfficeCd}"/>

<div class="con">
    <div class="tabType1" ng-controller="goalCtrl" ng-init="init()">
        <ul>
            <%-- 일자별목표대배매출 탭 --%>
            <li>
                <a id="goalDayTab" href="#" class="on" ng-click="goalDayShow()"><s:message code="goal.goalDay.versusGoalSale"/></a>
            </li>
            <%-- 월별목표대배매출 탭 --%>
            <li>
                <a id="goalMonthTab" href="#" ng-click="goalMonthShow()"><s:message code="goal.goalMonth.versusGoalSale"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/goal/goal.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일자별목표대배매출 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/goal/day/goalDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import> 

<%-- 월별목표대배매출 레이어  --%>
<c:import url="/WEB-INF/view/sale/anals/goal/month/goalMonth.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>