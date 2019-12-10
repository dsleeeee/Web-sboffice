<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<wj-popup id="hqLayer" control="hqLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:600px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="application.hq.title" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="searchHqCtrl">

      <%-- 조회 조건 --%>
      <table class="tblType01 mt5">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="application.hq.search.hqOfficeCd"/></th>
          <td><input type="text" id="srchPopupHqOfficeCd" ng-model="hqOfficeCd" maxlength="5" /></td>
          <th><s:message code="application.hq.search.hqOfficeNm"/></th>
          <td><input type="text" id="srchPopupHqOfficeNm" ng-model="hqOfficeNm" maxlength="16" /></td>
        </tr>
        </tbody>
      </table>

        <div class="mt40 oh sb-select dkbr">
          <%-- 페이지 스케일  --%>
          <wj-combo-box
                  class="w100px fl"
                  id="listScaleBox"
                  ng-model="listScale"
                  control="listScaleCombo"
                  items-source="_getComboData('listScaleBox')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
          <%--// 페이지 스케일  --%>

          <%-- 신규매장등록 --%>
            <button class="btn_skyblue ml5 fr" id="btnSearch" ng-click="_pageView('searchHqCtrl', 1)"><s:message code="cmm.search" /></button>
        </div>

      <div class="oh mt10">
        <%--- 본사 그리드 --%>
        <div class="w100">
          <div class="wj-TblWrap mr10" style="height:210px; overflow-y: hidden;">
            <div id="hqGrid" style="height: 190px; overflow-y: hidden;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter"
                      is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="application.hq.search.hqOfficeCd"/>" binding="hqOfficeCd" width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="application.hq.search.hqOfficeNm"/>" binding="hqOfficeNm" width="*" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="application.hq.search.ownerNm"/>" binding="ownerNm" width="*" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="application.hq.search.clsFg"/>" binding="clsFg" width="70" align="center" data-map="clsFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="application.hq.search.sysStatFg"/>" binding="sysStatFg" width="70" align="center" data-map="sysStatFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="application.hq.search.0027"/>" binding="envst0027" width="70" align="center" visible="false"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>

          <%-- 페이지 리스트 --%>
          <div class="pageNum mt20">
            <%-- id --%>
            <ul id="searchHqCtrlPager" data-size="10">
            </ul>
          </div>
          <%--//페이지 리스트--%>

        </div>
      </div>
    </div>
  </div>
</wj-popup>
<script>
var clsFg     = ${ccu.getCommCodeExcpAll("001")};
var sysStatFg = ${ccu.getCommCode("005")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/application/layer/searchHq.js?ver=2018102301.10" charset="utf-8"></script>
