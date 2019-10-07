<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/store/manage/status"/>

<div class="con">
    <div class="tabType1" ng-controller="statusCtrl" ng-init="init()">
        <ul>
            <%-- 매장 탭 --%>
            <li>
                <a id="statusStoreTab" href="#" class="on" ng-click="statusStoreShow()"><s:message code="storeStatus.store"/></a>
            </li>
            <%-- 관리업체 탭 --%>
            <li>
                <a id="statusAgencyTab" href="#" ng-click="statusAgencyShow()"><s:message code="storeStatus.agency"/></a>
            </li>
            <%-- VAN사 탭 --%>
            <li>
                <a id="statusVanTab" href="#" ng-click="statusVanShow()"><s:message code="storeStatus.van"/></a>
            </li>
            <%-- POS 설치현황 탭 --%>
            <li>
                <a id="statusPosInstallTab" href="#" ng-click="statusPosInstallShow()"><s:message code="storeStatus.posInstall"/></a>
            </li>
            <%-- 관리매장 승인내역 탭 --%>
            <li>
                <a id="statusApprListTab" href="#" ng-click="statusApprListShow()"><s:message code="storeStatus.apprList"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/status/status.js?ver=20190920.10" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 매장 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/status/statusStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 관리업체 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/status/statusAgency.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- VAN사 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/status/statusVan.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- POS설치현황 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/status/statusPosInstall.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 관리매장 승인내역 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/status/statusApprList.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>