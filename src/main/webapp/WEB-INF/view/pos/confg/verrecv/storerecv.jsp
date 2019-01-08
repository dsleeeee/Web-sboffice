<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<%--<c:set var="baseUrl" value="/pos/confg/verRecv/storeRecv/"/>--%>

<div class="subCon" ng-controller="storeRecvCtrl">
  <%-- 탭 --%>
  <ul class="subTab mb20">
    <li><a href="#" id="verrecv" onclick="changeTab('R')"><s:message code="verRecv.verrecv" /></a></li>
    <li><a href="#" id="storerecv" class="on" onclick="changeTab('S')"><s:message code="verRecv.storerecv" /></a></li>
    <li><a href="#" id="verstore" onclick="changeTab('V')"><s:message code="verRecv.verstore" /></a></li>
  </ul>

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" onclick="getStoreList()">
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
      <tr>
        <%-- 본사코드 --%>
        <th><s:message code="verRecv.hqOfficeCd" /></th>
        <td>
          <input type="text" id="hqOfficeCd" name="hqOfficeCd" ng-model="hqOfficeCd" class="sb-input" maxlength="7" size="50">
        </td>
        <%-- 본사명 --%>
        <th><s:message code="verRecv.hqOfficeNm" /></th>
        <td>
          <input type="text" id="hqOfficeNm" name="hqOfficeNm" ng-model="hqOfficeNm" class="sb-input" maxlength="16" size="50">
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="verRecv.storeCd" /></th>
        <td>
          <input type="text" id="storeCd" name="storeCd" ng-model="storeCd" class="sb-input" maxlength="5" size="50">
        </td>
        <%-- 매장명 --%>
        <th><s:message code="verRecv.storeNm" /></th>
        <td>
          <input type="text" id="storeNm" name="storeNm" ng-model="storeNm" class="sb-input" maxlength="16" size="50">
        </td>
      </tr>
    </tbody>
  </table>

  <%-- 페이지 스케일  --%>
  <wj-combo-box
          class="w100px fl"
          id="listScaleBox"
          ng-model="listScale"
          items-source="_getComboData('listScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          is-editable="false"
          initialized="initComboBox(s)"
          ng-hide="true">
  </wj-combo-box>

  <div class="mt20 oh sb-select dkbr">
    <%-- 엑셀다운로드 버튼 //TODO --%>
    <%--
    <div class="w150px fl"></div>
    <button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down" /></button>
    --%>
  </div>

  <%-- 매장별 수신현황 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              control="flex"
              autoGenerateColumns="false"
              selection-mode="Row"
              initialized="initGrid(s,e)"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="90" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="140" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="180" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.storeCd"/>" binding="storeCd" width="130" align="center" is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.posCnt"/>" binding="posCnt" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="page" data-size="10">
    </ul>
  </div>
</div>
<script>
  var mainYn = ${cnv.getEnvCodeExcpAll("4021")};
  var posFg  = ${cnv.getEnvCodeExcpAll("4020")};
  var verRecvFg = ${ccu.getCommCodeExcpAll("060")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verRecv/storeRecv.js?ver=20190107.01" charset="utf-8"></script>

<%-- 매장추가 레이어 --%>
<c:import url="/WEB-INF/view/pos/confg/verrecv/storeRecvDtl.jsp">
</c:import>
