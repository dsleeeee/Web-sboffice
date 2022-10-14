<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="storeChannelCtrl" ng-init="init()">
        <ul>
            <%-- 기간별 탭 --%>
            <li>
                <a id="storeChannelPeriodTab" href="#" class="on" ng-click="storeChannelPeriodShow()"><s:message code="storeChannel.period"/></a>
            </li>
            <%-- 일별 탭 --%>
            <li>
                <a id="storeChannelDayTab" href="#" ng-click="storeChannelDayShow()"><s:message code="storeChannel.day"/></a>
            </li>
            <%-- 월별 탭 --%>
            <li>
                <a id="storeChannelMonthTab" href="#" ng-click="storeChannelMonthShow()"><s:message code="storeChannel.month"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/store/storeChannel/storeChannel.js?ver=20210903.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 기간별 레이어 --%>
<c:import url="/WEB-INF/view/sale/store/storeChannel/storeChannelPeriod.jsp">
</c:import>

<%-- 일별 레이어 --%>
<c:import url="/WEB-INF/view/sale/store/storeChannel/storeChannelDay.jsp">
</c:import>

<%-- 월별 레이어 --%>
<c:import url="/WEB-INF/view/sale/store/storeChannel/storeChannelMonth.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>

<%-- 팝업 --%>
<%-- 일별 상세 --%>
<c:import url="/WEB-INF/view/sale/store/storeChannel/storeChannelDtl.jsp">
</c:import>