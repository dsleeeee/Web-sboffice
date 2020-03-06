<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="pwdUnlockPopupLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="pwdManage.layer.unlockLogin" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" ng-controller="pwdUnlockCtrl">
            <input type="hidden" id="pwdModifyUserId" />
            <input type="hidden" id="pwdModifyEmpNo" />
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
                        <td id="layerUserId">{{pwdUnlock.userId}}</td>
                    </tr>
                    <%-- 사용자명 --%>
                    <tr>
                        <th><s:message code="pwdManage.layer.userNm" /></th>
                        <td id="layerUserNm">{{pwdUnlock.userNm}}</td>
                    </tr>
                    </tbody>
                </table>
                <p class="mt20 s12">
                    <s:message code="pwdManage.layer.unlockMsg" />
                </p>
            </div>
            <div id="viewBtnArea" class="mt20 tc">
                <%-- 변경하기 --%>
                <button class="btn_skyblue" id="btnUnlockPwd" ng-click="unlockPwd()"><s:message code="pwdManage.layer.unlock" /></button>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/pwdManage/pwdUnlock.js?ver=20200305.08" charset="utf-8"></script>
