<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="prodSaleCtrl" ng-init="init()">
        <ul>
            <%-- 분류별상품 탭 --%>
            <li>
                <a id="prodClassTab" href="#" class="on" ng-click="prodClassShow()"><s:message code="prodsale.class"/></a>
            </li>
            <%-- 상품매출순위 탭 --%>
            <li>
                <a id="prodRankTab" href="#" ng-click="prodRankShow()"><s:message code="prodsale.rank"/></a>
            </li>
            <%-- 결제수단별 탭 --%>
            <li>
                <a id="prodPayFgTab" href="#" ng-click="prodPayFgShow()"><s:message code="prodsale.fayfg"/></a>
            </li>
            <%-- 시간대별 탭 --%>
            <li>
                <a id="prodHourTab" href="#" ng-click="prodHourShow()"><s:message code="prodsale.hour"/></a>
            </li>
            <%-- 일자별 탭 --%>
            <li>
                <a id="prodDayTab" href="#" ng-click="prodDayShow()"><s:message code="prodsale.day"/></a>
            </li>
            <%-- 포스별 탭 --%>
            <li>
                <a id="prodPosTab" href="#" ng-click="prodPosShow()"><s:message code="prodsale.pos"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/prod/prodSale.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 분류별상품 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/prod/class/prodClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import> 

<%-- 상품매출순위 레이어  --%>
<c:import url="/WEB-INF/view/sale/status/prod/rank/prodRank.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 결제수단별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/prod/payfg/prodPayFg.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 시간대별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/prod/hour/prodHour.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 일자별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/prod/day/prodDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포스별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/prod/pos/prodPos.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>