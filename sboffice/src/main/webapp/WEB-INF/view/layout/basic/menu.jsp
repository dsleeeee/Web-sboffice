<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 왼쪽 메뉴 --%>
<nav id="_nav" class="menuOpen">

  <!--로고영역-->
  <h1>
    <a href="#" class="on">
      <span><img src="/resource/solbipos/img/main/logo_main.png" alt="" /></span>
    </a>
  </h1>
  <!-- 활성화 : class="on" -->
  <!--//로고영역-->


  <!--전체,즐겨찾기-->
  <div class="menuTab">
    <p class="all">
      <a href="#" id="_all" class="on">
        <span>전체</span>
      </a>
    </p>
    <!-- 활성화 : class="on" -->
    <p class="favorite">
      <a href="#" id="_favorite">
        <span>즐겨찾기</span>
      </a>
    </p>

  </div>
  <!--//전체,즐겨찾기-->




  <div class="menuTree">

    <!--위즈모 메뉴-->
    <div>
      <div id="theTree"></div>
    </div>
    <!--//위즈모 메뉴-->

  </div>

</nav>

<script>
  
</script>




















