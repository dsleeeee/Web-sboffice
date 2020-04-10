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
      		<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('prodRankCtrlSrch')">
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
        	<td>
          	<div class="sb-select">
          	    <span class="txtIn"><input id="srchRankStartDate" class="w120px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchRankEndDate" class="w120px"></span>
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
        <tr>
            <%-- 매장코드 --%>
          	<th><s:message code="todayBillSaleDtl.store"/></th>
          	<td colspan="3">
            	<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
              		<jsp:param name="targetId" value="prodRankSelectStore"/>
            	</jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          	</td>
        </tr>
      	</c:if>
     	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
        	<input type="hidden" id="prodRankSelectStoreCd" value="${sessionInfo.storeCd}"/>
      	</c:if>
      	</tbody>
		</table>
    	<div style="clear: both;"></div>

		<div class="mt40 oh sb-select dkbr">
		    <%-- 페이지 스케일  --%>
		    <wj-combo-box
		      class="w100px fl"
		      id="prodRanklistScaleBox"
		      ng-model="prodRanklistScale"
		      items-source="_getComboData('prodRanklistScaleBox')"
		      display-member-path="name"
		      selected-value-path="value"
		      is-editable="false"
		      initialized="_initComboBox(s)">
		    </wj-combo-box>
			<c:if test="${sessionInfo.orgnFg == 'HQ'}">
				<input type="text" id="prodRankSelectStoreStoreNum" ng-model="storeNum">
			</c:if>
		    <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadRank()"><s:message code="cmm.excel.down" />
		    </button>
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
	          <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.prodCd"/>" 		binding="prodCd" 		width="120" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.prodNm"/>"			binding="prodNm" 		width="200" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.prodBar"/>" 		binding="prodBar" 		width="120" align="center" is-read-only="true"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.totSaleQty"/>" 	binding="totSaleQty" 	width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.totDcAmt"/>" 		binding="totDcAmt" 		width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodrank.realSaleAmt"/>" 	binding="realSaleAmt" 	width="120" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
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
	</div>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/prod/rank/prodRank.js?ver=20190125.02" charset="utf-8"></script>

