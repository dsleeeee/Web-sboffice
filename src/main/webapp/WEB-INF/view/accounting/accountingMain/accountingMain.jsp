<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="accountingMainCtrl" ng-init="init()">
        <ul>
            <%-- 일별전송 --%>
            <li>
                <a id="acDayTransferTab" href="#" class="on" ng-click="acDayTransferShow()"><s:message code="accountingMain.dayTransfer"/></a>
            </li>
            <%-- 월별전송 --%>
            <li>
                <a id="acMonthTransferTab" href="#" ng-click="acMonthTransferShow()"><s:message code="accountingMain.monthTransfer"/></a>
            </li>
            <%-- 매장별항목관리 --%>
            <li>
                <a id="acStoreOptionTab" href="#" ng-click="acStoreOptionShow()"><s:message code="accountingMain.storeOption"/></a>
            </li>
            <%-- 공통코드관리 --%>
            <li>
                <a id="acComCodeTab" href="#" ng-click="acComCodeShow()"><s:message code="accountingMain.comCode"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/accounting/accountingMain/accountingMain.js?ver=20260713.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일별전송 레이어 --%>
<c:import url="/WEB-INF/view/accounting/accountingMain/acDayTransfer.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 월별전송 레이어 --%>
<c:import url="/WEB-INF/view/accounting/accountingMain/acMonthTransfer.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장별항목관리 레이어 --%>
<c:import url="/WEB-INF/view/accounting/accountingMain/acStoreOption.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 공통코드관리 레이어 --%>
<c:import url="/WEB-INF/view/accounting/accountingMain/acComCode.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>
