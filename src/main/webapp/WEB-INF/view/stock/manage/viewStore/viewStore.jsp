<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/manage/viewStore/"/>

<div class="subCon" ng-controller="stockViewStoreCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnDcDcfgSearch" ng-click="_broadcast('stockViewStoreCtrl')">
            <s:message code="cmm.search" />
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w13"/>
            <col class="w37"/>
            <col class="w13"/>
            <col class="w37"/>
        </colgroup>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="viewStore.srchDate" /></th>
            <td colspan="3">
            <div class="sb-select">
                <span class="txtIn"><input id="srchStartDate" class="w120px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchEndDate" class="w120px"></span>
            </div>
            </td>
        </tr>
        <tr>
            <%-- 상태 --%>
            <th><s:message code="viewStore.statusFg" /></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w150px">
                        <wj-combo-box
                            id="srchStatusFg"
                            ng-model="statusFgModel"
                            items-source="_getComboData('srchStatusFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </span>
                </div>
            </td>
            <%-- 진행 --%>
            <th><s:message code="viewStore.procFg" /></th>
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
		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<tr>
				<%-- 매장코드 --%>
				<th><s:message code="viewStore.storeNm"/></th>
				<td colspan="3">
					<%-- 매장선택 모듈 멀티 선택 사용시 include --%>
					<jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
						<jsp:param name="targetId" value="stockViewStoreSelectStore"/>
					</jsp:include>
					<%--// 매장선택 모듈 멀티 선택 사용시 include --%>
				</td>
			</tr>
		</c:if>
		<c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="stockViewStoreSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
    </table>
    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="stockViewStoreListScaleBox"
            ng-model="listScale"
            items-source="_getComboData('stockViewStoreListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="_initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
        </wj-combo-box>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadViewStore()"><s:message code="cmm.excel.down" /></button>
    </div>

    <%--위즈모 테이블 : 수정해야함--%>
    <div id="wjGridWrap" class="mt10">
      <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <c:if test="${orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="viewStore.storeCd"/>"         binding="storeCd"        width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="viewStore.storeNm"/>"         binding="storeNm"        width="120" align="center" is-read-only="true"></wj-flex-grid-column>
          </c:if>
          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="viewStore.statusFg"/>"         binding="hqGbn"        width="50" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="viewStore.statusFg"/>"         binding="hqGbnNm"        width="50" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="viewStore.saleDate"/>"         binding="totDate"        width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="viewStore.seqNo"/>"           binding="seqNo"        width="50" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="viewStore.procFg"/>"        binding="procFgNm"        width="50" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="viewStore.title"/>"           binding="title"        width="*" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="viewStore.prodQty"/>"        binding="dtlCnt"        width="90" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="stockViewStoreCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="stockViewStoreCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
  
  
	<%-- 엑셀 리스트 --%>
	<div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="stockViewStoreExcelCtrl">
      <div class="wj-gridWrap">
          <wj-flex-grid
			id="stockViewStoreExcelGrid"
            autoGenerateColumns="false"
            control="excelFlex"
            initialized="initGrid(s,e)"
            loaded-rows="loadedRows(s,e)"
			is-read-only="true"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter">
				<!-- define columns -->
          		<c:if test="${orgnFg == 'HQ'}">
            		<wj-flex-grid-column header="<s:message code="viewStore.storeCd"/>"         binding="storeCd"        width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            		<wj-flex-grid-column header="<s:message code="viewStore.storeNm"/>"         binding="storeNm"        width="120" align="center" is-read-only="true"></wj-flex-grid-column>
          		</c:if>
          		<!-- define columns -->
         		<wj-flex-grid-column header="<s:message code="viewStore.statusFg"/>"         binding="hqGbn"        width="50" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="viewStore.statusFg"/>"         binding="hqGbnNm"        width="50" align="center" is-read-only="true"></wj-flex-grid-column>
         		<wj-flex-grid-column header="<s:message code="viewStore.saleDate"/>"         binding="totDate"        width="90" align="center" is-read-only="true"></wj-flex-grid-column>
        		<wj-flex-grid-column header="<s:message code="viewStore.seqNo"/>"           binding="seqNo"        width="50" align="center" is-read-only="true"></wj-flex-grid-column>
       		    <wj-flex-grid-column header="<s:message code="viewStore.procFg"/>"        binding="procFgNm"        width="50" align="center" is-read-only="true"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="viewStore.title"/>"           binding="title"        width="*" align="left" is-read-only="true"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="viewStore.prodQty"/>"        binding="dtlCnt"        width="90" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/stock/manage/viewStore/viewStore.js?ver=20200316.01" charset="utf-8"></script>
