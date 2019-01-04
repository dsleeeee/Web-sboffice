<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/slipStockInfo/slipStockInfo/"/>

<div class="subCon">
  <div ng-controller="slipStockInfoCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open">${menuNm}</a>
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
        <th><s:message code="slipStockInfo.instockDate"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
        <%-- 전표구분 --%>
        <th><s:message code="slipStockInfo.slipFg"/></th>
        <td>
          <div class="sb-select">
          <span class="txtIn w120px">
            <wj-combo-box
              id="srchSlipFg"
              ng-model="slipFg"
              items-source="_getComboData('srchSlipFg')"
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
        <%-- 입고번호 --%>
        <th><s:message code="slipStockInfo.inSlipNo"/></th>
        <td>
          <input type="text" id="srchSlipNo" name="srchSlipNo" ng-model="slipNo" class="sb-input w100" maxlength="8"/>
        </td>
        <%-- 발주번호 --%>
        <th><s:message code="slipStockInfo.orderSlipNo"/></th>
        <td>
          <input type="text" id="srchOrderSlipNo" name="srchOrderSlipNo" ng-model="orderSlipNo" class="sb-input w100" maxlength="8"/>
        </td>
      </tr>
      <tr>
        <%-- 거래처 --%>
        <th><s:message code="slipStockInfo.vendr"/></th>
        <td>
          <%-- 거래처선택 모듈 멀티 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
            <jsp:param name="targetId" value="slipStockInfoSelectVendr"/>
          </jsp:include>
          <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
        </td>
        <%-- 상품분류 --%>
        <th><s:message code="slipStockInfo.prodClass"/></th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                 placeholder="<s:message code="cmm.all" />" readonly/>
          <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="slipStockInfo.prodCd"/></th>
        <td>
          <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
        </td>
        <%-- 상품명 --%>
        <th><s:message code="slipStockInfo.prodNm"/></th>
        <td>
          <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="16"/>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('slipStockInfoCtrl', 1)">
        <%--<button class="btn_blue fr" id="btnSearch" ng-click="fnSearch()">--%>
        <s:message code="cmm.search"/></button>
    </div>

    <div class="wj-TblWrap" style="height: 300px;">
      <div class="w100 mt10">
        <div class="wj-TblWrapBr">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap" style="height: 250px;">
            <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="slipStockInfo.inSlipNo"/>" binding="slipNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.instockDate"/>" binding="instockDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.vendr"/>" binding="vendrNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.orderSlipNo"/>" binding="orderSlipNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.slipFg"/>" binding="slipFg" width="70" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtlCnt"/>" binding="dtlCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="slipStockInfoCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
          <%--//위즈모 테이블--%>
        </div>
      </div>

      <div style="clear: both"></div>

      <div class="w100 mt10" ng-controller="slipStockInfoDtlCtrl">
        <div class="wj-TblWrapBr">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap" style="height: 250px;">
            <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodCd"/>" binding="prodCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitFg"/>" binding="poUnitFg" width="50" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitQty"/>" binding="poUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>" binding="inTotQty" width="40" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="slipStockInfoDtlCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
          <%--//위즈모 테이블--%>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/slipStockInfo/slipStockInfo.js?ver=20181224.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
