<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/status/periodiostock/"/>

<wj-popup id="prodCodeDtlLayer" control="prodCodeDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="prodCodeDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="periodIostock.iostockDetail"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">
      <table class="tblType01 mt10 tc">
        <colgroup>
          <col class="w15"/>
          <col class="w20"/>
          <col class="w20"/>
          <col class="w20"/>
          <col class="w25"/>
        </colgroup>
        <tbody>
        	<c:if test="${sessionInfo.orgnFg == 'HQ'}">
        	<tr>
          		<th class="tc"><s:message code="periodIostock.prodCd"/></th>
          		<th class="tc"><s:message code="periodIostock.prodNm"/></th>
          		<th class="tc"><s:message code="periodIostock.iostock"/></th>
        	</tr>
        	<tr>
          		<td id="prodCd" style="border-left: 1px solid #ccc;">{{prodCd}}</td>
          		<td id="prodNm" style="border-left: 1px solid #ccc;">{{prodNm}}</td>
          		<td id="srchDate" style="border-left: 1px solid #ccc; border-right: 1px solid #ccc;">{{startDate}}~{{endDate}}</td>
	        </tr>
	        </c:if>
	        
	        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
	        <tr>
	        	<th class="tc"><s:message code="periodIostock.storeCd"/></th>
          		<th class="tc"><s:message code="periodIostock.storeNm"/></th>
          		<th class="tc"><s:message code="periodIostock.prodCd"/></th>
          		<th class="tc"><s:message code="periodIostock.prodNm"/></th>
          		<th class="tc"><s:message code="periodIostock.iostock"/></th>
        	</tr>
        	<tr>
        		<td id="storeCdDetail" style="border-left: 1px solid #ccc;">{{storeCd}}</td>
          		<td id="storeNmDetail" style="border-left: 1px solid #ccc;">{{storeNm}}</td>
          		<td id="prodCd" style="border-left: 1px solid #ccc;">{{prodCd}}</td>
          		<td id="prodNm" style="border-left: 1px solid #ccc;">{{prodNm}}</td>
          		<td id="srchDate" style="border-left: 1px solid #ccc; border-right: 1px solid #ccc;">{{startDate}}~{{endDate}}</td>
	        </tr>
	        </c:if>
        </tbody>
      </table>
      
      <input type="hidden" id="hqOfficeCode" value="${sessionInfo.hqOfficeCd}"/>

      <div class="tr mt20 fr">
        <div id="volmErrBtnLayer" style="display: none;">
          <span class="chk txtIn fl lh30" style="top: -2px;" ng-if="volmErrConfirmFg">
            <input type="checkbox" name="volmErrConfirmFg" id="volmErrConfirmFg" value="Y" ng-click="fnConfirmChk()"/>
            <label for="volmErrConfirmFg"><s:message code="volmErr.dtl.confirm"/></label>
          </span>
          <%-- 출고일자 --%>
        <%--<div id="divDtlOutDate" class="sb-select ml10 fl" style="display: none;">--%>
       		<div id="divDtlOutDate2" class="sb-select ml10 fl">
            <p class="s12 fl mr5 lh30"><s:message code="volmErr.dtl.outDate"/> : </p>
            <span class="txtIn"><input id="dtlOutDate2" class="w120px"></span>
          </div>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()" ng-if="btnDtlSave">
            <s:message code="cmm.save"/></button>
        </div>
        
        <%-- 엑셀 다운로드 --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
        </button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 200px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="periodIostock.date"/>" 		binding="ioOccrDt" 			width="100" align="center" 	is-read-only="true"														></wj-flex-grid-column>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty01"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot01"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty16"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot16"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty13"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot13"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty02"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot02"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       </c:if>
	       <c:if test="${sessionInfo.orgnFg == 'STORE'}">
	       	  <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty03"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot03"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty12"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot12"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty06"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot06"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty18"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot18"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty11"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot11"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       </c:if>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty04"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot04"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty14"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot14"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty17"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot17"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty21"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot21"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty22"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot22"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty19"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot19"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	       <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty33"		width="80"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot33"		width="100"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		  </c:if>
          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="prodCodeDtlCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/status/periodiostock/prodCodeDtl.js?ver=20200319.01" charset="utf-8"></script>
