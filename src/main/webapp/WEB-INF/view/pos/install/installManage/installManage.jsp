<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="installManageCtrl">
  <%-- 탭 --%>
  <div>
    <ul class="subTab mt20">
      <%-- 설치요청 --%>
      <li><a id="reqTab" href="#" class="on" ng-click="changeTab('REQ');"><s:message code="instl.install.request" /></a></li>
      <%-- 업체현황 --%>
      <li><a id="corpStatusTab" href="#" ng-click="changeTab('M');"><s:message code="instl.company.status" /></a></li>
      <%-- 운영현황 --%>
      <li><a id="operStatusTab" href="#" ng-click="changeTab('M');"><s:message code="instl.operation.status" /></a></li>
    </ul>
  </div>

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

<%-- 매장정보 --%>
<%--
<c:import url="/WEB-INF/view/store/manage/storeManage/storeInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
--%>

<%-- 환경변수 --%>
<%--
<c:import url="/WEB-INF/view/store/manage/storeManage/storeEnv.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
--%>
