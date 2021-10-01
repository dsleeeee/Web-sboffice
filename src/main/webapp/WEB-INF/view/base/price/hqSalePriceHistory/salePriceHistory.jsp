<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="con">
    <div class="tabType1" ng-controller="salePriceHistoryCtrl" ng-init="init()">
      <%-- 탭 --%>
        <ul class="subTab mt20">
          <%-- 매장판매가변경이력 --%>
          <li><a id="storeSalePriceHistoryTab" href="#" class="on" ng-click="storeSalePriceHistoryShow();"><s:message code="hqSalePriceHistory.storeSalePriceHistory" /></a></li>
          <%-- 본사판매가변경이력 --%>
          <li><a id="hqSalePriceHistoryTab" href="#" ng-click="hqSalePriceHistoryShow();"><s:message code="hqSalePriceHistory.hqSalePriceHistory" /></a></li>
        </ul>
    </div>
</div>

<script>
  var subPriceFg = "${subPriceFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/hqSalePriceHistory/salePriceHistory.js?ver=20210927.01" charset="utf-8"></script>

<%-- 매장판매가변경이력 --%>
<c:import url="/WEB-INF/view/base/price/hqSalePriceHistory/storeSalePriceHistory.jsp">
</c:import>

<%-- 본사판매가변경이력 --%>
<c:import url="/WEB-INF/view/base/price/hqSalePriceHistory/hqSalePriceHistory.jsp">
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
