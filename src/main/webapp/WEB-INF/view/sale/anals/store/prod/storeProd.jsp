<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/sale/anals/store/prod/"/>

<div id="storeProdView" class="subCon" ng-controller="storeProdCtrl">
	<input type="hidden" id="HqOfficeCd" name="HqOfficeCd" ng-model="HqOfficeCd" value="${gvHqOfficeCd}"/>
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="store.prod"/></a>		
    	<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeProdCtrl')">
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
        	<td>
          	<div class="sb-select">      
       		    <span class="txtIn"><input id="srchProdStartDate" class="w110px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchProdEndDate" class="w110px"></span>
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
      	<tr>  
      		<th><s:message code="store.prodCat" /></th>
			    <td colspan="3">
			        <input type="text" class="sb-input w50" id="srchStoreProdProdNm" ng-model="prodCdNm" ng-click="popUpProd()" style="float: left; width:200px;" placeholder="선택" readonly/>
			        <input type="hidden" id="srchStoreProdProdCd" name="srchStoreProdProdCd" ng-model="prodCd" disabled />
			        <input type="hidden" id="srchStoreProdProdClassCd" name="srchStoreProdProdClassCd" ng-model="prodCalssCdModel" disabled />
			        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdCd" style="margin-left: 5px;" ng-click="delProd()"><s:message code="cmm.selectCancel"/></button>
            	<span class="chk ml10 pst3">
                	<input type="checkbox"  ng-model="isCheckedProdAll" ng-change="totalProd()" />
                	<label for="totalProd()">상품상세보기</label>
            	</span>
        	</td>    	
      	</tr>
      	<c:if test="${sessionInfo.orgnFg == 'HQ'}">
      	<tr>
            <%-- 매장코드 --%>           
          	<th><s:message code="todayBillSaleDtl.store"/></th>
          	<td colspan="3">
            	<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
             		<jsp:param name="targetId" value="storeProdSelectStore"/>
            	</jsp:include>
              	<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          	</td> 	
        </tr>
        </c:if>
      	<c:if test="${sessionInfo.orgnFg == 'STORE'}">  
        	<input type="hidden" id="storeProdSelectStoreCd" value="${sessionInfo.storeCd}"/>
      	</c:if>
		</tbody>
	</table>

	<div class="mt10 oh sb-select dkbr">
		<%-- 페이지 스케일  --%>
	    <wj-combo-box
	      class="w100px fl"
	      id="storeProdlistScaleBox"
	      ng-model="listScale"
	      items-source="_getComboData('storeProdlistScaleBox')"
	      display-member-path="name"
	      selected-value-path="value"
	      initialized="_initComboBox(s)"
	      control="conListScale"
		  is-editable="true"
		  text-changed="_checkValidation(s)">
	    </wj-combo-box>
	    <%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue fr" ng-click="excelDownloadStoreProd()"><s:message code="cmm.excel.down" />
	    </button>
	</div>
  
	<%--위즈모 테이블--%>
    <div class="w100 mt10">   
      <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          id="storeProdGrid"
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="store.indexNo"/>" 			binding="indexNo" 		width="65" align="center"  is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.storeNm"/>" 			binding="storeNm" 		width="200" align="center"  is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center"  is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center"  is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center"  is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.prodCd"/>"			binding="prodCd" 		width="120" align="center"  is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.prodNm"/>" 			binding="prodNm" 		width="120" align="center"  is-read-only="true" ></wj-flex-grid-column>
 		  <wj-flex-grid-column header="<s:message code="store.totSaleQty"/>" 		binding="totSaleQty" 	width="100" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
  		  <wj-flex-grid-column header="<s:message code="store.totSaleAmt"/>" 		binding="totSaleAmt" 	width="120" align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
   		  <wj-flex-grid-column header="<s:message code="store.totDcAmt"/>" 			binding="totDcAmt" 		width="100" align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
   		  <wj-flex-grid-column header="<s:message code="store.realSaleAmt"/>" 		binding="realSaleAmt" 	width="120" align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="storeProdCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>  
    
    <%-- 페이지 리스트 --%>
	  <div class="pageNum mt20">
	    <%-- id --%>
	    <ul id="storeProdCtrlPager" data-size="10">
	    </ul>
	  </div>
	<%--//페이지 리스트--%>
	
	<%-- 엑셀 리스트 --%>
	<div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="storeProdExcelCtrl">
      <div class="wj-gridWrap">
	   <wj-flex-grid
          id="storeProdExcelGrid"
          autoGenerateColumns="false"
          control="excelFlex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
		   <wj-flex-grid-column header="<s:message code="store.indexNo"/>" 			binding="indexNo" 		width="65" align="center"  is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.storeNm"/>" 			binding="storeNm" 		width="200" align="center"  is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center"  is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center"  is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center"  is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.prodCd"/>"			binding="prodCd" 		width="120" align="center"  is-read-only="true" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.prodNm"/>" 			binding="prodNm" 		width="120" align="center"  is-read-only="true" ></wj-flex-grid-column>
 		  <wj-flex-grid-column header="<s:message code="store.totSaleQty"/>" 		binding="totSaleQty" 	width="100" align="center"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
  		  <wj-flex-grid-column header="<s:message code="store.totSaleAmt"/>" 		binding="totSaleAmt" 	width="120" align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
   		  <wj-flex-grid-column header="<s:message code="store.totDcAmt"/>" 			binding="totDcAmt" 		width="100" align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
   		  <wj-flex-grid-column header="<s:message code="store.realSaleAmt"/>" 		binding="realSaleAmt" 	width="120" align="right"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
	   </div>
	</div>
	<%--//엑셀 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/store/prod/storeProd.js?ver=20190125.02" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품정보 --%>
<c:import url="/WEB-INF/view/sale/com/popup/selectProdS.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

