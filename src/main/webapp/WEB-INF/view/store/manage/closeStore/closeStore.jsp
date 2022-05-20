<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div class="subCon" ng-controller="closeStoreCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch"  ng-click="_pageView('closeStoreCtrl',1)">
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
      <%-- --%>
      <th><s:message code="closeStore.startMonth" /></th>
      <td>
        <div class="sb-select">
          <span class="txtIn"><input id="startMonth" name="startDate" class="w110px" /></span>
        </div>
      </td>
      <%-- 매장상태 --%>
      <th><s:message code="closeStore.sysStatFg" /></th>
      <td>
        <div class="sb-select">
          <wj-combo-box
                  id="srchStatFg"
                  ng-model="sysStatFg"
                  control="statFgCombo"
                  items-source="_getComboData('srchStatFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 관리업체코드 --%>
      <th><s:message code="closeStore.agencyCd" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchAgencyCd" ng-model="agencyCd" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 관리업체명 --%>
      <th><s:message code="closeStore.agencyNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchAgencyNm" ng-model="agencyNm" onkeyup="fnNxBtnSearch();"/>
      </td>
    </tr>
    <tr>
      <tr>
        <%-- 본사코드 --%>
        <th><s:message code="closeStore.hqOfficeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="closeStore.hqOfficeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="closeStore.storeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="closeStore.storeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="closeStore.remark" /></th>
      <td colspan="3">
        <input type="text" class="sb-input w90" id="remark" ng-model="remark"/>
        <button class="btn_skyblue" id="btnCloseStore" ng-click="closeStore()">
          <s:message code="closeStore.closeStore" />
        </button>
      </td>
    </tr>
    </tbody>
  </table>

  <div id="grid" class="w100">
    <div class="mt20 oh sb-select dkbr">
      <%-- 페이지 스케일  --%>
      <wj-combo-box
        class="w100px fl"
        id="listScaleBox"
        ng-model="listScale"
        control="listScaleCombo"
        items-source="_getComboData('listScaleBox')"
        display-member-path="name"
        selected-value-path="value"
        is-editable="false"
        initialized="_initComboBox(s)">
      </wj-combo-box>
      <span class="s12 fl">※ 기준 년월로부터 매장 등록한지 3개월 이상이고, 1개월 동안 매출 자료가 발생되지 않아 익월 자동으로 폐점될 매장 목록입니다.</span>

      <%-- 엑셀 다운로드 //TODO --%>
      <%--<button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down" /></button>--%>
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

          <wj-flex-grid-column header="<s:message code="closeStore.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="closeStore.hqOfficeNm"/>" binding="hqOfficeNm" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="closeStore.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="closeStore.storeNm"/>" binding="storeNm" width="200" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="closeStore.agencyNm"/>" binding="agencyNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="closeStore.maxSaleDate"/>" binding="maxSaleDate" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="closeStore.regDate"/>" binding="regDt" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="closeStore.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="closeStoreCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="closeStoreCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
  var sysStatFg = ${ccu.getCommCode("005")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/closeStore/closeStore.js?ver=20220422.01" charset="utf-8"></script>
