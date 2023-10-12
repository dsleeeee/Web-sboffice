<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}" />
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}" />
<c:set var="baseUrl" value="/sale/satus/table/" />

<div id="tableDayPeriodView" class="subCon" ng-controller="tableDayPeriodCtrl" style="display: none;">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message
				code="tableDayPeriod.tableDayPeriodSale" /></a>
		<%-- 조회 --%>
   		<div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      		<button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('tableDayPeriodCtrlSrch')"><s:message code="cmm.search"/></button>
   		</div>
	</div>


	<table class="searchTbl">
		<colgroup>
			<col class="w15" />
			<col class="w35" />
			<col class="w15" />
			<col class="w35" />
		</colgroup>
		<tbody>
			<tr>
				<%-- 조회일자 --%>
				<th><s:message code="cmm.search.date" /></th>
				<td>
					<div class="sb-select">
						<span class="txtIn w110px">
						<wj-input-date id="srchTableDayPeriodStartDate"
								ng-model='srchTableDayPeriodStartDate'
								control="tableDayPeriodStartDate"> </wj-input-date>
						</span> <span class="rg">~</span> <span class="txtIn w110px"> <wj-input-date
								id="srchTableDayPeriodEndDate"
								ng-model="srchTableDayPeriodEndDate"
								control="tableDayPeriodEndDate"> </wj-input-date>

						</span>
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
				<tr>
					<%-- 매장선택 --%>
					<th><s:message code="cmm.store.select"/></th>
				  	<td>
						<%-- 매장선택 모듈 사용시 include --%>
						<jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
							<jsp:param name="targetTypeFg" value="M"/>
							<jsp:param name="targetId" value="tableDayPeriodSelectStore"/>
						</jsp:include>
						<%--// 매장선택 모듈 사용시 include --%>
				  	</td>
				</tr>
		  	</c:if>
			<c:if test="${sessionInfo.orgnFg == 'STORE'}">
				<input type="hidden" id="tableDayPeriodSelectStoreCd" value="${sessionInfo.storeCd}"/>
			</c:if>
		</tbody>
	</table>

	<div class="mt10 oh sb-select dkbr">
		<div class="sb-select dkbr">
			<%-- 페이지 스케일  --%>
			<wj-combo-box
			    class="w100px fl"
			    id="listScaleBox"
				ng-model="listScale"
				control="listScaleCombo"
				items-source="_getComboData('listScaleBox')"
				display-member-path="name"
				selected-value-path="value"
				initialized="_initComboBox(s)"
				is-editable="true"
                text-changed="_checkValidation(s)">
			</wj-combo-box>
			<%--// 페이지 스케일  --%>
		</div>
		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<input type="text" id="tableDayPeriodSelectStoreStoreNum" ng-model="storeNum">
		</c:if>
		<%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
	    </button>

	</div>

	<%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType1">
        <div class="wj-gridWrap">
			<wj-flex-grid 
				autoGenerateColumns="false" 
				selection-mode="Row" 
				items-source="data" 
				control="flex" 
				initialized="initGrid(s,e)" 
				is-read-only="false" 
				item-formatter="_itemFormatter" 
				allowMerging="Cells">
			<!-- define columns -->
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="tableDayPeriod.storeNm"/>" binding="storeNm" width="160" align="center" is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.tblCd"/>" binding="tblCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.tblNm"/>" binding="tblNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.tblGrpNm"/>" binding="" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.col5"/>" binding="" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.saleCnt"/>" binding="saleCnt" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.guestCnt"/>" binding="guestCnt" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.totDcAmt"/>" binding="totDcAmt" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.totTipAmt"/>" binding="totTipAmt" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="tableDayPeriod.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column> </wj-flex-grid>

			<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp"
				flush="true">
				<jsp:param name="pickerTarget" value="tableDayPeriodCtrl" />
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
		</div>
		<%--//위즈모 테이블--%>
		<div class="pageNum mt20">
			<%-- id --%>
			<ul id="tableDayPeriodCtrlPager" data-size="10">
			</ul>
		</div>
	</div>

	<%-- 엑셀 리스트 --%>
    <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="tableDayPeriodExcelCtrl">
      <div class="wj-gridWrap">
            <wj-flex-grid
                id="tableDayPeriodExcelGrid"
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="excelFlex"
                initialized="initGrid(s,e)"
                loaded-rows="loadedRows(s,e)"
                is-read-only="true"
                frozen-columns="7"
                item-formatter="_itemFormatter">
                <!-- define columns -->
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.saleDate"/>" binding="saleDate" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.storeCd"/>" binding="storeCd" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.storeNm"/>" binding="storeNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.tblCd"/>" binding="tblCd" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.tblNm"/>" binding="tblNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.tblGrpNm"/>" binding="" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.col5"/>" binding="" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.saleCnt"/>" binding="saleCnt" width="*" align="center" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.guestCnt"/>" binding="guestCnt" width="*" align="center" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.totSaleAmt"/>" binding="totSaleAmt" width="*" align="right" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.totDcAmt"/>" binding="totDcAmt" width="*" align="right" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.totTipAmt"/>" binding="totTipAmt" width="*" align="right" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="tableDayPeriod.realSaleAmt"/>" binding="realSaleAmt" width="*" align="right" is-read-only="true" data-type="Number" aggregate="Sum" format="n0"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script type="text/javascript"
	src="/resource/solbipos/js/sale/status/table/dayPeriod/dayPeriod.js?ver=20190122.06"
	charset="utf-8">
</script>

