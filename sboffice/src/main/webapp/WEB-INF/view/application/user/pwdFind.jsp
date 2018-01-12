<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2>Password Find</h2>
  
  <a onClick="">인증번호 받기</a>
  
  <f:form method="post" action="/user/pwdFind.sb">
    <input type="submit" value="확인" />
  </f:form>
  
</div>


<script>
function authNum() {
  
  
  
}
</script>