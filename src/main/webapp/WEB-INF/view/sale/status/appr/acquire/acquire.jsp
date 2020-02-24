<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="con" id="apprAcquireView" name="apprAcquireView" ng-controller="apprAcquireCtrl" ng-init="init()">
    <div class="tabType1">
        <ul>
            <%-- 신용카드 --%>
            <li>
                <a id="apprAcquireCardTab" href="#" class="on" ng-click="apprAcquireCardTabShow()"><s:message code="dailyReport.apprCard"/></a>
            </li>
            <%-- Mpay --%>
            <li>
                <a id="apprAcquireMpayTab" href="#" ng-click="apprAcquireMpayTabShow()"><s:message code="dailyReport.apprMpay"/></a>
            </li>
            <%-- Mcoupn --%>
            <li>
                <a id="apprAcquireMcouponTab" href="#" ng-click="apprAcquireMcouponTabShow()"><s:message code="dailyReport.apprMcoupn"/></a>
            </li>
            <%-- Ncard --%>
            <li>
                <a id="apprAcquireNcardTab" href="#" ng-click="apprAcquireNcardTabShow()"><s:message code="dailyReport.apprNcard"/></a>
            </li>
        </ul>
    </div>
        <%-- 탭페이지 레이어 시작 --%>
        <%-- 신용카드 매출현황 --%>
        <c:import url="/WEB-INF/view/sale/status/appr/acquire/card/card.jsp">
            <c:param name="menuCd" value="${menuCd}"/>
            <c:param name="menuNm" value="${menuNm}"/>
        </c:import>
        
        <%-- Mpay 매출현황 --%>
        <c:import url="/WEB-INF/view/sale/status/appr/acquire/mpay/mpay.jsp">
            <c:param name="menuCd" value="${menuCd}"/>
            <c:param name="menuNm" value="${menuNm}"/>
        </c:import>
        
        <%-- Mcoupn 매출현황 --%>
        <c:import url="/WEB-INF/view/sale/status/appr/acquire/mcoupon/mcoupon.jsp">
            <c:param name="menuCd" value="${menuCd}"/>
            <c:param name="menuNm" value="${menuNm}"/>
        </c:import>
        
        <%-- Ncard 매출현황 --%>
        <c:import url="/WEB-INF/view/sale/status/appr/acquire/ncard/ncard.jsp">
            <c:param name="menuCd" value="${menuCd}"/>
            <c:param name="menuNm" value="${menuNm}"/>
        </c:import>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/appr/acquire/acquire.js" charset="utf-8"></script>

