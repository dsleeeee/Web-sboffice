<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page import="java.text.*" %>--%>
<%--<%@ page import="java.util.*" %>--%>
<%--<%@ page import="kr.co.kcp.CT_CLI"%>--%>

<%--<%--%>
<%--    String  SITE_CD ="";--%>
<%--    String  WEB_SITEID = "";--%>
<%--    String  ENC_KEY = "";--%>
<%--    String  RET_URL = "";--%>
<%--    String  GW_URL = "";--%>

<%--//    if(request.getRequestURL().indexOf("://192") > 0 || request.getRequestURL().indexOf("://localhost") > 0) {--%>
<%--//        SITE_CD = "S6186";--%>
<%--//        WEB_SITEID = "";--%>
<%--//        ENC_KEY = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0";--%>
<%--//        // https로만 결과값 전송이 가능한데 개발서버는 http라 테스트 불가능--%>
<%--////        RET_URL = "https://192.168.0.85:10001/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoRegisterRequest.sb";--%>
<%--//        RET_URL      = "https://neo.solbipos.com/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoRegisterRequest.sb";--%>
<%--//        GW_URL = "https://testcert.kcp.co.kr/kcp_cert/cert_view.jsp";--%>
<%--//    }--%>
<%--//    else if(request.getRequestURL().indexOf("://neo.solbipos.com") > 0) {--%>
<%--        SITE_CD      = "AGSVU";--%>
<%--        WEB_SITEID   = "J21101407426";--%>
<%--        ENC_KEY      = "beba66643a50ad06b9bd92b6bcf6239d8199071bc8ffd361a81441f651f8efd2";--%>
<%--        RET_URL      = "https://neo.solbipos.com/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoRegisterRequest.sb";--%>
<%--        GW_URL       = "https://cert.kcp.co.kr/kcp_cert/cert_view.jsp";--%>
<%--//    }--%>
<%--        String ordr_idxx = new SimpleDateFormat("yyyyMMddHHmmssSSSSSSS").format(new Date());--%>
<%--%>--%>
<%--<%--%>
<%--    System.out.println("시간:: " + new Date());--%>
<%--    CT_CLI       cc      = new CT_CLI();--%>

<%--    String up_hash       = "";--%>
<%--    up_hash = cc.makeHashData( ENC_KEY, SITE_CD   +--%>
<%--            ordr_idxx +--%>
<%--            ""   +--%>
<%--            ""   +--%>
<%--            "00" +--%>
<%--            "00" +--%>
<%--            "00" +--%>
<%--            ""   +--%>
<%--            ""--%>
<%--    );--%>
<%--%>--%>
<wj-popup id="wjSmsTelNoRegisterLayer" control="wjSmsTelNoRegisterLayer" show-trigger="Click" hide-trigger="Click" style="display:none;" fade-in="false" fade-out="false">

    <div ng-controller="smsTelNoRegisterCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsTelNoRegister.info"/>
            <a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="subCon">
            <div class="oh sb-select dkbr">
                <p class="tl s14 mt5 lh15">- "인증요청" 버튼을 클릭하면 본인인증을 위한 화면이 뜹니다.</p>
                <p class="tl s14 mt5 lh15">&nbsp;&nbsp;본인인증 성공시 인증한 번호를 발신번호로 저장합니다.</p>
                <p class="tl s14 mt5 lh15">- 사전등록 인증 처리 진행 중 창을 강제로 닫을 경우 정상적으로 </p>
                <p class="tl s14 mt5 lh15">인증처리 되지 않을 수 있습니다.</p>
            </div>

            <div class="mt20 oh sb-select dkbr">
                <%-- 사전등록 발신번호 --%>
                <%--                <s:message code="smsTelNoRegister.registerTelNo" />--%>
                <%--                    <input type="hidden" class="sb-input w70" id="ordr_idxx" ng-model="ordr_idxx" />--%>
                <%--                    <input type="hidden" class="sb-input w70" id="site_cd" value="${siteCd}" />--%>
                <%--                    <input type="hidden" class="sb-input w70" id="gw_url" value="${gwUrl}" />--%>
                <%--                    <input type="hidden" class="sb-input w70" id="Ret_URL" value="${retUrl}" />--%>
                <%--                    <input type="text" class="sb-input w70" id="site_cd2" value="<%=SITE_CD%>" />--%>
                <%--                    <input type="text" class="sb-input w70" id="web_sitei2" value="<%=WEB_SITEID%>" />--%>
                <%--                    <input type="text" class="sb-input w70" id="gw_url2" value="<%=GW_URL%>" />--%>
                <%--                    <input type="text" class="sb-input w70" id="Ret_URL2" value="<%=RET_URL%>" />--%>
                <%--                    <input type="text" class="sb-input w70" id="ordr_idxx2" value="<%=ordr_idxx%>" />--%>
                <%--                    <input type="text" class="sb-input w70" id="up_hash2" value="<%=up_hash%>" />--%>
                <%--                    <br>--%>
                <%--                    <Br>--%>
                <input type="hidden" class="sb-input w70" id="site_cd" />
                <input type="hidden" class="sb-input w70" id="web_siteid" />
                <input type="hidden" class="sb-input w70" id="gw_url" />
                <input type="hidden" class="sb-input w70" id="Ret_URL"  />
                <input type="hidden" class="sb-input w70" id="ordr_idxx" />
                <input type="hidden" class="sb-input w70" id="up_hash"  />
                <input type="hidden" class="sb-input w70" id="sessionId"  />
                <%-- 인증요청 --%>
                <%--                <button id="request" class="btn_skyblue ml5 fr" ng-click="telNoRequest()">--%>
                <button id="request" class="btn_skyblue ml5 fr" onclick="return telNoRequest();close();">
                    <s:message code="smsTelNoRegister.telNoRequest" />
                </button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/smsTelNoRegister.js?ver=20210625.05" charset="utf-8"></script>

