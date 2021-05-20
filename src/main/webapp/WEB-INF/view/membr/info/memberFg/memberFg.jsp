<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="con">
  <div class="tabType1" ng-controller="memberFgCtrl" ng-init="init()">
    <ul>
      <%-- 선불화면 --%>
      <li><a id="memberPrepaid" href="#" class="on" ng-click="changeTab('Pr');"><s:message code="memberFg.prepaid" /></a></li>
      <%-- 후불화면 --%>
      <li><a id="memberPostpaid" href="#" ng-click="changeTab('Po');"><s:message code="memberFg.postpaid" /></a></li>
    </ul>
  </div>
</div>

<%-- 선불화면 --%>
<c:import url="/WEB-INF/view/membr/info/memberFg/memberPrepaid.jsp">
  <c:param name="memberClassList" value="${memberClassList}"/>
</c:import>

<%-- 후불화면 --%>
<c:import url="/WEB-INF/view/membr/info/memberFg/memberPostpaid.jsp">
  <c:param name="memberClassList" value="${memberClassList}"/>
</c:import>

<script>
  var memberClassList = ${memberClassList}; <%-- 회원등급목록 --%>
</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/memberFg/memberFg.js?ver=20181220.01" charset="utf-8"></script>

