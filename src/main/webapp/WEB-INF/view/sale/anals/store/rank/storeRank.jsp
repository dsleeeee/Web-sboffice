<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/sale/anals/store/rank/"/>

<div id="storeRankView" class="subCon" ng-controller="storeRankCtrl">
	<input type="hidden" id="HqOfficeCd" name="HqOfficeCd" ng-model="HqOfficeCd" value="${gvHqOfficeCd}"/>
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="store.rank"/></a>
    	<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnStoreRankSearch" ng-click="_broadcast('storeRankCtrl')">
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
        	<td colspan="3">
          	<div class="sb-select">
       		    <span class="txtIn"><input id="srchRankStartDate" class="w110px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchRankEndDate" class="w110px"></span>
            	<span class="chk ml10" style="display: none;">
					<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
	              	<label for="chkDt">
                		<s:message code="cmm.all.day" />
              		</label>
            	</span>
          	</div>
        	</td>
        </tr>
      	<tr>
      		<th><s:message code="store.sort" /></th>
        	<td colspan="3">

            	<span class="rdo fl mr20 pst7">
                	<label class="r-box"><input type="radio"  ng-model="isCheckedSort"  value="1" checked/>상위</label>
                	<label class="r-box"><input type="radio"  ng-model="isCheckedSort"  value="2" />하위</label>
            	</span>

                <div class="sb-select fl w150px mr20">
                    <wj-combo-box
                            id="rowNum"
                            ng-model ="rowNum"
                            control="conRowNum"
                            items-source="_getComboData('srchRowNumCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
        		<%-- 전체순위 --%>
            	<span class="chk pst4">
                	<input type="checkbox"  ng-model="isCheckedRankAll" ng-change="totalRank()" />
                	<label for="totalRank()">전체순위</label>
            	</span>
        	</td>
      	</tr>
      	<c:if test="${sessionInfo.orgnFg == 'HQ'}">
      	<tr>
            <%-- 매장코드 --%>
          	<th><s:message code="todayBillSaleDtl.store"/></th>
          	<td colspan="3">
            	<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
             		<jsp:param name="targetId" value="storeRankSelectStore"/>
            	</jsp:include>
              	<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
              	<%-- 결제수단 전체보기 --%>
                <span class="chk pst4">
                    <input type="checkbox"  ng-model="isCheckedPayAll"/>
                    <label for="totalPay()">전체결제수단 표시</label>
                </span>
          	</td>
        </tr>
        </c:if>
      	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
        	<input type="hidden" id="storeRankSelectStoreCd" value="${sessionInfo.storeCd}"/>
      	</c:if>
		</tbody>
	</table>

	<div class="mt10 oh sb-select dkbr">
	    
	  <div class="fr">
		<%-- 매장표시설정 --%>
		<button class="btn_skyblue" id="btnStoreIndexNo" ng-click="storeIndexNoShow()"><s:message code="store.storeIndexNo" /></button>
	  	<%-- 차트 --%>
	    <button class="btn_skyblue" id="btnShowChart"><s:message code="cmm.chart" /></button>
	    <%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue" ng-click="excelDownloadStoreRank()"><s:message code="cmm.excel.down" /></button>
		</div>
	</div>
	<div class="clearfix"></div>
	
	<%--위즈모 테이블--%>
    <div class="w100 mt10">
      <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          id="storeRankGrid"
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          loaded-rows="loadedRows(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          frozen-columns="5"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="store.indexNo"/>" 		binding="indexNo" 		width="65" align="center" is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.storeNm"/>" 		binding="storeNm" 		width="200" align="center" is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.totSaleAmt"/>" 	binding="totSaleAmt" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.totDcAmt"/>"		binding="totDcAmt" 		width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="store.realSaleAmt"/>" 	binding="realSaleAmt" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
 		  <wj-flex-grid-column header="<s:message code="store.openDay"/>" 		binding="saleDateCnt" 		width="100" align="center" is-read-only="true"></wj-flex-grid-column>
  		  <wj-flex-grid-column header="<s:message code="store.openDayAmt"/>" 	binding="realSaleAmtAvg" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
   		  <wj-flex-grid-column header="<s:message code="store.billCnt"/>" 		binding="billCnt" 		width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
   		  <wj-flex-grid-column header="<s:message code="store.totBillAmt"/>" 	binding="totBillAmt" 	width="120" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
   		  <wj-flex-grid-column header="<s:message code="store.totGuestCnt"/>" 	binding="totGuestCnt" 	width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
   		  <wj-flex-grid-column header="<s:message code="store.storeRat"/>" 		binding="storeRat" 		width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="storeRankCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

    <%--layer:For Center screen--%>
	<div class="fullDimmed storeRankLayer" id="storeRankMask" style="display: none"></div>
	<div class="layer storeRankLayer" id="storeRankLayer" style="display: none; z-index:1499;">
	    <div class="layer_inner">

	        <%--layerContent--%>
	        <div class="title" style="width:980px; padding:0;" ng-controller="storeRankBarChartCtrl">
	            <p class="tit" id="tblAttrTitle" style="padding-left:20px"><s:message code="cmm.chart" /></p>
	            <a href="#" class="btn_close _btnClose"></a>

	            <%--위즈모 테이블--%>
			    <div class="w100">
						<!-- 막대 차트 샘플 -->
						<div>
							<wj-flex-chart
								id="storeRankBarChart"
								name="barChart1"
								class="custom-flex-chart"
								initialized="initChart(s,e)"
								items-source="data"
								binding-x="storeNm">

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
</div>

<script>
    $(document).ready(function() {

        $("#btnShowChart").click(function(e) {
        	$("div.storeRankLayer").show();
            $("#btnStoreRankSearch").click();
        });

        $("._btnClose").click(function(e) {
            $("div.storeRankLayer").hide();
        });

    });
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/store/rank/storeRank.js?ver=20190126.03" charset="utf-8"></script>

<c:import url="/WEB-INF/view/sale/anals/store/rank/storeIndexNo.jsp">
	<c:param name="menuCd" value="${menuCd}"/>
	<c:param name="menuNm" value="${menuNm}"/>
</c:import>