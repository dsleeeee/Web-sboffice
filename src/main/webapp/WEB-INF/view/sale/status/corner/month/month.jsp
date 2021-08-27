<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="cornerMonthView" class="subCon" style="display: none;" ng-controller="cornerMonthCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="corner.month"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnCornerMonthSearch" ng-click="_broadcast('cornerMonthCtrlSrch')">
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
        <th><s:message code="cmm.search.month"/></th>
        <td <c:if test="${sessionInfo.orgnFg == 'STORE'}">colspan="3"</c:if> >
        <div class="sb-select">
          <span class="txtIn w110px">
              <wj-input-date
                      id="srchCornerMonthStartDate"
                      ng-model="startDate"
                      control="cornerMonthStartDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)"
                      selection-mode="Month"
                      format="y">
              </wj-input-date>
            </span>
            <span class="rg">~</span>
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchCornerMonthEndDate"
                      ng-model="endDate"
                      control="cornerMonthEndDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)"
                      selection-mode="Month"
                      format="y">
              </wj-input-date>
            </span>
            <span class="chk ml10" style="display: none;">
				<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
				<label for="chkDt">
					<s:message code="cmm.all.day" />
				</label>
			</span>
        </div>
        </td>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="cornerMonthSelectStoreCd" value=""/>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreS.jsp" flush="true">
                <jsp:param name="targetId" value="cornerMonthSelectStore"/>
                <jsp:param name="subTargetId" value="cornerMonthSelectCorner"/>
                <jsp:param name="closeFunc" value="closeSelectStore"/>
            </jsp:include>
        </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="cornerMonthSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
                <input type="hidden" id="cornerMonthSelectCornerCd" value=""/>
                <input type="hidden" id="cornerMonthSelectCornerName" value=""/>
                <input type="hidden" id="cornerMonthSelectExcelCornerCd" value=""/>
                <input type="hidden" id="cornerMonthSelectExcelCornerName" value=""/>
      </tr>
      <tr>
        <%-- 코너표시 --%>
        <th><s:message code="corner.cornrDisplay" /></th>
        <td colspan="3">
          <jsp:include page="/WEB-INF/view/sale/com/popup/selectCornerM.jsp" flush="true">
                <jsp:param name="targetId" value="cornerMonthSelectCorner"/>
                <jsp:param name="targetStoreId" value="cornerMonthSelectStore"/>
                <jsp:param name="closeFunc" value="closeSelectCorner"/>
            </jsp:include>
        </td>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt10 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="cornerMonthListScaleBox"
            ng-model="cornerMonthListScale"
            items-source="_getComboData('cornerMonthListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
    </wj-combo-box>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="text" id="cornerMonthSelectStoreStoreNum" ng-model="storeNum">
    </c:if>
    <%-- 엑셀 다운로드 //TODO --%>
    <button class="btn_skyblue fr" ng-click="excelDownloadMonth()"><s:message code="cmm.excel.down" />
    </button>
  </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType3">
        <div class="wj-gridWrap">
        <wj-flex-grid
          id="cornrMonthGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          frozen-columns="3"
          item-formatter="_itemFormatter">
          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="corner.saleYm"/>"            binding="saleYm"            width="100" align="center" is-read-only="true" format="yyyy/MM"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="corner.totRealSaleAmt"/>"    binding="totRealSaleAmt"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="corner.totSaleQty"/>"        binding="totSaleQty"        width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="cornerMonthCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
   <%-- id --%>
    <ul id="cornerMonthCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
  
    <%--엑셀 리스트--%>
    <div class="w100 mt10" id="wjWrapType3" style="display: none" ng-controller="cornerMonthExcelCtrl">
        <div class="wj-gridWrap">
        <wj-flex-grid
          id="cornrMonthExcelGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="excelFlex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          frozen-columns="3"
          item-formatter="_itemFormatter">
          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="corner.saleYm"/>"            binding="saleYm"            width="100" align="center" is-read-only="true" format="yyyy/MM"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="corner.totRealSaleAmt"/>"    binding="totRealSaleAmt"    width="150" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="corner.totSaleQty"/>"        binding="totSaleQty"        width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/corner/month/month.js?ver=20200125.01" charset="utf-8"></script>