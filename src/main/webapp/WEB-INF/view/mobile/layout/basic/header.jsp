<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="historyMenuData" value="${sessionScope.sessionInfo.historyMenuData}" />
<c:set var="fixedMenuData" value="${sessionScope.sessionInfo.fixedMenuData}" />
<c:set var="historyMenuSize" value="${fn:length(historyMenuData)}" />
<c:set var="sessionId" value="${param.sid}" />
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}" />

<%-- 사용자정보영역 --%>
<div class="topBar">
  <div class="menuControl" style="float:left;">
    <a href="#" id="_arrow" class="arrowClose">
      <span></span>
    </a>
  </div>
  <div class="userNm">
     <span>
       <em>[${sessionScope.sessionInfo.orgnCd}]</em>
       <em><label id="lblOrgnNm"></label></em>
     </span>
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
        <%--<li><a href="#">내 정보 변경</a></li>--%>
        <li><a href="#" id="pwchg">비밀번호 변경</a></li>
        <li><a href="/mobile/auth/logout.sb">로그아웃</a></li>
      </ul>
    </div>
  </div>
</div>
<%--//사용자정보영역--%>

<%-- 비밀번호 변경 레이어 팝업 가져오기 --%>
<c:import url="/WEB-INF/view/mobile/application/layer/pwChgPop.jsp">
  <c:param name="type" value="user" />
</c:import>

<script type="text/javascript" src="/resource/solbipos/js/mobile/layout/basic/header.js?ver=2018100401" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/variables/commonVariables.js?ver=2018100401" charset="utf-8"></script>

<script type="text/javascript">
  $(document).ready(function() {
    var str = "${orgnNm}";
    var length = 13; // 표시할 글자수 기준

    if (str.length > length) {
      str = str.substr(0, length-2) + '...';
    }
    $("#lblOrgnNm").text(str);
  });
</script>
