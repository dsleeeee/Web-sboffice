<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/adi/alimtalk/alimtalkTemplateTab"/>

<div class="con">
    <div class="tabType1" ng-controller="alimtalkTemplateTabCtrl" ng-init="init()">
        <ul>
            <%-- 알림톡 템플릿관리 탭 --%>
            <li>
                <a id="alimtalkTemplateTab" href="#" class="on" ng-click="alimtalkTemplateShow()"><s:message code="alimtalkTemplateTab.alimtalkTemplate"/></a>
            </li>
        </ul>
    </div>
</div>


<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkTemplateTab/alimtalkTemplateTab.js?ver=20220704.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 알림톡 템플릿관리 레이어 --%>
<c:import url="/WEB-INF/view/adi/alimtalk/alimtalkTemplate/alimtalkTemplate.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>