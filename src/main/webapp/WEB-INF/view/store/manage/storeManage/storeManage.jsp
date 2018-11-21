<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}"/>

<div class="subCon" ng-controller="storeManageCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storeManageCtrl')">
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
        <th><s:message code="storeManage.hqOfficeCd" /></th>
        <td><input type="text" id="srchHqOfficeCd" class="sb-input w100" maxlength="5" ng-model="hqOfficeCd"/></td>
        <%-- 본사명 --%>
        <th><s:message code="storeManage.hqOfficeNm" /></th>
        <td><input type="text" id="srchHqOfficeNm" class="sb-input w100" maxlength="15" ng-model="hqOfficeNm"/></td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="storeManage.storeCd" /></th>
        <td><input type="text" id="srchStoreCd" class="sb-input w100" maxlength="7" ng-model="storeCd"/></td>
        <%-- 매장명 --%>
        <th><s:message code="storeManage.storeNm" /></th>
        <td><input type="text" id="srchStoreNm" class="sb-input w100" maxlength="15" ng-model="storeNm"/></td>
      </tr>
      <tr>
        <%-- 사업자번호 --%>
        <th><s:message code="storeManage.bizNo" /></th>
        <td><input type="text" id="srchBizNo" class="sb-input w100" maxlength="10" ng-model="bizNo" placeholder="<s:message code='storeManage.bizNo.comment' />"/></td>
        <%-- 용도 --%>
        <th><s:message code="storeManage.clsFg" /></th>
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
        <th><s:message code="storeManage.sysStatFg" /></th>
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
        <td></td>
        <td></td>
      </tr>
    </tbody>
  </table>

  <div class="mt40 oh sb-select dkbr">
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

    <%-- 신규매장등록 --%>
    <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="addStore()">
      <s:message code="storeManage.regist.new.store" />
    </button>
  </div>

 <%-- 매장 그리드 --%>
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
        <wj-flex-grid-column header="<s:message code="storeManage.hqOffice"/>" binding="hqOfficeCdNm" visible="false" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.hqOfficeNm"/>" binding="hqOfficeNm" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.storeCd"/>" binding="storeCd" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.storeNm"/>" binding="storeNm" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.weatherArea"/>" binding="areaCd" data-map="areaFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.clsFg"/>" binding="clsFg" data-map="clsFgDataMap" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.sysOpenDate"/>" binding="sysOpenDate" align="center" width="110" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.van"/>" binding="vanNm" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.agency"/>" binding="agencyNm" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeManageCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script>
var clsFg = ${ccu.getCommCodeSelect("001")};
var sysStatFg = ${ccu.getCommCodeSelect("005")};
var areaCd = ${ccu.getCommCodeSelect("061")};

</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeManage.js?ver=2018102301" charset="utf-8"></script>

<%-- 매장정보 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/storeInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 환경변수 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/storeEnv.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
