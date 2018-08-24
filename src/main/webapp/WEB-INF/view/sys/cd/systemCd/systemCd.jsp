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
    
  <div id="gridRepresent" class="w50 fl" style="width: 60%" ng-app="app" ng-controller="appTCCtrl">
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

        </wj-flex-grid>
        <!-- dialog used to pick and re-order grid columns -->
        <wj-popup control="pickColumn" show-trigger="Click" hide-trigger="Click" style="display: none;">
          <div class="wj-dialog wj-dialog-columns">
            <div class="wj-dialog-header wj-dialog-header-font">
              컬럼을 선택하세요.
              <a href="javascript:;" class="btn_close"></a>
            </div>
            <div class="wj-dialog-body">
              <div class="text-info mb10">
                표시하려는 열을 오른쪽의 목록으로 드래그하십시오.<br/>
                원하지 않는 항목을 왼쪽의 목록으로 드래그하십시오.<br/>
                오른쪽의 목록에서 드래그하여 열의 순서를 변경하십시오.<br/>
                그리드에 변경 사항을 적용하려면 "적용"버튼을 클릭하십시오.
              </div>
              <div id="columnPicker"></div>
            </div>
            <div class="wj-dialog-footer">
              <button class="btn wj-hide-apply btn_blue">적용</button>
              <button class="btn wj-hide btn_gray">취소</button>
            </div>
          </div>
        </wj-popup>
      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <div class="w50 fr" style="width: 40%">
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
      <div id="gridDetail" style="height:310px;"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
    
</div>
<script type="text/javascript" src="/resource/solbipos/js/sys/cd/systemCd/systemCd.js?ver=2018081301" charset="utf-8"></script>
