<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="fnkeyCmNmcdTabCtrl" ng-init="init()">
        <ul>
            <%-- 기능키(공통) 탭 --%>
            <li <c:if test="${orgnFg ne 'MASTER'}">style="display: none;"</c:if>>
                <a id="cmPosFnkeyTab" href="#" class="on" ng-click="cmPosFnkeyShow()"><s:message code="fnkeyCmNmcd.cmPosFnkey"/></a>
            </li>
            <%-- 기능키(매장) 탭 --%>
            <li <c:if test="${orgnFg ne 'HQ'}">style="display: none;"</c:if>>
                <a id="storeFnkeyTab" href="#" class="on" ng-click="storeFnkeyShow()"><s:message code="fnkeyCmNmcd.storeFnkey"/></a>
            </li>
            <%-- 공통코드 탭 --%>
            <li <c:if test="${orgnFg ne 'MASTER'}">style="display: none;"</c:if>>
                <a id="cmNmcdTab" href="#" ng-click="cmNmcdShow()"><s:message code="fnkeyCmNmcd.cmNmcd"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/fnkeyCmNmcd/fnkeyCmNmcdTab.js?ver=20231215.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 기능키(공통) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/fnkeyCmNmcd/cmPosFnkey.jsp">
</c:import>

<%-- 기능키(매장) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/fnkeyCmNmcd/storeFnkey.jsp">
</c:import>

<%-- 공통코드 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/fnkeyCmNmcd/cmNmcd.jsp">
</c:import>

<%-- 탭페이지 레이어 끝 --%>