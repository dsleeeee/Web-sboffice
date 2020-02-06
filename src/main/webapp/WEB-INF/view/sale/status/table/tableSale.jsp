<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="tableCtrl" ng-init="init()">
        <ul>
            <%-- 일자별 탭 --%>
            <li>
                <a id="tableDayTab" href="#" class="on" ng-click="tableDayShow()"><s:message code="tableDay.tableDaySale"/></a>
            </li>
            <%-- 요일별 탭 --%>
            <li>
                <a id="tableDayOfWeekTab" href="#" ng-click="tableDayOfWeekShow()"><s:message code="tableDayOfWeek.tableDayOfWeekSale"/></a>
            </li>
            <%-- 월별 탭 --%>
            <li>
                <a id="tableMonthTab" href="#" ng-click="tableMonthShow()"><s:message code="tableMonth.tableMonthSale"/></a>
            </li>
            <%-- 설정기간별 탭 --%>
            <li>
                <a id="tableDayPeriodTab" href="#" ng-click="tableDayPeriodShow()"><s:message code="tableDayPeriod.tableDayPeriodSale"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/table/tableSale.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일자별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/table/day/day.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 요일별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/table/dayOfWeek/dayOfWeek.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 월별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/table/month/month.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 설정기간별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/table/dayPeriod/dayPeriod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>