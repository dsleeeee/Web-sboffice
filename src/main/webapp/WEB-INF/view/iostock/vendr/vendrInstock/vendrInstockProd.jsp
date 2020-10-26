<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrInstock/vendrInstockProd/"/>

<div id="prodView" style="display: none;" ng-controller="vendrInstockProdCtrl">
<!--   <ul class="txtSty3 mt10"> -->
<%--     <li class="red"><s:message code="vendrInstock.prod.txt1"/></li> --%>
<!--   </ul> -->

  <div class="mt20 tr">
  	<%--출고창고
   	<p class="s14 bk fl mr5 lh30"><s:message code="outstockConfm.dtl.outStorage"/></p>
   	<span class="txtIn w150px sb-select fl mr5">
       <wj-combo-box
         id="saveDtlOutStorageCd"
         ng-model="save.dtl.outStorageCd"
         items-source="_getComboData('saveDtlOutStorageCd')"
         display-member-path="name"
         selected-value-path="value"
         is-editable="false"
         initialized="_initComboBox(s)"
         selected-index-changed="selectedIndexChanged(s)"
         >
       </wj-combo-box>
     </span>
      --%>
    <%-- 발주내역으로 등록 --%>
    <button type="button" class="btn_skyblue ml5" id="btnOrderInfoRegist" ng-click="orderInfoRegist()" ng-if="btnOrderInfoRegistIfFg">
      <s:message code="vendrInstock.prod.orderInfoRegist"/></button>
    <%-- 추가/변경 --%>
    <button type="button" class="btn_skyblue ml5" id="btnAddProd" ng-click="addProd()" ng-if="btnAddProdIfFg">
      <s:message code="vendrInstock.prod.addProd"/></button>
    <%-- 저장 --%>
    <button type="button" class="btn_skyblue ml5" id="btnProdSave" ng-click="save()" ng-if="btnProdSaveIfFg">
      <s:message code="cmm.save"/></button>
    
  </div>

  <div class="w100 mt10 mb20">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 400px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.inUnitQty"/>" binding="inUnitQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.inEtcQty"/>" binding="inEtcQty" width="50" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.inTotQty"/>" binding="inTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.prevInTotQty"/>" binding="prevInTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.inAmt"/>" binding="inAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.inVat"/>" binding="inVat" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.inTot"/>" binding="inTot" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.prod.vendrVatFg01"/>" binding="vendrVatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrInstock/vendrInstockProd.js?ver=20201020.01" charset="utf-8"></script>

<%-- 상품 추가/변경 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrInstock/vendrInstockProdReg.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품 발주내역으로 세팅 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrInstock/vendrInstockOrderInfoReg.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
