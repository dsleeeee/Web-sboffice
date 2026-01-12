<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon">
  <div ng-controller="nonTaxSaleCtrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('nonTaxSaleCtrl', 1)">
          <s:message code="cmm.search"/>
        </button>
      </div>
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
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
        <%-- 일자표시옵션 --%>
        <th><s:message code="nonTaxSale.dayOption"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchDayOptionCombo"
                    ng-model="dayOption"
                    items-source="_getComboData('dayOptionCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="srchDayOptionCombo">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
            <%-- 매장선택 --%>
          <th style="border-left:1px solid #ccc"><s:message code="cmm.store.select"/></th>
          <td colspan="3">
              <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="M"/>
              <jsp:param name="targetId" value="nonTaxSaleStore"/>
            </jsp:include>
              <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="nonTaxSaleStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
      <%-- 현재화면 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent"/></button>
    </div>

    <div class="w100 mt10">
      <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
                id="wjGridList"
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="nonTaxSale.dayFrom"/>"  binding="dayFrom"   width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.dayTo"/>"    binding="dayTo"     width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.saleDate"/>" binding="saleDate"  width="80"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.storeCd"/>"  binding="storeCd"   width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.storeNm"/>"  binding="storeNm"   width="120" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.posNo"/>"    binding="posNo"     width="80"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.billNo"/>"   binding="billNo"    width="80"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.coupnFg"/>"  binding="coupnFg"   width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.onlineFg"/>" binding="onlineFg"  width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.posFg"/>"    binding="posFg"     width="80"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.coupnNm"/>"  binding="coupnNm"   width="120" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="nonTaxSale.saleAmt"/>"  binding="saleAmt"   width="80"  align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="nonTaxSaleCtrlPager" data-size="10">
    </ul>
  </div>
  <%-- //페이지 리스트 --%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
  var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/nonTaxSale/nonTaxSale.js?ver=20260112.01" charset="utf-8"></script>
