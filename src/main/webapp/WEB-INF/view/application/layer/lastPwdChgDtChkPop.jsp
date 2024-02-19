<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />

<div id="fullDimmedLastPwdChgDtChkPop" class="fullDimmed" style="display: none;"></div>
<div id="layerLastPwdChgDtChkPop" class="layer" style="display: none;">
    <div class="layer_inner" style="position:absolute; left:50%; top:50%;  transform: translate(-50%, -50%); text-align: center;">
        <!--layerContent-->
        <div class="title" style="width:470px;">
            <div class="con">
                <s:message code="login.pw.chg.lastPwd"/>
            </div>
            <%--<a href="#" class="btn_close"></a>--%>
            <div class="btnSet">
                <span><a href="#" class="btn_blue" id="btnPwdChg"><s:message code="login.pw.chg"/></a></span>
                <span><a href="#" class="btn_blue" id="btn_close"><s:message code="login.pw.chg.next"/></a></span>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/application/layer/lastPwdChgDtChkPop.js?ver=20240216.01" charset="utf-8"></script>