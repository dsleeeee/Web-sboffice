<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/frnchs/order/prodInOutstockInfo/"/>

<wj-popup id="wjProdInOutstockInfoLayer" control="wjProdInOutstockInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="prodInOutstockInfoLayer" class="wj-dialog wj-dialog-columns" ng-controller="prodInOutstockInfoCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prodInOutstockInfo.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 450px;">
      <div style="clear: both;"></div>

      <div class="searchBar w100 mt10 mb20">
        <div class="oh sb-select mb10">
          <span class="fl bk lh30" id="subTitle" ng-bind-html="prodCdNm"></span>
          
          <%-- 엑셀다운로드 --%>
          <button class="btn_skyblue fr ml20 mt5" ng-click="excelDownloadProdInOutstockInfo()" style="margin-right: 10px">
              <s:message code="cmm.excel.down" />
          </button>
            
          <span class="fr bk sb-radio mt10">
              <label><input type="radio" name="displayFgPop" id="displayAllPop" value="all" checked="checked" ng-click="displayChg()"><s:message code="cmm.all" /></label>
              <label><input type="radio" name="displayFgPop" id="displayCntSumPop" value="cntSum" ng-click="displayChg()"><s:message code="orderStockInfo.dtl.inTotQty+inTot" /></label>
          </span>
        </div>
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            id="prodInOutstockInfoGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.outDate"/>"    binding="outDt"      width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.inDate"/>"     binding="inDt"       width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.storeNm"/>"    binding="storeNm"    width="180"align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.slipFg"/>"     binding="slipFgNm"   width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.slipKind"/>"   binding="slipKindNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.procFg"/>"     binding="procFgNm"   width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.mdSplyUprc"/>" binding="splyUprc"   width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.inTotQty"/>"   binding="outTotQty"  width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.inAmt"/>"      binding="outAmt"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.inVat"/>"      binding="outVat"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.inTot"/>"      binding="outTot"     width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.inTotQty"/>"   binding="inTotQty"   width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.inAmt"/>"      binding="inAmt"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.inVat"/>"      binding="inVat"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.inTot"/>"      binding="inTot"      width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.penaltyAmt"/>" binding="penaltyAmt" width="80" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInOutstockInfo.remark"/>"     binding="remark"     width="80" align="center" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/frnchs/prod/prodInOutstockInfo.js?ver=20220728.01" charset="utf-8"></script>