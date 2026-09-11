<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="orderEmpBensonTabCtrl" ng-init="init()">
        <ul>
            <%-- 기간별 탭 --%>
            <li>
                <a id="orderEmpBensonPeriodTab" href="#" class="on" ng-click="orderEmpBensonPeriodShow()"><s:message code="orderEmpBenson.orderEmpPeriod"/></a>
            </li>
            <%-- 일별 탭 --%>
            <li>
                <a id="orderEmpBensonDayTab" href="#" ng-click="orderEmpBensonDayShow()"><s:message code="orderEmpBenson.orderEmpDay"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/orderEmpBenson/orderEmpBensonTab.js?ver=20260910.02" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 기간별 레이어 --%>
<c:import url="/WEB-INF/view/sale/benson/orderEmpBenson/orderEmpBensonPeriod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 일자별 레이어 --%>
<c:import url="/WEB-INF/view/sale/benson/orderEmpBenson/orderEmpBensonDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- //탭페이지 레이어 --%>

<%-- 팝업 레이어 시작 --%>
<%-- 상품매출내역 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/prod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
