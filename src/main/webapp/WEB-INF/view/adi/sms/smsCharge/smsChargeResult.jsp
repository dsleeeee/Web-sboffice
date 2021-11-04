<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="sessionId" value="${param.sid}" />

<wj-popup control="wjSmsChargeResultLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:350px;height:390px;" fade-in="false" fade-out="false">
    <div ng-controller="smsChargeResultCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsChargeResult.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table>
                <colgroup>
                    <col class="w100"/>
                </colgroup>
                <tbody>
                <tr>
                    <td>
                        <label id="lblContentSmsChargeResult"></label>
                    </td>
                </tr>
                </tbody>
            </table>
            <table class="tblType01 mt30">
                <colgroup>
                    <col class="w30"/>
                    <col class="w70"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 결제수단 --%>
                    <th>
                        <s:message code="smsChargeDtl.pgresource" />
                    </th>
                    <td>
                        <wj-combo-box
                                id="pgresourceSmsChargeResultCombo"
                                ng-model="pgresourceSmsChargeResult"
                                items-source="_getComboData('pgresourceSmsChargeResultCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="pgresourceSmsChargeResultCombo"
                                ng-hide="true"
                                text="_pgresourceSmsChargeResult">
                        </wj-combo-box>
                        <%--{{_pgresourceDtl}}--%>
                        <input type="text" class="sb-input w100" id="pgresourceSmsChargeResult" ng-model="_pgresourceSmsChargeResult" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 결제금액 --%>
                    <th>
                        <s:message code="smsChargeResult.chargeTot" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="chargeTotSmsChargeResult" ng-model="chargeTotSmsChargeResult" style="text-align: right;" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 충전일자 --%>
                    <th>
                        <s:message code="smsChargeResult.chargeDate" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="chargeDateSmsChargeResult" ng-model="chargeDateSmsChargeResult" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 충전시간 --%>
                    <th>
                        <s:message code="smsChargeResult.chargeTime" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="chargeTimeSmsChargeResult" ng-model="chargeTimeSmsChargeResult" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 충전금액 --%>
                    <th>
                        <s:message code="smsChargeResult.chargeAmt" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="chargeAmtSmsChargeResult" ng-model="chargeAmtSmsChargeResult" style="text-align: right;" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 승인번호 --%>
                    <th>
                        <s:message code="smsChargeResult.controlno" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="controlnoSmsChargeResult" ng-model="controlnoSmsChargeResult" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 결과코드 --%>
                    <th>
                        <s:message code="smsChargeResult.resultcode" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="resultcodeSmsChargeResult" ng-model="resultcodeSmsChargeResult" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 결과메세지 --%>
                    <th>
                        <s:message code="smsChargeResult.resultmessage" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="resultmessageSmsChargeResult" ng-model="resultmessageSmsChargeResult" readonly />
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/smsChargeResult.js?ver=20211104.02" charset="utf-8"></script>