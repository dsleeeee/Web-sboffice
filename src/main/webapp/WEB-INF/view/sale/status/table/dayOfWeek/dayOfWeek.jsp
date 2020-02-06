<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/status/table/"/>

<div id="tableDayOfWeekView" class="subCon" ng-controller="tableDayOfWeekCtrl">
  <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="tableDayOfWeek.tableDayOfWeekSale"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('tableDayOfWeekCtrl')">
        <s:message code="cmm.search"/>
      </button>
    </div>
    <table class="searchTbl">
      <colgroup>
        <col class="w25"/>
        <col class="w75"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 조회일자 --%>
        <th><s:message code="cmm.search.date"/></th>
        <td>
          <div class="sb-select">
          <span class="txtIn w110px">
              <wj-input-date
                      id="srchTableDayOfWeekStartDate"
                      value="tableDayOfWeekStartDate"
                      ng-model="startDate"
                      control="tableDayOfWeekStartDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
            <span class="rg">~</span>
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchTableDayOfWeekEndDate"
                      value="tableDayOfWeekEndDate"
                      ng-model="endDate"
                      control="tableDayOfWeekEndDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
        </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
            <%-- 매장코드 --%>
          <th><s:message code="todayDtl.store"/></th>
          <td>
              <%-- 매장선택 모듈 싱글 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                closeFunc - 팝업 닫기시 호출할 함수
              --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
              <jsp:param name="targetId" value="tableDayOfWeekSelectStore"/>
              <jsp:param name="closeFunc" value="getStoreTableList"/>
            </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="tableDayOfWeekSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      <tr>
        <%-- 테이블 표시 --%>
      <th><s:message code="tableDay.table"/></th>
        <td>
          <%-- 테이블 선택 --%>
        <span class="txtIn w150px sb-select">
            <wj-combo-box
              id="srchTableDayOfWeekTableNo"
              ng-model="tableNo"
              control="tableDayOfWeekTableNoCombo"
              items-source="_getComboData('srchTableDayOfWeekTableNo')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
          <span class="chk ml10">
          	<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" class="ng-pristine ng-valid ng-empty ng-touched">
          	<label for="chkDt">전체 테이블</label>
          </span>
        </td>
      </tr>
      </tbody>
    </table>

	<div class="mt40 oh sb-select dkbr">
		<div class="sb-select dkbr">
			<%-- 페이지 스케일  --%>
			<wj-combo-box class="w100px fl" id="listScaleBox"
				ng-model="listScale" control="listScaleCombo"
				items-source="_getComboData('listScaleBox')"
				display-member-path="name" selected-value-path="value"
				is-editable="false" initialized="_initComboBox(s)">
			</wj-combo-box>
			<%--// 페이지 스케일  --%>
		</div>
		<c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<input type="text" id="tableDayOfWeekSelectStoreStoreNum" ng-model="storeNum">
		</c:if>
		<%-- 엑셀다운로드 --%>
		<button class="btn_skyblue fr" ng-click="excelDownloadTableDayOfWeek()">
			<s:message code="cmm.excel.down" />
		</button>
	</div>

    <%-- 테이블 일자별 매출 --%>

      <div class="w100 mt10">
        <div class="wj-TblWrapBr">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap" style="height: 350px;">
            <wj-flex-grid
              id="tableDayOfWeekGrid"
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">
              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="tableDayOfWeek.saleDay"/>" binding="saleDay" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDayOfWeek.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDayOfWeek.totRealSaleCnt"/>" binding="totRealSaleCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="tableDayOfWeek.totGuestCnt"/>" binding="totGuestCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="tableDayCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
          <%--//위즈모 테이블--%>
		<div class="pageNum mt20">
			<%-- id --%>
			<ul id="tableDayOfWeekCtrlPager" data-size="10">
			</ul>
		</div>
        </div>
      </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/table/dayOfWeek/dayOfWeek.js?ver=20190122.04" charset="utf-8"></script>
<%-- 상품매출내역 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/table.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>


