<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="storeOpenCloseBensonCtrl" ng-init="init()">
        <ul>
            <%-- 일별 탭 --%>
            <li>
                <a id="storeOpenCloseBensonDayTab" href="#" class="on" ng-click="storeOpenCloseBensonDayShow()"><s:message code="storeOpenCloseBenson.day"/></a>
            </li>
            <%-- 월별 탭 --%>
            <li>
                <a id="storeOpenCloseBensonMonthTab" href="#" ng-click="storeOpenCloseBensonMonthShow()"><s:message code="storeOpenCloseBenson.month"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">

    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/storeOpenCloseBenson/storeOpenCloseBenson.js?ver=20260909.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일별 레이어 --%>
<c:import url="/WEB-INF/view/sale/benson/storeOpenCloseBenson/storeOpenCloseBensonDay.jsp">
</c:import>

<%-- 월별 레이어 --%>
<c:import url="/WEB-INF/view/sale/benson/storeOpenCloseBenson/storeOpenCloseBensonMonth.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>

<%-- 본사 상세정보 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/posExcclc/posExcclc/posExcclcDetail.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
