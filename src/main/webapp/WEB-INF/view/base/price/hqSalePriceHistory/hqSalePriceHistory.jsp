<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div id="hqSalePriceHistoryView" class="subCon" style="display: none;" ng-controller="hqSalePriceHistoryCtrl">
  <div class="searchBar">
    <a href="#" class="open fl"><s:message code="hqSalePriceHistory.hqSalePriceHistory" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('hqSalePriceHistoryCtrl', 1)">
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
      <th><s:message code="hqSalePriceHistory.date" /></th>
      <td>
        <div class="sb-select">
          <span class="txtIn"><input id="hqStartDate" ng-model="hqStartDate" class="w110px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="hqEndDate" ng-model="hqEndDate" class="w110px"></span>
        </div>
      </td>
      <th><s:message code="hqSalePriceHistory.prodClass" /></th>
      <td>
        <input type="text" id="hqProdClassNm" ng-model="hqProdClassNm" class="sb-input w70" ng-click="popUpHqProdClass()" style="float: left;"
               placeholder="선택" readonly />
        <input type="hidden" id="hqProdClassCd" ng-model="hqProdClassCd" disabled/>
        <button type="button" class="btn_skyblue fl mr5" id="btnCancelHqProdClassCd" style="margin-left: 5px;" ng-click="delHqProdClass()"><s:message code="cmm.selectCancel"/></button>
      </td>
    </tr>
    <tr>
      <%-- 상품코드 --%>
      <th>
        <s:message code="hqSalePriceHistory.prodCd" />
      </th>
      <td>
        <input type="text" class="sb-input w100" id="hqProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('2');"/>
      </td>
      <%-- 상품명 --%>
      <th>
        <s:message code="hqSalePriceHistory.prodNm" />
      </th>
      <td>
        <input type="text" class="sb-input w100" id="hqProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('2');"/>
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
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="empCardInfo.excelDownload" /></button>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10">
    <div id="theGridStore" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.prodCd"/>"   binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.prodNm"/>"   binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"    binding="bSaleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"     binding="saleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.startDate"/>"  binding="startDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.endDate"/>"    binding="endDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
        <c:if test="${subPriceFg == '1'}">
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"  binding="bStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"   binding="stinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"  binding="bDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"   binding="dlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"  binding="bPackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"   binding="packSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.procFg"/>"   binding="procFg" is-read-only="true" width="60" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.procDt"/>"   binding="procDt" is-read-only="true" width="130" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.modId"/>"    binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="hqSalePriceHistoryCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div id="wjWrapType2" class="w100 mt10" style="display:none;" ng-controller="hqExcelCtrl">
      <div class="wj-gridWrap"> <%-- 수정 사항 || 그리드 높이값 스타일 제거 :: style="height: 000px;" --%>
        <wj-flex-grid
                id="excelGrid"
                autoGenerateColumns="false"
                control="excelFlex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.prodCd"/>"   binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.prodNm"/>"   binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"    binding="bSaleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"     binding="saleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.startDate"/>"binding="startDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.endDate"/>"  binding="endDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
          <c:if test="${subPriceFg == '1'}">
            <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"  binding="bStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"   binding="stinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"  binding="bDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"   binding="dlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"  binding="bPackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"   binding="packSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          </c:if>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.procFg"/>"   binding="procFg" is-read-only="true" width="60" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.procDt"/>"   binding="procDt" is-read-only="true" width="130" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.modId"/>"    binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script>
  // 브랜드 사용여부
  var brandUseFg = "${brandUseFg}";
  // 사용자 브랜드
  var userHqBrandCdComboList = ${userHqBrandCdComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/hqSalePriceHistory/hqSalePriceHistory.js?ver=20210910.01" charset="utf-8"></script>
