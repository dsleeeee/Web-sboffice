<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="smsBadwordTabCtrl" ng-init="init()">
        <ul>
            <%-- 금칙어관리 탭 --%>
            <li>
                <a id="badwordManageTab" href="#" class="on" ng-click="badwordManageShow()"><s:message code="smsBadwordTab.badwordManage"/></a>
            </li>
            <%-- 탐지/차단 결과 로그 탭 --%>
            <li>
                <a id="msgBlockLogTab" href="#" ng-click="msgBlockLogShow()"><s:message code="smsBadwordTab.msgBlockLog"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsBadword/smsBadwordTab.js?ver=20260701.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 금칙어관리 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsBadword/badwordManage.jsp">
</c:import>

<%-- 탐지/차단 결과 로그 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsBadword/msgBlockLog.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>
