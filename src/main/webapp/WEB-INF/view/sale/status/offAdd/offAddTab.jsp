<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="offAddCtrl" ng-init="init()">
        <ul>
            <%-- 일별 탭 --%>
            <li>
                <a id="offAddDayTab" href="#" class="on" ng-click="offAddDayShow()"><s:message code="offAdd.day"/></a>
            </li>
            <%-- 월별 탭 --%>
            <li>
                <a id="offAddMonthTab" href="#" ng-click="offAddMonthShow()"><s:message code="offAdd.month"/></a>
            </li>
            <%-- 상품별 탭 --%>
            <li>
                <a id="offAddProdTab" href="#" ng-click="offAddProdShow()"><s:message code="offAdd.prod"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/offAdd/offAddTab.js?ver=20220311.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/offAdd/offAddDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 월별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/offAdd/offAddMonth.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/offAdd/offAddProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>
