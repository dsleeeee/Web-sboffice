<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/store/manage/envConfgBatchChange"/>

<div class="con">
    <div class="tabType1" ng-controller="envConfgBatchChangeCtrl" ng-init="init()">
        <ul>
            <%-- 본사환경 탭 --%>
            <li>
                <a id="hqTab" href="#" class="on" ng-click="hqShow()"><s:message code="envConfgBatchChange.hq"/></a>
            </li>
            <%-- 매장환경 탭 --%>
            <li>
                <a id="storeTab" href="#" ng-click="storeShow()"><s:message code="envConfgBatchChange.store"/></a>
            </li>
            <%-- 매장포스환경 탭 --%>
            <li>
                <a id="storePosTab" href="#" ng-click="storePosShow()"><s:message code="envConfgBatchChange.storePos"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/envConfgBatchChange/envConfgBatchChange.js?ver=20210322.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 본사환경 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/envConfgBatchChange/envConfgBatchChangeHq.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장환경 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/envConfgBatchChange/envConfgBatchChangeStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장포스환경 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/envConfgBatchChange/envConfgBatchChangeStorePos.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>

<%-- 환경설정 조회 팝업 --%>
<c:import url="/WEB-INF/view/store/manage/envConfgBatchChange/searchEnvConfg.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>