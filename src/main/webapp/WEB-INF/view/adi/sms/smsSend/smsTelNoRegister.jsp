<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSmsTelNoRegisterLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;height:240px;" fade-in="false" fade-out="false">
    <div ng-controller="smsTelNoRegisterCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsTelNoRegister.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="subCon">
            <div class="oh sb-select dkbr">
                <p class="tl s14 mt5 lh15">- 사전등록 발신번호에 인증 처리 ARS를 수신 받을 번호를 입력 후</p>
                <p class="tl s14 mt5 lh15">"인증요청" 버튼을 클릭하시면 인증을 위한 ARS 전화가 걸려옵니다.</p>
                <p class="tl s14 mt5 lh15">- ARS전화를 받으시면 인증번호를 청취 후 전화기에 입력해주십시오.</p>
                <p class="tl s14 mt5 lh15">- 사전등록 인증 처리 진행 중 창을 강제로 닫을 경우 정상적으로 </p>
                <p class="tl s14 mt5 lh15">인증처리 되지 않을 수 있습니다.</p>
            </div>

            <div class="mt20 oh sb-select dkbr">
                <%-- 사전등록 발신번호 --%>
                <s:message code="smsTelNoRegister.registerTelNo" />
                <input type="text" class="sb-input w40" id="srchTelNo" ng-model="telNo" />
                <%-- 인증요청 --%>
                <button class="btn_skyblue ml5 fr" ng-click="telNoRequest()">
                    <s:message code="smsTelNoRegister.telNoRequest" />
                </button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/smsTelNoRegister.js?ver=20210625.01" charset="utf-8"></script>