<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="salePriceHistoryCtrl">
  <%--searchTbl--%>
  <%--<div class="searchBar flddUnfld">--%>
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('salePriceHistoryCtrl', 1)">
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
      <th><s:message code="salePriceHistory.date" /></th>
      <td>
        <div class="sb-select">
          <span class="txtIn"><input id="startDate" ng-model="startDate" class="w110px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="endDate" ng-model="endDate" class="w110px"></span>
        </div>
      </td>
      <th><s:message code="salePriceHistory.prodClass" /></th>
      <td>
        <input type="text" id="searchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;"
               placeholder="선택" readonly />
        <input type="hidden" id="searchProdClassCd" ng-model="prodClassCd" disabled/>
        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
      </td>
    </tr>
    <tr>
      <%-- 상품코드 --%>
      <th>
        <s:message code="salePrice.prodCd" />
      </th>
      <td>
        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 상품명 --%>
      <th>
        <s:message code="salePrice.prodNm" />
      </th>
      <td>
        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
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
        <wj-flex-grid-column header="<s:message code="salePriceHistory.prodCd"/>"   binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePriceHistory.prodNm"/>"   binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePriceHistory.saleB"/>"    binding="bSaleUprc" is-read-only="true" width="60" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePriceHistory.sale"/>"     binding="saleUprc" is-read-only="true" width="60" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePriceHistory.startDate"/>"  binding="startDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePriceHistory.endDate"/>"    binding="endDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
        <c:if test="${subPriceFg == '1'}">
          <wj-flex-grid-column header="<s:message code="salePriceHistory.saleB"/>"  binding="bStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.sale"/>"   binding="stinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.saleB"/>"  binding="bDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.sale"/>"   binding="dlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.saleB"/>"  binding="bPackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.sale"/>"   binding="packSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="salePriceHistory.procFg"/>"   binding="procFg" is-read-only="true" width="60" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePriceHistory.procDt"/>"   binding="procDt" is-read-only="true" width="130" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePriceHistory.modId"/>"    binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="salePriceHistoryCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div id="wjWrapType1" class="w100 mt10" style="display:none;" ng-controller="excelCtrl">
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
          <wj-flex-grid-column header="<s:message code="salePriceHistory.prodCd"/>"   binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.prodNm"/>"   binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.saleB"/>"    binding="bSaleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.sale"/>"     binding="saleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.startDate"/>"  binding="startDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.endDate"/>"    binding="endDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
          <c:if test="${subPriceFg == '1'}">
            <wj-flex-grid-column header="<s:message code="salePriceHistory.saleB"/>"  binding="bStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePriceHistory.sale"/>"   binding="stinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePriceHistory.saleB"/>"  binding="bDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePriceHistory.sale"/>"   binding="dlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePriceHistory.saleB"/>"  binding="bPackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePriceHistory.sale"/>"   binding="packSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          </c:if>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.procFg"/>"   binding="procFg" is-read-only="true" width="60" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.procDt"/>"   binding="procDt" is-read-only="true" width="130" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePriceHistory.modId"/>"    binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePriceHistory/salePriceHistory.js?ver=20210927.01" charset="utf-8"></script>
<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>