<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/cd/systemCd/systemCd/" />

<div class="subCon">
  
  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a>
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
          <div class="sb-select">
            <div id="srchNmcodeCd"></div>
          </div>
        </td>
        <%-- 코드명 --%>
        <th><s:message code="systemCd.nmcodeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchNmcodeNm"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
  
  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
      <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>
    
  <%--<div id="gridRepresent" class="w50 fl" style="width: 60%" ng-app="representApp" ng-controller="representCtrl">--%>
  <div id="gridRepresent" class="w50 fl" style="width: 60%" ng-app="representApp" ng-controller="representCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='systemCd.grpGridNm' /></span>
        <button class="btn_skyblue" id="btnAddRepresent" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelRepresent" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveRepresent" style="display: none;">
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
                item-formatter="itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="systemCd.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeCd"/>" binding="nmcodeCd" width="60"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeNm"/>" binding="nmcodeNm" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeItem1"/>" binding="nmcodeItem1" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeItem2"/>" binding="nmcodeItem2" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.useColNm"/>" binding="useColNm" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeGrpCd"/>" binding="nmcodeGrpCd" visible="false"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="representApp"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <%--<div id="gridDetail" class="w50 fr" style="width: 40%" ng-app="detailApp" ng-controller="detailCtrl">--%>
  <div id="gridDetail" class="w50 fr" style="width: 40%" ng-app="detailApp"  ng-controller="detailCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr ml10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='systemCd.gridNm' /></span>
        <button class="btn_skyblue" id="btnAddDetail" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelDetail" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveDetail" style="display: none;">
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
                item-formatter="itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="systemCd.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeCd"/>" binding="nmcodeCd" width="60"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeNm"/>" binding="nmcodeNm" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeItem1"/>" binding="nmcodeItem1" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="systemCd.nmcodeItem2"/>" binding="nmcodeItem2" width="*"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="detailApp"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

</div>
<script type="text/javascript" src="/resource/solbipos/js/sys/cd/systemCd/systemCd.js?ver=2018081301" charset="utf-8"></script>
