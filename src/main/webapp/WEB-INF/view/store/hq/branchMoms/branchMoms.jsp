<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" ng-controller="branchMomsCtrl">

  <div>
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('branchMomsCtrl', 1)">
          <s:message code="cmm.search" />
        </button>
      </div>
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
        <%-- 지사코드 --%>
        <th><s:message code="branchMoms.branchCd" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchBranchCd" ng-model="branchCd" onkeyup="fnNxBtnSearch();"/>
          </div>
        </td>
        <%-- 지사명 --%>
        <th><s:message code="branchMoms.branchNm" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchBranchNm" ng-model="branchNm" onkeyup="fnNxBtnSearch();"/>
          </div>
        </td>
      </tr>
      <tr>
        <%-- branchMoms.branchOwnerNm --%>
        <th><s:message code="branchMoms.branchOwnerNm" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchBranchOwnerNm" ng-model="branchOwnerNm" onkeyup="fnNxBtnSearch();"/>
          </div>
        </td>
        <%-- 휴대번호 --%>
        <th><s:message code="branchMoms.phoneNo" /></th>
        <td>
          <div class="sb-select">
            <input type="text" class="sb-input w100" id="srchPhoneNo" ng-model="phoneNo" onkeyup="fnNxBtnSearch();"/>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 사용여부 --%>
        <th><s:message code="hqBrand.useYn" /></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchUseYnFg"
                      ng-model="useYnFg"
                      items-source="_getComboData('srchUseYnFg')"
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
    <%--페이지 스케일 --%>
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
    <div class="tr">
      <%-- 신규등록 --%>
      <button class="btn_skyblue" id="btnAdd" ng-click="newAdd()"><s:message code="cmm.new.add" /></button>
    </div>
  </div>

  <%-- 위즈모 테이블 --%>
  <div class="wj-TblWrap mt10">
    <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter"
              is-read-only="true"
              frozen-columns="2"
              ime-enabled="true">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="branchMoms.branchCd"/>"       binding="branchCd" width="70" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.branchNm"/>"       binding="branchNm" width="120" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.branchOwnerNm"/>"  binding="branchOwnerNm" width="80" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.telNo"/>"          binding="telNo" width="100" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.phoneNo"/>"        binding="phoneNo" width="100" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.email"/>"          binding="email" width="120" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.addr"/>"           binding="addr" width="200" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.storeCnt"/>"       binding="storeCnt" width="70" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.useYn"/>"          binding="useYn" width="70" data-map="useYnDataMap" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.regDt"/>"          binding="regDt" width="80" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="branchMoms.regId"/>"          binding="regId" width="80" align="center"></wj-flex-grid-column>

        <wj-flex-grid-column header="<s:message code="branchMoms.orgplceInfo"/>"    binding="orgplceInfo" width="80" align="center" visible="false"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="branchMomsCtrlPager" data-size="10">
    </ul>
  </div>
</div>

<script>
  <%-- 사용 여부 --%>
  var useYn = ${ccu.getCommCodeExcpAll("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/hq/branchMoms/branchMoms.js?ver=20220923.01" charset="utf-8"></script>

<c:import url="/WEB-INF/view/store/hq/branchMoms/branchMomsDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>