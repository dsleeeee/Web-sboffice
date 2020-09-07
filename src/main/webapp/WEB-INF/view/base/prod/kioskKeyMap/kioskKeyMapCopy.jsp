<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="kioskKeyMapCopyView" name="kioskKeyMapCopyView" class="subCon" style="display: none;" ng-controller="kioskKeyMapCopyCtrl">
</div>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapCopy.js?ver=20190220.01" charset="utf-8"></script>