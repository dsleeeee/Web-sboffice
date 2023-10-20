<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="lsmStoreCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="nxBtnSearch" ng-click="_broadcast('lsmStoreCtrl')">
        <s:message code="cmm.search"/>
      </button>
    </div>
  </div>
  <div class="mt10 oh sb-select dkbr">
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 500px; overflow-y: hidden; overflow-x: hidden;">
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
        <wj-flex-grid-column header="<s:message code="lsmStore.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.tukeyGrpCd"/>" binding="tukeyGrpCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.tukeyGrpNm"/>" binding="tukeyGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.tukeyClassCd"/>" binding="tukeyClassCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.tukeyClassNm"/>" binding="tukeyClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.saleUprc"/>" binding="saleUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="lsmStoreCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
  <%-- 엑셀 리스트 --%>
  <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="lsmStoreExcelCtrl">
    <div class="wj-gridWrap">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="excelFlex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="lsmStore.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.tukeyGrpCd"/>" binding="tukeyGrpCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.tukeyGrpNm"/>" binding="tukeyGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.tukeyClassCd"/>" binding="tukeyClassCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.tukeyClassNm"/>" binding="tukeyClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="lsmStore.saleUprc"/>" binding="saleUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
  <%--//엑셀 리스트 --%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/lsmStore/lsmStore.js?ver=20230426.01" charset="utf-8"></script>