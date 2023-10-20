<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/sale/status/prod/payFg/prodPayFg/"/>

<div id="prodPayFgView" class="subCon" style="display: none;padding: 10px 20px 40px;">
	<div ng-controller="prodPayFgCtrl">
		<div class="searchBar flddUnfld">
			<a href="#" class="open fl"><s:message code="prodsale.fayfg"/></a>
    		<%-- 조회 --%>
    		<button class="btn_blue fr mt5 mr10" id="nxBtnSearch3" ng-click="_pageView('prodPayFgCtrl',1)">
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
						<span class="txtIn"> <input id="startDatePayFg" name="startDate" class="w110px" /></span>
						<span class="rg">~</span>
						<span class="txtIn"> <input id="endDatePayFg" name="endDate" class="w110px" /></span>
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
	        <td><input type="text" id="srchPayFgProdCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('3');"/></td>
	        <%-- 상품명 --%>
	        <th><s:message code="prodcalss.prodNm" /></th>
	        <td><input type="text" id="srchPayFgProdNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('3');"/></td>
	      	</tr>
      		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
	        <tr>
	            <%-- 매장코드 --%>
	          	<th><s:message code="todayBillSaleDtl.store"/></th>
	          	<td colspan="3">
	            	<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
	             		<jsp:param name="targetId" value="pordPayFgSelectStore"/>
	            	</jsp:include>
	              	<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
	          	</td>
	        </tr>
      		</c:if>
	      	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
	        	<input type="hidden" id="pordPayFgSelectStoreCd" value="${sessionInfo.storeCd}"/>
	      	</c:if>
			</tbody>
		</table>

		<div class="mt10 oh sb-select dkbr">
		    <%-- 페이지 스케일  --%>
		    <wj-combo-box
		      class="w100px fl"
		      id="prodPayFglistScaleBox"
		      ng-model="prodPayFglistScale"
		      control="listScaleCombo"
		      items-source="_getComboData('prodPayFglistScaleBox')"
		      display-member-path="name"
		      selected-value-path="value"
		      initialized="_initComboBox(s)"
		      is-editable="true"
              text-changed="_checkValidation(s)">
		    </wj-combo-box>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="text" id="pordPayFgSelectStoreStoreNum" ng-model="storeNum">
			</c:if>
		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadPayFg()"><s:message code="cmm.excel.down" />
		    </button>
		</div>

		<%--위즈모 테이블--%>
	    <div class="w100 mt10" id="wjWrapType1">
	      <div class="wj-gridWrap" style="height: 350px;">
	        <wj-flex-grid
	          autoGenerateColumns="false"
	          control="flex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          frozen-columns="3"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
			  <wj-flex-grid-column header="<s:message code="prodrank.prodClassNm"/>"   binding="pathNm"        width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.prodCd"/>" 		binding="prodCd" 		width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.prodNm"/>"		binding="prodNm" 		width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.barcdCd"/>" 		binding="barcdCd" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totSaleQty"/>" 	binding="totSaleQty" 	width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totDcAmt"/>" 	binding="totDcAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.realSaleAmt"/>" 	binding="realSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.netSaleAmt"/>" 	binding="totGaAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.vatAmt"/>" 		binding="totVatAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totTipAmt"/>" 	binding="totTipAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totEtcAmt"/>" 	binding="totEtcAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totPayAmt"/>" 	binding="totPayAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <%-- 결제수단 컬럼 생성--%>
              <c:forEach var="payCol" items="${payColList}">
                <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="prodPayFgCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>

	  <%-- 페이지 리스트 --%>
	  <div class="pageNum mt20">
	    <%-- id --%>
	    <ul id="prodPayFgCtrlPager" data-size="10">
	    </ul>
	  </div>
	  <%--//페이지 리스트--%>
	  
	  <%--엑셀 리스트 --%>
	    <div class="w100 mt10" id="wjWrapType1" style="display:none;" ng-controller="prodPayFgExcelCtrl">
	      <div class="wj-gridWrap">
	        <wj-flex-grid
	          autoGenerateColumns="false"
	          control="excelFlexTrd"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          frozen-columns="3"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
			 <wj-flex-grid-column header="<s:message code="prodrank.prodClassNm"/>"   binding="pathNm"        width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
			 <wj-flex-grid-column header="<s:message code="prodpayfg.prodCd"/>" 		binding="prodCd" 		width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
		 	 <wj-flex-grid-column header="<s:message code="prodpayfg.prodNm"/>"		binding="prodNm" 		width="150" align="center" is-read-only="true"></wj-flex-grid-column>
			 <wj-flex-grid-column header="<s:message code="prodpayfg.barcdCd"/>" 		binding="barcdCd" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
		 	 <wj-flex-grid-column header="<s:message code="prodpayfg.totSaleQty"/>" 	binding="totSaleQty" 	width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			 <wj-flex-grid-column header="<s:message code="prodpayfg.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		 	 <wj-flex-grid-column header="<s:message code="prodpayfg.totDcAmt"/>" 	binding="totDcAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			 <wj-flex-grid-column header="<s:message code="prodpayfg.realSaleAmt"/>" 	binding="realSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			 <wj-flex-grid-column header="<s:message code="prodpayfg.netSaleAmt"/>" 	binding="totGaAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		 	 <wj-flex-grid-column header="<s:message code="prodpayfg.vatAmt"/>" 		binding="totVatAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			 <wj-flex-grid-column header="<s:message code="prodpayfg.totTipAmt"/>" 	binding="totTipAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			 <wj-flex-grid-column header="<s:message code="prodpayfg.totEtcAmt"/>" 	binding="totEtcAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			 <wj-flex-grid-column header="<s:message code="prodpayfg.totPayAmt"/>" 	binding="totPayAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <%-- 결제수단 컬럼 생성--%>
              <c:forEach var="payCol" items="${payColList}">
                <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="prodPayFgCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//엑셀 리스트--%>
	</div>
</div>

<script type="text/javascript">
	var payColList = [];
	<%--javascript에서 사용할 결제수단 json 데이터 생성--%>
	<c:forEach var="payCol" items="${payColList}">
	var payParam       = {};
	payParam.payCd     = "${payCol.payCd}";
	payParam.payMethod = "${payCol.payMethod}";
	payColList.push(payParam);
	</c:forEach>

	var payCol      = '${payCol}';
	var arrPayCol   = payCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/prod/payFg/prodPayFg.js?ver=20200106.04" charset="utf-8"></script>

<%-- 결제내역 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/payFg.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>