<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/store/manage/virtualLogin/virtualLogin/"/>

<div class="subCon" ng-controller="gridCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
        <th><s:message code="virtualLogin.hqOfficeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" />
        </td>
        <%-- 본사명 --%>
        <th><s:message code="virtualLogin.hqOfficeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" />
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="virtualLogin.storeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
        </td>
        <%-- 매장명 --%>
        <th><s:message code="virtualLogin.storeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
        </td>
      </tr>
      <tr>
        <%-- 용도 --%>
        <th><s:message code="virtualLogin.clsFgNm" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchClsFg"
              ng-model="clsFg"
              control="clsFgCombo"
              items-source="_getComboData('srchClsFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <%-- 상태 --%>
        <th><s:message code="virtualLogin.sysStatFgNm" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchStatFg"
              ng-model="statFg"
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
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('gridCtrl');"><s:message code="cmm.search" /></button>
  </div>

  <div id="grid" class="w100">

    <div class="mt40 oh sb-select dkbr">
      <%-- 페이지 스케일  --%>
      <wj-combo-box
        class="w150 fl"
        id="listScaleBox"
        ng-model="listScale"
        control="listScaleCombo"
        items-source="_getComboData('listScaleBox')"
        display-member-path="name"
        selected-value-path="value"
        is-editable="false"
        initialized="_initComboBox(s)">
      </wj-combo-box>
      <%-- 엑셀 다운로드 //TODO --%>
      <%--<button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down" /></button>--%>
    </div>

    <div class="wj-gridWrap mt10" style="height:315px; overflow-y: hidden;">
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
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeCd"/>" binding="hqOfficeCd" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeNm"/>" binding="hqOfficeNm" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqUserId"/>" binding="hqUserId" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.storeCd"/>" binding="storeCd" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.storeNm"/>" binding="storeNm" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.msUserId"/>" binding="msUserId" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.clsFgNm"/>" binding="clsFgNm" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.sysStatFgNm"/>" binding="sysStatFgNm" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.ownerNm"/>" binding="ownerNm" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.telNo"/>" binding="telNo" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.mpNo"/>" binding="mpNo" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.agencyNm"/>" binding="agencyNm" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.cmUserId"/>" binding="cmUserId" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.sysOpenDate"/>" binding="sysOpenDate" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.sysClosureDate"/>" binding="sysClosureDate" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.orgnFg"/>" binding="orgnFg" ></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="gridCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="gridCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
  var clsFg = ${ccu.getCommCodeSelect("001")};
  var sysStatFg = ${ccu.getCommCodeSelect("005")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/virtualLogin/virtualLogin.js?ver=2018102701" charset="utf-8"></script>
