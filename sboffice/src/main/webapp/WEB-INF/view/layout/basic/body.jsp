<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<body>

  <c:set var="userAuthType" value="${sessionScope.sessionInfo.userAuthType}" />

  <%-- 유져 타입에 따라 css를 변경 메인 화면 색상을 변경해 준다. --%>
  <c:choose>
    <c:when test="${userAuthType == 'SYSTEM'}">
      <c:set var="userCss" value="type_Orange" />
    </c:when>
    <c:when test="${userAuthType == 'HEDOFC'}">
      <c:set var="userCss" value="type_Purple" />
    </c:when>
    <c:when test="${userAuthType == 'AGENCY'}">
      <c:set var="userCss" value="type_Blue" />
    </c:when>
    <c:when test="${userAuthType == 'MRHST'}">
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

  <c:import url="/WEB-INF/view/application/layer/alert.jsp">
  </c:import>

</body>

<script type="text/javascript">

/*
$(".menuTab .all").click(function() {
  $("#_all").addClass("on");
  $("#_favorite").removeClass();
  
  //$("#faMenu").css("display","none");
  $("#faMenu").hide();
  
  showAllMenu();

});

$(".menuTab .favorite").click(function() {
  $("#_all").removeClass();
  $("#_favorite").addClass("on");

  //$("#faMenu").css("display","block");
  $("#faMenu").show();
  
  showBmkMenu();
});
*/
</script>












