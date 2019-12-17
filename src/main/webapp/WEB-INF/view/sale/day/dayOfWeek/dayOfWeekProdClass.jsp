<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/dayOfWeek/dayOfWeekProdClass/"/>

<div id="dayOfWeekProdClassView" name="dayOfWeekView" class="subCon" style="display: none;" ng-controller="dayOfWeekProdClassCtrl">
    상품분류별
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayOfWeek/dayOfWeekProdClass.js?ver=20191119" charset="utf-8"></script>