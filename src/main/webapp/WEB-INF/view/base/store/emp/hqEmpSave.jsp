<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 본사 사원 신규등록 & 수정 팝업 --%>
<wj-popup control="hqEmpRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:700px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="hqEmpRegistCtrl">
  <form name="empForm">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqEmp.hqEmpInfo"/><span>{{hqEmpRegistInfo.empInfo}}</span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
      <h3 class="h3_tbl brt"><s:message code="hqEmp.hqEmpInfo"/></h3>

      <%-- 상세 --%>
      <div id="dtlArea" style="height: 266px; overflow-y: auto;">

        <table class="tblType01">
          <colgroup>
            <col class="w20" />
            <col class="w30" />
            <col class="w20" />
            <col class="w30" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 사원번호 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.empNo" /></div>
            </th>
            <td>
              <input type="text" name="empNo" class="sb-input w100" ng-model="hqEmpRegistInfo.empNo" placeholder="사원번호는 자동생성 됩니다." disabled />
            </td>
            <%-- 사원명 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.empNm" /></div>
            </th>
            <td>
              <input type="text" id="_empNm" name="empNm" class="sb-input w100"
                     ng-model="hqEmpRegistInfo.empNm"
                     required
                     popover-enable="empForm.empNm.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="hqEmp.empNm" />은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
            <%-- 웹사용여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.webUseYn" /></div>
            </th>
            <td>
              <div class="sb-select">
                <wj-combo-box id="_webUseYn" name="webUseYn"
                              control="hqEmpWebUseYnCombo"
                              ng-model="hqEmpRegistInfo.webUseYn"
                              items-source="_getComboData('hqEmpWebUseYnComboData')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              required
                              popover-enable="empForm.webUseYn.$invalid"
                              popover-placement="bottom-left"
                              popover-trigger="'mouseenter'"
                              uib-popover="<s:message code="hqEmp.webUseYn" />은(는) 필수 입력항목 입니다.">
                </wj-combo-box>
              </div>
            </td>
            <th></th>
            <td></td>
          </tr>
          <tr ng-if="hqEmpRegistInfo.webUseYn == 'Y'">
            <%-- 웹사용자ID --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.userId" /></div>
            </th>
            <td colspan="3">
              <input type="text" id="_userId" name="userId" class="w100 sb-input" style="width:30%"
                     maxlength="20"
                     ng-model="hqEmpRegistInfo.userId"
                     required
                     ng-readonly="newEmpYn == false"
                     popover-enable="empForm.userId.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="hqEmp.userId" />은(는) 필수 입력항목 입니다."/>
              <input type="hidden" ng-model="hqEmpRegistInfo.originalWebUserId"/>
              <%-- 중복체크 --%>
              <span class="txtIn" ng-show="hqEmpRegistInfo.originalWebUserId == '' || hqEmpRegistInfo.originalWebUserId == undefined">
                <a href="#" class="btn_grayS" ng-click="checkDuplicate()"><s:message code="hqEmp.chk.duplicate" /></a>
              </span>
            </td>
          </tr>
          <tr ng-if="hqEmpRegistInfo.webUseYn == 'Y'">
            <%-- 웹 사용 비밀번호 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.pwd" /></div>
            </th>
            <td colspan="3">
              <input type="password" id="_userPwd" name="userPwd" class="sb-input w30" style="width: 30%"
                     ng-model="hqEmpRegistInfo.userPwd"
                     ng-show="newEmpYn == true"
                     ng-required="newEmpYn == true"
                     maxlength="12"
                     popover-enable="empForm.userPwd.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     placeholder="<s:message code="hqEmp.pwd" />"
                     uib-popover="<s:message code="hqEmp.pwd" />은(는) 필수 입력항목 입니다."/>
              <input type="password" id="_userPwdCfm" name="userPwdCfm" class="sb-input ml10 w30" style="width: 30%"
                     ng-model="hqEmpRegistInfo.userPwdCfm"
                     ng-show="newEmpYn == true"
                     ng-required="newEmpYn == true"
                     maxlength="12"
                     popover-enable="empForm.userPwd.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     placeholder="<s:message code="hqEmp.pwd.confirm" />"
                     uib-popover="<s:message code="hqEmp.pwd.confirm" />은(는) 필수 입력항목 입니다."/>
              <a href="#" class="btn_grayS" ng-show="newEmpYn == false" ng-click="changePassword()"><s:message code="hqEmp.change.pwd" /></a>
            </td>
          </tr>
          <tr>
            <%-- 휴대폰번호 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.mpNo" /></div>
            </th>
            <td>
              <input type="text" id="_mpNo" name="mpNo" class="sb-input w100"
                     ng-model="hqEmpRegistInfo.mpNo"
                     required
                     popover-enable="empForm.mpNo.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="hqEmp.mpNo" />은(는) 필수 입력항목 입니다."/>
            </td>
            <%-- SMS수신여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.smsRecvYn" /></div>
            </th>
            <td>
              <div class="sb-select">
              <wj-combo-box id="_smsRecvYn" name="smsRecvYn"
                            ng-model="hqEmpRegistInfo.smsRecvYn"
                            items-source="_getComboData('hqEmpSmsRecvYnComboData')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            required
                            popover-enable="empForm.smsRecvYn.$invalid"
                            popover-placement="bottom-left"
                            popover-trigger="'mouseenter'"
                            uib-popover="<s:message code="hqEmp.smsRecvYn" />은(는) 필수 입력항목 입니다.">
              </wj-combo-box>
            </div>
            </td>
          </tr>
          <tr>
            <%-- 재직여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.serviceFg" /></div>
            </th>
            <td>
              <div class="sb-select">
                <wj-combo-box id="_serviceFg" name="serviceFg"
                              ng-model="hqEmpRegistInfo.serviceFg"
                              items-source="_getComboData('hqEmpServiceFgComboData')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              required
                              popover-enable="empForm.serviceFg.$invalid"
                              popover-placement="bottom-left"
                              popover-trigger="'mouseenter'"
                              uib-popover="<s:message code="hqEmp.serviceFg" />은(는) 필수 입력항목 입니다.">
                </wj-combo-box>
              </div>
            </td>
            <%-- 사용여부 --%>
            <th>
              <div class="impWrap"><s:message code="hqEmp.useYn" /></div>
            </th>
            <td>
              <div class="sb-select">
                <wj-combo-box id="_useYnFg" name="useYnFg"
                              ng-model="hqEmpRegistInfo.useYn"
                              items-source="_getComboData('hqEmpUseYnFgComboData')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              required
                              popover-enable="empForm.useYnFg.$invalid"
                              popover-placement="bottom-left"
                              popover-trigger="'mouseenter'"
                              uib-popover="<s:message code="hqEmp.useYn" />은(는) 필수 입력항목 입니다.">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="wj-dialog-footer">
      <%-- 등록 regist() --%>
      <button class="btn btn_blue" ng-click="empForm.$valid && regist()" ng-show="newEmpYn == true"><s:message code="cmm.new.add"/></button>
      <%-- 저장 --%>
      <button class="btn btn_blue" ng-click="empForm.$valid && save()" ng-show="newEmpYn == false"><s:message code="cmm.save"/></button>
      <%-- 닫기 --%>
      <button class="btn btn_gray" ng-click="close()"><s:message code="cmm.close"/></button>
    </div>
  </form>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/hqEmpSave.js?ver=2018112101" charset="utf-8"></script>

