<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="loginArea">
  <h2>Welcome Login</h2>

  <f:form class="loginF" modelAttribute="sessionInfo" method="post" action="/auth/login.sb">
    
<%--     <s:eval expression="@env['login.check.id.save']" var="idField"/> --%>
    <c:set var="cid" value="${cookie.saveid.value}" />

    <div class="writeInfo">
      <input class="id" type="text" id="userId" name="userId" placeholder="<s:message code="label.login.userId"/>" value="${cid}" required="required" /><label for="userId"></label>
<%-- <f:errors path="userId"/> --%>
      <input class="pw" type="password" id="userPwd" name="userPwd" placeholder="<s:message code="label.login.userPasswd"/>" required="required" /><label for="userPwd"></label>
<%-- <f:errors path="userPwd"/> --%>
    </div>

    <div class="idsave">
      <span> 
        <input type="checkbox" id="chk" name="chk" ${empty cid ? '' : 'checked="checked"' } /> 
        <label for="chk"> <s:message code="label.login.rememberId" />
        </label>
      </span>
      <button class="btn_login">
        <s:message code="label.login.submit" />
      </button>
    </div>

  </f:form>

  <div class="linkArea">
    <span class="find"> 
      <a href="/user/idFind.sb" class="fdId">
        <s:message code="label.login.find.id" />
      </a> 
      <a href="/user/pwdFind.sb" class="fdPw">
        <s:message code="label.login.find.pw" />
      </a>
    </span>
    <a href="http://www.solbipos.com" target="_blank" class="distributor">
      <s:message code="label.login.add.dist" />
    </a>
    <a href="http://www.solbipos.com" target="_blank" class="agency">
      <s:message code="label.login.add.agency" />
    </a>
  </div>

</div>


