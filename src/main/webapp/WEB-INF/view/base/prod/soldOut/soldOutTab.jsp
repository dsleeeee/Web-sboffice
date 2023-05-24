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
    // (상품관리)브랜드사용여부
    var brandUseFg = "${brandUseFg}";
    // 브랜드
    var brandList = ${brandList};
    var orgnFg = "${orgnFg}";
    var momsEnvstVal = "${momsEnvstVal}";
    // 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/soldOut/soldOutTab.js?ver=20220225.01" charset="utf-8"></script>

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
