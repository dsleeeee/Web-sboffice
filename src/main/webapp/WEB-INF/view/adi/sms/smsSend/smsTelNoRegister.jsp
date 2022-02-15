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
<wj-popup id="wjSmsTelNoRegisterLayer" control="wjSmsTelNoRegisterLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:830px;height:690px;" fade-in="false" fade-out="false">
    <div ng-controller="smsTelNoRegisterCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsTelNoRegister.info"/>
            <a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="subCon">
            <div class="w100 pd10">
                <div class="oh sb-select dkbr">
                    <p class="tl s14 mt5 lh15 red mb10">1. 휴대폰번호 등록시</p>
                    <p class="tl s14 mt5 lh15">- "휴대폰번호 인증요청" 버튼을 클릭하면 본인인증을 위한 화면이 뜹니다. 본인인증 성공시 인증한 번호를 발신번호로 저장합니다.</p>
                    <p class="tl s14 mt5 lh15">- 사전등록 인증 처리 진행 중 창을 강제로 닫을 경우 정상적으로 인증처리 되지 않을 수 있습니다.</p>
                </div>
                <div class="oh sb-select dkbr mt10">
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
                    <%-- 휴대폰번호 인증요청 --%>
                    <%--                <button id="request" class="btn_skyblue ml5 fr" ng-click="telNoRequest()">--%>
                    <button id="request" class="btn_skyblue ml5 fr" onclick="return telNoRequest();close();">
                        <s:message code="smsTelNoRegister.telNoRequest" />
                    </button>
                </div>
            </div>
            <div class="w100 pd10 bt">
                <div class="oh sb-select dkbr">
                    <p class="tl s14 mt5 lh15 red mb10">2. 일반번호 등록시</p>
                    <p class="tl s14 mt5 lh15">- "일반번호 인증요청" 버튼을 클릭하면 증빙서류를 제출하기 위한 화면이 뜹니다.</p>
                    <p class="tl s14 mt5 lh15">- 증빙서류는 통신사에서 발급한 "통신서비스 이용 증명원"을 제출해주세요.</p>
                    <p class="tl s14 mt5 lh15">- 소유자와 발신번호를 명확히 증빙이 가능한 서류만 심사를 거친 후 등록을 도와드리고 있습니다.</p>
                    <p class="tl s14 mt5 lh15">- 통신사별 이용 증명원의 발급일자가 최근 3개월 보다 오래된 경우 사용 불가합니다.</p>
                    <p class="tl s14 mt5 lh15">- 서비스 가입 신청서, 요금 납부 내역서 등은 증빙서류로 사용하실 수 없습니다.</p>
                    <p class="tl s14 mt5 lh15">- 발신번호 증빙서류는 사용하고 있는 각 서비스 이통사에 문의하거나, 해당 이통사 홈페이지를 통하여 발급받을 수 있습니다.</p>
                    <p class="tl s14 mt5 lh15 blue">- 인증 요청하신 내역은 "부가서비스 > SMS관리 > SMS전송 > 발신번호관리 탭"에서 확인 가능합니다.</p>
                </div>
                <div class="oh sb-select dkbr mt10">
                    <%-- 일반번호 인증요청 --%>
                    <button id="request" class="btn_skyblue ml5 fr" ng-click="smsGeneralNoRequest()">
                        <s:message code="smsTelNoRegister.smsGeneralNoRequest" />
                    </button>
                </div>
                <div class="oh sb-select dkbr">
                    <p class="tl s14 mt5 lh15 red">* 증빙서류 발급방법 *</p>
                    <p class="tl s14 mt5 lh15 blue">[KT 올레 가입증명원]</p>
                    <p class="tl s14 mt5 lh15">기업고객은 080-530-7979 KT기업고객센터로 전화하여 "통신가입증명원" 발급 요청
                                                팩스로 사업자등록증 및 대표자 신분증 접수 후 팩스로 가입증명원 수신 가능 (팩스로만 수신 받을 수 있는 것에 유의)
                                                개인은 KT 사이트(http://www.olleh.com) 접속 후 로그인
                                                My올레 메뉴 클릭 혹은 내 상품 조회
                                                발급받을 번호(인터넷전화, 일반전화, 모바일) 조회 > 실명인증 후 가입증명원 출력</p>
                    <p class="tl s14 mt5 lh15 blue">[LG U+ 가입사실 확인서]</p>
                    <p class="tl s14 mt5 lh15">모바일의 경우 고객센터(LG U+망 전화번호 114) 문의 후 팩스로 받기 또는
                                                LG U+ 사이트(http://www.uplus.co.kr) 접속 후 로그인
                                                고객지원 > 상품가입안내 > 가입조회 클릭 후 실명인증 및 가입조회
                                                인터넷 전화의 경우 고객센터(전화번호 101) 문의 후 팩스로 받기,
                                                대표번호(법인) 가입확인서 발급시 고객센터 문의(1544-0001)</p>
                    <p class="tl s14 mt5 lh15 blue">[SKT 이용계약증명서]]</p>
                    <p class="tl s14 mt5 lh15">tworld 사이트(http://www.tworld.co.kr) 접속 후 로그인
                                                my T > 조회 > 나의 가입정보 > 이용계약증명서 조회
                                                SK브로드밴드 대표번호(법인) 가입사실 증명원 발급시 고객센터 문의(080-8282-123)</p>
                    <p class="tl s14 mt5 lh15 blue">[알뜰폰, 기타통신사]</p>
                    <p class="tl s14 mt5 lh15">각 고객센터 문의
                                                세종텔레콤(1699-1000)
                                                KCT한국케이블텔레콤(070-8188-0114)
                                                헬로모바일KT(1855-1144, 080-888-0114)
                                                헬로모바일SKT(070-8188-0114)</p>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/smsTelNoRegister.js?ver=20220201.01" charset="utf-8"></script>

<script type="text/javascript">
    // 휴대폰번호 인증요청
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

<%-- 일반번호 인증요청 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSend/smsGeneralNoRegister.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>