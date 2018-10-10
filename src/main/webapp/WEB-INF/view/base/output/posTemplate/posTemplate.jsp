<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/output/pos/" />

<div class="subCon">

  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
          <div class="sb-select fl w200">
            <wj-combo-box
                    id="srchPrtClassCdCombo"
                    ng-model="prtClassCd"
                    items-source="_getComboData('srchPrtClassCdCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
            <input type="hidden" id="srchPrtClassCdVal" value={{prtClassCd}} />
          </div>
        </td>
        <td>
        </td>
      </tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 oh">
    <button class="btn_blue fr" id="btnSrchTemplate" ng-click="_broadcast('templateCtrl')">
      <s:message code="posTemplate.srchBtnNm" />
    </button>
  </div>


  <div class="wj-TblWrap mt40">
    <%-- 템플릿 --%>
    <div class="w25 fl">
      <%--위즈모 테이블--%>
      <div id="gridTemplate" class="wj-TblWrapBr pd20" style="height:485px;" ng-controller="templateCtrl">
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
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="posTemplate.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posTemplate.templtNm"/>" binding="templtNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posTemplate.prtForm"/>" binding="prtForm" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="posTemplate.templtRegFg"/>" binding="templtRegFg" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>

        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <%-- 코드리스트 --%>
    <div class="w15 fl">
      <div class="wj-TblWrapBr ml10 pd20" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='posTemplate.listNm' /></span>
        </div>
        <div class="app-input-group">
          <div id="listBoxCode" style="width: 100%;height: 410px;"></div>
        </div>
      </div>
    </div>

    <div class="w30 fl">
      <div class="wj-TblWrapBr ml10 pd20 templateEdit" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='posTemplate.editNm' /></span>
          <button class="btn_skyblue" id="btnSaveEditTemplate" style="display: none;" ng-click="$broadcast('saveEditTemplate')">
            <s:message code="cmm.save" />
          </button>
        </div>
        <div>
          <textarea id="editTextArea" class="w100" cols="42" style="height:410px;"></textarea>
        </div>
      </div>
    </div>

    <div class="w30 fl">
      <div class="wj-TblWrapBr ml10 pd20 templateEdit" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='posTemplate.viewNm' /></span>
        </div>
        <div id="preview" class="s12 lh15" style="height:410px;">
        </div>
      </div>
    </div>

  </div>

  <script type="text/javascript">
  var prtClassComboData = ${listPrintType};
  </script>
  <script type="text/javascript" src="/resource/solbipos/js/base/output/posTemplate/posTemplate.js?ver=20181010.01" charset="utf-8"></script>

</div>
