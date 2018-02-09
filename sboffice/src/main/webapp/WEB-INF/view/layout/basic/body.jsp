<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<body>

  <div class="wrap type_Blue">
    
    <tiles:insertAttribute name="menu" />
    
    <%-- 오른쪽 메인 부분 --%>
    <div class="contents">
    
      <!--사용자정보영역-->   
      <div class="topBar">
          <div class="menuControl">
              <a href="#" id="_arrow" class="arrowOpen"><span></span></a>
          </div>
          <div class="userInfo">
              <a href="#" class="userNotice"><span>2</span></a><!--새로운 공지 있는경우 span추가-->
              <a href="#" class="userId"><span>kcpmaster</span></a>
          </div>
      </div>
      <!--//사용자정보영역-->
    
      
      <!--고정메뉴-->
      <div class="fixedMenu">
          <!--고정메뉴 없는경우-->
          <p class="empty" style="display:none;">즐겨찾기에서 고정메뉴를 등록하여 편리하게 사용하세요!</p>
          <!--//고정메뉴 없는경우-->
          
          <!--고정메뉴 있는경우-->
          <nav>                
              <ul>
                  <li><a href="#" class="on">POS 로그인현황</a><a href="#" class="btn_close"></a></li>
                  <li><a href="#">설치업체관리</a><a href="#" class="btn_close"></a></li>
                  <li><a href="#">미사용 라이센스 조회</a><a href="#" class="btn_close"></a></li>
              </ul>
              <div class="moveBtn">
                  <a href="#" class="mL" title="왼쪽으로 메뉴 이동"></a>
                  <a href="#" class="mR" title="오른쪽으로 메뉴 이동"></a>
              </div>
          </nav>
          <!--고정메뉴 있는경우-->    
      </div>
      <!--//고정메뉴-->
    
    
    </div>
  
  </div>
  
</body>

<script type="text/javascript">
</script>












