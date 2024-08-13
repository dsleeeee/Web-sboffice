<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" ng-controller="basicSideMenuCtrl">

    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="basicSideMenu.basicSideMenu"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="queryTab();">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>

    <%-- 탭 --%>
    <div>
        <ul class="subTab mt10">
            <%-- 선택메뉴(세트) --%>
            <li><a id="sideMenuSelectMenu" href="#" class="on" ng-click="changeTab('C');"><s:message code="basicSideMenu.tab.selectMenu(set)"/></a></li>
            <%-- 선택메뉴(싱글) --%>
            <li><a id="sideMenuSelectMenuSingle" href="#" ng-click="changeTab('S');"><s:message code="basicSideMenu.tab.selectMenu(single)"/></a></li>
        </ul>
    </div>

    <c:import url="/WEB-INF/view/base/prod/basicSideMenu/basicSideMenuSelectMenu.jsp">
    </c:import>

    <c:import url="/WEB-INF/view/base/prod/basicSideMenu/basicSideMenuSelectMenuSingle.jsp">
    </c:import>

</div>

<script>
    <%--  var prodEnvstVal = "${prodEnvstVal}";--%>

    // 브랜드
    var brandList = ${brandList};

    // [1014 포스프로그램구분] 환경설정값
    var posVerEnvstVal = "${posVerEnvstVal}";
    // [1261 필수선택사용여부] 환경설정값
    var requireYnEnvstVal = "${requireYnEnvstVal}";

    var momsEnvstVal = "${momsEnvstVal}"; // [1250 맘스터치] 환경설정값
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
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/basicSideMenu/basicSideMenu.js?ver=20240812.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:if test="${param.gubun ne 'sideMenu'}">
    <c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
    </c:import>
</c:if>