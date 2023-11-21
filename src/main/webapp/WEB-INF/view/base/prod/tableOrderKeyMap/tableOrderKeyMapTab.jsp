<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/prod/tableOrderKeyMap"/>

<div class="con">
    <div class="tabType1" ng-controller="tableOrderKeyMapTabCtrl" ng-init="init()">
        <ul>
            <%-- 테이블오더키맵등록 탭 --%>
            <li>
                <a id="tableOrderKeyMapRegistTab" href="#" class="on" ng-click="tableOrderKeyMapRegistShow()"><s:message code="tableOrderKeyMapTab.tableOrderKeyMapRegist"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 상품유형구분
    var prodTypeFg = ${ccu.getCommCode("008")};

    // KIOSK-매장수정여부 0:미사용 1:사용
    var kioskKeyEnvstVal = "${kioskKeyEnvstVal}";

    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";
    // 사용자 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};

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
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/tableOrderKeyMap/tableOrderKeyMapTab.js?ver=20230726.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 테이블오더키맵관리 - 테이블오더키맵등록 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/tableOrderKeyMap/tableOrderKeyMapRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>