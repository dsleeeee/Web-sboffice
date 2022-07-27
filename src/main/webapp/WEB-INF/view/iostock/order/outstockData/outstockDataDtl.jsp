<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockData/outstockDataDtl/"/>

<wj-popup id="wjOutstockDataDtlLayer" control="wjOutstockDataDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height: 750px;">
  <div id="outstockDataDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="outstockDataDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">

      <div class="tr">
        <div class="tooltip fl">?
          <span class="tooltiptext tooltip-right">
          * <s:message code="outstockData.dtl.txt1"/><br/>
          * <s:message code="outstockData.dtl.txt2"/><br/>
          </span>
        </div>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.poUnitFg"/>" binding="poUnitFg" width="65" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.poUnitQty"/>" binding="poUnitQty" width="50" align="right" is-read-only="true" data-type="Number"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.dstbSplyUprc"/>" binding="dstbSplyUprc" width="60" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.dstbUnitQty"/>" binding="dstbUnitQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.dstbEtcQty"/>" binding="dstbEtcQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.dstbAmt"/>" binding="dstbAmt" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.dstbVat"/>" binding="dstbVat" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.dstbTot"/>" binding="dstbTot" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockData.dtl.remark"/>" binding="remark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockData/outstockDataDtl.js?ver=20220722.01" charset="utf-8"></script>
