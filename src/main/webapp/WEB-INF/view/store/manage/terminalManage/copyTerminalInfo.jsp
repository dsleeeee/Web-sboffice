<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<wj-popup control="copyTerminalInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:400px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="copyTerminalInfoCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label><s:message code="terminalManage.copy.terminal" /></label>
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="pdb40" style="padding-top:20px;">
                <label style="display:none;" id="copyTerminalInfo_storeCd"></label>
                <table class="tblType01">
                    <colgroup>
                        <col class="w40"/>
                        <col class="w60"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <!-- 복사 -->
                        <th><s:message code="terminalManage.copyPosNo" /></th>
                        <td style="padding-left: 15px;">
                            <div class="sb-select" style="float:left;">
                                <wj-combo-box
                                        id="copyPosNo"
                                        ng-model="copyPosNo"
                                        items-source="_getComboData('copyPosNo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="copyPosNoCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <!-- 붙여넣기 -->
                        <th><s:message code="terminalManage.pastePosNo" /></th>
                        <td style="padding-left: 15px;">
                            <div class="sb-select" style="float:left;">
                                <wj-combo-box
                                        id="pastePosNo"
                                        ng-model="pastePosNo"
                                        items-source="_getComboData('pastePosNo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="pastePosNoCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 버튼영역 --%>
            <div class="tr">
                <button class="btn_blue ml10 fr" id="btnCopy" ng-click="btnCopy()"><s:message code="cmm.copy"/></button>
            </div>
         </div>
    </div>
</wj-popup>

<script>
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/terminalManage/copyTerminalInfo.js?ver=20230203.01" charset="utf-8"></script>