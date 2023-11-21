<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="captionMsgTabCtrl" ng-init="init()">
        <ul>
            <%-- 기능키/메시지 탭 --%>
            <li>
                <a id="captionMsgTab" href="#" class="on" ng-click="captionMsgShow()"><s:message code="captionMsg.captionMsg"/></a>
            </li>
            <%-- 화면구분등록 탭 --%>
            <li>
                <a id="captionMsgGrpTab" href="#" ng-click="captionMsgGrpShow()"><s:message code="captionMsg.captionMsgGrp"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var captionMsgGrpList = ${captionMsgGrpList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/captionMsg/captionMsgTab.js?ver=20231120.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 기능키/메시지 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/captionMsg/captionMsg.jsp">
</c:import>

<%-- 화면구분등록 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/captionMsg/captionMsgGrp.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>