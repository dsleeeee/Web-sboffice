<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="orderStatusCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_pageView('orderStatusCtrl',1)">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>

  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
    <%-- 변경 일자 --%>
    <tr>
      <th><s:message code="orderStatus.srchDate" /></th>
      <td>
        <div class="sb-select">
          <span class="txtIn"><input id="startDate" ng-model="startDate" class="w110px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="endDate" ng-model="endDate" class="w110px"></span>
        </div>
      </td>
    </tr>
   </tbody>
  </table>
  <%--//searchTbl--%>

  <div class="mt10 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>
    <%--// 페이지 스케일  --%>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10">
    <div id="theGrid" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="orderStatus.saleDate"/>"      binding="saleDate"      width="80" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.posNo"/>"         binding="posNo"         width="70" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.orderNo"/>"       binding="orderNo"       width="80" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.orderFg"/>"       binding="orderFg"       width="80" is-read-only="true" align="center" data-map="orderFgDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.dlvrOrderFg"/>"   binding="dlvrOrderFg"   width="90" is-read-only="true" align="center" data-map="dlvrOrderFgDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.orderStartDt"/>"  binding="orderStartDt"  width="130" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.orderEndDt"/>"    binding="orderEndDt"    width="130" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.totSaleAmt"/>"    binding="totSaleAmt"    width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.totDcAmt"/>"      binding="totDcAmt"      width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.totTipAmt"/>"     binding="totTipAmt"     width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.totEtcAmt"/>"     binding="totEtcAmt"     width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.realSaleAmt"/>"   binding="realSaleAmt"   width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.taxSaleAmt"/>"    binding="taxSaleAmt"    width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.vatAmt"/>"        binding="vatAmt"        width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.noTaxSaleAmt"/>"  binding="noTaxSaleAmt"  width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.pureSaleAmt"/>"   binding="pureSaleAmt"   width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.expectPayAmt"/>"  binding="expectPayAmt"  width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.recvPayAmt"/>"    binding="recvPayAmt"    width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.rtnPayAmt"/>"     binding="rtnPayAmt"     width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.dutchPayCnt"/>"   binding="dutchPayCnt"   width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.totGuestCnt"/>"   binding="totGuestCnt"   width="80" is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.kitchenMemo"/>"   binding="kitchenMemo"   width="80" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.tblCd"/>"         binding="tblCd"         width="80" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.moveOrderNo"/>"   binding="moveOrderNo"   width="80" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.empNo"/>"         binding="empNo"         width="80" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.pagerNo"/>"       binding="pagerNo"       width="80" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.dlvrYn"/>"        binding="dlvrYn"        width="80" is-read-only="true" align="center" data-map="dlvrYnDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.membrYn"/>"       binding="membrYn"      width="80" is-read-only="true" align="center" data-map="membrYnDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.resveYn"/>"       binding="resveYn"       width="80" is-read-only="true" align="center" data-map="resveYnDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.refundYn"/>"      binding="refundYn"      width="80" is-read-only="true" align="center" data-map="refundYnDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.sendYn"/>"        binding="sendYn"        width="80" is-read-only="true" align="center" data-map="sendYnDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="orderStatus.sendDt"/>"        binding="sendDt"        width="130" is-read-only="true" align="center"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="orderStatusCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/orderStatus/orderStatus.js?ver=20211001.01" charset="utf-8"></script>

<%-- 상세 팝업 --%>
<c:import url="/WEB-INF/view/sale/status/orderStatus/orderStatusDtl.jsp">
</c:import>