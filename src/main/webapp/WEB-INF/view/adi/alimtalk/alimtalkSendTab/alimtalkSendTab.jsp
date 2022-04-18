<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/adi/alimtalk/alimtalkSendTab"/>

<div class="con">
    <div class="tabType1" ng-controller="alimtalkSendTabCtrl" ng-init="init()">

        화면 준비중

    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendTab/alimtalkSendTab.js?ver=20220418.01" charset="utf-8"></script>