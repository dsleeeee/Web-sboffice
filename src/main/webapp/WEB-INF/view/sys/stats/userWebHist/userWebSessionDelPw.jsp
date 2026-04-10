<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup id="userWebSessionDelPwLayer" control="userWebSessionDelPwLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px; height:250px;">

    <div ng-controller="userWebSessionDelPwCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="userWebHist.title.inputPw"/>
            <a href="#" class="wj-hide btn_close" ng-click="closeUserWebSessionDelPw()"></a>
        </div>

        <div style="padding:10px 30px 0 30px;">
            <table class="tblType01 mt20">
                <colgroup>
                    <col class="w30"/>
                    <col class="w70"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><s:message code="prod.pwd"/></th>
                    <td>
                        <input type="text" class="sb-input" style="width: 150px;" id="addDelPw" ng-model="addDelPw" />
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="btnSet tc">
                <button class="btn_blue" id="btnConfirm" ng-click="confirm()"><s:message code="cmm.confirm"/></button>
                <button class="btn_blue" id="btnCancel" ng-click="cancel()"><s:message code="cmm.cancel"/></button>
            </div>
        </div>
    </div>

</wj-popup>

<script>
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/stats/userWebHist/userWebSessionDelPw.js?ver=20260331.01" charset="utf-8"></script>