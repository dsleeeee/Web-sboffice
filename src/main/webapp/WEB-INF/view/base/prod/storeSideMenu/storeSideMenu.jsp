<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
  <div class="tabType1" ng-controller="storeSideMenuCtrl" ng-init="init()">
    <ul>
      <%-- 상품정보관리 --%>
      <li>
        <a id="prodTab" href="#" class="on" ng-click="prodShow()"><s:message code="storeSideMenu.prod"/></a>
      </li>
      <%-- 세트메뉴구성(BBQ전용)--%>
      <li>
        <a id="setProdTab" href="#" ng-click="setProdShow()"><s:message code="storeSideMenu.setProd"/></a>
      </li>
      <%-- 판매터치키등록--%>
      <li>
        <a id="touchKeyTab" href="#" ng-click="touchKeyShow()"><s:message code="storeSideMenu.touchKey"/></a>
      </li>
    </ul>
  </div>
</div>

<script>
  var touchKeyEnvstVal = ${touchKeyEnvstVal};
  var touchKeyGrpData  = ${touchKeyGrp};
  var maxClassRow      = ${maxClassRow};
  var prodNoEnvFg      = "${prodNoEnvFg}";
  var prodAuthEnvstVal = "${prodAuthEnvstVal}";

  // [1014 포스프로그램구분] 환경설정값
  var posVerEnvstVal = "${posVerEnvstVal}";
  // [1261 필수선택사용여부] 환경설정값
  var requireYnEnvstVal = "${requireYnEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/storeSideMenu/storeSideMenu.js?ver=20230906.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 세트메뉴구성(BBQ전용) --%>
<c:import url="/WEB-INF/view/base/prod/setProd/setProd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="gubun" value="sideMenu"/>
</c:import>
<%-- 상품정보관리 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prod.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="gubun" value="sideMenu"/>
</c:import>
<%-- 판매터치키등록 --%>
<c:import url="/WEB-INF/view/base/prod/touchKey/touchKey.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="gubun" value="sideMenu"/>
</c:import>