<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="offAddProdView" class="subCon" style="display: none;">
	<div ng-controller="offAddProdCtrl">
		<div class="searchBar flddUnfld">
			<a href="#" class="open fl"><s:message code="offAdd.prod"/></a>
    		<%-- 조회 --%>
    		<button class="btn_blue fr mt5 mr10" id="nxBtnSearch3" ng-click="_pageView('offAddProdCtrl',1)">
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
						<span class="txtIn"> <input id="startDateProd" name="startDate" class="w110px" /></span>
						<span class="rg">~</span>
						<span class="txtIn"> <input id="endDateProd" name="endDate" class="w110px" /></span>
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
	        <th><s:message code="offAdd.prod.prodCd" /></th>
	        <td><input type="text" id="srchProdCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('3');"/></td>
	        <%-- 상품명 --%>
	        <th><s:message code="offAdd.prod.prodNm" /></th>
	        <td><input type="text" id="srchProdNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('3');"/></td>
	      	</tr>

			<tr>
				<%-- 상품분류 --%>
				<th><s:message code="offAdd.prod.prodClass"/></th>
				<td>
					<input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
						   placeholder="<s:message code="cmm.all" />" readonly/>
					<input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
					<button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
				</td>
				<%-- 오프라인추가구분 --%>
				<th><s:message code="offAdd.prod.orderAddFg" /></th>
				<td>
					<div class="sb-select">
						<wj-combo-box
								id="orderAddFg"
								ng-model="orderAddFg"
								control="orderAddFgCombo"
								items-source="_getComboData('orderAddFg')"
								display-member-path="name"
								selected-value-path="value"
								is-editable="false"
								initialized="_initComboBox(s)">
						</wj-combo-box>
					</div>
				</td>
			</tr>

      		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
	        <tr>
	            <%-- 매장코드 --%>
	          	<th><s:message code="offAdd.prod.store"/></th>
	          	<td>
	            	<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
	             		<jsp:param name="targetId" value="offAddProdStore"/>
	            	</jsp:include>
	              	<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
	          	</td>
				<th></th>
				<td></td>
	        </tr>
      		</c:if>
	      	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
	        	<input type="hidden" id="offAddProdStoreCd" value="${sessionInfo.storeCd}"/>
	      	</c:if>
			</tbody>
		</table>

		<div class="mt10 oh sb-select dkbr">
			<%-- 페이지 스케일  --%>
			<wj-combo-box
					class="w100px fl"
					id="listScaleBox"
					ng-model="listScale"
					items-source="_getComboData('listScaleBox')"
					display-member-path="name"
					selected-value-path="value"
					is-editable="false"
					initialized="initComboBox(s)">
			</wj-combo-box>
			<%--// 페이지 스케일  --%>
		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadPayFg()"><s:message code="cmm.excel.down" />
		    </button>
		</div>

		<%--위즈모 테이블--%>
	    <div class="w100 mt10" id="wjWrapType1">
	      <div class="wj-gridWrap">
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
			  <wj-flex-grid-column header="<s:message code="prodrank.prodClassNm"/>"	binding="pathNm"        width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.prodCd"/>" 		binding="prodCd" 		width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.prodNm"/>"		binding="prodNm" 		width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.orderAddFg"/>"	binding="orderAddFg"	width="100" align="center" is-read-only="true" data-map="orderAddFgDataMap"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.barcdCd"/>"		binding="barcdCd" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.totSaleQty"/>" 	binding="totSaleQty" 	width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.totDcAmt"/>" 	binding="totDcAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.realSaleAmt"/>"	binding="realSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.netSaleAmt"/>" 	binding="totGaAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.vatAmt"/>" 		binding="totVatAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.totTipAmt"/>" 	binding="totTipAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.totEtcAmt"/>" 	binding="totEtcAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="offAdd.prod.totPayAmt"/>" 	binding="totPayAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <%-- 결제수단 컬럼 생성--%>
              <c:forEach var="payCol" items="${payColList}">
                <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="offAddProdCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>

	  <%-- 페이지 리스트 --%>
	  <div class="pageNum mt20">
	    <%-- id --%>
	    <ul id="offAddProdCtrlPager" data-size="10">
	    </ul>
	  </div>
	  <%--//페이지 리스트--%>
	  
	  <%--엑셀 리스트 --%>
	    <div class="w100 mt10" id="wjWrapType1" style="display:none;" ng-controller="offAddProdExcelCtrl">
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
				<wj-flex-grid-column header="<s:message code="prodrank.prodClassNm"/>"	binding="pathNm"        width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.prodCd"/>" 		binding="prodCd" 		width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.prodNm"/>"		binding="prodNm" 		width="150" align="center" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.orderAddFg"/>"	binding="orderAddFg"	width="100" align="center" is-read-only="true" data-map="orderAddFgDataMap"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.barcdCd"/>"		binding="barcdCd" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.totSaleQty"/>" 	binding="totSaleQty" 	width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.totDcAmt"/>" 	binding="totDcAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.realSaleAmt"/>"	binding="realSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.netSaleAmt"/>" 	binding="totGaAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.vatAmt"/>" 		binding="totVatAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.totTipAmt"/>" 	binding="totTipAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.totEtcAmt"/>" 	binding="totEtcAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
				<wj-flex-grid-column header="<s:message code="offAdd.prod.totPayAmt"/>" 	binding="totPayAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/sale/status/offAdd/offAddProd.js?ver=20220314.00" charset="utf-8"></script>

<%-- 결제내역 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/payFg.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>