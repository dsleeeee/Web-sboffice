<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="priceEnvstVal" value="${priceEnvstVal}" />

<div class="subCon" ng-controller="storeSalePriceCtrl">
  <%--searchTbl--%>
  <%--<div class="searchBar flddUnfld">--%>
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('storeSalePriceCtrl', 1)">
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
    <%-- 매장선택 --%>
    <tr>
      <%-- 매장 --%>
      <th><s:message code="cmm.store.select"/></th>
      <td>
            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="storeSalePriceStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
      <th><s:message code="salePrice.select.prodClass" /></th>
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
    <tr>
      <th><s:message code="salePrice.uprcComparison"/></th>
      <td colspan="3">
        <input type="checkbox" id="saleUprc" ng-model="saleUprc"/> <s:message code="salePrice.salePrice"/>&nbsp;&nbsp;
        <c:if test="${subPriceFg == '1'}">
        <input type="checkbox" id="stinSaleUprc" ng-model="stinSaleUprc"/> <s:message code="salePrice.stinSaleUprc"/>&nbsp;&nbsp;
        <input type="checkbox" id="dlvrSaleUprc" ng-model="dlvrSaleUprc"/> <s:message code="salePrice.dlvrSaleUprc"/>&nbsp;&nbsp;
        <input type="checkbox" id="packSaleUprc" ng-model="packSaleUprc"/> <s:message code="salePrice.packSaleUprc"/>&nbsp;&nbsp;
        </c:if>
      </td>
    </tr>
   </tbody>
  </table>
  <%--//searchTbl--%>

  <div class="mt10 oh sb-select dkbr">
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
        <wj-flex-grid-column header="<s:message code="salePrice.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqSaleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="saleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
         <c:if test="${subPriceFg == '1'}">
          <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="stinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
         <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="dlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqPackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="packSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
        </c:if>
      </wj-flex-grid>
    </div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeSalePriceCtrlPager" data-size="10">
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
          <wj-flex-grid-column header="<s:message code="salePrice.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqSaleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="saleUprc" is-read-only="true" width="60" align="right"></wj-flex-grid-column>
          <c:if test="${subPriceFg == '1'}">
            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqStinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="stinSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqDlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="dlvrSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.hq"/>" binding="hqPackSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="salePrice.store"/>" binding="packSaleUprc" width="60" is-read-only="true" align="right" max-length="10" ></wj-flex-grid-column>
          </c:if>
        </wj-flex-grid>
      </div>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script>
  var priceEnvstVal = "${priceEnvstVal}";
  var subPriceFg = "${subPriceFg}";
  <%-- 가격관리구분 --%>
  var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/storeSalePrice/storeSalePrice.js?ver=20210910.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>