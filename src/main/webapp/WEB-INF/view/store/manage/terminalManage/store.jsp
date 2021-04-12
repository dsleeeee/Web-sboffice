<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup id="storeLayer" control="storeLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:950px;">
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
          <c:if test="${orgnFg != 'HQ'}">
            <tr>
              <th><s:message code="terminalManage.hqOfficeCd"/></th>
              <td><input type="text" id="srchHqOfficeCd" ng-model="hqOfficeCd" /></td>
              <th><s:message code="terminalManage.hqOfficeNm"/></th>
              <td><input type="text" id="srchHqOfficeNm" ng-model="hqOfficeNm" /></td>
            </tr>
          </c:if>
          <tr>
            <th><s:message code="terminalManage.storeCd"/></th>
            <td><input type="text" id="srchStoreCd" ng-model="storeCd" /></td>
            <th><s:message code="terminalManage.storeNm"/></th>
            <td><input type="text" id="srchStoreNm" ng-model="storeNm" /></td>
          </tr>
          <tr>
            <%--<th><s:message code="terminalManage.clsFg"/></th>
            <td><input type="text" id="srchClsFg" ng-model="clsFg" /></td>
            <th><s:message code="terminalManage.sysStatFg"/></th>
            <td><input type="text" id="srchStatFg" ng-model="sysStatFg" /></td>--%>
              <%-- 용도 --%>
              <th><s:message code="storeManage.clsFg" /></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchClsFg"
                          ng-model="clsFg"
                          items-source="_getComboData('clsFg')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 상태 --%>
              <th><s:message code="terminalManage.sysStatFg" /></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchSysStatFg"
                          ng-model="sysStatFg"
                          items-source="_getComboData('sysStatFg')"
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
            <div id="storeGrid" style="height: 300px;">
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
                <c:if test="${orgnFg != 'HQ'}">
                  <wj-flex-grid-column header="<s:message code="terminalManage.hqOfficeCd"/>" binding="hqOfficeCd" ></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="terminalManage.hqOfficeCd"/>" binding="hqOfficeNm" ></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="terminalManage.storeCd"/>" binding="storeCd" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="terminalManage.storeNm"/>" binding="storeNm" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="terminalManage.clsFg"/>" binding="clsFg" data-map="clsFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="terminalManage.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  var clsFg = ${ccu.getCommCodeSelect("001")};
  var sysStatFg = ${ccu.getCommCodeSelect("005")};
  var hqOfficeCd = "${hqOfficeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/terminalManage/store.js?ver=2018100601.12" charset="utf-8"></script>
