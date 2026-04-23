<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="authorInExRegistTabCtrl" ng-init="init()">
        <ul>
            <%-- 사용자기준 탭 --%>
            <li>
                <a id="userInExTab" href="#" class="on" ng-click="userInExShow()"><s:message code="authorInExRegist.userInExRegist"/></a>
            </li>
            <%-- 소속기준 --%>
            <li>
                <a id="orgnInExTab" href="#" ng-click="orgnInExShow()"><s:message code="authorInExRegist.orgnInExRegist"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin2/authorInExRegist/authorInExRegistTab.js?ver=20260417.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 사용자기준 레이어 --%>
<c:import url="/WEB-INF/view/sys/admin2/authorInExRegist/userInExRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 소속기준 레이어 --%>
<c:import url="/WEB-INF/view/sys/admin2/authorInExRegist/orgnInExRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>
