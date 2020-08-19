<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/sale/anals/versusPeriod/week/"/>

<div id="versusPeriodWeekView" class="subCon" style="display: none;">
	<div ng-controller="versusPeriodWeekCtrl">
		<div class="searchBar flddUnfld">
			<a href="#" class="open fl"><s:message code="versusPeriod.week"/></a>
	    	<%-- 조회 --%>
	    	<button class="btn_blue fr mt5 mr10" id="btnVersusPeriodWeekSearch" ng-click="_broadcast('versusPeriodWeekCtrlSrch')">
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
	                      id="srchWeekStartDate"
	                      value="srchWeekStartDate"
	                      ng-model="srchStartDate"
	                      control="startDateCombo"
	                      min="2000-01-01"
	                      max="2099-12-31"
	                      initialized="_initDateBox(s)"
	                      ng-change="changeDate()">
	              		</wj-input-date>
	              	</span>
                    <span class="rg">~</span>
	                <span class="txtIn w120px">
	                   	<wj-input-date
	                      id="srchWeekEndDate"
	                      value="srchWeekEndDate"
	                      ng-model="srchEndDate"
	                      control="endDateCombo"
	                      min="2000-01-01"
	                      max="2099-12-31"
	                      initialized="_initDateBox(s)"
	                      ng-change="changeDate()">
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
                             id="compWeekStartDate"
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
                             id="compWeekEndDate"
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
	             		<jsp:param name="targetId" value="versusPeriodWeekSelectStore"/>
	            	</jsp:include>
	              	<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
	          	</td>
	        </tr>
	      	</c:if>
	      	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
	        	<input type="hidden" id="versusPeriodWeekSelectStoreCd" value="${sessionInfo.storeCd}"/>
	      	</c:if>
			</tbody>
		</table>

		<div class="mt40 oh sb-select dkbr">
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="text" id="versusPeriodWeekSelectStoreStoreNum" ng-model="storeNum">
			</c:if>
			
			<div class="fr">
		    <%-- 차트 --%>
		    <button class="btn_skyblue" id="btnShowChart"><s:message code="cmm.chart" /></button>
		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" /></button>
			</div>
			<div class="clearfix"></div>
		</div>

		<%--위즈모 테이블--%>
	    <div class="w100 mt10">
	      <div class="wj-gridWrap" style="height: 350px;">
	        <wj-flex-grid
	          id="versusPeriodWeekGrid"
	          autoGenerateColumns="false"
	          control="flex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="versusPeriod.day"/>" 	binding="lvNm" 	width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.realSaleAmt"/>" 	binding="saleDateCntA" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtA" 	width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.totRealSaleAmt"/>" 	binding="saleCntA" 		width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.rat"/>" 		binding="ratA" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.realSaleAmt"/>" 	binding="saleDateCntB" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtB" 	width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.totRealSaleAmt"/>" 	binding="saleCntB" 		width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.rat"/>" binding="ratB" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 		binding="sinAmt" 		width="*" align="center" is-read-only="true" aggregate="Sum"  format="n2"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="sinCnt" 		width="*" align="center" is-read-only="true" aggregate="Sum" format="n2"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="versusPeriodWeekCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>
</div>

<%--layer:For Center screen--%>
<div class="fullDimmed versusPeriodWeekLayer" id="versusPeriodWeekMask" style="display: none"></div>
<div class="layer versusPeriodWeekLayer" id="versusPeriodWeekLayer" style="display: none; z-index:1499;">
    <div class="layer_inner">

        <%--layerContent--%>
        <div class="title" style="width:980px; padding:0">
            <p class="tit" id="versusPeriodWeekTitle" style="padding-left:20px"><s:message code="cmm.chart" /></p>
            <a href="#" class="btn_close _btnClose"></a>

            <%--위즈모 테이블--%>
		    <div class="w100" id="wjWrapType1" ng-controller="versusPeriodWeekChartCtrl">
		    	<div class="wj-gridWrap" style="display:none;" >
		    		<wj-flex-grid
						id="versusPeriodWeekChartGrid"
						autoGenerateColumns="false"
						selection-mode="Row"
						items-source="data"
						control="flex"
						is-read-only="true"
						item-formatter="_itemFormatter">
						<!-- define columns -->
						<wj-flex-grid-column header="<s:message code="versusPeriod.category"/>"		binding="searchName"	width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
						<wj-flex-grid-column header="<s:message code="pos.realSaleAmtMon"/>"		binding="realSaleAmtA"	width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
						<wj-flex-grid-column header="<s:message code="pos.realSaleAmtTue"/>"		binding="realSaleAmtB"	width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
					</wj-flex-grid>
				</div>

				<div class="mt20 oh sb-select dkbr pd10">
					<!-- 막대 차트 샘플 -->
					<div>
						<wj-flex-chart
							id="versusPeriodWeekBarChart"
							name="barChart1"
							class="custom-flex-chart"
							initialized="initChart(s,e)"
							items-source="data"
							binding-x="searchName">

							<wj-flex-chart-series name="<s:message code="pos.realSaleAmtSrch"/>" binding="realSaleAmtA"></wj-flex-chart-series>
							<wj-flex-chart-series name="<s:message code="pos.realSaleAmtVs"/>" binding="realSaleAmtB"></wj-flex-chart-series>

						</wj-flex-chart>
					</div>
				</div>
			</div>
               <%--//위즈모 테이블--%>
        </div>

    </div>
    <%--//layerContent--%>
</div>
<%--//layer:For Center screen--%>

<script>
$(document).ready(function() {

    $("#btnShowChart").click(function(e) {
        $("div.versusPeriodWeekLayer").show();
        $("#btnVersusPeriodWeekSearch").click();
    });

    $("._btnClose").click(function(e) {
        $("div.versusPeriodWeekLayer").hide();
    });

});
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/anals/versusPeriod/week/versusPeriodWeek.js?ver=20190125.03" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<%-- <c:import url="/WEB-INF/view/base/prod/prod/prodModifyView.jsp"> --%>
<%--   <c:param name="menuCd" value="${menuCd}"/> --%>
<%--   <c:param name="menuNm" value="${menuNm}"/> --%>
<%-- </c:import> --%>

