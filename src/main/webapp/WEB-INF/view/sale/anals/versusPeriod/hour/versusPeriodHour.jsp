<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/sale/anals/versusPeriod/hour/"/>

<div id="versusPeriodHourView" class="subCon" style="display: none;">
	<div ng-controller="versusPeriodHourCtrl">
		<div class="searchBar flddUnfld">
			<a href="#" class="open fl"><s:message code="versusPeriod.hour"/></a>
	    	<%-- 조회 --%>
	    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('versusPeriodHourCtrlSrch')">
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
	          		<span class="txtIn w120px">
	         	    	<wj-input-date
	                     id="srchStartDate"
	                     value="srchStartDate"
	                     ng-model="srchStartDate"
	                     control="startDateCombo"
	                     min="2000-01-01"
	                     max="2099-12-31"
	                     initialized="_initDateBox(s)">
	             		</wj-input-date>
	             	</span>
                    <span class="rg">~</span>
	                <span class="txtIn w120px">
	                    <wj-input-date
	                      id="srchEndDate"
	                      value="srchEndDate"
	                      ng-model="srchEndDate"
	                      control="endDateCombo"
	                      min="2000-01-01"
	                      max="2099-12-31"
	                      initialized="_initDateBox(s)">
	             	 	</wj-input-date>
	             	</span>
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
	          	    <span class="txtIn w120px">
	          	        <wj-input-date
	                         id="compHourStartDate"
	                         value="compStartDate"
	                         ng-model="compStartDate"
	                         control="compStartDateCombo"
	                         min="2000-01-01"
	                         max="2099-12-31"
	                         initialized="_initDateBox(s)">
                        </wj-input-date>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn w120px">
                        <wj-input-date
                             id="compHourEndDate"
                             value="compEndDate"
                             ng-model="compEndDate"
                             control="compEndDateCombo"
                             min="2000-01-01"
                             max="2099-12-31"
                             initialized="_initDateBox(s)">
                        </wj-input-date>
                    </span>
	            	<%-- <span class="chk ml10">
						<input type="checkbox" ng-model="isCheckedComp" ng-change="isChkDtComp()" />
		              	<label for="chkDt">
	                		<s:message code="cmm.all.day" />
	              		</label>
	            	</span> --%>
	          	</div>
	        	</td>
			</tr>
	      	<c:if test="${sessionInfo.orgnFg == 'HQ'}">
	        <tr>
	            <%-- 매장코드 --%>
	          	<th><s:message code="todayBillSaleDtl.store"/></th>
	          	<td>
	            	<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
	             		<jsp:param name="targetId" value="versusPeriodHourSelectStore"/>
	            	</jsp:include>
	              	<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
	          	</td>
	        </tr>
	      	</c:if>
	      	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
	        	<input type="hidden" id="versusPeriodHourSelectStoreCd" value="${sessionInfo.storeCd}"/>
	      	</c:if>
			</tbody>
		</table>

		<div class="mt40 oh sb-select dkbr">
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="text" id="versusPeriodHourSelectStoreStoreNum" ng-model="storeNum">
			</c:if>

			<div class="fr">
				<%-- 차트  --%>
				<button class="btn_skyblue" ng-click="showChart()"><s:message code="cmm.chart" /></button>
				<%-- 엑셀 다운로드 //TODO --%>
			  <button class="btn_skyblue" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" /></button>
		    </div>
		  <div class="clearfix"></div>
		</div>

		<%--위즈모 테이블--%>
	    <div class="w100 mt10">
	      <div class="wj-gridWrap" style="height: 350px;">
	        <wj-flex-grid
	          id="versusPeriodHourGrid"
	          autoGenerateColumns="false"
	          control="flex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="versusPeriod.lv"/>" 	binding="lv" 		width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtA" 		width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" binding="saleCntA" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtB" 	width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="saleCntB" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 		binding="sinAmt" 		width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true" format="n2"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="sinCnt" 		width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true" format="n2"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="versusPeriodHourCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>

	    <%--layer:For Center screen--%>
		<div class="fullDimmed versusPeriodHourLayer" id="versusPeriodHourMask" style="display: none"></div>
		<div class="layer versusPeriodHourLayer" id="versusPeriodHourLayer" style="display: none; z-index:1499;">
		    <div class="layer_inner">

		        <%--layerContent--%>
		        <div class="title" style="width:980px; padding:0"  ng-controller="versusPeriodHourChartCtrl">
		            <p class="tit" id="tblAttrTitle" style="padding-left:20px"><s:message code="cmm.chart" /></p>
		            <a href="#" class="btn_close _btnClose"></a>

		            <%--위즈모 테이블--%>
				    <div class="w100 mt10" id="wjWrapType1">
							<!-- 막대 차트 샘플 -->
							<div>
								<wj-flex-chart
									id="versusPeriodHourBarChart"
									name="barChart1"
									class="custom-flex-chart"
									initialized="initChart(s,e)"
									items-source="data"
									binding-x="lv">

									<wj-flex-chart-series name="<s:message code="pos.realSaleAmtSrch"/>" binding="realSaleAmtA"></wj-flex-chart-series>
									<wj-flex-chart-series name="<s:message code="pos.realSaleAmtVs"/>" binding="realSaleAmtB"></wj-flex-chart-series>
								</wj-flex-chart>
						</div>
					</div>
		               <%--//위즈모 테이블--%>
		        </div>

		    </div>
		    <%--//layerContent--%>
		</div>
		<%--//layer:For Center screen--%>
	</div>
</div>
<script>
    $(document).ready(function() {

        $("._btnClose").click(function(e) {
            $("div.versusPeriodHourLayer").hide();
        });

    });
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/anals/versusPeriod/hour/versusPeriodHour.js?ver=20190125.02" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<%-- <c:import url="/WEB-INF/view/base/prod/prod/prodModifyView.jsp"> --%>
<%--   <c:param name="menuCd" value="${menuCd}"/> --%>
<%--   <c:param name="menuNm" value="${menuNm}"/> --%>
<%-- </c:import> --%>

