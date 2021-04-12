<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2 class="sTit">
    <s:message code="login.find.id" />
  </h2>
  <p class="h2_txt">
    <s:message code="login.id.find.ok.info" />
  </p>

  <div class="idInfo">
    <p><span>${userId}</span></p>
  </div>

  <div class="linkArea">
    <span class="find">
      <a href="/mobile/auth/login.sb" class="fdId">
        <s:message code="login.go" />
      </a>
      <a href="/mobile/user/pwdFind.sb" class="fdPw">
        <s:message code="login.find.pw" />
      </a>
    </span>
  </div>
</div>