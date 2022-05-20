<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="nonSaleTabCtrl" ng-init="init()">
        <ul>
            <%-- 일별 탭 --%>
            <li>
                <a id="nonSaleDayTab" href="#" class="on" ng-click="nonSaleDayShow()"><s:message code="nonSale.day"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/nonSale/nonSaleTab.js?ver=20220516.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/nonSale/nonSaleDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>
