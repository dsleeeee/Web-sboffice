<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/status/payTemporary"/>

<div class="con">
    <div class="tabType1" ng-controller="payTemporaryTabCtrl" ng-init="init()">
        <ul>
            <%-- 가승인-상품권결제차액 조회 탭 --%>
            <li>
                <a id="payTemporaryTab" href="#" class="on" ng-click="payTemporaryShow()"><s:message code="payTemporaryTab.payTemporary"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/payTemporary/payTemporaryTab.js?ver=20230504.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 가승인-상품권결제차액 조회 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/payTemporary/payTemporary.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>