<script type="text/javascript">

    // 인증요청
    function telNoRequest(){
        // $("#wjSmsTelNoRegisterLayer").hide();
        document.getElementById('btn_close').click();
        var auth_form = document.form_auth;

        var return_gubun;
        var width = 410;
        var height = 500;

        var leftpos = screen.width / 2 - (width / 2);
        var toppos = screen.height / 2 - (height / 2);

        var winopts = "width=" + width + ", height=" + height + ", toolbar=no,status=no,statusbar=no,menubar=no,scrollbars=no,resizable=no";
        var position = ",left=" + leftpos + ", top=" + toppos;


        var url = $("#gw_url").val() + '?' +                        // KCP 인증창
            'site_cd=' + $("#site_cd").val() + '&' +                // 상점코드
            'ordr_idxx=' + $("#ordr_idxx").val() + '&' +            // 상점관리요청번호
            'req_tx=cert' + '&' +                                   // 요청의 종류를 구분하는 변수
            'cert_method=01' + '&' +                                // 01-휴대폰인증 02-공인인증(추후제공)
            'up_hash=' + $("#up_hash").val() + '&' +                // 요청 hash data
            'Ret_URL=' + $("#Ret_URL").val() + '?sid=' + $("#sessionId").val() + '&' +                // 본인인증 결과 리턴페이지
            'cert_otp_use=Y' + '&' +                                // 인요청시 OTP승인 여부
            'cert_enc_use_ext=Y'
        ;
        console.log("JH");
        console.log("site_cd : " + $("#site_cd").val());
        console.log("web_siteid : " + $("#web_siteid").val());
        console.log("gw_url : " + $("#gw_url").val());
        console.log("Ret_URL : " + $("#Ret_URL").val());
        console.log("ordr_idxx : " + $("#ordr_idxx").val());
        console.log("up_hash : " + $("#up_hash").val());
        console.log("sessionID : " + $("#sessionId").val());
        console.log("url : " + url);
        // 저장기능 수행
        var params = {};
        params.certId = $("#ordr_idxx").val();

        $.postJSONArray("/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoManageSave.sb", params, function (result) {
                console.log("JH : 결과");
                // window.dialogArguments.window.parentsPopWIndow = window;
                // window.dialogArguments.window.parentsPopWIndow = window;
                // if (window.dialogArguments != null){
                //     console.log('믱');
                //
                // } else {
                //     console.log("뫙");
                // }
                var AUTH_POP =  window.open(url, 'auth_popup', winopts + position);

                // var AUTH_POP =  Window.open(url, 'auth_popup', winopts + position);
                // var AUTH_POP = window.open(url, 'auth_popup', winopts + position);
                console.log('1111');
            },
            function (result) {
                s_alert.pop("JH : 결과msg" + result.message);
                s_alert.pop(result.message);
            });



    };

</script>