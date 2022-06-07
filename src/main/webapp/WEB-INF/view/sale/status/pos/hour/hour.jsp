<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="posHourView" class="subCon" style="display: none;" ng-controller="posHourCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="pos.hour"/></a>
		<%-- 조회 --%>
		<button class="btn_blue fr mt5 mr10" id="btnPosDaySearch" ng-click="_broadcast('posHourCtrlSrch')">
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
						<span class="txtIn"><input id="srchPosHourStartDate" class="w110px"></span>
						<span class="rg">~</span>
						<span class="txtIn"><input id="srchPosHourEndDate" class="w110px"></span>
						<span class="chk ml10" style="display: none;">
							<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
							<label for="chkDt">
								<s:message code="cmm.all.day" />
							</label>
						</span>
					</div>
				</td>
				<%-- 옵션 --%>
				<th><s:message code="day.time.optionFg"/></th>
				<td>
					<span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
					<span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
				</td>
			</tr>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="hidden" id="posHourSelectStoreCd" value=""/>
				<tr>
					<%-- 매장코드 --%>
					<th><s:message code="todayBillSaleDtl.store"/></th>
					<td>
						<%-- 매장선택 모듈 멀티 선택 사용시 include --%>
<%-- 							<jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectStoreM.jsp" flush="true"> --%>
						<jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreS.jsp" flush="true">
							<jsp:param name="targetId" value="posHourSelectStore"/>
							<jsp:param name="subTargetId" value="posHourSelectPos"/>
							<jsp:param name="closeFunc" value="getPosNmList"/>
						</jsp:include>
						<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
					</td>
				</tr>
			</c:if>
			<c:if test="${sessionInfo.orgnFg == 'STORE'}">
				<input type="hidden" id="posHourSelectStoreCd" value="${sessionInfo.storeCd}"/>
			</c:if>

				<input type="hidden" id="posHourSelectPosCd" value=""/>
				<input type="hidden" id="posHourSelectPosName" value=""/>
				<input type="hidden" id="posHourSelectHqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
				<tr>
					<%-- 포스선택 --%>
					<th><s:message code="pos.pos" /></th>
					<td>
						<%-- 포스선택 모듈 멀티 선택 사용시 include --%>
						<jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectPosM.jsp" flush="true">
							<jsp:param name="targetId" value="posHourSelectPos"/>
							<jsp:param name="targetStoreId" value="posHourSelectStore"/>
							<jsp:param name="closeFunc" value="getPosNmList"/>
						</jsp:include>
						<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
					</td>
				</tr>
			<tr id="timeOption">
				<th><s:message code="month.srchTime"/></th>
				<td colspan="3">
					<div class="sb-select fl w200px">
						<div class="sb-slect fl" style="width:65px;">
							<wj-combo-box
									id="startTime"
									ng-model="startTime"
									items-source="_getComboData('startTimeCombo')"
									display-member-path="name"
									selected-value-path="value"
									is-editable="false"
									control="startTimeCombo"
									initialized="_initComboBox(s)">
							</wj-combo-box>
						</div>
						<div class="fl pd5" style="padding-right: 15px;">
							<label> ~ </label>
						</div>
						<div class="sb-select fl" style="width:65px;">
							<wj-combo-box
									id="endTime"
									ng-model="endTime"
									items-source="_getComboData('endTimeCombo')"
									display-member-path="name"
									selected-value-path="value"
									is-editable="false"
									control="endTimeCombo"
									initialized="_initComboBox(s)">
							</wj-combo-box>
						</div>
					</div>
				</td>
			</tr>
			<tr id="timeSlotOption" style="display: none">
				<th><s:message code="day.time.time"/></th>
				<td colspan="3">
					<div class="sb-select fl w120px" >
						<wj-combo-box
								id="timeSlotCombo"
								ng-model="timeSlot"
								control="timeSlotCombo"
								items-source="_getComboData('timeSlotCombo')"
								display-member-path="name"
								selected-value-path="value"
								is-editable="false"
								initialized="_initComboBox(s)">
						</wj-combo-box>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div style="clear: both;"></div>

	<div class="mt10 oh sb-select dkbr">
		<%-- 페이지 스케일  --%>
		<!-- <wj-combo-box
			class="w100px fl"
			id="posHourListScaleBox"
			ng-model="posHourListScale"
			items-source="_getComboData('posHourListScaleBox')"
			display-member-path="name"
			selected-value-path="value"
			is-editable="false"
			initialized="initComboBox(s)">
		</wj-combo-box> -->

		<%-- 엑셀 다운로드 //TODO --%>
		<button class="btn_skyblue fr" ng-click="excelDownloadHour()">
			<s:message code="cmm.excel.down" />
		</button>
	</div>

	<%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType3">
      <div class="wj-gridWrap">
			<wj-flex-grid
				id="posHourGrid"
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
				<wj-flex-grid-column header="<s:message code="pos.saleHour"/>"			binding="saleHour" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.saleStore"/>"			binding="saleStoreCnt" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleAmt"/>"		binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totDcAmt"/>"			binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totRealSaleAmt"/>"	binding="totRealSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleQty"/>"		binding="totSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			</wj-flex-grid>
			<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				<jsp:param name="pickerTarget" value="posHourCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
		</div>
		<%--//위즈모 테이블--%>
	</div>

	<%-- 페이지 리스트 --%>
	<%-- <div class="pageNum mt20">
	id
		<ul id="posHourCtrlPager" data-size="10">
		</ul>
	</div> --%>
	<%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/pos/hour/hour.js?ver=20190125.03" charset="utf-8"></script>

<%-- 수량합계 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/prodHour.jsp">
	<c:param name="menuCd" value="${menuCd}"/>
	<c:param name="menuNm" value="${menuNm}"/>
</c:import>