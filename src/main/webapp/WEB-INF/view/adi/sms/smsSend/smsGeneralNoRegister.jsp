<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjSmsGeneralNoRegisterLayer" control="wjSmsGeneralNoRegisterLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:550px;height:210px;" fade-in="false" fade-out="false">
    <div ng-controller="smsGeneralNoRegisterCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsGeneralNoRegister.info"/>
            <a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="wj-dialog-body">
            <div class="oh sb-select dkbr mb10">
                <p class="tl s14 mt5 lh15">통신사에서 발급한 "통신서비스 이용 증명원"을 제출해주세요.</p>
                <p class="tl s14 mt5 lh15">- 요청하신 서류는 근무일 1~5일 이내로 심사가 완료됩니다.</p>
            </div>
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w20"/>
                    <col class="w60"/>
                </colgroup>
                <tbody>
                <tr>
                    <th>
                        <%-- 팩스로 접수 --%>
                        <input type="radio" id="fax" name="faxFile" value="0" ng-click="radioFaxChange()" checked/>
                        <label><s:message code="smsGeneralNoRegister.fax"/></label>
                    </th>
                    <th>
                        <%-- 파일로 첨부 --%>
                        <input type="radio" id="file" name="faxFile" value="1" ng-click="radioFileChange()"/>
                        <label><s:message code="smsGeneralNoRegister.file"/></label>
                    </th>
                    <td>
                        <div id="divFax" style="display: block;">
                            FAX) 0502-898-5194 으로 서류를 보내주시고, "서류인증신청" 버튼을 클릭하여 신청해주세요.
                        </div>
                        <div id="divFile" style="display: none;">
                            <f:form id="telForm" name="telForm" method="post" enctype="multipart/form-data">
                                <input class="form-control" type="file" id="fileTel" name="fileTel" accept="image/x-png, .jpg"/>
                            </f:form>
                        </div>
                    </td>
                </tr>
            </table>
            <div class="mt10">
                <%-- 서류인증신청 --%>
                <button id="request" class="btn_skyblue ml5 fr" ng-click="smsGeneralNoSave()">
                    <s:message code="smsGeneralNoRegister.smsGeneralNoSave" />
                </button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/smsGeneralNoRegister.js?ver=20220201.01" charset="utf-8"></script>