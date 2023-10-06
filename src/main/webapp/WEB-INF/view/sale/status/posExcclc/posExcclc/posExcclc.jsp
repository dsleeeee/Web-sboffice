<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="posExcclcView" class="subCon3"  ng-controller="posExcclcCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="posExccl.posExcclInfo"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnPosExcclcSearch" ng-click="_broadcast('posExcclcCtrlSrch')">
        <s:message code="cmm.search"/>
      </button>
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
        <th><s:message code="cmm.search.date"/></th>
        <td>
        <div class="sb-select">
            <span class="txtIn"><input id="srchPosExcclcStartDate" class="w110px"></span>
                <span class="rg">~</span>
            <span class="txtIn"><input id="srchPosExcclcEndDate" class="w110px"></span>
            <span class="chk ml10" style="display: none;">
				<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
				<label for="chkDt"><s:message code="cmm.all.day" /></label>
			</span>
        </div>
        </td>

      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="posExcclcSelectStoreCd" value="${sessionInfo.storeCd}"/>
        <%-- 매장선택 --%>
        <th><s:message code="cmm.store.select"/></th>
        <td>
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                <jsp:param name="targetTypeFg" value="M"/>
                <jsp:param name="targetId" value="posExcclcSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
        </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="posExcclcSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>

      <tr>
		<%-- 마감구분 --%>
		<th><s:message code="posExcclc.closeFg" /></th>
		<td colspan="3">
			<div class="sb-select fl">
				<span class="txtIn">
					<wj-combo-box id="srchCloseFgDisplay" ng-model="closeFgModel"
						items-source="_getComboData('srchCloseFgDisplay')"
						display-member-path="name" selected-value-path="value"
						is-editable="false" initialized="_initComboBox(s)">
					</wj-combo-box>
				</span>
			</div>
            &nbsp;&nbsp;마감자료가 없는 경우 <span style="color:red;">빨간색</span>으로 표시됩니다.
		</td>
	  </tr>

      </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="posExcclcListScaleBox"
            ng-model="posExcclcListScale"
            items-source="_getComboData('posExcclcListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
        </wj-combo-box>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" />
        </button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap2">
        <wj-flex-grid
          id="posExcclcGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">
          <!-- define columns -->
          <wj-flex-grid-column header=""        									 binding="hqOfficeCd"       width="0"   align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.storeCd"/>"        binding="storeCd"          width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.storeNm"/>"        binding="storeNm"          width="150" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.saleDate"/>"       binding="saleDate"         width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.posNo"/>"          binding="posNo"            width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header=""                                             binding="regSeq"           width="70"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.closeFg"/>"        binding="closeFgNm"        width="80"  align="center" is-read-only="true" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.openDate"/>"       binding="openDate"         width="150" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.closeDate"/>"      binding="closeDate"        width="150" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.totSaleAmt"/>"     binding="totSaleAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.totDcAmt"/>"       binding="totDcAmt"         width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.realSaleAmt"/>"    binding="realSaleAmt"      width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.cashSaleAmt"/>"    binding="cashExactAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.cashBillSaleAmt"/>" binding="cashBillSaleAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.posFundAmt"/>"     binding="fundAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.inAmt"/>"          binding="accntInAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.outAmt"/>"         binding="accntOutAmt"      width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.cashTicketAmt"/>"  binding="cashTicketAmt"    width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posExcclc.cashLostAmt"/>"    binding="lostAmt"      	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="posExcclcCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="posExcclcCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
  
    <%--엑셀 리스트--%>
    <div class="wj-gridWrap2" style="display:none;" ng-controller="posExcclcExcelCtrl">
        <wj-flex-grid
            id="posExcclcExcelGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="excelFlex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">
            <!-- define columns -->
            <wj-flex-grid-column header=""        									 binding="hqOfficeCd"       width="0"   align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.storeCd"/>"        binding="storeCd"          width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.storeNm"/>"        binding="storeNm"          width="150" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.saleDate"/>"       binding="saleDate"         width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.posNo"/>"          binding="posNo"            width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.closeFg"/>"        binding="closeFgNm"        width="80"  align="center" is-read-only="true" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.openDate"/>"       binding="openDate"         width="150" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.closeDate"/>"      binding="closeDate"        width="150" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.totSaleAmt"/>"     binding="totSaleAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.totDcAmt"/>"       binding="totDcAmt"         width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.realSaleAmt"/>"    binding="realSaleAmt"      width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.cashSaleAmt"/>"    binding="cashExactAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.cashBillSaleAmt"/>"    binding="cashBillSaleAmt"     width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.posFundAmt"/>"     binding="fundAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.inAmt"/>"          binding="accntInAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.outAmt"/>"         binding="accntOutAmt"      width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.cashTicketAmt"/>"  binding="cashTicketAmt"    width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posExcclc.cashLostAmt"/>"    binding="lostAmt"      	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/posExcclc/posExcclc/posExcclc.js?ver=201901112.18" charset="utf-8"></script>

<%-- 본사 상세정보 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/posExcclc/posExcclc/posExcclcDetail.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>