<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/prod/recpOrigin"/>

<div class="con">
    <div class="tabType1" ng-controller="recpOriginTabCtrl" ng-init="init()">
        <ul>
            <%-- 원산지 --%>
            <li>
                <a id="recpOriginTab" href="#" class="on" ng-click="recpOriginShow()"><s:message code="recpOriginTab.recpOrigin"/></a>
            </li>
            <%-- 상품-원산지관리 탭 --%>
            <li>
                <a id="prodRecpOriginTab" href="#" ng-click="prodRecpOriginShow()"><s:message code="recpOriginTab.prodRecpOrigin"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/recpOrigin/recpOriginTab.js?ver=20210326.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 원산지 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/recpOrigin/recpOrigin.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품-원산지관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/recpOrigin/prodRecpOrigin.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>