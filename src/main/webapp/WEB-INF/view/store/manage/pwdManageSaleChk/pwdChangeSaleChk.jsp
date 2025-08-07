<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="pwdChangeSaleChkPopupLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="pwdManageSaleChk.layer.modify" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" ng-controller="pwdChangeSaleChkCtrl">
            <input type="hidden" id="pwdModifyUserId" />
            <input type="hidden" id="pwdModifyEmpNo" />
            <div>
                <table class="tblType01">
                    <colgroup>
                        <col class="w40" />
                        <col class="w60" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="pwdManageSaleChk.layer.userId" /></th>
                        <td id="layerUserId">{{pwdChange.userId}}</td>
                    </tr>
                    <tr>
                        <th><s:message code="pwdManageSaleChk.layer.salePwdYn" /></th>
                        <td>
                            <span class="sb-radio">
                                <input type="radio" style="width:15px;height:15px;position:relative;top:0px;" id="salePwdYnY" name="salePwdYn" value="Y"/>
                                <label for="salePwdYnY">사용</label>
                            </span>
                            <span class="sb-radio">
                                <input type="radio" style="width:15px;height:15px;position:relative;top:0px;" id="salePwdYnN" name="salePwdYn" value="N" />
                                <label for="salePwdYnN">미사용</label>
                            </span>
                        </td>
                    </tr>
                    <%-- 비밀번호 --%>
                    <tr>
                        <th><s:message code="pwdManageSaleChk.layer.password" /></th>
                        <td>
                            <input id="layerPassword" type="password" ng-model="pwdChange.password" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                            <span id="passwordError" class="errorMsg" style="display: none"></span>
                        </td>
                    </tr>
                    <%-- 새비밀번호 --%>
                    <tr>
                        <th><s:message code="pwdManageSaleChk.layer.newPassword" /></th>
                        <td>
                            <input id="layerNewPassword" type="password" ng-model="pwdChange.newPassword" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                            <span id="newPasswordError" class="errorMsg" style="display: none"></span>
                        </td>
                    </tr>
                    <%-- 새비밀번호확인 --%>
                    <tr>
                        <th><s:message code="pwdManage.layer.confirmPassword" /></th>
                        <td>
                            <input id="layerConfirmPassword" type="password" ng-model="pwdChange.confirmPassword" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                            <span id="confirmPasswordError" class="errorMsg" style="display: none"></span>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <p class="mt20 s12">
                    ※ 비밀번호 규칙<br/>사용가능문자 : 숫자<br/>비밀번호길이 : 4자리
                    <br>
                    <br>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        ※ 패스워드초기화 <br/> 매출조회 확인 비밀번호 : 0000
                    </c:if>
                </p>
            </div>
            <div id="viewBtnArea" class="mt20 tc">
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 패스워드초기화 --%>
                    <button class="btn_skyblue" id="btnClearPwd" ng-click="clearSalePwd()"><s:message code="login.pw.clearPwd" /></button>
                    <%-- 변경하기 --%>
                    <button class="btn_skyblue" id="btnModify" ng-click="modifySalePwd()"><s:message code="pwdManage.layer.modifyBtn" /></button>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <%-- 변경하기 --%>
                    <button class="btn_blue" id="btnModifyStore" ng-click="modifySalePwd()"><s:message code="cmm.confirm" /></button>
                </c:if>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/pwdManageSaleChk/pwdChangeSaleChk.js?ver=20250805.01" charset="utf-8"></script>