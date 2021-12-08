<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/status/storeProdSaleReport"/>

<div class="con">
    <div class="tabType1" ng-controller="storeProdSaleReportTabCtrl" ng-init="init()">
        <ul>
            <%-- 기간별 매장-상품 매출 다운로드 탭 --%>
            <li>
                <a id="storeProdSaleReportTab" href="#" class="on" ng-click="storeProdSaleReportShow()"><s:message code="storeProdSaleReportTab.storeProdSaleReport"/></a>
            </li>
            <%-- 지사-지역관리 --%>
            <li>
                <a id="branchAreaTab" href="#" ng-click="branchAreaShow()"><s:message code="storeProdSaleReportTab.branchArea"/></a>
            </li>
            <%-- 지역-매장관리 --%>
            <li>
                <a id="areaStoreMappingTab" href="#" ng-click="areaStoreMappingShow()"><s:message code="storeProdSaleReportTab.areaStoreMapping"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/storeProdSaleReport/storeProdSaleReportTab.js?ver=20211207.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 기간별 매장-상품 매출 다운로드 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/storeProdSaleReport/storeProdSaleReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 지사-지역관리 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/storeProdSaleReport/branchArea.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 지역-매장관리 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/storeProdSaleReport/areaStoreMapping.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>