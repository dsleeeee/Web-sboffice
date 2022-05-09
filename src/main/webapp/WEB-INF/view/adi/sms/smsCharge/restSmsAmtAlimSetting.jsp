<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjRestSmsAmtAlimSettingLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:350px;height:170px;" fade-in="false" fade-out="false">
    <div ng-controller="restSmsAmtAlimSettingCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="restSmsAmtAlimSetting.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w50"/>
                    <col class="w50"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 잔여금액 설정값 --%>
                    <th>
                        <s:message code="restSmsAmtAlimSetting.restSmsAmt"/>
                    </th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="srchRmSmsAmtCombo"
                                    ng-model="rmSmsAmt"
                                    items-source="_getComboData('rmSmsAmtCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchRmSmsAmtCombo"
                                    selected-index="2">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/restSmsAmtAlimSetting.js?ver=20220509.01" charset="utf-8"></script>