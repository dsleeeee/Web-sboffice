<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" ng-controller="instructorMembrCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="instructorMembr.instructorMembr"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('instructorMembrCtrl')">
            <s:message code="cmm.search"/>
        </button>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w10"/>
            <col class="w20"/>
            <col class="w10"/>
            <col class="w20"/>
            <col class="w10"/>
            <col class="w20"/>
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="instructorMembr.instructorNm" /></th>
          <td>
              <div class="sb-select fl">
                <wj-combo-box
                    id="teacherCd"
                    ng-model="teacherCd"
                    items-source="_getComboData('teacherCdCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="teacherCdCombo"
                    initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
          </td>
          <th><s:message code="instructorMembr.classFg" /></th>
          <td>
              <div class="sb-select fl">
                  <wj-combo-box
                      id="classFg"
                      ng-model="classFg"
                      items-source="_getComboData('classFgCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      control="classFgCombo"
                      initialized="_initComboBox(s)">
                  </wj-combo-box>
              </div>
          </td>
          <th><s:message code="instructorMembr.membrUseYn" /></th>
          <td>
              <div class="sb-select fl">
                  <wj-combo-box
                      id="membrUseYn"
                      ng-model="membrUseYn"
                      items-source="_getComboData('membrUseYnCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      control="membrUseYnCombo"
                      initialized="_initComboBox(s)">
                  </wj-combo-box>
              </div>
          </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter"
                id="wjGrid">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="instructorMembr.membrNo"/>" binding="membrNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instructorMembr.membrNm"/>" binding="membrNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instructorMembr.registerDate"/>" binding="registerDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instructorMembr.startDate"/>" binding="useStartDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instructorMembr.endDate"/>" binding="useEndDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="instructorMembr.status"/>" binding="status" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
</div>

<script type="text/javascript">
    // 강사명
    var vTeacherCd = ${teacherCd};
    // 강습구분
    var vClassFg = ${classFg};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/reportKwu/instructorMembr/instructorMembr.js?ver=20220922.01" charset="utf-8"></script>