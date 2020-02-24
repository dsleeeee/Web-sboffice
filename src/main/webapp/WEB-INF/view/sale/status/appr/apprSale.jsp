<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="con">
    <div class="tabType1" ng-controller="apprSaleCtrl" ng-init="init()">
        <ul>
            <%-- 승인현황 --%>
            <li>
                <a id="payMethodTab" href="#" class="on" ng-click="payMethodTabShow()"><s:message code="dailyReport.appr"/></a>
            </li>
            <%-- 매입현황 --%>
            <li>
                <a id="apprAcquireTab" href="#" ng-click="apprAcquireTabShow()"><s:message code="dailyReport.acquire"/></a>
            </li>
            <%-- 제휴카드 --%>
       <!--     <li>
                <a id="apprCoprtnTab" href="#" ng-click="apprCoprtnTabShow()"><s:message code="corner.month"/></a>
            </li>
            <%-- 현금영수증 --%>
            <li>
                <a id="apprCashBillTab" href="#" ng-click="apprCashBillTabShow()"><s:message code="corner.dayPeriod"/></a>
           </li>   --> 
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/appr/apprSale.js" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 승인현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/payMethod/payMethod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매입현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/acquire/acquire.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 제휴카드별 --%>
<%-- <c:import url="/WEB-INF/view/sale/status/appr/coprtn/coprtn.jsp"> --%>
<%--     <c:param name="menuCd" value="${menuCd}"/> --%>
<%--     <c:param name="menuNm" value="${menuNm}"/> --%>
<%-- </c:import> --%>

<%-- 현금영수증별 --%>
<%-- <c:import url="/WEB-INF/view/sale/status/appr/cashBill/cashBill.jsp"> --%>
<%--     <c:param name="menuCd" value="${menuCd}"/> --%>
<%--     <c:param name="menuNm" value="${menuNm}"/> --%>
<%-- </c:import> --%>

