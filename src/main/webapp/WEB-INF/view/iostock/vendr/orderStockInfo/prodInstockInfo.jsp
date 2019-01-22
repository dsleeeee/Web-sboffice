<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/orderStockInfo/prodInstockInfo/"/>

<wj-popup id="wjProdInstockInfoLayer" control="wjProdInstockInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
  <div id="prodInstockInfoLayer" class="wj-dialog wj-dialog-columns" ng-controller="prodInstockInfoCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prodInstockInfo.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 450px;">
      <div class="txtIn">
        <p id="subTitle" class="s14 bk fl" ng-bind-html="prodCdNm"></p>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.instockDate"/>" binding="instockDate" width="*" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.slipFg"/>" binding="slipFg" width="50" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.costUprc"/>" binding="costUprc" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.inTotQty"/>" binding="inTotQty" width="50" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/orderStockInfo/prodInstockInfo.js?ver=20181224.01" charset="utf-8"></script>
