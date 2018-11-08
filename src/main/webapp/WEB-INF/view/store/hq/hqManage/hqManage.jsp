<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="hqManageCtrl">
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
        <%--본사코드 --%>
        <th><s:message code="hqManage.hqOfficeCd" /></th>
        <td>
          <input type="text" id="srchHqOfficeCd" ng-model="hqOfficeCd" maxlength="5" class="sb-input w100"/>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="hqManage.hqOfficeNm" /></th>
        <td>
          <input type="text" id="srchHqOfficeNm" ng-model="hqOfficeNm" maxlength="12" class="sb-input w100"/>
        </td>
      </tr>
      <tr>
        <%--사업자번호 --%>
        <th><s:message code="hqManage.bizNo" /></th>
        <td>
          <input type="text" id="srchBizNo" ng-model="bizNo" maxlength="10" class="sb-input w100"/>
        </td>
        <%-- 용도 --%>
        <th><s:message code="hqManage.clsFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchClsFg"
                    ng-model="clsFg"
                    items-source="_getComboData('clsFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 상태 --%>
        <th><s:message code="hqManage.sysStatFg" /></th>
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

  <div class="mt10 pdb10 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('hqManageCtrl')"><s:message code="cmm.search" /></button>
  </div>

  <div class="mt10 oh sb-select dkbr">
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
    <%--// 페이지 스케일  --%>

    <div class="tr">
      <%-- 본사신규등록 --%>
      <button class="btn_skyblue" id="btnRegist" ng-click="regist()"><s:message code="hqManage.newHq" /></button>
      <%-- sms 전송 //TODO --%>
      <%--<button class="btn_skyblue" id="btnSMS" ng-click="sendSms()><s:message code="hqManage.sendSMS" /></button>--%>
      <%-- 엑셀다운로드 //TODO--%>
      <%--<button class="btn_skyblue" id="btnExcel" ng-click="excelDown()"><s:message code="cmm.excel.down" /></button>--%>
    </div>
  </div>

  <%-- 본사 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:315px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              control="flex"
              autoGenerateColumns="false"
              selection-mode="Row"
              initialized="initGrid(s,e)"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="hqManage.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.hqOfficeNm"/>" binding="hqOfficeNm" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.ownerNm"/>" binding="ownerNm" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.weatherArea"/>" binding="areaCd" data-map="areaFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.clsFg"/>" binding="clsFg" data-map="clsFgDataMap" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.storeCnt"/>" binding="cnt" data-map="sysStatFgDataMap" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.sysOpenDate"/>" binding="sysOpenDate" align="center" width="110" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.posNm"/>" binding="posNm" align="center" width="110" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.telNo"/>" binding="telNo" align="center" width="110" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqManage.faxNo"/>" binding="faxNo" align="center" width="110" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" align="center" width="110" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="hqManageCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>
<script>
var clsFg = ${ccu.getCommCodeSelect("001")};
var sysStatFg = ${ccu.getCommCodeSelect("005")};
var areaCd = ${ccu.getCommCodeSelect("061")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/hqManage/hqManage.js?ver=2018102301" charset="utf-8"></script>

<%-- 본사 상세정보 레이어 --%>
<c:import url="/WEB-INF/view/store/hq/hqManage/hqInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 환경변수 --%>
<c:import url="/WEB-INF/view/store/hq/hqManage/hqEnv.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
