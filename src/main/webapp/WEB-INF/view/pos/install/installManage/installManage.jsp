<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div ng-controller="installManageCtrl">
  <%-- 탭  //todo --%>
  <%--
  <div>
    <ul class="subTab mt20">
      &lt;%&ndash; 설치요청 &ndash;%&gt;
      <li><a id="reqTab" href="#" class="on" ng-click="changeTab('REQ');"><s:message code="instl.install.request" /></a></li>
      &lt;%&ndash; 업체현황 &ndash;%&gt;
      <li><a id="corpStatusTab" href="#" ng-click="changeTab('M');"><s:message code="instl.company.status" /></a></li>
      &lt;%&ndash; 운영현황 &ndash;%&gt;
      <li><a id="operStatusTab" href="#" ng-click="changeTab('M');"><s:message code="instl.operation.status" /></a></li>
    </ul>
  </div>
  --%>
  <%-- 설치요청 --%>
  <c:import url="/WEB-INF/view/pos/install/installManage/installRequestList.jsp">
  </c:import>

  <%-- 업체현황 --%>
  <%--
  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuSelectMenu.jsp">
  </c:import>
  --%>

  <%-- 운영현황 --%>
  <%--
  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuSelectMenu.jsp">
  </c:import>
 --%>

</div>
<script type="text/javascript" src="/resource/solbipos/js/pos/install/installManage/installManage.js?ver=2018102301" charset="utf-8"></script>
