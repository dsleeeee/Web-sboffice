<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}" />
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}" />
<c:set var="baseUrl" value="/sale/anals/store/" />

<div id="storeBrandView" class="subCon" ng-controller="storeBrandCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="store.brand" /></a>
		<%-- 조회 --%>
		<button class="btn_blue fr mt5 mr10" id="btnStoreBrandSearch" ng-click="_broadcast('storeBrandCtrlSrch')">
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
						<span class="txtIn"><input id="srchStoreBrandStartDate" class="w120px"></span>
						<span class="rg">~</span>
						<span class="txtIn"><input id="srchStoreBrandEndDate" class="w120px"></span>
						<span class="chk ml10">
							<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
							<label for="chkDt"><s:message code="cmm.all.day" /></label>
						</span>
					</div>
				</td>
			</tr>

            <tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	            <%-- 매장코드 --%>
	            <th><s:message code="todayBillSaleDtl.store"/></th>
	            <td >
	                <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
	                    <jsp:param name="targetId" value="storeBrandSelectStore"/>
	                </jsp:include>
	                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
	            </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="storeBrandSelectStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            <%-- 정렬구분선택 --%>
          <%--   <th><s:message code="store.sortFg" /></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                        <wj-combo-box id="srchStoreFgDisplay" ng-model="storeBrand"
                            items-source="_getComboData('srchStoreFgDisplay')"
                            display-member-path="name" selected-value-path="value"
                            is-editable="false" initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td> --%>
            </tr>

		</tbody>
	</table>

	<div class="mt40 oh sb-select dkbr">
		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadStoreBrand()"><s:message code="cmm.excel.down" />
		    </button>
		</div>

		<%--위즈모 테이블--%>
	    <div class="w100 mt10">
	      <div class="wj-gridWrap" style="height: 350px;">
	        <wj-flex-grid
	          id="storeBrandGrid"
	          autoGenerateColumns="false"
	          control="flex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="store.hqBrandNm"/>"  binding="hqBrandNm"       width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="store.storeNm"/>" 	binding="storeNm" 		width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="store.totSaleAmt"/>" 	binding="totSaleAmt" 		width="*" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="store.totDcAmt"/>" binding="totDcAmt" 	width="*" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="store.realSaleAmt"/>" 	binding="realSaleAmt" 	width="*" align="right" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="store.saleCnt"/>" 	binding="saleCnt" 	width="*" align="center" is-read-only="true" aggregate="Sum" ></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="store.ratRealSaleAmt"/>" 		binding="ratRealSaleAmt" 		width="*" align="center" is-read-only="true" aggregate="Sum" format="n2"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="store.ratCnt"/>" 	binding="ratCnt" 		width="*" align="center" is-read-only="true" aggregate="Sum" format="n2"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="storeBrandCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>

	</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/store/brand/storeBrand.js?ver=20190125.02" charset="utf-8"></script>

<%-- 상품매출내역 팝업 상세 레이어 --%>
<%-- <c:import url="/WEB-INF/view/sale/com/popup/prod.jsp">
	<c:param name="menuCd" value="${menuCd}" />
	<c:param name="menuNm" value="${menuNm}" />
</c:import> --%>