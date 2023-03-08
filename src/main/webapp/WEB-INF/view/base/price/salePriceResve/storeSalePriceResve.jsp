<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="coercionFg" value="${coercionFg}" />

<div class="con">
    <div class="tabType1" ng-controller="storeSalePriceResveCtrl">
        <%-- 탭 --%>
        <ul class="subTab mt20">
            <%-- 상품별 판매가관리 --%>
            <li><a id="prodSalePriceResve" href="#" class="on" ng-click="changeTab('P');"><s:message code="salePriceResve.prodSalePrice" /></a></li>
            <%-- 매장별 판매가관리 --%>
            <li><a id="storeSalePriceResve" href="#" ng-click="changeTab('S');"><s:message code="salePriceResve.storeSalePrice" /></a></li>
            <%-- [1250 맘스터치] --%>
            <c:if test="${momsEnvstVal == '1'}">
                <%-- 가격예약(매장판매가) 엑셀업로드 탭 --%>
                <li><a id="storeSalePriceResveExcelUpload" href="#" ng-click="changeTab('U');"><s:message code="salePriceResve.storeSalePriceResveExcelUpload" /></a></li>
            </c:if>
        </ul>
    </div>
</div>

<%-- 상품별 판매가관리 --%>
<c:import url="/WEB-INF/view/base/price/salePriceResve/storeProdSalePriceResve.jsp">
</c:import>

<%-- 매장별 판매가관리 --%>
<c:import url="/WEB-INF/view/base/price/salePriceResve/storeStoreSalePriceResve.jsp">
</c:import>

<%-- 가격예약(매장판매가) 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/base/price/salePriceResve/storeSalePriceResveExcelUpload.jsp">
</c:import>

<script>
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceResve/storeSalePriceResve.js?ver=20230306.01" charset="utf-8"></script>