<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>
<c:set var="baseUrl" value="/store/manage/virtualLogin/virtualLogin/"/>

<div class="subCon" ng-controller="posHwInfoCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch"  ng-click="_pageView('posHwInfoCtrl',1)">
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
        <th><s:message code="posHwInfo.hqOfficeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="posHwInfo.hqOfficeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="posHwInfo.storeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="posHwInfo.storeNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%-- 용도 --%>
        <th><s:message code="posHwInfo.clsFgNm" /></th>
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
        <%-- 매장상태 --%>
        <th><s:message code="posHwInfo.sysStatFgNm" /></th>
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
      <c:if test="${brandUseFg == '1'}">
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <tr>
            <%-- 매장브랜드 --%>
            <th><s:message code="posHwInfo.storeHqBrand" /></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchStoreHqBrandCd"
                  ng-model="storeHqBrandCd"
                  items-source="_getComboData('srchStoreHqBrandCd')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchStoreHqBrandCdCombo">
                </wj-combo-box>
              </div>
            </td>
            <th></th>
            <td></td>
          </tr>
        </c:if>
      </c:if>
    </tbody>
  </table>

  <div id="grid" class="w100">

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀 다운로드 --%>
      <button class="btn_skyblue ml5 fr" id="btnExcel" ng-click="excelDownload()">
        <s:message code="cmm.excel.down" />
      </button>
    </div>

    <div class="wj-gridWrap mt10" style="height:450px; overflow-y: hidden;">
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

          <wj-flex-grid-column header="<s:message code="posHwInfo.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.hqOfficeNm"/>" binding="hqOfficeNm"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.storeCd"/>" binding="storeCd" width="100" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.storeNm"/>" binding="storeNm" width="200"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.agencyNm"/>" binding="agencyNm" width="100" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.posNo"/>" binding="posNo" width="70" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.sysDtime"/>" binding="sysDtime" width="130" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.winVerNo"/>" binding="winVerNo" width="100" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.winBldNo"/>" binding="winBldNo" width="120" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.winEdtNo"/>" binding="winEdtNo" width="120" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.ieVerNo"/>" binding="ieVerNo" width="140" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.cTotDisk"/>" binding="cTotDisk" width="100" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.cRemainDisk"/>" binding="cRemainDisk" width="120" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.dTotDisk"/>" binding="dTotDisk" width="100" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.dRemainDisk"/>" binding="dRemainDisk" width="120" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.macAddress"/>" binding="macAddress" width="110" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.cpuInfo"/>" binding="cpuInfo" width="250" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="posHwInfo.memInfo"/>" binding="memInfo" width="80" align="center"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="posHwInfoCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
  </div>

</div>

<script type="text/javascript">
  var clsFg = ${ccu.getCommCode("001")};
  var sysStatFg = ${ccu.getCommCode("005")};
  var pAgencyCd = "${pAgencyCd}";
  var orgnFg = "${orgnFg}";
  // 브랜드 사용여부
  var brandUseFg = "${brandUseFg}";
  // 사용자 브랜드
  var userHqBrandCdComboList = ${userHqBrandCdComboList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/posHwInfo/posHwInfo.js?ver=20240315.01" charset="utf-8"></script>
