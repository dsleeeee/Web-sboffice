<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div id="tableDayView" class="subCon"  ng-controller="tableDayCtrl" style="display: none;">
	<input type="hidden" id="HqOfficeCd" name="HqOfficeCd" ng-model="HqOfficeCd" value="${gvHqOfficeCd}"/>
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="tableDay.tableDaySale"/></a>
		<%-- 조회 --%>
		<button class="btn_blue fr mt5 mr10" id="btnTableDaySearch" ng-click="_broadcast('tableDayCtrlSrch')">
			<s:message code="cmm.search"/>
		</button>
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
       			<th><s:message code="cmm.search.date"/></th>
       			<td>
					<div class="sb-select">
						<span class="txtIn"><input id="srchTableDayStartDate" class="w110px"></span>
						<span class="rg">~</span>
						<span class="txtIn"><input id="srchTableDayEndDate" class="w110px"></span>
						<span class="chk ml10" style="display: none;">
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
							<jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreS.jsp" flush="true">
								<jsp:param name="targetId" value="tableDaySelectStore"/>
								<jsp:param name="subTargetId" value="tableDaySelectTable"/>
								<jsp:param name="closeFunc" value="closeSelectStore"/>
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
				<input type="hidden" id="tableDaySelectTableCdOrg" value=""/>
				<tr>
					<%-- 테이블선택 --%>
					<th><s:message code="tableDay.table" /></th>
					<td>
						<%-- 테이블선택 모듈 멀티 선택 사용시 include --%>
						<jsp:include page="/WEB-INF/view/sale/status/table/cmm/selectTableM.jsp" flush="true">
							<jsp:param name="targetId" value="tableDaySelectTable"/>
							<jsp:param name="targetStoreId" value="tableDaySelectStore"/>
							<jsp:param name="closeFunc" value="closeSelectTable"/>
						</jsp:include>
						<%--// 테이블선택 모듈 멀티 선택 사용시 include --%>
					</td>
				</tr>
		</tbody>
	</table>
	<div style="clear: both;"></div>

	<div class="mt10 oh sb-select dkbr">
		<%-- 페이지 스케일  --%>
		<wj-combo-box
			class="w100px fl"
			id="tableDayListScaleBox"
			ng-model="tableDayListScale"
			control="listScaleCombo"
			items-source="_getComboData('tableDayListScaleBox')"
			display-member-path="name"
			selected-value-path="value"
			initialized="initComboBox(s)"
			is-editable="true"
            text-changed="_checkValidation(s)">
		</wj-combo-box>
		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<input type="text" id="tableDaySelectStoreStoreNum" ng-model="storeNum">
		</c:if>
		<%-- 엑셀 다운로드 //TODO --%>
		<button class="btn_skyblue fr" ng-click="excelDownloadDay()">
			<s:message code="cmm.excel.down" />
		</button>
	</div>

	<%--위즈모 테이블--%>
	<div class="w100 mt10" id="wjWrapType3">
        <div class="wj-gridWrap">
			<wj-flex-grid
				id="tableDayGrid"
				autoGenerateColumns="false"
				selection-mode="Row"
				items-source="data"
				control="flex"
				initialized="initGrid(s,e)"
				is-read-only="true"
				frozen-columns="5"
				item-formatter="_itemFormatter">
				<!-- define columns -->
              <wj-flex-grid-column header="<s:message code="tableDay.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.saleDate"/>" binding="saleDate" width="130" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.saleDay"/>" binding="saleDay" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.totSaleCnt"/>" binding="totSaleCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
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

	<%-- 엑셀 리스트 --%>
    <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="tableDayExcelCtrl">
      <div class="wj-gridWrap">
            <wj-flex-grid
                id="tableDayExcelGrid"
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="excelFlex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                frozen-columns="5"
                item-formatter="_itemFormatter">
                <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="tableDay.saleDate"/>" binding="saleDate" width="130" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.saleDay"/>" binding="saleDay" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.totSaleCnt"/>" binding="totSaleCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDay.totGuestCnt"/>" binding="totGuestCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/table/day/day.js?ver=20200904.01" charset="utf-8"></script>

