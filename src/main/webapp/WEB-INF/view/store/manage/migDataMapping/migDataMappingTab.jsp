<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sesionScope.sesionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sesionScope.sesionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sesionScope.sesionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="migDataMappingTabCtrl" ng-init="init()">
        <ul>
            <%-- OKPOS-KCP 데이터 이관 요청 내역 탭 --%>
            <li>
                <a id="okMigDataMappingTab" href="#" class="on" ng-click="okMigDataMapping()"><s:message code="migDataMapping.migDataMapping"/></a>
            </li>
            <%-- NXPOS1 매출정보 데이터 이관 요청 내역 탭 --%>
            <li>
                <a id="nxMigDataMappingTab" href="#" ng-click="nxMigDataMapping()"><s:message code="migDataMapping.nxMigDataMapping"/></a>
            </li>
            <%-- NXPOS1 매출정보 데이터 이관 요청 내역 탭 --%>
            <li>
                <a id="zeusDataMappingTab" href="#" ng-click="zeusDataMapping()"><s:message code="migDataMapping.zeusDataMapping"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var authHqList = ${authHqList};
    var sysStatFgComboData = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/migDataMapping/migDataMappingTab.js?ver=20250204.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- OKPOS 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/migDataMapping/migDataMapping.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- NXPOS 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/migDataMapping/nxMigDataMapping.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- NXPOS 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/migDataMapping/zeusDataMapping.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 종료 --%>
