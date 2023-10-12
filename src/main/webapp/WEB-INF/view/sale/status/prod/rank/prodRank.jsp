<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/sale/status/prod/rank/prodRank/"/>

<div id="prodRankView" class="subCon" style="display: none;">
  	<div ng-controller="prodRankCtrl">
    	<div class="searchBar flddUnfld">
      		<a href="#" class="open fl"><s:message code="prodsale.rank"/></a>
      		<%-- 조회 --%>
      		<button class="btn_blue fr mt5 mr10" id="btnProdRankSearch" ng-click="_pageView('prodRankCtrl',1)">
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
			<%-- 조회일자 --%>
			<tr>
				<th><s:message code="cmm.search.date" /></th>
				<td>
					<div class="sb-select">
						<span class="txtIn"><input id="startDateProdRank" class="w110px"></span>
						<span class="rg">~</span>
						<span class="txtIn"><input id="endDateProdRank" class="w110px"></span>
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
			<tr>
				<%-- 정렬기준 --%>
				<th><s:message code="prodRank.orderType"/></th>
				<td colspan="3">
					<div class="sb-select" style="width:200px;">
						<wj-combo-box
							id="orderType"
							ng-model="orderType"
							items-source="_getComboData('orderType')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							control="orderTypeCombo"
							initialized="_initComboBox(s)">
						</wj-combo-box>
					</div>
				</td>
			</tr>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<tr>
				<%-- 매장선택 --%>
				<th><s:message code="cmm.store.select"/></th>
				<td colspan="3">
					<%-- 매장선택 모듈 사용시 include --%>
					<jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
						<jsp:param name="targetTypeFg" value="M"/>
						<jsp:param name="targetId" value="prodRankSelectStore"/>
					</jsp:include>
					<%--// 매장선택 모듈 사용시 include --%>
				</td>
			</tr>
			</c:if>
			<c:if test="${sessionInfo.orgnFg == 'STORE'}">
				<input type="hidden" id="prodRankSelectStoreCd" value="${sessionInfo.storeCd}"/>
			</c:if>
			</tbody>
		</table>
    	<div style="clear: both;"></div>

		<div class="mt10 oh sb-select dkbr">
		    <%-- 페이지 스케일  --%>
		    <wj-combo-box
		      class="w100px fl"
		      id="prodRanklistScaleBox"
		      ng-model="prodRanklistScale"
		      items-source="_getComboData('prodRanklistScaleBox')"
		      display-member-path="name"
		      selected-value-path="value"
		      initialized="_initComboBox(s)"
		      control="listScaleCombo"
		      is-editable="true"
              text-changed="_checkValidation(s)">
		    </wj-combo-box>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="text" id="prodRankSelectStoreStoreNum" ng-model="storeNum">
			</c:if>
			<div class="fr">
				<%-- 차트 --%>
				<button class="btn_skyblue" id="btnShowChart" ng-click=""><s:message code="cmm.chart" /></button>
				<%-- 엑셀 다운로드 //TODO --%>
				<button class="btn_skyblue" ng-click="excelDownloadRank()"><s:message code="cmm.excel.down" /></button>
		    </div>
		</div>
		
	    <div class="w100 mt10" id="wjWrapType1">
	      <%--위즈모 테이블--%>
	      <div class="wj-gridWrap">
	        <wj-flex-grid
	          autoGenerateColumns="false"
	          selection-mode="Row"
	          items-source="data"
	          control="flex"
	          initialized="initGrid(s,e)"
	          is-read-only="true"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="prodrank.prodClassNm"/>" 	binding="pathNm" 		width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.prodCd"/>" 		binding="prodCd" 		width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.prodNm"/>"		binding="prodNm" 		width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.barcdCd"/>" 		binding="barcdCd" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.totSaleQty"/>" 	binding="totSaleQty" 	width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.totDcAmt"/>" 		binding="totDcAmt" 		width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.realSaleAmt"/>" 	binding="realSaleAmt" 	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	         </wj-flex-grid>
	        <%-- ColumnPicker 사용시 include --%>
	        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	          <jsp:param name="pickerTarget" value="prodRankCtrl"/>
	        </jsp:include>
	        <%--// ColumnPicker 사용시 include --%>
	      </div>
	      <%--//위즈모 테이블--%>

		  <%-- 페이지 리스트 --%>
		  <div class="pageNum mt20">
			<%-- id --%>
			<ul id="prodRankCtrlPager" data-size="10">
			</ul>
		  </div>
		  <%--//페이지 리스트--%>
	  </div>
		  
	  <%--엑셀 리스트--%>
	  <div class="w100 mt10" id="wjWrapType1" style="display:none;" ng-controller="prodRankExcelCtrl">
		  <%--위즈모 테이블--%>
		  <div class="wj-gridWrap">
			<wj-flex-grid
			  autoGenerateColumns="false"
			  selection-mode="Row"
			  items-source="data"
			  control="excelFlexSec"
			  initialized="initGrid(s,e)"
			  is-read-only="true"
			  item-formatter="_itemFormatter">

			  <!-- define columns -->
			  <wj-flex-grid-column header="<s:message code="prodrank.prodClassNm"/>" 	binding="pathNm" 		width="300" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="prodrank.prodCd"/>" 		binding="prodCd" 		width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="prodrank.prodNm"/>"		binding="prodNm" 		width="150" align="center" is-read-only="true"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="prodrank.barcdCd"/>" 		binding="barcdCd" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="prodrank.totSaleQty"/>" 	binding="totSaleQty" 	width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="prodrank.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="prodrank.totDcAmt"/>" 		binding="totDcAmt" 		width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			  <wj-flex-grid-column header="<s:message code="prodrank.realSaleAmt"/>" 	binding="realSaleAmt" 	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
			 </wj-flex-grid>
			<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
			  <jsp:param name="pickerTarget" value="prodRankCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
		  </div>
		  <%--//위즈모 테이블--%>
	  </div>
	<%--//엑셀 리스트--%>
	</div>
