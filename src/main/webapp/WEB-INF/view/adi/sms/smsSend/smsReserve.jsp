<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSmsReserveLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:460px;height:120px;" fade-in="false" fade-out="false">
    <div ng-controller="smsReserveCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsReserve.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="subCon">
            <table>
                <colgroup>
                    <col class="w20"/>
                    <col class="w80"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 예약시간 --%>
                    <th>
                        <s:message code="smsReserve.date"/>
                    </th>
                    <td>
                        <div class="sb-select">
                            <span class="txtIn"><input id="startDateSmsReserve" name="startDate" class="w110px" /></span>
                            <span class="txtIn w70px">
                                <wj-combo-box
                                    id="srchStartTimeSmsReserveCombo"
                                    ng-model="startTimeSmsReserveCombo"
                                    items-source="_getComboData('startTimeSmsReserveCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </span>
                            <span class="txtIn w70px">
                                <wj-combo-box
                                        id="srchStartMinuteSmsReserveCombo"
                                        ng-model="startMinuteSmsReserveCombo"
                                        items-source="_getComboData('startMinuteSmsReserveCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </span>
                            <%-- 예약전송 --%>
                            <button class="btn_skyblue ml5 fr" ng-click="smsReserveSave()">
                                <s:message code="smsReserve.info" />
                            </button>
                            <%-- 예약여부 --%>
                            <label id="lblSmsReserveReserveYn" style="display: none;"></label>
                            <%-- 팝업 사용한화면 구분 --%>
                            <label id="lblSmsReserveGubun" style="display: none;"></label>
                            <%-- 메세지타입 --%>
                            <label id="lblSmsReserveMsgType" style="display: none;"></label>
                            <%-- 메세지별 건당금액 --%>
                            <label id="lblSmsReserveMsgOneAmt" style="display: none;"></label>
                            <%-- 전송건수 --%>
                            <label id="lblSmsReserveSmsSendListCnt" style="display: none;"></label>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/smsReserve.js?ver=20211230.01" charset="utf-8"></script>