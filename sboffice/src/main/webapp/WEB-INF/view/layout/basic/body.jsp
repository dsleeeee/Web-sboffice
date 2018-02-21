<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
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
    <tiles:insertAttribute name="menu" />
    
    <%-- 오른쪽 메인 부분 --%>
    <div class="contents">
      
      <%-- 헤더 즐겨찾기 부분 --%>
      <tiles:insertAttribute name="header" />
            
      <%-- 메인 영역 --%>
      <div>
      <tiles:insertAttribute name="content" />
      </div>
    
    </div>
    <%-- 오른쪽 메인 부분 --%>
    
  </div>
  
  
  <div class="fullDimmed" style="display:none;"></div>
  <div id="layerpop" class="layer" style="display:none;">
    <div class="layer_inner">
      <!--layerContent-->
      <div class="noTitle w500">
        
        <p id="lbk" class="bk"></p>
        
        <div class="btnSet">
          <span><a href="#" class="btn_blue">확인</a></span></span>
        </div>
      </div>
      <!--//layerContent-->
    </div>
  </div>

</body>

<script type="text/javascript">

$(".btn_blue").click(function() {
  $(".fullDimmed").toggle();
  $("#layerpop").toggle();
});

$(".menuControl").click(function() {
  $("#_nav").toggleClass("menuOpen menuClose");
  $("#_arrow").toggleClass("arrowOpen arrowClose");
});
$(".menuTab").click(function() {
  $("#_all").toggleClass("on");
  $("#_favorite").toggleClass("on");
});
</script>












