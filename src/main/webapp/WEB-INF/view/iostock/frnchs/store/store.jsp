<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/iostock/frnchs/store/"/>

<div id="frnchsStoreView" class="subCon3" ng-controller="frnchsStoreCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="frnchsStore.frnchsStore"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('frnchsStoreCtrlSrch')">
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
                <span class="txtIn"><input id="srchClassStartDate" class="w120px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchClassEndDate" class="w120px"></span>
            </div>
            </td>
            <c:if test="${envst1242 == '1'}">
                <th><s:message code="orderStockInfo.vendr" /></th>
                <td>
                    <div class="sb-select fl w150px">
                        <wj-combo-box
                                id="vendrCd"
                                ng-model="vendrCd"
                                control="vendrCdCombo"
                                items-source="_getComboData('vendrCd')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </c:if>
        </tr>
        <tr style="display: none;">
          <%-- 전표구분 --%>
	      <th><s:message code="frnchsStore.slipKind"/></th>
	      <td colspan="3">
	        <div class="sb-select">
	          <span class="txtIn w150px">
	            <wj-combo-box
	              id="srchSlipKind"
	              ng-model="slipKindModel"
	              items-source="_getComboData('srchSlipKind')"
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
            <%-- 매장코드 멀티 선택 팝업 --%>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <th><s:message code="todayBillSaleDtl.store"/></th>
                <td>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="frnchsStoreSelectStore"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="frnchsStoreSelectStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            <%-- 매장명 --%>
            <th><s:message code="frnchsStore.storeNm" /></th>
            <td>
                <input type="text" id="storeNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- 세금계산서 --%>
    <table class="searchTbl mt10">
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr class="brt">
      <%-- 거래명세표 --%>
      <th><s:message code="dstmn.stmtAcct"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="stmtAcctFg"
            ng-model="stmtAcctFg"
            items-source="_getComboData('stmtAcctFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <a href="#" class="btn_grayS" ng-click="report('trans')"><s:message code="dstmn.stmtAcctPrint"/></a>
        <%-- <a href="#" class="btn_grayS" ng-click="report('transExcelDown')"><s:message code="dstmn.stmtAcctExcel"/></a> --%>
      </td>
    </tr>
    <tr>
      <%-- 세금계산서 --%>
      <th><s:message code="frnchsStore.taxReport"/></th>
      <td>
        <div class="sb-select fl mr5">
          <span class="txtIn"><input id="writtenDate" class="w120px"></span>
        </div>
        <span class="txtIn w80px sb-select fl mr5">
          <wj-combo-box
            id="billFg"
            ng-model="billFg"
            items-source="_getComboData('billFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <span class="txtIn w120px sb-select fl mr5">
          <wj-combo-box
            id="taxFg"
            ng-model="taxFg"
            items-source="_getComboData('taxFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <a href="#" class="btn_grayS" ng-click="report('tax')"><s:message code="dstmn.taxBillIssue"/></a>
      </td>
    </tr>
        </tbody>
    </table>
    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
          class="w100px fl"
          id="frnchsStorelistScaleBox"
          ng-model="listScale"
          items-source="_getComboData('frnchsStorelistScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          initialized="_initComboBox(s)"
          control="conListScale"
		  is-editable="true"
		  text-changed="_checkValidation(s)">
        </wj-combo-box>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="text" id="frnchsStoreSelectStoreStoreNum" ng-model="storeNum">
        </c:if>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadClass()"><s:message code="cmm.excel.down" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div id="wjWrapType1" class="w100 mt10">
      <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>"                     binding="gChk" width="40" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header=""                                                binding="slipNo" width="40" align="center" visible="false" ng-model="silpNo"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.storeCd"/>"         binding="storeCd"        width="120" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.storeNm"/>"         binding="storeNm"        width="200" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.slipCnt"/>"       	binding="slipCnt"        width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.orderTot"/>"        binding="orderTot"        width="120" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.mdTot"/>"          	binding="mdTot"       width="200" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.outTot"/>"          binding="outTot"       width="200" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.inTot"/>"           binding="inTot"    width="130" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.outAmt"/>"          binding="outAmt"    width="130" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="frnchsStore.outVat"/>"        	binding="outVat"       width="200" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="frnchsStoreCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="frnchsStoreCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>


    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="display: none;" ng-controller="frnchsStoreExcelCtrl">
      <wj-flex-grid
        id="frnchsStoreExcelGrid"
        autoGenerateColumns="false"
        control="excelFlex"
        initialized="initGrid(s,e)"
        sticky-headers="true"
        selection-mode="Row"
        items-source="data"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>"                     binding="gChk" width="40" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header=""                                                binding="slipNo" width="40" align="center" visible="false" ng-model="silpNo"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.storeCd"/>"         binding="storeCd"        width="120" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.storeNm"/>"         binding="storeNm"        width="200" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.slipCnt"/>"         binding="slipCnt"        width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.orderTot"/>"        binding="orderTot"        width="120" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.mdTot"/>"           binding="mdTot"       width="200" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.outTot"/>"          binding="outTot"       width="200" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.inTot"/>"           binding="inTot"    width="130" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.outAmt"/>"          binding="outAmt"    width="130" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="frnchsStore.outVat"/>"          binding="outVat"       width="200" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
</div>
<script type="text/javascript">
    var gEnvst1242  = '${envst1242}';
    var empVendrCd = '${empVendrCd}';
    <%-- 본사 거래처 콤보박스 --%>
    var vendrList = ${vendrList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/iostock/frnchs/store/store.js?ver=20190125.03" charset="utf-8"></script>

<%-- 매장별 입출고내역 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/frnchs/store/storeDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 세금계산서 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/taxReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 거래명세표 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/transReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 거래명세표 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/dstmnDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 거래명세표 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/transReportExcelDown.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
