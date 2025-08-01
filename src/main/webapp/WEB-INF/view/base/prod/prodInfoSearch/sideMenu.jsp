<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon" id="sideMenuView" ng-controller="sideMenuCtrl" style="display: none;padding: 10px 20px 40px;">
  <%--searchTbl--%>
  <div class="searchBar">
    <a href="#" class="open fl"><s:message code="prodInfoSearch.sideMenu"/></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('sideMenuCtrl',1)">
        <s:message code="cmm.search" />
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
      <%-- 그룹코드 --%>
      <th><s:message code="prodInfoSearch.sideMenu.grpCd" /></th>
      <td>
        <input type="text" id="srchSdselGrpCd" ng-model="sdselGrpCd" class="sb-input w100" maxlength="13"/>
      </td>
      <%-- 그룹명 --%>
      <th><s:message code="prodInfoSearch.sideMenu.grpNm" /></th>
      <td>
        <input type="text" id="srchSdselGrpNm" ng-model="sdselGrpNm" class="sb-input w100" maxlength="100"/>
      </td>
    </tr>
    <tr>
      <%-- 분류코드 --%>
      <th><s:message code="prodInfoSearch.sideMenu.classCd" /></th>
      <td>
        <input type="text" id="srchSdselClassCd" ng-model="sdselClassCd" class="sb-input w100" maxlength="13" />
      </td>
      <%-- 분류명 --%>
      <th><s:message code="prodInfoSearch.sideMenu.classNm" /></th>
      <td>
        <input type="text" id="srchSdselSrchClassNm" ng-model="sdselClassNm" class="sb-input w100" maxlength="100"/>
      </td>
    </tr>
    <tr>
      <%-- 선택상품코드 --%>
      <th><s:message code="prodInfoSearch.sideMenu.prodCd" /></th>
      <td>
        <input type="text" id="srchSdselSelectProdCd" ng-model="sdselProdCd" class="sb-input w100" maxlength="13"/>
      </td>
      <%-- 선택상품명 --%>
      <th><s:message code="prodInfoSearch.sideMenu.prodNm" /></th>
      <td>
        <input type="text" id="srchSdselSelectProdNm" ng-model="sdselProdNm" class="sb-input w100" maxlength="100"/>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 oh sb-select dkbr">
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
  </div>

  <%--//searchTbl--%>
    <div class="wj-TblWrapBr mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 450px; overflow-x: hidden; overflow-y: hidden;">
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
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselGrpNm"/>" binding="sdselGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.fixProdFg"/>" binding="grpFixProdFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselTypeFg"/>" binding="sdselTypeFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselClassCd"/>" binding="sdselClassCd" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselQty"/>" binding="sdselQty" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.requireYn"/>" binding="requireYn" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
          <c:if test="${(orgnFg == 'HQ' and hqOfficeCd == 'A0001') or (orgnFg == 'HQ' and hqOfficeCd == 'DS019')}">
            <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.topYn"/>" binding="topYn" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.expandYn"/>" binding="expandYn" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.mappingYn"/>" binding="mappingYn" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          </c:if>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.addProdUprc"/>" binding="addProdUprc" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.addProdQty"/>" binding="addProdQty" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.fixProdFg"/>" binding="fixProdFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.regStoreFg"/>" binding="regStoreFg" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
          </c:if>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.printYn"/>" binding="printYn" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo2.modDt"/>"  binding="modDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo2.userNm"/>" binding="userNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.rcvkind"/>" binding="rcvkind" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
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
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselGrpNm"/>" binding="sdselGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.fixProdFg"/>" binding="grpFixProdFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselTypeFg"/>" binding="sdselTypeFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselClassCd"/>" binding="sdselClassCd" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.sdselQty"/>" binding="sdselQty" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.requireYn"/>" binding="requireYn" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
          <c:if test="${(orgnFg == 'HQ' and hqOfficeCd == 'A0001') or (orgnFg == 'HQ' and hqOfficeCd == 'DS019')}">
            <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.topYn"/>" binding="topYn" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.expandYn"/>" binding="expandYn" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.mappingYn"/>" binding="mappingYn" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
          </c:if>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.addProdUprc"/>" binding="addProdUprc" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.addProdQty"/>" binding="addProdQty" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.fixProdFg"/>" binding="fixProdFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.regStoreFg"/>" binding="regStoreFg" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
          </c:if>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.printYn"/>" binding="printYn" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo2.modDt"/>"  binding="modDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo2.userNm"/>" binding="userNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.rcvkind"/>" binding="rcvkind" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
    <%--//엑셀 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodInfoSearch/sideMenu.js?ver=20250710.01" charset="utf-8"></script>