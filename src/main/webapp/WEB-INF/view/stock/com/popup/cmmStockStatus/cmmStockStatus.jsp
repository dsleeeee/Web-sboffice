<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/com/popup/cmmStockStatus/"/>

<wj-popup id="cmmStockStatusLayer" control="cmmStockStatusLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="cmmStockStatusCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="cmmStockStatus.stockStatus"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">
    <%-- 상품코드, 상품명 --%>
	<table class="searchTbl" style="text-align: center;">
		<colgroup>
			<col class="w50"/>
			<col class="w50"/>
		</colgroup>
		<tbody>
			<tr style="border-top: 1px solid #ccc;">
				<th style="text-align: center;"><s:message code="cmmStockStatus.prodCd"/></th>
				<th style="text-align: center;"><s:message code="cmmStockStatus.prodNm"/></th>
			</tr>
			<tr>
                <td style="border-left: 1px solid #ccc;">{{prodCd}}</td>
                <td>{{prodNm}}</td>
            </tr>
		</tbody>
	</table>

    <!-- 조회기간, 단위구분 -->
    <table class="searchTbl mt20">
        <colgroup>
            <col class="w13"/>
            <col class="w37"/>
            <col class="w13"/>
            <col class="w37"/>
        </colgroup>
        <%-- 조회기간 --%>
        <tr style="border-top: 1px solid #ccc;">
            <th><s:message code="cmmStockStatus.srchDate" /></th>
            <td colspan="3">
            <div class="sb-select">
                <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w120px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w120px"></span>
            </div>
            </td>
        </tr>
        <%-- 단위구분 --%>
        <tr>
            <th><s:message code="cmmStockStatus.unitFg" /></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn w150px">
                        <wj-combo-box
                            id="srchUnitFg"
                            ng-model="unitFgPop"
                            items-source="_getComboData('srchUnitFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td>
        </tr>
    </table>
	<div class="mt20 oh sb-select dkbr">
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnStockStatusSearch" ng-click="_broadcast('cmmStockStatusSrchCtrl')">
            <s:message code="cmm.search" />
        </button>
    </div>

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px; overflow-x: hidden; overflow-y: hidden;">
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
            <wj-flex-grid-column header="<s:message code="cmmStockStatus.iostockDate"/>"        binding="ioProcDate"        width="*"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmmStockStatus.iostockFg"/>"          binding="ioOccrFgNm"        width="*"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmmStockStatus.qty"/>"                binding="ioOccrQty"         width="*"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmmStockStatus.totQTy"/>"             binding="totIoOccrQty"      width="*"     align="center" is-read-only="true"></wj-flex-grid-column>
          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="cmmStockStatusCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/com/popup/cmmStockStatus/cmmStockStatus.js?ver=20190207.01" charset="utf-8"></script>
