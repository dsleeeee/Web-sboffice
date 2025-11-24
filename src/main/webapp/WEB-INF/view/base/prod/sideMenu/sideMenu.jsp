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
    <ul class="subTab mt10">
      <%-- 속성 --%>
      <li><a id="sideMenuAttr" href="#" class="on" ng-click="changeTab('A');"><s:message code="sideMenu.tab.attr" /></a></li>
      <%-- 선택메뉴(세트) --%>
      <li><a id="sideMenuSelectMenu" href="#" ng-click="changeTab('C');"><s:message code="sideMenu.tab.selectMenu(set)" /></a></li>
      <%-- 선택메뉴(싱글) --%>
      <li><a id="sideMenuSelectMenuSingle" href="#" ng-click="changeTab('S');"><s:message code="sideMenu.tab.selectMenu(single)" /></a></li>
      <%-- 옵션메뉴(배달) --%>
      <%--<li><a id="optionMenuDlvr" href="#" ng-click="changeTab('O');"><s:message code="sideMenu.tab.optionMenu(dlvr)" /></a></li>--%>
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

  <c:import url="/WEB-INF/view/base/prod/sideMenu/optionMenuDlvr.jsp">
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
  var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
  var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
  var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
  var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
  var momsStoreFg05ComboList = ${momsStoreFg05ComboList};

  // 보나비(A0001) 전용 선택분류 '진행단계' 콤보박스 셋팅 데이터
  var progressStageData = ${ccu.getCommCodeExcpAll("241")};
  var bonaviePopUpClassYnData = [{name:"미사용", value:"N"}];

  for(var i = 0; i < progressStageData.length; i++){
      var comboData   = {};
      comboData.name = progressStageData[i].name;
      comboData.value = progressStageData[i].value;
      bonaviePopUpClassYnData.push(comboData);
  }
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenu.js?ver=20251112.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:if test="${param.gubun ne 'sideMenu'}">
  <c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
  </c:import>
</c:if>