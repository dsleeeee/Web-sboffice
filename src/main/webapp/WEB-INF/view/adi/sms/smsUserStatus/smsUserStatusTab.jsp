<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="con">
    <div class="tabType1" ng-controller="smsUserStatusTabCtrl" ng-init="init()">
        <ul>
            <%-- SMS사용자 탭 --%>
            <li>
                <a id="smsUserTab" href="#" class="on" ng-click="smsUserShow()"><s:message code="smsUserStatus.smsUser"/></a>
            </li>
            <%-- 발신번호 탭 --%>
            <li>
                <a id="sendTelNoTab" href="#" ng-click="sendTelNoShow()"><s:message code="smsUserStatus.sendTelNo"/></a>
            </li>
            <%-- 충전내역 탭 --%>
            <li>
                <a id="smsChargeStatusTab" href="#" ng-click="smsChargeStatusShow()"><s:message code="smsUserStatus.smsChargeStatus"/></a>
            </li>
            <%-- 전송이력 탭 --%>
            <li>
                <a id="smsSendHistTab" href="#" ng-click="smsSendHistShow()"><s:message code="smsUserStatus.smsSendHist"/></a>
            </li>
        </ul>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsUserStatus/smsUserStatusTab.js?ver=20260807.02" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- SMS 사용자 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsUserStatus/smsUser.jsp">
</c:import>

<%-- 발신번호 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsUserStatus/sendTelNo.jsp">
</c:import>

<%-- 충전내역 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsUserStatus/smsChargeStatus.jsp">
</c:import>

<%-- 전송이력 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsUserStatus/smsSendHist.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>
