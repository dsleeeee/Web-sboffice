<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="con">
    <div class="tabType1" ng-controller="soldOutTabCtrl" ng-init="init()">
        <ul>
            <%-- 상품 품절관리 탭 --%>
            <li>
                <a id="prodSoldOut" href="#" class="on" ng-click="prodSoldOutShow()"><s:message code="soldOut.prod"/></a>
            </li>
            <%-- 사이드메뉴(상품) 품절관리 탭 --%>
            <li>
                <a id="sideProdSoldOut" href="#" ng-click="sideProdSoldOutShow()"><s:message code="soldOut.sideMenuProd"/></a>
            </li>
            <%-- 사이드메뉴 품절관리 탭 --%>
            <li>
                <a id="sideSoldOut" href="#" ng-click="sideSoldOutShow()"><s:message code="soldOut.sideMenu"/></a>
            </li>
        </ul>
    </div>
</div>
<script>
    onload = function()
    {

        // 분류조회팝업 한번씩 열었다 닫아야 정상동작함 딱 한번만 반복하도록
        var prodScope = agrid.getScope("prodSoldOutCtrl");
        prodScope.prodClassPopUpLayer.show();
        prodScope.prodClassPopUpLayer.hide();

        var sideMenuProdScope = agrid.getScope("sideMenuProdSoldOutCtrl");
        sideMenuProdScope.prodClassPopUpLayer.show();
        sideMenuProdScope.prodClassPopUpLayer.hide();

    }
</script>

<script>
    var orgnFg = "${orgnFg}";

    /* 상품상세 필수 START */
    // 내점/배달/포장 가격관리 사용여부 (0: 미사용 1: 사용)
    var subPriceFg = "${subPriceFg}";
    // (상품관리)브랜드사용여부
    var brandUseFg = "${brandUseFg}";
    // 매장상품제한구분 사용여부(매장 세트구성상품 등록시 사용, 매장에서 사용하지만 본사환경설정값으로 여부파악)
    var storeProdUseFg = "${storeProdUseFg}";
    // 브랜드
    var brandList = ${brandList};
    // 매장별 브랜드 콤보박스 조회(사용자 상관없이 전체 브랜드 표시)
    var userHqStoreBrandCdComboList = ${userHqStoreBrandCdComboList};
    // 사용자 매장브랜드(조회용)
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
    // 코너 콤보박스
    var cornerList = ${cornerList};
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

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
    /* 상품상세 필수 END */
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/soldOut/soldOutTab.js?ver=20250221.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 상품 품절관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/soldOut/prodSoldOut.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사이드메뉴 품절관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/soldOut/sideMenuSoldOut.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사이드메뉴(상품) 품절관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/soldOut/sideMenuProdSoldOut.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝--%>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDetailView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
    <c:param name="prodNoEnvFg" value="${prodNoEnvFg}"/>
</c:import>
