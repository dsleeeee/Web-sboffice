<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMobileMessageDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:190px;height:250px;" fade-in="false" fade-out="false">
    <div ng-controller="mobileMessageDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="mobile.messageDtl.info"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="subCon4">
            <table>
                <colgroup>
                    <col class="w100"/>
                </colgroup>
                <tbody>
                <tr>
                    <td>
                        <input type="text" class="sb-input-msg w100" id="srchMessageDtlSubject" ng-model="subject" readonly/>
                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr>
                    <td>
                        <textarea id="messageContentDtl" name="messageContentDtl" ng-model="messageContentDtl" style="width:100%; height:160px; overflow-x:hidden; background-color: #EAF7FF" readonly></textarea>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/mobile/adi/sms/sendStatus/mobileMessageDtl.js?ver=20220104.01" charset="utf-8"></script>