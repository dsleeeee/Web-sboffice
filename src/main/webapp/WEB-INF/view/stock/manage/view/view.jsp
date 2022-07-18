<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/status/storeperiod/"/>

<div class="subCon" ng-controller="stockManageViewCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl"><s:message code="stockManageView.stockManageView"/></a>
		<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('stockManageViewCtrlSrch')"><s:message code="cmm.search"/></button>
	</div>
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
				<th><s:message code="stockManageView.srchDate" /></th>
				<td>
				<div class="sb-select">
					<span class="txtIn"><input id="srchClassStartDate" class="w110px"></span>
					<span class="rg">~</span>
					<span class="txtIn"><input id="srchClassEndDate" class="w110px"></span>
				</div>
				</td>
				<%-- 진행 --%>
				<th><s:message code="stockManageView.procFg" /></th>
				<td>
					<div class="sb-select">
					<span class="txtIn w150px">
						<wj-combo-box
								id="srchProcFg"
								ng-model="procFgModel"
								items-source="_getComboData('srchProcFg')"
								display-member-path="name"
								selected-value-path="value"
								is-editable="false"
								initialized="_initComboBox(s)">
						</wj-combo-box>
					</span>
					</div>
				</td>
			</tr>
			<tr>
				<%-- 상태 --%>
				<th><s:message code="stockManageView.hqGbn" /></th>
				<td>
					<div class="sb-select fl mr5 w110px">
						<wj-combo-box
							id="srchStatus"
							ng-model="hqGbnModel"
							items-source="_getComboData('srchStatus')"
							display-member-path="name"
							selected-value-path="value"
							is-editable="false"
							initialized="_initComboBox(s)"
							selected-index-changed="setReason(s)">
						</wj-combo-box>
					</div>
					<div class="sb-select fl w110px">
						<wj-combo-box
								id="srchReason"
								ng-model="reason"
								items-source="_getComboData('srchReason')"
								display-member-path="name"
								selected-value-path="value"
								is-editable="false"
								initialized="_initComboBox(s)">
						</wj-combo-box>
					</div>
				</td>
			</tr>
		</tbody>
	</table>

	<input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
	<input type="hidden" id="storeCd" value="${sessionInfo.storeCd}"/>

	<div class="mt20 oh sb-select dkbr">
		<%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="stockManageViewListScaleBox"
            ng-model="listScale"
            items-source="_getComboData('stockManageViewListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="_initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
        </wj-combo-box>

    	<%-- 엑셀 다운로드 --%>
    	<button class="btn_skyblue ml5 fr" id="btnExcelDown" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
  	</div>

  	<%--위즈모 테이블--%>
    <div id="wjGridWrap" class="mt10">
      <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          id="stockManageViewGrid"
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="stockManageView.hqGbn"/>"         binding="hqGbn"        width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.hqGbn"/>"         binding="hqGbnNm"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.totDate"/>"         binding="totDate"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.seqNo"/>"       	binding="seqNo"        width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.procFg"/>"        binding="procFgNm"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.title"/>"       	binding="title"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="<s:message code="stockManageView.reasonNm"/>"        binding="reasonNm"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.dtlCnt"/>"        binding="dtlCnt"        width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="stockManageViewCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="stockManageViewCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
  
  <%--엑셀 리스트--%>
    <div id="wjGridWrap" class="mt10" style="display:none;" ng-controller="stockManageViewExcelCtrl">
      <div class="wj-gridWrap">
        <wj-flex-grid
          id="stockManageViewExcelGrid"
          autoGenerateColumns="false"
          control="excelFlex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="stockManageView.hqGbn"/>"         binding="hqGbn"        width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.hqGbn"/>"         binding="hqGbnNm"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.totDate"/>"         binding="totDate"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.seqNo"/>"       	binding="seqNo"        width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.procFg"/>"        binding="procFgNm"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.title"/>"       	binding="title"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.reasonNm"/>"        binding="reasonNm"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="stockManageView.dtlCnt"/>"        binding="dtlCnt"        width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
    <%--//엑셀 리스트--%>


</div>

<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/manage/view/viewDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/stock/manage/view/view.js?ver=20200316.02" charset="utf-8"></script>
