<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup id="storeLayer" control="storeLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:900px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="storeCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="terminalManage.select.store" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <table class="tblType01 mt20">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
          <tr>
            <th><s:message code="terminalManage.hqOfficeCd"/></th>
            <td><input type="text" id="srchHqOfficeCd" ng-model="hqOfficeCd" /></td>
            <th><s:message code="terminalManage.hqOfficeNm"/></th>
            <td><input type="text" id="srchHqOfficeNm" ng-model="hqOfficeNm" /></td>
          </tr>
          <tr>
            <th><s:message code="terminalManage.storeCd"/></th>
            <td><input type="text" id="srchStoreCd" ng-model="storeCd" /></td>
            <th><s:message code="terminalManage.storeNm"/></th>
            <td><input type="text" id="srchStoreNm" ng-model="storeNm" /></td>
          </tr>
          <tr>
            <th><s:message code="terminalManage.clsFg"/></th>
            <td><input type="text" id="srchClsFg" ng-model="clsFg" /></td>
            <th><s:message code="terminalManage.sysStatFg"/></th>
            <td><input type="text" id="srchStatFg" ng-model="sysStatFg" /></td>
          </tr>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('storeCtrl')" ><s:message code="cmm.search" /></button>
      </div>

      <div class="oh mt40">

        <%-- 페이지 스케일  --%>
        <div class="mt10 oh sb-select dkbr" style="display: none;">
          <wj-combo-box
                  class="w100px fl"
                  id="listScaleBox"
                  ng-model="listScale"
                  items-source="_getComboData('listScaleBox')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="initComboBox(s)">
          </wj-combo-box>
        </div>
        <%--// 페이지 스케일  --%>

        <%--- 매장 그리드 --%>
        <div class="fl">
          <div class="wj-TblWrap mr10" style="height:310px;">
            <div id="storeGrid" style="height: 270px;">
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
                <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="terminalManage.hqOfficeCd"/>" binding="hqOfficeCd" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="terminalManage.hqOfficeCd"/>" binding="hqOfficeNm" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="terminalManage.storeCd"/>" binding="storeCd" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="terminalManage.storeNm"/>" binding="storeNm" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="terminalManage.clsFg"/>" binding="clsFg" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="terminalManage.sysStatFg"/>" binding="sysStatFg"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>

          <%-- 페이지 리스트 --%>
          <div class="pageNum mt5 mb5">
            <ul id="storeCtrlPager" data-size="10">
            </ul>
          </div>
          <%--//페이지 리스트--%>
        </div>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  var clsFg        = ${ccu.getCommCodeExcpAll("001")};
  var sysStatFg    = ${ccu.getCommCodeExcpAll("005")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/terminalManage/store.js?ver=2018110501" charset="utf-8"></script>
