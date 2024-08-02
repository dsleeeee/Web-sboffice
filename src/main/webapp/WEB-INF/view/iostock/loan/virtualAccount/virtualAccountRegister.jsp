<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}"/>
<c:set var="storeNm" value="${sessionScope.sessionInfo.storeNm}"/>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>
<c:set var="userNm" value="${sessionScope.sessionInfo.userNm}"/>

<wj-popup control="wjVirtualAccountRegisterLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:400px;" fade-in="false" fade-out="false">
    <div ng-controller="virtualAccountRegisterCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="virtualAccountRegister.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
            <%-- [매장코드] 매장명 --%>
            <label id="lblStoreCdNm" style="display: none;"></label>
        </div>

        <div class="wj-dialog-body">
            <div class="w100 mb5" style="display: none;">
                <div class="oh">
                    <p class="tl s14 mt5 lh15 blue">* 기본정보</p>
                </div>
            </div>
            <table class="tblType01" style="display: none;">
                <colgroup>
                    <col class="w35"/>
                    <col class="w65"/>
                </colgroup>
                <tbody>
                <%--<tr>--%>
                    <%--&lt;%&ndash; 주문번호 &ndash;%&gt;--%>
                    <%--<th>--%>
                        <%--<div class="impWrap">--%>
                            <%--<s:message code="virtualAccountRegister.ordrIdxx"/>--%>
                            <%--<em class="imp">*</em>--%>
                        <%--</div>--%>
                    <%--</th>--%>
                    <%--<td>--%>
                        <%--<input type="text" class="sb-input w100" id="ordr_idxx" ng-model="ordr_idxx" />--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--&lt;%&ndash; 상품명 &ndash;%&gt;--%>
                    <%--<th><s:message code="virtualAccountRegister.goodName"/></th>--%>
                    <%--<td>--%>
                        <%--<input type="text" class="sb-input w100" id="good_name" ng-model="good_name" />--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <%-- 주문자명 --%>
                    <th><s:message code="virtualAccountRegister.buyrName"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="buyr_name" ng-model="buyr_name" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 휴대폰번호 --%>
                    <th><s:message code="virtualAccountRegister.buyrTel2"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="buyr_tel2" ng-model="buyr_tel2" />
                    </td>
                </tr>
                <tr>
                    <%-- 이메일 --%>
                    <th><s:message code="virtualAccountRegister.buyrMail"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="buyr_mail" ng-model="buyr_mail" />
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="w100 mt10 mb5">
                <div class="oh">
                    <p class="tl s14 mt5 lh15 blue">* 가상계좌정보</p>
                </div>
            </div>
            <table class="tblType01">
                <colgroup>
                    <col class="w35"/>
                    <col class="w65"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 가상계좌 발급금액 --%>
                    <th>
                        <div class="impWrap">
                            <s:message code="virtualAccountRegister.vaMny"/>
                            <em class="imp">*</em>
                        </div>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100 tr" id="va_mny" ng-model="va_mny" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 입금은행 --%>
                    <th>
                        <div class="impWrap">
                            <s:message code="virtualAccountRegister.vaBankcode"/>
                            <em class="imp">*</em>
                        </div>
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchVaBankcodeCombo"
                                    ng-model="va_bankcode"
                                    items-source="_getComboData('vaBankcodeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchVaBankcodeCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 입금자명 --%>
                    <th>
                        <div class="impWrap">
                            <s:message code="virtualAccountRegister.vaName"/>
                            <em class="imp">*</em>
                        </div>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="va_name" ng-model="va_name" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 입금마감 시각 --%>
                    <th>
                        <div class="impWrap">
                            <s:message code="virtualAccountRegister.vaDate"/>
                            <em class="imp">*</em>
                        </div>
                    </th>
                    <td>
                        <div style="display: none;">
                            <input type="text" class="sb-input w100" id="va_date" ng-model="va_date" readonly />
                        </div>
                        <input type="text" class="sb-input w100" id="va_date2" ng-model="va_date2" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 현금영수증 --%>
                    <th><s:message code="virtualAccountRegister.receipt"/></th>
                    <th>
                        <%-- 발급안함 --%>
                        <input type="radio" id="receiptNo" name="receipt" value="N" ng-click="radioReceiptChange()" checked />
                        <label><s:message code="virtualAccountRegister.receiptNo"/></label>
                        &nbsp;&nbsp;
                        <%-- 발급 --%>
                        <input type="radio" id="receiptYes" name="receipt" value="Y" ng-click="radioReceiptChange()"/>
                        <label><s:message code="virtualAccountRegister.receiptYes"/></label>
                    </th>
                </tr>
                <tr id="trReceipt" style="display: none;">
                    <%-- 발행용도 --%>
                    <th><s:message code="virtualAccountRegister.vaReceiptGubn"/></th>
                    <th>
                        <%-- 소득공제용 --%>
                        <input type="radio" id="vaReceiptGubn0" name="vaReceiptGubn" value="0" ng-click="radioVaReceiptGubnChange()" checked />
                        <label><s:message code="virtualAccountRegister.vaReceiptGubn0"/></label>
                        &nbsp;&nbsp;
                        <%-- 지출증빙용 --%>
                        <input type="radio" id="vaReceiptGubn1" name="vaReceiptGubn" value="1" ng-click="radioVaReceiptGubnChange()"/>
                        <label><s:message code="virtualAccountRegister.vaReceiptGubn1"/></label>
                    </th>
                </tr>
                <tr id="trReceipt2" style="display: none;">
                    <%-- 휴대폰번호/현금영수증카드 --%>
                    <th id="thVaReceiptGubn0"><s:message code="virtualAccountRegister.vaTaxno0"/></th>
                    <%-- 사업자번호 --%>
                    <th id="thVaReceiptGubn1" style="display: none;"><s:message code="virtualAccountRegister.vaTaxno1"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="va_taxno" ng-model="va_taxno" />
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="tc mt20">
                <%-- 가상계좌 발급 --%>
                <button class="btn_blue" id="btnVirtualAccountRegisterSave" ng-click="virtualAccountInfoChk()"><s:message code="virtualAccountRegister.virtualAccountSave" /></button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";
    var storeNm = "${storeNm}";
    var userId = "${userId}";
    var userNm = "${userNm}";

    <%-- 가상계좌 API 은행코드 --%>
    var vaBankcodeComboData = ${ccu.getCommCodeSelect("233")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/virtualAccount/virtualAccountRegister.js?ver=20240801.01" charset="utf-8"></script>