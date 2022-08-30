<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/status/dailyIoStock/"/>

<div class="subCon" ng-controller="dailyIoStockCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl">${menuNm}</a>
		<%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnDcDcfgSearch" ng-click="_broadcast('dailyIoStockCtrl')">
            <s:message code="cmm.search" />
        </button>
	</div>
	<table class="searchTbl">
		<colgroup>
			<col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
		</colgroup>
		<%-- 조회일자 --%>
		<tr>
			<th><s:message code="periodIostock.srchDate" /></th>
			<td>
			<div class="sb-select">
				<span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px"></span>
				<span class="rg">~</span>
				<span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px"></span>
			</div>
			</td>
			<%-- 조회옵션 --%>
			<th><s:message code="periodIostock.srchOption" /></th>
			<td>
				<div class="sb-select">
	          		<span class="txtIn w120px">
	            		<wj-combo-box
	              			id="srchSrchOption"
	              			ng-model="srchOption"
	              			items-source="_getComboData('srchSrchOption')"
	              			display-member-path="name"
	              			selected-value-path="value"
	              			is-editable="false"
	              			initialized="_initComboBox(s)">
	            		</wj-combo-box>
	          		</span>
	        	</div>
			</td>
		</tr>
	</table>
	<div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="dailyIoStockListScaleBox"
            ng-model="listScale"
            items-source="_getComboData('dailyIoStockListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="_initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
        </wj-combo-box>

        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
        </button>
    </div>

  	<%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType1">
    <div class="wj-gridWrap" style="height: 360px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter"
        frozen-columns="1">

          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrDt"/>"   binding="ioOccrDt"     	 	width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="vendrInQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="vendrInTot"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="vendrOutQty"   	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="vendrOutTot"   	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="hqOutQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="hqOutTot"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="hqInQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="hqInTot"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="storeMoveInQty"   	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="storeMoveInTot"   	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="storeMoveOutQty"   width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="storeMoveOutTot"   width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="dailyIostock.col8"/>"  		binding="disuseQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="dailyIostock.col9"/>"  		binding="adjQty"   			width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="dailyIostock.col10"/>"  	binding="setInQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="saleVendrOrderQty"	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="saleVendrOrderTot"	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="saleVendrRtnQty"   width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="saleVendrRtnTot"   width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="dailyIoStockCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dailyIoStockCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
    
    <%-- 엑셀 리스트 --%>
	<div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="dailyIoStockExcelCtrl">
      <div class="wj-gridWrap">
      <wj-flex-grid
		id="dailyIoStockExcelGrid"
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="excelFlex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter"
        frozen-columns="1">

          <!-- define columns -->
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrDt"/>"   binding="ioOccrDt"      	width="100" align="center" is-read-only="true"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="vendrInQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="vendrInTot"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="vendrOutQty"   	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="vendrOutTot"   	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="hqOutQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="hqOutTot"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="hqInQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="hqInTot"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="storeMoveInQty"   	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="storeMoveInTot"   	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="storeMoveOutQty"   width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="storeMoveOutTot"   width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.col8"/>"  		binding="disuseQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.col9"/>"  		binding="adjQty"   			width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.col10"/>"  	binding="setInQty"   		width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="saleVendrOrderQty"	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="saleVendrOrderTot"	width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"  binding="saleVendrRtnQty"   width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"  binding="saleVendrRtnTot"   width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

      </wj-flex-grid>
      </div>
	</div>
	<%--//엑셀 리스트--%>
    
</div>


<script type="text/javascript" src="/resource/solbipos/js/stock/status/dailyIoStock/hqDailyIoStock.js?ver=20220803.01" charset="utf-8"></script>

<%-- 본사출고정보 팝업 레이어 --%>
<c:import url="/WEB-INF/view/stock/com/popup/dailyIoStockInfo/dailyIoStockInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
