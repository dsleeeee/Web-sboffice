<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>
<c:set var="baseUrl" value="/store/manage/closeStoreView"/>

<div class="con">
    <div class="tabType1" ng-controller="closeStoreViewCtrl" ng-init="init()">
        <ul>
            <%-- 폐점예정매장 탭 --%>
            <li>
                <a id="closeTab" href="#" class="on" ng-click="closeShow()"><s:message code="closeStore.store"/></a>
            </li>
            <%-- 폐점제외매장 탭 --%>
            <li>
                <a id="exceptTab" href="#" ng-click="exceptShow()"><s:message code="storeCloseExcept.storeCloseExcept"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/closeStore/closeStoreView.js?ver=20240216.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>

<%-- 폐점예정매장 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/closeStore/closeStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 폐점제외매장 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/closeStore/storeCloseExcept.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 탭페이지 레이어 끝 --%>

