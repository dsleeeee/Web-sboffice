<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="gvOrgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon3" ng-controller="saleTrnsitnCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('saleTrnsitnCtrlSrch')">
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
				<th><s:message code="prod.prodCd" /></th>		<%-- 상품코드 --%>
				<td><input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCdModel" /></td>
				<th><s:message code="prod.prodNm" /></th>		<%-- 상품명 --%>
				<td><input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNmModel" /></td>
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

			<%--
            <tr>
                <th>TEST용 <s:message code="cmm.search.date"/></th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="baseDate" name="baseDate" class="w200px" /></span>
                    </div>
                </td>
            </tr>

            <tr>
                <th>
                    <s:message code="periodMembr.srchDate" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
                    </div>
                </td>
            </tr>
            --%>
            <tr style="display: none;">
                <th>
                    TEST용 <s:message code="cmm.search.date" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span> <!-- ng-model="baseDate" -->
                        (2019.06.30 1건 & 2019.06.20 이전 다건 )
                        <%--
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" readonly /></span>
                        --%>
                    </div>
                </td>
            </tr>


        </tbody>
    </table>


    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
          class="w100px fl"
          id="saleTrnsitnlistScaleBox"
          ng-model="saleTrnsitnlistScale"
          items-source="_getComboData('saleTrnsitnlistScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          initialized="_initComboBox(s)"
          control="conListScale"
	      is-editable="true"
		  text-changed="_checkValidation(s)">
        </wj-combo-box>
    
    	<button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>	<%-- 엑셀 다운로드 //TODO --%>
	</div>


    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap2" style="overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid	#saleTrnsitnGrid
            				autoGenerateColumns="false"
			                control="flex"
			                initialized="initGrid(s,e)"
			                sticky-headers="true"
			                selection-mode="Row"
			                items-source="data"
			                item-formatter="_itemFormatter"
			                is-read-only="true"
			                frozen-columns="3">

                <wj-flex-grid-column header="<s:message code="saleTrnsitn.prodCd"/>" 			binding="prodCd"  			width="100" is-read-only="true" align="center" format="d"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.prodNm"/>" 			binding="prodNm" 			width="130" is-read-only="true" align="left"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.splyUprc"/>" 			binding="splyUprc" 			width="100" is-read-only="true" align="right"	></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore13"/>"		binding="dateBefore13Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore12"/>"		binding="dateBefore12Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore11"/>"		binding="dateBefore11Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore10"/>"		binding="dateBefore10Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore9"/>"		binding="dateBefore9Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore8"/>"		binding="dateBefore8Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore7"/>"		binding="dateBefore7Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore6"/>"		binding="dateBefore6Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore5"/>"		binding="dateBefore5Qty" 	width="100" is-read-only="true" align="right"	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore4"/>"		binding="dateBefore4Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore3"/>"		binding="dateBefore3Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore2"/>"		binding="dateBefore2Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore1"/>"		binding="dateBefore1Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore0"/>"		binding="dateBefore0Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>


                <wj-flex-grid-column header="<s:message code="saleTrnsitn.twoWeekTot"/>" 		binding="twoWeekTot" 		width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.twoWeekAvr"/>" 		binding="twoWeekAvr" 		width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>

				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.inWhCarryOut"/>" 		binding="inWhCarryOut" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.outWhCarryIn"/>" 		binding="outWhCarryIn" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.hqCurrentStk"/>" 		binding="hqCurrentStk" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.sale"/>" 				binding="sale" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.storeTotStk"/>" 		binding="storeTotStk" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.saleRatio"/>" 		binding="saleRatio" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.firstSaleDate"/>" 	binding="firstSaleDate" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.lastSaleDate"/>" 		binding="lastSaleDate" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.saleNumberOfDays"/>" 	binding="saleNumberOfDays" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dayAvrSale"/>" 		binding="dayAvrSale" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.exhaustionOrg"/>" 	binding="exhaustionOrg" 	width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
				</c:if>
				<c:if test="${sessionInfo.orgnFg == 'STORE'}">
					<wj-flex-grid-column header="<s:message code="saleTrnsitn.inWh"/>" 				binding="inWh" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.storeCurrentStk"/>" 	binding="storeCurrentStk" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.sale"/>" 				binding="sale" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.saleRatio"/>" 		binding="saleRatio" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.firstSaleDate"/>" 	binding="firstSaleDate" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.lastSaleDate"/>" 		binding="lastSaleDate" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.saleNumberOfDays"/>" 	binding="saleNumberOfDays" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dayAvrSale"/>" 		binding="dayAvrSale" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.exhaustionOrg"/>" 	binding="exhaustionOrg" 	width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
				</c:if>
				
				<%-- Header의 날짜 setting을 위한 값들 --%>
				<wj-flex-grid-column header="" binding="dateBefore13" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore12" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore11" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore10"	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore9" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore8" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore7" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore6"	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore5" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore4" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore3" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore2"	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore1"	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore0" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="saleTrnsitnCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%-- 페이지 리스트 --%>
	    <div class="pageNum mt20">
	      <%-- id --%>
	      <ul id="saleTrnsitnCtrlPager" data-size="10">
	      </ul>
	    </div>
        <%--//페이지 리스트--%>
        
	<%-- 엑셀 리스트 --%>
	<div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="saleTrnsitnExcelCtrl">
      <div class="wj-gridWrap">
      	<wj-flex-grid	    id="saleTrnsitnExcelGrid"
            				autoGenerateColumns="false"
			                control="excelFlex"
			                initialized="initGrid(s,e)"
			                sticky-headers="true"
			                selection-mode="Row"
			                items-source="data"
			                item-formatter="_itemFormatter"
			                is-read-only="true"
			                frozen-columns="3">

                <wj-flex-grid-column header="<s:message code="saleTrnsitn.prodCd"/>" 			binding="prodCd"  			width="100" is-read-only="true" align="center" format="d"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.prodNm"/>" 			binding="prodNm" 			width="130" is-read-only="true" align="left"	></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.splyUprc"/>" 			binding="splyUprc" 			width="100" is-read-only="true" align="right"	></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore13"/>"		binding="dateBefore13Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore12"/>"		binding="dateBefore12Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore11"/>"		binding="dateBefore11Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore10"/>"		binding="dateBefore10Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore9"/>"		binding="dateBefore9Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore8"/>"		binding="dateBefore8Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore7"/>"		binding="dateBefore7Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore6"/>"		binding="dateBefore6Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore5"/>"		binding="dateBefore5Qty" 	width="100" is-read-only="true" align="right"	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore4"/>"		binding="dateBefore4Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore3"/>"		binding="dateBefore3Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore2"/>"		binding="dateBefore2Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore1"/>"		binding="dateBefore1Qty"	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dateBefore0"/>"		binding="dateBefore0Qty" 	width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>


                <wj-flex-grid-column header="<s:message code="saleTrnsitn.twoWeekTot"/>" 		binding="twoWeekTot" 		width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleTrnsitn.twoWeekAvr"/>" 		binding="twoWeekAvr" 		width="100" is-read-only="true" align="right" 	aggregate="Sum"></wj-flex-grid-column>

				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.inWhCarryOut"/>" 		binding="inWhCarryOut" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.outWhCarryIn"/>" 		binding="outWhCarryIn" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.hqCurrentStk"/>" 		binding="hqCurrentStk" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.sale"/>" 				binding="sale" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.storeTotStk"/>" 		binding="storeTotStk" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.saleRatio"/>" 		binding="saleRatio" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.firstSaleDate"/>" 	binding="firstSaleDate" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.lastSaleDate"/>" 		binding="lastSaleDate" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.saleNumberOfDays"/>" 	binding="saleNumberOfDays" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dayAvrSale"/>" 		binding="dayAvrSale" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.exhaustionOrg"/>" 	binding="exhaustionOrg" 	width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
				</c:if>
				<c:if test="${sessionInfo.orgnFg == 'STORE'}">
					<wj-flex-grid-column header="<s:message code="saleTrnsitn.inWh"/>" 				binding="inWh" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.storeCurrentStk"/>" 	binding="storeCurrentStk" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.sale"/>" 				binding="sale" 				width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.saleRatio"/>" 		binding="saleRatio" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.firstSaleDate"/>" 	binding="firstSaleDate" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.lastSaleDate"/>" 		binding="lastSaleDate" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.saleNumberOfDays"/>" 	binding="saleNumberOfDays" 	width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.dayAvrSale"/>" 		binding="dayAvrSale" 		width="100" is-read-only="true" align="right" 	></wj-flex-grid-column>
	                <wj-flex-grid-column header="<s:message code="saleTrnsitn.exhaustionOrg"/>" 	binding="exhaustionOrg" 	width="100" is-read-only="true" align="center"	></wj-flex-grid-column>
				</c:if>

				<%-- Header의 날짜 setting을 위한 값들 --%>
				<wj-flex-grid-column header="" binding="dateBefore13" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore12" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore11" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore10"	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore9" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore8" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore7" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore6"	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore5" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore4" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore3" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore2"	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore1"	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
				<wj-flex-grid-column header="" binding="dateBefore0" 	width="10" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
	   </div>
	</div>
	<%--//엑셀 리스트--%>
    </div>
</div>

<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp"/><%-- 상품분류 팝업 --%>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/saleTrnsitn/saleTrnsitn.js?ver=20200131.01" charset="utf-8"></script>
