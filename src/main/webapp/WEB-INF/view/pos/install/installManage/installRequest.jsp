<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="installRegistPopupLayer" control="installRegistPopupLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="outstockConfmDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="installRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="instl.req"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
          <tr>
            <%-- 본사코드 --%>
            <th><s:message code="instl.hqOfficeCd"/></th>
            <td><input type="text" id="hqOfficeCd" class="sb-input w100" ng-model="hqOfficeCd"/></td>
            <%-- 본사명 --%>
            <th><s:message code="instl.hqOfficeNm"/></th>
            <td><input type="text" id="hqOfficeNm" class="sb-input w100" ng-model="hqOfficeNm"/></td>
          </tr>
          <tr>
            <%-- 매장코드 --%>
            <th><s:message code="instl.storeCd"/></th>
            <td><input type="text" id="storeCd" class="sb-input w100" ng-model="storeCd"/></td>
            <%-- 매장명 --%>
            <th><s:message code="instl.storeNm"/></th>
            <td><input type="text" id="storeNm" class="sb-input w100" ng-model="storeNm"/></td>
          </tr>
          <tr>
            <%-- 대리점 --%>
            <th><s:message code="instl.agency"/></th>
            <td>
              <input type="text" id="pSrchAgencyNm" value="선택" class="sb-input w100" ng-readonly="true" ng-click="searchAgency('1')"/>
              <input type="text" id="pSrchAgencyCd" ng-hide="true"/>
            </td>
            <%-- 설치구분 --%>
            <th><s:message code="instl.instFg"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchInstFg"
                        ng-model="instFg"
                        items-source="_getComboData('srchInstFg')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <%-- 조회 --%>
      <div class="mt10 pdb20 oh">
        <button class="btn_skyblue fr" id="searchBtn" ng-click="_pageView('installRegistCtrl', 1)">
          <s:message code="cmm.search" />
        </button>
      </div>

      <%-- 설치업체, 설치사유 입력 --%>
      <table class="tblType01 mt10">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 대리점 --%>
          <th><s:message code="instl.agency"/></th>
          <td>
            <input type="text" id="agencyNm" value="선택" class="sb-input w100" ng-readonly="true" ng-click="searchAgency('2')"/>
            <input type="text" id="agencyCd" ng-hide="true"/>
          </td>
          <%-- 설치사유 --%>
          <th><s:message code="instl.instReason"/></th>
          <td>
            <div class="sb-select w100">
              <wj-combo-box
                      id="instReason"
                      ng-model="request.instReason"
                      items-source="_getComboData('reasonCombo')"
                      control="instReasonCombo"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      selected-index-changed="setSelectedCombo(s)">
              </wj-combo-box>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 기타사유 --%>
          <th><s:message code="instl.remark"/></th>
          <td colspan="3">
            <div class="w80 s12 txtIn">
              <input type="text" class="sb-input w100" ng-model="request.remark" maxlength="60" ng-readonly="reasonReadOnly"/>
            </div>
            <a href="#" class="btn_grayS2" ng-click="request()"><s:message code="instl.install.request"/></a>
          </td>
        </tr>
        </tbody>
      </table>

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
              initialized="_initComboBox(s)"
              ng-hide="true">
      </wj-combo-box>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px;">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  selection-mode="Row"
                  items-source="data"
                  control="flex"
                  initialized="initGrid(s,e)"
                  is-read-only="false"
                  item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cd.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.hqOfficeNm"/>" binding="hqOfficeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.storeNm"/>" binding="storeNm" width="140" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.agencyNm"/>" binding="agencyNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.posNo"/>" binding="posNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.sysStatFg"/>" binding="sysStatFg" width="80" align="center" data-map="sysStatFgDataMap" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.instFg"/>" binding="instFg" width="70" align="center" data-map="instFgDataMap" is-read-only="true" data-map="instFgDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.seqNo"/>" binding="seqNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.instReason"/>" binding="instReason" width="70" data-map="reasonDataMap" is-read-only="true" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instl.remark"/>" binding="remark" width="200" is-read-only="true" ></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>

      <%-- 페이지 리스트 --%>
      <div class="pageNum2 mt20">
        <%-- id --%>
        <ul id="installRegistCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script>
  var reasonData = ${ccu.getCommCodeExcpAll("102")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/install/installManage/installRequest.js?ver=2019010302.01" charset="utf-8"></script>

