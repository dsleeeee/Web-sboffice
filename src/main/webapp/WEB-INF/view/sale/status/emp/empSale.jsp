<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="empSaleCtrl" ng-init="init()">
        <ul>
            <%-- 일자별 탭 --%>
            <li>
                <a id="empDayTab" href="#" class="on" ng-click="empDayShow()"><s:message code="empsale.day"/></a>
            </li>
            <%-- 요일별 탭 --%>
            <li>
                <a id="empDayOfWeekTab" href="#" ng-click="empDayOfWeekShow()"><s:message code="empsale.dayofweek"/></a>
            </li>
            <%-- 월별 탭 --%>
            <li>
                <a id="empMonthTab" href="#" ng-click="empMonthShow()"><s:message code="empsale.month"/></a>
            </li>
            <%-- 설정기간별 탭 --%>
            <li>
                <a id="empDayPeriodTab" href="#" ng-click="empDayPeriodShow()"><s:message code="empsale.dayPeriod"/></a>
            </li>
            <%-- 설정기간별 탭 --%>
            <li>
                <a id="empPosTab" href="#" ng-click="empPosShow()"><s:message code="empsale.pos"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/emp/empSale.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일자별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/emp/day/empDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import> 

<%-- 요일별 레이어  --%>
<c:import url="/WEB-INF/view/sale/status/emp/dayOfWeek/empDayOfWeek.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 월별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/emp/month/empMonth.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 설정기간별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/emp/dayPeriod/empDayPeriod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포스별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/emp/pos/empPos.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>


<%-- 팝업 레이어 시작 --%>
<%-- 상품매출내역 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/prod.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>