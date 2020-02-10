<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="con">
    <div class="tabType1" ng-controller="rtnStatusSaleCtrl" ng-init="init()">
        <ul>
            <%-- 반품현황 --%>
            <li>
                <a id="rtnStatusDayTab" href="#" class="on" ng-click="rtnStatusDayTabShow()"><s:message code="rtnStatus.rtnStatus"/></a>
            </li>
            <%-- 상품별 반품현황 --%>
            <li>
                <a id="rtnStatusProdTab" href="#" ng-click="rtnStatusProdTabShow()"><s:message code="rtnStatus.prod"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/rtnStatus/rtnStatusSale.js?" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일자별 --%>
<c:import url="/WEB-INF/view/sale/status/rtnStatus/rtnStatus.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품별 --%>
<c:import url="/WEB-INF/view/sale/status/rtnStatus/prod.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>