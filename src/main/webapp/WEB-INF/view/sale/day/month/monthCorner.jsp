<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/month/monthCorner/"/>

<div id="monthCornerView" name="monthView" class="subCon" style="display: none;" ng-controller="monthCornerCtrl">
    코너별
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/month/monthCorner.js?ver=20191209" charset="utf-8"></script>