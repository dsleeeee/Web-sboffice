<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMessageDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:230px;height:290px;" fade-in="false" fade-out="false">
    <div ng-controller="messageDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="messageDtl.info"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="subCon">
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

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/sendStatus/messageDtl.js?ver=20210806.04" charset="utf-8"></script>