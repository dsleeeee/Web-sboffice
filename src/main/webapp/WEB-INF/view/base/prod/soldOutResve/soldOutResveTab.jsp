<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="con">
    <div class="tabType1" ng-controller="soldOutResveTabCtrl" ng-init="init()">
        <ul>
            <%-- 상품 품절관리 탭 --%>
            <li>
                <a id="soldOutResve" href="#" class="on" ng-click="soldOutResveShow()"><s:message code="soldOutResve.prod"/></a>
            </li>
            <%-- 사이드메뉴 품절관리 탭 --%>
            <li>
                <a id="sdselSoldOutResve" href="#" ng-click="sdselSoldOutResveShow()"><s:message code="soldOutResve.sdsel"/></a>
            </li>
        </ul>
    </div>
</div>
<script>
    onload = function()
    {

        // 분류조회팝업 한번씩 열었다 닫아야 정상동작함 딱 한번만 반복하도록
        var scope = agrid.getScope("soldOutResveCtrl");
        scope.prodClassPopUpLayer.show();
        scope.prodClassPopUpLayer.hide();
        var addScope = agrid.getScope("soldOutResveAddCtrl");
        addScope.prodClassPopUpLayer.show();
        addScope.prodClassPopUpLayer.hide();

        var sdselScope = agrid.getScope("sdselSoldOutResveCtrl");
        sdselScope.prodClassPopUpLayer.show();
        sdselScope.prodClassPopUpLayer.hide();

    }
</script>

<script>
    var orgnFg = "${orgnFg}";
    // 내점/배달/포장 가격관리 사용여부 (0: 미사용 1: 사용)
    var subPriceFg = "${subPriceFg}";
    // (상품관리)브랜드사용여부
    var brandUseFg = "${brandUseFg}";
    // 브랜드
    var brandList = ${brandList};
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    // List 형식("" 안붙임)
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/soldOutResve/soldOutResveTab.js?ver=20230530.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 상품 품절관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/soldOutResve/soldOutResve.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사이드메뉴(상품) 품절관리 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/soldOutResve/sdselSoldOutResve.jsp">
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