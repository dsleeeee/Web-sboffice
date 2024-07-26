<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}"/>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<div class="subCon" ng-controller="virtualAccountCtrl">

    <div class="mt10 oh sb-select dkbr">
        <%-- 가상계좌 입금 생성 --%>
        <button class="btn_skyblue ml5 mb10 fr" ng-click="loanVirtualAccount()"><s:message code="virtualAccount.virtualAccountRegister"/></button>
        <%--<span class="fr bk lh30">--%>
            <%--&lt;%&ndash; 여신사용액 &ndash;%&gt;--%>
            <%--<label id="lblUseLoanAmt" style="display: none;"></label>--%>
        <%--</span>--%>
    </div>

</div>

<script type="text/javascript">
    var orgnCd = "${orgnCd}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";
    var userId = "${userId}";

    // KCP PG-API 인증서정보(직렬화)
    var site_cd = "${site_cd}";

    // NHN KCP 발급 사이트코드
    var kcp_cert_info = "${kcp_cert_info}";

    <%-- 가상계좌 API 은행코드 --%>
    var vaBankcodeComboData = ${ccu.getCommCodeSelect("233")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/virtualAccount/virtualAccount.js?ver=20240724.01" charset="utf-8"></script>

<%-- 가상계좌 입금 생성 팝업 --%>
<c:import url="/WEB-INF/view/iostock/loan/virtualAccount/virtualAccountRegister.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>