<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSmsChargeDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:290px;" fade-in="false" fade-out="false">
    <div ng-controller="smsChargeDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsChargeDtl.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="mb10 oh sb-select dkbr">
                <%-- 결제취소 --%>
                <button class="btn_skyblue ml5 fr" id="btnSmsChargeCencel" ng-click="smsChargeCencel()"><s:message code="smsChargeDtl.smsChargeCencel" /></button>
            </div>
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w30"/>
                    <col class="w20"/>
                    <col class="w30"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 소속명 --%>
                    <th>
                        <s:message code="smsChargeDtl.orgnNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="orgnNmDtl" ng-model="orgnNmDtl" readonly />
                    </td>
                    <%-- 결제자 --%>
                    <th>
                        <s:message code="smsChargeDtl.chargeIdNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="chargeIdNmDtl" ng-model="chargeIdNmDtl" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 충전일자 --%>
                    <th>
                        <s:message code="smsChargeDtl.chargeDate" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="chargeDateDtl" ng-model="chargeDateDtl" readonly />
                    </td>
                    <%-- 충전시간 --%>
                    <th>
                        <s:message code="smsChargeDtl.chargeTime" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="chargeTimeDtl" ng-model="chargeTimeDtl" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 충전금액 --%>
                    <th>
                        <s:message code="smsChargeDtl.chargeAmt" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="chargeAmtDtl" ng-model="chargeAmtDtl" style="text-align: right;" readonly />
                    </td>
                    <%-- 충전수량 --%>
                    <th>
                        <s:message code="smsChargeDtl.smsChargeQty" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="smsChargeQtyDtl" ng-model="smsChargeQtyDtl" style="text-align: right;" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 결제수단 --%>
                    <th>
                        <s:message code="smsChargeDtl.pgresource" />
                    </th>
                    <td>
                        <wj-combo-box
                                id="pgresourceDtlCombo"
                                ng-model="pgresourceDtl"
                                items-source="_getComboData('pgresourceDtlCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="pgresourceDtlCombo"
                                ng-hide="true"
                                text="_pgresourceDtl">
                        </wj-combo-box>
                        <%--{{_pgresourceDtl}}--%>
                        <input type="text" class="sb-input w100" id="pgresourceDtl" ng-model="_pgresourceDtl" readonly />
                    </td>
                    <%-- 승인번호 --%>
                    <th>
                        <s:message code="smsChargeDtl.controlno" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="controlnoDtl" ng-model="controlnoDtl" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 성공여부 --%>
                    <th>
                        <s:message code="smsChargeDtl.successYn" />
                    </th>
                    <td>
                        <wj-combo-box
                                id="successYnDtlCombo"
                                ng-model="successYnDtl"
                                items-source="_getComboData('successYnDtlCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="successYnDtlCombo"
                                ng-hide="true"
                                text="_successYnDtl">
                        </wj-combo-box>
                        <%--{{_successYnDtl}}--%>
                        <input type="text" class="sb-input w100" id="successYnDtl" ng-model="_successYnDtl" readonly />
                    </td>
                    <%-- 결과메세지 --%>
                    <th>
                        <s:message code="smsChargeDtl.resultmessage" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="resultmessageDtl" ng-model="resultmessageDtl" readonly />
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsChargeHist/smsChargeDtl.js?ver=20210823.01" charset="utf-8"></script>