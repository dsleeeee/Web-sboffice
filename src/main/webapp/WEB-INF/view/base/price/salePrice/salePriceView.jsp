<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="sideMenuCtrl">

  <%-- 탭 --%>
  <div style="display:none;">
    <ul class="subTab mt20">
      <%-- 상품별 판매가 관리 --%>
      <li><a id="prodSalePrice" href="#" class="on" ng-click="changeTab('P');"><s:message code="salePrice.prodSalePrice" /></a></li>
      <%-- 매장별 판매가 관리 --%>
      <li><a id="storeSalePrice" href="#" ng-click="changeTab('S');"><s:message code="salePrice.storeSalePrice" /></a></li>
    </ul>
  </div>

  <%-- 상품별 판매가 관리 --%>
  <c:import url="/WEB-INF/view/base/price/salePrice/prodSalePriceView.jsp">
  </c:import>

  <%-- 매장별 판매가 관리 --%>
    <%--
  <c:import url="/WEB-INF/view/base/price/salePrice/storeSalePriceView.jsp">
  </c:import>
--%>
</div>
<script type="text/javascript" src="/resource/solbipos/js/base/price/salePrice/salePrice.js?ver=20181220.01" charset="utf-8"></script>

