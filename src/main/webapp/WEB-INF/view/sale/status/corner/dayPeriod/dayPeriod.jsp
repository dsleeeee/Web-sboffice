<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd"	value="${sessionScope.sessionInfo.currentMenu.resrceCd}" />
<c:set var="menuNm"	value="${sessionScope.sessionInfo.currentMenu.resrceNm}" />

<div id="cornerDayPeriodView" class="subCon" style="display: none;" ng-controller="cornerDayPeriodCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="corner.dayPeriod" /></a>
		<%-- 조회 --%>
		<button class="btn_blue fr mt5 mr10" id="btnCornerDayPeriodSearch" ng-click="_broadcast('cornerDayPeriodMainCtrlSrch')">
			<s:message code="cmm.search" />
		</button>
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
				<th><s:message code="cmm.search.date" /></th>
				<td>
					<div class="sb-select">
						<span class="txtIn">
							<input id="srchCornerDayPeriodStartDate" class="w120px"/>
						</span>
						<span class="rg">~</span>
						<span class="txtIn">
							<input id="srchCornerDayPeriodEndDate" class="w120px"/>
						</span>
						<span class="chk ml10">
							<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
							<label for="chkDt">
								<s:message code="cmm.all.day" />
							</label>
						</span>
					</div>
				</td>
				<%-- 조회옵션 --%>
				<th><s:message code="periodIostock.srchOption" /></th>
				<td>
		          	<span class="chk ml10">
						<input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
		              	<label for="chkDt">
	                		<s:message code="periodIostock.prodClassDisplay" />
	              		</label>
	            	</span>
				</td>
			</tr>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="hidden" id="cornerDayPeriodSelectStoreCd" value="" />
				<tr>
				<%-- 매장코드 --%>
				<th><s:message code="todayBillSaleDtl.store" /></th>
				<td colspan="3">
					<%-- 매장선택 모듈 싱글 선택 사용시 include
						param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                           displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                           modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                           closeFunc - 팝업 닫기시 호출할 함수            --%>
                       <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreS.jsp" flush="true">
						<jsp:param name="targetId" value="cornerDayPeriodSelectStore" />
						<jsp:param name="targetCornerId" value="cornerDayPeriodSelectCorner" />
						<jsp:param name="closeFunc" value="closeSelectStore" />
					</jsp:include> <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
				</td>
				</tr>
			</c:if>
			<c:if test="${sessionInfo.orgnFg == 'STORE'}">
				<input type="hidden" id="cornerDayPeriodSelectStoreCd" value="${sessionInfo.storeCd}" />
			</c:if>
			<input type="hidden" id="cornerDayPeriodSelectCornerCd" value="" />
			<input type="hidden" id="cornerDayPeriodSelectCornerName" value="" />
			<tr>
				<%-- 코너표시 --%>
				<th><s:message code="corner.cornrDisplay" /></th>
				<td colspan="3">
					<jsp:include page="/WEB-INF/view/sale/com/popup/selectCornerM.jsp" flush="true">
						<jsp:param name="targetId" value="cornerDayPeriodSelectCorner" />
						<jsp:param name="targetStoreId" value="cornerDayPeriodSelectStore" />
						<jsp:param name="closeFunc" value="closeSelectCorner" />
					</jsp:include>
				</td>
			</tr>
		</tbody>
	</table>

	<div id="gridRepresent" class="w50 fl" style="width: 49%;">
		<%-- 코너별 --%>
		<div class="w100 mt20" ng-controller="cornerDayPeriodMainCtrl">
			<div class="oh sb-select mb10">
				<%-- 페이지 스케일  --%>
				<wj-combo-box
				    class="w100px fl"
				    id="cornerDayPeriodListScaleBox"
					ng-model="cornerDayPeriodListScale"
					items-source="_getComboData('cornerDayPeriodListScaleBox')"
					display-member-path="name"
					selected-value-path="value"
					initialized="initComboBox(s)"
					control="listScaleCombo"
                    is-editable="true"
                    text-changed="_checkValidation(s)">
				</wj-combo-box>
				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
					<input type="text" id="cornerDayPeriodSelectStoreStoreNum" ng-model="storeNum" class="inputTxt" />
				</c:if>

				<%-- 코너별 매출 엑셀다운로드 --%>
				<button class="btn_skyblue fr" ng-click="excelDownloadDayPeriod()">
					<s:message code="cmm.excel.down" />
				</button>
			</div>
			<%--위즈모 테이블--%>
		    <div class="w100 mt10" id="wjWrapType1">
		        <div class="wj-gridWrap col2-t2">
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
					<wj-flex-grid-column header="<s:message code="corner.cornr"/>" binding="cornrCd" 	width="250" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="corner.cornrNm"/>" binding="cornrNm" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="corner.saleQty"/>" binding="totSaleQty" width="150" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="corner.realSaleAmt"/>" binding="realSaleAmt" width="150" align="right" is-read-only="true" aggregate="Sum" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
				</wj-flex-grid>
				<%-- ColumnPicker 사용시 include --%>
				<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
					<jsp:param name="pickerTarget" value="cornerDayPeriodMainCtrl" />
				</jsp:include>
				<%--// ColumnPicker 사용시 include --%>
			 </div>
			</div>
			<%--//위즈모 테이블--%>
			<%-- 페이지 리스트 --%>
			<div class="pageNum3 mt20">
				<%-- id --%>
				<ul id="cornerDayPeriodMainCtrlPager" data-size="10"></ul>
			</div>
			<%--//페이지 리스트--%>
		</div>
	</div>

	<div id="gridDetail" class="w50 fr" style="width: 49%;">
		<%-- 코너별 --%>
		<div class="w100 mt20" ng-controller="cornerDayPeriodDtlCtrl">
			<div class="oh sb-select mb10">
				<%--          <span class="fl bk lh30"><s:message code='corner.SaleDtl'/></span> --%>
				<%-- 페이지 스케일  --%>
				<wj-combo-box
				    class="w100px fl"
				    id="cornerDayPeriodDtlListScaleBox"
					ng-model="cornerDayPeriodDtlListScale"
					items-source="_getComboData('cornerDayPeriodDtlListScaleBox')"
					display-member-path="name"
					selected-value-path="value"
					initialized="initComboBox(s)"
					control="listScaleCombo"
                    is-editable="true"
                    text-changed="_checkValidation(s)">
			    </wj-combo-box>
				<c:if test="${sessionInfo.orgnFg == 'HQ'}">
					<input type="text" id="cornerDayPeriodDtlSelectStoreStoreNum" ng-model="storeNum" class="inputTxt" />
				</c:if>

				<%-- 코너별 매출 상세 엑셀다운로드 --%>
				<button class="btn_skyblue fr" ng-click="excelDownloadDayPeriodDtl()">
					<s:message code="cmm.excel.down" />
				</button>
			</div>
			<%--위즈모 테이블--%>
		    <div class="w100 mt10" id="wjWrapType1">
		        <div class="wj-gridWrap col2-t2">
				<wj-flex-grid
				    autoGenerateColumns="false"
                    selection-mode="Row"
					items-source="data"
					control="flex"
					initialized="initGrid(s,e)"
					is-read-only="false"
					item-formatter="_itemFormatter">

					<!-- define columns -->
					<wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          			<wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          			<wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="corner.prodCd"/>" binding="prodCd" width="200" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="corner.prodNm"/>"	binding="prodNm" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="corner.saleQty"/>" binding="totSaleQty" width="100" align="center" is-read-only="true" aggregate="Sum" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
					<wj-flex-grid-column header="<s:message code="corner.realSaleAmt"/>" binding="realSaleAmt" width="150" align="right" is-read-only="true" aggregate="Sum" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
				</wj-flex-grid>
				<%-- ColumnPicker 사용시 include --%>
				<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
					<jsp:param name="pickerTarget" value="cornerDayPeriodDtlCtrl" />
				</jsp:include>
				<%--// ColumnPicker 사용시 include --%>
			  </div>
			</div>
			<%--//위즈모 테이블--%>
			<%-- 페이지 리스트 --%>
			<div class="pageNum3 mt20">
				<%-- id --%>
				<ul id="cornerDayPeriodDtlCtrlPager" data-size="10"></ul>
			</div>
			<%--//페이지 리스트--%>
		</div>
	</div>
	
    <%--엑셀 리스트1--%>
    <div class="w100 mt10" id="wjWrapType1" style="display: none" ng-controller="cornerDayPeriodExcelCtrl">
        <div class="wj-gridWrap col2-t2">
        <wj-flex-grid
            id="cornerDayPeriodExcelGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="excelFlex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="corner.cornr"/>" binding="cornrCd"    width="250" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="corner.cornrNm"/>" binding="cornrNm" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="corner.saleQty"/>" binding="totSaleQty" width="150" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="corner.realSaleAmt"/>" binding="realSaleAmt" width="150" align="right" is-read-only="true" aggregate="Sum" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
        </wj-flex-grid>
     </div>
    </div>
    
    <%--엑셀 리스트2--%>
    <div class="w100 mt10" id="wjWrapType1" style="display: none" ng-controller="cornerDayPeriodDtlExcelCtrl">
        <div class="wj-gridWrap col2-t2">
        <wj-flex-grid
            id="cornerDayPeriodDtlExcelGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="excelFlex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>"     binding="lv1Nm"         width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>"     binding="lv2Nm"         width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>"     binding="lv3Nm"         width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="corner.prodCd"/>" binding="prodCd" width="200" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="corner.prodNm"/>" binding="prodNm" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="corner.saleQty"/>" binding="totSaleQty" width="100" align="center" is-read-only="true" aggregate="Sum" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="corner.realSaleAmt"/>" binding="realSaleAmt" width="150" align="right" is-read-only="true" aggregate="Sum" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/corner/dayPeriod/dayPeriod.js?ver=20190125.05" charset="utf-8"></script>
