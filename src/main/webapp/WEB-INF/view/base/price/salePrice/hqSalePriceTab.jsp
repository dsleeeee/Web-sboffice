<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/base/price/salePrice"/>

<div class="con">
    <div class="tabType1" ng-controller="hqSalePriceTabCtrl" ng-init="init()">
        <ul>
            <%-- 본사판매가관리 조회 탭 --%>
            <li>
                <a id="hqSalePriceTab" href="#" class="on" ng-click="hqSalePriceShow()"><s:message code="hqSalePriceTab.hqSalePrice"/></a>
            </li>
            <%-- [1250 맘스터치] --%>
            <c:if test="${momsEnvstVal == '1'}">
                <%-- 본사판매가관리 엑셀업로드 탭 --%>
                <li>
                    <a id="hqSalePriceExcelUploadTab" href="#" ng-click="hqSalePriceExcelUploadShow()"><s:message code="hqSalePriceTab.hqSalePriceExcelUpload"/></a>
                </li>
            </c:if>
        </ul>
    </div>
</div>

<script>
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePrice/hqSalePriceTab.js?ver=20230323.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 본사판매가관리 조회 레이어 --%>
<c:import url="/WEB-INF/view/base/price/salePrice/hqSalePriceView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 본사판매가관리 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/base/price/salePrice/hqSalePriceExcelUpload.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>