<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/curr/hqCurr/hqCurr/"/>

<div class="subCon" ng-controller="hqCurrCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('hqCurrCtrl')">
          <s:message code="cmm.search" />
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
      <%-- 상품코드 --%>
      <th><s:message code="hqCurr.prodCd"/></th>
      <td>
        <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCdModel" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 상품명 --%>
      <th><s:message code="hqCurr.prodNm"/></th>
      <td>
        <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNmModel" class="sb-input w100" maxlength="50" onkeyup="fnNxBtnSearch();"/>
      </td>
    </tr>
    <tr>
      <%-- 바코드 --%>
      <th><s:message code="hqCurr.barcdNm"/></th>
      <td>
        <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCdModel" class="sb-input w100" maxlength="40" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 단위구분 --%>
      <th><s:message code="hqCurr.unitFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w120px">
            <wj-combo-box
              id="srchUnitFg"
              ng-model="unitFgModel"
              items-source="_getComboData('srchUnitFg')"
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
      <%-- 분류 --%>
      <th><s:message code="hqCurr.prodClass"/></th>
      <td>
          <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                 placeholder="<s:message code="cmm.all" />" readonly/>
          <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCdModel" disabled/>
          <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
      </td>
      <%-- 안전재고 --%>
      <th><s:message code="hqCurr.safeStockFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w120px">
            <wj-combo-box
              id="srchSafeStockFg"
              ng-model="safeStockFgModel"
              items-source="_getComboData('srchSafeStockFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
          <span class="chk ml5">
                <input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                <label for="chkDt">
                    <s:message code="periodIostock.prodClassDisplay" />
                </label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <c:choose>
        <c:when test="${envst008 != null && envst008 != '00'}">
          <%-- 상품구분 --%>
          <th><s:message code="storeCurr.weightFg"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchWeightFg"
                  ng-model="weightFg"
                  items-source="_getComboData('srchWeightFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </span>
            </div>
          </td>
        </c:when>
        <c:otherwise>
        </c:otherwise>
      </c:choose>
    </tr>
    <tr>
        <%-- 거래처 --%>
        <th><s:message code="hqCurr.vendrNm"/></th>
        <td>
          <%-- 거래처선택 모듈 멀티 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                           displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                           modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                           closeFunc - 팝업 닫기시 호출할 함수
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
            <jsp:param name="targetId" value="hqCurrSelectVendr"/>
          </jsp:include>
          <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
        </td>
    </tr>
    </tbody>
  </table>

  <input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
  <input type="hidden" id="storeCd" value="${sessionInfo.storeCd}"/>
