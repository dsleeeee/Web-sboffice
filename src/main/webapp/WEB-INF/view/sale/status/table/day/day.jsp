<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="tableDayView" class="subCon"  ng-controller="tableDayCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="tableDay.tableDaySale"/></a>
		<%-- 조회 --%>
		<button class="btn_blue fr mt5 mr10" id="btnTableDaySearch" ng-click="_broadcast('tableDayCtrl')">
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
						<span class="txtIn"><input id="srchTableDayStartDate" class="w120px"></span>
						<span class="rg">~</span>
						<span class="txtIn"><input id="srchTableDayEndDate" class="w120px"></span>
						<span class="chk ml10">
							<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
							<label for="chkDt">
								<s:message code="cmm.all.day" />
							</label>
						</span>
					</div>
				</td>
			</tr>
				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
					<input type="hidden" id="tableDaySelectStoreCd" value=""/>
					<tr>
						<%-- 매장코드 --%>
						<th><s:message code="todayDtl.store"/></th>
						<td>
							<%-- 매장선택 모듈 멀티 선택 사용시 include --%>
							<jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
								<jsp:param name="targetId" value="tableDaySelectStore"/>
								<jsp:param name="targetTableId" value="tableDaySelectTable"/>
							</jsp:include>
							<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
						</td>
					</tr>
				</c:if>
				<c:if test="${sessionInfo.orgnFg == 'STORE'}">
					<input type="hidden" id="tableDaySelectStoreCd" value="${sessionInfo.storeCd}"/>
				</c:if>

				<input type="hidden" id="tableDaySelectTableCd" value=""/>
				<input type="hidden" id="tableDaySelectTableName" value=""/>
				<tr>
					<%-- 테이블선택 --%>
					<th><s:message code="tableDay.table" /></th>
					<td>
						<%-- 테이블선택 모듈 멀티 선택 사용시 include --%>
						<jsp:include page="/WEB-INF/view/sale/status/table/cmm/selectTableM.jsp" flush="true">
							<jsp:param name="targetId" value="tableDaySelectTable"/>
							<jsp:param name="targetStoreId" value="tableDaySelectStore"/>
						</jsp:include>
						<%--// 테이블선택 모듈 멀티 선택 사용시 include --%>
					</td>
				</tr>
		</tbody>
	</table>
	<div style="clear: both;"></div>

	<div class="mt20 oh sb-select dkbr">
		<%-- 페이지 스케일  --%>
		<wj-combo-box
			class="w100px fl"
			id="tableDayListScaleBox"
			ng-model="tableDayListScale"
			items-source="_getComboData('tableDayListScaleBox')"
			display-member-path="name"
			selected-value-path="value"
			is-editable="false"
			initialized="initComboBox(s)">
		</wj-combo-box>
		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<input type="text" id="tableDaySelectStoreStoreNum" ng-model="storeNum">
		</c:if>
		<%-- 엑셀 다운로드 //TODO --%>
		<button class="btn_skyblue fr" ng-click="excelDownloadDay()">
			<s:message code="cmm.excel.down" />
		</button>
	</div>

	<div class="w100 mt10">
		<%--위즈모 테이블--%>
		<div class="wj-gridWrap" style="height: 350px;">
			<wj-flex-grid
				id="tableDayGrid"
				autoGenerateColumns="false"
				selection-mode="Row"
				items-source="data"
				control="flex"
				initialized="initGrid(s,e)"
				is-read-only="true"
				item-formatter="_itemFormatter">
				<!-- define columns -->
              <wj-flex-grid-column header="<s:message code="tableDay.saleDate"/>" binding="saleDate" width="130" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.saleDay"/>" binding="saleDay" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.totRealSaleCnt"/>" binding="totRealSaleCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.totGuestCnt"/>" binding="totGuestCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
			<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				<jsp:param name="pickerTarget" value="tableDayCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
		</div>
		<%--//위즈모 테이블--%>
	</div>

	<%-- 페이지 리스트 --%>
	<div class="pageNum mt20">
	<%-- id --%>
		<ul id="tableDayCtrlPager" data-size="10">
		</ul>
	</div>
	<%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/table/day/day.js?ver=20190125.02" charset="utf-8"></script>
<%-- 상품매출내역 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/table.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

