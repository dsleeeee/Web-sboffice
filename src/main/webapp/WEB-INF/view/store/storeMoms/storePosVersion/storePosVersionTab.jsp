<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sesionScope.sesionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sesionScope.sesionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sesionScope.sesionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="con">
    <div class="tabType1" ng-controller="storePosVersionTabCtrl" ng-init="init()">
        <ul>
            <%-- 포스버전현황 탭 --%>
            <li>
                <a id="storePosVersionTab" href="#" class="on" ng-click="storePosVersionChangeShow()"><s:message code="storePosVersion.storePosVersion"/></a>
            </li>
            <%-- 포스패치로그 탭 --%>
            <li id="posTab" style="display:none;">
                <a id="posPatchLogTab" href="#" ng-click="posPatchLogChangeShow()"><s:message code="storePosVersion.posPatchLog"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">

    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
    var hqOfficeCd = "${hqOfficeCd}";

    var clsFg = ${ccu.getCommCodeSelect("001")};
    var sysStatFg = ${ccu.getCommCodeSelect("005")};
    var areaCd = ${ccu.getCommCodeSelect("061")};
    var verTypeFg = ${ccu.getCommCodeExcpAll("059")};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
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
    var selectVerComboList = ${selectVerComboList};
    var selectSubPos = ${selectSubPos};

</script>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storePosVersion/storePosVersionTab.js?ver=20240313.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 매장포스버전현황 레이어 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storePosVersion/storePosVersion.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포스패치로그 레이어 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storePosVersion/posPatchLog.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>
