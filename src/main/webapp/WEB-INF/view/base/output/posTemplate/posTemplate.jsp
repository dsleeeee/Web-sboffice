<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/base/output/pos/" />

<div class="subCon" ng-controller="templateCtrl" style="overflow-y: hidden; overflow-x: auto;">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}
    </a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue" id="btnSrchTemplate" ng-click="_broadcast('templateCtrl')">
        <s:message code="posTemplate.srchBtnNm" />
      </button>
    </div>
  </div>

  <table class="searchTbl">
    <colgroup>
      <col class="w10" />
      <col class="w20" />
      <col class="w70" />
    </colgroup>
    <tbody>
      <tr>
        <th><s:message code="posTemplate.srchNm" /></th>
        <td colspan="2" class="oh">
          <div class="sb-select fl w200px">
            <wj-combo-box
              id="srchPrtClassCdCombo"
              ng-model="prtClassCd"
              control="prtClassCdCombo"
              items-source="_getComboData('srchPrtClassCdCombo')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)"
              selected-index-changed="setPrtClassCdCombo(s)"
              got-focus="prtClassCdComboFocus(s,e)">
            </wj-combo-box>
          </div>
        </td>
        <td>
        </td>
      </tr>
    </tbody>
  </table>

  <div class="wj-TblWrap mt20" style="display: flex; width: 1050px;">
    <%-- 템플릿 --%>
    <div class="fl mt10 mr10" style="width: 250px;">
      <%--위즈모 테이블--%>
      <div id="gridTemplate" class="wj-TblWrapBr pd20" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='posTemplate.gridNm' /></span>
          <button class="btn_skyblue" id="btnAddTemplate" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelTemplate" style="display: none;" ng-click="delete()">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSaveTemplate" style="display: none;" ng-click="save()">
            <s:message code="cmm.save" />
          </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div style="height:410px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter"
            ime-enabled="true"
            id="wjGridPosTemplate">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posTemplate.templtNm"/>" binding="templtNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posTemplate.prtForm"/>" binding="prtForm" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posTemplate.templtRegFg"/>" binding="templtRegFg" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posTemplate.templtCd"/>" binding="templtCd" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posTemplate.applyTempltCd"/>" binding="applyTempltCd" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posTemplate.applyTempltRegFg"/>" binding="applyTempltRegFg" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>

        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <%-- 코드리스트 --%>
    <div class="fl mt10 mr10" style="width: 160px;">
      <div class="wj-TblWrapBr pd20" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='posTemplate.listNm' /></span>
        </div>
        <div class="app-input-group">
          <wj-list-box
            style="width: 100%;height: 410px;"
            control="listBoxCodeList"
            initialized="initListBox(s,e)"
            display-member-path="prtCd"
            selected-value-path="prtCd"
            is-editable="false">
          </wj-list-box>
        </div>
      </div>
    </div>

    <%-- 편집 영역 --%>
    <div class="fl mt10 mr10" style="width: 325px;">
      <div class="wj-TblWrapBr pd20 templateEdit" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='posTemplate.editNm' /></span>
          <span class="fl bk lh30 s12" ng-if="showTempltRegFgNm">&nbsp;- {{templtRegFgNm}}등록 > {{templtEditableTxt}}</span>
          <button class="btn_skyblue" id="btnSaveEditTemplate" ng-if="showBtnSaveEdit" ng-click="saveEditTemplate()">
            <s:message code="cmm.save" />
          </button>
        </div>
        <div>
          <textarea id="editTextArea" class="w100" cols="42" style="height:410px;"></textarea>
        </div>
      </div>
    </div>

    <%-- 미리보기 영역 --%>
    <div class="fl mt10" style="width: 330px;">
      <div class="wj-TblWrapBr pd20 templateEdit" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='posTemplate.viewNm' /></span>
          <c:if test="${orgnFg == 'HQ'}">
            <button class="btn_skyblue" id="btnApplyStoreTemplate" ng-if="showBtnApplyStore" ng-click="applyToStoreReal()" style="font-size:0.7em; padding:0 5px;">
              <s:message code="posTemplate.prtFormToStore" />
            </button>
            <button class="btn_skyblue" id="btnApplyStoreTemplate" ng-if="showBtnApplyStore" ng-click="applyToStoreTemplate()" style="font-size:0.7em; padding:0 5px;">
              <s:message code="posTemplate.templtToStore" />
            </button>
          </c:if>
        </div>
        <div id="preview" class="s12 lh15" style="height:410px;">
        </div>
      </div>
    </div>
  </div>

</div>

<script type="text/javascript">
  var prtClassComboData = ${listPrintType};

  // POS에서 해당 WEB 화면 최초 접속한 경우(접속하면서 session 생성), 왼쪽 메뉴영역은 접어두기.
  // 최초 접속시에는 이전 URL 인자값으로 판별가능
  var referrer = document.referrer;
  if(referrer.indexOf("userId") > 0 && referrer.indexOf("resrceCd") > 0 && referrer.indexOf("accessCd") > 30 ){
      $(".menuControl").trigger("click");
  }

  // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값 판단하여 왼쪽 메뉴영역은 접어두기.
  // 재접속시에는 이전 URL 인자값이 없어, 로그인 여부 판별시에 특정 parameter 값을 보내 처리.
  if("${posLoginReconnect}" === "Y"){ // 직접입력한경우
      $(".menuControl").trigger("click");
  }
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/output/posTemplate/posTemplate.js?ver=20181112.04" charset="utf-8"></script>
<%-- 레이어 팝업 : 적용매장 선택 팝업 --%>
<c:import url="/WEB-INF/view/base/output/posTemplate/storePosTemplate.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
