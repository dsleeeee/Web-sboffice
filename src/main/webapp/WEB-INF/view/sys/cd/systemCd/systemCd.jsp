<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/cd/systemCd/systemCd/" />

<div class="subCon">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_broadcast('representCtrl')" id="nxBtnSearch">
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
        <%-- 코드 --%>
        <th><s:message code="systemCd.nmcodeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchNmcodeCd" ng-model="nmcodeCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 코드명 --%>
        <th><s:message code="systemCd.nmcodeNm" /></th>
        <td>
            <input type="text" class="sb-input w100" id="srchNmcodeNm" ng-model="nmcodeNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr><tr>
        <%-- 사용대상 --%>
        <th><s:message code="systemCd.useTargetFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchUseTargetFg"
              ng-model="useTargetFg"
              items-source="_getComboData('srchUseTargetFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <%-- 사용시스템 --%>
        <th><s:message code="systemCd.useSystemFg" /></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
              id="srchUseSystemFg"
              ng-model="useSystemFg"
              items-source="_getComboData('srchUseSystemFg')"
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

  <div id="gridRepresent" class="w50 fl mt10" style="width: 60%" ng-controller="representCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mr10 pd20" style="height: 480px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='systemCd.grpGridNm' /></span>
        <button class="btn_skyblue" id="btnAddRepresent" style="display: none;" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
<%--        <button class="btn_skyblue" id="btnDelRepresent" style="display: none;">--%>
<%--          <s:message code="cmm.delete" />--%>
<%--        </button>--%>
        <button class="btn_skyblue" id="btnSaveRepresent" style="display: none;" ng-click="save()">
          <s:message code="cmm.save" />
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
              frozen-columns="3"
              item-formatter="_itemFormatter"
              ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="systemCd.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="systemCd.nmcodeGrpCd"/>" binding="nmcodeGrpCd" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="systemCd.nmcodeCd"/>" binding="nmcodeCd" width="50"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="systemCd.nmcodeNm"/>" binding="nmcodeNm" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="systemCd.nmcodeItem1"/>" binding="nmcodeItem1" width="100"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="systemCd.nmcodeItem2"/>" binding="nmcodeItem2" width="100"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="systemCd.useColNm"/>" binding="useColNm" width="100"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="systemCd.useTargetFg"/>" binding="useTargetFg" data-map="useTargetFgDataMap" width="100"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="systemCd.useSystemFg"/>" binding="useSystemFg" data-map="useSystemFgDataMap" width="100"></wj-flex-grid-column>

          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="representCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <div id="gridDetail" class="w50 fr mt10 mb20" style="width: 40%" ng-controller="detailCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr pd10" style="height: 480px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='systemCd.gridNm' /></span>
        <button class="btn_skyblue" id="btnAddDetail" style="display: none;" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
<%--        <button class="btn_skyblue" id="btnDelDetail" style="display: none;">--%>
<%--          <s:message code="cmm.delete" />--%>
<%--        </button>--%>
        <button class="btn_skyblue" id="btnSaveDetail" style="display: none;" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                frozen-columns="2"
                sorted-column="toggleFreeze(false)"
                ime-enabled="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="systemCd.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeCd"/>" binding="nmcodeCd" width="50"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeNm"/>" binding="nmcodeNm" width="150"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeItem1"/>" binding="nmcodeItem1" width="100"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeItem2"/>" binding="nmcodeItem2" width="100"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.useTargetFg"/>" binding="useTargetFg" data-map="useTargetFgDataMap" width="100"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.useSystemFg"/>" binding="useSystemFg" data-map="useSystemFgDataMap" width="100"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="detailCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

</div>
<script type="text/javascript" src="/resource/solbipos/js/sys/cd/systemCd/systemCd.js?ver=2018111301" charset="utf-8"></script>
