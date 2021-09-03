<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="orderChannelCtrl" ng-init="init()">
        <ul>
            <%-- 기간별 탭 --%>
            <li>
                <a id="orderChannelPeriodTab" href="#" class="on" ng-click="orderChannelPeriodShow()"><s:message code="orderChannel.period"/></a>
            </li>
            <%-- 일별 탭 --%>
            <li>
                <a id="orderChannelDayTab" href="#" ng-click="orderChannelDayShow()"><s:message code="orderChannel.day"/></a>
            </li>
            <%-- 월별 탭 --%>
            <li>
                <a id="orderChannelMonthTab" href="#" ng-click="orderChannelMonthShow()"><s:message code="orderChannel.month"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/dlvr/orderChannel/orderChannel.js?ver=20210903.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 기간별 레이어 --%>
<c:import url="/WEB-INF/view/sale/dlvr/orderChannel/orderChannelPeriod.jsp">
</c:import>

<%-- 일별 레이어 --%>
<c:import url="/WEB-INF/view/sale/dlvr/orderChannel/orderChannelDay.jsp">
</c:import>

<%-- 월별 레이어 --%>
<c:import url="/WEB-INF/view/sale/dlvr/orderChannel/orderChannelMonth.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>