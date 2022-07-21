<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/standMove/standMoveDtl/"/>

<wj-popup id="wjStandMoveDtlLayer" control="wjStandMoveDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="standMoveDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="standMoveDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="standMove.dtl.dtlTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 500px;">

      <div class="tr mt20 fr">OutstockDataMapper.updateDstbDataCreate
        <%-- 상품추가/변경 --%>
        <button type="button" id="btnAddProd" class="btn_skyblue ml5 fl" ng-click="addStandMoveDtlProd()" ng-if="btnAddProd">
          <s:message code="standMove.addProd"/></button>
        <%-- 저장 --%>
        <button type="button" id="btnRegSave" class="btn_skyblue ml5 fl" ng-click="saveDtl()" ng-if="btnDtlSave">
          <s:message code="cmm.save"/></button>
        <%-- 확정 --%>
        <button type="button" id="btnConfirm" class="btn_skyblue ml5 fl" ng-click="confirmDtl()" ng-if="btnConfirm">
          <s:message code="standMove.confm"/></button>
      </div>
      <div style="clear: both;"></div>

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
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="storeMove.reg.prodCd"/>" 			binding="prodCd" 	width="100" align="center" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.reg.prodNm"/>" 			binding="prodNm" 	width="150" align="left" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.reg.poUnitFg"/>" 		binding="poUnitFg"  width="70" 	align="right" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.reg.poUnitQty"/>" 		binding="poUnitQty" width="70" align="right" 	is-read-only="true"></wj-flex-grid-column>
           
            <wj-flex-grid-column header="<s:message code="standMove.safeStockQty"/>" 		binding="safeStockQty" 		width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="standMove.standCurrQty"/>"    	binding="totCurrQty" 		width="70" align="right" aggregate="Sum" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="standMove.standTotQty"/>" 		binding="standTotQty" 		width="70" align="right" visible="false" max-length=8 data-type="Number" format="n0" is-read-only="true" value="0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="standMove.standCurrUnitQty"/>" 	binding="standCurrUnitQty" 	width="70" align="right" max-length=8 data-type="Number" format="n0" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="standMove.standCurrEtcQty"/>" 	binding="standCurrEtcQty" 	width="70" align="right" max-length=8 data-type="Number" format="n0" is-read-only="true"></wj-flex-grid-column>
                        
            <wj-flex-grid-column header=""              binding="arrStorageCd"  	width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>
			<wj-flex-grid-column header=""              binding="arrStorageNm"  	width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>
			
			<wj-flex-grid-column header=""              binding="arrCurrQty"  		width="200" align="left"   	is-read-only="true"     visible="false" 		 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""              binding="arrUnitQty"  		width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>
			<wj-flex-grid-column header=""              binding="arrEtcQty"   		width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>
			<wj-flex-grid-column header=""              binding="arrTotQty"   		width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>

<!-- 			<wj-flex-grid-column header=""              binding="arrOrderAmt"      	width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column> -->
<!-- 			<wj-flex-grid-column header=""              binding="arrOrderVat"      	width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column> -->
<!-- 			<wj-flex-grid-column header=""              binding="arrOrderTot"      	width="200" align="left"   	is-read-only="true"     visible="false"  		 ></wj-flex-grid-column> -->
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/move/standMove/standMoveDtl.js?ver=20181224.01" charset="utf-8"></script>
