<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="empDayPeriodView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="empDayPeriodCtrl">
    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="empsale.dayPeriod"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnEmpDayPeriodSearch" ng-click="_broadcast('empDayPeriodMainCtrlSrch')">
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
            <th><s:message code="cmm.search.date" /></th>
        	<td colspan="3">
          	<div class="sb-select">
       		    <span class="txtIn"><input id="srchEmpDayPeriodStartDate" class="w110px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchEmpDayPeriodEndDate" class="w110px"></span>
            	<span class="chk ml10" style="display: none;">
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
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="empDayPeriodSelectStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
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
    <div class="w100 mt10" id="wjWrapType1">
      <div class="wj-gridWrap col2-t2" style="height:380px;">
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
      </div>
      <%--//위즈모 테이블--%>

      <%--페이지 리스트--%>
      <div class="pageNum3 mt20">
      	<%-- id --%>
      	<ul id="empDayPeriodMainCtrlPager" data-size="10"></ul>
      </div>
      <%--//페이지 리스트--%>
      <%-- 엑셀 리스트 --%>
      <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="empDayPeriodExcelCtrl">
        <div class="wj-gridWrap">
          <wj-flex-grid
          id="empDayPeriodExcelGrid"
          loaded-rows="loadedRows(s,e)"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="excelFlex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>"              binding="storeNm"           width="200" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empsale.emp"/>"              binding="empNm"             width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empday.billCnt"/>"           binding="billCnt"           width="150" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="empday.realSaleAmt"/>"       binding="realSaleAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        </div>
  	 </div>
  	 <%--//엑셀 리스트 --%>    
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
    <div class="w100 mt10" id="wjWrapType1">
      <div class="wj-gridWrap col2-t2" style="height:380px;">
        <wj-flex-grid
          id="empDayPeriodDtlGrid"
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
          <wj-flex-grid-column header="<s:message code="saleComPopup.tblCd"/>"       binding="tblCd"            width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="saleComPopup.billDt"/>"      binding="billDt"           width="130" align="center" is-read-only="true"></wj-flex-grid-column>
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
      </div>
      <%--//위즈모 테이블--%>

      <%-- 페이지 리스트 --%>
      <div class="pageNum3 mt20">
      	<%-- id --%>
      	<ul id="empDayPeriodDtlCtrlPager" data-size="10"></ul>
      </div>
      <%--//페이지 리스트--%>
  	  <%-- 엑셀 리스트 --%>
      <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="empDayPeriodDtlExcelCtrl">
        <div class="wj-gridWrap">
        <wj-flex-grid
          id="empDayPeriodDtlExcelGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="excelFlex"
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
        </div>
  	 </div>
  	 <%--//엑셀 리스트 --%>
     </div>
    </div>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/emp/dayPeriod/empDayPeriod.js?ver=20250415.01" charset="utf-8"></script>

