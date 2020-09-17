<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/sale/anals/versusPeriod/cls/"/>

<div id="versusPeriodClassView" class="subCon" style="display: none;">
	<div ng-controller="versusPeriodClassCtrl">
		<div class="searchBar flddUnfld">
			<a href="#" class="open fl"><s:message code="versusPeriod.cls"/></a>
	    	<%-- 조회 --%>
	    	<button class="btn_blue fr mt5 mr10" id="btnVersusPeriodClassSearch" ng-click="_broadcast('versusPeriodClassCtrlSrch')">
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
		                      id="srchClassStartDate"
		                      value="srchClassStartDate"
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
		                      id="srchClassEndDate"
		                      value="srchClassEndDate"
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
	            	</span>
	          	</div> --%>
	        	</td>
			</tr>
			<%-- 대비일자 --%>
			<tr>
		    	<th><s:message code="versusPeriod.compDate" /></th>
	        	<td colspan="3">
	          	<div class="sb-select">
	          	    <span class="txtIn w120px">
                        <wj-input-date
                             id="compClassStartDate"
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
                             id="compClassEndDate"
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
	        <tr>
	        	<%-- 브랜드코드 --%>
        		<th><s:message code="versusPeriod.brandCd" /></th>
        		<td>
                <div class="sb-select fl w200px">
                    <wj-combo-box
                            id="brandCd"
                            ng-model="brandCdModel"
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
	            </td>
	          	</c:if>
	          	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
	        		<input type="hidden" id="versusPeriodClassSelectStoreCd" value="${sessionInfo.storeCd}"/>
	      		</c:if>

	        </tr>
			</tbody>
		</table>

		<div class="mt40 oh sb-select dkbr">
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="text" id="versusPeriodClassSelectStoreStoreNum" ng-model="storeNum">
			</c:if>
		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadVersusPeriodClass()"><s:message code="cmm.excel.down" />
		    </button>
		</div>



		<%--위즈모 테이블--%>
	    <div class="w100 mt10">
	      <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
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
	          <wj-flex-grid-column header="<s:message code="versusPeriod.realSaleAmt2"/>" 		binding="sinAmt" 		width="*" align="right" is-read-only="true" aggregate="Sum" format="n2"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="sinCnt" 		width="*" align="center" is-read-only="true" aggregate="Sum" format="n2"></wj-flex-grid-column>
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
		      items-source="_getComboData('versusPeriodClassDtllistScaleBox')"
		      display-member-path="name"
		      selected-value-path="value"
		      initialized="_initComboBox(s)"
		      control="conListScale"
              is-editable="true"
              text-changed="_checkValidation(s)">
		    </wj-combo-box>

		    <%-- 엑셀 다운로드 //TODO --%>
			<button class="btn_skyblue fr mr5" ng-click="excelDownloadVersusPeriodClassDtl()"><s:message code="cmm.excel.down" />
			</button>
			<button class="btn_skyblue fr" id="btnVersusPeriodClassChartSearch" ng-click="_broadcast('versusPeriodClassChartCtrlSrch')"><s:message code="cmm.chart" />
			</button>
		</div>

		<%--위즈모 테이블--%>
	    <div class="w100 mt10">
	      <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
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
	          <wj-flex-grid-column header="<s:message code="versusPeriod.prod"/>" 	binding="prodNm" 	width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtA" 	width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="saleCntA" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtB" 		width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" binding="saleCntB" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 		binding="sinAmt" 		width="*" align="center" is-read-only="true" aggregate="Sum" format="n2"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="sinCnt" 		width="*" align="center" is-read-only="true" aggregate="Sum" format="n2"></wj-flex-grid-column>
	        </wj-flex-grid>

	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="versusPeriodClassDtlCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>


	</div>
	<%-- 페이지 리스트 --%>
	  <div class="pageNum mt20">
	    <%-- id --%>
	    <ul id="versusPeriodClassDtlCtrlPager" data-size="10">
	    </ul>
	  </div>
	  <%--//페이지 리스트--%>
</div>

	  <%-- 엑셀 리스트 --%>
  	  <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="versusPeriodClassDtlExcelCtrl">
         <div class="wj-gridWrap">
	        <wj-flex-grid
	          id="versusPeriodClassDtlExcelGrid"
	          autoGenerateColumns="false"
	          control="excelFlex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="versusPeriod.prod"/>" 	binding="prodNm" 	width="*" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtA" 	width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="saleCntA" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 	binding="realSaleAmtB" 		width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" binding="saleCntB" 	width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.saleCnt"/>" 		binding="sinAmt" 		width="*" align="center" is-read-only="true" aggregate="Sum" format="n2"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="versusPeriod.sinCnt"/>" 	binding="sinCnt" 		width="*" align="center" is-read-only="true" aggregate="Sum" format="n2"></wj-flex-grid-column>
	        </wj-flex-grid>
  		 </div>
 	  </div>
	  <%--//엑셀 리스트--%>

<%--layer:For Center screen--%>
<div class="fullDimmed versusPeriodClassLayer" id="versusPeriodClassMask" style="display: none; z-index:4;"></div>
<div class="layer versusPeriodClassLayer" id="versusPeriodClassLayer" style="display: none; z-index:5;">
    <div class="layer_inner">

        <%--layerContent--%>
        <div class="title" style="width:1010px">
            <p class="tit" id="tblAttrTitle" style="padding-left:20px">
            </p>
            <a href="#" class="btn_close _btnClose"></a>

            <%--위즈모 테이블--%>
		    <div class="w100 mt10" id="wjWrapType1" ng-controller="versusPeriodClassChartCtrl">
		    	<div class="wj-gridWrap" style="display:none;" >
		    		<wj-flex-grid
						id="versusPeriodClassChartGrid"
						autoGenerateColumns="false"
						selection-mode="Row"
						items-source="data"
						control="flex"
						is-read-only="true"
						item-formatter="_itemFormatter">
						<!-- define columns -->
						<wj-flex-grid-column header="<s:message code="cmm.storeNm"/>"				binding="prodNm" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
						<wj-flex-grid-column header="<s:message code="pos.realSaleAmtSrch"/>"		binding="realSaleAmtA" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
						<wj-flex-grid-column header="<s:message code="pos.realSaleAmtVs"/>"			binding="realSaleAmtB" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
					</wj-flex-grid>
				</div>

				<div class="mt20 oh sb-select dkbr pd10">
					<!-- 막대 차트 샘플 -->
					<div>
						<wj-flex-chart
							id="versusPeriodClassBarChart"
							name="barChart1"
							class="custom-flex-chart"
							initialized="initChart(s,e)"
							items-source="data"
							binding-x="prodNm">

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

        $("._btnClose").click(function(e) {
            $("div.versusPeriodClassLayer").hide();
        });

    });
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/anals/versusPeriod/cls/versusPeriodClass.js?ver=20190126.01" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<%-- <c:import url="/WEB-INF/view/base/prod/prod/prodModifyView.jsp"> --%>
<%--   <c:param name="menuCd" value="${menuCd}"/> --%>
<%--   <c:param name="menuNm" value="${menuNm}"/> --%>
<%-- </c:import> --%>

