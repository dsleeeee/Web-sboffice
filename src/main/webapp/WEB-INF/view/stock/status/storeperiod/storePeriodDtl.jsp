<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/frnchs/store/"/>

<wj-popup id="storePeriodDtlLayer" control="storePeriodDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="storePeriodDtlCtrl">
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
          <th class="tc"><s:message code="storePeriod.prodCd"/></th>
          <th class="tc"><s:message code="storePeriod.prodNm"/></th>
          <th class="tc"><s:message code="storePeriod.poUnitQty"/></th>
        </tr>
        <tr>
          <td><s:message code="volmErr.dtl.tableTd1_1"/></td>
          <td><s:message code="volmErr.dtl.tableTd2_1"/></td>
          <td></td>
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
        <div class="wj-gridWrap" style="height: 400px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="storePeriod.slipNo"/>" 		binding="slipNo" 		width="100" align="center" 	is-read-only="true"														></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.prodCd"/>" 		binding="prodCd" 		width="100" align="center" 	is-read-only="true"														></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.prodNm"/>" 		binding="prodNm" 		width="150" align="left" 	is-read-only="true"														></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.outUnitQty"/>" 	binding="outUnitQty" 	width="80" 	align="right" 	is-read-only="true" 	data-type="Number" 	format="n0" aggregate="Sum"	></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.outEtcQty"/>" 	binding="outEtcQty" 	width="80" 	align="right" 	is-read-only="true" 	data-type="Number" 	format="n0" aggregate="Sum"	></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.inUnitQty"/>" 	binding="inUnitQty" 	width="80" 	align="right" 	is-read-only="true" 	data-type="Number" 	format="n0" aggregate="Sum"	></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.inEtcQty"/>" 		binding="inEtcQty" 		width="80" 	align="right" 	is-read-only="true" 	data-type="Number" 	format="n0"	aggregate="Sum"	></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.errFg"/>" 		binding="errFg" 		width="100" align="center" 	is-read-only="false"	data-map="errFgMap"								></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.remark"/>" 		binding="remark" 		width="200" align="left" 	is-read-only="false"	max-length=300									></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/status/storeperiod/storePeriodDtl.js?ver=20200318.01" charset="utf-8"></script>
