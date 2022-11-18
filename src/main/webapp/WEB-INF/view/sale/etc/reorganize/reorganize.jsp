<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
  <div class="tabType1" ng-controller="storeSideMenuCtrl" ng-init="init()">
    <ul>
      <%-- 사이드메뉴관리 --%>
      <li>
        <a id="sideMenuTab" href="#" class="on" ng-click="sideMenuShow()"><s:message code="reorganize.prod"/></a>
      </li>
      <%-- 상품정보관리 --%>
      <li>
        <a id="prodTab" href="#" ng-click="prodShow()"><s:message code="reorganize.day"/></a>
      </li>
      <%-- 판매터치키등록--%>
      <li>
        <a id="touchKeyTab" href="#" ng-click="touchKeyShow()"><s:message code="reorganize.prod"/></a>
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
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/etc/reorganize/reorganize.js?ver=20211220.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 사이드메뉴관리 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenu.jsp">
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