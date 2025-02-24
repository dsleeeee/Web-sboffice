<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<wj-popup control="versionInfoDetailLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:370px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="verAddr.verInfo" />
      <span id="versionDetailTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="verDetailCtrl">

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 버전정보 --%>
        <li><a id="storeInfo" href="#" class="on"><s:message code="verAddr.verInfo" /></a></li>
        <%-- 적용매장 --%>
        <li><a id="storeEnv" href="#" ng-click="changeTab();"><s:message code="verAddr.store.registed" /></a></li>
      </ul>

      <div>
        <div style="height:170px; overflow-y: auto;">
          <f:form id="viewForm" name="viewForm" >
            <h3 class="h3_tbl"><s:message code="storeManage.basicInfo" /></h3>
            <table class="searchTbl">
              <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
              </colgroup>
              <tbody>
              <tr>
                <%-- 버전일련번호 --%>
                <th><s:message code="verAddr.verSerNo" /></th>
                <td>{{version.verSerNo}}</td>
                <%-- 버전적용명 --%>
                <th><s:message code="verAddr.verSerNm" /></th>
                <td>{{version.verSerNm}}</td>
              </tr>
              <tr>
                <%-- 파일사이즈 --%>
                <th><s:message code="verAddr.fileSize" /></th>
                <td>{{version.fileSize}}</td>
                <%-- 프로그램구분 --%>
                <th><s:message code="verAddr.progFg" /></th>
                <td>
                  <wj-combo-box
                          ng-model="version.progFg"
                          ng-hide="true"
                          text="_progFg"
                          items-source="_getComboData('progFgCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false">
                  </wj-combo-box>
                  {{_progFg}}
                </td>
              </tr>
              <tr>
                  <%-- 포함내역 --%>
                <th><s:message code="verAddr.incldDtls" /></th>
                <td>{{version.incldDtls}}</td>
                  <%-- 사용여부 --%>
                <th><s:message code="verAddr.useYn" /></th>
                <td>
                  <wj-combo-box
                          ng-model="version.useYn"
                          ng-hide="true"
                          text="_useYn"
                          items-source="_getComboData('useYnCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false">
                  </wj-combo-box>
                  {{_useYn}}
                </td>
              </tr>
              <tr>
                  <%-- 비고 --%>
                <th style="height:65px;"><s:message code="verAddr.fileDesc" /></th>
                <td colspan="3">{{version.fileDesc}}</td>
              </tr>
              </tbody>
            </table>
          </f:form>
        </div>
        <div class="btnSet2">
          <c:if test="${orgnFg != 'HQ'}">
            <%-- 수정 --%>
            <span><a href="#" class="btn_blue pd20" ng-click="modify()"><s:message code="regist.modify" /></a></span>
          </c:if>
          <%-- 닫기 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verAddr/verInfoDtl.js?ver=20231026.01" charset="utf-8"></script>


