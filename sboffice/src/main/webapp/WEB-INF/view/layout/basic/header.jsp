<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

  <c:set var="hist" value="${sessionScope.sessionInfo.histMenu}" />
  <c:set var="fix" value="${sessionScope.sessionInfo.fixMenu}" />
  <c:set var="histSize" value="${fn:length(hist)}" />
  
  <!--사용자정보영역-->   
  <div class="topBar">
      <div class="menuControl">
        <a href="#" id="_arrow" class="arrowOpen"><span></span></a>
      </div>
      <div class="userInfo">
        <!--새로운 공지 있는경우 span추가-->
        <a href="#" class="userNotice"><span>777</span></a>
        
        <a href="#" class="userId"><span>${sessionScope.sessionInfo.userId}</span></a>
  
        <div class="userLayer" style="display:none;">
          <p>
            <span>${sessionScope.sessionInfo.userId}</span>
            <span> <em>[A01]</em> <em>운영시스템</em></span> 
            <span>${sessionScope.sessionInfo.userName}</span>
          </p>
          <ul>
            <li><a href="#">내 정보 변경</a></li>
            <li><a id="pwchg" >비밀번호 변경</a></li>
            <li><a href="/auth/logout.sb">로그아웃</a></li>
          </ul>
        </div>
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
          
          
              <%-- 즐겨찾기 메뉴 --%>
              <c:forEach var="item" items="${fix}" varStatus="status">
        
                <li id="${item.resrceCd}">
                  <a href="${item.url}" class="${item.activation == true ? 'on' : ''}">${item.resrceNm}</a>
                  <a onclick="deleteFixMenu('${item.resrceCd}');" class="btn_close"></a>
                </li>
                
              </c:forEach>
              
              <%-- 히스토리 메뉴 --%>
              <c:forEach var="item" items="${hist}" varStatus="status">
                <li id="${item.resrceCd}">
                  <a href="${item.url}" class="${item.activation == true ? 'on' : ''}">${item.resrceNm}</a>
                  <a onclick="deleteHistMenu('${item.resrceCd}');" class="btn_close"></a>
                </li>
              </c:forEach>
              
          </ul>
          
          <div class="moveBtn">
              <a href="#" class="mL" title="왼쪽으로 메뉴 이동"></a>
              <a href="#" class="mR" title="오른쪽으로 메뉴 이동"></a>
          </div>
          
      </nav>
      <!--고정메뉴 있는경우-->
      
      <%-- 비밀번호 변경 레이어 팝업 가져오기 --%>
      <c:import url="/WEB-INF/view/application/layer/pwChgPop.jsp">
        <c:param name="type" value="user" />
      </c:import>
      
  </div>
  <!--//고정메뉴-->

<script type="text/javascript">
  $("#pwchg").click(function() {
    $(".fullDimmed").toggle();
    $("#layerpw").toggle();
  });

  $(".userInfo").click(function() {
    $(".userLayer").toggle();
  });

  function deleteHistMenu(menuId) {
    var url = "/menu/delHistMenu.sb";
    callPostJson(url, menuId);
  }

  function deleteFixMenu(menuId) {
    var url = "/menu/delFixMenu.sb";
    callPostJson(url, menuId);
  }

  function callPostJson(url, menuId) {
    var param = {};
    param.menuId = menuId;
    $.postJSON(url, param, function(result) {
      $("#" + menuId).remove();
    }).fail(function() {
      alert("Ajax Fail");
    });
  }
</script>












