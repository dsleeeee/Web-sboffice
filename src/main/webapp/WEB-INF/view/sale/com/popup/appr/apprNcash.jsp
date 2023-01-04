<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/com/popup/"/>

<wj-popup id="apprNcashLayer" control="apprNcashLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="ncashLayer" class="wj-dialog wj-dialog-columns" ng-controller="saleApprNcashCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="saleComPopup.card.store"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2" style="height: 400px;">
      <div class="oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
          <s:message code="cmm.excel.down" />
        </button>
      </div>
      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 320px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.saleDate"/>" 		binding="saleDate" 		width="80" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.posNo"/>" 			binding="posNo"			width="60" 		align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.billNo"/>" 		binding="billNo"		width="60" 		align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncard.apprProc"/>" 		binding="apprProc"		width="60" 		align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.saleAmt"/>" 		binding="saleAmt" 		width="80" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.tipAmt"/>" 		binding="tipAmt" 		width="80" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.vatAmt"/>" 		binding="vatAmt"		width="80" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.cashBillCardNo"/>" 		binding="cashBillCardNo" 		width="130" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.cashBillCardTypeFg"/>" 	binding="cashBillCardTypeFg" 	width="60" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.apprDt"/>" 		binding="apprDt" 		width="130" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.apprNo"/>" 		binding="apprNo" 		width="70" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.ncash.apprAmt"/>" 		binding="apprAmt" 		width="80" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>

  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/com/appr/apprNcash.js?ver=20210415.03" charset="utf-8"></script>