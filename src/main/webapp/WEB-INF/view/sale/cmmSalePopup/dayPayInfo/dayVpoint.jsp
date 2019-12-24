<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/dayPayInfo/dayVpoint/"/>

<wj-popup id="wjDayVpointLayer" control="wjDayVpointLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="dayVpointLayer" class="wj-dialog wj-dialog-columns" ng-controller="dayVpointCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="dayVpoint.dayVpointPay"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 340px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="dayVpoint.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayVpoint.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayVpoint.posNo"/>" binding="posNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayVpoint.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayVpoint.cashBillApprProcFg"/>" binding="cashBillApprProcFg" width="60" align="center" is-read-only="true" data-map="cashBillApprProcFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayVpoint.vpointCardNo"/>" binding="vpointCardNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayVpoint.vpointApprNo"/>" binding="vpointApprNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayVpoint.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayVpoint.membrOrderNo"/>" binding="membrOrderNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/dayPayInfo/dayVpoint.js?ver=20191219" charset="utf-8"></script>
