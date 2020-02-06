<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/sale/anals/store/rank/"/>

<div id="storeMonthView" class="subCon" ng-controller="storeMonthCtrl">
	<input type="hidden" id="HqOfficeCd" name="HqOfficeCd" ng-model="HqOfficeCd" value="${gvHqOfficeCd}"/>
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="store.month"/></a>		
    	<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeMonthCtrl')">
    		<s:message code="cmm.search"/>
    	</button>		
	</div> 
    <%-- 조회조건 --%>
    <table class="searchTbl">
		<colgroup>
        	<col class="w14"/>
	        <col class="w13"/>
	        <col class="w73"/>
      	</colgroup>
      	<tbody>
       	<%-- 조회일자 --%>
		<tr>
	    	<th><s:message code="cmm.search.date" /></th>
        	<td colsapn="2">
          	<div class="sb-select">      
	          <span class="txtIn w110px">
	              <wj-input-date
	                      id="srchMonthStartDate"
	                      value="storeMonthStartDate"
	                      ng-model="startDate"
	                      control="storeMonthStartDateCombo"
	                      min="2000-01-01"
	                      max="2099-12-31"
	                      initialized="_initDateBox(s)"
	                      selection-mode="Month" 
	                      format="y">
	              </wj-input-date>
	            </span>
	            <span class="rg">~</span>
	            <span class="txtIn w110px">
	              <wj-input-date
	                      id="srchMonthEndDate"
	                      value="storeMonthEndDate"
	                      ng-model="endDate"
	                      control="storeMonthEndDateCombo"
	                      min="2000-01-01"
	                      max="2099-12-31"
	                      initialized="_initDateBox(s)"
	                      selection-mode="Month" 
	                      format="y">
	              </wj-input-date>
	            </span>        
          	</div>
        	</td>
        </tr>
      	<tr>  
      		<th><s:message code="store.sort" /></th>
        	<td>
            	<span class="rdo ml10">
                	<label><input type="radio"  ng-model="isCheckedSortMonth"  value="1" checked/>상위</label>
                	<label><input type="radio"  ng-model="isCheckedSortMonth"  value="2" />하위</label>
            	</span>   
            </td>
            <td>  	
                <div class="sb-select fl w150px">
                    <wj-combo-box
                            id="rowNum"
                            ng-model="rowNum"
                            items-source="_getComboData('srchRowNumCombo')"
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

	<div class="mt40 oh sb-select dkbr">	
	    <%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue fr" ng-click="excelDownloadStoreMonth()"><s:message code="cmm.excel.down" />
	    </button>
	</div>
  
	<%--위즈모 테이블--%>
    <div class="w100 mt10">   
      <div class="wj-gridWrap" style="height: 350px;">
        <wj-flex-grid
          id="storeMonthGrid"
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="store.storeNm"/>" 		binding="storeNm" 		width="200" align="center" is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.realSaleAmt"/>" 	binding="realSaleAmt" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
   		  <wj-flex-grid-column header="<s:message code="store.billCnt"/>" 		binding="billCnt" 		width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
   		  <wj-flex-grid-column header="<s:message code="store.totBillAmt"/>" 	binding="totBillAmt" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
   		  <wj-flex-grid-column header="<s:message code="store.storeRat"/>" 		binding="storeRat" 		width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>  
        </wj-flex-grid>
        
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="storeMonthCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>  
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/store/month/storeMonth.js?ver=20190125.02" charset="utf-8"></script>
