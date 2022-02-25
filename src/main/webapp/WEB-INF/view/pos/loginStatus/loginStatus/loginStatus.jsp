<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ufn" uri="solbipos/function" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div class="subCon" ng-controller="loginStatusCtrl">
  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('loginStatusCtrl', 1)">
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
      <%-- 조회 일자 --%>
      <tr>
        <th><s:message code="cmm.search.date" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      value="startDate"
                      ng-model="startDate"
                      control="startDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
            <span class="rg">~</span>
            <span class="txtIn w110px">
              <wj-input-date
                      value="endDate"
                      ng-model="endDate"
                      control="endDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
            <span class="chk ml10">
              <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
              <label for="chkDt">
                <s:message code="cmm.all.day" />
              </label>
            </span>
          </div>
      </tr>
      <tr>
        <%-- 본사코드 --%>
        <th><s:message code="cmm.hedofc.cd" /></th>
        <td>
          <input type="text" id="srchHqOfficeCd" ng-model="hqOfficeCd" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
        </td>
        <%-- 본사명 --%>
        <th><s:message code="cmm.hedofc.nm" /></th>
        <td>
          <input type="text" id="srchHqOfficeNm" ng-model="hqOfficeNm" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
        </td>
      </tr>
      <tr>
        <%-- 매장 코드 --%>
        <th><s:message code="cmm.mrhst.cd" /></th>
        <td>
          <input type="text" id="srchStoreCd" ng-model="storeCd" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
        </td>
        <%-- 매장명 --%>
        <th><s:message code="cmm.mrhst.nm" /></th>
        <td>
          <input type="text" id="srchStoreNm" ng-model="storeNm" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
        </td>
      </tr>
      <tr>
        <%-- 상태 --%>
        <th><s:message code="cmm.status" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchSysStatFg"
                    ng-model="sysStatFg"
                    items-source="_getComboData('sysStatFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <th></th>
        <td></td>
      </tr>
    </tbody>
  </table>

  <div class="mt20 oh sb-select dkbr">

    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>
  </div>

  <%-- 포스 로그인 현황 그리드 --%>
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
        <wj-flex-grid-column header="<s:message code="loginStatus.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loginStatus.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="120" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loginStatus.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loginStatus.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loginStatus.posNo"/>" binding="posNo" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loginStatus.loginDt"/>" binding="loginDt" align="center" width="140" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loginStatus.hwAuthKey"/>" binding="hwAuthKey" align="center" width="120" is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loginStatus.loginIp"/>" binding="loginIp" align="center" width="130" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loginStatus.posVerNo"/>" binding="posVerNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loginStatus.sysStatFgNm"/>" binding="sysStatFgNm" align="center" width="90" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>


  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="loginStatusCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script>
  var sysStatFg   = ${ccu.getCommCodeExcpAll("005")};

  var orgnFg = "${orgnFg}";
  var orgnCd = "${orgnCd}";
  var pAgencyCd = "${pAgencyCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/loginStatus/loginStatus.js?ver=2019010501.04" charset="utf-8"></script>
