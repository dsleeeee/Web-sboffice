<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="cupRefundView" class="subCon" style="display: none;">
	<div ng-controller="cupRefundCtrl">
		<div class="searchBar flddUnfld">
			<a href="#" class="open fl"><s:message code="nonSale.cupRefund"/></a>
	    	<%-- 조회 --%>
	    	<button class="btn_blue fr mt5 mr10" id="nxBtnSearch5" ng-click="_pageView('cupRefundCtrl',1)">
	    		<s:message code="cmm.search"/>
	    	</button>
		</div>
		<%-- 조회조건 --%>
		<table class="searchTbl">
			<colgroup>
				<col class="w15"/>
				<col class="w35"/>
				<col class="w15"/>
				<col class="w35"/>
			</colgroup>
			<tbody>
				<%-- 조회일자 --%>
				<tr>
					<th><s:message code="cmm.search.date" /></th>
					<td>
						<div class="sb-select">
							<span class="txtIn"><input id="startDateCupRefund" class="w110px"></span>
							<span class="rg">~</span>
							<span class="txtIn"><input id="endDateCupRefund" class="w110px"></span>
						</div>
					</td>
				</tr>
				<tr>
					<%-- 매장코드 --%>
					<c:if test="${sessionInfo.orgnFg == 'HQ'}">
						<th><s:message code="todayBillSaleDtl.store"/></th>
						<td>
							<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
								<jsp:param name="targetId" value="cupRefundSelectStore"/>
							</jsp:include>
								<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
						</td>
					</c:if>
					<c:if test="${sessionInfo.orgnFg == 'STORE'}">
						<input type="hidden" id="cupRefundSelectStoreCd" value="${sessionInfo.storeCd}"/>
					</c:if>
				</tr>
			</tbody>
		</table>
		<div class="mt10 oh sb-select dkbr">
		    <%-- 페이지 스케일  --%>
		    <wj-combo-box
		      class="w100px fl"
		      id="cupRefundlistScaleBox"
		      ng-model="cupRefundlistScale"
		      control="listScaleCombo"
		      items-source="_getComboData('cupRefundlistScaleBox')"
		      display-member-path="name"
		      selected-value-path="value"
		      initialized="_initComboBox(s)"
		      is-editable="true"
              text-changed="_checkValidation(s)">
		    </wj-combo-box>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="text" id="pordDaySelectStoreStoreNum" ng-model="storeNum">
			</c:if>
		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" />
		    </button>
		</div>

		<%--위즈모 테이블--%>
	    <div id="wjWrapType1" class="w100 mt10">
	      <div class="wj-gridWrap">
	        <wj-flex-grid
	          autoGenerateColumns="false"
	          control="flex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="nonSaleDay.storeCd"/>" 			binding="storeCd" 		width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
          	  <wj-flex-grid-column header="<s:message code="nonSaleDay.storeNm"/>" 			binding="storeNm" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="nonSaleDay.saleDate"/>"			binding="saleDate" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="nonSaleDay.posNo"/>"			binding="posNo" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="nonSaleDay.refundNo"/>"			binding="refundNo" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="nonSaleDay.barcodeId"/>" 		binding="barcodeId" 	width="80" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="nonSaleDay.depositAmount"/>" 	binding="depositAmount"	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="nonSaleDay.containerType"/>" 	binding="containerType"	width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="nonSaleDay.standardYn"/>" 		binding="standardYn" 	width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="nonSaleDay.refundedAmount"/>" 	binding="refundedAmount" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="cupRefundCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>

	  <%-- 페이지 리스트 --%>
	  <div class="pageNum mt20">
	    <%-- id --%>
	    <ul id="cupRefundCtrlPager" data-size="10">
	    </ul>
	  </div>
	  <%--//페이지 리스트--%>

	  <%--엑셀 다운로드--%>
	    <div id="wjWrapType1" class="w100 mt10" style="display:none;" ng-controller="cupRefundExcelCtrl">
	      <div class="wj-gridWrap">
	        <wj-flex-grid
	          autoGenerateColumns="false"
	          control="excelFlex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
				<wj-flex-grid-column header="<s:message code="nonSaleDay.storeCd"/>" 		binding="storeCd" 		width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="nonSaleDay.storeNm"/>" 		binding="storeNm" 		width="300" align="center" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="nonSaleDay.saleDate"/>"		binding="saleDate" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="nonSaleDay.posNo"/>"			binding="posNo" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="nonSaleDay.refundNo"/>"		binding="refundNo" 		width="150" align="center" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="nonSaleDay.barcodeId"/>" 		binding="barcodeId" 	width="80" align="center" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="nonSaleDay.depositAmount"/>" 	binding="depositAmount"	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="nonSaleDay.containerType"/>" 	binding="containerType"	width="100" align="right" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="nonSaleDay.standardYn"/>"		binding="standardYn" 	width="100" align="right" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="nonSaleDay.refundedAmount"/>"	binding="refundedAmount" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="cupRefundCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>
	  
	</div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/nonSale/cupRefund.js?ver=20220608.01" charset="utf-8"></script>