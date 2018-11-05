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

        <%--- 매장 그리드 --%>
        <div class="fl">
          <div class="wj-TblWrap mr10" style="height:350px;">
            <%--
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="coupon.regProd"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
            </div>
            --%>
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
        </div>
      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  var clsFg        = ${ccu.getCommCodeExcpAll("001")};
  var sysStatFg    = ${ccu.getCommCodeExcpAll("005")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/terminalManage/store.js?ver=20181006.01" charset="utf-8"></script>
