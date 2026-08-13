<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<style>
    .marketingSmsSendVfcDialog {background:#fff;}
    .marketingSmsSendVfcModal {min-height:380px; padding:32px 30px 16px; box-sizing:border-box; background:#fff; color:#333; font-family:"Malgun Gothic","Apple SD Gothic Neo",sans-serif;}
    .marketingSmsSendVfcIcon {width:48px; height:48px; margin:0 auto 16px; display:flex; align-items:center; justify-content:center; border-radius:50%; background:#eaf1fb; color:#3b82d9; font-size:22px;}
    .marketingSmsSendVfcTitle {margin:0; text-align:center; font-size:18px; line-height:26px; font-weight:700; letter-spacing:0;}
    .marketingSmsSendVfcDesc {margin:8px 0 22px; text-align:center; color:#777; font-size:12px; line-height:19px; letter-spacing:0;}
    .marketingSmsSendVfcInputRow {display:flex; gap:6px;}
    .marketingSmsSendVfcInput {min-width:0; flex:1; height:46px; padding:0 12px; box-sizing:border-box; border:1px solid transparent; border-radius:4px; background:#eaf1fb; color:#333; font-size:15px; font-weight:700; text-align:center; outline:none; letter-spacing:0;}
    .marketingSmsSendVfcInput:focus {border-color:#3b82d9; background:#fff;}
    .marketingSmsSendVfcInput.error {border-color:#d3413c; background:#fcebea;}
    .marketingSmsSendVfcSendBtn {flex-shrink:0; height:46px; padding:0 13px; border:0; border-radius:4px; background:#3baa5c; color:#fff; font-size:12px; font-weight:700; cursor:pointer; white-space:nowrap;}
    .marketingSmsSendVfcSendBtn:hover {background:#2f8e4b;}
    .marketingSmsSendVfcSendBtn:disabled {background:#9ca5ad; cursor:default;}
    .marketingSmsSendVfcMeta {margin:8px 0 14px; color:#999; font-size:11px; line-height:17px;}
    .marketingSmsSendVfcMeta strong {color:#d3413c;}
    .marketingSmsSendVfcMessage {display:none; margin-bottom:14px; padding:9px 10px; border-radius:4px; background:#edf6ef; color:#2f7c44; font-size:12px; line-height:18px; white-space:pre-line; word-break:keep-all;}
    .marketingSmsSendVfcMessage.show {display:block;}
    .marketingSmsSendVfcMessage.error {background:#fcebea; color:#c43631;}
    .marketingSmsSendVfcConfirmBtn {width:100%; height:46px; border:0; border-radius:4px; background:#3b82d9; color:#fff; font-size:15px; font-weight:700; cursor:pointer;}
    .marketingSmsSendVfcConfirmBtn:hover {background:#2e6fc0;}
    .marketingSmsSendVfcConfirmBtn:disabled {background:#9ca5ad; cursor:default;}
    .marketingSmsSendVfcNote {margin-top:12px; color:#999; text-align:center; font-size:11px; line-height:17px;}
</style>

<wj-popup control="wjMarketingSmsSendVfcLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:410px;border-radius:8px;overflow:hidden;" fade-in="false" fade-out="false">
    <div class="wj-dialog wj-dialog-columns marketingSmsSendVfcDialog" ng-controller="marketingSmsSendVfcCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            추가인증
            <a href="#" class="wj-hide btn_close" ng-click="close()" title="닫기"></a>
        </div>

        <div class="marketingSmsSendVfcModal">

            <div class="marketingSmsSendVfcIcon"><i class="fa fa-shield" aria-hidden="true"></i></div>
            <h2 class="marketingSmsSendVfcTitle">인증번호 확인</h2>
            <p class="marketingSmsSendVfcDesc">SMS 전송을 위해<br />등록된 휴대폰번호로 추가인증을 진행합니다.</p>

            <div class="marketingSmsSendVfcInputRow">
                <input type="text" id="marketingSmsSendVfcNo" class="marketingSmsSendVfcInput" placeholder="인증번호" maxlength="6" inputmode="numeric" autocomplete="off" />
                <button type="button" id="marketingSmsSendVfcSendBtn" class="marketingSmsSendVfcSendBtn" ng-click="requestCode()">인증번호 받기</button>
            </div>
            <div class="marketingSmsSendVfcMeta">인증번호를 요청하면 <strong id="marketingSmsSendVfcRemainTime">03:00</strong> 이내로 입력해주세요.</div>

            <div id="marketingSmsSendVfcMessage" class="marketingSmsSendVfcMessage" role="alert"></div>
            <button type="button" id="marketingSmsSendVfcConfirmBtn" class="marketingSmsSendVfcConfirmBtn" ng-click="verifyCode()">확인</button>
            <div class="marketingSmsSendVfcNote">요청 또는 입력 제한 횟수를 초과하면 일정 시간 추가인증이 제한됩니다.</div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/marketingSmsSend/marketingSmsSendVfc.js?ver=20260812.02" charset="utf-8"></script>
