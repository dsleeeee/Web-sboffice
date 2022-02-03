<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/cd/systemCd/systemCd/" />

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

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
        <th><s:message code="cd.nmcodeCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchNmcodeCd" ng-model="nmcodeCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 코드명 --%>
        <th><s:message code="cd.nmcodeNm" /></th>
        <td>
            <input type="text" class="sb-input w100" id="srchNmcodeNm" ng-model="nmcodeNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr style='display:none;'>
        <%-- 사용여부 --%>
        <th><s:message code="cd.useYnFg" /></th>
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
        <td>
        </td>
      </tr>
    </tbody>
  </table>


  <input type="hidden" id="s_nmcodeCd" name="s_nmcodeCd" value="" />
  <input type="hidden" id="s_nmcodeItem1" name="s_nmcodeItem1" value="" />
  <input type="hidden" id="s_nmcodeItem2" name="s_nmcodeItem2" value="" />

  <div id="gridRepresent" class="w50 fl" style="width: 40%" ng-controller="representCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='cd.grpGridNm' /></span>
        <button class="btn_skyblue" id="btnAddRepresent" style="display: none;" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelRepresent" style="display: none;" ng-click="deleteRow()">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveRepresent" style="display: none;" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div class="wj-gridWrap" style="height:315px">
        <div class="row">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            is-editable="false"
            item-formatter="_itemFormatter"
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cd.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cd.nmcodeGrpCd"/>" binding="nmcodeGrpCd" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cd.nmcodeCd"/>" binding="nmcodeCd" width="40" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cd.nmcodeNm"/>" binding="nmcodeNm" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cd.nmcodeItem1"/>" binding="nmcodeItem1" width="50" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cd.nmcodeItem2Cd"/>" binding="nmcodeItem2" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cd.useYnFg"/>" binding="useYn" data-map="useYnFgDataMap" width="100" visible="false"></wj-flex-grid-column>

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

  <div id="gridDetail" class="w50 fr" style="width: 60%" ng-controller="detailCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr ml10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='cd.gridNm' /></span>
        <button class="btn_skyblue" id="btnAddDetail" style="display: none;" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelDetail" style="display: none;" ng-click="deleteRow()">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveDetail" style="display: none;" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div class="wj-gridWrap" style="height:310px">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter"
          sorted-column="toggleFreeze(false)"
          ime-enabled="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cd.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cd.nmcodeCd"/>" binding="nmcodeCd" width="50" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cd.nmcodeNm"/>" binding="nmcodeNm" width="150"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cd.nmcodeItem1"/>" binding="nmcodeItem1" width="100"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cd.nmcodeItem2"/>" binding="nmcodeItem2" width="100"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="cd.useYnFg"/>" binding="useYn" data-map="useYnFgDataMap" width="100" visible="false"></wj-flex-grid-column>

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
<script type="text/javascript" src="/resource/solbipos/js/adi/etc/cd/cd.js?ver=20210106001" charset="utf-8"></script>
