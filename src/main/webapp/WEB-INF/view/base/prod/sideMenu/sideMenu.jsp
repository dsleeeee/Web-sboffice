<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="sideMenuCtrl">
  <%--searchTbl--%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>


</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/sideMenu.js?ver=20181114.01" charset="utf-8"></script>


