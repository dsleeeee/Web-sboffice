<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="dlvrFgProdView" class="subCon" ng-controller="dlvrFgProdCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="dlvrFg.prod"/></a>
    	<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dlvrFgProdCtrl')">
    		<s:message code="cmm.search"/>
    	</button>
	</div>
    <%-- 조회조건 --%>
    <table class="searchTbl">
		<colgroup>
        	<col class="w15"/>
	        <col class="w35"/>
	        <col class="w15"/>
	        <col class="w35"/>
      	</colgroup>
      	<tbody>
		<tr>
			<%-- 조회일자 --%>
	    	<th><s:message code="cmm.search.date" /></th>
        	<td>
          	<div class="sb-select">
				<span class="txtIn"> <input id="startDate" name="startDate" class="w110px" /></span>
				<span class="rg">~</span>
				<span class="txtIn"> <input id="endDate" name="endDate" class="w110px" /></span>
          	</div>
        	</td>
        	<%-- 배달구분 --%>
			<th><s:message code="dlvrFg.prod.dlvrFg" /></th>
			<td>
				<div class="sb-select w100px fl mr5">
					<wj-combo-box
							id="packFg"
							ng-model="packFg"
							items-source="_getComboData('packFg')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)">
					</wj-combo-box>
				</div>
				<div class="sb-select w150px fl">
					<wj-combo-box
							id="dlvrInFg"
							ng-model="dlvrInFg"
							items-source="_getComboData('dlvrInFg')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)">
					</wj-combo-box>
				</div>
			</td>
		</tr>
		<tr>
            <%-- 상품코드 --%>
            <th><s:message code="dlvrFg.prod.prodCd" /></th>
            <td><input type="text" id="srchProdCd" class="sb-input w100" maxlength="13" ng-model="prodCd" /></td>
            <%-- 상품명 --%>
            <th><s:message code="dlvrFg.prod.prodNm" /></th>
            <td><input type="text" id="srchProdNm" class="sb-input w100" maxlength="100" ng-model="prodNm" /></td>
      	</tr>
        <tr>
        	<%-- 분류조회 --%>
	        <th><s:message code="dlvrFg.prod.prodClass" /></th>
			<td>
				<input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
					   placeholder="<s:message code="dlvrFg.prod.prodClass" /> 선택" readonly/>
				<input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
				<button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
			</td>
            <%-- 매장코드 --%>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<th><s:message code="dlvrFg.prod.storeCd"/></th>
				<td>
					<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
						<jsp:param name="targetId" value="dlvrFgProdStore"/>
					</jsp:include>
					<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
				</td>
        	</c:if>
			<c:if test="${sessionInfo.orgnFg == 'STORE'}">
				<th></th>
				<td></td>
			</c:if>
		</tr>
		</tbody>
	</table>

	<%--위즈모 테이블--%>
    <div class="w100 mt5">
		<%-- 유형별 --%>
		<div class="w100 mt10" ng-controller="dlvrFgProdOrderFgCtrl">
			<div class="oh sb-select mb10">
				<span class="fl bk lh30"><s:message code='dlvrFg.prod.orderFg'/></span>
				<%-- 유형별 엑셀다운로드 --%>
				<button class="btn_skyblue ml5 fr" ng-click="excelDownloadOrderFg()"><s:message code="cmm.excel.down"/></button>
			</div>
			<%--위즈모 테이블--%>
			<div class="wj-gridWrap" style="height: 150px; overflow-x: hidden; overflow-y: hidden;">
				<wj-flex-grid
						autoGenerateColumns="false"
						selection-mode="Row"
						items-source="data"
						control="flex"
						initialized="initGrid(s,e)"
						is-read-only="false"
						item-formatter="_itemFormatter">

					<!-- define columns -->
					<wj-flex-grid-column header="<s:message code="dlvrFg.dlvrFg"/>" binding="dlvrPackFg" data-map="packFgDataMap" width="80" is-read-only="true" align="center" ></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totSaleAmt"/>" binding="totSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totDcAmt"/>" binding="totDcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.gaAmt"/>" binding="totGaAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.vatAmt"/>" binding="totVatAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totTipAmt"/>" binding="totTipAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totEtcAmt"/>" binding="totEtcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<%-- 결제수단 컬럼 생성--%>
					<c:forEach var="payCol" items="${payColList}">
						<wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					</c:forEach>
				</wj-flex-grid>
				<%-- ColumnPicker 사용시 include --%>
				<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
					<jsp:param name="pickerTarget" value="todayGnrlzCtrl"/>
				</jsp:include>
				<%--// ColumnPicker 사용시 include --%>
			</div>
			<%--//위즈모 테이블--%>
		</div>

		<%-- 상품별 --%>
		<div class="w100 mt10" ng-controller="dlvrFgProdProdCtrl">
			<div class="oh sb-select mb10">
				<span class="fl bk lh30"><s:message code='dlvrFg.prod.prod'/></span>
				<%-- 상품별 엑셀다운로드 --%>
				<button class="btn_skyblue ml5 fr" ng-click="excelDownloadProd()"><s:message code="cmm.excel.down"/></button>
			</div>

			<%--위즈모 테이블--%>
			<div class="wj-gridWrap" style="height: 150px; overflow-x: hidden; overflow-y: hidden;">
				<wj-flex-grid
						autoGenerateColumns="false"
						selection-mode="Row"
						items-source="data"
						control="flex"
						initialized="initGrid(s,e)"
						is-read-only="false"
						item-formatter="_itemFormatter">

					<!-- define columns -->
					<wj-flex-grid-column header="<s:message code="dlvrFg.dlvrFg"/>" binding="dlvrPackFg" data-map="packFgDataMap" width="80" is-read-only="true" align="center" ></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.prodCd"/>" binding="prodCd" width="80" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.prodNm"/>" binding="prodNm" width="80" is-read-only="true" ></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.barCd"/>" binding="barCd" width="80" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.saleQty"/>" binding="saleQty" width="80" is-read-only="true" align="center" ></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totSaleAmt"/>" binding="totSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totDcAmt"/>" binding="totDcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.gaAmt"/>" binding="totGaAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.vatAmt"/>" binding="totVatAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totTipAmt"/>" binding="totTipAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totEtcAmt"/>" binding="totEtcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<%-- 결제수단 컬럼 생성--%>
					<c:forEach var="payCol" items="${payColList}">
						<wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					</c:forEach>
				</wj-flex-grid>
			</div>
			<%--//위즈모 테이블--%>
		</div>

		<%-- 상품별(상세) --%>
		<div class="w100 mt10" ng-controller="dlvrFgProdProdDtlCtrl">
			<div class="oh sb-select mb10">
				<span class="fl bk lh30"><s:message code='dlvrFg.prod.prodDtl'/></span>
				<%-- 상품별(상세) 엑셀다운로드 --%>
				<button class="btn_skyblue ml5 fr" ng-click="excelDownloadProdDtl()"><s:message code="cmm.excel.down"/></button>
			</div>

			<%--위즈모 테이블--%>
			<div class="wj-gridWrap" style="height: 150px; overflow-x: hidden; overflow-y: hidden;">
				<wj-flex-grid
						autoGenerateColumns="false"
						selection-mode="Row"
						items-source="data"
						control="flex"
						initialized="initGrid(s,e)"
						is-read-only="false"
						item-formatter="_itemFormatter">

					<wj-flex-grid-column header="<s:message code="dlvrFg.dlvrFg"/>" binding="dlvrPackFg" data-map="packFgDataMap" width="80" is-read-only="true" align="center" ></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.dlvrInFg"/>" binding="dlvrInFg" data-map="dlvrInFgDataMap" width="80" is-read-only="true" align="center" ></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.prodCd"/>" binding="prodCd" width="80" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.prodNm"/>" binding="prodNm" width="80" is-read-only="true" ></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.barCd"/>" binding="barCd" width="80" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.saleQty"/>" binding="saleQty" width="80" is-read-only="true" align="center" ></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totSaleAmt"/>" binding="totSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totDcAmt"/>" binding="totDcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.gaAmt"/>" binding="totGaAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.vatAmt"/>" binding="totVatAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totTipAmt"/>" binding="totTipAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totEtcAmt"/>" binding="totEtcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="dlvrFg.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<%-- 결제수단 컬럼 생성--%>
					<c:forEach var="payCol" items="${payColList}">
						<wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					</c:forEach>

				</wj-flex-grid>
			</div>
			<%--//위즈모 테이블--%>
		</div>
    </div>
    <%--//위즈모 테이블--%>

</div>
<script type="text/javascript">
	<%-- 배달구분 --%>
	var packFg = ${ccu.getCommCode("017")};
	<%-- 배달경로_공통코드 --%>
	var dlvrInFg = ${ccu.getCommCodeExcpAll("112")};

	var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/dlvr/dlvrFg/dlvrFgProd.js?ver=20210729.02" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 매출상세 팝업 --%>
<c:import url="/WEB-INF/view/sale/dlvr/dlvrFg/dlvrFgPop/dlvrFgSaleDtlPop.jsp">
</c:import>