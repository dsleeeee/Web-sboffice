<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/com/popup/"/>

<wj-popup id="prodSLayer" control="prodSLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
<input type="hidden" id="storeCd" name="storeCd" />
<input type="hidden" id="prodClassCd" name="prodClassCd" />
<input type="hidden" id="gubun" name="gubun" />
  <div class="wj-dialog wj-dialog-columns"  ng-controller="prodStrl">
	<div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="saleComPopup.prod.prod"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">
     
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="saleComPopup.prod.prod"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnEmpDayPeriodSearch" ng-click="_broadcast('prodDtlStrl')">
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
        <%-- 상품코드 --%>
        <th><s:message code="prodcalss.prodCd" /></th>
        <td><input type="text" id="srchPopProdCd" name="srchPopProdCd" class="sb-input w100"  ng-model="srchPopProdCd"/></td>
        <%-- 상품명 --%>
        <th><s:message code="prodcalss.prodNm" /></th>
        <td><input type="text" id="srchPopProdNm" name="srchPopProdNm" class="sb-input w100"  ng-model="srchPopProdNm"/></td>
      	</tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <div id="gridRepresent" class="w50 fl" style="width:49%;">
    <%-- 상품대분류 --%>
    	<div class="w100 mt10">
      	<div class="wj-gridWrap" style="height: 250px; overflow-x: hidden; overflow-y: hidden;">
	        <wj-flex-grid
	          loaded-rows="loadedRows(s,e)"
	          autoGenerateColumns="false"
	          selection-mode="Row"
	          items-source="data"
	          control="flex"
	          initialized="initGrid(s,e)"
	          is-read-only="false"
	          item-formatter="_itemFormatter">
	
	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="saleComPopup.prod.prodClassCd"/>"  binding="prodClassCd"   align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="saleComPopup.prod.prodClassNm"/>"  binding="prodClassNm"   align="center" is-read-only="true"></wj-flex-grid-column>
	        </wj-flex-grid>
	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="prodStrl"/>
	        </jsp:include>
        	<%--// ColumnPicker 사용시 include --%>
      	</div>
      	</div>
      </div>
      
	  <div id="gridDetail" class="w50 fr" style="width:49%;">
	   <%-- 상품상세 --%>
     	<div class="w100 mt10" ng-controller="prodDtlStrl">
     	<div class="wj-gridWrap" style="height: 250px; overflow-x: hidden; overflow-y: hidden;">
	        <wj-flex-grid
	          id="prodDtlGrid"
	          autoGenerateColumns="false"
	          selection-mode="Row"
	          items-source="data"
	          control="flex"
	          initialized="initGrid(s,e)"
	          is-read-only="false"
	          item-formatter="_itemFormatter">
	
	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="saleComPopup.prod.prodCd"/>"  binding="prodCd"    width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="saleComPopup.prod.prodNm"/>"  binding="prodNm"    align="center" is-read-only="true"></wj-flex-grid-column>
	        </wj-flex-grid>
        	<%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="prodDtlStrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
      	</div>
	</div>
	</div>
	</div>
</div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/com/selectProdS.js?ver=20190207.11" charset="utf-8"></script>