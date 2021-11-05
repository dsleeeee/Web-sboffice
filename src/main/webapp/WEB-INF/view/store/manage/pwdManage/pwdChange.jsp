<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="pwdChangePopupLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="pwdManage.layer.modify" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="pwdChangeCtrl">
      <input type="hidden" id="pwdModifyUserId" />
      <input type="hidden" id="pwdModifyEmpNo" />
      <div>
        <table class="tblType01">
          <colgroup>
            <col class="w40" />
            <col class="w60" />
          </colgroup>
          <tbody>
          <%-- 비밀번호 구분 --%>
          <tr>
            <th><s:message code="pwdManage.layer.pwdChgFg" /></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="layerPwdChgFg"
                        ng-model="pwdChange.pwdChgFg"
                        control="layerPwdChgFgCombo"
                        items-source="_getComboData('pwdChgFgDataMap')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        selected-index-changed="changePwdChgFg(s,e)">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <%-- 사용자ID --%>
          <tr>
            <th><s:message code="pwdManage.layer.userId" /></th>
            <td id="layerUserId">{{pwdChange.userId}}</td>
          </tr>
          <%-- 사용자명 --%>
          <tr>
            <th><s:message code="pwdManage.layer.userNm" /></th>
            <td id="layerUserNm">{{pwdChange.userNm}}</td>
          </tr>
          <%-- 새비밀번호 --%>
          <tr>
            <th><s:message code="pwdManage.layer.newPassword" /></th>
            <td>
              <input id="layerNewPassword" type="password" ng-model="pwdChange.newPassword" maxlength="16" onkeyup="this.value=this.value.replace(/[^A-Za-z0-9]/g,'');"/>
              <span id="newPasswordError" class="errorMsg" style="display: none"></span>
            </td>
          </tr>
          <%-- 새비밀번호확인 --%>
          <tr>
            <th><s:message code="pwdManage.layer.confirmPassword" /></th>
            <td>
              <input id="layerConfirmPassword" type="password" ng-model="pwdChange.confirmPassword" maxlength="16" onkeyup="this.value=this.value.replace(/[^A-Za-z0-9]/g,'');"/>
              <span id="confirmPasswordError" class="errorMsg" style="display: none"></span>
            </td>
          </tr>
          </tbody>
        </table>
        <p class="mt20 s12">
          <s:message code="login.layer.pwchg.policy" arguments="6,20" />
          <br>
          <br>
          <s:message code="pwdManage.layer.msg" />
        </p>
      </div>
      <div id="viewBtnArea" class="mt20 tc">
        <%-- 변경하기 --%>
        <button class="btn_skyblue" id="btnModify" ng-click="modifyPwd()"><s:message code="pwdManage.layer.modifyBtn" /></button>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/pwdManage/pwdChange.js?ver=20181211.03" charset="utf-8"></script>
