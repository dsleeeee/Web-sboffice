<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 매장사원 비밀번호 변경 레이어 --%>

<div id="pwdModifyTent" class="fullDimmed" style="display: none;"></div>
<div id="pwdModifyLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w350">
      <%-- 비밀번호 변경 --%>
      <p class="tit"><s:message code="pwdManage.layer.modify" /></p>
      <a href="#" class="btn_close pwdModifyClose"></a>
      <input id="pwdModifyUserId" style="display: none;" />
      <input id="pwdModifyEmpNo" style="display: none;" />
      <div class="con">
        <div>
          <table class="tblType01">
            <colgroup>
              <col class="w40" />
              <col class="w60" />
            </colgroup>
            <tbody>
              <%-- 사용자ID --%>
              <tr>
                <th><s:message code="pwdManage.layer.userId" /></th>
                <td id="layerUserId"></td>
              </tr>
              <%-- 사용자명 --%>
              <tr>
                <th><s:message code="pwdManage.layer.userNm" /></th>
                <td id="layerUserNm"></td>
              </tr>
              <%-- 새비밀번호 --%>
              <tr>
                <th><s:message code="pwdManage.layer.newPassword" /></th>
                <td>
                  <div class="msgWrap">
                    <input id="newPwdLayer" type="password" maxlength="20"/>
                    <span id="newPwdLayerErrorMsg" class="errorMsg" style="display:none;"></span>
                  </div>
                </td>
              </tr>
              <%-- 새비밀번호확인 --%>
              <tr>
                <th><s:message code="pwdManage.layer.confirmPassword" /></th>
                <td>
                  <div class="msgWrap">
                    <input id="newPwdConfirmLayer" type="password" maxlength="20" />
                    <span id="newPwdConfirmLayerErrorMsg" class="errorMsg" style="display:none;"></span>
                  </div>
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
      </div>
      <div id="viewBtnArea" class="mt30 tc">
        <%-- 변경하기 --%>
        <button class="btn_skyblue" id="btnPwdModify"><s:message code="pwdManage.layer.modifyBtn" /></button>
      </div>
    </div>
  </div>
</div>
