<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2>Id Find Ok</h2>
  
  <h2>${findUserNm}</h2>  
  
  <a href="/auth/login.sb">로그인 하기</a>
  
  <a href="/user/pwdFind.sb">비밀번호 찾기</a>
  
  <%-- 
  <f:form method="post" action="pwdFind.sb">
    <input type="submit" value="비밀번호 찾기" />
  </f:form>
   --%>
</div>


