<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/status/storeday/storeMonth/"/>

<!-- contents start -->
<div class="subCon3" ng-controller="storeMonthCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>

      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeMonthMainCtrlSrch')">
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
        <td>
          <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchStoreMonthStartDate"
                      ng-model="startDate"
                      control="storeMonthStartDateCombo"
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
	        <input type="hidden" id="storeMonthSelectStoreCd" valaue=""/>
	        <%-- 매장코드 --%>
	        <th><s:message code="storeMonth.storeNm"/></th>
	        <td>
	            <%-- 매장선택 모듈 싱글 선택 사용시 include
	               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
	                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
	                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
	                            closeFunc - 팝업 닫기시 호출할 함수
	            --%>
	            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
	                <jsp:param name="targetId" value="storeMonthSelectStore"/>
	            </jsp:include>
	        </td>
        </c:if>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="storeMonth.prodCd"/></th>
        <td><input type="text" class="sb-input w100" id="srchProdCd" ng-model="srchProdCd" onkeyup="fnNxBtnSearch();"/></td>

        <%-- 상품명 --%>
        <th><s:message code="storeMonth.prodNm"/></th>
        <td><input type="text" class="sb-input w100" id="srchProdNm" ng-model="srchProdNm" onkeyup="fnNxBtnSearch();"/></td>
      </tr>
      <tr>
        <%-- 바코드 --%>
        <th><s:message code="storeMonth.barcdCd"/></th>
        <td><input type="text" class="sb-input w100" id="srchBarcdCd" ng-model="srchBarcdCd" onkeyup="fnNxBtnSearch();"/></td>

        <%-- 상품분류 --%>
        <th><s:message code="storeMonth.prodClass"/></th>
        <td>
          <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                 placeholder="<s:message code="cmm.all" />" readonly/>
          <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
          <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
        </td>

      </tr>
      <tr>
        <%-- 단위구분 --%>
        <th><s:message code="storeMonth.unitFg"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn">
                <wj-combo-box
                  id="srchUnitFgDisplay"
                  ng-model="unitFg"
                  items-source="_getComboData('srchUnitFgDisplay')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
            </span>
          </div>
        </td>

        <%-- 조회옵션 --%>
        <th><s:message code="storeMonth.srchOption"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn">
                <wj-combo-box
                  id="srchOptionDisplay"
                  ng-model="srchOption"
                  items-source="_getComboData('srchOptionDisplay')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
            </span>
            <span class="chk ml10">
                <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()"/>
                <label for="chkDt">
                    <s:message code="storeMonth.prodClassDisplay" />
                </label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 거래처 --%>
        <th style="display: none;"><s:message code="storeMonth.vendr"/></th>
        <td style="display: none;">
          <%-- 거래처선택 모듈 멀티 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
            <jsp:param name="targetId" value="storeMonthSelectVendr"/>
          </jsp:include>
          <input type="hidden" id="storeMonthSelectVendrCd" value=""/>
        </td>
      </tr>
      </tbody>
    </table>

    <input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="storeCd" value="${sessionInfo.storeCd}"/>
        <input type="hidden" id="storeNm" value="${sessionInfo.storeNm}"/>
    </c:if>

    <div ng-controller="storeMonthMainCtrl">
	    <div class="mt20 oh sb-select dkbr">
	       <%-- 페이지 스케일  --%>
          <wj-combo-box
            class="w100px fl"
            id="storeMonthMainlistScaleBox"
            ng-model="listScale"
            items-source="_getComboData('storeMonthMainlistScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="_initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
          </wj-combo-box>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
              <input type="text" id="storeMonthMainSelectStoreStoreNum" ng-model="storeNum">
          </c:if>
	      <%-- 엑셀 다운로드 //TODO --%>
	      <button class="btn_skyblue fr" ng-click="excelDownloadStoreMonth()"><s:message code="cmm.excel.down" />
	      </button>
	    </div>

	    <%-- gird 1 --%>
	    <div class="w100 mt10" id="wjWrapType2">
	        <div class="wj-gridWrap">
	            <wj-flex-grid
	              id="storeMonthMainGrid"
	              loaded-rows="loadedRows(s,e)"
	              autoGenerateColumns="false"
	              selection-mode="Row"
	              items-source="data"
	              control="flex"
	              initialized="initGrid(s,e)"
	              is-read-only="true"
	              item-formatter="_itemFormatter"
	              frozen-columns="5">

	              <!-- define columns -->
	              <wj-flex-grid-column header="<s:message code="storeMonth.prodClassNm"/>"                binding="prodClassNm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="storeMonth.prodCd"/>"                      binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.prodNm"/>"                      binding="prodNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="storeMonth.storeCd"/>"                     binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="storeMonth.storeNm"/>"                     binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.poUnitQty"/>"                   binding="poUnitQty" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.poUnitFg"/>"                    binding="poUnitFgNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.barcdCd"/>"                     binding="barcdCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.basicStockQty"/>"               binding="baseQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.basicStockAmt"/>"               binding="baseAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreInQty"/>"               binding="ioOccrQty03" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreInAmt"/>"               binding="ioOccrTot03" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreOutQty"/>"              binding="ioOccrQty12" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreOutAmt"/>"              binding="ioOccrTot12" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accPurchsInQty"/>"              binding="ioOccrQty06" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accPurchsInAmt"/>"              binding="ioOccrTot06" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accPurchsOutQty"/>"             binding="ioOccrQty18" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accPurchsOutAmt"/>"             binding="ioOccrTot18" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreSaleQty"/>"             binding="ioOccrQty11" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreSaleAmt"/>"             binding="ioOccrTot11" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreMoveInQty"/>"           binding="ioOccrQty04" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreMoveInAmt"/>"           binding="ioOccrTot04" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreMoveOutQty"/>"          binding="ioOccrQty14" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accStoreMoveOutAmt"/>"          binding="ioOccrTot14" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accDisuseQty"/>"                binding="ioOccrQty17" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accDisuseAmt"/>"                binding="ioOccrTot17" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accAdjQty"/>"                   binding="ioOccrQty21" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accAdjAmt"/>"                   binding="ioOccrTot21" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accSetInQty"/>"                 binding="ioOccrQty22" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.accSetInAmt"/>"                 binding="ioOccrTot22" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="storeMonth.endingStockQty"/>"              binding="closeQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="storeMonth.endingStockAmt"/>"              binding="closeAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	            </wj-flex-grid>
	            <%-- ColumnPicker 사용시 include --%>
	            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	              <jsp:param name="pickerTarget" value="storeMonthMainCtrl"/>
	            </jsp:include>
	            <%--// ColumnPicker 사용시 include --%>
	        </div>
	        <%-- 페이지 리스트 --%>
		    <div class="pageNum mt20">
		      <%-- id --%>
		      <ul id="storeMonthMainCtrlPager" data-size="10">
		      </ul>
		    </div>
		    <%--//페이지 리스트--%>
	    </div>
    </div>
</div>
<!-- //contents end -->

<script type="text/javascript" src="/resource/solbipos/js/stock/status/storemonth/storeMonth.js?ver=20220725.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/prodCodeDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수량 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/prodQtyDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

