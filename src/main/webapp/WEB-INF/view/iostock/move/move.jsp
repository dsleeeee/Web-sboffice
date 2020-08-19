<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<input type="hidden" id="HqOfficeCd" name="HqOfficeCd" ng-model="HqOfficeCd" value="${gvHqOfficeCd}"/>
<div class="con" ng-controller="moveCtrl">
</div>

<%-- 탭페이지 레이어 시작 --%>
<%-- 본사 매장이동관리 --%>
<c:import url="/WEB-INF/view/iostock/move/hqStoreMove/hqStoreMove.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장 매장이동관리 --%>
<c:import url="/WEB-INF/view/iostock/move/storeMove/storeMove.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- /탭페이지 레이어 종료 --%>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/move/move.js" charset="utf-8"></script>
