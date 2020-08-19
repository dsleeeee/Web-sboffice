<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="goalMonthView" class="subCon3"  ng-controller="goalMonthCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="goal.goalMonth.versusGoalSaleAnals"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnGoalMonthSearch" ng-click="_broadcast('goalMonthCtrlSrch')">
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
                      id="srchGoalMonthStartDate"
                      ng-model="startDate"
                      control="goalMonthStartDateCombo"
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
                      id="srchGoalMonthEndDate"
                      ng-model="endDate"
                      control="goalMonthEndDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)"
                      selection-mode="Month" 
                      format="y">
              </wj-input-date>
            </span>
            <span class="chk ml10">
                <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                <label for="chkDt">
                    <s:message code="cmm.all.day" />
                </label>
            </span>  
        </div>
        </td>
        
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="goalMonthSelectStoreCd" valaue=""/>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="goalMonthSelectStore"/>
            </jsp:include>
        </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">  
            <input type="hidden" id="goalMonthSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>
    
    <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="goalMonthListScaleBox"
            ng-model="goalMonthListScale"
            items-source="_getComboData('goalMonthListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="initComboBox(s)"
            control="conListScale"
			is-editable="true"
			text-changed="_checkValidation(s)">
    </wj-combo-box>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="text" id="goalMonthSelectStoreStoreNum" ng-model="storeNum">
    </c:if>
    <%-- 엑셀 다운로드 //TODO --%>
    <button class="btn_skyblue fr" ng-click="excelDownloadGoalMonth()"><s:message code="cmm.excel.down" />
    </button>
  </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap2">
        <wj-flex-grid
          id="cornrDayGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">
          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.mrhst.nm"/>"                     binding="storeNm"            width="200" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="goal.goalMonth.saleMonth"/>"         binding="saleGoalYm"         width="150" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="goal.goalMonth.monthDay"/>"          binding="saleGoalDateCnt"    width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="goal.goalMonth.saleCnt"/>"           binding="saleDateCnt"        width="100" align="center"  is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="goal.goalDay.monthGoalAmt"/>"        binding="saleGoalMonthlyAmt" width="150" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="goal.goalMonth.dayTotSale"/>"        binding="saleGoalAmt"        width="150" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="goal.goalMonth.realSaleAmt"/>"       binding="totSaleAmt"         width="200" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="goal.goalMonth.month"/>"             binding="goalAchiMonthly"    width="150" align="right"  is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="goal.goalMonth.dayTot"/>"            binding="goalAchi"           width="150" align="right"  is-read-only="true" ></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="goalMonthCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    
  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="goalMonthCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
  
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap2" style="display: none;" ng-controller="goalMonthExcelCtrl">
      <wj-flex-grid
        id="goalMonthExcelGrid"
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="excelFlex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter">
        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.mrhst.nm"/>"                     binding="storeNm"            width="200" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="goal.goalMonth.saleMonth"/>"         binding="saleGoalYm"         width="150" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="goal.goalMonth.monthDay"/>"          binding="saleGoalDateCnt"    width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="goal.goalMonth.saleCnt"/>"           binding="saleDateCnt"        width="100" align="center"  is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="goal.goalDay.monthGoalAmt"/>"        binding="saleGoalMonthlyAmt" width="150" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="goal.goalMonth.dayTotSale"/>"        binding="saleGoalAmt"        width="150" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="goal.goalMonth.realSaleAmt"/>"       binding="totSaleAmt"         width="200" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="goal.goalMonth.month"/>"             binding="goalAchiMonthly"    width="150" align="right"  is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="goal.goalMonth.dayTot"/>"            binding="goalAchi"           width="150" align="right"  is-read-only="true" ></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/anals/goal/month/goalMonth.js" charset="utf-8"></script>

