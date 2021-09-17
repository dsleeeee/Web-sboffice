<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/adi/sms/smsAnalysisTab"/>

<div class="con">
    <div class="tabType1" ng-controller="smsAnalysisTabCtrl" ng-init="init()">
        <ul>
            <%-- SMS전송이력 탭 --%>
            <li>
                <a id="smsSendHistTab" href="#" class="on" ng-click="smsSendHistShow()"><s:message code="smsAnalysisTab.smsSendHist"/></a>
            </li>
            <%-- 일자별 전송현황 탭 --%>
            <li>
                <a id="daySendStatusTab" href="#" ng-click="daySendStatusShow()"><s:message code="smsAnalysisTab.daySendStatus"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsAnalysisTab/smsAnalysisTab.js?ver=20210917.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- SMS전송이력 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSendHist/smsSendHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 일자별 전송현황 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/sendStatus/daySendStatus.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>