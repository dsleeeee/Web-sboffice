<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="monthSalesVolumeCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('monthSalesVolumeCtrl')">
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
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="startMonth" class="w110px"></span>
          </div>
        </td>
          <th><s:message code="monthSalesVolume.saleHour"/></th>
          <td>
            <div class="sb-select fl w110px">
              <wj-combo-box
                      id="startTime"
                      ng-model="startTime"
                      items-source="_getComboData('startTimeCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      control="startTimeCombo"
                      initialized="_initComboBox(s)">
              </wj-combo-box>
            </div>
            <span class="rg fl">~</span>
            <div class="sb-select fl w110px">
              <wj-combo-box
                      id="endTime"
                      ng-model="endTime"
                      items-source="_getComboData('endTimeCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      control="endTimeCombo"
                      initialized="_initComboBox(s)">
              </wj-combo-box>
            </div>
          </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="monthSalesVolume.prodCd" /></th>
        <td><input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('1');"/></td>
        <%-- 상품명 --%>
        <th><s:message code="monthSalesVolume.prodNm" /></th>
        <td><input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('1');"/></td>
      </tr>
      <tr>
        <%-- 분류조회 --%>
        <th><s:message code="prod.prodClass" /></th>
        <td>
          <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                 placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
          <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
          <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
        </td>
        <%-- 매장코드 --%>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <th><s:message code="todayBillSaleDtl.store"/></th>
          <td>
            <%-- [NXPOS-1648,1699] 매장선택 모듈 통합 / 추후작업예정 - 화면못찾음 --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
              <jsp:param name="targetId" value="monthSalesVolumeStore"/>
            </jsp:include>
          </td>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="monthSalesVolumeStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
      </tr>
      <tr>
        <%-- 매장브랜드 --%>
        <th><s:message code="monthSalesVolume.hqBrand"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="hqBrandCd"
                    ng-model="hqBrandCd"
                    items-source="_getComboData('hqBrandCd')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="hqBrandCdCombo">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="monthSalesVolume.saleYm"/>"     binding="saleYm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="monthSalesVolume.saleHour"/>"   binding="saleHour" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="monthSalesVolume.prodClassNm"/>" binding="pathNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="monthSalesVolume.prodNm"/>"     binding="prodNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="monthSalesVolume.totSaleQty"/>" binding="totSaleQty" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="monthSalesVolume.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="monthSalesVolumeCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--엑셀 리스트--%>
  <div class="w100 mt10" style="display:none;" ng-controller="monthSalesVolumeExcelCtrl">
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
        <wj-flex-grid-column header="<s:message code="monthSalesVolume.saleYm"/>"     binding="saleYm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="monthSalesVolume.saleHour"/>"   binding="saleHour" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="monthSalesVolume.prodNm"/>"     binding="prodNm" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="monthSalesVolume.totSaleQty"/>" binding="totSaleQty" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="monthSalesVolume.totSaleAmt"/>" binding="totSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
  var hqBrandList = ${hqBrandList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/prod/monthSalesVolume/monthSalesVolume.js?ver=20221018.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>