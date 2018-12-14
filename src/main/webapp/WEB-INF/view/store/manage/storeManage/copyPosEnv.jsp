<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="copyPosEnvLayer" show-trigger="Click" hide-trigger="Click" style="width:400px;height:300px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="copyPosEnvCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.copy.posSetting" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
      <%-- A포스의 환경설정을 B포스로 복사 --%>
      <table class="tblType01">
        <colgroup>
          <col class="w45" />
          <col class="w55" />
        </colgroup>

        <tbody>
          <tr>
            <th>
              <div class="sb-select">
                <wj-combo-box
                      id="posNo"
                      ng-model="posNo"
                      control="posNoCombo"
                      items-source="_getComboData('posNo')"
                      display-member-path="posCdNm"
                      selected-value-path="posCd"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      selected-index-changed="setPos(s,e)">
                </wj-combo-box>
              </div>
            </th>
            <td class="w65"><s:message code="storeManage.copy.posSetting.comment1" /></td>
          </tr>
          <tr>
            <th>
              <div class="sb-select">
                <wj-combo-box
                      id="tPosNo"
                      ng-model="targetPosNo"
                      control="tPosNoCombo"
                      items-source="_getComboData('tPosNo')"
                      display-member-path="posCdNm"
                      selected-value-path="posCd"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      selected-index-changed="setTargetPos(s,e)">
                </wj-combo-box>
              </div>
            </th>
            <td><s:message code="storeManage.copy.posSetting.comment2" /></td>
          </tr>
        </tbody>
      </table>

      <%-- 복사 --%>
      <div class="btnSet">
        <span><a id="btnCopy" href="#" class="btn_blue" ng-click="copy()"><s:message code="cmm.copy" /></a></span>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/copyPosEnv.js?ver=2018110601" charset="utf-8"></script>
