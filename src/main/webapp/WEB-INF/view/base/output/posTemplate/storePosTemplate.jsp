<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="storePosTemplateLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:500px;" >
  <div class="wj-dialog wj-dialog-columns title" ng-controller="storePosTemplateCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="posTemplate.prtFormToStore" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

      <input type="hidden" id="hdPrtClassCd" />
      <input type="hidden" id="hdApplyTempltRegFg" />
      <input type="hidden" id="hdTempltCd" />

    <%-- body --%>
    <div class="wj-dialog-body">
      <table class="tblType01">
        <colgroup>
          <col class="w30" />
          <col class="w70" />
        </colgroup>
        <tbody>
        <tr><%-- 적용할 템플릿명 --%>
          <th><s:message code="posTemplate.applyTempltNm"/></th>
          <td><label id="lblApplyTempltNm"></label></td>
        </tr>
        <tr><%-- 매장 --%>
          <th><s:message code="prod.storeCd"/></th>
          <td><input type="text" id="srchStoreCd" ng-model="storeCd" /></td>
        </tr>
        <tr>
          <th><s:message code="prod.storeNm"/></th>
          <td><input type="text" id="srchStoreNm" ng-model="storeNm" /></td>
        </tr>
        <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
          <tr> <%-- 매장상태 --%>
            <th><s:message code="prod.sysStatFg"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchSysStatFg"
                        ng-model="sysStatFg"
                        items-source="_getComboData('srchSysStatFg')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                </wj-combo-box>
              </div>
            </td>
          </tr>
        </c:if>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('storePosTemplateCtrl',1)" ><s:message code="cmm.search" /></button>
        <button class="btn_skyblue" id="btnApply" ng-click="storeApply()" ><s:message code="cmm.apply" /></button>
      </div>

      <%--- 적용매장 그리드 --%>
      <div class="oh mt20">
        <div class="fl" >
          <div class="wj-TblWrap mr10" style="height:395px; overflow-y:hidden;">
            <div class="oh">
              <span class="fl bk lh20 s14"><s:message code="prod.regStore"/></span>
            </div>
            <div id="regStoreGrid" class="mt10" style="height: 370px; overflow-y: hidden;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="70" is-read-only="true"  align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="230" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

              </wj-flex-grid>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">
  var sysStatFg = ${ccu.getCommCode("005")};
  var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/output/posTemplate/storePosTemplate.js?ver=20230315.01" charset="utf-8"></script>
