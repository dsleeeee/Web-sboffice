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

<div id="storeRankView" class="subCon" ng-controller="storeRankCtrl">
	<input type="hidden" id="HqOfficeCd" name="HqOfficeCd" ng-model="HqOfficeCd" value="${gvHqOfficeCd}"/>
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="store.rank"/></a>		
    	<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeRankCtrl')">
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
        	<td colspan="5">
          	<div class="sb-select">      
       		    <span class="txtIn"><input id="srchRankStartDate" class="w120px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchRankEndDate" class="w120px"></span>
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
      		<th><s:message code="store.sort" /></th>
        	<td>

            	<span class="rdo ml10">
                	<label><input type="radio"  ng-model="isCheckedSort"  value="1" checked/>상위</label>
                	<label><input type="radio"  ng-model="isCheckedSort"  value="2" />하위</label>
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
         	<td colspan="2">
        		<%-- 결제수단 전체보기 --%>
            	<span class="chk ml10">
                	<input type="checkbox"  ng-model="isCheckedPayAll" ng-change="totalPay()" />
                	<label for="totalPay()">전체결제수단 표시</label>
            	</span>
        	</td>    	
      	</tr>
		</tbody>
	</table>

	<div class="mt40 oh sb-select dkbr">	
	    <%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue fr" ng-click="excelDownloadStoreRank()"><s:message code="cmm.excel.down" />
	    </button>
	</div>
  
	<%--위즈모 테이블--%>
    <div class="w100 mt10">   
      <div class="wj-gridWrap" style="height: 350px;">
        <wj-flex-grid
          id="storeRankGrid"
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="store.storeNm"/>" 		binding="storeNm" 		width="200" align="center" is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.totSaleAmt"/>" 	binding="totSaleAmt" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.totDcAmt"/>"		binding="totDcAmt" 		width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.realSaleAmt"/>" 	binding="realSaleAmt" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
 		  <wj-flex-grid-column header="<s:message code="store.openDay"/>" 		binding="saleDateCnt" 		width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
  		  <wj-flex-grid-column header="<s:message code="store.openDayAmt"/>" 	binding="realSaleAmtAvg" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
   		  <wj-flex-grid-column header="<s:message code="store.billCnt"/>" 		binding="billCnt" 		width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column> 
   		  <wj-flex-grid-column header="<s:message code="store.totBillAmt"/>" 	binding="totBillAmt" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
   		  <wj-flex-grid-column header="<s:message code="store.totGuestCnt"/>" 	binding="totGuestCnt" 	width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
   		  <wj-flex-grid-column header="<s:message code="store.storeRat"/>" 		binding="storeRat" 		width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>  
        </wj-flex-grid>
        
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="storeRankCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>  
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/store/rank/storeRank.js?ver=20190125.02" charset="utf-8"></script>
