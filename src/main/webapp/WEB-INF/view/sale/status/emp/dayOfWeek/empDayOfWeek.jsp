<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/sale/status/emp/day/empDayOfWeek/"/>

<div id="empDayOfWeekView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="empDayOfWeekCtrl">
	<div class="searchBar">
		<a href="#" class="open fl"><s:message code="empsale.dayofweek"/></a>
    	<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('empDayOfWeekCtrlSrch')">
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
		<tr>
			<%-- 조회일자 --%>
	    	<th><s:message code="cmm.search.date" /></th>
        	<td colspan="3">
          	<div class="sb-select fl">
       		    <span class="txtIn"><input id="srchDayOfWeekStartDate" class="w110px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchDayOfWeekEndDate" class="w110px"></span>
            	<span class="chk ml10" style="display: none;">
					<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
	              	<label for="chkDt">
                		<s:message code="cmm.all.day" />
              		</label>
            	</span>
          	</div>
        		<%-- 판매자 전체보기 --%>
            	<span class="chk ml10">
                	<input type="checkbox"  ng-model="isCheckedEmpAll" ng-change="totalEmpDayOfWeek()" />
                	<label for="totalEmpDay()">판매자 전체보기</label>
            	</span>
        	</td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="empDayOfWeekSelectStoreCd" value=""/>
      	<tr>
			<%-- 매장선택 --%>
			<th><s:message code="cmm.store.select"/></th>
         	<td colspan="3">
				<%-- 매장선택 모듈 사용시 include --%>
				<jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
					<jsp:param name="targetTypeFg" value="S"/>
					<jsp:param name="targetId" value="empDayOfWeekSelectStore"/>
				</jsp:include>
				<%--// 매장선택 모듈 사용시 include --%>
         	</td>
      </tr>
      </c:if>
      	<c:if test="${sessionInfo.orgnFg == 'STORE'}">
        	<input type="hidden" id="empDayOfWeekSelectStoreCd" value="${sessionInfo.storeCd}"/>
      	</c:if>
		</tbody>
	</table>
	<div class="mt10 oh sb-select dkbr">
		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<input type="text" id="empDayOfWeekSelectStoreStoreNum" ng-model="storeNum">
		</c:if>
	    <%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue fr" ng-click="excelDownloadEmpDayOfWeek()"><s:message code="cmm.excel.down" />
	    </button>
	</div>

	<%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType3">
      <div class="wj-gridWrap" style="height:380px;">
        <wj-flex-grid
          id="empDayOfWeekGrid"
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          frozen-columns="4"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="empday.yoil"/>" 			binding="yoil" 				width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empday.storeCnt"/>" 		binding="storeCnt" 			width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empday.realSaleAmtTot"/>"	binding="realSaleAmtTot" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empday.totBillCnt"/>" 		binding="totBillCnt" 		width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="empDayOfWeekCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

</div>
<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/emp/dayOfWeek/empDayOfWeek.js?ver=20250415.01" charset="utf-8"></script>

