<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="historyMenuData" value="${sessionScope.sessionInfo.historyMenuData}" />
<c:set var="fixedMenuData" value="${sessionScope.sessionInfo.fixedMenuData}" />
<c:set var="historyMenuSize" value="${fn:length(historyMenuData)}" />
<c:set var="sessionId" value="${param.sid}" />

<%-- 사용자정보영역 --%>
<div class="topBar">
  <div class="menuControl">
    <a href="#" id="_arrow" class="arrowOpen">
      <span></span>
    </a>
  </div>
  <div class="userInfo">
    <%--새로운 공지 있는경우 span추가--%>
    <%--<a href="#" class="userNotice">--%>
      <%--<span>777</span>--%>
    <%--</a>--%>

    <a href="#" class="userId">
      <span>${sessionScope.sessionInfo.userId}</span>
    </a>

    <div class="userLayer" style="display: none;">
      <p>
        <span>${sessionScope.sessionInfo.userId}</span>
        <span> 
          <em>[${sessionScope.sessionInfo.orgnCd}]</em> 
          <em>${sessionScope.sessionInfo.orgnNm}</em>
        </span> 
        <span>${sessionScope.sessionInfo.userNm}</span>
      </p>
      <ul>
        <li><a href="#">내 정보 변경</a></li>
        <li><a href="#" id="pwchg">비밀번호 변경</a></li>
        <li><a href="/auth/logout.sb">로그아웃</a></li>
      </ul>
    </div>
  </div>
</div>
<%--//사용자정보영역--%>


<%--고정메뉴--%>
<div class="fixedMenu">

  <%--고정메뉴 없는경우--%>
  <p class="empty" style="display: none;">즐겨찾기에서 고정메뉴를 등록하여 편리하게 사용하세요!</p>
  <%--//고정메뉴 없는경우--%>

  <%--고정메뉴 있는경우--%>
  <nav>
    <ul id="_fixMenu">
      <%-- 즐겨찾기 메뉴 --%>
      <c:forEach var="item" items="${fixedMenuData}" varStatus="status">
        <li id="${item.resrceCd}">
          <c:if test="${sessionId ne null}">
            <a href="${item.url}?sid=${sessionId}" class="${item.activation == true ? 'on' : ''}">${item.resrceNm}</a>
          </c:if>
          <c:if test="${sessionId eq null}">
            <a href="${item.url}" class="${item.activation == true ? 'on' : ''}">${item.resrceNm}</a>
          </c:if>
          <a href="#" class="btn_close favClose" data-value="${item.resrceCd}" ></a>
        </li>
      </c:forEach>

      <%-- 히스토리 메뉴 --%>
      <c:forEach var="item" items="${historyMenuData}" varStatus="status">
        <li id="${item.resrceCd}">
          <c:if test="${sessionId ne null}">
            <a href="${item.url}?sid=${sessionId}" class="${item.activation == true ? 'on' : ''}">${item.resrceNm}</a>
          </c:if>
          <c:if test="${sessionId eq null}">
            <a href="${item.url}" class="${item.activation == true ? 'on' : ''}">${item.resrceNm}</a>
          </c:if>
          <a href="#" class="btn_close histClose" data-value="${item.resrceCd}"></a>
        </li>
      </c:forEach>
    </ul>

    <div class="moveBtn">
      <a href="#" class="mL" title="왼쪽으로 메뉴 이동"></a>
      <a href="#" class="mR" title="오른쪽으로 메뉴 이동"></a>
    </div>

  </nav>
  <%--고정메뉴 있는경우--%>

  <%-- 비밀번호 변경 레이어 팝업 가져오기 --%>
  <c:import url="/WEB-INF/view/application/layer/pwChgPop.jsp">
    <c:param name="type" value="user" />
  </c:import>
  
</div>
<%--//고정메뉴--%>

<script type="text/javascript" src="/resource/solbipos/js/layout/basic/header.js?ver=2018100401" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/variables/commonVariables.js?ver=2018100401" charset="utf-8"></script>
