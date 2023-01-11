<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div id="storeSalePriceHistoryView" class="subCon" ng-controller="storeSalePriceHistoryCtrl">
  <%--searchTbl--%>
  <%--<div class="searchBar flddUnfld">--%>
  <div class="searchBar">
    <a href="#" class="open fl"><s:message code="hqSalePriceHistory.storeSalePriceHistory" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="nxBtnSearch1" ng-click="_pageView('storeSalePriceHistoryCtrl', 1)">
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
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="storeStartDate" ng-model="storeStartDate" class="w110px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="storeEndDate" ng-model="storeEndDate" class="w110px"></span>
        </div>
      </td>
    </tr>
    <%-- 매장선택 --%>
    <tr>
      <%-- 매장 --%>
      <th><s:message code="cmm.store.select"/></th>
      <td>
        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
        <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
          <jsp:param name="targetId" value="storeSalePriceHistoryStore"/>
        </jsp:include>
        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
      <th><s:message code="hqSalePriceHistory.prodClass" /></th>
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
        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch('1');"/>
      </td>
      <%-- 상품명 --%>
      <th>
        <s:message code="salePrice.prodNm" />
      </th>
      <td>
        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch('1');"/>
      </td>
    </tr>
    <c:if test="${brandUseFg == '1'}">
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
          <%-- 상품브랜드 --%>
          <th><s:message code="salePrice.prodHqBrand" /></th>
          <td>
            <div class="sb-select">
                <div class="sb-select">
                    <wj-combo-box
                        id="srchProdHqBrandCd"
                        ng-model="prodHqBrandCd"
                        items-source="_getComboData('srchProdHqBrandCd')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchProdHqBrandCdCombo">
                    </wj-combo-box>
                </div>
            </div>
          </td>
          <th></th>
          <td></td>
        </tr>
      </c:if>
    </c:if>
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
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.storeCd"/>"    binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.storeNm"/>"    binding="storeNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.prodCd"/>"     binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.prodNm"/>"     binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"      binding="bSaleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"       binding="saleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.startDate"/>"  binding="startDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.endDate"/>"    binding="endDate" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
        <c:if test="${subPriceFg == '1'}">
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"    binding="bStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"     binding="stinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"    binding="bDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"     binding="dlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.saleB"/>"    binding="bPackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.sale"/>"     binding="packSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.procFg"/>"     binding="procFg" is-read-only="true" width="60" align="center" data-map="procFgDataMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.procDt"/>"     binding="procDt" is-read-only="true" width="130" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.modId"/>"      binding="modId" is-read-only="true" width="100" align="center"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeSalePriceHistoryCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

    <%--엑셀 리스트--%>
    <div id="wjWrapType1" class="w100 mt10" style="display:none;" ng-controller="storeExcelCtrl">
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
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.storeCd"/>"   binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="hqSalePriceHistory.storeNm"/>"   binding="storeNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
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
    <%--//엑셀 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/price/hqSalePriceHistory/storeSalePriceHistory.js?ver=20230111.01" charset="utf-8"></script>