<%--   <input type="hidden" id="orgnFg" value="${sessionInfo.orgnFg}"/> --%>

  <ul class="txtSty3 mt10">
    <li><s:message code="hqCurr.txt1"/></li>
  </ul>

  <div class="mt10 oh sb-select dkbr">
      <%-- 페이지 스케일  --%>
      <wj-combo-box
          class="w100px fl"
          id="hqCurrListScaleBox"
          ng-model="listScale"
          items-source="_getComboData('hqCurrListScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          initialized="_initComboBox(s)"
          control="listScaleCombo"
		  is-editable="true"
		  text-changed="_checkValidation(s)">
      </wj-combo-box>

      <%-- 엑셀 다운로드 //TODO --%>
      <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
      </button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter"
        frozen-columns="6"
        >

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="hqCurr.prodClassNm"/>" binding="prodClassNm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.prodNm"/>" binding="prodNm" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.poUnitFg"/>" binding="poUnitFgNm" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.poUnitQty"/>" binding="poUnitQty" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.vendrCd"/>" binding="vendrCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.vendrNm"/>" binding="vendrNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.barcdNm"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.costUprc"/>" binding="costUprc" width="80" align="right" is-read-only="true" data-type="Number"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.safeStockQty"/>" binding="safeStockQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.currQty"/>" binding="currQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.currAmt"/>" binding="currAmt" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="hqCurr.accVendrInQty"/>" binding="accVendrInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accVendrOutQty"/>" binding="accVendrOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.hqOutQty"/>" binding="accHqOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.hqInQty"/>" binding="accHqInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveInQty"/>" binding="accStoreMoveInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveOutQty"/>" binding="accStoreMoveOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreInQty"/>" binding="accStoreInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreOutQty"/>" binding="accStoreOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accPurchsInQty"/>" binding="accPurchsInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accPurchsOutQty"/>" binding="accPurchsOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreSaleQty"/>" binding="accStoreSaleQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveInQty"/>" binding="accMoveInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveOutQty"/>" binding="accMoveOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        </c:if>

        <wj-flex-grid-column header="<s:message code="hqCurr.accDisuseQty"/>" binding="accDisuseQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accAdjQty"/>" binding="accAdjQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accSetInQty"/>" binding="accSetInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="hqCurr.accSaleVendrOutQty"/>" binding="accSaleVendrOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accSaleVendrInQty"/>" binding="accSaleVendrInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accHqMoveOutQty"/>" binding="accHqMoveOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accHqMoveInQty"/>" binding="accHqMoveInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.firstVendrInDate"/>" binding="firstVendrInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.lastVendrInDate"/>" binding="lastVendrInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.firstHqOutDate"/>" binding="firstHqOutDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.lastHqOutDate"/>" binding="lastHqOutDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <wj-flex-grid-column header="<s:message code="hqCurr.firstVendrInDate"/>" binding="firstInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.lastVendrInDate"/>" binding="lastInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="hqCurr.firstSaleDate"/>" binding="firstSaleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.lastSaleDate"/>" binding="lastSaleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="hqCurrCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="hqCurrCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div class="wj-gridWrap" style="height: 350px; display:none;" ng-controller="hqCurrExcelCtrl">
      <wj-flex-grid
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
          <wj-flex-grid-column header="<s:message code="hqCurr.prodClassNm"/>" binding="prodClassNm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.prodNm"/>" binding="prodNm" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.poUnitFg"/>" binding="poUnitFgNm" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.poUnitQty"/>" binding="poUnitQty" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.vendrCd"/>" binding="vendrCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.vendrNm"/>" binding="vendrNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.barcdNm"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.costUprc"/>" binding="costUprc" width="80" align="right" is-read-only="true" data-type="Number"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.safeStockQty"/>" binding="safeStockQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.currQty"/>" binding="currQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.currAmt"/>" binding="currAmt" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
              <wj-flex-grid-column header="<s:message code="hqCurr.accVendrInQty"/>" binding="accVendrInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accVendrOutQty"/>" binding="accVendrOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.hqOutQty"/>" binding="accHqOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.hqInQty"/>" binding="accHqInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveInQty"/>" binding="accStoreMoveInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveOutQty"/>" binding="accStoreMoveOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          </c:if>
          <c:if test="${sessionInfo.orgnFg == 'STORE'}">
              <wj-flex-grid-column header="<s:message code="hqCurr.accStoreInQty"/>" binding="accStoreInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accStoreOutQty"/>" binding="accStoreOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accPurchsInQty"/>" binding="accPurchsInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accPurchsOutQty"/>" binding="accPurchsOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accStoreSaleQty"/>" binding="accStoreSaleQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveInQty"/>" binding="accMoveInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveOutQty"/>" binding="accMoveOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          </c:if>

          <wj-flex-grid-column header="<s:message code="hqCurr.accDisuseQty"/>" binding="accDisuseQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.accAdjQty"/>" binding="accAdjQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.accSetInQty"/>" binding="accSetInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
              <wj-flex-grid-column header="<s:message code="hqCurr.accSaleVendrOutQty"/>" binding="accSaleVendrOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accSaleVendrInQty"/>" binding="accSaleVendrInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accHqMoveOutQty"/>" binding="accHqMoveOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.accHqMoveInQty"/>" binding="accHqMoveInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.firstVendrInDate"/>" binding="firstVendrInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.lastVendrInDate"/>" binding="lastVendrInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.firstHqOutDate"/>" binding="firstHqOutDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.lastHqOutDate"/>" binding="lastHqOutDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          </c:if>
          <c:if test="${sessionInfo.orgnFg == 'STORE'}">
              <wj-flex-grid-column header="<s:message code="hqCurr.firstVendrInDate"/>" binding="firstInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.lastVendrInDate"/>" binding="lastInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          </c:if>
          <wj-flex-grid-column header="<s:message code="hqCurr.firstSaleDate"/>" binding="firstSaleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqCurr.lastSaleDate"/>" binding="lastSaleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//엑셀 리스트--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/stock/curr/hqCurr/hqCurr.js?ver=20220803.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
