<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/bill/template/" />

<div class="subCon" ng-controller="templateCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSrchTemplate" ng-click="_broadcast('templateCtrl')">
        <s:message code="cmm.search" />
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
        <th><s:message code="template.srchNm" /></th>
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

  <%-- 조회 --%>
  <div class="mt10 oh">
    <button class="btn_blk fl" id="btnApplyTemplate" ng-click="$broadcast('showPopUp')">
      <s:message code="template.layerBtnNm" />
    </button>
  </div>

  <div class="wj-TblWrap mt40">
    <%-- 템플릿 --%>
    <div class="w25 fl">
      <%--위즈모 테이블--%>
      <div id="gridTemplate" class="wj-TblWrapBr pd20" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='template.gridNm' /></span>
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
            <wj-flex-grid-column header="<s:message code="template.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="template.templtNm"/>" binding="templtNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="template.prtForm"/>" binding="prtForm" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>

        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <%-- 코드리스트 --%>
    <div class="w20 fl">
      <div class="wj-TblWrapBr ml10 pd20" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='template.listNm' /></span>
        </div>
        <div class="app-input-group">
          <div id="listBoxCode" style="width: 100%;height: 410px;"></div>
        </div>
      </div>
    </div>

    <div class="fl" style="width:325px;">
      <div class="wj-TblWrapBr ml10 pd20 templateEdit" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='template.editNm' /></span>
          <button class="btn_skyblue" id="btnSaveEditTemplate" style="display: none;" ng-click="$broadcast('saveEditTemplate')">
            <s:message code="cmm.save" />
          </button>
        </div>
        <div>
          <textarea id="editTextArea" class="w100" cols="42" style="height:410px;"></textarea>
        </div>
      </div>
    </div>

    <div class="fl" style="width:325px;">
      <div class="wj-TblWrapBr ml10 pd20 templateEdit" style="height:485px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='template.viewNm' /></span>
        </div>
        <div id="preview" class="s12 lh15" style="height:410px;">
        </div>
      </div>
    </div>

  </div>

<script type="text/javascript">
  var prtClassComboData = ${listPrintType};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/bill/template/template.js?ver=2018111201" charset="utf-8"></script>

  <%-- 레이어 팝업 --%>
  <c:import url="/WEB-INF/view/sys/bill/template/popUpTemplate.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
  </c:import>

</div>
