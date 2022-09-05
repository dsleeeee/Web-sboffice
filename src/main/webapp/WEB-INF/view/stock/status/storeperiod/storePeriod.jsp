<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/status/storeperiod/"/>

<div class="subCon" ng-controller="storePeriodCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="storePeriod.storePeriod"/></a>
		<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storePeriodCtrlSrch')"><s:message code="cmm.search"/></button>
	</div>
	<table class="searchTbl">
		<colgroup>
			<col class="w13"/>
            <col class="w37"/>
            <col class="w13"/>
            <col class="w37"/>
		</colgroup>
		<tr>
			<%-- 조회기간 --%>
			<th><s:message code="storePeriod.srchDate" /></th>
			<td>
			<div class="sb-select">
				<span class="txtIn"><input id="srchClassStartDate" class="w120px"></span>
				<span class="rg">~</span>
				<span class="txtIn"><input id="srchClassEndDate" class="w120px"></span>
			</div>
			</td>
			<%-- 매장명 --%>
			<th><s:message code="storePeriod.storeNm" /></th>
			<td>
				<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
          			<jsp:param name="targetId" value="storePeriodSelectStore"/>
        		</jsp:include>
			</td>

		</tr>

		<c:if test="${sessionInfo.orgnFg == 'STORE'}">
      		<input type="hidden" id="todayGnrlzSelectStoreCd" value="${sessionInfo.storeCd}"/>
      	</c:if>
		<tr>
			<%-- 상품코드 --%>
			<th><s:message code="storePeriod.prodCd" /></th>
			<td>
				<span class="txtIn w150px">
	                <input type="text" id="srchProdCd" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch();"/>
	            </span>
			</td>
			<%-- 상품명 --%>
			<th><s:message code="storePeriod.prodNm" /></th>
			<td>
				<span class="txtIn w150px">
	                <input type="text" id="srchProdNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch();"/>
	            </span>
			</td>
		</tr>
		<tr>
			<%-- 바코드 --%>
			<th><s:message code="storePeriod.barcdCd" /></th>
			<td>
				<span class="txtIn w150px">
	                <input type="text" id="srchBarcdCd" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch();"/>
	            </span>
			</td>

			<%-- 단위구분 --%>
			<th><s:message code="storePeriod.unitFg" /></th>
			<td>
				<div class="sb-select">
				<span class="txtIn w120px">
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
		</tr>
		<tr>
			<%-- 분류 --%>
			<th><s:message code="storePeriod.prodClass" /></th>
			<td>
				<input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
					   placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
				<input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCdModel" disabled />
				<button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
			</td>

			<%-- 조회옵션 --%>
            <th><s:message code="storePeriod.srchOption" /></th>
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
                    <span class="chk ml5">
                    <input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                    <label for="chkDt">
                        <s:message code="storePeriod.prodClassDisplay" />
                    </label>
                </span>
                </div>
            </td>
		</tr>
		<tr>
			<%-- 거래처 --%>
            <th style="display: none;"><s:message code="storePeriod.vendr" /></th>
            <td style="display: none;">
              <%-- 거래처선택 모듈 멀티 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
              --%>
              <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
                <jsp:param name="targetId" value="storePeriodIostockSelectVendr"/>
              </jsp:include>
              <input type="hidden" id="storePeriodIostockSelectVendrCd" value=""/>
            </td>
		</tr>
	</table>
	<div class="mt20 oh sb-select dkbr">
		<%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="storePeriodListScaleBox"
            ng-model="listScale"
            items-source="_getComboData('storePeriodListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="_initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
        </wj-combo-box>

    	<%-- 엑셀 다운로드 --%>
    	<button class="btn_skyblue ml5 fr" ng-click="excelDownloadStore()"><s:message code="cmm.excel.down"/></button>
  	</div>

  	<%--위즈모 테이블--%>
    <div id="wjGridWrap" class="mt10">
      <div class="wj-gridWrap" style="height:320px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
          id="storePeriodGrid"
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter"
          frozen-columns="5">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="storePeriod.prodClassNm"/>"	binding="prodClassNm"	width="200"		align="left"	is-read-only="true"	visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePeriod.prodCd"/>"		binding="prodCd"		width="100"		align="center"	is-read-only="true"	format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePeriod.prodNm"/>"		binding="prodNm"		width="100"		align="left"	is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePeriod.storeCd"/>"		binding="storeCd"		width="80"		align="center"	is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePeriod.storeNm"/>"		binding="storeNm"		width="100"		align="left"	is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePeriod.poUnitQty"/>"	binding="poUnitQty"		width="60"		align="center"	is-read-only="true"	aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePeriod.poUnitFg"/>"	binding="poUnitFgNm"	width="60"		align="center"	is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storePeriod.barcdCd"/>"		binding="barcdCd"		width="100"		align="center"	is-read-only="true" aggregate="Sum"	format="d"></wj-flex-grid-column>

	      <wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="storeInQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="storeInTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="storeOutQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="storeOutTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="purchsInQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="purchsInTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="purchsOutQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="purchsOutTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="storeSaleQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="storeSaleTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	      <wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="moveInQty"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="moveInTot"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="moveOutQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="moveOutTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.ioOccr17"/>"	binding="disuseQty"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.ioOccr21"/>"	binding="adjQty"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="storePeriod.ioOccr22"/>"	binding="setInQty"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	      <wj-flex-grid-column header="<s:message code="cmm.search.date"/>"	binding="startDate"		width="60"		align="center"	is-read-only="true" visible="false"></wj-flex-grid-column>
	      <wj-flex-grid-column header="<s:message code="cmm.search.date"/>"	binding="endDate"		width="60"		align="center"	is-read-only="true" visible="false"></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="storePeriodCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storePeriodCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
  
  <%--엑셀 리스트--%>
    <div id="wjGridWrap" class="mt10" style="display:none;" ng-controller="storePeriodExcelCtrl">
      <div class="wj-gridWrap">
        <wj-flex-grid
          id="storePeriodExcelGrid"
          autoGenerateColumns="false"
          control="excelFlex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter"
          frozen-columns="5">

          <!-- define columns -->
			<wj-flex-grid-column header="<s:message code="storePeriod.prodClassNm"/>"	binding="prodClassNm"	width="200"		align="left"	is-read-only="true"	visible="false"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.prodCd"/>"		binding="prodCd"		width="100"		align="center"	is-read-only="true"	format="d"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.prodNm"/>"		binding="prodNm"		width="100"		align="left"	is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.storeCd"/>"		binding="storeCd"		width="80"		align="center"	is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.storeNm"/>"		binding="storeNm"		width="100"		align="left"	is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.poUnitQty"/>"		binding="poUnitQty"		width="60"		align="center"	is-read-only="true"	aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.poUnitFg"/>"		binding="poUnitFgNm"	width="60"		align="center"	is-read-only="true" ></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.barcdCd"/>"		binding="barcdCd"		width="100"		align="center"	is-read-only="true" aggregate="Sum"	format="d"></wj-flex-grid-column>

			<wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="storeInQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="storeInTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="storeOutQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="storeOutTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="purchsInQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="purchsInTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="purchsOutQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="purchsOutTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="storeSaleQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="storeSaleTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

			<wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="moveInQty"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="moveInTot"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.qty"/>"			binding="moveOutQty"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.tot"/>"			binding="moveOutTot"	width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.ioOccr17"/>"		binding="disuseQty"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.ioOccr21"/>"		binding="adjQty"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="storePeriod.ioOccr22"/>"		binding="setInQty"		width="60"		align="right"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
    <%--//위즈모 테이블--%>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/stock/status/storeperiod/storePeriod.js?ver=20220803.02" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/storeProdCodeDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수량 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/prodQtyDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>