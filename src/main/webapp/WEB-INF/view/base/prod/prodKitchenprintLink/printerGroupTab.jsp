<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="printerGroupTabCtrl" ng-init="init()">
        <ul>
            <%-- 주방프린터그룹관리 탭 --%>
            <li>
                <a id="printerGroupTab" href="#" class="on" ng-click="printerGroupShow()"><s:message code="printerGroup.printerGroup"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 브랜드
    var brandList = ${brandList};
    // 매장타입
    var storeGroupList = ${storeGroupList};

    var momsEnvstVal = "${momsEnvstVal}";

    // List 형식("" 안붙임)
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodKitchenprintLink/printerGroupTab.js?ver=20230921.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 주방프린터그룹관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodKitchenprintLink/printerGroup.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>