<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/status/storeperiod/"/>

<div class="subCon" ng-controller="stockManageDtlViewCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="stockManageDtlView.stockManageDtlView"/></a>
		<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('stockManageDtlViewCtrlSrch')"><s:message code="cmm.search"/></button>
	</div>
	<table class="searchTbl">
		<colgroup>
			<col class="w15"/>
			<col class="w35"/>
			<col class="w15"/>
			<col class="w35"/>
		</colgroup>
		<tbody>
			<tr>
				<%-- 조회일자 --%>
				<th><s:message code="stockManageDtlView.srchDate" /></th>
				<td>
				<div class="sb-select">
					<span class="txtIn"><input id="srchClassStartDate" class="w110px"></span>
					<span class="rg">~</span>
					<span class="txtIn"><input id="srchClassEndDate" class="w110px"></span>
				</div>
				</td>
				<%-- 진행 --%>
				<th><s:message code="stockManageDtlView.procFg" /></th>
				<td>
					<div class="sb-select">
					<span class="txtIn w150px">
						<wj-combo-box
								id="srchProcFg"
								ng-model="procFgModel"
								items-source="_getComboData('srchProcFg')"
								display-member-path="name"
								selected-value-path="value"
								is-editable="false"
								initialized="_initComboBox(s)">
						</wj-combo-box>
					</span>
					</div>
				</td>
			</tr>
			<tr>
				<%-- 상태 --%>
				<th><s:message code="stockManageDtlView.hqGbn" /></th>
				<td>
					<div class="sb-select fl mr5 w110px">
						<wj-combo-box
							id="srchStatus"
							ng-model="hqGbnModel"
							items-source="_getComboData('srchStatus')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							selected-index-changed="setReason(s)">
						</wj-combo-box>
					</div>
					<div class="sb-select fl w110px">
						<wj-combo-box
								id="srchReason"
								ng-model="reason"
								items-source="_getComboData('srchReason')"
								display-member-path="name"
								selected-value-path="value"
								is-editable="false"
								initialized="_initComboBox(s)">
						</wj-combo-box>
					</div>
				</td>
			</tr>
			<tr>
				<th><s:message code="stockManageDtlView.prodCd"/></th>
				<td>
					<input type="text" id="prodCd" class="sb-input w100" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
				</td>
				<th><s:message code="stockManageDtlView.prodNm"/></th>
				<td>
					<input type="text" id="prodNm" class="sb-input w100" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
				</td>
			</tr>
			<tr>
				<%-- 바코드 --%>
				<th><s:message code="stockManageDtlView.barcd" /></th>
				<td>
					<input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch();"/>
				</td>
				<%-- 분류조회 --%>
				<th><s:message code="prodCorner.prodClass" /></th>
				<td>
					<input type="text" class="sb-input w80" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
						   placeholder="<s:message code="prodCorner.prodClass" /> 선택" readonly/>
					<input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
					<button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
				</td>
			</tr>
		</tbody>
	</table>

	<input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
	<input type="hidden" id="storeCd" value="${sessionInfo.storeCd}"/>

	<div class="mt20 oh sb-select dkbr">

    	<%-- 엑셀 다운로드 --%>
    	<button class="btn_skyblue ml5 fr" id="btnExcelDown" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
  	</div>

  	<%--위즈모 테이블--%>
    <div id="wjGridWrap" class="mt10">
      <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
          id="stockManageDtlViewGrid"
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
		  is-read-only="true"
          item-formatter="_itemFormatter">

          <!-- define columns -->
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.hqGbn"/>"		binding="hqGbn"		width="0" align="center" visible="false"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.prodCd"/>"		binding="prodCd"	width="120" align="center" ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.prodNm"/>"		binding="prodNm"	width="120" align="left" ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.hqGbn"/>"		binding="hqGbnNm"	width="60" align="center" ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.totDate"/>"	binding="totDate"	width="80" align="center" ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.seqNo"/>"		binding="seqNo"		width="60" align="right" ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.procFg"/>"		binding="procFgNm"	width="60" align="center" ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.title"/>"		binding="title"		width="80" align="left" ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.reasonNm"/>"	binding="reasonNm"	width="60" align="center" ></wj-flex-grid-column>

			<wj-flex-grid-column header="<s:message code="stockManageDtlView.currQty"/>"	binding="currQty"	width="70" align="right" ></wj-flex-grid-column>	<!-- 전산재고 -->
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.acins"/>"		binding="acinsQty"	width="60" align="right" ></wj-flex-grid-column>	<!-- 실사 -->
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.adj"/>"		binding="adjQty"	width="60" align="right" ></wj-flex-grid-column>	<!-- 조정 -->
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.disuse"/>"		binding="disuseQty"	width="60" align="right" ></wj-flex-grid-column>	<!-- 폐기 -->
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.mod"/>"	    binding="modQty"	width="60" align="right" ></wj-flex-grid-column>	<!-- 수정 -->
			<wj-flex-grid-column header="<s:message code="stockManageDtlView.remark"/>"		binding="remark"	width="100" align="left" ></wj-flex-grid-column>	<!-- 비고 -->
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="stockManageDtlViewCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

</div>

<%-- 합계 Title 정렬 CSS --%>
<style>
	.itemAlignment {
		text-align:left !important;
	}
</style>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<script type="text/javascript" src="/resource/solbipos/js/stock/manage/dtlView/dtlView.js?ver=20220712.01" charset="utf-8"></script>
