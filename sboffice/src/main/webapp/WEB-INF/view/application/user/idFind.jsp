<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2>Id Find</h2>

  <f:form method="post" modelAttribute="user" action="/user/idFind.sb">
    <div class="writeInfo">
      <input type="text" name="empNm" placeholder="담당자 이름" value="${user.empNm}" />
      <f:errors path="empNm"/>
      <input type="text" name="mpNo" placeholder="전화번호 또는 휴대폰 번호(숫자만 입력)" value="${user.mpNo}" />
      <f:errors path="mpNo"/>
    </div>
    <input class="btn_login" type="submit" value="확인" />
  </f:form>
  
  <a href="/auth/login.sb">로그인 화면</a>
</div>


