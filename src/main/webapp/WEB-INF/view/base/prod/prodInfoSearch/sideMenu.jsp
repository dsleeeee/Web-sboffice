<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" id="sideMenuView" ng-controller="sideMenuCtrl" style="display: none;">
  <%--searchTbl--%>
  <div class="searchBar">
    <a href="#" class="open fl"><s:message code="prodInfoSearch.sideMenu"/></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('sideMenuCtrl')">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>

  <div class="mt10 oh sb-select dkbr">
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
  </div>

  <%--//searchTbl--%>
    <div class="wj-TblWrapBr mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 330px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                sticky-headers="true"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselGrpNm"/>" binding="sdselGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselClassCd"/>" binding="sdselClassCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselQty"/>" binding="sdselQty" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.addProdUprc"/>" binding="addProdUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.addProdQty"/>" binding="addProdQty" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.fixProdFg"/>" binding="fixProdFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
      <%-- id --%>
      <ul id="sideMenuCtrlPager" data-size="10">
      </ul>
    </div>
    <%-- //페이지 리스트 --%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="sideMenuExcelCtrl">
      <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
                id="wjGridExcelList"
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="excelFlex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselGrpNm"/>" binding="sdselGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselClassCd"/>" binding="sdselClassCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselQty"/>" binding="sdselQty" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.addProdUprc"/>" binding="addProdUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.addProdQty"/>" binding="addProdQty" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.fixProdFg"/>" binding="fixProdFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
    <%--//엑셀 리스트--%>
</div>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodInfoSearch/sideMenu.js?ver=20221223.01" charset="utf-8"></script>
