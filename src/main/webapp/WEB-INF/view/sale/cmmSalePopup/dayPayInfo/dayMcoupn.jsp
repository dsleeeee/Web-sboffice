<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/dayPayInfo/dayMcoupn/"/>

<wj-popup id="wjDayMcoupnLayer" control="wjDayMcoupnLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="dayMcoupnLayer" class="wj-dialog wj-dialog-columns" ng-controller="dayMcoupnCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="dayMcoupn.dayMcoupnPay"/>
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
            <wj-flex-grid-column header="<s:message code="dayMcoupn.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.posNo"/>" binding="posNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.saleYn"/>" binding="saleYn" width="70" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.mcoupnTypeFg"/>" binding="mcoupnTypeFg" width="60" align="center" is-read-only="true" data-map="mcoupnTypeFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.mcoupnBarcdNo"/>" binding="mcoupnBarcdNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.apprDate"/>" binding="apprDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.apprTime"/>" binding="apprTime" width="80" align="center" is-read-only="true" format="time"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.apprNo"/>" binding="apprNo" width="180" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayMcoupn.mcoupnUprc"/>" binding="mcoupnUprc" width="80" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/dayPayInfo/dayMcoupn.js?ver=20190219.01" charset="utf-8"></script>
