<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />

<wj-popup control="versionRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:350px;">
  <div class="wj-dialog wj-dialog-columns title">
    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="media.verInfo" />
      <span id="versionDetailTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="verRegistCtrl">
      <div>
        <div style="height:200px; overflow-y: auto;">
          <f:form id="regForm" name="regForm" >
            <h3 class="h3_tbl"><s:message code="media.verInfo" /></h3>
            <table class="searchTbl">
              <colgroup>
                <col class="w13" />
                <col class="w37" />
                <col class="w13" />
                <col class="w37" />
              </colgroup>
              <tbody>
              <tr>
                <%-- 버전일련번호 --%>
                <th><s:message code="media.verSerNo" /></th>
                <td><input type="text" class="sb-input w100" id="verSerNo" ng-model="version.verSerNo" readonly></td>
                <%-- 버전적용명 --%>
                <th><s:message code="media.verSerNm" /></th>
                <td><input type="text" class="sb-input w100" id="verSerNm" ng-model="version.verSerNm"></td>
              </tr>

              <tr>
              <%-- 파일업로드 --%>
                <th><s:message code="media.file" /></th>
                <td colspan="3" id="fileIn">
                  <input type="file" class="form-control" id="file"
                         name="file"
                         ng-model="version.uploadFile"
                         onchange="angular.element(this).scope().uploadChange()"/>
                </td>
                <th id="fileOrgH" style="display:none;"><s:message code="media.fileNm"/></th>
                <td id="fileOrgD" style="display:none;">
                  <input type="text" class="sb-input w100" id="fileOrgNm" ng-model="version.fileOrgNm" readonly/>
                </td>
              </tr>
              <tr> <%-- 사용기한 --%>
                <th><s:message code="media.useDate" /></th>
                <td>
                  <div class="sb-select">
                    <span class="txtIn"><input id="startDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endDate" class="w110px"></span>
                  </div>
                </td>
                  <%-- 파일사이즈 --%>
                <th><s:message code="media.fileSize" /></th>
                <td>{{version.fileSize}}</td>
              </tr>

              <tr> <%-- 파일타입 --%>
                <th><s:message code="media.fileType" /></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box
                            id="fileTypeCombo"
                            ng-model="version.fileType"
                            control="versionFileTypeCombo"
                            items-source="_getComboData('fileTypeCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                    </wj-combo-box>
                  </div>
                </td>
                  <%-- 사용여부 --%>
                <th><s:message code="media.useYn" /></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box
                            id="useYn"
                            ng-model="version.useYn"
                            control="versionUseYnCombo"
                            items-source="_getComboData('useYnCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                    </wj-combo-box>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </f:form>
        </div>
        <div class="btnSet2">
          <%-- 신규 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="mediaSave()" ng-show="!isEdit"><s:message code="cmm.save" /></a></span>
          <%-- 수정 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="mediaSave()" ng-show="isEdit"><s:message code="cmm.save" /></a></span>
          <%-- 닫기 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript">
  var fileTypeData = ${ccu.getCommCodeExcpAll("303")};
  var useYnData    = ${ccu.getCommCodeExcpAll("067")};
  var orgnCd       = "${orgnCd}";
  var orgnFg       = "${orgnFg}";
  var hqOfficeCd   = "${hqOfficeCd}";
  var storeCd      = "${storeCd}";
  var userId       = "${userId}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/store/media/verRegist.js?ver=20210617.01" charset="utf-8"></script>


