<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjRestSmsAmtLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:350px;height:130px;" fade-in="false" fade-out="false">
    <div ng-controller="restSmsAmtCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="restSmsAmt.info"/>
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
                    <%-- 현재잔여금액 --%>
                    <th>
                        <s:message code="restSmsAmt.info" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="restSmsAmtRestSmsAmt" ng-model="restSmsAmt" readonly />
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/restSmsAmt.js?ver=20221215.01" charset="utf-8"></script>