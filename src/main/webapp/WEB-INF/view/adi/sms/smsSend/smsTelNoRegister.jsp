<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- $scope.getVal()이 getKcpVerifyVal.sb를 호출해 KCP 인증 폼 제출값을 준비한다. --%>
<wj-popup id="wjSmsTelNoRegisterLayer" control="wjSmsTelNoRegisterLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:830px;height:690px;" fade-in="false" fade-out="false">
    <div ng-controller="smsTelNoRegisterCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsTelNoRegister.info"/>
            <a href="#" id="btnSmsTelNoRegisterClose" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="subCon">
            <div class="w100 pd10">
                <div class="oh">
                    <p class="tl s14 mt5 lh15 red mb10">1. 휴대폰번호 등록시</p>
                    <p class="tl s14 mt5 lh15">- "휴대폰번호 인증요청" 버튼을 클릭하면 본인인증을 위한 화면이 뜹니다. 본인인증 성공시 인증한 번호를 발신번호로 저장합니다.</p>
                    <p class="tl s14 mt5 lh15">- 사전등록 인증 처리 진행 중 창을 강제로 닫을 경우 정상적으로 인증처리 되지 않을 수 있습니다.</p>
                </div>
                <div class="mt10 oh">
                    <%-- #smsTelNoRegisterOrdrIdxx는 getSmsTelNoManageSave.sb의 CERT_ID만 보관한다. --%>
                    <input type="hidden" id="smsTelNoRegisterOrdrIdxx" />
                    <%-- smsSend.jsp의 두 인증 폼을 구분하도록 #smsTelNoRegisterKcpAuthForm과 form.elements를 사용한다. --%>
                    <%-- telNoRequest()가 reg_cert_key/kcp_page_submit_yn만 POST하며 getSmsTelNoRegisterRequest.sb는 결과를 S2S 조회한다. --%>
                    <form id="smsTelNoRegisterKcpAuthForm" name="smsTelNoRegisterKcpAuthForm" method="post" style="display:none;">
                        <input type="hidden" name="reg_cert_key" />
                        <input type="hidden" name="kcp_page_submit_yn" value="N" />
                    </form>
                    <%-- 휴대폰번호 인증요청 --%>
                    <button id="btnSmsTelNoRequest" class="btn_skyblue ml5 fr" onclick="return telNoRequest();">
                        <s:message code="smsTelNoRegister.telNoRequest" />
                    </button>
                </div>
            </div>
            <div class="w100 pd10 bt">
                <div class="oh">
                    <p class="tl s14 mt5 lh15 red mb10">2. 일반번호 등록시</p>
                    <p class="tl s14 mt5 lh15">- "일반번호 인증요청" 버튼을 클릭하면 증빙서류를 제출하기 위한 화면이 뜹니다.</p>
                    <p class="tl s14 mt5 lh15">- 증빙서류는 통신사에서 발급한 "통신서비스 이용 증명원"을 제출해주세요.</p>
                    <p class="tl s14 mt5 lh15">- 소유자와 발신번호를 명확히 증빙이 가능한 서류만 심사를 거친 후 등록을 도와드리고 있습니다.</p>
                    <p class="tl s14 mt5 lh15">- 통신사별 이용 증명원의 발급일자가 최근 3개월 보다 오래된 경우 사용 불가합니다.</p>
                    <p class="tl s14 mt5 lh15">- 서비스 가입 신청서, 요금 납부 내역서 등은 증빙서류로 사용하실 수 없습니다.</p>
                    <p class="tl s14 mt5 lh15">- 발신번호 증빙서류는 사용하고 있는 각 서비스 이통사에 문의하거나, 해당 이통사 홈페이지를 통하여 발급받을 수 있습니다.</p>
                    <p class="tl s14 mt5 lh15">※ 인증 요청하신 내역은 "부가서비스 > SMS관리 > SMS전송 > 발신번호관리 탭"에서 확인 가능합니다.</p>
                </div>
                <div class="mt10 oh">
                    <%-- 일반번호 인증요청 --%>
                    <button id="btnSmsGeneralNoRequest" class="btn_skyblue ml5 fr" ng-click="smsGeneralNoRequest()">
                        <s:message code="smsTelNoRegister.smsGeneralNoRequest" />
                    </button>
                </div>
                <div class="oh">
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

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/smsTelNoRegister.js?ver=20260813.01" charset="utf-8"></script>

