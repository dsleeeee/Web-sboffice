<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/pos/license/instlManage"/>

<div class="con">
    <div class="tabType1" ng-controller="operCtrl" ng-init="init()">
        <ul>
            <%-- 러닝매장현황 탭 --%>
            <li>
                <a id="runSaleStoreListTab" href="#" class="on" ng-click="runSaleStoreListShow()"><s:message code="oper.runSaleStoreList"/></a>
            </li>
            <%-- 매출매장현황 탭 --%>
            <li>
                <a id="saleStoreListTab" href="#" ng-click="saleStoreListShow()"><s:message code="oper.saleStoreList"/></a>
            </li>
            <%-- 대리점인증현황 탭 --%>
            <li>
                <a id="agencyAuthListTab" href="#" ng-click="agencyAuthListShow()"><s:message code="oper.agencyAuthList"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/oper/oper.js?ver=20220510.05" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 러닝매장현황 레이어 --%>
<c:import url="/WEB-INF/view/pos/license/oper/runSaleStoreList.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매출매장현황 레이어 --%>
<c:import url="/WEB-INF/view/pos/license/oper/saleStoreList.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 대리점인증현황 레이어 --%>
<c:import url="/WEB-INF/view/pos/license/oper/agencyAuthList.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>