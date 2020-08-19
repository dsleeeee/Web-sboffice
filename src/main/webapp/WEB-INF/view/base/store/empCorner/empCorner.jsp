<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/store/empCorner"/>

<div class="con">
    <div class="tabType1" ng-controller="empCornerCtrl" ng-init="init()">
        <ul>
            <%-- 사원별 탭 --%>
            <li>
                <a id="empCornerEmpTab" href="#" class="on" ng-click="empCornerEmpShow()"><s:message code="empCorner.emp"/></a>
            </li>
            <%-- 코너별 탭 --%>
            <li>
                <a id="empCornerCornerTab" href="#" ng-click="empCornerCornerShow()"><s:message code="empCorner.corner"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/empCorner/empCorner.js?ver=202005014" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 사원별 레이어 --%>
<c:import url="/WEB-INF/view/base/store/empCorner/emp.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 코너별 레이어 --%>
<c:import url="/WEB-INF/view/base/store/empCorner/corner.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>