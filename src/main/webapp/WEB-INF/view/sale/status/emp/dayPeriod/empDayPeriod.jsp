<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="empDayPeriodView" class="subCon"  ng-controller="empDayPeriodCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="empsale.dayPeriod"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnEmpDayPeriodSearch" ng-click="_broadcast('empDayPeriodMainCtrlSrch')">
        <s:message code="cmm.search"/>
      </button>
    </div>
    <table class="searchTbl">
      <colgroup>
        	<col class="w13"/>
	        <col class="w37"/>
	        <col class="w13"/>
	        <col class="w37"/>
      	</colgroup>
      <tbody>
      <tr>
	    	<th><s:message code="cmm.search.date" /></th>
        	<td colspan="3">
          	<div class="sb-select">
       		    <span class="txtIn"><input id="srchEmpDayPeriodStartDate" class="w120px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchEmpDayPeriodEndDate" class="w120px"></span>
            	<span class="chk ml10">
					<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
	              	<label for="chkDt">
                		<s:message code="cmm.all.day" />
              		</label>
            	</span>
          	</div>
        	</td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
      	<tr>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td colspan="3">
            <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="empDayPeriodSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="empDayPeriodSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <div id="gridRepresent" class="w50 fl" style="width:49%;">
     <%-- 판매자별 --%>
     <div class="w100 mt10" ng-controller="empDayPeriodMainCtrl">
       <div class="oh sb-select mb10">
         <span class="fl bk lh30"><s:message code='empsale.empsale'/></span>

         <%-- 페이지 스케일  --%>
<!--         <wj-combo-box -->
<!--                 class="w100px fl" -->
<!--                 id="empDayPeriodDtlListScaleBox" -->
<!--                 ng-model="empDayPeriodDtlListScale" -->
<!--                 items-source="_getComboData('empDayPeriodDtlListScaleBox')" -->
<!--                 display-member-path="name" -->
<!--                 selected-value-path="value" -->
<!--                 is-editable="false" -->
<!--                 initialized="initComboBox(s)"> -->
<!--         </wj-combo-box> -->
         <%-- 엑셀다운로드 --%>
         <button class="btn_skyblue fr" ng-click="excelDownloadDayPeriod()"><s:message code="cmm.excel.down" />
         </button>
       </div>
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 350px;">
        <wj-flex-grid
          id="empDayPeriodGrid"
          loaded-rows="loadedRows(s,e)"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>"              binding="storeNm"           width="200" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empsale.emp"/>"              binding="empNm"             width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empday.billCnt"/>"           binding="billCnt"           width="150" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empday.realSaleAmt"/>"       binding="realSaleAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="empDayPeriodMainCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>

      <%--페이지 리스트--%>
      <div class="pageNum mt20">
      	<%-- id --%>
      	<ul id="empDayPeriodMainCtrlPager" data-size="10"></ul>
      </div>
      <%--//페이지 리스트--%>
     </div>
    </div>


    <div id="gridDetail" class="w50 fr" style="width:49%;">
     <%-- 코너별 --%>
     <div class="w100 mt10" ng-controller="empDayPeriodDtlCtrl">
       <div class="oh sb-select mb10">
         <span class="fl bk lh30"><s:message code='corner.SaleDtl'/></span>

         <%-- 페이지 스케일  --%>
<!--         <wj-combo-box -->
<!--                 class="w100px fl" -->
<!--                 id="empDayPeriodDtlListScaleBox" -->
<!--                 ng-model="empDayPeriodDtlListScale" -->
<!--                 items-source="_getComboData('empDayPeriodDtlListScaleBox')" -->
<!--                 display-member-path="name" -->
<!--                 selected-value-path="value" -->
<!--                 is-editable="false" -->
<!--                 initialized="initComboBox(s)"> -->
<!--         </wj-combo-box> -->
         <%-- 코너별 매출 상세 엑셀다운로드 --%>
         <button class="btn_skyblue fr" ng-click="excelDownloadDayPeriodDtl()"><s:message code="cmm.excel.down" />
         </button>
       </div>
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 350px;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="empday.saleDate"/>"          binding="saleDate"         width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleComPopup.posNo"/>"       binding="posNo"            width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleComPopup.billNo"/>"      binding="billNo"           width="100" align="center" is-read-only="true" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleComPopup.saleFg"/>"      binding="saleYn"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleComPopup.totSaleAmt"/>"  binding="totSaleAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="billInfo.dcAmt"/>"           binding="totDcAmt"         width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empday.realSaleAmt"/>"       binding="realSaleAmt"      width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="empDayPeriodDtlCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>

      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
      	<%-- id --%>
      	<ul id="empDayPeriodDtlCtrlPager" data-size="10"></ul>
      </div>
      <%--//페이지 리스트--%>
     </div>
    </div>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/emp/dayPeriod/empDayPeriod.js?ver=20190125.02" charset="utf-8"></script>

<%-- 상품매출내역 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/prod.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>