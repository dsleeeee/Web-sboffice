<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/frnchs/store/"/>

<wj-popup id="frnchsStoreDtlLayer" control="frnchsStoreDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="frnchsStoreDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="frnchsStore.dtl"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
     <%-- 매장상세표 --%>
	<table class="searchTbl">
		<colgroup>
			<col class="w13"/>
			<col class="w37"/>
			<col class="w13"/>
			<col class="w37"/>
		</colgroup>
		<tbody>
			<tr style="border-top: 1px solid #e8e8e8;">
				<th><s:message code="cmm.search.date"/></th>
				<td colspan="3">{{startDate}} ~ {{endDate}}</td>
			</tr>
			<tr>
				<th><s:message code="frnchsStore.storeNm"/></th>
				<td>{{storeNm}}</td>
				<th><s:message code="frnchsStore.storeCd"/></th>
				<td>{{storeCd}}</td>
			</tr>
			<tr>
				<th><s:message code="frnchsStore.bizNo"/></th>
				<td>{{bizNo}}</td>
				<th><s:message code="frnchsStore.bizStoreNm"/></th>
				<td>{{bizStoreNm}}</td>
			</tr>
			<tr>
				<th><s:message code="frnchsStore.ownerNm"/></th>
				<td>{{ownerNm}}</td>
				<th><s:message code="frnchsStore.postNo"/></th>
				<td>{{postNo}}</td>
			</tr>
			<tr>
				<th><s:message code="frnchsStore.addr"/></th>
				<td>{{addr}}</td>
				<th><s:message code="frnchsStore.clsFgNm"/></th>
				<td>{{clsFgNm}}</td>
			</tr>
			<tr>
				<th><s:message code="frnchsStore.storeTypeNm"/></th>
				<td>{{storeTypeNm}}</td>
				<th><s:message code="frnchsStore.email"/></th>
				<td>{{email}}</td>
			</tr>
		</tbody>
	</table>
	<div class="searchBar flddUnfld mt10">
        <a href="#" class="open fl"><s:message code="dstmn.stmtAcct"/>/<s:message code="frnchsStore.taxReport"/></a>
    </div>

    <input type="hidden" id="storeHqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
    <%-- 세금계산서 --%>
    <table class="searchTbl">
        <colgroup>
            <col class="w13"/>
            <col class="w37"/>
            <col class="w13"/>
            <col class="w37"/>
        </colgroup>
        <tbody>
        <tr>
      <%-- 거래명세표 --%>
      <th><s:message code="dstmn.stmtAcct"/></th>
      <td colspan="3">
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="storeDtlStmtAcctFg"
            ng-model="stmtAcctFg"
            items-source="_getComboData('storeDtlStmtAcctFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <a href="#" class="btn_grayS" ng-click="report('trans')"><s:message code="dstmn.stmtAcctPrint"/></a>
        <%-- <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.stmtAcctExcel"/></a> --%>
      </td>
    </tr>
    <tr>
      <%-- 세금계산서 --%>
      <th><s:message code="frnchsStore.taxReport"/></th>
      <td colspan="3">
        <div class="sb-select fl mr5">
          <span class="txtIn"><input id="storeDtlWrittenDate" class="w120px"></span>
        </div>
        <span class="txtIn w80px sb-select fl mr5">
          <wj-combo-box
            id="storeDtlBillFg"
            ng-model="billFg"
            items-source="_getComboData('storeDtlBillFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <span class="txtIn w120px sb-select fl mr5">
          <wj-combo-box
            id="storeDtlTaxFg"
            ng-model="taxFg"
            items-source="_getComboData('storeDtlTaxFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <a href="#" class="btn_grayS" ng-click="report('tax')"><s:message code="dstmn.taxBillIssue"/></a>
      </td>
    </tr>
        </tbody>
    </table>
      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 250px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            id="storeDtlGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="frnchsStore.prodCd"/>"        binding="prodCd"         width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStore.prodNm"/>"       binding="prodNm"        width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            <!-- orgplceCd는 원산지코드임.. 나중에 '원산지명: orplceNm' 확인 후 변경하기 -->
            <wj-flex-grid-column header="<s:message code="frnchsStore.orgplceCd"/>"       binding="orgplceCd"        width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStore.poUnitFgNm"/>"       binding="poUnitFgNm"        width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStore.poUnitQty"/>"   binding="poUnitQty"    width="100"     align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStore.splyUprc"/>"  binding="splyUprc"   width="100"     align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStore.totQty"/>"   binding="totQty"    width="100"     align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStore.totAmt"/>"   binding="totAmt"    width="100"     align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStore.totVat"/>"       binding="totVat"        width="100"     align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="frnchsStore.totTot"/>"        binding="totTot"         width="100"     align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/frnchs/store/storeDtl.js?ver=20190207.01" charset="utf-8"></script>
