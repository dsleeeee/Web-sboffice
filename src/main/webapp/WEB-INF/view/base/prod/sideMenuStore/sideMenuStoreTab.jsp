<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/prod/sideMenuStore"/>

<div class="con">
    <div class="tabType1" ng-controller="sideMenuStoreTabCtrl" ng-init="init()">
        <ul>
            <%-- 선택분류(적용매장) 탭 --%>
            <li>
                <a id="sideMenuClassRegStoreTab" href="#" class="on" ng-click="sideMenuClassRegStoreShow()"><s:message code="sideMenuStoreTab.sideMenuClassRegStore"/></a>
            </li>
            <%-- 선택상품(적용매장) 탭 --%>
            <li>
                <a id="sideMenuProdRegStoreTab" href="#" ng-click="sideMenuProdRegStoreShow()"><s:message code="sideMenuStoreTab.sideMenuProdRegStore"/></a>
            </li>
            <%-- 선택분류(매장별) 탭 --%>
            <li>
                <a id="sideMenuClassStoreTab" href="#" ng-click="sideMenuClassStoreShow()"><s:message code="sideMenuStoreTab.sideMenuClassStore"/></a>
            </li>
            <%-- 선택상품(매장별) 탭 --%>
            <li>
                <a id="sideMenuProdStoreTab" href="#" ng-click="sideMenuProdStoreShow()"><s:message code="sideMenuStoreTab.sideMenuProdStore"/></a>
            </li>
            <%-- 선택분류(선택분류별) 탭 --%>
            <li>
                <a id="sideMenuClassTab" href="#" ng-click="sideMenuClassShow()"><s:message code="sideMenuStoreTab.sideMenuClass"/></a>
            </li>
            <%-- 선택상품(선택상품별) 탭 --%>
            <li>
                <a id="sideMenuProdTab" href="#" ng-click="sideMenuProdShow()"><s:message code="sideMenuStoreTab.sideMenuProd"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    // List 형식("" 안붙임)
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
    var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
    var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
    var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
    var momsStoreFg05ComboList = ${momsStoreFg05ComboList};

    // 브랜드사용여부
    var brandUseFg = "${brandUseFg}";
    // 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenuStore/sideMenuStoreTab.js?ver=20230622.02" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 선택분류(적용매장) 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenuStore/sideMenuClassRegStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택상품(적용매장) 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenuStore/sideMenuProdRegStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택분류(매장별) 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenuStore/sideMenuClassStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택상품(매장별) 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenuStore/sideMenuProdStore.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택분류(선택분류별) 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenuStore/sideMenuClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 선택상품(선택상품별) 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenuStore/sideMenuProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>