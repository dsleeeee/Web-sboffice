<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 사원 상세 팝업 --%>
<wj-popup control="changePwdLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="changePwdCtrl">
  <form name="changePwdForm">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="emp.change.password"/><span>{{emp.empInfo}}</span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
      <%-- 상세 --%>
      <div style="height: 175px; overflow-y: auto;">
        <table class="tblType01">
          <colgroup>
            <col class="w30" />
            <col class="w70" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 현재비밀번호 --%>
            <th>
              <div class="impWrap"><s:message code="emp.currentPwd" /></div>
            </th>
            <td ><input type="password" id="_empCurrentPwd" name="currentPwd" class="sb-input w100"
                        ng-model="emp.currentPwd"
                        required
                        popover-enable="changePwdForm.currentPwd.$invalid"
                        popover-placement="bottom-left"
                        popover-trigger="'mouseenter'"
                        placeholder="<s:message code="emp.currentPwd" />"
                        uib-popover="<s:message code="emp.currentPwd" />은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
            <%-- 변경비밀번호 --%>
            <th>
              <div class="impWrap"><s:message code="emp.changePwd" /></div>
            </th>
            <td><input type="password" id="_empUserPwd" name="userPwd" class="sb-input w100"
                       ng-model="emp.userPwd"
                       required
                       popover-enable="changePwdForm.userPwd.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       placeholder="<s:message code="emp.changePwd" />"
                       uib-popover="<s:message code="emp.changePwd" />은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
            <%-- 변경비밀번호 확인 --%>
            <th>
              <div class="impWrap"><s:message code="emp.confirm.changePwd" /></div>
            </th>
            <td><input type="password" id="_empUserPwdCfm" name="userPwdCfm" class="sb-input w100"
                       ng-model="emp.userPwdCfm"
                       required
                       popover-enable="changePwdForm.userPwdCfm.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       placeholder="<s:message code="emp.confirm.changePwd" />"
                       uib-popover="<s:message code="emp.confirm.changePwd" />은(는) 필수 입력항목 입니다."/></td></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="wj-dialog-footer">
      <div class="btnSet">
        <%-- 수정 --%>
        <span><a href="#" class="btn_blue" ng-click="change()"><s:message code="emp.change" /></a></span>
        <%-- 닫기 --%>
        <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
  </form>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/changePassword.js?ver=20181129.01" charset="utf-8"></script>
