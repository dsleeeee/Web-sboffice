<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con" id="kioskKeyMapView" name="kioskKeyMapView">
    <div class="tabType1" ng-controller="kioskKeyMapManageCtrl" ng-init="init()">
        <ul>
            <%-- 키오스크키맵등록 탭 --%>
            <li>
                <a id="kioskKeyMapRegistTab" href="#" class="on" ng-click="kioskKeyMapRegistShow()"><s:message code="kioskKeyMap.kioskKeyMapRegist"/></a>
            </li>
            <%-- 키오스크키맵복사 탭 --%>
            <%--<li>
                <a id="kioskKeyMapCopyTab" href="#" ng-click="kioskKeyMapCopyShow()"><s:message code="kioskKeyMap.kioskKeyMapCopy"/></a>
            </li>--%>
        </ul>
    </div>
</div>

<script type="text/javascript">

    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";
    // 사용자 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMap.js?ver=20220111.01" charset="utf-8"></script>
<%-- 탭페이지 레이어 시작 --%>
<%-- 키오스크키맵등록 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 키오스크키맵복사 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapCopy.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝--%>