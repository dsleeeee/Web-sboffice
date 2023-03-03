<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/cd/envConfg/envConfg/" />

<div class="subCon">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr"  ng-click="_broadcast('representCtrl')" id="nxBtnSearch">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>
  <div>
    <table class="searchTbl" >
      <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
      </colgroup>
      <tbody>
      <tr>
        <%-- 설정코드 --%>
        <th><s:message code="envConfg.envstCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchEnvstCd" ng-model="envstCd" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 설정명 --%>
        <th><s:message code="envConfg.envstNm" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchEnvstNm" ng-model="envstNm" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%-- 설정그룹 --%>
        <th><s:message code="envConfg.envstFgNm" /></th>
        <td>
          <%--<input type="text" class="sb-input w100" id="srchEnvstFg" ng-model="envstFg" />--%>
          <div class="sb-select">
            <wj-combo-box
                    id="srchEnvstFg"
                    ng-model="envstFg"
                    items-source="_getComboData('envstFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    selected-index-changed="setSrchEnvstFg(s)">
            </wj-combo-box>
          </div>
        </td>
        <%-- 환경그룹 --%>
        <th><s:message code="envConfg.envstGrpCdNm" /></th>
        <td>
          <%--<input type="text" class="sb-input w100" id="srchEnvstGrpCd" ng-model="envstGrpCd" />--%>
          <div class="sb-select">
            <wj-combo-box
                    id="srchEnvstGrpCd"
                    ng-model="envstGrpCd"
                    items-source="_getComboData('envstGrpCd')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    selected-index-changed="setSrchEnvstGrpCd(s)">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <%-- 조회 --%>
    <div class="ml10 mt10 oh">
      <div class="mt10"><span style="color:#ff0000;"><b><s:message code="systemCd.prefixInfo"/></b></span></div>
      <div class="mt10"><span><s:message code="systemCd.prefix.0"/></span></div>
      <div class="mt5"><span><s:message code="systemCd.prefix.1"/></span></div>
      <div class="mt5"><span><s:message code="systemCd.prefix.2"/></span></div>
      <div class="mt5"><span><s:message code="systemCd.prefix.3"/></span></div>
      <div class="mt5"><span><s:message code="systemCd.prefix.4"/></span></div>
    </div>

    <div id="gridRepresent" class="w60 fl mt10" style="width: 60%" ng-controller="representCtrl">
      <%--위즈모 테이블--%>
      <div class="wj-TblWrapBr mr10 pd20" style="height: 480px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='systemCd.grpGridNm' /></span>
          <button class="btn_skyblue" id="btnAddRepresent" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
<%--          <button class="btn_skyblue" id="btnDelRepresent" style="display: none;">--%>
<%--            <s:message code="cmm.delete" />--%>
<%--          </button>--%>
          <button class="btn_skyblue" id="btnSaveRepresent" style="display: none;" ng-click="save()">
            <s:message code="cmm.save" />
          </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div class="wj-gridWrap" style="height:370px">
          <div class="row">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    beginning-edit="changeEnvstFg(s,e)"
                    ime-enabled="true">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envConfg.envstCd"/>" binding="envstCd" width="70" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envConfg.envstNm"/>" binding="envstNm" width="130"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envConfg.envstFgNm"/>" binding="envstFg" width="140" data-map="envstFgNmDataMap" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envConfg.envstGrpCdNm"/>" binding="envstGrpCdNm" width="100" data-map="envstGrpCdNmDataMap"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envConfg.dirctInYn"/>" binding="dirctInYn" width="70" data-map="dirctInYnDataMap"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envConfg.targtFgNm"/>" binding="targtFg" data-map="targtFgDataMap"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envConfg.useYn"/>" binding="useYn" width="80" data-map="useYnDataMap"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envConfg.remark"/>" binding="remark" width="200"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envConfg.envstRemark"/>" binding="envstRemark" width="200"></wj-flex-grid-column>

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

  </div>

  <div id="gridDetail" class="w40 fr mt10" style="width: 40%" ng-controller="detailCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr ml10 pd20" style="height: 480px;">
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
      <div class="wj-gridWrap" style="height:370px;">
        <div class="row">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter"
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="envConfg.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="envConfg.envstValCd"/>" binding="envstValCd" width="70"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="envConfg.envstValNm"/>" binding="envstValNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="envConfg.defltYn"/>" binding="defltYn" width="80" data-map="defltYnDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="envConfg.useYn"/>" binding="useYn" width="80" data-map="useYnDataMap"></wj-flex-grid-column>

          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="detailCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
      </div>

      </div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

</div>

<script type="text/javascript">
var envstFgNm = ${ccu.getCommCode("003")};
var envstGrpCdNm = ${envstGrpList};
var targtFg = ${ccu.getCommCodeExcpAll("038")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/cd/envConfg/envConfg.js?ver=2018111301" charset="utf-8"></script>
