<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/pos/license/instlManage"/>

<div class="con">
    <div class="tabType1" ng-controller="runStoreCtrl" ng-init="init()">
        <ul>
            <%-- 런닝매장현황 탭 --%>
            <li>
                <a id="runStoreListTab" href="#" class="on" ng-click="runStoreListShow()"><s:message code="runStore.runStoreList"/></a>
            </li>
            <%-- 런닝COPY수 탭 --%>
            <li>
                <a id="runCopyCntTab" href="#" ng-click="runCopyCntShow()"><s:message code="runStore.runCopyCnt"/></a>
            </li>
            <%-- 런닝매장추이 탭 --%>
            <li>
                <a id="runStoreTrnsitnTab" href="#" ng-click="runStoreTrnsitnShow()"><s:message code="runStore.runStoreTrnsitn"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/runStore/runStoreTab.js?ver=20240411.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 러닝매장현황 레이어 --%>
<c:import url="/WEB-INF/view/pos/license/runStore/runStoreList.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매출매장현황 레이어 --%>
<c:import url="/WEB-INF/view/pos/license/runStore/runCopyCnt.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 대리점인증현황 레이어 --%>
<c:import url="/WEB-INF/view/pos/license/runStore/runStoreTrnsitn.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 관리밴사 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchVan.jsp">
</c:import>

<%-- 관리업체 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>