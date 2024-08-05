<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="versionRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:550px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="verRegistCtrl" style="height:550px;">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="verManage.verInfo" />
      <span id="versionDetailTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close" ng-click="closePop()"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
      <div style="height:500px;">
        <div style="overflow-y: auto;">
          <f:form id="regForm" name="regForm" >
            <h3 class="h3_tbl"><s:message code="verManage.verInfo" /></h3>
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
                <td><input type="text" class="sb-input w100" id="verSerNo" ng-model="version.verSerNo" maxlength="20" ></td>
                <%-- 버전적용명 --%>
                <th><s:message code="verManage.verSerNm" /></th>
                <td><input type="text" class="sb-input w100" id="verSerNm" ng-model="version.verSerNm" maxlength="10" ></td>
              </tr>
              <tr>
                <%-- 파일업로드 --%>
                <th><s:message code="verManage.fileSize" /></th>
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
                <th><s:message code="verManage.fileSize" /></th>
                <td>{{version.fileSize}}</td>
                <%-- 프로그램구분 --%>
                <th><s:message code="verManage.progFg" /></th>
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
                <th><s:message code="verManage.incldDtls" /></th>
                <td>
                  <input type="checkbox" id="pgm" name="incldDtls" ng-model="version.pgm" ><label for="pgm"><s:message code='verManage.pgm' /></label>
                  <input type="checkbox" id="db"  name="incldDtls" ng-model="version.db"  ><label for="db"><s:message code='verManage.db' /></label>
                  <input type="checkbox" id="img" name="incldDtls" ng-model="version.img" ><label for="img"><s:message code='verManage.img' /></label>
                </td>
                  <%-- 사용여부 --%>
                <th><s:message code="verManage.useYn" /></th>
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
                <th><s:message code="verManage.fileDesc" /></th>
                <td colspan="3">
                  <input type="text" class="sb-input w100" id="fileDesc" ng-model="version.fileDesc" maxlength="500"/>
                </td>
              </tr>
              <tr>
                  <%-- 버전체크정보(참고용) --%>
                <th><s:message code="verManage.orgnCds" /></th>
                <td>
                  <jsp:include page="/WEB-INF/view/iostock/cmm/selectHqM.jsp" flush="true">
                    <jsp:param name="targetId" value="orgnCds"/>
                  </jsp:include>
                <button type="button" class="btn_skyblue fl mr5" id="btnDelOrgnCds" style="margin-left: 5px;" ng-click="delOrgnCds()"><s:message code="cmm.del"/></button>
                </td>
                  <%-- 프로그램상세구분 --%>
                  <th><s:message code="verManage.progDetailFg" /></th>
                  <td>
                    <div class="sb-select">
                      <wj-combo-box
                              id="progDetailFg"
                              ng-model="version.progDetailFg"
                              control="versionProgDetailFgCombo"
                              items-source="_getComboData('progDetailFgCombo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false">
                      </wj-combo-box>
                    </div>
                  </td>
              </tr>
              <tr>
                  <%-- 시스템타입 --%>
                <th><s:message code="verManage.systemTypeFg" /></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box
                            id="systemTypeFg"
                            ng-model="version.systemTypeFg"
                            control="versionSystemTypeFgCombo"
                            items-source="_getComboData('systemTypeFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false">
                    </wj-combo-box>
                  </div>
                </td>
                  <%--총판/대리점노출여부--%>
                <th><s:message code="verManage.agencyDispYn" /></th>
                  <td>
                    <div class="sb-select">
                      <wj-combo-box
                              id="agencyDispYn"
                              ng-model="version.agencyDispYn"
                              control="versionAgencyDispYnCombo"
                              items-source="_getComboData('agencyDispYnCombo')"
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
                <textarea id="_info"  class="w100" cols="42" style="height:100px;resize: none;" ng-model="version.verSerPatchInfo"></textarea>
              </th>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="btnSet2">
          <%-- 신규 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="chkForm()" id="btnReg"><s:message code="verManage.regist.new" /></a></span>
          <%-- 수정 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="chkForm()" id="btnMod"><s:message code="regist.modify" /></a></span>
          <%-- 닫기 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="closePop()"><s:message code="cmm.close" /></a></span>
        </div>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verManage/verRegist.js?ver=20240805.01" charset="utf-8"></script>


