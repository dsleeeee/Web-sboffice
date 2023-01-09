<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeNm" value="${sessionScope.sessionInfo.hqOfficeNm}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>
<c:set var="authHqList" value="${authHqList}" />

<div class="subCon" ng-controller="storeManageCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('storeManageCtrl', 1)">
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
      <c:if test="${orgnFg != 'HQ'}">
        <tr>
          <%-- 본사코드 --%>
          <th><s:message code="storeManage.hqOfficeCd" /></th>
            <td><input type="text" id="srchHqOfficeCd" class="sb-input w100" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/></td>
          <%-- 본사명 --%>
          <th><s:message code="storeManage.hqOfficeNm" /></th>
          <td><input type="text" id="srchHqOfficeNm" class="sb-input w100" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();"/></td>
        </tr>
      </c:if>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="storeManage.storeCd" /></th>
        <td><input type="text" id="srchStoreCd" class="sb-input w100" ng-model="storeCd" onkeyup="fnNxBtnSearch();"/></td>
        <%-- 매장명 --%>
        <th><s:message code="storeManage.storeNm" /></th>
        <td><input type="text" id="srchStoreNm" class="sb-input w100" ng-model="storeNm" onkeyup="fnNxBtnSearch();"/></td>
      </tr>
      <tr>
        <%-- 사업자번호 --%>
        <th><s:message code="storeManage.bizNo" /></th>
        <td><input type="text" id="srchBizNo" class="sb-input w100" maxlength="10" ng-model="bizNo" placeholder="<s:message code='storeManage.bizNo.comment' />" onkeyup="fnNxBtnSearch();"/></td>
        <%-- 용도 --%>
        <th><s:message code="storeManage.clsFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchClsFg"
                    ng-model="clsFg"
                    items-source="_getComboData('srchClsFg')"
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
                    items-source="_getComboData('srchSysStatFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
          <c:if test="${brandUseFg != '1' or orgnFg != 'HQ'}">
            <td></td>
            <td></td>
          </c:if>
          <c:if test="${brandUseFg == '1'}">
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장브랜드 --%>
                <th><s:message code="storeManage.storeHqBrand" /></th>
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
            </c:if>
          </c:if>
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

    <c:if test="${orgnFg != 'STORE'}">
      <%-- 신규매장등록 --%>
      <button class="btn_skyblue ml5 fr" id="btnAddRepresent" ng-click="addStore()">
        <s:message code="storeManage.regist.new.store" />
      </button>
    </c:if>
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
  </div>

 <%-- 매장 그리드 --%>
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
        <wj-flex-grid-column header="<s:message code="storeManage.hqOffice"/>" binding="hqOfficeCdNm" visible="false" is-read-only="true"></wj-flex-grid-column>
        <c:if test="${orgnFg != 'HQ'}">
          <wj-flex-grid-column header="<s:message code="storeManage.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeManage.hqOfficeNm"/>" binding="hqOfficeNm" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
        </c:if>
        <wj-flex-grid-column header="<s:message code="storeManage.storeCd"/>" binding="storeCd" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.storeNm"/>" binding="storeNm" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.weatherArea"/>" binding="areaCd" data-map="areaFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.clsFg"/>" binding="clsFg" data-map="clsFgDataMap" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.sysOpenDate"/>" binding="sysOpenDate" align="center" width="110" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.van"/>" binding="vanNm" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.agency"/>" binding="agencyNm" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.storeLocation"/>" binding="storeLocation" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.addr"/>" binding="addr" align="center" width="0" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.latitude"/>" binding="latitude" align="center" width="0" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeManage.longitude"/>" binding="longitude" align="center" width="0" is-read-only="true" visible="false"></wj-flex-grid-column>

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

  <%-- 엑셀다운로드 그리드 --%>
  <div class="w100 mt10 mb20" style="display:none;" ng-controller="storeManageExcelCtrl">
    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              control="excelFlex"
              autoGenerateColumns="false"
              selection-mode="Row"
              initialized="initGrid(s,e)"
              sticky-headers="true"
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

</div>

<script>
  var clsFg = ${ccu.getCommCodeSelect("001")};
  var clsFgAll = ${ccu.getCommCode("001")}; // 전체 포함
  var sysStatFg = ${ccu.getCommCodeSelect("005")};
  var areaCd = ${ccu.getCommCodeSelect("061")};
  var orgnFg = "${orgnFg}";
  var orgnCd = "${orgnCd}";
  var orgnNm = "${orgnNm}";
  var pAgencyCd = "${pAgencyCd}";
  var authHqList = ${authHqList};
  var hqOfficeNm = "${hqOfficeNm}";

  // 본사에서 등록시 본사정보 자동셋팅
  var hqEnvst0027 = "${hqEnvst0027}"; // 매장코드 채번방식 [0:자동(기본) / 1:수동]
  var hqEnvst0043 = "${hqEnvst0043}"; // 본사신규상품매장생성 [0:자동(기본) / 1:수동]
  var hqSysStatFg = "${hqSysStatFg}"; // 본사 상태구분 값
  var digit8Store = "${digit8Store}"; // 매장코드 8 자리 이상 사용하는 본사인지 조회
  var erpLinkHq = "${erpLinkHq}"; // ERP를 연동하는 본사인지 확인
  var momsEnvstVal = "${momsEnvstVal}"; // [1250 맘스터치] 환경설정값
  // 브랜드 사용여부
  var brandUseFg = "${brandUseFg}";
  // 사용자 매장브랜드
  var userHqBrandCdComboList = ${userHqBrandCdComboList};

</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeManage.js?ver=20230109.01" charset="utf-8"></script>

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

<%-- 메뉴권한 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/storeAuth.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 지도보기 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/mapPop.jsp">
</c:import>