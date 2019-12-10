<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>
<c:set var="baseUrl" value="/store/manage/virtualLogin/virtualLogin/"/>

<div class="subCon" ng-controller="gridCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch"  ng-click="_pageView('gridCtrl',1)">
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
      <tr <c:if test="${orgnFg == 'HQ'}">style="display: none;"</c:if>>
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
    </tbody>
  </table>

  <div id="grid" class="w100">

    <div class="mt40 oh sb-select dkbr">
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
          item-formatter="_itemFormatter"
          is-read-only="true">

          <!-- define columns -->

          <%--<wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" ng-if="userOrgnFg == 'M'"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeNm"/>" binding="hqOfficeNm" ng-if="userOrgnFg == 'M'"></wj-flex-grid-column>--%>
          <%--<wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">visible="false"</c:if>></wj-flex-grid-column>--%>
          <%--<wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeNm"/>" binding="hqOfficeNm" <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">visible="false"</c:if>></wj-flex-grid-column>--%>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqOfficeNm"/>" binding="hqOfficeNm"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.hqUserId"/>" binding="hqUserId" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.storeCd"/>" binding="storeCd" width="100" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.storeNm"/>" binding="storeNm" width="200"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.msUserId"/>" binding="msUserId" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.clsFgNm"/>" binding="clsFgNm" width="80" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.sysStatFgNm"/>" binding="sysStatFgNm" width="80" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.ownerNm"/>" binding="ownerNm" width="90" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.telNo"/>" binding="telNo" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.mpNo"/>" binding="mpNo" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.agencyNm"/>" binding="agencyNm" width="100" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.cmUserId"/>" binding="cmUserId" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.sysOpenDate"/>" binding="sysOpenDate" width="110" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.sysClosureDate"/>" binding="sysClosureDate" width="110" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="virtualLogin.orgnFg"/>" binding="orgnFg" visible="false"></wj-flex-grid-column>

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
  var pAgencyCd = "${pAgencyCd}";
  var orgnFg = "${orgnFg}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/virtualLogin/virtualLogin.js?ver=2018120601.06" charset="utf-8"></script>
