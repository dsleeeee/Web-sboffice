<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<s:eval var="ext" expression="@config['url.ext']" />
<c:set var="uri" value="${fn:replace(requestScope['javax.servlet.forward.request_uri'], ext,'')}" />
<c:set var="ssMenuOffice" scope="request" value="${sessionScope[sessionUrlField][uri]}" />
<c:set var="ssMnuCd" scope="request" value="${ssMenuOffice.mnuCd}" />
<c:set var="minimalClssNm" scope="request" value="${empty ssMnuCd ? ' minimal' : ''}" />
<c:set var="firstMenu" value="<%=new java.util.LinkedHashMap<String, String>()%>" />
<c:set var="icons" value="<%=new java.util.LinkedHashMap<String, String>()%>" />


<%--Top bar--%>
<div class="topbar">

  <div class="topbar_logo">
    <%--[D] 메뉴 축소 minimal 추가--%>
    <h1 class="logo">
      <a href="/">
        <span class="hidden"></span>SolbiPos
      </a>
    </h1>
  </div>


  <div class="topbar_right clear">
    <ul>
      <li><i class="fa fa-user top_icon"></i> <span>${sessionScope.sessionInfo.userName} (${sessionScope.sessionInfo.userId})</span></li>
      <li><a id="modifyPasswd" href="/system/user/info/modifyPasswd.sb?userId=${sessionScope.sessionInfo.userId}">
          <i class="fa fa-unlock-alt top_icon"></i>
          <s:message code="label.layout.changePasswd" />
        </a></li>
      <li><a href="/auth/logout.sb">
          <i class="fa fa-sign-in top_icon"></i>
          <s:message code="label.layout.logout" />
        </a></li>
    </ul>
  </div>


  <div class="topbar_menu">

    <%--[D] 메뉴 축소 minimal 추가--%>
    <div class="menu_pullbar">
      <button>
        <i class="fa fa-bars f_s_21 i_menu_pullbar"></i>
      </button>
    </div>
  </div>


</div>
<%--//Top bar--%>




<%--Side menu--%>
<div class="side_menu">
  <ul class="menu_depth_2">
    <li><a href="#">샘플</a></li>

    <ul class="menu_depth_3">
      <li><a href="/exRedis.sb">레디스 이동</a></li>
      <li><a href="/sample3.sb">이동</a></li>
      <li><a href="/sample2.sb">샘플 2 이동</a></li>
    </ul>

  </ul>

  <ul class="menu_depth_2">
    <li><a href="#">그리드</a></li>
    
    <ul class="menu_depth_3">
      <li><a href="/sampleGrid.sb">그리드 샘플 이동(json)</a></li>
      <li><a href="/sampleGrid2.sb">그리드 샘플 이동(test)</a></li>
      <li><a href="/exGridPage.sb">그리드 페이징 샘플 이동</a></li>
      
      <li><a href="/exGridHeader.sb?rnum=1000">그리드 헤더 번역 샘플 이동</a></li>
      <li><a href="/exInput.sb">INPUT 테스트</a></li>
      <li><a href="/exTree.sb">Tree 테스트</a></li>
      <li><a href="/sampleGridMain.sb">그리드 샘플</a></li>
      <!-- 
      <li><a href="/exTreeMenu.sb">메뉴 - 트리구조 샘플</a></li>
      <li><a href="/exTreeStore.sb">본사/매장 - 트리구조  샘플</a></li>
      <li><a href="/exTreeStore2.sb">본사/매장 - 트리구조  샘플 (매장다중선택)</a></li>
      <li><a href="/groupGridSample.sb">본사/매장 - 단순 그리드 구조  Grouping 샘플</a></li>
      <li><a href="/groupGridSample2.sb">본사/매장 - 단순 그리드 구조 Grouping (+ 매장정보) 샘플</a></li>
      <li><a href="/exDragNDrop.sb">drag & drop 샘플</a></li>
      <li><a href="/exDragNDrop2.sb">drag & drop 샘플2 (테스트용)</a></li>
      <li><a href="/exGridInsert.sb">데이터 Insert 테스트</a></li>
       -->
       
       
    </ul>
    
  </ul>
  
  <ul class="menu_depth_2">
    <li><a href="#">에디터</a></li>
    
    <ul class="menu_depth_3">
      <li><a href="/editorSample.sb">에디터 샘플</a></li>
      <li><a href="/editorSample2.sb">에디터 조회저장 샘플</a></li>
      <li><a href="/editorSample3.sb">에디터 데이터 조회</a></li>
    </ul>
    
  </ul>
  
</div>


<%--//Side menu--%>
<script>
  $(document).ready(function() {
  });
</script>




















