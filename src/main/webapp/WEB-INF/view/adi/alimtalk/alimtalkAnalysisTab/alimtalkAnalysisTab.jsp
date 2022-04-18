<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/adi/alimtalk/alimtalkAnalysisTab"/>

<div class="con">
    <div class="tabType1" ng-controller="alimtalkAnalysisTabCtrl" ng-init="init()">
        <ul>
            <%-- 알림톡 전송결과 탭 --%>
            <li>
                <a id="alimtalkSendStatusTab" href="#" class="on" ng-click="alimtalkSendStatusShow()"><s:message code="alimtalkAnalysisTab.alimtalkSendStatus"/></a>
            </li>
            <%-- 알림톡 전송이력 탭 --%>
            <li>
                <a id="alimtalkSendHistTab" href="#" ng-click="alimtalkSendHistShow()"><s:message code="alimtalkAnalysisTab.alimtalkSendHist"/></a>
            </li>
            <%-- 알림톡 일자별 전송현황 탭 --%>
            <li>
                <a id="alimtalkDaySendStatusTab" href="#" ng-click="alimtalkDaySendStatusShow()"><s:message code="alimtalkAnalysisTab.alimtalkDaySendStatus"/></a>
            </li>
            <c:if test="${orgnFg != 'STORE'}">
                <%-- 알림톡 기간별 전송현황 탭 --%>
                <li>
                    <a id="alimtalkPeriodSendStatusTab" href="#" ng-click="alimtalkPeriodSendStatusShow()"><s:message code="alimtalkAnalysisTab.alimtalkPeriodSendStatus"/></a>
                </li>
            </c:if>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkAnalysisTab/alimtalkAnalysisTab.js?ver=20220419.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 알림톡 전송결과 레이어 --%>
<%-- 조회버튼 ID 1 : 서비스화면부터 하다보니 그렇게 됨 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendStatus/alimtalkSendStatus.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 알림톡 전송이력 레이어 --%>
<%-- 조회버튼 ID 2 : 서비스화면부터 하다보니 그렇게 됨 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendHist/alimtalkSendHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 알림톡 일자별 전송현황 레이어 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendStatus/alimtalkDaySendStatus.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 알림톡 기간별 전송현황 레이어 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendStatus/alimtalkPeriodSendStatus.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>