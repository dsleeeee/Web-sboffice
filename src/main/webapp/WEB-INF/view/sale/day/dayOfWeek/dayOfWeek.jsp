<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/dayOfWeek/dayOfWeek"/>

<div class="con" id="dayOfWeekView" name="dayOfWeekView">
    <div class="tabType1" ng-controller="dayOfWeekCtrl" ng-init="init()">
        <ul>
            <%-- 주간종합 탭 --%>
            <li>
                <a id="dayOfWeekTotalTab" href="#" class="on" ng-click="dayOfWeekTotalShow()"><s:message code="dayofweek.total"/></a>
            </li>
            <%-- 할인구분별 탭 --%>
            <li>
                <a id="dayOfWeekDcTab" href="#" ng-click="dayOfWeekDcShow()"><s:message code="dayofweek.dc"/></a>
            </li>
            <%-- 과면세별 탭 --%>
            <li>
                <a id="dayOfWeekTaxTab" href="#" ng-click="dayOfWeekTaxShow()"><s:message code="dayofweek.tax"/></a>
            </li>
            <%-- 시간대별 탭 --%>
            <li>
                <a id="dayOfWeekTimeTab" href="#" ng-click="dayOfWeekTimeShow()"><s:message code="dayofweek.time"/></a>
            </li>
            <%-- 상품분류별 탭 --%>
            <li>
                <a id="dayOfWeekProdClassTab" href="#" ng-click="dayOfWeekProdClassShow()"><s:message code="dayofweek.prodClass"/></a>
            </li>
            <%-- 코너별 탭 --%>
            <li>
                <a id="dayOfWeekCornerTab" href="#" ng-click="dayOfWeekCornerShow()"><s:message code="dayofweek.corner"/></a>
            </li>
            <%-- 외식테이블 탭 --%>
            <li <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if> >
                <a id="dayOfWeekTableTab" href="#" ng-click="dayOfWeekTableShow()"><s:message code="dayofweek.table"/></a>
            </li>
            <%-- 포스별 탭 --%>
            <li>
                <a id="dayOfWeekPosTab" href="#" ng-click="dayOfWeekPosShow()"><s:message code="dayofweek.pos"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayOfWeek/dayOfWeek.js?ver=20191119.09" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 주간종합 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekTotal.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 할인구분별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekDc.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 과면세별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekTax.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 시간대별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekTime.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekProdClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 코너별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekCorner.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 외식테이블 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekTable.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포스별 레이어 --%>
<c:import url="/WEB-INF/view/sale/day/dayOfWeek/dayOfWeekPos.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>