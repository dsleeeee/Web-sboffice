<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
    파일명 : saleTrnsitnBenson.jsp
    설  명 : 벤슨 > 매출분석 > 매출추이분석
    작성자 : 링크 개발실 개발1팀 김유승
    작성일 : 2026.09.10
--%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="gvOrgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon3" ng-controller="saleTrnsitnBensonCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('saleTrnsitnBensonCtrlSrch')" id="nxBtnSearch">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
			<tr>
				<%-- 조회일자 --%>
				<th><s:message code="cmm.search.date"/></th>
				<td>
					<div class="sb-select">
						<span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px"></span>
						<span class="rg">~</span>
						<span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px"></span>
					</div>
				</td>
				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<%-- 매장선택 --%>
				<th><s:message code="cmm.store.select"/></th>
				<td>
					<%-- 매장선택 모듈 멀티 선택 사용시 include --%>
					<jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
						<jsp:param name="targetTypeFg" value="M"/>
						<jsp:param name="targetId" value="saleTrnsitnBensonStore"/>
					</jsp:include>
					<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
				</td>
				</c:if>
			</tr>
			<tr>
				<th><s:message code="prod.prodCd" /></th>		<%-- 상품코드 --%>
				<td><input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCdModel" onkeyup="fnNxBtnSearch();"/></td>
				<th><s:message code="prod.prodNm" /></th>		<%-- 상품명 --%>
				<td><input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNmModel" onkeyup="fnNxBtnSearch();"/></td>
			</tr>
			<tr>
		        <th><s:message code="prod.prodClass" /></th>	<%-- 분류조회 --%>
		        <td>
		          	<input type="text" 		class="sb-input w70" 		id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;" placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
		          	<input type="hidden" 								id="_prodClassCd" name="prodClassCd" ng-model="prodClassCdModel" disabled />
		        	<button type="button" 	class="btn_skyblue fl mr5" 	id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
		        </td>
				<th></th>
				<td></td>
			</tr>
        </tbody>
    </table>


    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
          class="w100px fl"
          id="saleTrnsitnBensonlistScaleBox"
          ng-model="saleTrnsitnBensonlistScale"
          items-source="_getComboData('saleTrnsitnBensonlistScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          initialized="_initComboBox(s)"
          control="conListScale"
	      is-editable="true"
		  text-changed="_checkValidation(s)">
        </wj-combo-box>

    	<button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>	<%-- 엑셀 다운로드 --%>
	</div>


    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap2" style="overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid	id="saleTrnsitnBensonGrid"
            				autoGenerateColumns="false"
			                control="flex"
			                initialized="initGrid(s,e)"
			                sticky-headers="true"
			                selection-mode="Row"
			                items-source="data"
			                item-formatter="_itemFormatter"
			                is-read-only="true"
			                frozen-columns="5">

                <wj-flex-grid-column header="<s:message code="cmm.storeCd"/>" 					binding="storeCd"  			width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>" 					binding="storeNm" 			width="130" is-read-only="true" align="left"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.prodCd"/>" 			binding="prodCd"  			width="100" is-read-only="true" align="center" format="d"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.prodNm"/>" 			binding="prodNm" 			width="130" is-read-only="true" align="left"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.splyUprc"/>" 			binding="splyUprc" 			width="100" is-read-only="true" align="right"	></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore13"/>"		binding="dateBefore13Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore12"/>"		binding="dateBefore12Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore11"/>"		binding="dateBefore11Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore10"/>"		binding="dateBefore10Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore9"/>"		binding="dateBefore9Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore8"/>"		binding="dateBefore8Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore7"/>"		binding="dateBefore7Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore6"/>"		binding="dateBefore6Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore5"/>"		binding="dateBefore5Qty" 	width="100" is-read-only="true" align="right"	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore4"/>"		binding="dateBefore4Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore3"/>"		binding="dateBefore3Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore2"/>"		binding="dateBefore2Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore1"/>"		binding="dateBefore1Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore0"/>"		binding="dateBefore0Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>


                <wj-flex-grid-column header="" 		binding="twoWeekTot" 		width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="" 		binding="twoWeekAvr" 		width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>

				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.inWhCarryOut"/>" 		binding="inWhCarryOut" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.outWhCarryIn"/>" 		binding="outWhCarryIn" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.hqCurrentStk"/>" 		binding="hqCurrentStk" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.sale"/>" 				binding="sale" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.storeTotStk"/>" 		binding="storeTotStk" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.saleRatio"/>" 		binding="saleRatio" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.firstSaleDate"/>" 	binding="firstSaleDate" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.lastSaleDate"/>" 		binding="lastSaleDate" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.saleNumberOfDays"/>" 	binding="saleNumberOfDays" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dayAvrSale"/>" 		binding="dayAvrSale" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.exhaustionOrg"/>" 	binding="exhaustionOrg" 	width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
				</c:if>
				<c:if test="${sessionInfo.orgnFg == 'STORE'}">
					<wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.inWh"/>" 				binding="inWh" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.storeCurrentStk"/>" 	binding="storeCurrentStk" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.sale"/>" 				binding="sale" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.saleRatio"/>" 		binding="saleRatio" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.firstSaleDate"/>" 	binding="firstSaleDate" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.lastSaleDate"/>" 		binding="lastSaleDate" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.saleNumberOfDays"/>" 	binding="saleNumberOfDays" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dayAvrSale"/>" 		binding="dayAvrSale" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.exhaustionOrg"/>" 	binding="exhaustionOrg" 	width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
				</c:if>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="saleTrnsitnBensonCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%-- 페이지 리스트 --%>
	    <div class="pageNum mt20">
	      <%-- id --%>
	      <ul id="saleTrnsitnBensonCtrlPager" data-size="10">
	      </ul>
	    </div>
        <%--//페이지 리스트--%>

	<%-- 엑셀 리스트 --%>
	<div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="saleTrnsitnBensonExcelCtrl">
      <div class="wj-gridWrap">
      	<wj-flex-grid	    id="saleTrnsitnBensonExcelGrid"
            				autoGenerateColumns="false"
			                control="excelFlex"
			                initialized="initGrid(s,e)"
			                sticky-headers="true"
			                selection-mode="Row"
			                items-source="data"
			                item-formatter="_itemFormatter"
			                is-read-only="true"
			                frozen-columns="5">

                <wj-flex-grid-column header="<s:message code="cmm.storeCd"/>" 					binding="storeCd"  			width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>" 					binding="storeNm" 			width="130" is-read-only="true" align="left"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.prodCd"/>" 			binding="prodCd"  			width="100" is-read-only="true" align="center" format="d"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.prodNm"/>" 			binding="prodNm" 			width="130" is-read-only="true" align="left"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.splyUprc"/>" 			binding="splyUprc" 			width="100" is-read-only="true" align="right"	></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore13"/>"		binding="dateBefore13Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore12"/>"		binding="dateBefore12Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore11"/>"		binding="dateBefore11Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore10"/>"		binding="dateBefore10Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore9"/>"		binding="dateBefore9Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore8"/>"		binding="dateBefore8Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore7"/>"		binding="dateBefore7Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore6"/>"		binding="dateBefore6Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore5"/>"		binding="dateBefore5Qty" 	width="100" is-read-only="true" align="right"	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore4"/>"		binding="dateBefore4Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore3"/>"		binding="dateBefore3Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore2"/>"		binding="dateBefore2Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore1"/>"		binding="dateBefore1Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dateBefore0"/>"		binding="dateBefore0Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>


                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.twoWeekTot"/>" 		binding="twoWeekTot" 		width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.twoWeekAvr"/>" 		binding="twoWeekAvr" 		width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>

				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.inWhCarryOut"/>" 		binding="inWhCarryOut" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.outWhCarryIn"/>" 		binding="outWhCarryIn" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.hqCurrentStk"/>" 		binding="hqCurrentStk" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.sale"/>" 				binding="sale" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.storeTotStk"/>" 		binding="storeTotStk" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.saleRatio"/>" 		binding="saleRatio" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.firstSaleDate"/>" 	binding="firstSaleDate" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.lastSaleDate"/>" 		binding="lastSaleDate" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.saleNumberOfDays"/>" 	binding="saleNumberOfDays" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dayAvrSale"/>" 		binding="dayAvrSale" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.exhaustionOrg"/>" 	binding="exhaustionOrg" 	width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
				</c:if>
				<c:if test="${sessionInfo.orgnFg == 'STORE'}">
					<wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.inWh"/>" 				binding="inWh" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.storeCurrentStk"/>" 	binding="storeCurrentStk" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.sale"/>" 				binding="sale" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.saleRatio"/>" 		binding="saleRatio" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.firstSaleDate"/>" 	binding="firstSaleDate" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.lastSaleDate"/>" 		binding="lastSaleDate" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.saleNumberOfDays"/>" 	binding="saleNumberOfDays" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.dayAvrSale"/>" 		binding="dayAvrSale" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitnBenson.exhaustionOrg"/>" 	binding="exhaustionOrg" 	width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
				</c:if>

            </wj-flex-grid>
	   </div>
	</div>
	<%--//엑셀 리스트--%>
    </div>
</div>

<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp"/><%-- 상품분류 팝업 --%>

<script type="text/javascript" src="/resource/solbipos/js/sale/benson/saleTrnsitnBenson/saleTrnsitnBenson.js?ver=20260910.02" charset="utf-8"></script>
