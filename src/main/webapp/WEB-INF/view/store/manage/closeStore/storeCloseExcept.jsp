<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div id="storeCloseExcept" class="subCon" ng-controller="storeCloseExceptCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl"><s:message code="storeCloseExcept.storeCloseExcept" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_pageView('storeCloseExceptCtrl',1)" id="nxBtnSearch2">
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
      <%-- 밴사코드 --%>
      <th><s:message code="storeCloseExcept.vanCd" /></th>
      <td>
        <div class="sb-select">
          <wj-combo-box
                  id="srchVanCd"
                  ng-model="vanCd"
                  control="vanCdCombo"
                  items-source="_getComboData('srchVanCd')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
      </td>
      <%-- 매장상태 --%>
      <th><s:message code="storeCloseExcept.sysStatFg" /></th>
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
    <c:if test="${orgnFg == 'MASTER'}">
    <tr>
      <%-- 관리업체코드 --%>
      <th><s:message code="storeCloseExcept.agencyCd" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchAgencyCd" ng-model="agencyCd" onkeyup="fnNxBtnSearch('2');"/>
      </td>
      <%-- 관리업체명 --%>
      <th><s:message code="storeCloseExcept.agencyNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchAgencyNm" ng-model="agencyNm" onkeyup="fnNxBtnSearch('2');"/>
      </td>
    </tr>
    </c:if>
    <tr>
      <%-- 본사코드 --%>
      <th><s:message code="storeCloseExcept.hqOfficeCd" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch('2');"/>
      </td>
      <%-- 본사명 --%>
      <th><s:message code="storeCloseExcept.hqOfficeNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch('2');"/>
      </td>
    </tr>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="storeCloseExcept.storeCd" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch('2');"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="storeCloseExcept.storeNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch('2');"/>
      </td>
    </tr>
    </tbody>
  </table>


  <div id="gridRepresent" class="w50 fl mt10" style="width: 50%" ng-controller="storeCloseExceptCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mr10 pd10" style="height: 480px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='storeCloseExcept.storeCloseExcept' /></span>
        <button class="btn_skyblue ml5 fr" id="btnDel" ng-click="delete()">
          <s:message code="cmm.delete" />
        </button>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
          <s:message code="cmm.excel.down" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
        <div class="row" >
          <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter"
              frozen-columns="1"
              ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeCloseExcept.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeCloseExcept.hqOfficeNm"/>" binding="hqOfficeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeCloseExcept.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeCloseExcept.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeCloseExcept.agencyNm"/>" binding="agencyNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeCloseExcept.vanCd"/>" binding="vanCd" width="100" align="center" is-read-only="true" data-map="vanCdDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeCloseExcept.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <div id="gridDetail" class="w50 fr mt10 mb10" style="width: 50%" ng-controller="storeCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr pd10" style="height: 480px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='storeCloseExcept.store' /></span>
        <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
          <s:message code="cmm.excel.down" />
        </button>
      </div>
      <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                frozen-columns="1"
                sorted-column="toggleFreeze(false)"
                ime-enabled="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCloseExcept.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCloseExcept.hqOfficeNm"/>" binding="hqOfficeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCloseExcept.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCloseExcept.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCloseExcept.agencyNm"/>" binding="agencyNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCloseExcept.vanCd"/>" binding="vanCd" width="100" align="center" is-read-only="true" data-map="vanCdDataMap"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeCloseExcept.remark"/>" binding="remark" width="100" align="left"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

</div>

<script type="text/javascript">
  var sysStatFg = ${ccu.getCommCode("005")};
  var vanComboList = ${vanComboList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/closeStore/storeCloseExcept.js?ver=20240422.01" charset="utf-8"></script>
