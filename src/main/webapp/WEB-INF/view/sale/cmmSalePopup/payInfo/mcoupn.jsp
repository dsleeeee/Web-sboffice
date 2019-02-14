<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/payInfo/mcoupn/"/>

<wj-popup id="wjMcoupnLayer" control="wjMcoupnLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="mcoupnLayer" class="wj-dialog wj-dialog-columns" ng-controller="mcoupnCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="mcoupn.mcoupnPay"/>
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
            <wj-flex-grid-column header="<s:message code="mcoupn.cornrNm"/>" binding="cornrNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mcoupn.mcoupnTypeFg"/>" binding="mcoupnTypeFg" width="60" align="center" is-read-only="true" data-map="mcoupnTypeFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mcoupn.mcoupnBarcdNo"/>" binding="mcoupnBarcdNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mcoupn.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mcoupn.apprDate"/>" binding="apprDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mcoupn.apprTime"/>" binding="apprTime" width="80" align="center" is-read-only="true" format="time"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mcoupn.apprNo"/>" binding="apprNo" width="180" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mcoupn.mcoupnUprc"/>" binding="mcoupnUprc" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="mcoupn.mcoupnRemainAmt"/>" binding="mcoupnRemainAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/payInfo/mcoupn.js?ver=20190211.01" charset="utf-8"></script>
