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

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/saleByPeriod.js?ver=20191119.10" charset="utf-8"></script>

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