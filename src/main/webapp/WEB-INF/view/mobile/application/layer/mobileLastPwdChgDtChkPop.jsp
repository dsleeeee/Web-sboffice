<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />

<div id="fullDimmedMobileLastPwdChgDtChkPop" class="fullDimmed" style="display: none;"></div>
<div id="layerMobileLastPwdChgDtChkPop" class="layer" style="display: none;">
    <div class="layer_inner" style="position:absolute; left:50%; top:50%;  transform: translate(-50%, -50%); text-align: center;">
        <!--layerContent-->
        <div class="title" style="width:355px;">
            <div class="con s13">
                <s:message code="login.pw.chg.lastPwd"/>
            </div>
            <%--<a href="#" class="btn_close"></a>--%>
            <div class="btnSet">
                <span><a href="#" class="btn_blue s13" id="btnPwdChg"><s:message code="login.pw.chg"/></a></span>
                <span><a href="#" class="btn_blue s13" id="btn_close"><s:message code="login.pw.chg.next"/></a></span>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var userId = "${userId}";

    // 6개월이상 비밀번호 미수정시 알림 팝업
    var lastPwdChgDtChk = "${lastPwdChgDtChk}";

    // 메인화면 진입인지 체크
    var mainYn = "${mainYn}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/application/layer/mobileLastPwdChgDtChkPop.js?ver=20250625.01" charset="utf-8"></script>