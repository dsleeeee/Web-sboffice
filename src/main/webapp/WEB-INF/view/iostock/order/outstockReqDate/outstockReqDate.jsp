<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockReqDate/outstockReqDate/"/>

<div class="con">
  <%-- 본사정보, 메뉴권한, 코드자리수, 환경설정 탭 --%>
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
  /**
   * get application
   */
  var app = agrid.getApp();

  app.controller('outstockReqDateTabCtrl', ['$scope', function ($scope) {
    $scope.init            = function () {
      $("#daysView").show();
    };
    // 요일별 탭 보이기
    $scope.daysShow        = function () {
      $("#daysTab").addClass("on");
      $("#specificTab").removeClass("on");
      $("#reqDateCopyTab").removeClass("on");

      $("#daysView").show();
      $("#specificView").hide();
      $("#reqDateCopyView").hide();
    };
    // 특정일 탭 보이기
    $scope.specificShow    = function () {
      $("#daysTab").removeClass("on");
      $("#specificTab").addClass("on");
      $("#reqDateCopyTab").removeClass("on");

      $("#daysView").hide();
      $("#specificView").show();
      $("#reqDateCopyView").hide();
    };
    // 요청일복사 탭 보이기
    $scope.reqDateCopyShow = function () {
      $("#daysTab").removeClass("on");
      $("#specificTab").removeClass("on");
      $("#reqDateCopyTab").addClass("on");

      $("#daysView").hide();
      $("#specificView").hide();
      $("#reqDateCopyView").show();
    };
  }]);
</script>

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
