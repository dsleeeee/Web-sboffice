<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/dayPeriod/dayPeriod"/>

<div class="con" id="dayPeriodView" name="dayPeriodView">
    <div class="tabType1" ng-controller="dayPeriodCtrl" ng-init="init()">
        <ul>
            <%-- 시간대별 탭 --%>
            <li>
                <a id="dayPeriodTimeTab" href="#" class="on" ng-click="dayPeriodTimeShow()"><s:message code="dayPeriod.time"/></a>
            </li>
            <%-- 상품분류별 탭 --%>
            <li>
                <a id="dayPeriodProdClassTab" href="#" ng-click="dayPeriodProdClassShow()"><s:message code="dayPeriod.prodClass"/></a>
            </li>
            <%-- 외식테이블 탭 --%>
            <li <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if> >
                <a id="dayPeriodTableTab" href="#" ng-click="dayPeriodTableShow()"><s:message code="dayPeriod.table"/></a>
            </li>
            <%-- 코너별 탭 --%>
            <li>
                <a id="dayPeriodCornerTab" href="#" ng-click="dayPeriodCornerShow()"><s:message code="dayPeriod.corner"/></a>
            </li>
            <%-- 상품권별 탭 --%>
            <li>
                <a id="dayPeriodGiftTab" href="#" ng-click="dayPeriodGiftShow()"><s:message code="dayPeriod.gift"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayPeriod/dayPeriod.js?ver=20200130.06" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 시간대별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayPeriod/dayPeriodTime.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayPeriod/dayPeriodProdClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 외식테이블 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayPeriod/dayPeriodTable.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 코너별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayPeriod/dayPeriodCorner.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품권별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayPeriod/dayPeriodGift.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>