<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMsgOneAmtGuideLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:350px;height:220px;" fade-in="false" fade-out="false">
    <div ng-controller="msgOneAmtGuideCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="msgOneAmtGuide.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" id="divMsgOneAmtGuideSms" style="display: none;">
            <div class="w100 mb10">
                <div class="oh sb-select dkbr">
                    <p class="tl s14 mt5 lh15">※ 건당 금액입니다.</p>
                </div>
            </div>
            <table class="tblType01">
                <colgroup>
                    <col class="w50"/>
                    <col class="w50"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- SMS --%>
                    <th>
                        <s:message code="msgOneAmtGuide.sms" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="msgOneAmtGuideSmsOneAmt" ng-model="smsOneAmt" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- LMS --%>
                    <th>
                        <s:message code="msgOneAmtGuide.lms" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="msgOneAmtGuideLmsOneAmt" ng-model="lmsOneAmt" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- MMS --%>
                    <th>
                        <s:message code="msgOneAmtGuide.mms" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="msgOneAmtGuideMmsOneAmt" ng-model="mmsOneAmt" readonly />
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="wj-dialog-body" id="divMsgOneAmtGuideAlk" style="display: none;">
            <div class="w100 mb10">
                <div class="oh sb-select dkbr">
                    <p class="tl s14 mt5 lh15">※ 건당 금액입니다.</p>
                </div>
            </div>
            <table class="tblType01">
                <colgroup>
                    <col class="w50"/>
                    <col class="w50"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 알림톡 --%>
                    <th>
                        <s:message code="msgOneAmtGuide.alk" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="msgOneAmtGuideAlkOneAmt" ng-model="alkOneAmt" readonly />
                    </td>
                </tr>
                <%--<tr>--%>
                    <%--&lt;%&ndash; 알림톡SMS &ndash;%&gt;--%>
                    <%--<th>--%>
                        <%--<s:message code="msgOneAmtGuide.alkSms" />--%>
                    <%--</th>--%>
                    <%--<td>--%>
                        <%--<input type="text" class="sb-input w100" id="msgOneAmtGuideAlkSmsOneAmt" ng-model="alkSmsOneAmt" readonly />--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <%-- 알림톡LMS --%>
                    <th>
                        <s:message code="msgOneAmtGuide.alkLms" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="msgOneAmtGuideAlkLmsOneAmt" ng-model="alkLmsOneAmt" readonly />
                    </td>
                </tr>
                <%--<tr>--%>
                    <%--&lt;%&ndash; 알림톡MMS &ndash;%&gt;--%>
                    <%--<th>--%>
                        <%--<s:message code="msgOneAmtGuide.alkMms" />--%>
                    <%--</th>--%>
                    <%--<td>--%>
                        <%--<input type="text" class="sb-input w100" id="msgOneAmtGuideAlkMmsOneAmt" ng-model="alkMmsOneAmt" readonly />--%>
                    <%--</td>--%>
                <%--</tr>--%>
                </tbody>
            </table>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/msgOneAmtGuide.js?ver=20220503.01" charset="utf-8"></script>