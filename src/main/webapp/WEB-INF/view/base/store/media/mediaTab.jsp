<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/prod/recpOrigin"/>

<div class="con">
    <div class="tabType1" ng-controller="mediaTabCtrl" ng-init="init()">
        <ul>
            <%-- 미디어관리 탭 --%>
            <li>
                <a id="mediaTab" href="#" class="on" ng-click="mediaShow()"><s:message code="mediaTab.media"/></a>
            </li>
            <%-- 재생순서관리 --%>
            <li>
                <a id="mediaPlaySeqTab" href="#" ng-click="mediaPlaySeqShow()"><s:message code="mediaTab.mediaPlaySeq"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/media/mediaTab.js?ver=20221017.02" charset="utf-8"></script>

<script type="text/javascript">

    // POS에서 해당 WEB 화면 최초 접속한 경우(접속하면서 session 생성), 왼쪽 메뉴영역은 접어두기.
    // 최초 접속시에는 이전 URL 인자값으로 판별가능
    var referrer = document.referrer;
    if(referrer.indexOf("userId") > 0 && referrer.indexOf("resrceCd") > 0 && referrer.indexOf("accessCd") > 30 ){
        $(".menuControl").trigger("click");
    }

    // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값 판단하여 왼쪽 메뉴영역은 접어두기.
    // 재접속시에는 이전 URL 인자값이 없어, 로그인 여부 판별시에 특정 parameter 값을 보내 처리.
    if("${posLoginReconnect}" === "Y"){ // 직접입력한경우
        $(".menuControl").trigger("click");
    }
</script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 미디어관리 레이어 --%>
<c:import url="/WEB-INF/view/base/store/media/media.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 재생순서관리 레이어 --%>
<c:import url="/WEB-INF/view/base/store/media/mediaPlaySeq.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>