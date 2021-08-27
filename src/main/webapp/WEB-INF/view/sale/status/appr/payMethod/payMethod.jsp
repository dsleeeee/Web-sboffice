<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="con" id="payMethodView" name="payMethodView" ng-controller="payMethodCtrl" ng-init="init()" style="display: none;">
    <div class="tabType1">
        <ul>
            <%-- 신용카드 --%>
            <li>
                <a id="apprCardTab" href="#" class="on" ng-click="apprCardTabShow()"><s:message code="store.pay1"/></a>
            </li>
            <%-- 현금 --%>
            <li>
                <a id="apprCashTab" href="#" ng-click="apprCashTabShow()"><s:message code="store.pay2"/></a>
            </li>
            <%-- Payco --%>
            <li>
                <a id="apprPaycoTab" href="#" ng-click="apprPaycoTabShow()"><s:message code="dailyReport.apprPayco"/></a>
            </li>
            <%-- Mpay --%>
            <li>
                <a id="apprMpayTab" href="#" ng-click="apprMpayTabShow()"><s:message code="dailyReport.apprMpay"/></a>
            </li>
            <%-- Mcoupn --%>
            <li>
                <a id="apprMcouponTab" href="#" ng-click="apprMcouponTabShow()"><s:message code="dailyReport.apprMcoupn"/></a>
            </li>
            <%-- Partner --%>
            <li>
                <a id="apprPartnerTab" href="#" ng-click="apprPartnerTabShow()"><s:message code="dailyReport.apprPartner"/></a>
            </li>
            <%-- Ncard --%>
            <li>
                <a id="apprNcardTab" href="#" ng-click="apprNcardTabShow()"><s:message code="dailyReport.apprNcard"/></a>
            </li>
            <%-- Ncash --%>
            <li>
                <a id="apprNcashTab" href="#" ng-click="apprNcashTabShow()"><s:message code="dailyReport.apprNcash"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/appr/payMethod/payMethod.js" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 신용카드 승인현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/payMethod/card/card.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 현금 승인현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/payMethod/cash/cash.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- Payco 승인현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/payMethod/payco/payco.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- Mpay 승인현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/payMethod/mpay/mpay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- Mcoupn 승인현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/payMethod/mcoupon/mcoupon.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- Partner 승인현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/payMethod/partner/partner.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- Ncard 승인현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/payMethod/ncard/ncard.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- Ncash 승인현황 --%>
<c:import url="/WEB-INF/view/sale/status/appr/payMethod/ncash/ncash.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- /탭페이지 레이어 종료 --%>


<%-- 팝업 레이어 시작 --%>
<%-- 매장현황 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/appr/apprCard.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장현황 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/appr/apprMcoupon.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장현황 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/appr/apprMpay.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장현황 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/appr/apprNcard.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- /팝업 레이어 종료 --%>

