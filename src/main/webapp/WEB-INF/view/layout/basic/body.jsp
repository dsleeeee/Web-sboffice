<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<body ng-app="rootApp" ng-controller="rootCtrl">

  <c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

  <%-- 유져 타입에 따라 css를 변경 메인 화면 색상을 변경해 준다. --%>
  <c:choose>
    <c:when test="${orgnFg == 'MASTER'}">
      <c:set var="userCss" value="type_Orange" />
    </c:when>
    <c:when test="${orgnFg == 'HQ'}">
      <c:set var="userCss" value="type_Purple" />
    </c:when>
    <c:when test="${orgnFg == 'AGENCY'}">
      <c:set var="userCss" value="type_Blue" />
    </c:when>
    <c:when test="${orgnFg == 'STORE'}">
      <c:set var="userCss" value="type_Green" />
    </c:when>
    <c:otherwise>
      <c:set var="userCss" value="type_Green" />
    </c:otherwise>
  </c:choose>

  <div class="wrap ${userCss}">

    <%-- 왼쪽 메뉴 --%>
    <nav id="_nav" class="menuOpen">
      <tiles:insertAttribute name="menu" />
    </nav>

    <%-- 오른쪽 부분 --%>
    <%-- 헤더 즐겨찾기 부분 --%>
    <header class="topWrap">
      <tiles:insertAttribute name="header" />
    </header>

    <%-- 메인 영역 --%>
    <div class="contents">
      <tiles:insertAttribute name="content" />
    </div>
    <%-- 오른쪽 부분 --%>

  </div>

  <%-- 레이어 팝업 추가 --%>
  <c:import url="/WEB-INF/view/application/layer/alert.jsp">
  </c:import>

  <%-- 로딩 이미지 추가 --%>
  <c:import url="/WEB-INF/view/layout/basic/loading.jsp">
  </c:import>

  <%-- 저장 이미지 추가 --%>
  <c:import url="/WEB-INF/view/layout/basic/saving.jsp">
  </c:import>

  <%-- 즐겨찾기관리 팝업 추가 --%>
  <c:import url="/WEB-INF/view/application/layer/bkmk.jsp">
  </c:import>

<script type="text/javascript" src="/resource/solbipos/js/layout/basic/body.js?ver=20180801" charset="utf-8"></script>

</body>
