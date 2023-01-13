<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="orderEmpPeriodView" class="subCon" style="display: none;" ng-controller="orderEmpPeriodCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="orderEmp.orderEmpPeriod"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnOrderEmpPeriodSearch" ng-click="_broadcast('orderEmpPeriodMainCtrlSrch')">
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
	    	<th><s:message code="cmm.search.date" /></th>
        	<td colspan="3">
          	<div class="sb-select">
       		    <span class="txtIn"><input id="srchOrderEmpPeriodStartDate" class="w110px"></span>
                <span class="rg">~</span>
                <span class="txtIn"><input id="srchOrderEmpPeriodEndDate" class="w110px"></span>
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
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="orderEmpPeriodSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="orderEmpPeriodSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <div id="gridRepresent" class="w50 fl" style="width:49%;">
     <%-- 판매자별 --%>
     <div class="w100 mt20" ng-controller="orderEmpPeriodMainCtrl">
       <div class="oh sb-select mb10">
         <span class="fl bk lh30"><s:message code='orderEmp.orderEmp'/></span>

         <%-- 엑셀다운로드 --%>
         <button class="btn_skyblue fr" ng-click="excelDownloadDayPeriod()"><s:message code="cmm.excel.down" />
         </button>
       </div>
      <%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType1">
      <div class="wj-gridWrap col2-t2">
        <wj-flex-grid
          id="orderEmpPeriodGrid"
          loaded-rows="loadedRows(s,e)"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>"             binding="storeNm"           width="130" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.orderEmp2"/>"      binding="empNm"             width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.billCnt"/>"        binding="billCnt"           width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.realSaleAmt"/>"    binding="realSaleAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="orderEmpPeriodMainCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      </div>
      <%--//위즈모 테이블--%>

      <%--페이지 리스트--%>
      <div class="pageNum3 mt20">
      	<%-- id --%>
      	<ul id="orderEmpPeriodMainCtrlPager" data-size="10"></ul>
      </div>
      <%--//페이지 리스트--%>
      <%-- 엑셀 리스트 --%>
      <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="orderEmpPeriodExcelCtrl">
        <div class="wj-gridWrap">
          <wj-flex-grid
          id="orderEmpPeriodExcelGrid"
          loaded-rows="loadedRows(s,e)"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="excelFlex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>"             binding="storeNm"           width="130" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.orderEmp2"/>"      binding="empNm"             width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.billCnt"/>"        binding="billCnt"           width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.realSaleAmt"/>"    binding="realSaleAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        </div>
  	 </div>
  	 <%--//엑셀 리스트 --%>    
     </div>
    </div>


    <div id="gridDetail" class="w50 fr" style="width:49%;">
     <%-- 코너별 --%>
     <div class="w100 mt20" ng-controller="orderEmpPeriodDtlCtrl">
       <div class="oh sb-select mb10">
         <span class="fl bk lh30"><s:message code='orderEmp.saleDtl'/></span>

         <%-- 코너별 매출 상세 엑셀다운로드 --%>
         <button class="btn_skyblue fr" ng-click="excelDownloadDayPeriodDtl()"><s:message code="cmm.excel.down" />
         </button>
       </div>
      <%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType1">
      <div class="wj-gridWrap col2-t2">
        <wj-flex-grid
          id="orderEmpPeriodDtlGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="orderEmp.saleDate"/>"    binding="saleDate"         width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.posNo"/>"       binding="posNo"            width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.billNo"/>"      binding="billNo"           width="80" align="center" is-read-only="true" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.saleFg"/>"      binding="saleYn"           width="60" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.tblCd"/>"       binding="tblCd"            width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.billDt"/>"      binding="billDt"           width="130" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.totSaleAmt"/>"  binding="totSaleAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.dcAmt"/>"       binding="totDcAmt"         width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="orderEmp.realSaleAmt"/>" binding="realSaleAmt"      width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="orderEmpPeriodDtlCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      </div>
      <%--//위즈모 테이블--%>

      <%-- 페이지 리스트 --%>
      <div class="pageNum3 mt20">
      	<%-- id --%>
      	<ul id="orderEmpPeriodDtlCtrlPager" data-size="10"></ul>
      </div>
      <%--//페이지 리스트--%>

  	  <%-- 엑셀 리스트 --%>
      <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="orderEmpPeriodDtlExcelCtrl">
        <div class="wj-gridWrap">
            <wj-flex-grid
              id="orderEmpPeriodDtlExcelGrid"
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="excelFlex"
              initialized="initGrid(s,e)"
              is-read-only="false"
              item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="orderEmp.saleDate"/>"    binding="saleDate"         width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderEmp.posNo"/>"       binding="posNo"            width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderEmp.billNo"/>"      binding="billNo"           width="80" align="center" is-read-only="true" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderEmp.saleFg"/>"      binding="saleYn"           width="60" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderEmp.tblCd"/>"       binding="tblCd"            width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderEmp.billDt"/>"      binding="billDt"           width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderEmp.totSaleAmt"/>"  binding="totSaleAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderEmp.dcAmt"/>"       binding="totDcAmt"         width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="orderEmp.realSaleAmt"/>" binding="realSaleAmt"      width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        </div>
  	 </div>
  	 <%--//엑셀 리스트 --%>
     </div>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/orderEmp/orderEmpPeriod.js?ver=20220610.01" charset="utf-8"></script>