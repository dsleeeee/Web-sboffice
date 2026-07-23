<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="accountingDlvrCtrl" ng-init="init()">
        <ul>
            <li>
                <a id="accountingDlvrDayTab" href="#" class="on" ng-click="accountingDlvrDayShow()"><s:message code="accountingDlvr.day"/></a>
            </li>
            <li>
                <a id="accountingDlvrMonthTab" href="#" ng-click="accountingDlvrMonthShow()"><s:message code="accountingDlvr.month"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/accounting/accountingDlvr/accountingDlvr.js?ver=20260716.01" charset="utf-8"></script>

<c:import url="/WEB-INF/view/accounting/accountingDlvr/accountingDlvrDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<c:import url="/WEB-INF/view/accounting/accountingDlvr/accountingDlvrMonth.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
