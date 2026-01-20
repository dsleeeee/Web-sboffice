<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjContentPop2Layer" control="wjContentPop2Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:750px;height:610px;" fade-in="false" fade-out="false">
    <div ng-controller="contentPop1Ctrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            광고 및 스팸 문자 정책
            <a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- 내용 --%>
        <div class="subCon">
            <%-- 광고 및 스팸 문자 정책 내용1 --%>
            <div style="width: 710px; height: 520px; overflow-x: auto; overflow-y: auto; border: 1px solid #e8e8e8;" class="tl s14 lh15 pd10">
                <p>
                    <strong>[ 광고 및 스팸SMS문자 정책 ]</strong><br>
                    <br>
                    <br>
                    정보통신망 이용촉진 및 정보보호 등에 관한 법률(정보통신망법) 제50조에 의거하여 모든 휴대폰 스팸 SMS메시지의에 대해서 전발송을 원천적으로 금하고 있습니다.<br>
                    <br>
                    <br>
                    1. 용어정리
                    <br>
                    ο 스팸 : 정보통신망을 통해 수신자의 명시적인 사전 동의 없이 일방적으로 전송되는 영리 목적의 광고성 정보수신자의 의사에 반하여 전달되는 영리를 목적으로 하는 모든 유형의 상업성 정보 (정보통신망법 제50조 제1항)<br>
                    ο (불법) 스팸SMS : 정보통신망법을 위반하여 전송하는 영리목적의 광고성 정보 메시지<br>
                    사전에 수신동의를 하지 않은 고객에게 전송하는 SMS 및 야간시간(21시~8시)에 전송되는 모든 광고성 SMS<br>
                    ο Opt-In : 사전에 수신을 동의한 고객에게만 광고SMS를 발송할 수 있는 방식<br>
                    <br>
                    <br>
                    2. 스팸SMS 전송 정책규제방안<br>
                    <br>
                    - 모든 상업성 SMS는 Opt-In 방식, 즉 사전동의를 받은 고객에 대해서만 전송이 가능합니다.<br>
                    - 야간 시간(오후9시부터 그 다음날 오전 8시까지)의 대 광고성 정보 전송이 금지됩니다. 휴대폰광고는 전송이 금지됩니다.(21시~8시), 야간 시간에대 광고성 정보를 전발송하실 경우 별도의 추가사전 동의를 받으셔야 합니다.<br>
                    - 청소년에게 전송되는 모든 음란성 휴대폰 메시지는 일체 금지됩니다.<br>
                    - 광고성 정보를 전송할 때에는 광고성 정보가 시작되는 부분에 ‘(광고)’를 표시하여야 하며, 전송자의 명칭 및 연락처를 기재하여야 합니다. 또한 수신 거부 또는 수신동의 철회 방식을 구체적으로 밝히셔야 하고, 수신 거부 또는 수신동의 철회시 수신자가 비용을 부담하지 아니함을 반드시 표시하여야 합니다.<br>
                    - 과거에 사용하셨으나 현재는 해지된 전화번호이거나, 실제로 통화 연결이 불가능한 전화번호의 경우 발신번호 차단될 수 있습니다.<br>
                    - 본 정책을 위반할 시 사전동의 없이 서비스 사용이 중단될 수 있으며, 정책 위반으로 인한 서비스 사용중지시 회원의 사이버머니(캐쉬), 마일리지포인트는 현금으로 환불되지 않고 소멸됩니다. 시 고객님의<br>
                    - 본 정책을 위반할 경우 관리자는 정보통신망법에 따라 방송통신위원회 및 한국인터넷진흥원, 뷸법스팸대응센터에 스팸 전송자에 대한 신고 및 자료(성명, 주소, 주민등록번호, 이용기간, 연락처 등)제공이 가능합니다.<br>
                    사전동의 없이 서비스 사용이 중단될수도 있습니다. 서비스 사용중지시, 회원의 사이버머니(캐쉬), 마일리지포인트는 현금으로 환불되지 않으며, 소멸됩니다.<br>
                    - 휴대폰 문자광고는 "[광고]" 문구와 무료 수신거부번호 080번호를 꼭 기입하셔야 합니다.<br>
                    <br>
                    <br>
                    3. 700, 060, 030 발송제한<br>
                    <br>
                    700, 060, 030 번호를 회신번호 또는 메시지 내용에 사용하는 광고성 SMS의 경우에는 반드시 사전에 수신동의 여부를 확인할 수 있는 자료를 관리자에게 제출하여야 합니다.<br>
                    <br>
                    모든 회원께서는 스팸SMS전송 정책관리규정을 준수하여 주시기 바랍니다. 이용에 불편이 없도록 최선을 다하겠습니다.<br>
                    <br>
                </p>
            </div>
            <%-- //광고 및 스팸 문자 정책 내용1 --%>
        </div>
        <%-- //내용 --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/contentPop2.js?ver=20241106.01" charset="utf-8"></script>