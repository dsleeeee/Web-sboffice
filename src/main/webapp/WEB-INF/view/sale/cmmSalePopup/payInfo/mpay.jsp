<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/payInfo/mpay/"/>

<wj-popup id="wjMpayLayer" control="wjMpayLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="mpayLayer" class="wj-dialog wj-dialog-columns" ng-controller="mpayCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="mpay.mpayPay"/>
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
            <wj-flex-grid-column header="<s:message code="mpay.cornrNm"/>" binding="cornrNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mpay.mpayNm"/>" binding="mpayNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mpay.apprProcFg"/>" binding="apprProcFg" width="60" align="center" is-read-only="true" data-map="apprProcFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mpay.mpayBarcdTypeFg"/>" binding="mpayBarcdTypeFg" width="120" align="center" is-read-only="true" data-map="mpayBarcdTypeFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mpay.mpayBarcdNo"/>" binding="mpayBarcdNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mpay.apprAmt"/>" binding="apprAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mpay.apprDate"/>" binding="apprDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mpay.apprTime"/>" binding="apprTime" width="80" align="center" is-read-only="true" format="time"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mpay.apprNo"/>" binding="apprNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/payInfo/mpay.js?ver=20190211.01" charset="utf-8"></script>
