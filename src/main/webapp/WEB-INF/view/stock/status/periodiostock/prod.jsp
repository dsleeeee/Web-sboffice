<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/status/periodiostock/"/>

<div class="subCon" ng-controller="periodiostockCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="periodIostock.periodIostock"/></a>
		<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('periodiostockCtrl')"><s:message code="cmm.search"/></button>
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
			<td colspan="3">
			<div class="sb-select">
				<span class="txtIn"><input id="srchClassStartDate" class="w120px"></span>
				<span class="rg">~</span>
				<span class="txtIn"><input id="srchClassEndDate" class="w120px"></span>
			</div>
			</td>
		</tr>
		<tr>
			<%-- 상품코드 --%>
			<th><s:message code="periodIostock.prodCd" /></th>
			<td>
				<input type="text" id="srchProdCd" class="sb-input w100" maxlength="100" />
			</td>
			<%-- 상품명 --%>
			<th><s:message code="periodIostock.prodNm" /></th>
			<td>
				<input type="text" id="srchProdNm" class="sb-input w100" maxlength="100" />
			</td>
		</tr>
		<tr>
			<%-- 바코드 --%>
			<th><s:message code="periodIostock.barcdCd" /></th>
			<td>
				<input type="text" id="srchBarcdCd" class="sb-input w100" maxlength="100" />
			</td>
			<%-- 분류 --%>
            <th><s:message code="periodIostock.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                 placeholder="<s:message code="cmm.all" />" readonly/>
		        <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCdModel" disabled/>
		        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
		</tr>
		<tr>
			<%-- 단위구분 --%>
			<th><s:message code="periodIostock.unitFg" /></th>
			<td>
				<div class="sb-select">
	          		<span class="txtIn w150px">
	            		<wj-combo-box
	              			id="srchUnitFg"
	              			ng-model="unitFgModel"
	              			items-source="_getComboData('srchUnitFg')"
	              			display-member-path="name"
	              			selected-value-path="value"
	              			is-editable="false"
	              			initialized="_initComboBox(s)">
	            		</wj-combo-box>
	          		</span>
	        	</div>
			</td>

			<%-- 조회옵션 --%>
            <th><s:message code="periodIostock.srchOption" /></th>
            <td>
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
                    <span class="chk ml10">
                     <input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                     <label for="chkDt">
                         <s:message code="periodIostock.prodClassDisplay" />
                     </label>
                    </span>
                </div>
            </td>
		</tr>
		<tr>
			<%-- 거래처 --%>
            <th style="display: none;"><s:message code="periodIostock.vendr" /></th>
            <td style="display: none;">
                <%-- 거래처선택 모듈 멀티 선택 사용시 include
                param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                --%>
                <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
                    <jsp:param name="targetId" value="periodIostockSelectVendr"/>
                </jsp:include>
                <input type="hidden" id="periodIostockSelectVendrCd" value=""/>
            </td>
		</tr>
	</table>

	<input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
	<input type="hidden" id="storeCd" value="${sessionInfo.storeCd}"/>
    <input type="hidden" id="storeNm" value="${sessionInfo.storeNm}"/>

	<div class="mt20 oh sb-select dkbr">
		<%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="periodiostockListScaleBox"
            ng-model="listScale"
            items-source="_getComboData('periodiostockListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="_initComboBox(s)"
            control="listScaleCombo"
			is-editable="true"
			text-changed="_checkValidation(s)">
        </wj-combo-box>

    	<%-- 엑셀 다운로드 --%>
    	<button class="btn_skyblue ml5 fr" id="btnExcelDown" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
  	</div>

  	<%--위즈모 테이블--%>
    <div id="wjGridWrap" class="w100 mt10">
      <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter"
          frozen-columns="5">

          <!-- define columns -->

          <wj-flex-grid-column header="<s:message code="periodIostock.lv1Nm"/>"        			binding="lv1Nm"					width="120"		align="center"	is-read-only="true"	visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.lv2Nm"/>"        			binding="lv2Nm"					width="150"		align="center"	is-read-only="true"	visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.lv3Nm"/>"       			binding="lv3Nm"					width="200"		align="center"	is-read-only="true"	visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.prodCd"/>"				binding="prodCd"				width="100"		align="center"	is-read-only="true"	format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.prodNm"/>"				binding="prodNm"				width="150"		align="center"	is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.poUnitQty"/>"				binding="poUnitQty"				width="70"		align="right"	is-read-only="true"	aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.poUnitFg"/>"				binding="poUnitFgNm"				width="70"		align="center"	is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.barcdCd"/>"				binding="barcdCd"				width="120"		align="center"	is-read-only="true" format="d"></wj-flex-grid-column>


          <c:if test="${orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty01"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot01"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty16"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot16"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty13"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot13"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty02"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot02"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       </c:if>
	       <c:if test="${orgnFg == 'STORE'}">
	       	  <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty03"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot03"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty12"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot12"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty06"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot06"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty18"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot18"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty11"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot11"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       </c:if>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty04"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot04"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty14"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot14"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty17"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot17"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty21"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot21"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty22"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot22"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty19"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot19"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <c:if test="${orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty33"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot33"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
		  </c:if>

        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="periodiostockCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="periodiostockCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--엑셀 리스트--%>
    <div id="wjGridWrap" class="w100 mt10" style="display:none;" ng-controller="periodiostockExcelCtrl">
      <div class="wj-gridWrap" style="height: 350px;">
        <wj-flex-grid
          id="periodiostockExcelGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="excelFlex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter"
          frozen-columns="5">

          <!-- define columns -->

          <wj-flex-grid-column header="<s:message code="periodIostock.lv1Nm"/>"        			binding="lv1Nm"					width="120"		align="center"	is-read-only="true"	visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.lv2Nm"/>"        			binding="lv2Nm"					width="150"		align="center"	is-read-only="true"	visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.lv3Nm"/>"       			binding="lv3Nm"					width="200"		align="center"	is-read-only="true"	visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.prodCd"/>"				binding="prodCd"				width="100"		align="center"	is-read-only="true"	format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.prodNm"/>"				binding="prodNm"				width="150"		align="center"	is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.poUnitQty"/>"				binding="poUnitQty"				width="70"		align="right"	is-read-only="true"	aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.poUnitFg"/>"				binding="poUnitFgNm"				width="70"		align="center"	is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="periodIostock.barcdCd"/>"				binding="barcdCd"				width="120"		align="center"	is-read-only="true" format="d"></wj-flex-grid-column>


          <c:if test="${orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty01"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot01"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty16"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot16"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty13"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot13"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty02"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot02"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       </c:if>
	       <c:if test="${orgnFg == 'STORE'}">
	       	  <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty03"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot03"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty12"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot12"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty06"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot06"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty18"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot18"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"	binding="ioOccrQty11"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"	binding="ioOccrTot11"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       </c:if>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty04"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot04"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty14"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot14"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty17"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot17"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty21"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot21"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty22"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot22"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"			binding="ioOccrQty19"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"			binding="ioOccrTot19"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	       <c:if test="${orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="periodIostock.Qty"/>"		binding="ioOccrQty33"		width="70"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="periodIostock.Tot"/>"		binding="ioOccrTot33"		width="100"		align="right"	is-read-only="true" aggregate="Sum"	visible="false"></wj-flex-grid-column>
		  </c:if>
        </wj-flex-grid>
      </div>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/stock/status/periodiostock/prod.js?ver=20200312.02" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/prodCodeDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수량 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/prodQtyDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>