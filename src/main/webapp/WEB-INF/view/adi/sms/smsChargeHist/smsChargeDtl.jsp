<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

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
                <c:if test="${orgnFg eq 'MASTER'}">
                    <div id="divCancel" style="display: none;">
                        <%-- 결제취소 --%>
                        <button class="btn_skyblue ml5 fr" id="btnSmsChargeCancel" onclick="jsf__cancel(document.cancel_info);"><s:message code="smsChargeDtl.smsChargeCencel" /></button>
                    </div>
                </c:if>
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
                    <th></th>
                    <td></td>
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

<form id="cancel_info" name="cancel_info" method="post" action="/adi/sms/smsCharge/smsCharge/cancel.sb" target="cancelFrm">
    <iframe name="cancelFrm" style="display:none;"></iframe>

    <input type="hidden" name="req_tx"      value="mod"  /> <%--프로세스 요청 종류 구분 변수--%>
    <input type="hidden" name="mod_type"    value="STSC" /> <%--프로세스 요청의 구분 변수--%>
    <input type="hidden" name="tno"         value="" /> <%--결제 건에 대한 NHN KCP 거래 고유번호--%>
    <input type="hidden" name="mod_desc"    value="" /> <%--프로세스 취소 사유에 대한 변수 값--%>

    <input type="hidden" name="orgnCd" value="" /> <!-- 소속코드 -->
    <input type="hidden" name="userId" value="" /> <!-- 사용자ID -->
</form>

<script type="text/javascript">
    function jsf__cancel( form )
    {
        // alert(form);
        // alert(form.req_tx.value);
        form.tno.value = $("#controlnoDtl").val(); // 승인번호
        form.orgnCd.value = "${orgnCd}"; // 소속코드
        form.userId.value = "${userId}"; // 사용자ID

        var scope = agrid.getScope('smsChargeDtlCtrl');
        // 로딩바 show
        scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

        document.cancel_info.submit();
    }
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsChargeHist/smsChargeDtl.js?ver=20211021.01" charset="utf-8"></script>

<%-- SMS결제취소 결과 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsCharge/smsCancelResult.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>