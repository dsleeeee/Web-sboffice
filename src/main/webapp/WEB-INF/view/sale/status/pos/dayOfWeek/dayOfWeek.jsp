<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="posDayOfWeekView" class="subCon" style="display: none;" ng-controller="posDayOfWeekCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="pos.day"/></a>
		<%-- 조회 --%>
		<button class="btn_blue fr mt5 mr10" id="btnPosDayOfWeekSearch" ng-click="_broadcast('posDayOfWeekCtrlSrch')">
			<s:message code="cmm.search"/>
		</button>
	</div>
	<table class="searchTbl">
		<colgroup>
			<col class="w15"/>
			<col class="w35"/><col class="w15"/>
        	<col class="w35"/>
       	</colgroup>
       	<tbody>
       		<tr>
       			<th><s:message code="cmm.search.date"/></th>
       			<td>
					<div class="sb-select">
						<span class="txtIn"><input id="srchPosDayOfWeekStartDate" class="w120px"></span>
						<span class="rg">~</span>
						<span class="txtIn"><input id="srchPosDayOfWeekEndDate" class="w120px"></span>
						<span class="chk ml10">
							<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
							<label for="chkDt">
								<s:message code="cmm.all.day" />
							</label>
						</span>
					</div>
				</td>

				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
					<input type="hidden" id="posDayOfWeekSelectStoreCd" value=""/>
					<tr>
						<%-- 매장코드 --%>
						<th><s:message code="todayBillSaleDtl.store"/></th>
						<td>
							<%-- 매장선택 모듈 멀티 선택 사용시 include --%>
<%-- 							<jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectStoreM.jsp" flush="true"> --%>
							<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
								<jsp:param name="targetId" value="posDayOfWeekSelectStore"/>
								<jsp:param name="targetPosId" value="posDayOfWeekSelectPos"/>
								<jsp:param name="closeFunc" value="getPosNmList"/>
							</jsp:include>
							<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
						</td>
					</tr>
				</c:if>
				<c:if test="${sessionInfo.orgnFg == 'STORE'}">
					<input type="hidden" id="posDayOfWeekSelectStoreCd" value="${sessionInfo.storeCd}"/>
				</c:if>

				<input type="hidden" id="posDayOfWeekSelectPosCd" value=""/>
				<input type="hidden" id="posDayOfWeekSelectPosName" value=""/>
				<input type="hidden" id="posDayOfWeekSelectHqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
				<tr>
					<%-- 포스선택 --%>
					<th><s:message code="pos.pos" /></th>
					<td>
						<%-- 포스선택 모듈 멀티 선택 사용시 include --%>
						<jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectPosM.jsp" flush="true">
							<jsp:param name="targetId" value="posDayOfWeekSelectPos"/>
							<jsp:param name="targetStoreId" value="posDayOfWeekSelectStore"/>
							<jsp:param name="closeFunc" value="getPosNmList"/>
						</jsp:include>
						<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
					</td>
				</tr>

			</tr>
		</tbody>
	</table>
	<div style="clear: both;"></div>

	<div class="mt20 oh sb-select dkbr">
		<%-- 페이지 스케일  --%>
		<wj-combo-box
			class="w100px fl"
			id="posDayOfWeekListScaleBox"
			ng-model="posDayOfWeekListScale"
			items-source="_getComboData('posDayOfWeekListScaleBox')"
			display-member-path="name"
			selected-value-path="value"
			is-editable="false"
			initialized="initComboBox(s)">
		</wj-combo-box>

		<%-- 엑셀 다운로드 //TODO --%>
		<button class="btn_skyblue fr" ng-click="excelDownloadDay()">
			<s:message code="cmm.excel.down" />
		</button>
	</div>

	<%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType3">
      <div class="wj-gridWrap">
			<wj-flex-grid
				id="posDayOfWeekGrid"
				autoGenerateColumns="false"
				selection-mode="Row"
				items-source="data"
				control="flex"
				initialized="initGrid(s,e)"
				loaded-rows="loadedRows(s,e)"
				is-read-only="true"
				frozen-columns="6"
				item-formatter="_itemFormatter">
				<!-- define columns -->
				<wj-flex-grid-column header="<s:message code="pos.yoil"/>"				binding="dayName" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.saleStore"/>"			binding="saleStoreCnt" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleAmt"/>"		binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totDcAmt"/>"			binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totRealSaleAmt"/>"	binding="totRealSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleQty"/>"		binding="totSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			</wj-flex-grid>
			<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				<jsp:param name="pickerTarget" value="posDayOfWeekCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
		</div>
		<%--//위즈모 테이블--%>
	</div>

	<%-- 페이지 리스트 --%>
	<div class="pageNum mt20">
	<%-- id --%>
		<ul id="posDayOfWeekCtrlPager" data-size="10">
		</ul>
	</div>
	<%--//페이지 리스트--%>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/pos/dayOfWeek/dayOfWeek.js?ver=20190125.02" charset="utf-8"></script>

