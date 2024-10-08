<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="con">
    <div class="tabType1" ng-controller="posSaleCtrl" ng-init="init()">
        <ul>
            <%-- 일자별 --%>
            <li>
                <a id="posDayTab" href="#" class="on" ng-click="posDayTabShow()"><s:message code="pos.day"/></a>
            </li>
            <%-- 요일별 --%>
            <li>
                <a id="posDayOfWeekTab" href="#" ng-click="posDayOfWeekTabShow()"><s:message code="pos.dayOfWeek"/></a>
            </li>
            <%-- 월별 --%>
            <li>
                <a id="posMonthTab" href="#" ng-click="posMonthTabShow()"><s:message code="pos.month"/></a>
            </li>
            <%-- 상품별 --%>
            <li>
                <a id="posProdTab" href="#" ng-click="posProdTabShow()"><s:message code="pos.prod"/></a>
            </li>
            <%-- 설정기간별 --%>
            <li>
                <a id="posDayPeriodTab" href="#" ng-click="posDayPeriodTabShow()"><s:message code="pos.dayPeriod"/></a>
            </li>
            <%-- 시간대별 --%>
            <li>
                <a id="posHourTab" href="#" ng-click="posHourTabShow()"><s:message code="pos.hour"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 시간대분류
    var timeSlotColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="timeSlotCol" items="${timeSlotColList}">
    var timeSlotParam   = {};
    timeSlotParam.name  = "${timeSlotCol.name}";
    timeSlotParam.value = "${timeSlotCol.value}";
    timeSlotColList.push(timeSlotParam);
    </c:forEach>

    var timeSlotCol    = '${timeSlotCol}';
    var arrTimeSlotCol = timeSlotCol.split(',');

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/pos/posSale.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일자별 --%>
<c:import url="/WEB-INF/view/sale/status/pos/posDay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 요일별 --%>
<c:import url="/WEB-INF/view/sale/status/pos/posDayOfWeek.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 월별 --%>
<c:import url="/WEB-INF/view/sale/status/pos/posMonth.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품별 --%>
<c:import url="/WEB-INF/view/sale/status/pos/posProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 설정기간별 --%>
<c:import url="/WEB-INF/view/sale/status/pos/posDayPeriod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 시간대별 --%>
<c:import url="/WEB-INF/view/sale/status/pos/posHour.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 팝업 레이어 시작 --%>
<%-- 상품매출내역 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/prod.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>