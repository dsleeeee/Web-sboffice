<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/frnchs/order/order/"/>

<div class="subCon3">
  <div ng-controller="orderCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('orderMainCtrlSrch')">
      <%--<button class="btn_blue fr" id="btnSearch" ng-click="fnSearch()">--%>
      <s:message code="cmm.search"/></button>
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
        <th>
            <div class="sb-select">
               <span class="txtIn">
                    <wj-combo-box
                      id="srchOrderOutDateFgDisplay"
                      ng-model="outDateFgModel"
                      items-source="_getComboData('srchOrderOutDateFgDisplay')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                </span>
             </div>
        </th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"><input id="srchOrderStartDate" class="w120px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchOrderEndDate" class="w120px"></span>
          </div>
        </td>
      </tr>
      <tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
             <input type="hidden" id="orderSelectStoreCd" value="" />
             <%-- 매장코드 --%>
             <th style="border-left: 1px solid #ccc"><s:message code="orderStockInfo.storeNm" /></th>
             <td>
                 <%-- 매장선택 모듈 싱글 선택 사용시 include
                     param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                     displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                     modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                     closeFunc - 팝업 닫기시 호출할 함수            --%>
                 <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                     <jsp:param name="targetId" value="orderSelectStore" />
                 </jsp:include> <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
             </td>
         </c:if>
         <c:if test="${sessionInfo.orgnFg == 'STORE'}">
             <input type="hidden" id="orderSelectStoreCd" value="${sessionInfo.storeCd}" />
         </c:if>
        <%-- 전표구분 --%>
        <th <c:if test="${sessionInfo.orgnFg == 'STORE'}">style="border-left: 1px solid #ccc"</c:if>><s:message code="orderStockInfo.slipFg" /></th>
        <td <c:if test="${sessionInfo.orgnFg == 'STORE'}">colspan="3"</c:if>>
          <div class="sb-select">
              <span class="txtIn">
                    <wj-combo-box
                      id="srchOrderSlipFgDisplay"
                      ng-model="slipFgModel"
                      items-source="_getComboData('srchOrderSlipFgDisplay')"
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
        <%-- 진행상태 --%>
        <th><s:message code="orderStockInfo.procFg" /></th>
        <td colspan="3">
          <div class="sb-select">
              <span class="txtIn">
                    <wj-combo-box
                      id="srchOrderProcFgDisplay"
                      ng-model="procFgModel"
                      items-source="_getComboData('srchOrderProcFgDisplay')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                </span>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="wj-TblWrap" style="overflow-y: hidden; overflow-x: hidden;">

      <div class="w100 mt20" ng-controller="orderMainCtrl">
	    <div class="mt20 oh sb-select dkbr">
	        <%-- 페이지 스케일  --%>
	        <wj-combo-box
	                class="w100px fl"
	                id="orderMainListScaleBox"
	                ng-model="orderMainListScale"
	                items-source="_getComboData('orderMainListScaleBox')"
	                display-member-path="name"
	                selected-value-path="value"
	                initialized="initComboBox(s)"
	                control="conListScale"
					is-editable="true"
					text-changed="_checkValidation(s)">
	        </wj-combo-box>
	        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	            <input type="text" id="orderMainSelectStoreStoreNum" ng-model="storeNum">
	        </c:if>
	        <%-- 엑셀 다운로드 //TODO --%>
	        <button class="btn_skyblue fr" ng-click="excelDownloadOrder()"><s:message code="cmm.excel.down" />
	        </button>
	    </div>
        <div id="wjWrapType2" class="w100 mt10">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap">
            <wj-flex-grid
              loaded-rows="loadedRows(s,e)"
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="orderStockInfo.outstockResveDate"/>"    binding="reqDateFm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                  <wj-flex-grid-column header="<s:message code="orderStockInfo.storeNm"/>"          binding="storeNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
              </c:if>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.slipFg"/>"               binding="slipFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.procFg"/>"               binding="procFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.slipNo"/>"               binding="slipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.outDate"/>"              binding="outDtFm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.inDate"/>"               binding="inDtFm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.prodCnt"/>"              binding="dtlCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.qty"/>"                  binding="orderTotQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.totOrderAmt"/>"          binding="orderTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.qty"/>"                  binding="outTotQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.totOrderAmt"/>"          binding="outTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.qty"/>"                  binding="inTotQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.totOrderAmt"/>"          binding="inTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.penaltyAmt"/>"           binding="penaltyAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              
              <wj-flex-grid-column header=""    binding="reqDate" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header=""    binding="outDt" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header=""    binding="inDt" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="orderMainCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
          <%--//위즈모 테이블--%>
        </div>
        <%-- 페이지 리스트 --%>
		<div class="pageNum mt20">
		  <ul id="orderMainCtrlPager" data-size="10">
		  </ul>
		</div>
		<%--//페이지 리스트--%>
      </div>

      <div style="clear: both"></div>

      <div class="w100 mt20" ng-controller="orderDtlCtrl">
        <div class="oh sb-select mb10 dkbr">
          <%-- 페이지 스케일  --%>
          <wj-combo-box
                  class="w100px fl"
                  id="orderDtlListScaleBox"
                  ng-model="orderDtlListScale"
                  items-source="_getComboData('orderDtlListScaleBox')"
                  display-member-path="name"
                  selected-value-path="value"
                  initialized="initComboBox(s)"
                  control="conListScale"
			      is-editable="true"
			      text-changed="_checkValidation(s)">
          </wj-combo-box>
          
          <span class="fl bk lh30 ml10" id="dtlSlipNo"></span>
          
	      <%-- 주문대비 입고현황 상품상세 엑셀다운로드 --%>
          <button class="btn_skyblue fr ml20 mt5" ng-click="excelDownloadOrderDtl()">
              <s:message code="cmm.excel.down" />
          </button>
            
          <span class="fr bk sb-radio mt10">
              <label><input type="radio" name="displayFg" id="displayAll" value="all"  ng-click="displayChg()"><s:message code="cmm.all" /></label>
              <label><input type="radio" name="displayFg" id="displayCntSum" value="cntSum" checked="checked" ng-click="displayChg()"><s:message code="orderStockInfo.dtl.inTotQty+inTot" /></label>
          </span>
        </div>
        <div id="wjWrapType2" class="w100 mt10">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap">
            <wj-flex-grid
              id="orderDtlGrid"
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="orderStockInfo.outstockResveDate"/>"    binding="reqDateFm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.prodCd"/>"           binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.prodNm"/>"           binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.poUnitFg"/>"         binding="poUnitFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.poUnitQty"/>"        binding="poUnitQty" width="40" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.outSplyUprc"/>"      binding="splyUprc" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.fg"/>"               binding="slipFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTotQty"/>"         binding="orderTotQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inAmt"/>"            binding="orderAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inVat"/>"            binding="orderVat" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTot"/>"            binding="orderTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTotQty"/>"         binding="mdTotQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inAmt"/>"            binding="mdAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inVat"/>"            binding="mdVat" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTot"/>"            binding="mdTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTotQty"/>"         binding="outTotQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inAmt"/>"            binding="outAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inVat"/>"            binding="outVat" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTot"/>"            binding="outTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTotQty"/>"         binding="inTotQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inAmt"/>"            binding="inAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inVat"/>"            binding="inVat" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTot"/>"            binding="inTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.penaltyAmt"/>"       binding="penaltyAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.remark"/>"           binding="remark" width="80"></wj-flex-grid-column>
              
              <wj-flex-grid-column header=""    binding="reqDate" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header=""    binding="poUnitFg" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="orderDtlCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
          <%--//위즈모 테이블--%>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <ul id="orderDtlCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
      </div>
    </div>
  </div>
  
  
  <%--엑셀 리스트1--%>
  <div class="wj-gridWrap" style="display: none" ng-controller="orderExcelCtrl">
    <wj-flex-grid
      id="orderExcelGrid"
      autoGenerateColumns="false"
      selection-mode="Row"
      items-source="data"
      control="excelFlex"
      initialized="initGrid(s,e)"
      is-read-only="true"
      item-formatter="_itemFormatter">

      <!-- define columns -->
      <wj-flex-grid-column header="<s:message code="orderStockInfo.outstockResveDate"/>"    binding="reqDateFm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <wj-flex-grid-column header="<s:message code="orderStockInfo.storeNm"/>"          binding="storeNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
      </c:if>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.slipFg"/>"               binding="slipFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.procFg"/>"               binding="procFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.slipNo"/>"               binding="slipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.outDate"/>"              binding="outDtFm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.inDate"/>"               binding="inDtFm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.prodCnt"/>"              binding="dtlCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.qty"/>"                  binding="orderTotQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.totOrderAmt"/>"          binding="orderTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.qty"/>"                  binding="outTotQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.totOrderAmt"/>"          binding="outTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.qty"/>"                  binding="inTotQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.totOrderAmt"/>"          binding="inTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.penaltyAmt"/>"           binding="penaltyAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header=""    binding="reqDate" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header=""    binding="outDt" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header=""    binding="inDt" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
    </wj-flex-grid>
  </div>
  
  <%--엑셀 리스트2--%>
  <div class="wj-gridWrap" style="display: none" ng-controller="orderDtlExcelCtrl">
    <wj-flex-grid
      id="orderDtlExcelGrid"
      autoGenerateColumns="false"
      selection-mode="Row"
      items-source="data"
      control="excelFlex"
      initialized="initGrid(s,e)"
      is-read-only="true"
      item-formatter="_itemFormatter">

      <!-- define columns -->
      <wj-flex-grid-column header="<s:message code="orderStockInfo.outstockResveDate"/>"    binding="reqDateFm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.prodCd"/>"           binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.prodNm"/>"           binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.poUnitFg"/>"         binding="poUnitFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.poUnitQty"/>"        binding="poUnitQty" width="40" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.outSplyUprc"/>"      binding="splyUprc" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.fg"/>"               binding="slipFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTotQty"/>"         binding="orderTotQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inAmt"/>"            binding="orderAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inVat"/>"            binding="orderVat" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTot"/>"            binding="orderTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTotQty"/>"         binding="mdTotQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inAmt"/>"            binding="mdAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inVat"/>"            binding="mdVat" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTot"/>"            binding="mdTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTotQty"/>"         binding="outTotQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inAmt"/>"            binding="outAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inVat"/>"            binding="outVat" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTot"/>"            binding="outTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTotQty"/>"         binding="inTotQty" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inAmt"/>"            binding="inAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inVat"/>"            binding="inVat" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.inTot"/>"            binding="inTot" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.penaltyAmt"/>"       binding="penaltyAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="orderStockInfo.dtl.remark"/>"           binding="remark" width="80"></wj-flex-grid-column>
      <wj-flex-grid-column header=""    binding="reqDate" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header=""    binding="poUnitFg" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

    </wj-flex-grid>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/frnchs/order/order.js" charset="utf-8"></script>

<%-- 입고 상세 팝업 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/orderStockInfo/prodInstockInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
