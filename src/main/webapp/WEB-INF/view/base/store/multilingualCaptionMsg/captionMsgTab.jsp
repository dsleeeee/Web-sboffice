<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="captionMsgTabCtrl" ng-init="init()">
        <ul>
            <%-- 기능키/메시지 탭 --%>
            <li>
                <a id="captionMsgTab" href="#" class="on" ng-click="captionMsgShow()"><s:message code="multilingualCaptionMsg.captionMsg"/></a>
            </li>
            <%-- 화면구분등록 탭 --%>
            <li>
                <a id="captionMsgGrpTab" href="#" ng-click="captionMsgGrpShow()"><s:message code="multilingualCaptionMsg.captionMsgGrp"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/multilingualCaptionMsg/captionMsgTab.js?ver=20231109.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 기능키/메시지 탭 --%>

<%-- 화면구분등록 탭 --%>
<c:import url="/WEB-INF/view/base/store/multilingualCaptionMsg/captionMsgGrp.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>