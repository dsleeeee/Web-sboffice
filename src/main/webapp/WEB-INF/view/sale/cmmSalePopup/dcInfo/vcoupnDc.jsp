<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/dcInfo/vcoupnDc/"/>

<wj-popup id="wjVcoupnDcLayer" control="wjVcoupnDcLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vcoupnDcLayer" class="wj-dialog wj-dialog-columns" ng-controller="vcoupnDcCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vcoupnDc.vcoupnDcInfo"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2" style="height: 360px;">
      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="vcoupnDc.vcoupnNo"/>" binding="vcoupnNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcoupnDc.vcoupnNm"/>" binding="vcoupnNm" width="*" align="left" is-read-only="true" data-map="cashBillApprProcFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcoupnDc.vcoupnType"/>" binding="vcoupnType" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcoupnDc.vcoupnApprNo"/>" binding="vcoupnApprNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcoupnDc.vcoupnDcAmt"/>" binding="vcoupnDcAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcoupnDc.vcoupnSavePoint"/>" binding="vcoupnSavePoint" width="100" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vcoupnDc.membrOrderNo"/>" binding="membrOrderNo" width="120" align="center" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/dcInfo/vcoupnDc.js?ver=20190214.01" charset="utf-8"></script>
