<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrInstock/vendrInstockOrderInfoReg/"/>

<wj-popup id="wjVendrInstockOrderInfoRegLayer" control="wjVendrInstockOrderInfoRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vendrInstockOrderInfoRegLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrInstockOrderInfoRegCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrInstock.ord.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="vendrInstock.ord.txt1"/></li>
      </ul>

      <div class="mt20 tr">
      <%--출고창고 --%>
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
      
        <%-- 초기화 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDefault" ng-click="setDefault()">
          <s:message code="vendrInstock.ord.default"/></button>
        <%-- 발주내역으로 세팅 --%>
        <button type="button" class="btn_skyblue ml5" id="btnOrderToInstock" ng-click="setOrderToInstock()">
          <s:message code="vendrInstock.ord.setOrderToInstock"/></button>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5" id="btnOrderInfoRegSave" ng-click="save()">
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
            item-formatter="_itemFormatter"
            frozen-columns		="2">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.lastCostUprc"/>" binding="lastCostUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.poUnitQty"/>" binding="poUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.orderCostUprc"/>" binding="orderCostUprc" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.unitQty"/>" binding="orderUnitQty" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.etcQty"/>" binding="orderEtcQty" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inCostUprc"/>" binding="costUprc" width="80" align="right" is-read-only="false" max-length=8></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.unitQty"/>" binding="inUnitQty" width="80" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.etcQty"/>" binding="inEtcQty" width="80" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inTotQty"/>" binding="inTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.prevInTotQty"/>" binding="prevInTotQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inAmt"/>" binding="inAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inVat"/>" binding="inVat" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.inTot"/>" binding="inTot" width="90" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.ord.vendrVatFg01"/>" binding="vendrVatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
			
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrInstock/vendrInstockOrderInfoReg.js?ver=20181224.01" charset="utf-8"></script>
