<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/prod/storeProdPrintYn"/>

<div class="con">
    <div class="tabType1" ng-controller="storeProdPrintYnTabCtrl" ng-init="init()">
        <ul>
            <%-- 옵션관리 탭 --%>
            <li>
                <a id="storeProdOptionPrintYnTab" href="#" class="on" ng-click="storeProdOptionPrintYnShow()"><s:message code="storeProdPrintYnTab.storeProdOptionPrintYn"/></a>
            </li>
            <%-- 사이드메뉴관리 탭 --%>
            <li>
                <a id="storeSideMenuProdPrintYnTab" href="#" ng-click="storeSideMenuProdPrintYnShow()"><s:message code="storeProdPrintYnTab.storeSideMenuProdPrintYn"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/storeProdPrintYn/storeProdPrintYnTab.js?ver=20230725.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 매장별출력여부관리 - 옵션관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/storeProdPrintYn/storeProdOptionPrintYn.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장별출력여부관리 - 사이드메뉴관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/storeProdPrintYn/storeSideMenuProdPrintYn.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>