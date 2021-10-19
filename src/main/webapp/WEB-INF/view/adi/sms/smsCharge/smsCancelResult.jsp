<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSmsCancelResultLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:350px;height:200px;" fade-in="false" fade-out="false">
    <div ng-controller="smsCancelResultCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsCancelResult.info"/>
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
                        <label id="lblContentSmsCancelResult"></label>
                    </td>
                </tr>
                <tr style="height: 30px"></tr>
                <tr>
                    <td>
                        <label id="lblContentSmsCancelResult2"></label>
                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr>
                    <td>
                        <label id="lblContentSmsCancelResult3"></label>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsCharge/smsCancelResult.js?ver=20211019.01" charset="utf-8"></script>