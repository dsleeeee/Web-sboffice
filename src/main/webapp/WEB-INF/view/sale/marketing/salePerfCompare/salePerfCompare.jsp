<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="con">
    <div class="tabType1" ng-controller="salePerfCompareCtrl" ng-init="init()">
        <ul>
            <%-- 전체점포 --%>
            <li>
                <a id="salePerfCompareAll" href="#" class="on" ng-click="salePerfCompareAllTabShow()"><s:message code="salePerfCompare.allStore"/></a>
            </li>
            <%-- 선택점포 --%>
            <li>
                <a id="salePerfCompareStore" href="#" ng-click="salePerfCompareStoreTabShow()"><s:message code="salePerfCompare.selectStore"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/marketing/salePerfCompare/salePerfCompare.js?20250807.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 전체점포 탭 --%>
<c:import url="/WEB-INF/view/sale/marketing/salePerfCompare/salePerfCompareAll.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택점포 탭 --%>
<c:import url="/WEB-INF/view/sale/marketing/salePerfCompare/salePerfCompareStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>