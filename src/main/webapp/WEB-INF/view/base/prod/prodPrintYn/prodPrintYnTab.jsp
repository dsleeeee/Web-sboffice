<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/prod/prodPrintYn"/>

<div class="con">
    <div class="tabType1" ng-controller="prodPrintYnTabCtrl" ng-init="init()">
        <ul>
            <%-- 옵션관리 탭 --%>
            <li>
                <a id="prodOptionPrintYnTab" href="#" class="on" ng-click="prodOptionPrintYnShow()"><s:message code="prodPrintYnTab.prodOptionPrintYn"/></a>
            </li>
            <%-- 사이드메뉴관리 탭 --%>
            <li>
                <a id="sideMenuProdPrintYnTab" href="#" ng-click="sideMenuProdPrintYnShow()"><s:message code="prodPrintYnTab.sideMenuProdPrintYn"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodPrintYn/prodPrintYnTab.js?ver=20230630.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 출력여부관리 - 옵션관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodPrintYn/prodOptionPrintYn.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 출력여부관리 - 사이드메뉴관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodPrintYn/sideMenuProdPrintYn.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>