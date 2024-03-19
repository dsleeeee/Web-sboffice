<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sesionScope.sesionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sesionScope.sesionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sesionScope.sesionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="con">
    <div class="tabType1" ng-controller="lsmStoreTabCtrl" ng-init="init()">
        <ul>
            <%-- 터치키 탭 --%>
            <li>
                <a id="tukeyStoreTab" href="#" class="on" ng-click="tukeyStoreChangeShow()"><s:message code="lsmStore.tukey"/></a>
            </li>
            <%-- 키오스크 탭 --%>
            <li>
                <a id="kioskStoreTab" href="#" ng-click="kioskStoreChangeShow()"><s:message code="lsmStore.kiosk"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/lsmStore/lsmStoreTab.js?ver=20240318.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 터치키 레이어 --%>
<c:import url="/WEB-INF/view/store/storeMoms/lsmStore/lsmStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 키오스크 레이어 --%>
<c:import url="/WEB-INF/view/store/storeMoms/lsmStore/lsmKioskStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>
