<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/pos/license/instalManage"/>

<div class="con">
    <div class="tabType1" ng-controller="instalManageCtrl" ng-init="init()">
        <ul>
            <%-- 업체현황 탭 --%>
            <li>
                <a id="agencyListTab" href="#" class="on" ng-click="agencyListShow()"><s:message code="instl.agencyList"/></a>
            </li>
            <%-- 설치현황 탭 --%>
            <li>
                <a id="instalListTab" href="#" ng-click="instalListShow()"><s:message code="instl.instalList"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/instalManage/instalManage.js?ver=20191014.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 업체현황 레이어 --%>
<c:import url="/WEB-INF/view/pos/license/instalManage/agencyList.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 설치현황 레이어 --%>
<c:import url="/WEB-INF/view/pos/license/instalManage/instalList.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>