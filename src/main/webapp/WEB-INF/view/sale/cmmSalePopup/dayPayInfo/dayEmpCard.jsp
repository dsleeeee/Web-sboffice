<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/dayPayInfo/dayEmpCard/"/>

<wj-popup id="wjDayEmpCardLayer" control="wjDayEmpCardLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px; height:480px;">
  <div id="dayEmpCardLayer" class="wj-dialog wj-dialog-columns" ng-controller="dayEmpcardCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="dayEmpCard.dayEmpCardPay"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2">
      <div class="oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
          <s:message code="cmm.excel.down" />
        </button>
      </div>
      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="dayEmpCard.officeNm"/>" binding="officeNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayEmpCard.officeDeptNm"/>" binding="officeDeptNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayEmpCard.officeEmpNo"/>" binding="officeEmpNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayEmpCard.officeEmpNm"/>" binding="officeEmpNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayEmpCard.accountFg"/>" binding="accountFg" width="70" align="center" is-read-only="true" data-map="accountFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayEmpCard.saleAmt"/>" binding="saleAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayEmpCard.remainAmt"/>" binding="remainAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>

  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/dayPayInfo/dayEmpCard.js?ver=20210719.01" charset="utf-8"></script>