<script type="text/javascript">
    var smsTelNoRegisterAuthPopup = null;
    var smsTelNoRegisterAuthTimer = null;

    // KCP 인증창·감시 타이머·인증 폼·주문번호를 정리한다.
    // closePopup이 true이면 열린 인증 팝업도 닫는다.
    function clearSmsTelNoRegisterAuth(closePopup) {
        if (smsTelNoRegisterAuthTimer) {
            clearInterval(smsTelNoRegisterAuthTimer);
            smsTelNoRegisterAuthTimer = null;
        }

        if (closePopup && smsTelNoRegisterAuthPopup && !smsTelNoRegisterAuthPopup.closed) {
            smsTelNoRegisterAuthPopup.close();
        }
        smsTelNoRegisterAuthPopup = null;

        var authForm = document.getElementById("smsTelNoRegisterKcpAuthForm");
        if (authForm) {
            authForm.reset();
            authForm.removeAttribute("action");
            authForm.removeAttribute("target");
            delete authForm.dataset.callUrl;
        }
        $("#smsTelNoRegisterOrdrIdxx").val("");
    }

    // KCP 인증 대기 데이터를 저장한 뒤 인증 팝업에 폼을 제출한다.
    function telNoRequest(){
        var authForm = document.getElementById("smsTelNoRegisterKcpAuthForm");
        var ordrIdxx = $("#smsTelNoRegisterOrdrIdxx").val();

        // $scope.getVal()이 채운 callUrl/reg_cert_key/ordrIdxx가 모두 있어야 telNoRequest()를 진행한다.
        if (!authForm || !authForm.dataset.callUrl ||
            !authForm.elements["reg_cert_key"].value || !ordrIdxx) {
            s_alert.pop("본인확인 요청 준비 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.");
            return false;
        }

        var width = 410;
        var height = 500;
        var leftpos = screen.width / 2 - (width / 2);
        var toppos = screen.height / 2 - (height / 2);
        var winopts = "width=" + width + ", height=" + height + ", toolbar=no,status=no,statusbar=no,menubar=no,scrollbars=no,resizable=no";
        var position = ",left=" + leftpos + ", top=" + toppos;
        var popupName = "smsTelNoRegisterKcpAuth_" + new Date().getTime();

        // 사용자 클릭 시점에 빈 KCP 인증창을 선오픈한다.
        smsTelNoRegisterAuthPopup = window.open("about:blank", popupName, winopts + position);
        if (!smsTelNoRegisterAuthPopup) {
            s_alert.pop("팝업이 차단되었습니다. 팝업 허용 후 다시 시도해주세요.");
            return false;
        }

        document.getElementById("btnSmsTelNoRegisterClose").click();

        // 저장기능 수행
        var params = {};
        params.certId = ordrIdxx;

        var saveHandled = false;
        // KCP 콜백이 갱신할 CERT_ID 대기 데이터를 저장한다.
        var saveRequest = $.postJSONArray("/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoManageSave.sb", params, function () {
                saveHandled = true;
                if (!smsTelNoRegisterAuthPopup || smsTelNoRegisterAuthPopup.closed) {
                    // 닫힌 KCP 팝업·감시 타이머·인증 폼을 정리한다.
                    clearSmsTelNoRegisterAuth(false);
                    return;
                }

                authForm.action = authForm.dataset.callUrl;
                authForm.target = popupName;
                // 선오픈한 팝업을 KCP 인증창으로 이동시킨다.
                authForm.submit();

                // getSmsTelNoRegisterRequest.sb가 창을 닫으면 smsTelNoRegisterAuthTimer가 clearSmsTelNoRegisterAuth()를 호출한다.
                smsTelNoRegisterAuthTimer = setInterval(function () {
                    if (!smsTelNoRegisterAuthPopup || smsTelNoRegisterAuthPopup.closed) {
                        // 인증을 마친 KCP 팝업·감시 타이머·인증 폼을 정리한다.
                        clearSmsTelNoRegisterAuth(false);
                    }
                }, 500);
            },
            function (result) {
                saveHandled = true;
                // 대기 데이터 저장 실패 시 KCP 팝업·타이머·폼을 정리한다.
                clearSmsTelNoRegisterAuth(true);
                s_alert.pop(result.message);
            });

        saveRequest.always(function () {
            if (!saveHandled) {
                // 미처리 저장 종료 시 KCP 팝업·타이머·폼을 정리한다.
                clearSmsTelNoRegisterAuth(true);
            }
        });
        return false;
    }
</script>

<%-- #wjSmsGeneralNoRegisterLayer는 getValSmsGeneralNo()의 getVal.sb CERT_ID 채번만 사용한다. --%>
<c:import url="/WEB-INF/view/adi/sms/smsSend/smsGeneralNoRegister.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
