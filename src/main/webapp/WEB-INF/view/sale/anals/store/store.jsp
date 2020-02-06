<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
    <div class="tabType1" ng-controller="empSaleCtrl" ng-init="init()">
        <ul>
            <%-- 매장순위 탭 --%>
            <li>
                <a id="storeRankTab" href="#" class="on" ng-click="storeRankShow()"><s:message code="store.rank"/></a>
            </li>
            <%-- 매장상품순위 탭 --%>
            <li>
                <a id="storeProdTab" href="#" ng-click="storeProdShow()"><s:message code="store.prod"/></a>
            </li>
            <%-- 매장월별순위 탭 --%>
            <li>
                <a id="storeMonthTab" href="#" ng-click="storeMonthShow()"><s:message code="store.month"/></a>
            </li>
            <%-- 매장형태별 매출 탭 --%>
	        <li>
	            <a id="storeFgTab" href="#" ng-click="storeFgShow()"><s:message code="store.fg"/></a> 
	        </li> 
            <%-- 브랜드별 매출 탭 --%>
	        <li> 
	           <a id="storeBrandTab" href="#" ng-click="storeBrandShow()"><s:message code="store.brand"/></a>
	        </li> 
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/store/store.js?ver=201901112.15" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 매장순위 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/store/rank/storeRank.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import> 

<%-- 매장상품순위 레이어  --%>
<c:import url="/WEB-INF/view/sale/anals/store/prod/storeProd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장월별순위 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/store/month/storeMonth.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장형태별 매출 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/store/fg/storeFg.jsp">
     <c:param name="menuCd" value="${menuCd}"/>
     <c:param name="menuNm" value="${menuNm}"/>
</c:import> 

<%-- 브랜드별 매출 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/store/brand/storeBrand.jsp"> 
	  <c:param name="menuCd" value="${menuCd}"/> 
	  <c:param name="menuNm" value="${menuNm}"/> 
</c:import> 