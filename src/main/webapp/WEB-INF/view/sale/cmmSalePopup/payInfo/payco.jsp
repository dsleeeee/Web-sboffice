<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/payInfo/payco/"/>

<wj-popup id="wjPaycoLayer" control="wjPaycoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="paycoLayer" class="wj-dialog wj-dialog-columns" ng-controller="paycoCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="payco.paycoPay"/>
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
            <wj-flex-grid-column header="<s:message code="payco.cornrNm"/>" binding="cornrNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.apprProcFg"/>" binding="apprProcFg" width="60" align="center" is-read-only="true" data-map="apprProcFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.paycoBarcdTypeFg"/>" binding="paycoBarcdTypeFg" width="120" align="center" is-read-only="true" data-map="paycoBarcdTypeFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.paycoBarcdNo"/>" binding="paycoBarcdNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.instCnt"/>" binding="instCnt" width="40" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.apprAmt"/>" binding="apprAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.apprDate"/>" binding="apprDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.apprTime"/>" binding="apprTime" width="80" align="center" is-read-only="true" format="time"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.apprNo"/>" binding="apprNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.apprCompanyNm"/>" binding="apprCompanyNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.vanNm"/>" binding="vanNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="payco.acquireNm"/>" binding="acquireNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/payInfo/payco.js?ver=20190207.01" charset="utf-8"></script>
