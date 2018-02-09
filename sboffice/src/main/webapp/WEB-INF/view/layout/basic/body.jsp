<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<body>
  <div class="wrap type_Blue">
    
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
</body>

<script type="text/javascript">
$(".menuControl").click(function() {
  $("#_nav").toggleClass("menuOpen menuClose");
  $("#_arrow").toggleClass("arrowOpen arrowClose");
});
 
$(".menuTab").click(function() {
  $("#_all").toggleClass("on");
  $("#_favorite").toggleClass("on");
});
</script>












