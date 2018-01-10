<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2>Id Find</h2>

  <f:form method="post" action="/user/idFind.sb">
    <input type="submit" value="확인" />
  </f:form>
  
  <a href="/auth/login.sb">로그인 화면</a>
</div>


