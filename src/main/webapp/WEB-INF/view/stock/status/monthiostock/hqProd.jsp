<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/status/dayiostock/pord/"/>

<!-- contents start -->
<div class="subCon3" ng-controller="monthIostockCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>

      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('monthIostockMainCtrlSrch')">
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
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchMonthIostockStartDate"
                      ng-model="startDate"
                      control="monthIostockStartDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)"
                      selection-mode="Month"
                      format="y">
              </wj-input-date>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="monthIostock.prodCd"/></th>
        <td><input type="text" class="sb-input w100" id="srchProdCd" ng-model="srchProdCd" onkeyup="fnNxBtnSearch();"/></td>

        <%-- 상품명 --%>
        <th><s:message code="monthIostock.prodNm"/></th>
        <td><input type="text" class="sb-input w100" id="srchProdNm" ng-model="srchProdNm" onkeyup="fnNxBtnSearch();"/></td>
      </tr>
      <tr>
        <%-- 바코드 --%>
        <th><s:message code="monthIostock.barcdCd"/></th>
        <td><input type="text" class="sb-input w100" id="srchBarcdCd" ng-model="srchBarcdCd" onkeyup="fnNxBtnSearch();"/></td>

        <%-- 단위구분 --%>
        <th><s:message code="monthIostock.unitFg"/></th>
        <td>
            <div class="sb-select">
        <span class="txtIn w120px">
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
      </tr>
      <tr>
        <%-- 상품분류 --%>
        <th><s:message code="monthIostock.prodClass"/></th>
        <td>
          <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                 placeholder="<s:message code="cmm.all" />" readonly/>
          <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
          <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
        </td>

        <%-- 조회옵션 --%>
        <th><s:message code="monthIostock.srchOption"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn w120px">
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
            <span class="chk ml5">
                <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()"/>
                <label for="chkDt">
                    <s:message code="monthIostock.prodClassDisplay" />
                </label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 거래처 --%>
        <th style="display: none;"><s:message code="monthIostock.vendr"/></th>
        <td style="display: none;">
          <%-- 거래처선택 모듈 멀티 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
            <jsp:param name="targetId" value="monthIostockSelectVendr"/>
          </jsp:include>
          <input type="hidden" id="monthIostockSelectVendrCd" value=""/>
        </td>
      </tr>
      </tbody>
    </table>

    <input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
    <div ng-controller="monthIostockMainCtrl">
	    <div class="mt20 oh sb-select dkbr">
	      <%-- 페이지 스케일  --%>
          <wj-combo-box
            class="w100px fl"
            id="monthIostockMainlistScaleBox"
            ng-model="listScale"
            items-source="_getComboData('monthIostockMainlistScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="_initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
          </wj-combo-box>
	        <%-- 엑셀 다운로드 //TODO --%>
	        <button class="btn_skyblue fr" ng-click="excelDownloadMonthIostock()"><s:message code="cmm.excel.down" />
	        </button>
	    </div>

	    <%-- gird 1 --%>
	    <div class="w100 mt10" id="wjWrapType2">
	        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;">
	            <wj-flex-grid
	              id="monthIostockMainGrid"
	              loaded-rows="loadedRows(s,e)"
	              autoGenerateColumns="false"
	              selection-mode="Row"
	              items-source="data"
	              control="flex"
	              initialized="initGrid(s,e)"
	              is-read-only="true"
	              item-formatter="_itemFormatter"
	              frozen-columns="6">

	              <!-- define columns -->
	              <wj-flex-grid-column header="<s:message code="monthIostock.prodClassNm"/>"        binding="prodClassNm" width="200" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="monthIostock.prodCd"/>"             binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.prodNm"/>"             binding="prodNm" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.poUnitQty"/>"          binding="poUnitQty" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.poUnitFg"/>"           binding="poUnitFgNm" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.barcdCd"/>"            binding="barcdCd" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.basicStockQty"/>"      binding="baseQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.basicStockAmt"/>"      binding="baseAmt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                  <wj-flex-grid-column header="<s:message code="monthIostock.accVendrInQty"/>"      binding="vendrInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.accVendrInAmt"/>"      binding="vendrInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.accVendrOutQty"/>"     binding="vendrOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.accVendrOutAmt"/>"     binding="vendrOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.hqOutQty"/>"           binding="hqOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.hqOutAmt"/>"           binding="hqOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.hqInQty"/>"            binding="hqInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.hqInAmt"/>"            binding="hqInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>


	              <wj-flex-grid-column header="<s:message code="monthIostock.accStoreMoveInQty"/>"  binding="storeMoveInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.accStoreMoveInAmt"/>"  binding="storeMoveInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.accStoreMoveOutQty"/>" binding="storeMoveOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.accStoreMoveOutAmt"/>" binding="storeMoveOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.accDisuse"/>"          binding="disuseQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.accAdj"/>"             binding="adjQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.accSetIn"/>"           binding="setInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                  <wj-flex-grid-column header="<s:message code="monthIostock.accSaleVendrOutQty"/>" binding="saleVendrOrderQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.accSaleVendrOutAmt"/>" binding="saleVendrOrderTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.accSaleVendrInQty"/>"  binding="saleVendrRtnQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="monthIostock.accSaleVendrInAmt"/>"  binding="saleVendrRtnTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="monthIostock.endingStockQty"/>"     binding="closeQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="monthIostock.endingStockAmt"/>"     binding="closeAmt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="cmm.search.date"/>"	binding="startDate"		width="60"		align="center"	is-read-only="true" visible="false"></wj-flex-grid-column>
	            </wj-flex-grid>
	            <%-- ColumnPicker 사용시 include --%>
	            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	              <jsp:param name="pickerTarget" value="monthIostockMainCtrl"/>
	            </jsp:include>
	            <%--// ColumnPicker 사용시 include --%>
	        </div>
	        <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
              <%-- id --%>
              <ul id="monthIostockMainCtrlPager" data-size="10">
              </ul>
            </div>
            <%--//페이지 리스트--%>


            <%-- 엑셀 리스트 --%>
            <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="monthIostockExcelCtrl">
                <div class="wj-gridWrap">
                    <wj-flex-grid
                            id="monthIostockMainExcelGrid"
                            loaded-rows="loadedRows(s,e)"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="excelFlex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter"
                            frozen-columns="6"
                    >

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="monthIostock.prodClassNm"/>"        binding="prodClassNm" width="200" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="monthIostock.prodCd"/>"             binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.prodNm"/>"             binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.poUnitQty"/>"          binding="poUnitQty" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.poUnitFg"/>"           binding="poUnitFgNm" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.barcdCd"/>"            binding="barcdCd" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.basicStockQty"/>"      binding="baseQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.basicStockAmt"/>"      binding="baseAmt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="monthIostock.accVendrInQty"/>"      binding="vendrInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accVendrInAmt"/>"      binding="vendrInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accVendrOutQty"/>"     binding="vendrOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accVendrOutAmt"/>"     binding="vendrOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.hqOutQty"/>"           binding="hqOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.hqOutAmt"/>"           binding="hqOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.hqInQty"/>"            binding="hqInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.hqInAmt"/>"            binding="hqInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>


                        <wj-flex-grid-column header="<s:message code="monthIostock.accStoreMoveInQty"/>"  binding="storeMoveInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accStoreMoveInAmt"/>"  binding="storeMoveInTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accStoreMoveOutQty"/>" binding="storeMoveOutQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accStoreMoveOutAmt"/>" binding="storeMoveOutTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accDisuse"/>"          binding="disuseQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accAdj"/>"             binding="adjQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accSetIn"/>"           binding="setInQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="monthIostock.accSaleVendrOutQty"/>" binding="saleVendrOrderQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accSaleVendrOutAmt"/>" binding="saleVendrOrderTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accSaleVendrInQty"/>"  binding="saleVendrRtnQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.accSaleVendrInAmt"/>"  binding="saleVendrRtnTot" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="monthIostock.endingStockQty"/>"     binding="closeQty" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="monthIostock.endingStockAmt"/>"     binding="closeAmt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%--//엑셀 리스트--%>
	    </div>
    </div>
</div>
<!-- //contents end -->

<script type="text/javascript" src="/resource/solbipos/js/stock/status/monthiostock/hqProd.js?ver=20220603.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/hqProdCodeDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수량 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/prodQtyDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
