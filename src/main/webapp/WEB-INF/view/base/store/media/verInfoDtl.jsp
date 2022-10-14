<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<wj-popup control="versionInfoDetailLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:340px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="media.verInfo" />
      <span id="versionDetailTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="verDetailCtrl">
      <c:if test="${orgnFg == 'HQ'}">
        <%-- 탭 --%>
        <ul class="subTab">
          <%-- 버전정보 --%>
          <li><a id="storeInfo" href="#" class="on"><s:message code="media.verInfo" /></a></li>
          <%-- 적용매장 --%>
          <li><a id="storeEnv" href="#" ng-click="changeTab();"><s:message code="media.store.registed" /></a></li>
        </ul>
      </c:if>
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
                <th><s:message code="media.verSerNo" /></th>
                <td>{{version.verSerNo}}</td>
                <%-- 버전적용명 --%>
                <th><s:message code="media.verSerNm" /></th>
                <td>{{version.verSerNm}}</td>
              </tr>
              <tr>
                  <%-- 사용기한 --%>
                <th><s:message code="media.useDate" /></th>
                <td>{{version.useDate}}</td>
                <%-- 파일사이즈 --%>
                <th><s:message code="media.fileSize" /></th>
                <td>{{version.fileSize}}</td>
              </tr>
              <tr>
                  <%-- 프로그램구분 --%>
                <th><s:message code="media.fileType" /></th>
                <td>
                  <wj-combo-box
                          ng-model="version.fileType"
                          ng-hide="true"
                          text="_fileType"
                          items-source="_getComboData('fileTypeCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false">
                  </wj-combo-box>
                  {{_fileType}}
                </td>
                  <%-- 사용여부 --%>
                <th><s:message code="media.useYn" /></th>
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
                <%-- 이미지출력시간 --%>
                <th><s:message code="media.dispTime" /></th>
                <td>{{version.dispTime}}</td>
                <th></th>
                <td></td>
              </tr>
              </tbody>
            </table>
          </f:form>
        </div>
        <div class="btnSet2">
          <%-- 수정 --%>
          <span id="btnMod"><a href="#" class="btn_blue pd20" ng-click="modify()"><s:message code="regist.modify" /></a></span>

          <%-- 닫기 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>
      </div>
    </div>
  </div>
</wj-popup>

<script>
  var fileType    = ${ccu.getCommCode("303")};
  var useYn       = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/media/verInfoDtl.js?ver=2019011001" charset="utf-8"></script>


