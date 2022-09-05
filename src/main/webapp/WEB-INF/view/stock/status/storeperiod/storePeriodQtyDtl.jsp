<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/status/periodiostock/"/>

<wj-popup id="storePeriodQtyDtlLayer" control="storePeriodQtyDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="storePeriodQtyDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storePeriod.accStoreInInfo"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">
      <table class="tblType01 mt10">
        <colgroup>
          <col class="w20"/>
          <col class="w30"/>
          <col class="w25"/>
          <col class="w25"/>
        </colgroup>
        <tbody>
        <tr>
          <th class="tc"><s:message code="periodIostock.prodCd"/></th>
          <th class="tc"><s:message code="periodIostock.prodNm"/></th>
          <th class="tc"><s:message code="periodIostock.poUnitQty"/></th>
        </tr>
        <tr>
          <td>{{prodCd}}</td>
          <td>{{prodNm}}</td>
          <td>{{poUnitQty}}</td>
        </tr>
        </tbody>
      </table>

      <div class="tr mt20 fr">
        <div id="volmErrBtnLayer" style="display: none;">
          <span class="chk txtIn fl lh30" style="top: -2px;" ng-if="volmErrConfirmFg">
            <input type="checkbox" name="volmErrConfirmFg" id="volmErrConfirmFg" value="Y" ng-click="fnConfirmChk()"/>
            <label for="volmErrConfirmFg"><s:message code="volmErr.dtl.confirm"/></label>
          </span>
          <%-- 출고일자 --%>
        <%--<div id="divDtlOutDate" class="sb-select ml10 fl" style="display: none;">--%>
       		<div id="divDtlOutDate" class="sb-select ml10 fl">
            <p class="s12 fl mr5 lh30"><s:message code="volmErr.dtl.outDate"/> : </p>
            <span class="txtIn"><input id="dtlOutDate" class="w120px"></span>
          </div>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()" ng-if="btnDtlSave">
            <s:message code="cmm.save"/></button>
        </div>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 200px;">
          <wj-flex-grid
          	id="storePeriodQtyDtlGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="periodIostock.slipNo"/>"          binding="slipNo"	width="*"     align="center"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="periodIostock.outDate"/>"         binding="outDate"	width="*"     align="center"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="periodIostock.storeCd"/>"         binding="storeCd"	width="*"     align="center"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="periodIostock.storeNm"/>"         binding="storeNm"	width="*"     align="left"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"          	binding="qty"		width="*"     align="right"		is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"             binding="tot"		width="*"     align="right"		is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/status/storeperiod/storePeriodQtyDtl.js?ver=20200319.01" charset="utf-8"></script>