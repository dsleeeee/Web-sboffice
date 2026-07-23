<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="sysConnectKpnCtrl" ng-init="init()" style="text-align: center;padding:300px 0px;">

    <button class="btn_blue" ng-click="sysConnectKpn()">
        KPN시스템접속
    </button>
</div>
<script type="text/javascript" src="/resource/solbipos/js/adi/sysConnectKpn/sysConnectKpn/sysConnectKpn.js?ver=20260721.01" charset="utf-8"></script>
