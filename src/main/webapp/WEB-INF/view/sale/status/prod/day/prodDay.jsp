<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/sale/status/prod/day/prodDay/"/>

<div id="prodDayView" class="subCon" style="display: none;">
	<div ng-controller="prodDayCtrl">
		<div class="searchBar flddUnfld">
			<a href="#" class="open fl"><s:message code="prodsale.day"/></a>
	    	<%-- 조회 --%>
	    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('prodDayCtrl',1)">
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
		          	    <span class="txtIn"><input id="startDateProdDay" class="w110px"></span>
	                    <span class="rg">~</span>
	                    <span class="txtIn"><input id="endDateProdDay" class="w110px"></span>
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
                <%-- 상품코드 --%>
                <th><s:message code="prodcalss.prodCd" /></th>
                <td><input type="text" id="srchDayProdCd" class="sb-input w100" maxlength="13"/></td>
                <%-- 상품명 --%>
                <th><s:message code="prodcalss.prodNm" /></th>
                <td><input type="text" id="srchDayProdNm" class="sb-input w100" maxlength="100"/></td>
	      	</tr>
	        <tr>
	        	<%-- 분류조회 --%>
		        <th><s:message code="prod.prodClass" /></th>
		        <td>
		          <input type="text" class="sb-input w70" id="srchProdDayCd" ng-model="prodDayCdNm" ng-click="popUpProdDay()" style="float: left;"
		                 placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
		          <input type="hidden" id="_prodDayCd" name="prodDayCd" ng-model="prodDayCdModel" disabled />
		          <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdDayCd" style="margin-left: 5px;" ng-click="delProdDay()"><s:message code="cmm.selectCancel"/></button>
		        </td>
	            <%-- 매장코드 --%>
	            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
					<th><s:message code="todayBillSaleDtl.store"/></th>
					<td>
						<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
							<jsp:param name="targetId" value="pordDaySelectStore"/>
						</jsp:include>
						<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
					</td>
	          	</c:if>
				<c:if test="${sessionInfo.orgnFg != 'HQ'}">
					<td></td>
					<td></td>
				</c:if>
	        </tr>
	      	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
	        	<input type="hidden" id="pordDaySelectStoreCd" value="${sessionInfo.storeCd}"/>
	      	</c:if>
			</tbody>
		</table>

		<div class="mt10 oh sb-select dkbr">
		    <%-- 페이지 스케일  --%>
		    <wj-combo-box
		      class="w100px fl"
		      id="prodDaylistScaleBox"
		      ng-model="prodDaylistScale"
		      control="listScaleCombo"
		      items-source="_getComboData('prodDaylistScaleBox')"
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
	          <wj-flex-grid-column header="<s:message code="prodday.saleDate"/>" 	binding="saleDate" 		width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
          	  <wj-flex-grid-column header="<s:message code="prodrank.prodClassNm"/>" 	binding="pathNm" 		width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.prodCd"/>" 		binding="prodCd" 		width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodhour.prodNm"/>" 	binding="prodNm" 		width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.totSaleQty"/>" 	binding="totSaleQty" 	width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.totDcAmt"/>" 	binding="totDcAmt" 		width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.realSaleAmt"/>" binding="realSaleAmt" 	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="prodDayCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>

	  <%-- 페이지 리스트 --%>
	  <div class="pageNum mt20">
	    <%-- id --%>
	    <ul id="prodDayCtrlPager" data-size="10">
	    </ul>
	  </div>
	  <%--//페이지 리스트--%>

	  <%--엑셀 다운로드--%>
	    <div id="wjWrapType1" class="w100 mt10" style="display:none;" ng-controller="prodDayExcelCtrl">
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
	          <wj-flex-grid-column header="<s:message code="prodday.saleDate"/>" 	binding="saleDate" 		width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
          	  <wj-flex-grid-column header="<s:message code="prodrank.prodClassNm"/>" 	binding="pathNm" 		width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.prodCd"/>" 		binding="prodCd" 		width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodhour.prodNm"/>" 	binding="prodNm" 		width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.totSaleQty"/>" 	binding="totSaleQty" 	width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.totDcAmt"/>" 	binding="totDcAmt" 		width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodday.realSaleAmt"/>" binding="realSaleAmt" 	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="prodDayCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>
	  
	</div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/prod/day/prodDay.js?ver=20200111.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<%-- <c:import url="/WEB-INF/view/base/prod/prod/prodModifyView.jsp"> --%>
<%--   <c:param name="menuCd" value="${menuCd}"/> --%>
<%--   <c:param name="menuNm" value="${menuNm}"/> --%>
<%-- </c:import> --%>