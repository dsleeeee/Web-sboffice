<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/today/todayBillSaleDtl/todayBillSaleDtl/"/>

<div id="todayBillSaleDtlView" class="subCon" style="display: none;">
  <div ng-controller="todayBillSaleDtlCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="todayBillSaleDtl.billSale"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('todayBillSaleDtlCtrl')">
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
            <span class="txtIn"><input id="srchTodayBillSaleDtlStartDate" class="w120px"></span>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
            <%-- 매장코드 --%>
          <th><s:message code="todayBillSaleDtl.store"/></th>
          <td colspan="3">
              <%-- 매장선택 모듈 싱글 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                closeFunc - 팝업 닫기시 호출할 함수
              --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
              <jsp:param name="targetId" value="todayBillSaleDtlSelectStore"/>
              <jsp:param name="closeFunc" value="getStorePosList"/>
            </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
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
          <input type="text" id="srchBillNo" name="srchBillNo" ng-model="billNo" class="sb-input w100" maxlength="4"/>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="todayBillSaleDtl.prodCd"/></th>
        <td>
          <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
        </td>
        <%-- 상품명 --%>
        <th><s:message code="todayBillSaleDtl.prodNm"/></th>
        <td>
          <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
        </td>
      </tr>
      <tr>
        <%-- 바코드 --%>
        <th><s:message code="todayBillSaleDtl.barcdCd"/></th>
        <td>
          <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
        </td>
        <th></th>
        <td></td>
      </tr>
      </tbody>
    </table>
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
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.posNo"/>" binding="posNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.saleYn"/>" binding="saleYn" width="60" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.tblNm"/>" binding="tblNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="todayBillSaleDtl.billDt"/>" binding="billDt" width="80" align="center" is-read-only="true" format="time"></wj-flex-grid-column>
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
          <jsp:param name="pickerTarget" value="todayBillSaleDtlCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/today/todayBillSaleDtl/todayBillSaleDtl.js?ver=20190125.02" charset="utf-8"></script>

