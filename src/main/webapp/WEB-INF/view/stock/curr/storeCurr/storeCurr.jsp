<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/curr/storeCurr/storeCurr/"/>

<div class="subCon3" ng-controller="storeCurrCtrl">
	<div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
        
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeCurrCtrl')">
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
        <%-- 매장선택 --%>
        <th><s:message code="cmm.store.select"/></th>
        <td>
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                <jsp:param name="targetTypeFg" value="M"/>
                <jsp:param name="targetId" value="storeCurrSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
        </td>
        <c:choose>
            <c:when test="${envst008 != null && envst008 != '00'}">
                <%-- 상품구분 --%>
                <th><s:message code="storeCurr.weightFg"/></th>
                <td colspan="3">
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
        </c:choose>
    </tr>
    <tr>
      <%-- 상품코드 --%>
      <th><s:message code="storeCurr.prodCd"/></th>
      <td>
        <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 상품명 --%>
      <th><s:message code="storeCurr.prodNm"/></th>
      <td>
        <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50" onkeyup="fnNxBtnSearch();"/>
      </td>
    </tr>
    <tr>
      <%-- 바코드 --%>
      <th><s:message code="storeCurr.barcdNm"/></th>
      <td>
        <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 단위구분 --%>
      <th><s:message code="storeCurr.unitFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w120px">
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
      <%-- 분류 --%>
      <th><s:message code="storeCurr.prodClass"/></th>
      <td>
        <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
               placeholder="<s:message code="cmm.all" />" readonly/>
        <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
      </td>
      <%-- 안전재고 --%>
      <th><s:message code="storeCurr.safeStockFg"/></th>
      <td>
          <div class="sb-select">
      <span class="txtIn w120px">
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
    	<%-- 거래처 --%>
      		<th><s:message code="storeCurr.vendrNm"/></th>
      		<td>
      		<%-- 거래처선택 모듈 싱글 선택 사용시 include
      			param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
      						displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
      						modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
      						closeFunc - 팝업 닫기시 호출할 함수
      		--%>
      		<jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
      			<jsp:param name="targetId" value="storeCurrSelectVendr"/>
   	   		</jsp:include>
      		<%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
      		</td>
    	</tr>
    </tbody>
  </table>

  <ul class="txtSty3 mt10">
    <li><s:message code="storeCurr.txt1"/></li>
  </ul>

  <div style="clear: both;"></div>

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
        frozen-columns="5">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="periodIostock.prodClassNm"/>" binding="prodClassNm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.prodNm"/>" binding="prodNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.storeNm"/>" binding="storeNm" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.poUnitFg"/>" binding="poUnitFg" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.poUnitQty"/>" binding="poUnitQty" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.vendrCd"/>" binding="vendrCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.vendrNm"/>" binding="vendrNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.barcdNm"/>" binding="barcdNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.costUprc"/>" binding="costUprc" width="80" align="right" is-read-only="true" data-type="Number"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.safeStockQty"/>" binding="safeStockQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.currQty"/>" binding="currQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.currAmt"/>" binding="currAmt" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
<%--         <c:if test="${sessionInfo.orgnFg == 'STORE' && sessionInfo.hqOfficeCd != '0000'}"> --%>
          <wj-flex-grid-column header="<s:message code="storeCurr.accStoreInQty"/>" binding="accStoreInQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCurr.accStoreOutQty"/>" binding="accStoreOutQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCurr.accMoveInQty"/>" binding="accMoveInQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCurr.accMoveOutQty"/>" binding="accMoveOutQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
<%--         </c:if> --%>
        <wj-flex-grid-column header="<s:message code="storeCurr.accPurchsInQty"/>" binding="accPurchsInQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.accPurchsOutQty"/>" binding="accPurchsOutQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.accStoreSaleQty"/>" binding="accStoreSaleQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.accDisuseQty"/>" binding="accDisuseQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.accAdjQty"/>" binding="accAdjQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.accSetInQty"/>" binding="accSetInQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.firstInDate"/>" binding="firstInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.lastInDate"/>" binding="lastInDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.firstSaleDate"/>" binding="firstSaleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeCurr.lastSaleDate"/>" binding="lastSaleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="storeCurrCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/stock/curr/storeCurr/storeCurr.js?ver=20240611.01" charset="utf-8"></script>

<%-- 현재고현황 상세 레이어 --%>
<%--<c:import url="/WEB-INF/view/stock/curr/storeCurr/storeCurrDtl.jsp">--%>
<%--<c:param name="menuCd" value="${menuCd}"/>--%>
<%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
