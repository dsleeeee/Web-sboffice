<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="dlvrRateCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dlvrRateCtrl')">
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
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
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
              <jsp:param name="targetTypeFg" value="M"/>
              <jsp:param name="targetId" value="dlvrRateStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="dlvrRateStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="dlvrRate.saleDate"/>"         binding="saleDate" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.storeCnt"/>"         binding="storeCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.billCntStin"/>"      binding="billCntStin" width="100" align="left" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.billCntPick"/>"      binding="billCntPick" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.billCntDlvr"/>"      binding="billCntDlvr" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.billCnt"/>"          binding="billCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.realSaleAmtStin"/>"  binding="realSaleAmtStin" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.realSaleAmtPick"/>"  binding="realSaleAmtPick" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.realSaleAmtDlvr"/>"  binding="realSaleAmtDlvr" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.realSaleAmt"/>"      binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.stinAsp"/>"          binding="stinAsp" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.pickAsp"/>"          binding="pickAsp" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.dlvrAsp"/>"          binding="dlvrAsp" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.asp"/>"              binding="asp" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.stinRate"/>"         binding="stinRate" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.pickRate"/>"         binding="pickRate" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dlvrRate.dlvrRate"/>"         binding="dlvrRate" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="dlvrRateCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--엑셀 리스트--%>
  <div class="w100 mt10" style="display:none;" ng-controller="dlvrRateExcelCtrl">
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
        <wj-flex-grid-column header="<s:message code="dlvrRate.saleDate"/>"         binding="saleDate" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.storeCnt"/>"         binding="storeCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.billCntStin"/>"      binding="billCntStin" width="100" align="left" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.billCntPick"/>"      binding="billCntPick" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.billCntDlvr"/>"      binding="billCntDlvr" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.billCnt"/>"          binding="billCnt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.realSaleAmtStin"/>"  binding="realSaleAmtStin" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.realSaleAmtPick"/>"  binding="realSaleAmtPick" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.realSaleAmtDlvr"/>"  binding="realSaleAmtDlvr" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.realSaleAmt"/>"      binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.stinAsp"/>"          binding="stinAsp" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.pickAsp"/>"          binding="pickAsp" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.dlvrAsp"/>"          binding="dlvrAsp" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.asp"/>"              binding="asp" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.stinRate"/>"         binding="stinRate" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.pickRate"/>"         binding="pickRate" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvrRate.dlvrRate"/>"         binding="dlvrRate" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/dlvr/dlvrRate/dlvrRate.js?ver=20221017.01" charset="utf-8"></script>

