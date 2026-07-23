<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="timeSaleBensonCtrl" ng-init="init()">
        <ul>
            <li>
                <a id="timeSaleBensonDayTab" href="#" class="on" ng-click="timeSaleBensonDayShow()"><s:message code="timeSaleBenson.day"/></a>
            </li>
            <li>
                <a id="timeSaleBensonMonthTab" href="#" ng-click="timeSaleBensonMonthShow()"><s:message code="timeSaleBenson.month"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/timeSaleBenson/timeSaleBenson.js?ver=20260720.01" charset="utf-8"></script>

<c:import url="/WEB-INF/view/sale/anals/timeSaleBenson/timeSaleBensonDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<c:import url="/WEB-INF/view/sale/anals/timeSaleBenson/timeSaleBensonMonth.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
