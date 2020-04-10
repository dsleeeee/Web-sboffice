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
			<col class="w13"/>
            <col class="w37"/>
            <col class="w13"/>
            <col class="w37"/>
		</colgroup>
		<%-- 조회일자 --%>
		<tr>
			<th><s:message code="periodIostock.srchDate" /></th>
			<td colspan="3">
			<div class="sb-select">
				<span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w120px"></span>
				<span class="rg">~</span>
				<span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w120px"></span>
			</div>
			</td>
		</tr>

		<tr>
			<%-- 조회옵션 --%>
			<th><s:message code="periodIostock.srchOption" /></th>
			<td colspan="3">
				<div class="sb-select">
	          		<span class="txtIn w150px">
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
            control="listScaleCombo"
            items-source="_getComboData('dailyIoStockListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
        </wj-combo-box>

        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
        </button>
    </div>

  	<%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType1">
    <div class="wj-gridWrap" style="height: 350px;">
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
          <c:if test="${orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrDt"/>"                binding="ioOccrDt"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"                binding="ioOccrQty01"        width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"             binding="ioOccrTot01"        width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"              binding="ioOccrQty16"        width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"               binding="ioOccrTot16"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"         binding="ioOccrQty13"       width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"         binding="ioOccrTot13"    width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"        binding="ioOccrQty02"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"        binding="ioOccrTot02"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"              binding="ioOccrQty04"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"              binding="ioOccrTot04"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"               binding="ioOccrQty14"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"               binding="ioOccrTot14"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"     binding="ioOccrQty17"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"     binding="ioOccrTot17"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"    binding="ioOccrQty21"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"    binding="ioOccrTot21"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"          binding="ioOccrQty22"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"        	binding="ioOccrTot22"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"             binding="ioOccrQty19"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"             binding="ioOccrTot19"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"           binding="ioOccrQty33"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"        	  binding="ioOccrTot33"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:if>
          <c:if test="${orgnFg == 'STORE'}">
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrDt"/>"                binding="ioOccrDt"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"                binding="ioOccrQty03"        width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"             binding="ioOccrTot03"        width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"              binding="ioOccrQty12"        width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"               binding="ioOccrTot12"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"         binding="ioOccrQty06"       width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"         binding="ioOccrTot06"    width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"        binding="ioOccrQty18"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"        binding="ioOccrTot18"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"              binding="ioOccrQty11"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"              binding="ioOccrTot11"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"               binding="ioOccrQty04"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"               binding="ioOccrTot04"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"     binding="ioOccrQty14"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"     binding="ioOccrTot14"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"    binding="ioOccrQty01"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"    binding="ioOccrTot01"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"          binding="ioOccrQty02"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"          binding="ioOccrTot02"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrQty"/>"             binding="ioOccrQty05"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="dailyIostock.ioOccrTot"/>"             binding="ioOccrTot05"       width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:if>

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
</div>


<script type="text/javascript" src="/resource/solbipos/js/stock/status/dailyIoStock/dailyIoStock.js?ver=20200312.01" charset="utf-8"></script>

<%-- 본사출고정보 팝업 레이어 --%>
<c:import url="/WEB-INF/view/stock/com/popup/dailyIoStockInfo/dailyIoStockInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
