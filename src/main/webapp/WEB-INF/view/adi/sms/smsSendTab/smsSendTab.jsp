<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/adi/sms/smsSendTab"/>

<div class="con">
    <div class="tabType1" ng-controller="smsSendTabCtrl" ng-init="init()">
        <ul>
            <c:if test="${orgnFg == 'HQ' or orgnFg == 'STORE'}">
                <%-- 마케팅용 SMS전송 탭 --%>
                <li>
                    <a id="marketingSmsSendTab" href="#" class="on" ng-click="marketingSmsSendShow()"><s:message code="smsSendTab.marketingSmsSend"/></a>
                </li>
            </c:if>
            <%-- 문자전송현황 탭 --%>
            <li>
                <a id="sendStatusTab" href="#" ng-click="sendStatusShow()"><s:message code="smsSendTab.sendStatus"/></a>
            </li>
            <%-- 메세지관리 탭 --%>
            <li>
                <a id="msgManageTab" href="#" ng-click="msgManageShow()"><s:message code="smsSendTab.msgManage"/></a>
            </li>
            <%-- 발신번호관리 탭 --%>
            <li>
                <a id="smsTelNoManageTab" href="#" ng-click="smsTelNoManageShow()"><s:message code="smsSendTab.smsTelNoManage"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSendTab/smsSendTab.js?ver=20210915.02" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<c:if test="${orgnFg == 'HQ' or orgnFg == 'STORE'}">
    <%-- 마케팅용 SMS전송 레이어 --%>
    <c:import url="/WEB-INF/view/adi/sms/marketingSmsSend/marketingSmsSend.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>
</c:if>

<%-- 문자전송현황 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/sendStatus/sendStatus.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 메세지관리 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/msgManage/msgManage.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 발신번호관리 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/smsTelNoManage/smsTelNoManage.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>