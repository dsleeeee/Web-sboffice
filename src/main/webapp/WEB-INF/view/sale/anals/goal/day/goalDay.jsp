<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="goalDayView" class="subCon3"  ng-controller="goalDayCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="goal.goalDay.versusGoalSaleAnals"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnGoalDaySearch" ng-click="_broadcast('goalDayCtrlSrch')">
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
                      id="srchGoalDayStartDate"
                      ng-model="startDate"
                      control="goalDayStartDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)"
                      selection-mode="Month" 
                      format="y">
              </wj-input-date>
            </span>
        </div>
        </td>
        
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="goalDaySelectStoreCd" valaue=""/>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="goalDaySelectStore"/>
            </jsp:include>
        </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">  
            <input type="hidden" id="goalDaySelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>
    
    <div class="mt10 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="goalDayListScaleBox"
            ng-model="goalDayListScale"
            items-source="_getComboData('goalDayListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="initComboBox(s)"
            control="conListScale"
			is-editable="true"
			text-changed="_checkValidation(s)">
    </wj-combo-box>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="text" id="goalDaySelectStoreStoreNum" ng-model="storeNum">
    </c:if>
    <%-- 엑셀 다운로드 //TODO --%>
    <button class="btn_skyblue fr ml10" ng-click="excelDownloadGoalDay()"><s:message code="cmm.excel.down" />
    </button>
    <%-- 매출목표 등록 //TODO --%>
    <button class="btn_skyblue fr" ng-click="saleGoalReg()"><s:message code="goal.goalDay.saleGoalReg" />
    </button>
  </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap2" style="overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          id="goalDayGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          frozen-columns="2"
          item-formatter="_itemFormatter">
          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="goal.goalDay.saleDate"/>"          binding="saleGoalDate"          width="200" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="goal.goalDay.yoil"/>"              binding="saleGoalDy"            width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="goalDayCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    
  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="goalDayCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
  
    <%-- 엑셀 리스트 --%>
    <div class="wj-gridWrap2" style="display: none;" ng-controller="goalDayExcelCtrl">
     <wj-flex-grid
       id="goalDayExcelGrid"
       autoGenerateColumns="false"
       selection-mode="Row"
       items-source="data"
       control="excelFlex"
       initialized="initGrid(s,e)"
       is-read-only="true"
       frozen-columns="2"
       item-formatter="_itemFormatter">
       <!-- define columns -->
       <wj-flex-grid-column header="<s:message code="goal.goalDay.saleDate"/>"          binding="saleGoalDate"          width="200" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
       <wj-flex-grid-column header="<s:message code="goal.goalDay.yoil"/>"              binding="saleGoalDy"            width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
     </wj-flex-grid>
   </div>
   <%-- //엑셀 리스트 --%>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/anals/goal/day/goalDay.js" charset="utf-8"></script>

<%-- 팝업 레이어 시작 --%>
<%-- 매장현황 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/anals/goal/goalPop/goalPop.jsp">
	<c:param name="menuCd" value="${menuCd}"/>
	<c:param name="menuNm" value="${menuNm}"/>
</c:import>
