<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/adi/alimtalk/alimtalkSendTab"/>

<div class="con">
    <div class="tabType1" ng-controller="alimtalkSendTabCtrl" ng-init="init()">
        <ul>
            <%-- 알림톡 전송유형 탭 --%>
            <li>
                <a id="alimtalkSendTypeTab" href="#" class="on" ng-click="alimtalkSendTypeShow()"><s:message code="alimtalkSendTab.alimtalkSendType"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendTab/alimtalkSendTab.js?ver=20220321.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 알림톡 전송유형 레이어 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkSendType/alimtalkSendType.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>