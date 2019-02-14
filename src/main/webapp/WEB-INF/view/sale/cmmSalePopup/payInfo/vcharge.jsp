<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/payInfo/vcharge/"/>

<wj-popup id="wjVchargeLayer" control="wjVchargeLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vchargeLayer" class="wj-dialog wj-dialog-columns" ng-controller="vchargeCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vcharge.vchargePay"/>
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
            <wj-flex-grid-column header="<s:message code="vcharge.cornrNm"/>" binding="cornrNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcharge.cashBillApprProcFg"/>" binding="cashBillApprProcFg" width="60" align="center" is-read-only="true" data-map="cashBillApprProcFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcharge.vchargeCardNo"/>" binding="vchargeCardNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcharge.vchargeApprNo"/>" binding="vchargeApprNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcharge.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcharge.membrOrderNo"/>" binding="membrOrderNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/payInfo/vcharge.js?ver=20190207.01" charset="utf-8"></script>
