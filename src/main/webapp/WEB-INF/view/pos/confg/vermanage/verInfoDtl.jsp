<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<wj-popup control="versionInfoDetailLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:600px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="verManage.verInfo" />
      <span id="versionDetailTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="verDetailCtrl">

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 버전정보 --%>
        <li><a id="storeInfo" href="#" class="on"><s:message code="verManage.verInfo" /></a></li>
        <%-- 적용매장 --%>
        <li><a id="storeEnv" href="#" ng-click="changeTab();"><s:message code="verManage.store.registed" /></a></li>
      </ul>

      <div>
        <div style="height:250px; overflow-y: auto;">
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
                <th><s:message code="verManage.verSerNo" /></th>
                <td>{{version.verSerNo}}</td>
                <%-- 버전적용명 --%>
                <th><s:message code="verManage.verSerNm" /></th>
                <td>{{version.verSerNm}}</td>
              </tr>
              <tr>
                <%-- 파일사이즈 --%>
                <th><s:message code="verManage.fileSize" /></th>
                <td>{{version.fileSize}}</td>
                <%-- 프로그램구분 --%>
                <th><s:message code="verManage.progFg" /></th>
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
                <th><s:message code="verManage.incldDtls" /></th>
                <td>{{version.incldDtls}}</td>
                  <%-- 사용여부 --%>
                <th><s:message code="verManage.useYn" /></th>
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
                <th style="height:65px;"><s:message code="verManage.fileDesc" /></th>
                <td colspan="3">{{version.fileDesc}}</td>
              </tr>
              <tr>
                  <%-- 버전체크정보(참고용) --%>
                <th><s:message code="verManage.orgnCds" /></th>
                <td>{{version.orgnCds}}</td>
                  <%-- 프로그램상세구분 --%>
                <th><s:message code="verManage.progDetailFg" /></th>
                <td>{{version.progDetailFg}}</td>
              </tr>
              <tr>
                  <%-- 시스템타입 --%>
                <th><s:message code="verManage.systemTypeFg" /></th>
                <td>{{version.systemTypeFg}}</td>
                <c:if test="${orgnFg == 'MASTER'}">
                    <%--총판/대리점노출여부--%>
                    <th><s:message code="verManage.agencyDispYn" /></th>
                    <td>
                        <wj-combo-box
                              ng-model="version.agencyDispYn"
                              ng-hide="true"
                              text="_agencyDispYn"
                              items-source="_getComboData('agencyDispYnCombo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false">
                        </wj-combo-box>
                        {{_agencyDispYn}}
                        <%--{{version.agencyDispYn}}--%>
                    </td>
                </c:if>
              </tr>
              </tbody>
            </table>
          </f:form>
        </div>
        <h3 class="h3_tbl"><s:message code="verManage.posInfo"/></h3>
        <div class="tblBr">
          <table class="tblType01">
            <colgroup>
              <col class="w100"/>
            </colgroup>
            <tbody>
            <tr>
              <th class="gr lh30">
                <%--              <input type="text" id="_info" name="info" style="height:50px" class="sb-input w100" ng-model="prodModifyInfo.info"/>--%>
                <textarea id="_info"  class="w100" cols="42" style="height:100px;resize: none;" ng-model="version.verSerPatchInfo" readonly></textarea>
              </th>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="btnSet2">
          <c:if test="${orgnFg == 'MASTER'}">
            <%-- 삭제 --%>
            <span><a href="#" class="btn_red pd20" ng-click="delete()"><s:message code="cmm.delete" /></a></span>
            <%-- 수정 --%>
            <span><a href="#" class="btn_blue pd20" ng-click="modify()"><s:message code="cmm.edit" /></a></span>
          </c:if>
          <%-- 닫기 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>
      </div>
    </div>
    <%-- body --%>

  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verManage/verInfoDtl.js?ver=20250617.01" charset="utf-8"></script>

<%-- 버전관리 삭제정보 팝업 --%>
<c:import url="/WEB-INF/view/pos/confg/vermanage/verDelInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>