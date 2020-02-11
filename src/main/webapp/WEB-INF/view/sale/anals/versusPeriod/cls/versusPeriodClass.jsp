<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/anals/versusPeriod/cls/"/>

<div id="versusPeriodClassView" class="subCon" style="display: none;">
	<div ng-controller="versusPeriodClassCtrl">
		<div class="searchBar flddUnfld">
			<a href="#" class="open fl"><s:message code="versusPeriod.cls"/></a>
	    	<%-- 조회 --%>
	    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('versusPeriodClassCtrl')">
	    		<s:message code="cmm.search"/>
	    	</button>
		</div>
	    <%-- 조회조건 --%>
	    <table class="searchTbl">
			<colgroup>
	        	<col class="w13"/>
		        <col class="w37"/>
		        <col class="w13"/>
		        <col class="w37"/>
	      	</colgroup>
	      	<tbody>
	       	<%-- 조회일자 --%>
			<tr>
		    	<th><s:message code="cmm.search.date" /></th>
	        	<td colspan="3">
	          	<div class="sb-select">
	          	    <span class="txtIn"><input id="srchClassStartDate" class="w120px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchClassEndDate" class="w120px"></span>
	            	<%-- <span class="chk ml10">
						<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
		              	<label for="chkDt">
	                		<s:message code="cmm.all.day" />
	              		</label>
	            	</span> --%>
	          	</div>
	        	</td>
			</tr>
			<%-- 대비일자 --%>
			<tr>
		    	<th><s:message code="versusPeriod.compDate" /></th>
	        	<td colspan="3">
	          	<div class="sb-select">
	          	    <span class="txtIn"><input id="compClassStartDate" class="w120px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="compClassEndDate" class="w120px"></span>
	            	<%-- <span class="chk ml10">
						<input type="checkbox" ng-model="isCheckedComp" ng-change="isChkDtComp()" />
		              	<label for="chkDt">
	                		<s:message code="cmm.all.day" />
	              		</label>
	            	</span> --%>
	          	</div>
	        	</td>
			</tr>
	        <tr>
	        	<%-- 브랜드코드 --%>
        		<th><s:message code="versusPeriod.brandCd" /></th>
        		<td>
                <div class="sb-select fl w200px">
                    <wj-combo-box
                            id="brandCd"
                            ng-model="brandCd"
                            items-source="_getComboData('brandCd')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	            <%-- 매장코드 --%>
	          	<th><s:message code="todayBillSaleDtl.store"/></th>
	          	<td>
	            	<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
	             		<jsp:param name="targetId" value="versusPeriodClassSelectStore"/>
	            	</jsp:include>
	              	<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
	          	</c:if>
	          	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
	        		<input type="hidden" id="versusPeriodClassSelectStoreCd" value="${sessionInfo.storeCd}"/>
	      		</c:if>
	          	</td>
	        </tr>
			</tbody>
		</table>

		<div class="mt40 oh sb-select dkbr">
		    <%-- 페이지 스케일  --%>
		    <wj-combo-box
		      class="w100px fl"
		      id="versusPeriodClasslistScaleBox"
		      ng-model="listScale"
		      control="listScaleCombo"
		      items-source="_getComboData('versusPeriodClasslistScaleBox')"
		      display-member-path="name"
		      selected-value-path="value"
		      is-editable="false"
		      initialized="_initComboBox(s)">
		    </wj-combo-box>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="text" id="versusPeriodClassSelectStoreStoreNum" ng-model="storeNum">
			</c:if>
		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" />
		    </button>
		</div>



		<%--위즈모 테이블--%>
	    <div class="w100 mt10">
	      <div class="wj-gridWrap" style="height: 350px;">
	        <wj-flex-grid
	          id="versusPeriodClassGrid"
	          loaded-rows="loadedRows(s,e)"
	          autoGenerateColumns="false"
	          control="flex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="versusPeriod.first"/>" 	binding="lv1Nm" 		width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.second"/>" 	binding="lv2Nm" 		width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.category"/>" 	binding="lv3Cd" 		width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.third"/>" 	binding="lv3Nm" 		width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.realSaleAmt2"/>" 	binding="realSaleAmtA" 		width="*" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="saleCntA" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.realSaleAmt2"/>" 	binding="realSaleAmtB" 	width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="saleCntB" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.realSaleAmt2"/>" 		binding="sinAmt" 		width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="sinCnt" 		width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="versusPeriodClassCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>
	</div>

	<div ng-controller="versusPeriodClassDtlCtrl">

		<div class="mt40 oh sb-select dkbr">
		    <%-- 페이지 스케일  --%>
		    <wj-combo-box
		      class="w100px fl"
		      id="versusPeriodClassDtllistScaleBox"
		      ng-model="listScale"
		      control="listScaleCombo"
		      items-source="_getComboData('versusPeriodClassDtllistScaleBox')"
		      display-member-path="name"
		      selected-value-path="value"
		      is-editable="false"
		      initialized="_initComboBox(s)">
		    </wj-combo-box>

		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" />
		    </button>
		</div>

		<%--위즈모 테이블--%>
	    <div class="w100 mt10">
	      <div class="wj-gridWrap" style="height: 350px;">
	        <wj-flex-grid
	          id="versusPeriodClassDtlGrid"
	          autoGenerateColumns="false"
	          control="flex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="versusPeriod.prod"/>" 	binding="prodNm" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtA" 	width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="saleCntA" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtB" 		width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" binding="saleCntB" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 		binding="sinAmt" 		width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="sinCnt" 		width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="versusPeriodClassDtlCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>

	  <%-- 페이지 리스트 --%>
	  <div class="pageNum mt20">
	    <%-- id --%>
	    <ul id="versusPeriodClassDtlCtrlPager" data-size="10">
	    </ul>
	  </div>
	  <%--//페이지 리스트--%>
	</div>

</div>


<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/anals/versusPeriod/cls/versusPeriodClass.js?ver=20190125.02" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<%-- <c:import url="/WEB-INF/view/base/prod/prod/prodModifyView.jsp"> --%>
<%--   <c:param name="menuCd" value="${menuCd}"/> --%>
<%--   <c:param name="menuNm" value="${menuNm}"/> --%>
<%-- </c:import> --%>

