<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/com/popup/"/>

<wj-popup id="apprPartnerLayer" control="apprPartnerLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="partnerLayer" class="wj-dialog wj-dialog-columns" ng-controller="saleApprPartnerCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="saleComPopup.card.store"/>
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
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.saleDate"/>" 	binding="saleDate" 		width="100" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.posNo"/>" 		binding="posNo"			width="80" 		align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.apprProc"/>" 	binding="saleFg"		width="80" 		align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.apprProcFg"/>" 	binding="apprProcFg" 	width="80" 		align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.saleAmt"/>" 		binding="saleAmt" 		width="100" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>            
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.tipAmt"/>" 		binding="tipAmt" 		width="100" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>     
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.vatAmt"/>" 		binding="vatAmt"		width="100" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>          
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.partnCardno"/>" 	binding="partnCardno" 	width="150" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.savePoint"/>" 	binding="savePoint" 	width="100" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.usePoint"/>" 	binding="usePoint" 		width="100" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.apprDt"/>" 		binding="apprDt" 		width="150" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.apprNo"/>" 		binding="apprNo" 		width="100" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.partner.apprAmt"/>" 		binding="apprAmt" 		width="100" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/com/appr/apprPartner.js?ver=20190207.01" charset="utf-8"></script>
