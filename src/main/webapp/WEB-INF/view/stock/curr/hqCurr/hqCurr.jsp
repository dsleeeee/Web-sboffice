<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/curr/hqCurr/hqCurr/"/>

<div class="subCon" ng-controller="hqCurrCtrl">
	<div class="searchBar flddUnfld">
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
        <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
      </td>
      <%-- 상품명 --%>
      <th><s:message code="hqCurr.prodNm"/></th>
      <td>
        <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
      </td>
    </tr>
    <tr>
      <%-- 바코드 --%>
      <th><s:message code="hqCurr.barcdNm"/></th>
      <td>
        <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
      </td>
      <%-- 단위구분 --%>
      <th><s:message code="hqCurr.unitFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
              id="srchUnitFg"
              ng-model="unitFg"
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
      <%-- 거래처 --%>
      <th><s:message code="hqCurr.vendrNm"/></th>
      <td>
        <%-- 거래처선택 모듈 싱글 선택 사용시 include
             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                          closeFunc - 팝업 닫기시 호출할 함수
        --%>
        <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
          <jsp:param name="targetId" value="hqCurrSelectVendr"/>
          <jsp:param name="displayNm" value="전체"/>
        </jsp:include>
        <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
      </td>
      <%-- 분류 --%>
      <th><s:message code="hqCurr.prodClass"/></th>
      <td>
        <input type="text" class="sb-input w200px" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
               placeholder="<s:message code="cmm.all" />" readonly/>
        <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
      </td>
    </tr>
    <tr>
      <%-- 안전재고 --%>
      <th><s:message code="hqCurr.safeStockFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
              id="srchSafeStockFg"
              ng-model="safeStockFg"
              items-source="_getComboData('srchSafeStockFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
          <span class="chk ml10">
				<input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
	            <label for="chkDt">
                	<s:message code="periodIostock.prodClassDisplay" />
              	</label>
          </span>
        </div>
      </td>
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
          <th></th>
          <td></td>
        </c:otherwise>
      </c:choose>
    </tr>
    </tbody>
  </table>

  <ul class="txtSty3 mt10">
    <li><s:message code="hqCurr.txt1"/></li>
  </ul>

  <div class="mt20 oh sb-select dkbr">
      <%-- 페이지 스케일  --%>
      <wj-combo-box
          class="w100px fl"
          id="hqCurrListScaleBox"
          ng-model="listScale"
          control="listScaleCombo"
          items-source="_getComboData('hqCurrListScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          is-editable="false"
          initialized="_initComboBox(s)">
      </wj-combo-box>

      <%-- 엑셀 다운로드 //TODO --%>
      <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
      </button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter"
        frozen-columns="5"
        >

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.prodNm"/>" binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.poUnitFg"/>" binding="poUnitFg" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.poUnitQty"/>" binding="poUnitQty" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.vendrCd"/>" binding="vendrCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.vendrNm"/>" binding="vendrNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.barcdNm"/>" binding="barcdNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.costUprc"/>" binding="costUprc" width="80" align="right" is-read-only="true" data-type="Number"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.safeStockQty"/>" binding="safeStockQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.currQty"/>" binding="currQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.currAmt"/>" binding="currAmt" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	        <wj-flex-grid-column header="<s:message code="hqCurr.accVendrInQty"/>" binding="accVendrInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
	        <wj-flex-grid-column header="<s:message code="hqCurr.accVendrOutQty"/>" binding="accVendrOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
	        <wj-flex-grid-column header="<s:message code="hqCurr.hqOutQty"/>" binding="hqOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
	        <wj-flex-grid-column header="<s:message code="hqCurr.hqInQty"/>" binding="hqInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreInQty"/>" binding="accStoreInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreOutQty"/>" binding="accStoreOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accPurchsInQty"/>" binding="accPurchsInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accPurchsOutQty"/>" binding="accPurchsOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.accStoreSaleQty"/>" binding="accStoreSaleQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveInQty"/>" binding="accStoreMoveInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveOutQty"/>" binding="accStoreMoveOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accDisuseQty"/>" binding="accDisuseQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accAdjQty"/>" binding="accAdjQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accSetInQty"/>" binding="accSetInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	        <wj-flex-grid-column header="<s:message code="hqCurr.accSaleVendrOutQty"/>" binding="accSaleVendrOutQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
	        <wj-flex-grid-column header="<s:message code="hqCurr.accSaleVendrInQty"/>" binding="accSaleVendrInQty" width="80" align="center" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="hqCurr.firstVendrInDate"/>" binding="firstVendrInDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.lastVendrInDate"/>" binding="lastVendrInDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.firstHqOutDate"/>" binding="firstHqOutDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.lastHqOutDate"/>" binding="lastHqOutDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.firstSaleDate"/>" binding="firstSaleDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.lastSaleDate"/>" binding="lastSaleDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>

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
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/stock/curr/hqCurr/hqCurr.js?ver=20181224.01" charset="utf-8"></script>

<%-- 재고현황 팝업 --%>
<c:import url="/WEB-INF/view/stock/com/popup/cmmStockStatus/cmmStockStatus.jsp"></c:import>
<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp"></c:import>
