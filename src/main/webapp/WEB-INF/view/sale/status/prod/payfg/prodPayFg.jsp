<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/status/prod/payFg/prodPayFg/"/>

<div id="prodPayFgView" class="subCon" style="display: none;">
	<div ng-controller="prodPayFgCtrl">
		<div class="searchBar flddUnfld">
			<a href="#" class="open fl"><s:message code="prodsale.fayfg"/></a>		
    		<%-- 조회 --%>
    		<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('prodPayFgCtrl')">
    			<s:message code="cmm.search"/>
    		</button>		
		</div> 
    	<%-- 조회조건 --%>
    	<table class="searchTbl">
			<colgroup>
	        	<col class="w13"/>
		        <col class="w37"/>
		        <col class="w13"/>
		        <col class="w37"/>
      		</colgroup>
      		<tbody>
	       	<%-- 조회일자 --%>
			<tr>
		    	<th><s:message code="cmm.search.date" /></th>
	        	<td colspan="3">
	          	<div class="sb-select">
	          		<span class="txtIn"><input id="srchPayFgStartDate" class="w120px"></span>
	                <span class="rg">~</span>
	                <span class="txtIn"><input id="srchPayFgEndDate" class="w120px"></span>
	            	<span class="chk ml10">
						<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
		              	<label for="chkDt">
	                		<s:message code="cmm.all.day" />
	              		</label>
	            	</span>
	          	</div>
	        	</td>
			</tr>
			<tr>
	        <%-- 상품코드 --%>
	        <th><s:message code="prodcalss.prodCd" /></th>
	        <td><input type="text" id="srchPayFgProdCd" class="sb-input w100" maxlength="13" ng-model="prodCd"/></td>
	        <%-- 상품명 --%>
	        <th><s:message code="prodcalss.prodNm" /></th>
	        <td><input type="text" id="srchPayFgProdNm" class="sb-input w100" maxlength="100" ng-model="prodNm"/></td>
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
       	
		<div class="mt40 oh sb-select dkbr">
		    <%-- 페이지 스케일  --%>
		    <wj-combo-box
		      class="w100px fl"
		      id="prodPayFglistScaleBox"
		      ng-model="listScale"
		      control="listScaleCombo"
		      items-source="_getComboData('prodPayFglistScaleBox')"
		      display-member-path="name"
		      selected-value-path="value"
		      is-editable="false"
		      initialized="_initComboBox(s)">
		    </wj-combo-box>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="text" id="pordPayFgSelectStoreStoreNum" ng-model="storeNum">
			</c:if>
		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadPayFg()"><s:message code="cmm.excel.down" />
		    </button>
		</div>
  
		<%--위즈모 테이블--%>
	    <div class="w100 mt10">   
	      <div class="wj-gridWrap" style="height: 350px;">
	        <wj-flex-grid
	          autoGenerateColumns="false"
	          control="flex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          item-formatter="_itemFormatter">
	
	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="prodpayfg.prodClassNm"/>" 	binding="prodClassNm" 	width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.prodCd"/>" 		binding="prodCd" 		width="120" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.prodBar"/>" 		binding="prodBar" 		width="120" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.prodNm"/>"		binding="prodNm" 		width="200" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totSaleQty"/>" 	binding="totSaleQty" 	width="100" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totDcAmt"/>" 		binding="totDcAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>          
	          <wj-flex-grid-column header="<s:message code="prodpayfg.realSaleAmt"/>" 	binding="realSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.netSaleAmt"/>" 	binding="netSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.vatAmt"/>" 		binding="vatAmt" 		width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totTipAmt"/>" 	binding="totTipAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.totEtcAmt"/>" 	binding="totEtcAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
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
<script type="text/javascript" src="/resource/solbipos/js/sale/status/prod/payFg/prodPayFg.js?ver=20190125.02" charset="utf-8"></script>

