<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="sideMenuCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>

  <div class="mt10 oh">
    <button class="btn_blue fr" id="btnSearch" ng-click="queryTab();" >
      <s:message code="cmm.search" />
    </button>
  </div>

  <%-- 탭 --%>
  <div>
    <ul class="subTab">
      <%-- 속성 --%>
      <li><a id="sideMenuAttr" href="#" class="on" ng-click="changeTab('A');"><s:message code="sideMenu.tab.attr" /></a></li>
      <%-- 선택메뉴 --%>
      <li><a id="sideMenuSelectMenu" href="#" ng-click="changeTab('M');"><s:message code="sideMenu.tab.selectMenu" /></a></li>
    </ul>
  </div>

  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuAttr.jsp">
  </c:import>

  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuSelectMenu.jsp">
  </c:import>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenu.js?ver=20181120.01" charset="utf-8"></script>


