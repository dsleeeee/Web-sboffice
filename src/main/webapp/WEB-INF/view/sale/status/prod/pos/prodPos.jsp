<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="prodPosView" class="subCon3" style="display: none;" ng-controller="prodPosCtrl"> <%-- 수정 사항 :: class="subCon" >> class="subCon3"--%>
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="prodsale.pos"/></a>
		<%-- 조회 --%>
		<button class="btn_blue fr mt5 mr10" id="btnPosProdSearch" ng-click="_broadcast('prodPosCtrlSrch')">
			<s:message code="cmm.search"/>
		</button>
	</div>
	<table class="searchTbl">
		<colgroup>
			<col class="w13"/>
			<col class="w37"/>
			<col class="w13"/>
        	<col class="w37"/>
       	</colgroup>
       	<tbody>
       		<tr>
       			<th><s:message code="cmm.search.date"/></th>
       			<td>
					<div class="sb-select">
						<span class="txtIn"><input id="srchPosProdStartDate" class="w120px"></span>
						<span class="rg">~</span>
						<span class="txtIn"><input id="srchPosProdEndDate" class="w120px"></span>
						<span class="chk ml10" style="display: none;">
							<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
							<label for="chkDt">
								<s:message code="cmm.all.day" />
							</label>
						</span>
					</div>
				</td>
				<%-- 조회옵션 --%>
				<th><s:message code="periodIostock.srchOption" /></th>
				<td>
		          	<span class="chk ml10">
						<input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
		              	<label for="chkDt">
	                		<s:message code="periodIostock.prodClassDisplay" />
	              		</label>
	            	</span>
				</td>
			</tr>
				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
					<input type="hidden" id="posProdSelectStoreCd" value=""/>
					<tr>
						<%-- 매장코드 --%>
						<th><s:message code="todayBillSaleDtl.store"/></th>
						<td colspan="3">
							<%-- 매장선택 모듈 멀티 선택 사용시 include --%>
<%-- 							<jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectStoreM.jsp" flush="true"> --%>
							<jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreS.jsp" flush="true">
								<jsp:param name="targetId" value="posProdSelectStore"/>
								<jsp:param name="subTargetId" value="posProdSelectPos"/>
								<jsp:param name="closeFunc" value="getPosNmList"/>
							</jsp:include>
							<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
						</td>
					</tr>
				</c:if>
				<c:if test="${sessionInfo.orgnFg == 'STORE'}">
					<input type="hidden" id="posProdSelectStoreCd" value="${sessionInfo.storeCd}"/>
				</c:if>

				<input type="hidden" id="posProdSelectPosCd" value=""/>
				<input type="hidden" id="posProdSelectPosName" value=""/>
				<tr>
					<%-- 포스선택 --%>
					<th><s:message code="pos.pos" /></th>
					<td colspan="3">
						<%-- 포스선택 모듈 멀티 선택 사용시 include --%>
						<jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectPosM.jsp" flush="true">
							<jsp:param name="targetId" value="posProdSelectPos"/>
							<jsp:param name="targetStoreId" value="posProdSelectStore"/>
						</jsp:include>
						<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
					</td>
				</tr>
			</tr>
		</tbody>
	</table>
	<div style="clear: both;"></div>

	<div class="mt20 oh sb-select dkbr"> <%-- 수정 사항 || 클래스변경 :: class="mt40" >> class="mt20" --%>
		<%-- 페이지 스케일  --%>
		<wj-combo-box
			class="w100px fl"
			id="posProdListScaleBox"
			ng-model="posProdListScale"
			items-source="_getComboData('posProdListScaleBox')"
			display-member-path="name"
			selected-value-path="value"
			initialized="initComboBox(s)"
			control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
		</wj-combo-box>
		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<input type="hidden" id="posProdSelectStoreStoreNum" ng-model="storeNum">
		</c:if>
		<%-- 엑셀 다운로드 //TODO --%>
		<button class="btn_skyblue fr" ng-click="excelDownloadPos()">
			<s:message code="cmm.excel.down" />
		</button>
	</div>

	<div id="wjWrapType3" class="w100 mt10"> <%-- 수정 사항 || head line 3 아이디 추가 :: id="wjWrapType3" --%>
		<%--위즈모 테이블--%>
		<div class="wj-gridWrap"> <%-- 수정 사항 || 그리드 높이값 스타일 제거 :: style="height: 000px;" --%>
			<wj-flex-grid
				id="posProdGrid"
				autoGenerateColumns="false"
				selection-mode="Row"
				items-source="data"
				control="flex"
				initialized="initGrid(s,e)"
				loaded-rows="loadedRows(s,e)"
				is-read-only="true"
				frozen-columns="9"
				item-formatter="_itemFormatter">
				<!-- define columns -->
				<wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.prodNm"/>"			binding="prodNm" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.saleStore"/>"			binding="saleStoreCnt" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleAmt"/>"		binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totDcAmt"/>"			binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totRealSaleAmt"/>"	binding="totRealSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleQty"/>"		binding="totSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			</wj-flex-grid>
			<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				<jsp:param name="pickerTarget" value="prodPosCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
		</div>
		<%--//위즈모 테이블--%>
	</div>

	<%-- 페이지 리스트 --%>
	<div class="pageNum mt20">
	<%-- id --%>
		<ul id="prodPosCtrlPager" data-size="10">
		</ul>
	</div>
	
	<%--//엑셀 다운로드--%>
	<div id="wjWrapType3" class="w100 mt10"> <%-- 수정 사항 || head line 3 아이디 추가 :: id="wjWrapType3" --%>
		<%--위즈모 테이블--%>
		<div class="wj-gridWrap" style="display:none;" ng-controller="prodPosExcelCtrl"> <%-- 수정 사항 || 그리드 높이값 스타일 제거 :: style="height: 000px;" --%>
			<wj-flex-grid
				id="posProdExcelGrid"
				autoGenerateColumns="false"
				selection-mode="Row"
				items-source="data"
				control="excelFlex"
				initialized="initGrid(s,e)"
				loaded-rows="loadedRows(s,e)"
				is-read-only="true"
				<%--frozen-columns="9"--%>
				frozen-columns="6"
				item-formatter="_itemFormatter">
				<!-- define columns -->
				<wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.prodNm"/>"			binding="prodNm" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.saleStore"/>"			binding="saleStoreCnt" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleAmt"/>"		binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totDcAmt"/>"			binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totRealSaleAmt"/>"	binding="totRealSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleQty"/>"		binding="totSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			</wj-flex-grid>
			<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				<jsp:param name="pickerTarget" value="prodPosCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
		</div>
		<%--//위즈모 테이블--%>
	</div>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/prod/pos/prodPos.js?ver=20190125.02" charset="utf-8"></script>

