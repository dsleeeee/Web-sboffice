<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="todaySaleCtrl" ng-init="init()">
        <ul>
            <%-- 당일매출종합 탭 --%>
            <li>
                <a id="todayGnrlzTab" href="#" class="on" ng-click="todayGnrlzShow()"><s:message code="todayGnrlz.todaySaleTotal"/></a>
            </li>
            <%-- 당일매출상세 탭 --%>
            <li>
                <a id="todayDtlTab" href="#" ng-click="todayDtlShow()"><s:message code="todayDtl.todaySaleDtl"/></a>
            </li>
            <%-- 영수증별매출 탭 --%>
            <li>
                <a id="todayBillSaleDtlTab" href="#" ng-click="todayBillSaleDtlShow()"><s:message code="todayBillSaleDtl.billSale"/></a>
            </li>
            <%-- 비매출내역 탭 --%>
            <%--<li>
                <a id="nonSaleTab" href="#" ng-click="nonSaleShow()"><s:message code="storeStatus.posInstall"/></a>
            </li>--%>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/today/todaySale.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 당일매출종합 레이어 --%>
<c:import url="/WEB-INF/view/sale/today/todayGnrlz/todayGnrlz.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 당일매출상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/today/todayDtl/todayDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 영수증별매출 레이어 --%>
<c:import url="/WEB-INF/view/sale/today/todayBillSaleDtl/todayBillSaleDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 비매출내역 레이어 --%>
<%--<c:import url="/WEB-INF/view/sale/today/todayGnrlz/todayGnrlz.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>--%>
<%-- 탭페이지 레이어 끝 --%>