</div>

<%--layer:For Center screen--%>
<div class="fullDimmed prodRankLayer" id="prodRankMask" style="display: none"></div>
<div class="layer prodRankLayer" id="prodRankLayer" style="display: none; z-index:1499">
    <div class="layer_inner">

        <%--layerContent--%>
        <div class="title" style="width:980px; padding:0">
            <p class="tit" id="tblAttrTitle" style="padding-left:20px"><s:message code="cmm.chart" /></p>
            <a href="#" class="btn_close _btnClose"></a>

            <%--위즈모 테이블--%>
		    <div class="w100" id="wjWrapType1" ng-controller="prodRankChartCtrl">
		    	<div class="wj-gridWrap" style="display:none;" >
		    		<wj-flex-grid
						id="prodRankChartGrid"
						autoGenerateColumns="false"
						selection-mode="Row"
						items-source="data"
						control="flex"
						is-read-only="true"
						item-formatter="_itemFormatter">
						<!-- define columns -->
						<wj-flex-grid-column header="<s:message code="pos.prodNm"/>"			binding="prodNm" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
						<wj-flex-grid-column header="<s:message code="pos.realSaleAmt"/>"		binding="realSaleAmt" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
					</wj-flex-grid>
				</div>
				<!-- 막대 차트 샘플 -->
				<div>
					<wj-flex-chart
						id="prodRankBarChart"
						name="barChart1"
						class="custom-flex-chart"
						initialized="initChart(s,e)"
						items-source="data"
						binding-x="prodNm">

						<wj-flex-chart-series name="<s:message code="pos.realSaleAmt"/>" binding="realSaleAmt"></wj-flex-chart-series>
					</wj-flex-chart>
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

    	$("div.prodRankLayer").show();

        $("#btnProdRankSearch").click();
    });

    $("._btnClose").click(function(e) {
        $("div.prodRankLayer").hide();
    });

});
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/prod/rank/prodRank.js?ver=20210809.02" charset="utf-8"></script>