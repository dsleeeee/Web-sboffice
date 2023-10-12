<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="periodSaleCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="nxBtnSearch3" ng-click="_broadcast('periodSaleCtrl')">
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
            <span class="txtIn"><input id="srchPeriodSaleStartDate" class="w120px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchPeriodSaleEndDate" class="w120px"></span>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
          <%-- 매장선택 --%>
          <th><s:message code="cmm.store.select"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="S"/>
              <jsp:param name="targetId" value="periodSaleSelectStore"/>
              <jsp:param name="closeFunc" value="getStorePosList"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="todayBillSaleDtlSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      <tr>
        <%-- 포스번호 --%>
        <th><s:message code="todayBillSaleDtl.posNo"/></th>
        <td>
          <span class="txtIn w150px sb-select fl mr5">
            <wj-combo-box
              id="srchPosNo"
              ng-model="posNo"
              items-source="_getComboData('srchPosNo')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
        </td>
        <%-- 영수증번호 --%>
        <th><s:message code="todayBillSaleDtl.billNo"/></th>
        <td>
          <input type="text" id="srchBillNo" name="srchBillNo" ng-model="billNo" class="sb-input w100" maxlength="4" onkeyup="fnNxBtnSearch('3');"/>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="todayBillSaleDtl.prodCd"/></th>
        <td>
          <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('3');"/>
        </td>
        <%-- 상품명 --%>
        <th><s:message code="todayBillSaleDtl.prodNm"/></th>
        <td>
          <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50" onkeyup="fnNxBtnSearch('3');"/>
        </td>
      </tr>
      <tr>
        <%-- 바코드 --%>
        <th><s:message code="todayBillSaleDtl.barcdCd"/></th>
        <td>
          <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40" onkeyup="fnNxBtnSearch('3');"/>
        </td>
        <td></td>
        <td></td>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
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
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleYn"/>" binding="saleYn" width="60" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.tblNm"/>" binding="tblNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <%--<wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billDt"/>" binding="billDt" width="80" align="center" is-read-only="true" format="time"></wj-flex-grid-column>--%>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billDt"/>" binding="billDt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.prodNm"/>" binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleQty"/>" binding="saleQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.dcAmt"/>" binding="dcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.gaAmt"/>" binding="gaAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.vatAmt"/>" binding="vatAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="periodSaleCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/periodSale/periodSale.js?ver=20220701.01" charset="utf-8"></script>