<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="weightTabCtrl" ng-init="init()">
        <ul>
            <%-- 중량별 탭 --%>
            <li>
                <a id="weightTab" href="#" class="on" ng-click="weightShow()"><s:message code="weight.weightSale"/></a>
            </li>
            <%-- 상품별 탭 --%>
            <li>
                <a id="weightProdTab" href="#" ng-click="weightProdShow()"><s:message code="weight.weightProd"/></a>
            </li>
            <%-- 일자별 탭 --%>
            <li>
                <a id="weightDayTab" href="#" ng-click="weightDayShow()"><s:message code="weight.weightDay"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/weight/weightTab.js?ver=20220516.02" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 중량별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/weight/weight.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 일자별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/weight/weightDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품별 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/weight/weightProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>
