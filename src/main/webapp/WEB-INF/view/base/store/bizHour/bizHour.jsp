<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/base/store/bizHour/"/>

<div class="con">
  <%-- 요일별, 특정일 탭 --%>
  <div class="tabType1" ng-controller="bizHourTabCtrl" ng-init="init()">
    <ul>
      <%-- 요일별 탭 --%>
      <li>
        <a id="daysTab" href="#" class="on" ng-click="daysShow()"><s:message code="bizHour.days"/></a>
      </li>
      <%-- 특정일 탭 --%>
      <li>
        <a id="specificTab" href="#" ng-click="specificShow()"><s:message code="bizHour.specificDate"/></a>
      </li>
    </ul>
  </div>
</div>

<script type="text/javascript">
  var orgnFg    = "${orgnFg}";
  var orgnCd    = "${orgnCd}";
  var baseUrl   = "${baseUrl}";
  var sysStatFg = ${ccu.getCommCode("005")};

  // 요일 콤보 (1:일 ~ 7:토)
  var dayFgData = [
    {value: "1", name: "<s:message code='bizHour.day1'/>"},
    {value: "2", name: "<s:message code='bizHour.day2'/>"},
    {value: "3", name: "<s:message code='bizHour.day3'/>"},
    {value: "4", name: "<s:message code='bizHour.day4'/>"},
    {value: "5", name: "<s:message code='bizHour.day5'/>"},
    {value: "6", name: "<s:message code='bizHour.day6'/>"},
    {value: "7", name: "<s:message code='bizHour.day7'/>"}
  ];
  // 영업구분 콤보 (1:영업 2:휴게)
  var bizFgData = [
    {value: "1", name: "<s:message code='bizHour.bizFg1'/>"},
    {value: "2", name: "<s:message code='bizHour.bizFg2'/>"}
  ];
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/bizHour/bizHour.js?ver=20261001.03" charset="utf-8"></script>

<%-- 요일별 레이어 --%>
<c:import url="/WEB-INF/view/base/store/bizHour/days.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 특정일 레이어 --%>
<c:import url="/WEB-INF/view/base/store/bizHour/specificDate.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
