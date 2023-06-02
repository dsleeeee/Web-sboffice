<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<c:set var="prodEnvstVal" value="${prodEnvstVal}" />--%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="sideMenuCtrl" id="sideMenuView">

  <div class="searchBar">
    <a href="#" class="open fl"><s:message code="storeSideMenu.sideMenu" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="queryTab();" >
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>


  <%-- 탭 --%>
  <div>
    <ul class="subTab mt20">
      <%-- 속성 --%>
      <li><a id="sideMenuAttr" href="#" class="on" ng-click="changeTab('A');"><s:message code="sideMenu.tab.attr" /></a></li>
      <%-- 선택메뉴 --%>
      <li><a id="sideMenuSelectMenu" href="#" ng-click="changeTab('C');"><s:message code="sideMenu.tab.selectMenu" /></a></li>
      <%-- 선택메뉴(싱글) --%>
      <li><a id="sideMenuSelectMenuSingle" href="#" ng-click="changeTab('S');"><s:message code="sideMenu.tab.selectMenu(single)" /></a></li>
      <%-- 사이드메뉴관리 --%>
      <li><a id="sideMenuManage" href="#" ng-click="changeTab('M');"><s:message code="sideMenu.tab.manage" /></a></li>
    </ul>
  </div>

  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuAttr.jsp">
  </c:import>

  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuSelectMenu.jsp">
  </c:import>

  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuSelectMenuSingle.jsp">
  </c:import>

  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuManage.jsp">
  </c:import>

</div>

<script>
<%--  var prodEnvstVal = "${prodEnvstVal}";--%>

  // [1014 포스프로그램구분] 환경설정값
  var posVerEnvstVal = "${posVerEnvstVal}";
  // [1261 필수선택사용여부] 환경설정값
  var requireYnEnvstVal = "${requireYnEnvstVal}";

  var momsEnvstVal = "${momsEnvstVal}"; // [1250 맘스터치] 환경설정값
  var branchCdComboList = ${branchCdComboList};
  var momsTeamComboList = ${momsTeamComboList};
  var momsAcShopComboList = ${momsAcShopComboList};
  var momsAreaFgComboList = ${momsAreaFgComboList};
  var momsCommercialComboList = ${momsCommercialComboList};
  var momsShopTypeComboList = ${momsShopTypeComboList};
  var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenu.js?ver=20230217.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:if test="${param.gubun ne 'sideMenu'}">
  <c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
  </c:import>
</c:if>

