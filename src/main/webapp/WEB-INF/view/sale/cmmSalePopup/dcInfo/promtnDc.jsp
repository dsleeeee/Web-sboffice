<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/dcInfo/promtnDc/"/>

<wj-popup id="wjPromtnDcLayer" control="wjPromtnDcLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
  <div id="promtnDcLayer" class="wj-dialog wj-dialog-columns" ng-controller="promtnDcCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="promtnDc.promtnDcInfo"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="promtnDc.prodCd"/>" binding="prodCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="promtnDc.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="promtnDc.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="promtnDc.promtnProdFgNm"/>" binding="promtnProdFgNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/dcInfo/promtnDc.js?ver=20190214.01" charset="utf-8"></script>
