<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div class="subCon" ng-controller="posSmartOrderConCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button id="nxBtnSearch" class="btn_blue fr"  ng-click="_pageView('posSmartOrderConCtrl',1)">
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
        <%-- 시스템일자 --%>
        <tr>
            <th><s:message code="posSmartOrderCon.sysDate" /></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w120px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w120px"></span>
                </div>
            </td>
        </tr>
      <c:if test="${sessionInfo.orgnFg != 'STORE'}">
        <c:if test="${sessionInfo.orgnFg != 'HQ'}">
          <tr>
            <%-- 본사코드 --%>
            <th><s:message code="posSmartOrderCon.hqOfficeCd" /></th>
            <td>
              <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 본사명 --%>
            <th><s:message code="posSmartOrderCon.hqOfficeNm" /></th>
            <td>
              <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();" />
            </td>
          </tr>
        </c:if>
        <tr>
          <%-- 매장코드 --%>
          <th><s:message code="posSmartOrderCon.storeCd" /></th>
          <td>
            <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();" />
          </td>
          <%-- 매장명 --%>
          <th><s:message code="posSmartOrderCon.storeNm" /></th>
          <td>
            <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();" />
          </td>
        </tr>
      </c:if>
      <tr>
        <%-- 주문접속타입 --%>
        <th><s:message code="posSmartOrderCon.connectType" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchConnectType"
              ng-model="connectType"
              control="connectTypeCombo"
              items-source="_getComboData('srchConnectType')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <%-- 전송응답 --%>
        <th><s:message code="posSmartOrderCon.result" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchResultCode"
              ng-model="resultCode"
              control="resultCodeCombo"
              items-source="_getComboData('srchResultCode')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)"
              selected-index="2">
            </wj-combo-box>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <div id="grid" class="w100">

    <div class="mt20 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
        <s:message code="cmm.excel.down" />
      </button>
    </div>

    <div class="wj-gridWrap mt10" style="height:370px; overflow-y: hidden;">
      <div class="row">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.hqOfficeNm"/>" binding="hqOfficeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.sysDate"/>" binding="sysDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.connectType"/>" binding="connectType" width="90" align="center" is-read-only="true" data-map="connectTypeDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.connectDt"/>" binding="connectDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.responseDt"/>" binding="responseDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.resultCode"/>" binding="resultCode" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posSmartOrderCon.resultMsg"/>" binding="resultMsg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="posSmartOrderConCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin/posSmartOrderCon/posSmartOrderCon.js?ver=20230620.01" charset="utf-8"></script>
