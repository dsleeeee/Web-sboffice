<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="versionRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:350px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="verAddr.verInfo" />
      <span id="versionDetailTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="verRegistCtrl">
      <div>
        <div style="height:200px; overflow-y: auto;">
          <f:form id="regForm" name="regForm" >
            <h3 class="h3_tbl"><s:message code="verAddr.verInfo" /></h3>
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
                <td><input type="text" class="sb-input w100" id="verSerNo" ng-model="version.verSerNo" maxlength="20" ></td>
                <%-- 버전적용명 --%>
                <th><s:message code="verAddr.verSerNm" /></th>
                <td><input type="text" class="sb-input w100" id="verSerNm" ng-model="version.verSerNm" maxlength="10" ></td>
              </tr>
              <tr>
                <%-- 파일업로드 --%>
                <th><s:message code="verAddr.fileSize" /></th>
                <td colspan="3">
                  <input type="file" class="form-control" id="file"
                         name="file"
                         ng-model="version.uploadFile"
                         onchange="angular.element(this).scope().uploadChange()"
                         accept=".zip"/>
                </td>
              </tr>
              <tr>
                <%-- 파일사이즈 --%>
                <th><s:message code="verAddr.fileSize" /></th>
                <td>{{version.fileSize}}</td>
                <%-- 프로그램구분 --%>
                <th><s:message code="verAddr.progFg" /></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box
                            id="progFg"
                            ng-model="version.progFg"
                            control="versionProgFgCombo"
                            items-source="_getComboData('progFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                    </wj-combo-box>
                  </div>
                </td>
              </tr>
              <tr>
                <%-- 포함내역 --%>
                <th><s:message code="verAddr.incldDtls" /></th>
                <td>
                  <input type="checkbox" id="pgm" name="incldDtls" ng-model="version.pgm" ><label for="pgm"><s:message code='verAddr.pgm' /></label>
                  <input type="checkbox" id="db"  name="incldDtls" ng-model="version.db"  ><label for="db"><s:message code='verAddr.db' /></label>
                  <input type="checkbox" id="img" name="incldDtls" ng-model="version.img" ><label for="img"><s:message code='verAddr.img' /></label>
                </td>
                  <%-- 사용여부 --%>
                <th><s:message code="verAddr.useYn" /></th>
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
              <tr>
                <%-- 비고 --%>
                <th><s:message code="verAddr.fileDesc" /></th>
                <td colspan="3">
                  <input type="text" class="sb-input w100" id="fileDesc" ng-model="version.fileDesc" maxlength="500"/>
                </td>
              </tr>
              </tbody>
            </table>
          </f:form>
        </div>
        <div class="btnSet2">
          <%-- 신규 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="regist()" ng-show="!isEdit"><s:message code="verAddr.regist.new" /></a></span>
          <%-- 수정 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="regist()" ng-show="isEdit"><s:message code="regist.modify" /></a></span>
          <%-- 닫기 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verAddr/verRegist.js?ver=20231026.01" charset="utf-8"></script>


