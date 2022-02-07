<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrOrder/vendrOrderProd/"/>

<div id="prodView" style="display: none;" ng-controller="vendrOrderProdCtrl">
  <ul class="txtSty3 mt10">
    <li class="red"><s:message code="vendrOrder.prod.txt1"/></li>
  </ul>

  <div class="mt20 tr">
    <%-- 추가/변경 --%>
    <button type="button" class="btn_skyblue ml5" id="btnAddProd" ng-click="addProd()" ng-if="btnAddProdFg">
      <s:message code="vendrOrder.prod.addProd"/></button>
    <%-- 저장 --%>
    <button type="button" class="btn_skyblue ml5" id="btnProdSave" ng-click="save()" ng-if="btnProdSaveFg">
      <s:message code="cmm.save"/></button>
  </div>

  <div class="w100 mt10 mb20">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="_itemFormatter"
        frozen-columns="2"
        ime-enabled="true">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderUnitQty"/>" binding="orderUnitQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderEtcQty"/>" binding="orderEtcQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderTotQty"/>" binding="orderTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.prevOrderTotQty"/>" binding="prevOrderTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderAmt"/>" binding="orderAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderVat"/>" binding="orderVat" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.orderTot"/>" binding="orderTot" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrOrder.prod.vendrVatFg01"/>" binding="vendrVatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrOrder/vendrOrderProd.js?ver=20181224.02" charset="utf-8"></script>

<%-- 상품 등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderProdReg.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
