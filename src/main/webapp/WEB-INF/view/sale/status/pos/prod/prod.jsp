<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="posProdView" class="subCon" style="display: none;" ng-controller="posProdCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="pos.prod"/></a>
		<%-- 조회 --%>
		<button class="btn_blue fr mt5 mr10" id="btnPosProdSearch" ng-click="_pageView('posProdCtrl',1)">
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
				<%--조회일자--%>
       			<th><s:message code="cmm.search.date"/></th>
       			<td>
					<div class="sb-select">
						<span class="txtIn"><input id="startDateProd" class="w110px"></span>
						<span class="rg">~</span>
						<span class="txtIn"><input id="endDateProd" class="w110px"></span>
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
            <tr>
				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 매장코드 --%>
                    <th><s:message code="todayBillSaleDtl.store"/></th>
                    <td>
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
<%-- 							<jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectStoreM.jsp" flush="true"> --%>
                        <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreS.jsp" flush="true">
                            <jsp:param name="targetId" value="posProdSelectStore"/>
                            <jsp:param name="subTargetId" value="posProdSelectPos"/>
                            <jsp:param name="closeFunc" value="getPosNmList"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                        <input type="hidden" id="posProdSelectStoreCd" value=""/>
                    </td>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <input type="hidden" id="posProdSelectStoreCd" value="${sessionInfo.storeCd}"/>
                </c:if>
                <input type="hidden" id="posProdSelectPosCd" value=""/>
                <input type="hidden" id="posProdSelectPosName" value=""/>
                <input type="hidden" id="posProdSelectHqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
                <%-- 포스선택 --%>
                <th><s:message code="pos.pos" /></th>
                <td>
                    <%-- 포스선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectPosM.jsp" flush="true">
                        <jsp:param name="targetId" value="posProdSelectPos"/>
                        <jsp:param name="targetStoreId" value="posProdSelectStore"/>
                        <jsp:param name="closeFunc" value="getPosNmList"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
		</tbody>
	</table>

	<div class="mt10 oh sb-select dkbr">
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

		<%-- 엑셀 다운로드 //TODO --%>
		<button class="btn_skyblue fr" ng-click="excelDownloadDay()">
			<s:message code="cmm.excel.down" />
		</button>
	</div>

	<%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType3">
      <div class="wj-gridWrap">
			<wj-flex-grid
				id="posProdGrid"
				autoGenerateColumns="false"
				selection-mode="Row"
				items-source="data"
				control="flex"
				initialized="initGrid(s,e)"
				loaded-rows="loadedRows(s,e)"
				is-read-only="true"
				frozen-columns="8"
				item-formatter="_itemFormatter">
				<!-- define columns -->
				<wj-flex-grid-column header="<s:message code="pos.prodClassNm"/>" 		binding="pathNm" 	width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="pos.prodCd"/>"			binding="prodCd" width="100" align="center" visible="false" is-read-only="true" format="d"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.prodNm"/>"			binding="prodNm" width="150" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.saleStore"/>"		binding="saleStoreCnt" width="60" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleAmt"/>"		binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totDcAmt"/>"			binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totRealSaleAmt"/>"	binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleQty"/>"		binding="totSaleCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			</wj-flex-grid>
			<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				<jsp:param name="pickerTarget" value="posProdCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
		</div>
		<%--//위즈모 테이블--%>
	</div>

	<%-- 페이지 리스트 --%>
	<div class="pageNum mt20">
	<%-- id --%>
		<ul id="posProdCtrlPager" data-size="10">
		</ul>
	</div>
	<%--//페이지 리스트--%>

	<%-- 엑셀 리스트 --%>
	<div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="posProdExcelCtrl">
      <div class="wj-gridWrap">
			<wj-flex-grid
				id="posProdExcelGrid"
				autoGenerateColumns="false"
				selection-mode="Row"
				items-source="data"
				control="excelFlex"
				initialized="initGrid(s,e)"
				loaded-rows="loadedRows(s,e)"
				is-read-only="true"
				frozen-columns="8"
				item-formatter="_itemFormatter">
				<!-- define columns -->
				<wj-flex-grid-column header="<s:message code="pos.prodClassNm"/>" 		binding="pathNm" 	width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="pos.prodCd"/>"			binding="prodCd" width="100" align="center" visible="false" is-read-only="true" format="d"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.prodNm"/>"			binding="prodNm" width="150" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.saleStore"/>"		binding="saleStoreCnt" width="60" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleAmt"/>"		binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totDcAmt"/>"			binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totRealSaleAmt"/>"	binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="pos.totSaleQty"/>"		binding="totSaleCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			</wj-flex-grid>
		</div>
	</div>
	<%--//엑셀 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/pos/prod/prod.js?ver=20200121.02" charset="utf-8"></script>