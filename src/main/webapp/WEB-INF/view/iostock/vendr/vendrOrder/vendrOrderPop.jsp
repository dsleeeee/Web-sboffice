<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/vendrOrder/vendrOrderPop/"/>
<wj-popup id="wjVendrOrderPopLayer" control="wjVendrOrderPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vendrOrderPopLayer" class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrOrder.pop.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <div ng-controller="vendrOrderPopCtrl">
        <%-- 발주정보, 발주상품, 발주서 탭 --%>
        <div class="tabType1">
          <ul>
            <%-- 발주정보 탭 --%>
            <li>
              <a id="dtlTab" href="#" class="on" ng-click="dtlShow()" ng-if="dtlShowFg"><s:message code="vendrOrder.pop.vendrOrderDtl"/></a>
            </li>
            <%-- 발주상품 탭 --%>
            <li>
              <a id="prodTab" href="#" ng-click="prodShow()" ng-if="prodShowFg"><s:message code="vendrOrder.pop.vendrOrderProd"/></a>
            </li>
            <%-- 발주서 탭 --%>
            <li>
              <a id="reportTab" href="#" ng-click="reportShow()" ng-if="reportShowFg"><s:message code="vendrOrder.pop.vendrOrderReport"/></a>
            </li>
          </ul>
        </div>
      </div>


<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrOrder/vendrOrderPop.js?ver=20181224.01" charset="utf-8"></script>

    <%-- 발주정보 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 발주상품 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderProd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 발주서 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

    </div>
  </div>
</wj-popup>
