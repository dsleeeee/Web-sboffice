<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2 class="sTit">
    <s:message code="label.login.userId" />
    &nbsp;
    <s:message code="label.cmm.find" />
  </h2>
  <p class="h2_txt">
    <s:message code="label.id.find.ok.info" />
  </p>

  <div class="idInfo">
    <span><s:message code="label.login.userId" /></span>
    <ul>
      <c:forEach var="item" items="${list}" varStatus="status">
        <li><span>${item.userId}</span> (<span>${item.orgnNm}</span>)</li>
      </c:forEach>
    </ul>

  </div>

  <div class="linkArea">
    <span class="find"> <a href="/auth/login.sb" class="login_blue">
        <s:message code="label.login.go" />
      </a> <a href="/user/pwdFind.sb" class="fdPw">
        <s:message code="label.login.find.pw" />
      </a>
    </span>
  </div>
</div>


