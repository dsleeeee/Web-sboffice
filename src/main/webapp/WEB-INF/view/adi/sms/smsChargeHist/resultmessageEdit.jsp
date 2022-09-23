<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup control="wjResultmessageEditLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:150px;" fade-in="false" fade-out="false">
    <div ng-controller="resultmessageEditCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsChargeHist.resultmessage"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="mb10 oh sb-select dkbr">
                <c:if test="${orgnFg eq 'MASTER'}">
                    <button class="btn_skyblue ml5 fr" id="btnResultmessageSave" ng-click="resultmessageSave()"><s:message code="cmm.save" /></button>
                </c:if>
            </div>
            <input type="text" class="sb-input w100" id="resultmessage" ng-model="resultmessage" maxlength="100"/>
            <input type="hidden" id="orgnCd" ng-model="orgnCd"/>
            <input type="hidden" id="chargeDate" ng-model="chargeDate"/>
            <input type="hidden" id="chargeTime" ng-model="chargeTime"/>
        </div>
        <%-- //body --%>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsChargeHist/resultmessageEdit.js?ver=20211021.03" charset="utf-8"></script>
