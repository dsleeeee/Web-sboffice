<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/loan/storeLoanManageDtl/storeLoanManageDtl/"/>

<wj-popup id="wjStoreLoanManageDtlLayer" control="wjStoreLoanManageDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="storeOrderDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeLoanManageDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="loan.dtl.title"/><span id="spanDtlTitle" class="ml10"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 조회일자 --%>
          <th><s:message code="loan.dtl.searchDate"/></th>
          <td colspan="3">
            <div class="sb-select">
              <span class="txtIn"><input id="srchDtlStartDate" class="w120px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchDtlEndDate" class="w120px"></span>
            </div>
          </td>
        </tr>
        </tbody>
      </table>

      <%-- 조회 --%>
      <div class="mt10 pdb20 oh bb">
        <button class="btn_blue fr" id="btnSearch" ng-click="searchStoreLoanManageDtl()">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="mt20 oh sb-select dkbr">
        <%-- 엑셀 다운로드 --%>
        <%--<button id="btnExcel" class="btn_skyblue fr" ng-click="excelDown()"><s:message code="cmm.excel.down"/></button>--%>
      </div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="loan.dtl.loanDate"/>" binding="loanDate" width="120" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="loan.dtl.outAmt"/>" binding="outAmt" width="120" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="loan.dtl.inAmt"/>" binding="inAmt" width="120" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="loan.dtl.currLoanAmt"/>" binding="currLoanAmt" width="120" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="loan.dtl.remark"/>" binding="remark" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoanManage/storeLoanManageDtl.js?ver=20181224.01" charset="utf-8"></script>
