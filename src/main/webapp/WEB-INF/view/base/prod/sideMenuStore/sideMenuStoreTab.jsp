<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/prod/sideMenuStore"/>

<div class="con">
    <div class="tabType1" ng-controller="sideMenuStoreTabCtrl" ng-init="init()">
        <ul>
            <%-- 선택분류(매장별) 탭 --%>
            <li>
                <a id="sideMenuClassStoreTab" href="#" class="on" ng-click="sideMenuClassStoreShow()"><s:message code="sideMenuStoreTab.sideMenuClassStore"/></a>
            </li>
            <%-- 선택분류(선택분류별) 탭 --%>
            <li>
                <a id="sideMenuClassTab" href="#" ng-click="sideMenuClassShow()"><s:message code="sideMenuStoreTab.sideMenuClass"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenuStore/sideMenuStoreTab.js?ver=20230612.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 선택분류(매장별) 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenuStore/sideMenuClassStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택분류(선택분류별) 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenuStore/sideMenuClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>