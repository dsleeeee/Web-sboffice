<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="smsXrayTabCtrl" ng-init="init()">
        <ul>
            <%-- URL 관리 탭 --%>
            <li>
                <a id="smsXrayManageTab" href="#" class="on" ng-click="smsXrayManageShow()"><s:message code="smsXrayTab.smsXrayManage"/></a>
            </li>
            <%-- 탐지/차단결과 로그 탭 --%>
            <li>
                <a id="urlBlockLogTab" href="#" ng-click="urlBlockLogShow()"><s:message code="smsXrayTab.urlBlockLog"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsXrayManage/smsXrayTab.js?ver=20260730.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- URL 관리 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsXrayManage/smsXrayManage.jsp">
</c:import>

<%-- 탐지/차단결과 로그 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsXrayManage/urlBlockLog.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>
