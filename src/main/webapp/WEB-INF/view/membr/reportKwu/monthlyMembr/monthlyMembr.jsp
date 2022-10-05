<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" ng-controller="monthlyMembrCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="monthlyMembr.monthlyMembr"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('monthlyMembrCtrl')">
            <s:message code="cmm.search"/>
        </button>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="cmm.search.month" /></th>
          <td>
              <div class="sb-select">
                  <span class="txtIn"><input id="startMonth" name="startMonth" class="w110px" /></span>
              </div>
          </td>
          <th><s:message code="monthlyMembr.classFg" /></th>
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
               <wj-flex-grid-column header="<s:message code="monthlyMembr.registerDate"/>" binding="registerDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.membrNm"/>" binding="membrNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.gender"/>" binding="gendrFg" width="50" align="center" is-read-only="true" data-map="gendrFgDataMap"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.age"/>" binding="age" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.addr"/>" binding="addr" width="250" align="left" is-read-only="true"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.telNo"/>" binding="telNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.startDate"/>" binding="useStartDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.endDate"/>" binding="useEndDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.useAmt"/>" binding="useAmt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.grindAmt"/>" binding="grindAmt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.skateFg"/>" binding="skateFg" width="90" align="center" is-read-only="true" data-map="skateFgDataMap"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.classFg"/>" binding="classFg" width="70" align="center" is-read-only="true" data-map="classFgDataMap"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.useWeek"/>" binding="useWeek" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.instructorNm"/>" binding="teacherCd" width="80" align="center" is-read-only="true" data-map="teacherCdDataMap"></wj-flex-grid-column>
               <wj-flex-grid-column header="<s:message code="monthlyMembr.remark"/>" binding="remark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>

           </wj-flex-grid>
       </div>
    </div>
</div>

<script type="text/javascript">
    // 강습구분(콤보박스용)
    var vComboClassFg = ${ComboClassFg};
    // 강사명
    var vTeacherCd = ${teacherCd};
    // 강습구분
    var vClassFg = ${classFg};
    // 스케이트종류
    var vSkateFg = ${skateFg};

</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/reportKwu/monthlyMembr/monthlyMembr.js?ver=20220922.01" charset="utf-8"></script>