<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/base/price/salePriceResve"/>

<div class="con">
    <div class="tabType1" ng-controller="hqSalePriceResveTabCtrl" ng-init="init()">
        <ul>
            <%-- 가격예약(본사판매가) 조회 탭 --%>
            <li>
                <a id="hqSalePriceResveTab" href="#" class="on" ng-click="hqSalePriceResveShow()"><s:message code="hqSalePriceResveTab.hqSalePriceResve"/></a>
            </li>
            <%-- [1250 맘스터치] --%>
            <c:if test="${momsEnvstVal == '1'}">
                <%-- 가격예약(본사판매가) 엑셀업로드 엑셀업로드 탭 --%>
                <li>
                    <a id="hqSalePriceResveExcelUploadTab" href="#" ng-click="hqSalePriceResveExcelUploadShow()"><s:message code="hqSalePriceResveTab.hqSalePriceResveExcelUpload"/></a>
                </li>
            </c:if>
        </ul>
    </div>
</div>

<script>
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";
    // 내일날짜
    var tomorrowDate = "${tomorrowDate}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceResve/hqSalePriceResveTab.js?ver=20230224.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 가격예약(본사판매가) 조회 레이어 --%>
<c:import url="/WEB-INF/view/base/price/salePriceResve/hqSalePriceResve.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 가격예약(본사판매가) 엑셀업로드 조회 레이어 --%>
<c:import url="/WEB-INF/view/base/price/salePriceResve/hqSalePriceResveExcelUpload.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>