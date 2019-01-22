<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockReqDate/outstockReqDate/"/>

<div class="con">
  <%-- 요일별, 특정일, 요청일복사 탭 --%>
  <div class="tabType1" ng-controller="outstockReqDateTabCtrl" ng-init="init()">
    <ul>
      <%-- 출고요청일관리 요일별 탭 --%>
      <li>
        <a id="daysTab" href="#" class="on" ng-click="daysShow()"><s:message code="outstockReqDate.days"/></a>
      </li>
      <%-- 출고요청일관리 특정일 탭 --%>
      <li>
        <a id="specificTab" href="#" ng-click="specificShow()"><s:message code="outstockReqDate.specificDate"/></a>
      </li>
      <%-- 출고요청일관리 요청일복사 탭 --%>
      <li>
        <a id="reqDateCopyTab" href="#" ng-click="reqDateCopyShow()"><s:message code="outstockReqDate.reqDateCopy"/></a>
      </li>
    </ul>
  </div>
</div>

<script type="text/javascript">
  var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockReqDate/outstockReqDate.js?ver=20181224.01" charset="utf-8"></script>

<%-- 요일별 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/outstockReqDate/days.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 특정일 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/outstockReqDate/specificDate.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 요청일복사 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/outstockReqDate/reqDateCopy.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
